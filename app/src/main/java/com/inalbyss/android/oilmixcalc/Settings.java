package com.inalbyss.android.oilmixcalc;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;


public class Settings extends AppCompatActivity {


    public static final String OIL_MIX_SETTINGS = "OilMix-Settings";
    public static final String OIL_FROM = "OilFrom";
    public static final String OIL_TO = "OilTo";
    public static final String GAS_FROM = "GasFrom";
    public static final String GAS_TO = "GasTo";
    public static final String LAYOUT = "layout_setting";

    SharedPreferences preferences;

    EditText editText_OilFrom, editText_OilTo, editText_GasFrom, editText_GasTo;
    RadioGroup radioGroup_theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Get reference of EditTexts
        editText_OilFrom = (EditText) findViewById(R.id.editText);
        editText_OilTo = (EditText) findViewById(R.id.editText2);
        editText_GasFrom = (EditText) findViewById(R.id.editText3);
        editText_GasTo = (EditText) findViewById(R.id.editText4);

        // Get reference of RadioGroup
        radioGroup_theme = (RadioGroup) findViewById(R.id.radioGroup);

        // Show 'Not implemented yet' message when click on any RadioButton
        radioGroup_theme.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Toast.makeText(Settings.this, "Not implemented yet!", Toast.LENGTH_SHORT).show();
            }
        });

        // Get reference of preferences
        preferences = getSharedPreferences(OIL_MIX_SETTINGS, MODE_PRIVATE);

        // Read and load preferences
        readAndLoadPreferences();
    }

    private void readAndLoadPreferences() {
        editText_OilFrom.setText(preferences.getString(OIL_FROM, getString(R.string.default_oilFrom)));
        editText_OilTo.setText(preferences.getString(OIL_TO, getString(R.string.default_oilTo)));
        editText_GasFrom.setText(preferences.getString(GAS_FROM, getString(R.string.default_gasFrom)));
        editText_GasTo.setText(preferences.getString(GAS_TO, getString(R.string.default_gasTo)));

        int rGroupId = preferences.getInt(LAYOUT, 1);

        if (rGroupId == 1) {
            radioGroup_theme.check(R.id.radioButton);
        } else if (rGroupId == 2) {
            radioGroup_theme.check(R.id.radioButton2);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Open and save preferences
        savePreferences();
    }

    private void savePreferences() {
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(OIL_FROM, editText_OilFrom.getText().toString());
        editor.putString(OIL_TO, editText_OilTo.getText().toString());
        editor.putString(GAS_FROM, editText_GasFrom.getText().toString());
        editor.putString(GAS_TO, editText_GasTo.getText().toString());

        int rGroupId = radioGroup_theme.getCheckedRadioButtonId();

        if (rGroupId == R.id.radioButton) {
            editor.putInt(LAYOUT, 1);
        } else if (rGroupId == R.id.radioButton2) {
            editor.putInt(LAYOUT, 2);
        }

        editor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
}
