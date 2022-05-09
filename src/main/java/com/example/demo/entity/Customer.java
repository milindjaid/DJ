package com.example.demo.entity;

import javax.annotation.PostConstruct;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.factory.InitializingBean;

@Entity
@Table
public class Customer implements InitializingBean {

	static String shopName;

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	@Column
	String customerName;

	@Column
	String email;

	static {  //Call First
		shopName = "Vijay Sales";
		System.out.println("Static block1");
	}
	static {
		shopName = "Vijay Sales1";
		System.out.println("Static block2");
	}

	// Called 2nd
	{
		System.out.println("Intailize block 1");
	}
	{
		System.out.println("Intailize block 2");
	}

	//Called 3rd
	public Customer() {
		super();
		System.out.println("In Constructor");
	}

	public Customer(int id, String customerName, String email) {
		super();
		this.id = id;
		this.customerName = customerName;
		this.email = email;
	}

	// This is init method for customer bean...called 6 th
	public Customer abc() throws Exception {
		if(this.customerName==null) {
			//throw new Exception("Customer Name cannot be null");
			this.customerName="Milind";
		}
		System.out.println("Initialization Customer Bean with Init Method>>>"+this);
		return this;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", customerName=" + customerName + ", email=" + email + "]";
	}

	@Override // Called 5 th
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		this.email = "milindjaid93@gmail.com";
		System.out.println("In afterPropertiesSet==="+this);
	}
	@PostConstruct     // Called 4 th
	public void customerId() {
		System.out.println("In customer post construct method==="+this);
	}

	/**
	 * 1. Object creation
	 * 2.After dependency injection postcontuct,afterPropertiesSet,and init method getting execute
	 */
}
