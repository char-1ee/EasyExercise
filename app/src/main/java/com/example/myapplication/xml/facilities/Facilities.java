package com.example.myapplication.xml.facilities;

import com.example.myapplication.beans.Coordinates;
import com.example.myapplication.beans.Facility;
import com.example.myapplication.beans.FacilityDescription;
import com.example.myapplication.beans.location.SVY21Coordinates;
import com.example.myapplication.utils.XMLUtil;
import com.example.myapplication.utils.comparators.FacilityComparator;

import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Facilities {
    private static final int POSTAL_CODE_INDEX = 3;
    private static final int NAME_INDEX = 5;
    private static final int URL_INDEX = 6;
    private static final int DESCRIPTION_INDEX = 7;
    private static final int ADDRESS_INDEX = 9;
    private static final int X_ADDR_INDEX = 15;
    private static final int Y_ADDR_INDEX = 16;

    private final List<Facility> facilities;
    private final Set<String> sports;

    public Facilities(String xmlString) {
        Elements locationElements = XMLUtil.getXMLDocumentFromString(xmlString)
                .getElementsByTag("Placemark");
        this.facilities = new ArrayList<>(locationElements.size());
        this.sports = new HashSet<>();
        for (int i = 0; i < locationElements.size(); ++i) {
            Elements attributes = XMLUtil
                    .getXMLDocumentFromString(locationElements.get(i)
                            .getElementsByTag("description").first().text())
                    .body().child(0).child(0).getElementsByTag("table").first()
                    .getElementsByTag("tr");

            String name = attributes.get(NAME_INDEX).child(1).text();
            String url = attributes.get(URL_INDEX).child(1).text();
            String description = ((TextNode) attributes.get(DESCRIPTION_INDEX)
                    .child(1).childNode(0)).getWholeText();
            FacilityDescription facilityDescription =
                    new FacilityDescription(description);
            String address = attributes.get(ADDRESS_INDEX).child(1).text();
            String postalCode =
                    attributes.get(POSTAL_CODE_INDEX).child(1).text();
            SVY21Coordinates svy21Coordinates = new SVY21Coordinates(
                    Double.parseDouble(
                            attributes.get(Y_ADDR_INDEX).child(1).text()),
                    Double.parseDouble(
                            attributes.get(X_ADDR_INDEX).child(1).text()),
                    name);
            Facility facility = new Facility(name, url, address, postalCode,
                    facilityDescription.getDescription(), 0,
                    facilityDescription.getOpeningHours(),
                    facilityDescription.getFacilities(),
                    facilityDescription.getSports(),
                    svy21Coordinates.toCoordinates());
            this.facilities.add(facility);
            this.sports.addAll(facilityDescription.getSports());
        }
    }

    public List<Facility> getFacilityList(Coordinates location) {
        List<Facility> result = new ArrayList<>(facilities);
        result.sort(new FacilityComparator(location));
        return result;
    }

    public Set<String> getSports() {
        return sports;
    }
}
