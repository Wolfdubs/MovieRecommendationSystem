

/**
 * Write a description of SecondRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class SecondRatings {
    private ArrayList<Movie> myMovies;
    private ArrayList<Rater> myRaters;
    
    public SecondRatings() {
        // default constructor
        this("ratedmoviesfull.csv", "ratings.csv");
    }
    
    public SecondRatings(String movieFilename, String ratingsFilename){
        FirstRatings fr = new FirstRatings();
        myMovies = fr.loadMovies("data/" + movieFilename);
        myRaters = fr.loadRaters("data/" + ratingsFilename);
    }
    
    public int getMovieSize(){
        return myMovies.size();
    }
    
    public int getRaterSize(){
        return myRaters.size();
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
        for (Movie movie : myMovies){
            double averageRating = getAverageByID(movie.getID(), minimumRaters);
            if (averageRating == 0.0) continue;
            Rating rating = new Rating(movie.getID(), averageRating);
            ratingsList.add(rating);
        }
        return ratingsList;
    }
    
    public String getTitle(String movieID){
        for (Movie movie : myMovies){
            if (movie.getID().equals(movieID))
                return movie.getTitle();
        }
        return "Movie ID not found";
    }
    
    public String getID(String movieTitle){
        for (Movie movie : myMovies){
            if (movie.getTitle().equals(movieTitle))
                return movie.getID();
        }
        return "Movie Title not found";
    }
    

    
}





































