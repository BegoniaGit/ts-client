package site.yan.core.data;

public class Pack<T> {

    private T value;

    public Pack(T value) {
        this.value = value;
    }

    public static <T> Pack<T> create(T value) {
        return new Pack(value);
    }

    public void update(T value) {
        this.value = value;
    }

    public T getValue() {
        return this.value;
    }
}
