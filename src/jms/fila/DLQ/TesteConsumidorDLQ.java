package jms.fila.DLQ;

import java.util.Scanner;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

public class TesteConsumidorDLQ {
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		
		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection cn = factory.createConnection();
		cn.start();
		
		Session session = cn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//FILA DEFAULT DO ACTIVE MQ QUE MANTEM AS MENSAGENS QUE OS CONSUMIDORES NAO CONSEGUIRAM OBTER POR PROBLEMAS TECNICOS OU DE CODIGO(EXCEPTION)
		Destination fila = (Destination) context.lookup("DLQ");
		MessageConsumer consumer = session.createConsumer(fila);
		
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				
				System.out.println(message);
			}
		});
		
		new Scanner(System.in).nextLine();
		
		session.close();
		cn.close();
		context.close();
		
	}
}
