package org.uma.jvLink.server.configuration;


import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;


@Profile("setup")
@Configuration
@EnableReactiveMongoRepositories
public class ReactiveMongoConfiguration extends AbstractReactiveMongoConfiguration {

    @Value("${mongo.host}")
    private String host;

    @Value("${mongo.port}")
    private String port;

    @Value("${mongo.database}")
    private String database;


    @Override
    public MongoClient reactiveMongoClient() {
        // ちゃんとレプリを組んでいない状態で「?replicaSet=rs0」を入れるとダメ。
        return MongoClients.create("mongodb://" + this.host + ":" + this.port + "/" + this.database);
    }

    @Override
    protected String getDatabaseName() {
        return this.database;
    }

    /**
     * 非推奨になったAPIがあり、
     *
     * @return ReactiveMongoTemplate
     * @throws Exception
     * @see org.springframework.data.mongodb.core.ReactiveMongoOperations#inTransaction()
     * 型をReactiveMongoOperationsから、ReactiveMongoTemplateに変更すれば利用可能。
     */
    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate() throws Exception {
        return new ReactiveMongoTemplate(reactiveMongoClient(), getDatabaseName());
    }

}
