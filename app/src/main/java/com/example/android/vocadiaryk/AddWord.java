package com.example.android.vocadiaryk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import com.example.android.vocadiaryk.data.VocaContract.VocaEntry;
import com.example.android.vocadiaryk.data.VocaDbHelper;

import android.net.Uri;
import android.widget.Toast;

public class AddWord extends AppCompatActivity {

    private EditText mWord;

    private EditText mMeaning;

    private EditText mMnemonic;

    private EditText mSentence;


    public void saveWord() {

        ContentValues values = new ContentValues();

        values.put(VocaEntry.COLUMN_WORD, mWord.getText().toString().trim());
        values.put(VocaEntry.COLUMN_MEANING, mMeaning.getText().toString().trim());
        values.put(VocaEntry.COLUMN_MNEMONIC, mMnemonic.getText().toString().trim());
        values.put(VocaEntry.COLUMN_SENTENCE, mSentence.getText().toString().trim());
        values.put(VocaEntry.COLUMN_STAGE, VocaEntry.LEARNING);



        Uri uri = getContentResolver().insert(VocaEntry.CONTENT_URI, values);

        if (uri == null) {
            // If the new content URI is null, then there was an error with insertion.
            Toast.makeText(this, getString(R.string.wordUnsaved),
                    Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast.
            Toast.makeText(this, getString(R.string.wordSaved),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        mWord = (EditText) findViewById(R.id.edit_word);
        mMeaning = (EditText) findViewById(R.id.edit_meaning);
        mMnemonic = (EditText) findViewById(R.id.edit_mnemonic);
        mSentence = (EditText) findViewById(R.id.edit_sentence);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_add_word, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_save:
                saveWord();
                finish();
                return true;

            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }




}
