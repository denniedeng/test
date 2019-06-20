package xx.test.kafka;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerConnector;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

public class TestKafkaConsumer extends Thread{
	public static Logger log = Logger.getLogger("TestKafkaConsumer");
	
    public static void main(String[] args) {  
    	 KafkaConsumer<String, String> consumer = KafkaUtil.getConsumer();  
         consumer.subscribe(Arrays.asList("test"));  
         while(true) {  
             ConsumerRecords<String, String> records = consumer.poll(1000);  
             for(ConsumerRecord<String, String> record : records) {  
                 System.out.println("fetched from partition " + record.partition() + ", offset: " + record.offset() + ", message: " + record.value());  
             }  
         }  
    }
}
