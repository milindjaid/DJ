package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Order {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;
	@Column
	private String totalOrderAmount;
	@Column
	private String orderAmount;
	@Column
	private String taxAmount;
	@Column
	private String customerId;
	
	public Order() {
		super();
	}
	
	public Order(Long orderId, String totalOrderAmount, String orderAmount, String taxAmount, String customer) {
		super();
		this.orderId = orderId;
		this.totalOrderAmount = totalOrderAmount;
		this.orderAmount = orderAmount;
		this.taxAmount = taxAmount;
		this.customerId = customer;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getTotalOrderAmount() {
		return totalOrderAmount;
	}
	public void setTotalOrderAmount(String totalOrderAmount) {
		this.totalOrderAmount = totalOrderAmount;
	}
	public String getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}
	public String getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(String taxAmount) {
		this.taxAmount = taxAmount;
	}
	public String getCustomer() {
		return customerId;
	}
	public void setCustomer(String customer) {
		this.customerId = customer;
	}
	
	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", totalOrderAmount=" + totalOrderAmount + ", orderAmount=" + orderAmount
				+ ", taxAmount=" + taxAmount + ", customer=" + customerId + "]";
	}
	

}
