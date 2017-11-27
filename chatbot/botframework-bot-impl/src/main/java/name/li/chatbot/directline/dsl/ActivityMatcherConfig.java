package name.li.chatbot.directline.dsl;

import com.imsavva.weatherclient.WeatherService;
import name.li.chatbot.directline.ChatbotConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.ImmutableList;

import name.li.chatbot.directline.ReplyService;

@Configuration
public class ActivityMatcherConfig {

	@Bean
	public ActivityMatcher activityMatcher(ReplyService replyService) {

		// TODO add weather requests handling

		return new ActivityMatcher(
				new ImmutableList.Builder<ActivityMatcher.Rule>()
				.add(new ActivityMatcher.Rule(
						ActivityMatcher.Predicates.isMessage().and(ActivityMatcher.Predicates.isOwnMessage().negate()
						.and(ActivityMatcher.Predicates.isGreeting())),
						a -> replyService.reply(a, ChatbotConstants.GREETINGS_MESSAGE))
						)
				.add(new ActivityMatcher.Rule(
						ActivityMatcher.Predicates.isMessage().and(ActivityMatcher.Predicates.isOwnMessage().negate()
						.and(ActivityMatcher.Predicates.isHelpRequest())),
						a -> replyService.reply(a, ChatbotConstants.HELP_MESSAGE)
				))
				.build());
	}

	@Bean
	public WeatherService weatherService() {
		return new WeatherService();
	}

}
