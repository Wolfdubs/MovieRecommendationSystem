
/**
 * Write a description of FourthRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class FourthRatings {
    
    //returns a double representing the average movie rating for this ID if there are at least minimalRaters ratings
    public double getAverageByID(String movieID, int minimumRaters){
        double totalRating = 0;
        double ratersCount = 0;
        for (Rater rater : RaterDatabase.getRaters()){
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
    
    private int dotProduct(Rater me, Rater other){
        ArrayList<String> moviesRatedMe = me.getItemsRated();
        ArrayList<String> moviesRatedOther = other.getItemsRated();
        Set<String> moviesRatedSetBoth = new HashSet<>();
        for (String movieID : moviesRatedMe)
            moviesRatedSetBoth.add(movieID);
        for (String movieID : moviesRatedOther)
            moviesRatedSetBoth.add(movieID);
        int adjustedRatingsProductSum = 0;
        for (String movieID : moviesRatedOther){
            double ratingMe = me.getRating(movieID);
            double ratingOther = other.getRating(movieID);
            double adjustedRatingMe = ratingMe -5;
            double adjustedRatingOther = ratingOther -5;
            double adjustedRatingsProduct = adjustedRatingMe * adjustedRatingOther;
            adjustedRatingsProductSum += adjustedRatingsProduct;
        }
        return adjustedRatingsProductSum;
    }
    
    public ArrayList<Rating> getSimilarities(String raterID){
        ArrayList<Rating> ratings = new ArrayList<>();
        Rater myRater = RaterDatabase.getRater(raterID);
        for (Rater rater : RaterDatabase.getRaters()){
            if (rater.getID().equals(raterID)){
                System.out.println("input rater found and skipped");
                continue;
            }
            else {
                int similarity = dotProduct(myRater, rater);
                if (similarity < 0) continue;
                Rating rating = new Rating(rater.getID(), similarity);
                ratings.add(rating);
            }
        }
        Collections.sort(ratings, Collections.reverseOrder());
        return ratings;
    }
    
    public ArrayList<Rating> getSimilarRatings(String raterID, int numSimilarRaters, int minimumRaters){
        ArrayList<Rating> ratingsList = new ArrayList<>();
        ArrayList<Rating> similarRatings = getSimilarities(raterID);
        System.out.println("Similar Ratings = \n" + similarRatings);
        // List<Rating> topSimilarRatings = similarRatings.subList(0,numSimilarRaters);
        for (String movieID : MovieDatabase.filterBy(new TrueFilter())){
            int aggregateRating = 0;
            int countOfRatingsBySimilarRaters = 0;
            for (int i = 0; i < numSimilarRaters; i++){
                Rating currentRating = similarRatings.get(i);
                System.out.println("current Rating = " + currentRating);
                String currentRaterID = currentRating.getItem();
                System.out.println("current Rater ID = " + currentRaterID);
                double currentRaterWeighting = currentRating.getValue();
                System.out.println("current Rater weighting = " + currentRaterWeighting);
                Rater currentRater = RaterDatabase.getRater(currentRaterID);
                System.out.println("current Rater = " + currentRater);
                if (currentRater.hasRating(movieID))
                    countOfRatingsBySimilarRaters++;
                    System.out.println("countOfRatingsBySimilarRaters = " + countOfRatingsBySimilarRaters);
                double ratingForCurrentMovie = currentRater.getRating(movieID);
                System.out.println("ratingForCurrentMovie = " + ratingForCurrentMovie);
                double weightedMovieRating = ratingForCurrentMovie * currentRaterWeighting;
                System.out.println("weightedMovieRating = " + weightedMovieRating);
                aggregateRating += weightedMovieRating;
                System.out.println("aggregateRating = " + aggregateRating);
            }
            if (countOfRatingsBySimilarRaters >= minimumRaters){
                int averageRating = aggregateRating / numSimilarRaters;
                System.out.println("averageRating = " + averageRating);
                Rating movieAndAverageSimilarRating = new Rating(movieID, averageRating);
                System.out.println("movieAndAverageSimilarRating = " + movieAndAverageSimilarRating);
                ratingsList.add(movieAndAverageSimilarRating);
            }
        }
        Collections.sort(ratingsList,Collections.reverseOrder());
        System.out.println("ratingsList = \n " + ratingsList);
        return ratingsList;
    }
    
    public ArrayList<Rating> getSimilarRatingsByFilter(String raterID, int numSimilarRaters, int minimumRaters, Filter filterCriteria){
        ArrayList<Rating> ratingsList = new ArrayList<>();
        ArrayList<Rating> similarRatings = getSimilarities(raterID);
        // List<Rating> topSimilarRatings = similarRatings.subList(0,numSimilarRaters);
        for (String movieID : MovieDatabase.filterBy(filterCriteria)){
            int aggregateRating = 0;
            int countOfRatingsBySimilarRaters = 0;
            for (int i = 0; i < numSimilarRaters; i++){
                Rating currentRating = similarRatings.get(i);
                String currentRaterID = currentRating.getItem();
                double currentRaterValue = currentRating.getValue();
                Rater currentRater = RaterDatabase.getRater(currentRaterID);
                if (currentRater.hasRating(movieID))
                    countOfRatingsBySimilarRaters++;
                double ratingForCurrentMovie = currentRater.getRating(movieID);
                double weightedMovieRating = ratingForCurrentMovie * currentRaterValue;
                aggregateRating += weightedMovieRating;
            }
            if (countOfRatingsBySimilarRaters >= minimumRaters){
                int averageRating = aggregateRating / numSimilarRaters;
                Rating movieAndAverageSimilarRating = new Rating(movieID, averageRating);
                ratingsList.add(movieAndAverageSimilarRating);
            }
        }
        Collections.sort(ratingsList,Collections.reverseOrder());
        return ratingsList;
    }
    
    
}

















































