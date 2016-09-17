package org.lyh.validate;

import java.io.File;

/**
 * @author lvyahui (lvyahui8@gmail.com,lvyahui8@126.com)
 * @since 2016/9/16 22:16
 */
public class MapValidator extends Validator {
    public MapValidator() {
        this(false);
    }

    public MapValidator(boolean direct) {
        super(direct);
    }

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    protected void handleError() {

    }

    @Override
    public File getXmlFile() {
        return null;
    }

    @Override
    public Object getValue(String prop) throws NoSuchFieldException, IllegalAccessException {
        return null;
    }
}
