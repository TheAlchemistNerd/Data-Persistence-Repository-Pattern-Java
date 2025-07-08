package repository;


import java.util.List;

public interface StudentRepository<T>{
    void create(T t);
    List<T> getAll();
    void update (T t);
    void delete (String emailAddress);
}
