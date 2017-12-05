package name.li.chatbot.directline.dsl;

import com.imsavva.weatherclient.WeatherService;
import com.imsavva.weatherclient.WeatherServiceException;
import com.imsavva.weatherclient.beans.DailyForecast;
import com.imsavva.weatherclient.beans.WeeklyForecast;
import name.li.chatbot.directline.ChatbotConstants;
import name.li.chatbot.directline.MessageHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.ImmutableList;

import name.li.chatbot.directline.ReplyService;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ActivityMatcherConfig {

	@Bean
	public ActivityMatcher activityMatcher(ReplyService replyService, WeatherService weatherService,
										   MessageHandler messageHandler, String botName) {

		// TODO Make this code readable.

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
				.add(new ActivityMatcher.Rule(
						ActivityMatcher.Predicates.isMessage().and(ActivityMatcher.Predicates.isOwnMessage().negate())
						.and(ActivityMatcher.Predicates.isTodayWeatherRequest()),
						a -> {
							String incomingMessage = messageHandler.removeSubstring(a.getText(), botName);
							String location = messageHandler.extractLocation(incomingMessage);
							String message;

							try {
								DailyForecast forecast = weatherService.getDailyForecast(location);
								message = messageHandler.createDailyForecastMessage(location, forecast);
							} catch (WeatherServiceException e) {
								message = ChatbotConstants.DEFAULT_WEATHER_FORECAST;
							}

							replyService.reply(a, message);
						}
				))
				.add(new ActivityMatcher.Rule(
						ActivityMatcher.Predicates.isMessage().and(ActivityMatcher.Predicates.isOwnMessage().negate()
						.and(ActivityMatcher.Predicates.isWeeklyWeatherRequest())),
						a -> {
							String incomingMessage = messageHandler.removeSubstring(a.getText(), botName);
							String location = messageHandler.extractLocation(incomingMessage);
							String message;

							try {
								WeeklyForecast forecast = weatherService.getWeeklyForecast(location);
								message = messageHandler.createWeeklyForecastMessage(location, forecast);
							} catch (WeatherServiceException e) {
								message = ChatbotConstants.DEFAULT_WEATHER_FORECAST;
							}

							replyService.reply(a, message);
						}
				))
				.build());
	}

	@Bean
	public WeatherService weatherService(RestTemplate restTemplate) {
		return new WeatherService(restTemplate);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public MessageHandler messageHandler() {
		return new MessageHandler();
	}

	@Bean
	public String botName() {
		return System.getenv().get("BOT_NAME");
	}

}
