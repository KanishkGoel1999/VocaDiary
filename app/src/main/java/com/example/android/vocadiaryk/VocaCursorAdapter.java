package com.example.android.vocadiaryk;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.example.android.vocadiaryk.data.VocaContract.VocaEntry;

import com.example.android.vocadiaryk.data.VocaContract;

public class VocaCursorAdapter extends CursorAdapter {

    public VocaCursorAdapter (Context context, Cursor cursor){
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.cursor_list_view, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        View view1 = (View)view.findViewById(R.id.rootView);

        TextView mWordView = (TextView) view.findViewById(R.id.word);
        TextView mStageView = (TextView)view.findViewById(R.id.stage);

        mWordView.setText(cursor.getString(cursor.getColumnIndex(VocaEntry.COLUMN_WORD)));
        mStageView.setText(cursor.getString(cursor.getColumnIndex(VocaEntry.COLUMN_MEANING)));


    }
}
