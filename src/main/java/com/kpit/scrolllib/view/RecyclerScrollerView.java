package com.kpit.scrolllib.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kpit.scrolllib.R;
import com.kpit.scrolllib.interfaces.IRecyclerScrollerView;

import java.security.acl.LastOwnerException;

import static android.view.MotionEvent.ACTION_CANCEL;
import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;
import static android.view.MotionEvent.ACTION_UP;

public class RecyclerScrollerView extends LinearLayout implements IRecyclerScrollerView {
    private static final int TRACK_SNAP_RANGE = 5;
    private ImageButton scrollup, scrolldown;
    private View handle;
    private RecyclerView recyclerView;
    private int height;
    private View tracker;
    private boolean isInitialized = false;
    private ObjectAnimator currentAnimator = null;
    private static String TAG = "Recylerview";
    LinearLayoutManager linearLayoutManager = null;
    int totalItemCount = 0 ;

    private final RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(@NonNull final RecyclerView recyclerView, final int dx, final int dy) {
                updateHandlePosition();

        }
    };

    public RecyclerScrollerView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public RecyclerScrollerView(final Context context) {
        super(context);
        init(context);
    }

    public RecyclerScrollerView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    protected void init(Context context) {
        if (isInitialized)
            return;
        isInitialized = true;
        setOrientation(HORIZONTAL);
        setClipChildren(false);
        setViewsToUse(R.layout.recycler_view_scroller, R.id.scroller_tracker, R.id.scroller_handle, R.id.scrollupbtn, R.id.scrolldownbtn);

    }

    public void setViewsToUse(@LayoutRes int layoutResId, @IdRes int trackerResId, @IdRes int handleResId, @IdRes int scrollUpResId, @IdRes int scrollDownResId) {
        final LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(layoutResId, this, true);
        handle = findViewById(handleResId);
        tracker = findViewById(trackerResId);
        scrollup = findViewById(scrollUpResId);
        scrolldown = findViewById(scrollDownResId);

        scrollup.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN: {

                        scrollup.setImageResource(R.drawable.button_uppressed);

                        if (totalItemCount <= 0) {

                        }
                        else {

                            linearLayoutManager.smoothScrollToPosition(recyclerView, null,  0);
                        }
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        scrollup.setImageResource(R.drawable.button_up);
                        break;
                }
                return false;
            }
        });

        scrolldown.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN: {

                        scrolldown.setImageResource(R.drawable.button_downpressed);

                        if (totalItemCount <= 0) {
                        }
                        else {

                            linearLayoutManager.smoothScrollToPosition(recyclerView, null,   totalItemCount);
                        }

                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        scrolldown.setImageResource(R.drawable.button_down);
                        break;
                }

                return false;
            }
        });
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = h;
        if (recyclerView != null) {
            Log.d(TAG, "Recyclerview is not null");
            updateHandlePosition();
            handle.setVisibility(VISIBLE);
            tracker.setVisibility(VISIBLE);
            scrollup.setVisibility(VISIBLE);
            scrolldown.setVisibility(VISIBLE);
        } else {
            Log.d(TAG, "RecyclrerView is null");
            handle.setVisibility(INVISIBLE);
            tracker.setVisibility(INVISIBLE);
            scrollup.setVisibility(INVISIBLE);
            scrolldown.setVisibility(INVISIBLE);
        }
    }


    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        final int action = event.getAction();
        switch (action) {
            case ACTION_DOWN:
                if (event.getX() < handle.getX() - ViewCompat.getPaddingStart(handle))
                    return false;
                if (currentAnimator != null)
                    currentAnimator.cancel();
                handle.setSelected(true);
            case ACTION_MOVE:
                final float y = event.getY();
                setHandlePosition(y);
                setRecyclerViewPosition(y);
                return true;
            case ACTION_UP:
            case ACTION_CANCEL:
                handle.setSelected(false);
                return true;
        }
        return super.onTouchEvent(event);
    }

    public void setRecyclerView(final RecyclerView recyclerView ,Context mContext) {
        if (this.recyclerView != recyclerView) {
            if (this.recyclerView != null)
                this.recyclerView.removeOnScrollListener(onScrollListener);
            this.recyclerView = recyclerView;
            if (this.recyclerView == null)
                return;
            recyclerView.addOnScrollListener(onScrollListener);
           linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
          totalItemCount  = recyclerView.getAdapter().getItemCount();
        }
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (recyclerView != null) {
            recyclerView.removeOnScrollListener(onScrollListener);
            recyclerView = null;
        }
    }

    private void setRecyclerViewPosition(float y) {
        if (recyclerView != null) {
            final int itemCount = recyclerView.getAdapter().getItemCount();
            float proportion;
            if (handle.getY() == tracker.getTop())
                proportion = 0f;
            else if (handle.getY() + handle.getHeight() >= height - TRACK_SNAP_RANGE)
                proportion = 1f;
            else
                proportion = y / (float) height;
            final int targetPos = getValueInRange(0, itemCount - 1, (int)(proportion * (float) itemCount));
            ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPositionWithOffset(targetPos, 0);

        }
    }

    private int getValueInRange(int min, int max, int value) {
        int minimum = Math.max(min, value);
        return Math.min(minimum, max);
    }

    private void updateHandlePosition() {
        if (handle.isSelected())
            return;

        final int verticalScrollOffset = recyclerView.computeVerticalScrollOffset();
        final int verticalScrollRange = recyclerView.computeVerticalScrollRange();

        float proportion = (float) verticalScrollOffset / ((float) verticalScrollRange - height);

        setHandlePosition(height * proportion);
    }


    private void setHandlePosition(float y) {
        final int handleHeight = handle.getHeight();
        if (handleHeight == 0) {
            tracker.post(new Runnable() {
                @Override
                public void run() {
                    handle.setY(tracker.getTop()+30);
                }
            });
        }
        handle.setY(getValueInRange(tracker.getTop()+30, (tracker.getHeight() + tracker.getTop()) - (handleHeight / 2 + 60), (int)(y - handleHeight / 2)));
        }

}