package presentation;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * View is an abstract class
 * which implements the GUI
 *
 * @author Kerekes Agnes
 * @version 1.0
 * @since 2021-06-30
 */

public class View<T> {
    private JFrame frame;
    private JPanel mainPanel;
    private JPanel tablePanel;
    private JPanel buttonPanel;

    private JTable table;

    private JScrollPane scroll;

    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton viewButton;

    private final Class<T> type;

    private List<T> data = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public View() {
        this.type = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];

        frame = new JFrame(type.getSimpleName());

        frame.setSize(500, 600);

        Field[] fields = type.getDeclaredFields();

        String[] columnNames = new String[fields.length];

        int i = 0;
        for(Field field : fields){
            columnNames[i] = field.getName();
            i++;
        }


        table = new JTable(new AbstractTableModel() {
            public String getColumnName(int col) {
                return columnNames[col].toString();
            }
            public int getRowCount() { return data.size(); }
            public int getColumnCount() { return columnNames.length; }
            public Object getValueAt(int row, int col) {
                T obj = data.get(row);
                Field f = type.getDeclaredFields()[col];
                try {
                    f.setAccessible(true);
                    return f.get(obj);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                return null;
            }
            public boolean isCellEditable(int row, int col)
            { return false; }
        });

        scroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        table.setPreferredScrollableViewportSize(new Dimension(420, 300));
        table.setFillsViewportHeight(true);

        addButton = new JButton("Add");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");
        viewButton = new JButton("View");

        tablePanel = new JPanel();
        tablePanel.add(scroll);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());

        buttonPanel.add(addButton, BorderLayout.WEST);
        buttonPanel.add(editButton, BorderLayout.CENTER);
        buttonPanel.add(deleteButton, BorderLayout.EAST);
        buttonPanel.add(viewButton, BorderLayout.NORTH);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

        frame.add(tablePanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void addButton(JButton button){
        buttonPanel.add(button, BorderLayout.SOUTH);
    }
    public void updateTable(List<T> objects){
        data = objects;
        AbstractTableModel abstractTableModel = (AbstractTableModel)table.getModel();
        abstractTableModel.fireTableDataChanged();
    }

    public void addAddButtonListener(ActionListener actionListener){
        this.addButton.addActionListener(actionListener);
    }

    public void addEditButtonListener(ActionListener actionListener){
        this.editButton.addActionListener(actionListener);
    }

    public void addDeleteButtonListener(ActionListener actionListener){
        this.deleteButton.addActionListener(actionListener);
    }

    public void addViewButtonListener(ActionListener actionListener){
        this.viewButton.addActionListener(actionListener);
    }


    public void addView(ActionListener actionListener){
        JFrame fr = new JFrame("Add");
        fr.setSize(500, 200);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));

        for(Field f : type.getDeclaredFields()){
            if(f.getName().startsWith("id")){
                continue;
            }
            JLabel label = new JLabel(f.getName());
            JTextField text = new JTextField();
            panel.add(label);
            panel.add(text);
        }

        JButton ok = new JButton("OK");
        ok.addActionListener(actionListener);

        panel.add(ok);

        fr.add(panel);
        fr.setVisible(true);

    }

    public void editView(ActionListener actionListener){
        JFrame fr = new JFrame("Edit");
        fr.setSize(500, 200);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));

        for(Field f : type.getDeclaredFields()){
            JLabel label = new JLabel(f.getName());
            JTextField text = new JTextField();
            panel.add(label);
            panel.add(text);
        }

        JButton ok = new JButton("OK");
        ok.addActionListener(actionListener);

        panel.add(ok);

        fr.add(panel);
        fr.setVisible(true);
    }

    public void deleteView(ActionListener actionListener){
        JFrame fr = new JFrame("Delete");
        fr.setSize(500, 100);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));

        for(Field f : type.getDeclaredFields()){
            if(f.getName().startsWith("id")){
                JLabel label = new JLabel(f.getName());
                JTextField text = new JTextField();
                panel.add(label);
                panel.add(text);
            }
        }

        JButton ok = new JButton("OK");
        ok.addActionListener(actionListener);

        panel.add(ok);

        fr.add(panel);
        fr.setVisible(true);
    }


}
