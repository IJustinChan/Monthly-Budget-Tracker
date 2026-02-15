package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestIncome {
    private Income job;
    private Income scholarship;

    @BeforeEach
    void runBefore() {
        job = new Income(1000, 1, "Work", 0.1);
        scholarship = new Income(2500, 15, "Scholarship", 0);
    }

    @Test
    void testConstructor() {
        assertEquals(1000, job.getOriginalAmount());
        assertEquals("Work", job.getSource());
        assertEquals(0.1, job.getTax());
        assertEquals(900, job.getAmount());
        assertEquals(1, job.getDay());
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

    @Test
    void testAddIncomeDescription() {
        scholarship.setDescription("Won this scholarship from X by doing Y");
        assertEquals("Won this scholarship from X by doing Y", scholarship.getDescription());
    }
    
}



