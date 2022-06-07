package com.xiaomi.micolauncher.skills.miot.multipledevices;

import android.graphics.Rect;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.miot.support.MiotManager;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes3.dex */
public class MiotMultipleDevicesAdapter extends RecyclerView.Adapter<a> {
    private static String a = "MiotMultipleDevicesAdapter";
    private List<MultipleDevicesItem> b;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static class a extends RecyclerView.ViewHolder {
        LinearLayout a;
        ImageView b;
        TextView c;
        TextView d;

        public a(View view) {
            super(view);
            this.a = (LinearLayout) view.findViewById(R.id.layoutmiotMultipleDevicesItem);
            this.b = (ImageView) view.findViewById(R.id.imageView);
            this.c = (TextView) view.findViewById(R.id.deviceName);
            this.d = (TextView) view.findViewById(R.id.roomName);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public MiotMultipleDevicesAdapter(List<MultipleDevicesItem> list) {
        this.b = list;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(List<MultipleDevicesItem> list) {
        this.b = list;
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NotNull
    public a onCreateViewHolder(@NotNull ViewGroup viewGroup, int i) {
        return new a(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.miot_multiple_devices_select_item, viewGroup, false));
    }

    public void onBindViewHolder(@NotNull final a aVar, int i) {
        final MultipleDevicesItem multipleDevicesItem = this.b.get(i);
        L.miot.d("%s device2 name: %s, room name: %s, url: %s", a, multipleDevicesItem.getDeviceName(), multipleDevicesItem.getRoomName(), multipleDevicesItem.getViewUrl());
        aVar.b.setImageDrawable(null);
        Glide.with(aVar.b).load(multipleDevicesItem.getViewUrl()).into(aVar.b);
        aVar.c.setText(multipleDevicesItem.getDeviceName());
        aVar.d.setText(multipleDevicesItem.getRoomName());
        aVar.a.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.skills.miot.multipledevices.-$$Lambda$MiotMultipleDevicesAdapter$UzG8ySdXPgeeCG1yEvG0jXRVJg0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MiotMultipleDevicesAdapter.a(MultipleDevicesItem.this, aVar, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(MultipleDevicesItem multipleDevicesItem, @NotNull a aVar, View view) {
        L.miot.d("%s onClickListener", a);
        String did = multipleDevicesItem.getDid();
        String type = multipleDevicesItem.getType();
        if (!TextUtils.isEmpty(did) && !TextUtils.isEmpty(type)) {
            L.miot.d("%s click showDevicePage did=%s, type=%s", a, multipleDevicesItem.getDid(), multipleDevicesItem.getType());
            MiotManager.showDevicePage(aVar.b.getContext(), did, type);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<MultipleDevicesItem> list = this.b;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    /* loaded from: classes3.dex */
    public static class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int a;

        /* JADX INFO: Access modifiers changed from: package-private */
        public SpacesItemDecoration(int i) {
            L.miot.d("%s space: %s", MiotMultipleDevicesAdapter.a, Integer.valueOf(i));
            this.a = i;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            int itemCount = adapter == null ? 0 : adapter.getItemCount();
            if (Hardware.isBigScreen()) {
                rect.right = this.a * 4;
                if (recyclerView.getChildAdapterPosition(view) == itemCount - 1) {
                    rect.right = 0;
                    return;
                }
                return;
            }
            rect.left = this.a;
            if (recyclerView.getChildAdapterPosition(view) == 0) {
                rect.left = this.a * 4;
            }
            if (recyclerView.getChildAdapterPosition(view) == itemCount - 1) {
                rect.right = this.a * 4;
            }
        }
    }
}
