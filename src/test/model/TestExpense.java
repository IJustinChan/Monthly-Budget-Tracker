package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestExpense {
    private Expense rent;
    private Expense netflixSubscription;

    @BeforeEach
    void runBefore() {
        rent = new Expense(2500, 11, 1, 2025, "Rent", "Need");
        netflixSubscription = new Expense(10, 3, 20, 2024, "Entertainment", "Want");
    }

    @Test
    void testContructor() {
        assertEquals(2500, rent.getAmount());
        assertEquals(1, rent.getDay());
        assertEquals("Rent", rent.getCategory());
        assertEquals("Need", rent.getNecessityType());
    }

    @Test
    void testImpactOnMonthlyTotal() {
        // impactOnMonthlyTotal() returns a double so we need to add a delta (tolerance) to allow
        // close enough decimal approximations to pass the test
        assertEquals(0.5, netflixSubscription.impactOnMonthlyTotal(20), 0.01);
        assertEquals(0.01, netflixSubscription.impactOnMonthlyTotal(1000), 0.01);
        assertEquals(0.03, netflixSubscription.impactOnMonthlyTotal(333), 0.01);
    }

}
