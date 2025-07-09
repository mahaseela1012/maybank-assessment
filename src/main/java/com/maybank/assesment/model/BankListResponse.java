package com.maybank.assesment.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BankListResponse {
	private List<BankInfo> data;

	public BankListResponse() {
	}

	public List<BankInfo> getData() {
		return data;
	}

	public void setData(List<BankInfo> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "BankListResponse{data=" + data + '}';
	}
}
