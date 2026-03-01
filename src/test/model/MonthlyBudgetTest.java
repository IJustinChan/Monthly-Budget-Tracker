package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
public class MonthlyBudgetTest {
    private MonthlyBudget monthBudget;
    private Income job;
    private Income scholarship;
    private Income scholarship2;
    private Expense rent;
    private Expense netflixSubscription;
    private Expense randomSubscription;
    private Expense bigExpense;


    @BeforeEach
    void runBefore() {
        monthBudget = new MonthlyBudget(3, 2025);
        job = new Income(1000, 1, "Work", 0.1);
        scholarship = new Income(2500, 15, "Scholarship", 0);
        scholarship2 = new Income(1500, 12, "Scholarship", 0);

        rent = new Expense(2500, 1, "Rent", "Need");
        netflixSubscription = new Expense(10, 20, "Entertainment", "Want");
        randomSubscription = new Expense(15, 16, "Entertainment", "Want");
        bigExpense = new Expense(10000, 1, "Transportation", "Need");
    }

    @Test
    void testConstructor() {
        assertEquals(3, monthBudget.getMonth());
        assertEquals(2025, monthBudget.getYear());
        assertEquals(0, monthBudget.getAllIncome().size());
        assertEquals(0, monthBudget.getAllExpenses().size());
    }

    @Test
    void testGetNetIncome() {
        monthBudget.addIncome(job);
        monthBudget.addIncome(scholarship);
        monthBudget.addIncome(scholarship2);

        monthBudget.addExpense(rent);
        monthBudget.addExpense(netflixSubscription);
        monthBudget.addExpense(randomSubscription);
        assertEquals(2375, monthBudget.getNetIncome());

        monthBudget.addExpense(bigExpense);
        assertEquals(-7625, monthBudget.getNetIncome());
    }

    @Test
    void testGetTotalIncome() {
        monthBudget.addIncome(job);
        monthBudget.addIncome(scholarship);
        assertEquals(3400, monthBudget.getTotalIncome());

        monthBudget.addIncome(scholarship2);
        assertEquals(4900, monthBudget.getTotalIncome());
    }

    @Test
    void testGetTotalExpenses() {
        monthBudget.addExpense(rent);
        monthBudget.addExpense(netflixSubscription);
        assertEquals(2510, monthBudget.getTotalExpenses());

        monthBudget.addExpense(randomSubscription);
        assertEquals(2525, monthBudget.getTotalExpenses());
    }

    @Test
    void testAddIncome() {
        monthBudget.addIncome(job);
        assertEquals(1, monthBudget.getAllIncome().size());
        assertEquals(1000, monthBudget.getAllIncome().get(0).getOriginalAmount());

        monthBudget.addIncome(scholarship);
        assertEquals(2, monthBudget.getAllIncome().size());
        assertEquals(2500, monthBudget.getAllIncome().get(1).getOriginalAmount());

    }

    @Test
    void testAddExpense() {
        monthBudget.addExpense(rent);
        assertEquals(1, monthBudget.getAllExpenses().size());
        assertEquals(2500, monthBudget.getAllExpenses().get(0).getAmount());

        monthBudget.addExpense(netflixSubscription);
        assertEquals(2, monthBudget.getAllExpenses().size());
        assertEquals(10, monthBudget.getAllExpenses().get(1).getAmount());
    }

    @Test
    void testRemoveIncome() {
        monthBudget.removeIncome(1, 1000, "Work");
        assertEquals(0, monthBudget.getAllIncome().size());

        monthBudget.addIncome(job);
        monthBudget.addIncome(scholarship);

        monthBudget.removeIncome(1, 1000, "Work");
        assertEquals(1, monthBudget.getAllIncome().size());
        assertEquals(2500, monthBudget.getAllIncome().get(0).getOriginalAmount());

        monthBudget.removeIncome(1, 1000, "Work");
        assertEquals(1, monthBudget.getAllIncome().size());
        assertEquals(2500, monthBudget.getAllIncome().get(0).getOriginalAmount());
    }

    @Test
    void testRemoveExpense() {
        monthBudget.removeExpense(20, 10, "Entertainment");
        assertEquals(0, monthBudget.getAllExpenses().size());

        monthBudget.addExpense(rent);
        monthBudget.addExpense(netflixSubscription);

        monthBudget.removeExpense(20, 10, "Entertainment");
        assertEquals(1, monthBudget.getAllExpenses().size());
        assertEquals(2500, monthBudget.getAllExpenses().get(0).getAmount());

        monthBudget.removeExpense(20, 10, "Entertainment");
        assertEquals(1, monthBudget.getAllExpenses().size());
        assertEquals(2500, monthBudget.getAllExpenses().get(0).getAmount());
    }

    @Test
    void testUpdateIncome() {
        monthBudget.updateIncome(1, 1000, "Work", 5, 3000, "Work");
        assertEquals(0, monthBudget.getAllIncome().size());

        monthBudget.addIncome(job);
        monthBudget.addIncome(scholarship);

        monthBudget.updateIncome(1, 1000, "Work", 5, 3000, "Work");
        assertEquals(2, monthBudget.getAllIncome().size());
        assertEquals(3000, monthBudget.getAllIncome().get(0).getOriginalAmount());
        assertEquals(5, monthBudget.getAllIncome().get(0).getDay());
        assertEquals("Work", monthBudget.getAllIncome().get(0).getSource());

        monthBudget.updateIncome(12, 3333, "Work", 5, 2000, "Work");
        assertEquals(2, monthBudget.getAllIncome().size());
        assertEquals(3000, monthBudget.getAllIncome().get(0).getOriginalAmount());
        assertEquals(5, monthBudget.getAllIncome().get(0).getDay());
        assertEquals("Work", monthBudget.getAllIncome().get(0).getSource());
    }
    
    @Test
    void testUpdateExpense() {
        monthBudget.updateExpense(1, 2500, "Rent", 30, 3000, "Rent");
        assertEquals(0, monthBudget.getAllExpenses().size());

        monthBudget.addExpense(rent);
        monthBudget.addExpense(netflixSubscription);

        monthBudget.updateExpense(1, 2500, "Rent", 30, 3000, "Rent");
        assertEquals(2, monthBudget.getAllExpenses().size());
        assertEquals(3000, monthBudget.getAllExpenses().get(0).getAmount());
        assertEquals(30, monthBudget.getAllExpenses().get(0).getDay());
        assertEquals("Rent", monthBudget.getAllExpenses().get(0).getCategory());

        monthBudget.updateExpense(18, 2123, "Rent", 10, 3000, "Rent");
        assertEquals(2, monthBudget.getAllExpenses().size());
        assertEquals(3000, monthBudget.getAllExpenses().get(0).getAmount());
        assertEquals(30, monthBudget.getAllExpenses().get(0).getDay());
        assertEquals("Rent", monthBudget.getAllExpenses().get(0).getCategory());

    }

}

