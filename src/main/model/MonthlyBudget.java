package model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import persistance.Writable;

// MonthlyBudget represents information about how a user's monthly budget appears, such as storing
// all the expenses and incomes in a given month
public class MonthlyBudget implements Writable {
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

    // MODIFIES: this
    // EFFECTS: adds a new income to the list of all incomes
    public void addIncome(Income income) {
        allIncome.add(income);
        EventLog.getInstance().logEvent(new Event(String.format("An income of amount $%s added to day %s", income.getAmount(), income.getDay())));
    }

    // MODIFIES: this
    // EFFECTS: adds a new expense to the list of all expenses
    public void addExpense(Expense expense) {
        allExpenses.add(expense);
        EventLog.getInstance().logEvent(new Event(String.format("An expense of amount $%s added to day %s", expense.getAmount(), expense.getDay())));
    }

    // REQUIRES: 1 <= day <= 31 AND amount > 0
    // MODIFIES: this
    // EFFECTS: removes a income from all incomes that matches the day, amount and source
    //          If no income matches given requirements, nothing is removed
    public void removeIncome(int day, int amount, String source) {
        int index = 0;
        for (Income income : allIncome) {
            if (income.getDay() == day && income.getOriginalAmount() == amount 
                       && income.getSource().equals(source)) {
                allIncome.remove(index);
                EventLog.getInstance().logEvent(new Event(String.format("An income of amount $%s removed from day %s", income.getAmount(), income.getDay())));
                break;
            }
            index++;
        }
    }

    // REQUIRES: 1 <= day <= 31 AND amount > 0
    // MODIFIES: this
    // EFFECTS: removes a expense from all expenses that matches the day, amount and category
    //          If no expense matches given requirements, nothing is removed
    public void removeExpense(int day, int amount, String category) {
        int index = 0;
        for (Expense expense : allExpenses) {
            if (expense.getDay() == day && expense.getAmount() == amount 
                        && expense.getCategory().equals(category)) {
                allExpenses.remove(index);
                EventLog.getInstance().logEvent(new Event(String.format("An expense of amount $%s removed day %s", expense.getAmount(), expense.getDay())));
                break;
            }
            index++;
        }
    }

    // REQUIRES: 1 <= oldDay <= 31 AND oldAmount > 0, AND 1 <= newDay <= 31 AND newAmount
    // MODIFIES: this
    // EFFECTS: updates a income from all incomes that matches the day, amount and source with its new information
    //          If no income matches given requirements, nothing is updated
    public void updateIncome(int oldDay, int oldAmount, String oldSource, int newDay, int newAmount, String newSource) {
        for (Income income : allIncome) {
            if (income.getDay() == oldDay && income.getOriginalAmount() == oldAmount 
                       && income.getSource().equals(oldSource)) {
                income.setAmount(newAmount);
                income.setDay(newDay);
                income.setSource(newSource);
                System.out.println("yep");
                break;
            }
        }
    }

    // REQUIRES: 1 <= oldDay <= 31 AND oldAmount > 0, AND 1 <= newDay <= 31 AND newAmount
    // MODIFIES: this
    // EFFECTS: updates a expense from all expenses that matches the day, amount and category with its new information
    //          If no expense matches given requirements, nothing is updated
    public void updateExpense(int oldDay, int oldAmount, String oldCategory, int newDay, 
              int newAmount, String newCategory) {
        for (Expense expense : allExpenses) {
            if (expense.getDay() == oldDay && expense.getAmount() == oldAmount 
                        && expense.getCategory().equals(oldCategory)) {
                expense.setAmount(newAmount);
                expense.setDay(newDay);
                expense.setCategory(newCategory);
                break;
            }
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

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("month", month);
        json.put("year", year);
        json.put("allIncome", incomeToJson());
        json.put("allExpenses", expenseToJson());
        return json;
    }

    // EFFECTS: returns all income in the MonthlyBudget as a JSON Array
    private JSONArray incomeToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Income income : allIncome) {
            jsonArray.put(income.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns all expense in the MonthlyBudget as a JSON Array
    private JSONArray expenseToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Expense expense : allExpenses) {
            jsonArray.put(expense.toJson());
        }

        return jsonArray;
    }

}
