package daos;

import java.util.List;

/**
 *interface of all databse-view daos
 *
 * defines only the select methods a databse-view DAO must implement
 * since views can only be read in postgresql
 *
 * @param <T> class of the model the dao interacts with
 */
interface DatabaseViewDao<T>{
    List<T> getAll();

    T getById(int id);
}
