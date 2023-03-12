
/**
 * Write a description of EfficientRater here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class EfficientRater implements Rater{
    private String myID;
    private Map<String, Rating> movieToRatingMap;

    public EfficientRater(String id) {
        myID = id;
        movieToRatingMap = new HashMap<>();
    }
    
    public void addRating(String item, double rating) {   //item is the IMDB code for the movie
        Rating ratingObj = new Rating(item, rating);
        movieToRatingMap.put(item, ratingObj);
    }

    public boolean hasRating(String item) {
        if (movieToRatingMap.get(item)!=null)
            return true;
        return false;
    }

    public String getID() {
        return myID;
    }

    public double getRating(String item) {
        if (movieToRatingMap.get(item)!= null)
            return movieToRatingMap.get(item).getValue();
        return -1;
    }

    public int numRatings() {
        return movieToRatingMap.size();
    }

    public ArrayList<String> getItemsRated() {
        ArrayList<String> list = new ArrayList<String>();
            for (String movieID : movieToRatingMap.keySet())
                list.add(movieID);
        return list;
    }
}














