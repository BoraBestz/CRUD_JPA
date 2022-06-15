package com.esign.service.configuration;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableCaching
@SpringBootApplication
public class ConfigurationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigurationServiceApplication.class, args);
	}

	public static void restart() {
		/*///Restart By Context
		ApplicationArguments args = context.getBean(ApplicationArguments.class);

		Thread thread = new Thread(() -> {
			context.close();
			context = SpringApplication.run(CMSServiceApplication.class, args.getSourceArgs());
		});

		thread.setDaemon(false);
		thread.start();*/

		///Restart By Linux
		try{
			System.out.println("RESTART APP");
			Process proc = Runtime.getRuntime().exec(System.getProperty("user.dir")+"/restartApp.sh");
			proc.waitFor();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
		return modelMapper;
	}
}
