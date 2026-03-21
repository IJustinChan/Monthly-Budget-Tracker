package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TableExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Table Demo");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[] columns = {"Type", "Amount", "Category"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        JTable table = new JTable(model);

        // Add some data
        model.addRow(new Object[]{"Income", 1000, "Salary"});
        model.addRow(new Object[]{"Expense", 75, "Groceries"});
        model.addRow(new Object[]{});

        frame.add(new JScrollPane(table));

        frame.setVisible(true);
        model.addRow(new Object[]{});
    }
}