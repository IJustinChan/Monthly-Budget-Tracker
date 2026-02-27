package persistance;

import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.Expense;
import model.Income;
import model.MonthlyBudget;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// code for this class is referenced from JsonSerializationDemo code from edX
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

public class JsonTest {
    protected void checkMonthlyBudget(int month, int year, ArrayList<Income> allIncome, ArrayList<Expense> allExpenses,
                   MonthlyBudget monthlyBudget) {
        assertEquals(month, monthlyBudget.getMonth());
        assertEquals(year, monthlyBudget.getYear());
        assertEquals(allIncome.size(), monthlyBudget.getAllIncome().size());
        assertEquals(allExpenses.size(), monthlyBudget.getAllExpenses().size());
    }

    protected void checkExpense(int amount, int day, String category, String necessityType, Expense expense) {
        assertEquals(amount, expense.getAmount());
        assertEquals(day, expense.getDay());
        assertEquals(category, expense.getCategory());
        assertEquals(necessityType, expense.getNecessityType());
    }

    protected void checkIncome(int amount, int day, String source, double tax, Income income) {
        assertEquals(amount, income.getOriginalAmount());
        assertEquals(day, income.getDay());
        assertEquals(source, income.getSource());
        assertEquals(tax, income.getTax());
    }

}
