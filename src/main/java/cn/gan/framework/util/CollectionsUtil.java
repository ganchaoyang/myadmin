package cn.gan.framework.util;

import java.util.Collection;
import java.util.Collections;

/**
 * @author ganchaoyang
 * @date 2018/10/11 16:23
 * @description
 */
public class CollectionsUtil {

    /**
     * 判断集合是否为null或者空
     * @param collection
     * @return
     */
    public static boolean isNullOrEmpty(Collection collection) {
        return (null == collection || collection.isEmpty());
    }

    /**
     * 判断集合是否不为null或者空
     * @param collection
     * @return
     */
    public static boolean isNotNullOrEmpty(Collection collection) {
        return !isNullOrEmpty(collection);
    }

}
