package pidal.alfonso.phonedialergroup1;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneNumberUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class CheckNumberActivity extends Activity {

    private TextView phone_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_number);

        // Get the intent and use the data inside it to update the phone number on screen.
        Intent intent = getIntent();
        phone_check = (TextView) findViewById(R.id.phone_check);

        String formated_number = PhoneNumberUtils.formatNumber(intent.getStringExtra("phone_number"), "NL");

        phone_check.setText(formated_number);

    }

    // TODO: why this activity doesn't need the onSaveInstance() and onRestoreInstance() methods to save state? wtf!!

    @Override
    protected void onPause() {
        super.onPause();

        // Toast.makeText(this, "Paused!!", Toast.LENGTH_SHORT).show();

        // TODO: This is not working, no idea why. But it should be fired also when the back button (action bar) is pressed.
        /*
        Intent i = new Intent();
        TextView phone_check = (TextView) findViewById(R.id.phone_check);
        i.putExtra("phone_number_returns", phone_check.getText().toString() );

        // Toast.makeText(this, phone_check.getText().toString(), Toast.LENGTH_SHORT).show();

        setResult(RESULT_OK, i);
        */

        /*
        Intent i = getIntent();
        setResult(RESULT_OK, i);
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_check_number, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
