package hr.fer.jmbag0036507836.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.fer.jmbag0036507836.firstapp.data.ImageResponse;

public class Show3Activity extends AppCompatActivity {

    @BindView(R.id.url_labela)
    TextView tvUrlLabela;

    @BindView(R.id.back)
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show1);
        ButterKnife.bind(this);

        Intent poruka = getIntent();

        ImageResponse ir = (ImageResponse) poruka.getExtras().getSerializable("mojObjekt");

        tvUrlLabela.setText(ir.getUrl());
    }

    @OnClick(R.id.back)
    public void backButtonPressed() {
        finish();
    }
}
