package com.podsproject.podsproject.wallet;

import java.io.IOException;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args)throws IOException{
		SpringApplication.run(Application.class, args);
	}
		
//		WalletController wc = new WalletController();
//		wc.reInitialize1();
		
//	@Bean
//	public CommandLineRunner CommandLineRunnerBean()
//	{
//		return (args) -> 
//		{
//			WalletController wc = new WalletController();
//			wc.reInitialize1();
//	    };
//	}	
}

