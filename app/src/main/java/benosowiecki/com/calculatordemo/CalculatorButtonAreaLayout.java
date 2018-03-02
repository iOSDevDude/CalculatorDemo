package benosowiecki.com.calculatordemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Benjamin on 2/28/18.
 *
 * This class creates grids of buttons, namely for a calculator
 */

public class CalculatorButtonAreaLayout extends ViewGroup {

    private Integer numRows, numCols;

    //accept attributes from xml layout file, namely number of columns and rows
    public CalculatorButtonAreaLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CalculatorButtonAreaLayout);
        this.numRows = array.getInteger(R.styleable.CalculatorButtonAreaLayout_numRows, 4);
        this.numCols = array.getInteger(R.styleable.CalculatorButtonAreaLayout_numCols, 4);

        array.recycle();
    }

    //setup layout of buttons
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        left = 0;
        right = left + this.getWidth();

        top = 0;
        bottom = top + this.getHeight();

        //determine width and height of rows and columns
        int rowHeight = Math.round((bottom-top)/numRows);
        int colWidth = Math.round((right-left)/numCols);

        //determine number of views provided
        int numButtonViews = this.getChildCount();

        //check if there are less views for buttons then specified number of rows and columns
        //Should throw error and be handled properly
        if(numButtonViews < numRows * numCols) {
            System.out.println("Error Creating Button Area Layout");
        }

        //layout button views
        int currentRow = 0, currentCol = 0;

        for(int index=0; index<numButtonViews; index++) {

            View buttonView = getChildAt(index);

            int buttonLeft = left+(currentCol*colWidth);
            int buttonRight = buttonLeft+colWidth;
            int buttonTop = currentRow*rowHeight;
            int buttonBottom = buttonTop+rowHeight;

            //Layout Buttons and ensure size
            buttonView.measure(MeasureSpec.makeMeasureSpec(colWidth, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(rowHeight, MeasureSpec.EXACTLY));

            buttonView.layout(buttonLeft, buttonTop, buttonRight, buttonBottom);

            //increment number of columns. If at end of row, number of columns is zero and current row is incremented
            currentCol++;
            if(currentCol == numCols) {
                currentCol = 0;
                currentRow++;
            }
        }

    }
}
