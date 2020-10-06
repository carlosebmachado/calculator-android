package me.carlos.calculator.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import me.carlos.calculator.R;
import me.carlos.calculator.util.StrUtil;

public class MainActivity extends AppCompatActivity {

    // constants
    private final int SCREEN_DIGITS_LIMIT = 9;

    enum Operator {
        NO_OPERATOR, ADD, SUB, TIMES, DIVIDE;
    }

    private Operator operator = Operator.NO_OPERATOR;
    private boolean startedOperation = false;

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

    private void doOperation(){
        if(!startedOperation){
            startedOperation = true;
            text = textView.getText().toString();
            operand = Double.parseDouble(text);
        }else{
            switch (operator){
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
            screenUpdate();
        }
        text = "0";
    }

    public void addition(View v){
        doOperation();
        operator = Operator.ADD;
    }

    public void subtraction(View v){
        doOperation();
        operator = Operator.SUB;
    }

    public void multiply(View v){
        doOperation();
        operator = Operator.TIMES;
    }

    public void division(View v){
        doOperation();
        operator = Operator.DIVIDE;
    }

    public void equal(View v){
        doOperation();
        clear();
    }

    public void delete(View v){
        if(text.equals("0")){
            clear();
        } else if(text.length() <= 1){
            text = "0";
        } else {
            text = text.substring(0, text.length() - 1);
        }

        screenUpdate();
    }

    private void clear(){
        text = "0";
        operand = 0f;
        startedOperation = false;
        operator = Operator.NO_OPERATOR;
    }

    public void dot(View v){
        if(!StrUtil.isFloat(text)){
            text += ".";
        }

        screenUpdate();
    }

    private void number(String number){
        if(text.equals("0") && number.equals("0") ||
           !belowLimit()){
            // 1: ja e zero
            // 2: nao esta abaixo do limite (ja atingiu o limite)
            return;
        }

        if(text.equals("0")){
            text = number;
        } else {
            text += number;
        }

        screenUpdate();
    }

    public void zero(View v){number("0");}
    public void one(View v){number("1");}
    public void two(View v){number("2");}
    public void three(View v){number("3");}
    public void four(View v){number("4");}
    public void five(View v){number("5");}
    public void six(View v){number("6");}
    public void seven(View v){number("7");}
    public void eight(View v){number("8");}
    public void nine(View v){number("9");}

    private void screenUpdate(){
        textView.setText(text);
    }

    private boolean belowLimit(){
        String tstr = textView.getText().toString().trim();
        return tstr.length() < SCREEN_DIGITS_LIMIT;
    }

    private void message(@org.jetbrains.annotations.NotNull Object msg){
        Toast.makeText(this, msg.toString(), Toast.LENGTH_SHORT).show();
    }
}
