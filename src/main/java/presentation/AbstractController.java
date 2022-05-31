package presentation;

import bll.AbstractBLL;
import bll.BLLException;

import javax.swing.*;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

/**
 * AbtstractController is an abstract class
 * which interacts with the business logic layer
 * handles the button events
 *
 * @author Kerekes Agnes
 * @version 1.0
 * @since 2021-06-30
 */

public class AbstractController<T> {
    private View<T> view;
    private Class<T> type;
    private AbstractBLL<T> bll;

    @SuppressWarnings("unchecked")
    public AbstractController(View<T> view, AbstractBLL<T> bll){
        this.view = view;
        this.type = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.bll = bll;

        this.view.addAddButtonListener(a -> {
            this.view.addView(b -> {
                JButton button = (JButton)b.getSource();
                JPanel p = (JPanel)button.getParent();
                int i = 1;
                try {
                    T obj = type.newInstance();
                    for (Field field : type.getDeclaredFields()) {
                        JTextField text = (JTextField) p.getComponent(i);
                        i += 2;
                        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                        Method method = propertyDescriptor.getWriteMethod();
                        if(field.getType().equals(Integer.TYPE)){
                            if(field.getName().startsWith("id")){
                                method.invoke(obj,1);
                                i -= 2;
                            } else {
                                int value = Integer.parseInt(text.getText());
                                method.invoke(obj, value);
                            }
                        } else{
                            method.invoke(obj, text.getText());
                        }
                    }
                    bll.add(obj);
                    view.updateTable(bll.list());
                }catch(IntrospectionException | InstantiationException | IllegalAccessException | InvocationTargetException e){
                    e.printStackTrace();
                }catch(BLLException exception){
                    JOptionPane.showMessageDialog(null, exception.getMessage());
                }
            });
        });

        this.view.addEditButtonListener(a -> {
            this.view.editView(b->{
                JButton button = (JButton)b.getSource();
                JPanel p = (JPanel)button.getParent();
                int i = 1;
                try {
                    T obj = type.newInstance();
                    for (Field field : type.getDeclaredFields()) {
                        JTextField text = (JTextField) p.getComponent(i);
                        i += 2;
                        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                        Method method = propertyDescriptor.getWriteMethod();
                        if(field.getType().equals(Integer.TYPE)){
                            int value = Integer.parseInt(text.getText());
                            method.invoke(obj, value);
                        } else{
                            method.invoke(obj, text.getText());
                        }
                    }
                    bll.update(obj);
                    view.updateTable(bll.list());
                }catch(IntrospectionException | InstantiationException | IllegalAccessException | InvocationTargetException e){
                    e.printStackTrace();
                }
            });
        });

        this.view.addDeleteButtonListener(a -> {
            this.view.deleteView(b -> {
                JButton button = (JButton)b.getSource();
                JPanel p = (JPanel)button.getParent();
                JTextField text = (JTextField)p.getComponent(1);
                int id = Integer.parseInt(text.getText());
                bll.delete(id);
                view.updateTable(bll.list());
            });
        });

        this.view.addViewButtonListener(a -> {
            view.updateTable(bll.list());
        });
    }
}
