package persistance;

import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.Budget;
import model.Income;
import model.Expense;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// code for this class is referenced from JsonSerializationDemo code from edX
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

@ExcludeFromJacocoGeneratedReport
public class JsonReaderTest extends JsonTest {
    
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/fileDoesNotExistAnywhere.json");
        try {
            Budget wr = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyBudget() {
        JsonReader reader = new JsonReader("./data/testReaderBudgetEmpty.json");
        try {
            Budget wr = reader.read();
            assertEquals(1, wr.getMonth());
            assertEquals(2026, wr.getYear());
            assertFalse(wr.getIsInScenarioMode());
            checkMonthlyBudget(1, 2026, new ArrayList<Income>(), new ArrayList<Expense>(), wr.getCurrentMonthBudget());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralBudget() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralBudget.json");
        try {
            Budget wr = reader.read();
            assertEquals(1, wr.getMonth());
            assertEquals(2026, wr.getYear());
            //
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
