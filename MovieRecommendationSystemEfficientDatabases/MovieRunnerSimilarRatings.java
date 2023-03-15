
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
    private String ratingsFileQuiz = "ratingsQuiz.csv";
    
    
    public void printAverageRatings(){
        FourthRatings fr = new FourthRatings();
        RaterDatabase.initialize(ratingsFile);
        int uniqueRatersInDBCount = RaterDatabase.size();
        System.out.println("There are " + uniqueRatersInDBCount + " raters");
        
        MovieDatabase.initialize(moviesFile);
        int moviesInDBCount = MovieDatabase.size();
        System.out.println("There are " + moviesInDBCount + " movies");
        
        ArrayList<Rating> ratings = fr.getAverageRatings(1); 
        Collections.sort(ratings);
        for (Rating rating : ratings){
            String movieID = rating.getItem();
            String ratingMovieTitle = MovieDatabase.getTitle(movieID);
            System.out.println(rating.getValue() + "\t" + ratingMovieTitle);
        }
        System.out.println("Length of list of rated movies returned: " + ratings.size() + "\n");
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
        System.out.println("Length of list of rated movies returned: " + filteredRatings.size() + "\n");
    }
    
    public void printSimilarRatings(){
        FourthRatings fr = new FourthRatings();
        RaterDatabase.initialize(ratingsFile);
        int uniqueRatersInDBCount = RaterDatabase.size();
        System.out.println("There are " + uniqueRatersInDBCount + " raters");
        
        MovieDatabase.initialize(moviesFile);
        int moviesInDBCount = MovieDatabase.size();
        System.out.println("There are " + moviesInDBCount + " movies");
        
        ArrayList<Rating> ratings = fr.getSimilarRatings("71",20,5) ;
        //Collections.sort(ratings);

        int limitOfNumberOfRecommendations = 15;
        for (int i = 0; i < Math.min(limitOfNumberOfRecommendations, ratings.size()); i++){
            String movieID = ratings.get(i).getItem();
            String ratingMovieTitle = MovieDatabase.getTitle(movieID);
            System.out.printf("%d %.2f %s \n", i+1, ratings.get(i).getValue(), ratingMovieTitle); 
        }
        System.out.println("Length of list of rated movies returned: " + ratings.size() + "\n");
    }
    
    public void printSimilarRatingsByGenre(){
        FourthRatings fr = new FourthRatings();
        RaterDatabase.initialize(ratingsFile);
        int uniqueRatersInDBCount = RaterDatabase.size();
        System.out.println("There are " + uniqueRatersInDBCount + " raters");
        
        MovieDatabase.initialize(moviesFile);
        int moviesInDBCount = MovieDatabase.size();
        System.out.println("There are " + moviesInDBCount + " movies");
        
        Filter filter = new GenreFilter("Mystery");
        ArrayList<Rating> ratings = fr.getSimilarRatingsByFilter("964",20,5,filter) ;
        //Collections.sort(ratings);

        int limitOfNumberOfRecommendations = 15;
        for (int i = 0; i < Math.min(limitOfNumberOfRecommendations, ratings.size()); i++){
            String movieID = ratings.get(i).getItem();
            String ratingMovieTitle = MovieDatabase.getTitle(movieID);
            System.out.printf("%d %.2f %s \n \t %s \n", i+1, ratings.get(i).getValue(), ratingMovieTitle, MovieDatabase.getGenres(movieID));
        }
        System.out.println("Length of list of rated movies returned: " + ratings.size() + "\n");
    }
    
    public void printSimilarRatingsByDirector(){
        FourthRatings fr = new FourthRatings();
        RaterDatabase.initialize(ratingsFile);
        int uniqueRatersInDBCount = RaterDatabase.size();
        System.out.println("There are " + uniqueRatersInDBCount + " raters");
        
        MovieDatabase.initialize(moviesFile);
        int moviesInDBCount = MovieDatabase.size();
        System.out.println("There are " + moviesInDBCount + " movies");
        
        Filter filter = new DirectorsFilter("Clint Eastwood,J.J. Abrams,Alfred Hitchcock,Sydney Pollack,David Cronenberg,Oliver Stone,Mike Leigh");
        ArrayList<Rating> ratings = fr.getSimilarRatingsByFilter("120",10,2,filter) ;
        //Collections.sort(ratings);

        int limitOfNumberOfRecommendations = 15;
        for (int i = 0; i < Math.min(limitOfNumberOfRecommendations, ratings.size()); i++){
            String movieID = ratings.get(i).getItem();
            String ratingMovieTitle = MovieDatabase.getTitle(movieID);
            System.out.printf("%d %.2f %s \n \t %s \n", i+1, ratings.get(i).getValue(), ratingMovieTitle, MovieDatabase.getDirector(movieID));
            
        }
        System.out.println("Length of list of rated movies returned: " + ratings.size() + "\n");
    }

    public void printSimilarRatingsByGenreAndMinutes(){
        FourthRatings fr = new FourthRatings();
        RaterDatabase.initialize(ratingsFile);
        int uniqueRatersInDBCount = RaterDatabase.size();
        System.out.println("There are " + uniqueRatersInDBCount + " raters");
        
        MovieDatabase.initialize(moviesFile);
        int moviesInDBCount = MovieDatabase.size();
        System.out.println("There are " + moviesInDBCount + " movies");
        
        AllFilters allFilters = new AllFilters();
        allFilters.addFilter(new GenreFilter("Drama"));
        allFilters.addFilter(new MinutesFilter(80,160));
        ArrayList<Rating> ratings = fr.getSimilarRatingsByFilter("168",10,3,allFilters) ;
        //Collections.sort(ratings);

        int limitOfNumberOfRecommendations = 15;
        for (int i = 0; i < Math.min(limitOfNumberOfRecommendations, ratings.size()); i++){
            String movieID = ratings.get(i).getItem();
            String ratingMovieTitle = MovieDatabase.getTitle(movieID);
            System.out.printf("%d %.2f %s %d" + "m" + "\n \t %s \n", i+1, ratings.get(i).getValue(), ratingMovieTitle, MovieDatabase.getMinutes(movieID),MovieDatabase.getGenres(movieID)); 
        }
        System.out.println("Length of list of rated movies returned: " + ratings.size() + "\n");
    }
    
    public void printSimilarRatingsByYearAndMinutes(){
        FourthRatings fr = new FourthRatings();
        RaterDatabase.initialize(ratingsFile);
        int uniqueRatersInDBCount = RaterDatabase.size();
        System.out.println("There are " + uniqueRatersInDBCount + " raters");
        
        MovieDatabase.initialize(moviesFile);
        int moviesInDBCount = MovieDatabase.size();
        System.out.println("There are " + moviesInDBCount + " movies");
        
        AllFilters allFilters = new AllFilters();
        allFilters.addFilter(new YearAfterFilter(1975));
        allFilters.addFilter(new MinutesFilter(70,200));
        ArrayList<Rating> ratings = fr.getSimilarRatingsByFilter("314",10,5,allFilters) ;
        //Collections.sort(ratings);

        int limitOfNumberOfRecommendations = 15;
        for (int i = 0; i < Math.min(limitOfNumberOfRecommendations, ratings.size()); i++){
            String movieID = ratings.get(i).getItem();
            String ratingMovieTitle = MovieDatabase.getTitle(movieID);
            System.out.printf("%d %.2f %s %d" + "m, year: " +  "%d \n", i+1, ratings.get(i).getValue(), ratingMovieTitle, MovieDatabase.getMinutes(movieID),MovieDatabase.getYear(movieID)); 
        }
        System.out.println("Length of list of rated movies returned: " + ratings.size() + "\n");
    }
}








































