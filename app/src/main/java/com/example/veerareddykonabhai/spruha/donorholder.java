package com.example.veerareddykonabhai.spruha;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;

/**
 * Created by lenovo on 12/13/2017.
 */

public class donorholder extends ViewHolder{
    public TextView tv1,tv2,tv3,tv4,tv5;
    public donorholder(View itemView) {
        super(itemView);
        tv1=(TextView)itemView.findViewById(R.id.uname);
        tv2=(TextView)itemView.findViewById(R.id.uid);
        tv3=(TextView)itemView.findViewById(R.id.uphone);
        tv4=(TextView)itemView.findViewById(R.id.ulocality);
        tv5=(TextView)itemView.findViewById(R.id.uplace);
    }
}
