package sg.edu.ntu.scse.cz2006.ontology.easyexercise.utils;

public class Box<T> {
    private T t;

    public Box() {
        t = null;
    }

    public Box(T t) {
        this.t = t;
    }

    public T get() {
        return t;
    }

    public void set(T t) {
        this.t = t;
    }
}
