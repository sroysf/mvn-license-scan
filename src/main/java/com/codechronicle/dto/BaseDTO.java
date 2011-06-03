package com.codechronicle.dto;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

public class BaseDTO {
	
	private boolean success = true;
	
	@Override
	public String toString() {
		try {
			Map<String, String> desc = BeanUtils.describe(this);
			return desc.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
