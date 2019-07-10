package hr.fer.jmbag0036507836.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import hr.fer.jmbag0036507836.firstapp.data.ImageResponse;

public class Show2Activity extends AppCompatActivity {

    private TextView tvUrlLabela;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show1);

        tvUrlLabela = findViewById(R.id.url_labela);
        btnBack = findViewById(R.id.back);

        Intent poruka = getIntent();

        ImageResponse ir = (ImageResponse) poruka.getExtras().getSerializable("mojObjekt");

        tvUrlLabela.setText(ir.getUrl());

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
