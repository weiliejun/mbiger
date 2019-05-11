package com.mbiger.cli;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan("com.mbiger")

public class TestCli implements CommandLineRunner {

    public void run(String... strings) throws Exception {

    }
    public static void main(String[] args) {
        new SpringApplicationBuilder(TestCli.class).web(WebApplicationType.NONE).run();
    }

}
