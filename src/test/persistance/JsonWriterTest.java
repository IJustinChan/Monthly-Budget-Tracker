package persistance;

import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.Budget;
import model.Expense;
import model.Income;
import model.MonthlyBudget;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// code for this class is referenced from JsonSerializationDemo code from edX
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

@ExcludeFromJacocoGeneratedReport
public class JsonWriterTest extends JsonTest {

    void setupBudget(Budget budget) {
        budget.setup(1, 2026);
        budget.getCurrentMonthBudget().addIncome(new Income(100, 3, "work", 0.1));
        budget.getCurrentMonthBudget().addIncome(new Income(1000, 20, "scholarship", 0));
        budget.getCurrentMonthBudget().addExpense(new Expense(50, 25, "food", "need"));
        budget.getScenarioAddOns().addIncome(new Income(2500, 27, "scholarship", 0));
        budget.getScenarioAddOns().addExpense(new Expense(1400, 1, "rent", "need"));
    }
    
    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            Budget budget = new Budget();
            budget.setup(1, 2026);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyBudget.json");
            writer.open();
            writer.write(budget);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyBudget.json");
            budget = reader.read();

            assertEquals(1, budget.getMonth());
            assertEquals(2026, budget.getYear());
            checkMonthlyBudget(1, 2026, 0, 0, budget.getCurrentMonthBudget());
            checkMonthlyBudget(1, 2026, 0, 0, budget.getScenarioAddOns());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            Budget budget = new Budget();
            setupBudget(budget);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralBudget.json");
            writer.open();
            writer.write(budget);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterGeneralBudget.json");
            budget = reader.read();
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
            fail("Exception should not have been thrown");
        }
    }
}


