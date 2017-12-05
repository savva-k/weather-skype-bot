package name.li.chatbot.directline.dsl;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.google.common.collect.ImmutableCollection;

import io.swagger.client.model.Activity;

public class ActivityMatcher {

	public static class Rule {
		private Predicate<Activity> predicate;
		private Consumer<Activity> handler;

		public Rule(Predicate<Activity> predicate, Consumer<Activity> handler) {
			super();
			this.predicate = predicate;
			this.handler = handler;
		}

		public boolean test(Activity activity) {
			return predicate.test(activity);
		}

		public void apply(Activity activity) {
			handler.accept(activity);
		}

	}

	public static class Predicates {

		public static Predicate<Activity> isMessage() {
			return activity -> Objects.equals("message", activity.getType());
		}

		public static Predicate<Activity> isOwnMessage() {
			return activity -> Objects.equals(activity.getFrom(), activity.getRecipient());
		}

		public static Predicate<Activity> isGreeting() {
			return activity -> activity.getText() != null
					&& activity.getText().toLowerCase().contains("hello")
					|| activity.getText().toLowerCase().contains("hi");
		}

		public static Predicate<Activity> isHelpRequest() {
			return activity -> activity.getText() != null
					&& activity.getText().toLowerCase().contains("help");
		}

		public static Predicate<Activity> isTodayWeatherRequest() {
			return activity -> activity.getText() != null
					&& activity.getText().toLowerCase().contains("weather today");
		}

		public static Predicate<Activity> isWeeklyWeatherRequest() {
			return activity -> activity.getText() != null
					&& activity.getText().toLowerCase().contains("weather week");
		}
	}
	
	private ImmutableCollection<Rule> rules;

	public ActivityMatcher(ImmutableCollection<Rule> rules) {
		this.rules = rules;
	}
	
	public void apply(Activity activity) {
		rules.stream()
			.filter(rule -> rule.test(activity))
			.forEach(rule -> rule.apply(activity));
	}

}
