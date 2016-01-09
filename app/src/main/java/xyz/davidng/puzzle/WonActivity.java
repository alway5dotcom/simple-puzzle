package xyz.davidng.puzzle;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class WonActivity extends Activity {
    private ImageView imgMedal;
    private TextView tvResult;
    private Button btnShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_won);
        imgMedal = (ImageView)findViewById(R.id.imageviewMedal);
        tvResult = (TextView)findViewById(R.id.tvResult);
        btnShare = (Button)findViewById(R.id.btnShare);

        int moveCounter = Integer.parseInt(getIntent().getStringExtra("moves"));
        if(moveCounter < 50){
            tvResult.setText("Gold Medal !");
            imgMedal.setImageResource(R.drawable.gold_medal);
        } else if(moveCounter >= 50 && moveCounter < 100){
            tvResult.setText("Silver Medal !");
            imgMedal.setImageResource(R.drawable.silver_medal);
        } else if(moveCounter >= 100 && moveCounter < 150){
            tvResult.setText("Bronze Medal !");
            imgMedal.setImageResource(R.drawable.bronze_medal);
        } else{
            tvResult.setText("Chicken !");
            imgMedal.setImageResource(R.drawable.chicken);
        }
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = takeScreenshot();
                saveBitmap(bitmap);

                if(Utils.isOnline(getApplicationContext())){
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();

                    Intent i = new Intent(WonActivity.this, FacebookShareImage.class);
                    i.putExtra("image", byteArray);
                    startActivity(i);
                    finish();
                } else{
                    Toast.makeText(getApplicationContext(), "Network is not available", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private Bitmap takeScreenshot() {
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }
    public void saveBitmap(Bitmap bitmap) {
        File imagePath = new File(Environment.getExternalStorageDirectory() + "/screenshot.png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("Holy shit", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("Holy shit", e.getMessage(), e);
        }
    }
}