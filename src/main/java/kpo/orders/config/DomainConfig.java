package kpo.orders.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("kpo.orders.domain")
@EnableJpaRepositories("kpo.orders.repos")
@EnableTransactionManagement
public class DomainConfig {
}
