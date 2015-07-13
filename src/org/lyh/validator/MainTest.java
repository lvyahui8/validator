package org.lyh.validator;

/**
 * Created by lvyahui on 2015-06-28.
 */
public class MainTest {
    public static void main(String[] args) {

        UserEntity userEntity = new UserEntity();
        userEntity.validate();
        System.out.println(userEntity.getErrors());

        userEntity.setUsername("lvyahui");
        userEntity.setRePassword("admin888");
        userEntity.setPassword("admin888");
        userEntity.setEmail("lvyaui82.com");
        userEntity.validate();
        System.out.println(userEntity.getErrors());

        userEntity.setEmail("lvyaui8@12.com");
        userEntity.setPhone("hjhjkhj7867868");
        userEntity.setGold(1);
        userEntity.setSite("www.baidu.com");

        userEntity.validate();

        System.out.println(userEntity.getErrors());
        // ([a-zA-Z]*://)?([\w-]+\.)+[\w-]+(/[\w-]+)*[/?%&=]*
        userEntity.setSite("http://www.baidu.com/index.php");
        userEntity.setProgress(123);
        userEntity.setPhone("156464564512");
        userEntity.validate();

        System.out.println(userEntity.getErrors());

    }

}
