package com.brioal.todo.main;

import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.brioal.todo.R;
import com.brioal.todo.bean.TodoBean;
import com.brioal.todo.db.DBHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * email:brioal@foxmail.com
 * github:https://github.com/Brioal
 * Created by Brioal on 2017/7/17.
 */

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.TodoViewHolder> {

    private Context mContext;
    private List<TodoBean> mList = new ArrayList<>();

    public ToDoListAdapter(Context context) {
        mContext = context;
    }


    /**
     * 传入数据
     *
     * @param list
     */
    public void showList(List<TodoBean> list) {
        mList.clear();
        mList.addAll(list);
        Collections.sort(mList);
    }

    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TodoViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_todo, parent, false));
    }

    @Override
    public void onBindViewHolder(TodoViewHolder holder, int position) {
        holder.bind(mList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class TodoViewHolder extends RecyclerView.ViewHolder {
        CheckBox mCheck;
        TextView mContent;

        public View rootView;

        public TodoViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            mCheck = rootView.findViewById(R.id.item_todo_check);
            mContent = rootView.findViewById(R.id.item_todo_content);
        }

        /**
         * 对Item内组件进行操作
         *
         * @param bean
         * @param position
         */
        public void bind(final TodoBean bean, final int position) {
            //显示todo内容
            mContent.setText(bean.getContent() + "");
            //显示是否完成
            if (bean.isDone()) {
                //已完成
                mCheck.setChecked(true);
                mContent.setTextColor(Color.parseColor("#86000000"));
            } else {
                //未完成
                mCheck.setChecked(false);
                mContent.setTextColor(Color.BLACK);
            }
            //选中事件
            mCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (bean.isDone()) {
                        //未完成
                        bean.setDone(false);
                    } else {
                        //已完成
                        bean.setDone(true);
                    }
                    Collections.sort(mList);
                    notifyDataSetChanged();
                }
            });
            //长按事件
            rootView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("是否删除?").setMessage("是否删除当前Todo?").setCancelable(true).setPositiveButton("删除", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            long id = mList.get(position).getTodoID();
                            mList.remove(position);
                            notifyDataSetChanged();
                            dialogInterface.dismiss();
                            try {
                                DBHelper helper = new DBHelper(mContext, "todo.db3", null, 1);
                                SQLiteDatabase database = helper.getWritableDatabase();
                                String deleteSQL = "delete from todo where id = ?";
                                database.execSQL(deleteSQL, new Object[]{id});
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


}
