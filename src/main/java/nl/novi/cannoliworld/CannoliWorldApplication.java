package nl.novi.cannoliworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class CannoliWorldApplication {


    public static void main(String[] args) {
        SpringApplication.run(CannoliWorldApplication.class, Arrays.toString(args));
    }
}
