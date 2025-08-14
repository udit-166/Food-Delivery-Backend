package com.notification.send.core.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import com.notification.send.adapter.models.EmailNotificationDTO;
import com.notification.send.adapter.models.FcmNotification;
import com.notification.send.adapter.models.HandleNotificationRequest;


@Configuration
public class KafkaConfiguration {

	@Bean
	public ConsumerFactory<String, String> consumerFactory(){
		Map<String, Object> config = new HashMap<>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		config.put(ConsumerConfig.GROUP_ID_CONFIG, "notification-group");
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		//config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
		config.put(JsonDeserializer.TRUSTED_PACKAGES,"*");
		
		return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), new JsonDeserializer<>(String.class));
		
	}
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(){
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}
	
	@Bean
	public ConsumerFactory<String, FcmNotification> consumerSendNotificationFactory(){
		Map<String, Object> config = new HashMap<>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		config.put(ConsumerConfig.GROUP_ID_CONFIG, "notification-group");
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		config.put(JsonDeserializer.TRUSTED_PACKAGES,"*");
		
		return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), new JsonDeserializer<>(FcmNotification.class));
		
	}
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, FcmNotification> kafkaListenerNotificationContainerFactory(){
		ConcurrentKafkaListenerContainerFactory<String, FcmNotification> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerSendNotificationFactory());
		return factory;
	}
	
	@Bean
    public ConsumerFactory<String, HandleNotificationRequest> orderRequestConsumerFactory() {
		Map<String, Object> config = new HashMap<>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		config.put(ConsumerConfig.GROUP_ID_CONFIG, "notification-group");
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
		config.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.notification.send.adapter.models.HandleNotificationRequest");
	    config.put(JsonDeserializer.TYPE_MAPPINGS, 
	        "com.order.track.adapter.model.HandleNotificationRequest:com.notification.send.adapter.models.HandleNotificationRequest");
		config.put(JsonDeserializer.TRUSTED_PACKAGES,"*");
        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), new JsonDeserializer<>(HandleNotificationRequest.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, HandleNotificationRequest> kafkaListenerHandleOrderNotificationContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, HandleNotificationRequest> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(orderRequestConsumerFactory());
        return factory;
    }
	
	
}
