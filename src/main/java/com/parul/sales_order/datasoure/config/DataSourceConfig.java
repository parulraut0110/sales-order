package com.parul.sales_order.datasoure.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.boot.jdbc.DataSourceBuilder;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class DataSourceBean {

	@Bean(name = "datasource")
	public DataSource dataSource() {
		DataSource dataSource = null;
		try {
			dataSource = DataSourceBuilder.create()
					.url("jdbc:mysql://localhost:3306/TestApplication")
					.username("root")
					.password("Raut#0110")
					.driverClassName("com.mysql.cj.jdbc.Driver")
					.build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataSource;
	}
}



@Configuration
public class DataSourceConfig {

	@Bean
	public DataSourceInitializer dataSourceInitializer(@Qualifier("datasource") DataSource dataSource) {
		ResourceDatabasePopulator dbPopulator = new ResourceDatabasePopulator();
		//dbPopulator.addScript(new ClassPathResource("stored_procedures.sql"));
		//dbPopulator.addScript(new ClassPathResource("InitData2.sql"));
		//dbPopulator.addScript(new ClassPathResource("InitData1.sql"));


		DataSourceInitializer dsInitializer = new DataSourceInitializer();
		dsInitializer.setDatabasePopulator(dbPopulator);
		dsInitializer.setDataSource(dataSource);
		return dsInitializer;
	}
}


