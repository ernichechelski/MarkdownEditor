package com.example.ernestchechelski.markdowntest.barAction;

import android.util.Log;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ErnestChechelski on 30.10.2017.
 */

public class BarAction {
    public static String TAG = BarAction.class.getSimpleName();

    public String name;
    public Boolean expanded;
    public static BarActionAdapter parentAdapter;
    public List<BarAction> children;
    public BarAction parent;
    public View.OnClickListener onClickListener;

    public BarAction(String name, View.OnClickListener onClickListener) {
        this.expanded =false;
        this.name = name;
        this.onClickListener = onClickListener;
        children = new ArrayList<>();
    }

    public BarAction(String name) {
        this.expanded =false;
        this.name = name;
        this.onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"Default onClickListener for BarAction:" + BarAction.this.toString());
            }
        };
        children = new ArrayList<>();
    }


    public void addChild(BarAction barAction){
        if(children.isEmpty()){
            expanded = true;
            barAction.parent = this;
            onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    expanded = !expanded;
                    if(expanded) {
                        parentAdapter.notifyItemRangeInserted(parentAdapter.getOuterArray().indexOf(BarAction.this)+1,BarAction.this.children.size());
                    } else {
                        parentAdapter.notifyItemRangeRemoved(parentAdapter.getOuterArray().indexOf(BarAction.this)+1,BarAction.this.children.size());
                    }

                }
            };
        }

        children.add(barAction);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public String toString() {
        return "BarAction{" +
                "name='" + name + '\'' +
                ", expanded=" + expanded +
                ", parentAdapter=" + parentAdapter +
                ", children=" + children +
                ", parent=" + parent +
                ", onClickListener=" + onClickListener +
                '}';
    }
}