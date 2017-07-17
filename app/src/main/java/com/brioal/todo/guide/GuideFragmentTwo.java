package com.brioal.todo.guide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brioal.todo.R;
import com.brioal.todo.base.BaseFragment;

/**
 * email:brioal@foxmail.com
 * github:https://github.com/Brioal
 * Created by Brioal on 2017/7/16.
 */

public class GuideFragmentTwo extends BaseFragment {
    private static GuideFragmentTwo sFragmentTwo;

    public synchronized static GuideFragmentTwo getInstance() {
        if (sFragmentTwo == null) {
            sFragmentTwo = new GuideFragmentTwo();
        }
        return sFragmentTwo;
    }

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fra_guide_two, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
