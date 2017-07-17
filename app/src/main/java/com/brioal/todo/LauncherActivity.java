package com.brioal.todo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.brioal.todo.base.BaseActivity;
import com.brioal.todo.guide.GuideActivity;
import com.brioal.todo.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LauncherActivity extends BaseActivity {
    @BindView(R.id.launcher_icon)
    ImageView mIvIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_launcher);
        ButterKnife.bind(this);
        initAnimation();
    }

    private void initAnimation() {
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.anim_launcher);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                judgeEnter();
            }


            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animation.setFillAfter(true);
        animation.setDuration(1200);
        mIvIcon.setAnimation(animation);
        animation.start();
    }

    /**
     * 判断进入的界面内容
     */
    private void judgeEnter() {
        SharedPreferences preferences = getSharedPreferences("Launcher", Context.MODE_PRIVATE);
        boolean isGuided = preferences.getBoolean("IsGuided", false);
        if (isGuided) {
            //直接进入主界面
            Intent intent = new Intent(mContext, MainActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        } else {
            //进入引导界面
            Intent intent = new Intent(mContext, GuideActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }
    }
}
