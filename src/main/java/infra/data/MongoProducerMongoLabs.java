package infra.data;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

/**
 * @author Claudio Eduardo de Oliveira (claudioeduardo.deoliveira@sonymobile.com)
 */
@Configuration
@Profile(value = "labs")
public class MongoProducerMongoLabs {

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        String textUri = "mongodb://handshake:core2DUO11@ds061199.mongolab.com:61199/handshake";
        MongoClientURI uri = new MongoClientURI(textUri);
        MongoClient mongoClient = new MongoClient(uri);
        String databaseName = "handshake";
        MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongoClient, databaseName);
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory);
        return mongoTemplate;
    }

}
