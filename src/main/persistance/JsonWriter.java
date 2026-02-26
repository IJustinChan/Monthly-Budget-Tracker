package persistance;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

import model.Budget;

// code for this class is referenced from JsonSerializationDemo code from edX
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

// A writer that writes JSON version of budget to a file
public class JsonWriter {
    
    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: opens writer 
    // throws FileNotFoundException if destination file cannot be opened for writing
    public void open() throws FileNotFoundException {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of Budget to file
    public void write(Budget b) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        // stub
    }

}
