package domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Raphael Moraes (raphael.lsmoraes@gmail.com)
 */
@Document(collection = "user")
public class User implements Serializable {

    /**
     * User not clustered constant
     */
    public final static String NOT_CLUSTERED = "NC";

    @Id
    private String id;

    private String name;

    private String facebookId;

    private Date birthday;

    private ArrayList<FavoriteBooks> favoriteBooks;

    private ArrayList<FavoriteMovies> favoriteMovies;

    private ArrayList<FavoriteMusics> favoriteMusics;

    private ArrayList<FavoriteAthletes> favoriteAthletes;

    private Location location;

    private String gender;

    private Personality personality;

    private ArrayList<Profession> professions;

    private String groupId;

    public User() {
    }

    public User(String id, String name, String facebookId, Date birthday, ArrayList<FavoriteBooks> favoriteBooks,
                ArrayList<FavoriteMovies> favoriteMovies, ArrayList<FavoriteMusics> favoriteMusics,
                ArrayList<FavoriteAthletes> favoriteAthletes, Location location, String gender,
                Personality personality, String groupId) {

        this.id = id;
        this.name = name;
        this.facebookId = facebookId;
        this.birthday = birthday;
        this.favoriteBooks = favoriteBooks;
        this.favoriteMovies = favoriteMovies;
        this.favoriteMusics = favoriteMusics;
        this.favoriteAthletes = favoriteAthletes;
        this.location = location;
        this.gender = gender;
        this.personality = personality;
        this.groupId = groupId;
    }

    public static User newUser(String id, String name, String facebookId, Date birthday, ArrayList<FavoriteBooks> favoriteBooks,
                               ArrayList<FavoriteMovies> favoriteMovies, ArrayList<FavoriteMusics> favoriteMusics,
                               ArrayList<FavoriteAthletes> favoriteAthletes, Location location, String gender,
                               Personality personality) {

        return new User(id, name, facebookId, birthday, favoriteBooks,
                favoriteMovies, favoriteMusics, favoriteAthletes, location,
                gender, personality, NOT_CLUSTERED);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public ArrayList<FavoriteBooks> getFavoriteBooks() {
        return favoriteBooks;
    }

    public void setFavoriteBooks(ArrayList<FavoriteBooks> favoriteBooks) {
        this.favoriteBooks = favoriteBooks;
    }

    public ArrayList<FavoriteMovies> getFavoriteMovies() {
        return favoriteMovies;
    }

    public void setFavoriteMovies(ArrayList<FavoriteMovies> favoriteMovies) {
        this.favoriteMovies = favoriteMovies;
    }

    public ArrayList<FavoriteMusics> getFavoriteMusics() {
        return favoriteMusics;
    }

    public void setFavoriteMusics(ArrayList<FavoriteMusics> favoriteMusics) {
        this.favoriteMusics = favoriteMusics;
    }

    public ArrayList<FavoriteAthletes> getFavoriteAthletes() {
        return favoriteAthletes;
    }

    public void setFavoriteAthletes(ArrayList<FavoriteAthletes> favoriteAthletes) {
        this.favoriteAthletes = favoriteAthletes;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Personality getPersonality() {
        return personality;
    }

    public void setPersonality(Personality personality) {
        this.personality = personality;
    }

    public ArrayList<Profession> getProfessions() {
        return professions;
    }

    public void setProfessions(ArrayList<Profession> professions) {
        this.professions = professions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
