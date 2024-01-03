package com.jason;

import com.jason.model.User;
import com.jason.util.CollectionUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CollectionUtilTest {

    @Test
    public void convertMapTest() {
        List<User> list = new ArrayList<>();

        User user1 = User.builder()
                .name("jason")
                .status(Boolean.TRUE)
                .age(28)
                .build();
        list.add(user1);
        System.out.println(CollectionUtils.convertMap(list, User::getName));
    }

}
