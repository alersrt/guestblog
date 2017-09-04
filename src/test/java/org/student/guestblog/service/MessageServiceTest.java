package org.student.guestblog.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.student.guestblog.model.Message;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/appconfig-root.xml"})
@WebAppConfiguration
@ActiveProfiles("dev")
public class MessageServiceTest extends AbstractServiceTest {

	private Message message = new Message("This is a test message", "It is a body of the test message", new byte[1]);

	@Autowired
	MessageService messageService;

	@Test
	@WithMockUser(username = "robinhood")
	public void addMessageWithMockUser() {
		message = messageService.addMessage(message);
		LOGGER.info(message.getOwnerName());
		Assert.assertNotNull("Message does't exist", messageService.findById(message.getId()));
	}

	@Test
	@WithAnonymousUser
	public void delMessageWithAnonymousUser() {

	}
}
