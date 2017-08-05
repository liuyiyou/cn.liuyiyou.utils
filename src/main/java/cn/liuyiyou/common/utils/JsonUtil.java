package cn.liuyiyou.common.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapLikeType;

/**
 * 
 * 名称: JsonUtil <br/> 
 * @author liuyiyou.cn
 * @date 2017年7月25日
 * @version 6.0.0
 */
@SuppressWarnings("unchecked")
public final class JsonUtil {

//	private static final DaojiaAnnotationIntrospector ANNOTATION_INTROSPECTOR = new DaojiaAnnotationIntrospector();

	/** 将日期对象转换为 JSON 格式字符串时的默认日期格式： {@value} */
	public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
    private static final ObjectMapper MAPPER;

    private static final ObjectMapper SENSITIVE_DATA_MAPPER;

    static {
        MAPPER = generateMapper();

        SENSITIVE_DATA_MAPPER = generateMapper();
       // SENSITIVE_DATA_MAPPER.setAnnotationIntrospector(ANNOTATION_INTROSPECTOR);
    }

	/**
	 * 创建 ObjectMapper 对象
	 * 
	 * @return 新创建的 ObjectMapper 实例
	 */
	public static ObjectMapper generateMapper() {
		return generateMapper(Include.ALWAYS);
	}

	/**
	 * 创建 ObjectMapper 对象
	 * 
	 * @param incl
	 *            传入一个枚举值，设置序列化为 json 的 JavaBean 属性的类型：
	 *            <ul>
	 *            <li>{@link Include#ALWAYS} - 全部列入</li>
	 *            <li>{@link Include#NON_DEFAULT} - 字段值和其默认值相同的时候不会列入</li>
	 *            <li>{@link Include#NON_EMPTY} - 字段值为 null、"" 或 length、size 为 0
	 *            的时候不会列入</li>
	 *            <li>{@link Include#NON_NULL} - 字段值为 null 时候不会列入</li>
	 *            </ul>
	 * @return 新创建的 ObjectMapper 实例
	 */
	public static ObjectMapper generateMapper(Include incl) {
		return generateMapper(incl, DEFAULT_DATETIME_FORMAT);
	}

    /**
     * 创建 ObjectMapper 对象
     * 
     * @param incl
     *            传入一个枚举值，设置序列化为 json 的 JavaBean 属性的类型：
     *            <ul>
     *            <li>{@link Include#ALWAYS} - 全部列入</li>
     *            <li>{@link Include#NON_DEFAULT} - 字段值和其默认值相同的时候不会列入</li>
     *            <li>{@link Include#NON_EMPTY} - 字段值为 null、"" 或 length、size 为 0
     *            的时候不会列入</li>
     *            <li>{@link Include#NON_NULL} - 字段值为 null 时候不会列入</li>
     *            </ul>
     * @param dateFormat
     *            日期字符串的格式
     * @return 新创建的 ObjectMapper 实例
     */
    public static ObjectMapper generateMapper(Include incl, String dateFormat) {
        return generateMapper(incl, dateFormat, true);
    }
    
	/**
	 * 创建 ObjectMapper 对象
	 * 
	 * @param incl
	 *            传入一个枚举值，设置序列化为 json 的 JavaBean 属性的类型：
	 *            <ul>
	 *            <li>{@link Include#ALWAYS} - 全部列入</li>
	 *            <li>{@link Include#NON_DEFAULT} - 字段值和其默认值相同的时候不会列入</li>
	 *            <li>{@link Include#NON_EMPTY} - 字段值为 null、"" 或 length、size 为 0
	 *            的时候不会列入</li>
	 *            <li>{@link Include#NON_NULL} - 字段值为 null 时候不会列入</li>
	 *            </ul>
	 * @param dateFormat
	 *            日期字符串的格式
	 * @param serializerEnumAsObject
	 *            是否将枚举序列化成普通对象的形式，如：<code>{"name":"THIS_IS_ENUM_NAME"}</code>
	 * @return 新创建的 ObjectMapper 实例
	 */
	public static ObjectMapper generateMapper(Include incl, String dateFormat, boolean serializerEnumAsObject) {
		/*
		 * JsonFactory jsonFactory = new JsonFactory();
		 * jsonFactory.enable(Feature.ALLOW_COMMENTS);
		 * jsonFactory.enable(Feature.ALLOW_SINGLE_QUOTES);
		 * jsonFactory.enable(Feature.ALLOW_UNQUOTED_FIELD_NAMES);
		 * ObjectMapper objectMapper = new ObjectMapper(jsonFactory);
		 */

		ObjectMapper objectMapper = new ObjectMapper();

		// 设置输入时忽略在 JSON 字符串中存在但 Java 对象实际没有的属性
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// 对于原生数据类型的属性允许 JSON 字符串中的值为 null
		objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
		// 禁止使用 int 代表 Enum 的 order() 来反序列化 Enum，非常危险
		objectMapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, true);
		objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE, true);
		objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
		// 允许数组为单值
		objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false);
		objectMapper.configure(Feature.WRITE_BIGDECIMAL_AS_PLAIN, true);

		if(incl != null) { // 设置输出时包含属性的风格
		    objectMapper.setSerializationInclusion(incl);
		}
		if(dateFormat != null) { // 设置默认日期格式
		    objectMapper.setDateFormat(new SimpleDateFormat(dateFormat));
		}
		objectMapper.setLocale(Locale.CHINA);
		objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		// objectMapper.setFilters(new SimpleFilterProvider().setFailOnUnknownId(false));

		SimpleModule module = new SimpleModule("DaojiaModule");
		module.addSerializer(BigDecimal.class, new DecimalSerializer())
				.addDeserializer(Boolean.class, new BooleanDeserializer())
				.addDeserializer(Date.class, new DateDeserializer());
//		if(serializerEnumAsObject) {
//		    module.addSerializer(Enum.class, new DaojiaEnumSerializer());
//		}
		objectMapper.registerModule(module);
		return objectMapper;
	}

	/**
	 * 将 JSON 字符串转换成 Java 对象
	 * 
	 * @param json
	 *            json 字符串
	 * @param clazz
	 *            要转换的目标类型
	 * @param <T>
	 *            JSON 字符串需要转换的目标类型
	 * @return 转换后的 Java 对象
	 */
	public static <T> T json2Object(String json, Class<T> clazz) {
		if (String.class.equals(clazz)) {
			return (T) json;
		}
		try {
			return MAPPER.readValue(json, clazz);
		} catch (Exception e) {
			throw new RuntimeException("将 " + json + " 转换成 " + clazz + " 对象失败。", e);
		}
	}

	/**
	 * 将 JSON 字符串转换成 Java 对象
	 * 
	 * @param json
	 *            JSON 字符串
	 * @param typeReference
	 *            类型引用对象
	 * @param <T>
	 *            JSON 字符串需要转换的目标类型
	 * @return 转换后的 Java 对象
	 */
	public static <T> T json2Object(String json, TypeReference<T> typeReference) {
		if (String.class.equals(typeReference.getType())) {
			return (T) json;
		}

		try {
			return (T) MAPPER.readValue(json, typeReference);
		} catch (Exception e) {
			throw new RuntimeException("将 " + json + " 转换成 " + typeReference.getType() + " 对象失败。", e);
		}
	}

	/**
	 * 将 JSON 字符串转换成 List
	 * 
	 * @param json
	 *            JSON 字符串
	 * @param elementClass
	 *            List 中元素的类型
	 * @param <T>
	 *            JSON 字符串需要转换的目标类型
	 * @return 转换后得到的 List 实例
	 */
	public static <T> List<T> json2List(String json, Class<T> elementClass) {
		CollectionType typeReference = MAPPER.getTypeFactory().constructCollectionType(ArrayList.class, elementClass);
		try {
			return (List<T>) MAPPER.readValue(json, typeReference);
		} catch (Exception e) {
			throw new RuntimeException("将 " + json + " 转换成 List<" + elementClass + "> 对象失败。", e);
		}
	}

	/**
	 * 将 JSON 字符串转换成 List
	 * 
	 * @param json
	 *            JSON 字符串
	 * @param keyClass
	 *            Map 中 key 的类型
	 * @param valueClass
	 *            Map 中 value 的类型
	 * @param <K>
	 *            the type of keys maintained by this map
	 * @param <V>
	 *            the type of mapped values
	 * @return 转换后得到的 Map 实例
	 */
	public static <K, V> Map<K, V> json2Map(String json, Class<K> keyClass, Class<V> valueClass) {
		MapLikeType typeReference = MAPPER.getTypeFactory().constructMapLikeType(LinkedHashMap.class, keyClass,
				valueClass);
		try {
			return (Map<K, V>) MAPPER.readValue(json, typeReference);
		} catch (Exception e) {
			throw new RuntimeException("将 " + json + " 转换成 Map<" + keyClass + ", " + valueClass + "> 对象失败。", e);
		}
	}

	/**
	 * 将 Java 对象转换成 JSON 字符串
	 * 
	 * @param obj
	 *            要转换成 JSON 字符串的 Java 对象，可以是一个 JavaBea、数组、List、Map 等任何非 null 对象
	 * @return 转换后的 JSON 字符串
	 */
	public static String object2Json(Object obj) {
		return object2Json(obj, false);
	}
	
	/**
	 * 将 Java 对象转换成 JSON 字符串
	 * 
	 * @param obj 要转换成 JSON 字符串的 Java 对象，可以是一个 JavaBea、数组、List、Map 等任何非 null 对象
	 * @param includeSensitiveData 是否含包手机号、身份证号等敏感数据
	 * @return 转换后的 JSON 字符串
	 */
	public static String object2Json(Object obj, boolean includeSensitiveData) {
	    return object2Json(obj, (Include) null, includeSensitiveData);
	}
	
	/**
	 * 将 Java 对象转换成 JSON 字符串
	 * 
	 * @param obj 要转换成 JSON 字符串的 Java 对象，可以是一个 JavaBea、数组、List、Map 等任何非 null 对象
	 * @param incl 需要转换为 JSON 字符串的字段包含规则枚举
	 * @return 转换后的 JSON 字符串
	 */
	public static String object2Json(Object obj, Include incl) {
	    return object2Json(obj, incl, false);
	}
	
	
	/**
	 * 将 Java 对象转换成 JSON 字符串
	 * 
	 * @param obj 要转换成 JSON 字符串的 Java 对象，可以是一个 JavaBea、数组、List、Map 等任何非 null 对象
     * @param incl 需要转换为 JSON 字符串的字段包含规则枚举
     * @param includeSensitiveData 是否含包手机号、身份证号等敏感数据
	 * @return 转换后的 JSON 字符串
	 */
	public static String object2Json(Object obj, Include incl, boolean includeSensitiveData) {
	    ObjectMapper om = null;
	    try {
	        if(incl == null) {
	            om = includeSensitiveData ? SENSITIVE_DATA_MAPPER : MAPPER;
	        } else {
	            om = generateMapper(incl);
	            if(includeSensitiveData) {
	               // om.setAnnotationIntrospector(ANNOTATION_INTROSPECTOR);
	            }
	        }
	        return om.writeValueAsString(obj);
	    } catch (JsonProcessingException e) {
	        throw new RuntimeException("将 " + obj + " 转换成 JSON 字符串失败。", e);
	    }
	}

	/**
	 * 将 Java 对象转换成 JSON 字符串
	 * 
	 * @param obj
	 *            要转换成 JSON 字符串的 Java 对象。该 Java 对象的类定义必须使用 &#64;
	 *            {@link JsonFilter} 注解进行标注
	 * @param properties
	 *            要序列化成 JSON 字符串的属性
	 * @return 转换后的 JSON 字符串
	 * @throws IllegalArgumentException
	 *             - 传入的 obj 对象的类定义没有使用 &#64; {@link JsonFilter} 注解进行标注时，抛出此异常
	 */
	public static String object2Json(Object obj, String[] properties) {
		try {
			return getIncludeObjectWriter(obj.getClass(), properties).writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("将 " + obj + " 转换成 JSON 字符串失败。", e);
		}
	}

	/**
	 * 将 Java 对象转换成 JSON 字符串
	 * 
	 * @param obj
	 *            要转换成 JSON 字符串的 Java 对象。如果 excludeProperties 不为 null 且长度大于 0， 该
	 *            Java 对象的类定义必须使用 &#64;{@link JsonFilter} 注解进行标注
	 * @param excludeProperties
	 *            将 Java 对象序列化成 JSON 字符串时需要排除的属性
	 * @return 转换后的 JSON 字符串
	 * @throws IllegalArgumentException
	 *             - excludeProperties 不为空，且传入的 obj 对象的类定义没有使用 &#64;
	 *             {@link JsonFilter} 注解进行标注时，抛出此异常
	 */
	public static String object2JsonWithExcludeProps(Object obj, String[] excludeProperties) {
		try {
			return getExcludeObjectWriter(obj.getClass(), excludeProperties).writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("将 " + obj + " 转换成 JSON 字符串失败。", e);
		}
	}

	/**
	 * 将 Java 对象转换成 JSON 字符串，并输出到指定的输出流中
	 * 
	 * @param out
	 *            目标输出流
	 * @param obj
	 *            要转换成 JSON 字符串的 Java 对象，可以是一个 JavaBea、数组、List、Map 等任何非 null 对象
	 */
	public static void object2Json(OutputStream out, Object obj) {
		try {
			MAPPER.writeValue(out, obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将 Java 对象转换成 JSON 字符串，并输出到指定的输出流中
	 * 
	 * @param out
	 *            目标输出流
	 * @param obj
	 *            要转换成 JSON 字符串的 Java 对象。该 Java 对象的类定义必须使用 &#64;
	 *            {@link JsonFilter} 注解进行标注
	 * @param properties
	 *            要序列化成 JSON 字符串的属性
	 * @throws IllegalArgumentException
	 *             - 传入的 obj 对象的类定义没有使用 &#64; {@link JsonFilter} 注解进行标注时，抛出此异常
	 */
	public static void object2Json(OutputStream out, Object obj, String[] properties) {
		try {
			getIncludeObjectWriter(obj.getClass(), properties).writeValue(out, obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将 Java 对象转换成 JSON 字符串，并输出到指定的输出流中
	 * 
	 * @param out
	 *            目标输出流
	 * @param obj
	 *            要转换成 JSON 字符串的 Java 对象。如果 excludeProperties 不为 null 且长度大于 0， 该
	 *            Java 对象的类定义必须使用 &#64;{@link JsonFilter} 注解进行标注
	 * @param excludeProperties
	 *            将 Java 对象序列化成 JSON 字符串时需要排除的属性
	 * @throws IllegalArgumentException
	 *             - excludeProperties 不为空，且传入的 obj 对象的类定义没有使用 &#64;
	 *             {@link JsonFilter} 注解进行标注时，抛出此异常
	 */
	public static void object2JsonWithExcludeProps(OutputStream out, Object obj, String[] excludeProperties) {
		try {
			getExcludeObjectWriter(obj.getClass(), excludeProperties).writeValue(out, obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将 Java 对象转换成 JSON 字符串，并输出到指定的输出流中
	 * 
	 * @param write
	 *            目标输出流
	 * @param obj
	 *            要转换成 JSON 字符串的 Java 对象，可以是一个 JavaBea、数组、List、Map 等任何非 null 对象
	 */
	public static void object2Json(Writer write, Object obj) {
		try {
			MAPPER.writeValue(write, obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将 Java 对象转换成 JSON 字符串，并输出到指定的输出流中
	 * 
	 * @param write
	 *            目标输出流
	 * @param obj
	 *            要转换成 JSON 字符串的 Java 对象。该 Java 对象的类定义必须使用 &#64;
	 *            {@link JsonFilter} 注解进行标注
	 * @param properties
	 *            要序列化成 JSON 字符串的属性
	 * @throws IllegalArgumentException
	 *             - 传入的 obj 对象的类定义没有使用 &#64; {@link JsonFilter} 注解进行标注时，抛出此异常
	 */
	public static void object2Json(Writer write, Object obj, String[] properties) {
		try {
			getIncludeObjectWriter(obj.getClass(), properties).writeValue(write, obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将 Java 对象转换成 JSON 字符串，并输出到指定的输出流中
	 * 
	 * @param write
	 *            目标输出流
	 * @param obj
	 *            要转换成 JSON 字符串的 Java 对象。如果 excludeProperties 不为 null 且长度大于 0， 该
	 *            Java 对象的类定义必须使用 &#64;{@link JsonFilter} 注解进行标注
	 * @param excludeProperties
	 *            将 Java 对象序列化成 JSON 字符串时需要排除的属性
	 * @throws IllegalArgumentException
	 *             - excludeProperties 不为空，且传入的 obj 对象的类定义没有使用 &#64;
	 *             {@link JsonFilter} 注解进行标注时，抛出此异常
	 */
	public static void object2JsonWithExcludeProps(Writer write, Object obj, String[] excludeProperties) {
		try {
			getExcludeObjectWriter(obj.getClass(), excludeProperties).writeValue(write, obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将 Java 对象转换成 JSON 字符串，并保存到指定的文件中
	 * 
	 * @param file
	 *            目标输出流
	 * @param obj
	 *            要转换成 JSON 字符串的 Java 对象，可以是一个 JavaBea、数组、List、Map 等任何非 null 对象
	 */
	public static void object2Json(File file, Object obj) {
		try {
			MAPPER.writeValue(file, obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将 Java 对象转换成 JSON 字符串，并保存到指定的文件中
	 * 
	 * @param file
	 *            目标输出流
	 * @param obj
	 *            要转换成 JSON 字符串的 Java 对象。该 Java 对象的类定义必须使用 &#64;
	 *            {@link JsonFilter} 注解进行标注
	 * @param properties
	 *            要序列化成 JSON 字符串的属性
	 * @throws IllegalArgumentException
	 *             - 传入的 obj 对象的类定义没有使用 &#64; {@link JsonFilter} 注解进行标注时，抛出此异常
	 */
	public static void object2Json(File file, Object obj, String[] properties) {
		try {
			getIncludeObjectWriter(obj.getClass(), properties).writeValue(file, obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将 Java 对象转换成 JSON 字符串，并保存到指定的文件中
	 * 
	 * @param file
	 *            目标输出流
	 * @param obj
	 *            要转换成 JSON 字符串的 Java 对象。如果 excludeProperties 不为 null 且长度大于 0， 该
	 *            Java 对象的类定义必须使用 &#64;{@link JsonFilter} 注解进行标注
	 * @param excludeProperties
	 *            将 Java 对象序列化成 JSON 字符串时需要排除的属性
	 * @throws IllegalArgumentException
	 *             - excludeProperties 不为空，且传入的 obj 对象的类定义没有使用 &#64;
	 *             {@link JsonFilter} 注解进行标注时，抛出此异常
	 */
	public static void object2JsonWithExcludeProps(File file, Object obj, String[] excludeProperties) {
		try {
			getExcludeObjectWriter(obj.getClass(), excludeProperties).writeValue(file, obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 数据转换
	 * 
	 * @param fromValue
	 *            原始数据
	 * @param toValueType
	 *            目标类型
	 * @return 目标类型的值
	 */
	public static <T> T convertValue(Object fromValue, Class<T> toValueType) {
		return MAPPER.convertValue(fromValue, toValueType);
	}

	/**
	 * 数据转换
	 * 
	 * @param fromValue
	 *            原始数据
	 * @param toValueType
	 *            目标类型
	 * @return 目标类型的值
	 */
	public static <T> T convertValue(Object fromValue, TypeReference<?> toValueTypeRef) {
		return MAPPER.convertValue(fromValue, toValueTypeRef);
	}

	/**
	 * 数据转换
	 * 
	 * @param fromValue
	 *            原始数据
	 * @param toValueType
	 *            目标类型
	 * @return 目标类型的值
	 * @see com.fasterxml.jackson.databind.type.TypeFactory
	 */
	public static <T> T convertValue(Object fromValue, JavaType toValueType) {
		return MAPPER.convertValue(fromValue, toValueType);
	}

	/**
	 * 将字符串转成 JsonNode 对象
	 * 
	 * @param json
	 *            JSON 字符串
	 * @return JsonNode 对象
	 */
	public static JsonNode fromString(String json) {
		return fromString(json, null);
	}

	/**
	 * 将 JSON 字符串转换为 JsonNode 对象， 如果 JSON 字符串中没有某个属性，而默认值中存在，则从默认值中继承
	 * 
	 * @param json
	 *            JSON 字符串
	 * @param defaultValue
	 *            默认值的 JSON 字符串
	 * @return 转换后的 JsonNode 对象
	 */
	public static JsonNode fromString(String json, String defaultValue) {
		try {
			JsonNode result = MAPPER.readTree(json);
			if (StringUtils.isNotBlank(defaultValue) && (result instanceof ObjectNode)) {
				extend((ObjectNode) result, MAPPER.readTree(defaultValue));
			}
			return result;
		} catch (IOException e) {
			throw new RuntimeException("解析 JSON 字符串失败", e);
		}
	}

	/**
	 * 将资源文件中的 JSON 字符串读取为 JsonNode
	 * 
	 * @param resource
	 *            资源文件的路径
	 * @return JsonNode 实例
	 */
	public static JsonNode fromResource(String resource) {
		return fromResource(resource, null);
	}

	/**
	 * Read a {@link JsonNode} from a resource path.
	 * 
	 * <p>
	 * This method explicitly throws an {@link IOException} if the resource does
	 * not exist, instead of letting a {@link NullPointerException} slip
	 * through.
	 * </p>
	 * 
	 * @param resource
	 *            The path to the resource
	 * @param classLoader
	 *            资源文件对应的类加载器
	 * @return the JSON document at the resource
	 */
	public static JsonNode fromResource(String resource, ClassLoader classLoader) {
		if (classLoader == null) {
			classLoader = Thread.currentThread().getContextClassLoader();
		}

		final URL url = classLoader.getResource(resource);
		if (url == null) {
			throw new RuntimeException("resource " + resource + " not found");
		}

		return fromURL(url);
	}

	/**
	 * Read a {@link JsonNode} from an URL.
	 * 
	 * @param url
	 *            The URL to fetch the JSON document from
	 * @return The document at that URL
	 */
	public static JsonNode fromURL(URL url) {
		try {
			return MAPPER.readTree(url);
		} catch (IOException e) {
			throw new RuntimeException("将 URL [" + url + "] 的内容转换为 JsonNode 失败。", e);
		}
	}

	/**
	 * Read a {@link JsonNode} from a file on the local filesystem.
	 * 
	 * @param path
	 *            the path (relative or absolute) to the file
	 * @return the document in the file
	 */
	public static JsonNode fromPath(String path) {
		return fromFile(new File(path));
	}

	/**
	 * Same as {@link #fromPath(String)}, but this time the user supplies the
	 * {@link File} object instead
	 * 
	 * @param file
	 *            the File object
	 * @return The document
	 */
	public static JsonNode fromFile(File file) {
		try {
			return MAPPER.readTree(file);
		} catch (IOException e) {
			throw new RuntimeException("将文件 [" + file + "] 的内容转换为 JsonNode 失败。", e);
		}
	}

	/**
	 * Read a {@link JsonNode} from a user supplied {@link Reader}
	 * 
	 * @param reader
	 *            The reader
	 * @return the document
	 */
	public static JsonNode fromReader(final Reader reader) {
		try {
			return MAPPER.readTree(reader);
		} catch (Exception e) {
			throw new RuntimeException("从 Reader 加载 JSON 数据发生错误。", e);
		}
	}

	/**
	 * 将输入流中的 JSON 字符串读取为 JsonNode 对象
	 * 
	 * @param in
	 *            包含 JSON 字符串的输入流
	 * @return 转换后的 JsonNode 对象
	 */
	public static JsonNode fromInputStream(InputStream in) {
		try {
			return MAPPER.readTree(in);
		} catch (Exception e) {
			throw new RuntimeException("从输入流加载 JSON 数据发生错误。", e);
		}
	}

	private static ObjectWriter getIncludeObjectWriter(Class<?> clazz, String[] properties) {
		return getObjectWriter(clazz, properties, false);
	}

	private static ObjectWriter getExcludeObjectWriter(Class<?> clazz, String[] excludeProperties) {
		return getObjectWriter(clazz, excludeProperties, true);
	}

	private static ObjectWriter getObjectWriter(Class<?> clazz, String[] properties, boolean exclude) {
	    if(clazz.isArray()) {
	        clazz = clazz.getComponentType();
	    } else if (Collection.class.isAssignableFrom(clazz)) {
	        throw new UnsupportedOperationException("不支持集合类型");
	    } else if (Map.class.isAssignableFrom(clazz)) {
	        throw new UnsupportedOperationException("不支持 Map 类型");
        }
	    
		// 如果是需要排除指定属性，且需要排除的属性列表为空，则直接返回默认的 ObjectWriter
		if (exclude && ArrayUtils.isEmpty(properties)) {
			return MAPPER.writer();
		}

		JsonFilter jsonFilter = clazz.getAnnotation(JsonFilter.class);
		if (jsonFilter == null) {
			throw new IllegalArgumentException(clazz + " 的类定义没有使用 @JsonFilter 注解进行标注时");
		}
		String filterId = jsonFilter.value();
		SimpleBeanPropertyFilter filter = exclude ? SimpleBeanPropertyFilter.serializeAllExcept(properties)
				: SimpleBeanPropertyFilter.filterOutAllExcept(properties);
		return MAPPER.writer(new SimpleFilterProvider().addFilter(filterId, filter));
	}

	/**
	 * 实现 JsonNode 的深层继承，类似于 jQuery 的 extend 方法
	 * 
	 * @param obj
	 *            源对象
	 * @param def
	 *            需要继承的 JsonNode 对象
	 */
	public static void extend(ObjectNode obj, JsonNode def) {
		Iterator<Entry<String, JsonNode>> defaultFields = def.fields();
		Entry<String, JsonNode> entry = null;
		while (defaultFields.hasNext()) {
			entry = defaultFields.next();
			String key = entry.getKey();
			JsonNode defVal = entry.getValue();
			if (defVal == null || defVal.getNodeType() == JsonNodeType.NULL) {
				continue;
			}

			JsonNode value = obj.get(key);
			if (value == null || value.getNodeType() == JsonNodeType.NULL) {
				obj.set(key, defVal);
			} else if (value instanceof ObjectNode) {
				extend((ObjectNode) value, defVal);
			}
		}
	}
}