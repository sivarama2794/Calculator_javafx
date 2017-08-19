/**
 * Created by Sivasubramanian on 8/17/2017.
 */
package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.sql.Timestamp;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.text.SimpleDateFormat;
import java.nio.file.Files;
import java.math.*;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class Controller {
    @FXML
    private Text output;
    private float number1 = 0;

    private String number2, expression, out,newout;
    private String operator = "";
    private Model model = new Model();
    private boolean start = true;
    private int value1;
    public Label Output_label = new Label();

    Databaseconc dbConnect = new Databaseconc();
    Connection conn = dbConnect.DBconnector();
    PreparedStatement pst = null;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    String time = sdf.format(timestamp);

    @FXML
    private void processNumpad(ActionEvent event) {

        if (start) {
            //if operator is pressed then screen becomes empty
            output.setText("");
            start = false;
        }
        String value = ((Button) event.getSource()).getText();
        output.setText(output.getText() + value);

    }

    @FXML
    private void processBackspace(ActionEvent event) {
        String outputDelete = output.getText();
        if (outputDelete.length() > 0) {

            outputDelete = outputDelete.substring(0, outputDelete.length() - 1);
            System.out.println(outputDelete);

            output.setText(outputDelete);
        }

        start = false;
    }

    @FXML
    private void processClear(ActionEvent event) {
        String outputClear = output.getText();
        outputClear = "";
        output.setText(outputClear);
        start = false;
    }

    @FXML
    private void processdecimal(ActionEvent event) {
        String outputdecimal = output.getText();
        outputdecimal = outputdecimal + ".";
        output.setText(outputdecimal);
        start = false;
    }

    @FXML
    private void processOppad(ActionEvent event) {
        String value = ((Button) event.getSource()).getText();

        if (!"=".equals(value)) {
            if (!operator.isEmpty())
                return;
            //Getting the Input numbers from the User
            if (output.getText() != "") {
                operator = value;
                System.out.println(operator);
                number1 = Float.parseFloat(output.getText());
                System.out.println(number1);
            }
            //Clearing the Screen Once the Operator is pressed by the User
            output.setText("");
        } //Only when equal is pressed
        else {
            //If Square of a number has to be performed
//            if (operator.equals("S") ) {
//                float number1=0.1F;
//                Float New=Math.sqrt(number1);
//                Output_label.setText(String.valueOf(New));
//                databaseInsert(String.valueOf(number1), Output_label.getText(), time);
//                outputFile(String.valueOf(number1), Output_label.getText(), time);
//            }
            if (operator.equals("P") ) {
                Float New=number1 / 100;
                Output_label.setText(String.valueOf(New));
                databaseInsert(String.valueOf(number1), Output_label.getText(), time);
                outputFile(String.valueOf(number1), Output_label.getText(), time);
            }
            else {
                //Calculation part
                if (operator.isEmpty())
                    return;
                number2 = output.getText();
                output.setText(String.valueOf(model.calculate(number1, Float.parseFloat(output.getText()), operator)));
                expression = number1 + operator + number2 + "=" + output.getText();
                Output_label.setText(number1 + operator + number2 + "=" + output.getText());
                out = output.getText();
                databaseInsert(expression, out, time);
                outputFile(expression, out, time);
            }
            operator = "";
            start = true;

        }
    }


    public String outputFile(String expression, String out, String time) {

        String text = "Input Expression--> " + expression + " Result--> " + out + " TimeStamp--> " + time + "\n";
        try {
            Files.write(Paths.get("./Logfile.txt"), text.getBytes(), APPEND, CREATE);
        } catch (Exception e) {
            System.out.println(e);
        }
        return "";
    }


    public void databaseInsert(String expression, String out, String time) {
        try {
//            String query = "INSERT INTO Calcidb (ID,Expresion,Result,Timestamp ) VALUES(?,?,?,?)";
            String query = "INSERT INTO Calculator (Expression,Result,TimeStamp  ) VALUES (?,?,?)";
            Statement statement = conn.createStatement();
//            String sql = "SELECT * FROM Calculator;";
//            ResultSet rs = statement.executeQuery(sql);
//            while (rs.next()) {
//                System.out.println(rs.getInt("ID") +  "\t");
//
//                System.out.println("Connection to SQLite has been established.");
//            }
            pst = conn.prepareStatement(query);
            pst.setString(1, expression);
            pst.setString(2, out);
            pst.setString(3, time);
            pst.executeUpdate();
        } catch (Exception e1) {
            System.err.println(e1);
        }
    }

}
