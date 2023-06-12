package com.example.android.vocadiaryk;

public class homeTitle {

    private String title;
   // private String defaultTranslation;
    private int colorResoarceId;

    public homeTitle(String defaultTitle,  int colorR) {
        title = defaultTitle;
        //miwokTranslation = miwokT;
        colorResoarceId = colorR;
    }

//    public homeTitle(String defaultT, String miwokT) {
//        defaultTranslation = defaultT;
//        miwokTranslation = miwokT;
//    }

    public String getTitle(){
        return title;
    }

//    public String getDeafaultTranslation(){
//        return defaultTranslation;
//    }

    public int getColorResoarceId(){return colorResoarceId; }
}
