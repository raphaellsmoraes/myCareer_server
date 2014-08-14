package domain.repository;

import domain.User;
import domain.UserSteps;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Claudio Eduardo de Oliveira (claudioeduardo.deoliveira@sonymobile.com)
 */
@Repository
public interface UserStepsRepository extends MongoRepository<UserSteps,String> {

    public List<UserSteps> findByGroupId(String groupId);

}
