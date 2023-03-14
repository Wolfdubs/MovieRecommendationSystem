

/**
 * Write a description of SecondRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class ThirdRatings {
    private ArrayList<Rater> myRaters;
    
    public ThirdRatings() {
        // default constructor
        this("ratings.csv");
    }
    
    public ThirdRatings(String ratingsFilename){
        FirstRatings fr = new FirstRatings();
        myRaters = fr.loadRaters("data/" + ratingsFilename);
    }
  
    public int getRaterSize(){
        return myRaters.size();
    }
    
    public int getUniqueRaterCount(){
        HashSet<String> unqiueRaters = new HashSet<>();
        for (Rater rater : myRaters){
            unqiueRaters.add(rater.getID());
        }
        return unqiueRaters.size();
    }
    
    //returns a double representing the average movie rating for this ID if there are at least minimalRaters ratings
    public double getAverageByID(String movieID, int minimumRaters){
        double totalRating = 0;
        double ratersCount = 0;
        for (Rater rater : myRaters){
            double rating = rater.getRating(movieID);
            if (rating != -1) {
                totalRating += rating;
                ratersCount++;
            }
        }
        if (ratersCount < minimumRaters) return 0.0;
        else return totalRating/ratersCount;
    }
    
    //find the average rating for every movie that has been rated by at least minimalRaters raters
    public ArrayList<Rating> getAverageRatings(int minimumRaters){
        ArrayList<Rating> ratingsList = new ArrayList<>();
        ArrayList<String> movieIDs = MovieDatabase.filterBy(new TrueFilter());
        for (String movieID : movieIDs){
            double averageRating = getAverageByID(movieID, minimumRaters);
            if (averageRating == 0.0) continue;
            Rating rating = new Rating(movieID, averageRating);
            ratingsList.add(rating);
        }
        return ratingsList;
    }
    
    public ArrayList<Rating> getAverageRatingsByFilter(int minimumRaters, Filter filterCriteria){
        ArrayList<Rating> ratingsMeetingCriteria = new ArrayList<Rating>();
        ArrayList<String> movieIDs = MovieDatabase.filterBy(filterCriteria);
        for (String movieID : movieIDs){
            double averageRating = getAverageByID(movieID, minimumRaters);
            if (averageRating == 0.0) continue;
            Rating rating = new Rating(movieID, averageRating);
            ratingsMeetingCriteria.add(rating);
        }
        return ratingsMeetingCriteria;
    }
    
    

    
}





































