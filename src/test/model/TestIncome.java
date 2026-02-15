package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestIncome {
    private Income job;
    private Income scholarship;

    @BeforeEach
    void runBefore() {
        job = new Income(1000, 1, 1, 2026, "Work", 0.1);
        scholarship = new Income(2500, 5, 15, 2025, "Scholarship", 0);
    }

    @Test
    void testConstructor() {
        assertEquals(1000, job.getOriginalAmount());
        assertEquals("Work", job.getSource());
        assertEquals(0.1, job.getTax());
        assertEquals(900, job.getAmount());
        assertEquals(1, job.getMonth());
        assertEquals(1, job.getDay());
        assertEquals(2026, job.getYear());
    }

    @Test
    void testGetAmountWithTax() {
        assertEquals(900, job.getAmount());
    }

    @Test
    void testGetAmountNoTax() {
        assertEquals(2500, scholarship.getAmount());
    }

    @Test
    void testCheckIsTaxable() {
        assertTrue(job.checkIsTaxable(job.getTax()));
        assertFalse(scholarship.checkIsTaxable(scholarship.getTax()));
    }

    @Test
    void testIncomeAfterTax() {
        assertEquals(900, job.incomeAfterTax(job.getTax()));
    }
    
}



