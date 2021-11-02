package sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location;

import static org.junit.Assert.assertTrue;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.Coordinates;

import org.junit.Test;

public class SVY21CoordinatesTest {
    @Test
    public void test(){
        SVY21Coordinates svy21Coordinates = new SVY21Coordinates(38744.57, 28001.64);
        Coordinates coordinates = new Coordinates(1.366666, 103.833333);
        assertTrue(svy21Coordinates.toCoordinates().getDistance(coordinates) < 0.01);
    }
}
