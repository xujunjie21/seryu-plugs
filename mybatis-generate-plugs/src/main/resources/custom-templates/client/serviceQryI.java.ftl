package ${cfg.packService}.qry;

import org.seryu.framework.data.page.PageData;
import ${cfg.packBO}.${entity?substring(0,entity?length-2)}Bo;
import ${cfg.packQry}.${entity?substring(0,entity?length-2)}Qry;


/**
 * <p>
 * ${table.comment!} 查询服务
 * </p>
 * @author ${author}
 * @since ${date}
 */
public interface ${entity?substring(0,entity?length-2)}${cfg.app_serviceName_qry!} extends ${cfg.app_superServiceClass_qry}
{
    /**
    * 获取${table.comment}列表
    * @param qry
    * @return
    */
    PageData<${entity?substring(0,entity?length-2)}Bo> page(${entity?substring(0,entity?length-2)}Qry qry);

 <#list table.fields as field>
  <#if field.keyIdentityFlag>
     /**
     * 根据${field.propertyName?uncap_first}获取${table.comment}信息
     * @param id ${field.comment!}
     * @return
     */
    ${entity?substring(0,entity?length-2)}Bo infoBy${field.propertyName?cap_first}(${field.propertyType} ${field.propertyName?uncap_first});
  </#if>
 </#list>
}
