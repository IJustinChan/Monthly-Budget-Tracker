package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestBudget {
    private Budget budget;
    private Income job;
    private Income scholarship;
    private Income scholarship2;
    private Expense rent;
    private Expense netflixSubscription;
    private Expense randomSubscription;
    private Expense bigExpense;

    @BeforeEach
    void runBefore() {
        budget = new Budget();

        job = new Income(1000, 1, "Work", 0.1);
        scholarship = new Income(2500, 15, "Scholarship", 0);
        scholarship2 = new Income(1500, 12, "Scholarship", 0);

        rent = new Expense(2500, 1, "Rent", "Need");
        netflixSubscription = new Expense(10, 20, "Entertainment", "Want");
        randomSubscription = new Expense(15, 16, "Entertainment", "Want");
        bigExpense = new Expense(10000, 1, "Transportation", "Need");

        budget.setup(1, 2026);
    }

    @Test
    void testConstructor() {
        assertFalse(budget.getIsInScenarioMode());
    }

    @Test
    void testSetup() {
        assertEquals(1, budget.getMonth());
        assertEquals(2026, budget.getYear());

        assertEquals(1, budget.getCurrentMonthBudget().getMonth());
        assertEquals(2026, budget.getCurrentMonthBudget().getYear());

        assertEquals(1, budget.getScenarioAddOns().getMonth());
        assertEquals(2026, budget.getScenarioAddOns().getYear());
    }

    @Test
    void testGetNetIncomeWithScenario() {
        budget.getCurrentMonthBudget().addIncome(job);
        budget.getCurrentMonthBudget().addIncome(scholarship);
        budget.scenarioAddIncome(scholarship2);

        budget.getCurrentMonthBudget().addExpense(rent);
        budget.scenarioAddExpense(netflixSubscription);

        assertEquals(2390, budget.getNetIncomeWithScenario());
    }

    @Test
    void testGetTotalExpensesWithScenario() {
        budget.getCurrentMonthBudget().addExpense(rent);
        budget.scenarioAddExpense(netflixSubscription);
        assertEquals(2510, budget.getTotalExpensesWithScenario());
    }

    @Test
    void testGetTotalIncomeWithScenario() {
        budget.getCurrentMonthBudget().addIncome(job);
        budget.getCurrentMonthBudget().addIncome(scholarship);
        budget.scenarioAddIncome(scholarship2);
        assertEquals(4900, budget.getTotalIncomeWithScenario());
    }

    @Test
    void testScenarioAddIncome() {
        budget.scenarioAddIncome(scholarship2);
        assertEquals(1, budget.scenarioGetAllIncome().size());
    }

    @Test
    void testScenarioAddExpense() {
        budget.scenarioAddExpense(netflixSubscription);
        assertEquals(1, budget.scenarioGetAllExpense().size());
    }

    @Test
    void testScenarioRemoveIncome() {
        budget.scenarioAddIncome(scholarship2);
        assertEquals(1, budget.scenarioGetAllIncome().size());

        budget.scenarioRemoveIncome(12, 1500, "Scholarship");
        assertEquals(0, budget.scenarioGetAllIncome().size());
    }

    @Test
    void testScenarioRemoveExpense() {
        budget.scenarioAddExpense(netflixSubscription);
        assertEquals(1, budget.scenarioGetAllExpense().size());

        budget.scenarioRemoveExpense(20, 10, "Entertainment");
        assertEquals(0, budget.scenarioGetAllExpense().size());
    }

    @Test
    void testResetScenarioAddOns() {
        budget.scenarioAddIncome(scholarship2);
        budget.scenarioAddExpense(netflixSubscription);
        budget.scenarioAddExpense(bigExpense);

        assertEquals(1, budget.scenarioGetAllIncome().size());
        assertEquals(2, budget.scenarioGetAllExpense().size());

        budget.resetScenarioAddOns();

        assertEquals(0, budget.scenarioGetAllIncome().size());
        assertEquals(0, budget.scenarioGetAllExpense().size());
    }

    @Test
    void testSetIsInScenarioMode() {
        assertFalse(budget.getIsInScenarioMode());
        budget.setIsInScenarioMode(true);
        assertTrue(budget.getIsInScenarioMode());
    }



}
