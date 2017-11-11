package name.li.chatbot.directline;

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

	public void reply(Activity in, String text) throws ApiException {
		ApiClient client = conversationsApi.getApiClient();
		client.setBasePath(in.getServiceUrl());
		conversationsApi.conversationsPostActivity(in.getConversation().getId(), createReply(in, text));
	}

	private Activity createReply(Activity in, String text) {
		return new Activity()
				.type("message")
				.channelId(in.getChannelId())
				.text(in.getText())
				.from(in.getRecipient())
				.recipient(in.getFrom())
				.replyToId(in.getId())
				.conversation(in.getConversation());
	}
}
