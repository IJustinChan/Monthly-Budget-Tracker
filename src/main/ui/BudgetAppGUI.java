package ui;

import model.Budget;
import model.EventLog;
import model.Expense;
import model.Income;
import model.Event;
import persistance.JsonReader;
import persistance.JsonWriter;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/*
Citations

Image for this GUI came from:
https://www.daniaaccounting.com/wp-content/uploads/2022/09/Budget.jpg?x65792

Referenced code from AlarmSystem and RobustTrafficLights
AlarmSystem: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
RobustTrafficLights: https://github.students.cs.ubc.ca/CPSC210/C3-LectureLabStarter

Referenced code and information from "Java Swing GUI Full Course" by Bro Code
https://www.youtube.com/watch?v=Kmgo00avvEw

Referenced code from this video to make tables in Swing:
https://www.youtube.com/watch?v=ccUdvsj4L0U&t=812s

*/

@ExcludeFromJacocoGeneratedReport
// BudgetAppGUI class handles making the graphical user interface and interactions of the monthly budget
public class BudgetAppGUI extends JFrame {
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 600;
    private static final String JSON_STORE = "./data/budget.json";
    private static final String imagePath = "images/budgetImage.png";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Budget budget;
    private DefaultTableModel incomeTableModel;
    private DefaultTableModel expenseTableModel;
    private JTable incomeTable;
    private JScrollPane incomeScrollPane;
    private JTable expenseTable;
    private JScrollPane expenseScrollPane;
    private JPanel incomePanel;
    private JTextField incomeAmountField;
    private JTextField incomeSourceField;
    private JTextField incomeDayField;
    private JTextField incomeTaxField;
    private JButton incomeAddButton;
    private JButton incomeRemoveButton;
    private JPanel expensePanel;
    private JTextField expenseAmountField;
    private JTextField expenseCategoryField;
    private JTextField expenseDayField;
    private JTextField expenseNecessityField;
    private JButton expenseAddButton;
    private JButton expenseRemoveButton;
    private JPanel centerPanel;
    private JButton saveBudget;

    // EFFECTS: initializes everything in the GUI and calls method to handle user input and other functionalities
    public BudgetAppGUI() {
        budget = new Budget();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        createTables();

        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        setupApp();
        setTitle("Monthly Budget Tracker: " + getMonthName(budget.getMonth()) + " " + budget.getYear());

        initializeIncomePanel();
        addInfoToIncomePanel();

        initializeExpensePanel();
        addInfoToExpensePanel();

        setupCenterPanel();

        handleSaveBudget();
        handleAddIncome();
        handleRemoveIncome();
        handleAddExpense();
        handleRemoveExpense();

        handleEventLog();

        setupFrame();
        setVisible(true);
    }

    // EFFECTS: Writes all the contents in EventLog to console after the window is closed
    private void handleEventLog() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                for (Event event : EventLog.getInstance()) {
                    System.out.println(event);
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Asks user if they want to load a existing budget or createa new budget.
    //          Handles which choice the user chooses.
    private void setupApp() {
        String wantsLoadBudget = JOptionPane.showInputDialog(null,
			"Type \"Y\" (no quotes) to load existing budget otherwise type anything (don't leave blank)", 
                "Load budget", JOptionPane.QUESTION_MESSAGE);
        
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
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        }
    }

    // MODIFIES: this
    // EFFECTS: Loads budget from file. Stops the application if budget cannot be loaded
    //          Displays the existing income and expenses to the GUI
    private void loadBudget() {
        try {
            budget = jsonReader.read();
            displayPreviousIncome();
            displayPreviousExpenses();
        } catch (IOException e) {
            System.exit(0);
        }
    }

    // REQUIRES: amount >= 0 AND amountAfterTax >= 0 AND 1 <= day <= 31
    // MODIFIES: this
    // EFFECTS: display the new income to the table in GUI
    private void displayNewIncome(int amount, int amountAfterTax, String source, int day) {
        incomeTableModel.addRow(new Object[]{day, amount, amountAfterTax, source});
    }

    // REQUIRES: amount >= 0 AND 1 <= day <= 31
    // MODIFIES: this
    // EFFECTS: display the new expense to the table in GUI
    private void displayNewExpense(int amount, String category, int day, String necessityType) {
        expenseTableModel.addRow(new Object[]{day, amount, category, necessityType});
    }

    // REQUIRES: amount >= 0 AND 1 <= day <= 31
    // MODIFIES: this
    // EFFECTS: removes the first occurence of the income row in table which matches given amount, source and day
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

    // REQUIRES: amount >= 0 AND 1 <= day <= 31
    // MODIFIES: this
    // EFFECTS: removes the first occurence of the expense row in table which matches given amount, source and day
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

    // MODIFIES: this
    // EFFECTS: displays incomes that were already in the budget when user loads their data
    private void displayPreviousIncome() {
        ArrayList<Income> previousIncomes = budget.getCurrentMonthBudget().getAllIncome();
        if (previousIncomes != null) {
            for (Income income : previousIncomes) {
                incomeTableModel.addRow(new Object[]{income.getDay(), income.getOriginalAmount(), 
                        income.getAmount(), income.getSource()});
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: displays expenses that were already in the budget when user loads their data
    private void displayPreviousExpenses() {
        ArrayList<Expense> previousExpenses = budget.getCurrentMonthBudget().getAllExpenses();
        if (previousExpenses != null) {
            for (Expense expense: previousExpenses) {
                expenseTableModel.addRow(new Object[]{expense.getDay(), expense.getAmount(), 
                        expense.getCategory(), expense.getNecessityType()});
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: saves the budget when the save button is pressed and display confirmation message
    //          if budget cannot be saved, display a error message
    private void handleSaveBudget() {
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

    // MODIFIES: this
    // EFFECTS: creates the tables for income and expenses
    private void createTables() {
        String[] incomeColumns = {"Day", "Amount", "Amount (after tax)", "Source"};
        incomeTableModel = new DefaultTableModel(incomeColumns, 0);
        incomeTable = new JTable(incomeTableModel);

        incomeScrollPane = new JScrollPane(incomeTable);

        String[] expenseColumns = {"Day", "Amount", "Category", "Necessity Type"};
        expenseTableModel = new DefaultTableModel(expenseColumns, 0);
        expenseTable = new JTable(expenseTableModel);

        expenseScrollPane = new JScrollPane(expenseTable);
    }

    // MODIFIES: this
    // EFFECTS: adds all the panels to JFrame
    private void setupFrame() {
        add(incomePanel, BorderLayout.NORTH);
        add(expensePanel, BorderLayout.SOUTH);
        add(incomeScrollPane, BorderLayout.WEST);
        add(expenseScrollPane, BorderLayout.EAST);
        add(centerPanel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: sets up all the text fields and buttons in the income panel
    private void initializeIncomePanel() {
        incomePanel = new JPanel(new GridLayout(3, 2, 10, 10));
        incomeAmountField = new JTextField();
        incomeSourceField = new JTextField();
        incomeDayField = new JTextField();
        incomeTaxField = new JTextField();
        incomeAddButton = new JButton("Add Income");
        incomeRemoveButton = new JButton("Remove Income");
    }

    // MODIFIES: this
    // EFFECTS: creates the labels and add them to the income panel
    private void addInfoToIncomePanel() {
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
    }

    // MODIFIES: this
    // EFFECTS: sets up all the text fields and buttons in the expense panel
    private void initializeExpensePanel() {
        expensePanel = new JPanel(new GridLayout(3, 4, 10, 10));
        expenseAmountField = new JTextField();
        expenseCategoryField = new JTextField();
        expenseDayField = new JTextField();
        expenseNecessityField = new JTextField();
        expenseAddButton = new JButton("Add Expense");
        expenseRemoveButton = new JButton("Remove Expense");
    }

    // MODIFIES: this
    // EFFECTS: creates the labels and add them to the expense panel
    private void addInfoToExpensePanel() {
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
    }

    // MODIFIES: this
    // EFFECTS: creates the center panel and adds the button and image to it
    private void setupCenterPanel() {
        centerPanel = new JPanel(new GridLayout(2, 1, 10,10));
        ImageIcon budgetVisual = new ImageIcon(imagePath);
        JLabel budgetVisualLabel = new JLabel(budgetVisual);
        saveBudget = new JButton("Save Budget");

        centerPanel.add(budgetVisualLabel);
        centerPanel.add(saveBudget);
    }

    // MODIFIES: this
    // EFFECTS: adds new income to budget and displays it when user inputs new income
    private void handleAddIncome() {
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
    }

    // MODIFIES: this
    // EFFECTS: removes the income from budget and from table when user inputs information about income to remove
    //          If the income does not exist, do nothing
    private void handleRemoveIncome() {
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
    }

    // MODIFIES: this
    // EFFECTS: adds new expense to budget and displays it when user inputs new expense
    private void handleAddExpense() {
        incomeRemoveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int amount = Integer.parseInt(incomeAmountField.getText());
                String source = incomeSourceField.getText();
                int day = Integer.parseInt(incomeDayField.getText());
                budget.getCurrentMonthBudget().removeIncome(day, amount, source);
                deleteRemovedIncome(amount, source, day);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: removes the expense from budget and from table when user inputs information about expense to remove
    //          If the expense does not exist, do nothing
    private void handleRemoveExpense() {
        expenseRemoveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int amount = Integer.parseInt(expenseAmountField.getText());
                String category = expenseCategoryField.getText();
                int day = Integer.parseInt(expenseDayField.getText());
                budget.getCurrentMonthBudget().removeExpense(day, amount, category);
                deleteRemovedExpense(amount, category, day);
            }
        });
    }

    // EFFECTS: starts the application
    public static void main(String[] args) {
        new BudgetAppGUI();
    }

}
