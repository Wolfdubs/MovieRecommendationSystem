
/**
 * Write a description of MovieRunnerAverage here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;

public class MovieRunnerAverage {
    
    public void printAverageRatings(){
        SecondRatings sr = new SecondRatings("ratedmoviesfull.csv", "ratings.csv");
        int moviesCount = sr.getMovieSize(), ratersCount = sr.getRaterSize();
        System.out.println("There are " + moviesCount + " movies, and " + ratersCount + " raters");
        
        ArrayList<Rating> ratings = sr.getAverageRatings(50);
        Collections.sort(ratings);
        System.out.println("Length of list of rated movies returned: " + ratings.size());
        for (Rating rating : ratings){
            String ratingMovieTitle = sr.getTitle(rating.getItem());
            System.out.println(rating.getValue() + "\t" + ratingMovieTitle);
        }
    }
    
    public void getAverageRatingOneMovie(){
        SecondRatings sr = new SecondRatings("ratedmoviesfull.csv", "ratings.csv");
        String movieTitle = "Vacation";
        String movieID = sr.getID(movieTitle);
        double averageRating = sr.getAverageByID(movieID, 0);
        System.out.println("The movie \"" + movieTitle + "\" has an average rating of: " + averageRating);
    }
    
    
}


































