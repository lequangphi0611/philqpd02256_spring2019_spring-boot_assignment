package com.quangphi;

import com.quangphi.filter.AuthenticateFilter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public FilterRegistrationBean<AuthenticateFilter> authenticateFilter() {
		FilterRegistrationBean<AuthenticateFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new AuthenticateFilter());
		// registrationBean.addUrlPatterns("/index.html");
		// registrationBean.addUrlPatterns("/department/*");
		// registrationBean.addUrlPatterns("/staffs/*");
		registrationBean.addUrlPatterns("/account/*");
		// registrationBean.addUrlPatterns("/records/*");
		return registrationBean;
	}

}
