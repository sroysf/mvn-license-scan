package com.codechronicle.dto;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

public class BeanMapperUtil {

	public static <X,T> List<X> createDTOList(Class<X> clazz, List<T> mos) {
		
		if ((mos == null) || (mos.size() == 0)) {
			return null;
		}
		
		List<X> dtoList = new ArrayList<X>();
		
		try {
			Constructor<X> dtoConstructor = clazz.getConstructor(mos.get(0).getClass());
			System.out.println("DTO Constructor = " + dtoConstructor);

			for (T modelObject : mos) {
				X dto = dtoConstructor.newInstance(modelObject);
				dtoList.add(dto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dtoList;
	}
	
	public static void copyProperties(Object src, Object dest) {
		try {
			BeanUtils.copyProperties(dest, src);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
}
