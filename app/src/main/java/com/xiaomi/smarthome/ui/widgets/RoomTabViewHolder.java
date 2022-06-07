package com.xiaomi.smarthome.ui.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4TabAndSwitch;
import com.xiaomi.micolauncher.common.view.AnimLifecycleManager;
import com.xiaomi.micolauncher.common.view.BaseViewHolder;
import com.xiaomi.miot.support.core.MiotRoom;
import com.xiaomi.smarthome.R;
import io.reactivex.functions.Consumer;

/* loaded from: classes4.dex */
public class RoomTabViewHolder extends BaseViewHolder {
    private final TextView a;
    private MiotRoom b;
    private int c;
    private RoomClickListener d;

    /* loaded from: classes4.dex */
    public interface RoomClickListener {
        void onTabClick(MiotRoom miotRoom, int i);
    }

    @SuppressLint({"ClickableViewAccessibility", "CheckResult"})
    public RoomTabViewHolder(@NonNull View view) {
        super(view);
        this.a = (TextView) view;
        AnimLifecycleManager.repeatOnAttach(this.a, MicoAnimConfigurator4TabAndSwitch.get());
        RxViewHelp.debounceClicks(this.a, 500L).subscribe(new Consumer() { // from class: com.xiaomi.smarthome.ui.widgets.-$$Lambda$RoomTabViewHolder$_IFFVqIFQXI1z0PzFK12I3Zc6-E
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                RoomTabViewHolder.this.a(obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Object obj) throws Exception {
        RoomClickListener roomClickListener = this.d;
        if (roomClickListener != null) {
            roomClickListener.onTabClick(this.b, this.c);
        }
    }

    public void setRoomClickListener(RoomClickListener roomClickListener) {
        this.d = roomClickListener;
    }

    public void bindData(MiotRoom miotRoom, int i, boolean z) {
        int i2;
        Context context;
        this.b = miotRoom;
        this.c = i;
        if (miotRoom != null) {
            this.a.setText(miotRoom.roomName);
            this.a.setTextColor(this.itemView.getContext().getColor(z ? R.color.mico_miot_room_tab_name_selected_color : R.color.mico_miot_room_tab_name_normal_color));
            TextView textView = this.a;
            if (z) {
                context = this.itemView.getContext();
                i2 = R.string.font_sans_serif_demi_bold;
            } else {
                context = this.itemView.getContext();
                i2 = R.string.font_sans_serif_regular;
            }
            textView.setTypeface(Typeface.create(context.getString(i2), 0));
        }
    }
}
