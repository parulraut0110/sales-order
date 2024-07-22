package com.parul.sales_order.datasoure.config;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.QueryRewriter;
import org.springframework.stereotype.Component;

@Component
public class MyQueryReWriter implements QueryRewriter {

    @Override
    public String rewrite(String query, Sort sort) {
    	System.out.println("query1 " + query);
        String baseQuery = query.split("order by")[0].trim();

        return query;
    }
}
