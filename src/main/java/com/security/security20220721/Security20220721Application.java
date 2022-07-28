package com.security.security20220721;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@Slf4j
@EnableAsync
@EnableSwagger2
@EnableTransactionManagement(proxyTargetClass = true)
public class Security20220721Application {

	public static void main(String[] args) {
		SpringApplication.run(Security20220721Application.class, args);
	}

}
