package com.vtkhiem.scanqrcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private Button btnClick ;
    private TextView tvQRCode;
    private ImageView imgApp;
    private TextView tvDiaChi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    protected void initView() {
        btnClick = findViewById(R.id.btnClick);
        tvQRCode = findViewById(R.id.tvQRCode);
        imgApp = findViewById(R.id.imageApp);
        tvDiaChi = findViewById(R.id.tvDiaChi);
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentIntegrator.initiateScan();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Picasso.with(this).load(result.getContents()).into(imgApp);
                try {
                    JSONObject jsonObject = new JSONObject(result.getContents());
                    tvDiaChi.setText(jsonObject.getString("Dia chi"));
                    tvQRCode.setText(jsonObject.getString("name"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}