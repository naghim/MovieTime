package com.example.movietimez.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movietimez.Interfaces.OnItemClickListener;
import com.example.movietimez.Models.Movie;
import com.example.movietimez.R;

import java.util.List;

public class RelatedContentAdapter extends RecyclerView.Adapter<RelatedContentAdapter.CustomViewHolder> {
    private List<Movie> mMovieRecyclerList;
    private Context context;
    private OnItemClickListener mOnItemClickListener;

    /**
     * Setter
     * @param mMovieRecyclerList
     */
    public void setMovieRecyclerList(List<Movie> mMovieRecyclerList) {
        this.mMovieRecyclerList = mMovieRecyclerList;
    }

    /**
     * Setter
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }

    /**
     * Helper class
     */
    public static class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        public TextView txtTitle;
        public ImageView coverImage;

        public CustomViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mView = itemView;

            txtTitle = mView.findViewById(R.id.title);
            coverImage = mView.findViewById(R.id.coverImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ( listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(v,position);
                        }
                    }
                }
            });
        }
    }

    /**
     * Constructor
     * @param context
     * @param movieRecyclerList
     */
    public RelatedContentAdapter(Context context, List<Movie> movieRecyclerList) {
        this.context = context;
        this.mMovieRecyclerList = movieRecyclerList;
    }

    /**
     * On create
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.related_content, parent, false);
        CustomViewHolder rvh = new CustomViewHolder(view, mOnItemClickListener);
        return rvh;
    }


    /**
     * On bind
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Movie currentItem = mMovieRecyclerList.get(position);
        Glide.with(this.context).load(currentItem.getPosterPath()).into(holder.coverImage);

        holder.txtTitle.setText(currentItem.getTitle());
    }

    /**
     * Get item count
     * @return
     */
    @Override
    public int getItemCount() {
        return mMovieRecyclerList.size();
    }
}
