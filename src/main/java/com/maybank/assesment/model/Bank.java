package com.maybank.assesment.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.*;

@Entity
@Table(name = "bank")
@Data
public class Bank {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id;

	@Column(name = "bank_code")
	public String bankCode;

	@Column(name = "bank_name")
	public String bankName;

	@Column(name = "branch_name")
	public String branchName;

	@Column(name = "branch_code")
	public String branchCode;

	@Column(name = "contact_number")
	public String contactNumber;

	@Column(name = "bank_manager_name")
	public String bankManagerName;

	@Column(name = "address")
	public String address;

	@Column(name = "email")
	public String email;

	@Column(name = "city")
	public String city;

	@Column(name = "state")
	public String state;

	@Column(name = "post_code")
	public String postCode;

	@Column(name = "status")
	public String status;

	@Column(name = "created_by")
	public String createdby;

	@Column(name = "created_date")
	public String createdDate;

}
