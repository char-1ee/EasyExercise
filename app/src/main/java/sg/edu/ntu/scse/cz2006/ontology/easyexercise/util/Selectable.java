package sg.edu.ntu.scse.cz2006.ontology.easyexercise.util;

/**
 * A wrapper for selectable items.
 *
 * @param <T> the type of the object to be wrapped
 * @author Zhong Ruoyu
 * @author Ma Xinyi
 */
public class Selectable<T> extends Box<T> {
    private boolean isSelected = false;
    private final T t;

    public Selectable(T t) {
        super(t);
        this.t = t;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void toggle() {
        isSelected = !isSelected;
    }

    public void select() {
        isSelected = true;
    }

    public void deselect() {
        isSelected = false;
    }
}
