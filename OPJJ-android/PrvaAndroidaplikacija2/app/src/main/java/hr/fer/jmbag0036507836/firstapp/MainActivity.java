package hr.fer.jmbag0036507836.firstapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import hr.fer.jmbag0036507836.firstapp.data.ImageResponse;
import hr.fer.jmbag0036507836.firstapp.data.UserInfo;
import hr.fer.jmbag0036507836.firstapp.networking.FetcherService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText edtFirstNumber;
    private EditText edtSecondNumber;
    private Button btnCalculate;
    private TextView tvResult;
    private Button btnCalculate2;
    private Button btnSend1;
    private Button btnSend2;
    private Button btnSend3;
    private Button btnGet1;
    private ImageView slika;
    private Button btnGet2;

    private static final int SQUARE_MSG_CODE = 100;

    private Retrofit retrofit;
    private FetcherService fetcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("App", "Pozvan onCreate()");

        retrofit = new Retrofit.Builder().baseUrl("https://ferko.fer.hr").addConverterFactory(GsonConverterFactory.create()).build();
        fetcher = retrofit.create(FetcherService.class);

        edtFirstNumber = findViewById(R.id.firstNumber);
        edtSecondNumber = findViewById(R.id.secondNumber);
        btnCalculate = findViewById(R.id.calculate);
        tvResult = findViewById(R.id.result);
        btnCalculate2 = findViewById(R.id.calculate2);
        btnSend1 = findViewById(R.id.send1);
        btnSend2 = findViewById(R.id.send2);
        btnSend3 = findViewById(R.id.send3);
        btnGet1 = findViewById(R.id.get1);
        slika = findViewById(R.id.slika);
        btnGet2 = findViewById(R.id.get2);

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

        btnSend1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageResponse ir = new ImageResponse();
                ir.setUrl("http://neki.host.com/nesto");

                Gson gson = new Gson();
                String mojObjekt = gson.toJson(ir);

                Intent poruka = new Intent(MainActivity.this, Show1Activity.class);
                Bundle podaci = new Bundle();
                podaci.putString("mojObjekt", mojObjekt);
                poruka.putExtras(podaci);
                startActivity(poruka);
            }
        });

        btnSend2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageResponse ir = new ImageResponse();
                ir.setUrl("http://neki2.host.com/nesto");

                Intent poruka = new Intent(MainActivity.this, Show2Activity.class);
                Bundle podaci = new Bundle();
                podaci.putSerializable("mojObjekt", ir);
                poruka.putExtras(podaci);
                startActivity(poruka);
            }
        });

        btnSend3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageResponse ir = new ImageResponse();
                ir.setUrl("http://neki3.host.com/nesto");

                Intent poruka = new Intent(MainActivity.this, Show3Activity.class);
                Bundle podaci = new Bundle();
                podaci.putSerializable("mojObjekt", ir);
                poruka.putExtras(podaci);
                startActivity(poruka);
            }
        });

        btnGet1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetcher.getImage().enqueue(new Callback<ImageResponse>() {
                    @Override
                    public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                        ImageResponse ir = response.body();
                        Glide.with(MainActivity.this).load(ir.getUrl()).into(slika);
                        Toast.makeText(MainActivity.this, ir.getUrl(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<ImageResponse> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        btnGet2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetcher.getUserInfo().enqueue(new Callback<UserInfo>() {
                    @Override
                    public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                        UserInfo usr = response.body();

                        Intent poruka = new Intent(MainActivity.this, ShowUserActivity.class);
                        Bundle podaci = new Bundle();
                        podaci.putSerializable("usr", usr);
                        poruka.putExtras(podaci);
                        startActivity(poruka);
                    }

                    @Override
                    public void onFailure(Call<UserInfo> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_LONG).show();
                    }
                });
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
