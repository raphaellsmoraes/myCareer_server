package domain.repository;

import domain.model.Occupation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by rapha_000 on 19/08/2014.
 */
@Repository
public interface OccupationRepository extends MongoRepository<Occupation, String> {

    public List<Occupation> findByGroupId(String groupId);

}