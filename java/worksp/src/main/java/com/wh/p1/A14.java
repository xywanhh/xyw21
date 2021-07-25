package com.wh.p1;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 表注解
 */
@Target(ElementType.TYPE) // 只能应用于类上
@Retention(RetentionPolicy.RUNTIME) // 保存到运行时
@interface DBTable {
    String name() default "";
}

/**
 * 注解Integer类型的字段
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface SQLInteger {
    String name() default ""; // 字段对应数据库表列名
    Constraints constraint() default @Constraints; // 嵌套注解
}

/**
 * 注解String类型的字段
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface SQLString {
    String name() default ""; // 字段对应数据库表列名
    int value() default 0; // 列类型分配的长度，如varchar(30)的30
    Constraints constraint() default @Constraints; // 嵌套注解
}

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface Constraints {
    boolean primaryKey() default false; // 判断是否作为主键约束
    boolean allowNull() default false; // 判断是否允许为null
    boolean unique() default false; // 判断是否唯一
}

/**
 * 运行时注解处理器，构造表创建语句
 */
class TableCreator {
    public static String createTableSql(String className) throws ClassNotFoundException {
        Class<?> cls = Class.forName(className);
        DBTable dbTable = cls.getAnnotation(DBTable.class);
        // 如果没有表注解，直接返回
        if(dbTable == null) {
            System.out.println(
                    "No DBTable annotations in class " + className);
            return null;
        }
        String tableName = dbTable.name();
        // If the name is empty, use the Class name:
        if(tableName.length() < 1)
            tableName = cls.getName().toUpperCase();
        List<String> columnDefs = new ArrayList<String>();
        // 通过Class类API获取到所有成员字段
        for (Field field : cls.getDeclaredFields()) {
            String columnName = null;
            // 获取字段上的注解
            Annotation[] anns = field.getDeclaredAnnotations();
            if(anns.length < 1)
                continue; // Not a db table column
            // 判断注解类型
            if(anns[0] instanceof SQLInteger) {
                SQLInteger sInt = (SQLInteger) anns[0];
                // 获取字段对应列名称，如果没有就是使用字段名称替代
                if (sInt.name().length() < 1)
                    columnName = field.getName().toUpperCase();
                else
                    columnName = sInt.name();
                // 构建语句
                columnDefs.add(columnName + " INT" +
                        getConstraints(sInt.constraint()));
            }
            // 判断String类型
            if(anns[0] instanceof SQLString) {
                SQLString sString = (SQLString) anns[0];
                // Use field name if name not specified.
                if (sString.name().length() < 1)
                    columnName = field.getName().toUpperCase();
                else
                    columnName = sString.name();
                columnDefs.add(columnName + " VARCHAR(" +
                        sString.value() + ")" +
                        getConstraints(sString.constraint()));
            }
        }
        // 数据库表构建语句
        StringBuilder createCommand = new StringBuilder(
                "CREATE TABLE " + tableName + "(");
        for(String columnDef : columnDefs)
            createCommand.append("\n    " + columnDef + ",");

        // Remove trailing comma
        String tableCreate = createCommand.substring(
                0, createCommand.length() - 1) + ");";
        return tableCreate;
    }
    /**
     * 判断该字段是否有其他约束
     */
    static String getConstraints(Constraints con) {
        String constraints = "";
        if(!con.allowNull())
            constraints += " NOT NULL";
        if(con.primaryKey())
            constraints += " PRIMARY KEY";
        if(con.unique())
            constraints += " UNIQUE";
        return constraints;
    }
}

/**
 * 数据库表Member对应实例类bean
 */
@DBTable(name = "MEMBER")
class Member {
    //主键ID
    @SQLString(name = "ID", value = 50, constraint = @Constraints(primaryKey = true))
    private String id;

    @SQLString(name = "NAME" , value = 30)
    private String name;

    @SQLInteger(name = "AGE")
    private int age;

    @SQLString(name = "DESCRIPTION" ,value = 150 , constraint = @Constraints(allowNull = true))
    private String description;
}

/**
 * Hibernate的原理
 */
public class A14 {
    public static void main(String[] args) throws ClassNotFoundException {
        String[] arg={"com.wh.p1.Member"};
        for(String className : arg) {
            System.out.println("Table Creation SQL for " +
                    className + " is :\n" + TableCreator.createTableSql(className));
        }
    }
    /**
     * Table Creation SQL for com.wh.p1.Member is :
     * CREATE TABLE MEMBER(
     *     ID VARCHAR(50) NOT NULL PRIMARY KEY,
     *     NAME VARCHAR(30) NOT NULL,
     *     AGE INT NOT NULL,
     *     DESCRIPTION VARCHAR(150));
     */
}
