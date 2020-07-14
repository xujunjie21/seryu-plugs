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
public class ControllerGeneratorHelper {
  /**
   * @description: 设置服务接口实现
   * @param gc
   * @param pc
   * @param focList
   * @param rb
   */
  public static void setController(
      Map<String, Object> config,
      GlobalConfig gc,
      PackageConfig pc,
      List<FileOutConfig> focList,
      ResourceBundle rb) {
    String porjcetRoot = rb.getString("porject.root.path");
    String packageName = rb.getString("package.name.controller");
    String bizClassTag = "." + rb.getString("bizClassTag");
    config.put("packController", packageName + (bizClassTag.length() == 1 ? "" : bizClassTag));

    ConfigValueBuilder.buildController(config, rb, "");

    // 设置前端控制层
    focList.add(
        new FileOutConfig("/custom-templates/web/controller.java.ftl") {
          @Override
          public String outputFile(TableInfo tableInfo) {
            String dtoName =
                tableInfo.getEntityName().substring(0, tableInfo.getEntityName().length() - 2);
            // 自定义输入文件名称
            return (porjcetRoot
                        + rb.getString("OutputControllerDir")
                        + packageName
                        + bizClassTag
                        + dtoName)
                    .replace(".", "/")
                + config.get("web_controller")
                + StringPool.DOT_JAVA;
          }
        });
  }
}
