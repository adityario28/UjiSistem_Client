/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Quartz.oracle;

import static com.Quartz.oracle.CreateTable.CreateTable;
import static com.Quartz.oracle.CreateTable.line_map_path;
import static com.Quartz.oracle.CreateTable.table_map_path;
import static com.Quartz.oracle.Mapping.HashMapFromTextFile;
import static com.Quartz.quartz.Client.send2ServiceC1;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

/**
 *
 * @author LENOVO IP SLIM 3
 */
public class InsertTable {
    
    final static String line_map_val = "C:\\Users\\LENOVO IP SLIM 3\\Documents\\Semester 9\\Uji Sistem\\TR UJI SISTEM\\All_Data-5\\Data-5\\ALL\\";
    private static JdbcTemplate jdbcTemplate;
    
    public static String InsertAllTable(String data, String url) throws Exception{
        String status = null;
        try {
            InsertIntoTable(data,url);
            status = "Oracle table Inserted Succesfully";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }
    
    public static String sendtoOracle(String endpoint, String h, String v){
        String response = "";
        try {
            URL url = new URL(endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            
            Gson gson = new Gson();
            JsonObject data = new JsonObject();
            data.addProperty(h, v);
            String gs= gson.toJson(data);
            
            OutputStream os = conn.getOutputStream();
            os.write(gs.getBytes());
            os.flush();
            
            if (conn.getResponseCode()!= HttpURLConnection.HTTP_OK){
                throw new RuntimeException("Failed : HTPP error code : "
                        + conn.getResponseCode());
            }
            
            BufferedReader br = new BufferedReader (new InputStreamReader((conn.getInputStream())));
            
            String output;
            
            while ((output = br.readLine()) != null){
            response = output;
        }
            
            conn.disconnect();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    
    public static void InsertIntoTable(String data, String url) throws IOException{
        //Ambil data map dan key
        Map<String, String> tipe_l = HashMapFromTextFile ("map/line_map.txt");
        String key = "";
        for (Map.Entry<String, String> entry : tipe_l.entrySet()) {
            key = key + entry.getKey() + ",";
        }
        Map<String, String> tipe_t = HashMapFromTextFile ("map/table_map.txt"); 
        for (Map.Entry<String, String> entry : tipe_t.entrySet()) {
            key = key + entry.getKey() + ",";
        }
        
        BufferedReader br = null;
        String[] files = new File(data).list();
        
        try {
            for (String filename : files) {
                String header = "";
                int i = 0;
                int j = 0;
                FileReader reader = new FileReader(data + filename);
                BufferedReader bfr = new BufferedReader(reader);
                String line = bfr.readLine();  
                boolean firstline = true;

                while(line != null){
                    List<String> listheader = new ArrayList<>();
                    String h = "";
                    List<String> listvalue = new ArrayList<>();
                    String v = "";
                   
                    if (!firstline) {
                        String replace = line.replace("\"", "");
                        String[] headerr = header.split(",");
                        String[] value = replace.split(",");
                        int length = headerr.length;
                        
                    for (i = 0; i < length; i++) {
                        String head = headerr[i].trim();
                        String val = value[i].trim();
                        String tipel = tipe_l.get(headerr[i].trim());
                        String tipet = tipe_t.get(headerr[i].trim());
                        
                        //Mengeliminasi kolom ganda dan nama kolom salah
                        if ((!listheader.contains(head)) && ((key.toUpperCase()).contains(head.toUpperCase()))) {
                            //Mengganti nama kolom
                            if (head.equals("MODE")) {
                                listvalue.add("\'" + val + "\'");
                                listheader.add(head + "_1");
                                
                            }
                            else if (head.equals("OPERATOR")) {
                                listvalue.add("\'" + val + "\'");
                                listheader.add(head + "_1");

                            }
                            //MenentukanDataKosong
                            else if (val.equals("")) {
                                String b = null;
                                listvalue.add(b);
                                listheader.add(head);
                            
                            }
                            //Menentukan INT
                            else if ((tipel != null && tipel.equals("int")) || (tipet != null && tipet.equals("int"))) {
                                listvalue.add(val);
                                listheader.add(head);

                            }
                            //Menentukan Date
                            else if ((tipel != null && tipel.equals("Date")) || (tipet != null && tipet.equals("Date"))) {
                                String b = "\'" + val.substring(0, 4) + "-" + val.substring(4, 6) + "-" + val.substring(6, 8) + "\'";
                                listvalue.add("TO_DATE(" + b + ",'YYYY-MM-DD')");
                                listheader.add(head);

                            }
                            //Menentukan Time
                            else if (head.equals("RCVTIME")) {
                                String b = null;
                                listvalue.add(b);
                                listheader.add(head);

                            }
                            else {
                                listvalue.add("\'" + val + "\'");
                                listheader.add(head);

                            }
                        }
                        
                    }
                    } else {
                        header = line;
                        h = null;
                        firstline = false;
                    }
                    
                    //Menggabungkan data
                    for (j = 0; j < listheader.size(); j++) {
                        if (j != listheader.size() - 1){
                            v = v + listvalue.get(j) + ",";
                            h = h + listheader.get(j) + ",";
                        } else {
                            v = v + listvalue.get(j);
                            h = h + listheader.get(j);
                        }
                    }
                    
                    //Insert data
                    if (h != null) {
                        try {
                            sendtoOracle(url, h, v);
//                            if (h.contains("HoyaItemType")){
//                                   jdbcTemplate.execute("INSERT INTO TABLESTOCK (" + h + ") VALUES (" + v + ")");
//                            } else {
//                                   jdbcTemplate.execute("INSERT INTO LINESTOCK (" + h + ") VALUES (" + v + ")");
//                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    line = bfr.readLine();
                }
                bfr.close();
                reader.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            // Always close the BufferedReader
            if (br != null) {
                try {
                    br.close();
                }
                catch (Exception e) {
                };
            }
//            ds.destroy();
        }
    }
}
