package com.lib.ext.widget.recorderdialog;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import android.app.AlertDialog;
import android.content.Context;
import android.media.MediaRecorder;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.lib.R;

public class RecorderDialog {
    private static final String TAG = "RecorderDialog";

    private Context mContext;
    private AlertDialog mDialog;
    //录音对象
    private MediaRecorder mRecorder;
    private String mFileName = "";
    private String folderPath = "";
    private OnRecordListener mRecordListener;


    /*
    * 录音时候的动画切换
    */
    int[] micRun = new int[]{R.drawable.mic_run1, R.drawable.mic_run2, R.drawable.mic_run3, R.drawable.mic_run4};

    /**
     * 构造函数
     */
    public RecorderDialog(Context context) {
        mContext = context;
    }

    public void setFileSaveFolder(String path) {
        folderPath = path;
    }

    public void setOnRecordListener(OnRecordListener l) {
        mRecordListener = l;
    }

    /*
    /**
     * 开始录音
     */
    public void startRecording() {
        mRecorder = new MediaRecorder();
        //设置音源为Micphone  
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        //设置封装格式  
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        File file = new File(folderPath, "WA");

        if (!file.exists()) { // 创建目录
            file.mkdirs();
        }

        String name = System.currentTimeMillis() + ".3gp";
        File file1 = new File(file, name);
        mFileName = file1.getAbsolutePath();
        mRecorder.setOutputFile(mFileName);
        //设置编码格式  
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        showVoidDialog();

        mRecorder.start();
    }

    /**
     * 停止录音
     */
    public void stopRecording() {
        mDialog.dismiss();
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
        if (mRecordListener != null) {
            mRecordListener.onRecordComplete(mFileName);
        }

    }

    /**
     * 显示提示框
     */
    private void showVoidDialog() {
        if (mDialog == null) mDialog = new AlertDialog.Builder(mContext).create();
        mDialog.show();
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_void, null);
        final ImageView micRunIv = (ImageView) view.findViewById(R.id.micRunIv);
        mDialog.setContentView(view);
        updateMicRun(micRunIv);


    }

    /**
     * 显示录音提示框的动画
     */
    Handler handler = new Handler();

    private void updateMicRun(final ImageView micRunIv) {
        if (!mDialog.isShowing()) return;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                micRunIv.setBackgroundResource(micRun[new Random().nextInt(4)]);
                updateMicRun(micRunIv);
            }
        }, 500);

    }
}

