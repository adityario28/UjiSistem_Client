package com.Quartz.quartz;

import java.util.Date;
import org.junit.Test;
import org.quartz.DateBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.SimpleTriggerImpl;

/**
 *
 * @author LENOVO IP SLIM 3
 */

//@Test
public class QuartzApplication {


    public static void main(String[] args) {
        try {
            SchedulerFactory sdf = new StdSchedulerFactory();
            Scheduler scheduler = sdf.getScheduler();

            JobDetail jobDetail_1 = new JobDetailImpl("jobDetail_1", postgree.class);
            JobDetail jobDetail_2 = new JobDetailImpl("jobDetail_2", oracle.class);

            //Trigger 1
            SimpleTriggerImpl postgree = new SimpleTriggerImpl("Tringgerc1");

            //Trigger2
            SimpleTriggerImpl oracle = new SimpleTriggerImpl("Tringgerc2");

            scheduler.scheduleJob(jobDetail_1, postgree);
            scheduler.scheduleJob(jobDetail_2, oracle);

            scheduler.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
