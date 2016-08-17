package org.lyh;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created by lvyahui on 2015-06-27.
 */
public class BaseEntity {

    private Validator validator = new Validator(this);

    protected Map<String,String> labelMap = new HashMap<String,String>();

    {
        String [][] allLabels = labels();
        if(allLabels != null){
            for(String [] label : allLabels){
                String prop = label[0],propLabel = label[1];
                if(prop != null && hasProp(prop) && propLabel != null){
                    labelMap.put(prop,propLabel);
                }
            }
        }
    }

    public boolean hasProp(String prop) {
        try {
            Field field = this.getClass().getDeclaredField(prop);
            return true;
        } catch (NoSuchFieldException e) {
            return false;
        }
    }

    public String getLabel(String prop){
        return labelMap.get(prop);
    }

    /**
     * 执行校验
     * @return 校验结果
     */
    public boolean validate() {
        return this.validator.validate();
    }

    /**
     * 获取错误信息
     * @return 错误信息
     */
    public Map<String,Map<String,String>> getErrors(){
        return this.validator.getErrors();
    }

    /**
     * 字段的文本翻译
     * @return 翻译数组
     */

    public String[][] labels(){return null;}

    /**
     * 验证规则
     * @return 验证规则数组
     */
    public String [][] rules(){return null;}

}
