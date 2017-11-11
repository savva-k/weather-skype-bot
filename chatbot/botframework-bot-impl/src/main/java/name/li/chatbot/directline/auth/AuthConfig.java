package name.li.chatbot.directline.auth;

import java.util.Optional;

import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import name.li.chatbot.directline.auth.AuthTokenService.AppIdAppPasswordProvider;

@Configuration
public class AuthConfig {

	@Bean
	public AppIdAppPasswordProvider authDataProvider() {
		return new AppIdAppPasswordProvider() {
			
			@Override
			public String getAppId() {
				return Optional.of(System.getenv().get("MICROSOFT-APP-ID")).get();
			}
			
			@Override
			public String getAppPassword() {
				return Optional.of(System.getenv().get("MICROSOFT-APP-PASSWORD")).get();
			}
		};
	}
	
	@Bean
	public JerseyClient tokenClient() {
		return JerseyClientBuilder.createClient();
	}
	
	@Bean
	public AuthTokenProvider tokenProvider() {
		return new AuthTokenService(tokenClient(), authDataProvider());
	}
	
}
