package com.anhs.lesson17k1;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG ="109" ;
    private static final Object KEY_UPDATE_COUNTER_1 = 100;
    private static final Object KEY_UPDATE_COUNTER_2 = 80;
    private TextView tvCount1;
    private TextView tvCount2;
    private Runnable rb;
    private MyTask mTask1, mTask2;

    @SuppressLint("StaticFieldLeak")
    private AsyncTask<Integer, Integer, Boolean> mTask = new AsyncTask<Integer, Integer, Boolean>() {
        private static final String TAG = "123";

        @Override
        protected Boolean doInBackground(Integer... data) {
            for (int i = 0; i < data[0]; i++) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Log.e(TAG, "mTask is canceled");
                }

                publishProgress(i);
            }
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            tvCount1.setText(String.format("%s", values[0]));
        }

        @Override
        protected void onPostExecute(Boolean result) {
            Toast.makeText(MainActivity.this, result ? "Sucsess" : "Failure", Toast.LENGTH_SHORT).show();
        }
    };
    private int REQUEST_UPDATE_COUNTER1 = 101;
    private int REQUEST_UPDATE_COUNTER2 = 102;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.what == REQUEST_UPDATE_COUNTER1) {
                tvCount1.setText(String.format("%s", msg.arg1));
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        tvCount1 = findViewById(R.id.tv_counter1);
        tvCount1 = findViewById(R.id.tv_counter2);

        findViewById(R.id.bt_start).setOnClickListener(this);
        findViewById(R.id.bt_stop).setOnClickListener(this);
        rb = new Runnable() {
            @Override
            public void run() {
//                execTask();
            }
        };
    }

    private Object execTask(MyTask task, int key, Object data) {
        int count = (int) data;
        for (int i = 0; i <count; i++) {

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Log.e(TAG, "My task is canceled");
        }
        task.updateTask(key, i);
    }
    return true;
}

//    private void execTask() {
//        for (int i = 0; i < 1000; i++) {
//            Message msg = new Message();
//            msg.what = REQUEST_UPDATE_COUNTER;
//            msg.arg1 = i;
//            msg.setTarget(mHandler);
//            msg.sendToTarget();
//            try {
//                Thread.sleep(200);
//                tvCount1.setText(i + "");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

    @Override
    public void onClick(View v) {
//    new Thread(rb).start();
//        mTask.execute(100);
        if ((v.getId() == R.id.bt_start)){
            mTask1.execute(KEY_UPDATE_COUNTER_1, 100);
            mTask1.execute(KEY_UPDATE_COUNTER_2, 80);
        }else {
            mTask1.cancel(true);
            mTask2.cancel(true);
        }
    }


}
