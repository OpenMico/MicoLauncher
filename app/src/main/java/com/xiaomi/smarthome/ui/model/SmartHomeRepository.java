package com.xiaomi.smarthome.ui.model;

import android.util.Log;
import androidx.annotation.WorkerThread;
import com.blankj.utilcode.util.GsonUtils;
import com.elvishew.xlog.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.miot.support.MicoSupConstants;
import com.xiaomi.miot.support.MiotManager;
import com.xiaomi.miot.support.category.DeviceCategory;
import com.xiaomi.miot.support.category.DeviceInfoWrapper;
import com.xiaomi.miot.support.category.HomeCategory;
import com.xiaomi.miot.support.category.ModelInfo;
import com.xiaomi.smarthome.core.db.CategoryRankEntity;
import com.xiaomi.smarthome.core.db.Db;
import com.xiaomi.smarthome.core.db.DeviceRankEntity;
import com.xiaomi.smarthome.core.db.RankDao;
import com.xiaomi.smarthome.core.entity.CategoryDic;
import com.xiaomi.smarthome.core.entity.DeviceInfoBean;
import com.xiaomi.smarthome.core.entity.MediaType;
import com.xiaomi.smarthome.core.entity.MicoMediaData;
import com.xiaomi.smarthome.core.entity.TypeGroup;
import com.xiaomi.smarthome.core.exts.LogExtsKt;
import com.xiaomi.smarthome.core.utils.BundleTypeAdapterFactory;
import com.xiaomi.smarthome.ui.adapter.MiotRegionAdapter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: SmartHomeRepository.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00142\u00020\u0001:\u0001\u0014B\u0005¢\u0006\u0002\u0010\u0002J \u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0007R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0015"}, d2 = {"Lcom/xiaomi/smarthome/ui/model/SmartHomeRepository;", "", "()V", "deviceCount", "", "getDeviceCount", "()I", "setDeviceCount", "(I)V", "gson", "Lcom/google/gson/Gson;", "getGson", "()Lcom/google/gson/Gson;", "loadDevices", "", "Lcom/xiaomi/smarthome/ui/model/ISmartHomeModel;", "region", "Lcom/xiaomi/smarthome/ui/adapter/MiotRegionAdapter$MiotRegion;", "mediaData", "Lcom/xiaomi/smarthome/core/entity/MicoMediaData;", "Companion", "smarthome_release"}, k = 1, mv = {1, 4, 2})
/* loaded from: classes4.dex */
public final class SmartHomeRepository {
    @NotNull
    public static final String KEY_GSON = "BundleCompat";
    @NotNull
    private final Gson a;
    private volatile int b;
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private static final Lazy c = LazyKt.lazy(LazyThreadSafetyMode.SYNCHRONIZED, (Function0) a.a);

    /* compiled from: SmartHomeRepository.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", "it", "Lcom/xiaomi/miot/support/category/DeviceCategory;", "invoke"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    static final class b extends Lambda implements Function1<DeviceCategory, Boolean> {
        public static final b a = new b();

        b() {
            super(1);
        }

        @Override // kotlin.jvm.functions.Function1
        public /* synthetic */ Boolean invoke(DeviceCategory deviceCategory) {
            return Boolean.valueOf(a(deviceCategory));
        }

        public final boolean a(@NotNull DeviceCategory it) {
            Intrinsics.checkNotNullParameter(it, "it");
            CategoryDic.Companion companion = CategoryDic.Companion;
            String categoryName = it.getCategoryName();
            Intrinsics.checkNotNullExpressionValue(categoryName, "it.categoryName");
            return companion.shouldBeShow(categoryName);
        }
    }

    public SmartHomeRepository() {
        Gson create = new GsonBuilder().registerTypeAdapterFactory(new BundleTypeAdapterFactory()).create();
        Intrinsics.checkNotNullExpressionValue(create, "GsonBuilder().registerTy…dapterFactory()).create()");
        this.a = create;
        GsonUtils.setGson(KEY_GSON, this.a);
    }

    @NotNull
    public final Gson getGson() {
        return this.a;
    }

    public final int getDeviceCount() {
        return this.b;
    }

    public final void setDeviceCount(int i) {
        this.b = i;
    }

    /* compiled from: SmartHomeRepository.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001b\u0010\u0003\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\t\u001a\u00020\nX\u0086T¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lcom/xiaomi/smarthome/ui/model/SmartHomeRepository$Companion;", "", "()V", "INSTANCE", "Lcom/xiaomi/smarthome/ui/model/SmartHomeRepository;", "getINSTANCE", "()Lcom/xiaomi/smarthome/ui/model/SmartHomeRepository;", "INSTANCE$delegate", "Lkotlin/Lazy;", "KEY_GSON", "", "smarthome_release"}, k = 1, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    public static final class Companion {
        @NotNull
        public final SmartHomeRepository getINSTANCE() {
            Lazy lazy = SmartHomeRepository.c;
            Companion companion = SmartHomeRepository.Companion;
            return (SmartHomeRepository) lazy.getValue();
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: SmartHomeRepository.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {"<anonymous>", "Lcom/xiaomi/smarthome/ui/model/SmartHomeRepository;", "invoke"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    static final class a extends Lambda implements Function0<SmartHomeRepository> {
        public static final a a = new a();

        a() {
            super(0);
        }

        @NotNull
        /* renamed from: a */
        public final SmartHomeRepository invoke() {
            return new SmartHomeRepository();
        }
    }

    @WorkerThread
    @NotNull
    public final List<ISmartHomeModel> loadDevices(@NotNull MiotRegionAdapter.MiotRegion region, @Nullable MicoMediaData micoMediaData) {
        Intrinsics.checkNotNullParameter(region, "region");
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(MiotManager.getRoomDevices(region.getQueryHomeId(), region.getQueryRoomIds()));
        Logger logger = L.smarthome;
        Intrinsics.checkNotNullExpressionValue(logger, "L.smarthome");
        Logger appendLine = LogExtsKt.appendLine(logger);
        appendLine.d("miot devices size:" + arrayList.size());
        this.b = 0;
        Map<String, HomeCategory> homeCategories = MiotManager.getHomeCategories(arrayList);
        Log.i(MicoSupConstants.TAG_LAU, "Info: launcher call getHomeCategories finish, map: " + homeCategories);
        if (homeCategories == null) {
            Logger logger2 = L.smarthome;
            Intrinsics.checkNotNullExpressionValue(logger2, "L.smarthome");
            LogExtsKt.appendLine(logger2).e("MiotManager.getHomeCategories returned null");
            return new ArrayList();
        }
        Logger logger3 = L.smarthome;
        Intrinsics.checkNotNullExpressionValue(logger3, "L.smarthome");
        Logger appendLine2 = LogExtsKt.appendLine(logger3);
        appendLine2.d("region=" + region.getRegionId() + ",miot map:" + GsonUtils.getGson(KEY_GSON).toJson(homeCategories));
        ArrayList arrayList2 = new ArrayList();
        if (region.isHasVoiceBox()) {
            arrayList2.add(new DeviceCategory(CategoryDic.CATEGORY_MEDIA.getTypeName()));
        }
        HomeCategory homeCategory = homeCategories.get(region.getQueryHomeId());
        if (homeCategory != null) {
            arrayList2.addAll(homeCategory.getDeviceCategories().values());
        }
        RankDao rankDao = Db.Companion.getInstance().rankDao();
        String regionId = region.getRegionId();
        Intrinsics.checkNotNullExpressionValue(regionId, "region.regionId");
        List mutableList = SequencesKt.toMutableList(SequencesKt.sortedWith(SequencesKt.map(SequencesKt.map(SequencesKt.filter(CollectionsKt.asSequence(arrayList2), b.a), new c(micoMediaData, MapsKt.toMutableMap(rankDao.getCategoryRankByHomeId(regionId)), region)), new d()), new Comparator<T>() { // from class: com.xiaomi.smarthome.ui.model.SmartHomeRepository$loadDevices$$inlined$sortedBy$1
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                return ComparisonsKt.compareValues(Integer.valueOf(((ISmartHomeModel) t).getOrderNo()), Integer.valueOf(((ISmartHomeModel) t2).getOrderNo()));
            }
        }));
        List list = mutableList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (Object obj : list) {
            ISmartHomeModel iSmartHomeModel = (ISmartHomeModel) obj;
            String typeName = iSmartHomeModel.type().getTypeName();
            int orderNo = iSmartHomeModel.getOrderNo();
            String regionId2 = region.getRegionId();
            Intrinsics.checkNotNullExpressionValue(regionId2, "region.regionId");
            arrayList3.add(new CategoryRankEntity(typeName, orderNo, regionId2, obj instanceof TypeGroup ? ((TypeGroup) obj).getDeviceList().size() : 1));
        }
        List<CategoryRankEntity> list2 = CollectionsKt.toList(arrayList3);
        RankDao rankDao2 = Db.Companion.getInstance().rankDao();
        String regionId3 = region.getRegionId();
        Intrinsics.checkNotNullExpressionValue(regionId3, "region.regionId");
        rankDao2.insertCategoryRankRecords(regionId3, list2);
        return CollectionsKt.toMutableList((Collection) mutableList);
    }

    /* compiled from: SmartHomeRepository.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", "it", "Lcom/xiaomi/miot/support/category/DeviceCategory;", "invoke"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    static final class c extends Lambda implements Function1<DeviceCategory, Object> {
        final /* synthetic */ MicoMediaData $mediaData;
        final /* synthetic */ Map $rankCategoryMap;
        final /* synthetic */ MiotRegionAdapter.MiotRegion $region;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        c(MicoMediaData micoMediaData, Map map, MiotRegionAdapter.MiotRegion miotRegion) {
            super(1);
            this.$mediaData = micoMediaData;
            this.$rankCategoryMap = map;
            this.$region = miotRegion;
        }

        @NotNull
        /* renamed from: a */
        public final Object invoke(@NotNull DeviceCategory it) {
            Intrinsics.checkNotNullParameter(it, "it");
            if (Intrinsics.areEqual(it.getCategoryName(), CategoryDic.CATEGORY_MEDIA.getTypeName())) {
                MicoMediaData micoMediaData = this.$mediaData;
                if (micoMediaData == null) {
                    micoMediaData = new MicoMediaData(MediaType.NONE, "", "", "", null, null, null, 0, null, 496, null);
                }
                Integer num = (Integer) this.$rankCategoryMap.get(it.getCategoryName());
                if (num == null) {
                    return micoMediaData;
                }
                micoMediaData.setOrderNo(num.intValue());
                return micoMediaData;
            }
            RankDao rankDao = Db.Companion.getInstance().rankDao();
            String regionId = this.$region.getRegionId();
            Intrinsics.checkNotNullExpressionValue(regionId, "region.regionId");
            String categoryName = it.getCategoryName();
            Intrinsics.checkNotNullExpressionValue(categoryName, "it.categoryName");
            Map<String, DeviceRankEntity> deviceRankRecordsByCategoryId = rankDao.getDeviceRankRecordsByCategoryId(regionId, categoryName);
            Logger logger = L.smarthome;
            Intrinsics.checkNotNullExpressionValue(logger, "L.smarthome");
            LogExtsKt.appendLine(logger).d("miot rank records:" + SmartHomeRepository.this.getGson().toJson(deviceRankRecordsByCategoryId));
            List<DeviceInfoWrapper> devices = it.getDevices();
            Intrinsics.checkNotNullExpressionValue(devices, "it.devices");
            ArrayList arrayList = new ArrayList();
            for (Object obj : devices) {
                DeviceInfoWrapper device = (DeviceInfoWrapper) obj;
                CategoryDic.Companion companion = CategoryDic.Companion;
                Intrinsics.checkNotNullExpressionValue(device, "device");
                ModelInfo modelInfo = device.getModelInfo();
                Intrinsics.checkNotNullExpressionValue(modelInfo, "device.modelInfo");
                String deviceType = modelInfo.getDeviceType();
                Intrinsics.checkNotNullExpressionValue(deviceType, "device.modelInfo.deviceType");
                if (companion.deviceShouldBeShow(deviceType)) {
                    arrayList.add(obj);
                }
            }
            ArrayList arrayList2 = arrayList;
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
            Iterator it2 = arrayList2.iterator();
            while (true) {
                boolean z = true;
                int i = 0;
                if (!it2.hasNext()) {
                    break;
                }
                DeviceInfoWrapper d = (DeviceInfoWrapper) it2.next();
                Intrinsics.checkNotNullExpressionValue(d, "d");
                DeviceRankEntity deviceRankEntity = deviceRankRecordsByCategoryId.get(d.getDeviceInfo().did);
                int orderNo = deviceRankEntity != null ? deviceRankEntity.getOrderNo() : Integer.MAX_VALUE;
                if (!(this.$region instanceof MiotRegionAdapter.MiotRegionRoom) || !Hardware.isX6A()) {
                    z = false;
                }
                if (deviceRankEntity != null) {
                    i = deviceRankEntity.isShowInFirstPage();
                }
                arrayList3.add(new DeviceInfoBean(orderNo, d, z, i));
            }
            List mutableList = CollectionsKt.toMutableList((Collection) CollectionsKt.sortedWith(arrayList3, new Comparator<T>() { // from class: com.xiaomi.smarthome.ui.model.SmartHomeRepository$loadDevices$sortedHomeDevices$2$$special$$inlined$sortedBy$1
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    return ComparisonsKt.compareValues(Integer.valueOf(((DeviceInfoBean) t).getOrderNo()), Integer.valueOf(((DeviceInfoBean) t2).getOrderNo()));
                }
            }));
            RankDao rankDao2 = Db.Companion.getInstance().rankDao();
            String regionId2 = this.$region.getRegionId();
            Intrinsics.checkNotNullExpressionValue(regionId2, "region.regionId");
            String categoryName2 = it.getCategoryName();
            Intrinsics.checkNotNullExpressionValue(categoryName2, "it.categoryName");
            int deviceShowCountByCategoryId = rankDao2.getDeviceShowCountByCategoryId(regionId2, categoryName2);
            if (deviceShowCountByCategoryId == 0) {
                CategoryDic.Companion companion2 = CategoryDic.Companion;
                String categoryName3 = it.getCategoryName();
                Intrinsics.checkNotNullExpressionValue(categoryName3, "it.categoryName");
                deviceShowCountByCategoryId = companion2.getDefaultShowCount(categoryName3);
            }
            if (deviceRankRecordsByCategoryId == null || deviceRankRecordsByCategoryId.isEmpty()) {
                int size = mutableList.size();
                int i2 = 0;
                while (i2 < size) {
                    ((DeviceInfoBean) mutableList.get(i2)).setShowInFirstPage(i2 < deviceShowCountByCategoryId ? 1 : 0);
                    i2++;
                }
            }
            Logger logger2 = L.smarthome;
            Intrinsics.checkNotNullExpressionValue(logger2, "L.smarthome");
            LogExtsKt.appendLine(logger2).d("miot category=" + it.getCategoryName() + ",sorted list:" + SmartHomeRepository.this.getGson().toJson(mutableList));
            CategoryDic.Companion companion3 = CategoryDic.Companion;
            String categoryName4 = it.getCategoryName();
            Intrinsics.checkNotNullExpressionValue(categoryName4, "it.categoryName");
            if (categoryName4 != null) {
                TypeGroup typeGroup = new TypeGroup(companion3.mapToEnum(StringsKt.trim(categoryName4).toString()), mutableList, deviceShowCountByCategoryId);
                Integer num2 = (Integer) this.$rankCategoryMap.get(it.getCategoryName());
                if (num2 != null) {
                    typeGroup.setOrderNo(num2.intValue());
                }
                return typeGroup;
            }
            throw new NullPointerException("null cannot be cast to non-null type kotlin.CharSequence");
        }
    }

    /* compiled from: SmartHomeRepository.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0001\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {"<anonymous>", "", "it", "", "invoke", "(Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 4, 2})
    /* loaded from: classes4.dex */
    static final class d extends Lambda implements Function1<Object, Object> {
        d() {
            super(1);
        }

        @Override // kotlin.jvm.functions.Function1
        @NotNull
        public final Object invoke(@NotNull Object it) {
            Intrinsics.checkNotNullParameter(it, "it");
            if (it instanceof MicoMediaData) {
                SmartHomeRepository smartHomeRepository = SmartHomeRepository.this;
                smartHomeRepository.setDeviceCount(smartHomeRepository.getDeviceCount() + 1);
            } else if (it instanceof TypeGroup) {
                SmartHomeRepository smartHomeRepository2 = SmartHomeRepository.this;
                int deviceCount = smartHomeRepository2.getDeviceCount();
                ArrayList arrayList = new ArrayList();
                for (Object obj : ((TypeGroup) it).getDeviceList()) {
                    if (((DeviceInfoBean) obj).isShowInFirstPage() == 1) {
                        arrayList.add(obj);
                    }
                }
                smartHomeRepository2.setDeviceCount(deviceCount + arrayList.size());
            }
            return it;
        }
    }
}
