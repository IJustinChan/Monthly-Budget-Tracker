package ui;

import java.io.FileNotFoundException;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
public class Main {
    public static void main(String[] args) {
        try {
            new BudgetApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run the application because the file is not found");
        }
        
    }
}




