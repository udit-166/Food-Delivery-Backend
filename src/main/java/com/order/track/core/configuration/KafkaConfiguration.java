package com.order.track.core.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.order.track.adapter.model.FcmNotification;
import com.order.track.adapter.model.HandleNotificationRequest;
import com.order.track.adapter.model.OrderStatusUpdateEvent;

@Configuration
public class KafkaConfiguration {

	@Bean
	public ProducerFactory<String, HandleNotificationRequest> producerFactory(){
		try {
			Map<String, Object> config = new HashMap<>();
			
			config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
			config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
			config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
			
			return new DefaultKafkaProducerFactory<>(config);
		} catch (Exception e) {
			System.out.println("The error while configuring kafka in auth-service as producer role is: "+e);
			return null;
		}
	}
	
	@Bean
	public KafkaTemplate<String, HandleNotificationRequest> kafkaTemplate(){
		return new KafkaTemplate<>(producerFactory());
	}
	
	@Bean
	public ConsumerFactory<String, UUID> consumerFactory(){
		Map<String, Object> config = new HashMap<>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		config.put(ConsumerConfig.GROUP_ID_CONFIG, "notification-group");
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		//config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
		config.put(JsonDeserializer.TRUSTED_PACKAGES,"*");
		
		return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), new JsonDeserializer<>(UUID.class));
		
	}
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, UUID> kafkaListenerContainerFactory(){
		ConcurrentKafkaListenerContainerFactory<String, UUID> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}
	
	@Bean
	public ConsumerFactory<String, OrderStatusUpdateEvent> updateOrderStatusListner(){
		Map<String, Object> config = new HashMap<>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		config.put(ConsumerConfig.GROUP_ID_CONFIG, "notification-group");
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
		config.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.order.track.adapter.model.OrderStatusUpdateEvent");
	    config.put(JsonDeserializer.TYPE_MAPPINGS, 
	        "com.delivery.cart.adapter.models.OrderStatusUpdateEvent:com.order.track.adapter.model.OrderStatusUpdateEvent");
		config.put(JsonDeserializer.TRUSTED_PACKAGES,"*"); // consumer's own DTO

		
		return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), new JsonDeserializer<>(OrderStatusUpdateEvent.class));
		
	}
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, OrderStatusUpdateEvent> KafkaListnerForUpdateOrderStatus(){
		ConcurrentKafkaListenerContainerFactory<String, OrderStatusUpdateEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(updateOrderStatusListner());
		return factory;
	}
}
