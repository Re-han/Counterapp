package com.example.counter_tazbeehzikr;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;


public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar; //Custom Toolbar
    private ProgressBar progressBar; //Primary progress bar
    private Button pause_button; //pause btn
    private Button count_increment; //Increment btn
    private Button reset_button;  //reset btn
    private TextView count_textView; // current Count
    private EditText count_name; //name of the counter
    private EditText count_number; // no. of counts
    private TextView status; // status textView e.g. completed, paused
    private String counter_name; // storing counter name
    private String counter_number; // storing counter number
    private String complete; // storing status value
    private String counter_textView; //storing current counts
    private int progress; // setting progress
    private SharedPreferences sharedPreferences;
    private static int count = 0; // initializing count to 0

    private static int COUNT_MAX = 1000; //max limit for no. of coounts


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Counter");
        progressBar = findViewById(R.id.progressBar);
        count_increment = findViewById(R.id.count_increment);
        pause_button = findViewById(R.id.pause_button);
        reset_button = findViewById(R.id.reset_button);
        count_number = findViewById(R.id.CounterNumber);
        count_name = findViewById(R.id.CounterName);
        count_textView = findViewById(R.id.count_textView);
        status = findViewById(R.id.status);
        setSupportActionBar(toolbar);
        //pauseAction/savingData
        pause_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade);
                pause_button.startAnimation(animation);
                // for savingData of vibration in settings tab
                SharedPreferences settingPreference = PreferenceManager.getDefaultSharedPreferences(getApplication());
                boolean check_vibration_on_btn = settingPreference.getBoolean(getString(R.string.switch_Preference_vibrate), false);
                Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                if (count_name.length() == 0 && count_number.length() == 0) {
                    if (!check_vibration_on_btn) {
                        vibrator.vibrate(100);
                    }
                    // error when user doesn't fill counterName & No of Counts
                    Toast.makeText(MainActivity.this, "Please enter Counter Name & Number of Counts ", Toast.LENGTH_SHORT).show();
                } else if (count_name.length() == 0) {
                    if (!check_vibration_on_btn) {
                        vibrator.vibrate(100);
                    }// error when user doesn't fill counterName
                    Toast.makeText(MainActivity.this, "Please enter Counter Name", Toast.LENGTH_SHORT).show();
                } else if (count_number.length() == 0) {
                    if (!check_vibration_on_btn) {
                        vibrator.vibrate(100);
                    }// error when user doesn't fill No of Counts
                    Toast.makeText(MainActivity.this, "Please enter Number of Counts", Toast.LENGTH_SHORT).show();
                } else if (progressBar.getProgress() == progressBar.getMax()) {
                    if (!check_vibration_on_btn) {
                        vibrator.vibrate(100);
                    }// error when user try to pause after the counter is completed
                    Toast.makeText(MainActivity.this, "Counter already completed", Toast.LENGTH_SHORT).show();
                } else if (count < 1) {
                    if (!check_vibration_on_btn) {
                        vibrator.vibrate(100);
                    }// error when user try to pause when counter is 0
                    Toast.makeText(MainActivity.this, "Count is 0 ! Please increment atleast by one", Toast.LENGTH_SHORT).show();
                } else {
                    vibrator.cancel();
                    //for storing data into sharedPreferences when the pause_btn is clicked and the status is set to paused
                    status.setText("PAUSED");
                    sharedPreferences = getSharedPreferences("Pause Reference", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("counter name", count_name.getText().toString());
                    editor.putString("counter number", count_number.getText().toString());
                    editor.putString("count textView", count_textView.getText().toString());
                    editor.putInt("progress bar value", progressBar.getProgress());
                    editor.putString("complete", status.getText().toString());
                    editor.apply();

                }
            }

        });
            //counter Increment action
        count_increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences settingPreference = PreferenceManager.getDefaultSharedPreferences(getApplication());
                boolean check_vibration_on_btn = settingPreference.getBoolean(getString(R.string.switch_Preference_vibrate), false);
                Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

                if (count_name.length() == 0 && count_number.length() == 0) {
                    if (!check_vibration_on_btn) {
                        vibrator.vibrate(100);
                    }
// error when user doesn't fill counterName & No of Counts
                    Toast.makeText(MainActivity.this, "Please enter Counter Name and Number of Counts", Toast.LENGTH_SHORT).show();
                } else if (count_name.length() == 0) {
                    if (!check_vibration_on_btn) {
                        vibrator.vibrate(100);
                    }// error when user doesn't fill counterName
                    Toast.makeText(MainActivity.this, "Please enter Counter Name", Toast.LENGTH_SHORT).show();
                } else if (count_number.length() == 0) {
                    if (!check_vibration_on_btn) {
                        vibrator.vibrate(100);
                    }// error when user doesn't fill No of Counts
                    Toast.makeText(MainActivity.this, "Please enter Number of Counts", Toast.LENGTH_SHORT).show();
                } else if (status.getText().equals("PAUSED")) {
                    if (!check_vibration_on_btn) {
                        vibrator.vibrate(100);
                    } //when user try to Resume right after pausing
                    status.setText("RESUMED");
                    clearAll();
                } else {
                    if (!check_vibration_on_btn) {
                        vibrator.vibrate(100);
                    }
                    int count_max_check_vibration_on_btn = Integer.parseInt(count_number.getText().toString());
                    if (count_max_check_vibration_on_btn > COUNT_MAX) {
                        //error when user enter more than MAXCOUNT
                        Toast.makeText(MainActivity.this, "Max Limit is 1000", Toast.LENGTH_SHORT).show();
                        count_number.setText("");
                    } else {
                        //count increment logic
                        vibrator.cancel();
                        status.setText("");
                        count_number.setEnabled(false);
                        count_name.setEnabled(false);
                        progressBar.setMax(progressMaxValue());
                        count = count + 1;
                        progressBar.setProgress(count);
                        String show = Integer.toString(count);
                        count_textView.setText(show);
                        if (progressBar.getProgress() == progressBar.getMax()) {
                            count_increment.setEnabled(false);
                            count_name.setEnabled(false);
                            count_number.setEnabled(false);
                            status.setText("COMPLETED");
                            clearAll();
                        }

                    }
                }
            }
        });
             //resetAction
        reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate);
                reset_button.startAnimation(animation);
                //vibration
                SharedPreferences settingPreference = PreferenceManager.getDefaultSharedPreferences(getApplication());
                boolean check_vibration_on_btn = settingPreference.getBoolean(getString(R.string.switch_Preference_vibrate), false);
                Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                if (!check_vibration_on_btn) {
                    vibrator.vibrate(100);
                }
                //resetting the views and clearing saved data
                count = 0;
                count_name.setText("");
                count_number.setText("");
                count_textView.setText("0");
                status.setText("");
                count_name.setEnabled(true);
                count_number.setEnabled(true);
                count_increment.setEnabled(true);
                progressBar.setProgress(0);
                clearAll();
            }
        });


    }
//loading saved data
    @Override
    protected void onResume() {
        super.onResume();
        //check if status is paused
        sharedPreferences = getSharedPreferences("Pause Reference", MODE_PRIVATE);
        complete = sharedPreferences.getString("complete", "");
        //if status is paused load saved data to views
        if (complete.equals("PAUSED")) {
            status.setText("PAUSED");
            count_name.setEnabled(false);
            count_number.setEnabled(false);
            counter_name = sharedPreferences.getString("counter name", "");
            counter_number = sharedPreferences.getString("counter number", "");
            counter_textView = sharedPreferences.getString("count textView", "");
            progress = sharedPreferences.getInt("progress bar value", 0);
            updatingPausedSession();
        }


    }
//setting maximum value to progressBar
    public int progressMaxValue() {
        String getTextFromCountNumber = count_number.getText().toString();
        int convertToInt = Integer.parseInt(getTextFromCountNumber);
        return convertToInt;

    }
//updating saved data to the views
    public void updatingPausedSession() {
        count_name.setText(counter_name);
        count_number.setText(counter_number);
        count_textView.setText(counter_textView);
        progressBar.setProgress(0);
        count = progress;


    }
//clearing the saved data
    public void clearAll() {
        sharedPreferences = getSharedPreferences("Pause Reference", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.right_menu, menu);
        return true;
    }
//for opening settings activity through menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (item.getItemId() == itemId) {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
        } else {
            super.onOptionsItemSelected(item);
        }
        return true;
    }
//displaying dialogBox when user press backBtn with pausing/saving data
    @Override
    public void onBackPressed() {
        if (count_number.getText().length() > 0 && count_name.getText().length()> 0 && count_textView.getText().length()> 0 && status.getText().length() == 0 ) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("ARE YOU SURE?");
            alertDialog.setIcon(getResources().getDrawable(R.drawable.ic_baseline_warning_24));
            alertDialog.setMessage("Exiting without Pause might lose your current counts! Continue?");
            alertDialog.setCancelable(true);
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    Toast.makeText(MainActivity.this, "No data Saved", Toast.LENGTH_SHORT).show();
                }
            });
            alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alertDialog.create();
            alertDialog.show();
        } else {
            super.onBackPressed();
        }

    }


}
