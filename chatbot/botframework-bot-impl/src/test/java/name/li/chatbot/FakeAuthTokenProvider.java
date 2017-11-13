package name.li.chatbot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import name.li.chatbot.directline.auth.AuthTokenProvider;

public class FakeAuthTokenProvider {
	
	public static final String TOKEN_VALUE = "Fake token value" ;

	@Configuration
	@Import({ ChatBotSpringBootApplication.class })
	public static class AuthTestContext {
		@Bean
		@Primary
		public AuthTokenProvider testTokenProvider() {
			return () -> TOKEN_VALUE;
		}
	}
}
