package jms.topic.object;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;
import modelo.Pedido;
import modelo.PedidoFactory;

public class TesteProdutorTopicoObject {
	
	public static void main(String[] args) throws Exception {
		
		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection cn = factory.createConnection();
		cn.start();
		
		Session session = cn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Destination topico = (Destination) context.lookup("loja");
		
		MessageProducer producer = session.createProducer(topico);
		
		Pedido pedido = new PedidoFactory().geraPedidoComValores();
	
		Message message = session.createObjectMessage(pedido);
		producer.send(message);
		
		session.close();
		cn.close();
		context.close();
		
	}
}
