package sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.beans;

public abstract class Location {
    private Coordinates coordinates;
    private boolean isFacility;

    public Location(Coordinates coordinates, boolean isFacility) {
        this.coordinates = coordinates;
        this.isFacility = isFacility;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public boolean isFacility() {
        return isFacility;
    }

    public void setFacility(boolean facility) {
        isFacility = facility;
    }
}
