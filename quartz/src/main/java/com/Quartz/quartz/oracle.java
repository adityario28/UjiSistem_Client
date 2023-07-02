/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Quartz.quartz;

import static com.Quartz.oracle.CreateTable.CreateAllTable;
import static com.Quartz.oracle.InsertTable.InsertAllTable;
import java.util.Date;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author LENOVO IP SLIM 3
 */
public class oracle implements Job {

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        oracle ();
    }
    
    public void oracle () {
        try {
            String DB_URL = "jdbc:oracle:thin:@localhost:1523/orcl";
            String USER = "system";
            String PASS = "agung2002";
            
            Date awal = new Date(System.currentTimeMillis());

//            System.out.println(CreateAllTable(DB_URL,USER,PASS));
            System.out.println(InsertAllTable(DB_URL,USER,PASS));
            Date akhir = new Date(System.currentTimeMillis());
            long waktu = (akhir.getTime() - awal.getTime());
            
            System.out.println("Waktu yang dibutuhkan oracle = " + waktu + " milidetik");
        } catch (Exception e) {
        }
    }
}
