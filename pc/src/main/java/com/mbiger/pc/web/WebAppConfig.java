package com.mbiger.pc.web;

import com.mbiger.pc.web.interceptor.BasicInterceptor;
import com.mbiger.pc.web.interceptor.SecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfig implements WebMvcConfigurer {
	@Autowired
	BasicInterceptor basicInterceptor;

	@Autowired
	SecurityInterceptor securityInterceptor;
	/**
	 * 配置拦截器，登录拦截
	 */
	public void addInterceptors(InterceptorRegistry registry) {
		//注册基本拦截器
		registry.addInterceptor(basicInterceptor)
				.addPathPatterns("/**");
		//注册权限拦截器
		registry.addInterceptor(securityInterceptor)
				.addPathPatterns("/account/**",
						"/mbiger/service/online/*/"
						).excludePathPatterns("/mbiger/service/online/weatherQuery/",
				"/mbiger/service/online/mobileNumberPlaceQuery/",
				"/mbiger/service/online/ipPlaceQuery/",
				"/mbiger/service/online/drivingQuestionsQuery/");
	}
	/**
	 * 配置静态资源
	 */
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//配置不拦截静态资源
        registry.addResourceHandler("/static/**","/templates/**").
				addResourceLocations("classpath:/static/","classpath:/templates/");
	}


}