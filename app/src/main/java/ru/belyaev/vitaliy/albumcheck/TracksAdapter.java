package ru.belyaev.vitaliy.albumcheck;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import ru.belyaev.vitaliy.albumcheck.domain.Track;

public class TracksAdapter extends RecyclerView.Adapter<TracksAdapter.TrackVH> {

    private List<Track> tracks;

    public TracksAdapter() {
    }

    public void replaceWith(List<Track> tracks) {
        this.tracks = tracks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TracksAdapter.TrackVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new TrackVH(inflater.inflate(R.layout.track_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TracksAdapter.TrackVH holder, int position) {

        Track track = tracks.get(position);

        holder.trackNumber.setText(String.valueOf(track.getTrackNumber()));
        holder.trackName.setText(track.getTrackName());
        holder.artist.setText(track.getArtistName());
        holder.duration.setText(formatTime(track.getTrackTimeMillis()));
    }

    @Override
    public int getItemCount() {
        if (tracks != null) {
            return tracks.size();
        }
        return 0;
    }

    class TrackVH extends RecyclerView.ViewHolder{

        TextView trackNumber;
        TextView trackName;
        TextView artist;
        TextView duration;

        TrackVH(View itemView){
            super(itemView);

            trackNumber = itemView.findViewById(R.id.tv_track_number);
            trackName = itemView.findViewById(R.id.tv_track_name);
            artist = itemView.findViewById(R.id.tv_track_artist);
            duration = itemView.findViewById(R.id.tv_track_duration);
        }
    }

    private String formatTime(int timeInMillis){
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss",Locale.getDefault());
        return sdf.format(timeInMillis);
    }
}
