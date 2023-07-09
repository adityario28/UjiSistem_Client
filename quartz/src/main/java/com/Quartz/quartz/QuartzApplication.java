package com.Quartz.quartz;

import java.util.Date;
import org.quartz.DateBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author LENOVO IP SLIM 3
 */

@SpringBootApplication
public class QuartzApplication {

	public static void main(String[] args) {
//		SpringApplication.run(QuartzApplication.class, args);
                
                try {
            SchedulerFactory sdf =  new StdSchedulerFactory();
            Scheduler scheduler = sdf.getScheduler();
            
            
            
            JobDetail jobDetail_1 = new JobDetailImpl("jobDetail_1", postgree.class);
            JobDetail jobDetail_2 = new JobDetailImpl("jobDetail_2", oracle.class);
            
            //Trigger 1
            SimpleTriggerImpl postgree = new SimpleTriggerImpl("Tringgerc1");
//            Date startTime = DateBuilder.futureDate(10, DateBuilder.IntervalUnit.SECOND); // Mengatur waktu mulai 10 detik dari sekarang
//            postgree.setStartTime(DateBuilder.todayAt(17, 40, 0)); // Set jam jam/ menit/ detik
//            postgree.setRepeatCount(10); //Ulang
//            postgree.setNextFireTime(new Date(System.currentTimeMillis())); // Tanggal
//            postgree.setRepeatInterval(1000); //Berapa detik 1000 == 1s
            
            //Trigger2
            SimpleTriggerImpl oracle = new SimpleTriggerImpl("Tringgerc2");
//            Date startTime2 = DateBuilder.futureDate(10, DateBuilder.IntervalUnit.SECOND); // Mengatur waktu mulai 10 detik dari sekarang
//            oracle.setStartTime(DateBuilder.todayAt(20, 33, 0)); // Set jam jam/ menit/ detik
//            oracle.setRepeatCount(10); //Ulang
//            oracle.setNextFireTime(new Date(System.currentTimeMillis())); // Tanggal
//            oracle.setRepeatInterval(1000); //Berapa detik 1000 == 1s
            
            scheduler.scheduleJob(jobDetail_1, postgree);
            scheduler.scheduleJob(jobDetail_2, oracle);
            
            scheduler.start();
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
