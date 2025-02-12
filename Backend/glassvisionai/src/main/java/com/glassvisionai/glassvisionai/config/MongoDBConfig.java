package com.glassvisionai.glassvisionai.config;

import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.glassvisionai.glassvisionai.repository")
public class MongoDBConfig {

    @Bean
    public MongoTemplate mongoTemplate() {
        // Mongo URI from application.properties
        String mongoUri = "mongodb+srv://admin:admin123@cluster0.nqqai.mongodb.net/mydb?retryWrites=true&w=majority&appName=Cluster0";
        // Create MongoDatabaseFactory
        MongoDatabaseFactory mongoDbFactory = new SimpleMongoClientDatabaseFactory(MongoClients.create(mongoUri), "mydb");
        return new MongoTemplate(mongoDbFactory);
    }

    @Bean
    public GridFsTemplate gridFsTemplate(MongoTemplate mongoTemplate) {
        // Create GridFsTemplate using MongoTemplate (No need for MongoDbFactory directly)
        return new GridFsTemplate(mongoTemplate.getMongoDatabaseFactory(), mongoTemplate.getConverter());
    }
}
