package org.seryu.plugs.generate.mybatisPlugs.generator;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import org.seryu.plugs.generate.mybatisPlugs.common.ConfigValueBuilder;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @program: seryu-plugs
 * @description:
 * @author: xujunjie
 * @create: 2020-06-08 10:04
 */
public class ClientGeneratorHelper {
  /**
   * @description: 设置服务接口
   * @param gc
   * @param pc
   * @param focList
   * @param rb
   */
  public static void setServiceI(
      Map<String, Object> config,
      GlobalConfig gc,
      PackageConfig pc,
      List<FileOutConfig> focList,
      ResourceBundle rb) {
    String porjcetRoot = rb.getString("porject.root.path");
    String packageName = rb.getString("package.name.service");
    String bizClassTag = "." + rb.getString("bizClassTag");
    config.put("packService", packageName + (bizClassTag.length() == 1 ? "" : bizClassTag));

    ConfigValueBuilder.buildServiceQryAndCmdI(config, rb, "");

    // 设置查询服务
    focList.add(
        new FileOutConfig("/custom-templates/client/serviceQryI.java.ftl") {
          @Override
          public String outputFile(TableInfo tableInfo) {
            String dtoName =
                tableInfo.getEntityName().substring(0, tableInfo.getEntityName().length() - 2);
            // 自定义输入文件名称
            return (porjcetRoot
                        + rb.getString("OutputBoDir")
                        + packageName
                        + "/qry"
                        + bizClassTag
                        + "/"
                        + dtoName)
                    .replace(".", "/")
                + config.get("app_serviceName_qry")
                + StringPool.DOT_JAVA;
          }
        });

    // 设置操作服务
    focList.add(
        new FileOutConfig("/custom-templates/client/serviceCmdI.java.ftl") {
          @Override
          public String outputFile(TableInfo tableInfo) {
            String dtoName =
                tableInfo.getEntityName().substring(0, tableInfo.getEntityName().length() - 2);
            // 自定义输入文件名称
            return (porjcetRoot
                        + rb.getString("OutputBoDir")
                        + packageName
                        + "/cmd"
                        + bizClassTag
                        + "/"
                        + dtoName)
                    .replace(".", "/")
                + config.get("app_serviceName_cmd")
                + StringPool.DOT_JAVA;
          }
        });
  }
}
