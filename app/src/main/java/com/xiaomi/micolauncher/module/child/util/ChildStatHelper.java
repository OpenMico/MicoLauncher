package com.xiaomi.micolauncher.module.child.util;

import com.xiaomi.micolauncher.api.model.IListItem;
import com.xiaomi.micolauncher.api.model.ProductPrice;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.stat.StatKeyGenerateName;
import com.xiaomi.micolauncher.common.stat.StatUtils;
import com.xiaomi.micolauncher.common.track.EventType;
import com.xiaomi.micolauncher.common.track.ExtendJsonWrapper;
import com.xiaomi.micolauncher.common.track.TrackConstant;
import com.xiaomi.micolauncher.common.track.TrackStat;
import com.xiaomi.micolauncher.common.track.TrackWidget;
import com.xiaomi.micolauncher.common.utils.Util;
import com.xiaomi.micolauncher.module.child.course.KidCourseDataManager;
import com.xiaomi.micolauncher.module.video.manager.VideoStatHelper;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import org.apache.commons.lang3.concurrent.AbstractCircuitBreaker;

/* loaded from: classes3.dex */
public class ChildStatHelper {
    private static String a;

    /* loaded from: classes3.dex */
    public enum ChildStatKey implements StatKeyGenerateName {
        CHILD_MODE_OPEN,
        CHILD_MODE_CLOSE,
        CHILD_TAB_RECOMMEND_CLICK;

        @Override // com.xiaomi.micolauncher.common.stat.StatKeyGenerateName
        public String lowerCaseName() {
            return name().toLowerCase();
        }
    }

    private static String a(ChildStatKey childStatKey) {
        return childStatKey.name().toLowerCase();
    }

    /* loaded from: classes3.dex */
    public enum SwitchChildMode implements StatKeyGenerateName {
        OPEN_FROM_APP,
        OPEN_FROM_LAUNCHER,
        CLOSE;

        @Override // com.xiaomi.micolauncher.common.stat.StatKeyGenerateName
        public String lowerCaseName() {
            return name().toLowerCase();
        }
    }

    public static void recordChildTabCardClick(String str) {
        StatUtils.recordCountEvent("childTab", a(ChildStatKey.CHILD_TAB_RECOMMEND_CLICK), "category", str);
    }

    public static void recordChildModeSwitch(ChildStatKey childStatKey, SwitchChildMode switchChildMode) {
        StatUtils.recordCountEvent(VideoStatHelper.PARAM_KEY_CHILD_MODE, a(childStatKey), "category", switchChildMode.lowerCaseName());
    }

    public static void recordKidVideoDiscoveryClick(TrackWidget trackWidget, IListItem iListItem, String str) {
        if (iListItem != null) {
            ExtendJsonWrapper obtain = ExtendJsonWrapper.obtain();
            obtain.setCpId(iListItem.getItemId());
            obtain.setCpName("mitv");
            obtain.setMediaType(TrackConstant.MEDIA_TYPE_LONG_VIDEO);
            obtain.setModuleName(str);
            TrackStat.simpleCountEvent(trackWidget, EventType.CLICK, obtain);
        }
    }

    public static void recordTeachClassDiscoveryClick(IListItem iListItem) {
        if (iListItem != null) {
            ExtendJsonWrapper obtain = ExtendJsonWrapper.obtain();
            obtain.setCpId(iListItem.getItemId());
            obtain.setCpName("mitv");
            obtain.setDialogOrigin("soundbox");
            obtain.setMediaType(TrackConstant.MEDIA_TYPE_LONG_VIDEO);
            obtain.setTab(KidCourseDataManager.getManager().getCurrentTab().getName());
            obtain.setSubTab(KidCourseDataManager.getManager().getSubTab());
            TrackStat.simpleCountEvent(TrackWidget.TEACH_CLASS_DISCOVER_PAGE, EventType.CLICK, obtain);
        }
    }

    public static void recordTeachClassDiscoveryExpose(IListItem iListItem) {
        if (iListItem != null) {
            ExtendJsonWrapper obtain = ExtendJsonWrapper.obtain();
            obtain.setCpId(iListItem.getItemId());
            obtain.setCpName("mitv");
            obtain.setDialogOrigin("soundbox");
            obtain.setMediaType(TrackConstant.MEDIA_TYPE_LONG_VIDEO);
            obtain.setTab(KidCourseDataManager.getManager().getCurrentTab().getName());
            obtain.setSubTab(KidCourseDataManager.getManager().getSubTab());
            TrackStat.simpleCountEvent(TrackWidget.TEACH_CLASS_DISCOVER_PAGE, EventType.EXPOSE, obtain);
        }
    }

    public static void recordKidVideoDiscoveryExpose(TrackWidget trackWidget, IListItem iListItem, String str) {
        if (iListItem != null) {
            ExtendJsonWrapper obtain = ExtendJsonWrapper.obtain();
            obtain.setCpName("mitv");
            obtain.setCpId(iListItem.getItemId());
            obtain.setMediaType(TrackConstant.MEDIA_TYPE_LONG_VIDEO);
            obtain.setModuleName(str);
            TrackStat.simpleCountEvent(trackWidget, EventType.EXPOSE, obtain);
        }
    }

    public static void recordChildVideoVipPopExpose(VideoItem videoItem) {
        if (videoItem != null) {
            ExtendJsonWrapper obtain = ExtendJsonWrapper.obtain();
            resetPayOriginId();
            obtain.setOriginId(a);
            if (videoItem.originDialogId != null) {
                obtain.setFrom("voice");
            } else {
                obtain.setFrom(TrackConstant.RECOMMEND);
            }
            TrackStat.simpleCountEvent(TrackWidget.CHILD_VIDEO_VIP_POP, EventType.EXPOSE, obtain);
        }
    }

    public static void recordChildVideoVipPopClick(boolean z) {
        ExtendJsonWrapper obtain = ExtendJsonWrapper.obtain();
        obtain.setOriginId(a);
        obtain.setButtonContent(z ? AbstractCircuitBreaker.PROPERTY_NAME : "not_open");
        TrackStat.simpleCountEvent(TrackWidget.CHILD_VIDEO_VIP_POP, EventType.CLICK, obtain);
    }

    public static void recordChildVideoVipPayPageFinish(ProductPrice.PriceBean priceBean) {
        ExtendJsonWrapper obtain = ExtendJsonWrapper.obtain();
        obtain.setOriginId(a);
        try {
            obtain.setPrice(Double.parseDouble(priceBean.getReal_price()));
            obtain.setProduct(priceBean.getProduct_name());
        } catch (Exception e) {
            L.mitv.e(e);
        }
        TrackStat.simpleCountEvent(TrackWidget.CHILD_VIDEO_VIP_PAY_PAGE, EventType.FINISH, obtain);
    }

    public static void resetPayOriginId() {
        a = Util.getUUID();
    }
}
