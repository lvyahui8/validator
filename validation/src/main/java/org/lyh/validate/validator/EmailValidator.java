package org.lyh.validate.validator;

import org.lyh.validate.config.ParameterType;

import java.util.Map;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2016/9/15 0:10
 */
public class EmailValidator implements IValidator {
    public boolean validate(Object value, Map<ParameterType,Object> params) {
        return ((String) value).matches("^\\w+@\\w+.\\w+.*\\w*$");
    }
}
