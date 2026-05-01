package com.example.demo.config;
import com.example.demo.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.annotation.EnableKafkaStreams;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Consumed;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
@EnableKafkaStreams
@Slf4j
public class KafkaStreamsConfig {

    private final StringRedisTemplate redisTemplate;

    public KafkaStreamsConfig(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Bean
    public KStream<String, Transaction> kStream(StreamsBuilder streamsBuilder) {
        JsonSerializer<Transaction> serializer = new JsonSerializer<>();
        JsonDeserializer<Transaction> deserializer = new JsonDeserializer<>(Transaction.class);
        deserializer.addTrustedPackages("*");
        // Package path eka ekama nisa setUseTypeHeaders(false) danna ona wenne naha,
        // eth safe wenna thibbata kamak naha.
        deserializer.setUseTypeHeaders(false);

        Serde<Transaction> transactionSerde = Serdes.serdeFrom(serializer, deserializer);

        KStream<String, Transaction> stream = streamsBuilder.stream("transactions-topic",
                Consumed.with(Serdes.String(), transactionSerde));

        // Fraud Detection Logic
        KStream<String, Transaction> flaggedStream = stream.filter((key, transaction) -> {
            if (transaction == null) return false;

            // Rule: Amount check
            boolean isHighAmount = transaction.getAmount() > 50000;

            // Rule: Redis Blacklist check
            String redisKey = "blacklist:" + transaction.getUserId();
            boolean isBlacklisted = Boolean.TRUE.equals(redisTemplate.hasKey(redisKey));

            return isHighAmount || isBlacklisted;
        });

        flaggedStream.to("flagged-transactions-topic",
                Produced.with(Serdes.String(), transactionSerde));

        return stream;
    }
}