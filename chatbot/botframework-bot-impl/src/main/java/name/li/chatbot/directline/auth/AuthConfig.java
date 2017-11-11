package name.li.chatbot.directline.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthConfig {

	@Bean
	public AuthTokenProvider tokenProvider() {
		return () -> "not implemented";
	}
}
