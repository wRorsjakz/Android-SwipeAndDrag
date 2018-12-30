package com.example.user.swipeanddrag.Swipe;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.user.swipeanddrag.R;

public class SwipeRVTouchHelper extends ItemTouchHelper.SimpleCallback {

    private SwipeRVTouchHelperListener listener;

    public interface SwipeRVTouchHelperListener {
        void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
    }

    public SwipeRVTouchHelper(SwipeRVTouchHelperListener listener, int dragDirs, int swipeDirs) {
        super(dragDirs, swipeDirs);
        this.listener = listener;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        listener.onSwiped(viewHolder, i, viewHolder.getAdapterPosition());
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder != null) {
            View foreground = ((SwipeRVAdapter.SwipeRVViewHolder) viewHolder).getForegroundContainer();
            getDefaultUIUtil().onSelected(foreground);
        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        // User swipes the item
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            RelativeLayout backgroundLayout = viewHolder.itemView.findViewById(R.id.swipe_background_container);
            ImageView rightIcon = backgroundLayout.findViewById(R.id.swipe_item_icon_right);
            ImageView leftIcon = backgroundLayout.findViewById(R.id.swipe_item_icon_left);
            // User swipes left
            if (dX < 0) {
                leftIcon.setImageResource(android.R.color.transparent);
                rightIcon.setImageResource(R.drawable.ic_delete_white_24dp);
                backgroundLayout.setBackgroundResource(R.color.colorPrimaryDark);
            } // User swipes right
            else if (dX > 0) {
                rightIcon.setImageResource(android.R.color.transparent);
                leftIcon.setImageResource(R.drawable.ic_archive_white_24dp);
                backgroundLayout.setBackgroundResource(R.color.colorArchive);
            }
            View foreground = ((SwipeRVAdapter.SwipeRVViewHolder) viewHolder).getForegroundContainer();
            getDefaultUIUtil().onDraw(c, recyclerView, foreground, dX, dY, actionState, isCurrentlyActive);
        }
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        View foreground = ((SwipeRVAdapter.SwipeRVViewHolder) viewHolder).getForegroundContainer();
        getDefaultUIUtil().clearView(foreground);
    }
}
