package model;

import java.util.ArrayList;

// Represents a person's entire budget for several months
public class EntireBudget {
    private ArrayList<MonthlyBudget> allMonthlyBudgets;
    private MonthlyBudget currentMonthBudget;

    public EntireBudget() {
        allMonthlyBudgets = new ArrayList<>();
    }

    // will add more methods and specifications later

    public MonthlyBudget getCurrentMonthBudget() {
        return currentMonthBudget;
    }

    public void setCurrentMonthBudget(MonthlyBudget currentMonth) {
        currentMonthBudget = currentMonth;
    }
    

}
