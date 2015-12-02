
package auditorium;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bnanday
 */


/*

This class is used to get all the level types of the auditorium through spring depedency injection.
It contains a simple List object which holds all the available types of levels in the auditorium.

*/
public class LevelTypes {
    
    private ArrayList<String> levelTypes;

    public LevelTypes() {
    }

    public LevelTypes(List levelTypes) {
        this.levelTypes = (ArrayList<String>) levelTypes;
    }

    public ArrayList getLevelTypes() {
        return levelTypes;
    }

    public void setLevelTypes(List levelTypes) {
        this.levelTypes = (ArrayList<String>) levelTypes;
    }
    
    

    
    
}
