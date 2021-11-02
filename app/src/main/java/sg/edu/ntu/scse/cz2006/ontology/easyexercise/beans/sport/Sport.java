package sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.sport;

import androidx.annotation.NonNull;

import java.io.Serializable;

/**
 * A sport activity.
 *
 * @author Ma Xinyi
 * @author Zhong Ruoyu
 */
public class Sport implements Serializable {
    private final long id;
    private final String name;
    private final String alternativeName;
    private final SportType type;
    public Sport(long id, String name, String alternativeName, SportType type) {
        this.id = id;
        this.name = name;
        this.alternativeName = alternativeName;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAlternativeName() {
        return alternativeName;
    }

    public SportType getType() {
        return type;
    }

    public enum SportType {
        INDOOR("Indoor"), OUTDOOR("Outdoor"), INDOOR_OUTDOOR("Indoor/Outdoor");

        private final String name;

        SportType(String name) {
            this.name = name;
        }

        public static SportType getType(String typeString) {
            for (SportType type : SportType.values()) {
                if (type.toString().equals(typeString)) {
                    return type;
                }
            }
            return null;
        }

        @NonNull
        @Override
        public String toString() {
            return name;
        }
    }
}
