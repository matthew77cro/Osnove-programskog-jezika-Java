package hr.fer.jmbag0036507836.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.fer.jmbag0036507836.firstapp.data.UserInfo;

public class ShowUserActivity extends AppCompatActivity {

    @BindView(R.id.ime)
    public TextView tvIme;

    @BindView(R.id.prezime)
    public TextView tvPrezime;

    @BindView(R.id.slika)
    public ImageView img;

    @BindView(R.id.back)
    public Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user);
        ButterKnife.bind(this);

        Intent poruka = getIntent();
        UserInfo usr = (UserInfo) poruka.getExtras().getSerializable("usr");

        tvIme.setText(String.format(getString(R.string.firstNamePlaceholder), usr.getFirstName()));
        tvPrezime.setText(String.format(getString(R.string.lastNamePlaceholder), usr.getLastName()));

        Glide.with(this).load(usr.getAvatar()).into(img);
    }

    @OnClick(R.id.back)
    public void onBtnPress() {
        finish();
    }
}
