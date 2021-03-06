package com.skb.demo.config.bluemix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.config.java.ServiceScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * RabbitMQ Config 연동 샘플
 * @author Administrator
 *
 */
@Profile({ "dev", "stg", "prd" })// Cloud환경 인식하는 Annotation(manifest.yml의 Env정보와 매팽)
@Configuration// Spring Config 
//@ServiceScan// Cloud Config Service Scan Annotation 
public class RabbitMQCloudConfig/*extends AbstractCloudConfig*/ {

	@Value("${vcap.service.rabbitmq.addresses}")
	private String addresess;
	
	@Value("${vcap.service.rabbitmq.username}")
	private String username;
	
	@Value("${vcap.service.rabbitmq.password}")
	private String password;
	
	@Value("${vcap.application.instance.index}")
	private String index;
	
	public static final String DEMO_IMAGE_QUEUE = "demo.iamge";
	public static final String DEMO_IMAGE_QUEUE_INDEX = "demo.iamge.";
	public static final String DEMO_IMAGE_QUEUE_RESULT = "demo.result";
	//public static final String DEMO_EPG_QUEUE_INDEX = "demo.uihandler.";
	public static final String DEAD_LETTER_QUEUE = "demo.dead.letter";
    
	public static final String EXCHANGE = "demo.exchange";
	
	@Bean(name="rabbitmqConnectFactory")
	public ConnectionFactory connectionFactory(){
		
		CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
		cachingConnectionFactory.setAddresses(this.addresess);
		cachingConnectionFactory.setUsername(this.username);
		cachingConnectionFactory.setPassword(this.password);
		cachingConnectionFactory.setChannelCacheSize(10);
		return cachingConnectionFactory;
	}

	@Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(1);
        return factory;
    }
	
	@Bean
	public RabbitTemplate rabbitTemplate() {

		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());

		return rabbitTemplate;
	}/**/

	@Bean
	public RabbitAdmin rabbitAdmin() {

		RabbitAdmin admin = new RabbitAdmin(connectionFactory());
		
		return admin;
	}/**/
	
	@Bean
    List<Queue> queues(){
		
		List<Queue> queues = new ArrayList<Queue>();

		Map<String, Object> args = new HashMap<String, Object>();
		args.put("x-dead-letter-exchange", EXCHANGE); 				// Dead Letter Queue exchange
		args.put("x-dead-letter-routing-key", DEAD_LETTER_QUEUE);	// Dead Letter Queue routing key
		args.put("x-message-ttl", 10000); 							// TTL(timeout) 10s
		args.put("x-max-length", 10);								// Message count
		args.put("x-max-length-bytes", 1000000);					// Message size 1M
		
		queues.add(new Queue(DEAD_LETTER_QUEUE, false,false, true));
		queues.add(new Queue(DEMO_IMAGE_QUEUE, false, false, true, args));
		queues.add(new Queue(DEMO_IMAGE_QUEUE_RESULT, false, false, true, args));
		queues.add(new Queue(DEMO_IMAGE_QUEUE_INDEX+index, false, false, true, args));
		
		return queues;
    }

	@Bean
    List<TopicExchange> exchange(){
    	List<TopicExchange> topicExchange = new ArrayList<TopicExchange>();
    	topicExchange.add(new TopicExchange(EXCHANGE, false, false));
    		
        return topicExchange;
    }

	@Bean
    List<Binding> binding(List<Queue> queues, List<TopicExchange> exchange){
    	
    	List<Binding> bindings = new ArrayList<Binding>();

    	bindings.add(BindingBuilder.bind(queues.get(0)).to(exchange.get(0)).with("demo.dead.letter"));
    	bindings.add(BindingBuilder.bind(queues.get(1)).to(exchange.get(0)).with("demo.image"));
    	bindings.add(BindingBuilder.bind(queues.get(2)).to(exchange.get(0)).with("demo.result"));
    	bindings.add(BindingBuilder.bind(queues.get(3)).to(exchange.get(0)).with("demo.image.*"));
    	
    	return bindings;
    }
}