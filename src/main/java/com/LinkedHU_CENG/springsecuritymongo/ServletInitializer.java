package com.LinkedHU_CENG.springsecuritymongo;

import com.LinkedHU_CENG.SpringsecuritymongoApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringsecuritymongoApplication.class);
	}

}
