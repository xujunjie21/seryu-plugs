package org.seryu.plugs.generate.mybatisPlugs;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ResourceBundle;

/**
 * @program: seryu-plugs
 * @description: 测试类
 * @author: xujunjie
 * @create: 2020-07-13 16:27
 **/
@RunWith(JUnit4.class)
public class MybatisPlusGeneratorTest
{
    @Test
    public void generatorDao()
    {
        // 生成DAO层
        //用来获取Mybatis-Plus.properties文件的配置信息
        final ResourceBundle rb = ResourceBundle.getBundle("generator/mybatis-plus-config");
        new MybatisPlusGenerator().generator(rb);
    }
}
