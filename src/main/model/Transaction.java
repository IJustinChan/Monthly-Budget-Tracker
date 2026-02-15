package model;

// Transactions is an abstract class that represents a form of money in the budget (such as expense and income)
// saves the amount of money, the date (month, day, and year) the transaction was made, 
// and a description of the transaction
public abstract class Transaction {
    protected int amount;
    protected int day;
    protected String description;
    
    // REQUIRES: amount > 0 AND 1 <= day <= 31
    public Transaction(int amount, int day) {
        this.amount = amount;
        this.day = day;
        description = "";
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    // REQUIRES: 1 <= day <= 31
    public void setDay(int day) {
        this.day = day;
    }

    public void setDescription(String info) {
        description = info;
    }

    public int getAmount() {
        return amount;
    }

    public int getDay() {
        return day;
    }

    public String getDescription() {
        return description;
    }

}
