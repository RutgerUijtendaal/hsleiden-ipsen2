package daos;

import java.util.List;

public interface DatabaseViewDao<T>{
    List<T> getAll();

    T getById(int id);
}
