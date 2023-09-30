package com.quangtoi.good_news.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quangtoi.good_news.dto.enumeration.CacheName;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.util.Arrays;
import java.util.stream.Collectors;

@Log4j2
@Configuration
@EnableCaching
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "redis", name = "active", havingValue = "true")
public class RedisConfiguration {
    @Value("${redis.host}")
    private String host;
    @Value("${redis.port}")
    private Integer port;
    private final ObjectMapper objectMapper;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(host, port);
    }

    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        ObjectMapper copy = objectMapper.copy();
        copy.activateDefaultTyping(copy.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
        return RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(new GenericJackson2JsonRedisSerializer(copy)));
    }

    @Primary
    @Bean(name = "defaultCache")
    public CacheManager defaultCache(final RedisConnectionFactory redisConnectionFactory) {
        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(cacheConfiguration())
                .initialCacheNames(Arrays.stream(CacheName.values()).map(Enum::toString).collect(Collectors.toSet()))
                .build();
    }
}
