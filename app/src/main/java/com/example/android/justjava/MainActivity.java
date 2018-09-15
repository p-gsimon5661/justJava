/*
 * IMPORTANT: Make sure you are using the correct package name. 
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity
{
    private int quantity = 2;

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
        CheckBox whippedCreamCheckbox   = findViewById(R.id.whipped_cream_checkbox);
        boolean  hasWhippedCream        = whippedCreamCheckbox.isChecked();
        CheckBox chocolateCreamCheckbox = findViewById(R.id.chocolate_checkbox);
        boolean  hasChocolate           = chocolateCreamCheckbox.isChecked();
        EditText nameEditbox            = findViewById(R.id.name_edit_view);
        String   name                   = nameEditbox.getText().toString();
        int      price                  = calculatePrice(hasWhippedCream, hasChocolate);
        String   priceMessage           = createOrderSummary(price, hasWhippedCream, hasChocolate, name);

        /*displayMessage(priceMessage);*/

        /*/ Create the text message with a string
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        sendIntent.setType("text/plain");

        // Verify that the intent will resolve to an activity
        if (sendIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(sendIntent);
        }*/

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setType("*/*");
        emailIntent.setData(Uri.parse("mailto:"));
        //emailIntent.putExtra(Intent.EXTRA_EMAIL, addresses);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject, name));
        //emailIntent.putExtra(Intent.EXTRA_STREAM, priceMessage);
        emailIntent.putExtra(Intent.EXTRA_TEXT, priceMessage);


        if (emailIntent.resolveActivity(getPackageManager()) != null)
            startActivity(emailIntent);

    }

    /**
     * Calculates the price of the order.
     * @param addWhippedCream is whipped cream added
     * @param addChocolate is chocolate added
     * @return total price of coffee
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate)
    {
        final int pricePerCup          = 5;
        final int pricePerWhippedCream = 1;
        final int pricePerChocolate    = 2;
              int finalPrice           = 0;

        if(addWhippedCream && addChocolate)
            finalPrice += pricePerCup + pricePerWhippedCream + pricePerChocolate;
        else if(addWhippedCream)
            finalPrice += pricePerCup + pricePerWhippedCream;
        else if(addChocolate)
            finalPrice += pricePerCup + pricePerChocolate;
        else
            finalPrice = pricePerCup;

        return quantity * finalPrice;
    }

    /**
     * Calculates the price of the order.
     * @param price total price of order
     * @param addWhippedCream whether they want whipped cream
     * @param addChocolate whether they want chocolate
     * @param your_name name of the customer
     * @return summary of order
     */
    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String your_name)
    {
        String order_summary =  getString(R.string.order_summary_name, your_name) + "\n";
               order_summary += getString(R.string.order_quantity, quantity) + "\n";
               order_summary += getString(R.string.add_whipped, addWhippedCream) + "\n";
               order_summary += getString(R.string.add_cocoa, addChocolate) + "\n";
               order_summary += getString(R.string.order_total, price) + "\n" + getString(R.string.thank_you);

        return order_summary;
    }

    /**
     * This method is called when the + button is clicked.
     */
    public void increment(View view)
    {
        if(quantity < 100)
            quantity++;
        else
        {
            Context      context  = getApplicationContext();
            CharSequence msg      = getString(R.string.upper_bound);
            int          duration = Toast.LENGTH_SHORT;

            Toast toastUpperBound = Toast.makeText(context, msg, duration);
            toastUpperBound.show();
        }

        displayQuantity( quantity );
    }

    /**
     * This method is called when the - button is clicked.
     */
    public void decrement(View view)
    {
        if(quantity > 1)
            quantity--;
        else
        {
            Context      context  = getApplicationContext();
            CharSequence msg      = getString(R.string.lower_bound);
            int          duration = Toast.LENGTH_SHORT;

            Toast toastUpperBound = Toast.makeText(context, msg, duration);
            toastUpperBound.show();
        }
        displayQuantity( quantity );
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int amount)
    {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText(String.valueOf(amount) );
    }

    /*/**
     * This method displays the given text on the screen.
     */
    /*private void displayMessage(String message)
    {
        TextView orderSummaryTextView = findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }*/
}