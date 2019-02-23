package com.quangphi.model;

public enum Gender {
	
	MALE,FEMALE;
	
	
	public static Gender parseGender(String str) {
		for (Gender gd : Gender.values()) {
			if(gd.toString().equals(str)) {
				return gd;
			}
		}
		throw new Error(str + " Can not parse to " + Gender.class.getName());
	}
}
