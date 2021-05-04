package api.dao;

import java.util.List;

public interface Dao<T> {
    
   T get(String serial);
    
    List<T> getAll();
    
    void save(T t);
    
    void update(T t, String[] params);
    
    void delete(T t);
}