package bll;

import dao.AbstractDAO;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * AbstractBLL is an abstract business logic base class
 * which interacts with the data access layer and
 * implements CRUD operations
 *
 * @author Kerekes Agnes
 * @version 1.0
 * @since 2021-06-30
 */

public class AbstractBLL<T> {
    private Class<T> type;
    private AbstractDAO<T> dao;

    @SuppressWarnings("unchecked")
    public AbstractBLL(AbstractDAO<T> dao){
        this.type = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];

        this.dao = dao;
    }

    public List<T> list(){
        return dao.listing();
    }

    public void add(T obj) throws BLLException{
        dao.insert(obj);
    }

    public void update(T obj){
        dao.update(obj);
    }

    public void delete(int id){
        dao.delete(id);
    }
}
