package com.anafund.gameofcardscolors;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnPlay;
    private Button btnExit;
    private MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidgets();
        setUpListeners();
    }

    private void initWidgets() {
        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnExit = (Button) findViewById(R.id.btnExit);

        mediaPlayer = MediaPlayer.create(this, R.raw.zvuk1);
        mediaPlayer.start();
    }

    private void setUpListeners() {

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mediaPlayer.pause();


                Intent intent = new Intent(MainActivity.this, MemoryGameActivity.class);
                startActivity(intent);


            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mediaPlayer.stop();
                mediaPlayer.release();

                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu, menu);
        setTitle(getString(R.string.anas_fun_games));

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

    }

    @Override
    protected void onResume() {
        super.onResume();

        mediaPlayer.start();

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
                Intent intent = new Intent(MainActivity.this, MemoryGameActivity.class);
                startActivity(intent);
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mediaPlayer.stop();
                mediaPlayer.release();

                finish();

            }
        });

    }

}
