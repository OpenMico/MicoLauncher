package com.xiaomi.smarthome.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.elvishew.xlog.Logger;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.mico.base.utils.MD5;
import com.xiaomi.mico.settingslib.core.MicoSettingHome;
import com.xiaomi.mico.settingslib.core.MicoSettingRoom;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.view.BaseViewHolder;
import com.xiaomi.smarthome.R;
import com.xiaomi.smarthome.core.miot.MicoMiotDeviceManager;
import com.xiaomi.smarthome.ui.adapter.MiotRegionAdapter;
import com.xiaomi.smarthome.ui.widgets.MiotRegionViewHolder;
import com.xiaomi.smarthome.ui.widgets.RegionSelectListener;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.stream.Collectors;

/* loaded from: classes4.dex */
public class MiotRegionAdapter extends BaseMiotListAdapter<MiotRegion> {
    private final LayoutInflater a;
    private RegionSelectListener b;

    /* loaded from: classes4.dex */
    public static abstract class MiotRegion {
        public abstract String getHomeName();

        public abstract String getQueryHomeId();

        public abstract ArrayList<String> getQueryRoomIds();

        public abstract String getRegionId();

        public abstract String getRegionName();

        public abstract String getRoomName();

        public abstract boolean isCurrentSpeakerRoom();

        public abstract boolean isHasVoiceBox();

        public abstract boolean isSelected();

        public abstract void setSelected();
    }

    public MiotRegionAdapter(Context context) {
        this.a = LayoutInflater.from(context);
    }

    public void setmRegionSelectListener(RegionSelectListener regionSelectListener) {
        this.b = regionSelectListener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MiotRegionViewHolder(this.a.inflate(R.layout.item_smart_home_miot_home, viewGroup, false));
    }

    @SuppressLint({"CheckResult"})
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        if (baseViewHolder instanceof MiotRegionViewHolder) {
            MiotRegionViewHolder miotRegionViewHolder = (MiotRegionViewHolder) baseViewHolder;
            final MiotRegion miotRegion = (MiotRegion) this.dataList.get(i);
            miotRegionViewHolder.bindData(miotRegion, i, getItemCount());
            RxViewHelp.debounceClicksWithOneSeconds(miotRegionViewHolder.itemView).subscribe(new Consumer() { // from class: com.xiaomi.smarthome.ui.adapter.-$$Lambda$MiotRegionAdapter$CXdQNLJ2JnbboPTDk_VGcGid9fA
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    MiotRegionAdapter.this.a(miotRegion, obj);
                }
            });
        }
    }

    public /* synthetic */ void a(MiotRegion miotRegion, Object obj) throws Exception {
        miotRegion.setSelected();
        RegionSelectListener regionSelectListener = this.b;
        if (regionSelectListener != null) {
            regionSelectListener.onRegionChanged(miotRegion);
        }
        notifyDataSetChanged();
    }

    /* loaded from: classes4.dex */
    public static class MiotRegionHome extends MiotRegion {
        private MicoSettingHome a;

        @Override // com.xiaomi.smarthome.ui.adapter.MiotRegionAdapter.MiotRegion
        public String getRoomName() {
            return null;
        }

        @Override // com.xiaomi.smarthome.ui.adapter.MiotRegionAdapter.MiotRegion
        public boolean isCurrentSpeakerRoom() {
            return false;
        }

        public MiotRegionHome(@NonNull MicoSettingHome micoSettingHome) {
            this.a = null;
            this.a = micoSettingHome;
        }

        @Override // com.xiaomi.smarthome.ui.adapter.MiotRegionAdapter.MiotRegion
        public boolean isSelected() {
            MicoSettingHome micoSettingHome = this.a;
            if (micoSettingHome != null) {
                return micoSettingHome.isSelected();
            }
            return false;
        }

        @Override // com.xiaomi.smarthome.ui.adapter.MiotRegionAdapter.MiotRegion
        public String getRegionName() {
            MicoSettingHome micoSettingHome = this.a;
            if (micoSettingHome != null) {
                return micoSettingHome.getHomeName();
            }
            return null;
        }

        @Override // com.xiaomi.smarthome.ui.adapter.MiotRegionAdapter.MiotRegion
        public String getQueryHomeId() {
            MicoSettingHome micoSettingHome = this.a;
            if (micoSettingHome != null) {
                return micoSettingHome.getHomeId();
            }
            return null;
        }

        @Override // com.xiaomi.smarthome.ui.adapter.MiotRegionAdapter.MiotRegion
        public ArrayList<String> getQueryRoomIds() {
            MicoSettingHome micoSettingHome = this.a;
            if (micoSettingHome != null) {
                return (ArrayList) micoSettingHome.getRoomList().stream().filter($$Lambda$MiotRegionAdapter$MiotRegionHome$EZODM3FeHyQFXTFeHzPUdmhTADE.INSTANCE).map($$Lambda$MiotRegionAdapter$MiotRegionHome$QYDTjr6LgTyamEG2MtKavoldhs.INSTANCE).collect(Collectors.toCollection($$Lambda$OGSS2qx6njxlnp0dnKb4lA3jnw8.INSTANCE));
            }
            return new ArrayList<>();
        }

        @Override // com.xiaomi.smarthome.ui.adapter.MiotRegionAdapter.MiotRegion
        public void setSelected() {
            MicoSettingHome micoSettingHome = this.a;
            if (micoSettingHome != null) {
                micoSettingHome.setSelected(true);
                this.a.setChildSelected(false);
                this.a.getRoomList().stream().forEach($$Lambda$MiotRegionAdapter$MiotRegionHome$_ljwLXMvite1SorpUZg7JukV0uY.INSTANCE);
            }
        }

        @Override // com.xiaomi.smarthome.ui.adapter.MiotRegionAdapter.MiotRegion
        public String getRegionId() {
            MicoSettingHome micoSettingHome = this.a;
            if (micoSettingHome == null) {
                return "";
            }
            if (((Boolean) micoSettingHome.getRoomList().stream().map($$Lambda$MiotRegionAdapter$MiotRegionHome$838MmB6Wy2HHMO8sh1UTd_oMBvg.INSTANCE).reduce(true, $$Lambda$MiotRegionAdapter$MiotRegionHome$1ER0FXdwa582Mw5dmOBJPcTKmuI.INSTANCE)).booleanValue()) {
                return this.a.getHomeId();
            }
            String str = (String) this.a.getRoomList().stream().filter($$Lambda$MiotRegionAdapter$MiotRegionHome$OvyEkrHzTZDVRP1iED9OQqwN_yw.INSTANCE).map($$Lambda$MiotRegionAdapter$MiotRegionHome$y47bZ1zeNfh2LPpTZ73szbBOnXY.INSTANCE).reduce("", $$Lambda$MiotRegionAdapter$MiotRegionHome$8Ya3X36O58p5xfMWVUlZu0PSYw.INSTANCE);
            return this.a.getRoomList().size() <= 1 ? str : MD5.MD5_32(str);
        }

        public static /* synthetic */ Boolean d(MicoSettingRoom micoSettingRoom) {
            return Boolean.valueOf(micoSettingRoom.isConfigured());
        }

        public static /* synthetic */ Boolean a(Boolean bool, Boolean bool2) {
            return Boolean.valueOf(bool.booleanValue() && bool2.booleanValue());
        }

        public static /* synthetic */ String a(String str, String str2) {
            return str + str2;
        }

        @Override // com.xiaomi.smarthome.ui.adapter.MiotRegionAdapter.MiotRegion
        public boolean isHasVoiceBox() {
            if (!MicoMiotDeviceManager.isRegionFeatureOpen) {
                return true;
            }
            MicoSettingHome micoSettingHome = this.a;
            if (micoSettingHome == null || micoSettingHome.getRoomList() == null) {
                return false;
            }
            return this.a.getRoomList().stream().filter($$Lambda$MiotRegionAdapter$MiotRegionHome$BVdz0dtxsEYY1amm7TWXkv5Boo.INSTANCE).findFirst().isPresent();
        }

        @Override // com.xiaomi.smarthome.ui.adapter.MiotRegionAdapter.MiotRegion
        public String getHomeName() {
            MicoSettingHome micoSettingHome = this.a;
            if (micoSettingHome != null) {
                return micoSettingHome.getHomeName();
            }
            return null;
        }
    }

    /* loaded from: classes4.dex */
    public static class MiotRegionRoom extends MiotRegion {
        private MicoSettingHome a;
        private MicoSettingRoom b;

        public MiotRegionRoom(@NonNull MicoSettingHome micoSettingHome, @NonNull MicoSettingRoom micoSettingRoom) {
            this.a = micoSettingHome;
            this.b = micoSettingRoom;
        }

        @Override // com.xiaomi.smarthome.ui.adapter.MiotRegionAdapter.MiotRegion
        public boolean isSelected() {
            MicoSettingRoom micoSettingRoom = this.b;
            if (micoSettingRoom != null) {
                return micoSettingRoom.isSelected();
            }
            return false;
        }

        @Override // com.xiaomi.smarthome.ui.adapter.MiotRegionAdapter.MiotRegion
        public String getRegionName() {
            MicoSettingRoom micoSettingRoom = this.b;
            if (micoSettingRoom != null) {
                return micoSettingRoom.getRoomName();
            }
            return null;
        }

        @Override // com.xiaomi.smarthome.ui.adapter.MiotRegionAdapter.MiotRegion
        public String getQueryHomeId() {
            MicoSettingHome micoSettingHome = this.a;
            if (micoSettingHome != null) {
                return micoSettingHome.getHomeId();
            }
            return null;
        }

        @Override // com.xiaomi.smarthome.ui.adapter.MiotRegionAdapter.MiotRegion
        public ArrayList<String> getQueryRoomIds() {
            ArrayList<String> arrayList = new ArrayList<>();
            MicoSettingRoom micoSettingRoom = this.b;
            if (micoSettingRoom != null) {
                arrayList.add(micoSettingRoom.getRoomId());
            }
            return arrayList;
        }

        @Override // com.xiaomi.smarthome.ui.adapter.MiotRegionAdapter.MiotRegion
        public void setSelected() {
            MicoSettingHome micoSettingHome = this.a;
            if (micoSettingHome != null && this.b != null) {
                micoSettingHome.getRoomList().stream().forEach(new java.util.function.Consumer() { // from class: com.xiaomi.smarthome.ui.adapter.-$$Lambda$MiotRegionAdapter$MiotRegionRoom$qslkvbTazNuWvyXASD3T_xK2-mU
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        MiotRegionAdapter.MiotRegionRoom.this.a((MicoSettingRoom) obj);
                    }
                });
                this.a.setSelected(false);
                this.a.setChildSelected(true);
                Logger logger = L.smarthome;
                logger.e("mSetRoom=" + this.b.isSelected());
            }
        }

        public /* synthetic */ void a(MicoSettingRoom micoSettingRoom) {
            micoSettingRoom.setSelected(this.b.getRoomId().equals(micoSettingRoom.getRoomId()));
        }

        @Override // com.xiaomi.smarthome.ui.adapter.MiotRegionAdapter.MiotRegion
        public String getRegionId() {
            MicoSettingRoom micoSettingRoom = this.b;
            return micoSettingRoom != null ? micoSettingRoom.getRoomId() : "";
        }

        @Override // com.xiaomi.smarthome.ui.adapter.MiotRegionAdapter.MiotRegion
        public boolean isCurrentSpeakerRoom() {
            MicoSettingRoom micoSettingRoom = this.b;
            if (micoSettingRoom != null) {
                return micoSettingRoom.isCurrentSpeakerRoom();
            }
            return false;
        }

        @Override // com.xiaomi.smarthome.ui.adapter.MiotRegionAdapter.MiotRegion
        public boolean isHasVoiceBox() {
            if (!MicoMiotDeviceManager.isRegionFeatureOpen) {
                return true;
            }
            MicoSettingRoom micoSettingRoom = this.b;
            if (micoSettingRoom == null) {
                return false;
            }
            return micoSettingRoom.isCurrentSpeakerRoom();
        }

        @Override // com.xiaomi.smarthome.ui.adapter.MiotRegionAdapter.MiotRegion
        public String getHomeName() {
            MicoSettingHome micoSettingHome = this.a;
            if (micoSettingHome != null) {
                return micoSettingHome.getHomeName();
            }
            return null;
        }

        @Override // com.xiaomi.smarthome.ui.adapter.MiotRegionAdapter.MiotRegion
        public String getRoomName() {
            MicoSettingRoom micoSettingRoom = this.b;
            if (micoSettingRoom != null) {
                return micoSettingRoom.getRoomName();
            }
            return null;
        }
    }
}
