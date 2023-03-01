package com.parkinglotsapi.config;

import com.parkinglotsapi.domain.ParkingLotDto;
import com.parkinglotsapi.domain.models.ParkingLot;
import com.parkinglotsapi.mappers.ParkingSpotFieldSetMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    @Value("classPath:/input/LA parking lot.csv")
    private Resource inputFile;

    @Bean
    public Job readCSVFileJob() {
        return jobBuilderFactory
                .get("readCSVFileJob")
                .incrementer(new RunIdIncrementer())
                .start(step())
                .build();
    }

    @Bean
    public Step step() {
        ParkingLotSkipPolicy skipPolicy = new ParkingLotSkipPolicy();
        return stepBuilderFactory
                .get("step")
                .<ParkingLotDto, ParkingLot>chunk(50)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .faultTolerant()
                .skipPolicy(skipPolicy)
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public FlatFileItemReader<ParkingLotDto> reader() {
        System.out.println("USAOOOO");
        FlatFileItemReader<ParkingLotDto> reader = new FlatFileItemReader<>();
        reader.setResource(inputFile);
        reader.setLinesToSkip(1);
        DefaultLineMapper<ParkingLotDto> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setNames("Latitude", "Longitude", "A_Name", "Year", "Type");
        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
        defaultLineMapper.setFieldSetMapper(new ParkingSpotFieldSetMapper());
        reader.setLineMapper(defaultLineMapper);
        return reader;
    }

    @Bean
    public ItemProcessor<ParkingLotDto, ParkingLot> processor() {
        return new ParkingLotProcessor();
    }

    @Bean
    public ParkingLotRepositoryWriter<ParkingLot> writer() {
        return new ParkingLotRepositoryWriter<>();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(50);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("task executor - ");
        executor.initialize();
        return executor;
    }

}
