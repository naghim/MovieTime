package com.example.movietimez;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.CustomViewHolder> {
    private List<Model> mMovieRecyclerList;
    private Context context;

    public void setmMovieRecyclerList(List<Model> mMovieRecyclerList) {
        this.mMovieRecyclerList = mMovieRecyclerList;
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        public TextView txtTitle;
        public TextView txtDescription;
        public ImageView coverImage;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

            txtTitle = mView.findViewById(R.id.title);
            txtDescription = mView.findViewById(R.id.description);
            coverImage = mView.findViewById(R.id.coverImage);
        }
    }

    public MovieAdapter(Context context, List<Model> movieRecyclerList) {
        this.context = context;
        this.mMovieRecyclerList = movieRecyclerList;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_row_item, parent, false);
        CustomViewHolder rvh = new CustomViewHolder(view);
        return rvh;
    }


    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Model currentItem = mMovieRecyclerList.get(position);
        Glide.with(this.context).load(currentItem.getPosterPath()).into(holder.coverImage);

        holder.txtTitle.setText(currentItem.getTitle());
        holder.txtDescription.setText(currentItem.getOverview());


        //Picasso.Builder builder = new Picasso.Builder(context);
//        builder.downloader(new OkHttp3Downloader(context));
//        builder.build().load(dataList.get(position).getThumbnailUrl())
//                .placeholder((R.drawable.ic_launcher_background))
//                .error(R.drawable.ic_launcher_background)
//                .into(holder.coverImage);
    }

    @Override
    public int getItemCount() {
        return mMovieRecyclerList.size();
    }
}
