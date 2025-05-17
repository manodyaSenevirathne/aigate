package com.aigate.aigate_core;

import com.aigate.aigate_core.mpTalk.Payload;
import com.aigate.aigate_core.mpTalk.Sender;
import com.aigate.aigate_core.mpTalk.TcpMockPluginServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class AigateCoreApplication {

	public static void main(String[] args) {

		SpringApplication.run(AigateCoreApplication.class, args);


//		Payload payload = new Payload();
//		payload.setBody("Sample input");
//
//		Payload response = Sender.sendPayload("127.0.0.1", 7070, payload);
//
//		System.out.println("Plugin replied: " + response.getBody());

	}

}
