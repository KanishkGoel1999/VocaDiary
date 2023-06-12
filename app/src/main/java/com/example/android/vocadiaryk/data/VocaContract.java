package com.example.android.vocadiaryk.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class VocaContract {

    private VocaContract(){}

    public static final String CONTENT_AUTHORITY = "com.example.android.vocadiaryk";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_VOCA = "vocab";

    public static abstract class VocaEntry implements BaseColumns{

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_VOCA);


        public static final String TABLE_NAME = "vocab";

        public  static final String _ID = BaseColumns._ID;

        public static final String COLUMN_WORD = "Word";
        public static final String COLUMN_MEANING = "Meaning";
        public static final String COLUMN_MNEMONIC = "Mnemonic";
        public static final String COLUMN_SENTENCE = "Sentence";
        public static final String COLUMN_STAGE = "Stage";


        public static final int LEARNING = 0;
        public static final int MASTERED = 1;



    }


}
