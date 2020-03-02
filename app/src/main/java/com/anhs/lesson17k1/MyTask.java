package com.anhs.lesson17k1;

import android.os.AsyncTask;

public class MyTask extends AsyncTask<Object,Object,Object> {
    private  OnAsyncCallBack mCallBack;
    public void setOnAsyncCallBack(OnAsyncCallBack event){
        mCallBack = event;
    }


    @Override
    protected Object doInBackground(Object... value) {
        int key =(int ) value[0];
        return mCallBack.execTask(this,key, value[1]);
    }


    @Override
    protected void onProgressUpdate(Object... values) {
        int key = (int) values[0];
        mCallBack.updateUI(key, values[1]);

    }

    @Override
    protected void onPostExecute(Object value) {
        mCallBack.taskComplete(value);
    }

    public void updateTask(Object... data) {
        publishProgress(data);
    }

    public interface OnAsyncCallBack{

        Object execTask(MyTask myTask, int key, Object datum);

        default void taskComplete(Object value){}

         default void updateUI(int key, Object values){
             //do nothing
         }
    }
}
