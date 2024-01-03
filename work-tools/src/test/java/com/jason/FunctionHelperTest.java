package com.jason;

import com.jason.util.FunctionHelper;
import org.junit.Test;

public class FunctionHelperTest {

    @Test(expected = RuntimeException.class)
    public void test() {
        System.out.println(FunctionHelper.implicit(() -> Class.forName("ddd")));
    }

}
