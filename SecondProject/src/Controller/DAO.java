package Controller;

public interface DAO<T> {
    void getAll();
    T get(int id);
}
