
/**
 * Write a description of DirectorsFilter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

public class DirectorsFilter implements Filter{
    
    private String directors;
    
    public DirectorsFilter(String directors){
        this.directors = directors;
    }
    
    public boolean satisfies(String id){
        
        String[] directorsArray = directors.split(",");
        for (String director : directorsArray){
            if (MovieDatabase.getDirector(id).contains(director))
                return true;
        }
        return false;
    }

}
