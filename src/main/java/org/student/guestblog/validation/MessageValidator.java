package org.student.guestblog.validation;

import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.student.guestblog.model.Message;

@Component
public class MessageValidator implements Validator {
	@Override
	public boolean supports(Class<?> aClass) {
		return Message.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		Message message = (Message) o;

//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "required");
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "body", "required");
		ValidationUtils.rejectIfEmpty(errors, "image", "required");

		if (message.getImage().length > 0) {
			String mimeTypeOfImage = new Tika().detect(message.getImage());

			if(!(mimeTypeOfImage.toLowerCase().equals("image/jpg")
					|| mimeTypeOfImage.toLowerCase().equals("image/jpeg")
					|| mimeTypeOfImage.toLowerCase().equals("image/png"))) {
				errors.rejectValue("image", "format.supported");
			}
		}
	}
}
