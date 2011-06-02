package com.codechronicle.dto;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

public class BaseDTO {
	protected void copyProperties(Object src, Object dest) {
		try {
			BeanUtils.copyProperties(dest, src);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
	@Override
	public String toString() {
		try {
			Map<String, String> desc = BeanUtils.describe(this);
			return desc.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
