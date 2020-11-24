package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "sss";
    private ImageView curCard1;
    private ImageView  curCard2;
    private Card card1;
    private Card card2;

    private TextView num_of_wins_player1;
    private TextView num_of_wins_player2;
    private Deck deck=new Deck();

    private int player1_count_wins=0;
    private int player2_count_wins=0;
    private int winner;
    private int count=0;
    private ImageButton play_BTN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate:creating ");
        curCard1 = findViewById(R.id.IMG_card1);
        curCard2 = findViewById(R.id.IMG_card2);
        num_of_wins_player1 = findViewById(R.id.game_LBL_leftScore);
        num_of_wins_player2 = findViewById(R.id.game_LBL_rightScore);
        play_BTN= findViewById(R.id.game_IMGBTN_play);
        play_BTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    count++;
                    if(count==27){
                        second_Activity();
                    }
                    winner = startGame(deck.getCards());
                    setWinner();
                }

            });

    }

    public void second_Activity()
    {   String winner;
        Intent myIntent = new Intent(MainActivity.this, Winner_Activity.class);
        if(player1_count_wins>player2_count_wins)
            winner="player 1 with "+player1_count_wins+" score";
        else if(player1_count_wins<player2_count_wins)
            winner="player 2 with "+player2_count_wins+" score";
        else
            winner="equals scores!! they both win with:"+player1_count_wins;

        myIntent.putExtra(Winner_Activity.EXTRA_COUNT, winner);
        startActivity(myIntent);
        finish();
    }







    public void setWinner(){
        if(winner==1) {
            player1_count_wins++;
            num_of_wins_player1.setText(" "+player1_count_wins);
        }else if(winner==2) {
            player2_count_wins++;
            num_of_wins_player2.setText(" "+player2_count_wins);
        }else{
            player1_count_wins++;
            player2_count_wins++;
            num_of_wins_player1.setText(" "+player1_count_wins);
            num_of_wins_player2.setText(" "+player2_count_wins);

        }
    }
    public int startGame(ArrayList<Card> deck){
        //get a random 2 cards from the deck that left while we have a cards in the deck
        //remove from the deck the card we already play .
        //the function return the number of the winner.
        int winner;
        card1 = getRandomCard(deck);
        deck.remove(card1);
        card2 = getRandomCard(deck);
        deck.remove(card2);
        //set the random cards to the viewImage
        curCard1.setImageResource(card1.getCard_img_id());
        curCard2.setImageResource(card2.getCard_img_id());

        winner=compareCard(card1,card2);
        return winner;
    }
    public int compareCard(Card card1,Card card2){
        //compare between two cards by rank
        //return: 0 if equals,1 if card1 bigger,2 if card2 bigger
        if(card1.getRank()==card2.getRank())
            return 0;
        else if(card1.getRank().ordinal()>card2.getRank().ordinal())
            return 1;
        else
            return 2;
    }

    public Card getRandomCard(ArrayList<Card>curDeck){
        int rnd = new Random().nextInt(curDeck.size());
        return curDeck.get(rnd);
    }

    @Override
    protected void onStart() {
        Log.d("sss", "onStart");
        super.onStart();
    }
    @Override
    protected void onResume() {
        Log.d("sss", "onResume");
        super.onResume();
    }
    @Override
    protected void onPause() {
        Log.d("sss", "onPause");
        super.onPause();
    }


    @Override
    protected void onStop() {
        Log.d("sss", "onStop");
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        Log.d("sss", "onDestory");
        super.onDestroy();
    }






}