package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    boolean gameActive = true;




    // Player Representation
    // 0 - X
    // 1 - O

    int activePlayer = 0;
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    // State meanings
    // 0 - X
    // 1 - O
    // 2 - NULL

    int[][] winPositions = { {0,1,2}, {3,4,5}, {6,7,8},
                             {0,3,6}, {1,4,7}, {2,5,8},
                             {0,4,8}, {2,4,6}
    };

    public void playerTap(View view){
        MediaPlayer click2Aud;
        click2Aud = MediaPlayer.create(MainActivity.this,R.raw.click2);
        click2Aud.start();

        String winnerStr = null;
        if(!gameActive){
            gameReset(view);
            return;
        }

        ImageView img = (ImageView)view;
        int tappedImg = Integer.parseInt(img.getTag().toString());

        if (gameState[tappedImg]==2){
            gameState[tappedImg] = activePlayer;
            img.setTranslationY(-1000f);
            if(activePlayer==0){
                img.setImageResource(R.drawable.x);
                activePlayer = 1;
                String str1 = "O's Turn - Tap To Play";
                TextView status = findViewById(R.id.status);
                status.setText(str1);
                status.setTextColor(Color.parseColor("#0F9D58"));
            }else{
                img.setImageResource(R.drawable.to);
                activePlayer = 0;
                String str2 = "X's Turn - Tap To Play";
                TextView status = findViewById(R.id.status);
                status.setText(str2);
                status.setTextColor(Color.parseColor("#0F9D58"));
            }
            img.animate().translationYBy(1000f).setDuration(300);
        }
        // Check if any player has won
        for(int[] winPosition: winPositions){
            if(gameState[winPosition[0]] == gameState[winPosition[1]] &&
               gameState[winPosition[1]] == gameState[winPosition[2]] &&
               gameState[winPosition[0]] != 2){
                // somebody has won!! find out who?
                if(gameState[winPosition[0]] == 0){
                    winnerStr = "X has WON!";
                }else if(gameState[winPosition[0]] == 1){
                    winnerStr = "O has WON!";
                }

                // Update the status bar for winner announcement..
                click2Aud.stop();
                TextView status = findViewById(R.id.status);
                status.setText(winnerStr);
                String str3 = "Tap to Restart";
                TextView bottomStatus = findViewById(R.id.bottomStatus);
                bottomStatus.setText(str3);
                MediaPlayer clickAud;
                clickAud=MediaPlayer.create(MainActivity.this,R.raw.click);
                clickAud.start();
                gameActive = false;
                return;
            }
        }

        int add = 0;
        for(int i=0; i<gameState.length; i++){
            if(gameState[i] != 2){
                add++;
            }
        }

        if(add==9){
            winnerStr = "Tied!";
            click2Aud.stop();
            MediaPlayer errorAud;
            errorAud=MediaPlayer.create(MainActivity.this,R.raw.error);
            errorAud.start();

            String str4 = "Tap to Restart";
            TextView bottomStatus = findViewById(R.id.bottomStatus);
            bottomStatus.setText(str4);
            add=0;
            TextView status = findViewById(R.id.status);
            status.setText(winnerStr);
            gameActive = false;
        }
    }

    public void gameReset(View view){
        gameActive = true;
        activePlayer = 0;
        for(int i=0; i<gameState.length; i++){
            gameState[i] = 2;
        }

        TextView bottomStatus = findViewById(R.id.bottomStatus);
        bottomStatus.setText(null);

        ((ImageView)findViewById(R.id.imago0)).setImageResource(0);
        ((ImageView)findViewById(R.id.imago1)).setImageResource(0);
        ((ImageView)findViewById(R.id.imago2)).setImageResource(0);
        ((ImageView)findViewById(R.id.imago3)).setImageResource(0);
        ((ImageView)findViewById(R.id.imago4)).setImageResource(0);
        ((ImageView)findViewById(R.id.imago5)).setImageResource(0);
        ((ImageView)findViewById(R.id.imago6)).setImageResource(0);
        ((ImageView)findViewById(R.id.imago7)).setImageResource(0);
        ((ImageView)findViewById(R.id.imago8)).setImageResource(0);

        String str4 = "X's Turn - Tap To Play";
        TextView status = findViewById(R.id.status);
        status.setText(str4);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}