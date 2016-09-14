package org.lyh.validate.validator;

import org.lyh.validate.config.ParameterType;

import java.sql.Timestamp;
import java.util.Map;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2016/9/15 0:36
 */
public class TimestampValidator implements IValidator {
    public boolean validate(Object value, Map<ParameterType, Object> params) {
        if(value.getClass().equals(Timestamp.class)){
            return true;
        }
        return false;
    }
}
