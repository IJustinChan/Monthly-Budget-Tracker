package model;

import org.json.JSONObject;

import persistance.Writable;

// Income class represents information about a person's income such as amount before and after tax, 
// tax rate, and the source of that income
public class Income extends Transaction implements Writable {
    private String source;
    private double tax;
    private int amountAfterTax;
    
    // EFFECTS: creates a constructor for income by setting up the amount, day, source, tax, and amountAfterTax
    public Income(int amount, int day, String source, double tax) {
        super(amount, day);
        this.source = source;
        this.tax = tax;
        amountAfterTax = incomeAfterTax(tax);
    }

    // EFFECTS: returns original amount if income has not been taxed otherwise returns
    //          the income amount after tax
    public int getAmount() {
        if (tax == 0) {
            return amount;
        } else {
            return amountAfterTax;
        }
    }

    // REQUIRES: tax >= 0
    // EFFECTS: calculates the income after tax has been applied
    //          returns original income if there is no tax
    public int incomeAfterTax(double tax) {
        int taxAmount = (int) ((int) getOriginalAmount() * tax);
        return getOriginalAmount() - taxAmount;
    }

    // REQUIRES: newAmount > 0
    // MODIFIES: this
    // EFfECTS: sets the current amount to the new amount. Updates the amountAfterTax as well
    public void setAmount(int newAmount) {
        amount = newAmount;
        amountAfterTax = incomeAfterTax(tax);
    }

    public void setSource(String newSource) {
        source = newSource;
    }

    public String getSource() {
        return source;
    }

    public double getTax() {
        return tax;
    }

    public int getOriginalAmount() {
        return amount;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("amount", amount);
        json.put("source", source);
        json.put("tax", tax);
        json.put("day", day);
        return json;
    }


}
