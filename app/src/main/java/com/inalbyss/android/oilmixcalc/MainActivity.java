package com.inalbyss.android.oilmixcalc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {


    private SeekBar barOil, barGas;
    private TextView textOil, textGas, textResult;

    int oilMinValue, gasMinValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        barOil = (SeekBar) findViewById(R.id.seekBar);
        barOil.setOnSeekBarChangeListener(this);

        barGas = (SeekBar) findViewById(R.id.seekBar2);
        barGas.setOnSeekBarChangeListener(this);

        textOil = (TextView) findViewById(R.id.textView2);
        textGas = (TextView) findViewById(R.id.textView4);
        textResult = (TextView) findViewById(R.id.textView5);

        calculateAndUpdate();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        readAndLoadPreferences();

        // Update the real and visible progress
        barOil.setProgress(barOil.getProgress() - (oilMinValue * 10));
        barGas.setProgress(barGas.getProgress() - (gasMinValue * 10));
    }

    private void readAndLoadPreferences() {
        SharedPreferences preferences = getSharedPreferences(Settings.OIL_MIX_SETTINGS, MODE_PRIVATE);

        oilMinValue = Integer.parseInt(preferences.getString(Settings.OIL_FROM, getString(R.string.default_oilFrom)));
        barOil.setMax((Integer.parseInt(preferences.getString(Settings.OIL_TO, getString(R.string.default_oilTo))) - oilMinValue) * 10);

        gasMinValue = Integer.parseInt(preferences.getString(Settings.GAS_FROM, getString(R.string.default_gasFrom)));
        barGas.setMax((Integer.parseInt(preferences.getString(Settings.GAS_TO, getString(R.string.default_gasTo))) - gasMinValue) * 10);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        // Launch settings activity
        if (id == R.id.menu_settings) {
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void calculateAndUpdate() {
        float result = ((barOil.getProgress() + (oilMinValue * 10)) * (barGas.getProgress() + (gasMinValue * 10)))  / 10;

        textResult.setText(String.valueOf(result + " ml"));
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        if (seekBar.getId() == R.id.seekBar) {
            textOil.setText(String.valueOf((seekBar.getProgress() / 10f) + oilMinValue) + " %");
        } else if (seekBar.getId() == R.id.seekBar2) {
            textGas.setText(String.valueOf((seekBar.getProgress() / 10f) + gasMinValue) + " L");
        }

        calculateAndUpdate();
    }


    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }


    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
