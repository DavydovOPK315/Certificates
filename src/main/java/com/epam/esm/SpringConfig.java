package com.epam.esm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.epam.esm")
@PropertySource("classpath:application.properties")
@EnableWebMvc
public class SpringConfig {

    private final ApplicationContext applicationContext;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.url}")
    private String URL;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Autowired
    private DataSource dataSource;

    @Autowired
    public SpringConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
////        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
////        dataSource.setUrl("jdbc:mysql://localhost:3306/certificates?serverTimezone=EET&useSSL=false");
////        dataSource.setUsername("root");
////        dataSource.setPassword("");
//
//        dataSource.setDriverClassName(driverClassName);
//        dataSource.setUrl(URL);
//        dataSource.setUsername(username);
//        dataSource.setPassword(password);
//
//
//        return dataSource;
//    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
//        return new JdbcTemplate(dataSource());

//        System.out.println("data source \n"+ dataSource);
        return new JdbcTemplate(dataSource);
    }
}
