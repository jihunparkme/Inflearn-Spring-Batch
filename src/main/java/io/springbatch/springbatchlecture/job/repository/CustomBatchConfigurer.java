package io.springbatch.springbatchlecture.job.repository;

import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.boot.autoconfigure.batch.BasicBatchConfigurer;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class CustomBatchConfigurer extends BasicBatchConfigurer {

    private final DataSource dataSource;

    protected CustomBatchConfigurer(BatchProperties properties, DataSource dataSource, TransactionManagerCustomizers transactionManagerCustomizers) {
        super(properties, dataSource, transactionManagerCustomizers);
        this.dataSource = dataSource;
    }

    @Override
    protected JobRepository createJobRepository() throws Exception {

        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTransactionManager(getTransactionManager());
        factory.setIsolationLevelForCreate("ISOLATION_SERIALIZABLE"); // default. "ISOLATION_SERIALIZABLE"
        factory.setTablePrefix("BATCH_");  // default. "BATCH_" BATCH_JOB_EXECUTION -> SYSTEM_JOB_EXECUTION (실제 테이블명이 변경되는 것은 아님)
        factory.setMaxVarCharLength(1000); // varchar 최대 길이(기본값 2500)

        return factory.getObject(); // Proxy 객체 생성(트랜잭션 Advice 적용 등을 위해 AOP 기술 적용)

    }
}
