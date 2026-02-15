package ui;

import model.Budget;
import model.Expense;
import model.Income;
import model.MonthlyBudget;

import java.util.Scanner;

public class BudgetApp {
    private Scanner input;
    private Budget budget;
    private String currentMenu = "Main menu";
    private boolean keepGoing = true;

    public BudgetApp() {
        budget = new Budget();
        runBudget();
    }

    private void runBudget() {
        int command;

        init();

        while (keepGoing) {
            // command = input.next();

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

    private void init() {
        input = new Scanner(System.in);

        System.out.println("Please enter the month (1-12) this budget is for: ");
        int month = input.nextInt();

        System.out.println("Please enter the year this month is in: ");
        int year = input.nextInt();

        budget.setup(month, year);

    }

    private void processMainMenu(int command) {
        if (command == 1) {
            currentMenu = "Monthly budget";
        } else if (command == 2) {
            currentMenu = "Scenario mode";
        } else if (command == 3) {
            // write the monthly budget summary
        }
        
        else {
            keepGoing = false;
        }
    }

    private void processMonthlyBudgetMenu(int command) {
        if (command == 1) {
            Income income = getNewIncome();
            budget.getCurrentMonthBudget().addIncome(income);
        } else if (command == 2) {
            Expense expense = getNewExpense();
            budget.getCurrentMonthBudget().addExpense(expense);
        } else if (command == 3) {
            System.out.println(budget.getCurrentMonthBudget().getNetIncome());
        } else if (command == 4) {
            System.out.println(budget.getCurrentMonthBudget().getTotalIncome());
        } else if (command == 5) {
            System.out.println(budget.getCurrentMonthBudget().getTotalExpenses());
        } else if (command == 6) {

        } else if (command == 7) {

        } else if (command == 8) {

        } else if (command == 9) {

        } else if (command == 10) {
            
        } else if (command == 11) {

        } else {
            currentMenu = "Main menu";
        }
    }

    private void processScenarioModeMenu(int command) {
        // stub
    }

    // displays menu of options to the user
    private void displayMainMenu() {
        System.out.println("Select one of the following actions by typing the number: ");
        System.out.println("1. Edit the monthly budget");
        System.out.println("2. Go into scenario mode");
        System.out.println("3. See monthly budget summary");
        System.out.println("4. Exit application");
    }

    private void displayMonthlyBudgetMenu() {
        System.out.println("Select one of the following actions by typing the number: ");
        System.out.println("1. Add a new income");
        System.out.println("2. Add a new expense");
        System.out.println("3. Get net income");
        System.out.println("4. Get total income");
        System.out.println("5. Get total expense");
        System.out.println("6. Get all income that come from same source");
        System.out.println("7. Get all expense that belong in same category");
        System.out.println("8. Update an income");
        System.out.println("9. Update a expense");
        System.out.println("10. Remove an income");
        System.out.println("11. Remove a expense");
        System.out.println("12. Exit to main menu");
    }

    private void displayScenarioModeMenu() {
        System.out.println("Select one of the following actions by typing the number: ");
        System.out.println("1. Add a new income scenario");
        System.out.println("2. Add a new expense scenario");
        System.out.println("3. Get scenario net income");
        System.out.println("4. Get scenario total income");
        System.out.println("5. Get scenario total expense");
        System.out.println("6. Get all income that come from same source");
        System.out.println("7. Get all expense that belong in same category");
        System.out.println("8. Update a scenario income");
        System.out.println("9. Update a scenario expense");
        System.out.println("10. Remove a scenario income");
        System.out.println("11. Remove a scenario expense");
        System.out.println("12. Exit to main menu");
    }

    public Income getNewIncome() {
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

    public Expense getNewExpense() {
        System.out.println("Enter the amount of money for the expense: ");
        int amount = input.nextInt();

        System.out.println("Enter the day the expense occurred: ");
        int day = input.nextInt();

        System.out.println("Enter the category of that expense: ");
        String source = input.next();

        return new Expense(amount, day, source, source);
    }

}
