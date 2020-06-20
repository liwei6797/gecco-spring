package org.wx.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.spring.SpringGeccoEngine;

@SpringBootApplication
@Configuration
@ComponentScan(basePackages = "org.wx.test,com.geccocrawler.gecco.spring")
public class App {

	@Bean
	public SpringGeccoEngine initGecco() {
		return new SpringGeccoEngine() {
			@Override
			public void init() {
				GeccoEngine.create()
				.pipelineFactory(springPipelineFactory)
				.classpath("org.wx.test")
				.start("http://www.akxs5.com/meizi/hot/")
				//.start("http://www.baidu.com")
				.interval(100)
				.thread(5)
				.loop(false)
				.run();
			}
		};
	}
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(App.class, args);
	}
	
}
