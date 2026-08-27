package model;

import org.json.JSONObject;

import persistence.Writable;

// Expense class that stores information about a person's expense such as amount, date, category, 
// and whether its a need or want
public class Expense extends Transaction implements Writable {
    private String category;
    private String necessityType;
    
    // REQUIRES: amount > 0 AND 1 <= month <= 12 AND 1 <= day <= 31 AND year > 0
    //           necessityType is one of "needs" or "wants"
    public Expense(int amount, int day, String category, String necessityType) {
        super(amount, day);
        this.category = category;
        this.necessityType = necessityType;
    }

    // REQUIRES: total > 0
    // EFFECTS: returns the decimal percentage of how much this expense makes in total monthly expenses
    public double impactOnMonthlyTotal(int total) {
        return (double) getAmount() /  total;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setNecessityType(String necessityType) {
        this.necessityType = necessityType;
    }

    public String getCategory() {
        return category;
    }

    public String getNecessityType() {
        return necessityType;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("amount", amount);
        json.put("day", day);
        json.put("category", category);
        json.put("necessityType", necessityType);
        return json;
    }

}
