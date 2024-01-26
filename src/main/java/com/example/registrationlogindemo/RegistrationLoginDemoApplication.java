package com.example.registrationlogindemo;

import com.example.registrationlogindemo.storage.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.io.IOException;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class RegistrationLoginDemoApplication {

	public static void main(String[] args) {
		try{
			Process process = Runtime.getRuntime().exec("C:\\xampp\\mysql\\bin\\mysqld.exe");
		}catch (IOException e){
			e.printStackTrace();
		}
		SpringApplication.run(RegistrationLoginDemoApplication.class, args);
	}

}
