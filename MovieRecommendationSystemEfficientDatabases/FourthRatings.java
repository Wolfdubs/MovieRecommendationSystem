
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
    
    private double dotProductOld(Rater me, Rater other){
        ArrayList<String> moviesRatedMe = me.getItemsRated();
        ArrayList<String> moviesRatedOther = other.getItemsRated();
        Set<String> moviesRatedSetBoth = new HashSet<>();
        for (String movieID : moviesRatedMe)
            moviesRatedSetBoth.add(movieID);
        for (String movieID : moviesRatedOther)
            moviesRatedSetBoth.add(movieID);
        double adjustedRatingsProductSum = 0;
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
    
    private double dotProduct(Rater me, Rater other){
        double dotProduct = 0;
        ArrayList<String> movies = me.getItemsRated();
        for (String movieID : movies){
            if (other.hasRating(movieID)){
                double ratingMe = me.getRating(movieID);
                double ratingOther = other.getRating(movieID);
                ratingMe -= 5;
                ratingOther -= 5;
                dotProduct += ratingMe * ratingOther;
            }
        }
        return dotProduct;
    }
    
    
    
    public ArrayList<Rating> getSimilaritiesOld(String raterID){
        ArrayList<Rating> ratings = new ArrayList<>();
        RaterDatabase.initialize("ratings_short.csv");
        Rater myRater = RaterDatabase.getRater(raterID);
        for (Rater rater : RaterDatabase.getRaters()){
            if (rater.getID().equals(raterID))
                System.out.println("myRater = " + myRater + " is the input rater so found and skipped");
            else {
                System.out.println("myRater = " + myRater + " is not being skipped");
                double similarity = dotProduct(myRater, rater);   
                if (similarity < 0) continue;
                System.out.println("similarity = " + similarity);
                Rating rating = new Rating(rater.getID(), similarity);
                System.out.println("rating = " + rating);
                ratings.add(rating);
            }
        }
        Collections.sort(ratings, Collections.reverseOrder());
        System.out.println(ratings);
        return ratings;
    }
    
    public ArrayList<Rating> getSimilarities(String raterID){
        ArrayList<Rating> ratingsList = new ArrayList<>();
        Rater me = RaterDatabase.getRater(raterID);
        for (Rater rater : RaterDatabase.getRaters()){
            if (!rater.equals(me)){
                double dotProduct = dotProduct(me, rater);
                if (dotProduct > 0)
                    ratingsList.add(new Rating(rater.getID(), dotProduct));
            }
        }
        Collections.sort(ratingsList, Collections.reverseOrder());
        return ratingsList;
    }
    
    public ArrayList<Rating> getSimilarRatingsOld(String raterID, int numSimilarRaters, int minimumRaters){
        ArrayList<Rating> ratingsList = new ArrayList<>();
        ArrayList<Rating> similarRatings = getSimilarities(raterID);
        System.out.println("Similar Ratings = \n" + similarRatings);
        // List<Rating> topSimilarRatings = similarRatings.subList(0,numSimilarRaters);
        MovieDatabase.initialize("ratedmovies_short.csv");
        RaterDatabase.initialize("ratings_short.csv");
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
    
    public ArrayList<Rating> getSimilarRatings(String raterID, int numSimilarRaters, int minimumRaters){
        ArrayList<Rating> ratingsList = new ArrayList<>();
        ArrayList<Rating> similarRatings = getSimilarities(raterID);
        for (String movieID : MovieDatabase.filterBy(new TrueFilter())){
            double weightedAverage = 0;
            double sum = 0;
            int ratersCount = 0;
            for (int i = 0; i < Math.min(numSimilarRaters,similarRatings.size()); i++){
                Rating currentRating = similarRatings.get(i);
                double currentRatingWeight = currentRating.getValue();
                String currentRaterID = currentRating.getItem();
                Rater currentRater = RaterDatabase.getRater(currentRaterID);
                if (currentRater.hasRating(movieID)){
                    ratersCount++;
                    sum += currentRatingWeight * currentRater.getRating(movieID);
                } 
            }
            if (ratersCount >= minimumRaters){
                weightedAverage = sum / ratersCount;
                ratingsList.add(new Rating(movieID, weightedAverage));
            }
        }
        Collections.sort(ratingsList, Collections.reverseOrder());
        return ratingsList;
    }
    
    public ArrayList<Rating> getSimilarRatingsByFilter(String raterID, int numSimilarRaters, int minimumRaters, Filter filterCriteria){
        ArrayList<Rating> ratingsList = new ArrayList<>();
        ArrayList<Rating> similarRatings = getSimilarities(raterID);
        for (String movieID : MovieDatabase.filterBy(filterCriteria)){
            double weightedAverage = 0;
            double sum = 0;
            int ratersCount = 0;
            for (int i = 0; i < numSimilarRaters; i++){
                Rating currentRating = similarRatings.get(i);
                double currentRatingWeight = currentRating.getValue();
                String currentRaterID = currentRating.getItem();
                Rater currentRater = RaterDatabase.getRater(currentRaterID);
                if (currentRater.hasRating(movieID)){
                    ratersCount++;
                    sum += currentRatingWeight * currentRater.getRating(movieID);
                } 
            }
            if (ratersCount >= minimumRaters){
                weightedAverage = sum / ratersCount;
                ratingsList.add(new Rating(movieID, weightedAverage));
            }
        }
        Collections.sort(ratingsList, Collections.reverseOrder());
        return ratingsList;
    }
}

















































