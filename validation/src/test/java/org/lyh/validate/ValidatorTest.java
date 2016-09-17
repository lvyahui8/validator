package org.lyh.validate;

import com.alibaba.fastjson.JSON;
import junit.framework.TestCase;
import org.lyh.validate.config.ValidationType;
import org.lyh.validate.entity.UserEntity;
import org.lyh.validate.validator.IValidator;

import java.io.File;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2016/9/17 10:22
 */
public class ValidatorTest extends TestCase {

    private Validator validator;

    public void setUp() throws Exception {
        super.setUp();
        validator = new BeanValidator(new UserEntity());
    }

    public void testLoadConfiguration() throws Exception {
        validator.loadConfiguration();
        System.out.println(JSON.toJSON(validator.getRules()));
    }

    public void testGetXmlFile() throws Exception {

        File file = validator.getXmlFile();
        if(file != null && file.exists()){
            System.out.println(file.getAbsolutePath());
        }
    }

    public void testGetValidationClass() throws Exception {
        Class clazz = validator.getValidationClass(ValidationType.EQUALS);
        System.out.println(clazz);
    }

    public void testGetValidator() throws Exception {
        IValidator validator = this.validator.getValidator(ValidationType.EQUALS);
        System.out.println(validator);
    }
}