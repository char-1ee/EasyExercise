package sg.edu.ntu.scse.cz2006.ontology.easyexercise.recommendation;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.sport.Sport;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.weather.WeatherData;

/**
 * A utility class that generates a list of {@code Sport} based on the weather conditions given.
 *
 * @author Ma Xinyi
 */
public class SportsRecommendation {
    private final Collection<Sport> sports;

    public SportsRecommendation(Collection<Sport> sports) {
        this.sports = sports;
    }

    public Set<Sport> getRecommendation(WeatherData weather) {
        return sports.stream().filter(sport -> {
            if (weather.getRainfall().getResult() > 0
                    || weather.getUVIndex() > 7
                    || weather.getTemperature().getResult() > 35
                    || weather.getPM25().getResult() <= 55) {
                return sport.getType() == Sport.SportType.INDOOR || sport.getType() == Sport.SportType.INDOOR_OUTDOOR;
            }
            return sport.getType() == Sport.SportType.OUTDOOR || sport.getType() == Sport.SportType.INDOOR_OUTDOOR;
        }).collect(Collectors.toSet());
    }
}