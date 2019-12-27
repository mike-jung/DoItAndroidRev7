package org.techtown.mission25;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.ViewHolder>
                            implements OnPictureItemClickListener {
    ArrayList<PictureInfo> items = new ArrayList<PictureInfo>();

    OnPictureItemClickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.picture_item, viewGroup, false);

        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        PictureInfo item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(PictureInfo item) {
        items.add(item);
    }

    public void setItems(ArrayList<PictureInfo> items) {
        this.items = items;
    }

    public PictureInfo getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, PictureInfo item) {
        items.set(position, item);
    }

    public void setOnItemClickListener(OnPictureItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if (listener != null) {
            listener.onItemClick(holder, view, position);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView textView2;

        ImageView imageView;

        BitmapFactory.Options options = new BitmapFactory.Options();

        public ViewHolder(View itemView, final OnPictureItemClickListener listener) {
            super(itemView);

            textView = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView2);
            imageView = itemView.findViewById(R.id.imageView);

            options.inSampleSize = 12;

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

        public void setItem(PictureInfo item) {
            textView.setText(item.getDisplayName());
            textView2.setText(item.getDateAdded());

            Bitmap bitmap = BitmapFactory.decodeFile(item.getPath(), options);
            imageView.setImageBitmap(bitmap);
        }

    }

}
