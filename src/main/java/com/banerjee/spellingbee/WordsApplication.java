package com.banerjee.spellingbee;

import com.banerjee.spellingbee.dto.WordDTO;
import com.banerjee.spellingbee.service.WordWriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@EnableJpaRepositories
@ComponentScan("com.banerjee.*")
public class WordsApplication {

    @Autowired
    WordWriterService wordWriterService;

    public static void main(String[] args) {
        SpringApplication.run(WordsApplication.class, args);
    }
/*
    @Bean
    public String sometest1() throws Exception {
        wordWriterService.saveWords();
        return "blablabla1";
    }*/

}
