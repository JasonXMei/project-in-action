package com.mybatis.plus;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

public class POJOGeneratorTest {

    private static String author = "Jason";
    private static String url = "jdbc:mysql://localhost:3306/mybatis-plus-db?useUnicode=true&useSSL=false&characterEncoding=utf8";
    private static String userName = "root";
    private static String password = "Jason0313";
    private static String packageParent = "com.mybatis.plus";

    private static String projectPath = "C:\\Users\\Administrator\\Desktop\\workspace_idea\\project-in-action\\mybatis-plus\\src\\main\\java";
    // 多张表以 "," 分割
    private static String tables = "user,role,user_role";

    public static void main(String[] args) {
        FastAutoGenerator.create(url, userName, password)
                .globalConfig(builder -> {
                    builder.author(author)
                            .outputDir(projectPath);
                })
                .packageConfig(builder -> {
                    builder.parent(packageParent);
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tables.split(","))
                            .entityBuilder()
                            .enableLombok()
                            .enableTableFieldAnnotation()
                            .idType(IdType.ASSIGN_ID)
                            .mapperBuilder()
                            .enableBaseColumnList()
                            .enableBaseResultMap()
                            .enableMapperAnnotation()
                            .serviceBuilder()
                            .formatServiceFileName("%sService");
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
