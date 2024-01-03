package com.jason;

import com.jason.model.User;
import com.jason.util.ObjectUtil;
import org.junit.Test;

/**
 * @Author Jason
 * @Date 2022/07/18
 */
public class ObjectUtilTest {

    @Test
    public void obj2MapTest() {
        User user = User.builder()
                .name("jason")
                .status(Boolean.TRUE)
                .age(28)
                .build();

        System.out.println(ObjectUtil.obj2Map(user));
    }

}
