package org.lyh.validate;

import org.lyh.validate.config.ValidationType;

import java.lang.reflect.Field;
import java.text.MessageFormat;

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
        this(baseEntity,false);
    }

    public BeanValidator(BaseEntity baseEntity, boolean direct) {
        this.baseEntity = baseEntity;
        entityClass = baseEntity.getClass();
        rules = baseEntity.rules();
    }

    @Override
    public boolean validate() {
        errors.clear();
        if(rules==null){
            return true;
        }
        for (int i = 0; i < rules.length; i++) {
            String [] rule = rules[i],fields = rule[0].split(",");
            validationType = convValidationType(rule[1]);
            setParams(rule);
            try {
                Class validationClass = getValidationClass(validationType);
                for (int j = 0; j < fields.length; j++) {
                    if(direct && getError(fields[j]) != null){ continue; }

                    field = entityClass.getDeclaredField(fields[j]);
                    field.setAccessible(true);
                    value = field.get(baseEntity);
                    if(value != null || validationType == ValidationType.REQUIRED){
                        if(!(Boolean)validationClass.getMethod("validate").invoke(this,new Object[]{value,params})){
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

    private Class getValidationClass(ValidationType validationType) {
        return null;
    }

    protected ValidationType convValidationType(String s) {
        return null;
    }

    @Override
    protected void handleError() {
        String name = this.baseEntity.getLabel(field.getName()) != null
                ? this.baseEntity.getLabel(field.getName()) : field.getName(),
                message = MessageFormat.format(Messages.getMsg(validationType),name);
        this.addError(field.getName(), validationType, message);
    }
}
