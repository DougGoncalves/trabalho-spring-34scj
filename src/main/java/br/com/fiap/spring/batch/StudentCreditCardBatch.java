package br.com.fiap.spring.batch;

import br.com.fiap.spring.entity.StudentCreditCard;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.persistence.EntityManagerFactory;

@Configuration
public class StudentCreditCardBatch {

    @Bean("studentCreditCardJob")
    public Job job(JobBuilderFactory jobBuilderFactory, Step step) {
        return jobBuilderFactory.get("STUDENT_JOB")
                .start(step)
                .build();
    }

    @Bean
    public Step step(StepBuilderFactory stepBuilderFactory,
                     ItemReader<StudentCreditCard> itemReader,
                     ItemWriter<StudentCreditCard> itemWriter,
                     ItemProcessor<StudentCreditCard, StudentCreditCard> processor) {
        return stepBuilderFactory.get("STEP")
                .<StudentCreditCard, StudentCreditCard>chunk(2)
                .reader(itemReader)
                .processor(processor)
                .writer(itemWriter)
                .listener(listener())
                .allowStartIfComplete(false)
                .build();
    }

    @Bean
    public FlatFileItemReader<StudentCreditCard> itemReader(@Value("${file.input}") Resource resource){
        return new FlatFileItemReaderBuilder<StudentCreditCard>()
                .name("FILE_ITEM_READER")
                .targetType(StudentCreditCard.class)
                .delimited().delimiter(";").names("NAME", "REGISTRATION_CODE", "COURSE", "CARD_NUMBER",
                        "EXPIRATION_DATE", "VERIFICATION_CODE")
                .resource(resource)
                .linesToSkip(1)
                .build();
    }

    @Bean
    public ItemProcessor<StudentCreditCard, StudentCreditCard> processor() {
        return student -> {
            student.setName(student.getName().toUpperCase());
            student.setRegistration(student.getRegistration());
            student.setCourse(student.getCourse());
            student.setCardNumber(student.getCardNumber());
            student.setVerificationCode(student.getVerificationCode());
            student.setExpirationDate(student.getExpirationDate());
            return student;
        };
    }

    @Bean
    public ItemWriter<StudentCreditCard> databaseWriter(EntityManagerFactory emf) {
        final JpaItemWriter<StudentCreditCard> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(emf);
        return jpaItemWriter;
    }

    @Bean
    public ItemCountListener listener() {
        return new ItemCountListener();
    }
}
