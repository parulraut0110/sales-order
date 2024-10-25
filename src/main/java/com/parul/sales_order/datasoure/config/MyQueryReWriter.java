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

        /*
        StringBuilder orderByClause = new StringBuilder(baseQuery);
        
        if (sort.isSorted()) {
            orderByClause.append(" order by ");
            sort.forEach(order -> 
                orderByClause.append("a.")
                             .append(order.getProperty())
                             .append(" ")
                             .append(order.getDirection().name())
                             .append(", ")
            );
            orderByClause.setLength(orderByClause.length() - 2); // Remove trailing comma and space
            System.out.println("query " + query);
            System.out.println("orderByClause " + orderByClause);
        }
        */        
        //return orderByClause.toString();
        
        return baseQuery;
    }
}
