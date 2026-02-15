package model;

// Transactions is an abstract class that represents a form of money in the budget (such as expense and income)
// saves the amount of money, the date (month, day, and year) the transaction was made, 
// and a description of the transaction
public abstract class Transaction {
    protected int amount;
    protected int month;
    protected int day;
    protected int year;
    protected String description;
    
    // REQUIRES: amount > 0 AND 1 <= month <= 12 AND 1 <= day <= 31 AND year > 0
    public Transaction(int amount, int month, int day, int year) {
        this.amount = amount;
        this.month = month;
        this.day = day;
        this.year = year;
        description = "";
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    // REQUIRES: 1 <= month <= 12
    public void setMonth(int month) {
        this.month = month;
    }

    // REQUIRES: 1 <= day <= 31
    public void setDay(int day) {
        this.day = day;
    }

    // REQUIRES: year > 0
    public void setYear(int year) {
        this.year = year;
    }

    public void setDescription(String info) {
        description = info;
    }

    public int getAmount() {
        return amount;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getYear() {
        return year;
    }

    public String getDescription() {
        return description;
    }

}
