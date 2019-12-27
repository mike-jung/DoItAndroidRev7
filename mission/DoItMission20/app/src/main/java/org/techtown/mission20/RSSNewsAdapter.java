package org.techtown.mission20;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RSSNewsAdapter extends RecyclerView.Adapter<RSSNewsAdapter.ViewHolder>
                            implements OnRSSNewsItemClickListener {
    ArrayList<RSSNewsItem> items = new ArrayList<RSSNewsItem>();

    OnRSSNewsItemClickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.node_item, viewGroup, false);

        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        RSSNewsItem item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(RSSNewsItem item) {
        items.add(item);
    }

    public void setItems(ArrayList<RSSNewsItem> items) {
        this.items = items;
    }

    public RSSNewsItem getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, RSSNewsItem item) {
        items.set(position, item);
    }

    public void setOnItemClickListener(OnRSSNewsItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if (listener != null) {
            listener.onItemClick(holder, view, position);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mIcon;
        private TextView mText01;
        private TextView mText02;
        private TextView mText03;
        private WebView mText04;

        public ViewHolder(View itemView, final OnRSSNewsItemClickListener listener) {
            super(itemView);

            mIcon = itemView.findViewById(R.id.iconItem);
            mText01 = itemView.findViewById(R.id.dataItem01);
            mText02 = itemView.findViewById(R.id.dataItem02);
            mText03 = itemView.findViewById(R.id.dataItem03);
            mText04 = itemView.findViewById(R.id.dataItem04);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (listener != null) {
                        listener.onItemClick(ViewHolder.this, view, position);
                    }
                }
            });
        }

        public void setItem(RSSNewsItem item) {
            mIcon.setImageDrawable(item.getIcon());

            mText01.setText(item.getTitle());
            mText02.setText(item.getPubDate());

            String category = item.getCategory();
            if (category != null) {
                mText03.setText(category);
            }

            setTextToWebView(item.getDescription());

        }

        private void setTextToWebView(String msg) {
            Log.d("RSSNewsItemView", "setTextToWebView() called.");

            String outData = msg;

            outData = outData.replace("\"//", "\"http://");

            mText04.loadDataWithBaseURL("http://localhost/", outData, "text/html", "utf-8", null);
        }

    }

}
