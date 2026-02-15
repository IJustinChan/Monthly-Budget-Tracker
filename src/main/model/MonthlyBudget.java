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
    public int getNetIncome() {
        return getTotalIncome() - getTotalExpenses();
    }

    // EFFECTS: returns the total amount of income
    public int getTotalIncome() {
        int total = 0;
        for (Income income : allIncome) {
            total += income.getAmount();
        }
        return total;
    }

    // EFFECTS: returns the total amount of expenses
    public int getTotalExpenses() {
        int total = 0;
        for (Expense expense : allExpenses) {
            total += expense.getAmount();
        }
        return total;
    }

    // EFFECTS: returns all the Income that comes from given source
    public ArrayList<Income> getSameSourceIncome(String source) {
        ArrayList<Income> sameSourceIncome = new ArrayList<>();
        for (Income income : allIncome) {
            if (income.getSource().equals(source)) {
                sameSourceIncome.add(income);
            }
        }
        return sameSourceIncome;
    }

    // EFFECTS: returns all the Expense that belong in given category
    public ArrayList<Expense> getSameCategoryExpense(String category) {
        ArrayList<Expense> sameCategoryExpense = new ArrayList<>();
        for (Expense expense : allExpenses) {
            if (expense.getCategory().equals(category)) {
                sameCategoryExpense.add(expense);
            }
        }
        return sameCategoryExpense;
    }

    // MODIFIES: this
    // EFFECTS: adds a new income to the list of all incomes
    public void addIncome(Income income) {
        allIncome.add(income);
    }
    
    // MODIFIES: this
    // EFFECTS: adds a new expense to the list of all expenses
    public void addExpense(Expense expense) {
        allExpenses.add(expense);
    }

    // REQURIES: 1 <= month <= 12 AND 1 <= day <= 31 AND year > 0, and amount > 0
    // MODIFIES: this
    // EFFECTS: removes a income from all incomes that matches the date, amount and source
    //          If no income matches given requirements, nothing is removed
    public void removeIncome(int month, int day, int year, int amount, String source) {
        int index = 0;
        for (Income income : allIncome) {
            if (income.getDay() == day  && income.getOriginalAmount() == amount 
            && income.getSource().equals(source)) {
                allIncome.remove(index);
                break;
            }
            index++;
        }
    }

    // REQURIES: 1 <= month <= 12 AND 1 <= day <= 31 AND year > 0, and amount > 0
    // MODIFIES: this
    // EFFECTS: removes a expense from all expenses that matches the date, amount and category
    //          If no expense matches given requirements, nothing is removed
    public void removeExpense(int month, int day, int year, int amount, String category) {
        int index = 0;
        for (Expense expense : allExpenses) {
            if (expense.getDay() == day && expense.getAmount() == amount 
            && expense.getCategory().equals(category)) {
                allExpenses.remove(index);
                break;
            }
            index++;
        }
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
