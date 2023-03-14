
/**
 * Write a description of MovieRunnerSimilarRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;

public class MovieRunnerSimilarRatings {
    
    private String moviesFile = "ratedmoviesfull.csv";
    private String ratingsFile = "ratings.csv";
    private String moviesFileShort = "ratedmovies_short.csv";
    private String ratingsFileShort = "ratings_short.csv";
    
    public void printAverageRatings(){
        FourthRatings fr = new FourthRatings();
        RaterDatabase.initialize(ratingsFileShort);
        int uniqueRatersInDBCount = RaterDatabase.size();
        System.out.println("There are " + uniqueRatersInDBCount + " raters");
        
        MovieDatabase.initialize(moviesFileShort);
        int moviesInDBCount = MovieDatabase.size();
        System.out.println("There are " + moviesInDBCount + " movies");
        
        ArrayList<Rating> ratings = fr.getAverageRatings(1); 
        Collections.sort(ratings);
        for (Rating rating : ratings){
            String movieID = rating.getItem();
            String ratingMovieTitle = MovieDatabase.getTitle(movieID);
            System.out.println(rating.getValue() + "\t" + ratingMovieTitle);
        }
        System.out.println("Length of list of rated movies returned: " + ratings.size());
    }
    
    public void printAverageRatingsByYearAfterAndGenre(){
        FourthRatings fr = new FourthRatings();
        RaterDatabase.initialize(ratingsFileShort);
        int uniqueRatersInDBCount = RaterDatabase.size();
        System.out.println("There are " + uniqueRatersInDBCount + " raters");
        
        MovieDatabase.initialize(moviesFileShort);
        int moviesInDBCount = MovieDatabase.size();
        System.out.println("There are " + moviesInDBCount + " movies");
        
        GenreFilter gf = new GenreFilter("Crime");
        YearAfterFilter yaf = new YearAfterFilter(1970);
        AllFilters af = new AllFilters();
        af.addFilter(gf); af.addFilter(yaf);
        ArrayList<Rating> filteredRatings = fr.getAverageRatingsByFilter(1, af);
        
        Collections.sort(filteredRatings);
        for (Rating rating : filteredRatings){
            String movieID = rating.getItem();
            String ratingMovieTitle = MovieDatabase.getTitle(movieID);
            int movieYear = MovieDatabase.getYear(movieID);
            String movieGenres = MovieDatabase.getGenres(movieID);
            System.out.println(rating.getValue() + " " + movieYear + " " + ratingMovieTitle + "\n\t" + movieGenres);
        }
        System.out.println("Length of list of rated movies returned: " + filteredRatings.size());
    }
    
    public void printSimilarRatings(){
        FourthRatings fr = new FourthRatings();
        RaterDatabase.initialize(ratingsFile);
        int uniqueRatersInDBCount = RaterDatabase.size();
        System.out.println("There are " + uniqueRatersInDBCount + " raters");
        
        MovieDatabase.initialize(moviesFile);
        int moviesInDBCount = MovieDatabase.size();
        System.out.println("There are " + moviesInDBCount + " movies");
        
        ArrayList<Rating> ratings = fr.getSimilarRatings("65",20,5) ;
        //Collections.sort(ratings);
        for (Rating rating : ratings){
            String movieID = rating.getItem();
            String ratingMovieTitle = MovieDatabase.getTitle(movieID);
            System.out.println(rating.getValue() + "\t" + ratingMovieTitle);
        }
        System.out.println("Length of list of rated movies returned: " + ratings.size());
    }
    
    

}








































