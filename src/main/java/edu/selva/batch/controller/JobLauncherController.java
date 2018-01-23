package edu.selva.batch.controller;

// This job can be easily exposed through web if required in future.

// @RestController

/**
 * DOCUMENT ME!
 *
 * @author   Selva Dharmaraj
 * @version 1.0, 2018-01-22
 */
public class JobLauncherController {
  /* @Autowired
   JobLauncher jobLauncher;

   @Autowired
   Job job;

  //@RequestMapping("/launchjob")
   public String handle() throws Exception {

     Logger logger = LoggerFactory.getLogger(this.getClass());
     try {
       JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
           .toJobParameters();
       jobLauncher.run(job, jobParameters);
     } catch (Exception e) {
       logger.info(e.getMessage());
     }

     return "Done!";
   }*/
}
