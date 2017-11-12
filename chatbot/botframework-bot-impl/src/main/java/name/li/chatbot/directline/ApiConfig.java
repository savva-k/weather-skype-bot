package name.li.chatbot.directline;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import io.swagger.client.api.ConversationsApi;
import name.li.chatbot.directline.auth.AuthTokenProvider;

@Configuration
public class ApiConfig {

	@Bean
	@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
	public ConversationsApi conversationsApi(AuthTokenProvider tokenProvider) {
		ConversationsApi ca = new ConversationsApi();
		ca.getApiClient().addDefaultHeader("Authorization", "Bearer " + tokenProvider.get());
		ca.getApiClient().setDebugging(true);
		return ca;
	}

}
