package model;

// Expense class that stores information about a person's expense such as amount, date, category, 
// and whether its a need or want
public class Expense extends Transaction {
    private String category;
    private String necessityType;
    
    // REQUIRES: amount > 0 AND 1 <= month <= 12 AND 1 <= day <= 31 AND year > 0
    //           necessityType is one of "needs" or "wants"
    public Expense(int amount, int month, int day, int year, String category, String necessityType) {
        super(amount, month, day, year);
        this.category = category;
        this.necessityType = necessityType;
    }

    // REQUIRES: total > 0
    // EFFECTS: returns the decimal percentage of how much this expense makes in total monthly expenses
    public double impactOnMonthlyTotal(int total) {
        return -1; // stub
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

}
