package sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location;

import java.io.Serializable;

/**
 * A user-defined location.
 *
 * @author Ma Xinyi
 * @author Zhong Ruoyu
 */
public class CustomizedLocation extends Location implements Serializable {
    public CustomizedLocation(Coordinates coordinates) {
        super(coordinates.getLatitude(), coordinates.getLongitude(),
                coordinates.getName(), LocationType.CUSTOMISED_LOCATION);
    }
}
