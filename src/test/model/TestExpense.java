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
        assertEquals(11, rent.getMonth());
        assertEquals(1, rent.getDay());
        assertEquals(2025, rent.getYear());
        assertEquals("Rent", rent.getCategory());
        assertEquals("Need", rent.getNecessityType());
    }

    @Test
    void testImpactOnMonthlyTotal() {
        assertEquals(0.5, netflixSubscription.impactOnMonthlyTotal(20));
        assertEquals(0.01, netflixSubscription.impactOnMonthlyTotal(1000));
    }

}
