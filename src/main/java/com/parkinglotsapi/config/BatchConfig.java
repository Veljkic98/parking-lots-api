package com.parkinglotsapi.config;

import com.parkinglotsapi.domain.dto.ParkingLotDto;
import com.parkinglotsapi.domain.models.ParkingLot;
import com.parkinglotsapi.mappers.ParkingLotFieldSetMapper;
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

    @Value("classPath:/input/${input.fileName}")
    private Resource inputFile;

    @Bean
    public Job readCSVFileJob() {
        return jobBuilderFactory
                .get("readCSVFileJob")
                .incrementer(new RunIdIncrementer())
                .start(readAndPersist())
                .build();
    }

    @Bean
    public Step readAndPersist() {
        ParkingLotSkipPolicy skipPolicy = new ParkingLotSkipPolicy();
        return stepBuilderFactory
                .get("step")
                .<ParkingLotDto, ParkingLot>chunk(50)
                .reader(parkingLotCsvReader())
                .processor(parkingLotProcessor())
                .writer(parkingLotWriter())
                .faultTolerant()
                .skipPolicy(skipPolicy)
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public FlatFileItemReader<ParkingLotDto> parkingLotCsvReader() {
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setNames("Latitude", "Longitude", "A_Name", "Year", "Type");

        DefaultLineMapper<ParkingLotDto> defaultLineMapper = new DefaultLineMapper<>();
        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
        defaultLineMapper.setFieldSetMapper(new ParkingLotFieldSetMapper());

        FlatFileItemReader<ParkingLotDto> reader = new FlatFileItemReader<>();
        reader.setLineMapper(defaultLineMapper);
        reader.setResource(inputFile);
        reader.setLinesToSkip(1);

        return reader;
    }

    @Bean
    public ItemProcessor<ParkingLotDto, ParkingLot> parkingLotProcessor() {
        return new ParkingLotProcessor();
    }

    @Bean
    public ParkingLotWriter<ParkingLot> parkingLotWriter() {
        return new ParkingLotWriter<>();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(50);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("task exec. - ");
        executor.initialize();
        return executor;
    }

}
