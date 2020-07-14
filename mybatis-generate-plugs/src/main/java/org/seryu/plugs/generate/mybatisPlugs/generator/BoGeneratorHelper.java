package org.seryu.plugs.generate.mybatisPlugs.generator;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import org.seryu.plugs.generate.mybatisPlugs.common.ConfigValueBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @program: seryu-plugs
 * @description:
 * @author: xujunjie
 * @create: 2020-06-08 10:04
 */
public class BoGeneratorHelper {
  /**
   * @description: 设置Bo层
   * */
  public static void setBo(
      Map<String, Object> config,
      GlobalConfig gc,
      PackageConfig pc,
      List<FileOutConfig> focList,
      ResourceBundle rb) {
    String porjcetRoot = rb.getString("porject.root.path");
    String packageName = rb.getString("package.name.bo");
    String packageNameQuery = rb.getString("package.name.qry");
    String bizClassTag = "." + rb.getString("bizClassTag");

    if (null == pc.getPathInfo()) {
      pc.setPathInfo(new HashMap<>());
    }
    config.put("packBO", packageName + (bizClassTag.length() == 1 ? "" : bizClassTag));
    config.put("baseBo", rb.getString("package.name.base.bo"));
    config.put("packQry", packageNameQuery + (bizClassTag.length() == 1 ? "" : bizClassTag));
    ConfigValueBuilder.buildSearch(config, rb);

    // 设置bo地址
    focList.add(
        new FileOutConfig("/custom-templates/client/bo.java.ftl") {
          @Override
          public String outputFile(TableInfo tableInfo) {
            String dtoName =
                tableInfo.getEntityName().substring(0, tableInfo.getEntityName().length() - 2);
            // 自定义输入文件名称
            return (porjcetRoot
                        + rb.getString("OutputBoDir")
                        + packageName
                        + bizClassTag
                        + "/"
                        + dtoName)
                    .replace(".", "/")
                + "Bo"
                + StringPool.DOT_JAVA;
          }
        });

    // 设置qry bo
    focList.add(
        new FileOutConfig("/custom-templates/client/bo.qry.java.ftl") {
          @Override
          public String outputFile(TableInfo tableInfo) {
            String dtoName =
                tableInfo.getEntityName().substring(0, tableInfo.getEntityName().length() - 2);
            // 自定义输入文件名称
            return (porjcetRoot
                        + rb.getString("OutputBoDir")
                        + packageNameQuery
                        + bizClassTag
                        + "/"
                        + dtoName)
                    .replace(".", "/")
                + "Qry"
                + StringPool.DOT_JAVA;
          }
        });
  }
}
