package com.cognixia;

import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.amqp.AmqpItemReader;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.cognixia.model.FileDetails;

@Configuration
@EnableBatchProcessing
@EnableAutoConfiguration(exclude = {HibernateJpaAutoConfiguration.class})
public class BatchConfig  extends DefaultBatchConfigurer{
    @Override
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(null);
    }
    
	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	private Resource outputResource = new FileSystemResource("reconFile.csv");

	@Bean
	public FlatFileItemWriter<FileDetails> fileWriter() {
		FlatFileItemWriter<FileDetails> writer = new FlatFileItemWriter<>();
		writer.setResource(outputResource);
		writer.setAppendAllowed(true);

		writer.setLineAggregator(new DelimitedLineAggregator<FileDetails>() {
			{
				setDelimiter(",");
				setFieldExtractor(new BeanWrapperFieldExtractor<FileDetails>() {
					{

						setNames(new String[] { "id", "fileName", "fileType", "fileSize", "uploadedBy", "timeStamp" });
					}
				});
			}
		});
		return writer;
	}

	@Bean
	public ConnectionFactory connectionFactory() {
		return new CachingConnectionFactory();
	}

	@Bean
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
		rabbitTemplate.setDefaultReceiveQueue("fileupload");
		return rabbitTemplate;
	}

	@Bean
	public ItemReader<FileDetails> fileItemReader() {
		return new AmqpItemReader<>(this.rabbitTemplate());
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
	public JdbcBatchItemWriter<FileDetails> writer(DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<FileDetails>()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
				.sql("INSERT INTO fileconsume (id, fileName, fileType, fileSize, uploadedBy, uploadTimeStamp) VALUES (:id, :fileName,:fileType,:fileSize,:uploadedBy,:timeStamp)")
				.dataSource(dataSource).build();
	}

	@Bean
	public CompositeItemWriter<FileDetails> compositeWriter(JdbcBatchItemWriter<FileDetails> writer) {
		CompositeItemWriter<FileDetails> compositeItemWriter = new CompositeItemWriter<>();
		compositeItemWriter.setDelegates(Arrays.asList(itemWriter(), fileWriter(), writer));
		return compositeItemWriter;
	}

	@Bean
	public AmqpAdmin amqpAdmin() {
		return new RabbitAdmin(connectionFactory());
	}

	@Bean
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory());
		return factory;
	}

	@Bean
	public FileItemProcessor processor() {
	  return new FileItemProcessor();
	}

	@Bean
	public Step step1(CompositeItemWriter<FileDetails> compositeItemWriter) {
		return stepBuilderFactory.get("step1").<FileDetails, FileDetails>chunk(50).reader(fileItemReader()).processor(processor())
				.writer(compositeItemWriter).build();
	}

	@Bean
	public Job job(Step step1) {
		return jobBuilderFactory.get("job").start(step1).build();
	}
}
