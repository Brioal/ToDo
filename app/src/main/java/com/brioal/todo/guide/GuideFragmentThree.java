package com.brioal.todo.guide;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brioal.todo.R;
import com.brioal.todo.base.BaseFragment;
import com.brioal.todo.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * email:brioal@foxmail.com
 * github:https://github.com/Brioal
 * Created by Brioal on 2017/7/16.
 */

public class GuideFragmentThree extends BaseFragment {
    private static GuideFragmentThree sFragmentThree;
    @BindView(R.id.fra_guide_btn_enter)
    TextView mBtnEnter;
    Unbinder unbinder;

    public synchronized static GuideFragmentThree getInstance() {
        if (sFragmentThree == null) {
            sFragmentThree = new GuideFragmentThree();
        }
        return sFragmentThree;
    }

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fra_guide_three, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAction();
    }


    private void initAction() {
        mBtnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //进入主界面
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
                //结束引导界面
                getActivity().finish();
                getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                //设置标识
                SharedPreferences preferences = getActivity().getSharedPreferences("Launcher", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("IsGuided", true);
                editor.apply();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
