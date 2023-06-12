package com.example.android.vocadiaryk;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class Pop extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pop_up_layout);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.8), (int)(height*.6));

        String text = getIntent().getStringExtra("value");
        TextView view = (TextView) findViewById(R.id.popTextView);
        view.setText("THIS IS A PROTOTYPE. \n BUGS WILL BE RESOLVED SOON.");

    }


    private void displaytext(){



    }
}
