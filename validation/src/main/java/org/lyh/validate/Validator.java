package org.lyh.validate;


import org.lyh.validate.config.ParameterType;
import org.lyh.validate.config.ValidationType;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by lvyahui on 2015-06-27.
 *
 */
public abstract class Validator {

    protected Map<ParameterType,Object> params = new HashMap<ParameterType,Object>();

    /**
     * 验证失败的错误信息，形式如下
     * {"username":{"REQUIRED":"用户名必须为空",...},...}
     */
    protected Map<String,Map<ValidationType,String>> errors
        = new HashMap<String,Map<ValidationType,String>>();


    /**
     * 当前执行的校验
     */
    protected ValidationType validationType;

    /**
     * 当前被校验字段的值
     */
    protected Object value;

    /**
     * 是否短路
     */
    protected boolean direct;

    protected String [][] rules ;

    /**
     *
     */
    public Validator() {
        this(false);
    }

    public Validator(boolean direct){
        this.direct = direct;
    }

    public Map<String,Map<ValidationType,String>> getErrors() {
        return errors;
    }

    public Map<ValidationType,String> getError(String prop){
        return this.errors.get(prop);
    }

    public void addError(String prop,ValidationType validatorType,String message){
        Map<ValidationType,String> error = this.errors.get(prop);
        if(error==null || error.size() == 0){
            error = new HashMap<ValidationType,String>();
            errors.put(prop, error);
        }
        error.put(validatorType,message);
    }

    public void setRules(String[][] rules) {
        this.rules = rules;
    }

    public abstract boolean validate();

    protected void setParams(String [] rule) {
        params.clear();
        // 取附加参数
        switch (validationType){
            case REGEX:
                params.put(ParameterType.REGEX,rule[3]);
                break;
            case EQUALS:
                params.put(ParameterType.PROP, rule[2]);
                break;
            case LENGTH:
                if(rule[2] != null) {
                    params.put(ParameterType.MIN, Integer.valueOf(rule[2]));
                }
                if(rule.length >= 4){
                    params.put(ParameterType.MAX,Integer.valueOf(rule[3]));
                }
                break;
            default:
        }
    }

    protected abstract void handleError() ;

}
