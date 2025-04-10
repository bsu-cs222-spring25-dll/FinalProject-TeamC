package edu.bsu.cs;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

public class GUI extends Application {

    private Stage window;
    private Scene mainMenu;
    private final RatesParser ratesParser = new RatesParser();
    private final DecimalFormat decimalFormat = new DecimalFormat();
    private final CurrencyConverter converter = new CurrencyConverter();



    private final Color PRIMARYECOLOR = Color.web("#3498db");
    private final Color SECONDARYECOLOR = Color.web("#2ecc71");
    private final Color BACKGROUNDECOLOR = Color.web("#f5f7fa");
    private final Color TEXTECOLOR = Color.web("#2c3e50");
    private final Color ACCENTECOLOR = Color.web("#9b59b6");

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("Currency Converter");


        Scene startMenu = createStartMenuScene();
        window.setScene(startMenu);

        window.setMinWidth(400);
        window.setMinHeight(300);

        window.show();
    }

    private Scene createStartMenuScene() {

        Label titleLabel = new Label("Currency Converter");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        titleLabel.setTextFill(PRIMARYECOLOR);


        Label subtitleLabel = new Label("Your reliable currency conversion tool");
        subtitleLabel.setFont(Font.font("Arial", 16));
        subtitleLabel.setTextFill(TEXTECOLOR);


        Button startButton = createStyledButton("Get Started", SECONDARYECOLOR);
        startButton.setMinWidth(250);
        startButton.setMinHeight(60);
        startButton.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        startButton.setOnAction(_ -> {
            if (mainMenu == null) {
                mainMenu = createMainMenuScene();
            }
            window.setScene(mainMenu);
        });


        Label versionLabel = new Label("Version 0.2.4");
        versionLabel.setFont(Font.font("Arial", 12));
        versionLabel.setTextFill(Color.web("#7f8c8d"));


        VBox layout = new VBox(30);
        layout.setPadding(new Insets(40));
        layout.setAlignment(Pos.CENTER);
        layout.setBackground(new Background(new BackgroundFill(BACKGROUNDECOLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        layout.getChildren().addAll(titleLabel, subtitleLabel, startButton, versionLabel);


        StackPane rootPane = new StackPane();
        rootPane.setBackground(new Background(new BackgroundFill(BACKGROUNDECOLOR, CornerRadii.EMPTY, Insets.EMPTY)));


        Circle decor1 = new Circle(50, ACCENTECOLOR);
        Circle decor2 = new Circle(30, PRIMARYECOLOR);
        decor1.setOpacity(0.1);
        decor2.setOpacity(0.1);

        Pane decorPane = new Pane(decor1, decor2);
        decor1.setCenterX(100);
        decor1.setCenterY(100);
        decor2.setCenterX(300);
        decor2.setCenterY(300);

        rootPane.getChildren().addAll(decorPane, layout);

        return new Scene(rootPane, 600, 500);
    }

    private Scene createMainMenuScene() {
        Label titleLabel = new Label("Currency Converter");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setTextFill(TEXTECOLOR);

        Button displayRatesBtn = createStyledButton("Display Exchange Rates", PRIMARYECOLOR);
        Button convertCurrencyBtn = createStyledButton("Convert Currency", SECONDARYECOLOR);
        Button quitBtn = createStyledButton("Quit", Color.web("#95a5a6"));

        displayRatesBtn.setOnAction(_ -> window.setScene(createDisplayRatesScene()));
        convertCurrencyBtn.setOnAction(_ -> window.setScene(createConvertCurrencyScene()));
        quitBtn.setOnAction(_ -> window.close());

        VBox mainMenuLayout = new VBox(30);
        mainMenuLayout.setPadding(new Insets(40));
        mainMenuLayout.setAlignment(Pos.CENTER);
        mainMenuLayout.setBackground(new Background(new BackgroundFill(BACKGROUNDECOLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        mainMenuLayout.getChildren().addAll(titleLabel, displayRatesBtn, convertCurrencyBtn, quitBtn);

        return new Scene(mainMenuLayout, 500, 400);
    }

    private Button createStyledButton(String text, Color color) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: " + toHex(color) + "; " +
                "-fx-text-fill: white; " +
                "-fx-font-weight: bold; " +
                "-fx-padding: 10 20; " +
                "-fx-background-radius: 5;");
        button.setMinWidth(200);
        button.setOnMouseEntered(_ -> button.setStyle("-fx-background-color: " + toHex(color.darker()) + "; " +
                "-fx-text-fill: white; " +
                "-fx-font-weight: bold; " +
                "-fx-padding: 10 20; " +
                "-fx-background-radius: 5;"));
        button.setOnMouseExited(_ -> button.setStyle("-fx-background-color: " + toHex(color) + "; " +
                "-fx-text-fill: white; " +
                "-fx-font-weight: bold; " +
                "-fx-padding: 10 20; " +
                "-fx-background-radius: 5;"));
        return button;
    }

    private String toHex(Color color) {
        return String.format("#%02x%02x%02x",
                (int)(color.getRed() * 255),
                (int)(color.getGreen() * 255),
                (int)(color.getBlue() * 255));
    }

    private Scene createDisplayRatesScene() {
        try {
            CallForRates call = new CallForRates();
            String data = call.getRatesAndNames();
            TextArea outputArea = new TextArea(data);
            outputArea.setEditable(false);
            outputArea.setWrapText(true);
            outputArea.setFont(Font.font("Monospaced", 12));
            outputArea.setStyle("-fx-control-inner-background: #ecf0f1;");

            Button backBtn = createStyledButton("Back to Main Menu", Color.web("#95a5a6"));
            backBtn.setOnAction(_ -> {
                if (mainMenu == null) {
                    mainMenu = createMainMenuScene();
                }
                window.setScene(mainMenu);
            });

            ScrollPane scrollPane = new ScrollPane(outputArea);
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true);

            VBox layout = new VBox(20);
            layout.setPadding(new Insets(20));
            layout.setBackground(new Background(new BackgroundFill(BACKGROUNDECOLOR, CornerRadii.EMPTY, Insets.EMPTY)));
            layout.getChildren().addAll(scrollPane, backBtn);

            return new Scene(layout, 700, 500);
        } catch (IOException e) {
            showAlert("Failed to fetch exchange rates");
            return mainMenu;
        }
    }

    private Scene createConvertCurrencyScene() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(30, 30, 30, 30));
        grid.setVgap(15);
        grid.setHgap(15);
        grid.setAlignment(Pos.CENTER);
        grid.setBackground(new Background(new BackgroundFill(BACKGROUNDECOLOR, CornerRadii.EMPTY, Insets.EMPTY)));


        Label titleLabel = new Label("Currency Conversion");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleLabel.setTextFill(TEXTECOLOR);
        GridPane.setConstraints(titleLabel, 0, 0, 2, 1);
        GridPane.setMargin(titleLabel, new Insets(0, 0, 20, 0));


        Label fromLabel = createStyledLabel("Convert from:");
        GridPane.setConstraints(fromLabel, 0, 1);
        TextField fromField = createStyledTextField("e.g. USD");
        GridPane.setConstraints(fromField, 1, 1);


        Label toLabel = createStyledLabel("Convert to:");
        GridPane.setConstraints(toLabel, 0, 2);
        TextField toField = createStyledTextField("e.g. EUR");
        GridPane.setConstraints(toField, 1, 2);


        Label amountLabel = createStyledLabel("Amount:");
        GridPane.setConstraints(amountLabel, 0, 3);
        TextField amountField = createStyledTextField("e.g. 100.00");
        GridPane.setConstraints(amountField, 1, 3);


        Button convertButton = createStyledButton("Convert", SECONDARYECOLOR);
        GridPane.setConstraints(convertButton, 1, 4);
        GridPane.setMargin(convertButton, new Insets(20, 0, 0, 0));


        Label resultLabel = new Label();
        resultLabel.setWrapText(true);
        resultLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        resultLabel.setTextFill(TEXTECOLOR);
        GridPane.setConstraints(resultLabel, 0, 5, 2, 1);
        GridPane.setMargin(resultLabel, new Insets(20, 0, 0, 0));


        Button backButton = createStyledButton("Back to Main Menu", Color.web("#95a5a6"));
        GridPane.setConstraints(backButton, 0, 6, 2, 1);
        GridPane.setMargin(backButton, new Insets(20, 0, 0, 0));
        backButton.setOnAction(_ -> window.setScene(mainMenu));

        convertButton.setOnAction(_ -> {
            try {
                String currencyFrom = fromField.getText().toUpperCase();
                String currencyTo = toField.getText().toUpperCase();
                float amount = Float.parseFloat(amountField.getText());

                List<Float> rateList = ratesParser.parseThroughRatesForCurrentExchangeRateList(currencyFrom, currencyTo);
                float startingAmountFloat = Float.parseFloat(String.valueOf(amount));
                decimalFormat.setMaximumFractionDigits(2);

                String result = "Converting " + decimalFormat.format(startingAmountFloat) + " " + currencyFrom +
                        " to " + currencyTo + ":\n\n" +
                        "Result: " + decimalFormat.format(converter.convertUsingAmount(rateList, startingAmountFloat)) +
                        " " + currencyTo;

                resultLabel.setText(result);
            } catch (Exception ex) {
                showAlert("Invalid input. Please check your values and try again.");
            }
        });

        grid.getChildren().addAll(titleLabel, fromLabel, fromField, toLabel, toField,
                amountLabel, amountField, convertButton, resultLabel, backButton);

        return new Scene(grid, 500, 500);
    }

    private Label createStyledLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Arial", 14));
        label.setTextFill(TEXTECOLOR);
        return label;
    }

    private TextField createStyledTextField(String prompt) {
        TextField textField = new TextField();
        textField.setPromptText(prompt);
        textField.setStyle("-fx-padding: 8; -fx-font-size: 14; -fx-background-radius: 5;");
        textField.setMaxWidth(200);
        return textField;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-background-color: " + toHex(BACKGROUNDECOLOR) + ";");
        dialogPane.setHeader(null);
        dialogPane.lookup(".content.label").setStyle("-fx-text-fill: " + toHex(TEXTECOLOR) + ";");

        ButtonBar buttonBar = (ButtonBar) dialogPane.lookup(".button-bar");
        buttonBar.setStyle("-fx-background-color: transparent;");

        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}