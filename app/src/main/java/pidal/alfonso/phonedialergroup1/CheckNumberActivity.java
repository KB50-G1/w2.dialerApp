package pidal.alfonso.phonedialergroup1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import pidal.alfonso.phonedialergroup1.phoneapi.NetworkHelper;
import pidal.alfonso.phonedialergroup1.phoneapi.PhoneAPI;
import pidal.alfonso.phonedialergroup1.phonefunctions.PhoneFunctions;

public class CheckNumberActivity extends Activity {

    private TextView phone_check;
    private TextView country_check;
    private TextView linetype_check;

    private PhoneNumber phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_number);

        // Get the phone text view.
        phone_check = (TextView) findViewById(R.id.phone_check);

        // Getting the country text view
        country_check = (TextView) findViewById(R.id.country_check);

        // Get the line type text view
        linetype_check = (TextView) findViewById(R.id.linetype_check);

        if (savedInstanceState == null) {

            // Get the intent and use the data inside it to update the phone number on screen.
            Intent intent = getIntent();
            phoneNumber = new PhoneNumber(intent.getStringExtra("phone_number"));
            // Call the API only IF internet is available, and pass the phone number to it so it can grab data.
            if (NetworkHelper.isNetworkAvailable(getApplicationContext()))
                new PhoneAPICall(this).execute(phoneNumber.getNumber());
            else {
                noInternetData();
            }

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the object when the activity is destroyed
        outState.putSerializable("phoneNumber", phoneNumber);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Get back the saved object
        phoneNumber = (PhoneNumber) savedInstanceState.getSerializable("phoneNumber");

        // Re-populate the fields
        phone_check.setText(phoneNumber.getFormattedNumber());
        country_check.setText(phoneNumber.getCountry());
        linetype_check.setText(phoneNumber.getLineType());
    }

    public void noInternetData() {

        // Format the phone number received on the intent.
        // TODO: this is not working good enough. Also the method formatNumber() is deprecated?
        String formatted_number = PhoneNumberUtils.formatNumber(phoneNumber.getNumber(), "NL");

        // TODO: Fix this. IF statement not working no idea why.
        // Set the text with the formatted text.
        phone_check.setText(formatted_number);

        // Appending the country detected using the PhoneFunctions Helper Class.
        String country_code = PhoneFunctions.getInstance().getCountry(this.getResources().getStringArray(R.array.CountryCodes2), phone_check);

        if (country_code.length() > 1)
            country_check.append(" " + country_code);
        else
            country_check.setText("No country detected");

    }

    @Override
    protected void onPause() {
        super.onPause();
        // Toast.makeText(this, "Paused!!", Toast.LENGTH_SHORT).show();
        // TODO: This is not working, no idea why. But it should be fired also when the back button (action bar) is pressed.
    }

    public void goBack(View view) {
        // Get the intent, set the result to OK, and finish the activity. onActivityResult() will be fired in the DialerActivity.
        Intent i = getIntent();
        setResult(RESULT_OK, i);
        finish();
    }

    public void callNumber(View view) {
        // Create new intent to dial the number shown on screen and fires the activity.
        Intent i = new Intent(android.content.Intent.ACTION_CALL, Uri.parse("tel:+" + phone_check.getText().toString()));
        startActivity(i);
    }

    private class PhoneAPICall extends AsyncTask<String, Void, String> {

        private Activity activity;
        private ProgressDialog dialog;

        private PhoneAPICall(Activity activity) {
            this.activity = activity;
            this.dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog.setMessage("Checking the phone number...");
            this.dialog.show();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {

                JSONObject jsonResult = new JSONObject(result);

                String phoneFormatted = jsonResult.getString("formatted-number");
                String countryName = jsonResult.getString("region");
                String lineName = jsonResult.getString("line-type");

                phoneNumber.setFormattedNumber(phoneFormatted);
                phoneNumber.setCountry(countryName);
                phoneNumber.setLineType(lineName);

                phone_check.setText(phoneFormatted);
                country_check.setText(countryName);
                linetype_check.setText(lineName);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (dialog.isShowing()) {
                dialog.dismiss();
            }

        }

        @Override
        protected String doInBackground(String... params) {

            String phoneNumberString = params[0];
            PhoneAPI call = new PhoneAPI(phoneNumberString);

            String data = call.getData();

            return data;
        }
    }


}
