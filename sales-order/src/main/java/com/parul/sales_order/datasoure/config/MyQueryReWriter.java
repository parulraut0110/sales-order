package com.parul.sales_order.datasoure.config;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.QueryRewriter;
import org.springframework.stereotype.Component;

@Component
public class MyQueryReWriter implements QueryRewriter {

	@Override
	public String rewrite(String query, Sort sort) {
		return null;
	}

}
