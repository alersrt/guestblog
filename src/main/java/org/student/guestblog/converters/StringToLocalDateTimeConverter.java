package org.student.guestblog.converters;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;

public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {
	@Override
	public LocalDateTime convert(String s) {
		return LocalDateTime.parse(s);
	}
}
