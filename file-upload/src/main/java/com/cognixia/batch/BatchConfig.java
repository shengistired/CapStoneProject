package com.cognixia.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import com.cognixia.model.FileDetails;


public class BatchConfig {
	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public FlatFileItemReader<FileDetails> reader() {
		return new FlatFileItemReaderBuilder<FileDetails>()
			.name("fileItemReader")
			.resource(new ClassPathResource("reconFile.csv"))
			.delimited()
			.names(new String[]{"id", "fileName", "fileType", "fileSize", "uploadedBy", "timeStamp"})
			.fieldSetMapper(new BeanWrapperFieldSetMapper<FileDetails>() {{
				setTargetType(FileDetails.class);
			}})
			.build();
	}
	
	@Bean
	public FileItemProcessor processor() {
		return new FileItemProcessor();
	}
	@Bean
	public ItemWriter<FileDetails> itemWriter() {
		return items -> {
			for (FileDetails c : items) {
				System.out.println(c.getFileName().toString());
			}
		};
	}
	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1").<FileDetails, FileDetails>chunk(50).reader(reader()).processor(processor())
				.writer(itemWriter()).build();
	}

	@Bean
	public Job job(Step step1) {
		return jobBuilderFactory.get("job").start(step1).build();
	}
}
