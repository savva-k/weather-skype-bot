package name.li.chatbot.directline.auth;

import java.util.Map;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;

import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.filter.LoggingFilter;
import org.joda.time.DateTime;
import org.joda.time.Seconds;

public class AuthTokenService implements AuthTokenProvider {

	public static class Token {
		private String value;
		private DateTime expiresOn;

		public Token(String value, DateTime expiresOn) {
			this.value = value;
			this.expiresOn = expiresOn;
		}

		public DateTime getExpiresOn() {
			return expiresOn;
		}

		public String getValue() {
			return value;
		}
	}

	public static interface AppIdAppPasswordProvider {
		String getAppId();

		String getAppPassword();
	}

	private JerseyClient client;
	private AppIdAppPasswordProvider authDataProvider;

	private volatile Token token = new Token("", DateTime.now().minus(1));

	public AuthTokenService(JerseyClient client, AppIdAppPasswordProvider authDataProvider) {
		this.client = client;
		this.authDataProvider = authDataProvider;
	}

	@Override
	public String get() {
		if (token.getExpiresOn().isBefore(DateTime.now())) {
			refreshToken();
		}
		return token.getValue();
	}

	private MultivaluedMap<String, String> getTokenRequestData() {
		MultivaluedMap<String, String> data = new MultivaluedHashMap<>();
		data.add("grant_type", "client_credentials");
		data.add("client_id", authDataProvider.getAppId());
		data.add("client_secret", authDataProvider.getAppPassword());
		data.add("scope", "https://api.botframework.com/.default");
		return data;
	}

	public void refreshToken() {
		client.register(new LoggingFilter());
		Response resp = client.target("https://login.microsoftonline.com/botframework.com/oauth2/v2.0/token")
				.request()
				.header("Host", "login.microsoftonline.com")
				.post(Entity.form(getTokenRequestData()));
		if (resp.getStatusInfo().getFamily() == Family.SUCCESSFUL) {
			Map<String, Object> result = resp.readEntity(Map.class);
			String tokenValue = (String) result.get("access_token");
			Seconds expiresIn = Seconds.seconds((Integer) result.get("expires_in"));
			token = new Token(tokenValue, DateTime.now().plus(expiresIn));
		} else {
			throw new RuntimeException();
		}

	}
}
