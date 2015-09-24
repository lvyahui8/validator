# validator
a java background check plug-in
Flexible back office verification plugin

### Support

* required 
* length : Parameters include 'min' and 'max'(nullable)
* number
* equals : Paramerter include 'field'
* email
* url
* regex  : Paramerter include 'pattern'
* timestamp
* ....
### Use
``` Java
package org.lyh.validator;

import java.sql.Timestamp;

/**
 * Created by lvyahui on 2015-06-26.
 */

public class UserEntity extends BaseEntity {
    private String username;
    private String password;
    private String name;
    private Integer gold;
    private Integer progress;
    private Timestamp createdAt;
    private String email;
    private String phone;
    private String site;
    // 特殊表单数据
    private String rePassword;
    /*
    * getter setter
    */
    
    /**
    ** Validate rules
    ** {"fields","rule","param",...}
    **/
    @Override
    public String[][] rules() {
        return new String [][] {
                {"username,password,email,gold,progress,phone","required"},
                {"username,password","length","6","14"},
                {"rePassword","equals","password"},
                {"email","email","\\w{6,12}"},
                {"createdAt","timestamp"},
                {"phone","number"},
                {"site","url"}
        };
    }

    /**
    ** translate
    ** {"field","transFiled"}
    **/
    public String[][] labels() {
        return new String[][]{
                {"username","用户名"},
                {"password","密码"},
                {"rePassword","确认密码"},
                {"email","邮箱"},
                {"progress","进度"},
                {"phone","电话"},
                {"gold","金币"}
        };
    }

}

```

``` java
UserEntity userEntity = new UserEntity();
userEntity.fill(request.getParameterMap());
if(userEntity.validate()){
    // success
}else{
    // errors
    Map<String,Object> errors = userEntity.getErrors();
    // ...
    // used errors
    // ...
}
```
