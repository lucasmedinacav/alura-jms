package jms.fila.prioridade;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

public class TesteProdutorFilaPrioridade {
	
	public static void main(String[] args) throws Exception {
		
		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection cn = factory.createConnection();
		cn.start();
		
		Session session = cn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Destination fila = (Destination) context.lookup("LOG");
		
		MessageProducer producer = session.createProducer(fila);
		
		Message message = session.createTextMessage("INFO - 12/12/2012 12:12 LASKALKSAKKAALSU HUDHUUHS SJDIIJDSI");
		
		//PRIMEIRO PARAMETRO EH A MENSAGEM
		//SEGUNDO PARAMETRO EH O NON_PERSISTENT QUE DEFINE QUE A MENSAGEM SERA DESCARTADA SE O BROKER FOR REINICIADO OU SE ULTRAPASSAR O TEMPO DO QUATRO PARAMETRO
		//O TERCEIRO PARAMETRO DEFININE PRIORIDADE SENDO 0 PRIORIDADE + BAIXA E 9 PRIORIDADE + ALTA
		producer.send(message, DeliveryMode.NON_PERSISTENT, 3, 5000);
		
		session.close();
		cn.close();
		context.close();
		
	}
}
