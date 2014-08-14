package domain.repository;

import domain.NewConnection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Claudio Eduardo de Oliveira (claudioeduardo.deoliveira@sonymobile.com)
 */
@Repository
public interface NewConnectionRepository extends MongoRepository<NewConnection,String> {
}
