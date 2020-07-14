package ${cfg.packService}.cmd;

import ${cfg.packBO}.${entity?substring(0,entity?length-2)}Bo;

/**
* <p>
 * ${table.comment!} 操作服务
 * </p>
* @author ${author}
* @since ${date}
*/
public interface ${entity?substring(0,entity?length-2)}${cfg.app_serviceName_cmd!} extends ${cfg.app_superServiceClass_cmd}
{
    /**
    * 创建${table.comment}
    * @param info
    * @return
    */
    ${entity?substring(0,entity?length-2)}Bo create(${entity?substring(0,entity?length-2)}Bo info);

<#list table.fields as field>
<#if field.keyIdentityFlag>
    /**
    * 更新${table.comment}
    * @param info
    * @return
    */
    ${entity?substring(0,entity?length-2)}Bo updateBy${field.propertyName?cap_first}(${entity?substring(0,entity?length-2)}Bo info);

    /**
    * 删除${table.comment}
    * @param ${field.propertyName?uncap_first} ${field.comment!}
    */
    void deleteBy${field.propertyName?cap_first}(${field.propertyType} ${field.propertyName?uncap_first});
</#if>
</#list>
}
