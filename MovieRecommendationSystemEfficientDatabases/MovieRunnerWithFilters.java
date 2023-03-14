
/**
 * Write a description of MovieRunnerWithFilters here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;

public class MovieRunnerWithFilters {
    
    private String moviesFile = "ratedmoviesfull.csv";
    private String ratingsFile = "ratings.csv";
    
    public void printAverageRatings(){
        ThirdRatings tr = new ThirdRatings(ratingsFile);
        int uniqueRatersCount = tr.getUniqueRaterCount();
        System.out.println("There are " + uniqueRatersCount + " raters");
        
        MovieDatabase.initialize(moviesFile);
        int moviesInDBCount = MovieDatabase.size();
        System.out.println("There are " + moviesInDBCount + " movies");
        
        ArrayList<Rating> ratings = tr.getAverageRatings(35); 
        Collections.sort(ratings);
        for (Rating rating : ratings){
            String movieID = rating.getItem();
            String ratingMovieTitle = MovieDatabase.getTitle(movieID);
            System.out.println(rating.getValue() + "\t" + ratingMovieTitle);
        }
        System.out.println("Length of list of rated movies returned: " + ratings.size());
    }
    
    public void printAverageRatingsByYear(){
        ThirdRatings tr = new ThirdRatings(ratingsFile);
        int uniqueRatersCount = tr.getUniqueRaterCount();
        System.out.println("There are " + uniqueRatersCount + " raters");
        
        MovieDatabase.initialize(moviesFile);
        int moviesInDBCount = MovieDatabase.size();
        System.out.println("There are " + moviesInDBCount + " movies");
        
        YearAfterFilter yaf = new YearAfterFilter(2000);
        ArrayList<Rating> yearFilteredRatings = tr.getAverageRatingsByFilter(20, yaf);
        
        Collections.sort(yearFilteredRatings);
        for (Rating rating : yearFilteredRatings){
            String movieID = rating.getItem();
            String ratingMovieTitle = MovieDatabase.getTitle(movieID);
            int movieYear = MovieDatabase.getYear(movieID);
            System.out.println(rating.getValue() + "\t" + movieYear + "\t" + ratingMovieTitle);
        }
        System.out.println("Length of list of rated movies returned: " + yearFilteredRatings.size());
    }
    
    public void printAverageRatingsByGenre(){
        ThirdRatings tr = new ThirdRatings(ratingsFile);
        int uniqueRatersCount = tr.getUniqueRaterCount();
        System.out.println("There are " + uniqueRatersCount + " raters");
        
        MovieDatabase.initialize(moviesFile);
        int moviesInDBCount = MovieDatabase.size();
        System.out.println("There are " + moviesInDBCount + " movies");
        
        GenreFilter gf = new GenreFilter("Comedy");
        ArrayList<Rating> genreFilteredRatings = tr.getAverageRatingsByFilter(20, gf);
        
        Collections.sort(genreFilteredRatings);
        for (Rating rating : genreFilteredRatings){
            String movieID = rating.getItem();
            String ratingMovieTitle = MovieDatabase.getTitle(movieID);
            String movieGenres = MovieDatabase.getGenres(movieID);
            System.out.println(rating.getValue() + " " + ratingMovieTitle + "\n\t" + movieGenres );
        }
        System.out.println("Length of list of rated movies returned: " + genreFilteredRatings.size());
    }
    
    public void printAverageRatingsByMinutes(){
        ThirdRatings tr = new ThirdRatings(ratingsFile);
        int uniqueRatersCount = tr.getUniqueRaterCount();
        System.out.println("There are " + uniqueRatersCount + " raters");
        
        MovieDatabase.initialize(moviesFile);
        int moviesInDBCount = MovieDatabase.size();
        System.out.println("There are " + moviesInDBCount + " movies");
        
        MinutesFilter mf = new MinutesFilter(105,135);
        ArrayList<Rating> filteredRatings = tr.getAverageRatingsByFilter(5, mf);
        
        Collections.sort(filteredRatings);
        for (Rating rating : filteredRatings){
            String movieID = rating.getItem();
            String ratingMovieTitle = MovieDatabase.getTitle(movieID);
            int movieTime = MovieDatabase.getMinutes(movieID);
            System.out.println(rating.getValue() + " Time: " + movieTime + " " + ratingMovieTitle);
        }
        System.out.println("Length of list of rated movies returned: " + filteredRatings.size());
    }
    
    
    public void printAverageRatingsByDirectors(){
        ThirdRatings tr = new ThirdRatings(ratingsFile);
        int uniqueRatersCount = tr.getUniqueRaterCount();
        System.out.println("There are " + uniqueRatersCount + " raters");
        
        MovieDatabase.initialize(moviesFile);
        int moviesInDBCount = MovieDatabase.size();
        System.out.println("There are " + moviesInDBCount + " movies");
        
        DirectorsFilter df = new DirectorsFilter("Clint Eastwood,Joel Coen,Martin Scorsese,Roman Polanski,Nora Ephron,Ridley Scott,Sydney Pollack");
        ArrayList<Rating> filteredRatings = tr.getAverageRatingsByFilter(4, df);
        
        Collections.sort(filteredRatings);
        for (Rating rating : filteredRatings){
            String movieID = rating.getItem();
            String ratingMovieTitle = MovieDatabase.getTitle(movieID);
            String movieDirectors = MovieDatabase.getDirector(movieID);
            System.out.println(rating.getValue() + " " + ratingMovieTitle + "\n\t" + movieDirectors);
        }
        System.out.println("Length of list of rated movies returned: " + filteredRatings.size());
    }
    
    public void printAverageRatingsByYearAfterAndGenre(){
        ThirdRatings tr = new ThirdRatings(ratingsFile);
        int uniqueRatersCount = tr.getUniqueRaterCount();
        System.out.println("There are " + uniqueRatersCount + " raters");
        
        MovieDatabase.initialize(moviesFile);
        int moviesInDBCount = MovieDatabase.size();
        System.out.println("There are " + moviesInDBCount + " movies");
        
        GenreFilter gf = new GenreFilter("Drama");
        YearAfterFilter yaf = new YearAfterFilter(1990);
        AllFilters af = new AllFilters();
        af.addFilter(gf); af.addFilter(yaf);
        ArrayList<Rating> filteredRatings = tr.getAverageRatingsByFilter(8, af);
        
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
    
    public void printAverageRatingsByDirectosAfterAndMinutes(){
        ThirdRatings tr = new ThirdRatings(ratingsFile);
        int uniqueRatersCount = tr.getUniqueRaterCount();
        System.out.println("There are " + uniqueRatersCount + " raters");
        
        MovieDatabase.initialize(moviesFile);
        int moviesInDBCount = MovieDatabase.size();
        System.out.println("There are " + moviesInDBCount + " movies");
        
        Filter mf = new MinutesFilter(90,180);
        Filter df = new DirectorsFilter("Clint Eastwood,Joel Coen,Tim Burton,Ron Howard,Nora Ephron,Sydney Pollack");
        AllFilters af = new AllFilters();
        af.addFilter(mf); af.addFilter(df);
        ArrayList<Rating> filteredRatings = tr.getAverageRatingsByFilter(3, af);

        Collections.sort(filteredRatings);
        for (Rating rating : filteredRatings){
            String movieID = rating.getItem();
            String ratingMovieTitle = MovieDatabase.getTitle(movieID);
            int movieTime = MovieDatabase.getMinutes(movieID);
            String movieDirectors = MovieDatabase.getDirector(movieID);
            System.out.println(rating.getValue() + " Time: " + movieTime + " " + ratingMovieTitle + "\n\t" + movieDirectors);
        }
        System.out.println("Length of list of rated movies returned: " + filteredRatings.size());
    }

}













