package com.anafund.gameofcardscolors;

import android.app.Dialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

/**
 * Created by Ana on 12/12/2016.
 */

public class GameStartDialog extends Dialog {

        private Button btnYesDialog;
        private Button btnQuitDialog;
        public MemoryGameActivity memoryGameActivity;
        private MediaPlayer mediaPlayer;



        public GameStartDialog(MemoryGameActivity context) {
            super(context);
            this.memoryGameActivity = context;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.game_start);

            initWidgets();
            setUpListeners();
        }

        private void initWidgets() {
            btnYesDialog = (Button) findViewById(R.id.btnYesDialog);
            btnQuitDialog = (Button) findViewById(R.id.btnQuitDialog);

            mediaPlayer = new MediaPlayer();

        }

        private void setUpListeners() {
            btnYesDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();

                }
            });
            btnQuitDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dismiss();
                    mediaPlayer.stop();
                    mediaPlayer.release();

                    memoryGameActivity.onBackPressed();

                }
            });

        }


    }


