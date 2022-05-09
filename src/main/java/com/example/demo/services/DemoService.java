package com.example.demo.services;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.demo.dao.DemoDAO;
import com.example.demo.dao.OrderRepo;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Order;

@Service
public class DemoService {

	@Autowired
	DemoDAO demoDao;
	@Autowired
	OrderRepo orderRepo;
	
	@Transactional
	public Customer save(Customer customer) {
	return this.demoDao.save(customer);
	}
	
	@Transactional
	public Order saveOrder(Order order) {
		
		return orderRepo.save(order);
		
	}
	
	@Cacheable(
		      value = "squareCache", 
		      key = "#number", 
		      condition = "#number>10")
		    public BigDecimal square(Long number) {
		        BigDecimal square = BigDecimal.valueOf(number)
		          .multiply(BigDecimal.valueOf(number));
		        System.out.println("square of {} is {}" + number +" , "+square);
		        return square;
		    }
}
