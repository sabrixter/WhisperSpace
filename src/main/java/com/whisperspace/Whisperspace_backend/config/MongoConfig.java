//package com.whisperspace.Whisperspace_backend.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.mongodb.config.EnableMongoAuditing;
//
//@Configuration
//@EnableMongoAuditing
//public class MongoConfig {
//}
package com.whisperspace.Whisperspace_backend.config;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@EnableMongoAuditing
public class MongoConfig {

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(
                "mongodb+srv://smukh095_db_user:HnXqW9KpnvAZA3df@cluster0.w9h8noy.mongodb.net/whisperspace?retryWrites=true&w=majority&appName=Cluster0"
        );
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), "whisperspace");
    }
}