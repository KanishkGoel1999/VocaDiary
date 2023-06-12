package com.example.android.vocadiaryk;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    private onFragmentBtnSelected listener;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        TextView myListButton = (TextView) view.findViewById(R.id.MyListButton);
        TextView flashcardButton = (TextView) view.findViewById(R.id.FlashcardButton);
        TextView addWordButton = (TextView) view.findViewById(R.id.AddWordButton);


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
    public void onAttach(@NonNull Context context) {
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
