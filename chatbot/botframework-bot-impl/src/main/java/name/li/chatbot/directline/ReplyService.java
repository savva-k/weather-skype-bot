package name.li.chatbot.directline;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.ConversationsApi;
import io.swagger.client.model.Activity;
import name.li.chatbot.directline.dirtyhack.HeroCard;
import io.swagger.client.model.CardImage;
import io.swagger.client.model.Attachment;

@Component
public class ReplyService {

	private ConversationsApi conversationsApi;

	public ReplyService(ConversationsApi conversationsApi) {
		this.conversationsApi = conversationsApi;
	}

	public void reply(Activity in, String text) {
		Activity out = createTextReply(in, text);
		sendResponse(in, out);
	}

	private void sendResponse(Activity in, Activity out) {
		ApiClient client = conversationsApi.getApiClient();
		client.setBasePath(in.getServiceUrl());
		try {
			conversationsApi.conversationsReplyToActivity(in.getConversation().getId(), in.getId(), out);
		} catch (ApiException e) {
			throw new RuntimeException(e);
		}
	}

	public void sendHeroCard(Activity in, String imgUrl, String title, String text) {
		HeroCard heroCard = createHeroCard(imgUrl, title, text);
		Activity out = createActivityWithAttachment(in, "application/vnd.microsoft.card.hero", heroCard);
		sendResponse(in, out);
	}

	private HeroCard createHeroCard(String imgUrl, String title, String text) {
		return new HeroCard()
				.addImagesItem(new CardImage().url(imgUrl))
				.title(title)
				.text(text);
	}

	private Activity createTextReply(Activity in, String text) {
		return createCommonActivity(in)
				.timestamp(DateTime.now())
				.channelId(in.getChannelId())
				.text(text);
	}

	private Activity createActivityWithAttachment(Activity in, String attachmentType,
												  io.swagger.client.model.Object content) {
		Attachment attachment = new Attachment()
				.contentType(attachmentType)
				.content(content);

		return createCommonActivity(in)
				.addAttachmentsItem(attachment);
	}

	private Activity createCommonActivity(Activity in) {
		return new Activity()
				.type("message")
				.from(in.getRecipient())
				.recipient(in.getFrom())
				.replyToId(in.getId())
				.conversation(in.getConversation());
	}
}
