package dao;

import connection.ConnectionFactory;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * AbstractDao class interacts with the MYSQL Database and
 * contains the SQl queries
 *
 * @author Kerekes Agnes
 * @version 1.0
 * @since 2021-06-30
 */

public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO(){
        this.type = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public T findById(int id){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id" + type.getSimpleName());
        try{
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            System.out.println("AbstractDAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return null;

    }

    public void insert(T toInsert){
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement insertStatement = null;
        try {
            String query = createInsertQuery();
            insertStatement = dbConnection.prepareStatement(query);
            int i = 1;
            for(Field field : type.getDeclaredFields()){
                if(field.getName().startsWith("id")){
                    continue;
                }
                field.setAccessible(true);
                insertStatement.setObject(i, field.get(toInsert));
                i++;
            }
            insertStatement.executeUpdate();

        } catch (SQLException | IllegalAccessException e) {
            System.out.println("AbstractDAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
    }

    public void update(T toUpdate){
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement insertStatement = null;
        try {
            String query = createUpdateQuery(toUpdate);
            insertStatement = dbConnection.prepareStatement(query);
            int i = 1;
            Object fieldId = null;
            for(Field field : type.getDeclaredFields()){
                field.setAccessible(true);
                if(field.getName().startsWith("id")){
                    fieldId = field.get(toUpdate);
                    continue;
                }
                insertStatement.setObject(i, field.get(toUpdate));
                i++;
            }
            insertStatement.setObject(i, fieldId);
            insertStatement.executeUpdate();

        } catch (SQLException | IllegalAccessException e) {
            System.out.println("AbstractDAO:update " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
    }

    public void delete(int id){
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement deleteStatement = null;
        try {
            String query = createDeleteQuery("id" + type.getSimpleName());
            deleteStatement = dbConnection.prepareStatement(query);
            deleteStatement.setInt(1, id);
            deleteStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("AbstractDAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(dbConnection);
        }
    }

    public List<T> listing(){
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement listStatement = null;
        ResultSet resultSet = null;
        String query = createListingQuery();
        try{
            dbConnection = ConnectionFactory.getConnection();
            listStatement = dbConnection.prepareStatement(query);
            resultSet = listStatement.executeQuery();

            return createObjects(resultSet);

        } catch (SQLException e) {
            System.out.println("AbstractDAO:listing " + e.getMessage());
        }finally {
            ConnectionFactory.close(listStatement);
            ConnectionFactory.close(dbConnection);
        }
        return null;
    }

    private String createSelectQuery(String field){
        //"SELECT * FROM customer where idCustomer = ?"
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM `");
        sb.append(type.getSimpleName().toLowerCase());
        sb.append("` WHERE " + field + "=?");
        return sb.toString();
    }

    private String createInsertQuery(){
        // "INSERT INTO customer (name, email) VALUES (?,?)";
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO `");
        sb.append(type.getSimpleName().toLowerCase());
        sb.append("` (");
        String prefix ="";
        for(Field field : type.getDeclaredFields()){
            if(field.getName().startsWith("id")){
                continue;
            }
            sb.append(prefix);
            prefix = ",";
            sb.append(field.getName());
        }
        sb.append(")");
        sb.append(" VALUES (");
        prefix = "";
        for(Field field : type.getDeclaredFields()){
            if(field.getName().startsWith("id")){
                continue;
            }
            sb.append(prefix);
            prefix = ",";
            sb.append("?");
        }
        sb.append(")");
        return sb.toString();

    }

    private String createUpdateQuery(T toUpdate){
        //"UPDATE customer SET name = ?, email = ? WHERE (idCustomer = ?)";
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE `");
        sb.append(type.getSimpleName().toLowerCase());
        sb.append("` SET ");
        String prefix = "";
        Field field = null;
        for(Field f : type.getDeclaredFields()){
            if(f.getName().startsWith("id")){
                field = f;
                continue;
            }
            sb.append(prefix);
            prefix = ", ";
            sb.append(f.getName());
            sb.append(" = ?");
        }
        sb.append(" WHERE (" + field.getName() + " = ?)" );
        return sb.toString();
    }

    private String createDeleteQuery(String field){
        //DELETE FROM customer WHERE (idCustomer = ?);
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM `");
        sb.append(type.getSimpleName().toLowerCase());
        sb.append("` WHERE " + field + "=?");
        return sb.toString();
    }

    private String createListingQuery(){
        //"SELECT * FROM customer";
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM `");
        sb.append(type.getSimpleName().toLowerCase());
        sb.append("`");
        return sb.toString();
    }

    private List<T> createObjects(ResultSet resultSet){
        List<T> list = new ArrayList<T>();

        try{
            while(resultSet.next()){
                T instance = type.newInstance();
                for(Field field : type.getDeclaredFields()){
                    Object value = resultSet.getObject(field.getName());
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        }catch (InstantiationException | IllegalAccessException | InvocationTargetException | SQLException | IntrospectionException e){
            System.out.println("AbstractDAO createObjects " + e.getMessage());
        }

        return list;
    }

}
