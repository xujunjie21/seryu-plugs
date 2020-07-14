package org.seryu.plugs.generate.mybatisPlugs.generator;

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
public class D2VueGeneratorHelper {
  /**
   * @description: 设置服务接口实现
   * @param gc
   * @param pc
   * @param focList
   * @param rb
   */
  public static void setD2Vue(
      Map<String, Object> config,
      GlobalConfig gc,
      PackageConfig pc,
      List<FileOutConfig> focList,
      ResourceBundle rb) {
    String porjcetRoot = rb.getString("porject.root.path");
    String bizClassTag = "." + rb.getString("bizClassTag");
    String tablePrefix = rb.getString("TablePrefix");

    config.put("tablePrefix", tablePrefix + "_");
    config.put("detail", "_detail");

    ConfigValueBuilder.buildController(config, rb, "");

    // 设置查询服务
    focList.add(
        new FileOutConfig("/custom-templates/d2-vue/api.modules.js.ftl") {
          @Override
          public String outputFile(TableInfo tableInfo) {
            String dirName =
                tableInfo
                    .getName()
                    .replace(tablePrefix + "_", "")
                    .replace("_detail", "")
                    .replace("_", "-");
            // 自定义输入文件名称
            return (porjcetRoot
                        + rb.getString("OutputD2Vue")
                        + "/"
                        + bizClassTag
                        + "/"
                        + dirName
                        + "/"
                        + dirName)
                    .replace(".", "/")
                + ".js";
          }
        });
    // 表单
    focList.add(
        new FileOutConfig("/custom-templates/d2-vue/form.js.ftl") {
          @Override
          public String outputFile(TableInfo tableInfo) {
            String dirName =
                tableInfo
                    .getName()
                    .replace(tablePrefix + "_", "")
                    .replace("_detail", "")
                    .replace("_", "-");
            // 自定义输入文件名称
            return (porjcetRoot
                        + rb.getString("OutputD2Vue")
                        + "/"
                        + bizClassTag
                        + "/"
                        + dirName
                        + "/")
                    .replace(".", "/")
                + "form.js";
          }
        });
    // 表格
    focList.add(
        new FileOutConfig("/custom-templates/d2-vue/index.js.ftl") {
          @Override
          public String outputFile(TableInfo tableInfo) {
            String dirName =
                tableInfo
                    .getName()
                    .replace(tablePrefix + "_", "")
                    .replace("_detail", "")
                    .replace("_", "-");
            // 自定义输入文件名称
            return (porjcetRoot
                        + rb.getString("OutputD2Vue")
                        + "/"
                        + bizClassTag
                        + "/"
                        + dirName
                        + "/")
                    .replace(".", "/")
                + "index.js";
          }
        });
  }
}
