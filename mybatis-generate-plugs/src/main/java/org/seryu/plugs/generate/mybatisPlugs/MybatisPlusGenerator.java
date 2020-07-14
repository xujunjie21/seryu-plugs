package org.seryu.plugs.generate.mybatisPlugs;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.seryu.framework.db.mybatisPlugs.code.BaseDo;
import org.seryu.framework.db.mybatisPlugs.code.BaseGatewayI;
import org.seryu.framework.db.mybatisPlugs.code.BaseGatewayImpl;
import org.seryu.framework.db.mybatisPlugs.code.BaseMapper;
import org.seryu.plugs.generate.mybatisPlugs.generator.*;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @program: seryu-plugs
 * @description: 生成代码工具
 * @author: xujunjie
 * @create: 2020-04-26 19:17
 **/
public class MybatisPlusGenerator
{
    /** 自定义配置 */
    private Map<String, Object> configs = new HashMap<>();
    /** 输出文件配置 */
    private List<FileOutConfig> focList = new ArrayList<>();

    /**
     * 初始化通用配置
     * @param rb
     * @return
     */
    private GlobalConfig initGlobalConfig(ResourceBundle rb)
    {
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(rb.getString("OutputDir"));
        gc.setOpen(false);
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
        gc.setAuthor(rb.getString("author"));
        return gc;
    }

    /**
     * 初始化数据库资源配置
     * @param rb
     * @return
     */
    private DataSourceConfig initDataSourceConfig(ResourceBundle rb)
    {
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setUrl(rb.getString("url"));
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername(rb.getString("userName"));
        dsc.setPassword(rb.getString("password"));
        return dsc;
    }

    /**
     * 初始化包配置
     * @return
     */
    private PackageConfig initPackageConfig()
    {
        PackageConfig pc = new PackageConfig();
        pc.setParent("");
        return pc;
    }

    /**
     * 自定义配置
     * @return
     */
    private InjectionConfig initInjectionConfig()
    {
        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                if(null == this.getMap())
                {
                    this.setMap(new HashMap<>());
                }
                this.getMap().putAll(configs);
            }
        };
        cfg.setFileOutConfigList(focList);
        return cfg;
    }

    /**
     * 初始化策略配置
     * @param rb
     * @return
     */
    private StrategyConfig initStrategyConfig(ResourceBundle rb)
    {
        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setTablePrefix((StringUtils.isEmpty(rb.getString("TablePrefix"))?"t_":rb.getString("TablePrefix")).split(","));
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setInclude(rb.getString("tableName").split(","));
        strategy.setSuperMapperClass(BaseMapper.class.getName());
        strategy.setSuperEntityClass(BaseDo.class);
        strategy.setSuperServiceClass(BaseGatewayI.class.getName());
        strategy.setSuperServiceImplClass(BaseGatewayImpl.class.getName());

        // 设置父类属性
        List<String> superEntityColumns = new ArrayList<>();
        for(Field field : BaseDo.class.getDeclaredFields())
        {
            TableField annotation = field.getAnnotation(TableField.class);
            superEntityColumns.add(annotation.value());
        }
        strategy.setSuperEntityColumns(superEntityColumns.toArray(new String[superEntityColumns.size()]));
        return strategy;
    }

    /**
     * 初始化自定义模版
     * @param gc
     * @param pc
     * @param rb
     */
    private void initCustomTemp(GlobalConfig gc,PackageConfig pc,ResourceBundle rb)
    {
        DoGeneratorHelper.setDo(gc,pc,focList,rb);
        MapperAndXmlGeneratorHelper.setMapperAndXml(gc,pc,focList,rb);
        RepositoryGeneratorHelper.setRepository(configs,gc,pc,focList,rb);
        DtoGeneratorHelper.setDto(configs,gc,pc,focList,rb);
        BoGeneratorHelper.setBo(configs,gc,pc,focList,rb);
        ClientGeneratorHelper.setServiceI(configs,gc,pc,focList,rb);
        AppGeneratorHelper.setService(configs,gc,pc,focList,rb);
        ControllerGeneratorHelper.setController(configs,gc,pc,focList,rb);
        D2VueGeneratorHelper.setD2Vue(configs,gc,pc,focList,rb);
    }

    /**
     * 初始化模版生成器
     * @param rb
     * @return
     */
    private AutoGenerator initAutoGenerator(ResourceBundle rb)
    {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = initGlobalConfig(rb);
        // 数据源配置
        DataSourceConfig dsc = initDataSourceConfig(rb);
        // 包配置
        PackageConfig pc = initPackageConfig();
        // 自定义模版
        initCustomTemp(gc,pc,rb);
        // 自定义配置
        InjectionConfig cfg = initInjectionConfig();
        // 策略配置
        StrategyConfig strategy = initStrategyConfig(rb);

        mpg.setCfg(cfg);
        mpg.setTemplate(new TemplateConfig().setXml(null));
        mpg.setDataSource(dsc);
        mpg.setGlobalConfig(gc);
        mpg.setPackageInfo(pc);
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        return mpg;
    }

    /**
     * 开始生成
     * @param rb
     */
    public void generator(ResourceBundle rb)
    {
        initAutoGenerator(rb).execute();
    }

    public static void main(String[] args) {
        //用来获取Mybatis-Plus.properties文件的配置信息
        final ResourceBundle rb = ResourceBundle.getBundle("generator/mybatis-plus-config");
        new MybatisPlusGenerator().generator(rb);
    }
}
