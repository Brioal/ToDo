package com.brioal.todo.guide;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.brioal.todo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuideActivity extends AppCompatActivity {

    @BindView(R.id.guide_viewPager)
    ViewPager mViewPager;
    @BindView(R.id.guide_index_one)
    RadioButton mIndexOne;
    @BindView(R.id.guide_index_two)
    RadioButton mIndexTwo;
    @BindView(R.id.guide_index_three)
    RadioButton mIndexThree;
    @BindView(R.id.guide_index_group)
    RadioGroup mIndexGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_guide);
        ButterKnife.bind(this);
        initViewPager();
    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {
        GuideViewPagerAdapter adapter = new GuideViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mIndexGroup.check(R.id.guide_index_one);
                        break;
                    case 1:
                        mIndexGroup.check(R.id.guide_index_two);
                        break;
                    case 2:
                        mIndexGroup.check(R.id.guide_index_three);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
