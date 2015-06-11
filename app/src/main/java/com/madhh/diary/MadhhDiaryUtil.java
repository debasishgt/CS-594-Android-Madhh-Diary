package com.madhh.diary;

import android.content.SharedPreferences;

/**
 * Created by debasish on 6/11/2015.
 */
public class MadhhDiaryUtil {

    private static MadhhDiaryUtil madhhDiaryUtil = null;

    private MadhhDiaryUtil(){
    };

    public static MadhhDiaryUtil getMadhhDiaryUtil(){
        if(madhhDiaryUtil == null){
            madhhDiaryUtil = new MadhhDiaryUtil();
        }
        return madhhDiaryUtil;
    }

    public boolean getTrackState(SharedPreferences prefs){
        boolean ret = false;
        // = getSharedPreferences("trac_pref", MODE_PRIVATE);
        String trackState = prefs.getString("track_state", "Off");

        if(trackState.equalsIgnoreCase("On")){
            ret = true;
        }

        return ret;
    }
    public void toggleTrackState(SharedPreferences prefs, String state){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("track_state", state);
        editor.commit();
    }
}
