/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Quartz.quartz;

import static com.Quartz.postgree.CreateTable.CreateAllTable;
import static com.Quartz.postgree.InsertTable.InsertAllTable;
import java.util.Date;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author LENOVO IP SLIM 3
 */
public class postgree implements Job {

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        postgree();
    }

    public void postgree() {
        try {

//            String DB_URL = "jdbc:postgresql://localhost:5433/ujisistemc";
//            String USER = "adityario28";
//            String PASS = "Mahesario28.";
String DATA = "C:\\Users\\LENOVO IP SLIM 3\\Documents\\Semester 9\\Uji Sistem\\TR UJI SISTEM\\All_Data-5\\Data-5\\ALL\\";
//            String DATA = "D:\\Tugas\\Semester9\\Pengujian Sistem\\Data-5\\Data-5\\Full\\";
            String URL = "http://localhost:67/insertByte";

            Date awal = new Date(System.currentTimeMillis());

//            System.out.println(CreateAllTable(DB_URL, USER, PASS));
            System.out.println(InsertAllTable(DATA, URL));
//            Date akhir = new Date(System.currentTimeMillis());
//            long waktu = (akhir.getTime() - awal.getTime());

//            System.out.println("Waktu yang dibutuhkan postgree = " + waktu + " milidetik");
        } catch (Exception e) {
        }
    }
}
