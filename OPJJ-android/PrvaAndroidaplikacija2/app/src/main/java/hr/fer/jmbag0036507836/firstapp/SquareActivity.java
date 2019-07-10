package hr.fer.jmbag0036507836.firstapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SquareActivity extends AppCompatActivity {

    private TextView tvBroj;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_square);

        tvBroj = findViewById(R.id.result);
        btnBack = findViewById(R.id.back);

        Intent dobivenaPoruka = getIntent();
        tvBroj.setText(Integer.toString(dobivenaPoruka.getExtras().getInt("suma")));

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int broj = Integer.parseInt(tvBroj.getText().toString());
                int kvadrat = broj*broj;

                Intent poruka = new Intent();
                Bundle podaci = new Bundle();
                podaci.putInt("kvadrat", kvadrat);
                poruka.putExtras(podaci);
                setResult(RESULT_OK, poruka);
                finish();
            }
        });
    }

}
