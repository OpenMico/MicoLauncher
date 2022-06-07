package com.xiaomi.micolauncher.skills.wakeup;

import com.xiaomi.micolauncher.api.model.WakeGuide;
import com.xiaomi.micolauncher.common.track.EventType;
import com.xiaomi.micolauncher.common.track.ExtendJsonWrapper;
import com.xiaomi.micolauncher.common.track.TrackStat;
import com.xiaomi.micolauncher.common.track.TrackWidget;

/* loaded from: classes3.dex */
public class WakeStatUtil {

    /* loaded from: classes3.dex */
    public enum ExitType {
        AUTO_DISMISS,
        QUERY
    }

    public static void recordWakeGuideExpose(WakeGuide.AnswerBean answerBean) {
        if (answerBean != null) {
            ExtendJsonWrapper obtain = ExtendJsonWrapper.obtain();
            obtain.setTraceId(WakeDataHelper.getHelper().getTraceId());
            if (answerBean.getDebug_info() != null) {
                obtain.setDebugInfo(answerBean.getDebug_info().toString());
            }
            if (answerBean.getExp_id() != null) {
                obtain.setEid(answerBean.getExp_id());
            } else {
                obtain.setEid("");
            }
            obtain.setAppName(WakeDataHelper.getHelper().getCurrentAppName());
            if (WakeDataHelper.getHelper().getQuerySource() != null) {
                obtain.setQuerySource(WakeDataHelper.getHelper().getQuerySource().name().toLowerCase());
            }
            obtain.setDialogOrigin("voice");
            TrackStat.simpleCountEvent(TrackWidget.FUNC_GUIDE_WAKEUP, EventType.EXPOSE, obtain);
        }
    }

    public static void recordWakeGuideExit(WakeGuide.AnswerBean answerBean, long j, ExitType exitType) {
        if (answerBean != null) {
            ExtendJsonWrapper obtain = ExtendJsonWrapper.obtain();
            obtain.setTraceId(WakeDataHelper.getHelper().getTraceId());
            if (answerBean.getDebug_info() != null) {
                obtain.setDebugInfo(answerBean.getDebug_info().toString());
            }
            if (answerBean.getExp_id() != null) {
                obtain.setEid(answerBean.getExp_id());
            } else {
                obtain.setEid("");
            }
            obtain.setAppName(WakeDataHelper.getHelper().getCurrentAppName());
            if (WakeDataHelper.getHelper().getQuerySource() != null) {
                obtain.setQuerySource(WakeDataHelper.getHelper().getQuerySource().name().toLowerCase());
            }
            obtain.setDuration(j);
            obtain.setDialogOrigin("voice");
            obtain.setExitType(exitType.name().toLowerCase());
            TrackStat.simpleCountEvent(TrackWidget.FUNC_GUIDE_WAKEUP, EventType.EXIT, obtain);
        }
    }

    public static void recordWakeGuideResourceExpose(WakeGuide.AnswerBean answerBean, WakeGuide.AnswerBean.SuggestionsBean suggestionsBean, int i) {
        if (answerBean != null && suggestionsBean != null) {
            ExtendJsonWrapper obtain = ExtendJsonWrapper.obtain();
            obtain.setTraceId(WakeDataHelper.getHelper().getTraceId());
            obtain.setQueryText(suggestionsBean.getQuery());
            obtain.setSendQuery(suggestionsBean.getSend_query());
            obtain.setQueryId(suggestionsBean.getId());
            obtain.setCutIn(suggestionsBean.isIs_cut_in());
            obtain.setPosition(i);
            obtain.setAppName(WakeDataHelper.getHelper().getCurrentAppName());
            if (WakeDataHelper.getHelper().getQuerySource() != null) {
                obtain.setQuerySource(WakeDataHelper.getHelper().getQuerySource().name().toLowerCase());
            }
            obtain.setRecallType(answerBean.getCard_type());
            if (suggestionsBean.getDomain() != null) {
                obtain.setDomain(suggestionsBean.getDomain());
            } else {
                obtain.setDomain("");
            }
            if (answerBean.getDebug_info() != null) {
                obtain.setDebugInfo(answerBean.getDebug_info().toString());
            }
            obtain.setDialogOrigin("voice");
            if (answerBean.getExp_id() != null) {
                obtain.setEid(answerBean.getExp_id());
            } else {
                obtain.setEid("");
            }
            TrackStat.simpleCountEvent(TrackWidget.FUNC_GUIDE_WAKEUP_RESOURCE, EventType.EXPOSE, obtain);
        }
    }

    public static void recordWakeGuideResourceClick(WakeGuide.AnswerBean answerBean, WakeGuide.AnswerBean.SuggestionsBean suggestionsBean, int i) {
        if (answerBean != null && suggestionsBean != null) {
            ExtendJsonWrapper obtain = ExtendJsonWrapper.obtain();
            obtain.setTraceId(WakeDataHelper.getHelper().getTraceId());
            obtain.setQueryText(suggestionsBean.getQuery());
            obtain.setSendQuery(suggestionsBean.getSend_query());
            obtain.setQueryId(suggestionsBean.getId());
            obtain.setCutIn(suggestionsBean.isIs_cut_in());
            obtain.setPosition(i);
            obtain.setAppName(WakeDataHelper.getHelper().getCurrentAppName());
            if (WakeDataHelper.getHelper().getQuerySource() != null) {
                obtain.setQuerySource(WakeDataHelper.getHelper().getQuerySource().name().toLowerCase());
            }
            obtain.setDialogOrigin("voice");
            obtain.setRecallType(answerBean.getCard_type());
            if (suggestionsBean.getDomain() != null) {
                obtain.setDomain(suggestionsBean.getDomain());
            } else {
                obtain.setDomain("");
            }
            if (suggestionsBean.getDebug_info() != null) {
                obtain.setDebugInfo(suggestionsBean.getDebug_info().toString());
            }
            if (answerBean.getExp_id() != null) {
                obtain.setEid(answerBean.getExp_id());
            } else {
                obtain.setEid("");
            }
            TrackStat.simpleCountEvent(TrackWidget.FUNC_GUIDE_WAKEUP_RESOURCE, EventType.CLICK, obtain);
        }
    }
}
