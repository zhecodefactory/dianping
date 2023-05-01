package com.wz.dianping.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchClientConfig {


    @Value("${elasticsearch.ip}")
    String ipAddress;

    @Bean(name = "highLevelCilent")
    public RestHighLevelClient highLevelClient(){
        String[] address = ipAddress.split(":");

        String ip = address[0];

        Integer port = Integer.valueOf(address[1]);

        HttpHost http = new HttpHost(ip, port, "http");

        return new RestHighLevelClient(RestClient.builder(http));
    }

}
