package com.lottus.sfbservice.credentials.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class TransactionManagerConfig {

    @Bean(name = "chainedTransactionManager")
    public ChainedTransactionManager transactionManager(
        @Qualifier("inscripcionTransactionManager") PlatformTransactionManager inscripcionTransactionManager,
        @Qualifier("bannerTransactionManager") PlatformTransactionManager bannerTransactionManager) {
        return new ChainedTransactionManager(inscripcionTransactionManager, bannerTransactionManager);
    }

    /*@Bean(name = "chainedTransactionManager")
    public ChainedTransactionManager transactionManager(
            @Qualifier("bannerTransactionManager") PlatformTransactionManager bannerTransactionManager) {
        return new ChainedTransactionManager(bannerTransactionManager);
    }*/
}
