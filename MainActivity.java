package com.example.ebillcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextUnits;
    private EditText editTextRebate;
    private Button buttonCalculate;
    private Button buttonClear;
    private TextView textViewTotalCharges;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUnits = findViewById(R.id.editTextUnits);
        editTextRebate = findViewById(R.id.editTextRebate);
        buttonCalculate = findViewById(R.id.buttonCalculate);
        buttonClear = findViewById(R.id.buttonClear);
        textViewTotalCharges = findViewById(R.id.textViewTotalCharges);
        textViewResult = findViewById(R.id.textViewResult);

        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBill();
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearInputsAndOutputs();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                Intent aboutIntent = new Intent(this, AboutActivity.class);
                startActivity(aboutIntent);
                break;
            case R.id.instruction:
                Intent instructionIntent = new Intent(this, InstructionActivity.class);
                startActivity(instructionIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void calculateBill() {
        String unitsString = editTextUnits.getText().toString().trim();
        String rebateString = editTextRebate.getText().toString().trim();

        if (unitsString.isEmpty()) {
            editTextUnits.setError("Please enter units used");
            return;
        }

        if (rebateString.isEmpty()) {
            editTextRebate.setError("Please enter rebate percentage");
            return;
        }

        double units = Double.parseDouble(unitsString);
        double rebate = Double.parseDouble(rebateString);

        double charges;

        if (units <= 200) {
            charges = units * 0.218;
        } else if (units <= 300) {
            charges = 200 * 0.218 + (units - 200) * 0.334;
        } else if (units <= 600) {
            charges = 200 * 0.218 + 100 * 0.334 + (units - 300) * 0.516;
        } else {
            charges = 200 * 0.218 + 100 * 0.334 + 300 * 0.516 + (units - 600) * 0.546;
        }

        double finalCost = charges - (charges * rebate / 100);

        textViewTotalCharges.setText("Total Charges: RM " + charges);
        textViewResult.setText("Final Cost: RM " + finalCost);
    }

    private void clearInputsAndOutputs() {
        editTextUnits.setText("");
        editTextRebate.setText("");
        textViewTotalCharges.setText("");
        textViewResult.setText("");
    }
}
