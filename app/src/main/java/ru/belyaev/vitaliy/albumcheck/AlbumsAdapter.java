package ru.belyaev.vitaliy.albumcheck;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.AlbumVH> {

    private List<Album> albums;
    private AlbumOnClickHandler onClickHandler;


    interface AlbumOnClickHandler {
        void onClick(int albumIndex);

    }

    public AlbumsAdapter(AlbumOnClickHandler onClickHandler) {
        this.onClickHandler = onClickHandler;
    }

    public void replaceWith(List<Album> albums) {
        this.albums = albums;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AlbumVH onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new AlbumVH(inflater.inflate(R.layout.album_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumVH holder, int position) {

        Album album = albums.get(position);

        Picasso.get()
                .load(album.getArtworkUrl60())
                .placeholder(R.drawable.image_placeholder)
                .into(holder.artwork);

        holder.title.setText(album.getCollectionName());
        holder.artist.setText(album.getArtistName());
        holder.trackCount.setText(String.valueOf(album.getTrackCount()));

    }

    @Override
    public int getItemCount() {
        if (albums != null) {
            return albums.size();
        }
        return 0;
    }

    class AlbumVH extends RecyclerView.ViewHolder {

        ImageView artwork;
        TextView title;
        TextView artist;
        TextView trackCount;


        AlbumVH(View itemView) {
            super(itemView);

            artwork = itemView.findViewById(R.id.album_artwork);
            title = itemView.findViewById(R.id.tv_album_title);
            artist = itemView.findViewById(R.id.tv_album_artist);
            trackCount = itemView.findViewById(R.id.tv_track_count);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    onClickHandler.onClick(position);
                }
            });
        }
    }

}
