package org.seryu.plugs.generate.mybatisPlugs.generator;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;

import java.util.List;
import java.util.ResourceBundle;

/**
 * @program: seryu-plugs
 * @description: 数据库映射文件及mapper生成工具
 * @author: xujunjie
 * @create: 2020-05-28 16:20
 */
public class MapperAndXmlGeneratorHelper {
  /**
   * @description: 设置mapper目录
   * @param gc
   * @param pc
   * @param focList
   * @param rb
   */
  public static void setMapperAndXml(
      GlobalConfig gc, PackageConfig pc, List<FileOutConfig> focList, ResourceBundle rb) {
    String porjcetRoot = rb.getString("porject.root.path");
    String packageName = rb.getString("package.name.mapper");

    String bizClassTag = "." + rb.getString("bizClassTag");
    gc.setXmlName("%sMapper");
    pc.setMapper(packageName + (bizClassTag.length() == 1 ? "" : bizClassTag));

    // 设置xml地址
    focList.add(
        new FileOutConfig("/custom-templates/infrastructure/mapper.xml.ftl") {
          @Override
          public String outputFile(TableInfo tableInfo) {
            // 自定义输入文件名称
            return (porjcetRoot
                        + rb.getString("OutputDirXml")
                        + "/mapper/"
                        + bizClassTag
                        + "/"
                        + tableInfo.getMapperName())
                    .replace(".", "/")
                + StringPool.DOT_XML;
          }
        });

    gc.setMapperName("%sMapper");
    // 设置xml地址
    focList.add(
        new FileOutConfig("/custom-templates/infrastructure/mapper.java.ftl") {
          @Override
          public String outputFile(TableInfo tableInfo) {
            // 自定义输入文件名称
            return (porjcetRoot
                        + rb.getString("OutputMapperDir")
                        + packageName
                        + bizClassTag
                        + "/"
                        + tableInfo.getMapperName())
                    .replace(".", "/")
                + StringPool.DOT_JAVA;
          }
        });
  }
}
