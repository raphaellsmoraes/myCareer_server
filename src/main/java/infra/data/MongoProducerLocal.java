package infra.data;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

/**
 * @author Raphael Moraes (raphael.lsmoraes@gmail.com)
 */
@Configuration
@Profile(value = "dev")
public class MongoProducerLocal {

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        MongoClient mongoClient = new MongoClient("127.0.0.1");
        SimpleMongoDbFactory simpleMongoDbFactory = new SimpleMongoDbFactory(mongoClient, "myCareer");
        MongoTemplate mongoTemplate = new MongoTemplate(simpleMongoDbFactory);
        return mongoTemplate;
    }

}
