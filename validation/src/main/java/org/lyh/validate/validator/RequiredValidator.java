package org.lyh.validate.validator;

import org.lyh.validate.config.ParameterType;

import java.util.Map;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2016/9/15 0:03
 */
public class RequiredValidator implements IValidator{

    public boolean validate(Object value, Map<ParameterType,Object> params) {
        if(value!=null){
            if(value.getClass() == String.class
                    && "".equals(((String)value).trim())){
                return false;
            }
            return true;
        }
        return false;
    }
}
