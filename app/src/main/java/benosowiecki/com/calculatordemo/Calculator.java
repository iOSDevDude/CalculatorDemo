package benosowiecki.com.calculatordemo;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by Benjamin on 2/28/18.
 * This class is the main Activity of the CalculateDemo app, and orchestrates setup and function.
 */

public class Calculator extends AppCompatActivity implements View.OnLongClickListener {

    private CalculatorDisplay resultDisplay, expressionDisplay;

    //Do view setup and instantiate variables
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }

        setContentView(R.layout.activity_calculator);

        resultDisplay = (CalculatorDisplay) findViewById(R.id.display_result);
        expressionDisplay = (CalculatorDisplay) findViewById(R.id.display_expression);
        ((Button) findViewById(R.id.button_delete)).setOnLongClickListener(this);
    }

    //Handle calculator button being clicked
    public void onButtonClick(View view) {
        Button pressedButton = (Button) view;

        switch (pressedButton.getId()) {
            case R.id.button_delete:
                deleteFromDisplay();
                break;
            case R.id.button_equals:
                evaluateFromDisplay();
                break;
            default:
                expressionDisplay.append(pressedButton.getText());
        }
    }

    //Handle long press. Currently only used to fully clear calculator
    @Override
    public boolean onLongClick(View view) {
      if(view.getId() == R.id.button_delete) {
          clearFromDisplay();
          return true;
      }
      return false;
    }

    //Clear displays of calculator
    public void clearFromDisplay() {
        expressionDisplay.setText("");
        resultDisplay.setText("");
    }

    //Delete last character from expression display
    public void deleteFromDisplay() {
        String expressionText = expressionDisplay.getText().toString();
        if(expressionText.length() > 0) {
            expressionDisplay.setText(expressionText.substring(0, expressionText.length() - 1));
        }
    }

    //Evaluate expression in expression display and show it in result display.
    public void evaluateFromDisplay() {
        String expressionText = expressionDisplay.getText().toString();
        CalculatorExpressionEvaluator evaluator = new CalculatorExpressionEvaluator(getApplicationContext());
        ArrayList<String> tokenList = evaluator.formTokenList(expressionText);
        String result = evaluator.evaluate(tokenList);
        resultDisplay.setText(result);
    }
}
