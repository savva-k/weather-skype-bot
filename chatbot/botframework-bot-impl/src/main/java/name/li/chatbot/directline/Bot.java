package name.li.chatbot.directline;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.client.ApiException;
import io.swagger.client.model.Activity;

@RestController
public class Bot {
	
	private ReplyService replyService;

	public Bot(ReplyService replyService) {
		this.replyService = replyService;
	}

	@RequestMapping("/")
	public void echo(@RequestBody Activity in) throws ApiException {
		if (!in.getFrom().getName().equals("Bot")) {
			replyService.reply(in, in.getText());
		}
	}

}
