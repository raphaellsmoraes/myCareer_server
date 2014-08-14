package domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author Claudio Eduardo de Oliveira (claudioeduardo.deoliveira@sonymobile.com)
 */
@Document(collection = "user")
public class User implements Serializable {

    /**
     * User not clustered constant
     */
    public final static String NOT_CLUSTERED = "NC";

    @Id
    private String id;

    private String groupId;

    private double lat;

    private double lon;

    public User() {
    }

    private User(String id, double lat, double lon,String groupId) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.groupId = groupId;
    }

    public static User newUser(String id, double lat, double lon) {
        return new User(id, lat, lon,NOT_CLUSTERED);
    }

    public static User newUserWithGroupId(String id, double lat, double lon,String groupId) {
        return new User(id, lat, lon,groupId);
    }

    public static User newUserByNewConnection(NewConnection newConnection){
        return new User(newConnection.getId(),newConnection.getLat(),newConnection.getLon(),NOT_CLUSTERED);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        if (Double.compare(user.lat, lat) != 0) return false;
        if (Double.compare(user.lon, lon) != 0) return false;
        if (!id.equals(user.id)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id.hashCode();
        temp = Double.doubleToLongBits(lat);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(lon);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "User{" + "id='" + id + '\'' + ", groupId='" + groupId + '\'' + ", lat=" + lat + ", lon=" + lon +'}';
    }
}
