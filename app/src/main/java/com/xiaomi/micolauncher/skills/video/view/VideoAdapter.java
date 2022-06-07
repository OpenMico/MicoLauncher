package com.xiaomi.micolauncher.skills.video.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import com.xiaomi.micolauncher.skills.video.model.VideoModel;
import java.util.List;

/* loaded from: classes3.dex */
public class VideoAdapter extends ArrayAdapter<VideoItem> {
    private int a;

    public VideoAdapter(Context context, int i) {
        super(context, i);
        this.a = i;
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    @NonNull
    public View getView(int i, View view, @NonNull ViewGroup viewGroup) {
        a aVar;
        if (view != null) {
            aVar = (a) view.getTag();
        } else {
            view = LayoutInflater.from(getContext()).inflate(this.a, viewGroup, false);
            aVar = new a();
            aVar.a = (ImageButton) view.findViewById(R.id.video_icon);
            aVar.b = (TextView) view.findViewById(R.id.video_title);
            view.setTag(aVar);
        }
        List<VideoItem> playList = VideoModel.getInstance().getPlayList();
        if (i < playList.size()) {
            VideoItem videoItem = playList.get(i);
            if (videoItem == VideoModel.getInstance().getCurrentVideoItem()) {
                aVar.b.setTextColor(-65536);
            } else {
                aVar.b.setTextColor(-1);
            }
            aVar.b.setText(videoItem.getTitle());
        }
        return view;
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public int getCount() {
        return VideoModel.getInstance().getPlayList().size();
    }

    /* loaded from: classes3.dex */
    static class a {
        ImageButton a;
        TextView b;

        a() {
        }
    }
}
