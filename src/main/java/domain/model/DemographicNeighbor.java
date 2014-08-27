package domain.model;

import java.util.ArrayList;

/**
 * Created by rapha_000 on 27/08/2014.
 */
public class DemographicNeighbor {

    User user;
    Double ageRangeEighteen;
    Double ageRangeOverEighteen;
    Double ageRangeOverTwentyNine;
    Double ageRangeOverFortyNine;
    Double genderMale;
    Double genderFemale;

    public DemographicNeighbor(User user, Double ageRangeEighteen, Double ageRangeOverEighteen, Double ageRangeOverTwentyNine, Double ageRangeOverFortyNine, Double genderMale, Double genderFemale) {
        this.user = user;
        this.ageRangeEighteen = ageRangeEighteen;
        this.ageRangeOverEighteen = ageRangeOverEighteen;
        this.ageRangeOverTwentyNine = ageRangeOverTwentyNine;
        this.ageRangeOverFortyNine = ageRangeOverFortyNine;
        this.genderMale = genderMale;
        this.genderFemale = genderFemale;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getAgeRangeEighteen() {
        return ageRangeEighteen;
    }

    public void setAgeRangeEighteen(double ageRangeEighteen) {
        this.ageRangeEighteen = ageRangeEighteen;
    }

    public Double getAgeRangeOverEighteen() {
        return ageRangeOverEighteen;
    }

    public void setAgeRangeOverEighteen(double ageRangeOverEighteen) {
        this.ageRangeOverEighteen = ageRangeOverEighteen;
    }

    public Double getAgeRangeOverTwentyNine() {
        return ageRangeOverTwentyNine;
    }

    public void setAgeRangeOverTwentyNine(double ageRangeOverTwentyNine) {
        this.ageRangeOverTwentyNine = ageRangeOverTwentyNine;
    }

    public Double getAgeRangeOverFortyNine() {
        return ageRangeOverFortyNine;
    }

    public void setAgeRangeOverFortyNine(double ageRangeOverFortyNine) {
        this.ageRangeOverFortyNine = ageRangeOverFortyNine;
    }

    public Double getGenderMale() {
        return genderMale;
    }

    public void setGenderMale(double genderMale) {
        this.genderMale = genderMale;
    }

    public Double getGenderFemale() {
        return genderFemale;
    }

    public void setGenderFemale(double genderFemale) {
        this.genderFemale = genderFemale;
    }

    public ArrayList<Double> toDoubleArray() {

        ArrayList<Double> array = new ArrayList<Double>();

        array.add(getAgeRangeEighteen());
        array.add(getAgeRangeOverEighteen());
        array.add(getAgeRangeOverTwentyNine());
        array.add(getAgeRangeOverFortyNine());
        array.add(getGenderMale());
        array.add(getGenderFemale());

        return array;
    }
}
