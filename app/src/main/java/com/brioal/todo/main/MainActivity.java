package com.brioal.todo.main;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.brioal.circleimage.CircleImageView;
import com.brioal.todo.R;
import com.brioal.todo.base.BaseActivity;
import com.brioal.todo.bean.TodoBean;
import com.brioal.todo.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.main_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.main_et_add)
    EditText mEtAdd;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.main_btn_add)
    TextView mBtnAddTodo;
    @BindView(R.id.main_head_iv_head)
    CircleImageView mIvHead;
    @BindView(R.id.main_head_tv_name)
    TextView mTvName;
    @BindView(R.id.main_head_tv_desc)
    TextView mTvDesc;
    @BindView(R.id.main_menu_btn_add)
    Button mBtnAddMenu;
    @BindView(R.id.main_menu_recyclerView)
    RecyclerView mMenuRecyclerView;

    private List<TodoBean> mList = new ArrayList<>();
    private List<String> mMenuList = new ArrayList<>();
    private ToDoListAdapter mAdapter;
    private MenuAdapter mMenuAdapter;
    private String mCurrentClassify = "未分类";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        ButterKnife.bind(this);
        initToolBar();
        initDrawerLayout();
        initMenuList();
        initView();
        initData();
    }

    /**
     * 初始化菜单
     */
    private void initMenu() {
        mMenuAdapter = new MenuAdapter(mContext);
        mMenuAdapter.setList(mMenuList);
        mMenuAdapter.setOperatorListener(new MenuAdapter.OnMenuOperatorListener() {

            @Override
            public void select(String name) {
                mCurrentClassify = name;
                mToolbar.setTitle(name);
                initData();
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        mMenuRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mMenuRecyclerView.setAdapter(mMenuAdapter);
    }

    /**
     * 获取分类数据
     */
    private void initMenuList() {
        mMenuList.clear();
        mMenuList.add("未分类");
        try {
            DBHelper dbHelper = new DBHelper(mContext, "todo.db3", null, 1);
            SQLiteDatabase database = dbHelper.getReadableDatabase();
            String query = "select * from classify order by id";
            Cursor cursor = database.rawQuery(query, null);
            while (cursor.moveToNext()) {
                String name = cursor.getString(1);
                mMenuList.add(name);
            }
            initMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mBtnAddMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addClassify();
            }
        });

    }

    /**
     * 初始化组件
     */
    private void initView() {
        //添加事件
        mBtnAddTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = mEtAdd.getText().toString().trim();
                if (content.isEmpty()) {
                    showToast("内容不能为空");
                    return;
                }
                TodoBean bean = new TodoBean(content);
                if (mList.contains(bean)) {
                    showToast("内容重复");
                    return;
                }
                try {
                    DBHelper helper = new DBHelper(mContext, "todo.db3", null, 1);
                    SQLiteDatabase database = helper.getWritableDatabase();
                    String insertSQL = "insert into todo values ( ? , ? , ? , ? )";
                    database.execSQL(insertSQL, new Object[]{
                            System.currentTimeMillis(),
                            mCurrentClassify,
                            content,
                            0
                    });
                    initData();
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast(e.getMessage());
                }
                mEtAdd.setText("");
            }
        });
    }

    /**
     * 显示列表
     */
    private void initList() {
        mAdapter = new ToDoListAdapter(mContext);
        mAdapter.showList(mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
    }


    /**
     * 初始化TOdo数据
     */
    private void initData() {
        mList.clear();
        try {
            DBHelper helper = new DBHelper(mContext, "todo.db3", null, 1);
            SQLiteDatabase db = helper.getWritableDatabase();
            String querySql = "select * from todo where parent = ? order by id";
            Cursor cursor = db.rawQuery(querySql, new String[]{mCurrentClassify});
            while (cursor.moveToNext()) {
                long id = cursor.getLong(0);
                String parent = cursor.getString(1);
                String content = cursor.getString(2);
                boolean isDone = cursor.getInt(3) != 0;
                TodoBean bean = new TodoBean(content);
                bean.setTodoID(id).setParent(parent).setDone(isDone);
                mList.add(bean);
                initList();
            }
            initList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    private void initToolBar() {
        mToolbar.setTitle("未分类");
        setSupportActionBar(mToolbar);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * 添加分类
     */
    private void addClassify() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View layout = LayoutInflater.from(mContext).inflate(R.layout.layout_add_classify, null, false);
        final EditText etName = layout.findViewById(R.id.layout_add_et);
        builder.setView(layout).setTitle("添加分类").setPositiveButton("添加", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String name = etName.getText().toString().trim();
                if (name.isEmpty()) {
                    showToast("分类名称不能为空");
                    return;
                }
                try {
                    DBHelper dbHelper = new DBHelper(mContext, "todo.db3", null, 1);
                    SQLiteDatabase database = dbHelper.getWritableDatabase();
                    String inert_sql = "insert into classify values ( ? , ? )";
                    database.execSQL(inert_sql, new Object[]{
                            System.currentTimeMillis(),
                            name
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast(e.getMessage());
                }
                initMenuList();
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).create().show();
    }
}
