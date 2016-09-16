package org.lyh.validate;

import org.lyh.validate.config.ValidationType;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取消息
 * Created by lvyahui on 2015-06-28.
 */
public class Messages {
    private static Map<ValidationType,String> msgMap = new HashMap<ValidationType,String>();

    static{
        msgMap.put(ValidationType.REQUIRED,"{0}必须填写");
        msgMap.put(ValidationType.EMAIL,"{0}不合法");
        msgMap.put(ValidationType.EQUALS,"{0}与{1}不相同");
        msgMap.put(ValidationType.LENGTH,"{0}长度必须在{1}-{2}之间");
        msgMap.put(ValidationType.REGEX,"{0}不符合表达式");
        msgMap.put(ValidationType.TIMESTAMP,"{0}不是合法的日期格式");
        msgMap.put(ValidationType.NUMBER,"{0}不是数字格式");
        msgMap.put(ValidationType.URL,"{0}不是合法的url");
    }

    public static String getMsg(ValidationType validateType) {
        return msgMap.get(validateType);
    }
}
