package org.mycompany.app;

import org.json.JSONObject;

import java.util.HashMap;

public class Main {

    private static final String DATASET_URL = "http://assessment:8080/v1/dataset";
    private static final String RESULT_URL = "http://assessment:8080/v1/result";

    public static void main(String[] args) {
        try {

            Reader reader = new Reader();
            Calculator calculator = new Calculator();
            Transmition transmition = new Transmition();
            reader.read();
            JSONObject jsonObjects = reader.getJsonObject();
            calculator.processCalculation(jsonObjects);
            HashMap<String, Long> executionList = calculator.getExecutionTime();
            transmition.processTransmition(executionList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}