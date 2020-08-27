package com.shaddicard.viewmodel;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.shaddicard.model.MasterData;
import com.shaddicard.repositories.data.DataRepository;

import java.util.List;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<List<MasterData>> mMasterData;
    private DataRepository mRepo;
    private MutableLiveData<Boolean> mIsUpdating = new MutableLiveData<>();

    public void init(Context applicationContext){
        if(mMasterData != null){
            return;
        }
        mRepo = DataRepository.getInstance(applicationContext);
        mMasterData = mRepo.getMasterDatas();
    }



    public LiveData<List<MasterData>> getMasterData(){
        return mMasterData;
    }
}