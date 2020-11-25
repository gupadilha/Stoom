package com.stoom.stoomer.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Configuration
@Component("configService")
@PropertySource("classpath:application.properties")
public class ConfigService {
	
	@Value("${google-api.key}")
	private String googleKey;

	public String getGoogleKey() {
		return googleKey;
	}

}
