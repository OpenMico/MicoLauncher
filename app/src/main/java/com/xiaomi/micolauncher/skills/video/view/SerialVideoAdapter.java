package com.xiaomi.micolauncher.skills.video.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import com.xiaomi.micolauncher.skills.video.model.VideoModel;
import java.util.Locale;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes3.dex */
public class SerialVideoAdapter extends RecyclerView.Adapter<a> {
    private int a;
    private View.OnClickListener b;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static class a extends RecyclerView.ViewHolder {
        View a;
        TextView b;
        ImageView c;

        a(View view) {
            super(view);
            this.a = view;
            this.b = (TextView) view.findViewById(R.id.video_title);
            this.c = (ImageView) view.findViewById(R.id.video_current_play);
        }
    }

    public SerialVideoAdapter(int i, View.OnClickListener onClickListener) {
        this.a = i;
        this.b = onClickListener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NotNull
    public a onCreateViewHolder(@NotNull ViewGroup viewGroup, int i) {
        return new a(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_item_serial, viewGroup, false));
    }

    public void onBindViewHolder(@NotNull a aVar, int i) {
        aVar.a.setTag(Integer.valueOf(i));
        aVar.b.setText(String.format(Locale.getDefault(), "%d", Integer.valueOf(i + 1)));
        aVar.a.setOnClickListener(this.b);
        VideoItem currentVideoItem = VideoModel.getInstance().getCurrentVideoItem();
        if (currentVideoItem == null || i != currentVideoItem.getEpisode()) {
            aVar.c.setVisibility(4);
            aVar.b.setVisibility(0);
            return;
        }
        aVar.c.setVisibility(0);
        aVar.b.setVisibility(4);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.a;
    }
}
