package com.quangphi.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component("convertListSupport")
public class ConvertListSupport <T, S> {
	
	public Iterable<T> converting(Iterable<S> s, Parsers<T, S> parsers) {
		List<T> result = new ArrayList<>();
		s.forEach((items) -> result.add(parsers.parse(items)));
		return result;
	}
	
}
