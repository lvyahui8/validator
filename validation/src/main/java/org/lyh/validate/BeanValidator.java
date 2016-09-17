package org.lyh.validate;

import org.lyh.validate.config.ParameterType;
import org.lyh.validate.config.ValidationType;
import org.lyh.validate.entity.BaseEntity;
import org.lyh.validate.validator.IValidator;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Map;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2016/9/16 22:16
 */
public class BeanValidator extends Validator {
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

    public BeanValidator(BaseEntity baseEntity) {
        this(baseEntity, false);
    }

    public BeanValidator(BaseEntity baseEntity, boolean direct) {
        this.baseEntity = baseEntity;
        entityClass = baseEntity.getClass();
        this.direct = direct;
    }

    @Override
    public boolean validate() {
        errors.clear();
        loadConfiguration();
        if (rules == null) {
            return true;
        }
        for (Map.Entry<String, Map<ValidationType, Map<ParameterType, Object>>> item
                : this.rules.entrySet()){
            String prop = item.getKey();
            Map<ValidationType,Map<ParameterType,Object>> validationItems = item.getValue();
            for (Map.Entry<ValidationType,Map<ParameterType,Object>> validationItem :
                    validationItems.entrySet()){
                this.validationType = validationItem.getKey();
                try {
                    IValidator validator = getValidator(validationType);
                    if(validator != null){
                        if(validator.validate(getValue(prop),validationItem.getValue())){
                            handleError();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
//        for (int i = 0; i < rules; i++) {
//            String[] rule = rules[i], fields = rule[0].split(",");
//            validationType = convValidationType(rule[1]);
//            setParams(rule);
//            try {
//                Class validationClass = getValidationClass(validationType);
//                for (int j = 0; j < fields.length; j++) {
//                    if (direct && getError(fields[j]) != null) {
//                        continue;
//                    }
//
//                    field = entityClass.getDeclaredField(fields[j]);
//                    field.setAccessible(true);
//                    value = field.get(baseEntity);
//                    if (value != null || validationType == ValidationType.REQUIRED) {
//                        if (!(Boolean) validationClass.getMethod("validate").invoke(this, new Object[]{value, params})) {
//                            handleError();
//                        }
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        return true;
    }

    public Object getValue(String prop) throws NoSuchFieldException, IllegalAccessException {
        Field field = entityClass.getDeclaredField(prop);
        field.setAccessible(true);
        return field.get(baseEntity);
    }

    @Override
    protected void handleError() {
        String name = this.baseEntity.getLabel(field.getName()) != null
                ? this.baseEntity.getLabel(field.getName()) : field.getName(),
                message = MessageFormat.format(Messages.getMsg(validationType), name);
        this.addError(field.getName(), validationType, message);
    }

    @Override
    public File getXmlFile() {
        URL url = this.entityClass.getResource("");
        File xmlFile = null;
        if (url != null) {
            String basePath = url.getPath();
            xmlFile = new File(basePath + this.entityClass.getSimpleName() + ".xml");
        }

        return xmlFile;
    }
}
