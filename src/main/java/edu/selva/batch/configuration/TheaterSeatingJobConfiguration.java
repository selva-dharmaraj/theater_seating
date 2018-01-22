package edu.selva.batch.configuration;

import edu.selva.batch.listener.JobCompletionNotificationListener;
import edu.selva.batch.pojo.*;
import edu.selva.batch.tasklet.SeatingArrangementTask;
import edu.selva.batch.util.TicketRequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.validation.BindException;

@Configuration
public class TheaterSeatingJobConfiguration {
  private static final Logger LOGGER =
      LoggerFactory.getLogger(TheaterSeatingJobConfiguration.class);

  @Autowired private JobBuilderFactory jobBuilderFactory;

  @Autowired private StepBuilderFactory stepBuilderFactory;

  private TheaterLayout theaterLayout = new TheaterLayout();

  private TicketRequestHandler mailInRequests = new TicketRequestHandler();

  @Bean
  @StepScope
  public FlatFileItemReader<FieldSet> theaterLayoutReader(
      @Value("${layoutFile}") Resource resource) {
    FlatFileItemReader<FieldSet> reader = new FlatFileItemReader<>();
    // reader.setResource(new ClassPathResource("theater_layout.txt"));
    reader.setResource(resource);

    reader.setLineMapper(
        new DefaultLineMapper<FieldSet>() {
          {
            setLineTokenizer(
                new DelimitedLineTokenizer() {
                  {
                    setDelimiter(" ");
                    setFieldSetMapper(
                        new FieldSetMapper<FieldSet>() {
                          @Override
                          public FieldSet mapFieldSet(FieldSet fieldSet) throws BindException {
                            return fieldSet;
                          }
                        });
                  }
                });
          }
        });
    System.out.println(reader);
    return reader;
  }

  @Bean
  public ItemProcessor<FieldSet, FieldSet> theaterLayoutProcessor() {
    return new ItemProcessor() {
      @Override
      public Object process(Object o) throws Exception {
        FieldSet line = (FieldSet) o;
        int noOfSection = line.getFieldCount();
        if (noOfSection > 0) {
          int rowNumber = theaterLayout.getRowsCount() + 1;
          Row row = new Row(rowNumber);
          for (int i = 0; i < noOfSection; i++) {
            String sectionName = rowNumber + "_" + (i + 1);
            Section section = new Section(sectionName);
            section.setSectionNumber(i + 1);
            int seats = line.readInt(i);
            for (int j = 0; j < seats; j++) {
              String seatNumber = sectionName + (j + 1);
              Seat seat = new Seat(seatNumber);
              section.addSeat(seat);
            }
            row.addSection(section);
          }
          theaterLayout.addRow(row);
        }

        return o;
      }
    };
  }

  @Bean
  @StepScope
  public FlatFileItemReader<MailInRequest> mailInRequestReader(
      @Value("${requestFile}") Resource resource) {
    FlatFileItemReader<MailInRequest> reader = new FlatFileItemReader<MailInRequest>();
    reader.setResource(resource);
    reader.setLineMapper(
        new DefaultLineMapper<MailInRequest>() {
          {
            setLineTokenizer(
                new DelimitedLineTokenizer() {
                  {
                    setDelimiter(" ");
                    setNames(new String[] {"customerName", "ticketsCount"});
                  }
                });
            setFieldSetMapper(
                new BeanWrapperFieldSetMapper<MailInRequest>() {
                  {
                    setTargetType(MailInRequest.class);
                  }
                });
          }
        });
    return reader;
  }

  @Bean
  public ItemProcessor<MailInRequest, MailInRequest> mailInRequestProcessor() {
    return new ItemProcessor() {
      @Override
      public MailInRequest process(Object o) throws Exception {
        mailInRequests.addMailInRequest((MailInRequest) o);
        return null;
      }
    };
  }

  @Bean
  public Step seatingArrangementStep() {
    return stepBuilderFactory
        .get("seatingArrangementStep")
        .tasklet(new SeatingArrangementTask(theaterLayout, mailInRequests))
        .build();
  }

  @Bean
  public Step readTheaterLayoutStep() {
    return stepBuilderFactory
        .get("readTheaterLayoutStep")
        .<FieldSet, FieldSet>chunk(10)
        .reader(theaterLayoutReader(null))
        .processor(theaterLayoutProcessor())
        .build();
  }

  @Bean
  public Step readMailInRequestStep() {
    return stepBuilderFactory
        .get("readMailInRequestStep")
        .<MailInRequest, MailInRequest>chunk(10)
        .reader(mailInRequestReader(null))
        .processor(mailInRequestProcessor())
        .build();
  }

  @Bean
  public Job theaterSeatingJob(JobCompletionNotificationListener listener) {
    return jobBuilderFactory
        .get("theaterSeatingJob")
        .incrementer(new RunIdIncrementer())
        .listener(listener)
        .flow(readTheaterLayoutStep())
        .next(readMailInRequestStep())
        .next(seatingArrangementStep())
        .end()
        .build();
  }
}
