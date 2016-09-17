package org.lyh.validate;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.lyh.validate.config.ParameterType;
import org.lyh.validate.config.ValidationType;
import org.lyh.validate.validator.IValidator;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


/**
 *
 * Created by lvyahui on 2015-06-27.
 */
public abstract class Validator {

    protected Map<ParameterType, Object> params = new HashMap<ParameterType, Object>();

    /**
     * 验证失败的错误信息，形式如下
     * {"username":{"REQUIRED":"用户名必须为空",...},...}
     */
    protected Map<String, Map<ValidationType, String>> errors
            = new HashMap<String, Map<ValidationType, String>>();


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

    protected Map<String, Map<ValidationType, Map<ParameterType, Object>>> rules;

    /**
     *
     */
    public Validator() {
        this(false);
    }

    public Validator(boolean direct) {
        this.direct = direct;
    }

    public Map<String, Map<ValidationType, String>> getErrors() {
        return errors;
    }

    public Map<ValidationType, String> getError(String prop) {
        return this.errors.get(prop);
    }

    public void addError(String prop, ValidationType validatorType, String message) {
        Map<ValidationType, String> error = this.errors.get(prop);
        if (error == null || error.size() == 0) {
            error = new HashMap<ValidationType, String>();
            errors.put(prop, error);
        }
        error.put(validatorType, message);
    }

    public abstract boolean validate();

    protected void setParams(String[] rule) {
        params.clear();
        // 取附加参数
        switch (validationType) {
            case REGEX:
                params.put(ParameterType.REGEX, rule[3]);
                break;
            case EQUALS:
                params.put(ParameterType.PROP, rule[2]);
                break;
            case LENGTH:
                if (rule[2] != null) {
                    params.put(ParameterType.MIN, Integer.valueOf(rule[2]));
                }
                if (rule.length >= 4) {
                    params.put(ParameterType.MAX, Integer.valueOf(rule[3]));
                }
                break;
            default:
        }
    }

    protected abstract void handleError();

    public Class getValidationClass(ValidationType validationType) throws ClassNotFoundException {
        String pkgName = IValidator.class.getPackage().getName();
        String name = validationType.name().toLowerCase();
        char [] chars = name.toCharArray();
        chars[0] = chars[0] >= 'a' && chars[0] <= 'z' ? (char) (chars[0] - 32) : chars[0];
        String fullName = pkgName.concat(".").concat(String.copyValueOf(chars)).concat("Validator");
        return Class.forName(fullName);
    }

    public IValidator getValidator(ValidationType validationType) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class clazz = this.getValidationClass(validationType);
        Object obj = clazz.newInstance();
        return obj instanceof IValidator ? (IValidator) obj : null;
    }

    public void loadConfiguration() {
        File xmlFile = getXmlFile();
        SAXReader reader = new SAXReader();
        if(this.rules == null){
            this.rules
                    = new HashMap<String, Map<ValidationType, Map<ParameterType, Object>>>();
        }
        try {
            Document document = reader.read(xmlFile);
            Element root = document.getRootElement();
            Element rules = root.element("rules");
            List fields = rules.elements("field");
            for (Object item : fields) {
                Element field = (Element) item;
                String prop = field.attributeValue("name");
                Map<ValidationType, Map<ParameterType, Object>> ruleItem
                        = new HashMap<ValidationType, Map<ParameterType, Object>>();
                List fieldRules = field.elements("rule");
                for (Object obj : fieldRules) {
                    Element fieldRuleItem = (Element) obj;
                    fetchTypeAndParams(fieldRuleItem,ruleItem);
                }
                this.rules.put(prop,ruleItem);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Map<ValidationType, Map<ParameterType, Object>>> getRules() {
        return rules;
    }

    private void fetchTypeAndParams(Element fieldRuleItem, Map<ValidationType, Map<ParameterType, Object>> ruleItem) {
        ValidationType vType = null;
        Map<ParameterType, Object> ruleParams = new HashMap<ParameterType, Object>();
        String name = fieldRuleItem.attributeValue("name").toUpperCase();

        if (ValidationType.REQUIRED.name().equals(name)) {
            vType = ValidationType.REQUIRED;
        } else if (ValidationType.EQUALS.name().equals(name)) {
            vType = ValidationType.EQUALS;
            ruleParams.put(ParameterType.PROP,fieldRuleItem.attributeValue("prop"));
        } else if (ValidationType.EMAIL.name().equals(name)){
            vType = ValidationType.EMAIL;
        } else if (ValidationType.REGEX.name().equals(name)){
            vType = ValidationType.REGEX;
            ruleParams.put(ParameterType.REGEX,Pattern.compile(fieldRuleItem.attributeValue("pattern")));
        } else if (ValidationType.LENGTH.name().equals(name)){
            vType = ValidationType.LENGTH;
            ruleParams.put(ParameterType.MIN,Integer.valueOf(fieldRuleItem.attributeValue("min")));
            ruleParams.put(ParameterType.MAX,Integer.valueOf(fieldRuleItem.attributeValue("max")));
        }

        if(vType != null){
            ruleItem.put(vType,ruleParams);
        }
    }


    public abstract File getXmlFile();

    public abstract Object getValue(String prop) throws NoSuchFieldException, IllegalAccessException;
}
