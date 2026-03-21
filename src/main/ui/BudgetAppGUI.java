package ui;

import model.Budget;
import model.Expense;
import model.Income;
import persistance.JsonReader;
import persistance.JsonWriter;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

@ExcludeFromJacocoGeneratedReport
public class BudgetAppGUI extends JFrame {
    private static final int WIDTH = 1200;
	private static final int HEIGHT = 600;
    private static final String JSON_STORE = "./data/budget.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Budget budget;
    private DefaultTableModel incomeTableModel;
    private DefaultTableModel expenseTableModel;
    String imagePath = "images/budgetImage.png";

    public BudgetAppGUI() {
        budget = new Budget();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        String[] incomeColumns = {"Day", "Amount", "Amount (after tax)", "Source"};
        incomeTableModel = new DefaultTableModel(incomeColumns, 0);
        JTable incomeTable = new JTable(incomeTableModel);

        JScrollPane incomeScrollPane = new JScrollPane(incomeTable);

        String[] expenseColumns = {"Day", "Amount", "Category", "Necessity Type"};
        expenseTableModel = new DefaultTableModel(expenseColumns, 0);
        JTable expenseTable = new JTable(expenseTableModel);

        JScrollPane expenseScrollPane = new JScrollPane(expenseTable);

        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        setupApp();
        setTitle("Monthly Budget Tracker: " + getMonthName(budget.getMonth()) + " " + budget.getYear());

        // --- Handle the income panel ---
        JPanel incomePanel = new JPanel(new GridLayout(3, 2, 10, 10));
        JTextField incomeAmountField = new JTextField();
        JTextField incomeSourceField = new JTextField();
        JTextField incomeDayField = new JTextField();
        JTextField incomeTaxField = new JTextField();
        JButton incomeAddButton = new JButton("Add Income");
        JButton incomeRemoveButton = new JButton("Remove Income");
        JLabel incomeAmountText = new JLabel();
        JLabel incomeSourceText = new JLabel();
        JLabel incomeDayText = new JLabel();
        JLabel incomeTaxText = new JLabel();

        incomeAmountText.setText("Income Amount: ");
        incomeSourceText.setText("Income Source: ");
        incomeDayText.setText("Income Day: ");
        incomeTaxText.setText("Income Tax (0 if not taxed): ");

        incomePanel.add(incomeAmountText);
        incomePanel.add(incomeAmountField);
        incomePanel.add(incomeSourceText);
        incomePanel.add(incomeSourceField);
        incomePanel.add(incomeDayText);
        incomePanel.add(incomeDayField);
        incomePanel.add(incomeTaxText);
        incomePanel.add(incomeTaxField);
        incomePanel.add(incomeAddButton);
        incomePanel.add(incomeRemoveButton);

        // --- Handle the expense panel ---
        JPanel expensePanel = new JPanel(new GridLayout(3, 4, 10, 10));
        JTextField expenseAmountField = new JTextField();
        JTextField expenseCategoryField = new JTextField();
        JTextField expenseDayField = new JTextField();
        JTextField expenseNecessityField = new JTextField();
        JButton expenseAddButton = new JButton("Add Expense");
        JButton expenseRemoveButton = new JButton("Remove Expense");
        JLabel expenseAmountText = new JLabel();
        JLabel expenseCategoryText = new JLabel();
        JLabel expenseDayText = new JLabel();
        JLabel expenseNecessityText = new JLabel();

        expenseAmountText.setText("Expense Amount: ");
        expenseCategoryText.setText("Expense Category: ");
        expenseDayText.setText("Expense Day: ");
        expenseNecessityText.setText("Expense Necessity Type: ");

        expensePanel.add(expenseAmountText);
        expensePanel.add(expenseAmountField);
        expensePanel.add(expenseCategoryText);
        expensePanel.add(expenseCategoryField);
        expensePanel.add(expenseDayText);
        expensePanel.add(expenseDayField);
        expensePanel.add(expenseNecessityText);
        expensePanel.add(expenseNecessityField);
        expensePanel.add(expenseAddButton);
        expensePanel.add(expenseRemoveButton);

        // --- Action panel ---
        JPanel actionPanel = new JPanel(new GridLayout(2, 1, 10 ,10));
        ImageIcon budgetVisual = new ImageIcon(imagePath);
        JLabel budgetVisualLabel = new JLabel(budgetVisual);
        JButton saveBudget = new JButton("Save Budget");

        actionPanel.add(budgetVisualLabel);
        actionPanel.add(saveBudget);

        add(incomePanel, BorderLayout.NORTH);
        add(expensePanel, BorderLayout.SOUTH);
        add(incomeScrollPane, BorderLayout.WEST);
        add(expenseScrollPane, BorderLayout.EAST);
        add(actionPanel, BorderLayout.CENTER);

        handleSaveBudget(saveBudget);

        incomeAddButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int amount = Integer.parseInt(incomeAmountField.getText());
                String source = incomeSourceField.getText();
                int day = Integer.parseInt(incomeDayField.getText());
                double tax = Double.parseDouble(incomeTaxField.getText());
                Income income = new Income(amount, day, source, tax);
                budget.getCurrentMonthBudget().addIncome(income);
                displayNewIncome(amount, income.getAmount(), source, day);
            }
        });

        expenseAddButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int amount = Integer.parseInt(expenseAmountField.getText());
                String category = expenseCategoryField.getText();
                int day = Integer.parseInt(expenseDayField.getText());
                String necessityType = expenseNecessityField.getText();
                Expense expense = new Expense(amount, day, category, necessityType);
                budget.getCurrentMonthBudget().addExpense(expense);
                displayNewExpense(amount, category, day, necessityType);
            }
        });

        incomeRemoveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int amount = Integer.parseInt(incomeAmountField.getText());
                String source = incomeSourceField.getText();
                int day = Integer.parseInt(incomeDayField.getText());
                budget.getCurrentMonthBudget().removeIncome(day, amount, source);
                deleteRemovedIncome(amount, source, day);
            }
        });

        expenseRemoveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int amount = Integer.parseInt(expenseAmountField.getText());
                String category = expenseCategoryField.getText();
                int day = Integer.parseInt(expenseDayField.getText());
                budget.getCurrentMonthBudget().removeExpense(day, amount, category);
                deleteRemovedExpense(amount, category, day);
            }
        });

        setVisible(true);

    }

    private void setupApp() {
        String wantsLoadBudget = JOptionPane.showInputDialog(null,
					  "Type 'Y' (no quotes) to load existing budget otherwise type anything (don't leave blank)",
					  "Load budget",
					  JOptionPane.QUESTION_MESSAGE);
        
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
    private void saveBudget() throws FileNotFoundException {
        try {
            jsonWriter.open();
            jsonWriter.write(budget);
            jsonWriter.close();
            System.out.println("Saved budget for " + getMonthName(budget.getMonth()) + " " 
                    + budget.getYear() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
            throw new FileNotFoundException();
        }
    }

    // MODIFIES: this
    // EFFECTS: loads budget from file. Stops the application if budget cannot be loaded
    private void loadBudget() {
        try {
            budget = jsonReader.read();
            System.out.println("Loaded budget for " + getMonthName(budget.getMonth()) 
                    + " " + budget.getYear() + " from " + JSON_STORE);
            displayPreviousIncome();
            displayPreviousExpenses();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
            System.exit(0);
        }
    }

    private void displayNewIncome(int amount, int amountAfterTax, String source, int day) {
        incomeTableModel.addRow(new Object[]{day, amount, amountAfterTax, source});
    }

    private void displayNewExpense(int amount, String category, int day, String necessityType) {
        expenseTableModel.addRow(new Object[]{day, amount, category, necessityType});
    }

    private void deleteRemovedIncome(int amount, String source, int day) {
        for (int i = 0; i < incomeTableModel.getRowCount(); i++) {
            int tableDay = (Integer) incomeTableModel.getValueAt(i, 0);
            int tableAmount = (Integer) incomeTableModel.getValueAt(i, 1);
            String tableSource = (String) incomeTableModel.getValueAt(i, 3);
            if (tableDay == day && tableAmount == amount && tableSource.equals(source)) {
                incomeTableModel.removeRow(i);
                break;
            }
        }
    }

    private void deleteRemovedExpense(int amount, String category, int day) {
        for (int i = 0; i < expenseTableModel.getRowCount(); i++) {
            int tableDay = (Integer) expenseTableModel.getValueAt(i, 0);
            int tableAmount = (Integer) expenseTableModel.getValueAt(i, 1);
            String tableCategory = (String) expenseTableModel.getValueAt(i, 2);
            if (tableDay == day && tableAmount == amount && tableCategory.equals(category)) {
                expenseTableModel.removeRow(i);
                break;
            }
        }
    }

    private void displayPreviousIncome() {
        ArrayList<Income> previousIncomes = budget.getCurrentMonthBudget().getAllIncome();
        if (previousIncomes != null) {
            for (Income income : previousIncomes) {
                incomeTableModel.addRow(new Object[]{income.getDay(), income.getOriginalAmount(), income.getAmount(), income.getSource()});
            }
        }
    }

    private void displayPreviousExpenses() {
        ArrayList<Expense> previousExpenses = budget.getCurrentMonthBudget().getAllExpenses();
        if (previousExpenses != null) {
            for (Expense expense: previousExpenses) {
                expenseTableModel.addRow(new Object[]{expense.getDay(), expense.getAmount(), expense.getCategory(), expense.getNecessityType()});
            }
        }
    }

    private void handleSaveBudget(JButton saveBudget) {
        saveBudget.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                try {
                    saveBudget();
                    JOptionPane.showMessageDialog(null,
                    "Changes to budget has successfully been saved. Feel free to keep making changes.",
                    "Changes saved",
                    JOptionPane.PLAIN_MESSAGE);
                } catch (FileNotFoundException error) {
                    JOptionPane.showMessageDialog(null,
                        "Unable to write to file: " + JSON_STORE + ". Budget is unsaved.",
                    "Error saving budget",
                    JOptionPane.PLAIN_MESSAGE);
                }
				
            }
		});
    }

    // starts the application
	public static void main(String[] args) {
		new BudgetAppGUI();
	}

}
