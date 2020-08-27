package com.shaddicard.repositories.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import com.shaddicard.model.MasterData;
import com.shaddicard.model.Results;
import com.shaddicard.repositories.database.DatabaseClient;
import com.shaddicard.repositories.network.ApiInterface;
import com.shaddicard.repositories.network.ApiManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRepository {

    private static DataRepository instance;
    private List<MasterData> dataSet = new ArrayList<>();
    private static Context context;

    private ApiInterface api;

    public static DataRepository getInstance(Context applicationContext){
        context = applicationContext;
        if(instance == null){
            instance = new DataRepository();
        }
        return instance;
    }

    public  DataRepository(){
        api = ApiManager.getApiInterface();
    }

public boolean wait  = false;

    public MutableLiveData<List<MasterData>> getMasterDatas(){

        final MutableLiveData<List<MasterData>> data = new MutableLiveData<>();


        try {
            dataSet =  new getDbData().execute().get();
            if (dataSet.size() > 0){
                data.setValue(dataSet);
            }else{

                api.getData()
                        .enqueue(new Callback<Results>() {

                                     @Override
                                     public void onResponse(Call<Results> call, Response<Results> response) {
                                         try {
                                             if (response.isSuccessful()) {
                                                 dataSet.addAll(response.body().getResults());
                                                 dataSet = new insertData(dataSet).execute().get();
                                                 data.setValue(dataSet);
                                             }
                                         } catch (Exception e) {
                                             e.printStackTrace();
                                         }
                                     }
                                     @Override
                                     public void onFailure(Call<Results> call, Throwable t) {
                                         t.printStackTrace();
                                     }
                                 }
                        );

            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return data;
    }


    class getDbData extends AsyncTask<Void, Void, List<MasterData>> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<MasterData> doInBackground(Void... voids) {


            List<MasterData> taskList = DatabaseClient
                    .getInstance(context)
                    .getAppDatabase()
                    .dataDao()
                    .getAllData();



            return taskList;
        }

        @Override
        protected void onPostExecute(List<MasterData> data) {
            super.onPostExecute(data);
        }
    }


    class insertData extends AsyncTask<Void, Void, List<MasterData>> {

        List<MasterData> response;
        public insertData(List<MasterData> response1) {
            response = response1;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<MasterData> doInBackground(Void... voids) {

            DatabaseClient.getInstance(context).getAppDatabase()
                    .dataDao()
                    .insert(response);

            List<MasterData> taskList = DatabaseClient
                    .getInstance(context)
                    .getAppDatabase()
                    .dataDao()
                    .getAllData();



            return taskList;
        }

        @Override
        protected void onPostExecute(List<MasterData> aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}