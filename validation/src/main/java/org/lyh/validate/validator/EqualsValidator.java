package org.lyh.validate.validator;

import org.lyh.validate.config.ParameterType;

import java.util.Map;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2016/9/15 0:31
 */
public class EqualsValidator implements IValidator {
    public boolean validate(Object value, Map<ParameterType, Object> params) {
        // 取得待比较的字段值
        Object equalsValue = params.get(ParameterType.PROP);
        return value != null ? value.equals(equalsValue) : equalsValue == null;
    }
}
