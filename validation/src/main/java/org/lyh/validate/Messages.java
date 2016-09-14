package org.lyh.validate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lvyahui on 2015-06-28.
 */
public class Messages {
    private static Map<String,String> msgMap = new HashMap<String,String>();

    static{
        msgMap.put(Validator.required,"{0}必须填写");
        msgMap.put(Validator.email,"{0}不合法");
        msgMap.put(Validator.equals,"{0}与{1}不相同");
        msgMap.put(Validator.length,"{0}长度必须在{1}-{2}之间");
        msgMap.put(Validator.regex,"{0}不符合表达式");
        msgMap.put(Validator.timestamp,"{0}不是合法的日期格式");
        msgMap.put(Validator.number,"{0}不是数字格式");
        msgMap.put(Validator.url,"{0}不是合法的url");
    }

    public static String getMsg(String validateType) {
        return msgMap.get(validateType);
    }
}
