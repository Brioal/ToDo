package com.brioal.todo.main;

import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brioal.todo.R;
import com.brioal.todo.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * email:brioal@foxmail.com
 * github:https://github.com/Brioal
 * Created by Brioal on 2017/7/17.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
    private Context mContext;
    private List<String> mList = new ArrayList<>();
    private OnMenuOperatorListener mOperatorListener;

    public void setOperatorListener(OnMenuOperatorListener operatorListener) {
        mOperatorListener = operatorListener;
    }

    public MenuAdapter(Context context) {
        mContext = context;
    }

    public void setList(List<String> list) {
        mList.clear();
        mList.addAll(list);
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MenuViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_menu, parent, false));
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {
        holder.bind(mList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MenuViewHolder extends RecyclerView.ViewHolder {
        TextView mTvName;

        View rootView;

        public MenuViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            mTvName = rootView.findViewById(R.id.item_menu_tv);
        }

        public void bind(final String name, final int position) {
            //设置分类名称
            mTvName.setText(name);
            //设置点击事件
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOperatorListener != null) {
                        mOperatorListener.select(name);
                    }
                }
            });
            //设置长按事件
            rootView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("是否删除分类?").setMessage("删除分类会删除分类下所有任务,是否删除?").setCancelable(true).setPositiveButton("删除", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            mList.remove(name);
                            notifyDataSetChanged();
                            try {
                                DBHelper dbHelper = new DBHelper(mContext, "todo.db3", null, 1);
                                SQLiteDatabase database = dbHelper.getWritableDatabase();
                                String inert_sql = "delete from classify where name = ?";
                                database.execSQL(inert_sql, new Object[]{
                                        name
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }).setNegativeButton("不删除", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).create().show();
                    return true;
                }
            });
        }
    }

    public interface OnMenuOperatorListener {
        void select(String name);

    }
}
