package com.mbiger.pc.web.taglibs;

import com.mbiger.pc.web.taglibs.code.CodeDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DialectConfig {

    @Bean
    public CodeDialect registerCodeDialect(){
        return new CodeDialect();
    }

}