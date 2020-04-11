package br.com.fiap.spring.batch;

import br.com.fiap.spring.Application;
import br.com.fiap.spring.batch.config.BatchTestConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.sql.ResultSet;

import static java.util.concurrent.TimeUnit.SECONDS;
import static junit.framework.TestCase.assertNotNull;
import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Application.class, BatchTestConfiguration.class})
@SpringBootTest
public class StudentCreditCardJobTest {

	@Autowired
	private JobLauncherTestUtils testUtils;

	@Autowired
	@Qualifier("studentCreditCardJob")
	private Job job;

	@Autowired
	private DataSource dataSource;

	 @Test
	 public void shouldProcessStudentCreditCardJob() throws Exception {
		JobExecution result = testUtils.getJobLauncher()
				.run(job, testUtils.getUniqueJobParameters());

		assertNotNull(result);
		assertEquals(BatchStatus.COMPLETED, result.getStatus());

		ResultSet resultSet = dataSource.getConnection()
				.prepareStatement("select count(*) from STUDENT_CREDIT_CARD;")
				.executeQuery();

		await().atMost(10, SECONDS)
				.until(() -> {
					resultSet.last();
					return resultSet.getInt(1) == 1;
				});

		assertEquals(1, resultSet.getInt(1) );
	 }
}
