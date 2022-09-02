package by.teachmeskills.eshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

import static by.teachmeskills.eshop.utils.EshopConstants.DATA_SOURCE_DRIVER_CLASS_NAME;
import static by.teachmeskills.eshop.utils.EshopConstants.DATA_SOURCE_PASSWORD;
import static by.teachmeskills.eshop.utils.EshopConstants.DATA_SOURCE_URL;
import static by.teachmeskills.eshop.utils.EshopConstants.DATA_SOURCE_USERNAME;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "by")
public class SpringBootEshopApplication {
    private final Environment env;

    public SpringBootEshopApplication(Environment env) {
        this.env = env;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootEshopApplication.class, args);
    }

    @Bean(name = "dataSource")
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(env.getProperty(DATA_SOURCE_DRIVER_CLASS_NAME));
        dataSource.setUrl(env.getProperty(DATA_SOURCE_URL));
        dataSource.setUsername(env.getProperty(DATA_SOURCE_USERNAME));
        dataSource.setPassword(env.getProperty(DATA_SOURCE_PASSWORD));
        return dataSource;
    }
}