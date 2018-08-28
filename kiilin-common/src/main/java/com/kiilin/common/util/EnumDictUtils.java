package com.kiilin.common.util;

import com.kiilin.common.annotation.EnumDictType;
import com.kiilin.common.config.IDictEnum;
import com.kiilin.modules.pojo.dto.SysDict;
import com.kiilin.modules.service.SysDictService;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * 自定义枚举配置 用于查询 数据字典 动态增加枚举项
 * @author wagk
 */
@Component
public class EnumDictUtils {


    @Value("${mybatis-plus.typeEnumsPackage}")
    String packageName;

    static EnumDictUtils utils;


    @Autowired
    SysDictService dictService;

    /**
     * 解决静态方法注入问题
     */
    @PostConstruct
    public void init() {
        utils = this;
        utils.packageName = this.packageName;
        utils.dictService = this.dictService;

    }

    /**
     * 自定义枚举配置 用于查询 数据字典 动态增加枚举项
     *
     * @author wangkai
     */
    public static void enumDict() {


        // 获取实现自定义注解的枚举的class
        Reflections reflections = new Reflections(utils.packageName);
        Set<Class<?>> classesList = reflections.getTypesAnnotatedWith(EnumDictType.class);

        // 遍历
        for (Class<?> clazz : classesList) {
            createEnumDict(clazz);
        }
    }


    /**
     * 创建枚举菜单
     *
     * @param clazz
     */
    public static void createEnumDict(Class<?> clazz) {
        // 获取type值
        EnumDictType annotation = clazz.getAnnotation(EnumDictType.class);
        if (null != annotation && clazz.isEnum()) {

            // 清空
            cleanEnum(clazz);

            String type = annotation.type();

            // 查询字典
            List<SysDict> list = utils.dictService.selectByType(type);

            for (SysDict dict : list) {

                // 获取枚举名称
                String enumName = StringUtils.upperCase(dict.getValue());
                boolean flag = false;

                // 判断值是否已存在
                for (IDictEnum iEnum : (IDictEnum[]) clazz.getEnumConstants()) {
                    if (StringUtils.equalsIgnoreCase(enumName, (String) iEnum.getValue())) {
                        flag = true;
                    }
                }

                // 如果不存在 则创建
                if (!flag) {
                    EnumUtils.addEnum(clazz, enumName, new Class[]{String.class, String.class}, new Object[]{dict.getValue(), dict.getLabel()});
                }
            }
        }
    }


    /**
     * 清空原有枚举值
     *
     * @param clazz
     * @param <T>
     */
    private static <T extends Enum<?>> void cleanEnum(Class<?> clazz) {
        Field valuesField = null;
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().contains("$VALUES")) {
                valuesField = field;
                break;
            }
        }

        AccessibleObject.setAccessible(new Field[]{valuesField}, true);

        try {
            List<T> values = new ArrayList<T>();

            // 5. Set new values field
            EnumUtils.setFailsafeFieldValue(valuesField, null, values.toArray((T[]) Array.newInstance(clazz, 0)));

            // 6. Clean enum cache
            EnumUtils.cleanEnumCache(clazz);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

}
