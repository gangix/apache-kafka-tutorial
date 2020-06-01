package com.zero2hero.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducerDemo {
	public static void main(String[] args) {
		// create producer properties
		// create producer
		// send data

		final Logger logger = LoggerFactory.getLogger(ProducerDemo.class);
		Properties properties = new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

		Producer<String, String> producer = new KafkaProducer<String, String>(properties);
		
		for (int i = 0; i < 10; i++) {
			ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>("first_topic",
					"Hello World" + (i+1));

			producer.send(producerRecord, new Callback() {

				public void onCompletion(RecordMetadata metadata, Exception exception) {
					if (exception == null) {
						logger.info("received new metadata \n" + "Topic : " + metadata.topic() + "partition "
								+ metadata.partition() + "offset" + metadata.offset());
					} else {
						logger.error("error while producing" + exception.getMessage());
					}

				}
			});
		}

		producer.close();
	}

}
