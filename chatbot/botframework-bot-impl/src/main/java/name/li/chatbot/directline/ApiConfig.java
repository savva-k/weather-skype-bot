package name.li.chatbot.directline;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.client.api.ConversationsApi;
import name.li.chatbot.directline.auth.AuthTokenProvider;

@Configuration
public class ApiConfig {
	@Bean
	public ConversationsApi conversationsApi(AuthTokenProvider tokenProvider) {
		ConversationsApi ca = new ConversationsApi();
		ca.getApiClient().addDefaultHeader("Authorization", "Bearer " + tokenProvider.get());
		return ca;
	}
}
