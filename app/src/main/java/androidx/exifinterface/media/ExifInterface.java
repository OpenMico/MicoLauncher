package androidx.exifinterface.media;

import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.media.MediaDataSource;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.system.OsConstants;
import android.util.Log;
import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.view.InputDeviceCompat;
import androidx.exifinterface.media.a;
import com.alibaba.fastjson.asm.Opcodes;
import com.fasterxml.jackson.core.JsonPointer;
import com.xiaomi.idm.api.conn.EndPoint;
import com.xiaomi.micolauncher.skills.music.model.lrc.LrcRow;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.smarthome.devicelibrary.bluetooth.connect.Code;
import io.netty.handler.codec.dns.DnsRecord;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.CRC32;
import okhttp3.internal.ws.WebSocketProtocol;
import org.fourthline.cling.support.model.dlna.DLNAProfiles;
import org.slf4j.Marker;

/* loaded from: classes.dex */
public class ExifInterface {
    public static final short ALTITUDE_ABOVE_SEA_LEVEL = 0;
    public static final short ALTITUDE_BELOW_SEA_LEVEL = 1;
    public static final int COLOR_SPACE_S_RGB = 1;
    public static final int COLOR_SPACE_UNCALIBRATED = 65535;
    public static final short CONTRAST_HARD = 2;
    public static final short CONTRAST_NORMAL = 0;
    public static final short CONTRAST_SOFT = 1;
    public static final int DATA_DEFLATE_ZIP = 8;
    public static final int DATA_HUFFMAN_COMPRESSED = 2;
    public static final int DATA_JPEG = 6;
    public static final int DATA_JPEG_COMPRESSED = 7;
    public static final int DATA_LOSSY_JPEG = 34892;
    public static final int DATA_PACK_BITS_COMPRESSED = 32773;
    public static final int DATA_UNCOMPRESSED = 1;
    public static final short EXPOSURE_MODE_AUTO = 0;
    public static final short EXPOSURE_MODE_AUTO_BRACKET = 2;
    public static final short EXPOSURE_MODE_MANUAL = 1;
    public static final short EXPOSURE_PROGRAM_ACTION = 6;
    public static final short EXPOSURE_PROGRAM_APERTURE_PRIORITY = 3;
    public static final short EXPOSURE_PROGRAM_CREATIVE = 5;
    public static final short EXPOSURE_PROGRAM_LANDSCAPE_MODE = 8;
    public static final short EXPOSURE_PROGRAM_MANUAL = 1;
    public static final short EXPOSURE_PROGRAM_NORMAL = 2;
    public static final short EXPOSURE_PROGRAM_NOT_DEFINED = 0;
    public static final short EXPOSURE_PROGRAM_PORTRAIT_MODE = 7;
    public static final short EXPOSURE_PROGRAM_SHUTTER_PRIORITY = 4;
    public static final short FILE_SOURCE_DSC = 3;
    public static final short FILE_SOURCE_OTHER = 0;
    public static final short FILE_SOURCE_REFLEX_SCANNER = 2;
    public static final short FILE_SOURCE_TRANSPARENT_SCANNER = 1;
    public static final short FLAG_FLASH_FIRED = 1;
    public static final short FLAG_FLASH_MODE_AUTO = 24;
    public static final short FLAG_FLASH_MODE_COMPULSORY_FIRING = 8;
    public static final short FLAG_FLASH_MODE_COMPULSORY_SUPPRESSION = 16;
    public static final short FLAG_FLASH_NO_FLASH_FUNCTION = 32;
    public static final short FLAG_FLASH_RED_EYE_SUPPORTED = 64;
    public static final short FLAG_FLASH_RETURN_LIGHT_DETECTED = 6;
    public static final short FLAG_FLASH_RETURN_LIGHT_NOT_DETECTED = 4;
    public static final short FORMAT_CHUNKY = 1;
    public static final short FORMAT_PLANAR = 2;
    public static final short GAIN_CONTROL_HIGH_GAIN_DOWN = 4;
    public static final short GAIN_CONTROL_HIGH_GAIN_UP = 2;
    public static final short GAIN_CONTROL_LOW_GAIN_DOWN = 3;
    public static final short GAIN_CONTROL_LOW_GAIN_UP = 1;
    public static final short GAIN_CONTROL_NONE = 0;
    public static final String GPS_DIRECTION_MAGNETIC = "M";
    public static final String GPS_DIRECTION_TRUE = "T";
    public static final String GPS_DISTANCE_KILOMETERS = "K";
    public static final String GPS_DISTANCE_MILES = "M";
    public static final String GPS_DISTANCE_NAUTICAL_MILES = "N";
    public static final String GPS_MEASUREMENT_2D = "2";
    public static final String GPS_MEASUREMENT_3D = "3";
    public static final short GPS_MEASUREMENT_DIFFERENTIAL_CORRECTED = 1;
    public static final String GPS_MEASUREMENT_INTERRUPTED = "V";
    public static final String GPS_MEASUREMENT_IN_PROGRESS = "A";
    public static final short GPS_MEASUREMENT_NO_DIFFERENTIAL = 0;
    public static final String GPS_SPEED_KILOMETERS_PER_HOUR = "K";
    public static final String GPS_SPEED_KNOTS = "N";
    public static final String GPS_SPEED_MILES_PER_HOUR = "M";
    public static final String LATITUDE_NORTH = "N";
    public static final String LATITUDE_SOUTH = "S";
    public static final short LIGHT_SOURCE_CLOUDY_WEATHER = 10;
    public static final short LIGHT_SOURCE_COOL_WHITE_FLUORESCENT = 14;
    public static final short LIGHT_SOURCE_D50 = 23;
    public static final short LIGHT_SOURCE_D55 = 20;
    public static final short LIGHT_SOURCE_D65 = 21;
    public static final short LIGHT_SOURCE_D75 = 22;
    public static final short LIGHT_SOURCE_DAYLIGHT = 1;
    public static final short LIGHT_SOURCE_DAYLIGHT_FLUORESCENT = 12;
    public static final short LIGHT_SOURCE_DAY_WHITE_FLUORESCENT = 13;
    public static final short LIGHT_SOURCE_FINE_WEATHER = 9;
    public static final short LIGHT_SOURCE_FLASH = 4;
    public static final short LIGHT_SOURCE_FLUORESCENT = 2;
    public static final short LIGHT_SOURCE_ISO_STUDIO_TUNGSTEN = 24;
    public static final short LIGHT_SOURCE_OTHER = 255;
    public static final short LIGHT_SOURCE_SHADE = 11;
    public static final short LIGHT_SOURCE_STANDARD_LIGHT_A = 17;
    public static final short LIGHT_SOURCE_STANDARD_LIGHT_B = 18;
    public static final short LIGHT_SOURCE_STANDARD_LIGHT_C = 19;
    public static final short LIGHT_SOURCE_TUNGSTEN = 3;
    public static final short LIGHT_SOURCE_UNKNOWN = 0;
    public static final short LIGHT_SOURCE_WARM_WHITE_FLUORESCENT = 16;
    public static final short LIGHT_SOURCE_WHITE_FLUORESCENT = 15;
    public static final String LONGITUDE_EAST = "E";
    public static final String LONGITUDE_WEST = "W";
    public static final short METERING_MODE_AVERAGE = 1;
    public static final short METERING_MODE_CENTER_WEIGHT_AVERAGE = 2;
    public static final short METERING_MODE_MULTI_SPOT = 4;
    public static final short METERING_MODE_OTHER = 255;
    public static final short METERING_MODE_PARTIAL = 6;
    public static final short METERING_MODE_PATTERN = 5;
    public static final short METERING_MODE_SPOT = 3;
    public static final short METERING_MODE_UNKNOWN = 0;
    public static final int ORIENTATION_FLIP_HORIZONTAL = 2;
    public static final int ORIENTATION_FLIP_VERTICAL = 4;
    public static final int ORIENTATION_NORMAL = 1;
    public static final int ORIENTATION_ROTATE_180 = 3;
    public static final int ORIENTATION_ROTATE_270 = 8;
    public static final int ORIENTATION_ROTATE_90 = 6;
    public static final int ORIENTATION_TRANSPOSE = 5;
    public static final int ORIENTATION_TRANSVERSE = 7;
    public static final int ORIENTATION_UNDEFINED = 0;
    public static final int ORIGINAL_RESOLUTION_IMAGE = 0;
    private static final HashMap<Integer, d>[] P;
    public static final int PHOTOMETRIC_INTERPRETATION_BLACK_IS_ZERO = 1;
    public static final int PHOTOMETRIC_INTERPRETATION_RGB = 2;
    public static final int PHOTOMETRIC_INTERPRETATION_WHITE_IS_ZERO = 0;
    public static final int PHOTOMETRIC_INTERPRETATION_YCBCR = 6;
    private static final HashMap<String, d>[] Q;
    public static final int REDUCED_RESOLUTION_IMAGE = 1;
    public static final short RENDERED_PROCESS_CUSTOM = 1;
    public static final short RENDERED_PROCESS_NORMAL = 0;
    public static final short RESOLUTION_UNIT_CENTIMETERS = 3;
    public static final short RESOLUTION_UNIT_INCHES = 2;
    public static final short SATURATION_HIGH = 0;
    public static final short SATURATION_LOW = 0;
    public static final short SATURATION_NORMAL = 0;
    public static final short SCENE_CAPTURE_TYPE_LANDSCAPE = 1;
    public static final short SCENE_CAPTURE_TYPE_NIGHT = 3;
    public static final short SCENE_CAPTURE_TYPE_PORTRAIT = 2;
    public static final short SCENE_CAPTURE_TYPE_STANDARD = 0;
    public static final short SCENE_TYPE_DIRECTLY_PHOTOGRAPHED = 1;
    public static final short SENSITIVITY_TYPE_ISO_SPEED = 3;
    public static final short SENSITIVITY_TYPE_REI = 2;
    public static final short SENSITIVITY_TYPE_REI_AND_ISO = 6;
    public static final short SENSITIVITY_TYPE_SOS = 1;
    public static final short SENSITIVITY_TYPE_SOS_AND_ISO = 5;
    public static final short SENSITIVITY_TYPE_SOS_AND_REI = 4;
    public static final short SENSITIVITY_TYPE_SOS_AND_REI_AND_ISO = 7;
    public static final short SENSITIVITY_TYPE_UNKNOWN = 0;
    public static final short SENSOR_TYPE_COLOR_SEQUENTIAL = 5;
    public static final short SENSOR_TYPE_COLOR_SEQUENTIAL_LINEAR = 8;
    public static final short SENSOR_TYPE_NOT_DEFINED = 1;
    public static final short SENSOR_TYPE_ONE_CHIP = 2;
    public static final short SENSOR_TYPE_THREE_CHIP = 4;
    public static final short SENSOR_TYPE_TRILINEAR = 7;
    public static final short SENSOR_TYPE_TWO_CHIP = 3;
    public static final short SHARPNESS_HARD = 2;
    public static final short SHARPNESS_NORMAL = 0;
    public static final short SHARPNESS_SOFT = 1;
    public static final int STREAM_TYPE_EXIF_DATA_ONLY = 1;
    public static final int STREAM_TYPE_FULL_IMAGE_DATA = 0;
    public static final short SUBJECT_DISTANCE_RANGE_CLOSE_VIEW = 2;
    public static final short SUBJECT_DISTANCE_RANGE_DISTANT_VIEW = 3;
    public static final short SUBJECT_DISTANCE_RANGE_MACRO = 1;
    public static final short SUBJECT_DISTANCE_RANGE_UNKNOWN = 0;
    @Deprecated
    public static final String TAG_CAMARA_OWNER_NAME = "CameraOwnerName";
    public static final String TAG_CAMERA_OWNER_NAME = "CameraOwnerName";
    @Deprecated
    public static final String TAG_ISO_SPEED_RATINGS = "ISOSpeedRatings";
    public static final String TAG_LENS_SERIAL_NUMBER = "LensSerialNumber";
    public static final String TAG_MODEL = "Model";
    @Deprecated
    public static final int WHITEBALANCE_AUTO = 0;
    @Deprecated
    public static final int WHITEBALANCE_MANUAL = 1;
    public static final short WHITE_BALANCE_AUTO = 0;
    public static final short WHITE_BALANCE_MANUAL = 1;
    public static final short Y_CB_CR_POSITIONING_CENTERED = 1;
    public static final short Y_CB_CR_POSITIONING_CO_SITED = 2;
    private static final Pattern ap;
    private static final Pattern aq;
    private static final Pattern ar;
    private static final Pattern as;
    static final d[][] e;
    private String U;
    private FileDescriptor V;
    private AssetManager.AssetInputStream W;
    private int X;
    private boolean Y;
    private final HashMap<String, c>[] Z;
    private Set<Integer> aa;
    private ByteOrder ab;
    private boolean ac;
    private boolean ad;
    private boolean ae;
    private int af;
    private int ag;
    private byte[] ah;
    private int ai;
    private int aj;
    private int ak;
    private int al;
    private int am;
    private boolean an;
    private boolean ao;
    private static final boolean h = Log.isLoggable("ExifInterface", 3);
    private static final List<Integer> i = Arrays.asList(1, 6, 3, 8);
    private static final List<Integer> j = Arrays.asList(2, 7, 4, 5);
    public static final int[] BITS_PER_SAMPLE_RGB = {8, 8, 8};
    public static final int[] BITS_PER_SAMPLE_GREYSCALE_1 = {4};
    public static final int[] BITS_PER_SAMPLE_GREYSCALE_2 = {8};
    static final byte[] a = {-1, -40, -1};
    private static final byte[] k = {102, 116, 121, 112};
    private static final byte[] l = {109, 105, 102, 49};
    private static final byte[] m = {104, 101, 105, 99};
    private static final byte[] n = {79, 76, 89, 77, 80, 0};
    private static final byte[] o = {79, 76, 89, 77, 80, 85, 83, 0, 73, 73};
    private static final byte[] p = {-119, 80, 78, 71, 13, 10, 26, 10};
    private static final byte[] q = {101, 88, 73, 102};
    private static final byte[] r = {73, 72, 68, 82};
    private static final byte[] s = {73, 69, 78, 68};
    private static final byte[] t = {82, 73, 70, 70};
    private static final byte[] u = {87, 69, 66, 80};
    private static final byte[] v = {69, 88, 73, 70};
    private static final byte[] w = {-99, 1, 42};
    private static final byte[] x = "VP8X".getBytes(Charset.defaultCharset());
    private static final byte[] y = "VP8L".getBytes(Charset.defaultCharset());
    private static final byte[] z = "VP8 ".getBytes(Charset.defaultCharset());
    private static final byte[] A = "ANIM".getBytes(Charset.defaultCharset());
    private static final byte[] B = "ANMF".getBytes(Charset.defaultCharset());
    static final String[] b = {"", "BYTE", "STRING", "USHORT", "ULONG", "URATIONAL", "SBYTE", "UNDEFINED", "SSHORT", "SLONG", "SRATIONAL", "SINGLE", "DOUBLE", "IFD"};
    static final int[] c = {0, 1, 1, 2, 4, 8, 1, 1, 2, 4, 8, 4, 8, 1};
    static final byte[] d = {65, 83, 67, 73, 73, 0, 0, 0};
    public static final String TAG_NEW_SUBFILE_TYPE = "NewSubfileType";
    public static final String TAG_SUBFILE_TYPE = "SubfileType";
    public static final String TAG_IMAGE_WIDTH = "ImageWidth";
    public static final String TAG_IMAGE_LENGTH = "ImageLength";
    public static final String TAG_BITS_PER_SAMPLE = "BitsPerSample";
    public static final String TAG_COMPRESSION = "Compression";
    public static final String TAG_PHOTOMETRIC_INTERPRETATION = "PhotometricInterpretation";
    public static final String TAG_IMAGE_DESCRIPTION = "ImageDescription";
    public static final String TAG_MAKE = "Make";
    public static final String TAG_STRIP_OFFSETS = "StripOffsets";
    public static final String TAG_ORIENTATION = "Orientation";
    public static final String TAG_SAMPLES_PER_PIXEL = "SamplesPerPixel";
    public static final String TAG_ROWS_PER_STRIP = "RowsPerStrip";
    public static final String TAG_STRIP_BYTE_COUNTS = "StripByteCounts";
    public static final String TAG_X_RESOLUTION = "XResolution";
    public static final String TAG_Y_RESOLUTION = "YResolution";
    public static final String TAG_PLANAR_CONFIGURATION = "PlanarConfiguration";
    public static final String TAG_RESOLUTION_UNIT = "ResolutionUnit";
    public static final String TAG_TRANSFER_FUNCTION = "TransferFunction";
    public static final String TAG_SOFTWARE = "Software";
    public static final String TAG_DATETIME = "DateTime";
    public static final String TAG_ARTIST = "Artist";
    public static final String TAG_WHITE_POINT = "WhitePoint";
    public static final String TAG_PRIMARY_CHROMATICITIES = "PrimaryChromaticities";
    public static final String TAG_JPEG_INTERCHANGE_FORMAT = "JPEGInterchangeFormat";
    public static final String TAG_JPEG_INTERCHANGE_FORMAT_LENGTH = "JPEGInterchangeFormatLength";
    public static final String TAG_Y_CB_CR_COEFFICIENTS = "YCbCrCoefficients";
    public static final String TAG_Y_CB_CR_SUB_SAMPLING = "YCbCrSubSampling";
    public static final String TAG_Y_CB_CR_POSITIONING = "YCbCrPositioning";
    public static final String TAG_REFERENCE_BLACK_WHITE = "ReferenceBlackWhite";
    public static final String TAG_COPYRIGHT = "Copyright";
    public static final String TAG_RW2_SENSOR_TOP_BORDER = "SensorTopBorder";
    public static final String TAG_RW2_SENSOR_LEFT_BORDER = "SensorLeftBorder";
    public static final String TAG_RW2_SENSOR_BOTTOM_BORDER = "SensorBottomBorder";
    public static final String TAG_RW2_SENSOR_RIGHT_BORDER = "SensorRightBorder";
    public static final String TAG_RW2_ISO = "ISO";
    public static final String TAG_RW2_JPG_FROM_RAW = "JpgFromRaw";
    public static final String TAG_XMP = "Xmp";
    private static final d[] E = {new d(TAG_NEW_SUBFILE_TYPE, DnsRecord.CLASS_NONE, 4), new d(TAG_SUBFILE_TYPE, 255, 4), new d(TAG_IMAGE_WIDTH, 256, 3, 4), new d(TAG_IMAGE_LENGTH, 257, 3, 4), new d(TAG_BITS_PER_SAMPLE, EndPoint.MIRROR_V1_MC_VERSION, 3), new d(TAG_COMPRESSION, 259, 3), new d(TAG_PHOTOMETRIC_INTERPRETATION, 262, 3), new d(TAG_IMAGE_DESCRIPTION, 270, 2), new d(TAG_MAKE, 271, 2), new d("Model", 272, 2), new d(TAG_STRIP_OFFSETS, 273, 3, 4), new d(TAG_ORIENTATION, 274, 3), new d(TAG_SAMPLES_PER_PIXEL, 277, 3), new d(TAG_ROWS_PER_STRIP, 278, 3, 4), new d(TAG_STRIP_BYTE_COUNTS, 279, 3, 4), new d(TAG_X_RESOLUTION, 282, 5), new d(TAG_Y_RESOLUTION, 283, 5), new d(TAG_PLANAR_CONFIGURATION, 284, 3), new d(TAG_RESOLUTION_UNIT, 296, 3), new d(TAG_TRANSFER_FUNCTION, 301, 3), new d(TAG_SOFTWARE, 305, 2), new d(TAG_DATETIME, 306, 2), new d(TAG_ARTIST, 315, 2), new d(TAG_WHITE_POINT, 318, 5), new d(TAG_PRIMARY_CHROMATICITIES, 319, 5), new d("SubIFDPointer", 330, 4), new d(TAG_JPEG_INTERCHANGE_FORMAT, InputDeviceCompat.SOURCE_DPAD, 4), new d(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, 514, 4), new d(TAG_Y_CB_CR_COEFFICIENTS, 529, 5), new d(TAG_Y_CB_CR_SUB_SAMPLING, 530, 3), new d(TAG_Y_CB_CR_POSITIONING, 531, 3), new d(TAG_REFERENCE_BLACK_WHITE, 532, 5), new d(TAG_COPYRIGHT, 33432, 2), new d("ExifIFDPointer", 34665, 4), new d("GPSInfoIFDPointer", 34853, 4), new d(TAG_RW2_SENSOR_TOP_BORDER, 4, 4), new d(TAG_RW2_SENSOR_LEFT_BORDER, 5, 4), new d(TAG_RW2_SENSOR_BOTTOM_BORDER, 6, 4), new d(TAG_RW2_SENSOR_RIGHT_BORDER, 7, 4), new d(TAG_RW2_ISO, 23, 3), new d(TAG_RW2_JPG_FROM_RAW, 46, 7), new d(TAG_XMP, 700, 1)};
    public static final String TAG_EXPOSURE_TIME = "ExposureTime";
    public static final String TAG_F_NUMBER = "FNumber";
    public static final String TAG_EXPOSURE_PROGRAM = "ExposureProgram";
    public static final String TAG_SPECTRAL_SENSITIVITY = "SpectralSensitivity";
    public static final String TAG_PHOTOGRAPHIC_SENSITIVITY = "PhotographicSensitivity";
    public static final String TAG_OECF = "OECF";
    public static final String TAG_SENSITIVITY_TYPE = "SensitivityType";
    public static final String TAG_STANDARD_OUTPUT_SENSITIVITY = "StandardOutputSensitivity";
    public static final String TAG_RECOMMENDED_EXPOSURE_INDEX = "RecommendedExposureIndex";
    public static final String TAG_ISO_SPEED = "ISOSpeed";
    public static final String TAG_ISO_SPEED_LATITUDE_YYY = "ISOSpeedLatitudeyyy";
    public static final String TAG_ISO_SPEED_LATITUDE_ZZZ = "ISOSpeedLatitudezzz";
    public static final String TAG_EXIF_VERSION = "ExifVersion";
    public static final String TAG_DATETIME_ORIGINAL = "DateTimeOriginal";
    public static final String TAG_DATETIME_DIGITIZED = "DateTimeDigitized";
    public static final String TAG_OFFSET_TIME = "OffsetTime";
    public static final String TAG_OFFSET_TIME_ORIGINAL = "OffsetTimeOriginal";
    public static final String TAG_OFFSET_TIME_DIGITIZED = "OffsetTimeDigitized";
    public static final String TAG_COMPONENTS_CONFIGURATION = "ComponentsConfiguration";
    public static final String TAG_COMPRESSED_BITS_PER_PIXEL = "CompressedBitsPerPixel";
    public static final String TAG_SHUTTER_SPEED_VALUE = "ShutterSpeedValue";
    public static final String TAG_APERTURE_VALUE = "ApertureValue";
    public static final String TAG_BRIGHTNESS_VALUE = "BrightnessValue";
    public static final String TAG_EXPOSURE_BIAS_VALUE = "ExposureBiasValue";
    public static final String TAG_MAX_APERTURE_VALUE = "MaxApertureValue";
    public static final String TAG_SUBJECT_DISTANCE = "SubjectDistance";
    public static final String TAG_METERING_MODE = "MeteringMode";
    public static final String TAG_LIGHT_SOURCE = "LightSource";
    public static final String TAG_FLASH = "Flash";
    public static final String TAG_FOCAL_LENGTH = "FocalLength";
    public static final String TAG_SUBJECT_AREA = "SubjectArea";
    public static final String TAG_MAKER_NOTE = "MakerNote";
    public static final String TAG_USER_COMMENT = "UserComment";
    public static final String TAG_SUBSEC_TIME = "SubSecTime";
    public static final String TAG_SUBSEC_TIME_ORIGINAL = "SubSecTimeOriginal";
    public static final String TAG_SUBSEC_TIME_DIGITIZED = "SubSecTimeDigitized";
    public static final String TAG_FLASHPIX_VERSION = "FlashpixVersion";
    public static final String TAG_COLOR_SPACE = "ColorSpace";
    public static final String TAG_PIXEL_X_DIMENSION = "PixelXDimension";
    public static final String TAG_PIXEL_Y_DIMENSION = "PixelYDimension";
    public static final String TAG_RELATED_SOUND_FILE = "RelatedSoundFile";
    public static final String TAG_FLASH_ENERGY = "FlashEnergy";
    public static final String TAG_SPATIAL_FREQUENCY_RESPONSE = "SpatialFrequencyResponse";
    public static final String TAG_FOCAL_PLANE_X_RESOLUTION = "FocalPlaneXResolution";
    public static final String TAG_FOCAL_PLANE_Y_RESOLUTION = "FocalPlaneYResolution";
    public static final String TAG_FOCAL_PLANE_RESOLUTION_UNIT = "FocalPlaneResolutionUnit";
    public static final String TAG_SUBJECT_LOCATION = "SubjectLocation";
    public static final String TAG_EXPOSURE_INDEX = "ExposureIndex";
    public static final String TAG_SENSING_METHOD = "SensingMethod";
    public static final String TAG_FILE_SOURCE = "FileSource";
    public static final String TAG_SCENE_TYPE = "SceneType";
    public static final String TAG_CFA_PATTERN = "CFAPattern";
    public static final String TAG_CUSTOM_RENDERED = "CustomRendered";
    public static final String TAG_EXPOSURE_MODE = "ExposureMode";
    public static final String TAG_WHITE_BALANCE = "WhiteBalance";
    public static final String TAG_DIGITAL_ZOOM_RATIO = "DigitalZoomRatio";
    public static final String TAG_FOCAL_LENGTH_IN_35MM_FILM = "FocalLengthIn35mmFilm";
    public static final String TAG_SCENE_CAPTURE_TYPE = "SceneCaptureType";
    public static final String TAG_GAIN_CONTROL = "GainControl";
    public static final String TAG_CONTRAST = "Contrast";
    public static final String TAG_SATURATION = "Saturation";
    public static final String TAG_SHARPNESS = "Sharpness";
    public static final String TAG_DEVICE_SETTING_DESCRIPTION = "DeviceSettingDescription";
    public static final String TAG_SUBJECT_DISTANCE_RANGE = "SubjectDistanceRange";
    public static final String TAG_IMAGE_UNIQUE_ID = "ImageUniqueID";
    public static final String TAG_BODY_SERIAL_NUMBER = "BodySerialNumber";
    public static final String TAG_LENS_SPECIFICATION = "LensSpecification";
    public static final String TAG_LENS_MAKE = "LensMake";
    public static final String TAG_LENS_MODEL = "LensModel";
    public static final String TAG_GAMMA = "Gamma";
    public static final String TAG_DNG_VERSION = "DNGVersion";
    public static final String TAG_DEFAULT_CROP_SIZE = "DefaultCropSize";
    private static final d[] F = {new d(TAG_EXPOSURE_TIME, 33434, 5), new d(TAG_F_NUMBER, 33437, 5), new d(TAG_EXPOSURE_PROGRAM, 34850, 3), new d(TAG_SPECTRAL_SENSITIVITY, 34852, 2), new d(TAG_PHOTOGRAPHIC_SENSITIVITY, 34855, 3), new d(TAG_OECF, 34856, 7), new d(TAG_SENSITIVITY_TYPE, 34864, 3), new d(TAG_STANDARD_OUTPUT_SENSITIVITY, 34865, 4), new d(TAG_RECOMMENDED_EXPOSURE_INDEX, 34866, 4), new d(TAG_ISO_SPEED, 34867, 4), new d(TAG_ISO_SPEED_LATITUDE_YYY, 34868, 4), new d(TAG_ISO_SPEED_LATITUDE_ZZZ, 34869, 4), new d(TAG_EXIF_VERSION, 36864, 2), new d(TAG_DATETIME_ORIGINAL, 36867, 2), new d(TAG_DATETIME_DIGITIZED, 36868, 2), new d(TAG_OFFSET_TIME, 36880, 2), new d(TAG_OFFSET_TIME_ORIGINAL, 36881, 2), new d(TAG_OFFSET_TIME_DIGITIZED, 36882, 2), new d(TAG_COMPONENTS_CONFIGURATION, 37121, 7), new d(TAG_COMPRESSED_BITS_PER_PIXEL, 37122, 5), new d(TAG_SHUTTER_SPEED_VALUE, 37377, 10), new d(TAG_APERTURE_VALUE, 37378, 5), new d(TAG_BRIGHTNESS_VALUE, 37379, 10), new d(TAG_EXPOSURE_BIAS_VALUE, 37380, 10), new d(TAG_MAX_APERTURE_VALUE, 37381, 5), new d(TAG_SUBJECT_DISTANCE, 37382, 5), new d(TAG_METERING_MODE, 37383, 3), new d(TAG_LIGHT_SOURCE, 37384, 3), new d(TAG_FLASH, 37385, 3), new d(TAG_FOCAL_LENGTH, 37386, 5), new d(TAG_SUBJECT_AREA, 37396, 3), new d(TAG_MAKER_NOTE, 37500, 7), new d(TAG_USER_COMMENT, 37510, 7), new d(TAG_SUBSEC_TIME, 37520, 2), new d(TAG_SUBSEC_TIME_ORIGINAL, 37521, 2), new d(TAG_SUBSEC_TIME_DIGITIZED, 37522, 2), new d(TAG_FLASHPIX_VERSION, 40960, 7), new d(TAG_COLOR_SPACE, 40961, 3), new d(TAG_PIXEL_X_DIMENSION, 40962, 3, 4), new d(TAG_PIXEL_Y_DIMENSION, 40963, 3, 4), new d(TAG_RELATED_SOUND_FILE, 40964, 2), new d("InteroperabilityIFDPointer", 40965, 4), new d(TAG_FLASH_ENERGY, 41483, 5), new d(TAG_SPATIAL_FREQUENCY_RESPONSE, 41484, 7), new d(TAG_FOCAL_PLANE_X_RESOLUTION, 41486, 5), new d(TAG_FOCAL_PLANE_Y_RESOLUTION, 41487, 5), new d(TAG_FOCAL_PLANE_RESOLUTION_UNIT, 41488, 3), new d(TAG_SUBJECT_LOCATION, 41492, 3), new d(TAG_EXPOSURE_INDEX, 41493, 5), new d(TAG_SENSING_METHOD, 41495, 3), new d(TAG_FILE_SOURCE, 41728, 7), new d(TAG_SCENE_TYPE, 41729, 7), new d(TAG_CFA_PATTERN, 41730, 7), new d(TAG_CUSTOM_RENDERED, 41985, 3), new d(TAG_EXPOSURE_MODE, 41986, 3), new d(TAG_WHITE_BALANCE, 41987, 3), new d(TAG_DIGITAL_ZOOM_RATIO, 41988, 5), new d(TAG_FOCAL_LENGTH_IN_35MM_FILM, 41989, 3), new d(TAG_SCENE_CAPTURE_TYPE, 41990, 3), new d(TAG_GAIN_CONTROL, 41991, 3), new d(TAG_CONTRAST, 41992, 3), new d(TAG_SATURATION, 41993, 3), new d(TAG_SHARPNESS, 41994, 3), new d(TAG_DEVICE_SETTING_DESCRIPTION, 41995, 7), new d(TAG_SUBJECT_DISTANCE_RANGE, 41996, 3), new d(TAG_IMAGE_UNIQUE_ID, 42016, 2), new d("CameraOwnerName", 42032, 2), new d(TAG_BODY_SERIAL_NUMBER, 42033, 2), new d(TAG_LENS_SPECIFICATION, 42034, 5), new d(TAG_LENS_MAKE, 42035, 2), new d(TAG_LENS_MODEL, 42036, 2), new d(TAG_GAMMA, 42240, 5), new d(TAG_DNG_VERSION, 50706, 1), new d(TAG_DEFAULT_CROP_SIZE, 50720, 3, 4)};
    public static final String TAG_GPS_VERSION_ID = "GPSVersionID";
    public static final String TAG_GPS_LATITUDE_REF = "GPSLatitudeRef";
    public static final String TAG_GPS_LATITUDE = "GPSLatitude";
    public static final String TAG_GPS_LONGITUDE_REF = "GPSLongitudeRef";
    public static final String TAG_GPS_LONGITUDE = "GPSLongitude";
    public static final String TAG_GPS_ALTITUDE_REF = "GPSAltitudeRef";
    public static final String TAG_GPS_ALTITUDE = "GPSAltitude";
    public static final String TAG_GPS_TIMESTAMP = "GPSTimeStamp";
    public static final String TAG_GPS_SATELLITES = "GPSSatellites";
    public static final String TAG_GPS_STATUS = "GPSStatus";
    public static final String TAG_GPS_MEASURE_MODE = "GPSMeasureMode";
    public static final String TAG_GPS_DOP = "GPSDOP";
    public static final String TAG_GPS_SPEED_REF = "GPSSpeedRef";
    public static final String TAG_GPS_SPEED = "GPSSpeed";
    public static final String TAG_GPS_TRACK_REF = "GPSTrackRef";
    public static final String TAG_GPS_TRACK = "GPSTrack";
    public static final String TAG_GPS_IMG_DIRECTION_REF = "GPSImgDirectionRef";
    public static final String TAG_GPS_IMG_DIRECTION = "GPSImgDirection";
    public static final String TAG_GPS_MAP_DATUM = "GPSMapDatum";
    public static final String TAG_GPS_DEST_LATITUDE_REF = "GPSDestLatitudeRef";
    public static final String TAG_GPS_DEST_LATITUDE = "GPSDestLatitude";
    public static final String TAG_GPS_DEST_LONGITUDE_REF = "GPSDestLongitudeRef";
    public static final String TAG_GPS_DEST_LONGITUDE = "GPSDestLongitude";
    public static final String TAG_GPS_DEST_BEARING_REF = "GPSDestBearingRef";
    public static final String TAG_GPS_DEST_BEARING = "GPSDestBearing";
    public static final String TAG_GPS_DEST_DISTANCE_REF = "GPSDestDistanceRef";
    public static final String TAG_GPS_DEST_DISTANCE = "GPSDestDistance";
    public static final String TAG_GPS_PROCESSING_METHOD = "GPSProcessingMethod";
    public static final String TAG_GPS_AREA_INFORMATION = "GPSAreaInformation";
    public static final String TAG_GPS_DATESTAMP = "GPSDateStamp";
    public static final String TAG_GPS_DIFFERENTIAL = "GPSDifferential";
    public static final String TAG_GPS_H_POSITIONING_ERROR = "GPSHPositioningError";
    private static final d[] G = {new d(TAG_GPS_VERSION_ID, 0, 1), new d(TAG_GPS_LATITUDE_REF, 1, 2), new d(TAG_GPS_LATITUDE, 2, 5, 10), new d(TAG_GPS_LONGITUDE_REF, 3, 2), new d(TAG_GPS_LONGITUDE, 4, 5, 10), new d(TAG_GPS_ALTITUDE_REF, 5, 1), new d(TAG_GPS_ALTITUDE, 6, 5), new d(TAG_GPS_TIMESTAMP, 7, 5), new d(TAG_GPS_SATELLITES, 8, 2), new d(TAG_GPS_STATUS, 9, 2), new d(TAG_GPS_MEASURE_MODE, 10, 2), new d(TAG_GPS_DOP, 11, 5), new d(TAG_GPS_SPEED_REF, 12, 2), new d(TAG_GPS_SPEED, 13, 5), new d(TAG_GPS_TRACK_REF, 14, 2), new d(TAG_GPS_TRACK, 15, 5), new d(TAG_GPS_IMG_DIRECTION_REF, 16, 2), new d(TAG_GPS_IMG_DIRECTION, 17, 5), new d(TAG_GPS_MAP_DATUM, 18, 2), new d(TAG_GPS_DEST_LATITUDE_REF, 19, 2), new d(TAG_GPS_DEST_LATITUDE, 20, 5), new d(TAG_GPS_DEST_LONGITUDE_REF, 21, 2), new d(TAG_GPS_DEST_LONGITUDE, 22, 5), new d(TAG_GPS_DEST_BEARING_REF, 23, 2), new d(TAG_GPS_DEST_BEARING, 24, 5), new d(TAG_GPS_DEST_DISTANCE_REF, 25, 2), new d(TAG_GPS_DEST_DISTANCE, 26, 5), new d(TAG_GPS_PROCESSING_METHOD, 27, 7), new d(TAG_GPS_AREA_INFORMATION, 28, 7), new d(TAG_GPS_DATESTAMP, 29, 2), new d(TAG_GPS_DIFFERENTIAL, 30, 3), new d(TAG_GPS_H_POSITIONING_ERROR, 31, 5)};
    public static final String TAG_INTEROPERABILITY_INDEX = "InteroperabilityIndex";
    private static final d[] H = {new d(TAG_INTEROPERABILITY_INDEX, 1, 2)};
    public static final String TAG_THUMBNAIL_IMAGE_WIDTH = "ThumbnailImageWidth";
    public static final String TAG_THUMBNAIL_IMAGE_LENGTH = "ThumbnailImageLength";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String TAG_THUMBNAIL_ORIENTATION = "ThumbnailOrientation";
    private static final d[] I = {new d(TAG_NEW_SUBFILE_TYPE, DnsRecord.CLASS_NONE, 4), new d(TAG_SUBFILE_TYPE, 255, 4), new d(TAG_THUMBNAIL_IMAGE_WIDTH, 256, 3, 4), new d(TAG_THUMBNAIL_IMAGE_LENGTH, 257, 3, 4), new d(TAG_BITS_PER_SAMPLE, EndPoint.MIRROR_V1_MC_VERSION, 3), new d(TAG_COMPRESSION, 259, 3), new d(TAG_PHOTOMETRIC_INTERPRETATION, 262, 3), new d(TAG_IMAGE_DESCRIPTION, 270, 2), new d(TAG_MAKE, 271, 2), new d("Model", 272, 2), new d(TAG_STRIP_OFFSETS, 273, 3, 4), new d(TAG_THUMBNAIL_ORIENTATION, 274, 3), new d(TAG_SAMPLES_PER_PIXEL, 277, 3), new d(TAG_ROWS_PER_STRIP, 278, 3, 4), new d(TAG_STRIP_BYTE_COUNTS, 279, 3, 4), new d(TAG_X_RESOLUTION, 282, 5), new d(TAG_Y_RESOLUTION, 283, 5), new d(TAG_PLANAR_CONFIGURATION, 284, 3), new d(TAG_RESOLUTION_UNIT, 296, 3), new d(TAG_TRANSFER_FUNCTION, 301, 3), new d(TAG_SOFTWARE, 305, 2), new d(TAG_DATETIME, 306, 2), new d(TAG_ARTIST, 315, 2), new d(TAG_WHITE_POINT, 318, 5), new d(TAG_PRIMARY_CHROMATICITIES, 319, 5), new d("SubIFDPointer", 330, 4), new d(TAG_JPEG_INTERCHANGE_FORMAT, InputDeviceCompat.SOURCE_DPAD, 4), new d(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, 514, 4), new d(TAG_Y_CB_CR_COEFFICIENTS, 529, 5), new d(TAG_Y_CB_CR_SUB_SAMPLING, 530, 3), new d(TAG_Y_CB_CR_POSITIONING, 531, 3), new d(TAG_REFERENCE_BLACK_WHITE, 532, 5), new d(TAG_XMP, 700, 1), new d(TAG_COPYRIGHT, 33432, 2), new d("ExifIFDPointer", 34665, 4), new d("GPSInfoIFDPointer", 34853, 4), new d(TAG_DNG_VERSION, 50706, 1), new d(TAG_DEFAULT_CROP_SIZE, 50720, 3, 4)};
    private static final d J = new d(TAG_STRIP_OFFSETS, 273, 3);
    public static final String TAG_ORF_THUMBNAIL_IMAGE = "ThumbnailImage";
    private static final d[] K = {new d(TAG_ORF_THUMBNAIL_IMAGE, 256, 7), new d("CameraSettingsIFDPointer", 8224, 4), new d("ImageProcessingIFDPointer", 8256, 4)};
    public static final String TAG_ORF_PREVIEW_IMAGE_START = "PreviewImageStart";
    public static final String TAG_ORF_PREVIEW_IMAGE_LENGTH = "PreviewImageLength";
    private static final d[] L = {new d(TAG_ORF_PREVIEW_IMAGE_START, 257, 4), new d(TAG_ORF_PREVIEW_IMAGE_LENGTH, EndPoint.MIRROR_V1_MC_VERSION, 4)};
    public static final String TAG_ORF_ASPECT_FRAME = "AspectFrame";
    private static final d[] M = {new d(TAG_ORF_ASPECT_FRAME, 4371, 3)};
    private static final d[] N = {new d(TAG_COLOR_SPACE, 55, 3)};
    private static final d[] O = {new d("SubIFDPointer", 330, 4), new d("ExifIFDPointer", 34665, 4), new d("GPSInfoIFDPointer", 34853, 4), new d("InteroperabilityIFDPointer", 40965, 4), new d("CameraSettingsIFDPointer", 8224, 1), new d("ImageProcessingIFDPointer", 8256, 1)};
    private static final HashSet<String> R = new HashSet<>(Arrays.asList(TAG_F_NUMBER, TAG_DIGITAL_ZOOM_RATIO, TAG_EXPOSURE_TIME, TAG_SUBJECT_DISTANCE, TAG_GPS_TIMESTAMP));
    private static final HashMap<Integer, Integer> S = new HashMap<>();
    static final Charset f = Charset.forName("US-ASCII");
    static final byte[] g = "Exif\u0000\u0000".getBytes(f);
    private static final byte[] T = "http://ns.adobe.com/xap/1.0/\u0000".getBytes(f);
    private static SimpleDateFormat C = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss", Locale.US);
    private static SimpleDateFormat D = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    /* loaded from: classes.dex */
    public @interface ExifStreamType {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    /* loaded from: classes.dex */
    public @interface IfdType {
    }

    private static boolean a(int i2) {
        return (i2 == 4 || i2 == 9 || i2 == 13 || i2 == 14) ? false : true;
    }

    private static boolean b(int i2) {
        return i2 == 4 || i2 == 13 || i2 == 14 || i2 == 3 || i2 == 0;
    }

    static {
        d[] dVarArr = E;
        e = new d[][]{dVarArr, F, G, H, I, dVarArr, K, L, M, N};
        d[][] dVarArr2 = e;
        P = new HashMap[dVarArr2.length];
        Q = new HashMap[dVarArr2.length];
        C.setTimeZone(TimeZone.getTimeZone("UTC"));
        D.setTimeZone(TimeZone.getTimeZone("UTC"));
        for (int i2 = 0; i2 < e.length; i2++) {
            P[i2] = new HashMap<>();
            Q[i2] = new HashMap<>();
            d[] dVarArr3 = e[i2];
            for (d dVar : dVarArr3) {
                P[i2].put(Integer.valueOf(dVar.a), dVar);
                Q[i2].put(dVar.b, dVar);
            }
        }
        S.put(Integer.valueOf(O[0].a), 5);
        S.put(Integer.valueOf(O[1].a), 1);
        S.put(Integer.valueOf(O[2].a), 2);
        S.put(Integer.valueOf(O[3].a), 3);
        S.put(Integer.valueOf(O[4].a), 7);
        S.put(Integer.valueOf(O[5].a), 8);
        ap = Pattern.compile(".*[1-9].*");
        aq = Pattern.compile("^(\\d{2}):(\\d{2}):(\\d{2})$");
        ar = Pattern.compile("^(\\d{4}):(\\d{2}):(\\d{2})\\s(\\d{2}):(\\d{2}):(\\d{2})$");
        as = Pattern.compile("^(\\d{4})-(\\d{2})-(\\d{2})\\s(\\d{2}):(\\d{2}):(\\d{2})$");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class e {
        public final long a;
        public final long b;

        e(double d) {
            this((long) (d * 10000.0d), 10000L);
        }

        e(long j, long j2) {
            if (j2 == 0) {
                this.a = 0L;
                this.b = 1L;
                return;
            }
            this.a = j;
            this.b = j2;
        }

        public String toString() {
            return this.a + "/" + this.b;
        }

        public double a() {
            return this.a / this.b;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class c {
        public final int a;
        public final int b;
        public final long c;
        public final byte[] d;

        c(int i, int i2, byte[] bArr) {
            this(i, i2, -1L, bArr);
        }

        c(int i, int i2, long j, byte[] bArr) {
            this.a = i;
            this.b = i2;
            this.c = j;
            this.d = bArr;
        }

        public static c a(int[] iArr, ByteOrder byteOrder) {
            ByteBuffer wrap = ByteBuffer.wrap(new byte[ExifInterface.c[3] * iArr.length]);
            wrap.order(byteOrder);
            for (int i : iArr) {
                wrap.putShort((short) i);
            }
            return new c(3, iArr.length, wrap.array());
        }

        public static c a(int i, ByteOrder byteOrder) {
            return a(new int[]{i}, byteOrder);
        }

        public static c a(long[] jArr, ByteOrder byteOrder) {
            ByteBuffer wrap = ByteBuffer.wrap(new byte[ExifInterface.c[4] * jArr.length]);
            wrap.order(byteOrder);
            for (long j : jArr) {
                wrap.putInt((int) j);
            }
            return new c(4, jArr.length, wrap.array());
        }

        public static c a(long j, ByteOrder byteOrder) {
            return a(new long[]{j}, byteOrder);
        }

        public static c b(int[] iArr, ByteOrder byteOrder) {
            ByteBuffer wrap = ByteBuffer.wrap(new byte[ExifInterface.c[9] * iArr.length]);
            wrap.order(byteOrder);
            for (int i : iArr) {
                wrap.putInt(i);
            }
            return new c(9, iArr.length, wrap.array());
        }

        public static c a(String str) {
            if (str.length() != 1 || str.charAt(0) < '0' || str.charAt(0) > '1') {
                byte[] bytes = str.getBytes(ExifInterface.f);
                return new c(1, bytes.length, bytes);
            }
            byte[] bArr = {(byte) (str.charAt(0) - '0')};
            return new c(1, bArr.length, bArr);
        }

        public static c b(String str) {
            byte[] bytes = (str + (char) 0).getBytes(ExifInterface.f);
            return new c(2, bytes.length, bytes);
        }

        public static c a(e[] eVarArr, ByteOrder byteOrder) {
            ByteBuffer wrap = ByteBuffer.wrap(new byte[ExifInterface.c[5] * eVarArr.length]);
            wrap.order(byteOrder);
            for (e eVar : eVarArr) {
                wrap.putInt((int) eVar.a);
                wrap.putInt((int) eVar.b);
            }
            return new c(5, eVarArr.length, wrap.array());
        }

        public static c a(e eVar, ByteOrder byteOrder) {
            return a(new e[]{eVar}, byteOrder);
        }

        public static c b(e[] eVarArr, ByteOrder byteOrder) {
            ByteBuffer wrap = ByteBuffer.wrap(new byte[ExifInterface.c[10] * eVarArr.length]);
            wrap.order(byteOrder);
            for (e eVar : eVarArr) {
                wrap.putInt((int) eVar.a);
                wrap.putInt((int) eVar.b);
            }
            return new c(10, eVarArr.length, wrap.array());
        }

        public static c a(double[] dArr, ByteOrder byteOrder) {
            ByteBuffer wrap = ByteBuffer.wrap(new byte[ExifInterface.c[12] * dArr.length]);
            wrap.order(byteOrder);
            for (double d : dArr) {
                wrap.putDouble(d);
            }
            return new c(12, dArr.length, wrap.array());
        }

        public String toString() {
            return "(" + ExifInterface.b[this.a] + ", data length:" + this.d.length + ")";
        }

        Object a(ByteOrder byteOrder) {
            Throwable th;
            a aVar;
            IOException e;
            a aVar2;
            byte b;
            try {
                aVar = null;
            } catch (Throwable th2) {
                th = th2;
            }
            try {
                aVar2 = new a(this.d);
                try {
                    aVar2.a(byteOrder);
                    boolean z = true;
                    int i = 0;
                    switch (this.a) {
                        case 1:
                        case 6:
                            if (this.d.length != 1 || this.d[0] < 0 || this.d[0] > 1) {
                                String str = new String(this.d, ExifInterface.f);
                                try {
                                    aVar2.close();
                                } catch (IOException e2) {
                                    Log.e("ExifInterface", "IOException occurred while closing InputStream", e2);
                                }
                                return str;
                            }
                            String str2 = new String(new char[]{(char) (this.d[0] + 48)});
                            try {
                                aVar2.close();
                            } catch (IOException e3) {
                                Log.e("ExifInterface", "IOException occurred while closing InputStream", e3);
                            }
                            return str2;
                        case 2:
                        case 7:
                            if (this.b >= ExifInterface.d.length) {
                                int i2 = 0;
                                while (true) {
                                    if (i2 < ExifInterface.d.length) {
                                        if (this.d[i2] != ExifInterface.d[i2]) {
                                            z = false;
                                        } else {
                                            i2++;
                                        }
                                    }
                                }
                                if (z) {
                                    i = ExifInterface.d.length;
                                }
                            }
                            StringBuilder sb = new StringBuilder();
                            while (i < this.b && (b = this.d[i]) != 0) {
                                if (b >= 32) {
                                    sb.append((char) b);
                                } else {
                                    sb.append('?');
                                }
                                i++;
                            }
                            String sb2 = sb.toString();
                            try {
                                aVar2.close();
                            } catch (IOException e4) {
                                Log.e("ExifInterface", "IOException occurred while closing InputStream", e4);
                            }
                            return sb2;
                        case 3:
                            int[] iArr = new int[this.b];
                            while (i < this.b) {
                                iArr[i] = aVar2.readUnsignedShort();
                                i++;
                            }
                            try {
                                aVar2.close();
                            } catch (IOException e5) {
                                Log.e("ExifInterface", "IOException occurred while closing InputStream", e5);
                            }
                            return iArr;
                        case 4:
                            long[] jArr = new long[this.b];
                            while (i < this.b) {
                                jArr[i] = aVar2.b();
                                i++;
                            }
                            try {
                                aVar2.close();
                            } catch (IOException e6) {
                                Log.e("ExifInterface", "IOException occurred while closing InputStream", e6);
                            }
                            return jArr;
                        case 5:
                            e[] eVarArr = new e[this.b];
                            while (i < this.b) {
                                eVarArr[i] = new e(aVar2.b(), aVar2.b());
                                i++;
                            }
                            try {
                                aVar2.close();
                            } catch (IOException e7) {
                                Log.e("ExifInterface", "IOException occurred while closing InputStream", e7);
                            }
                            return eVarArr;
                        case 8:
                            int[] iArr2 = new int[this.b];
                            while (i < this.b) {
                                iArr2[i] = aVar2.readShort();
                                i++;
                            }
                            try {
                                aVar2.close();
                            } catch (IOException e8) {
                                Log.e("ExifInterface", "IOException occurred while closing InputStream", e8);
                            }
                            return iArr2;
                        case 9:
                            int[] iArr3 = new int[this.b];
                            while (i < this.b) {
                                iArr3[i] = aVar2.readInt();
                                i++;
                            }
                            try {
                                aVar2.close();
                            } catch (IOException e9) {
                                Log.e("ExifInterface", "IOException occurred while closing InputStream", e9);
                            }
                            return iArr3;
                        case 10:
                            e[] eVarArr2 = new e[this.b];
                            while (i < this.b) {
                                eVarArr2[i] = new e(aVar2.readInt(), aVar2.readInt());
                                i++;
                            }
                            try {
                                aVar2.close();
                            } catch (IOException e10) {
                                Log.e("ExifInterface", "IOException occurred while closing InputStream", e10);
                            }
                            return eVarArr2;
                        case 11:
                            double[] dArr = new double[this.b];
                            while (i < this.b) {
                                dArr[i] = aVar2.readFloat();
                                i++;
                            }
                            try {
                                aVar2.close();
                            } catch (IOException e11) {
                                Log.e("ExifInterface", "IOException occurred while closing InputStream", e11);
                            }
                            return dArr;
                        case 12:
                            double[] dArr2 = new double[this.b];
                            while (i < this.b) {
                                dArr2[i] = aVar2.readDouble();
                                i++;
                            }
                            try {
                                aVar2.close();
                            } catch (IOException e12) {
                                Log.e("ExifInterface", "IOException occurred while closing InputStream", e12);
                            }
                            return dArr2;
                        default:
                            try {
                                aVar2.close();
                            } catch (IOException e13) {
                                Log.e("ExifInterface", "IOException occurred while closing InputStream", e13);
                            }
                            return null;
                    }
                } catch (IOException e14) {
                    e = e14;
                    Log.w("ExifInterface", "IOException occurred during reading a value", e);
                    if (aVar2 != null) {
                        try {
                            aVar2.close();
                        } catch (IOException e15) {
                            Log.e("ExifInterface", "IOException occurred while closing InputStream", e15);
                        }
                    }
                    return null;
                }
            } catch (IOException e16) {
                e = e16;
                aVar2 = null;
            } catch (Throwable th3) {
                th = th3;
                if (0 != 0) {
                    try {
                        aVar.close();
                    } catch (IOException e17) {
                        Log.e("ExifInterface", "IOException occurred while closing InputStream", e17);
                    }
                }
                throw th;
            }
        }

        public double b(ByteOrder byteOrder) {
            Object a = a(byteOrder);
            if (a == null) {
                throw new NumberFormatException("NULL can't be converted to a double value");
            } else if (a instanceof String) {
                return Double.parseDouble((String) a);
            } else {
                if (a instanceof long[]) {
                    long[] jArr = (long[]) a;
                    if (jArr.length == 1) {
                        return jArr[0];
                    }
                    throw new NumberFormatException("There are more than one component");
                } else if (a instanceof int[]) {
                    int[] iArr = (int[]) a;
                    if (iArr.length == 1) {
                        return iArr[0];
                    }
                    throw new NumberFormatException("There are more than one component");
                } else if (a instanceof double[]) {
                    double[] dArr = (double[]) a;
                    if (dArr.length == 1) {
                        return dArr[0];
                    }
                    throw new NumberFormatException("There are more than one component");
                } else if (a instanceof e[]) {
                    e[] eVarArr = (e[]) a;
                    if (eVarArr.length == 1) {
                        return eVarArr[0].a();
                    }
                    throw new NumberFormatException("There are more than one component");
                } else {
                    throw new NumberFormatException("Couldn't find a double value");
                }
            }
        }

        public int c(ByteOrder byteOrder) {
            Object a = a(byteOrder);
            if (a == null) {
                throw new NumberFormatException("NULL can't be converted to a integer value");
            } else if (a instanceof String) {
                return Integer.parseInt((String) a);
            } else {
                if (a instanceof long[]) {
                    long[] jArr = (long[]) a;
                    if (jArr.length == 1) {
                        return (int) jArr[0];
                    }
                    throw new NumberFormatException("There are more than one component");
                } else if (a instanceof int[]) {
                    int[] iArr = (int[]) a;
                    if (iArr.length == 1) {
                        return iArr[0];
                    }
                    throw new NumberFormatException("There are more than one component");
                } else {
                    throw new NumberFormatException("Couldn't find a integer value");
                }
            }
        }

        public String d(ByteOrder byteOrder) {
            Object a = a(byteOrder);
            if (a == null) {
                return null;
            }
            if (a instanceof String) {
                return (String) a;
            }
            StringBuilder sb = new StringBuilder();
            int i = 0;
            if (a instanceof long[]) {
                long[] jArr = (long[]) a;
                while (i < jArr.length) {
                    sb.append(jArr[i]);
                    i++;
                    if (i != jArr.length) {
                        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
                    }
                }
                return sb.toString();
            } else if (a instanceof int[]) {
                int[] iArr = (int[]) a;
                while (i < iArr.length) {
                    sb.append(iArr[i]);
                    i++;
                    if (i != iArr.length) {
                        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
                    }
                }
                return sb.toString();
            } else if (a instanceof double[]) {
                double[] dArr = (double[]) a;
                while (i < dArr.length) {
                    sb.append(dArr[i]);
                    i++;
                    if (i != dArr.length) {
                        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
                    }
                }
                return sb.toString();
            } else if (!(a instanceof e[])) {
                return null;
            } else {
                e[] eVarArr = (e[]) a;
                while (i < eVarArr.length) {
                    sb.append(eVarArr[i].a);
                    sb.append(JsonPointer.SEPARATOR);
                    sb.append(eVarArr[i].b);
                    i++;
                    if (i != eVarArr.length) {
                        sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
                    }
                }
                return sb.toString();
            }
        }

        public int a() {
            return ExifInterface.c[this.a] * this.b;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class d {
        public final int a;
        public final String b;
        public final int c;
        public final int d;

        d(String str, int i, int i2) {
            this.b = str;
            this.a = i;
            this.c = i2;
            this.d = -1;
        }

        d(String str, int i, int i2, int i3) {
            this.b = str;
            this.a = i;
            this.c = i2;
            this.d = i3;
        }

        boolean a(int i) {
            int i2;
            int i3 = this.c;
            if (i3 == 7 || i == 7 || i3 == i || (i2 = this.d) == i) {
                return true;
            }
            if ((i3 == 4 || i2 == 4) && i == 3) {
                return true;
            }
            if ((this.c == 9 || this.d == 9) && i == 8) {
                return true;
            }
            return (this.c == 12 || this.d == 12) && i == 11;
        }
    }

    public ExifInterface(@NonNull File file) throws IOException {
        d[][] dVarArr = e;
        this.Z = new HashMap[dVarArr.length];
        this.aa = new HashSet(dVarArr.length);
        this.ab = ByteOrder.BIG_ENDIAN;
        if (file != null) {
            c(file.getAbsolutePath());
            return;
        }
        throw new NullPointerException("file cannot be null");
    }

    public ExifInterface(@NonNull String str) throws IOException {
        d[][] dVarArr = e;
        this.Z = new HashMap[dVarArr.length];
        this.aa = new HashSet(dVarArr.length);
        this.ab = ByteOrder.BIG_ENDIAN;
        if (str != null) {
            c(str);
            return;
        }
        throw new NullPointerException("filename cannot be null");
    }

    public ExifInterface(@NonNull FileDescriptor fileDescriptor) throws IOException {
        Throwable th;
        FileInputStream fileInputStream;
        d[][] dVarArr = e;
        this.Z = new HashMap[dVarArr.length];
        this.aa = new HashSet(dVarArr.length);
        this.ab = ByteOrder.BIG_ENDIAN;
        if (fileDescriptor != null) {
            FileInputStream fileInputStream2 = null;
            this.W = null;
            this.U = null;
            boolean z2 = false;
            if (Build.VERSION.SDK_INT < 21 || !a(fileDescriptor)) {
                this.V = null;
            } else {
                this.V = fileDescriptor;
                try {
                    fileDescriptor = a.C0016a.a(fileDescriptor);
                    z2 = true;
                } catch (Exception e2) {
                    throw new IOException("Failed to duplicate file descriptor", e2);
                }
            }
            try {
                fileInputStream = new FileInputStream(fileDescriptor);
            } catch (Throwable th2) {
                th = th2;
            }
            try {
                a(fileInputStream);
                a.a((Closeable) fileInputStream);
                if (z2) {
                    a.a(fileDescriptor);
                }
            } catch (Throwable th3) {
                th = th3;
                fileInputStream2 = fileInputStream;
                a.a((Closeable) fileInputStream2);
                if (z2) {
                    a.a(fileDescriptor);
                }
                throw th;
            }
        } else {
            throw new NullPointerException("fileDescriptor cannot be null");
        }
    }

    public ExifInterface(@NonNull InputStream inputStream) throws IOException {
        this(inputStream, 0);
    }

    public ExifInterface(@NonNull InputStream inputStream, int i2) throws IOException {
        d[][] dVarArr = e;
        this.Z = new HashMap[dVarArr.length];
        this.aa = new HashSet(dVarArr.length);
        this.ab = ByteOrder.BIG_ENDIAN;
        if (inputStream != null) {
            this.U = null;
            if (i2 == 1) {
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, g.length);
                if (!b(bufferedInputStream)) {
                    Log.w("ExifInterface", "Given data does not follow the structure of an Exif-only data.");
                    return;
                }
                this.Y = true;
                this.W = null;
                this.V = null;
                inputStream = bufferedInputStream;
            } else if (inputStream instanceof AssetManager.AssetInputStream) {
                this.W = (AssetManager.AssetInputStream) inputStream;
                this.V = null;
            } else {
                if (inputStream instanceof FileInputStream) {
                    FileInputStream fileInputStream = (FileInputStream) inputStream;
                    if (a(fileInputStream.getFD())) {
                        this.W = null;
                        this.V = fileInputStream.getFD();
                    }
                }
                this.W = null;
                this.V = null;
            }
            a(inputStream);
            return;
        }
        throw new NullPointerException("inputStream cannot be null");
    }

    public static boolean isSupportedMimeType(@NonNull String str) {
        if (str != null) {
            String lowerCase = str.toLowerCase(Locale.ROOT);
            char c2 = 65535;
            switch (lowerCase.hashCode()) {
                case -1875291391:
                    if (lowerCase.equals("image/x-fuji-raf")) {
                        c2 = '\n';
                        break;
                    }
                    break;
                case -1635437028:
                    if (lowerCase.equals("image/x-samsung-srw")) {
                        c2 = '\t';
                        break;
                    }
                    break;
                case -1594371159:
                    if (lowerCase.equals("image/x-sony-arw")) {
                        c2 = 5;
                        break;
                    }
                    break;
                case -1487464693:
                    if (lowerCase.equals("image/heic")) {
                        c2 = 11;
                        break;
                    }
                    break;
                case -1487464690:
                    if (lowerCase.equals("image/heif")) {
                        c2 = '\f';
                        break;
                    }
                    break;
                case -1487394660:
                    if (lowerCase.equals("image/jpeg")) {
                        c2 = 0;
                        break;
                    }
                    break;
                case -1487018032:
                    if (lowerCase.equals("image/webp")) {
                        c2 = 14;
                        break;
                    }
                    break;
                case -1423313290:
                    if (lowerCase.equals("image/x-adobe-dng")) {
                        c2 = 1;
                        break;
                    }
                    break;
                case -985160897:
                    if (lowerCase.equals("image/x-panasonic-rw2")) {
                        c2 = 6;
                        break;
                    }
                    break;
                case -879258763:
                    if (lowerCase.equals(DLNAProfiles.DLNAMimeTypes.MIME_IMAGE_PNG)) {
                        c2 = '\r';
                        break;
                    }
                    break;
                case -332763809:
                    if (lowerCase.equals("image/x-pentax-pef")) {
                        c2 = '\b';
                        break;
                    }
                    break;
                case 1378106698:
                    if (lowerCase.equals("image/x-olympus-orf")) {
                        c2 = 7;
                        break;
                    }
                    break;
                case 2099152104:
                    if (lowerCase.equals("image/x-nikon-nef")) {
                        c2 = 3;
                        break;
                    }
                    break;
                case 2099152524:
                    if (lowerCase.equals("image/x-nikon-nrw")) {
                        c2 = 4;
                        break;
                    }
                    break;
                case 2111234748:
                    if (lowerCase.equals("image/x-canon-cr2")) {
                        c2 = 2;
                        break;
                    }
                    break;
            }
            switch (c2) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case '\b':
                case '\t':
                case '\n':
                case 11:
                case '\f':
                case '\r':
                case 14:
                    return true;
                default:
                    return false;
            }
        } else {
            throw new NullPointerException("mimeType shouldn't be null");
        }
    }

    @Nullable
    private c a(@NonNull String str) {
        if (str != null) {
            if (TAG_ISO_SPEED_RATINGS.equals(str)) {
                if (h) {
                    Log.d("ExifInterface", "getExifAttribute: Replacing TAG_ISO_SPEED_RATINGS with TAG_PHOTOGRAPHIC_SENSITIVITY.");
                }
                str = TAG_PHOTOGRAPHIC_SENSITIVITY;
            }
            for (int i2 = 0; i2 < e.length; i2++) {
                c cVar = this.Z[i2].get(str);
                if (cVar != null) {
                    return cVar;
                }
            }
            return null;
        }
        throw new NullPointerException("tag shouldn't be null");
    }

    @Nullable
    public String getAttribute(@NonNull String str) {
        if (str != null) {
            c a2 = a(str);
            if (a2 == null) {
                return null;
            }
            if (!R.contains(str)) {
                return a2.d(this.ab);
            }
            if (!str.equals(TAG_GPS_TIMESTAMP)) {
                try {
                    return Double.toString(a2.b(this.ab));
                } catch (NumberFormatException unused) {
                    return null;
                }
            } else if (a2.a == 5 || a2.a == 10) {
                e[] eVarArr = (e[]) a2.a(this.ab);
                if (eVarArr != null && eVarArr.length == 3) {
                    return String.format("%02d:%02d:%02d", Integer.valueOf((int) (((float) eVarArr[0].a) / ((float) eVarArr[0].b))), Integer.valueOf((int) (((float) eVarArr[1].a) / ((float) eVarArr[1].b))), Integer.valueOf((int) (((float) eVarArr[2].a) / ((float) eVarArr[2].b))));
                }
                Log.w("ExifInterface", "Invalid GPS Timestamp array. array=" + Arrays.toString(eVarArr));
                return null;
            } else {
                Log.w("ExifInterface", "GPS Timestamp format is not rational. format=" + a2.a);
                return null;
            }
        } else {
            throw new NullPointerException("tag shouldn't be null");
        }
    }

    public int getAttributeInt(@NonNull String str, int i2) {
        if (str != null) {
            c a2 = a(str);
            if (a2 == null) {
                return i2;
            }
            try {
                return a2.c(this.ab);
            } catch (NumberFormatException unused) {
                return i2;
            }
        } else {
            throw new NullPointerException("tag shouldn't be null");
        }
    }

    public double getAttributeDouble(@NonNull String str, double d2) {
        if (str != null) {
            c a2 = a(str);
            if (a2 == null) {
                return d2;
            }
            try {
                return a2.b(this.ab);
            } catch (NumberFormatException unused) {
                return d2;
            }
        } else {
            throw new NullPointerException("tag shouldn't be null");
        }
    }

    public void setAttribute(@NonNull String str, @Nullable String str2) {
        int i2;
        String str3 = str;
        String str4 = str2;
        if (str3 != null) {
            if ((TAG_DATETIME.equals(str3) || TAG_DATETIME_ORIGINAL.equals(str3) || TAG_DATETIME_DIGITIZED.equals(str3)) && str4 != null) {
                boolean find = ar.matcher(str4).find();
                boolean find2 = as.matcher(str4).find();
                if (str2.length() != 19 || (!find && !find2)) {
                    Log.w("ExifInterface", "Invalid value for " + str3 + " : " + str4);
                    return;
                } else if (find2) {
                    str4 = str4.replaceAll(Constants.ACCEPT_TIME_SEPARATOR_SERVER, Constants.COLON_SEPARATOR);
                }
            }
            if (TAG_ISO_SPEED_RATINGS.equals(str3)) {
                if (h) {
                    Log.d("ExifInterface", "setAttribute: Replacing TAG_ISO_SPEED_RATINGS with TAG_PHOTOGRAPHIC_SENSITIVITY.");
                }
                str3 = TAG_PHOTOGRAPHIC_SENSITIVITY;
            }
            int i3 = 2;
            int i4 = 1;
            if (str4 != null && R.contains(str3)) {
                if (str3.equals(TAG_GPS_TIMESTAMP)) {
                    Matcher matcher = aq.matcher(str4);
                    if (!matcher.find()) {
                        Log.w("ExifInterface", "Invalid value for " + str3 + " : " + str4);
                        return;
                    }
                    str4 = Integer.parseInt(matcher.group(1)) + "/1," + Integer.parseInt(matcher.group(2)) + "/1," + Integer.parseInt(matcher.group(3)) + "/1";
                } else {
                    try {
                        str4 = new e(Double.parseDouble(str4)).toString();
                    } catch (NumberFormatException unused) {
                        Log.w("ExifInterface", "Invalid value for " + str3 + " : " + str4);
                        return;
                    }
                }
            }
            int i5 = 0;
            while (i5 < e.length) {
                if (i5 != 4 || this.ac) {
                    d dVar = Q[i5].get(str3);
                    if (dVar == null) {
                        i4 = i4;
                    } else if (str4 == null) {
                        this.Z[i5].remove(str3);
                        i4 = i4;
                    } else {
                        Pair<Integer, Integer> d2 = d(str4);
                        if (dVar.c == ((Integer) d2.first).intValue() || dVar.c == ((Integer) d2.second).intValue()) {
                            i2 = dVar.c;
                        } else if (dVar.d != -1 && (dVar.d == ((Integer) d2.first).intValue() || dVar.d == ((Integer) d2.second).intValue())) {
                            i2 = dVar.d;
                        } else if (dVar.c == i4 || dVar.c == 7 || dVar.c == i3) {
                            i2 = dVar.c;
                        } else if (h) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("Given tag (");
                            sb.append(str3);
                            sb.append(") value didn't match with one of expected formats: ");
                            sb.append(b[dVar.c]);
                            sb.append(dVar.d == -1 ? "" : ", " + b[dVar.d]);
                            sb.append(" (guess: ");
                            sb.append(b[((Integer) d2.first).intValue()]);
                            sb.append(((Integer) d2.second).intValue() == -1 ? "" : ", " + b[((Integer) d2.second).intValue()]);
                            sb.append(")");
                            Log.d("ExifInterface", sb.toString());
                            i4 = i4;
                        } else {
                            i4 = i4;
                        }
                        switch (i2) {
                            case 1:
                                i4 = i4;
                                this.Z[i5].put(str3, c.a(str4));
                                continue;
                            case 2:
                            case 7:
                                i4 = i4;
                                this.Z[i5].put(str3, c.b(str4));
                                continue;
                            case 3:
                                i4 = i4;
                                String[] split = str4.split(Constants.ACCEPT_TIME_SEPARATOR_SP, -1);
                                int[] iArr = new int[split.length];
                                for (int i6 = 0; i6 < split.length; i6++) {
                                    iArr[i6] = Integer.parseInt(split[i6]);
                                }
                                this.Z[i5].put(str3, c.a(iArr, this.ab));
                                continue;
                            case 4:
                                i4 = i4;
                                String[] split2 = str4.split(Constants.ACCEPT_TIME_SEPARATOR_SP, -1);
                                long[] jArr = new long[split2.length];
                                for (int i7 = 0; i7 < split2.length; i7++) {
                                    jArr[i7] = Long.parseLong(split2[i7]);
                                }
                                this.Z[i5].put(str3, c.a(jArr, this.ab));
                                continue;
                            case 5:
                                String[] split3 = str4.split(Constants.ACCEPT_TIME_SEPARATOR_SP, -1);
                                e[] eVarArr = new e[split3.length];
                                for (int i8 = 0; i8 < split3.length; i8++) {
                                    String[] split4 = split3[i8].split("/", -1);
                                    eVarArr[i8] = new e((long) Double.parseDouble(split4[0]), (long) Double.parseDouble(split4[1]));
                                }
                                i4 = 1;
                                this.Z[i5].put(str3, c.a(eVarArr, this.ab));
                                continue;
                            case 6:
                            case 8:
                            case 11:
                            default:
                                i4 = i4;
                                if (h) {
                                    Log.d("ExifInterface", "Data format isn't one of expected formats: " + i2);
                                    break;
                                } else {
                                    continue;
                                }
                            case 9:
                                String[] split5 = str4.split(Constants.ACCEPT_TIME_SEPARATOR_SP, -1);
                                int[] iArr2 = new int[split5.length];
                                for (int i9 = 0; i9 < split5.length; i9++) {
                                    iArr2[i9] = Integer.parseInt(split5[i9]);
                                }
                                this.Z[i5].put(str3, c.b(iArr2, this.ab));
                                i4 = 1;
                                continue;
                            case 10:
                                String[] split6 = str4.split(Constants.ACCEPT_TIME_SEPARATOR_SP, -1);
                                e[] eVarArr2 = new e[split6.length];
                                int i10 = 0;
                                while (i10 < split6.length) {
                                    String[] split7 = split6[i10].split("/", -1);
                                    eVarArr2[i10] = new e((long) Double.parseDouble(split7[0]), (long) Double.parseDouble(split7[i4]));
                                    i10++;
                                    i4 = 1;
                                }
                                this.Z[i5].put(str3, c.b(eVarArr2, this.ab));
                                i4 = 1;
                                continue;
                            case 12:
                                String[] split8 = str4.split(Constants.ACCEPT_TIME_SEPARATOR_SP, -1);
                                double[] dArr = new double[split8.length];
                                for (int i11 = 0; i11 < split8.length; i11++) {
                                    dArr[i11] = Double.parseDouble(split8[i11]);
                                }
                                this.Z[i5].put(str3, c.a(dArr, this.ab));
                                i4 = i4;
                                continue;
                        }
                    }
                } else {
                    i4 = i4;
                }
                i5++;
                i3 = 2;
            }
            return;
        }
        throw new NullPointerException("tag shouldn't be null");
    }

    public void resetOrientation() {
        setAttribute(TAG_ORIENTATION, Integer.toString(1));
    }

    public void rotate(int i2) {
        if (i2 % 90 == 0) {
            int attributeInt = getAttributeInt(TAG_ORIENTATION, 1);
            int i3 = 0;
            if (i.contains(Integer.valueOf(attributeInt))) {
                int indexOf = (i.indexOf(Integer.valueOf(attributeInt)) + (i2 / 90)) % 4;
                if (indexOf < 0) {
                    i3 = 4;
                }
                i3 = i.get(indexOf + i3).intValue();
            } else if (j.contains(Integer.valueOf(attributeInt))) {
                int indexOf2 = (j.indexOf(Integer.valueOf(attributeInt)) + (i2 / 90)) % 4;
                if (indexOf2 < 0) {
                    i3 = 4;
                }
                i3 = j.get(indexOf2 + i3).intValue();
            }
            setAttribute(TAG_ORIENTATION, Integer.toString(i3));
            return;
        }
        throw new IllegalArgumentException("degree should be a multiple of 90");
    }

    public void flipVertically() {
        int i2 = 1;
        switch (getAttributeInt(TAG_ORIENTATION, 1)) {
            case 1:
                i2 = 4;
                break;
            case 2:
                i2 = 3;
                break;
            case 3:
                i2 = 2;
                break;
            case 4:
                break;
            case 5:
                i2 = 8;
                break;
            case 6:
                i2 = 7;
                break;
            case 7:
                i2 = 6;
                break;
            case 8:
                i2 = 5;
                break;
            default:
                i2 = 0;
                break;
        }
        setAttribute(TAG_ORIENTATION, Integer.toString(i2));
    }

    public void flipHorizontally() {
        int i2 = 1;
        switch (getAttributeInt(TAG_ORIENTATION, 1)) {
            case 1:
                i2 = 2;
                break;
            case 2:
                break;
            case 3:
                i2 = 4;
                break;
            case 4:
                i2 = 3;
                break;
            case 5:
                i2 = 6;
                break;
            case 6:
                i2 = 5;
                break;
            case 7:
                i2 = 8;
                break;
            case 8:
                i2 = 7;
                break;
            default:
                i2 = 0;
                break;
        }
        setAttribute(TAG_ORIENTATION, Integer.toString(i2));
    }

    public boolean isFlipped() {
        int attributeInt = getAttributeInt(TAG_ORIENTATION, 1);
        if (!(attributeInt == 2 || attributeInt == 7)) {
            switch (attributeInt) {
                case 4:
                case 5:
                    break;
                default:
                    return false;
            }
        }
        return true;
    }

    public int getRotationDegrees() {
        switch (getAttributeInt(TAG_ORIENTATION, 1)) {
            case 3:
            case 4:
                return Opcodes.GETFIELD;
            case 5:
            case 8:
                return 270;
            case 6:
            case 7:
                return 90;
            default:
                return 0;
        }
    }

    private void b(String str) {
        for (int i2 = 0; i2 < e.length; i2++) {
            this.Z[i2].remove(str);
        }
    }

    private void a(@NonNull InputStream inputStream) {
        boolean z2;
        if (inputStream != null) {
            for (int i2 = 0; i2 < e.length; i2++) {
                try {
                    try {
                        this.Z[i2] = new HashMap<>();
                    } finally {
                        b();
                        if (h) {
                            a();
                        }
                    }
                } catch (IOException | UnsupportedOperationException e2) {
                    if (h) {
                        Log.w("ExifInterface", "Invalid image: ExifInterface got an unsupported image format file(ExifInterface supports JPEG and some RAW image formats only) or a corrupted JPEG file to ExifInterface.", e2);
                    }
                    if (!z2) {
                        return;
                    }
                }
            }
            if (!this.Y) {
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, 5000);
                this.X = a(bufferedInputStream);
                inputStream = bufferedInputStream;
            }
            if (a(this.X)) {
                f fVar = new f(inputStream);
                if (this.Y) {
                    c(fVar);
                } else if (this.X == 12) {
                    b(fVar);
                } else if (this.X == 7) {
                    d(fVar);
                } else if (this.X == 10) {
                    e(fVar);
                } else {
                    a(fVar);
                }
                fVar.a(this.aj);
                f(fVar);
            } else {
                a aVar = new a(inputStream);
                if (this.X == 4) {
                    a(aVar, 0, 0);
                } else if (this.X == 13) {
                    b(aVar);
                } else if (this.X == 9) {
                    a(aVar);
                } else if (this.X == 14) {
                    c(aVar);
                }
            }
            b();
            if (!h) {
                return;
            }
            return;
        }
        throw new NullPointerException("inputstream shouldn't be null");
    }

    private static boolean a(FileDescriptor fileDescriptor) {
        if (Build.VERSION.SDK_INT < 21) {
            return false;
        }
        try {
            a.C0016a.a(fileDescriptor, 0L, OsConstants.SEEK_CUR);
            return true;
        } catch (Exception unused) {
            if (h) {
                Log.d("ExifInterface", "The file descriptor for the given input is not seekable");
            }
            return false;
        }
    }

    private void a() {
        for (int i2 = 0; i2 < this.Z.length; i2++) {
            Log.d("ExifInterface", "The size of tag group[" + i2 + "]: " + this.Z[i2].size());
            for (Map.Entry<String, c> entry : this.Z[i2].entrySet()) {
                c value = entry.getValue();
                Log.d("ExifInterface", "tagName: " + entry.getKey() + ", tagType: " + value.toString() + ", tagValue: '" + value.d(this.ab) + LrcRow.SINGLE_QUOTE);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:73:0x0109 A[Catch: Exception -> 0x0139, all -> 0x0136, TryCatch #17 {Exception -> 0x0139, all -> 0x0136, blocks: (B:71:0x0105, B:73:0x0109, B:75:0x010d, B:76:0x011d, B:77:0x0125), top: B:133:0x0105 }] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x011d A[Catch: Exception -> 0x0139, all -> 0x0136, TryCatch #17 {Exception -> 0x0139, all -> 0x0136, blocks: (B:71:0x0105, B:73:0x0109, B:75:0x010d, B:76:0x011d, B:77:0x0125), top: B:133:0x0105 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void saveAttributes() throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 411
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.exifinterface.media.ExifInterface.saveAttributes():void");
    }

    public boolean hasThumbnail() {
        return this.ac;
    }

    public boolean hasAttribute(@NonNull String str) {
        return a(str) != null;
    }

    @Nullable
    public byte[] getThumbnail() {
        int i2 = this.ai;
        if (i2 == 6 || i2 == 7) {
            return getThumbnailBytes();
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00c1  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00cb  */
    /* JADX WARN: Type inference failed for: r0v1, types: [byte[]] */
    /* JADX WARN: Type inference failed for: r0v14 */
    /* JADX WARN: Type inference failed for: r0v17 */
    /* JADX WARN: Type inference failed for: r0v19 */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Unknown variable types count: 1 */
    @androidx.annotation.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public byte[] getThumbnailBytes() {
        /*
            Method dump skipped, instructions count: 207
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.exifinterface.media.ExifInterface.getThumbnailBytes():byte[]");
    }

    @Nullable
    public Bitmap getThumbnailBitmap() {
        if (!this.ac) {
            return null;
        }
        if (this.ah == null) {
            this.ah = getThumbnailBytes();
        }
        int i2 = this.ai;
        if (i2 == 6 || i2 == 7) {
            return BitmapFactory.decodeByteArray(this.ah, 0, this.ag);
        }
        if (i2 == 1) {
            int[] iArr = new int[this.ah.length / 3];
            for (int i3 = 0; i3 < iArr.length; i3++) {
                byte[] bArr = this.ah;
                int i4 = i3 * 3;
                iArr[i3] = (bArr[i4] << 16) + 0 + (bArr[i4 + 1] << 8) + bArr[i4 + 2];
            }
            c cVar = this.Z[4].get(TAG_THUMBNAIL_IMAGE_LENGTH);
            c cVar2 = this.Z[4].get(TAG_THUMBNAIL_IMAGE_WIDTH);
            if (!(cVar == null || cVar2 == null)) {
                return Bitmap.createBitmap(iArr, cVar2.c(this.ab), cVar.c(this.ab), Bitmap.Config.ARGB_8888);
            }
        }
        return null;
    }

    public boolean isThumbnailCompressed() {
        if (!this.ac) {
            return false;
        }
        int i2 = this.ai;
        return i2 == 6 || i2 == 7;
    }

    @Nullable
    public long[] getThumbnailRange() {
        if (this.an) {
            throw new IllegalStateException("The underlying file has been modified since being parsed");
        } else if (!this.ac) {
            return null;
        } else {
            if (!this.ad || this.ae) {
                return new long[]{this.af + this.aj, this.ag};
            }
            return null;
        }
    }

    @Nullable
    public long[] getAttributeRange(@NonNull String str) {
        if (str == null) {
            throw new NullPointerException("tag shouldn't be null");
        } else if (!this.an) {
            c a2 = a(str);
            if (a2 != null) {
                return new long[]{a2.c, a2.d.length};
            }
            return null;
        } else {
            throw new IllegalStateException("The underlying file has been modified since being parsed");
        }
    }

    @Nullable
    public byte[] getAttributeBytes(@NonNull String str) {
        if (str != null) {
            c a2 = a(str);
            if (a2 != null) {
                return a2.d;
            }
            return null;
        }
        throw new NullPointerException("tag shouldn't be null");
    }

    @Deprecated
    public boolean getLatLong(float[] fArr) {
        double[] latLong = getLatLong();
        if (latLong == null) {
            return false;
        }
        fArr[0] = (float) latLong[0];
        fArr[1] = (float) latLong[1];
        return true;
    }

    @Nullable
    public double[] getLatLong() {
        String attribute = getAttribute(TAG_GPS_LATITUDE);
        String attribute2 = getAttribute(TAG_GPS_LATITUDE_REF);
        String attribute3 = getAttribute(TAG_GPS_LONGITUDE);
        String attribute4 = getAttribute(TAG_GPS_LONGITUDE_REF);
        if (attribute == null || attribute2 == null || attribute3 == null || attribute4 == null) {
            return null;
        }
        try {
            return new double[]{a(attribute, attribute2), a(attribute3, attribute4)};
        } catch (IllegalArgumentException unused) {
            Log.w("ExifInterface", "Latitude/longitude values are not parsable. " + String.format("latValue=%s, latRef=%s, lngValue=%s, lngRef=%s", attribute, attribute2, attribute3, attribute4));
            return null;
        }
    }

    public void setGpsInfo(Location location) {
        if (location != null) {
            setAttribute(TAG_GPS_PROCESSING_METHOD, location.getProvider());
            setLatLong(location.getLatitude(), location.getLongitude());
            setAltitude(location.getAltitude());
            setAttribute(TAG_GPS_SPEED_REF, "K");
            setAttribute(TAG_GPS_SPEED, new e((location.getSpeed() * ((float) TimeUnit.HOURS.toSeconds(1L))) / 1000.0f).toString());
            String[] split = C.format(new Date(location.getTime())).split("\\s+", -1);
            setAttribute(TAG_GPS_DATESTAMP, split[0]);
            setAttribute(TAG_GPS_TIMESTAMP, split[1]);
        }
    }

    public void setLatLong(double d2, double d3) {
        if (d2 < -90.0d || d2 > 90.0d || Double.isNaN(d2)) {
            throw new IllegalArgumentException("Latitude value " + d2 + " is not valid.");
        } else if (d3 < -180.0d || d3 > 180.0d || Double.isNaN(d3)) {
            throw new IllegalArgumentException("Longitude value " + d3 + " is not valid.");
        } else {
            setAttribute(TAG_GPS_LATITUDE_REF, d2 >= 0.0d ? "N" : LATITUDE_SOUTH);
            setAttribute(TAG_GPS_LATITUDE, a(Math.abs(d2)));
            setAttribute(TAG_GPS_LONGITUDE_REF, d3 >= 0.0d ? "E" : LONGITUDE_WEST);
            setAttribute(TAG_GPS_LONGITUDE, a(Math.abs(d3)));
        }
    }

    public double getAltitude(double d2) {
        double attributeDouble = getAttributeDouble(TAG_GPS_ALTITUDE, -1.0d);
        int attributeInt = getAttributeInt(TAG_GPS_ALTITUDE_REF, -1);
        if (attributeDouble < 0.0d || attributeInt < 0) {
            return d2;
        }
        int i2 = 1;
        if (attributeInt == 1) {
            i2 = -1;
        }
        return attributeDouble * i2;
    }

    public void setAltitude(double d2) {
        String str = d2 >= 0.0d ? "0" : "1";
        setAttribute(TAG_GPS_ALTITUDE, new e(Math.abs(d2)).toString());
        setAttribute(TAG_GPS_ALTITUDE_REF, str);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public void setDateTime(@NonNull Long l2) {
        if (l2 == null) {
            throw new NullPointerException("Timestamp should not be null.");
        } else if (l2.longValue() >= 0) {
            String l3 = Long.toString(l2.longValue() % 1000);
            for (int length = l3.length(); length < 3; length++) {
                l3 = "0" + l3;
            }
            setAttribute(TAG_DATETIME, C.format(new Date(l2.longValue())));
            setAttribute(TAG_SUBSEC_TIME, l3);
        } else {
            throw new IllegalArgumentException("Timestamp should a positive value.");
        }
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public Long getDateTime() {
        return a(getAttribute(TAG_DATETIME), getAttribute(TAG_SUBSEC_TIME), getAttribute(TAG_OFFSET_TIME));
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public Long getDateTimeDigitized() {
        return a(getAttribute(TAG_DATETIME_DIGITIZED), getAttribute(TAG_SUBSEC_TIME_DIGITIZED), getAttribute(TAG_OFFSET_TIME_DIGITIZED));
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public Long getDateTimeOriginal() {
        return a(getAttribute(TAG_DATETIME_ORIGINAL), getAttribute(TAG_SUBSEC_TIME_ORIGINAL), getAttribute(TAG_OFFSET_TIME_ORIGINAL));
    }

    private static Long a(@Nullable String str, @Nullable String str2, @Nullable String str3) {
        if (str == null || !ap.matcher(str).matches()) {
            return null;
        }
        ParsePosition parsePosition = new ParsePosition(0);
        try {
            Date parse = C.parse(str, parsePosition);
            if (parse == null && (parse = D.parse(str, parsePosition)) == null) {
                return null;
            }
            long time = parse.getTime();
            if (str3 != null) {
                int i2 = 1;
                String substring = str3.substring(0, 1);
                int parseInt = Integer.parseInt(str3.substring(1, 3));
                int parseInt2 = Integer.parseInt(str3.substring(4, 6));
                if ((Marker.ANY_NON_NULL_MARKER.equals(substring) || Constants.ACCEPT_TIME_SEPARATOR_SERVER.equals(substring)) && Constants.COLON_SEPARATOR.equals(str3.substring(3, 4)) && parseInt <= 14) {
                    int i3 = ((parseInt * 60) + parseInt2) * 60 * 1000;
                    if (!Constants.ACCEPT_TIME_SEPARATOR_SERVER.equals(substring)) {
                        i2 = -1;
                    }
                    time += i3 * i2;
                }
            }
            if (str2 != null) {
                time += a.a(str2);
            }
            return Long.valueOf(time);
        } catch (IllegalArgumentException unused) {
            return null;
        }
    }

    @Nullable
    @SuppressLint({"AutoBoxing"})
    public Long getGpsDateTime() {
        String attribute = getAttribute(TAG_GPS_DATESTAMP);
        String attribute2 = getAttribute(TAG_GPS_TIMESTAMP);
        if (attribute == null || attribute2 == null || (!ap.matcher(attribute).matches() && !ap.matcher(attribute2).matches())) {
            return null;
        }
        String str = attribute + ' ' + attribute2;
        ParsePosition parsePosition = new ParsePosition(0);
        try {
            Date parse = C.parse(str, parsePosition);
            if (parse == null && (parse = D.parse(str, parsePosition)) == null) {
                return null;
            }
            return Long.valueOf(parse.getTime());
        } catch (IllegalArgumentException unused) {
            return null;
        }
    }

    private void c(String str) throws IOException {
        Throwable th;
        FileInputStream fileInputStream;
        if (str != null) {
            this.W = null;
            this.U = str;
            try {
                fileInputStream = new FileInputStream(str);
                try {
                    if (a(fileInputStream.getFD())) {
                        this.V = fileInputStream.getFD();
                    } else {
                        this.V = null;
                    }
                    a(fileInputStream);
                    a.a((Closeable) fileInputStream);
                } catch (Throwable th2) {
                    th = th2;
                    a.a((Closeable) fileInputStream);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                fileInputStream = null;
            }
        } else {
            throw new NullPointerException("filename cannot be null");
        }
    }

    private static double a(String str, String str2) {
        try {
            String[] split = str.split(Constants.ACCEPT_TIME_SEPARATOR_SP, -1);
            String[] split2 = split[0].split("/", -1);
            double parseDouble = Double.parseDouble(split2[0].trim()) / Double.parseDouble(split2[1].trim());
            String[] split3 = split[1].split("/", -1);
            double parseDouble2 = Double.parseDouble(split3[0].trim()) / Double.parseDouble(split3[1].trim());
            String[] split4 = split[2].split("/", -1);
            double parseDouble3 = parseDouble + (parseDouble2 / 60.0d) + ((Double.parseDouble(split4[0].trim()) / Double.parseDouble(split4[1].trim())) / 3600.0d);
            if (!str2.equals(LATITUDE_SOUTH) && !str2.equals(LONGITUDE_WEST)) {
                if (!str2.equals("N") && !str2.equals("E")) {
                    throw new IllegalArgumentException();
                }
                return parseDouble3;
            }
            return -parseDouble3;
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException unused) {
            throw new IllegalArgumentException();
        }
    }

    private String a(double d2) {
        long j2 = (long) d2;
        double d3 = d2 - j2;
        long j3 = (long) (d3 * 60.0d);
        long round = Math.round((d3 - (j3 / 60.0d)) * 3600.0d * 1.0E7d);
        return j2 + "/1," + j3 + "/1," + round + "/10000000";
    }

    private int a(BufferedInputStream bufferedInputStream) throws IOException {
        bufferedInputStream.mark(5000);
        byte[] bArr = new byte[5000];
        bufferedInputStream.read(bArr);
        bufferedInputStream.reset();
        if (a(bArr)) {
            return 4;
        }
        if (b(bArr)) {
            return 9;
        }
        if (c(bArr)) {
            return 12;
        }
        if (d(bArr)) {
            return 7;
        }
        if (e(bArr)) {
            return 10;
        }
        if (f(bArr)) {
            return 13;
        }
        return g(bArr) ? 14 : 0;
    }

    private static boolean a(byte[] bArr) throws IOException {
        int i2 = 0;
        while (true) {
            byte[] bArr2 = a;
            if (i2 >= bArr2.length) {
                return true;
            }
            if (bArr[i2] != bArr2[i2]) {
                return false;
            }
            i2++;
        }
    }

    private boolean b(byte[] bArr) throws IOException {
        byte[] bytes = "FUJIFILMCCD-RAW".getBytes(Charset.defaultCharset());
        for (int i2 = 0; i2 < bytes.length; i2++) {
            if (bArr[i2] != bytes[i2]) {
                return false;
            }
        }
        return true;
    }

    private boolean c(byte[] bArr) throws IOException {
        Throwable th;
        Exception e2;
        long readInt;
        byte[] bArr2;
        a aVar = null;
        try {
            try {
                aVar = new a(bArr);
            } catch (Exception e3) {
                e2 = e3;
            }
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            readInt = aVar.readInt();
            bArr2 = new byte[4];
            aVar.read(bArr2);
        } catch (Exception e4) {
            e2 = e4;
            aVar = aVar;
            if (h) {
                Log.d("ExifInterface", "Exception parsing HEIF file type box.", e2);
            }
            if (aVar != null) {
                aVar.close();
            }
            return false;
        } catch (Throwable th3) {
            th = th3;
            if (aVar != null) {
                aVar.close();
            }
            throw th;
        }
        if (!Arrays.equals(bArr2, k)) {
            aVar.close();
            return false;
        }
        long j2 = 16;
        if (readInt == 1) {
            readInt = aVar.readLong();
            if (readInt < 16) {
                aVar.close();
                return false;
            }
        } else {
            j2 = 8;
        }
        if (readInt > bArr.length) {
            readInt = bArr.length;
        }
        long j3 = readInt - j2;
        if (j3 < 8) {
            aVar.close();
            return false;
        }
        byte[] bArr3 = new byte[4];
        boolean z2 = false;
        boolean z3 = false;
        for (long j4 = 0; j4 < j3 / 4; j4++) {
            if (aVar.read(bArr3) != bArr3.length) {
                aVar.close();
                return false;
            }
            if (j4 != 1) {
                if (Arrays.equals(bArr3, l)) {
                    z2 = true;
                } else if (Arrays.equals(bArr3, m)) {
                    z3 = true;
                }
                if (z2 && z3) {
                    aVar.close();
                    return true;
                }
            }
        }
        aVar.close();
        return false;
    }

    private boolean d(byte[] bArr) throws IOException {
        a aVar;
        Throwable th;
        boolean z2 = false;
        try {
            aVar = new a(bArr);
            try {
                this.ab = d(aVar);
                aVar.a(this.ab);
                short readShort = aVar.readShort();
                if (readShort == 20306 || readShort == 21330) {
                    z2 = true;
                }
                aVar.close();
                return z2;
            } catch (Exception unused) {
                if (aVar != null) {
                    aVar.close();
                }
                return false;
            } catch (Throwable th2) {
                th = th2;
                if (aVar != null) {
                    aVar.close();
                }
                throw th;
            }
        } catch (Exception unused2) {
            aVar = null;
        } catch (Throwable th3) {
            th = th3;
            aVar = null;
        }
    }

    private boolean e(byte[] bArr) throws IOException {
        a aVar;
        Throwable th;
        boolean z2 = false;
        try {
            aVar = new a(bArr);
        } catch (Exception unused) {
            aVar = null;
        } catch (Throwable th2) {
            th = th2;
            aVar = null;
        }
        try {
            this.ab = d(aVar);
            aVar.a(this.ab);
            if (aVar.readShort() == 85) {
                z2 = true;
            }
            aVar.close();
            return z2;
        } catch (Exception unused2) {
            if (aVar != null) {
                aVar.close();
            }
            return false;
        } catch (Throwable th3) {
            th = th3;
            if (aVar != null) {
                aVar.close();
            }
            throw th;
        }
    }

    private boolean f(byte[] bArr) throws IOException {
        int i2 = 0;
        while (true) {
            byte[] bArr2 = p;
            if (i2 >= bArr2.length) {
                return true;
            }
            if (bArr[i2] != bArr2[i2]) {
                return false;
            }
            i2++;
        }
    }

    private boolean g(byte[] bArr) throws IOException {
        int i2 = 0;
        while (true) {
            byte[] bArr2 = t;
            if (i2 >= bArr2.length) {
                int i3 = 0;
                while (true) {
                    byte[] bArr3 = u;
                    if (i3 >= bArr3.length) {
                        return true;
                    }
                    if (bArr[t.length + i3 + 4] != bArr3[i3]) {
                        return false;
                    }
                    i3++;
                }
            } else if (bArr[i2] != bArr2[i2]) {
                return false;
            } else {
                i2++;
            }
        }
    }

    private static boolean b(BufferedInputStream bufferedInputStream) throws IOException {
        bufferedInputStream.mark(g.length);
        byte[] bArr = new byte[g.length];
        bufferedInputStream.read(bArr);
        bufferedInputStream.reset();
        int i2 = 0;
        while (true) {
            byte[] bArr2 = g;
            if (i2 >= bArr2.length) {
                return true;
            }
            if (bArr[i2] != bArr2[i2]) {
                return false;
            }
            i2++;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:63:0x0199, code lost:
        r19.a(r18.ab);
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x019e, code lost:
        return;
     */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00ba A[FALL_THROUGH] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0181 A[LOOP:0: B:10:0x0036->B:58:0x0181, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0189 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void a(androidx.exifinterface.media.ExifInterface.a r19, int r20, int r21) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 544
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.exifinterface.media.ExifInterface.a(androidx.exifinterface.media.ExifInterface$a, int, int):void");
    }

    private void a(f fVar) throws IOException {
        c cVar;
        e((a) fVar);
        a(fVar, 0);
        c(fVar, 0);
        c(fVar, 5);
        c(fVar, 4);
        c();
        if (this.X == 8 && (cVar = this.Z[1].get(TAG_MAKER_NOTE)) != null) {
            f fVar2 = new f(cVar.d);
            fVar2.a(this.ab);
            fVar2.a(6);
            a(fVar2, 9);
            c cVar2 = this.Z[9].get(TAG_COLOR_SPACE);
            if (cVar2 != null) {
                this.Z[1].put(TAG_COLOR_SPACE, cVar2);
            }
        }
    }

    private void a(a aVar) throws IOException {
        if (h) {
            Log.d("ExifInterface", "getRafAttributes starting with: " + aVar);
        }
        aVar.a(84);
        byte[] bArr = new byte[4];
        byte[] bArr2 = new byte[4];
        byte[] bArr3 = new byte[4];
        aVar.read(bArr);
        aVar.read(bArr2);
        aVar.read(bArr3);
        int i2 = ByteBuffer.wrap(bArr).getInt();
        int i3 = ByteBuffer.wrap(bArr2).getInt();
        int i4 = ByteBuffer.wrap(bArr3).getInt();
        byte[] bArr4 = new byte[i3];
        aVar.a(i2 - aVar.a());
        aVar.read(bArr4);
        a(new a(bArr4), i2, 5);
        aVar.a(i4 - aVar.a());
        aVar.a(ByteOrder.BIG_ENDIAN);
        int readInt = aVar.readInt();
        if (h) {
            Log.d("ExifInterface", "numberOfDirectoryEntry: " + readInt);
        }
        for (int i5 = 0; i5 < readInt; i5++) {
            int readUnsignedShort = aVar.readUnsignedShort();
            int readUnsignedShort2 = aVar.readUnsignedShort();
            if (readUnsignedShort == J.a) {
                short readShort = aVar.readShort();
                short readShort2 = aVar.readShort();
                c a2 = c.a((int) readShort, this.ab);
                c a3 = c.a((int) readShort2, this.ab);
                this.Z[0].put(TAG_IMAGE_LENGTH, a2);
                this.Z[0].put(TAG_IMAGE_WIDTH, a3);
                if (h) {
                    Log.d("ExifInterface", "Updated to length: " + ((int) readShort) + ", width: " + ((int) readShort2));
                    return;
                }
                return;
            }
            aVar.a(readUnsignedShort2);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [int] */
    /* JADX WARN: Type inference failed for: r0v2, types: [android.media.MediaMetadataRetriever] */
    private void b(final f fVar) throws IOException {
        String str;
        String str2;
        MediaMetadataRetriever mediaMetadataRetriever = Build.VERSION.SDK_INT;
        if (mediaMetadataRetriever >= 28) {
            try {
                mediaMetadataRetriever = new MediaMetadataRetriever();
                try {
                    a.b.a(mediaMetadataRetriever, new MediaDataSource() { // from class: androidx.exifinterface.media.ExifInterface.1
                        long a;

                        @Override // java.io.Closeable, java.lang.AutoCloseable
                        public void close() throws IOException {
                        }

                        @Override // android.media.MediaDataSource
                        public long getSize() throws IOException {
                            return -1L;
                        }

                        @Override // android.media.MediaDataSource
                        public int readAt(long j2, byte[] bArr, int i2, int i3) throws IOException {
                            if (i3 == 0) {
                                return 0;
                            }
                            if (j2 < 0) {
                                return -1;
                            }
                            try {
                                if (this.a != j2) {
                                    if (this.a >= 0 && j2 >= this.a + fVar.available()) {
                                        return -1;
                                    }
                                    fVar.a(j2);
                                    this.a = j2;
                                }
                                if (i3 > fVar.available()) {
                                    i3 = fVar.available();
                                }
                                int read = fVar.read(bArr, i2, i3);
                                if (read >= 0) {
                                    this.a += read;
                                    return read;
                                }
                            } catch (IOException unused) {
                            }
                            this.a = -1L;
                            return -1;
                        }
                    });
                    String extractMetadata = mediaMetadataRetriever.extractMetadata(33);
                    String extractMetadata2 = mediaMetadataRetriever.extractMetadata(34);
                    String extractMetadata3 = mediaMetadataRetriever.extractMetadata(26);
                    String extractMetadata4 = mediaMetadataRetriever.extractMetadata(17);
                    String str3 = null;
                    if ("yes".equals(extractMetadata3)) {
                        str3 = mediaMetadataRetriever.extractMetadata(29);
                        str2 = mediaMetadataRetriever.extractMetadata(30);
                        str = mediaMetadataRetriever.extractMetadata(31);
                    } else if ("yes".equals(extractMetadata4)) {
                        str3 = mediaMetadataRetriever.extractMetadata(18);
                        str2 = mediaMetadataRetriever.extractMetadata(19);
                        str = mediaMetadataRetriever.extractMetadata(24);
                    } else {
                        str2 = null;
                        str = null;
                    }
                    if (str3 != null) {
                        this.Z[0].put(TAG_IMAGE_WIDTH, c.a(Integer.parseInt(str3), this.ab));
                    }
                    if (str2 != null) {
                        this.Z[0].put(TAG_IMAGE_LENGTH, c.a(Integer.parseInt(str2), this.ab));
                    }
                    if (str != null) {
                        int i2 = 1;
                        int parseInt = Integer.parseInt(str);
                        if (parseInt == 90) {
                            i2 = 6;
                        } else if (parseInt == 180) {
                            i2 = 3;
                        } else if (parseInt == 270) {
                            i2 = 8;
                        }
                        this.Z[0].put(TAG_ORIENTATION, c.a(i2, this.ab));
                    }
                    if (!(extractMetadata == null || extractMetadata2 == null)) {
                        int parseInt2 = Integer.parseInt(extractMetadata);
                        int parseInt3 = Integer.parseInt(extractMetadata2);
                        if (parseInt3 > 6) {
                            fVar.a(parseInt2);
                            byte[] bArr = new byte[6];
                            if (fVar.read(bArr) == 6) {
                                int i3 = parseInt2 + 6;
                                int i4 = parseInt3 - 6;
                                if (Arrays.equals(bArr, g)) {
                                    byte[] bArr2 = new byte[i4];
                                    if (fVar.read(bArr2) == i4) {
                                        this.aj = i3;
                                        a(bArr2, 0);
                                    } else {
                                        throw new IOException("Can't read exif");
                                    }
                                } else {
                                    throw new IOException("Invalid identifier");
                                }
                            } else {
                                throw new IOException("Can't read identifier");
                            }
                        } else {
                            throw new IOException("Invalid exif length");
                        }
                    }
                    if (h) {
                        Log.d("ExifInterface", "Heif meta: " + str3 + "x" + str2 + ", rotation " + str);
                    }
                } catch (RuntimeException unused) {
                    throw new UnsupportedOperationException("Failed to read EXIF from HEIF file. Given stream is either malformed or unsupported.");
                }
            } finally {
                mediaMetadataRetriever.release();
            }
        } else {
            throw new UnsupportedOperationException("Reading EXIF from HEIF files is supported from SDK 28 and above");
        }
    }

    private void c(f fVar) throws IOException {
        fVar.a(g.length);
        byte[] bArr = new byte[fVar.available()];
        fVar.readFully(bArr);
        this.aj = g.length;
        a(bArr, 0);
    }

    private void d(f fVar) throws IOException {
        a(fVar);
        c cVar = this.Z[1].get(TAG_MAKER_NOTE);
        if (cVar != null) {
            f fVar2 = new f(cVar.d);
            fVar2.a(this.ab);
            byte[] bArr = new byte[n.length];
            fVar2.readFully(bArr);
            fVar2.a(0L);
            byte[] bArr2 = new byte[o.length];
            fVar2.readFully(bArr2);
            if (Arrays.equals(bArr, n)) {
                fVar2.a(8L);
            } else if (Arrays.equals(bArr2, o)) {
                fVar2.a(12L);
            }
            a(fVar2, 6);
            c cVar2 = this.Z[7].get(TAG_ORF_PREVIEW_IMAGE_START);
            c cVar3 = this.Z[7].get(TAG_ORF_PREVIEW_IMAGE_LENGTH);
            if (!(cVar2 == null || cVar3 == null)) {
                this.Z[5].put(TAG_JPEG_INTERCHANGE_FORMAT, cVar2);
                this.Z[5].put(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, cVar3);
            }
            c cVar4 = this.Z[8].get(TAG_ORF_ASPECT_FRAME);
            if (cVar4 != null) {
                int[] iArr = (int[]) cVar4.a(this.ab);
                if (iArr == null || iArr.length != 4) {
                    Log.w("ExifInterface", "Invalid aspect frame values. frame=" + Arrays.toString(iArr));
                } else if (iArr[2] > iArr[0] && iArr[3] > iArr[1]) {
                    int i2 = (iArr[2] - iArr[0]) + 1;
                    int i3 = (iArr[3] - iArr[1]) + 1;
                    if (i2 < i3) {
                        int i4 = i2 + i3;
                        i3 = i4 - i3;
                        i2 = i4 - i3;
                    }
                    c a2 = c.a(i2, this.ab);
                    c a3 = c.a(i3, this.ab);
                    this.Z[0].put(TAG_IMAGE_WIDTH, a2);
                    this.Z[0].put(TAG_IMAGE_LENGTH, a3);
                }
            }
        }
    }

    private void e(f fVar) throws IOException {
        if (h) {
            Log.d("ExifInterface", "getRw2Attributes starting with: " + fVar);
        }
        a(fVar);
        c cVar = this.Z[0].get(TAG_RW2_JPG_FROM_RAW);
        if (cVar != null) {
            a(new a(cVar.d), (int) cVar.c, 5);
        }
        c cVar2 = this.Z[0].get(TAG_RW2_ISO);
        c cVar3 = this.Z[1].get(TAG_PHOTOGRAPHIC_SENSITIVITY);
        if (cVar2 != null && cVar3 == null) {
            this.Z[1].put(TAG_PHOTOGRAPHIC_SENSITIVITY, cVar2);
        }
    }

    private void b(a aVar) throws IOException {
        if (h) {
            Log.d("ExifInterface", "getPngAttributes starting with: " + aVar);
        }
        aVar.a(ByteOrder.BIG_ENDIAN);
        aVar.a(p.length);
        int length = p.length + 0;
        while (true) {
            try {
                int readInt = aVar.readInt();
                int i2 = length + 4;
                byte[] bArr = new byte[4];
                if (aVar.read(bArr) == bArr.length) {
                    int i3 = i2 + 4;
                    if (i3 == 16 && !Arrays.equals(bArr, r)) {
                        throw new IOException("Encountered invalid PNG file--IHDR chunk should appearas the first chunk");
                    }
                    if (!Arrays.equals(bArr, s)) {
                        if (Arrays.equals(bArr, q)) {
                            byte[] bArr2 = new byte[readInt];
                            if (aVar.read(bArr2) == readInt) {
                                int readInt2 = aVar.readInt();
                                CRC32 crc32 = new CRC32();
                                crc32.update(bArr);
                                crc32.update(bArr2);
                                if (((int) crc32.getValue()) == readInt2) {
                                    this.aj = i3;
                                    a(bArr2, 0);
                                    c();
                                    f(new a(bArr2));
                                    return;
                                }
                                throw new IOException("Encountered invalid CRC value for PNG-EXIF chunk.\n recorded CRC value: " + readInt2 + ", calculated CRC value: " + crc32.getValue());
                            }
                            throw new IOException("Failed to read given length for given PNG chunk type: " + a.a(bArr));
                        }
                        int i4 = readInt + 4;
                        aVar.a(i4);
                        length = i3 + i4;
                    } else {
                        return;
                    }
                } else {
                    throw new IOException("Encountered invalid length while parsing PNG chunktype");
                }
            } catch (EOFException unused) {
                throw new IOException("Encountered corrupt PNG file.");
            }
        }
    }

    private void c(a aVar) throws IOException {
        if (h) {
            Log.d("ExifInterface", "getWebpAttributes starting with: " + aVar);
        }
        aVar.a(ByteOrder.LITTLE_ENDIAN);
        aVar.a(t.length);
        int readInt = aVar.readInt() + 8;
        aVar.a(u.length);
        int length = u.length + 8;
        while (true) {
            try {
                byte[] bArr = new byte[4];
                if (aVar.read(bArr) == bArr.length) {
                    int readInt2 = aVar.readInt();
                    int i2 = length + 4 + 4;
                    if (Arrays.equals(v, bArr)) {
                        byte[] bArr2 = new byte[readInt2];
                        if (aVar.read(bArr2) == readInt2) {
                            this.aj = i2;
                            a(bArr2, 0);
                            f(new a(bArr2));
                            return;
                        }
                        throw new IOException("Failed to read given length for given PNG chunk type: " + a.a(bArr));
                    }
                    if (readInt2 % 2 == 1) {
                        readInt2++;
                    }
                    length = i2 + readInt2;
                    if (length != readInt) {
                        if (length <= readInt) {
                            aVar.a(readInt2);
                        } else {
                            throw new IOException("Encountered WebP file with invalid chunk size");
                        }
                    } else {
                        return;
                    }
                } else {
                    throw new IOException("Encountered invalid length while parsing WebP chunktype");
                }
            } catch (EOFException unused) {
                throw new IOException("Encountered corrupt WebP file.");
            }
        }
    }

    private void a(InputStream inputStream, OutputStream outputStream) throws IOException {
        if (h) {
            Log.d("ExifInterface", "saveJpegAttributes starting with (inputStream: " + inputStream + ", outputStream: " + outputStream + ")");
        }
        a aVar = new a(inputStream);
        b bVar = new b(outputStream, ByteOrder.BIG_ENDIAN);
        if (aVar.readByte() == -1) {
            bVar.a(-1);
            if (aVar.readByte() == -40) {
                bVar.a(-40);
                c cVar = null;
                if (getAttribute(TAG_XMP) != null && this.ao) {
                    cVar = this.Z[0].remove(TAG_XMP);
                }
                bVar.a(-1);
                bVar.a(-31);
                a(bVar);
                if (cVar != null) {
                    this.Z[0].put(TAG_XMP, cVar);
                }
                byte[] bArr = new byte[4096];
                while (aVar.readByte() == -1) {
                    byte readByte = aVar.readByte();
                    if (readByte != -31) {
                        switch (readByte) {
                            case Code.REQUEST_MESH_REG_SERVER_VERIFY_CERT_FAILED /* -39 */:
                            case Code.REQUEST_PINCODE_IS_EMPTY /* -38 */:
                                bVar.a(-1);
                                bVar.a((int) readByte);
                                a.a(aVar, bVar);
                                return;
                            default:
                                bVar.a(-1);
                                bVar.a((int) readByte);
                                int readUnsignedShort = aVar.readUnsignedShort();
                                bVar.c(readUnsignedShort);
                                int i2 = readUnsignedShort - 2;
                                if (i2 >= 0) {
                                    while (i2 > 0) {
                                        int read = aVar.read(bArr, 0, Math.min(i2, bArr.length));
                                        if (read >= 0) {
                                            bVar.write(bArr, 0, read);
                                            i2 -= read;
                                        }
                                    }
                                    break;
                                } else {
                                    throw new IOException("Invalid length");
                                }
                                break;
                        }
                    } else {
                        int readUnsignedShort2 = aVar.readUnsignedShort() - 2;
                        if (readUnsignedShort2 >= 0) {
                            byte[] bArr2 = new byte[6];
                            if (readUnsignedShort2 >= 6) {
                                if (aVar.read(bArr2) != 6) {
                                    throw new IOException("Invalid exif");
                                } else if (Arrays.equals(bArr2, g)) {
                                    aVar.a(readUnsignedShort2 - 6);
                                }
                            }
                            bVar.a(-1);
                            bVar.a((int) readByte);
                            bVar.c(readUnsignedShort2 + 2);
                            if (readUnsignedShort2 >= 6) {
                                readUnsignedShort2 -= 6;
                                bVar.write(bArr2);
                            }
                            while (readUnsignedShort2 > 0) {
                                int read2 = aVar.read(bArr, 0, Math.min(readUnsignedShort2, bArr.length));
                                if (read2 >= 0) {
                                    bVar.write(bArr, 0, read2);
                                    readUnsignedShort2 -= read2;
                                }
                            }
                        } else {
                            throw new IOException("Invalid length");
                        }
                    }
                }
                throw new IOException("Invalid marker");
            }
            throw new IOException("Invalid marker");
        }
        throw new IOException("Invalid marker");
    }

    private void b(InputStream inputStream, OutputStream outputStream) throws IOException {
        Throwable th;
        if (h) {
            Log.d("ExifInterface", "savePngAttributes starting with (inputStream: " + inputStream + ", outputStream: " + outputStream + ")");
        }
        a aVar = new a(inputStream);
        b bVar = new b(outputStream, ByteOrder.BIG_ENDIAN);
        a.a(aVar, bVar, p.length);
        int i2 = this.aj;
        if (i2 == 0) {
            int readInt = aVar.readInt();
            bVar.b(readInt);
            a.a(aVar, bVar, readInt + 4 + 4);
        } else {
            a.a(aVar, bVar, ((i2 - p.length) - 4) - 4);
            aVar.a(aVar.readInt() + 4 + 4);
        }
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                b bVar2 = new b(byteArrayOutputStream, ByteOrder.BIG_ENDIAN);
                a(bVar2);
                byte[] byteArray = ((ByteArrayOutputStream) bVar2.a).toByteArray();
                bVar.write(byteArray);
                CRC32 crc32 = new CRC32();
                crc32.update(byteArray, 4, byteArray.length - 4);
                bVar.b((int) crc32.getValue());
                a.a((Closeable) byteArrayOutputStream);
                a.a(aVar, bVar);
            } catch (Throwable th2) {
                th = th2;
                a.a((Closeable) byteArrayOutputStream);
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v0, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r6v1 */
    /* JADX WARN: Type inference failed for: r6v2, types: [java.io.OutputStream, java.io.ByteArrayOutputStream, java.io.Closeable] */
    /* JADX WARN: Unknown variable types count: 1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void c(java.io.InputStream r20, java.io.OutputStream r21) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 534
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.exifinterface.media.ExifInterface.c(java.io.InputStream, java.io.OutputStream):void");
    }

    private void a(a aVar, b bVar, byte[] bArr, byte[] bArr2) throws IOException {
        String str;
        while (true) {
            byte[] bArr3 = new byte[4];
            if (aVar.read(bArr3) != bArr3.length) {
                StringBuilder sb = new StringBuilder();
                sb.append("Encountered invalid length while copying WebP chunks up tochunk type ");
                sb.append(new String(bArr, f));
                if (bArr2 == null) {
                    str = "";
                } else {
                    str = " or " + new String(bArr2, f);
                }
                sb.append(str);
                throw new IOException(sb.toString());
            }
            a(aVar, bVar, bArr3);
            if (Arrays.equals(bArr3, bArr)) {
                return;
            }
            if (bArr2 != null && Arrays.equals(bArr3, bArr2)) {
                return;
            }
        }
    }

    private void a(a aVar, b bVar, byte[] bArr) throws IOException {
        int readInt = aVar.readInt();
        bVar.write(bArr);
        bVar.b(readInt);
        if (readInt % 2 == 1) {
            readInt++;
        }
        a.a(aVar, bVar, readInt);
    }

    private void a(byte[] bArr, int i2) throws IOException {
        f fVar = new f(bArr);
        e((a) fVar);
        a(fVar, i2);
    }

    private void b() {
        String attribute = getAttribute(TAG_DATETIME_ORIGINAL);
        if (attribute != null && getAttribute(TAG_DATETIME) == null) {
            this.Z[0].put(TAG_DATETIME, c.b(attribute));
        }
        if (getAttribute(TAG_IMAGE_WIDTH) == null) {
            this.Z[0].put(TAG_IMAGE_WIDTH, c.a(0L, this.ab));
        }
        if (getAttribute(TAG_IMAGE_LENGTH) == null) {
            this.Z[0].put(TAG_IMAGE_LENGTH, c.a(0L, this.ab));
        }
        if (getAttribute(TAG_ORIENTATION) == null) {
            this.Z[0].put(TAG_ORIENTATION, c.a(0L, this.ab));
        }
        if (getAttribute(TAG_LIGHT_SOURCE) == null) {
            this.Z[1].put(TAG_LIGHT_SOURCE, c.a(0L, this.ab));
        }
    }

    private ByteOrder d(a aVar) throws IOException {
        short readShort = aVar.readShort();
        if (readShort == 18761) {
            if (h) {
                Log.d("ExifInterface", "readExifSegment: Byte Align II");
            }
            return ByteOrder.LITTLE_ENDIAN;
        } else if (readShort == 19789) {
            if (h) {
                Log.d("ExifInterface", "readExifSegment: Byte Align MM");
            }
            return ByteOrder.BIG_ENDIAN;
        } else {
            throw new IOException("Invalid byte order: " + Integer.toHexString(readShort));
        }
    }

    private void e(a aVar) throws IOException {
        this.ab = d(aVar);
        aVar.a(this.ab);
        int readUnsignedShort = aVar.readUnsignedShort();
        int i2 = this.X;
        if (i2 == 7 || i2 == 10 || readUnsignedShort == 42) {
            int readInt = aVar.readInt();
            if (readInt >= 8) {
                int i3 = readInt - 8;
                if (i3 > 0) {
                    aVar.a(i3);
                    return;
                }
                return;
            }
            throw new IOException("Invalid first Ifd offset: " + readInt);
        }
        throw new IOException("Invalid start code: " + Integer.toHexString(readUnsignedShort));
    }

    /* JADX WARN: Removed duplicated region for block: B:48:0x0150  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0156  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void a(androidx.exifinterface.media.ExifInterface.f r24, int r25) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 998
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.exifinterface.media.ExifInterface.a(androidx.exifinterface.media.ExifInterface$f, int):void");
    }

    private void b(f fVar, int i2) throws IOException {
        c cVar = this.Z[i2].get(TAG_IMAGE_LENGTH);
        c cVar2 = this.Z[i2].get(TAG_IMAGE_WIDTH);
        if (cVar == null || cVar2 == null) {
            c cVar3 = this.Z[i2].get(TAG_JPEG_INTERCHANGE_FORMAT);
            c cVar4 = this.Z[i2].get(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH);
            if (cVar3 != null && cVar4 != null) {
                int c2 = cVar3.c(this.ab);
                int c3 = cVar3.c(this.ab);
                fVar.a(c2);
                byte[] bArr = new byte[c3];
                fVar.read(bArr);
                a(new a(bArr), c2, i2);
            }
        }
    }

    private void f(a aVar) throws IOException {
        HashMap<String, c> hashMap = this.Z[4];
        c cVar = hashMap.get(TAG_COMPRESSION);
        if (cVar != null) {
            this.ai = cVar.c(this.ab);
            int i2 = this.ai;
            if (i2 != 1) {
                switch (i2) {
                    case 6:
                        a(aVar, hashMap);
                        return;
                    case 7:
                        break;
                    default:
                        return;
                }
            }
            if (a(hashMap)) {
                b(aVar, hashMap);
                return;
            }
            return;
        }
        this.ai = 6;
        a(aVar, hashMap);
    }

    private void a(a aVar, HashMap hashMap) throws IOException {
        c cVar = (c) hashMap.get(TAG_JPEG_INTERCHANGE_FORMAT);
        c cVar2 = (c) hashMap.get(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH);
        if (cVar != null && cVar2 != null) {
            int c2 = cVar.c(this.ab);
            int c3 = cVar2.c(this.ab);
            if (this.X == 7) {
                c2 += this.ak;
            }
            if (c2 > 0 && c3 > 0) {
                this.ac = true;
                if (this.U == null && this.W == null && this.V == null) {
                    byte[] bArr = new byte[c3];
                    aVar.skip(c2);
                    aVar.read(bArr);
                    this.ah = bArr;
                }
                this.af = c2;
                this.ag = c3;
            }
            if (h) {
                Log.d("ExifInterface", "Setting thumbnail attributes with offset: " + c2 + ", length: " + c3);
            }
        }
    }

    private void b(a aVar, HashMap hashMap) throws IOException {
        c cVar = (c) hashMap.get(TAG_STRIP_OFFSETS);
        c cVar2 = (c) hashMap.get(TAG_STRIP_BYTE_COUNTS);
        if (cVar != null && cVar2 != null) {
            long[] a2 = a.a(cVar.a(this.ab));
            long[] a3 = a.a(cVar2.a(this.ab));
            if (a2 == null || a2.length == 0) {
                Log.w("ExifInterface", "stripOffsets should not be null or have zero length.");
            } else if (a3 == null || a3.length == 0) {
                Log.w("ExifInterface", "stripByteCounts should not be null or have zero length.");
            } else if (a2.length != a3.length) {
                Log.w("ExifInterface", "stripOffsets and stripByteCounts should have same length.");
            } else {
                long j2 = 0;
                for (long j3 : a3) {
                    j2 += j3;
                }
                byte[] bArr = new byte[(int) j2];
                this.ae = true;
                this.ad = true;
                this.ac = true;
                int i2 = 0;
                int i3 = 0;
                for (int i4 = 0; i4 < a2.length; i4++) {
                    int i5 = (int) a2[i4];
                    int i6 = (int) a3[i4];
                    if (i4 < a2.length - 1 && i5 + i6 != a2[i4 + 1]) {
                        this.ae = false;
                    }
                    int i7 = i5 - i2;
                    if (i7 < 0) {
                        Log.d("ExifInterface", "Invalid strip offset value");
                        return;
                    }
                    long j4 = i7;
                    if (aVar.skip(j4) != j4) {
                        Log.d("ExifInterface", "Failed to skip " + i7 + " bytes.");
                        return;
                    }
                    int i8 = i2 + i7;
                    byte[] bArr2 = new byte[i6];
                    if (aVar.read(bArr2) != i6) {
                        Log.d("ExifInterface", "Failed to read " + i6 + " bytes.");
                        return;
                    }
                    i2 = i8 + i6;
                    System.arraycopy(bArr2, 0, bArr, i3, bArr2.length);
                    i3 += bArr2.length;
                }
                this.ah = bArr;
                if (this.ae) {
                    this.af = (int) a2[0];
                    this.ag = bArr.length;
                }
            }
        }
    }

    private boolean a(HashMap hashMap) throws IOException {
        c cVar;
        int c2;
        c cVar2 = (c) hashMap.get(TAG_BITS_PER_SAMPLE);
        if (cVar2 != null) {
            int[] iArr = (int[]) cVar2.a(this.ab);
            if (Arrays.equals(BITS_PER_SAMPLE_RGB, iArr)) {
                return true;
            }
            if (this.X == 3 && (cVar = (c) hashMap.get(TAG_PHOTOMETRIC_INTERPRETATION)) != null && (((c2 = cVar.c(this.ab)) == 1 && Arrays.equals(iArr, BITS_PER_SAMPLE_GREYSCALE_2)) || (c2 == 6 && Arrays.equals(iArr, BITS_PER_SAMPLE_RGB)))) {
                return true;
            }
        }
        if (!h) {
            return false;
        }
        Log.d("ExifInterface", "Unsupported data type value");
        return false;
    }

    private boolean b(HashMap hashMap) throws IOException {
        c cVar = (c) hashMap.get(TAG_IMAGE_LENGTH);
        c cVar2 = (c) hashMap.get(TAG_IMAGE_WIDTH);
        if (cVar == null || cVar2 == null) {
            return false;
        }
        return cVar.c(this.ab) <= 512 && cVar2.c(this.ab) <= 512;
    }

    private void c() throws IOException {
        a(0, 5);
        a(0, 4);
        a(5, 4);
        c cVar = this.Z[1].get(TAG_PIXEL_X_DIMENSION);
        c cVar2 = this.Z[1].get(TAG_PIXEL_Y_DIMENSION);
        if (!(cVar == null || cVar2 == null)) {
            this.Z[0].put(TAG_IMAGE_WIDTH, cVar);
            this.Z[0].put(TAG_IMAGE_LENGTH, cVar2);
        }
        if (this.Z[4].isEmpty() && b(this.Z[5])) {
            HashMap<String, c>[] hashMapArr = this.Z;
            hashMapArr[4] = hashMapArr[5];
            hashMapArr[5] = new HashMap<>();
        }
        if (!b(this.Z[4])) {
            Log.d("ExifInterface", "No image meets the size requirements of a thumbnail image.");
        }
        a(0, TAG_THUMBNAIL_ORIENTATION, TAG_ORIENTATION);
        a(0, TAG_THUMBNAIL_IMAGE_LENGTH, TAG_IMAGE_LENGTH);
        a(0, TAG_THUMBNAIL_IMAGE_WIDTH, TAG_IMAGE_WIDTH);
        a(5, TAG_THUMBNAIL_ORIENTATION, TAG_ORIENTATION);
        a(5, TAG_THUMBNAIL_IMAGE_LENGTH, TAG_IMAGE_LENGTH);
        a(5, TAG_THUMBNAIL_IMAGE_WIDTH, TAG_IMAGE_WIDTH);
        a(4, TAG_ORIENTATION, TAG_THUMBNAIL_ORIENTATION);
        a(4, TAG_IMAGE_LENGTH, TAG_THUMBNAIL_IMAGE_LENGTH);
        a(4, TAG_IMAGE_WIDTH, TAG_THUMBNAIL_IMAGE_WIDTH);
    }

    private void c(f fVar, int i2) throws IOException {
        c cVar;
        c cVar2;
        c cVar3 = this.Z[i2].get(TAG_DEFAULT_CROP_SIZE);
        c cVar4 = this.Z[i2].get(TAG_RW2_SENSOR_TOP_BORDER);
        c cVar5 = this.Z[i2].get(TAG_RW2_SENSOR_LEFT_BORDER);
        c cVar6 = this.Z[i2].get(TAG_RW2_SENSOR_BOTTOM_BORDER);
        c cVar7 = this.Z[i2].get(TAG_RW2_SENSOR_RIGHT_BORDER);
        if (cVar3 != null) {
            if (cVar3.a == 5) {
                e[] eVarArr = (e[]) cVar3.a(this.ab);
                if (eVarArr == null || eVarArr.length != 2) {
                    Log.w("ExifInterface", "Invalid crop size values. cropSize=" + Arrays.toString(eVarArr));
                    return;
                }
                cVar2 = c.a(eVarArr[0], this.ab);
                cVar = c.a(eVarArr[1], this.ab);
            } else {
                int[] iArr = (int[]) cVar3.a(this.ab);
                if (iArr == null || iArr.length != 2) {
                    Log.w("ExifInterface", "Invalid crop size values. cropSize=" + Arrays.toString(iArr));
                    return;
                }
                cVar2 = c.a(iArr[0], this.ab);
                cVar = c.a(iArr[1], this.ab);
            }
            this.Z[i2].put(TAG_IMAGE_WIDTH, cVar2);
            this.Z[i2].put(TAG_IMAGE_LENGTH, cVar);
        } else if (cVar4 == null || cVar5 == null || cVar6 == null || cVar7 == null) {
            b(fVar, i2);
        } else {
            int c2 = cVar4.c(this.ab);
            int c3 = cVar6.c(this.ab);
            int c4 = cVar7.c(this.ab);
            int c5 = cVar5.c(this.ab);
            if (c3 > c2 && c4 > c5) {
                c a2 = c.a(c3 - c2, this.ab);
                c a3 = c.a(c4 - c5, this.ab);
                this.Z[i2].put(TAG_IMAGE_LENGTH, a2);
                this.Z[i2].put(TAG_IMAGE_WIDTH, a3);
            }
        }
    }

    private int a(b bVar) throws IOException {
        d[][] dVarArr = e;
        int[] iArr = new int[dVarArr.length];
        int[] iArr2 = new int[dVarArr.length];
        for (d dVar : O) {
            b(dVar.b);
        }
        if (this.ac) {
            if (this.ad) {
                b(TAG_STRIP_OFFSETS);
                b(TAG_STRIP_BYTE_COUNTS);
            } else {
                b(TAG_JPEG_INTERCHANGE_FORMAT);
                b(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH);
            }
        }
        for (int i2 = 0; i2 < e.length; i2++) {
            for (Object obj : this.Z[i2].entrySet().toArray()) {
                Map.Entry entry = (Map.Entry) obj;
                if (entry.getValue() == null) {
                    this.Z[i2].remove(entry.getKey());
                }
            }
        }
        if (!this.Z[1].isEmpty()) {
            this.Z[0].put(O[1].b, c.a(0L, this.ab));
        }
        if (!this.Z[2].isEmpty()) {
            this.Z[0].put(O[2].b, c.a(0L, this.ab));
        }
        if (!this.Z[3].isEmpty()) {
            this.Z[1].put(O[3].b, c.a(0L, this.ab));
        }
        if (this.ac) {
            if (this.ad) {
                this.Z[4].put(TAG_STRIP_OFFSETS, c.a(0, this.ab));
                this.Z[4].put(TAG_STRIP_BYTE_COUNTS, c.a(this.ag, this.ab));
            } else {
                this.Z[4].put(TAG_JPEG_INTERCHANGE_FORMAT, c.a(0L, this.ab));
                this.Z[4].put(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, c.a(this.ag, this.ab));
            }
        }
        for (int i3 = 0; i3 < e.length; i3++) {
            int i4 = 0;
            for (Map.Entry<String, c> entry2 : this.Z[i3].entrySet()) {
                int a2 = entry2.getValue().a();
                if (a2 > 4) {
                    i4 += a2;
                }
            }
            iArr2[i3] = iArr2[i3] + i4;
        }
        int i5 = 8;
        for (int i6 = 0; i6 < e.length; i6++) {
            if (!this.Z[i6].isEmpty()) {
                iArr[i6] = i5;
                i5 += (this.Z[i6].size() * 12) + 2 + 4 + iArr2[i6];
            }
        }
        if (this.ac) {
            if (this.ad) {
                this.Z[4].put(TAG_STRIP_OFFSETS, c.a(i5, this.ab));
            } else {
                this.Z[4].put(TAG_JPEG_INTERCHANGE_FORMAT, c.a(i5, this.ab));
            }
            this.af = i5;
            i5 += this.ag;
        }
        if (this.X == 4) {
            i5 += 8;
        }
        if (h) {
            for (int i7 = 0; i7 < e.length; i7++) {
                Log.d("ExifInterface", String.format("index: %d, offsets: %d, tag count: %d, data sizes: %d, total size: %d", Integer.valueOf(i7), Integer.valueOf(iArr[i7]), Integer.valueOf(this.Z[i7].size()), Integer.valueOf(iArr2[i7]), Integer.valueOf(i5)));
            }
        }
        if (!this.Z[1].isEmpty()) {
            this.Z[0].put(O[1].b, c.a(iArr[1], this.ab));
        }
        if (!this.Z[2].isEmpty()) {
            this.Z[0].put(O[2].b, c.a(iArr[2], this.ab));
        }
        if (!this.Z[3].isEmpty()) {
            this.Z[1].put(O[3].b, c.a(iArr[3], this.ab));
        }
        int i8 = this.X;
        if (i8 != 4) {
            switch (i8) {
                case 13:
                    bVar.b(i5);
                    bVar.write(q);
                    break;
                case 14:
                    bVar.write(v);
                    bVar.b(i5);
                    break;
            }
        } else {
            bVar.c(i5);
            bVar.write(g);
        }
        bVar.a(this.ab == ByteOrder.BIG_ENDIAN ? (short) 19789 : (short) 18761);
        bVar.a(this.ab);
        bVar.c(42);
        bVar.a(8L);
        for (int i9 = 0; i9 < e.length; i9++) {
            if (!this.Z[i9].isEmpty()) {
                bVar.c(this.Z[i9].size());
                int size = iArr[i9] + 2 + (this.Z[i9].size() * 12) + 4;
                for (Map.Entry<String, c> entry3 : this.Z[i9].entrySet()) {
                    int i10 = Q[i9].get(entry3.getKey()).a;
                    c value = entry3.getValue();
                    int a3 = value.a();
                    bVar.c(i10);
                    bVar.c(value.a);
                    bVar.b(value.b);
                    if (a3 > 4) {
                        bVar.a(size);
                        size += a3;
                    } else {
                        bVar.write(value.d);
                        if (a3 < 4) {
                            while (a3 < 4) {
                                bVar.a(0);
                                a3++;
                            }
                        }
                    }
                }
                if (i9 != 0 || this.Z[4].isEmpty()) {
                    bVar.a(0L);
                } else {
                    bVar.a(iArr[4]);
                }
                for (Map.Entry<String, c> entry4 : this.Z[i9].entrySet()) {
                    c value2 = entry4.getValue();
                    if (value2.d.length > 4) {
                        bVar.write(value2.d, 0, value2.d.length);
                    }
                }
            }
        }
        if (this.ac) {
            bVar.write(getThumbnailBytes());
        }
        if (this.X == 14 && i5 % 2 == 1) {
            bVar.a(0);
        }
        bVar.a(ByteOrder.BIG_ENDIAN);
        return i5;
    }

    private static Pair<Integer, Integer> d(String str) {
        if (str.contains(Constants.ACCEPT_TIME_SEPARATOR_SP)) {
            String[] split = str.split(Constants.ACCEPT_TIME_SEPARATOR_SP, -1);
            Pair<Integer, Integer> d2 = d(split[0]);
            if (((Integer) d2.first).intValue() == 2) {
                return d2;
            }
            for (int i2 = 1; i2 < split.length; i2++) {
                Pair<Integer, Integer> d3 = d(split[i2]);
                int intValue = (((Integer) d3.first).equals(d2.first) || ((Integer) d3.second).equals(d2.first)) ? ((Integer) d2.first).intValue() : -1;
                int intValue2 = (((Integer) d2.second).intValue() == -1 || (!((Integer) d3.first).equals(d2.second) && !((Integer) d3.second).equals(d2.second))) ? -1 : ((Integer) d2.second).intValue();
                if (intValue == -1 && intValue2 == -1) {
                    return new Pair<>(2, -1);
                }
                if (intValue == -1) {
                    d2 = new Pair<>(Integer.valueOf(intValue2), -1);
                } else if (intValue2 == -1) {
                    d2 = new Pair<>(Integer.valueOf(intValue), -1);
                }
            }
            return d2;
        } else if (str.contains("/")) {
            String[] split2 = str.split("/", -1);
            if (split2.length == 2) {
                try {
                    long parseDouble = (long) Double.parseDouble(split2[0]);
                    long parseDouble2 = (long) Double.parseDouble(split2[1]);
                    if (parseDouble >= 0 && parseDouble2 >= 0) {
                        if (parseDouble <= 2147483647L && parseDouble2 <= 2147483647L) {
                            return new Pair<>(10, 5);
                        }
                        return new Pair<>(5, -1);
                    }
                    return new Pair<>(10, -1);
                } catch (NumberFormatException unused) {
                }
            }
            return new Pair<>(2, -1);
        } else {
            try {
                try {
                    Long valueOf = Long.valueOf(Long.parseLong(str));
                    if (valueOf.longValue() >= 0 && valueOf.longValue() <= WebSocketProtocol.PAYLOAD_SHORT_MAX) {
                        return new Pair<>(3, 4);
                    }
                    if (valueOf.longValue() < 0) {
                        return new Pair<>(9, -1);
                    }
                    return new Pair<>(4, -1);
                } catch (NumberFormatException unused2) {
                    return new Pair<>(2, -1);
                }
            } catch (NumberFormatException unused3) {
                Double.parseDouble(str);
                return new Pair<>(12, -1);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class f extends a {
        f(byte[] bArr) throws IOException {
            super(bArr);
            this.a.mark(Integer.MAX_VALUE);
        }

        f(InputStream inputStream) throws IOException {
            super(inputStream);
            if (inputStream.markSupported()) {
                this.a.mark(Integer.MAX_VALUE);
                return;
            }
            throw new IllegalArgumentException("Cannot create SeekableByteOrderedDataInputStream with stream that does not support mark/reset");
        }

        public void a(long j) throws IOException {
            if (this.b > j) {
                this.b = 0;
                this.a.reset();
            } else {
                j -= this.b;
            }
            a((int) j);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class a extends InputStream implements DataInput {
        private static final ByteOrder c = ByteOrder.LITTLE_ENDIAN;
        private static final ByteOrder d = ByteOrder.BIG_ENDIAN;
        final DataInputStream a;
        int b;
        private ByteOrder e;
        private byte[] f;

        a(byte[] bArr) throws IOException {
            this(new ByteArrayInputStream(bArr), ByteOrder.BIG_ENDIAN);
        }

        a(InputStream inputStream) throws IOException {
            this(inputStream, ByteOrder.BIG_ENDIAN);
        }

        a(InputStream inputStream, ByteOrder byteOrder) throws IOException {
            this.e = ByteOrder.BIG_ENDIAN;
            this.a = new DataInputStream(inputStream);
            this.a.mark(0);
            this.b = 0;
            this.e = byteOrder;
        }

        public void a(ByteOrder byteOrder) {
            this.e = byteOrder;
        }

        public int a() {
            return this.b;
        }

        @Override // java.io.InputStream
        public int available() throws IOException {
            return this.a.available();
        }

        @Override // java.io.InputStream
        public int read() throws IOException {
            this.b++;
            return this.a.read();
        }

        @Override // java.io.InputStream
        public int read(byte[] bArr, int i, int i2) throws IOException {
            int read = this.a.read(bArr, i, i2);
            this.b += read;
            return read;
        }

        @Override // java.io.DataInput
        public int readUnsignedByte() throws IOException {
            this.b++;
            return this.a.readUnsignedByte();
        }

        @Override // java.io.DataInput
        public String readLine() throws IOException {
            Log.d("ExifInterface", "Currently unsupported");
            return null;
        }

        @Override // java.io.DataInput
        public boolean readBoolean() throws IOException {
            this.b++;
            return this.a.readBoolean();
        }

        @Override // java.io.DataInput
        public char readChar() throws IOException {
            this.b += 2;
            return this.a.readChar();
        }

        @Override // java.io.DataInput
        public String readUTF() throws IOException {
            this.b += 2;
            return this.a.readUTF();
        }

        @Override // java.io.DataInput
        public void readFully(byte[] bArr, int i, int i2) throws IOException {
            this.b += i2;
            this.a.readFully(bArr, i, i2);
        }

        @Override // java.io.DataInput
        public void readFully(byte[] bArr) throws IOException {
            this.b += bArr.length;
            this.a.readFully(bArr);
        }

        @Override // java.io.DataInput
        public byte readByte() throws IOException {
            this.b++;
            int read = this.a.read();
            if (read >= 0) {
                return (byte) read;
            }
            throw new EOFException();
        }

        @Override // java.io.DataInput
        public short readShort() throws IOException {
            this.b += 2;
            int read = this.a.read();
            int read2 = this.a.read();
            if ((read | read2) >= 0) {
                ByteOrder byteOrder = this.e;
                if (byteOrder == c) {
                    return (short) ((read2 << 8) + read);
                }
                if (byteOrder == d) {
                    return (short) ((read << 8) + read2);
                }
                throw new IOException("Invalid byte order: " + this.e);
            }
            throw new EOFException();
        }

        @Override // java.io.DataInput
        public int readInt() throws IOException {
            this.b += 4;
            int read = this.a.read();
            int read2 = this.a.read();
            int read3 = this.a.read();
            int read4 = this.a.read();
            if ((read | read2 | read3 | read4) >= 0) {
                ByteOrder byteOrder = this.e;
                if (byteOrder == c) {
                    return (read4 << 24) + (read3 << 16) + (read2 << 8) + read;
                }
                if (byteOrder == d) {
                    return (read << 24) + (read2 << 16) + (read3 << 8) + read4;
                }
                throw new IOException("Invalid byte order: " + this.e);
            }
            throw new EOFException();
        }

        @Override // java.io.DataInput
        public int skipBytes(int i) throws IOException {
            throw new UnsupportedOperationException("skipBytes is currently unsupported");
        }

        public void a(int i) throws IOException {
            int i2 = 0;
            while (i2 < i) {
                int i3 = i - i2;
                int skip = (int) this.a.skip(i3);
                if (skip <= 0) {
                    if (this.f == null) {
                        this.f = new byte[8192];
                    }
                    skip = this.a.read(this.f, 0, Math.min(8192, i3));
                    if (skip == -1) {
                        throw new EOFException("Reached EOF while skipping " + i + " bytes.");
                    }
                }
                i2 += skip;
            }
            this.b += i2;
        }

        @Override // java.io.DataInput
        public int readUnsignedShort() throws IOException {
            this.b += 2;
            int read = this.a.read();
            int read2 = this.a.read();
            if ((read | read2) >= 0) {
                ByteOrder byteOrder = this.e;
                if (byteOrder == c) {
                    return (read2 << 8) + read;
                }
                if (byteOrder == d) {
                    return (read << 8) + read2;
                }
                throw new IOException("Invalid byte order: " + this.e);
            }
            throw new EOFException();
        }

        public long b() throws IOException {
            return readInt() & 4294967295L;
        }

        @Override // java.io.DataInput
        public long readLong() throws IOException {
            this.b += 8;
            int read = this.a.read();
            int read2 = this.a.read();
            int read3 = this.a.read();
            int read4 = this.a.read();
            int read5 = this.a.read();
            int read6 = this.a.read();
            int read7 = this.a.read();
            int read8 = this.a.read();
            if ((read | read2 | read3 | read4 | read5 | read6 | read7 | read8) >= 0) {
                ByteOrder byteOrder = this.e;
                if (byteOrder == c) {
                    return (read8 << 56) + (read7 << 48) + (read6 << 40) + (read5 << 32) + (read4 << 24) + (read3 << 16) + (read2 << 8) + read;
                }
                if (byteOrder == d) {
                    return (read << 56) + (read2 << 48) + (read3 << 40) + (read4 << 32) + (read5 << 24) + (read6 << 16) + (read7 << 8) + read8;
                }
                throw new IOException("Invalid byte order: " + this.e);
            }
            throw new EOFException();
        }

        @Override // java.io.DataInput
        public float readFloat() throws IOException {
            return Float.intBitsToFloat(readInt());
        }

        @Override // java.io.DataInput
        public double readDouble() throws IOException {
            return Double.longBitsToDouble(readLong());
        }

        @Override // java.io.InputStream
        public void mark(int i) {
            throw new UnsupportedOperationException("Mark is currently unsupported");
        }

        @Override // java.io.InputStream
        public void reset() {
            throw new UnsupportedOperationException("Reset is currently unsupported");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class b extends FilterOutputStream {
        final OutputStream a;
        private ByteOrder b;

        public b(OutputStream outputStream, ByteOrder byteOrder) {
            super(outputStream);
            this.a = outputStream;
            this.b = byteOrder;
        }

        public void a(ByteOrder byteOrder) {
            this.b = byteOrder;
        }

        @Override // java.io.FilterOutputStream, java.io.OutputStream
        public void write(byte[] bArr) throws IOException {
            this.a.write(bArr);
        }

        @Override // java.io.FilterOutputStream, java.io.OutputStream
        public void write(byte[] bArr, int i, int i2) throws IOException {
            this.a.write(bArr, i, i2);
        }

        public void a(int i) throws IOException {
            this.a.write(i);
        }

        public void a(short s) throws IOException {
            if (this.b == ByteOrder.LITTLE_ENDIAN) {
                this.a.write((s >>> 0) & 255);
                this.a.write((s >>> 8) & 255);
            } else if (this.b == ByteOrder.BIG_ENDIAN) {
                this.a.write((s >>> 8) & 255);
                this.a.write((s >>> 0) & 255);
            }
        }

        public void b(int i) throws IOException {
            if (this.b == ByteOrder.LITTLE_ENDIAN) {
                this.a.write((i >>> 0) & 255);
                this.a.write((i >>> 8) & 255);
                this.a.write((i >>> 16) & 255);
                this.a.write((i >>> 24) & 255);
            } else if (this.b == ByteOrder.BIG_ENDIAN) {
                this.a.write((i >>> 24) & 255);
                this.a.write((i >>> 16) & 255);
                this.a.write((i >>> 8) & 255);
                this.a.write((i >>> 0) & 255);
            }
        }

        public void c(int i) throws IOException {
            a((short) i);
        }

        public void a(long j) throws IOException {
            b((int) j);
        }
    }

    private void a(int i2, int i3) throws IOException {
        if (!this.Z[i2].isEmpty() && !this.Z[i3].isEmpty()) {
            c cVar = this.Z[i2].get(TAG_IMAGE_LENGTH);
            c cVar2 = this.Z[i2].get(TAG_IMAGE_WIDTH);
            c cVar3 = this.Z[i3].get(TAG_IMAGE_LENGTH);
            c cVar4 = this.Z[i3].get(TAG_IMAGE_WIDTH);
            if (cVar == null || cVar2 == null) {
                if (h) {
                    Log.d("ExifInterface", "First image does not contain valid size information");
                }
            } else if (cVar3 != null && cVar4 != null) {
                int c2 = cVar.c(this.ab);
                int c3 = cVar2.c(this.ab);
                int c4 = cVar3.c(this.ab);
                int c5 = cVar4.c(this.ab);
                if (c2 < c4 && c3 < c5) {
                    HashMap<String, c>[] hashMapArr = this.Z;
                    HashMap<String, c> hashMap = hashMapArr[i2];
                    hashMapArr[i2] = hashMapArr[i3];
                    hashMapArr[i3] = hashMap;
                }
            } else if (h) {
                Log.d("ExifInterface", "Second image does not contain valid size information");
            }
        } else if (h) {
            Log.d("ExifInterface", "Cannot perform swap since only one image data exists");
        }
    }

    private void a(int i2, String str, String str2) {
        if (!this.Z[i2].isEmpty() && this.Z[i2].get(str) != null) {
            HashMap<String, c>[] hashMapArr = this.Z;
            hashMapArr[i2].put(str2, hashMapArr[i2].get(str));
            this.Z[i2].remove(str);
        }
    }
}
