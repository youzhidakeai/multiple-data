package com.dc.multiple.kafka;

import com.alibaba.fastjson.JSON;
import com.dc.multiple.entity.ck.OrderEntityForCK;
import com.dc.multiple.mysql.listener.AggregationListener;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Component("kafkaSender")
public class KafkaSendImpl implements KafkaSend {

    @Value("${kafka.topic}")
    private String topic;

    private final ObjectMapper getObjectMapper;

    private final KafkaTemplate kafkaTemplate;


    public KafkaSendImpl(ObjectMapper getObjectMapper, KafkaTemplate kafkaTemplate) {
        this.getObjectMapper = getObjectMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sender(Object rowData) {
        kafkaTemplate.send(topic, rowData);
    }

    @SneakyThrows
    @KafkaListener(topics = {"ck-mysql-sync"}, groupId = "dataSync")
    public void tempConsumer (ConsumerRecord<?, ?> record) {

        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.orElse(null);
            AggregationListener.TempBO tempBO = JSON.parseObject(message.toString(), AggregationListener.TempBO.class);
            Map<String, String> filedMap = tempBO.getAfterMapList().stream().findAny().orElse(null);
            OrderEntityForCK entity = JSON.parseObject(JSON.toJSONString(filedMap), OrderEntityForCK.class);
            // todo 额外消费
            log.info("kafka consumer message : {}",JSON.toJSONString(entity));
        }
    }
}
