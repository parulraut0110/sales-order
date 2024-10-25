package com.parul.sales_order.Model;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import com.parul.sales_order.dto.OrderDTO;

public class ModelAndViewOrder {
 
	public ModelAndView ModelAndView(OrderDTO dto, Model model) {
		ModelAndView mav = new ModelAndView("userView");
		mav.addObject("Order", dto);
        return mav;
	}
}
