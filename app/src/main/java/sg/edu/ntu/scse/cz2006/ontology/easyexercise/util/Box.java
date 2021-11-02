package sg.edu.ntu.scse.cz2006.ontology.easyexercise.util;

/**
 * A simple wrapper.
 *
 * @param <T> the type of the object to be wrapped
 * @author Zhong Ruoyu
 * @author Ma Xinyi
 */
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
