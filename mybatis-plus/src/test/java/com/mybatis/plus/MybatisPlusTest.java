package com.mybatis.plus;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mybatis.plus.entity.Address;
import com.mybatis.plus.entity.User;
import com.mybatis.plus.enums.AgeEnum;
import com.mybatis.plus.enums.GenderEnum;
import com.mybatis.plus.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisPlusTest {

    @Autowired
    private UserService userService;

    /**
     * 基础使用, CRUD
     */
    @Test
    public void testCRUD() {
        User user = User.builder()
                .name("jason")
                .build();
        boolean saveResult = userService.save(user);
        Assert.assertTrue(saveResult);

        User getUser = userService.lambdaQuery().eq(User::getId, user.getId()).one();
        Assert.assertNotNull(getUser);

        getUser.setName("jason1");
        boolean updateResult = userService.updateById(getUser);
        Assert.assertTrue(updateResult);

        boolean removeResult = userService.removeById(getUser.getId());
        Assert.assertTrue(removeResult);
    }

    /**
     * 1) @MapperScan 扫描 mapper 类;
     * 2) 需要配置 xml 文件位置, mybatis-plus.mapper-locations
     */
    @Test
    public void testSelectXML() {
        User user = userService.selectByUserId(Long.MAX_VALUE);
        Assert.assertEquals(user, null);
    }

    /**
     * 需要配置分页插件(PaginationInnerInterceptor)才能生效
     */
    @Test
    public void testPage() {
        Page<User> userPage = new Page<>(1, 2);
        userService.page(userPage);
        System.out.println("总条数 ------> " + userPage.getTotal());
        System.out.println("总页数 ------> " + userPage.getPages());
        System.out.println("当前页数 ------> " + userPage.getCurrent());
        System.out.println("当前每页显示数 ------> " + userPage.getSize());

        Page<User> customPage = new Page<>(1, 2);
        User user = User.builder()
                .email("jason.mei@xgate.com")
                .build();
        userService.customPage(customPage, user);
        System.out.println("总条数 ------> " + customPage.getTotal());
        System.out.println("总页数 ------> " + customPage.getPages());
        System.out.println("当前页数 ------> " + customPage.getCurrent());
        System.out.println("当前每页显示数 ------> " + customPage.getSize());
    }

    /**
     * 需要在字段上加上注解 @TableLogic 才能生效
     */
    @Test
    public void testLogicDelete() {
        User user = User.builder()
                .name("jason")
                .build();
        userService.save(user);
        Assert.assertNotNull(user.getId());

        User dbUser = userService.getById(user.getId());
        Assert.assertNotNull(dbUser);
        Assert.assertTrue(dbUser.getDeleted() == 0);

        userService.removeById(user.getId());
        User dbDeleteUser = userService.getById(user.getId());
        Assert.assertNull(dbDeleteUser);
    }

    /**
     * 定义枚举类：
     * 1) 没有枚举字段, 则枚举值对应数据库字段内容, 不需要任何处理;
     * 2) 有枚举字段, 则需要用 @EnumValue 标识哪个字段对应数据库字段
     */
    @Test
    public void testGenericEnum() {
        User ageUser = User.builder()
                .age(AgeEnum.TWO)
                .build();
        userService.save(ageUser);
        User dbAgeUser = userService.getById(ageUser.getId());
        Assert.assertTrue(dbAgeUser.getAge() == AgeEnum.TWO);

        User genderUser = User.builder()
                .gender(GenderEnum.MAN)
                .build();
        userService.save(genderUser);

        User dbGenderUser = userService.getById(genderUser.getId());
        Assert.assertTrue(dbGenderUser.getGender() == GenderEnum.MAN);
    }

    /**
     * 类型转换器, 用于 JavaType 与 JdbcType 之间的转换
     * 1) @TableName 注解指定 autoResultMap = true;
     * 2) @TableField 指定 JSON 处理器(typeHandler)
     *
     * Note:
     * 1) entity 如果加了 @Builder 注解，则必须加上 @NoArgsConstructor 和 @AllArgsConstructor 注解，不然读取会报错;
     * https://stackoverflow.com/questions/67415161/how-to-solve-org-mybatis-spring-mybatissystemexception-nested-exception-is-org
     * 2) 选择对应的 JSON 处理器也必须引入对应 JSON 解析依赖包
     */
    @Test
    public void testTypeHandler() {
        Address address = Address.builder()
                .province("四川")
                .city("成都")
                .build();
        User user = User.builder()
                .address(address)
                .build();
        userService.save(user);

        User dbUser = userService.getById(user.getId());
        System.out.println(dbUser);
    }

    /**
     * 自动填充
     * 1) 标记为填充字段，@TableField(.. fill = FieldFill.INSERT)
     * 2) 自定义实现类 MyMetaObjectHandler 实现 MetaObjectHandler
     */
    @Test
    public void testAutoFill() {
        User user = User.builder()
                .name("jason")
                .build();
        userService.save(user);

        User dbUser = userService.getById(user.getId());
        Assert.assertNotNull(dbUser.getCreatedTime());
        Assert.assertNotNull(dbUser.getLastModifiedTime());

        dbUser.setAge(AgeEnum.THREE);
        userService.updateById(dbUser);
    }

    /**
     * 乐观锁
     * 1) 需要在字段加上 @Version 注解
     * 2) 添加 OptimisticLockerInnerInterceptor 插件
     */
    @Test
    public void testOptimisticLocker() {
        User user = User.builder()
                .name("jason")
                .build();
        userService.save(user);

        User dbUser = userService.getById(user.getId());
        dbUser.setName("jason1");
        Assert.assertTrue(userService.updateById(dbUser));

        User userUpdate = new User();
        userUpdate.setId(user.getId());
        userUpdate.setName("jason1");
        // Should update failed due to incorrect version(actually 1, but 0 passed in)
        userUpdate.setVersion(0L);
        Assert.assertFalse(userService.updateById(userUpdate));
    }

}
