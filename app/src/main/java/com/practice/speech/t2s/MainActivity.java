package com.practice.speech.t2s;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText et;
    TextView tv;
    Button bt,bt2;
    SpeechSynthesizer ss;
    RecognizerDialog rd;
//    SpeechRecognizer sr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //初始化语音配置对象
        SpeechUtility.createUtility(this, SpeechConstant.APPID+"=57aac834");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//设置一个Activity的显示界面

        et = (EditText)findViewById(R.id.et);
        tv = (TextView) findViewById(R.id.tv);
        bt = (Button)findViewById(R.id.bt);
        bt2 = (Button)findViewById(R.id.bt2);

        //创建语音合成对象
        ss = SpeechSynthesizer.createSynthesizer(this,null);
        //创建语音输入UI
        rd = new RecognizerDialog(this, null);
        //创建语音听写对象
//        sr = SpeechRecognizer.createRecognizer(this,null);

        //合成参数设置
        ss.setParameter(SpeechConstant.VOICE_NAME,"xiaoyu");
        ss.setParameter(SpeechConstant.SPEED,"50");
        ss.setParameter(SpeechConstant.VOLUME,"80");
        ss.setParameter(SpeechConstant.LANGUAGE,"zh_cn");
        ss.setParameter(SpeechConstant.ACCENT,"mandarin");
        ss.setParameter(SpeechConstant.ENGINE_TYPE,SpeechConstant.TYPE_CLOUD);
        //听写参数设置
//        sr.setParameter(SpeechConstant.DOMAIN,"iat");
//        sr.setParameter(SpeechConstant.LANGUAGE,"zh_cn");
//        sr.setParameter(SpeechConstant.ACCENT,"mandarin");
        //语音输入UI参数设置
        rd.setParameter(SpeechConstant.LANGUAGE,"zh_cn");
        rd.setParameter(SpeechConstant.ACCENT,"mandarin");
        rd.setParameter(SpeechConstant.DOMAIN,"iat");

        bt.setOnClickListener(this);
        bt2.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt:
                ss.startSpeaking(et.getText().toString(), new SynthesizerListener() {
                    @Override
                    public void onSpeakBegin() {

                    }

                    @Override
                    public void onBufferProgress(int i, int i1, int i2, String s) {

                    }

                    @Override
                    public void onSpeakPaused() {

                    }

                    @Override
                    public void onSpeakResumed() {

                    }

                    @Override
                    public void onSpeakProgress(int i, int i1, int i2) {

                    }

                    @Override
                    public void onCompleted(SpeechError speechError) {
                        Log.e("e",speechError.toString());
                    }

                    @Override
                    public void onEvent(int i, int i1, int i2, Bundle bundle) {

                    }
                });
                break;

            case R.id.bt2:
                rd.setListener(new RecognizerDialogListener() {
                    String text="";

                    @Override
                    public void onResult(RecognizerResult recognizerResult, boolean b) {
                        text += JsonParser.parseIatResult(recognizerResult.getResultString());
                        if (b){
                            tv.setText(text);
                        }
                    }

                    @Override
                    public void onError(SpeechError speechError) {

                    }

                });
                rd.show();
                break;

        }

    }
}
