package name.li.chatbot.directline;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.ConversationsApi;
import io.swagger.client.model.Activity;

@Component
public class ReplyService {

	private ConversationsApi conversationsApi;

	public ReplyService(ConversationsApi conversationsApi) {
		this.conversationsApi = conversationsApi;
	}

	public void reply(Activity in, String text) {
		ApiClient client = conversationsApi.getApiClient();
		client.setBasePath(in.getServiceUrl());
		try {
			conversationsApi.conversationsReplyToActivity(in.getConversation().getId(), in.getId(), createReply(in, text));
		} catch (ApiException e) {
			throw new RuntimeException(e);
		}
	}

	private Activity createReply(Activity in, String text) {
		return new Activity()
				.timestamp(DateTime.now())
				.type("message")
				.channelId(in.getChannelId())
				.text(text)
				.from(in.getRecipient())
				.recipient(in.getFrom())
				.replyToId(in.getId())
				.conversation(in.getConversation());
	}
}
