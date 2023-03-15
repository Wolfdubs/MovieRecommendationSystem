
/**
 * Write a description of RecommendationRunner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
import java.text.DecimalFormat;

public class RecommendationRunner implements Recommender{
    
    private Random random;
    private final DecimalFormat df = new DecimalFormat("0.00");
    

    public ArrayList<String> getItemsToRate(){
        random = new Random();
        ArrayList<String> movieIDsToRate = new ArrayList<>();
        ArrayList<String> movieIDs = MovieDatabase.filterBy(new TrueFilter());
        int numberOfMoviesToRate = 20;
        for (int i = 0; i < numberOfMoviesToRate; i ++){
            int randomInt = random.nextInt(MovieDatabase.size()); 
            String randomMovieID = movieIDs.get(randomInt);
            movieIDsToRate.add(randomMovieID);
        }
        return movieIDsToRate;        
    }
    
    public void printRecommendationsFor(String webRaterID){
        FourthRatings fr = new FourthRatings();
        ArrayList<Rating> similarRatings = fr.getSimilarRatings(webRaterID, 10, 3);
        int limitOfNumberOfRecommendations = 20;
        if (similarRatings.size() == 0)
            System.out.println("No recommended movie results found");
        else {
            System.out.println("Your recommended movies are: \n");
            ArrayList<String> printed = new ArrayList<>();
            String tableFields = ("<table> <tr><th>Ranking</th><th>Movie Title</th><th>Similarity Rating</th></tr>");
            String tableContent = "";
            for (int i = 0; i < Math.min(limitOfNumberOfRecommendations, similarRatings.size()); i++){
                String movieID = similarRatings.get(i).getItem();
                String ratingMovieTitle = MovieDatabase.getTitle(movieID);
                double movieValue = similarRatings.get(i).getValue();  
                String movieValueString = df.format(movieValue);
                int movieRanking = i;
                tableContent += "<tr> <td>" + movieRanking + "</td><td>" + ratingMovieTitle + "</td><td>" + movieValueString + "</td></tr>";
            }
            System.out.println(tableFields + tableContent + "</table>");
        }
    }
}





























