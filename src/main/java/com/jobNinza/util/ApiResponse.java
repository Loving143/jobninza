package com.jobNinza.util;

public class ApiResponse<T> {
	private T data;
	private String status;
	
	public ApiResponse() {
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public ApiResponse(T data, String status) {
		super();
		this.data = data;
		this.status = status;
	}
	

}
