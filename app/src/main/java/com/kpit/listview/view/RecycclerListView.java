package com.kpit.listview.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kpit.listview.R;
import com.kpit.listview.interfaces.IViewHandler;
import com.kpit.listview.model.UserDetails;
import com.kpit.scrolllib.interfaces.OnClickOkListener;
import com.kpit.scrolllib.view.CustomDialog;
import com.kpit.scrolllib.view.CustomToast;
import com.kpit.scrolllib.view.RecyclerScrollerView;

import java.util.ArrayList;

public class RecycclerListView implements IViewHandler {

    public static View mRecyclerListView;
    private Context mContext;
    public RecyclerView recyclerView;
    public RecyclerScrollerView recyclerScrollerView;
    ArrayList Userlist = null;
    CustomDialog dilogbox = null;
    CustomToast toast = null;
    Button Warning , Toast, notify ;



    @Override
    public void widgetInitializer(View view, Activity context) {

        mContext = context;
        mRecyclerListView = view;
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerScrollerView = view.findViewById(R.id.scroller);
        Warning = view.findViewById(R.id.popupWarning);
        Toast = view.findViewById(R.id.toast);
        notify = view.findViewById(R.id.popupNotify);
     //  notificationView = view.findViewById(R.id.notificationview);
       // removeView = view.findViewById(R.id.removeButtonView);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        addItemsInList();
        ListAdapter adapter = new ListAdapter(Userlist);
        recyclerView.setAdapter(adapter);
        recyclerScrollerView.setRecyclerView(recyclerView,context);
       // recyclerScrollerView.setLayoutManager(linearLayoutManager);
       dilogbox = new CustomDialog(context);
       toast = new CustomToast(mContext);



     //  notificationView.showToast();
       onCLick();

    }

    public void addItemsInList(){

        Userlist = new  ArrayList<UserDetails>();
        for (int i = 0 ; i <=10 ; i++) {

            Userlist.add(
                    new UserDetails(
                            "AYUSH",
                            "INGLE",
                            BitmapFactory.decodeResource(mContext.getResources(), R.drawable.smallaxe)
                    )
            );
            Userlist.add(
                   new  UserDetails(
                            "JAYASHREE",
                            "INGLE",
                            BitmapFactory.decodeResource(mContext.getResources(), R.drawable.smallaxe)
                    )
            );
        }

    }

    public void onCLick(){
        Warning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dilogbox.showDialog("Warning Text", "WARNING", "SampleWarning", new OnClickOkListener() {
                    @Override
                    public void okstate(String text) {

                    }
                });

            }
        });


        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dilogbox.showDialog("PopUp Text", "POPUP", "SampleNotification", new OnClickOkListener() {
                    @Override
                    public void okstate(String text) {

                    }
                });

            }
        });


        Toast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                toast.makeText("Hello Sample TOAST",CustomToast.LENGTH_LONG);
                toast.show();

            }
        });
    }



}
