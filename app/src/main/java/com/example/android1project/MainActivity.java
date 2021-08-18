package com.example.android1project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

/**
 * Created by Sagi Vidal
 */
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String numberOfRows = "4",numberofCols = "4";
    EditText cols,rows;

    /**
     * create the UI of the MainActivity and send numberOfCards to the SecondActivity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);

        cols= (EditText)findViewById(R.id.cols);
        rows= (EditText)findViewById(R.id.rows);

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Sizes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        ImageView startBtn = (ImageView)findViewById(R.id.button);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isNumeric(rows.getText().toString()) || !isNumeric(cols.getText().toString()))
                    alert();
                else if(Integer.parseInt(rows.getText().toString()) == 0 || Integer.parseInt(cols.getText().toString()) == 0  )
                    alert();
                else if(Integer.parseInt(rows.getText().toString()) * Integer.parseInt(cols.getText().toString()) % 2 == 1)
                    alert();
                else if(Integer.parseInt(rows.getText().toString())* Integer.parseInt(cols.getText().toString()) >16)
                    alert();
                else{
                    Intent intent = new Intent(getBaseContext(), SecondActivity.class);
                    intent.putExtra("Rows", rows.getText().toString());
                    intent.putExtra("Cols", cols.getText().toString());
                    startActivity(intent);
                }

            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position == 0){
            numberOfRows = "2";
            numberofCols = "2";

        }
        if(position == 1){
            numberOfRows = "4";
            numberofCols = "4";
        }
        cols.setText(numberofCols);
        rows.setText(numberOfRows);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    void alert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(MainActivity.this);
        View view = layoutInflaterAndroid.inflate(R.layout.alert_invalid_numbers, null);
        builder.setView(view);
        builder.setCancelable(false);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        view.findViewById(R.id.return_to_main).setOnClickListener(l -> alertDialog.dismiss());
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }


}