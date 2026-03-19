package ui;

import model.Budget;
import model.Expense;
import model.Income;
import persistance.JsonReader;
import persistance.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import java.util.Scanner;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
// Monthly budget application
public class BudgetApp {
    private Scanner input;
    private Budget budget;
    private String currentMenu = "Main menu";
    private boolean keepGoing = true;
    private static final String JSON_STORE = "./data/budget.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // Some code for this class was inspired by the TellerApp project from the project's description on edX
    // Specific parts include runBudget, init(), and handling user input
    // https://github.students.cs.ubc.ca/CPSC210/TellerApp.git

    // some code for this class is also referenced from JsonSerializationDemo code from edX
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

    // EFFECTS: runs the budget app
    public BudgetApp() throws FileNotFoundException {
        budget = new Budget();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runBudget();
    }

    // MOFIDIES: this
    // EFFECTS: gets user input and processes it
    private void runBudget() {
        int command;

        input = new Scanner(System.in);

        handleIntro();

        while (keepGoing) {

            if (currentMenu.equals("Main menu")) {
                displayMainMenu();
            } else if (currentMenu.equals("Monthly budget")) {
                displayMonthlyBudgetMenu();
            } else { // currentMenu = "Scenario mode"
                displayScenarioModeMenu();
            }

            command = input.nextInt();

            if (currentMenu.equals("Main menu")) {
                processMainMenu(command);;
            } else if (currentMenu.equals("Monthly budget")) {
                processMonthlyBudgetMenu(command);
            } else { // currentMenu = "Scenario mode"
                processScenarioModeMenu(command);
            }

        }

        System.out.println("Thanks for using this budget application!");
    }

    // MODIFIES: this
    // EFFECTS: initalizes the budget app object
    private void init() {
        System.out.println("Please enter the month (1-12) this budget is for: ");
        int month = input.nextInt();

        System.out.println("Please enter the year this month is in: ");
        int year = input.nextInt();

        budget.setup(month, year);
    }

    // REQUIRES: 1 <= command <= 5
    // MODIFIES: this
    // EFFECTS: handles the user input for when the user is on the main menu
    private void processMainMenu(int command) {
        if (command == 1) {
            currentMenu = "Monthly budget";
        } else if (command == 2) {
            currentMenu = "Scenario mode";
        } else if (command == 3) {
            displayMonthlyBudgetSummary();
        } else if (command == 4) { 
            saveBudget();
        } else {
            keepGoing = false;
        }
    }

    // REQUIRES: 1 <= command <= 10
    // MODIFIES: this
    // EFFECTS: handles user input for when the user is working on the monthly budget
    private void processMonthlyBudgetMenu(int command) {
        if (command == 1) {
            Income income = getNewIncome();
            budget.getCurrentMonthBudget().addIncome(income);
        } else if (command == 2) {
            Expense expense = getNewExpense();
            budget.getCurrentMonthBudget().addExpense(expense);
        } else if (command == 3) {
            System.out.println("Net income: " + budget.getCurrentMonthBudget().getNetIncome());
        } else if (command == 4) {
            System.out.println("Total income: " + budget.getCurrentMonthBudget().getTotalIncome());
        } else if (command == 5) {
            System.out.println("Total expenses: " + budget.getCurrentMonthBudget().getTotalExpenses());
        } else if (command == 6) {
            updateMonthlyBudgetIncome();
        } else if (command == 7) {
            updateMonthlyBudgetExpense();
        } else if (command == 8) {
            deleteMonthlyBudgetIncome();
        } else if (command == 9) {
            deleteMonthlyBudgetExpense();
        } else {
            currentMenu = "Main menu";
        }
    }

    // REQUIRES: 1 <= command <= 10
    // MODIFIES: this
    // EFFECTS: handles user input when the user is in scenario mode
    private void processScenarioModeMenu(int command) {
        if (command == 1) {
            Income income = getNewIncome();
            budget.scenarioAddIncome(income);
        } else if (command == 2) {
            Expense expense = getNewExpense();
            budget.scenarioAddExpense(expense);
        } else if (command == 3) {
            System.out.println("Net income: " + budget.getNetIncomeWithScenario());
        } else if (command == 4) {
            System.out.println("Total income: " + budget.getTotalIncomeWithScenario());
        } else if (command == 5) {
            System.out.println("Total expenses: " + budget.getTotalExpensesWithScenario());
        } else if (command == 6) {
            updateScenarioBudgetIncome();
        } else if (command == 7) {
            updateScenarioBudgetExpense();
        } else if (command == 8) {
            deleteScenarioBudgetIncome();
        } else if (command == 9) {
            deleteScenarioBudgetExpense();
        } else {
            currentMenu = "Main menu";
        }
    }

    // EFFECTS: displays main menu of options to the user
    private void displayMainMenu() {
        System.out.println("Select one of the following actions by typing the number: ");
        System.out.println("1. Edit the monthly budget");
        System.out.println("2. Go into scenario mode");
        System.out.println("3. See monthly budget summary");
        System.out.println("4. Save budget to a file");
        System.out.println("5. Exit application without saving budget changes");
    }

    // EFFECTS: displays options for the user when the user is editing the monthly budget
    private void displayMonthlyBudgetMenu() {
        System.out.println("Select one of the following actions by typing the number: ");
        System.out.println("1. Add a new income");
        System.out.println("2. Add a new expense");
        System.out.println("3. Get net income");
        System.out.println("4. Get total income");
        System.out.println("5. Get total expense");
        System.out.println("6. Update an income");
        System.out.println("7. Update a expense");
        System.out.println("8. Remove an income");
        System.out.println("9. Remove a expense");
        System.out.println("10. Exit to main menu");
    }

    // EFFECTS: displays options for the user when the user is in scenario mode
    private void displayScenarioModeMenu() {
        System.out.println("Select one of the following actions by typing the number: ");
        System.out.println("1. Add a new income scenario");
        System.out.println("2. Add a new expense scenario");
        System.out.println("3. Get scenario net income");
        System.out.println("4. Get scenario total income");
        System.out.println("5. Get scenario total expense");
        System.out.println("6. Update a scenario income");
        System.out.println("7. Update a scenario expense");
        System.out.println("8. Remove a scenario income");
        System.out.println("9. Remove a scenario expense");
        System.out.println("10. Exit to main menu");
    }

    // EFFECTS: creates a new Income object based on user input
    private Income getNewIncome() {
        System.out.println("Enter the amount of money for the income: ");
        int amount = input.nextInt();

        System.out.println("Enter the day the income was earned: ");
        int day = input.nextInt();

        System.out.println("Enter the source of that income: ");
        String source = input.next();

        System.out.println("Enter the tax rate of the income (0 if not taxed): ");
        double tax = input.nextDouble();

        return new Income(amount, day, source, tax);
    }

    // EFFECTS: creates a new Expense object based on user input
    private Expense getNewExpense() {
        System.out.println("Enter the amount of money for the expense: ");
        int amount = input.nextInt();

        System.out.println("Enter the day the expense occurred: ");
        int day = input.nextInt();

        System.out.println("Enter the category of that expense: ");
        String category = input.next();

        System.out.println("Enter if this expense is a need or want: ");
        String necessity = input.next();

        return new Expense(amount, day, category, necessity);
    }

    // EFFECTS: Writes out the contents of the income from a ArrayList containing Income
    private void writeIncomeArrayListContent(ArrayList<Income> incomeList) {
        for (Income income : incomeList) {
            System.out.println("Day " + income.getDay() + ": " + income.getSource() + " - " + "$" + income.getAmount());
        }
    }

    // EFFECTS: Writes out the contents of the expense from a ArrayList containing Expense
    private void writeExpenseArrayListContent(ArrayList<Expense> expenseList) {
        for (Expense expense : expenseList) {
            System.out.println(
                    "Day " + expense.getDay() + ": " + expense.getCategory() + " - " + "$" + expense.getAmount());
        }
    }

    // EFFECTS: Writes out all the income and expense the user has in their monthly budget
    private void displayMonthlyBudgetSummary() {
        System.out.println("Income summary: ");
        writeIncomeArrayListContent(budget.getCurrentMonthBudget().getAllIncome());
        System.out.println("Expense summary: ");
        writeExpenseArrayListContent(budget.getCurrentMonthBudget().getAllExpenses());
    }

    // MODIFIES: this
    // EFFECTS: deletes a income from the user's monthly budget
    //          no income is deleted if no income matches user specifications
    private void deleteMonthlyBudgetIncome() {
        System.out.println("Enter the amount of money for the income before tax: ");
        int amount = input.nextInt();

        System.out.println("Enter the day the income was earned: ");
        int day = input.nextInt();

        System.out.println("Enter the source of that income: ");
        String source = input.next();

        budget.getCurrentMonthBudget().removeIncome(day, amount, source);
    }

    // MODIFIES: this
    // EFFECTS: deletes a expense from the user's monthly budget
    //          no expense is deleted if no expense matches user specifications
    private void deleteMonthlyBudgetExpense() {
        System.out.println("Enter the amount of money for the expense: ");
        int amount = input.nextInt();

        System.out.println("Enter the day the expense occurred: ");
        int day = input.nextInt();

        System.out.println("Enter the category of that expense: ");
        String category = input.next();

        budget.getCurrentMonthBudget().removeExpense(day, amount, category);
    }

    // MODIFIES: this
    // EFFECTS: deletes a income from the user's scenario mode
    //          no income is deleted if no income in scenario mode matches user specifications
    private void deleteScenarioBudgetIncome() {
        System.out.println("Enter the amount of money for the income before tax: ");
        int amount = input.nextInt();

        System.out.println("Enter the day the income was earned: ");
        int day = input.nextInt();

        System.out.println("Enter the source of that income: ");
        String source = input.next();

        budget.scenarioRemoveIncome(day, amount, source);
    }

    // MODIFIES: this
    // EFFECTS: deletes a expense from the user's scenario mode
    //          no expense is deleted if no expense in scenario mode matches user specifications
    private void deleteScenarioBudgetExpense() {
        System.out.println("Enter the amount of money for the expense: ");
        int amount = input.nextInt();

        System.out.println("Enter the day the expense occurred: ");
        int day = input.nextInt();

        System.out.println("Enter the category of that expense: ");
        String category = input.next();

        budget.scenarioRemoveExpense(day, amount, category);
    }

    // MODIFIES: this
    // EFFECTS: updates a user's income from their monthly budget
    //          no income is updated if no income matches the user's specifications
    private void updateMonthlyBudgetIncome() {
        System.out.println("To update information, enter the original information then enter the new information.");
        System.out.println("Enter the original amount of money for the income before tax: ");
        int oldAmount = input.nextInt();

        System.out.println("Enter the original day the income was earned: ");
        int oldDay = input.nextInt();

        System.out.println("Enter the original source of that income: ");
        String oldSource = input.next();

        System.out.println("Enter the new amount of money for the income: ");
        int newAmount = input.nextInt();

        System.out.println("Enter the new day the income was earned: ");
        int newDay = input.nextInt();

        System.out.println("Enter the new source of that income: ");
        String newSource = input.next();

        budget.getCurrentMonthBudget().updateIncome(oldDay, oldAmount, oldSource, newDay, newAmount, newSource);

    }

    // MODIFIES: this
    // EFFECTS: updates a user's expense from their monthly budget
    //          no expense is updated if no expense matches the user's specifications
    private void updateMonthlyBudgetExpense() {
        System.out.println("To update information, enter the original information then enter the new information.");
        System.out.println("Enter the original amount of money for the expense: ");
        int oldAmount = input.nextInt();

        System.out.println("Enter the original day the expense occurred: ");
        int oldDay = input.nextInt();

        System.out.println("Enter the original category of that expense: ");
        String oldCategory = input.next();

        System.out.println("Enter the new amount of money for the expense: ");
        int newAmount = input.nextInt();

        System.out.println("Enter the new day the expense occurred: ");
        int newDay = input.nextInt();

        System.out.println("Enter the new category of that expense: ");
        String newCategory = input.next();

        budget.getCurrentMonthBudget().updateExpense(oldDay, oldAmount, oldCategory, newDay, newAmount, newCategory);

    }

    // MODIFIES: this
    // EFFECTS: updates a user's income from their scenario mode
    //          no income is updated if no income in scenario mode matches the user's specifications
    private void updateScenarioBudgetIncome() {
        System.out.println("To update information, enter the original information then enter the new information.");
        System.out.println("Enter the original amount of money for the income before tax: ");
        int oldAmount = input.nextInt();

        System.out.println("Enter the original day the income was earned: ");
        int oldDay = input.nextInt();

        System.out.println("Enter the original source of that income: ");
        String oldSource = input.next();

        System.out.println("Enter the new amount of money for the income: ");
        int newAmount = input.nextInt();

        System.out.println("Enter the new day the income was earned: ");
        int newDay = input.nextInt();

        System.out.println("Enter the new source of that income: ");
        String newSource = input.next();

        budget.getScenarioAddOns().updateIncome(oldDay, oldAmount, oldSource, newDay, newAmount, newSource);
    }

    // MODIFIES: this
    // EFFECTS: updates a user's expense from their scenario mode
    //          no expense is updated if no expense in scenario mode matches the user's specifications
    private void updateScenarioBudgetExpense() {
        System.out.println("To update information, enter the original information then enter the new information.");
        System.out.println("Enter the original amount of money for the expense: ");
        int oldAmount = input.nextInt();

        System.out.println("Enter the original day the expense occurred: ");
        int oldDay = input.nextInt();

        System.out.println("Enter the original category of that expense: ");
        String oldCategory = input.next();

        System.out.println("Enter the new amount of money for the expense: ");
        int newAmount = input.nextInt();

        System.out.println("Enter the new day the expense occurred: ");
        int newDay = input.nextInt();

        System.out.println("Enter the new category of that expense: ");
        String newCategory = input.next();

        budget.getScenarioAddOns().updateExpense(oldDay, oldAmount, oldCategory, newDay, newAmount, newCategory);
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
            keepGoing = false;
        }
    }

    // EFFECTS: Ask user if they want to load existing budget or not
    private void handleIntro() {
        System.out.println("Type 1 to load a existing budget or type 2 to create a new budget");
        int choice = input.nextInt();
        if (choice == 1) {
            loadBudget();
        } else {
            init();
        }
    }

}
