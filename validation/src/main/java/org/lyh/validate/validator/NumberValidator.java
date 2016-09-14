package org.lyh.validate.validator;

import org.lyh.validate.config.ParameterType;

import java.util.Map;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2016/9/15 0:07
 */
public class NumberValidator implements IValidator {
    public boolean validate(Object value, Map<ParameterType,Object> params) {
        if(value.getClass().getGenericSuperclass() == Number.class){
            return true;
        }else if(((String)value).matches("^\\d+$")){
            return true;
        }
        return false;
    }
}
