package domain.repository;

import domain.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Raphael Moraes (raphael.lsmoraes@gmail.com)
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

    public List<User> findByGroupId(String groupId);

}
