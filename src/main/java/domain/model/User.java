package domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;

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

    private String birthday;

    private ArrayList<FavoriteBooks> books;

    private ArrayList<FavoriteMovies> movies;

    private ArrayList<FavoriteMusics> music;

    private ArrayList<FavoriteAthletes> favorite_athletes;

    private Location location;

    private String gender;

    private Personality personality;

    private ArrayList<Profession> professions;

    private String groupId;

    public User() {
    }

    public User(String name, String facebookId, String birthday, ArrayList<FavoriteBooks> books,
                ArrayList<FavoriteMovies> movies, ArrayList<FavoriteMusics> music,
                ArrayList<FavoriteAthletes> favorite_athletes, Location location, String gender,
                Personality personality, String groupId) {

        //this.id = UUID.randomUUID().toString();
        this.name = name;
        this.facebookId = facebookId;
        this.birthday = birthday;
        this.books = books;
        this.movies = movies;
        this.music = music;
        this.favorite_athletes = favorite_athletes;
        this.location = location;
        this.gender = gender;
        this.personality = personality;
        this.groupId = groupId;
    }

    public static User newUser(String name, String facebookId, String birthday, ArrayList<FavoriteBooks> books,
                               ArrayList<FavoriteMovies> movies, ArrayList<FavoriteMusics> music,
                               ArrayList<FavoriteAthletes> favorite_athletes, Location location, String gender,
                               Personality personality) {

        return new User(name, facebookId, birthday, books,
                movies, music, favorite_athletes, location,
                gender, personality, NOT_CLUSTERED);
    }

    public static User newUser(User user) {

        return new User(user.getName(), user.getFacebookId(), user.getBirthday(), user.getBooks(),
                user.getMovies(), user.getMusic(), user.getFavorite_athletes(), user.getLocation(),
                user.getGender(), user.getPersonality(), NOT_CLUSTERED);
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public ArrayList<Profession> getProfessions() {
        return professions;
    }

    public void setProfessions(ArrayList<Profession> professions) {
        this.professions = professions;
    }

    public Personality getPersonality() {
        return personality;
    }

    public void setPersonality(Personality personality) {
        this.personality = personality;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public ArrayList<FavoriteAthletes> getFavorite_athletes() {
        return favorite_athletes;
    }

    public void setFavorite_athletes(ArrayList<FavoriteAthletes> favorite_athletes) {
        this.favorite_athletes = favorite_athletes;
    }

    public ArrayList<FavoriteMusics> getMusic() {
        return music;
    }

    public void setMusic(ArrayList<FavoriteMusics> music) {
        this.music = music;
    }

    public ArrayList<FavoriteMovies> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<FavoriteMovies> movies) {
        this.movies = movies;
    }

    public ArrayList<FavoriteBooks> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<FavoriteBooks> books) {
        this.books = books;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", facebookId='" + facebookId + '\'' +
                ", birthday='" + birthday + '\'' +
                ", books=" + books +
                ", movies=" + movies +
                ", music=" + music +
                ", favorite_athletes=" + favorite_athletes +
                ", location=" + location +
                ", gender='" + gender + '\'' +
                ", personality=" + personality +
                ", professions=" + professions +
                ", groupId='" + groupId + '\'' +
                '}';
    }
}
