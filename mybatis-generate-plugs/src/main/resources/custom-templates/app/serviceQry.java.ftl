package ${cfg.packServiceImpl}.qry;

import org.seryu.framework.data.page.PageData;
import org.seryu.framework.data.util.ConverterUtil;
import ${cfg.packBO}.${entity?substring(0,entity?length-2)}Bo;
import ${cfg.packQry}.${entity?substring(0,entity?length-2)}Qry;
import ${cfg.packService}.qry.${entity?substring(0,entity?length-2)}${cfg.app_serviceName_qry!};
import ${cfg.packDTO}.${entity?substring(0,entity?length-2)}Dto;
import ${cfg.packDTO}.${entity?substring(0,entity?length-2)}QryDto;
import ${package.Service}.${table.serviceName};

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

<#assign bo="${entity?substring(0,entity?length-2)}Bo">
<#assign dto="${entity?substring(0,entity?length-2)}Dto">
<#assign query="${entity?substring(0,entity?length-2)}Qry">
<#assign gateway="${table.serviceName?uncap_first}">
<#assign queryDto="${entity?substring(0,entity?length-2)}QryDto">

/**
 * <p>
 * ${table.comment!} 查询服务
 * </p>
 * @author ${author}
 * @since ${date}
 */
@Slf4j
@Service
public class ${entity?substring(0,entity?length-2)}${cfg.app_serviceName_qry_impl!} implements ${entity?substring(0,entity?length-2)}${cfg.app_serviceName_qry!}
{
     @Autowired
     private ${table.serviceName} ${gateway};

     private ConverterUtil<${dto},${bo}> converterUtil = new ConverterUtil<>();

     /**
     * 获取${table.comment}列表
     * @param qry
     * @return
     */
     public PageData<${bo}> page(${query} qry)
     {
         PageData<${dto}> page = ${gateway}.page(converterUtil.conver(qry, ${queryDto}.class), qry);
         return converterUtil.converPage(page,${bo}.class);
     }

 <#list table.fields as field>
  <#if field.keyIdentityFlag>
     /**
     * 根据${field.propertyName?uncap_first}获取${table.comment}信息
     * @param id ${field.comment!}
     * @return
     */
     public ${bo} infoBy${field.propertyName?cap_first}(${field.propertyType} ${field.propertyName?uncap_first})
     {
         return converterUtil.conver(${gateway}.getById(id),${bo}.class);
     }
  </#if>
 </#list>
}
