package name.li.chatbot.directline;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.client.ApiException;
import io.swagger.client.model.Activity;
import name.li.chatbot.directline.dsl.ActivityMatcher;

import java.util.logging.Logger;

@RestController
public class Bot {

	private static final Logger logger = Logger.getLogger(Bot.class.getName());

	private ActivityMatcher activityMatcher;

	public Bot(ActivityMatcher activityMatcher) {
		this.activityMatcher = activityMatcher;
	}

	@RequestMapping("/")
	public void echo(@RequestBody Activity in) throws ApiException {
		logger.info("Incoming activity: " + in.toString());
		activityMatcher.apply(in);
	}

}
