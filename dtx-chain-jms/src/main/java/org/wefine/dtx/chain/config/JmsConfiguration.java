package org.wefine.dtx.chain.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.TransactionAwareConnectionFactoryProxy;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

@Configuration
public class JmsConfiguration {

    @Bean
    public JmsTemplate initJmsTemplate(ConnectionFactory connectionFactory) {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory);
        template.setSessionTransacted(true);
        return template;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("tcp://localhost:61616");
        TransactionAwareConnectionFactoryProxy proxy = new TransactionAwareConnectionFactoryProxy();
        proxy.setTargetConnectionFactory(cf);
        proxy.setSynchedLocalTransactionAllowed(true);
        return proxy;
    }

}
