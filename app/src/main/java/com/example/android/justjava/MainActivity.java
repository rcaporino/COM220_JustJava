package com.example.android.justjava;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity
{


    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view)
    {
        CheckBox cb1 = (CheckBox) findViewById(R.id.whipped_cream_check);
        boolean whippedC = cb1.isChecked();

        CheckBox cb2 = (CheckBox) findViewById(R.id.chocolate_check);
        boolean chocolateC = cb2.isChecked();

        EditText nameField = (EditText) findViewById(R.id.name);
        String name = nameField.getText().toString();

        int price = calculatePrice(whippedC, chocolateC);
        //displayMessage(createOderSummary(price,whippedC, chocolateC, name));

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for " +  name);
        intent.putExtra(Intent.EXTRA_TEXT, createOderSummary(price, whippedC, chocolateC, name));

        if (intent.resolveActivity(getPackageManager()) != null)
        {
            startActivity(intent);
        }
    }

    public int calculatePrice(boolean whippedCream, boolean chocolate)
    {
        int wcPrice = 1;
        int cPrice = 2;
        int totalPrice;

        if(whippedCream == true)
            wcPrice *= quantity;
        else
            wcPrice *= 0;

        if(chocolate == true)
            cPrice *= quantity;
        else
            cPrice *= 0;

        totalPrice = (quantity * 5) + wcPrice + cPrice;

        return totalPrice;
    }

    public void increment(View view)
    {
        if(quantity >= 100)
        {
            quantity = 100;
            Toast.makeText(this, "You cannot order more than 100 cups of coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            quantity += 1;
        }

        displayQuantity(quantity);
    }

    public void decrement(View view)
    {
        if(quantity <= 1)
        {
            quantity = 1;
            Toast.makeText(this, "You cannot order less than 1 cup of coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            quantity -=1;
        }

        displayQuantity(quantity);
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees)
    {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }

    public String createOderSummary(int priceOfOrder, boolean addWhippedCream, boolean addChoco, String name)
    {
        String summary ="Name: " + name +
                "\nAdd whipped cream? " +  addWhippedCream +
                "\nAdd chocolate? " + addChoco +
                "\nQuantity: " + quantity +
                "\nTotal: $" + priceOfOrder +
                "\nThank you!";
        return summary;
    }

}