package kr.submit.goodsorderfeature.core.jpa.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EntityScan("kr.submit.goodsorderfeature.api")
@EnableJpaRepositories("kr.submit.goodsorderfeature.api")
public class JpaConfig {
}