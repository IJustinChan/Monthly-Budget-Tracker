package ui;

import model.Budget;
import model.Expense;
import model.Income;
import persistance.JsonReader;
import persistance.JsonWriter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.w3c.dom.events.MouseEvent;

@ExcludeFromJacocoGeneratedReport
public class BudgetAppGUI extends JFrame {
    private static final int WIDTH = 700;
	private static final int HEIGHT = 500;
    private Budget budget;
    private JInternalFrame controlPanel;

    public BudgetAppGUI() {
        budget = new Budget();

        setTitle("Monthly Budget Tracker");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // change color of background
        // getContentPane().setBackground(new Color(50, 100, 170));

        // setResizable(true); // not necessary

        // --- Handle the income panel ---
        JPanel incomePanel = new JPanel(new GridLayout(1, 3, 10, 10));
        JTextField incomeAmountField = new JTextField();
        JTextField incomeSourceField = new JTextField();
        JTextField incomeTaxField = new JTextField();
        JTextField incomeDayField = new JTextField();
        JButton incomeAddButton = new JButton("Add Income");

        incomePanel.add(incomeAmountField);
        incomePanel.add(incomeSourceField);
        incomePanel.add(incomeTaxField);
        incomePanel.add(incomeDayField);
        incomePanel.add(incomeAddButton);

        // --- Handle the expense panel ---
        JPanel expensePanel = new JPanel(new GridLayout(1, 3, 10, 10));
        JTextField expenseAmountField = new JTextField();
        JTextField expenseCategoryField = new JTextField();
        JTextField expenseDayField = new JTextField();
        JTextField expenseNecessityField = new JTextField();
        JButton expenseAddButton = new JButton("Add Expense");

        expensePanel.add(expenseAmountField);
        expensePanel.add(expenseCategoryField);
        expensePanel.add(expenseDayField);
        expensePanel.add(expenseNecessityField);
        expensePanel.add(expenseAddButton);

        String[] incomeColumns = {"Day", "Amount (after tax)", "Source"};
        DefaultTableModel incomeTableModel = new DefaultTableModel(incomeColumns, 0);
        JTable incomeTable = new JTable(incomeTableModel);

        JScrollPane incomeScrollPane = new JScrollPane(incomeTable);

        String[] expenseColumns = {"Day", "Amount", "Category", "Necessity Type"};
        DefaultTableModel expenseTableModel = new DefaultTableModel(expenseColumns, 0);
        JTable expenseTable = new JTable(expenseTableModel);

        JScrollPane expenseScrollPane = new JScrollPane(expenseTable);

        incomePanel.setBackground(Color.GREEN);
        add(incomePanel, BorderLayout.NORTH);
        add(expensePanel, BorderLayout.SOUTH);
        add(incomeScrollPane, BorderLayout.WEST);
        add(expenseScrollPane, BorderLayout.CENTER);

        setVisible(true);

        
    }

    // starts the application
	public static void main(String[] args) {
		new BudgetAppGUI();
	}

}
