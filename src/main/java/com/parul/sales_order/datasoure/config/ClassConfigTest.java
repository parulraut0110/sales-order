package com.parul.sales_order.datasoure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.Ordered;




class A {
	public A() {
		System.out.println("creating object A");
	}
}

class B {
	public B() {
		System.out.println("creating object B");
	}
}

class C implements Ordered {
	
	private int cx = 8;
	
	public C() {
		System.out.println("creating object C");
		cx = D.dx;
		System.out.println("the value of cx is " + cx);
	}

	@Override
	public int getOrder() {
		return 2;                  //The return value implies the bean creation order 
	}
}

class D implements Ordered {
	
	
	public static int dx = 11;
	
	public D() {
		System.out.println("creating object D");
		dx = 5;
		System.out.println("the value of dx is " + dx);

	}

	
	@Override
	public int getOrder() {
		return 3;
	}
}

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "spring.datasource")             //injects multiple fields with properties matching the prefix from configuration file 
public class ClassConfigTest {

	@Value("${C.cx}")                         //injects configuration properties (application.properties, system environment, ...) into fields or constructor
	public int annotatedField;
	
	String username;
	String password;
	
	@Bean(name = "A")
	@DependsOn("B")
	public A getA() {
		System.out.println("value of cx : " + annotatedField);
		showCredentials();
		return new A();
	}

	@Bean(name = "B")
	public B getB() {
		return new B();
	}
	
	
	@Bean
	public C getC() {
		return new C();
	}
	
	@Bean
	public D getD() {
		return new D();
	}
	
	public void showCredentials() {
		System.out.println("UserName : " + username + "  Password : " + password);
	}
	
	public void setUsername(String username) {
	        this.username = username;
	    }
	 
	public void setPassword(String password) {
	        this.password = password;
	    }
	
}
