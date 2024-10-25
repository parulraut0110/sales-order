package com.parul.sales_order.datasoure.config;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.QueryRewriter;
import org.springframework.stereotype.Component;


public class PagedQueryRewriter implements QueryRewriter {

    @Override
    public String rewrite(String query, Pageable pageable) {
        return query;
    }

	@Override
	public String rewrite(String query, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

    
}
