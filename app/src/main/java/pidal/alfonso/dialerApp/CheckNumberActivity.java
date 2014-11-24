package pidal.alfonso.dialerApp;

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

import pidal.alfonso.dialerApp.phoneApi.NetworkHelper;
import pidal.alfonso.dialerApp.phoneApi.PhoneAPI;
import pidal.alfonso.dialerApp.phoneFunctions.PhoneFunctions;
import pidal.alfonso.dialerApp.phoneFunctions.PhoneNumber;

public class CheckNumberActivity extends Activity {

    private TextView phoneCheck;
    private TextView countryCheck;
    private TextView lineTypeCheck;

    private PhoneNumber phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_number);

        // Get the phone text view.
        phoneCheck = (TextView) findViewById(R.id.phone_check);
        // Getting the country text view
        countryCheck = (TextView) findViewById(R.id.country_check);
        // Get the line type text view
        lineTypeCheck = (TextView) findViewById(R.id.linetype_check);

        if (savedInstanceState == null) {
            // Get the intent and use the data inside it to update the phone number on screen.
            Intent intent = getIntent();

            // Create new PhoneNumber instance, that will save all data.
            phoneNumber = new PhoneNumber(intent.getStringExtra("phoneNumber"));

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
        phoneCheck.setText(phoneNumber.getFormattedNumber());
        countryCheck.setText(phoneNumber.getCountry());
        lineTypeCheck.setText(phoneNumber.getLineType());
    }

    public void noInternetData() {

        // Format the phone number received on the intent.
        // TODO: this is not working good enough. Also the method formatNumber() is deprecated?

        String formattedNumber = phoneNumber.getNumber();
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            formattedNumber = PhoneNumberUtils.formatNumber(phoneNumber.getNumber(), "NL");
        }

        // Save formatted phone number
        phoneNumber.setFormattedNumber(formattedNumber);

        // Set the country detected using the PhoneFunctions Helper Class.
        phoneNumber.setCountry(PhoneFunctions.getInstance().getCountry(this.getResources().getStringArray(R.array.CountryCodes2), phoneNumber.getNumber()));

        // Save default (not available) when no internet is available
        phoneNumber.setLineType(getResources().getText(R.string.not_available).toString());

        // Set the text in the views
        phoneCheck.setText(phoneNumber.getFormattedNumber());
        countryCheck.setText(phoneNumber.getCountry());
        lineTypeCheck.setText(phoneNumber.getLineType());

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
        Intent i = new Intent(android.content.Intent.ACTION_CALL, Uri.parse("tel:" + phoneCheck.getText().toString()));
        startActivity(i);
    }

    private class PhoneAPICall extends AsyncTask<String, Void, String> {

        private ProgressDialog dialog;

        private PhoneAPICall(Activity activity) {
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
                String countryName = jsonResult.getString("iso-code") + ", " + jsonResult.getString("region");
                String lineName = jsonResult.getString("line-type");

                phoneNumber.setFormattedNumber(phoneFormatted);
                phoneNumber.setCountry(countryName);
                phoneNumber.setLineType(lineName);

                phoneCheck.setText(phoneFormatted);
                countryCheck.setText(countryName);
                lineTypeCheck.setText(lineName);

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

            return call.getData();
        }
    }


}
