package name.li.chatbot.directline.auth;

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
				return System.getenv().get("MICROSOFT_APP_ID");
			}
	
			@Override
			public String getAppPassword() {
				return System.getenv().get("MICROSOFT_APP_PASSWORD");
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
