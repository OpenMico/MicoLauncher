package com.xiaomi.micolauncher;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.xiaomi.ai.android.track.TraceConstants;
import com.xiaomi.micolauncher.databinding.ActivityAudioBookBindingImpl;
import com.xiaomi.micolauncher.databinding.ActivityBaikeX08BindingImpl;
import com.xiaomi.micolauncher.databinding.FragmentSelectionListBindingImpl;
import com.xiaomi.micolauncher.databinding.FragmentTabFlowBindingImpl;
import com.xiaomi.micolauncher.databinding.ItemAccurateShotMultiResultsCpsBindingImpl;
import com.xiaomi.micolauncher.databinding.ItemAudiobookTabBindingImpl;
import com.xiaomi.micolauncher.databinding.ItemBlockTitleBindingImpl;
import com.xiaomi.micolauncher.databinding.ItemCardBindingImpl;
import com.xiaomi.micolauncher.databinding.ItemPlaySourceBindingImpl;
import com.xiaomi.micolauncher.databinding.ItemSelectionListBindingImpl;
import com.xiaomi.micolauncher.databinding.LayoutAccurateShotMultiCpBindingImpl;
import com.xiaomi.micolauncher.databinding.LayoutAccurateShotSingleCpBindingImpl;
import com.xiaomi.micolauncher.databinding.LongVideoControllerBarBindingImpl;
import com.xiaomi.micolauncher.databinding.MultiCpMoreInfoBindingImpl;
import com.xiaomi.micolauncher.databinding.ShortVideoControllerBarBindingImpl;
import com.xiaomi.micolauncher.databinding.VideoControllerBarBindingImpl;
import com.xiaomi.micolauncher.databinding.ViewBaikeX08NormalBindingImpl;
import com.xiaomi.micolauncher.databinding.ViewBaikeX08SingleImageBindingImpl;
import com.xiaomi.micolauncher.databinding.ViewLoadingDialogBindingImpl;
import com.xiaomi.micolauncher.databinding.WidgetCpsBarBindingImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes3.dex */
public class DataBinderMapperImpl extends DataBinderMapper {
    private static final SparseIntArray a = new SparseIntArray(20);

    static {
        a.put(R.layout.activity_audio_book, 1);
        a.put(R.layout.activity_baike_x08, 2);
        a.put(R.layout.fragment_selection_list, 3);
        a.put(R.layout.fragment_tab_flow, 4);
        a.put(R.layout.item_accurate_shot_multi_results_cps, 5);
        a.put(R.layout.item_audiobook_tab, 6);
        a.put(R.layout.item_block_title, 7);
        a.put(R.layout.item_card, 8);
        a.put(R.layout.item_play_source, 9);
        a.put(R.layout.item_selection_list, 10);
        a.put(R.layout.layout_accurate_shot_multi_cp, 11);
        a.put(R.layout.layout_accurate_shot_single_cp, 12);
        a.put(R.layout.long_video_controller_bar, 13);
        a.put(R.layout.multi_cp_more_info, 14);
        a.put(R.layout.short_video_controller_bar, 15);
        a.put(R.layout.video_controller_bar, 16);
        a.put(R.layout.view_baike_x08_normal, 17);
        a.put(R.layout.view_baike_x08_single_image, 18);
        a.put(R.layout.view_loading_dialog, 19);
        a.put(R.layout.widget_cps_bar, 20);
    }

    @Override // androidx.databinding.DataBinderMapper
    public ViewDataBinding getDataBinder(DataBindingComponent dataBindingComponent, View view, int i) {
        int i2 = a.get(i);
        if (i2 <= 0) {
            return null;
        }
        Object tag = view.getTag();
        if (tag != null) {
            switch (i2) {
                case 1:
                    if ("layout/activity_audio_book_0".equals(tag)) {
                        return new ActivityAudioBookBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for activity_audio_book is invalid. Received: " + tag);
                case 2:
                    if ("layout/activity_baike_x08_0".equals(tag)) {
                        return new ActivityBaikeX08BindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for activity_baike_x08 is invalid. Received: " + tag);
                case 3:
                    if ("layout/fragment_selection_list_0".equals(tag)) {
                        return new FragmentSelectionListBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for fragment_selection_list is invalid. Received: " + tag);
                case 4:
                    if ("layout/fragment_tab_flow_0".equals(tag)) {
                        return new FragmentTabFlowBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for fragment_tab_flow is invalid. Received: " + tag);
                case 5:
                    if ("layout/item_accurate_shot_multi_results_cps_0".equals(tag)) {
                        return new ItemAccurateShotMultiResultsCpsBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for item_accurate_shot_multi_results_cps is invalid. Received: " + tag);
                case 6:
                    if ("layout/item_audiobook_tab_0".equals(tag)) {
                        return new ItemAudiobookTabBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for item_audiobook_tab is invalid. Received: " + tag);
                case 7:
                    if ("layout/item_block_title_0".equals(tag)) {
                        return new ItemBlockTitleBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for item_block_title is invalid. Received: " + tag);
                case 8:
                    if ("layout/item_card_0".equals(tag)) {
                        return new ItemCardBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for item_card is invalid. Received: " + tag);
                case 9:
                    if ("layout/item_play_source_0".equals(tag)) {
                        return new ItemPlaySourceBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for item_play_source is invalid. Received: " + tag);
                case 10:
                    if ("layout/item_selection_list_0".equals(tag)) {
                        return new ItemSelectionListBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for item_selection_list is invalid. Received: " + tag);
                case 11:
                    if ("layout/layout_accurate_shot_multi_cp_0".equals(tag)) {
                        return new LayoutAccurateShotMultiCpBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for layout_accurate_shot_multi_cp is invalid. Received: " + tag);
                case 12:
                    if ("layout/layout_accurate_shot_single_cp_0".equals(tag)) {
                        return new LayoutAccurateShotSingleCpBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for layout_accurate_shot_single_cp is invalid. Received: " + tag);
                case 13:
                    if ("layout/long_video_controller_bar_0".equals(tag)) {
                        return new LongVideoControllerBarBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for long_video_controller_bar is invalid. Received: " + tag);
                case 14:
                    if ("layout/multi_cp_more_info_0".equals(tag)) {
                        return new MultiCpMoreInfoBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for multi_cp_more_info is invalid. Received: " + tag);
                case 15:
                    if ("layout/short_video_controller_bar_0".equals(tag)) {
                        return new ShortVideoControllerBarBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for short_video_controller_bar is invalid. Received: " + tag);
                case 16:
                    if ("layout/video_controller_bar_0".equals(tag)) {
                        return new VideoControllerBarBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for video_controller_bar is invalid. Received: " + tag);
                case 17:
                    if ("layout/view_baike_x08_normal_0".equals(tag)) {
                        return new ViewBaikeX08NormalBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for view_baike_x08_normal is invalid. Received: " + tag);
                case 18:
                    if ("layout/view_baike_x08_single_image_0".equals(tag)) {
                        return new ViewBaikeX08SingleImageBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for view_baike_x08_single_image is invalid. Received: " + tag);
                case 19:
                    if ("layout/view_loading_dialog_0".equals(tag)) {
                        return new ViewLoadingDialogBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for view_loading_dialog is invalid. Received: " + tag);
                case 20:
                    if ("layout/widget_cps_bar_0".equals(tag)) {
                        return new WidgetCpsBarBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for widget_cps_bar is invalid. Received: " + tag);
                default:
                    return null;
            }
        } else {
            throw new RuntimeException("view must have a tag");
        }
    }

    @Override // androidx.databinding.DataBinderMapper
    public ViewDataBinding getDataBinder(DataBindingComponent dataBindingComponent, View[] viewArr, int i) {
        if (viewArr == null || viewArr.length == 0 || a.get(i) <= 0 || viewArr[0].getTag() != null) {
            return null;
        }
        throw new RuntimeException("view must have a tag");
    }

    @Override // androidx.databinding.DataBinderMapper
    public int getLayoutId(String str) {
        Integer num;
        if (str == null || (num = b.a.get(str)) == null) {
            return 0;
        }
        return num.intValue();
    }

    @Override // androidx.databinding.DataBinderMapper
    public String convertBrIdToString(int i) {
        return a.a.get(i);
    }

    @Override // androidx.databinding.DataBinderMapper
    public List<DataBinderMapper> collectDependencies() {
        ArrayList arrayList = new ArrayList(2);
        arrayList.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
        arrayList.add(new com.xiaomi.smarthome.DataBinderMapperImpl());
        return arrayList;
    }

    /* loaded from: classes3.dex */
    private static class a {
        static final SparseArray<String> a = new SparseArray<>(12);

        static {
            a.put(0, "_all");
            a.put(1, "mode");
            a.put(2, "item");
            a.put(3, "isLocationDeviceNameExchange");
            a.put(4, "isX6A");
            a.put(5, "editMode");
            a.put(6, "hideButtons");
            a.put(7, "category");
            a.put(8, "status");
            a.put(9, "data");
            a.put(10, TraceConstants.Result.CP);
        }
    }

    /* loaded from: classes3.dex */
    private static class b {
        static final HashMap<String, Integer> a = new HashMap<>(20);

        static {
            a.put("layout/activity_audio_book_0", Integer.valueOf((int) R.layout.activity_audio_book));
            a.put("layout/activity_baike_x08_0", Integer.valueOf((int) R.layout.activity_baike_x08));
            a.put("layout/fragment_selection_list_0", Integer.valueOf((int) R.layout.fragment_selection_list));
            a.put("layout/fragment_tab_flow_0", Integer.valueOf((int) R.layout.fragment_tab_flow));
            a.put("layout/item_accurate_shot_multi_results_cps_0", Integer.valueOf((int) R.layout.item_accurate_shot_multi_results_cps));
            a.put("layout/item_audiobook_tab_0", Integer.valueOf((int) R.layout.item_audiobook_tab));
            a.put("layout/item_block_title_0", Integer.valueOf((int) R.layout.item_block_title));
            a.put("layout/item_card_0", Integer.valueOf((int) R.layout.item_card));
            a.put("layout/item_play_source_0", Integer.valueOf((int) R.layout.item_play_source));
            a.put("layout/item_selection_list_0", Integer.valueOf((int) R.layout.item_selection_list));
            a.put("layout/layout_accurate_shot_multi_cp_0", Integer.valueOf((int) R.layout.layout_accurate_shot_multi_cp));
            a.put("layout/layout_accurate_shot_single_cp_0", Integer.valueOf((int) R.layout.layout_accurate_shot_single_cp));
            a.put("layout/long_video_controller_bar_0", Integer.valueOf((int) R.layout.long_video_controller_bar));
            a.put("layout/multi_cp_more_info_0", Integer.valueOf((int) R.layout.multi_cp_more_info));
            a.put("layout/short_video_controller_bar_0", Integer.valueOf((int) R.layout.short_video_controller_bar));
            a.put("layout/video_controller_bar_0", Integer.valueOf((int) R.layout.video_controller_bar));
            a.put("layout/view_baike_x08_normal_0", Integer.valueOf((int) R.layout.view_baike_x08_normal));
            a.put("layout/view_baike_x08_single_image_0", Integer.valueOf((int) R.layout.view_baike_x08_single_image));
            a.put("layout/view_loading_dialog_0", Integer.valueOf((int) R.layout.view_loading_dialog));
            a.put("layout/widget_cps_bar_0", Integer.valueOf((int) R.layout.widget_cps_bar));
        }
    }
}
