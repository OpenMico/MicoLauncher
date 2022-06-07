package com.xiaomi.smarthome;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.xiaomi.smarthome.databinding.ActivityCategoryReorderBindingImpl;
import com.xiaomi.smarthome.databinding.ActivityDeviceListBindingImpl;
import com.xiaomi.smarthome.databinding.EnvAirConditionControllerBindingImpl;
import com.xiaomi.smarthome.databinding.EnvAirPurifierController1BindingImpl;
import com.xiaomi.smarthome.databinding.EnvFanController1BindingImpl;
import com.xiaomi.smarthome.databinding.EnvFanController2BindingImpl;
import com.xiaomi.smarthome.databinding.EnvHeaterController1BindingImpl;
import com.xiaomi.smarthome.databinding.EnvHeaterController2BindingImpl;
import com.xiaomi.smarthome.databinding.EnvHumidityController1BindingImpl;
import com.xiaomi.smarthome.databinding.EnvHumidityController2BindingImpl;
import com.xiaomi.smarthome.databinding.FragmentSmartHomeModeCategoryBindingImpl;
import com.xiaomi.smarthome.databinding.FragmentSmartHomeModeRoomBindingImpl;
import com.xiaomi.smarthome.databinding.HouseWorkAirerBindingImpl;
import com.xiaomi.smarthome.databinding.HouseWorkSweepRobotBindingImpl;
import com.xiaomi.smarthome.databinding.ItemTypeGroupBindingImpl;
import com.xiaomi.smarthome.databinding.ItemWidgetEnvironmentControllerInDeviceListBindingImpl;
import com.xiaomi.smarthome.databinding.ItemWidgetLightControllerInDeviceListBindingImpl;
import com.xiaomi.smarthome.databinding.ItemWidgetSocketControllerInDeviceListBindingImpl;
import com.xiaomi.smarthome.databinding.LightController1BindingImpl;
import com.xiaomi.smarthome.databinding.LightController2BindingImpl;
import com.xiaomi.smarthome.databinding.OsOutletMultiControllerBindingImpl;
import com.xiaomi.smarthome.databinding.OsOutletMultiInDeviceListBindingImpl;
import com.xiaomi.smarthome.databinding.OsOutletSingleControllerBindingImpl;
import com.xiaomi.smarthome.databinding.OsOutletSingleInDeviceListBindingImpl;
import com.xiaomi.smarthome.databinding.OsSwitchMultiControllerBindingImpl;
import com.xiaomi.smarthome.databinding.OsSwitchMultiInDeviceListBindingImpl;
import com.xiaomi.smarthome.databinding.OsSwitchSingleControllerBindingImpl;
import com.xiaomi.smarthome.databinding.OsSwitchSingleInDeviceListBindingImpl;
import com.xiaomi.smarthome.databinding.SecurityCameraBindingImpl;
import com.xiaomi.smarthome.databinding.SecurityCameraInDeviceListBindingImpl;
import com.xiaomi.smarthome.databinding.ShortCardWidgetLightControllerBindingImpl;
import com.xiaomi.smarthome.databinding.ViewCurtainBindingImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes4.dex */
public class DataBinderMapperImpl extends DataBinderMapper {
    private static final SparseIntArray a = new SparseIntArray(32);

    static {
        a.put(R.layout.activity_category_reorder, 1);
        a.put(R.layout.activity_device_list, 2);
        a.put(R.layout.env_air_condition_controller, 3);
        a.put(R.layout.env_air_purifier_controller1, 4);
        a.put(R.layout.env_fan_controller1, 5);
        a.put(R.layout.env_fan_controller2, 6);
        a.put(R.layout.env_heater_controller1, 7);
        a.put(R.layout.env_heater_controller2, 8);
        a.put(R.layout.env_humidity_controller1, 9);
        a.put(R.layout.env_humidity_controller2, 10);
        a.put(R.layout.fragment_smart_home_mode_category, 11);
        a.put(R.layout.fragment_smart_home_mode_room, 12);
        a.put(R.layout.house_work_airer, 13);
        a.put(R.layout.house_work_sweep_robot, 14);
        a.put(R.layout.item_type_group, 15);
        a.put(R.layout.item_widget_environment_controller_in_device_list, 16);
        a.put(R.layout.item_widget_light_controller_in_device_list, 17);
        a.put(R.layout.item_widget_socket_controller_in_device_list, 18);
        a.put(R.layout.light_controller1, 19);
        a.put(R.layout.light_controller2, 20);
        a.put(R.layout.os_outlet_multi_controller, 21);
        a.put(R.layout.os_outlet_multi_in_device_list, 22);
        a.put(R.layout.os_outlet_single_controller, 23);
        a.put(R.layout.os_outlet_single_in_device_list, 24);
        a.put(R.layout.os_switch_multi_controller, 25);
        a.put(R.layout.os_switch_multi_in_device_list, 26);
        a.put(R.layout.os_switch_single_controller, 27);
        a.put(R.layout.os_switch_single_in_device_list, 28);
        a.put(R.layout.security_camera, 29);
        a.put(R.layout.security_camera_in_device_list, 30);
        a.put(R.layout.short_card_widget_light_controller, 31);
        a.put(R.layout.view_curtain, 32);
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
                    if ("layout/activity_category_reorder_0".equals(tag)) {
                        return new ActivityCategoryReorderBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for activity_category_reorder is invalid. Received: " + tag);
                case 2:
                    if ("layout/activity_device_list_0".equals(tag)) {
                        return new ActivityDeviceListBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for activity_device_list is invalid. Received: " + tag);
                case 3:
                    if ("layout/env_air_condition_controller_0".equals(tag)) {
                        return new EnvAirConditionControllerBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for env_air_condition_controller is invalid. Received: " + tag);
                case 4:
                    if ("layout/env_air_purifier_controller1_0".equals(tag)) {
                        return new EnvAirPurifierController1BindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for env_air_purifier_controller1 is invalid. Received: " + tag);
                case 5:
                    if ("layout/env_fan_controller1_0".equals(tag)) {
                        return new EnvFanController1BindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for env_fan_controller1 is invalid. Received: " + tag);
                case 6:
                    if ("layout/env_fan_controller2_0".equals(tag)) {
                        return new EnvFanController2BindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for env_fan_controller2 is invalid. Received: " + tag);
                case 7:
                    if ("layout/env_heater_controller1_0".equals(tag)) {
                        return new EnvHeaterController1BindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for env_heater_controller1 is invalid. Received: " + tag);
                case 8:
                    if ("layout/env_heater_controller2_0".equals(tag)) {
                        return new EnvHeaterController2BindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for env_heater_controller2 is invalid. Received: " + tag);
                case 9:
                    if ("layout/env_humidity_controller1_0".equals(tag)) {
                        return new EnvHumidityController1BindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for env_humidity_controller1 is invalid. Received: " + tag);
                case 10:
                    if ("layout/env_humidity_controller2_0".equals(tag)) {
                        return new EnvHumidityController2BindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for env_humidity_controller2 is invalid. Received: " + tag);
                case 11:
                    if ("layout/fragment_smart_home_mode_category_0".equals(tag)) {
                        return new FragmentSmartHomeModeCategoryBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for fragment_smart_home_mode_category is invalid. Received: " + tag);
                case 12:
                    if ("layout/fragment_smart_home_mode_room_0".equals(tag)) {
                        return new FragmentSmartHomeModeRoomBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for fragment_smart_home_mode_room is invalid. Received: " + tag);
                case 13:
                    if ("layout/house_work_airer_0".equals(tag)) {
                        return new HouseWorkAirerBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for house_work_airer is invalid. Received: " + tag);
                case 14:
                    if ("layout/house_work_sweep_robot_0".equals(tag)) {
                        return new HouseWorkSweepRobotBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for house_work_sweep_robot is invalid. Received: " + tag);
                case 15:
                    if ("layout/item_type_group_0".equals(tag)) {
                        return new ItemTypeGroupBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for item_type_group is invalid. Received: " + tag);
                case 16:
                    if ("layout/item_widget_environment_controller_in_device_list_0".equals(tag)) {
                        return new ItemWidgetEnvironmentControllerInDeviceListBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for item_widget_environment_controller_in_device_list is invalid. Received: " + tag);
                case 17:
                    if ("layout/item_widget_light_controller_in_device_list_0".equals(tag)) {
                        return new ItemWidgetLightControllerInDeviceListBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for item_widget_light_controller_in_device_list is invalid. Received: " + tag);
                case 18:
                    if ("layout/item_widget_socket_controller_in_device_list_0".equals(tag)) {
                        return new ItemWidgetSocketControllerInDeviceListBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for item_widget_socket_controller_in_device_list is invalid. Received: " + tag);
                case 19:
                    if ("layout/light_controller1_0".equals(tag)) {
                        return new LightController1BindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for light_controller1 is invalid. Received: " + tag);
                case 20:
                    if ("layout/light_controller2_0".equals(tag)) {
                        return new LightController2BindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for light_controller2 is invalid. Received: " + tag);
                case 21:
                    if ("layout/os_outlet_multi_controller_0".equals(tag)) {
                        return new OsOutletMultiControllerBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for os_outlet_multi_controller is invalid. Received: " + tag);
                case 22:
                    if ("layout/os_outlet_multi_in_device_list_0".equals(tag)) {
                        return new OsOutletMultiInDeviceListBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for os_outlet_multi_in_device_list is invalid. Received: " + tag);
                case 23:
                    if ("layout/os_outlet_single_controller_0".equals(tag)) {
                        return new OsOutletSingleControllerBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for os_outlet_single_controller is invalid. Received: " + tag);
                case 24:
                    if ("layout/os_outlet_single_in_device_list_0".equals(tag)) {
                        return new OsOutletSingleInDeviceListBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for os_outlet_single_in_device_list is invalid. Received: " + tag);
                case 25:
                    if ("layout/os_switch_multi_controller_0".equals(tag)) {
                        return new OsSwitchMultiControllerBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for os_switch_multi_controller is invalid. Received: " + tag);
                case 26:
                    if ("layout/os_switch_multi_in_device_list_0".equals(tag)) {
                        return new OsSwitchMultiInDeviceListBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for os_switch_multi_in_device_list is invalid. Received: " + tag);
                case 27:
                    if ("layout/os_switch_single_controller_0".equals(tag)) {
                        return new OsSwitchSingleControllerBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for os_switch_single_controller is invalid. Received: " + tag);
                case 28:
                    if ("layout/os_switch_single_in_device_list_0".equals(tag)) {
                        return new OsSwitchSingleInDeviceListBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for os_switch_single_in_device_list is invalid. Received: " + tag);
                case 29:
                    if ("layout/security_camera_0".equals(tag)) {
                        return new SecurityCameraBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for security_camera is invalid. Received: " + tag);
                case 30:
                    if ("layout/security_camera_in_device_list_0".equals(tag)) {
                        return new SecurityCameraInDeviceListBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for security_camera_in_device_list is invalid. Received: " + tag);
                case 31:
                    if ("layout/short_card_widget_light_controller_0".equals(tag)) {
                        return new ShortCardWidgetLightControllerBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for short_card_widget_light_controller is invalid. Received: " + tag);
                case 32:
                    if ("layout/view_curtain_0".equals(tag)) {
                        return new ViewCurtainBindingImpl(dataBindingComponent, view);
                    }
                    throw new IllegalArgumentException("The tag for view_curtain is invalid. Received: " + tag);
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
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
        return arrayList;
    }

    /* loaded from: classes4.dex */
    private static class a {
        static final SparseArray<String> a = new SparseArray<>(10);

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
        }
    }

    /* loaded from: classes4.dex */
    private static class b {
        static final HashMap<String, Integer> a = new HashMap<>(32);

        static {
            a.put("layout/activity_category_reorder_0", Integer.valueOf(R.layout.activity_category_reorder));
            a.put("layout/activity_device_list_0", Integer.valueOf(R.layout.activity_device_list));
            a.put("layout/env_air_condition_controller_0", Integer.valueOf(R.layout.env_air_condition_controller));
            a.put("layout/env_air_purifier_controller1_0", Integer.valueOf(R.layout.env_air_purifier_controller1));
            a.put("layout/env_fan_controller1_0", Integer.valueOf(R.layout.env_fan_controller1));
            a.put("layout/env_fan_controller2_0", Integer.valueOf(R.layout.env_fan_controller2));
            a.put("layout/env_heater_controller1_0", Integer.valueOf(R.layout.env_heater_controller1));
            a.put("layout/env_heater_controller2_0", Integer.valueOf(R.layout.env_heater_controller2));
            a.put("layout/env_humidity_controller1_0", Integer.valueOf(R.layout.env_humidity_controller1));
            a.put("layout/env_humidity_controller2_0", Integer.valueOf(R.layout.env_humidity_controller2));
            a.put("layout/fragment_smart_home_mode_category_0", Integer.valueOf(R.layout.fragment_smart_home_mode_category));
            a.put("layout/fragment_smart_home_mode_room_0", Integer.valueOf(R.layout.fragment_smart_home_mode_room));
            a.put("layout/house_work_airer_0", Integer.valueOf(R.layout.house_work_airer));
            a.put("layout/house_work_sweep_robot_0", Integer.valueOf(R.layout.house_work_sweep_robot));
            a.put("layout/item_type_group_0", Integer.valueOf(R.layout.item_type_group));
            a.put("layout/item_widget_environment_controller_in_device_list_0", Integer.valueOf(R.layout.item_widget_environment_controller_in_device_list));
            a.put("layout/item_widget_light_controller_in_device_list_0", Integer.valueOf(R.layout.item_widget_light_controller_in_device_list));
            a.put("layout/item_widget_socket_controller_in_device_list_0", Integer.valueOf(R.layout.item_widget_socket_controller_in_device_list));
            a.put("layout/light_controller1_0", Integer.valueOf(R.layout.light_controller1));
            a.put("layout/light_controller2_0", Integer.valueOf(R.layout.light_controller2));
            a.put("layout/os_outlet_multi_controller_0", Integer.valueOf(R.layout.os_outlet_multi_controller));
            a.put("layout/os_outlet_multi_in_device_list_0", Integer.valueOf(R.layout.os_outlet_multi_in_device_list));
            a.put("layout/os_outlet_single_controller_0", Integer.valueOf(R.layout.os_outlet_single_controller));
            a.put("layout/os_outlet_single_in_device_list_0", Integer.valueOf(R.layout.os_outlet_single_in_device_list));
            a.put("layout/os_switch_multi_controller_0", Integer.valueOf(R.layout.os_switch_multi_controller));
            a.put("layout/os_switch_multi_in_device_list_0", Integer.valueOf(R.layout.os_switch_multi_in_device_list));
            a.put("layout/os_switch_single_controller_0", Integer.valueOf(R.layout.os_switch_single_controller));
            a.put("layout/os_switch_single_in_device_list_0", Integer.valueOf(R.layout.os_switch_single_in_device_list));
            a.put("layout/security_camera_0", Integer.valueOf(R.layout.security_camera));
            a.put("layout/security_camera_in_device_list_0", Integer.valueOf(R.layout.security_camera_in_device_list));
            a.put("layout/short_card_widget_light_controller_0", Integer.valueOf(R.layout.short_card_widget_light_controller));
            a.put("layout/view_curtain_0", Integer.valueOf(R.layout.view_curtain));
        }
    }
}
