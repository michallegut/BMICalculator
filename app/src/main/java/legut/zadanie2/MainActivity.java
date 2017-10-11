package legut.zadanie2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.editMass) EditText editMass;
    @BindView(R.id.editHeight) EditText editHeight;
    @BindView(R.id.textBMICalculated) TextView textBMICalculated;
    @BindView(R.id.textCategory) TextView textCategory;
    @BindView(R.id.textActiveMode) TextView textActiveMode;

    private boolean mode;
    private float BMI;
    private ForKgM calculatorKgM;
    private ForLbIn calculatorLbIn;
    Toast toastObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        calculatorKgM = new ForKgM();
        calculatorLbIn = new ForLbIn();

        SharedPreferences data = getSharedPreferences("data", 0);
        mode = data.getBoolean("mode", true);
        if (mode) textActiveMode.setText(R.string.kg_m);
        else textActiveMode.setText(R.string.lb_in);
        editMass.setText(data.getString("mass",""));
        editHeight.setText(data.getString("height",""));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemAuthor:
                startActivity(new Intent(this, AuthorActivity.class));
                return true;
            case R.id.itemSave:
                SharedPreferences data = getSharedPreferences("data", 0);
                SharedPreferences.Editor editor = data.edit();
                editor.putBoolean("mode", mode);
                editor.putString("mass", editMass.getText().toString());
                editor.putString("height", editHeight.getText().toString());
                editor.apply();
                if (toastObject!=null) toastObject.cancel();
                toastObject = Toast.makeText(this,  R.string.data_saved, Toast.LENGTH_SHORT);
                toastObject.show();
                return true;
            case R.id.itemShare:
                if (BMI>0) {
                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.setType("text/plain");
                    share.putExtra(Intent.EXTRA_TEXT, String.format("%s%n%.2f%n%s%n%s", getResources().getString(R.string.share_text1), BMI, getResources().getString(R.string.share_text2), textCategory.getText().toString()));
                    startActivity(Intent.createChooser(share, getResources().getString(R.string.choose_application)));
                }
                else {
                    if (toastObject!=null) toastObject.cancel();
                    toastObject = Toast.makeText(this,  R.string.calculate_first, Toast.LENGTH_SHORT);
                    toastObject.show();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putFloat("BMI", BMI);
        savedInstanceState.putBoolean("mode", mode);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        BMI = savedInstanceState.getFloat("BMI");
        if (BMI>0) setBMI();
        mode = savedInstanceState.getBoolean("mode");
        if (mode) textActiveMode.setText(R.string.kg_m);
        else textActiveMode.setText(R.string.lb_in);
    }

    @OnClick(R.id.buttonCalculate)
    public void calculate() {
        try {
            if (mode) BMI = calculatorKgM.countBMI(Float.parseFloat(editMass.getText().toString()), Float.parseFloat(editHeight.getText().toString()));
            else BMI = calculatorLbIn.countBMI(Float.parseFloat(editMass.getText().toString()), Float.parseFloat(editHeight.getText().toString()));
            setBMI();
        }
        catch (IllegalArgumentException e) {
            textBMICalculated.setText("");
            textCategory.setText("");
            if (toastObject!=null) toastObject.cancel();
            toastObject = Toast.makeText(this,  R.string.wrong_input, Toast.LENGTH_SHORT);
            toastObject.show();
        }
        InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @OnClick(R.id.buttonLbIn)
    public void setLbIn() {
        if (mode) {
            mode=false;
            textActiveMode.setText(R.string.lb_in);
        }
        else {
            if (toastObject!=null) toastObject.cancel();
            toastObject = Toast.makeText(this,  R.string.already_set, Toast.LENGTH_SHORT);
            toastObject.show();
        }
    }

    @OnClick(R.id.buttonKgM)
    public void setKgM() {
        if (!mode) {
            mode=true;
            textActiveMode.setText(R.string.kg_m);
        }
        else {
            if (toastObject!=null) toastObject.cancel();
            toastObject = Toast.makeText(this,  R.string.already_set, Toast.LENGTH_SHORT);
            toastObject.show();
        }
    }

    private void setBMI() {
        if (BMI <= 15) {
            textCategory.setText(R.string.very_severely_underweight);
            textCategory.setTextColor(Color.parseColor("#ff0000"));
            textBMICalculated.setTextColor(Color.parseColor("#ff0000"));
        }
        else if (BMI <= 16) {
            textCategory.setText(R.string.severely_underweight);
            textCategory.setTextColor(Color.parseColor("#ff8000"));
            textBMICalculated.setTextColor(Color.parseColor("#ff8000"));
        }
        else if (BMI <= 18.5) {
            textCategory.setText(R.string.underweight);
            textCategory.setTextColor(Color.parseColor("#ffff00"));
            textBMICalculated.setTextColor(Color.parseColor("#ffff00"));
        }
        else if (BMI <= 25) {
            textCategory.setText(R.string.healthy_weight);
            textCategory.setTextColor(Color.parseColor("#00ff00"));
            textBMICalculated.setTextColor(Color.parseColor("#00ff00"));
        }
        else if (BMI <= 30) {
            textCategory.setText(R.string.overweight);
            textCategory.setTextColor(Color.parseColor("#ffff00"));
            textBMICalculated.setTextColor(Color.parseColor("#ffff00"));
        }
        else if (BMI <= 35) {
            textCategory.setText(R.string.moderately_obese);
            textCategory.setTextColor(Color.parseColor("#ff8000"));
            textBMICalculated.setTextColor(Color.parseColor("#ff8000"));
        }
        else if (BMI <= 40) {
            textCategory.setText(R.string.severely_obese);
            textCategory.setTextColor(Color.parseColor("#ff4000"));
            textBMICalculated.setTextColor(Color.parseColor("#ff4000"));
        }
        else {
            textCategory.setText(R.string.very_severely_obese);
            textCategory.setTextColor(Color.parseColor("#ff0000"));
            textBMICalculated.setTextColor(Color.parseColor("#ff0000"));
        }
        textBMICalculated.setText(String.format("%.2f", BMI));
    }
}
