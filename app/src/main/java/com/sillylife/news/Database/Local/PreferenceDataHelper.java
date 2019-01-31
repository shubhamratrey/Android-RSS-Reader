package com.sillylife.news.Database.Local;

import android.content.Context;

public class PreferenceDataHelper {

    private static PreferenceDataHelper instance;
    private SharedPreferenceHelper sharedPreferenceHelper;

    private static final String CURRENT_LANGUAGE = "current_language_selection";
    private static final String CURRENT_STATE = "current_state";
    private static final String CURRENT_DISTRICT = "current_district";
    private static final String CURRENT_SELECTED_OPTION = "current_selected_option";
    private static final String CURRENT_VIDHANSABHA = "current_vidhansabha";
    private static final String VOTING_STATUS = "voting_status";

    private PreferenceDataHelper(Context context) {
        SharedPreferenceHelper.initialize(context);
        this.sharedPreferenceHelper = SharedPreferenceHelper.getInstance();
    }

    public static synchronized PreferenceDataHelper getInstance(Context context) {

        if (instance == null) {
            instance = new PreferenceDataHelper(context);
        }

        return instance;
    }

    public void setCurrentLanguage(int language){
        sharedPreferenceHelper.setInt(CURRENT_LANGUAGE, language);
    }

    public void setCurrentState(String currentState){
        sharedPreferenceHelper.setString(CURRENT_STATE, currentState);
    }
    public String getCurrentState(){
        return sharedPreferenceHelper.getString(CURRENT_STATE);
    }

    public void setCurrentDistrict(String district){
        sharedPreferenceHelper.setString(CURRENT_DISTRICT, district);
    }

    public String getCurrentDistrict(){
        return sharedPreferenceHelper.getString(CURRENT_DISTRICT);
    }

    public void setCurrentVidhansabha(String vidhansabha){
        sharedPreferenceHelper.setString(CURRENT_VIDHANSABHA, vidhansabha);
    }

    public String getCurrentVidhansabha(){
        return sharedPreferenceHelper.getString(CURRENT_VIDHANSABHA);
    }
    public void setVotingStatus(Boolean vote){
        sharedPreferenceHelper.setBoolean(VOTING_STATUS, vote);
    }

    public Boolean getVotingStatus(){
        return sharedPreferenceHelper.getBoolean(VOTING_STATUS);
    }


	public void setCurrentSelectedOption(String currentSelectedOption){
		sharedPreferenceHelper.setString(CURRENT_SELECTED_OPTION, currentSelectedOption);
	}

	public String getCurrentSelectedOption(){
		return sharedPreferenceHelper.getString(CURRENT_SELECTED_OPTION);
	}
}
