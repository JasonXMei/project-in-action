package com.jason;

import cn.hutool.json.JSONUtil;
import com.jason.model.User;
import org.junit.Test;

import java.util.List;

public class HutoolJsonUtilTest {

    @Test
    public void json2ObjTest() {
        String json = "{\n" +
                "\"name\":\"jason\",\n" +
                "\"status\":true,\n" +
                "\"age\":20\n" +
                "}";
        System.out.println(JSONUtil.toBean(json, User.class));
    }

    @Test
    public void jsonList2ObjTest() {
        String json = "[{\"name\":\"jason\",\"status\":true,\"age\":20}]";
        List<User> users = JSONUtil.toList(json, User.class);
        System.out.println(users);
    }

}
