package persistance;

import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.Budget;
import model.MonthlyBudget;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// code for this class is referenced from JsonSerializationDemo code from edX
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

@ExcludeFromJacocoGeneratedReport
public class JsonReaderTest extends JsonTest {
    
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/fileDoesNotExistAnywhere.json");
        try {
            Budget budget = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyBudget() {
        JsonReader reader = new JsonReader("./data/testReaderBudgetEmpty.json");
        try {
            Budget budget = reader.read();
            assertEquals(1, budget.getMonth());
            assertEquals(2026, budget.getYear());
            assertFalse(budget.getIsInScenarioMode());
            checkMonthlyBudget(1, 2026, 0, 0, budget.getCurrentMonthBudget());
            checkMonthlyBudget(1, 2026, 0, 0, budget.getScenarioAddOns());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralBudget() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralBudget.json");
        try {
            Budget budget = reader.read();
            assertEquals(1, budget.getMonth());
            assertEquals(2026, budget.getYear());
            MonthlyBudget currentMonthlyBudget = budget.getCurrentMonthBudget();
            checkMonthlyBudget(1, 2026, 2, 1, currentMonthlyBudget);
            checkIncome(100, 3, "work", 0.1, currentMonthlyBudget.getAllIncome().get(0));
            checkIncome(1000, 20, "scholarship", 0, currentMonthlyBudget.getAllIncome().get(1));
            checkExpense(50, 25, "food", "need", currentMonthlyBudget.getAllExpenses().get(0));
            MonthlyBudget scenarioAddOns = budget.getScenarioAddOns();
            checkMonthlyBudget(1, 2026, 1, 1, scenarioAddOns);
            checkIncome(2500, 27, "scholarship", 0, scenarioAddOns.getAllIncome().get(0));
            checkExpense(1400, 1, "rent", "need", scenarioAddOns.getAllExpenses().get(0));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
