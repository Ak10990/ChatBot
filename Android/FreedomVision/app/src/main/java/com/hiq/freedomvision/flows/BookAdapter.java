package com.hiq.freedomvision.flows;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hiq.freedomvision.R;

import java.util.List;

/**
 * Created by akanksha on 21/10/16.
 */
public class BookAdapter extends BaseAdapter {

    private final Context context;
    private final List<String> options;
    private final OptionsFragmentCallback callback;
    private int layout;

    public BookAdapter(Context context, List<String> options, int layout, OptionsFragmentCallback callback) {
        this.context = context;
        this.options = options;
        this.layout = layout;
        this.callback = callback;
    }

    @Override
    public int getCount() {
        return options.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return options.get(position);
    }


    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        String data = options.get(position);
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(layout, null);
        }
        TextView tvName = (TextView) view.findViewById(R.id.tv_name);
        tvName.setText(data);
        TextView tvData = (TextView) view.findViewById(R.id.tv_data);
        tvData.setText(data);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onItemClicked(position);
            }
        });

        return view;
    }

    public interface OptionsFragmentCallback {
        void onItemClicked(int position);
    }
}
