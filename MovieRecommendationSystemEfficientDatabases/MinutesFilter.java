
/**
 * Write a description of MinutesFilter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MinutesFilter implements Filter{
    
    private int minMins, maxMins;
    
    public MinutesFilter(int minMins, int maxMins){
        this.minMins = minMins;
        this.maxMins = maxMins;
    }
    
    public boolean satisfies(String id){
        if (MovieDatabase.getMinutes(id) >= minMins && MovieDatabase.getMinutes(id) <= maxMins)
            return true;
        return false;
    }
}
