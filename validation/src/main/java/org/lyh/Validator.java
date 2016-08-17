package org.lyh;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.*;

/**
 * Created by lvyahui on 2015-06-27.
 *
 */
public class Validator {
    /**
     * 校验类型
     */
    public static final String required = "required";
    public static final String length = "length";
    public static final String number = "number";
    public static final String equals = "equals";
    public static final String email = "email";
    public static final String url = "url";
    public static final String regex = "regex";
    public static final String timestamp = "timestamp";

    /**
     * 附加参数类型
     */
    private static final int PATTERN = 1;
    private static final int MIN = 2;
    private static final int MAX = 3;
    private static final int PROP = 4;

    private Map<Integer,Object> params = new HashMap<Integer,Object>();
    /**
     * 验证失败的错误信息，形式如下
     * {"username":{"REQUIRED":"用户名必须为空",...},...}
     */
    protected Map<String,Map<String,String>> errors
        = new HashMap<String,Map<String,String>>();

    /**
     * 被校验的实体
     */
    private BaseEntity baseEntity;

    /**
     * 被校验实体的类型
     */
    private Class entityClass = null;

    /**
     * 当前正在被校验的字段
     */
    private Field field;
    /**
     * 当前执行的校验
     */
    private String validateType ;

    /**
     * 当前被校验字段的值
     */
    private Object value;

    /**
     * 是否短路
     */
    private boolean direct;

    private String [][] rules ;

    /**
     *
     * @param baseEntity
     */
    public Validator(BaseEntity baseEntity) {
        this(baseEntity,false);
    }

    public Validator(BaseEntity baseEntity,boolean direct){
        this.baseEntity = baseEntity;
        entityClass = baseEntity.getClass();
        rules = baseEntity.rules();
    }

    public Map<String,Map<String,String>> getErrors() {
        return errors;
    }

    public Map<String,String> getError(String prop){
        return this.errors.get(prop);
    }

    public void addError(String prop,String validatorType,String message){
        Map<String,String> error = this.errors.get(prop);
        if(error==null || error.size() == 0){
            error = new HashMap<String,String>();
            errors.put(prop, error);
        }
        error.put(validatorType,message);
    }

    public void setRules(String[][] rules) {
        this.rules = rules;
    }

    public boolean required(){
        if(value!=null){
            if(value.getClass() == String.class && "".equals(((String)value).trim())){
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean number(){
        if(value.getClass().getGenericSuperclass() == Number.class){
            return true;
        }else if(((String)value).matches("^\\d+$")){
            return true;
        }
        return false;
    }

    public boolean email(){
        return ((String) value).matches("^\\w+@\\w+.\\w+.*\\w*$");
    }

    public boolean url(){
        return ((String)value).matches("^([a-zA-Z]*://)?([\\w-]+\\.)+[\\w-]+(/[\\w\\-\\.]+)*[/\\?%&=]*$");
    }

    public boolean regex(){
        return ((String)value).matches((String) params.get(regex));
    }

    public boolean equals() throws NoSuchFieldException, IllegalAccessException {
        String prop = (String) params.get(PROP);
        Field equalsField = entityClass.getDeclaredField(prop);
        equalsField.setAccessible(true);
        return value.equals(equalsField.get(baseEntity));
    }
    public boolean timestamp(){
        if(field.getType().equals(Timestamp.class)){
            return true;
        }
        return false;
    }
    public boolean length() {
        String val = (String) value;
        Integer min = (Integer) params.get(MIN);
        Integer max = (Integer) params.get(MAX);

        return val.length() > min && (max == null || val.length() < max);


    }

    public boolean validate(){
        errors.clear();
        if(rules==null){
            return true;
        }

        for (int i = 0; i < rules.length; i++) {
            String [] rule = rules[i],fields = rule[0].split(",");
            validateType = rule[1];
            setParams(rule);
            try {
                Method validateMethod = this.getClass()
                    .getMethod(validateType);
                for (int j = 0; j < fields.length; j++) {
                    if(direct && getError(fields[j]) != null){ continue; }

                    field = entityClass.getDeclaredField(fields[j]);
                    field.setAccessible(true);
                    value = field.get(baseEntity);
                    if(value != null || (value == null && validateType == required)){
                        if(!(Boolean)validateMethod.invoke(this)){
                            handleError();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private void setParams(String [] rule) {
        params.clear();
        // 取附加参数
        switch (validateType){
            case regex:
                params.put(PATTERN,rule[3]);
                break;
            case equals:
                params.put(PROP, rule[2]);
                break;
            case length:
                if(rule[2] != null) {
                    params.put(MIN, Integer.valueOf(rule[2]));
                }
                if(rule.length >= 4){
                    params.put(MAX,Integer.valueOf(rule[3]));
                }
                break;
            default:
        }
    }

    private void handleError() {
        String name = this.baseEntity.getLabel(field.getName()) != null
                ? this.baseEntity.getLabel(field.getName()) : field.getName(),
                message = MessageFormat.format(Messages.getMsg(validateType),name);
        this.addError(field.getName(), validateType, message);
    }

}
