package com.anafund.gameofcardscolors;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MemoryGameActivity extends AppCompatActivity implements View.OnClickListener {


    private GameStartDialog dialog;
    private Chronometer chTimer;
    private int numberOfElements;
    private GridMemoryButtons[] buttons;
    private int[] buttonIdLocation;
    private int[] buttonGraphics;

    private GridMemoryButtons selectedButton1;
    private GridMemoryButtons selectedButton2;

    private boolean isBusy = false;

    private MediaPlayer mediaPlayer;
    private TextView tvSetScore;
    private TextView tvSetHighScore;
    long score;
    long bestScore;
    private SharedPreferences sharedPreferences;
    private static final String HIGH_SCORE = "high_score";
    public static final String PREFS_FILE = "prefsFile";


    public long count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_game);

        initWidgets();
        openDialog();
        initGrid();

    }

    private void openDialog() {

        GameStartDialog dialog = new GameStartDialog(MemoryGameActivity.this);
        dialog.show();
        tvSetHighScore.setText(Long.toString(bestScore));
    }


    private void initWidgets() {

        chTimer = (Chronometer) findViewById(R.id.chTimer);
        tvSetScore = (TextView) findViewById(R.id.tvSetScore);
        tvSetScore.setText(Long.toString(0));
        tvSetHighScore = (TextView) findViewById(R.id.tvSetHighScore);
        SharedPreferences sharedPreferences = this.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        bestScore = sharedPreferences.getLong(HIGH_SCORE, 0);


        mediaPlayer = MediaPlayer.create(this, R.raw.zvuk2);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();


    }

    private void initGrid() {
        GridLayout gridLayout = (GridLayout) findViewById(R.id.glGrid_4x4);

        int numColums = gridLayout.getColumnCount();
        int numRows = gridLayout.getRowCount();

        numberOfElements = numColums * numRows;

        buttons = new GridMemoryButtons[numberOfElements];

        buttonGraphics = new int[numberOfElements / 2];

        buttonGraphics[0] = R.drawable.ic_baby;
        buttonGraphics[1] = R.drawable.ic_blue;
        buttonGraphics[2] = R.drawable.ic_green;
        buttonGraphics[3] = R.drawable.ic_neon;
        buttonGraphics[4] = R.drawable.ic_pink;
        buttonGraphics[5] = R.drawable.ic_purple;
        buttonGraphics[6] = R.drawable.ic_red;
        buttonGraphics[7] = R.drawable.ic_yellow;

        buttonIdLocation = new int[numberOfElements];

        shuffleButtonGraphics();

        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numColums; c++) {

                GridMemoryButtons tempButton = new GridMemoryButtons(this, r, c, buttonGraphics[buttonIdLocation[r * numColums + c]]);
                tempButton.setId(View.generateViewId());
                tempButton.setOnClickListener(this);
                buttons[r * numColums + c] = tempButton;
                gridLayout.addView(tempButton);

            }
        }
    }

    private void shuffleButtonGraphics() {
        Random rand = new Random();
        for (int i = 0; i < numberOfElements; i++) {

            buttonIdLocation[i] = i % (numberOfElements / 2);

        }

        for (int i = 0; i < numberOfElements; i++) {
            int temp = buttonIdLocation[i];
            int swapIndex = rand.nextInt(16);

            buttonIdLocation[i] = buttonIdLocation[swapIndex];

            buttonIdLocation[swapIndex] = temp;
        }

    }


    private void Counter() {
        count++;
    }

    private void showToast() {

        Toast toast = Toast.makeText(this, R.string.victory, Toast.LENGTH_LONG);
        LinearLayout toastLayout = (LinearLayout) toast.getView();
        TextView toastTV = (TextView) toastLayout.getChildAt(0);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toastTV.setTextSize(30);
        toast.show();

    }


    private void displayScores() {
        score = (200 - (SystemClock.elapsedRealtime() - chTimer.getBase()) / 1000);
        tvSetScore.setText(Long.toString(score));
        tvSetHighScore.setText(Long.toString(bestScore));

    }


    @Override
    public void onClick(View view) {

        SharedPreferences sharedPreferences = this.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);

        chTimer.start();

        if (isBusy)
            return;

        GridMemoryButtons button = (GridMemoryButtons) view;


        if (button.isMatched())
            return;


        if (selectedButton1 == null) {
            selectedButton1 = button;
            selectedButton1.flip();
            return;
        }

        if (selectedButton1.getId() == button.getId()) {
            return;
        }


        if (selectedButton1.getFrontDrawableId() == button.getFrontDrawableId())

        {
            button.flip();
            button.setMatched(true);
            selectedButton1.setMatched(true);

            selectedButton1.setEnabled(false);
            button.setEnabled(false);

            selectedButton1 = null;

            Counter();


            if (count >= 8) {
                chTimer.stop();

                showToast();
                displayScores();


                if (bestScore > score) {
                    tvSetHighScore.setText(Long.toString(bestScore));
                } else {
                    bestScore = score;
                    tvSetHighScore.setText(Long.toString(bestScore));
                    sharedPreferences.edit().putLong(HIGH_SCORE, bestScore).apply();
                }
                displayScores();
            }
            return;


        } else {

            selectedButton2 = button;
            selectedButton2.flip();
            isBusy = true;


            final Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    selectedButton2.flip();
                    selectedButton1.flip();
                    selectedButton1 = null;
                    selectedButton2 = null;
                    isBusy = false;
                }
            }, 600);


        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu, menu);
        setTitle(getString(R.string.game_of_cards));


        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.btnMusic:
                mediaPlayer.start();
                break;
            case R.id.btnMute:
                mediaPlayer.pause();
                break;
        }

        return true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();

    }

    @Override
    protected void onResume() {
        super.onResume();

        mediaPlayer.start();


    }

}


