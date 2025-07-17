package com.order.track.core.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.order.track.adapter.model.FcmNotification;

@Configuration
public class KafkaConfiguration {

	@Bean
	public ProducerFactory<String, FcmNotification> producerFactory(){
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
	public KafkaTemplate<String, FcmNotification> kafkaTemplate(){
		return new KafkaTemplate<>(producerFactory());
	}
}
