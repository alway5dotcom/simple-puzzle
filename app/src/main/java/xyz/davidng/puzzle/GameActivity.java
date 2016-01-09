package xyz.davidng.puzzle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class GameActivity extends Activity {

    private static final Integer[] goal = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 0};
    private ArrayList<Integer> cells = new ArrayList<Integer>();
    private Boolean unmovable = false;
    private TextView tvCounter;
    private Button[] btn;
    private Button btnShareNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        tvCounter = (TextView) findViewById(R.id.tvCounter);
        btnShareNow = (Button)findViewById(R.id.btnShareNow);
        btnShareNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvCounter.setText("200");
                won();
            }
        });
        btn = initButtons();

        for (int i = 0; i < 9; i++) {
            this.cells.add(i);
        }

        Collections.shuffle(this.cells);

        drawGrid();

        for (int i = 1; i < 9; i++) {
            btn[i].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    move((Button) v);
                }
            });
        }
        tvCounter.setText("0");
    }


    public Button[] initButtons() {
        Button[] b = new Button[9];
        b[0] = (Button)findViewById(R.id.btn0);
        b[1] = (Button)findViewById(R.id.btn1);
        b[2] = (Button)findViewById(R.id.btn2);
        b[3] = (Button)findViewById(R.id.btn3);
        b[4] = (Button)findViewById(R.id.btn4);
        b[5] = (Button)findViewById(R.id.btn5);
        b[6] = (Button)findViewById(R.id.btn6);
        b[7] = (Button)findViewById(R.id.btn7);
        b[8] = (Button)findViewById(R.id.btn8);
        return b;
    }

    public void move(final Button b) {
        unmovable = true;
        int b_text, b_pos, zuk_pos;
        b_text = Integer.parseInt(b.getText().toString());
        b_pos = findPosition(b_text);
        zuk_pos = findPosition(0);
        switch (zuk_pos) {
            case (0):
                if (b_pos == 1 || b_pos == 3)
                    unmovable = false;
                break;
            case (1):
                if (b_pos == 0 || b_pos == 2 || b_pos == 4)
                    unmovable = false;
                break;
            case (2):
                if (b_pos == 1 || b_pos == 5)
                    unmovable = false;
                break;
            case (3):
                if (b_pos == 0 || b_pos == 4 || b_pos == 6)
                    unmovable = false;
                break;
            case (4):
                if (b_pos == 1 || b_pos == 3 || b_pos == 5 || b_pos == 7)
                    unmovable = false;
                break;
            case (5):
                if (b_pos == 2 || b_pos == 4 || b_pos == 8)
                    unmovable = false;
                break;
            case (6):
                if (b_pos == 3 || b_pos == 7)
                    unmovable = false;
                break;
            case (7):
                if (b_pos == 4 || b_pos == 6 || b_pos == 8)
                    unmovable = false;
                break;
            case (8):
                if (b_pos == 5 || b_pos == 7)
                    unmovable = false;
                break;
        }

        while (unmovable) {
            // do something when you can not move.
            return;
        }
        cells.remove(b_pos);
        cells.add(b_pos, 0);
        cells.remove(zuk_pos);
        cells.add(zuk_pos, b_text);
        drawGrid();
        tvCounter.setText(String.valueOf(Integer.parseInt(tvCounter.getText().toString()) + 1));

        for (int i = 0; i < 9; i++) {
            if (cells.get(i) != goal[i]) {
                return;
            }
        }
        won();
    }

    public void won(){
        Intent i = new Intent(GameActivity.this, WonActivity.class);
        i.putExtra("moves", tvCounter.getText().toString());
        startActivity(i);
        finish();
    }
    public void drawGrid() {
        for (int i = 0; i < 9; i++) {
            int text = cells.get(i);
            AbsoluteLayout.LayoutParams absParams =
                    (AbsoluteLayout.LayoutParams) btn[text].getLayoutParams();
            switch (i) {
                case (0):
                    absParams.x = 5;
                    absParams.y = 5;
                    btn[text].setLayoutParams(absParams);
                    break;
                case (1):
                    absParams.x = 110;
                    absParams.y = 5;
                    btn[text].setLayoutParams(absParams);
                    break;
                case (2):
                    absParams.x = 215;
                    absParams.y = 5;
                    btn[text].setLayoutParams(absParams);
                    break;
                case (3):
                    absParams.x = 5;
                    absParams.y = 110;
                    btn[text].setLayoutParams(absParams);
                    break;
                case (4):
                    absParams.x = 110;
                    absParams.y = 110;
                    btn[text].setLayoutParams(absParams);
                    break;
                case (5):
                    absParams.x = 215;
                    absParams.y = 110;
                    btn[text].setLayoutParams(absParams);
                    break;
                case (6):
                    absParams.x = 5;
                    absParams.y = 215;
                    btn[text].setLayoutParams(absParams);
                    break;
                case (7):
                    absParams.x = 110;
                    absParams.y = 215;
                    btn[text].setLayoutParams(absParams);
                    break;
                case (8):
                    absParams.x = 215;
                    absParams.y = 215;
                    btn[text].setLayoutParams(absParams);
                    break;
            }
        }
    }

    public int findPosition(int element) {
        int i = 0;
        for (i = 0; i < 9; i++) {
            if (cells.get(i) == element) {
                break;
            }
        }
        return i;
    }
}