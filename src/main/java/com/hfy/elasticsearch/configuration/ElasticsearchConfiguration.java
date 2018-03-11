package com.hfy.elasticsearch.configuration;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by HuangFangyuan on 2018/2/25.
 */
@Configuration
public class ElasticsearchConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger("LogService");

    @Value("${transport-client.cluster-name}")
    String clusterName;
    @Value("${transport-client.host}")
    String host;
    @Value("${transport-client.port}")
    int port;

    @Bean
    public TransportClient client() throws UnknownHostException {
        LOGGER.info("cluster name:" + clusterName + "host:" + host + "port:" + port);
        Settings settings = Settings.builder()
                .put("cluster.name",clusterName)
                .build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName(host), port));
        return client;

    }
}
