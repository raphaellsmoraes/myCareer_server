package domain.specification;

import domain.NewConnection;
import domain.geo.Haversine;
import infra.specification.AbstractSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * @author Claudio Eduardo de Oliveira (claudioeduardo.deoliveira@sonymobile.com)
 */
public class NewConnectionIsMatch extends AbstractSpecification<NewConnection> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewConnectionIsMatch.class);

    private static final String LOG_MESSAGE_PATTERN = "Distance between users %s M. Group User %s Proposed User %s";

    private static final Double MAX_DISTANCE = Double.valueOf(50);

    private final NewConnection proposed;

    public NewConnectionIsMatch(NewConnection proposed) {
        this.proposed = proposed;
    }

    @Override
    public boolean isSatisfiedBy(NewConnection instance) {
        double distenceBetweenUsers = Haversine.haversineInMeters(proposed.getLat(), proposed.getLon(), instance.getLat(), instance.getLon());
        LOGGER.debug(String.format(LOG_MESSAGE_PATTERN, String.valueOf(distenceBetweenUsers), proposed.getId(), instance.getId()));
        boolean connectUser = new BigDecimal(String.valueOf(distenceBetweenUsers)).compareTo(new BigDecimal(String.valueOf(MAX_DISTANCE))) < 0;
        if (connectUser) {
            return connectUser;
        }
        return false;
    }
}
