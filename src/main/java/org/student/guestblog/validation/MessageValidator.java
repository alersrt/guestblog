package org.student.guestblog.validation;

import org.apache.commons.codec.binary.Base64;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.student.guestblog.model.Message;
import org.student.guestblog.service.MessageService;

@Component
public class MessageValidator implements Validator {
	private static final Logger logger = LoggerFactory.getLogger(MessageValidator.class);

	@Autowired
	private MessageService messageService;

	@Override
	public boolean supports(Class<?> aClass) {
		return Message.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		Message message = (Message) o;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "body", "required");
		ValidationUtils.rejectIfEmpty(errors, "image", "required");

		if (message.getImage() != null) {
			String mimeTypeOfImage = new Tika().detect(message.getImage());
			logger.info("The file type mime: " + mimeTypeOfImage);

			if(!(mimeTypeOfImage.toLowerCase().equals("image/jpg")
					|| mimeTypeOfImage.toLowerCase().equals("image/jpeg")
					|| mimeTypeOfImage.toLowerCase().equals("image/png"))) {
				errors.rejectValue("image", "format.supported");
			}
		}
	}
}
