package com.xiaomi.smarthome.core.db;

import androidx.annotation.WorkerThread;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import com.blankj.utilcode.util.GsonUtils;
import com.elvishew.xlog.Logger;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.smarthome.core.entity.DeviceInfoBean;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: RankDao.kt */
@Dao
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b'\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H'J\u0018\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006H'J\u001c\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0016\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\u0006\u0010\u0005\u001a\u00020\u0006H'J\u001a\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\u00102\u0006\u0010\u0005\u001a\u00020\u0006J&\u0010\u0011\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u00120\n2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006H\u0007J\u001e\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00120\r2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006H'J\u0018\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006H'J\u0018\u0010\u0015\u001a\u00020\u000b2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006H'J\u001e\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u000e0\rH\u0017J'\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u000e0\rH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u001aJ\u0016\u0010\u001b\u001a\u00020\u00042\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u000e0\rH'J&\u0010\u001c\u001a\u00020\u00042\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u001d0\r2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006H\u0017J\u0016\u0010\u001e\u001a\u00020\u00042\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00120\rH'\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u001f"}, d2 = {"Lcom/xiaomi/smarthome/core/db/RankDao;", "", "()V", "deleteAllCategoryByHomeId", "", "homeId", "", "deleteDeviceRankRecordsByCategory", "categoryName", "getCategoryRankByHomeId", "", "", "getCategoryRankRecordsByCategoryIdInner", "", "Lcom/xiaomi/smarthome/core/db/CategoryRankEntity;", "getCategoryRankRecordsByHomeId", "Lkotlinx/coroutines/flow/Flow;", "getDeviceRankRecordsByCategoryId", "Lcom/xiaomi/smarthome/core/db/DeviceRankEntity;", "getDeviceRankRecordsByCategoryIdInner", "getDeviceShowCountByCategoryId", "getFirstPageShowCount", "insertCategoryRankRecords", "list", "insertCategoryRankRecordsForKotlin", "", "(Ljava/lang/String;Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertCategoryRankRecordsInner", "insertDeviceRankRecords", "Lcom/xiaomi/smarthome/core/entity/DeviceInfoBean;", "insertDeviceRankRecordsInner", "smarthome_release"}, k = 1, mv = {1, 4, 2})
/* loaded from: classes4.dex */
public abstract class RankDao {
    @Query("delete  from CategoryRankEntity where homeId=:homeId")
    public abstract void deleteAllCategoryByHomeId(@NotNull String str);

    @Query("delete  from DeviceRankEntity where homeId=:homeId and categoryName=:categoryName")
    public abstract void deleteDeviceRankRecordsByCategory(@NotNull String str, @NotNull String str2);

    @Query("select * from CategoryRankEntity where homeId=:homeId")
    @NotNull
    public abstract List<CategoryRankEntity> getCategoryRankRecordsByCategoryIdInner(@NotNull String str);

    @Query("select * from DeviceRankEntity where homeId=:homeId and categoryName=:categoryName")
    @NotNull
    public abstract List<DeviceRankEntity> getDeviceRankRecordsByCategoryIdInner(@NotNull String str, @NotNull String str2);

    @Query("select count(*) from DeviceRankEntity where homeId=:homeId and categoryName=:categoryName and isShowInFirstPage=1")
    public abstract int getDeviceShowCountByCategoryId(@NotNull String str, @NotNull String str2);

    @Query("select count(*) from DeviceRankEntity where homeId=:homeId and categoryName=:categoryName and isShowInFirstPage=1")
    public abstract int getFirstPageShowCount(@NotNull String str, @NotNull String str2);

    @Insert(onConflict = 1)
    public abstract void insertCategoryRankRecordsInner(@NotNull List<CategoryRankEntity> list);

    @Insert(onConflict = 1)
    public abstract void insertDeviceRankRecordsInner(@NotNull List<DeviceRankEntity> list);

    @WorkerThread
    @NotNull
    public final Map<String, DeviceRankEntity> getDeviceRankRecordsByCategoryId(@NotNull String homeId, @NotNull String categoryName) {
        Intrinsics.checkNotNullParameter(homeId, "homeId");
        Intrinsics.checkNotNullParameter(categoryName, "categoryName");
        List<DeviceRankEntity> deviceRankRecordsByCategoryIdInner = getDeviceRankRecordsByCategoryIdInner(homeId, categoryName);
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(deviceRankRecordsByCategoryIdInner, 10)), 16));
        for (Object obj : deviceRankRecordsByCategoryIdInner) {
            linkedHashMap.put(((DeviceRankEntity) obj).getDid(), obj);
        }
        return linkedHashMap;
    }

    @WorkerThread
    @NotNull
    public final Map<String, Integer> getCategoryRankByHomeId(@NotNull String homeId) {
        Intrinsics.checkNotNullParameter(homeId, "homeId");
        List<CategoryRankEntity> categoryRankRecordsByCategoryIdInner = getCategoryRankRecordsByCategoryIdInner(homeId);
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(categoryRankRecordsByCategoryIdInner, 10)), 16));
        for (CategoryRankEntity categoryRankEntity : categoryRankRecordsByCategoryIdInner) {
            Pair pair = TuplesKt.to(categoryRankEntity.getCategoryName(), Integer.valueOf(categoryRankEntity.getOrderNo()));
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        return linkedHashMap;
    }

    @NotNull
    public final Flow<List<CategoryRankEntity>> getCategoryRankRecordsByHomeId(@NotNull String homeId) {
        Intrinsics.checkNotNullParameter(homeId, "homeId");
        return FlowKt.flowOf(getCategoryRankRecordsByCategoryIdInner(homeId));
    }

    @WorkerThread
    @Transaction
    public void insertDeviceRankRecords(@NotNull List<DeviceInfoBean> list, @NotNull String homeId, @NotNull String categoryName) {
        Intrinsics.checkNotNullParameter(list, "list");
        Intrinsics.checkNotNullParameter(homeId, "homeId");
        Intrinsics.checkNotNullParameter(categoryName, "categoryName");
        deleteDeviceRankRecordsByCategory(homeId, categoryName);
        ArrayList arrayList = new ArrayList();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            DeviceInfoBean deviceInfoBean = list.get(i);
            String str = deviceInfoBean.getDeviceInfoWrapper().getDeviceInfo().did;
            Intrinsics.checkNotNullExpressionValue(str, "device.deviceInfo.did");
            arrayList.add(new DeviceRankEntity(str, i, categoryName, homeId, deviceInfoBean.isShowInFirstPage()));
        }
        insertDeviceRankRecordsInner(arrayList);
    }

    @WorkerThread
    @Transaction
    public void insertCategoryRankRecords(@NotNull String homeId, @NotNull List<CategoryRankEntity> list) {
        Intrinsics.checkNotNullParameter(homeId, "homeId");
        Intrinsics.checkNotNullParameter(list, "list");
        deleteAllCategoryByHomeId(homeId);
        int size = list.size();
        for (int i = 0; i < size; i++) {
            list.get(i).setOrderNo(i);
        }
        Logger logger = L.smarthome;
        logger.d("RankDao insertCategoryRankRecords list=" + GsonUtils.toJson(list));
        insertCategoryRankRecordsInner(list);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: RankDao.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, k = 3, mv = {1, 4, 2})
    @DebugMetadata(c = "com.xiaomi.smarthome.core.db.RankDao$insertCategoryRankRecordsForKotlin$2", f = "RankDao.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* loaded from: classes4.dex */
    public static final class a extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Boolean>, Object> {
        final /* synthetic */ String $homeId;
        final /* synthetic */ List $list;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        a(String str, List list, Continuation continuation) {
            super(2, continuation);
            RankDao.this = r1;
            this.$homeId = str;
            this.$list = list;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> completion) {
            Intrinsics.checkNotNullParameter(completion, "completion");
            return new a(this.$homeId, this.$list, completion);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Boolean> continuation) {
            return ((a) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            boolean z;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                try {
                    RankDao.this.insertCategoryRankRecords(this.$homeId, this.$list);
                    z = true;
                } catch (Exception e) {
                    L.smarthome.e("insertCategoryRankRecordsForKotlin", e);
                    z = false;
                }
                return Boxing.boxBoolean(z);
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    @Nullable
    public final Object insertCategoryRankRecordsForKotlin(@NotNull String str, @NotNull List<CategoryRankEntity> list, @NotNull Continuation<? super Boolean> continuation) {
        return BuildersKt.withContext(Dispatchers.getDefault(), new a(str, list, null), continuation);
    }
}
