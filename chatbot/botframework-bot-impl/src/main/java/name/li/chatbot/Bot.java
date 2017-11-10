package name.li.chatbot;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.client.model.Activity;

@RestController
public class Bot {
	
	@RequestMapping("/")
	public Activity echo(@RequestBody Activity in) {
		return new Activity()
				.text(in.getText())
				.from(in.getRecipient())
				.recipient(in.getFrom())
				.conversation(in.getConversation());
	}

}
