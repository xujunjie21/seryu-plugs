package org.seryu.plugs.generate.mybatisPlugs;

import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: seryu-plugs
 * @description: 通过bean生成sql
 * @author: xujunjie
 * @create: 2020-04-24 15:11
 **/
@Slf4j
public class SqlGenerator
{
    public static Map<String, String> property2SqlColumnMap = new HashMap<>();

    static {
        property2SqlColumnMap.put("integer", "INT");
        property2SqlColumnMap.put("short", "tinyint");
        property2SqlColumnMap.put("long", "bigint");
        property2SqlColumnMap.put("bigdecimal", "decimal(19,2)");
        property2SqlColumnMap.put("double", "double precision not null");
        property2SqlColumnMap.put("float", "float");
        property2SqlColumnMap.put("boolean", "bit");
        property2SqlColumnMap.put("timestamp", "datetime");
        property2SqlColumnMap.put("date", "datetime");
        property2SqlColumnMap.put("string", "VARCHAR(500)");
    }

    public static String generateSql(String className,String tableName,String primaryKey,String filePath){
        Class<?> clz = null;
        try {
             clz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            log.debug("SQL生成异常：",e);
            return null;
        }
        return  generateSql(clz,tableName,primaryKey,filePath);
    }

    public static String generateSql(Class clz,String tableName,String primaryKey,String filePath){
            String className = clz.getSimpleName();
            Field[] fields = clz.getDeclaredFields();
            StringBuffer column = new StringBuffer();
            for (Field f : fields) {
                if (f.getName().equals(primaryKey)){
                    continue;
                }
                //column.append(" \n `"+f.getName()+"`").append(varchar);
                column.append(getColumnSql(f));
            }

            if(Object.class != clz.getSuperclass())
            {
                for (Field f : clz.getSuperclass().getDeclaredFields()) {
                    if (f.getName().equals(primaryKey)){
                        continue;
                    }
                    //column.append(" \n `"+f.getName()+"`").append(varchar);
                    column.append(getColumnSql(f));
                }
            }

            String sqlPrimaryKey =camelToUnderline(primaryKey).toUpperCase();
            StringBuffer sql = new StringBuffer();
            sql.append("\n DROP TABLE IF EXISTS `"+tableName+"`; ")
                    .append(" \n CREATE TABLE `"+tableName+"`  (")
                    .append(" \n `"+sqlPrimaryKey+"` bigint(20) NOT NULL AUTO_INCREMENT,")
                    .append(" \n "+column)
                    .append(" \n PRIMARY KEY (`"+sqlPrimaryKey+"`)")
                    .append(" \n ) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci;");
            String sqlText = sql.toString();
            if(null != filePath)
            {
                StringToSql(sqlText,filePath);
            }

            return sqlText;
    }

    private static String getColumnSql(Field field)
    {
        ApiModelProperty annotation = field.getAnnotation(ApiModelProperty.class);

        String comment = "";
        if(null != annotation)
        {
            comment = "COMMENT '" + annotation.value() + "'";
            comment = comment.replace("\n","");
        }

        String tpl = "\n `%s` %s  DEFAULT NULL %s ,";
        String typeName = field.getType().getSimpleName().toLowerCase();
        String sqlType = property2SqlColumnMap.get(typeName);
        if (sqlType == null || sqlType.isEmpty()){
            log.info(field.getName() + ":"+field.getType().getName()+" 需要单独创建表");
            return "";
        }
        String column =camelToUnderline(field.getName()).toUpperCase();
        String sql = String.format(tpl,column,sqlType.toUpperCase(),comment);
        return sql;
    }
    private static void StringToSql(String str,String path){
        byte[] sourceByte = str.getBytes();
        if(null != sourceByte){
            try {
                File file = new File(path);
                if (!file.exists()) {
                    File dir = new File(file.getParent());
                    dir.mkdirs();
                    file.createNewFile();
                }
                FileOutputStream outStream = new FileOutputStream(file);
                outStream.write(sourceByte);
                outStream.flush();
                outStream.close();
                System.out.println("生成成功");
            } catch (Exception e) {
                log.debug("保存SQL文件异常：",e);
            }
        }
    }

    /**
     * 驼峰转下划线
     *
     * @param str
     * @return
     */
    public static String camelToUnderline(String str) {
        if (str == null || str.trim().isEmpty()){
            return "";
        }
        int len = str.length();
        StringBuilder sb = new StringBuilder(len);
        sb.append(str.substring(0, 1).toLowerCase());
        for (int i = 1; i < len; i++) {
            char c = str.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append("_");
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
