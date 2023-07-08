/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.Quartz.quartz;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nadya Mycota
 */
public class Client {

    /**
     * @param args the command line arguments
     */
     public static String send2ServiceC1(String endpoint, List<String> data){
        String response = "";
        try {
            URL url = new URL(endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            
            Gson gson = new Gson();
            String input= gson.toJson(data, List.class);
            
            OutputStream os = conn.getOutputStream();
            System.out.println(input);
            os.write(input.getBytes());
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
    
    public static void main(String[] args) {
        // TODO code application logic here
        // System.out.println("Test");
        try {
            String endpointC1 = "http://localhost:68/insertByte";
//            String dir = "C:\\Users\\LENOVO IP SLIM 3\\Documents\\Semester 9\\Uji Sistem\\TR UJI SISTEM\\All_Data-5\\Data-5\\ALL\\";
            String dir = "D:\\Tugas\\Semester9\\Pengujian Sistem\\Data-5\\Data-5\\Full\\";
            
            String[] files = new File(dir).list();
            List<String> listRequest = new ArrayList<>();
            for(String fileName : files){
//                System.out.println(fileName);
                
                //read file
                FileReader reader = new FileReader(dir + fileName);
                BufferedReader bfr = new BufferedReader(reader);
                String line = bfr.readLine();
                while(line != null){
                    listRequest.add(line);
                    line = bfr.readLine();
                }
                bfr.close();
                reader.close();
            }
            
            for (String line : listRequest){
                System.out.println(line);
            }
            
            String result = send2ServiceC1(endpointC1, listRequest);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        
    }
    

