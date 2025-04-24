package edu.bsu.cs;

import com.jayway.jsonpath.JsonPath;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.animation.PauseTransition;
import javafx.stage.Modality;
import javafx.util.Duration;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GUI extends Application {

    private Stage window;
    private Scene mainMenu;
    private final RatesParser ratesParser = new RatesParser();
    private final DecimalFormat decimalFormat = new DecimalFormat();
    private final CurrencyConverter converter = new CurrencyConverter();



    private final Color PRIMARYCOLOR = Color.web("#ff006e");
    private final Color SECONDARYCOLOR = Color.web("#fb5607");
    private final Color TERTIARYCOLOR = Color.web("#8338ec");
    private final Color BACKGROUNDCOLOR = Color.web("#355c7d");
    private final Color TEXTCOLOR = Color.web("#fffff0");
    private final Color ACCENTCOLOR = Color.web("#9b59b6");

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
        titleLabel.setTextFill(TEXTCOLOR);


        Label subtitleLabel = new Label("Your reliable currency conversion tool");
        subtitleLabel.setFont(Font.font("Arial", 16));
        subtitleLabel.setTextFill(TEXTCOLOR);


        Button startButton = createStyledButton("Get Started", SECONDARYCOLOR);
        startButton.setMinWidth(250);
        startButton.setMinHeight(60);
        startButton.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        startButton.setOnAction(_ -> {
            if (mainMenu == null) {
                mainMenu = createMainMenuScene();
            }
            window.setScene(mainMenu);
        });


        Label versionLabel = new Label("Version 0.3.1");
        versionLabel.setFont(Font.font("Arial", 12));
        versionLabel.setTextFill(Color.web("#7f8c8d"));


        VBox layout = new VBox(30);
        layout.setPadding(new Insets(40));
        layout.setAlignment(Pos.CENTER);
        layout.setBackground(new Background(new BackgroundFill(BACKGROUNDCOLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        layout.getChildren().addAll(titleLabel, subtitleLabel, startButton, versionLabel);


        StackPane rootPane = new StackPane();
        rootPane.setBackground(new Background(new BackgroundFill(BACKGROUNDCOLOR, CornerRadii.EMPTY, Insets.EMPTY)));


        Circle decor1 = new Circle(50, ACCENTCOLOR);
        Circle decor2 = new Circle(30, PRIMARYCOLOR);
        decor1.setOpacity(0.1);
        decor2.setOpacity(0.1);

        Pane decorPane = new Pane(decor1, decor2);
        decor1.setCenterX(100);
        decor1.setCenterY(100);
        decor2.setCenterX(300);
        decor2.setCenterY(300);

        rootPane.getChildren().addAll(decorPane, layout);

        return new Scene(rootPane, 700, 650);
    }

    private Scene createMainMenuScene() {
        Label titleLabel = new Label("Currency Converter");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setTextFill(TEXTCOLOR);

        Button displayRatesBtn = createStyledButton("Display Exchange Rates", PRIMARYCOLOR);
        Button convertCurrencyBtn = createStyledButton("Convert Currency", SECONDARYCOLOR);
        Button walletBtn = createStyledButton("Wallet", TERTIARYCOLOR);
        Button quitBtn = createStyledButton("Quit", Color.web("#95a5a6"));

        displayRatesBtn.setOnAction(_ -> window.setScene(createDisplayRatesScene()));
        convertCurrencyBtn.setOnAction(_ -> window.setScene(createConvertCurrencyScene()));
        walletBtn.setOnAction(_ -> window.setScene(createWalletScene()));
        quitBtn.setOnAction(_ -> window.close());

        VBox mainMenuLayout = new VBox(30);
        mainMenuLayout.setPadding(new Insets(40));
        mainMenuLayout.setAlignment(Pos.CENTER);
        mainMenuLayout.setBackground(new Background(new BackgroundFill(BACKGROUNDCOLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        mainMenuLayout.getChildren().addAll(titleLabel,
                displayRatesBtn,
                convertCurrencyBtn,
                walletBtn,
                quitBtn);

        return new Scene(mainMenuLayout, 700, 650);
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
            layout.setBackground(new Background(new BackgroundFill(BACKGROUNDCOLOR, CornerRadii.EMPTY, Insets.EMPTY)));
            layout.getChildren().addAll(scrollPane, backBtn);

            return new Scene(layout, 700, 650);
        } catch (IOException e) {
            showAlert("Failed to fetch exchange rates");
            return mainMenu;
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    private Scene createConvertCurrencyScene() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(30, 30, 30, 30));
        grid.setVgap(15);
        grid.setHgap(15);
        grid.setAlignment(Pos.CENTER);
        grid.setBackground(new Background(new BackgroundFill(BACKGROUNDCOLOR, CornerRadii.EMPTY, Insets.EMPTY)));

        Label titleLabel = new Label("Currency Conversion");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleLabel.setTextFill(TEXTCOLOR);
        GridPane.setConstraints(titleLabel, 0, 0, 2, 1);
        GridPane.setMargin(titleLabel, new Insets(0, 0, 20, 0));

        List<String> currencyList;
        try {
            CallForRates call = new CallForRates();
            String data = call.getRatesAndNames();
            currencyList = ratesParser.parseAvailableCurrencies(data);
        } catch (IOException e) {
            currencyList = List.of("USD", "EUR", "GBP", "JPY", "CAD", "AUD", "CNY", "INR", "MXN");
            showAlert("Couldn't fetch latest currencies, using default list");
        }

        ComboBox<String> fromComboBox = createEditableCurrencyComboBox(currencyList);
        ComboBox<String> toComboBox = createEditableCurrencyComboBox(currencyList);

        Label fromLabel = createStyledLabel("Convert from:");
        GridPane.setConstraints(fromLabel, 0, 1);
        GridPane.setConstraints(fromComboBox, 1, 1);

        Label toLabel = createStyledLabel("Convert to:");
        GridPane.setConstraints(toLabel, 0, 2);
        GridPane.setConstraints(toComboBox, 1, 2);

        Label amountLabel = createStyledLabel("Amount:");
        GridPane.setConstraints(amountLabel, 0, 3);
        TextField amountField = createStyledTextField();
        GridPane.setConstraints(amountField, 1, 3);

        Button convertButton = createStyledButton("Convert", SECONDARYCOLOR);
        GridPane.setConstraints(convertButton, 1, 4);
        GridPane.setMargin(convertButton, new Insets(20, 0, 0, 0));

        Label resultLabel = new Label();
        resultLabel.setWrapText(true);
        resultLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        resultLabel.setTextFill(TEXTCOLOR);
        GridPane.setConstraints(resultLabel, 0, 5, 2, 1);
        GridPane.setMargin(resultLabel, new Insets(20, 0, 0, 0));

        Button backButton = createStyledButton("Back to Main Menu", Color.web("#95a5a6"));
        GridPane.setConstraints(backButton, 0, 6, 2, 1);
        GridPane.setMargin(backButton, new Insets(20, 0, 0, 0));
        backButton.setOnAction(_ -> window.setScene(mainMenu));

        convertButton.setOnAction(_ -> {
            try {
                String currencyFrom = fromComboBox.getValue();
                String currencyTo = toComboBox.getValue();
                double amount = Double.parseDouble(amountField.getText());

                if (currencyFrom == null || currencyTo == null) {
                    showAlert("Please select both currencies");
                    return;
                }

                String ratesData = new CallForRates().getRatesAndNames();
                Map<String, Object> ratesMap = JsonPath.read(ratesData, "$.rates");

                double fromRate = ((Number)ratesMap.get(currencyFrom)).doubleValue();
                double toRate = ((Number)ratesMap.get(currencyTo)).doubleValue();

                double convertedAmount = amount * (toRate / fromRate);
                decimalFormat.setMaximumFractionDigits(2);

                resultLabel.setText(String.format(
                        "%.2f %s = %.2f %s",
                        amount, currencyFrom,
                        convertedAmount, currencyTo
                ));

            } catch (Exception ex) {
                showAlert("Conversion failed: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        grid.getChildren().addAll(titleLabel, fromLabel, fromComboBox, toLabel, toComboBox,
                amountLabel, amountField, convertButton, resultLabel, backButton);

        return new Scene(grid, 700, 650);
    }

    private ComboBox<String> createEditableCurrencyComboBox(List<String> currencies) {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setEditable(true);
        comboBox.getItems().addAll(currencies);
        comboBox.setPromptText("Select or type currency");
        comboBox.setMaxWidth(200);
        comboBox.setStyle("-fx-font-size: 14; -fx-background-radius: 5;");

        ObservableList<String> originalItems = FXCollections.observableArrayList(currencies);

        comboBox.getEditor().textProperty().addListener((_, _, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                comboBox.getItems().setAll(originalItems);
                comboBox.show();
            } else {
                List<String> filtered = originalItems.stream()
                        .filter(c -> c.toUpperCase().startsWith(newValue.toUpperCase()))
                        .collect(Collectors.toList());

                comboBox.getItems().setAll(filtered);
                comboBox.show();
            }
        });

        comboBox.setOnHidden(_ -> comboBox.getItems().setAll(originalItems));

        comboBox.getEditor().textProperty().addListener((_, _, newValue) -> {
            if (!newValue.toUpperCase().equals(newValue)) {
                comboBox.getEditor().setText(newValue.toUpperCase());
            }
        });

        return comboBox;
    }

    private Label createStyledLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Arial", 14));
        label.setTextFill(TEXTCOLOR);
        return label;
    }


    private TextField createStyledTextField() {
        TextField textField = new TextField();
        textField.setPromptText(" ");
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
        dialogPane.setStyle("-fx-background-color: " + toHex(BACKGROUNDCOLOR) + ";");
        dialogPane.setHeader(null);
        dialogPane.lookup(".content.label").setStyle("-fx-text-fill: " + toHex(TEXTCOLOR) + ";");

        ButtonBar buttonBar = (ButtonBar) dialogPane.lookup(".button-bar");
        buttonBar.setStyle("-fx-background-color: transparent;");

        alert.showAndWait();
    }


    private Scene createWalletScene() {
        VBox mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setBackground(new Background(new BackgroundFill(BACKGROUNDCOLOR, CornerRadii.EMPTY, Insets.EMPTY)));

        Label titleLabel = new Label("Wallet Management");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setTextFill(TEXTCOLOR);

        Button accessAccountBtn = createStyledButton("Access Account", PRIMARYCOLOR);
        Button openAccountBtn = createStyledButton("Open New Account", SECONDARYCOLOR);
        Button backBtn = createStyledButton("Back to Main Menu", Color.web("#95a5a6"));

        accessAccountBtn.setOnAction(_ -> window.setScene(createAccessAccountScene()));
        openAccountBtn.setOnAction(_ -> window.setScene(createOpenAccountScene()));
        backBtn.setOnAction(_ -> window.setScene(mainMenu));

        mainLayout.getChildren().addAll(titleLabel, accessAccountBtn, openAccountBtn, backBtn);
        return new Scene(mainLayout, 700, 650);
    }


    private Scene createAccessAccountScene() {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.setBackground(new Background(new BackgroundFill(BACKGROUNDCOLOR, CornerRadii.EMPTY, Insets.EMPTY)));

        Label titleLabel = new Label("Access Account");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setTextFill(TEXTCOLOR);

        Label pinLabel = createStyledLabel("Enter your PIN:");
        TextField pinField = createStyledTextField();
        pinField.setPromptText("4-digit PIN");

        Button submitBtn = createStyledButton("Submit", PRIMARYCOLOR);
        Button backBtn = createStyledButton("Back to Wallet", Color.web("#95a5a6"));

        Label errorLabel = new Label();
        errorLabel.setTextFill(Color.web("#e74c3c"));
        errorLabel.setVisible(false);

        VBox accountDisplay = new VBox(10);
        accountDisplay.setAlignment(Pos.CENTER);
        accountDisplay.setVisible(false);

        submitBtn.setOnAction(_ -> {
            String pin = pinField.getText();
            Customer customer = Wallet.getCustomer(pin);

            if (customer == null) {
                errorLabel.setText("Invalid PIN. Please try again.");
                errorLabel.setVisible(true);
                accountDisplay.setVisible(false);
                return;
            }

            errorLabel.setVisible(false);
            accountDisplay.getChildren().clear();

            Label customerLabel = new Label("Customer: " + customer.getFirstName() + " " + customer.getLastName());
            customerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            customerLabel.setTextFill(TEXTCOLOR);

            ListView<String> accountsList = new ListView<>();
            ObservableList<String> accounts = FXCollections.observableArrayList();

            for (Account account : customer.getAccountList()) {
                accounts.add(String.format("Account #%s - Balance: %.2f %s",
                        account.getAccountNumber(),
                        account.getBalance(),
                        account.getAccountCurrencyType()));
            }

            accountsList.setItems(accounts);
            accountsList.setMaxHeight(150);

            Button manageAccountBtn = createStyledButton("Manage Selected Account", SECONDARYCOLOR);
            manageAccountBtn.setDisable(true);

            accountsList.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) -> manageAccountBtn.setDisable(newValue == null));

            manageAccountBtn.setOnAction(_ -> {
                String selected = accountsList.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    String accountNumber = selected.split(" - ")[0].replace("Account #", "");
                    Account account = customer.getAccount(accountNumber, customer);
                    if (account != null) {
                        window.setScene(createManageAccountScene(customer, account));
                    }
                }
            });

            accountDisplay.getChildren().addAll(customerLabel, accountsList, manageAccountBtn);
            accountDisplay.setVisible(true);
        });

        backBtn.setOnAction(_ -> window.setScene(createWalletScene()));

        layout.getChildren().addAll(titleLabel, pinLabel, pinField, submitBtn, errorLabel, accountDisplay, backBtn);
        return new Scene(layout, 700, 650);
    }


    private Scene createOpenAccountScene() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(5, 20, 15, 20));
        layout.setAlignment(Pos.CENTER);
        layout.setBackground(new Background(new BackgroundFill(BACKGROUNDCOLOR, CornerRadii.EMPTY, Insets.EMPTY)));

        Label titleLabel = new Label("Open New Account");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setTextFill(TEXTCOLOR);

        ToggleGroup customerGroup = new ToggleGroup();
        RadioButton existingCustomerBtn = new RadioButton("Existing Customer");
        RadioButton newCustomerBtn = new RadioButton("New Customer");
        existingCustomerBtn.setToggleGroup(customerGroup);
        newCustomerBtn.setToggleGroup(customerGroup);
        existingCustomerBtn.setSelected(true);

        existingCustomerBtn.setTextFill(TEXTCOLOR);
        newCustomerBtn.setTextFill(TEXTCOLOR);


        VBox existingCustomerFields = new VBox(10);
        Label pinLabel = createStyledLabel("Enter your PIN:");
        TextField pinField = createStyledTextField();
        existingCustomerFields.getChildren().addAll(pinLabel, pinField);

        VBox newCustomerFields = new VBox(10);
        Label firstNameLabel = createStyledLabel("First Name:");
        TextField firstNameField = createStyledTextField();
        Label lastNameLabel = createStyledLabel("Last Name:");
        TextField lastNameField = createStyledTextField();
        Label newPinLabel = createStyledLabel("Create PIN:");
        TextField newPinField = createStyledTextField();
        newCustomerFields.getChildren().addAll(firstNameLabel, firstNameField, lastNameLabel, lastNameField, newPinLabel, newPinField);
        newCustomerFields.setVisible(false);

        customerGroup.selectedToggleProperty().addListener((_, _, newToggle) -> {
            if (newToggle == existingCustomerBtn) {
                existingCustomerFields.setVisible(true);
                newCustomerFields.setVisible(false);
            } else {
                existingCustomerFields.setVisible(false);
                newCustomerFields.setVisible(true);
            }
        });

        Label currencyLabel = createStyledLabel("Account Currency:");
        ComboBox<String> currencyComboBox = createEditableCurrencyComboBox(
                List.of("USD", "EUR", "GBP", "JPY", "CAD", "AUD", "CNY", "INR", "MXN"));

        Label amountLabel = createStyledLabel("Initial Deposit:");
        TextField amountField = createStyledTextField();
        amountField.setPromptText("0.00");

        Button submitBtn = createStyledButton("Create Account", PRIMARYCOLOR);
        Button backBtn = createStyledButton("Back to Wallet", Color.web("#95a5a6"));
        backBtn.setOnAction(_ -> window.setScene(createWalletScene()));

        Label resultLabel = new Label();
        resultLabel.setTextFill(TEXTCOLOR);
        resultLabel.setVisible(false);

        submitBtn.setOnAction(_ -> {
            try {
                Customer customer;

                if (existingCustomerBtn.isSelected()) {
                    String pin = pinField.getText();
                    customer = Wallet.getCustomer(pin);
                    if (customer == null) {
                        resultLabel.setText("Invalid PIN. Customer not found.");
                        resultLabel.setVisible(true);
                        return;
                    }
                } else {
                    String firstName = firstNameField.getText();
                    String lastName = lastNameField.getText();
                    String pin = newPinField.getText();

                    if (firstName.isEmpty() || lastName.isEmpty() || pin.isEmpty()) {
                        resultLabel.setText("Please fill all customer details");
                        resultLabel.setVisible(true);
                        return;
                    }

                    customer = new Customer(firstName, lastName, pin);
                    Wallet.addCustomer(customer);
                }

                String currency = currencyComboBox.getValue();
                if (currency == null || currency.isEmpty()) {
                    resultLabel.setText("Please select a currency");
                    resultLabel.setVisible(true);
                    return;
                }

                double amount = Double.parseDouble(amountField.getText());
                if (amount < 0) {
                    resultLabel.setText("Initial deposit cannot be negative");
                    resultLabel.setVisible(true);
                    return;
                }

                Account newAccount = new Account(amount, currency, customer.getPin());
                customer.addAccount(newAccount);

                resultLabel.setText(String.format("Account created successfully!\nAccount #: %s\nBalance: %.2f %s",
                        newAccount.getAccountNumber(),
                        newAccount.getBalance(),
                        currency));
                resultLabel.setVisible(true);

                if (existingCustomerBtn.isSelected()) {
                    pinField.clear();
                } else {
                    firstNameField.clear();
                    lastNameField.clear();
                    newPinField.clear();
                }
                amountField.clear();
                currencyComboBox.getSelectionModel().clearSelection();

            } catch (NumberFormatException e) {
                resultLabel.setText("Invalid amount format. Please enter a valid number.");
                resultLabel.setVisible(true);
            }
        });

        layout.getChildren().addAll(
                titleLabel,
                new HBox(20, existingCustomerBtn, newCustomerBtn),
                existingCustomerFields,
                newCustomerFields,
                currencyLabel,
                currencyComboBox,
                amountLabel,
                amountField,
                submitBtn,
                resultLabel,
                backBtn);

        return new Scene(layout, 700, 650);

    }

    private Scene createManageAccountScene(Customer customer, Account account) {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.setBackground(new Background(new BackgroundFill(BACKGROUNDCOLOR, CornerRadii.EMPTY, Insets.EMPTY)));

        Label titleLabel = new Label(String.format("Manage Account #%s", account.getAccountNumber()));
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setTextFill(TEXTCOLOR);

        Label balanceLabel = new Label(String.format("Current Balance: %.2f %s",
                account.getBalance(), account.getAccountCurrencyType()));
        balanceLabel.setFont(Font.font("Arial", 16));
        balanceLabel.setTextFill(TEXTCOLOR);

        Button depositBtn = createStyledButton("Deposit", PRIMARYCOLOR);
        Button withdrawBtn = createStyledButton("Withdraw", SECONDARYCOLOR);
        Button backBtn = createStyledButton("Back to Accounts", Color.web("#95a5a6"));

        depositBtn.setOnAction(_ -> showTransactionDialog(customer, account, "Deposit", true));
        withdrawBtn.setOnAction(_ -> showTransactionDialog(customer, account, "Withdraw", false));
        backBtn.setOnAction(_ -> window.setScene(createAccessAccountScene()));

        layout.getChildren().addAll(titleLabel, balanceLabel, depositBtn, withdrawBtn, backBtn);
        return new Scene(layout, 700, 650);
    }

    private void showTransactionDialog(Customer customer, Account account, String transactionType, boolean isDeposit) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle(transactionType);

        VBox dialogLayout = new VBox(20);
        dialogLayout.setPadding(new Insets(20));
        dialogLayout.setAlignment(Pos.CENTER);
        dialogLayout.setBackground(new Background(new BackgroundFill(BACKGROUNDCOLOR, CornerRadii.EMPTY, Insets.EMPTY)));

        Label amountLabel = new Label(transactionType + " Amount:");
        amountLabel.setFont(Font.font("Arial", 14));
        amountLabel.setTextFill(TEXTCOLOR);

        TextField amountField = createStyledTextField();
        amountField.setPromptText("0.00");

        Button submitBtn = createStyledButton("Submit", isDeposit ? PRIMARYCOLOR : SECONDARYCOLOR);
        Button cancelBtn = createStyledButton("Cancel", Color.web("#95a5a6"));

        Label resultLabel = new Label();
        resultLabel.setTextFill(TEXTCOLOR);

        submitBtn.setOnAction(_ -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                if (amount <= 0) {
                    resultLabel.setText("Amount must be positive");
                    return;
                }

                if (isDeposit) {
                    account.deposit(amount);
                } else {
                    if (amount > account.getBalance()) {
                        resultLabel.setText("Insufficient funds");
                        return;
                    }
                    account.withdraw(amount);
                }

                resultLabel.setText(String.format("%s successful!\nNew Balance: %.2f %s",
                        transactionType,
                        account.getBalance(),
                        account.getAccountCurrencyType()));

                PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
                delay.setOnFinished(_ -> dialog.close());
                delay.play();

            } catch (NumberFormatException e) {
                resultLabel.setText("Invalid amount format");
            }
        });

        cancelBtn.setOnAction(_ -> dialog.close());

        HBox buttonBox = new HBox(20, submitBtn, cancelBtn);
        buttonBox.setAlignment(Pos.CENTER);

        dialogLayout.getChildren().addAll(amountLabel, amountField, buttonBox, resultLabel);

        Scene dialogScene = new Scene(dialogLayout, 300, 200);
        dialog.setScene(dialogScene);
        dialog.showAndWait();

        window.setScene(createManageAccountScene(customer, account));
    }

    public static void main(String[] args) {
        launch(args);
    }
}