package com.luckybag.luckybagbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.TimeZone;

@SpringBootApplication
@EnableJpaAuditing
public class LuckyBagBackEndApplication {

    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }

    public static LocalDateTime nowFromZone() {
        return ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime();
    }

    public static void main(String[] args) {
        SpringApplication.run(LuckyBagBackEndApplication.class, args);
    }

}
