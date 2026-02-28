package persistance;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

import model.Budget;
import model.Expense;
import model.Income;
import model.MonthlyBudget;

// code for this class is referenced from JsonSerializationDemo code from edX
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

// A reader that reads a budget from JSON data stored in a file
public class JsonReader {
    private String source;

    // creates a reader to read information from a file
    public JsonReader(String path) {
        source = path;
    }

    // EFFECTS: Reads budget from file and returns it
    // throws IOException if an error occurs reading data from file
    public Budget read() throws IOException {
        String JsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(JsonData);
        return parseBudget(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses budget from JSON object and returns it
    private Budget parseBudget(JSONObject jsonObject) {
        int month = jsonObject.getInt("month");
        int year = jsonObject.getInt("year");
        Budget budget = new Budget();
        budget.setup(month, year);

        handleCreateMonthlyBudget(budget, jsonObject);
        handleCreateScenarioMode(budget, jsonObject);

        return budget;

    }

    // MODIFIES: budget
    // EFFECTS: creates the monthly budget for the overall budget
    private void handleCreateMonthlyBudget(Budget budget, JSONObject jsonObject) {
        JSONArray currentBudgetArray = jsonObject.getJSONArray("currentMonthBudget");
        JSONObject currentMonthBudget = currentBudgetArray.getJSONObject(0);
        JSONArray allIncome = currentMonthBudget.getJSONArray("allIncome");
        JSONArray allExpenses = currentMonthBudget.getJSONArray("allExpenses");

        addAllIncome(allIncome, budget.getCurrentMonthBudget());
        addAllExpenses(allExpenses, budget.getCurrentMonthBudget());
        
    }

    // MODIFIES: budget
    // EFFECTS: creates the scenario mode to have additional incomes and expenses
    public void handleCreateScenarioMode(Budget budget, JSONObject jsonObject) {
        JSONArray scenarioAddOnsArray = jsonObject.getJSONArray("scenarioAddOns");
        JSONObject scenarioAddOns = scenarioAddOnsArray.getJSONObject(0);
        JSONArray allIncome = scenarioAddOns.getJSONArray("allIncome");
        JSONArray allExpenses = scenarioAddOns.getJSONArray("allExpenses");

        addAllIncome(allIncome, budget.getScenarioAddOns());
        addAllExpenses(allExpenses, budget.getScenarioAddOns());
    }

    // MODIFIES: budget
    // EFFECTS: parses all the incomes and adds them to the budget
    private void addAllIncome(JSONArray allIncome, MonthlyBudget monthlyBudget) {
        for (Object json : allIncome) {
            JSONObject nextIncome = (JSONObject) json;
            addIncome(monthlyBudget, nextIncome);
        }
    }

    // MODIFIES: budget
    // EFFECTS: parses all the expenses and adds them to the budget
    private void addAllExpenses(JSONArray allExpenses, MonthlyBudget monthlyBudget) {
        for (Object json : allExpenses) {
            JSONObject nextExpense = (JSONObject) json;
            addExpense(monthlyBudget, nextExpense);
        }
    }

    // MODIFIES: budget
    // EFFECTS: parses Income from JSON object and adds it to the budget
    private void addIncome(MonthlyBudget monthlyBudget, JSONObject jsonObject) {
        int amount = jsonObject.getInt("amount");
        String source = jsonObject.getString("source");
        double tax = jsonObject.getDouble("tax");
        int day = jsonObject.getInt("day");
        Income income = new Income(amount, day, source, tax);
        monthlyBudget.addIncome(income);
    }

    // MODIFIES: budget
    // EFFECTS: parses Expense from JSON object and adds it to the budget
    private void addExpense(MonthlyBudget monthlyBudget, JSONObject jsonObject) {
        int amount = jsonObject.getInt("amount");
        int day = jsonObject.getInt("day");
        String category = jsonObject.getString("category");
        String necessityType = jsonObject.getString("necessityType");
        Expense expense = new Expense(amount, day, category, necessityType);
        monthlyBudget.addExpense(expense);
    }

}
