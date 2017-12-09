package com.android.fangxue.ui.Pro;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.fangxue.R;
import com.android.fangxue.utils.ZoomImageView;
import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShowBigImg extends AppCompatActivity {

    @Bind(R.id.showBgi)
    ZoomImageView showBgi;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.rightBtn)
    TextView rightBtn;
    @Bind(R.id.back_btn)
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_pro_show_big_img);
        ButterKnife.bind(this);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbarTitle.setText("图片信息");
        String src = getIntent().getStringExtra("imgsrc");
        Glide.with(this).load(src).placeholder(R.drawable.loading).into(showBgi);
    }
}
