package oi.github.brunotorres_be.libraryapi.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DataBaseConfiguration {

    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;
    @Value("${spring.datasource.driver-class-name}")
    String driver;

// Esse data source não é muito utilizado em produção pois ele acaba tenho limitação de numero de conexão simultaneas,
// Podendo quebrar se tiver muito usuarios
//    @Bean
//    public DataSource dataSource(){
//        DriverManagerDataSource ds = new DriverManagerDataSource();
//        ds.setUrl(url);
//        ds.setUrl(username);
//        ds.setPassword(password);
//        ds.setDriverClassName(driver);
//        return ds;
//    }

    @Bean
    public DataSource hikariDataSource(){
        HikariConfig config = new HikariConfig();
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driver);
        config.setJdbcUrl(url);

        config.setMaximumPoolSize(10); // Maximo de Conexões liberadas
        config.setMinimumIdle(1); // tamanho inicial do pool
        config.setPoolName("Library-db-pool"); //nome do pool Opcional
        config.setMaxLifetime(600000); //propriedade em Milissegundos (10 minutos nesse caso)
        config.setConnectionTimeout(100000); //o tempo que a aplicação vai ter para tentar a conexao
        config.setConnectionTestQuery("select 1"); //verificar se o banco esta conectado

        return new HikariDataSource(config);
    }

}
