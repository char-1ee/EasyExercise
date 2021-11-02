package sg.edu.ntu.scse.cz2006.ontology.easyexercise.util;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.R;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.sport.Sport;

/**
 * String matcher for getting image of sport according to its name.
 *
 * @author Ruan Donglin
 */
public class SportsImageMatcher {
    public static int getImage(Sport sport) {
        int image;
        switch (sport.getName()) {
            case "Free play": {
                image = R.drawable.outdoor;
                break;
            }
            case "Futsal": {
                image = R.drawable.futsal;
                break;
            }
            case "Football": {
                image = R.drawable.soccer;
                break;
            }
            case "Running": {
                image = R.drawable.run;
                break;
            }
            case "Squash": {
                image = R.drawable.squash;
                break;
            }
            case "Badminton": {
                image = R.drawable.badminton;
                break;
            }
            case "Swimming": {
                image = R.drawable.swimming;
                break;
            }
            case "Tennis": {
                image = R.drawable.tennis;
                break;
            }
            case "Hockey": {
                image = R.drawable.hockey;
                break;
            }
            case "Netball": {
                image = R.drawable.netball;
                break;
            }
            case "Basketball": {
                image = R.drawable.basketball;
                break;
            }
            case "Gym": {
                image = R.drawable.dumbbell;
                break;
            }
            default: {
                image = 0;
                break;
            }
        }
        return image;
    }
}
