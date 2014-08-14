package domain.repository;

import domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Claudio Eduardo de Oliveira (claudioeduardo.deoliveira@sonymobile.com)
 */
@Repository
public interface UserRepository extends MongoRepository<User,String> {

    public List<User> findByGroupId(String groupId);

}
