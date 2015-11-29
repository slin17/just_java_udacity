package com.example.android.justjava2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.justjava2.R;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {
    int quantity = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void increment(View view) {
        if (quantity >= 100) {
            Toast.makeText(getApplicationContext(), getString(R.string.more_than_100), Toast.LENGTH_SHORT).show();
        } else {
        quantity += 1;
        display(quantity);
        }
    }

    public void decrement(View view) {
        if (quantity > 0) {
            quantity -= 1;
        }
        display(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        //displayPrice(quantity*5);

        CheckBox cbw = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        Boolean wc = cbw.isChecked();
        CheckBox cbc = (CheckBox) findViewById(R.id.chocolate_checkbox);
        Boolean cho = cbc.isChecked();
        EditText name = (EditText) findViewById(R.id.name_edit_text);
        String n = name.getText().toString();
        /*int price = calculatePrice();
        String priceMessage = "$" + price;
        priceMessage = priceMessage + " for " + quantity;
        priceMessage = priceMessage + " cups of coffee. Pay up! You PC, bro? \n Thank you!";
        displayMessage(priceMessage);*/
        if (quantity == 0) {
            Toast.makeText(getApplicationContext(), getString(R.string.have_to_order), Toast.LENGTH_SHORT).show();
        } else {
            String message = createOrderSummary(n, quantity*5 ,wc, cho);
        //displayMessage(message);
            composeEmail(message, getString(R.string.order_summary_email_subject,n));
        }
        //displayMessage("Thank you!");
    }

    private void composeEmail(String message, String name) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, name);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private int calculatePrice (boolean wc, boolean cho) {
        int price;
        if (wc && cho) {
            price = 3;
        } else if (wc) {
            price = 1;
        } else if (cho){
            price = 2;
        } else {
            price = 0;
        }

        if (quantity == 0) {
            return 0;
        }
        return quantity*(5+price);
    }

    /*private String createOrderSummary(int quantity, boolean state, boolean state2, String n){
        return getString(R.string.name)+ ": " + n + "\nAdd " + getString(R.string.whipped_cream)+ "? " + state + "\nAdd " + getString(R.string.chocolate)+ "? "+ state2 + "\n" + getString(R.string.quantity) + ": "+ quantity +"\nTotal: $" + calculatePrice(state, state2) + "\n" + getString(R.string.thank_you);
    }*/

    /**
     * Create summary of the order.
     *
     * @param name            on the order
     * @param price           of the order
     * @param addWhippedCream is whether or not to add whipped cream to the coffee
     * @param addChocolate    is whether or not to add chocolate to the coffee
     * @return text summary
     */
    private String createOrderSummary(String name, int price, boolean addWhippedCream,
                                      boolean addChocolate) {
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += "\n" + getString(R.string.order_summary_whipped_cream, addWhippedCream);
        priceMessage += "\n" + getString(R.string.order_summary_chocolate, addChocolate);
        priceMessage += "\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage += "\n" + getString(R.string.order_summary_price,
                NumberFormat.getCurrencyInstance().format(price));
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
    /*private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }*/

    /**
     * This method displays the given text on the screen.
     */
    /*private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }*/
}