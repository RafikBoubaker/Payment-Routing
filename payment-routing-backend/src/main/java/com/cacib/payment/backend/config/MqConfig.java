package com.cacib.payment.backend.config;

import com.ibm.mq.jakarta.jms.MQConnectionFactory;
import com.ibm.msg.client.jakarta.wmq.WMQConstants;

import java.util.Hashtable;
import java.util.Properties;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;



@Configuration
public class MqConfig {
    @Value("${ibm.mq.queueManager}")
    private String queueManager;

    @Value("${ibm.mq.channel}")
    private String channel;

    @Value("${ibm.mq.connName}")
    private String connName;

    @Value("${ibm.mq.user}")
    private String user;

    @Value("${ibm.mq.password}")
    private String password;

    @Bean
    public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrency("1-1");
        factory.setSessionTransacted(true);
        return factory;
    }

    @Bean
    public ConnectionFactory connectionFactory() throws JMSException {
        MQConnectionFactory factory = new MQConnectionFactory();


        String hostName = connName.split("\\(")[0];
        int port = Integer.parseInt(connName.split("\\(")[1].replace(")", ""));

        factory.setHostName(hostName);
        factory.setPort(port);
        factory.setQueueManager(queueManager);
        factory.setChannel(channel);
        factory.setTransportType(WMQConstants.WMQ_CM_CLIENT);


        factory.setStringProperty(WMQConstants.USERID, user);
        factory.setStringProperty(WMQConstants.PASSWORD, password);

        Hashtable<String, Object> properties = new Hashtable<>();
        properties.put(WMQConstants.USER_AUTHENTICATION_MQCSP, true);
        properties.put(WMQConstants.USERID, user);
        properties.put(WMQConstants.PASSWORD, password);


        factory.setCCSID(1208);
        factory.setIntProperty(WMQConstants.JMS_IBM_ENCODING, 546);

        return factory;
    }
}