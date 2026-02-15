package model;

import java.util.ArrayList;

// MonthlyBudget represents information about how a user's monthly budget appears, such as storing
// all the expenses and incomes in a given month
public class MonthlyBudget {
    private int month;
    private int year;
    private ArrayList<Income> allIncome;
    private ArrayList<Expense> allExpenses;

    // REQUIRES: 1 <= month <= 12 AND year > 0
    public MonthlyBudget(int month, int year) {
        this.month = month;
        this.year = year;
        this.allIncome = new ArrayList<>();
        this.allExpenses = new ArrayList<>();
    }

    // EFFECTS: return the net income of the budget
    public double getNetIncome() {
        return -1; // stub
    }

    // EFFECTS: returns the total amount of income
    public double getTotalIncome() {
        return -1; // stub
    }

    // EFFECTS: returns the total amount of expenses
    public double getTotalExpenses() {
        return -1; // stub
    }

    // EFFECTS: returns all the Income that comes from given source
    public ArrayList<Income> getSameSourceIncome(String source) {
        return null; // stub
    }

    // EFFECTS: returns all the Expense that belong in given category
    public ArrayList<Income> getSameCategoryExpense(String category) {
        return null; // stub
    }

    // MODIFIES: this
    // EFFECTS: adds a new income to the list of all incomes
    public void addIncome(Income income) {
        // stub
    }
    
    // MODIFIES: this
    // EFFECTS: adds a new expense to the list of all expenses
    public void addExpense(Expense expense) {
        // stub
    }

    // REQURIES: 1 <= month <= 12 AND 1 <= day <= 31 AND year > 0, and amount > 0
    // MODIFIES: this
    // EFFECTS: removes a income from all incomes that matches the date, amount and source
    //          If no income matches given requirements, nothing is removed
    public void removeIncome(int month, int day, int year, int amount, String source) {
        // stub
    }

    // REQURIES: 1 <= month <= 12 AND 1 <= day <= 31 AND year > 0, and amount > 0
    // MODIFIES: this
    // EFFECTS: removes a expense from all expenses that matches the date, amount and category
    //          If no expense matches given requirements, nothing is removed
    public void removeExpense(int month, int day, int year, int amount, String category) {
        // stub
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public ArrayList<Income> getAllIncome() {
        return allIncome;
    }

    public ArrayList<Expense> getAllExpenses() {
        return allExpenses;
    }

}
