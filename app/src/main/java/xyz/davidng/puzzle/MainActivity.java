package xyz.davidng.puzzle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends Activity implements View.OnClickListener {
    private ImageButton play;
    private Button help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        play = (ImageButton)findViewById(R.id.btnPlay);
        help = (Button)findViewById(R.id.btnHelp);
        play.setOnClickListener(this);
        help.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnPlay:
                startActivity(new Intent(getApplicationContext(), GameActivity.class));
                break;
            case R.id.btnHelp:
                startActivity(new Intent(getApplicationContext(), Help.class));
                break;
        }
    }
}
