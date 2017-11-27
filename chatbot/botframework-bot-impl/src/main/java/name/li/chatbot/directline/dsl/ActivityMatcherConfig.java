package name.li.chatbot.directline.dsl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.ImmutableList;

import name.li.chatbot.directline.ReplyService;

@Configuration
public class ActivityMatcherConfig {

	@Bean
	public ActivityMatcher activityMatcher(ReplyService replyService) {
		return new ActivityMatcher(
				new ImmutableList.Builder<ActivityMatcher.Rule>()
				.add(new ActivityMatcher.Rule(
						ActivityMatcher.Predicates.isMessage().and(ActivityMatcher.Predicates.isOwnMessage().negate()),
						a -> replyService.reply(a, a.getText()))
						)
				.build());
	}

	@Bean
	public WeatherService weatherService() {
		return new WeatherService();
	}
}
