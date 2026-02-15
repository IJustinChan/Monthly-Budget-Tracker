package model;

import java.util.ArrayList;

// Represents a person's budget for a certain month along with scenario mode
// scenario mode lets users add new incomes or expenses to experiment what will happen
// to their current monthly budget. These changes do not affect their actual monthly budget
// The main purpose of this class is to integrate scenario mode add ons with the actual month budget
public class Budget {
    private int month;
    private int year;
    private MonthlyBudget currentMonthBudget;
    private boolean isInScenarioMode;
    private MonthlyBudget scenarioAddOns;

    public Budget() {
        isInScenarioMode = false;
    }

    public void setup(int month, int year) {
        this.month = month;
        this.year = year;
        currentMonthBudget = new MonthlyBudget(month, year);
        scenarioAddOns = new MonthlyBudget(month, year);
    }

    // EFFECTS: returns the net income including the net incomes from scenario mode
    public int getNetIncomeWithScenario() {
        return currentMonthBudget.getNetIncome() + scenarioAddOns.getNetIncome();
    }

    // EFFECTS: returns the total expense amount including the expenses from scenario moade
    public int getTotalExpensesWithScenario() {
        return currentMonthBudget.getTotalExpenses() + scenarioAddOns.getTotalExpenses();
    }

    // EFFECTS: returns the total income aount including the incomes from scenario mode
    public int getTotalIncomeWithScenario() {
        return currentMonthBudget.getNetIncome() + scenarioAddOns.getNetIncome();
    }

    // EFFECTS: returns all the Income that comes from given source including incomes in scenario mode
    public ArrayList<Income> getSameSourceIncomeWithScenario(String source) {
        ArrayList<Income> scenarioIncomes = new ArrayList<>(currentMonthBudget.getSameSourceIncome(source));
        for (Income income : scenarioAddOns.getAllIncome()) {
            if (income.getSource().equals(source)) {
                scenarioIncomes.add(income);
            }
        }
        return scenarioIncomes;
    }

    // EFFECTS: returns all the Expense that belong in given category including expenses in scenario mode
    public ArrayList<Expense> getSameCategoryExpensesWithScenario(String category) {
        ArrayList<Expense> scenarioExpenses = new ArrayList<>(currentMonthBudget.getSameCategoryExpense(category));
        for (Expense expense : scenarioAddOns.getAllExpenses()) {
            if (expense.getCategory().equals(category)) {
                scenarioExpenses.add(expense);
            }
        }
        return scenarioExpenses;
    }

    // MODIFIES: this
    // EFFECTS: adds a income into the scenario mode
    public void scenarioAddIncome(Income income) {
        scenarioAddOns.addIncome(income);
    }

    // MODIFIES: this
    // EFFECTS: adds a expense into the scenario mode
    public void scenarioAddExpense(Expense expense) {
        scenarioAddOns.addExpense(expense);
    }

    // REQUIRES: 1 <= day <= 31 AND amount > 0
    // MODIFIES: this
    // EFFECTS: removes a income from the scenario mode
    public void scenarioRemoveIncome(int day, int amount, String source) {
        scenarioAddOns.removeIncome(day, amount, source);
    }

    // REQUIRES: 1 <= day <= 31 AND amount > 0
    // MODIFIES: this
    // EFFECTS: removes a expense from the scenario mode
    public void scenarioRemoveExpense(int day, int amount, String category) {
        scenarioAddOns.removeExpense(day, amount, category);
    }

    public ArrayList<Income> scenarioGetAllIncome() {
        return scenarioAddOns.getAllIncome();
    }

    public ArrayList<Expense> scenarioGetAllExpense() {
        return scenarioAddOns.getAllExpenses();
    }

    // MODIFIES: this
    // EFFECTS: removes all added incomes and expenses in the scenario version
    public void resetScenarioAddOns() {
        scenarioAddOns = new MonthlyBudget(month, year);
    }

    public MonthlyBudget getCurrentMonthBudget() {
        return currentMonthBudget;
    }

    public void setIsInScenarioMode(boolean isUsingScenarioMode) {
        isInScenarioMode = isUsingScenarioMode;
    }

    public boolean getIsInScenarioMode() {
        return isInScenarioMode;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }
    

}
