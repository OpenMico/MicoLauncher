package com.xiaomi.smarthome.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.xiaomi.micolauncher.common.view.BaseViewHolder;
import com.xiaomi.miot.support.core.MiotRoom;
import com.xiaomi.smarthome.R;
import com.xiaomi.smarthome.ui.widgets.RoomTabViewHolder;

/* loaded from: classes4.dex */
public class RoomTabAdapter extends BaseMiotListAdapter<MiotRoom> {
    private final LayoutInflater a;
    private int b = 0;
    private Context c;
    private final RoomTabViewHolder.RoomClickListener d;

    public RoomTabAdapter(Context context, RoomTabViewHolder.RoomClickListener roomClickListener) {
        this.c = context;
        this.a = LayoutInflater.from(context);
        this.d = roomClickListener;
    }

    public void setHighlightPosition(int i) {
        if (this.b != i) {
            this.b = i;
            notifyDataSetChanged();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        TextView textView = new TextView(this.c);
        textView.setTextSize(this.c.getResources().getDimensionPixelSize(R.dimen.room_tab_text_size));
        textView.setPadding(0, 0, this.c.getResources().getDimensionPixelSize(R.dimen.room_tab_text_padding), 0);
        textView.setGravity(17);
        RoomTabViewHolder roomTabViewHolder = new RoomTabViewHolder(textView);
        roomTabViewHolder.setRoomClickListener(this.d);
        return roomTabViewHolder;
    }

    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        ((RoomTabViewHolder) baseViewHolder).bindData((MiotRoom) this.dataList.get(i), i, i == this.b);
    }
}
