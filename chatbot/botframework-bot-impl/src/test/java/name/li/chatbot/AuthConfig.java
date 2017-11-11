package name.li.chatbot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import io.swagger.client.ApiException;
import io.swagger.client.api.ConversationsApi;
import io.swagger.client.model.Activity;
import name.li.chatbot.directline.auth.AuthTokenProvider;

public class AuthConfig extends AbstractMvcTest {

	public static final String TEST_AUTH_TOKEN = "123123123";

	@Configuration
	@Import({ App.class })
	public static class AuthTestContext {
		@Bean
		@Primary
		public AuthTokenProvider testTokenProvider() {
			return () -> TEST_AUTH_TOKEN;
		}
	}

	@Autowired
	private ConversationsApi conversationsApi;

	@Test
	public void conversationsApiRequestsShouldContainAuthorizationHeader() throws ApiException {
		Client clientMock = mockHttpClient();
		conversationsApi.getApiClient().setHttpClient(clientMock);
		conversationsApi.conversationsPostActivity("asd", new Activity());
		Builder builder = clientMock.target("").request();

		ArgumentCaptor<String> headerNameCaptor = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<String> headerValueCaptor = ArgumentCaptor.forClass(String.class);

		verify(builder, atLeastOnce()).header(headerNameCaptor.capture(), headerValueCaptor.capture());
		List<String> headerNames = headerNameCaptor.getAllValues();
		int authorizationIndex = headerNames.indexOf("Authorization");
		
		assertNotEquals("Authorization header not found", authorizationIndex, -1);
		assertEquals("Bearer " + TEST_AUTH_TOKEN, headerValueCaptor.getAllValues().get(authorizationIndex));
	}

	public Client mockHttpClient() {
		Client client = mock(Client.class);

		WebTarget target = mock(WebTarget.class);
		when(client.target(Mockito.anyString())).thenReturn(target);

		Builder builder = Mockito.mock(Builder.class);
		when(target.request()).thenReturn(builder);

		when(builder.accept(Mockito.anyString())).thenReturn(builder);
		when(builder.header(Mockito.anyString(), Mockito.any())).thenReturn(builder);
		// when(builder.header(Mockito.anyString(), Mockito.anyString())).thenReturn(builder);

		Response resp = mockResponse();
		when(builder.post(Mockito.any(Entity.class))).thenReturn(resp);

		return client;
	}

	public Response mockResponse() {
		Response resp = mock(Response.class);

		when(resp.getStatusInfo()).thenReturn(Status.NO_CONTENT);
		MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
		headers.putSingle("Content-Type", "application/json");
		when(resp.getHeaders()).thenReturn(headers);
		return resp;
	}

}
