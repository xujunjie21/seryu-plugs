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
 * @description: 领域对象生成工具
 * @author: xujunjie
 * @create: 2020-05-28 16:17
 */
public class DtoGeneratorHelper {
  /**
   * @description: 生成领域模型
   * @param gc
   * @param pc
   * @param focList
   * @param rb
   */
  public static void setDto(
      Map<String, Object> config,
      GlobalConfig gc,
      PackageConfig pc,
      List<FileOutConfig> focList,
      ResourceBundle rb) {
    String porjcetRoot = rb.getString("porject.root.path");
    String packageName = rb.getString("package.name.dto");
    String bizClassTag = "." + rb.getString("bizClassTag");

    if (null == pc.getPathInfo()) {
      pc.setPathInfo(new HashMap<>());
    }
    config.put("packDTO", packageName + (bizClassTag.length() == 1 ? "" : bizClassTag));

    ConfigValueBuilder.buildSearch(config, rb);

    // 设置dto地址
    focList.add(
        new FileOutConfig("/custom-templates/domain/dto.java.ftl") {
          @Override
          public String outputFile(TableInfo tableInfo) {
            String dtoName =
                tableInfo.getEntityName().substring(0, tableInfo.getEntityName().length() - 2);
            // 自定义输入文件名称
            return (porjcetRoot
                        + rb.getString("OutputDtoDir")
                        + packageName
                        + bizClassTag
                        + "/"
                        + dtoName)
                    .replace(".", "/")
                + "Dto"
                + StringPool.DOT_JAVA;
          }
        });

    // 设置qry dto
    focList.add(
        new FileOutConfig("/custom-templates/domain/dto.qry.java.ftl") {
          @Override
          public String outputFile(TableInfo tableInfo) {
            String dtoName =
                tableInfo.getEntityName().substring(0, tableInfo.getEntityName().length() - 2);
            // 自定义输入文件名称
            return (porjcetRoot
                        + rb.getString("OutputDtoDir")
                        + packageName
                        + bizClassTag
                        + "/"
                        + dtoName)
                    .replace(".", "/")
                + "QryDto"
                + StringPool.DOT_JAVA;
          }
        });
  }
}
