package pidal.alfonso.phonedialergroup1;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


public class DialerActivity extends Activity {

    private TextView phone_number;
    private ImageButton call_button;
    private ImageButton delete_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialer);

        // Hidding the Action Bar
        ActionBar actionBar = getActionBar();
        actionBar.hide();

        // Getting references for activity views.
        phone_number = (TextView) findViewById(R.id.text_phone_number);
        call_button = (ImageButton) findViewById(R.id.button_check);
        delete_button = (ImageButton) findViewById(R.id.button_remove);

        call_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                goToCheckNumber(view);
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // TODO: You know an easier way to do this?

                // get the phone number
                String number = phone_number.getText().toString();

                // Avoid deleting the + sign on the string.
                if (number.length() <= 1)
                    return;

                // delete last character.
                number = number.substring(0, number.length() - 1);

                // set new string without last number
                phone_number.setText(number);
            }
        });

        delete_button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                phone_number.setText("+");

                return true;
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the phone number in the bundle before the activity is destroyed.
        outState.putString("phone_number", phone_number.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Restores the phone number saved previously on the bundle when the activity is re-created.
        phone_number.setText(savedInstanceState.getString("phone_number"));
    }

    public void goToCheckNumber(View view) {

        int request_code = 123;

        //Intent intent = new Intent("pidal.alfonso.phonedialergroup1.DialerActivity");
        Intent intent = new Intent(this, CheckNumberActivity.class);

        intent.putExtra("phone_number", phone_number.getText().toString());
        startActivityForResult(intent, request_code);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 123) {
            if (resultCode == RESULT_OK) {
                //Toast.makeText(this, "Number is back!", Toast.LENGTH_SHORT).show();
                phone_number.setText(data.getStringExtra("phone_number"));
            }
        }
    }

    public void addNumber(View view) {

        // Get the button that was pressed. (note all numbers use this method)
        Button pressed_button = (Button) findViewById(view.getId());

        // append the button text (the number itself) to the phone number.
        phone_number.append(pressed_button.getText().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_dialer, menu);
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
}
