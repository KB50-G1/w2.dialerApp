package pidal.alfonso.phonedialergroup1;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;


public class CheckNumberActivity extends Activity {

    private TextView phone_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_number);

        // Get the intent and use the data inside it to update the phone number on screen.
        Intent intent = getIntent();

        // Get the phone text view.
        phone_check = (TextView) findViewById(R.id.phone_check);

        // Format the phone number received on the intent.
        // TODO: this is not working good enough. Also the method formatNumber() is deprecated?
        String formatted_number = PhoneNumberUtils.formatNumber(intent.getStringExtra("phone_number"), "NL");


        // TODO: Fix this. IF statement not working no idea why.
        // Set the text with the formatted text.
        phone_check.setText(formatted_number);
        /*
        if(!formatted_number.isEmpty())
            phone_check.setText(formatted_number);
        else
            phone_check.setText("Invalid Phone Number");
        */

        // Getting the country text view
        TextView country_check = (TextView) findViewById(R.id.country_check);

        // Appending the country detected using the PhoneFunctions Helper Class.
        String country_code = PhoneFunctions.getInstance().getCountry(this.getResources().getStringArray(R.array.CountryCodes2), phone_check);

        if(country_code.length() > 1)
            country_check.append(" " + country_code);
        else
            country_check.setText("No country detected");

    }

    // TODO: why this activity doesn't need the onSaveInstance() and onRestoreInstance() methods to save state? wtf!!
    // Answer: Because the data is taken from the intent each time its created.
    // Its OK like this because user can't modify the data on this activity, otherwise changes wouldn't be saved!.

    @Override
    protected void onPause() {
        super.onPause();
        // Toast.makeText(this, "Paused!!", Toast.LENGTH_SHORT).show();
        // TODO: This is not working, no idea why. But it should be fired also when the back button (action bar) is pressed.
    }

    public void goBack(View view)
    {
        // Get the intent, set the result to OK, and finish the activity. onActivityResult() will be fired in the DialerActivity.
        Intent i = getIntent();
        setResult(RESULT_OK, i);
        finish();
    }

    public void callNumber(View view)
    {
        // Create new intent to dial the number shown on screen and fires the activity.
        Intent i = new Intent(android.content.Intent.ACTION_CALL, Uri.parse("tel:+" + phone_check.getText().toString()));
        startActivity(i);
    }


}
