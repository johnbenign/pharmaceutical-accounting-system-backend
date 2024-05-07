package com.johnbenign.dto.diagnose;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

@Component
public class DiagnoseResponse
{
	private Boolean result = false;  
    private String errorCode;
    private String errorMsg;   
    private String statusCode;
    private Object response;

    private java.util.List<String> errors;
    private String prescriptions;

	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

	public java.util.List<String> getErrors() {
		return errors;
	}

	public void setErrors(java.util.List<String> errors) {
		this.errors = errors;
	}

	public String getPrescriptions() {
		return prescriptions;
	}

	public void setPrescriptions(String prescriptions) {
		this.prescriptions = prescriptions;
	}

	
}
