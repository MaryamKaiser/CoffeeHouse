package com.example.android.justjava;

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
public class MainActivity extends AppCompatActivity {
    int quantity = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        EditText ed_name = (EditText) findViewById(R.id.name_edit_text);
        String name = ed_name.getText().toString();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price =   calculatePrice(hasWhippedCream, hasChocolate);

        String emailBody = createOrderSummary(name,price,hasWhippedCream, hasChocolate);
        String subject = "JustJava order for " + name;
        composeEmail(subject, emailBody);
    }

    public void composeEmail( String subject, String emailBody) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        //intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, emailBody);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    private String createOrderSummary(String name, int priceOfOrder, boolean hasWhippedCream, boolean hasChocolate){
        String summaryMessage = "Name: "+ name + "\nAdd Whipped Cream ? " + hasWhippedCream + "\nAdd Chocolate?"+hasChocolate+ "\nQuantity: " + quantity + "\nTotal: $" + priceOfOrder + "\nThank you!";
        return summaryMessage;
    }
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int basePrice = 5;
        if(hasWhippedCream)
        {
            basePrice = basePrice + 1;
        }
        if(hasChocolate)
        {
            basePrice = basePrice + 2;
        }
        return   basePrice* quantity;

    }
    public void incrementOrder(View view){

        if(quantity == 100)
        {
            String message = "You can nt have more than 100 cups of coffees";
            Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }
    public void decrementOrder(View view){

        if(quantity == 1)
        {
            String message = "You cannot order less than 1 cup of coffee";
            Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


}
