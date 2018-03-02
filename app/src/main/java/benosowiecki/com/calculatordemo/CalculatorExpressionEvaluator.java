package benosowiecki.com.calculatordemo;

import android.content.Context;
import java.util.ArrayList;

/**
 * Created by Benjamin on 3/1/18.
 * This class calculates results based on input expression.
 */

public class CalculatorExpressionEvaluator {

    private String addSymbol;
    private String subtractSymbol;
    private String multiplySymbol;
    private String divideSymbol;

    public CalculatorExpressionEvaluator(Context context) {

        //Local instance of all unicode operators in use
        this.addSymbol = context.getString(R.string.add_symbol);
        this.subtractSymbol = context.getString(R.string.subtract_symbol);
        this.multiplySymbol = context.getString(R.string.multiply_symbol);
        this.divideSymbol = context.getString(R.string.divide_symbol);
    }

    //Evaulates the expression in token form by working through position by position and calling evaluate helper when operator found
    public String evaluate(ArrayList<String> tokenList) {
        for(int i=0; i<tokenList.size(); i++) {
            if(tokenList.get(i).equals(this.divideSymbol) || tokenList.get(i).equals(this.multiplySymbol)) {
                tokenList.set(i, evaluateHelper(tokenList.get(i-1), tokenList.get(i+1), tokenList.get(i)));
                tokenList.remove(i+1);
                tokenList.remove(i-1);
                i=i-2;
            }
        }
        for(int i=0; i<tokenList.size(); i++) {
            if(tokenList.get(i).equals(this.addSymbol) || tokenList.get(i).equals(this.subtractSymbol)) {
                tokenList.set(i, evaluateHelper(tokenList.get(i-1), tokenList.get(i+1), tokenList.get(i)));
                tokenList.remove(i+1);
                tokenList.remove(i-1);
                i=i-2;
            }
        }

        return tokenList.get(0);
    }

    //Does computaions based on input left number, right number, and the operator used on them
    public String evaluateHelper(String left, String right, String operator) {
        double numOne = new Double(left);
        double numTwo = new Double(right);
        double result = 0;

        if(operator.equals(this.addSymbol)) {
            result = numOne + numTwo;
        }
        else if(operator.equals(this.subtractSymbol)) {
            result = numOne - numTwo;
        }
        else if(operator.equals(this.multiplySymbol)) {
            result = numOne * numTwo;
        }
        else if(operator.equals(this.divideSymbol)) {
            result = numOne / numTwo;
        }

        return Double.toString(result);
    }


    //Note: This method is nowhere near fool proof, depends on user entering expression correctly
    public ArrayList<String> formTokenList(String expression) {
        ArrayList<String> tokens = new ArrayList<String>();

        //Form list of strings from expression
        for(int i=0; i<expression.length(); i++) {
            tokens.add(expression.charAt(i)+"");
        }

        //Combine list positions which contain numbers or operators, thereby separating operators and numbers
        for(int i=1; i<tokens.size(); i++) {
            if(Character.getNumericValue(tokens.get(i-1).charAt(0)) >= 0 && Character.getNumericValue(tokens.get(i).charAt(0)) >= 0) {
                tokens.set(i-1, tokens.get(i-1)+tokens.get(i));
                tokens.remove(i);
                i=0;
            } else if(Character.getNumericValue(tokens.get(i-1).charAt(0)) < 0 && Character.getNumericValue(tokens.get(i).charAt(0)) < 0) {
                tokens.set(i-1, tokens.get(i-1)+tokens.get(i));
                tokens.remove(i);
                i=0;
            }
        }

        //If two subtraction symbols are next to one another, append on of them to the following number
        for(int i=0; i<tokens.size(); i++) {
            String token = tokens.get(i);

            if(token.length() > 1 && token.charAt(token.length()-1) == subtractSymbol.charAt(0)) {
                tokens.set(i, token.substring(0, token.length()-1));
                tokens.set(i+1, "-"+tokens.get(i+1));
            }
        }

        return tokens;
    }
}
