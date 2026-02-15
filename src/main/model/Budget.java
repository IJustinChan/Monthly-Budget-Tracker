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

    // MODIFIES: this
    // EFFECTS: removes all added incomes and expenses in the scenario version
    public void resetScenarioAddOns() {
        scenarioAddOns = new MonthlyBudget(scenarioAddOns.getMonth(), scenarioAddOns.getYear());
    }

    public MonthlyBudget getCurrentMonthBudget() {
        return currentMonthBudget;
    }

    public void setCurrentMonthBudget(MonthlyBudget currentMonth) {
        currentMonthBudget = currentMonth;
    }

    public void setInScenarioMode(boolean isUsingScenarioMode) {
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
