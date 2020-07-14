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
 * @description: 存储实现生成工具
 * @author: xujunjie
 * @create: 2020-05-28 16:21
 */
public class RepositoryGeneratorHelper {
  /**
   * @description: 设置存储服务实现
   * @param gc
   * @param pc
   * @param focList
   * @param rb
   */
  public static void setRepository(
      Map<String, Object> config,
      GlobalConfig gc,
      PackageConfig pc,
      List<FileOutConfig> focList,
      ResourceBundle rb) {
    String porjcetRoot = rb.getString("porject.root.path");
    String packageName = rb.getString("package.name.repository");
    String gatewayPackageName = rb.getString("package.name.gateway");

    String bizClassTag = "." + rb.getString("bizClassTag");
    gc.setServiceName("%sGateway");
    gc.setServiceImplName("%sRepository");
    pc.setServiceImpl(packageName + (bizClassTag.length() == 1 ? "" : bizClassTag));
    pc.setService(gatewayPackageName + (bizClassTag.length() == 1 ? "" : bizClassTag));

    ConfigValueBuilder.buildSearch(config, rb);

    config.put("packDTO", packageName + (bizClassTag.length() == 1 ? "" : bizClassTag));

    // 设置存储服务地址
    focList.add(
        new FileOutConfig("/custom-templates/infrastructure/serviceImpl.java.ftl") {
          @Override
          public String outputFile(TableInfo tableInfo) {
            tableInfo.setImportPackages(gatewayPackageName);
            // 自定义输入文件名称
            return (porjcetRoot
                        + rb.getString("OutputRepositoryDir")
                        + packageName
                        + bizClassTag
                        + "/"
                        + tableInfo.getServiceImplName())
                    .replace(".", "/")
                + StringPool.DOT_JAVA;
          }
        });

    // 设置基础服务网关地址
    focList.add(
        new FileOutConfig("/custom-templates/domain/service.java.ftl") {
          @Override
          public String outputFile(TableInfo tableInfo) {
            tableInfo.setImportPackages(gatewayPackageName);
            // 自定义输入文件名称
            return (porjcetRoot
                        + rb.getString("OutputGatewayDir")
                        + gatewayPackageName
                        + bizClassTag
                        + "/"
                        + tableInfo.getServiceName())
                    .replace(".", "/")
                + StringPool.DOT_JAVA;
          }
        });
  }
}
