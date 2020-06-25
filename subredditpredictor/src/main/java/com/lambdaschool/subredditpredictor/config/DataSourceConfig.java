package com.lambdaschool.subredditpredictor.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
	private static boolean stop = false;

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private Environment env;

	private static void checkEnvironmentVariable(String envvar) {
		if (System.getenv(envvar) == null) {
			stop = true;
		}
	}

	@Bean(name = "dsCustom")
	public DataSource dataSource() {
		String dbValue = env.getProperty("local.run.db");

		if (dbValue.equalsIgnoreCase("POSTGRESQL")) {
			checkEnvironmentVariable("DATABASE_URL");

			if (stop) {
				int exitCode = SpringApplication.exit(appContext, (ExitCodeGenerator) () -> 1);
				System.exit(exitCode);
			}

			String dbUrl = env.getProperty("DATABASE_URL");

			HikariConfig config = new HikariConfig();
			config.setJdbcUrl(dbUrl);
			return new HikariDataSource(config);
		} else {
			String myUrlString = "jdbc:h2:mem:testdb";
			String myDriverClass = "org.h2.Driver";
			String myDBUser = "sa";
			String myDBPassword = "";

			return DataSourceBuilder.create()
				.username(myDBUser)
				.password(myDBPassword)
				.url(myUrlString)
				.driverClassName(myDriverClass)
				.build();
		}
	}

	@Bean(name = "jdbcCustom")

	@Autowired
	public JdbcTemplate jdbcTemplate(@Qualifier("dsCustom") DataSource dsCustom) {
		return new JdbcTemplate(dsCustom);
	}
}