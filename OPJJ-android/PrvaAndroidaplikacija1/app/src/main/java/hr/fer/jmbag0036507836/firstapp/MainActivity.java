package hr.fer.jmbag0036507836.firstapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText edtFirstNumber;
    private EditText edtSecondNumber;
    private Button btnCalculate;
    private TextView tvResult;
    private Button btnCalculate2;

    private static final int SQUARE_MSG_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("App", "Pozvan onCreate()");

        edtFirstNumber = findViewById(R.id.firstNumber);
        edtSecondNumber = findViewById(R.id.secondNumber);
        btnCalculate = findViewById(R.id.calculate);
        tvResult = findViewById(R.id.result);
        btnCalculate2 = findViewById(R.id.calculate2);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int firstNumber = 0;
                int secondNumber = 0;
                try {
                    firstNumber = Integer.parseInt(edtFirstNumber.getText().toString());
                    secondNumber = Integer.parseInt(edtSecondNumber.getText().toString());
                } catch (NumberFormatException ex) {
                    Toast.makeText(MainActivity.this, "Invalid input!", Toast.LENGTH_LONG).show();
                    return;
                }

                int sum = firstNumber + secondNumber;
                tvResult.setText(Integer.toString(sum));
            }
        });

        btnCalculate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int firstNumber = 0;
                int secondNumber = 0;
                try {
                    firstNumber = Integer.parseInt(edtFirstNumber.getText().toString());
                    secondNumber = Integer.parseInt(edtSecondNumber.getText().toString());
                } catch (NumberFormatException ex) {
                    Toast.makeText(MainActivity.this, "Invalid input!", Toast.LENGTH_LONG).show();
                    return;
                }

                int sum = firstNumber + secondNumber;

                Intent poruka = new Intent(MainActivity.this, SquareActivity.class);
                Bundle podaci = new Bundle();
                podaci.putInt("suma", sum);
                poruka.putExtras(podaci);
                startActivityForResult(poruka, SQUARE_MSG_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode!=SQUARE_MSG_CODE) return;
        if(resultCode!=RESULT_OK) return;
        if(data==null || data.getExtras()==null) return; //BOLJE TOAST JER SMO DOBILI STATUS OK, A NEMA NISTA U PODACIMA

        int k = data.getExtras().getInt("kvadrat");
        tvResult.setText(Integer.toString(k));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("App", "Pozvan onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("App", "Pozvan onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("App", "Pozvan onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("App", "Pozvan onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("App", "Pozvan onDestroy()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("App", "Pozvan onRestart()");
    }
}
