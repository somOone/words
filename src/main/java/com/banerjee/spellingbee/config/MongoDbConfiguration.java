package com.banerjee.spellingbee.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoDbConfiguration {
  @Value(value = "${spring.data.mongodb.uri}")
  private String connectionUri;

  @Value(value = "${spring.data.mongodb.database}")
  private String db;

  @Bean
  public MongoDatabase mongoDatabase() {
    MongoClientURI connectionString = new MongoClientURI(connectionUri);
    MongoClient mongoClient = new MongoClient(connectionString);
    MongoDatabase mongoDatabase = mongoClient.getDatabase(db);

    return mongoDatabase;
  }
}
