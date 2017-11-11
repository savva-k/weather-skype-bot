package name.li.chatbot.directline;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.client.api.ConversationsApi;

@Configuration
public class ApiConfig {
	@Bean
	public ConversationsApi conversationsApi() {
		return new ConversationsApi();
	}
}
