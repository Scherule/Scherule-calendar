package com.scherule.calendaring

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DriverManagerDataSource
import javax.sql.DataSource


@Configuration
class IntegrationTestContext {

    /**
     * Note that this makes sense only for simple usage of PostgreSQL database.
     * If the needs become more complex real database has to be started just like for functional tests.
     */
    @Bean
    fun dataSource(): DataSource {
        val dataSource = DriverManagerDataSource()
        dataSource.setDriverClassName("org.h2.Driver")
        dataSource.url = "jdbc:h2:mem:db;MODE=PostgreSQL"
        dataSource.username = "sa"
        dataSource.password = ""
        return dataSource
    }

}