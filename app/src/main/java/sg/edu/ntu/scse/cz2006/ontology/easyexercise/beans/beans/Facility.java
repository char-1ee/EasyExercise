package sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.beans;

import java.util.List;

public class Facility extends Location {
    private String name;
    private String website;
    private String telephoneNo;
    private String address;
    private int image;
    private List<Sport> sportsSupported;
    private boolean isSelected = false;

    public Facility(Coordinates coordinates,
                    String name,
                    String website,
                    String telephoneNo,
                    String address,
                    int image,
                    List<Sport> sportsSupported) {
        super(coordinates, true);
        this.name = name;
        this.website = website;
        this.telephoneNo = telephoneNo;
        this.address = address;
        this.image = image;
        this.sportsSupported = sportsSupported;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getTelephoneNo() {
        return telephoneNo;
    }

    public void setTelephoneNo(String telephoneNo) {
        this.telephoneNo = telephoneNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public List<Sport> getSportsSupported() {
        return sportsSupported;
    }

    public void setSportsSupported(List<Sport> sportsSupported) {
        this.sportsSupported = sportsSupported;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
