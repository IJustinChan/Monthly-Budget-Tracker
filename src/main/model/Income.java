package model;

// Income class represents information about a person's income such as amount before and after tax, 
// tax rate, and the source of that income
public class Income extends Transaction {
    private String source;
    private double tax;
    private int amountAfterTax;
    
    public Income(int amount, int month, int day, int year, String source, double tax) {
        super(amount, month, day, year);
        this.source = source;
        this.tax = tax;
        amountAfterTax = incomeAfterTax(tax);
    }

    public String getSource() {
        return source;
    }

    // EFFECTS: returns original amount if income has not been taxed otherwise returns
    //          the income amount after tax
    public int getAmount() {
        if (!checkIsTaxable(amountAfterTax)) {
            return amount;
        } else {
            return amountAfterTax;
        }
    }

    // REQUIRES: tax >= 0
    // EFFECTS: returns true if the income is taxed otherwise false
    public boolean checkIsTaxable(double tax) {
        return false; // stub
    }

    // REQUIRES: tax >= 0
    // EFFECTS: calculates the income after tax has been applied
    //          returns original income if there is no tax
    public int incomeAfterTax(double tax) {
        return 0; // stub
    }

    public double getTaxAmount() {
        return tax;
    }

}
