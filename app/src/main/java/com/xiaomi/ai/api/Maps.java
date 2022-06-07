package com.xiaomi.ai.api;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.Application;
import com.xiaomi.ai.api.Template;
import com.xiaomi.ai.api.common.ContextPayload;
import com.xiaomi.ai.api.common.EventPayload;
import com.xiaomi.ai.api.common.InstructionPayload;
import com.xiaomi.ai.api.common.NamespaceName;
import com.xiaomi.ai.api.common.Required;
import com.xiaomi.common.Optional;
import java.util.List;

/* loaded from: classes3.dex */
public class Maps {

    @NamespaceName(name = "BackToNavigation", namespace = AIApiConstants.Map.NAME)
    @Deprecated
    /* loaded from: classes3.dex */
    public static class BackToNavigation implements InstructionPayload {
    }

    @NamespaceName(name = "ZoomIn", namespace = AIApiConstants.Map.NAME)
    @Deprecated
    /* loaded from: classes3.dex */
    public static class ZoomIn implements InstructionPayload {
    }

    @NamespaceName(name = "ZoomOut", namespace = AIApiConstants.Map.NAME)
    @Deprecated
    /* loaded from: classes3.dex */
    public static class ZoomOut implements InstructionPayload {
    }

    /* loaded from: classes3.dex */
    public enum BroadcastMode {
        UNKNOWN(-1),
        DETAIL(0),
        BRIEF(1);
        
        private int id;

        BroadcastMode(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum MapDirective {
        UNKNOWN(-1),
        ZOOM_IN(0),
        ZOOM_OUT(1),
        BACK_TO_NAVIGATION(2),
        CANCEL_NAVIGATION(3),
        QUERY_REST_OF_TIME(4),
        QUERY_WHOLE_ROUTE(5),
        DELETE_MID_POI(6),
        QUERY_REST_OF_ROUTE(7),
        SPEED_LIMIT(8),
        NEARBY_TRAFFIC_CONDITION(9),
        ZOOM_IN_MAX(10),
        ZOOM_OUT_MIN(11),
        SWITCH_MAP_MODE(12),
        COLLECT_CURRENT_POI(13),
        EXIT_WHOLE_ROUTE(14),
        NAVIGATION(15),
        SEARCH(16),
        WHERE(17),
        ROUTE_PLAN(18),
        MUTE(19),
        UNMUTE(20),
        OPEN_SETTING_PAGE(21),
        CLOSE_SETTING_PAGE(22);
        
        private int id;

        MapDirective(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum MapDisplayPage {
        UNKNOWN(-1),
        ROUTE_PLAN_PAGE(0),
        NAVIGATION_PAGE(1),
        SEARCH_DETAILS_PAGE(2);
        
        private int id;

        MapDisplayPage(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum MapFeature {
        UNKNOWN(-1),
        CRUISE_REPORT(0),
        REAL_TIME_TRAFFIC(1),
        RESTRICTION_REMIND(2),
        DESTINATION_PREDICTED(3);
        
        private int id;

        MapFeature(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum MapMode {
        UNKNOWN(-1),
        FOLLOW_MODE(0),
        NORTH_MODE(1),
        TWO_D_MODE(2),
        THERE_D_MODE(3),
        DAY_MODE(4),
        NIGHT_MODE(5);
        
        private int id;

        MapMode(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum MapPoiType {
        UNKNOWN(-1),
        GAS_STATION(0),
        ATM(1),
        AUTO_REPAIR_STORE(2),
        TOILET(3),
        CHARGING_STATION(4);
        
        private int id;

        MapPoiType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum PoiType {
        UNKNOWN(-1),
        NORMAL(0),
        HOME(1),
        COMPANY(2),
        MY_LOCATION(3);
        
        private int id;

        PoiType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum RoutePreference {
        UNKNOWN(-1),
        DEFAULT(0),
        AVOID_CONGESTION(1),
        NO_HIGHWAY(2),
        HIGHWAY_FIRST(3),
        AVOID_CHARGES(4),
        TIME_FIRST(5),
        DISTANCE_FIRST(6),
        FOLLOW_MY_SETTINGS(7),
        MAIN_ROAD(8),
        SIDE_ROAD(9),
        ON_THE_VIADUCT(10),
        UNDER_THE_VIADUCT(11);
        
        private int id;

        RoutePreference(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum SearchAlongType {
        UNKNOWN(-1),
        POI(0),
        ROAD_CONDITION(1),
        REMAIN_TIME(2),
        REMAIN_DISTANCE(3),
        TRAFFIC_LIGHT(4),
        SPEED(5);
        
        private int id;

        SearchAlongType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum TripMode {
        UNKNOWN(-1),
        DRIVING(0),
        RIDING(1),
        WALKING(2),
        TRANSIT(3);
        
        private int id;

        TripMode(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    @NamespaceName(name = "AddMidPoi", namespace = AIApiConstants.Map.NAME)
    /* loaded from: classes3.dex */
    public static class AddMidPoi implements InstructionPayload {
        @Required
        private Poi poi;

        public AddMidPoi() {
        }

        public AddMidPoi(Poi poi) {
            this.poi = poi;
        }

        @Required
        public AddMidPoi setPoi(Poi poi) {
            this.poi = poi;
            return this;
        }

        @Required
        public Poi getPoi() {
            return this.poi;
        }
    }

    @NamespaceName(name = "AmbiguousPlaces", namespace = AIApiConstants.Map.NAME)
    /* loaded from: classes3.dex */
    public static class AmbiguousPlaces implements EventPayload {
        @Required
        private List<String> places;
        @Required
        private TripMode tripMode;

        public AmbiguousPlaces() {
        }

        public AmbiguousPlaces(TripMode tripMode, List<String> list) {
            this.tripMode = tripMode;
            this.places = list;
        }

        @Required
        public AmbiguousPlaces setTripMode(TripMode tripMode) {
            this.tripMode = tripMode;
            return this;
        }

        @Required
        public TripMode getTripMode() {
            return this.tripMode;
        }

        @Required
        public AmbiguousPlaces setPlaces(List<String> list) {
            this.places = list;
            return this;
        }

        @Required
        public List<String> getPlaces() {
            return this.places;
        }
    }

    /* loaded from: classes3.dex */
    public static class Busline {
        @Required
        private String description;
        @Required
        private String dest_station_id;
        @Required
        private String id;
        @Required
        private Template.Launcher launcher;
        @Required
        private String name;

        public Busline() {
        }

        public Busline(String str, String str2, String str3, String str4, Template.Launcher launcher) {
            this.id = str;
            this.dest_station_id = str2;
            this.name = str3;
            this.description = str4;
            this.launcher = launcher;
        }

        @Required
        public Busline setId(String str) {
            this.id = str;
            return this;
        }

        @Required
        public String getId() {
            return this.id;
        }

        @Required
        public Busline setDestStationId(String str) {
            this.dest_station_id = str;
            return this;
        }

        @Required
        public String getDestStationId() {
            return this.dest_station_id;
        }

        @Required
        public Busline setName(String str) {
            this.name = str;
            return this;
        }

        @Required
        public String getName() {
            return this.name;
        }

        @Required
        public Busline setDescription(String str) {
            this.description = str;
            return this;
        }

        @Required
        public String getDescription() {
            return this.description;
        }

        @Required
        public Busline setLauncher(Template.Launcher launcher) {
            this.launcher = launcher;
            return this;
        }

        @Required
        public Template.Launcher getLauncher() {
            return this.launcher;
        }
    }

    @NamespaceName(name = "ExecuteDirective", namespace = AIApiConstants.Map.NAME)
    /* loaded from: classes3.dex */
    public static class ExecuteDirective implements InstructionPayload {
        @Required
        private MapDirective directive;
        private Optional<String> pkg_name = Optional.empty();

        public ExecuteDirective() {
        }

        public ExecuteDirective(MapDirective mapDirective) {
            this.directive = mapDirective;
        }

        @Required
        public ExecuteDirective setDirective(MapDirective mapDirective) {
            this.directive = mapDirective;
            return this;
        }

        @Required
        public MapDirective getDirective() {
            return this.directive;
        }

        public ExecuteDirective setPkgName(String str) {
            this.pkg_name = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getPkgName() {
            return this.pkg_name;
        }
    }

    @NamespaceName(name = "ModifyUsualAddress", namespace = AIApiConstants.Map.NAME)
    /* loaded from: classes3.dex */
    public static class ModifyUsualAddress implements InstructionPayload {
        @Required
        private PoiType poi_type;

        public ModifyUsualAddress() {
        }

        public ModifyUsualAddress(PoiType poiType) {
            this.poi_type = poiType;
        }

        @Required
        public ModifyUsualAddress setPoiType(PoiType poiType) {
            this.poi_type = poiType;
            return this;
        }

        @Required
        public PoiType getPoiType() {
            return this.poi_type;
        }
    }

    @NamespaceName(name = "NavigateState", namespace = AIApiConstants.Map.NAME)
    /* loaded from: classes3.dex */
    public static class NavigateState implements ContextPayload {
        @Required
        private String current_road;
        @Required
        private Poi end_poi;
        private Optional<List<Poi>> mid_pois = Optional.empty();
        @Required
        private Poi start_poi;
        @Required
        private TripMode trip_mode;

        public NavigateState() {
        }

        public NavigateState(TripMode tripMode, Poi poi, Poi poi2, String str) {
            this.trip_mode = tripMode;
            this.start_poi = poi;
            this.end_poi = poi2;
            this.current_road = str;
        }

        @Required
        public NavigateState setTripMode(TripMode tripMode) {
            this.trip_mode = tripMode;
            return this;
        }

        @Required
        public TripMode getTripMode() {
            return this.trip_mode;
        }

        @Required
        public NavigateState setStartPoi(Poi poi) {
            this.start_poi = poi;
            return this;
        }

        @Required
        public Poi getStartPoi() {
            return this.start_poi;
        }

        @Required
        public NavigateState setEndPoi(Poi poi) {
            this.end_poi = poi;
            return this;
        }

        @Required
        public Poi getEndPoi() {
            return this.end_poi;
        }

        @Required
        public NavigateState setCurrentRoad(String str) {
            this.current_road = str;
            return this;
        }

        @Required
        public String getCurrentRoad() {
            return this.current_road;
        }

        public NavigateState setMidPois(List<Poi> list) {
            this.mid_pois = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<Poi>> getMidPois() {
            return this.mid_pois;
        }
    }

    /* loaded from: classes3.dex */
    public static class Place {
        @Required
        private String distance;
        @Required
        private Poi poi;
        private Optional<Template.AndroidIntent> intent = Optional.empty();
        private Optional<Poi> start_poi = Optional.empty();

        public Place() {
        }

        public Place(Poi poi, String str) {
            this.poi = poi;
            this.distance = str;
        }

        @Required
        public Place setPoi(Poi poi) {
            this.poi = poi;
            return this;
        }

        @Required
        public Poi getPoi() {
            return this.poi;
        }

        @Required
        public Place setDistance(String str) {
            this.distance = str;
            return this;
        }

        @Required
        public String getDistance() {
            return this.distance;
        }

        public Place setIntent(Template.AndroidIntent androidIntent) {
            this.intent = Optional.ofNullable(androidIntent);
            return this;
        }

        public Optional<Template.AndroidIntent> getIntent() {
            return this.intent;
        }

        public Place setStartPoi(Poi poi) {
            this.start_poi = Optional.ofNullable(poi);
            return this;
        }

        public Optional<Poi> getStartPoi() {
            return this.start_poi;
        }
    }

    @NamespaceName(name = "PlanRoute", namespace = AIApiConstants.Map.NAME)
    /* loaded from: classes3.dex */
    public static class PlanRoute implements InstructionPayload {
        @Required
        private Poi end_poi;
        @Required
        private MapDisplayPage map_display_page;
        private Optional<List<Poi>> mid_pois = Optional.empty();
        @Required
        private String pkg_name;
        @Required
        private List<RoutePreference> route_preference;
        @Required
        private Poi start_poi;
        @Required
        private TripMode trip_mode;

        public PlanRoute() {
        }

        public PlanRoute(String str, MapDisplayPage mapDisplayPage, TripMode tripMode, Poi poi, Poi poi2, List<RoutePreference> list) {
            this.pkg_name = str;
            this.map_display_page = mapDisplayPage;
            this.trip_mode = tripMode;
            this.start_poi = poi;
            this.end_poi = poi2;
            this.route_preference = list;
        }

        @Required
        public PlanRoute setPkgName(String str) {
            this.pkg_name = str;
            return this;
        }

        @Required
        public String getPkgName() {
            return this.pkg_name;
        }

        @Required
        public PlanRoute setMapDisplayPage(MapDisplayPage mapDisplayPage) {
            this.map_display_page = mapDisplayPage;
            return this;
        }

        @Required
        public MapDisplayPage getMapDisplayPage() {
            return this.map_display_page;
        }

        @Required
        public PlanRoute setTripMode(TripMode tripMode) {
            this.trip_mode = tripMode;
            return this;
        }

        @Required
        public TripMode getTripMode() {
            return this.trip_mode;
        }

        @Required
        public PlanRoute setStartPoi(Poi poi) {
            this.start_poi = poi;
            return this;
        }

        @Required
        public Poi getStartPoi() {
            return this.start_poi;
        }

        @Required
        public PlanRoute setEndPoi(Poi poi) {
            this.end_poi = poi;
            return this;
        }

        @Required
        public Poi getEndPoi() {
            return this.end_poi;
        }

        @Required
        public PlanRoute setRoutePreference(List<RoutePreference> list) {
            this.route_preference = list;
            return this;
        }

        @Required
        public List<RoutePreference> getRoutePreference() {
            return this.route_preference;
        }

        public PlanRoute setMidPois(List<Poi> list) {
            this.mid_pois = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<Poi>> getMidPois() {
            return this.mid_pois;
        }
    }

    /* loaded from: classes3.dex */
    public static class Poi {
        @Required
        private String address;
        @Required
        private String name;
        @Required
        private PoiType type;
        private Optional<String> id = Optional.empty();
        private Optional<Double> longitude = Optional.empty();
        private Optional<Double> latitude = Optional.empty();

        public Poi() {
        }

        public Poi(PoiType poiType, String str, String str2) {
            this.type = poiType;
            this.name = str;
            this.address = str2;
        }

        @Required
        public Poi setType(PoiType poiType) {
            this.type = poiType;
            return this;
        }

        @Required
        public PoiType getType() {
            return this.type;
        }

        @Required
        public Poi setName(String str) {
            this.name = str;
            return this;
        }

        @Required
        public String getName() {
            return this.name;
        }

        @Required
        public Poi setAddress(String str) {
            this.address = str;
            return this;
        }

        @Required
        public String getAddress() {
            return this.address;
        }

        public Poi setId(String str) {
            this.id = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getId() {
            return this.id;
        }

        public Poi setLongitude(double d) {
            this.longitude = Optional.ofNullable(Double.valueOf(d));
            return this;
        }

        public Optional<Double> getLongitude() {
            return this.longitude;
        }

        public Poi setLatitude(double d) {
            this.latitude = Optional.ofNullable(Double.valueOf(d));
            return this;
        }

        public Optional<Double> getLatitude() {
            return this.latitude;
        }
    }

    @NamespaceName(name = "QueryTrafficCondition", namespace = AIApiConstants.Map.NAME)
    /* loaded from: classes3.dex */
    public static class QueryTrafficCondition implements InstructionPayload {
        @Required
        private String name;

        public QueryTrafficCondition() {
        }

        public QueryTrafficCondition(String str) {
            this.name = str;
        }

        @Required
        public QueryTrafficCondition setName(String str) {
            this.name = str;
            return this;
        }

        @Required
        public String getName() {
            return this.name;
        }
    }

    /* loaded from: classes3.dex */
    public static class RawPlace {
        @Required
        private String keyword;
        private Optional<String> district = Optional.empty();
        private Optional<String> city = Optional.empty();
        private Optional<String> province = Optional.empty();
        private Optional<String> country = Optional.empty();

        public RawPlace() {
        }

        public RawPlace(String str) {
            this.keyword = str;
        }

        @Required
        public RawPlace setKeyword(String str) {
            this.keyword = str;
            return this;
        }

        @Required
        public String getKeyword() {
            return this.keyword;
        }

        public RawPlace setDistrict(String str) {
            this.district = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDistrict() {
            return this.district;
        }

        public RawPlace setCity(String str) {
            this.city = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCity() {
            return this.city;
        }

        public RawPlace setProvince(String str) {
            this.province = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getProvince() {
            return this.province;
        }

        public RawPlace setCountry(String str) {
            this.country = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getCountry() {
            return this.country;
        }
    }

    @NamespaceName(name = "RawPlanRoute", namespace = AIApiConstants.Map.NAME)
    /* loaded from: classes3.dex */
    public static class RawPlanRoute implements InstructionPayload {
        @Required
        private RawPlace dest;
        @Required
        private RawPlace raw_dest;
        @Required
        private List<RoutePreference> route_preference;
        private Optional<RawPlace> origin = Optional.empty();
        private Optional<List<RawPlace>> passing = Optional.empty();
        private Optional<String> tag = Optional.empty();
        private Optional<String> directive = Optional.empty();

        public RawPlanRoute() {
        }

        public RawPlanRoute(RawPlace rawPlace, RawPlace rawPlace2, List<RoutePreference> list) {
            this.dest = rawPlace;
            this.raw_dest = rawPlace2;
            this.route_preference = list;
        }

        @Required
        public RawPlanRoute setDest(RawPlace rawPlace) {
            this.dest = rawPlace;
            return this;
        }

        @Required
        public RawPlace getDest() {
            return this.dest;
        }

        @Required
        public RawPlanRoute setRawDest(RawPlace rawPlace) {
            this.raw_dest = rawPlace;
            return this;
        }

        @Required
        public RawPlace getRawDest() {
            return this.raw_dest;
        }

        public RawPlanRoute setOrigin(RawPlace rawPlace) {
            this.origin = Optional.ofNullable(rawPlace);
            return this;
        }

        public Optional<RawPlace> getOrigin() {
            return this.origin;
        }

        public RawPlanRoute setPassing(List<RawPlace> list) {
            this.passing = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<RawPlace>> getPassing() {
            return this.passing;
        }

        public RawPlanRoute setTag(String str) {
            this.tag = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getTag() {
            return this.tag;
        }

        public RawPlanRoute setDirective(String str) {
            this.directive = Optional.ofNullable(str);
            return this;
        }

        public Optional<String> getDirective() {
            return this.directive;
        }

        @Required
        public RawPlanRoute setRoutePreference(List<RoutePreference> list) {
            this.route_preference = list;
            return this;
        }

        @Required
        public List<RoutePreference> getRoutePreference() {
            return this.route_preference;
        }
    }

    @NamespaceName(name = "RefreshBuslines", namespace = AIApiConstants.Map.NAME)
    /* loaded from: classes3.dex */
    public static class RefreshBuslines implements EventPayload {
        @Required
        private List<Busline> lines;

        public RefreshBuslines() {
        }

        public RefreshBuslines(List<Busline> list) {
            this.lines = list;
        }

        @Required
        public RefreshBuslines setLines(List<Busline> list) {
            this.lines = list;
            return this;
        }

        @Required
        public List<Busline> getLines() {
            return this.lines;
        }
    }

    @NamespaceName(name = "RefreshBuslinesInfo", namespace = AIApiConstants.Map.NAME)
    /* loaded from: classes3.dex */
    public static class RefreshBuslinesInfo implements InstructionPayload {
        @Required
        private List<Busline> lines;

        public RefreshBuslinesInfo() {
        }

        public RefreshBuslinesInfo(List<Busline> list) {
            this.lines = list;
        }

        @Required
        public RefreshBuslinesInfo setLines(List<Busline> list) {
            this.lines = list;
            return this;
        }

        @Required
        public List<Busline> getLines() {
            return this.lines;
        }
    }

    @NamespaceName(name = "SearchAlong", namespace = AIApiConstants.Map.NAME)
    /* loaded from: classes3.dex */
    public static class SearchAlong implements InstructionPayload {
        @Required
        private String name;
        private Optional<Boolean> search_target = Optional.empty();
        private Optional<SearchAlongType> search_type = Optional.empty();
        private Optional<MapPoiType> poi_type = Optional.empty();
        private Optional<List<Application.Hint>> hints = Optional.empty();

        public SearchAlong() {
        }

        public SearchAlong(String str) {
            this.name = str;
        }

        @Required
        public SearchAlong setName(String str) {
            this.name = str;
            return this;
        }

        @Required
        public String getName() {
            return this.name;
        }

        public SearchAlong setSearchTarget(boolean z) {
            this.search_target = Optional.ofNullable(Boolean.valueOf(z));
            return this;
        }

        public Optional<Boolean> isSearchTarget() {
            return this.search_target;
        }

        public SearchAlong setSearchType(SearchAlongType searchAlongType) {
            this.search_type = Optional.ofNullable(searchAlongType);
            return this;
        }

        public Optional<SearchAlongType> getSearchType() {
            return this.search_type;
        }

        public SearchAlong setPoiType(MapPoiType mapPoiType) {
            this.poi_type = Optional.ofNullable(mapPoiType);
            return this;
        }

        public Optional<MapPoiType> getPoiType() {
            return this.poi_type;
        }

        public SearchAlong setHints(List<Application.Hint> list) {
            this.hints = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<Application.Hint>> getHints() {
            return this.hints;
        }
    }

    @NamespaceName(name = "SetBroadcastMode", namespace = AIApiConstants.Map.NAME)
    /* loaded from: classes3.dex */
    public static class SetBroadcastMode implements InstructionPayload {
        @Required
        private BroadcastMode mode;

        public SetBroadcastMode() {
        }

        public SetBroadcastMode(BroadcastMode broadcastMode) {
            this.mode = broadcastMode;
        }

        @Required
        public SetBroadcastMode setMode(BroadcastMode broadcastMode) {
            this.mode = broadcastMode;
            return this;
        }

        @Required
        public BroadcastMode getMode() {
            return this.mode;
        }
    }

    @NamespaceName(name = "SetMapMode", namespace = AIApiConstants.Map.NAME)
    /* loaded from: classes3.dex */
    public static class SetMapMode implements InstructionPayload {
        @Required
        private List<MapMode> map_mode;

        public SetMapMode() {
        }

        public SetMapMode(List<MapMode> list) {
            this.map_mode = list;
        }

        @Required
        public SetMapMode setMapMode(List<MapMode> list) {
            this.map_mode = list;
            return this;
        }

        @Required
        public List<MapMode> getMapMode() {
            return this.map_mode;
        }
    }

    @NamespaceName(name = "SetPreference", namespace = AIApiConstants.Map.NAME)
    /* loaded from: classes3.dex */
    public static class SetPreference implements InstructionPayload {
        private Optional<List<Application.Hint>> hints = Optional.empty();
        @Required
        private List<RoutePreference> route_preference;

        public SetPreference() {
        }

        public SetPreference(List<RoutePreference> list) {
            this.route_preference = list;
        }

        @Required
        public SetPreference setRoutePreference(List<RoutePreference> list) {
            this.route_preference = list;
            return this;
        }

        @Required
        public List<RoutePreference> getRoutePreference() {
            return this.route_preference;
        }

        public SetPreference setHints(List<Application.Hint> list) {
            this.hints = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<Application.Hint>> getHints() {
            return this.hints;
        }
    }

    @NamespaceName(name = "ShowTips", namespace = AIApiConstants.Map.NAME)
    /* loaded from: classes3.dex */
    public static class ShowTips implements InstructionPayload {
        @Required
        private List<Place> items;
        @Required
        private MapDisplayPage map_display_page;
        @Required
        private String pkg_name;
        @Required
        private List<RoutePreference> route_preference;
        private Optional<Template.Image> skill_icon = Optional.empty();
        @Required
        private TripMode trip_mode;

        public ShowTips() {
        }

        public ShowTips(String str, MapDisplayPage mapDisplayPage, TripMode tripMode, List<Place> list, List<RoutePreference> list2) {
            this.pkg_name = str;
            this.map_display_page = mapDisplayPage;
            this.trip_mode = tripMode;
            this.items = list;
            this.route_preference = list2;
        }

        @Required
        public ShowTips setPkgName(String str) {
            this.pkg_name = str;
            return this;
        }

        @Required
        public String getPkgName() {
            return this.pkg_name;
        }

        @Required
        public ShowTips setMapDisplayPage(MapDisplayPage mapDisplayPage) {
            this.map_display_page = mapDisplayPage;
            return this;
        }

        @Required
        public MapDisplayPage getMapDisplayPage() {
            return this.map_display_page;
        }

        @Required
        public ShowTips setTripMode(TripMode tripMode) {
            this.trip_mode = tripMode;
            return this;
        }

        @Required
        public TripMode getTripMode() {
            return this.trip_mode;
        }

        @Required
        public ShowTips setItems(List<Place> list) {
            this.items = list;
            return this;
        }

        @Required
        public List<Place> getItems() {
            return this.items;
        }

        @Required
        public ShowTips setRoutePreference(List<RoutePreference> list) {
            this.route_preference = list;
            return this;
        }

        @Required
        public List<RoutePreference> getRoutePreference() {
            return this.route_preference;
        }

        public ShowTips setSkillIcon(Template.Image image) {
            this.skill_icon = Optional.ofNullable(image);
            return this;
        }

        public Optional<Template.Image> getSkillIcon() {
            return this.skill_icon;
        }
    }

    @NamespaceName(name = "TurnOff", namespace = AIApiConstants.Map.NAME)
    /* loaded from: classes3.dex */
    public static class TurnOff implements InstructionPayload {
        @Required
        private MapFeature feature;

        public TurnOff() {
        }

        public TurnOff(MapFeature mapFeature) {
            this.feature = mapFeature;
        }

        @Required
        public TurnOff setFeature(MapFeature mapFeature) {
            this.feature = mapFeature;
            return this;
        }

        @Required
        public MapFeature getFeature() {
            return this.feature;
        }
    }

    @NamespaceName(name = "TurnOn", namespace = AIApiConstants.Map.NAME)
    /* loaded from: classes3.dex */
    public static class TurnOn implements InstructionPayload {
        @Required
        private MapFeature feature;

        public TurnOn() {
        }

        public TurnOn(MapFeature mapFeature) {
            this.feature = mapFeature;
        }

        @Required
        public TurnOn setFeature(MapFeature mapFeature) {
            this.feature = mapFeature;
            return this;
        }

        @Required
        public MapFeature getFeature() {
            return this.feature;
        }
    }
}
