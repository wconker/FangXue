package com.android.teacher.ui.Schdule;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.teacher.R;
import com.android.teacher.callback.MessageCallBack;
import com.android.teacher.entity.scheduleofclass;
import com.android.teacher.newwork.MessageCenter;
import com.android.teacher.utils.JSONUtils;
import com.android.teacher.utils.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class SchedulFromDay extends Activity implements MessageCallBack {

    @Bind(R.id.back_btn)
    ImageView backBtn;

    private MessageCenter messageCenter;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.rightBtn)
    TextView rightBtn;
    @Bind(R.id.submit)
    Button ok;
    @Bind(R.id.from)
    EditText from;
    @Bind(R.id.to)
    EditText to;

    private Observer<String> observer = new Observer<String>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(String s) {

            JSONObject cmd = JSONUtils.StringToJSON(s);
            if (JSONUtils.getInt(cmd, "code", 0) == 1) {
                if (JSONUtils.getString(cmd, "cmd").equals("schedule.copyfromday")) {
                    finish();
                }

            }

            Toast.FangXueToast(SchedulFromDay.this, JSONUtils.getString(cmd, "message"));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedul_from_day);

        ButterKnife.bind(this);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String f = from.getText().toString();
                String t = to.getText().toString();
                if (!f.isEmpty() && !t.isEmpty())
                    messageCenter.SendYouMessage(messageCenter.ChooseCommand().schedule_copyfromday(Integer.valueOf(f), Integer.valueOf(t)));
            }
        });
        toolbarTitle.setText("按天复制");
        messageCenter = new MessageCenter();
        messageCenter.setCallBackInterFace(this);
    }

    @Override
    public void onMessage(String str) {
        Observable.just(str)
                .observeOn(AndroidSchedulers
                        .mainThread())
                .subscribe(observer);


    }
}
