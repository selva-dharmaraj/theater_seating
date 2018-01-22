package edu.selva.batch.controller;

//@RestController
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