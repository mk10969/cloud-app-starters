//package org.uma.platform.feed.application.configuration;
//
//import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoClients;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
//import org.springframework.data.mongodb.core.MongoTemplate;
//
//@Configuration
//public class MongoConfiguration extends AbstractMongoClientConfiguration {
//
//    @Value("${mongo.host}")
//    private String host;
//
//    @Value("${mongo.port}")
//    private String port;
//
//    @Value("${mongo.database}")
//    private String database;
//
//    @Override
//    public MongoClient mongoClient() {
//        return MongoClients.create("mongodb://" + this.host + ":" + this.port + "/" + this.database);
//    }
//
//    @Override
//    protected String getDatabaseName() {
//        return this.database;
//    }
//
//    @Bean
//    public MongoTemplate MongoTemplate() throws Exception {
//        return new MongoTemplate(mongoClient(), getDatabaseName());
//    }
//
//}
//
