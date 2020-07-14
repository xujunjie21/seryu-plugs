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
 * @description: 数据库对象生成工具
 * @author: xujunjie
 * @create: 2020-05-28 16:19
 */
public class DoGeneratorHelper {
  /** @description: 设置实体类目录 */
  public static void setDo(
      GlobalConfig gc, PackageConfig pc, List<FileOutConfig> focList, ResourceBundle rb) {
    String porjcetRoot = rb.getString("porject.root.path");
    String packageName = rb.getString("package.name.do");

    String bizClassTag = "." + rb.getString("bizClassTag");
    gc.setEntityName("%sDo");
    pc.setEntity(packageName + (bizClassTag.length() == 1 ? "" : bizClassTag));
    // 设置entity地址
    focList.add(
        new FileOutConfig("/custom-templates/infrastructure/entity.java.ftl") {
          @Override
          public String outputFile(TableInfo tableInfo) {
            // 自定义输入文件名称
            return (porjcetRoot
                        + rb.getString("OutputDoDir")
                        + packageName
                        + bizClassTag
                        + "/"
                        + tableInfo.getEntityName())
                    .replace(".", "/")
                + StringPool.DOT_JAVA;
          }
        });
  }
}
