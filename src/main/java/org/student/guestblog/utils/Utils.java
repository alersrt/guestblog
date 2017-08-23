package org.student.guestblog.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Utils {
	public static Boolean isContainsObjectInList (List list, Object object) {
		return list.contains(object);
	}

	public static String byteArrayToString (byte[] bytes) {
		return new String(bytes);
	}
}
