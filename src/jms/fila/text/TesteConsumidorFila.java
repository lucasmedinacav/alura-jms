package jms.fila.text;

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

public class TesteConsumidorFila {
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		
		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection cn = factory.createConnection();
		cn.start();
		
		//NECESSARIO UM session.commit() da line 49 PARA CONFIRMAR RECEBIMENTO DA MSG
		Session session = cn.createSession(true, Session.SESSION_TRANSACTED);
		
		//COM Session.CLIENT_ACKNOWLEDGE É necessario um message.acknowledge() que esta na line 40
		//ELE QUE CONFIRMA QUE DE FATO A MENSAGEM CHEGOU
		//Session session = cn.createSession(false, Session.CLIENT_ACKNOWLEDGE);
		
		//COM Session.AUTO_ACKNOWLEDGE nao eh necessario um message.acknowledge() que esta na line 40
		//Session session = cn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Destination fila = (Destination) context.lookup("financeiro");
		MessageConsumer consumer = session.createConsumer(fila);
		
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				
				TextMessage textMessage = (TextMessage) message;
				try {
					//message.acknowledge();
					System.out.println(textMessage.getText());
					session.commit();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		
		new Scanner(System.in).nextLine();
		
		session.close();
		cn.close();
		context.close();
		
	}
}
