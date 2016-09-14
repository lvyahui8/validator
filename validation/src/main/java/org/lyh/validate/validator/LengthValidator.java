package org.lyh.validate.validator;

import org.lyh.validate.config.ParameterType;

import java.util.Map;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2016/9/15 0:39
 */
public class LengthValidator implements IValidator {
    public boolean validate(Object value, Map<ParameterType, Object> params) {
        String val = (String) value;
        Integer min = (Integer) params.get(ParameterType.MIN);
        Integer max = (Integer) params.get(ParameterType.MAX);

        return val.length() > min && (max == null || val.length() < max);
    }
}
