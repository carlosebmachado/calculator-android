package me.carlos.calculator.main;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import me.carlos.calculator.R;
import me.carlos.calculator.util.StringUtils;

public class MainActivity extends AppCompatActivity {

    private final int SCREEN_DIGITS_LIMIT = 9;

    private Operator operator = Operator.NO_OPERATOR;
    private boolean startedOperation = false;
    private boolean isOperation = false;
    private double operand;
    private TextView textView;
    private String text = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.tvScreenText);
        screenUpdate();
    }

    private void doOperation() {
        if (!startedOperation) {
            startedOperation = true;
            text = textView.getText().toString();
            operand = Double.parseDouble(text);
        } else {
            switch (operator) {
                case ADD:
                    operand += Double.parseDouble(text);
                    break;
                case SUB:
                    operand -= Double.parseDouble(text);
                    break;
                case TIMES:
                    operand *= Double.parseDouble(text);
                    break;
                case DIVIDE:
                    operand /= Double.parseDouble(text);
                    break;
                case NO_OPERATOR:
                default:
                    break;
            }
            text = Double.toString(operand);
            isOperation = true;
            screenUpdate();
            isOperation = false;
        }
        text = "0";
    }

    public void addition(View v) {
        doOperation();
        operator = Operator.ADD;
    }

    public void subtraction(View v) {
        doOperation();
        operator = Operator.SUB;
    }

    public void multiply(View v) {
        doOperation();
        operator = Operator.TIMES;
    }

    public void division(View v) {
        doOperation();
        operator = Operator.DIVIDE;
    }

    public void equal(View v) {
        doOperation();
        clear();
    }

    public void delete(View v) {
        if (text.equals("0")) {
            clear();
        } else if (text.length() <= 1) {
            text = "0";
        } else {
            text = text.substring(0, text.length() - 1);
        }
        screenUpdate();
    }

    private void clear() {
        text = "0";
        operand = 0f;
        startedOperation = false;
        operator = Operator.NO_OPERATOR;
    }

    public void dot(View v) {
        if (!belowLimit()) {
            return;
        }
        if (!StringUtils.isFloat(text)) {
            text += ".";
        }
        screenUpdate();
    }

    public void number(View v) {
        if (!belowLimit()) {
            return;
        }
        text += ((Button) v).getText();
        screenUpdate();
    }

    private void screenUpdate() {
        if (!StringUtils.isFloat(text) || isOperation) {
            double doubleNumber = Double.parseDouble(text);
            if (doubleNumber == (long) doubleNumber) {
                text = String.format(Locale.US, "%d", (long) doubleNumber);
            } else {
                text = String.format("%s", doubleNumber);
            }
        }
        textView.setText(text);
    }

    private boolean belowLimit() {
        return text.length() < SCREEN_DIGITS_LIMIT;
    }

    enum Operator {
        NO_OPERATOR, ADD, SUB, TIMES, DIVIDE
    }

}
