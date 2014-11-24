package pidal.alfonso.phonedialergroup1;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class DialerActivity extends Activity {

    private TextView phoneNumber;
    private ImageButton callButton;
    private ImageButton deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialer);

        // Hiding the Action Bar
        ActionBar actionBar = getActionBar();
        actionBar.hide();

        // Getting references for activity views.
        phoneNumber = (TextView) findViewById(R.id.text_phone_number);
        callButton = (ImageButton) findViewById(R.id.button_check);
        deleteButton = (ImageButton) findViewById(R.id.button_remove);

        callButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Goes to the CheckNumber activity.
                goToCheckNumber(view);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Removes a single number
                removeNumber(view);
            }
        });

        deleteButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Removes all numbers
                phoneNumber.setText("+");
                return true;
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the phone number in the bundle before the activity is destroyed.
        outState.putString("phoneNumber", phoneNumber.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Restores the phone number saved previously on the bundle when the activity is re-created.
        phoneNumber.setText(savedInstanceState.getString("phoneNumber"));
    }

    public void goToCheckNumber(View view) {

        int requestCode = 123;

        //Intent intent = new Intent("pidal.alfonso.phonedialergroup1.DialerActivity");
        Intent intent = new Intent(this, CheckNumberActivity.class);

        intent.putExtra("phoneNumber", phoneNumber.getText().toString());
        startActivityForResult(intent, requestCode);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 123) {
            if (resultCode == RESULT_OK) {
                // Creates toast when the 'Go Back' button is pressed.
                Toast.makeText(this, "Number is back!", Toast.LENGTH_SHORT).show();
                phoneNumber.setText(data.getStringExtra("phoneNumber"));
            }
        }
    }

    public void addNumber(View view) {

        // Get the button that was pressed. (note all numbers use this method)
        Button pressedButton = (Button) findViewById(view.getId());

        // MediaPlayer instance that plays sound.
        MediaPlayer buttonSound;

        // Switch case statement for different sounds with different buttons.
        switch (Integer.parseInt(pressedButton.getText().toString())) {
            case 0:
                buttonSound = MediaPlayer.create(DialerActivity.this, R.raw.cellphonenr0);
            case 1:
                buttonSound = MediaPlayer.create(DialerActivity.this, R.raw.cellphonenr1);
            case 2:
                buttonSound = MediaPlayer.create(DialerActivity.this, R.raw.cellphonenr2);
            case 3:
                buttonSound = MediaPlayer.create(DialerActivity.this, R.raw.cellphonenr3);
            case 4:
                buttonSound = MediaPlayer.create(DialerActivity.this, R.raw.cellphonenr4);
            case 5:
                buttonSound = MediaPlayer.create(DialerActivity.this, R.raw.cellphonenr5);
            case 6:
                buttonSound = MediaPlayer.create(DialerActivity.this, R.raw.cellphonenr6);
            case 7:
                buttonSound = MediaPlayer.create(DialerActivity.this, R.raw.cellphonenr7);
            case 8:
                buttonSound = MediaPlayer.create(DialerActivity.this, R.raw.cellphonenr8);
            case 9:
                buttonSound = MediaPlayer.create(DialerActivity.this, R.raw.cellphonenr9);
            default:
                buttonSound = MediaPlayer.create(DialerActivity.this, R.raw.cellphonenr0);
        }

        // Plays sound.
        buttonSound.start();

        // append the button text (the number itself) to the phone number.
        phoneNumber.append(pressedButton.getText().toString());
    }

    public void removeNumber(View view) {
        // TODO: You know an easier way to do this?

        // get the phone number
        String number = phoneNumber.getText().toString();

        // Avoid deleting the + sign on the string.
        if (number.length() <= 1)
            return;

        // delete last character.
        number = number.substring(0, number.length() - 1);

        // set new string without last number
        phoneNumber.setText(number);
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
