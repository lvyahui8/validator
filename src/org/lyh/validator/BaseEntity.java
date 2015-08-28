package org.lyh.validator;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
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
     * ����У��
     * @return У��
     */
    public boolean validate() {
        return this.validator.validate();
    }

    /**
     * ���У��ʧ��ͨ������ȡ������Ϣ
     * @return ������Ϣ
     */
    public Map<String,Map<String,String>> getErrors(){
        return this.validator.getErrors();
    }

    /**
     * У�����
     * @return У�����
     */

    public String[][] labels(){return null;}

    /**
     * �ֶη���
     * @return �ֶη���
     */
    public String [][] rules(){return null;}

}
