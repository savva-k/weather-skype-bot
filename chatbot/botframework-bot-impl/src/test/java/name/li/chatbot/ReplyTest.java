package name.li.chatbot;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import io.swagger.client.ApiClient;
import io.swagger.client.api.ConversationsApi;
import io.swagger.client.model.Activity;

public class ReplyTest extends AbstractMvcTest {
	@MockBean
	private ConversationsApi conversationsApi;

	@Test
	public void shouldSendReplyMessageToDirectLine() throws Exception {
		ApiClient apiClient = mock(ApiClient.class);
		when(conversationsApi.getApiClient()).thenReturn(apiClient);
		ArgumentCaptor<Activity> replyCaptor = ArgumentCaptor.forClass(Activity.class);
		String text = "123";

		getMockMvc().perform(post("/")
				.contentType("application/json; charset=utf-8")
				.content("{\"from\":{\"id\":\"j38nkcidj0f6\",\"name\":\"User\"},"
						+ "\"conversation\":{\"id\":\"h19fn3h7882b\"},"
						+ "\"recipient\":{\"id\":\"default-user\",\"name\":\"Bot\"},"
						+ "\"text\":\"" + text + "\","
						+ "\"replyToId\":\"aa0kl2ka73j\"}"));

		Mockito.verify(conversationsApi, times(1)).conversationsPostActivity(Mockito.anyString(),
				replyCaptor.capture());
		Activity submittedMessage = replyCaptor.getValue();
		assertEquals(text, submittedMessage.getText());

	}

}
