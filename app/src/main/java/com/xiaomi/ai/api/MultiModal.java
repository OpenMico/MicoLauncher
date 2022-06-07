package com.xiaomi.ai.api;

import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.common.EventPayload;
import com.xiaomi.ai.api.common.InstructionPayload;
import com.xiaomi.ai.api.common.NamespaceName;
import com.xiaomi.ai.api.common.Required;
import com.xiaomi.common.Optional;
import java.util.List;

/* loaded from: classes3.dex */
public class MultiModal {

    @NamespaceName(name = "ImageStreamFinished", namespace = AIApiConstants.MultiModal.NAME)
    /* loaded from: classes3.dex */
    public static class ImageStreamFinished implements EventPayload {
    }

    @NamespaceName(name = "VisionRecognizeFinished", namespace = AIApiConstants.MultiModal.NAME)
    /* loaded from: classes3.dex */
    public static class VisionRecognizeFinished implements EventPayload {
    }

    @NamespaceName(name = "VisionRecognizeStarted", namespace = AIApiConstants.MultiModal.NAME)
    /* loaded from: classes3.dex */
    public static class VisionRecognizeStarted implements EventPayload {
    }

    /* loaded from: classes3.dex */
    public enum FeatureName {
        EYE_CONTACT_WAKEUP(0),
        MORNING_GREET(1);
        
        private int id;

        FeatureName(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum GestureType {
        INVALID(-1),
        OK(0),
        FAST_BACKWARD(1),
        FAST_FORWARD(2),
        STOP(3),
        THUMS_UP(4);
        
        private int id;

        GestureType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum ImageDecodeAlgo {
        HUMAN_DETECTION(0);
        
        private int id;

        ImageDecodeAlgo(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum ImageFormat {
        JPEG(0),
        PNG(1);
        
        private int id;

        ImageFormat(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    /* loaded from: classes3.dex */
    public enum VisionRecognizeType {
        UNKNOWN(-1),
        FACE(0),
        GESTURE(1);
        
        private int id;

        VisionRecognizeType(int i) {
            this.id = i;
        }

        public int getId() {
            return this.id;
        }
    }

    @NamespaceName(name = "ExecutionResult", namespace = AIApiConstants.MultiModal.NAME)
    /* loaded from: classes3.dex */
    public static class ExecutionResult implements InstructionPayload {
        @Required
        private List<ExecutionResultDetail> results;

        public ExecutionResult() {
        }

        public ExecutionResult(List<ExecutionResultDetail> list) {
            this.results = list;
        }

        @Required
        public ExecutionResult setResults(List<ExecutionResultDetail> list) {
            this.results = list;
            return this;
        }

        @Required
        public List<ExecutionResultDetail> getResults() {
            return this.results;
        }
    }

    /* loaded from: classes3.dex */
    public static class ExecutionResultDetail {
        @Required
        private boolean is_success;
        @Required
        private FeatureName name;

        public ExecutionResultDetail() {
        }

        public ExecutionResultDetail(FeatureName featureName, boolean z) {
            this.name = featureName;
            this.is_success = z;
        }

        @Required
        public ExecutionResultDetail setName(FeatureName featureName) {
            this.name = featureName;
            return this;
        }

        @Required
        public FeatureName getName() {
            return this.name;
        }

        @Required
        public ExecutionResultDetail setIsSuccess(boolean z) {
            this.is_success = z;
            return this;
        }

        @Required
        public boolean isSuccess() {
            return this.is_success;
        }
    }

    /* loaded from: classes3.dex */
    public static class EyeContactInfo {
        @Required
        private boolean has_face;
        @Required
        private boolean is_eye_open;

        public EyeContactInfo() {
        }

        public EyeContactInfo(boolean z, boolean z2) {
            this.has_face = z;
            this.is_eye_open = z2;
        }

        @Required
        public EyeContactInfo setHasFace(boolean z) {
            this.has_face = z;
            return this;
        }

        @Required
        public boolean isHasFace() {
            return this.has_face;
        }

        @Required
        public EyeContactInfo setIsEyeOpen(boolean z) {
            this.is_eye_open = z;
            return this;
        }

        @Required
        public boolean isEyeOpen() {
            return this.is_eye_open;
        }
    }

    @NamespaceName(name = "EyeContactRecognizeResult", namespace = AIApiConstants.MultiModal.NAME)
    /* loaded from: classes3.dex */
    public static class EyeContactRecognizeResult implements EventPayload {
        @Required
        private List<EyeContactInfo> results;

        public EyeContactRecognizeResult() {
        }

        public EyeContactRecognizeResult(List<EyeContactInfo> list) {
            this.results = list;
        }

        @Required
        public EyeContactRecognizeResult setResults(List<EyeContactInfo> list) {
            this.results = list;
            return this;
        }

        @Required
        public List<EyeContactInfo> getResults() {
            return this.results;
        }
    }

    /* loaded from: classes3.dex */
    public static class FaceParam {
        private Optional<Integer> age = Optional.empty();
        private Optional<Integer> distance = Optional.empty();
        private Optional<Integer> eye_right_y = Optional.empty();
        private Optional<Integer> eye_right_x = Optional.empty();
        private Optional<Integer> eye_left_y = Optional.empty();
        private Optional<Integer> eye_left_x = Optional.empty();

        public FaceParam setAge(int i) {
            this.age = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getAge() {
            return this.age;
        }

        public FaceParam setDistance(int i) {
            this.distance = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getDistance() {
            return this.distance;
        }

        public FaceParam setEyeRightY(int i) {
            this.eye_right_y = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getEyeRightY() {
            return this.eye_right_y;
        }

        public FaceParam setEyeRightX(int i) {
            this.eye_right_x = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getEyeRightX() {
            return this.eye_right_x;
        }

        public FaceParam setEyeLeftY(int i) {
            this.eye_left_y = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getEyeLeftY() {
            return this.eye_left_y;
        }

        public FaceParam setEyeLeftX(int i) {
            this.eye_left_x = Optional.ofNullable(Integer.valueOf(i));
            return this;
        }

        public Optional<Integer> getEyeLeftX() {
            return this.eye_left_x;
        }
    }

    /* loaded from: classes3.dex */
    public static class GestureParam {
        @Required
        private GestureType type;

        public GestureParam() {
        }

        public GestureParam(GestureType gestureType) {
            this.type = gestureType;
        }

        @Required
        public GestureParam setType(GestureType gestureType) {
            this.type = gestureType;
            return this;
        }

        @Required
        public GestureType getType() {
            return this.type;
        }
    }

    /* loaded from: classes3.dex */
    public static class HumanDetectionParam {
        @Required
        private boolean human_detected;

        public HumanDetectionParam() {
        }

        public HumanDetectionParam(boolean z) {
            this.human_detected = z;
        }

        @Required
        public HumanDetectionParam setHumanDetected(boolean z) {
            this.human_detected = z;
            return this;
        }

        @Required
        public boolean isHumanDetected() {
            return this.human_detected;
        }
    }

    @NamespaceName(name = "ImageStreamStarted", namespace = AIApiConstants.MultiModal.NAME)
    /* loaded from: classes3.dex */
    public static class ImageStreamStarted implements EventPayload {
        @Required
        private ImageDecodeAlgo decode;
        @Required
        private ImageFormat format;
        @Required
        private int height;
        @Required
        private int width;

        public ImageStreamStarted() {
        }

        public ImageStreamStarted(ImageFormat imageFormat, ImageDecodeAlgo imageDecodeAlgo, int i, int i2) {
            this.format = imageFormat;
            this.decode = imageDecodeAlgo;
            this.height = i;
            this.width = i2;
        }

        @Required
        public ImageStreamStarted setFormat(ImageFormat imageFormat) {
            this.format = imageFormat;
            return this;
        }

        @Required
        public ImageFormat getFormat() {
            return this.format;
        }

        @Required
        public ImageStreamStarted setDecode(ImageDecodeAlgo imageDecodeAlgo) {
            this.decode = imageDecodeAlgo;
            return this;
        }

        @Required
        public ImageDecodeAlgo getDecode() {
            return this.decode;
        }

        @Required
        public ImageStreamStarted setHeight(int i) {
            this.height = i;
            return this;
        }

        @Required
        public int getHeight() {
            return this.height;
        }

        @Required
        public ImageStreamStarted setWidth(int i) {
            this.width = i;
            return this;
        }

        @Required
        public int getWidth() {
            return this.width;
        }
    }

    @NamespaceName(name = "Switch", namespace = AIApiConstants.MultiModal.NAME)
    /* loaded from: classes3.dex */
    public static class Switch implements InstructionPayload {
        @Required
        private List<SwitchOperation> operations;

        public Switch() {
        }

        public Switch(List<SwitchOperation> list) {
            this.operations = list;
        }

        @Required
        public Switch setOperations(List<SwitchOperation> list) {
            this.operations = list;
            return this;
        }

        @Required
        public List<SwitchOperation> getOperations() {
            return this.operations;
        }
    }

    /* loaded from: classes3.dex */
    public static class SwitchOperation {
        @Required
        private boolean enabled;
        @Required
        private FeatureName name;

        public SwitchOperation() {
        }

        public SwitchOperation(FeatureName featureName, boolean z) {
            this.name = featureName;
            this.enabled = z;
        }

        @Required
        public SwitchOperation setName(FeatureName featureName) {
            this.name = featureName;
            return this;
        }

        @Required
        public FeatureName getName() {
            return this.name;
        }

        @Required
        public SwitchOperation setEnabled(boolean z) {
            this.enabled = z;
            return this;
        }

        @Required
        public boolean isEnabled() {
            return this.enabled;
        }
    }

    @NamespaceName(name = "VisionRecognizeAggregateResult", namespace = AIApiConstants.MultiModal.NAME)
    /* loaded from: classes3.dex */
    public static class VisionRecognizeAggregateResult implements EventPayload {
        private Optional<List<FaceParam>> faces = Optional.empty();
        private Optional<GestureParam> gesture = Optional.empty();
        private Optional<HumanDetectionParam> human_detection = Optional.empty();

        public VisionRecognizeAggregateResult setFaces(List<FaceParam> list) {
            this.faces = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<FaceParam>> getFaces() {
            return this.faces;
        }

        public VisionRecognizeAggregateResult setGesture(GestureParam gestureParam) {
            this.gesture = Optional.ofNullable(gestureParam);
            return this;
        }

        public Optional<GestureParam> getGesture() {
            return this.gesture;
        }

        public VisionRecognizeAggregateResult setHumanDetection(HumanDetectionParam humanDetectionParam) {
            this.human_detection = Optional.ofNullable(humanDetectionParam);
            return this;
        }

        public Optional<HumanDetectionParam> getHumanDetection() {
            return this.human_detection;
        }
    }

    @NamespaceName(name = "VisionRecognizeResult", namespace = AIApiConstants.MultiModal.NAME)
    /* loaded from: classes3.dex */
    public static class VisionRecognizeResult implements EventPayload {
        private Optional<List<FaceParam>> face_param = Optional.empty();
        private Optional<GestureParam> gesture_param = Optional.empty();
        @Required
        private VisionRecognizeType type;

        public VisionRecognizeResult() {
        }

        public VisionRecognizeResult(VisionRecognizeType visionRecognizeType) {
            this.type = visionRecognizeType;
        }

        @Required
        public VisionRecognizeResult setType(VisionRecognizeType visionRecognizeType) {
            this.type = visionRecognizeType;
            return this;
        }

        @Required
        public VisionRecognizeType getType() {
            return this.type;
        }

        public VisionRecognizeResult setFaceParam(List<FaceParam> list) {
            this.face_param = Optional.ofNullable(list);
            return this;
        }

        public Optional<List<FaceParam>> getFaceParam() {
            return this.face_param;
        }

        public VisionRecognizeResult setGestureParam(GestureParam gestureParam) {
            this.gesture_param = Optional.ofNullable(gestureParam);
            return this;
        }

        public Optional<GestureParam> getGestureParam() {
            return this.gesture_param;
        }
    }
}
