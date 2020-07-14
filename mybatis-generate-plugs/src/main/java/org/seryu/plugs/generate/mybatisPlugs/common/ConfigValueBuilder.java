package org.seryu.plugs.generate.mybatisPlugs.common;

import org.seryu.plugs.generate.mybatisPlugs.util.StringUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @program: seryu-plugs
 * @description: 配置文件额外参数建造者
 * @author: xujunjie
 * @create: 2020-06-08 14:17
 **/
public class ConfigValueBuilder
{
    /**
     * 构建搜索查询参数
     * @param config
     * @param rb
     */
    public static void buildSearch(Map<String, Object> config, ResourceBundle rb)
    {
        String searchs = rb.getString("search");
        Map<String,String> map = new HashMap<>();
        if(null != searchs && !"".equals(searchs))
        {
            String[] split = searchs.split(",");
            for(String single : split)
            {
                String[] split1 = single.split(":");
                map.put(split1[0],split1[1]);
            }
            config.put("search",map);
        }
    }

    /**
     * 设置业务层
     * @param config
     * @param rb
     * @param bizName
     */
    public static void buildServiceQryAndCmdI(Map<String, Object> config, ResourceBundle rb,String bizName)
    {
        config.put("app_serviceName_qry", !StringUtil.isEmpty(bizName)?bizName:"ServiceQryI");
        config.put("app_superServiceClass_qry","BaseQryI");
        config.put("app_serviceName_cmd", !StringUtil.isEmpty(bizName)?bizName:"ServiceCmdI");
        config.put("app_superServiceClass_cmd","BaseCmdI");
    }

    /**
     * 设置业务层
     * @param config
     * @param rb
     * @param bizName
     */
    public static void buildServiceQryAndCmd(Map<String, Object> config, ResourceBundle rb,String bizName)
    {
        config.put("app_serviceName_qry_impl", !StringUtil.isEmpty(bizName)?bizName:"ServiceQry");
        config.put("app_serviceName_cmd_impl", !StringUtil.isEmpty(bizName)?bizName:"ServiceCmd");
    }

    /**
     * 设置控制器层(管理类)
     * @param config
     * @param rb
     * @param bizName
     */
    public static void buildController(Map<String, Object> config, ResourceBundle rb,String bizName)
    {
        config.put("web_controller", !StringUtil.isEmpty(bizName)?bizName:"Controller");

        String apiUrls = rb.getString("api_url");
        Map<String,String> map = new HashMap<>();
        if(null != apiUrls && !"".equals(apiUrls))
        {
            String[] split = apiUrls.split(",");
            for(String single : split)
            {
                String[] split1 = single.split(":");
                map.put(split1[0],split1[1]);
            }
            config.put("api_url",map);
        }
    }
}
