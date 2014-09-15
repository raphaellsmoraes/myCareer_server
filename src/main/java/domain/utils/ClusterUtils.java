package domain.utils;

import domain.model.*;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by rapha_000 on 26/08/2014.
 */
public class ClusterUtils {

    private static final Logger LOGGER = Logger.getLogger(ClusterUtils.class);
    public static String CLUSTER_EIGHTEEN = "18";
    public static String CLUSTER_OVER_EIGHTEEN_UNDER_TWENTYNINE = "18~29";
    public static String CLUSTER_OVER_TWENTYNINE_UNDER_FORTYNINE = "29~49";
    public static String CLUSTER_OVER_FORTYNINE = "49";

    public static String getDemographicClusterAge(String birthday) {

        int rating = 0;

        /*
            Demographics Table
            1 age ≤ 18
            2 18 < age ≤ 29 each user belongs to a single age grouping
            3 29 < age ≤ 49 the corresponding slot takes value 1 (true)
            4 age > 49 the rest of the features remain 0 (false)
         */

        int age = getAgeFromDOB(birthday);
        if (age <= 18) {
            return CLUSTER_EIGHTEEN;
        } else if ((age > 18) && (age <= 29)) {
            return CLUSTER_OVER_EIGHTEEN_UNDER_TWENTYNINE;
        } else if ((age > 29) && (age <= 49)) {
            return CLUSTER_OVER_TWENTYNINE_UNDER_FORTYNINE;
        } else {
            return CLUSTER_OVER_FORTYNINE;
        }
    }

    private static int getAgeFromDOB(String dob) {

        if (dob == null || dob.length() < 10) {
            return -1;
        }
        //take substrings of the dob so split out year, month and day
        int yearDOB = Integer.parseInt(dob.substring(6, 10));
        int monthDOB = Integer.parseInt(dob.substring(3, 5));
        int dayDOB = Integer.parseInt(dob.substring(0, 2));

        //calculate the current year, month and day into separate variables
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        java.util.Date date = new java.util.Date();
        int thisYear = Integer.parseInt(dateFormat.format(date));

        //if the current month is less than the dob month
        //then reduce the dob by 1 as they have not had their birthday yet this year
        dateFormat = new SimpleDateFormat("MM");
        date = new java.util.Date();
        int thisMonth = Integer.parseInt(dateFormat.format(date));

        dateFormat = new SimpleDateFormat("dd");
        date = new java.util.Date();
        int thisDay = Integer.parseInt(dateFormat.format(date));

        //create an age variable to hold the calculated age
        //to start will  set the age equal to the current year minus the year of the dob
        int age = thisYear - yearDOB;

        if (thisMonth < monthDOB) {
            age = age - 1;
        }

        if (thisMonth == monthDOB && thisDay < dayDOB) {
            age = age - 1;
        }

        return age;
    }

    public static int getLevenshteinDistance(String s0, String s1) {
        int len0 = s0.length() + 1;
        int len1 = s1.length() + 1;

        // the array of distances
        int[] cost = new int[len0];
        int[] newcost = new int[len0];

        // initial cost of skipping prefix in String s0
        for (int i = 0; i < len0; i++) cost[i] = i;

        // dynamicaly computing the array of distances

        // transformation cost for each letter in s1
        for (int j = 1; j < len1; j++) {
            // initial cost of skipping prefix in String s1
            newcost[0] = j;

            // transformation cost for each letter in s0
            for (int i = 1; i < len0; i++) {
                // matching current letters in both strings
                int match = (s0.charAt(i - 1) == s1.charAt(j - 1)) ? 0 : 1;

                // computing cost for each transformation
                int cost_replace = cost[i - 1] + match;
                int cost_insert = cost[i] + 1;
                int cost_delete = newcost[i - 1] + 1;

                // keep minimum cost
                newcost[i] = Math.min(Math.min(cost_insert, cost_delete), cost_replace);
            }

            // swap cost/newcost arrays
            int[] swap = cost;
            cost = newcost;
            newcost = swap;
        }

        // the distance is the cost for transforming all letters in both strings
        return cost[len0 - 1];
    }

    /* @getKnowledgeNeighborhood
   returns an neighborhood of users based on tastes (movies, books, athletes and musics)
    */
    public static ArrayList<Neighbor> getKnowledgeNeighborhood(User baseUser, List<User> users) {

        /* Neighborhood Users*/
        ArrayList<Neighbor> neighborhood = new ArrayList<Neighbor>();

        /* search and remove requested id */
        Iterator<User> iterator;
        for (iterator = users.iterator(); iterator.hasNext(); ) {
            User user = iterator.next();
            if (user.getId().equals(baseUser.getId())) {
                iterator.remove();
            }
        }

        /* Creating correlations Matrix */
        for (int i = 0; i <= (users.size() - 1); i++) {

            /* Add to neighborhood */
            neighborhood.add(
                    setKnowledgeCorrelation(baseUser, users.get(i))
            );
        }

        return neighborhood;
    }

    private static Neighbor setKnowledgeCorrelation(User base, User compare) {

        /* get books correlation btw users */
        double booksCorrelation = getBooksCorrelation(base, compare);
        LOGGER.info(String.format("booksCorrelation: %s", booksCorrelation));

        /* get movies correlation btw users */
        double moviesCorrelation = getMoviesCorrelation(base, compare);
        LOGGER.info(String.format("moviesCorrelation: %s", moviesCorrelation));

        /* get atlhetes correlation btw users */
        double athletesCorrelation = getAtlhetesCorrelation(base, compare);
        LOGGER.info(String.format("athletesCorrelation: %s", athletesCorrelation));

        /* get music correlation btw users */
        double musicCorrelation = getMusicCorrelation(base, compare);
        LOGGER.info(String.format("musicCorrelation: %s", musicCorrelation));

        /* get personality correlation btw users */
        //double personalityCorrelation = getBooksCorrelation(base, compare);

        return new Neighbor(compare,
                ((0.25 * booksCorrelation) + (0.25 * moviesCorrelation)
                        + (0.25 * athletesCorrelation) + (0.25 * musicCorrelation))
        );
    }

    private static double getBooksCorrelation(User base, User compare) {
        LOGGER.info(String.format("Starting getBooksCorrelation....%s", Thread.currentThread().getName()));
        PearsonsCorrelation pearsonsCorrelation = new PearsonsCorrelation();
        Neighbor result = null;

        /* Create Array List of books */
        ArrayList<FavoriteBooks> booksObject = new ArrayList<>();
        Set<String> rawBooksId = new HashSet<>();
        booksObject.addAll(base.getBooks());
        booksObject.addAll(compare.getBooks());

        for (FavoriteBooks e : booksObject) {
            rawBooksId.add(e.getId());
        }

        ArrayList<String> books = new ArrayList<>(rawBooksId);

        ArrayList<String> baseBooks = new ArrayList<>();
        ArrayList<String> compareBooks = new ArrayList<>();

        for (FavoriteBooks e : base.getBooks()) {
            baseBooks.add(e.getId());
        }

        for (FavoriteBooks e : compare.getBooks()) {
            compareBooks.add(e.getId());
        }


        /* Creating matrix */
        ArrayList<Double> baseArray = new ArrayList<Double>();
        ArrayList<Double> compareArray = new ArrayList<Double>();

        String[] booksArray = books.toArray(new String[books.size()]);

        String string = "";
        for (String b : books) {
            string = string + "/" + b;
        }

        LOGGER.info(String.format("Books: %s", string));

        for (int i = 0; i <= (booksArray.length - 1); i++) {

            if (baseBooks.contains(booksArray[i].toString())) {
                //LOGGER.info(String.format("Achou %s - %s", base.getId(), booksArray[i].toString()));
                baseArray.add(1.0);
            } else {
                //LOGGER.info(String.format("Nao Achou %s - %s", base.getId(), booksArray[i].toString()));
                baseArray.add(0.0);
            }

            if (compareBooks.contains(booksArray[i].toString())) {
                //LOGGER.info(String.format("Achou %s - %s", compare.getId(), booksArray[i].toString()));
                compareArray.add(1.0);
            } else {
                //LOGGER.info(String.format("Nao Achou %s - %s", compare.getId(), booksArray[i].toString()));
                compareArray.add(0.0);
            }

        }

        if ((baseArray.size() >= 2 && (compareArray.size() >= 2))) {

            return pearsonsCorrelation.correlation(
                    ArrayUtils.toPrimitive(baseArray.toArray(new Double[baseArray.size()]))
                    , ArrayUtils.toPrimitive(compareArray.toArray(new Double[compareArray.size()])));


        } else if ((baseArray.size() > 0 && baseArray.size() < 2) || (compareArray.size() > 0 && compareArray.size() < 2)) {
            return (ArrayUtils.toPrimitive(baseArray.toArray(new Double[baseArray.size()]))[0]
                    + ArrayUtils.toPrimitive(compareArray.toArray(new Double[compareArray.size()]))[0]) / 2;

        } else return 0.0;

    }

    private static double getMoviesCorrelation(User base, User compare) {
        LOGGER.info(String.format("Starting getMoviesCorrelation....%s", Thread.currentThread().getName()));
        PearsonsCorrelation pearsonsCorrelation = new PearsonsCorrelation();
        Neighbor result = null;

        /* Create Array List of books */
        ArrayList<FavoriteMovies> moviesObject = new ArrayList<>();
        Set<String> rawMoviesId = new HashSet<>();
        moviesObject.addAll(base.getMovies());
        moviesObject.addAll(compare.getMovies());

        for (FavoriteMovies e : moviesObject) {
            rawMoviesId.add(e.getId());
        }

        ArrayList<String> movies = new ArrayList<>(rawMoviesId);

        ArrayList<String> baseMovies = new ArrayList<>();
        ArrayList<String> compareMovies = new ArrayList<>();

        for (FavoriteMovies e : base.getMovies()) {
            baseMovies.add(e.getId());
        }

        for (FavoriteMovies e : compare.getMovies()) {
            compareMovies.add(e.getId());
        }


        /* Creating matrix */
        ArrayList<Double> baseArray = new ArrayList<Double>();
        ArrayList<Double> compareArray = new ArrayList<Double>();

        String[] moviesArray = movies.toArray(new String[movies.size()]);

        String string = "";
        for (String b : movies) {
            string = string + "/" + b;
        }

        LOGGER.info(String.format("Movies: %s", string));

        for (int i = 0; i <= (moviesArray.length - 1); i++) {

            if (baseMovies.contains(moviesArray[i].toString())) {
                //LOGGER.info(String.format("Achou %s - %s", base.getId(), moviesArray[i].toString()));
                baseArray.add(1.0);
            } else {
                //LOGGER.info(String.format("Nao Achou %s - %s", base.getId(), moviesArray[i].toString()));
                baseArray.add(0.0);
            }

            if (compareMovies.contains(moviesArray[i].toString())) {
                //LOGGER.info(String.format("Achou %s - %s", compare.getId(), moviesArray[i].toString()));
                compareArray.add(1.0);
            } else {
                //LOGGER.info(String.format("Nao Achou %s - %s", compare.getId(), moviesArray[i].toString()));
                compareArray.add(0.0);
            }

        }

        if ((baseArray.size() >= 2 && (compareArray.size() >= 2))) {

            return pearsonsCorrelation.correlation(
                    ArrayUtils.toPrimitive(baseArray.toArray(new Double[baseArray.size()]))
                    , ArrayUtils.toPrimitive(compareArray.toArray(new Double[compareArray.size()])));


        } else if ((baseArray.size() > 0 && baseArray.size() < 2) || (compareArray.size() > 0 && compareArray.size() < 2)) {
            return (ArrayUtils.toPrimitive(baseArray.toArray(new Double[baseArray.size()]))[0]
                    + ArrayUtils.toPrimitive(compareArray.toArray(new Double[compareArray.size()]))[0]) / 2;

        } else return 0.0;
    }

    private static double getMusicCorrelation(User base, User compare) {
        LOGGER.info(String.format("Starting getMusicCorrelation....%s", Thread.currentThread().getName()));
        PearsonsCorrelation pearsonsCorrelation = new PearsonsCorrelation();
        Neighbor result = null;

        /* Create Array List of books */
        ArrayList<FavoriteMusics> musicObject = new ArrayList<>();
        Set<String> rawMusicId = new HashSet<>();
        musicObject.addAll(base.getMusic());
        musicObject.addAll(compare.getMusic());

        for (FavoriteMusics e : musicObject) {
            rawMusicId.add(e.getId());
        }

        ArrayList<String> musics = new ArrayList<>(rawMusicId);

        ArrayList<String> baseMusics = new ArrayList<>();
        ArrayList<String> compareMusics = new ArrayList<>();

        for (FavoriteMusics e : base.getMusic()) {
            baseMusics.add(e.getId());
        }

        for (FavoriteMusics e : compare.getMusic()) {
            compareMusics.add(e.getId());
        }


        /* Creating matrix */
        ArrayList<Double> baseArray = new ArrayList<Double>();
        ArrayList<Double> compareArray = new ArrayList<Double>();

        String[] musicsArray = musics.toArray(new String[musics.size()]);

        String string = "";
        for (String b : musics) {
            string = string + "/" + b;
        }

        LOGGER.info(String.format("Music: %s", string));

        for (int i = 0; i <= (musicsArray.length - 1); i++) {

            if (baseMusics.contains(musicsArray[i].toString())) {
                //LOGGER.info(String.format("Achou %s - %s", base.getId(), musicsArray[i].toString()));
                baseArray.add(1.0);
            } else {
                // LOGGER.info(String.format("Nao Achou %s - %s", base.getId(), musicsArray[i].toString()));
                baseArray.add(0.0);
            }

            if (compareMusics.contains(musicsArray[i].toString())) {
                //LOGGER.info(String.format("Achou %s - %s", compare.getId(), musicsArray[i].toString()));
                compareArray.add(1.0);
            } else {
                // LOGGER.info(String.format("Nao Achou %s - %s", compare.getId(), musicsArray[i].toString()));
                compareArray.add(0.0);
            }

        }

        if ((baseArray.size() >= 2 && (compareArray.size() >= 2))) {

            return pearsonsCorrelation.correlation(
                    ArrayUtils.toPrimitive(baseArray.toArray(new Double[baseArray.size()]))
                    , ArrayUtils.toPrimitive(compareArray.toArray(new Double[compareArray.size()])));

        } else return 0.0;
    }

    private static double getAtlhetesCorrelation(User base, User compare) {

        LOGGER.info(String.format("Starting getAtlhetesCorrelation....%s", Thread.currentThread().getName()));
        PearsonsCorrelation pearsonsCorrelation = new PearsonsCorrelation();
        Neighbor result = null;

        /* Create Array List of books */
        ArrayList<FavoriteAthletes> atlhetesObject = new ArrayList<>();
        Set<String> rawAtlhetesId = new HashSet<>();
        atlhetesObject.addAll(base.getFavorite_athletes());
        atlhetesObject.addAll(compare.getFavorite_athletes());

        for (FavoriteAthletes e : atlhetesObject) {
            rawAtlhetesId.add(e.getId());
        }

        ArrayList<String> atlhetes = new ArrayList<>(rawAtlhetesId);

        ArrayList<String> baseAtlhetes = new ArrayList<>();
        ArrayList<String> compareAtlhetes = new ArrayList<>();

        for (FavoriteAthletes e : base.getFavorite_athletes()) {
            baseAtlhetes.add(e.getId());
        }

        for (FavoriteAthletes e : compare.getFavorite_athletes()) {
            compareAtlhetes.add(e.getId());
        }


        /* Creating matrix */
        ArrayList<Double> baseArray = new ArrayList<Double>();
        ArrayList<Double> compareArray = new ArrayList<Double>();

        String[] atlhetesArray = atlhetes.toArray(new String[atlhetes.size()]);

        String string = "";
        for (String b : atlhetes) {
            string = string + "/" + b;
        }

        LOGGER.info(String.format("Atlhetes: %s", string));

        for (int i = 0; i <= (atlhetesArray.length - 1); i++) {

            if (baseAtlhetes.contains(atlhetesArray[i].toString())) {
                //LOGGER.info(String.format("Achou %s - %s", base.getId(), atlhetesArray[i].toString()));
                baseArray.add(1.0);
            } else {
                //LOGGER.info(String.format("Nao Achou %s - %s", base.getId(), atlhetesArray[i].toString()));
                baseArray.add(0.0);
            }

            if (compareAtlhetes.contains(atlhetesArray[i].toString())) {
                //LOGGER.info(String.format("Achou %s - %s", compare.getId(), atlhetesArray[i].toString()));
                compareArray.add(1.0);
            } else {
                //LOGGER.info(String.format("Nao Achou %s - %s", compare.getId(), atlhetesArray[i].toString()));
                compareArray.add(0.0);
            }

        }

        if ((baseArray.size() >= 2 && (compareArray.size() >= 2))) {

            return pearsonsCorrelation.correlation(
                    ArrayUtils.toPrimitive(baseArray.toArray(new Double[baseArray.size()]))
                    , ArrayUtils.toPrimitive(compareArray.toArray(new Double[compareArray.size()])));


        } else if ((baseArray.size() > 0 && baseArray.size() < 2) || (compareArray.size() > 0 && compareArray.size() < 2)) {
            return (ArrayUtils.toPrimitive(baseArray.toArray(new Double[baseArray.size()]))[0]
                    + ArrayUtils.toPrimitive(compareArray.toArray(new Double[compareArray.size()]))[0]) / 2;

        } else return 0.0;
    }

    /* @getCollaborativeNeighborhood
    returns an neighborhood of users based on ratings
     */
    public static ArrayList<Neighbor> getCollaborativeNeighborhood(User baseUser, List<User> users) {

        PearsonsCorrelation pearsonsCorrelation = new PearsonsCorrelation();

        /* Neighborhood Users*/
        ArrayList<Neighbor> neighborhood = new ArrayList<Neighbor>();

        /* baseUser double array */
        ArrayList<Double> baseArray = new ArrayList<Double>();
        for (int looper = 0; looper <= (baseUser.getProfessions().size() - 1); looper++) {
            baseArray.add(baseUser.getProfessions().get(looper).getRating());
        }

        /* search and remove requested id */
        Iterator<User> iterator;
        for (iterator = users.iterator(); iterator.hasNext(); ) {
            User user = iterator.next();
            if (user.getId().equals(baseUser.getId())) {
                iterator.remove();
            }
        }

        /* Creating correlations Matrix */
        for (int i = 0; i <= (users.size() - 1); i++) {

            ArrayList<Double> similarArray = new ArrayList<Double>();
            for (int looper = 0; looper <= (users.get(i).getProfessions().size() - 1); looper++) {
                similarArray.add(users.get(i).getProfessions().get(looper).getRating());
            }

            neighborhood.add(new Neighbor(
                            users.get(i),
                            pearsonsCorrelation.correlation(
                                    ArrayUtils.toPrimitive(similarArray.toArray(new Double[similarArray.size()]))
                                    , ArrayUtils.toPrimitive(baseArray.toArray(new Double[baseArray.size()]))))
            );
        }

        return neighborhood;
    }

    /* @getDemographicNeighborhood
returns an neighborhood of users based on demographics (Age, gender and birthday)
    */
    public static ArrayList<Neighbor> getDemographicNeighborhood(User baseUser, List<User> users) {
        PearsonsCorrelation pearsonsCorrelation = new PearsonsCorrelation();

        /* Neighborhood Users*/
        ArrayList<Neighbor> neighborhood = new ArrayList<Neighbor>();

        /* Demographic Neighbors */
        ArrayList<DemographicNeighbor> udemNeighborhood = new ArrayList<DemographicNeighbor>();

        /* Criando correlação do baseUser - Basicamente uma matrix demografica */
        DemographicNeighbor udemBaseUser = getDemographicUser(baseUser);


        /* search and remove requested id */
        Iterator<User> iterator;
        for (iterator = users.iterator(); iterator.hasNext(); ) {
            User user = iterator.next();
            if (user.getId().equals(baseUser.getId())) {
                iterator.remove();
            }
        }

        for (int i = 0; i <= (users.size() - 1); i++) {

            ArrayList<Double> similarArray = getDemographicUser(users.get(i)).toDoubleArray();

            neighborhood.add(new Neighbor(
                            users.get(i),
                            pearsonsCorrelation.correlation(
                                    ArrayUtils.toPrimitive(similarArray.toArray(new Double[similarArray.size()]))
                                    , ArrayUtils.toPrimitive(udemBaseUser.toDoubleArray().toArray(
                                            new Double[udemBaseUser.toDoubleArray().size()]))))
            );

        }

        return neighborhood;
    }

    /* Merge Demographic and Normal Correlation and retrieve an List of Users */
    public static ArrayList<Neighbor> getMergedCorrelations(ArrayList<Neighbor> normalSimilarity, ArrayList<Neighbor> demographicSimilarity) {

        int length = normalSimilarity.size() == demographicSimilarity.size() ? normalSimilarity.size() : 0;
        ArrayList<Neighbor> neighboors = new ArrayList<>();

        if (length != 0) {
            for (int i = 0; i <= (length - 1); i++) {
                if (normalSimilarity.get(i).getUser().getId().equals(demographicSimilarity.get(i).getUser().getId())) {

                    neighboors.add(new Neighbor(normalSimilarity.get(i).getUser(),
                            (normalSimilarity.get(i).getCorrelation() +
                                    (normalSimilarity.get(i).getCorrelation()
                                            * demographicSimilarity.get(i).getCorrelation()))));
                }
            }
            return neighboors;

        } else return null;
    }

    /* {18, 18-29, 29-49, 49, male, female} */
    private static DemographicNeighbor getDemographicUser(User user) {

        String birthday = user.getBirthday();
        String gender = user.getGender();

        if (ClusterUtils.getDemographicClusterAge(birthday)
                .equals(ClusterUtils.CLUSTER_EIGHTEEN)) {

            if (gender.equals("male")) {
                return new DemographicNeighbor(user, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0);
            } else {
                return new DemographicNeighbor(user, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0);
            }

        } else if (ClusterUtils.getDemographicClusterAge(birthday)
                .equals(ClusterUtils.CLUSTER_OVER_EIGHTEEN_UNDER_TWENTYNINE)) {

            if (gender.equals("male")) {
                return new DemographicNeighbor(user, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0);
            } else {
                return new DemographicNeighbor(user, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0);
            }

        } else if (ClusterUtils.getDemographicClusterAge(birthday)
                .equals(ClusterUtils.CLUSTER_OVER_TWENTYNINE_UNDER_FORTYNINE)) {

            if (gender.equals("male")) {
                return new DemographicNeighbor(user, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0);
            } else {
                return new DemographicNeighbor(user, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0);
            }

        } else {

            if (gender.equals("male")) {
                return new DemographicNeighbor(user, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0);
            } else {
                return new DemographicNeighbor(user, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0);
            }
        }
    }
}
