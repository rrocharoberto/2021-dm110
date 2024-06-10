package br.inatel.dm110.beans.example;

import java.util.logging.Logger;

import jakarta.ejb.EJB;
import jakarta.ejb.Local;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import br.inatel.dm110.api.example.MessageTO;
import br.inatel.dm110.interfaces.example.HelloLocal;
import br.inatel.dm110.interfaces.example.HelloRemote;

@Stateless
public class HelloBean implements HelloLocal, HelloRemote {

	@Inject
	Logger log;
	
	@EJB
	private HelloQueueSender queueSender;
	
	@EJB
	private HelloTopicSender topicSender;

	public String status() {
		log.info("Status endpoint called.");
		return "Hello Session Bean Status ok.";
	}

	@Override
	public MessageTO sayHello(String name) {
		log.info("Chamou o Hello Bean: " + name);
		String msgStr = "Hello Session Bean greeting " + name + " !";
		//send the message to somewhere
		queueSender.sendTextMessage(msgStr);
		topicSender.sendTextMessage(msgStr);

		MessageTO msg = new MessageTO(name, "");
		msg.setMessage(msgStr);
		return msg;
	}
}
