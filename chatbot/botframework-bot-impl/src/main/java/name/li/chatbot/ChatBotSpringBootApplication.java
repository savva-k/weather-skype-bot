package name.li.chatbot;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.datatype.joda.JodaModule;

@SpringBootApplication
public class ChatBotSpringBootApplication {

	@Bean
	public JodaModule jodaModule() {
		return new JodaModule();
	}
}
