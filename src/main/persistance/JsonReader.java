package persistance;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

import model.Budget;

// code for this class is referenced from JsonSerializationDemo code from edX
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

// A reader that reads a budget from JSON data stored in a file
public class JsonReader {
    
    // creates a reader to read information from a file
    public JsonReader() {

    }

    // EFFECTS: Reads budget from file and returns it
    // throws IOException if an error occurs reading data from file
    public Budget read() throws IOException {
        return null; // stub
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        return ""; // stub
    }

    // EFFECTS: parses budget from JSON object and returns it
    private Budget parseBudget(JSONObject jsonObject) {
        return null;
    }

    // MODIFIES: b
    // EFFECTS: parses all the transactions from JSON object and adds them to the budget
    private void addTransactions(Budget b, JSONObject jsonObject) {
        // stub
    }

    // MODIFIES: b
    // EFFECTS: parses Income from JSON object and adds it to the budget
    private void addIncome(Budget b, JSONObject jsonObject) {

    }

    // MODIFIES: b
    // EFFECTS: parses Expense from JSON object and adds it to the budget
    private void addExpense(Budget b, JSONObject jsonObject) {

    }

}
