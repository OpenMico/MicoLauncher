package com.xiaomi.micolauncher.module.miot_v2;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.mico.token.TokenManager;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.module.miot_v2.MIoTHomePopupWindow;
import com.xiaomi.miot.support.core.MiotHome;
import java.util.List;

/* loaded from: classes3.dex */
public class MIoTHomeAdapter extends RecyclerView.Adapter<a> {
    private Context a;
    private List<MiotHome> b;
    private MIoTHomePopupWindow.HomeSelectListener c;

    public MIoTHomeAdapter(Context context) {
        this.a = context;
    }

    public void setHomeList(List<MiotHome> list) {
        this.b = list;
    }

    public void setListener(MIoTHomePopupWindow.HomeSelectListener homeSelectListener) {
        this.c = homeSelectListener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public a onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new a(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_miot_home_v2, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull a aVar, int i) {
        int i2;
        Context context;
        final MiotHome miotHome = this.b.get(i);
        String str = miotHome.homeName;
        if (TextUtils.isEmpty(str)) {
            str = this.a.getString(R.string.miot_home_name_empty_suffix, TokenManager.getInstance().getUserId());
        }
        aVar.a.setText(str);
        TextView textView = aVar.a;
        if (miotHome.selected) {
            context = this.a;
            i2 = R.color.miot_support_room;
        } else {
            context = this.a;
            i2 = R.color.color_898F9A;
        }
        textView.setTextColor(ContextCompat.getColor(context, i2));
        aVar.a.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.miot_v2.-$$Lambda$MIoTHomeAdapter$WGzgnkHTEIV9STSXJML_8QR6o0c
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MIoTHomeAdapter.this.a(miotHome, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(MiotHome miotHome, View view) {
        for (MiotHome miotHome2 : this.b) {
            miotHome2.selected = miotHome2.homeId.equals(miotHome.homeId);
        }
        MIoTHomePopupWindow.HomeSelectListener homeSelectListener = this.c;
        if (homeSelectListener != null) {
            homeSelectListener.onHomeChanged(miotHome, this.b);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return ContainerUtil.getSize(this.b);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static class a extends RecyclerView.ViewHolder {
        TextView a;

        public a(View view) {
            super(view);
            this.a = (TextView) view.findViewById(R.id.miot_home_name);
        }
    }
}
