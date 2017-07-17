package com.brioal.todo.bean;

import android.support.annotation.NonNull;

/**
 * email:brioal@foxmail.com
 * github:https://github.com/Brioal
 * Created by Brioal on 2017/7/16.
 */

public class TodoBean implements Comparable {
    private String mParent = "";//所属分类
    private String mContent = "";//显示内容
    private boolean isDone = false;//任务是否完成
    private long mTodoID = 0;//唯一标识符


    public TodoBean(String content) {
        mParent = "未分类";
        mContent = content;
        this.isDone = false;
        mTodoID = System.currentTimeMillis();
    }

    public String getParent() {
        return mParent;
    }

    public TodoBean setParent(String parent) {
        mParent = parent;
        return this;
    }

    public String getContent() {
        return mContent;
    }

    public TodoBean setContent(String content) {
        mContent = content;
        return this;
    }

    public boolean isDone() {
        return isDone;
    }

    public TodoBean setDone(boolean done) {
        isDone = done;
        return this;
    }

    public long getTodoID() {
        return mTodoID;
    }

    public TodoBean setTodoID(long todoID) {
        mTodoID = todoID;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        TodoBean other = (TodoBean) obj;
        return this.getParent().equals(other.getParent()) && this.getContent().equals(other.getContent());
    }

    @Override
    public int compareTo(@NonNull Object o) {
        TodoBean obj2 = (TodoBean) o;
        if (this.isDone() && !obj2.isDone()) {
            return 1;
        }
        if (!this.isDone() && obj2.isDone()) {
            return -1;
        }
        return this.mTodoID > obj2.mTodoID ? 1 : -1;
    }
}
