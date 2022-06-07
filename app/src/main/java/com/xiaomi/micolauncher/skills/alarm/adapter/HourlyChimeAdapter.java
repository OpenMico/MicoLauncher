package com.xiaomi.micolauncher.skills.alarm.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.skills.alarm.adapter.HourlyChimeAdapter;
import com.xiaomi.micolauncher.skills.alarm.model.HourlyChimeMode;
import com.xiaomi.micolauncher.skills.alarm.model.HourlyChimeRealmHelper;
import com.xiaomi.micolauncher.skills.alarm.model.bean.HourlyChimeObject;
import com.xiaomi.micolauncher.skills.alarm.view.HourlyChimeActivity;
import io.reactivex.functions.Consumer;
import java.util.List;

/* loaded from: classes3.dex */
public class HourlyChimeAdapter extends RecyclerView.Adapter<a> {
    private List<HourlyChimeObject> a;
    private HourlyChimeRealmHelper b;
    private boolean c;
    private Activity d;

    public HourlyChimeAdapter(Activity activity, List<HourlyChimeObject> list, HourlyChimeRealmHelper hourlyChimeRealmHelper, boolean z) {
        this.d = activity;
        this.a = list;
        this.b = hourlyChimeRealmHelper;
        this.c = z;
    }

    public void updateStatus(boolean z) {
        this.c = z;
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public a onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new a(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_hourly_chime_view, viewGroup, false));
    }

    public void onBindViewHolder(a aVar, int i) {
        if (i < this.a.size()) {
            aVar.a(this.a.get(i));
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        if (ContainerUtil.hasData(this.a)) {
            return this.a.size();
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public class a extends RecyclerView.ViewHolder {
        RelativeLayout a;
        TextView b;
        Context c;
        HourlyChimeObject d;

        a(View view) {
            super(view);
            this.c = view.getContext();
            this.a = (RelativeLayout) view.findViewById(R.id.root_view);
            this.b = (TextView) view.findViewById(R.id.hours);
            RxViewHelp.debounceClicks(this.a, 500L).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.skills.alarm.adapter.-$$Lambda$HourlyChimeAdapter$a$inw4CKSaYuemtAhXMkTZsgRvd1k
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    HourlyChimeAdapter.a.this.a(obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(Object obj) throws Exception {
            a();
        }

        void a(HourlyChimeObject hourlyChimeObject) {
            this.d = hourlyChimeObject;
            this.b.setText(hourlyChimeObject.getContent());
            if (HourlyChimeAdapter.this.c) {
                this.a.setEnabled(true);
                if (hourlyChimeObject.getStatus()) {
                    this.a.setSelected(true);
                    this.b.setTextColor(this.c.getResources().getColor(R.color.hourly_chime_item_border_color, null));
                    return;
                }
                this.a.setSelected(false);
                this.b.setTextColor(this.c.getResources().getColor(R.color.hourly_chime_item_hour_color, null));
                return;
            }
            this.a.setEnabled(false);
            this.a.setSelected(false);
            this.b.setTextColor(this.c.getResources().getColor(R.color.hourly_chime_item_hour_color, null));
        }

        private void a() {
            HourlyChimeActivity hourlyChimeActivity = (HourlyChimeActivity) HourlyChimeAdapter.this.d;
            if (!this.d.getStatus() || hourlyChimeActivity.getSelectCount() > 1) {
                boolean status = this.d.getStatus();
                this.a.setSelected(!status);
                L.hourlychime.d("HourlyChimeAdapter updateStatus before %b ", Boolean.valueOf(this.d.getStatus()));
                HourlyChimeObject hourlyChimeObject = this.d;
                hourlyChimeObject.setStatus(!hourlyChimeObject.getStatus());
                HourlyChimeAdapter.this.b.updateHours(this.d);
                L.hourlychime.d("HourlyChimeAdapter updateStatus after %b ", Boolean.valueOf(this.d.getStatus()));
                if (this.d.getStatus()) {
                    this.b.setTextColor(this.c.getResources().getColor(R.color.hourly_chime_item_border_color, null));
                } else {
                    this.b.setTextColor(this.c.getResources().getColor(R.color.hourly_chime_item_hour_color, null));
                }
                if (this.d.getStatus()) {
                    hourlyChimeActivity.setSelectCount(hourlyChimeActivity.getSelectCount() + 1);
                } else {
                    hourlyChimeActivity.setSelectCount(hourlyChimeActivity.getSelectCount() - 1);
                }
                if (!status) {
                    HourlyChimeMode.getInstance().addHourlyChime(this.d);
                } else {
                    HourlyChimeMode.getInstance().deleteHourlyChime(this.d);
                }
            } else {
                ToastUtil.showToast(HourlyChimeAdapter.this.d.getString(R.string.hourly_chime_toast));
            }
        }
    }
}
