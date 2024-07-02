package com.parul.sales_order.datasoure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.Ordered;

import lombok.Value;

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
public class ClassConfigTest {

	//@Value("${C.cx}")
	public int annotatedField;
	
	@Bean(name = "A")
	@DependsOn("B")
	public A getA() {
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
	
}
