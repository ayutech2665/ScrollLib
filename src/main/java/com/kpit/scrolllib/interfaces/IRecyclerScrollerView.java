package com.kpit.scrolllib.interfaces;

import android.content.Context;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;

public interface IRecyclerScrollerView  {
     void setViewsToUse(@LayoutRes int layoutResId, @IdRes int trackerResId, @IdRes int handleResId, @IdRes int scrollUpResId, @IdRes int scrollDownResId);
     void setRecyclerView(final RecyclerView recyclerView, Context context);
}
