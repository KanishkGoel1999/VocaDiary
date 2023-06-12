package com.example.android.vocadiaryk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;

import com.example.android.vocadiaryk.data.VocaContract;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;

import android.database.Cursor;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ImageButton;

import com.example.android.vocadiaryk.data.VocaContract.VocaEntry;

import java.util.Locale;

public class MyList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);


        // Setup FAB to open EditorActivity
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                Intent intent = new Intent(MyList.this, AddWord.class);
                startActivity(intent);
            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();
        displayWords();


    }

    private String cWord;
    private TextToSpeech t1;


    private void displayWords(){

        final String[] projection = {VocaEntry._ID, VocaEntry.COLUMN_WORD, VocaEntry.COLUMN_STAGE, VocaEntry.COLUMN_MEANING, VocaEntry.COLUMN_MNEMONIC, VocaEntry.COLUMN_SENTENCE };

        final Cursor cursor = getContentResolver().query(VocaEntry.CONTENT_URI, projection, null, null, null);
        VocaCursorAdapter vocaCursorAdapter = new VocaCursorAdapter(this, cursor);

        //cursor.moveToFirst();
        //getCursorValue(cursor);
       //final String cWord = cursor.getString(cursor.getColumnIndex(VocaEntry.COLUMN_WORD));

       // final String cStage = cursor.getString(cursor.getColumnIndex(VocaEntry.COLUMN_STAGE));
        View emptyView = (View)findViewById(R.id.empty_view);
        final ListView listView = (ListView)findViewById(R.id.word_list);


        listView.setEmptyView(emptyView);
        listView.setAdapter(vocaCursorAdapter);
        displayCard(listView, projection);


    }


    private void displayCard(final ListView listView, final String[] projection){

        final CardView cardView = (CardView) findViewById(R.id.card);
        final Button doneButton = (Button) findViewById(R.id.done_card);
        final Button deleteButton = (Button) findViewById(R.id.delete_card);
        final ImageButton voiceButton = (ImageButton) findViewById(R.id.voice_card_myList);
        t1 = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {


                TextView cardWord = (TextView) findViewById(R.id.cardText);
                TextView cardMnemonic = (TextView) findViewById(R.id.card_mnemonic);
                TextView cardMeaning = (TextView) findViewById(R.id.card_meaning);
                TextView cardSentence = (TextView) findViewById(R.id.card_sentence);



                final Cursor cursorUp = getContentResolver().query(VocaEntry.CONTENT_URI, projection, null, null, null);
                cursorUp.moveToPosition(i);
                //meaningEdit.setText(cursorUp.getString(cursorUp.getColumnIndex(VocaEntry.COLUMN_MEANING)));
                //cursorUp.moveToPosition(i);
                final String word = cursorUp.getString(cursorUp.getColumnIndex(VocaEntry.COLUMN_WORD));
                cardWord.setText(word);
                cardMeaning.setText("MEANING: " + cursorUp.getString(cursorUp.getColumnIndex(VocaEntry.COLUMN_MEANING)));
                cardMnemonic.setText("MNEMONIC: " +cursorUp.getString(cursorUp.getColumnIndex(VocaEntry.COLUMN_MNEMONIC)));
                cardSentence.setText("SENTENCE: " +cursorUp.getString(cursorUp.getColumnIndex(VocaEntry.COLUMN_SENTENCE)));

                listView.setVisibility(View.INVISIBLE);
                cardView.setVisibility(View.VISIBLE);


                doneButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        cardView.setVisibility(View.INVISIBLE);
                        listView.setVisibility(View.VISIBLE);
                    }
                });

                voiceButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        t1.speak(word, TextToSpeech.QUEUE_FLUSH, null);
                    }
                });

                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int j = getContentResolver().delete(ContentUris.withAppendedId(VocaEntry.CONTENT_URI, i), null, null);
                    }
                });

                cursorUp.close();
            }
        });



    }


    private void getCursorValue(Cursor cursor) {
        cursor.moveToFirst();
        cWord = cursor.getString(cursor.getColumnIndex(VocaEntry.COLUMN_WORD));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}
