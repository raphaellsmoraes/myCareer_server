package domain;

import java.io.Serializable;

/**
 * @author Claudio Eduardo de Oliveira (claudioeduardo.deoliveira@sonymobile.com)
 */
public class UserSteps implements Serializable{

    private String id;

    private String groupId;

    private long steps;

    public UserSteps() {
    }

    private UserSteps(String id, String groupId) {
        this.id = id;
        this.groupId = groupId;
    }

    public static UserSteps newUserSteps(String id, String groupId) {
        return new UserSteps(id, groupId);
    }


    public UserSteps addSteps(Long steps){
        this.steps += steps;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public long getSteps() {
        return steps;
    }

    public void setSteps(long steps) {
        this.steps = steps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserSteps)) return false;
        UserSteps userSteps = (UserSteps) o;
        if (steps != userSteps.steps) return false;
        if (id != null ? !id.equals(userSteps.id) : userSteps.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (int) (steps ^ (steps >>> 32));
        return result;
    }

}
