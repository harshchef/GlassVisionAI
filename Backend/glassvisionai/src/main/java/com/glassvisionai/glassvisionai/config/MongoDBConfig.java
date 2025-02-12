package com.glassvisionai.glassvisionai.config;

import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.glassvisionai.glassvisionai.repository")
public class MongoDBConfig {

    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory() {
        String mongoUri = "mongodb+srv://admin:admin123@cluster0.nqqai.mongodb.net/mydb?retryWrites=true&w=majority&appName=Cluster0";
        return new SimpleMongoClientDatabaseFactory(MongoClients.create(mongoUri), "mydb");
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDatabaseFactory) {
        return new MongoTemplate(mongoDatabaseFactory);
    }

    @Bean
    public GridFsTemplate gridFsTemplate(MongoDatabaseFactory mongoDatabaseFactory, MongoTemplate mongoTemplate) {
        return new GridFsTemplate(mongoDatabaseFactory, mongoTemplate.getConverter(), "image");
        // "custom_collection_name" will be used instead of the default "fs"
    }
}
