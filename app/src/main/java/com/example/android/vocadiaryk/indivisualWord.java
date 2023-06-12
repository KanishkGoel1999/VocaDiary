package com.example.android.vocadiaryk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Locale;
import java.util.Random;

import com.example.android.vocadiaryk.data.VocaContract;
import com.example.android.vocadiaryk.data.VocaContract.VocaEntry;

import org.w3c.dom.Text;


public class indivisualWord extends AppCompatActivity {

    private TextToSpeech t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indivisual_word);

        t1 = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        final String[] projection = {VocaEntry._ID, VocaEntry.COLUMN_WORD, VocaEntry.COLUMN_STAGE, VocaEntry.COLUMN_MEANING, VocaEntry.COLUMN_MNEMONIC, VocaEntry.COLUMN_SENTENCE };
        final Cursor cursor = getContentResolver().query(VocaEntry.CONTENT_URI , projection,null,null,null);

        final int max = cursor.getCount();

        final TextView meaningIView = (TextView) findViewById(R.id.flash_meaning);
        final TextView mnemonicIView = (TextView) findViewById(R.id.flash_mnemonic);


        Random random = new Random();
        final int randomNumber = random.nextInt(max);

        displayWord(randomNumber, cursor, meaningIView, mnemonicIView);


        final TextView tapView = ( TextView) findViewById(R.id.tap_to_see);
        final Button knownButton = (Button) findViewById(R.id.knownWord);
        final Button unknownButton = (Button) findViewById(R.id.unknownWord);



        knownButton.setVisibility(View.INVISIBLE);
        unknownButton.setVisibility(View.INVISIBLE);
        meaningIView.setVisibility(View.INVISIBLE);
        mnemonicIView.setVisibility(View.INVISIBLE);

        tapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                meaningIView.setVisibility(View.VISIBLE);
                mnemonicIView.setVisibility(View.VISIBLE);
                tapView.setVisibility(View.INVISIBLE);
                knownButton.setVisibility(View.VISIBLE);
                unknownButton.setVisibility(View.VISIBLE);

            }
        });

        knownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursorKnown= getContentResolver().query(VocaEntry.CONTENT_URI , projection,null,null,null);


                Random random = new Random();
                final int randomNumberKnown = random.nextInt(max);

                //changeStage(displayWord(randomNumberKnown, cursorKnown));

                displayWord(randomNumberKnown, cursorKnown, meaningIView, mnemonicIView);
                tapView.setVisibility(View.VISIBLE);
                knownButton.setVisibility(View.INVISIBLE);
                unknownButton.setVisibility(View.INVISIBLE);
                meaningIView.setVisibility(View.INVISIBLE);
                mnemonicIView.setVisibility(View.INVISIBLE);
                cursorKnown.close();
            }
        });

        unknownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                final int randomNumberUnknown = random.nextInt(max);

                displayWord(randomNumberUnknown, cursor, meaningIView, mnemonicIView);

                meaningIView.setVisibility(View.INVISIBLE);
                mnemonicIView.setVisibility(View.INVISIBLE);
                tapView.setVisibility(View.VISIBLE);
                knownButton.setVisibility(View.INVISIBLE);
                unknownButton.setVisibility(View.INVISIBLE);
            }
        });


    }


    private void changeStage(int r) {

        ContentValues values = new ContentValues();

        values.put(VocaEntry.COLUMN_STAGE, VocaEntry.MASTERED);

        int id = getContentResolver().update(ContentUris.withAppendedId(VocaEntry.CONTENT_URI, r), values, null, null);

        String[] selectionArgs = {Integer.toString(r)};

        //int id = getContentResolver().update(VocaEntry.CONTENT_URI, values, VocaEntry._ID, selectionArgs);

        }




    private int displayWord(int r, Cursor cursor, TextView meaningIView, TextView mnemonicIView) {
        //String word = getIntent().getStringExtra("word");

        TextView wordIView = (TextView) findViewById(R.id.wordI);

        cursor.moveToPosition(r);

        final String word = cursor.getString(cursor.getColumnIndex(VocaEntry.COLUMN_WORD));
       // int stageN = cursor.getInt(cursor.getColumnIndex(VocaEntry.COLUMN_STAGE));
        String meaning = cursor.getString(cursor.getColumnIndex(VocaEntry.COLUMN_MEANING));
        String mnemonic = cursor.getString(cursor.getColumnIndex(VocaEntry.COLUMN_MNEMONIC));

        meaningIView.setText("Meaning: " + meaning);
        mnemonicIView.setText("Mnemonic: " + mnemonic);


        wordIView.setText(word);

        final ImageButton voice = (ImageButton) findViewById(R.id.voice_card_flash);
        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t1.speak(word, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        return r;
    }

}
