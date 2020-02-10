package com.epam.brest;

import com.epam.brest.calc.Calculator;
import com.epam.brest.calc.CalculatorImpl;
import com.epam.brest.files.CSVFileReader;
import com.epam.brest.files.FileReader;
import com.epam.brest.menu.CorrectValue;
import com.epam.brest.menu.EnteredValue;
import com.epam.brest.menu.ExitValue;
import com.epam.brest.menu.IncorrectValue;
import com.epam.brest.selector.PriceSelector;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Scanner;

import static com.epam.brest.menu.EnteredValue.*;

public class Main {

    private static final String QUIT_SYMBOL = "q";

    public Map<Integer, BigDecimal> kgs;
    public Map<Integer, BigDecimal> kms;

    public static void main(String[] args) throws IOException {

        Calculator calculator = new CalculatorImpl();
        PriceSelector priceSelector = new PriceSelector();
        Main main = new Main();
        main.init();

        Scanner scanner = new Scanner(System.in);
        BigDecimal weightValue, distanceValue;

        do {
            EnteredValue value = main.getValueFromConsole("Enter weight of cargo in kg or 'q' for quit", scanner);
            if (isExitValue(value)) break;
            weightValue = ((CorrectValue) value).getValue();

            value = main.getValueFromConsole("Enter distance in km or 'q' for quit", scanner);
            if (isExitValue(value)) break;
            distanceValue = ((CorrectValue) value).getValue();

            BigDecimal priceWeight = priceSelector.selectPriceValue(main.kgs, weightValue);
            BigDecimal priceDistance = priceSelector.selectPriceValue(main.kms, distanceValue);
            BigDecimal calcResult = calculator.calc(weightValue, distanceValue, priceWeight, priceDistance);
            System.out.format("RESULT: %.2f$%n", calcResult);
        } while (true);

        System.out.println("Finish!");
    }

    private void init() throws IOException {

        FileReader fileReader = new CSVFileReader();
        kgs = fileReader.readData("weight_prices.csv");
        if (kgs == null || kgs.isEmpty()) {
            throw new FileNotFoundException("File with prices per kg not found.");
        }

        kms = fileReader.readData("distance_prices.csv");
        if (kms == null || kms.isEmpty()) {
            throw new FileNotFoundException("File with prices per km not found.");
        }

    }

    private EnteredValue getValueFromConsole(String message, Scanner scanner) {
        EnteredValue result = new IncorrectValue();
        while (result.getType() == Types.INCORRECT) {
            System.out.println(message);
            result = parseInputValue(scanner.nextLine());
        }
        return result;
    }

    private EnteredValue parseInputValue(String inputValue) {
        EnteredValue result = new ExitValue();
        if (!inputValue.trim().toLowerCase().equals(QUIT_SYMBOL)) {
            try {
                BigDecimal value = new BigDecimal(inputValue);
                if (value.compareTo(BigDecimal.ZERO) > 0) {
                    result = new CorrectValue(new BigDecimal(inputValue));
                } else {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException e) {
                System.out.format("Incorrect value: %s%n", inputValue);
                result = new IncorrectValue();
            }
        }
        return result;
    }

    private static boolean isExitValue(EnteredValue value) {
        return value.getType() == Types.EXIT;
    }
}
