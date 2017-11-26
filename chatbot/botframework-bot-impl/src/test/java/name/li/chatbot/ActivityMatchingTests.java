package name.li.chatbot;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.ConversationsApi;
import io.swagger.client.model.Activity;
import io.swagger.client.model.ChannelAccount;
import io.swagger.client.model.ConversationAccount;
import name.li.chatbot.directline.dsl.ActivityMatcher;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivityMatchingTests {

	@Autowired
	private ActivityMatcher matcher;

	@MockBean
	private ConversationsApi conversations;
	
	@Before
	public void init() {
		when(conversations.getApiClient()).thenReturn(mock(ApiClient.class));
	}

	@Test
	public void shouldReplyIfActivityIsMessageAndSentByOtherUser() throws ApiException {
		matcher.apply(new Activity()
				.conversation(conversation())
				.from(makeAccount("1"))
				.recipient(makeAccount("2"))
				.type("message"));
		expectReply();
	}

	@Test
	public void shouldNotReplyForNonMessageActivities() throws ApiException {
		matcher.apply(new Activity()
				.conversation(conversation())
				.from(makeAccount("1"))
				.recipient(makeAccount("2"))
				.type("conversationUpdate"));
		expectNoReply();
	}

	@Test
	public void shouldNotReplyIfSenderIsMe() throws ApiException {
		matcher.apply(new Activity()
				.conversation(conversation())
				.from(makeAccount("1"))
				.recipient(makeAccount("1"))
				.type("message"));
		expectNoReply();
	}

	void expectReply() throws ApiException {
		verify(conversations, times(1)).conversationsReplyToActivity(Mockito.anyString(), Mockito.anyString(), Mockito.any(Activity.class));
	}

	void expectNoReply() throws ApiException {
		verify(conversations, times(0)).conversationsReplyToActivity(Mockito.anyString(), Mockito.anyString(), Mockito.any(Activity.class));
	}

	public static ChannelAccount makeAccount(String id) {
		return new ChannelAccount()
				.id(id);
	}
	
	public static ConversationAccount conversation() {
		return new ConversationAccount().id("foo");
	}

}
