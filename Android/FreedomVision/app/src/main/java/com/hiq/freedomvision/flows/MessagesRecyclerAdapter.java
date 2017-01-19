package com.hiq.freedomvision.flows;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hiq.freedomvision.models.Chat;
import com.hiq.freedomvision.utils.DateUtils;
import com.hiq.freedomvision.constants.GenericConstants;
import com.hiq.freedomvision.R;

import java.util.List;

/**
 * Created by Akanksha on 27/4/16.
 * MessagesRecyclerAdapter for List
 */
public class MessagesRecyclerAdapter
        extends RecyclerView.Adapter<MessagesRecyclerAdapter.ViewHolder> {

    private final List<Chat> mValues;
    private final int meBg;
    private final int cbBg;

    public MessagesRecyclerAdapter(Context context, List<Chat> items) {
        mValues = items;
        meBg = ContextCompat.getColor(context, R.color.chat_message_background_outcoming);
        cbBg = ContextCompat.getColor(context, R.color.chat_message_background_incoming);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return mValues.get(position).getFromTo();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Chat message = mValues.get(position);
        holder.dateView.setText(DateUtils.getDisplayDate(message.getDate()));
        holder.contentView.setText(message.getBody());
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) holder.mainView.getLayoutParams();
        switch (holder.getItemViewType()) {
            case GenericConstants.CHAT_BOT:
                configureChatbotViewHolder(holder, params);
                break;
            case GenericConstants.ME:
                configureMeViewHolder(holder, params);
                break;
        }
    }

    private void configureMeViewHolder(ViewHolder holder, FrameLayout.LayoutParams params) {
        params.gravity = Gravity.RIGHT;
        holder.mainView.setLayoutParams(params);
        holder.mainView.setBackgroundColor(meBg);
    }

    private void configureChatbotViewHolder(ViewHolder holder, FrameLayout.LayoutParams params) {
        params.gravity = Gravity.LEFT;
        holder.mainView.setLayoutParams(params);
        holder.mainView.setBackgroundColor(cbBg);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final LinearLayout mainView;
        final TextView dateView;
        final TextView contentView;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mainView = (LinearLayout) view.findViewById(R.id.main_view);
            dateView = (TextView) view.findViewById(R.id.date);
            contentView = (TextView) view.findViewById(R.id.content);
        }
    }
}