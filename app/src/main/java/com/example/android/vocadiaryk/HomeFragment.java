package com.example.android.vocadiaryk;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HomeFragment extends Fragment {

    private onFragmentBtnSelected listener;


    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        TextView myListButton = (TextView) view.findViewById(R.id.MyListButton);
        TextView flashcardButton = (TextView) view.findViewById(R.id.FlashcardButton);
        TextView addWordButton = (TextView) view.findViewById(R.id.AddWordButton);
        TextView help = (TextView) view.findViewById(R.id.helpFeedback);

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "kanigoel1999@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "This is my feedback");
                startActivity(Intent.createChooser(emailIntent, null));
            }
        });

        myListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonSelected(MyList.class);
            }
        });

        flashcardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonSelected(indivisualWord.class);
            }
        });

        addWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonSelected(AddWord.class);
            }
        });


        return view;
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        if (context instanceof onFragmentBtnSelected) {
            listener = (onFragmentBtnSelected) context;
        } else
            throw new ClassCastException(context.toString() + "must implement listner");

    }

    public interface onFragmentBtnSelected{
        public void onButtonSelected(Class A);
    }


}
