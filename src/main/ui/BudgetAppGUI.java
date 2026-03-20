package ui;

import model.Budget;
import model.Expense;
import model.Income;
import persistance.JsonReader;
import persistance.JsonWriter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
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
    private static final int WIDTH = 1200;
	private static final int HEIGHT = 600;
    private static final String JSON_STORE = "./data/budget.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Budget budget;

    public BudgetAppGUI() {
        budget = new Budget();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        setupApp();
        setTitle("Monthly Budget Tracker: " + getMonthName(budget.getMonth()) + " " + budget.getYear());

        // setLayout(new GridBagLayout());

        // change color of background
        // getContentPane().setBackground(new Color(50, 100, 170));

        // --- Handle the income panel ---
        JPanel incomePanel = new JPanel(new GridLayout(3, 2, 10, 10));
        JTextField incomeAmountField = new JTextField();
        JTextField incomeSourceField = new JTextField();
        JTextField incomeTaxField = new JTextField();
        JTextField incomeDayField = new JTextField();
        JButton incomeAddButton = new JButton("Add Income");
        JButton incomeRemoveButton = new JButton("Remove Income");

        incomePanel.add(incomeAmountField);
        incomePanel.add(incomeSourceField);
        incomePanel.add(incomeTaxField);
        incomePanel.add(incomeDayField);
        incomePanel.add(incomeAddButton);
        incomePanel.add(incomeRemoveButton);

        // --- Handle the expense panel ---
        // 6, 1
        JPanel expensePanel = new JPanel(new GridLayout(3, 2, 10, 10));
        JTextField expenseAmountField = new JTextField();
        JTextField expenseCategoryField = new JTextField();
        JTextField expenseDayField = new JTextField();
        JTextField expenseNecessityField = new JTextField();
        JButton expenseAddButton = new JButton("Add Expense");
        JButton expenseRemoveButton = new JButton("Remove Expense");

        expensePanel.add(expenseAmountField);
        expensePanel.add(expenseCategoryField);
        expensePanel.add(expenseDayField);
        expensePanel.add(expenseNecessityField);
        expensePanel.add(expenseAddButton);
        expensePanel.add(expenseRemoveButton);

        // --- Action panel ---
        JPanel actionPanel = new JPanel(new GridLayout(3, 1, 10 ,10));
        JButton expenseCategoryButton = new JButton("Expense category piechart");

        actionPanel.add(expenseCategoryButton);

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
        add(expenseScrollPane, BorderLayout.EAST);
        add(actionPanel, BorderLayout.CENTER);

        setVisible(true);

    }

    private void setupApp() {
        String wantsLoadBudget = JOptionPane.showInputDialog(null,
					  "Type 'Y' to load existing budget otherwise type anything (don't leave blank)",
					  "Load budget",
					  JOptionPane.QUESTION_MESSAGE);
        System.out.println(wantsLoadBudget);
        
        if (wantsLoadBudget == null) {
            System.exit(0);
        } else if (wantsLoadBudget.equals("Y")) {
            loadBudget();
        } else {
            String monthText = JOptionPane.showInputDialog(null,
					  "Please enter the month (1-12) this budget is for: ",
					  "Enter month",
					  JOptionPane.QUESTION_MESSAGE);
            int month = Integer.parseInt(monthText);

            String yearText = JOptionPane.showInputDialog(null,
                        "Please enter the year this month is in: ",
                        "Enter year",
                        JOptionPane.QUESTION_MESSAGE);
            int year = Integer.parseInt(yearText);
            
            budget.setup(month, year);
        }
    }

    // REQUIRES: 1 <= month <= 12
    // EFFECTS: returns the name of the month
    private String getMonthName(int month) {
        ArrayList<String> months = new ArrayList<>();
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");
        return months.get(month - 1);
    }

    // EFFECTS: saves the budget to file
    private void saveBudget() {
        try {
            jsonWriter.open();
            jsonWriter.write(budget);
            jsonWriter.close();
            System.out.println("Saved budget for " + getMonthName(budget.getMonth()) + " " 
                    + budget.getYear() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads budget from file. Stops the application if budget cannot be loaded
    private void loadBudget() {
        try {
            budget = jsonReader.read();
            System.out.println("Loaded budget for " + getMonthName(budget.getMonth()) 
                    + " " + budget.getYear() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
            System.exit(0);
            // keepGoing = false;
        }
    }

    // starts the application
	public static void main(String[] args) {
		new BudgetAppGUI();
	}

}
