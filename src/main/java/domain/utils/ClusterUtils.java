package domain.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by rapha_000 on 26/08/2014.
 */
public class ClusterUtils {

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

}
