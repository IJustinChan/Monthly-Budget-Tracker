package ui;

import model.Budget;
import model.Expense;
import model.Income;
import persistance.JsonReader;
import persistance.JsonWriter;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

import javax.swing.*;

@ExcludeFromJacocoGeneratedReport
public class BudgetAppGUI extends JFrame {
    private static final int WIDTH = 700;
	private static final int HEIGHT = 500;
    private Budget budget;

    public BudgetAppGUI() {
        budget = new Budget();

        setTitle("Monthly Budget Tracker");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // change color of background
        // getContentPane().setBackground(new Color(50, 100, 170));

        // setResizable(true); // not necessary

        setVisible(true);
    }

    // starts the application
	public static void main(String[] args) {
		new BudgetAppGUI();
	}

}
