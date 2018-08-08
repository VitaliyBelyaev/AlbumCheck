package ru.belyaev.vitaliy.albumcheck;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.belyaev.vitaliy.albumcheck.domain.Album;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.AlbumVH> {

    interface AlbumOnClickHandler {
        void onClick(int position, int albumId);

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
                    onClickHandler.onClick(position, albums.get(position).getCollectionId());
                }
            });
        }
    }

    private ArrayList<Album> albums;
    private ArrayList<Album> defaultOrder;
    private AlbumOnClickHandler onClickHandler;


    public AlbumsAdapter(AlbumOnClickHandler onClickHandler) {
        this.onClickHandler = onClickHandler;
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

    public void setDefaultOrderedAlbums(List<Album> albums) {
        this.defaultOrder = (ArrayList<Album>) albums;
    }

    public void replaceWith(List<Album> albums) {
        this.albums = (ArrayList<Album>) albums;
        notifyDataSetChanged();
    }

    public void sortByName(Boolean reverseOrder) {
        ArrayList<Album> sortedAlbums = new ArrayList<>(albums);
        Collections.sort(sortedAlbums, Album.sortByName);
        if (reverseOrder) {
            Collections.reverse(sortedAlbums);
        }
        replaceWith(sortedAlbums);
        notifyDataSetChanged();
    }

    public void sortByDate(Boolean reverseOrder) {
        ArrayList<Album> sortedAlbums = new ArrayList<>(albums);
        Collections.sort(sortedAlbums, Album.sortByDate);
        if (reverseOrder) {
            Collections.reverse(sortedAlbums);
        }
        replaceWith(sortedAlbums);
        notifyDataSetChanged();
    }

    public void defaultOrder() {
        replaceWith(defaultOrder);
        notifyDataSetChanged();
    }

    public boolean hasData(){
        return albums != null;
    }
}
