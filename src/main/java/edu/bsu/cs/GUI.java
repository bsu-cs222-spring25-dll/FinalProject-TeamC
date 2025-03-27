package edu.bsu.cs;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

public class GUI extends Application {

    Stage window;
    Scene mainMenu;
    TextArea outputArea;
    private final RatesParser ratesParser = new RatesParser();
    private final DecimalFormat decimalFormat = new DecimalFormat();
    private final CurrencyConverter converter = new CurrencyConverter();
    private final JSONDataGetter dataGetter = new JSONDataGetter();


    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("Currency Converter");

        // Main Menu
        Label titleLabel = new Label("***Welcome***");
        Button displayRatesBtn = new Button("Display Exchange Rates");
        Button convertCurrencyBtn = new Button("Convert Currency");
        Button quitBtn = new Button("Quit");

        displayRatesBtn.setOnAction(e -> window.setScene(createDisplayRatesScene()));
        convertCurrencyBtn.setOnAction(e -> window.setScene(createConvertCurrencyScene()));
        quitBtn.setOnAction(e -> window.close());

        VBox mainMenuLayout = new VBox(20);
        mainMenuLayout.setPadding(new Insets(20));
        mainMenuLayout.setAlignment(Pos.CENTER);
        mainMenuLayout.getChildren().addAll(titleLabel, displayRatesBtn, convertCurrencyBtn, quitBtn);
        mainMenu = new Scene(mainMenuLayout, 400, 300);

        // Set initial scene
        window.setScene(mainMenu);
        window.show();
    }

    private Scene createDisplayRatesScene() {
        try {
            String data = dataGetter.dataGetter(APIConnection.encodedUrlString());
            outputArea = new TextArea(data);
            outputArea.setEditable(false);
            outputArea.setWrapText(true);

            Button backBtn = new Button("Back to Main Menu");
            backBtn.setOnAction(e -> window.setScene(mainMenu));

            VBox layout = new VBox(20);
            layout.setPadding(new Insets(20));
            layout.getChildren().addAll(outputArea, backBtn);

            return new Scene(layout, 600, 400);
        } catch (IOException e) {
            showAlert("Failed to fetch exchange rates");
            return mainMenu;
        }
    }

    private Scene createConvertCurrencyScene() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(10);
        grid.setHgap(10);

        // From Currency
        Label fromLabel = new Label("Convert from:");
        GridPane.setConstraints(fromLabel, 0, 0);
        TextField fromField = new TextField();
        fromField.setPromptText("e.g. USD");
        GridPane.setConstraints(fromField, 1, 0);

        // To Currency
        Label toLabel = new Label("Convert to:");
        GridPane.setConstraints(toLabel, 0, 1);
        TextField toField = new TextField();
        toField.setPromptText("e.g. EUR");
        GridPane.setConstraints(toField, 1, 1);


        Label amountLabel = new Label("Amount:");
        GridPane.setConstraints(amountLabel, 0, 2);
        TextField amountField = new TextField();
        amountField.setPromptText("e.g. 100.00");
        GridPane.setConstraints(amountField, 1, 2);


        Button convertButton = new Button("Convert");
        GridPane.setConstraints(convertButton, 1, 3);


        Label resultLabel = new Label();
        GridPane.setConstraints(resultLabel, 0, 4, 2, 1);


        Button backButton = new Button("Back to Main Menu");
        GridPane.setConstraints(backButton, 0, 5, 2, 1);
        backButton.setOnAction(e -> window.setScene(mainMenu));

        convertButton.setOnAction(e -> {
            try {
                String currencyFrom = fromField.getText().toUpperCase();
                String currencyTo = toField.getText().toUpperCase();
                float amount = Float.parseFloat(amountField.getText());

                List<Float> rateList = ratesParser.parseThroughRatesForCurrentExchangeRateList(currencyFrom, currencyTo);
                float startingAmountFloat = Float.parseFloat(String.valueOf(amount));
                decimalFormat.setMaximumFractionDigits(2);

                String result = "Converting from " + currencyFrom + " to " + currencyTo + " with " +
                        decimalFormat.format(startingAmountFloat) + " gives you " +
                        decimalFormat.format(converter.convertUsingAmount(rateList, startingAmountFloat)) +
                        " in " + currencyTo;

                resultLabel.setText(result);
            } catch (Exception ex) {
                showAlert("Invalid input. Please check your values and try again.");
            }
        });

        grid.getChildren().addAll(fromLabel, fromField, toLabel, toField, amountLabel, amountField,
                convertButton, resultLabel, backButton);

        return new Scene(grid, 500, 300);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}