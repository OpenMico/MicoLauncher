package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.ParseContext;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import io.netty.util.internal.StringUtil;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;
import java.lang.reflect.Type;

/* loaded from: classes.dex */
public class AwtCodec implements ObjectDeserializer, ObjectSerializer {
    public static final AwtCodec instance = new AwtCodec();

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 12;
    }

    public static boolean support(Class<?> cls) {
        return cls == Point.class || cls == Rectangle.class || cls == Font.class || cls == Color.class;
    }

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer jSONSerializer, Object obj, Object obj2, Type type, int i) throws IOException {
        SerializeWriter serializeWriter = jSONSerializer.out;
        if (obj == null) {
            serializeWriter.writeNull();
            return;
        }
        if (obj instanceof Point) {
            Point point = (Point) obj;
            serializeWriter.writeFieldValue(writeClassName(serializeWriter, Point.class, '{'), "x", point.x);
            serializeWriter.writeFieldValue(StringUtil.COMMA, "y", point.y);
        } else if (obj instanceof Font) {
            Font font = (Font) obj;
            serializeWriter.writeFieldValue(writeClassName(serializeWriter, Font.class, '{'), "name", font.getName());
            serializeWriter.writeFieldValue(StringUtil.COMMA, "style", font.getStyle());
            serializeWriter.writeFieldValue(StringUtil.COMMA, "size", font.getSize());
        } else if (obj instanceof Rectangle) {
            Rectangle rectangle = (Rectangle) obj;
            serializeWriter.writeFieldValue(writeClassName(serializeWriter, Rectangle.class, '{'), "x", rectangle.x);
            serializeWriter.writeFieldValue(StringUtil.COMMA, "y", rectangle.y);
            serializeWriter.writeFieldValue(StringUtil.COMMA, "width", rectangle.width);
            serializeWriter.writeFieldValue(StringUtil.COMMA, "height", rectangle.height);
        } else if (obj instanceof Color) {
            Color color = (Color) obj;
            serializeWriter.writeFieldValue(writeClassName(serializeWriter, Color.class, '{'), "r", color.getRed());
            serializeWriter.writeFieldValue(StringUtil.COMMA, "g", color.getGreen());
            serializeWriter.writeFieldValue(StringUtil.COMMA, "b", color.getBlue());
            if (color.getAlpha() > 0) {
                serializeWriter.writeFieldValue(StringUtil.COMMA, "alpha", color.getAlpha());
            }
        } else {
            throw new JSONException("not support awt class : " + obj.getClass().getName());
        }
        serializeWriter.write(125);
    }

    protected char writeClassName(SerializeWriter serializeWriter, Class<?> cls, char c) {
        if (!serializeWriter.isEnabled(SerializerFeature.WriteClassName)) {
            return c;
        }
        serializeWriter.write(123);
        serializeWriter.writeFieldName(JSON.DEFAULT_TYPE_KEY);
        serializeWriter.writeString(cls.getName());
        return StringUtil.COMMA;
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object obj) {
        T t;
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        if (jSONLexer.token() == 8) {
            jSONLexer.nextToken(16);
            return null;
        } else if (jSONLexer.token() == 12 || jSONLexer.token() == 16) {
            jSONLexer.nextToken();
            if (type == Point.class) {
                t = (T) parsePoint(defaultJSONParser, obj);
            } else if (type == Rectangle.class) {
                t = (T) parseRectangle(defaultJSONParser);
            } else if (type == Color.class) {
                t = (T) parseColor(defaultJSONParser);
            } else if (type == Font.class) {
                t = (T) parseFont(defaultJSONParser);
            } else {
                throw new JSONException("not support awt class : " + type);
            }
            ParseContext context = defaultJSONParser.getContext();
            defaultJSONParser.setContext(t, obj);
            defaultJSONParser.setContext(context);
            return t;
        } else {
            throw new JSONException("syntax error");
        }
    }

    protected Font parseFont(DefaultJSONParser defaultJSONParser) {
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        int i = 0;
        String str = null;
        int i2 = 0;
        while (jSONLexer.token() != 13) {
            if (jSONLexer.token() == 4) {
                String stringVal = jSONLexer.stringVal();
                jSONLexer.nextTokenWithColon(2);
                if (stringVal.equalsIgnoreCase("name")) {
                    if (jSONLexer.token() == 4) {
                        str = jSONLexer.stringVal();
                        jSONLexer.nextToken();
                    } else {
                        throw new JSONException("syntax error");
                    }
                } else if (stringVal.equalsIgnoreCase("style")) {
                    if (jSONLexer.token() == 2) {
                        i = jSONLexer.intValue();
                        jSONLexer.nextToken();
                    } else {
                        throw new JSONException("syntax error");
                    }
                } else if (!stringVal.equalsIgnoreCase("size")) {
                    throw new JSONException("syntax error, " + stringVal);
                } else if (jSONLexer.token() == 2) {
                    i2 = jSONLexer.intValue();
                    jSONLexer.nextToken();
                } else {
                    throw new JSONException("syntax error");
                }
                if (jSONLexer.token() == 16) {
                    jSONLexer.nextToken(4);
                }
            } else {
                throw new JSONException("syntax error");
            }
        }
        jSONLexer.nextToken();
        return new Font(str, i, i2);
    }

    protected Color parseColor(DefaultJSONParser defaultJSONParser) {
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (jSONLexer.token() != 13) {
            if (jSONLexer.token() == 4) {
                String stringVal = jSONLexer.stringVal();
                jSONLexer.nextTokenWithColon(2);
                if (jSONLexer.token() == 2) {
                    int intValue = jSONLexer.intValue();
                    jSONLexer.nextToken();
                    if (stringVal.equalsIgnoreCase("r")) {
                        i = intValue;
                    } else if (stringVal.equalsIgnoreCase("g")) {
                        i2 = intValue;
                    } else if (stringVal.equalsIgnoreCase("b")) {
                        i3 = intValue;
                    } else if (stringVal.equalsIgnoreCase("alpha")) {
                        i4 = intValue;
                    } else {
                        throw new JSONException("syntax error, " + stringVal);
                    }
                    if (jSONLexer.token() == 16) {
                        jSONLexer.nextToken(4);
                    }
                } else {
                    throw new JSONException("syntax error");
                }
            } else {
                throw new JSONException("syntax error");
            }
        }
        jSONLexer.nextToken();
        return new Color(i, i2, i3, i4);
    }

    protected Rectangle parseRectangle(DefaultJSONParser defaultJSONParser) {
        int i;
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        while (jSONLexer.token() != 13) {
            if (jSONLexer.token() == 4) {
                String stringVal = jSONLexer.stringVal();
                jSONLexer.nextTokenWithColon(2);
                int i6 = jSONLexer.token();
                if (i6 == 2) {
                    i = jSONLexer.intValue();
                    jSONLexer.nextToken();
                } else if (i6 == 3) {
                    i = (int) jSONLexer.floatValue();
                    jSONLexer.nextToken();
                } else {
                    throw new JSONException("syntax error");
                }
                if (stringVal.equalsIgnoreCase("x")) {
                    i2 = i;
                } else if (stringVal.equalsIgnoreCase("y")) {
                    i3 = i;
                } else if (stringVal.equalsIgnoreCase("width")) {
                    i4 = i;
                } else if (stringVal.equalsIgnoreCase("height")) {
                    i5 = i;
                } else {
                    throw new JSONException("syntax error, " + stringVal);
                }
                if (jSONLexer.token() == 16) {
                    jSONLexer.nextToken(4);
                }
            } else {
                throw new JSONException("syntax error");
            }
        }
        jSONLexer.nextToken();
        return new Rectangle(i2, i3, i4, i5);
    }

    protected Point parsePoint(DefaultJSONParser defaultJSONParser, Object obj) {
        int i;
        JSONLexer jSONLexer = defaultJSONParser.lexer;
        int i2 = 0;
        int i3 = 0;
        while (jSONLexer.token() != 13) {
            if (jSONLexer.token() == 4) {
                String stringVal = jSONLexer.stringVal();
                if (JSON.DEFAULT_TYPE_KEY.equals(stringVal)) {
                    defaultJSONParser.acceptType("java.awt.Point");
                } else if ("$ref".equals(stringVal)) {
                    return (Point) parseRef(defaultJSONParser, obj);
                } else {
                    jSONLexer.nextTokenWithColon(2);
                    int i4 = jSONLexer.token();
                    if (i4 == 2) {
                        i = jSONLexer.intValue();
                        jSONLexer.nextToken();
                    } else if (i4 == 3) {
                        i = (int) jSONLexer.floatValue();
                        jSONLexer.nextToken();
                    } else {
                        throw new JSONException("syntax error : " + jSONLexer.tokenName());
                    }
                    if (stringVal.equalsIgnoreCase("x")) {
                        i2 = i;
                    } else if (stringVal.equalsIgnoreCase("y")) {
                        i3 = i;
                    } else {
                        throw new JSONException("syntax error, " + stringVal);
                    }
                    if (jSONLexer.token() == 16) {
                        jSONLexer.nextToken(4);
                    }
                }
            } else {
                throw new JSONException("syntax error");
            }
        }
        jSONLexer.nextToken();
        return new Point(i2, i3);
    }

    private Object parseRef(DefaultJSONParser defaultJSONParser, Object obj) {
        JSONLexer lexer = defaultJSONParser.getLexer();
        lexer.nextTokenWithColon(4);
        String stringVal = lexer.stringVal();
        defaultJSONParser.setContext(defaultJSONParser.getContext(), obj);
        defaultJSONParser.addResolveTask(new DefaultJSONParser.ResolveTask(defaultJSONParser.getContext(), stringVal));
        defaultJSONParser.popContext();
        defaultJSONParser.setResolveStatus(1);
        lexer.nextToken(13);
        defaultJSONParser.accept(13);
        return null;
    }
}
