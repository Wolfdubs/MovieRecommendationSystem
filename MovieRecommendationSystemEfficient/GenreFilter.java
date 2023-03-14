
/**
 * Write a description of GenreFilter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.ArrayList;

public class GenreFilter implements Filter{

    String genre;
    
    public GenreFilter(String genre){
        this.genre = genre;
    }
    
    public boolean satisfies(String id){  
        if (MovieDatabase.getGenres(id).contains(genre))
            return true;
        return false;
    }
}
