
/**
 * Write a description of FirstRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;

public class FirstRatings {
    
    private ArrayList<Movie> loadMovies(String filename){
        ArrayList<Movie> movies = new ArrayList<>();
        
        FileResource fr = new FileResource(filename);
        CSVParser parser = fr.getCSVParser(true);
       
        for (CSVRecord record : parser){
            String id = record.get("id");
            String title = record.get("title");
            String year = record.get("year");
            String genre = record.get("genre");
            String director = record.get("director");
            String country = record.get("country");
            String poster = record.get("poster");
            int minutes = Integer.parseInt(record.get("minutes"));
            Movie movie = new Movie(id, title, year, genre, director, country, poster, minutes);
            movies.add(movie);
        }
        return movies;
    }
    
    public void testLoadMovies(){
        ArrayList<Movie> movies = loadMovies("data/ratedmoviesfull.csv");
        System.out.println("Movie list size = " + movies.size());
        for (Movie m : movies) System.out.println(m);
        
        int comediesCounter = 0;
        for (Movie m : movies){
            if (m.getGenres().contains("Comedy")){
                comediesCounter++;
            }
        }
        System.out.println("Count of comedies = " + comediesCounter);
        
        int longMovieCounter = 0;
        for (Movie m : movies){
            if (m.getMinutes() > 150){
                longMovieCounter++;
            }
        }
        System.out.println("Count of movies longer than 150 minutes = " + longMovieCounter);
        
        
        Map<String, Integer> directorMap = new HashMap<>();
        for (Movie m : movies){ 
            String director = m.getDirector();
            if (directorMap.containsKey(director)) {
                directorMap.put(director,directorMap.get(director)+1);
            } else{
                directorMap.put(director, 1);
            }
        }
        int maxDirectorCount = 0;
        String maxDirector = "";
        for (String director : directorMap.keySet()){
            maxDirectorCount = Math.max(maxDirectorCount, directorMap.get(director));
        }
        ArrayList<String> directors = new ArrayList<>();
        for (Map.Entry<String,Integer> entry : directorMap.entrySet()){
            if (entry.getValue() == maxDirectorCount)
                directors.add(entry.getKey());
        }
        System.out.println("The maximum movies directed by 1 director = " + maxDirectorCount);
        System.out.println("Directors who directed = " + maxDirectorCount + " movies = " + directors);
    }
    
    private ArrayList<Rater> loadRaters(String filename){
        ArrayList<Rater> raterList = new ArrayList<>();
        
        FileResource fr = new FileResource(filename);
        CSVParser parser = fr.getCSVParser(true);
        
        Map<String, ArrayList<Rating>> map = new HashMap<>();
        for (CSVRecord record : parser){
            String currentID = record.get("rater_id");
            String currentItem = record.get("movie_id");
            Double currentCSVRating = Double.parseDouble(record.get("rating"));
            Rating currentRating = new Rating(currentItem, currentCSVRating);
            if (!map.containsKey(currentID)){
                map.put(currentID, new ArrayList<Rating>(){{
                add(currentRating);}});
            } else {
                ArrayList<Rating> ratingsInMap = map.get(currentID);
                ratingsInMap.add(currentRating);
                map.put(currentID, ratingsInMap);
            }
        }
        
        for (Map.Entry<String, ArrayList<Rating>> entry : map.entrySet()){
            Rater rater = new Rater(entry.getKey(), entry.getValue());
            raterList.add(rater);
        }
        return raterList;
    }
    
    public void testLoadRaters(){
        //Print the total number of raters. Then for each rater, print the rater’s ID and the number of ratings they did on one line, followed by each rating (both the movie ID and the rating given) 
        ArrayList<Rater> raters = loadRaters("data/ratings.csv");
        System.out.println("Rater list size = " + raters.size());
        for (Rater rater : raters){
          //  System.out.println(rater.getID() + " has done " + rater.getItemsRated().size() + " ratings ");
            ArrayList<String> moviesRated = rater.getItemsRated();
           // for (String s : moviesRated)
           //     System.out.println("\t" + s + "\t" + rater.getRating(s));
        }
        
        //find the number of ratings for a particular rater
        for (Rater rater : raters){
            if (rater.getID().equals("193"))
                System.out.println("Rater with ID " + rater.getID() + " has done " + rater.getItemsRated().size() + " ratings ");
        }
        
        
        //find the maximum number of ratings by any rater. how many raters have this, who are they

        int maxRatingsCount = 0;
        for (Rater r : raters){ 
            int ratingsCount = r.numRatings();
            maxRatingsCount = Math.max(maxRatingsCount,ratingsCount); 
        }
        
        ArrayList<String> raterIDsWithMax = new ArrayList<>();
        for (Rater r : raters){
            if (r.numRatings() == maxRatingsCount)
                raterIDsWithMax.add(r.getID());
        }
        System.out.println("The maximum ratings by 1 rater = " + maxRatingsCount);
        System.out.println("There are " + raterIDsWithMax.size() + " raters with " + maxRatingsCount + " ratings");
        System.out.println("Raters who rated " + maxRatingsCount + " movies: " + raterIDsWithMax);
        
        //find the number of ratings a particular movie has
        String movie = "1798709";
        int movieFrequencyCount = 0;
        for (Rater r : raters){
            if (r.hasRating(movie))
                movieFrequencyCount++;
        }
        System.out.println("The movie " + movie + " was rated " + movieFrequencyCount + " times "); 
        
        //determine how many total movies have been rated overall
        HashSet<String> allMovies = new HashSet<>();
        for (Rater rater : raters){
            allMovies.addAll(rater.getItemsRated());
        }
        System.out.println("Overall, " + allMovies.size() + " movies were reviwed");
        
        /*
        //determine how many different movies have been rated by every one of these raters
        ArrayList<String> allImdbCodes = new ArrayList<>();
        for (Rater rater : raters){
            allImdbCodes.addAll(rater.getItemsRated());
        }
        Vector<String> ratedByAllVector = new Vector<>();
        for (String imdbCode : allImdbCodes){
            boolean ratedByAll = true;
            for (Rater rater : raters){
                if (!rater.getItemsRated().contains(imdbCode))
                    ratedByAll = false;
                }
            if (ratedByAll)
                ratedByAllVector.add(imdbCode);
        }
        System.out.println("There are " + ratedByAllVector.size() + " movies rated by all raters");
        System.out.println("These movies are: " + ratedByAllVector);
        */
    }
    
    
    

}




















































