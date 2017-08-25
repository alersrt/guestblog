package org.student.guestblog.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Utils {

	private static final Logger logger = LoggerFactory.getLogger(Utils.class);

	public static Boolean isContainsObjectInList (List list, Object object) {
		return list.contains(object);
	}

	public static String binaryDataToBase64String(byte[] bytes) {
		return Base64.encodeBase64String(bytes);
	}

	public static String getMimeTypeFromBynary(byte[] bytes) {
		return new Tika().detect(bytes);
	}
}
