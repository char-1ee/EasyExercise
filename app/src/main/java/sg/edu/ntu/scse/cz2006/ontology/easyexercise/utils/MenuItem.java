package sg.edu.ntu.scse.cz2006.ontology.easyexercise.utils;

public class MenuItem<T> extends Box<T> {
    private boolean isSelected = false;
    private T t;

    public MenuItem(T t) {
        super(t);
        this.t = t;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean value) {
        isSelected = value;
    }

    public void select() {
        isSelected = true;
    }

    public void deselect() {
        isSelected = false;
    }

}
