package com.kiilin.common.abstracts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.kiilin.common.abstracts.dao.AbstractDao;
import com.kiilin.common.abstracts.service.AbstractService;
import com.kiilin.common.datatables.DataTablePager;
import com.kiilin.common.util.CamelNameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * service的超类实现
 *
 * @author wangkai
 */
public class AbstractServiceImpl<M extends AbstractDao<T>, T> extends ServiceImpl<M, T> implements AbstractService<M, T> {


    /**
     * 当前泛型真实类型的Class
     */
    private Class<T> modelClass;

    public AbstractServiceImpl() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        modelClass = (Class<T>) pt.getActualTypeArguments()[1];
    }

    @Override
    public DataTablePager getPager(Map map) {
        // map 转json
        JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(map));

        // 反序列化
        DataTablePager pager = JSONObject.toJavaObject(json, DataTablePager.class);
        return pager;
    }

    @Override
    public DataTablePager<T> selectPage(Map map) {
        // map 转json
        JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(map));

        // 反序列化
        DataTablePager<T> pager = JSONObject.toJavaObject(json, DataTablePager.class);
        // 获取查询条件的
        T t = JSONObject.toJavaObject(json, modelClass);
        List<T> list = baseMapper.selectPage(pager.getPage(), this.eq2Like(t));
        pager.setData(list);
        return pager;
    }

    @Override
    public Wrapper<T> eq2Like(T t) {
        Wrapper<T> wrapper = new EntityWrapper<>();

        // 转为JSONObject
        JSONObject json = JSONObject.parseObject(JSON.toJSONString(t));
        // 遍历JSONObject
        Set<String> keys = json.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            // 属性名
            String key = iterator.next();

            // 属性值
            Object o = json.get(key);


            // 表字段名
            Field field = FieldUtils.getField(modelClass, key, true);
            // 获取字段上的注解
            TableField tableField = field.getAnnotation(TableField.class);
            String columName = null;
            if (null != tableField) {
                // 注解中的
                columName = tableField.value();
            }

            // 没有注解或者注解未配置字段名时使用驼峰转换
            if (StringUtils.isBlank(columName)) {
                columName = CamelNameUtils.camel2underscore(key);
            }

            // 注入条件
            if (o != null) {
                // string类型注入为模糊查询 id 不注入
                if (o instanceof String
                        && StringUtils.isNotBlank((String) o)
                        && !StringUtils.equalsIgnoreCase("id", key)
                        && StringUtils.indexOf(key, "Id") == -1
                        && !StringUtils.startsWith((String) o, "=")
                        ) {
                    wrapper.like(columName, (String) o);
                } else if (o instanceof String
                        && StringUtils.startsWith((String) o, "=")) {
                    // "=" 开头
                    wrapper.eq(columName, ((String) o).split("=")[1]);
                } else if (o instanceof Date) {
                    // 时间类型不注入
                } else {
                    wrapper.eq(columName, o);
                }
            }
        }


        return wrapper;
    }

}
