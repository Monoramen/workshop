package com.workskop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WorkshopApplication  {
    private static final Logger log = LoggerFactory.getLogger(WorkshopApplication.class);



    public static void main(String[] args) {


        SpringApplication.run(WorkshopApplication.class, args);


    }


}
