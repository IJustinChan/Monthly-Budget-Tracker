package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestMonthlyBudget {
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

        rent = new Expense(2500, 11, 1, 2025, "Rent", "Need");
        netflixSubscription = new Expense(10, 3, 20, 2024, "Entertainment", "Want");
        randomSubscription = new Expense(15, 7, 16, 2024, "Entertainment", "Want");
        bigExpense = new Expense(10000, 2, 1, 2024, "Transportation", "Need");
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
    void testGetSameSourceIncome() {
        monthBudget.addIncome(job);
        monthBudget.addIncome(scholarship);
        monthBudget.addIncome(scholarship2);

        assertEquals(0, monthBudget.getSameSourceIncome("Student loan").size());

        assertEquals(1, monthBudget.getSameSourceIncome("Work").size());
        assertEquals(1000, monthBudget.getSameSourceIncome("Work").get(0).getOriginalAmount());

        assertEquals(2, monthBudget.getSameSourceIncome("Scholarship").size());
        assertEquals(2500, monthBudget.getSameSourceIncome("Scholarship").get(0).getOriginalAmount());
        assertEquals(1500, monthBudget.getSameSourceIncome("Scholarship").get(1).getOriginalAmount());
    }

    @Test
    void testGetSameCategoryExpense() {
        monthBudget.addExpense(rent);
        monthBudget.addExpense(netflixSubscription);
        monthBudget.addExpense(randomSubscription);
        monthBudget.addExpense(bigExpense);

        assertEquals(0, monthBudget.getSameCategoryExpense("Food").size());

        assertEquals(1, monthBudget.getSameCategoryExpense("Rent").size());
        assertEquals(2500, monthBudget.getSameCategoryExpense("Rent").get(0).getAmount());

        assertEquals(2, monthBudget.getSameCategoryExpense("Entertainment").size());
        assertEquals(10, monthBudget.getSameCategoryExpense("Entertainment").get(0).getAmount());
        assertEquals(15, monthBudget.getSameCategoryExpense("Entertainment").get(1).getAmount());
        
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
        monthBudget.addIncome(job);
        monthBudget.addIncome(scholarship);

        monthBudget.removeIncome(1, 1000, "Work");
        assertEquals(1, monthBudget.getAllIncome().size());
        assertEquals(2500, monthBudget.getAllIncome().get(0).getOriginalAmount());
    }

    @Test
    void testRemoveExpense() {
        monthBudget.addExpense(rent);
        monthBudget.addExpense(netflixSubscription);

        monthBudget.removeExpense(20, 10, "Entertainment");
        assertEquals(1, monthBudget.getAllExpenses().size());
        assertEquals(2500, monthBudget.getAllExpenses().get(0).getAmount());
    }
    
}

