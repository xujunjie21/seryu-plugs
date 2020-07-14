package ${cfg.packServiceImpl}.cmd;


import org.seryu.framework.data.util.ConverterUtil;
import ${cfg.packBO}.${entity?substring(0,entity?length-2)}Bo;
import ${cfg.packDTO}.${entity?substring(0,entity?length-2)}Dto;
import ${cfg.packService}.cmd.${entity?substring(0,entity?length-2)}${cfg.app_serviceName_cmd!};
import ${package.Service}.${table.serviceName};

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

<#assign bo="${entity?substring(0,entity?length-2)}Bo">
<#assign dto="${entity?substring(0,entity?length-2)}Dto">
<#assign gateway="${table.serviceName?uncap_first}">

/**
* <p>
 * ${table.comment!} 操作服务
 * </p>
* @author ${author}
* @since ${date}
*/
@Slf4j
@Service
public class ${entity?substring(0,entity?length-2)}${cfg.app_serviceName_cmd_impl!} implements ${entity?substring(0,entity?length-2)}${cfg.app_serviceName_cmd!}
{
    @Autowired
    private ${table.serviceName} ${gateway};

    private ConverterUtil<${dto},${bo}> converterUtil = new ConverterUtil<>();

    /**
    * 创建${table.comment}
    * @param info
    * @return
    */
    @Override
    public ${bo} create(${bo} info)
    {
        ${dto} conver = converterUtil.conver(info, ${dto}.class);
        ${gateway}.save(conver);
        return info;
    }

<#list table.fields as field>
<#if field.keyIdentityFlag>
    /**
    * 更新${table.comment}
    * @param info
    * @return
    */
    @Override
    public ${bo} updateBy${field.propertyName?cap_first}(${bo} info)
    {
        ${dto} conver = converterUtil.conver(info, ${dto}.class);
        ${gateway}.updateById(conver);
        return info;
    }

    /**
    * 删除${table.comment}
    * @param ${field.propertyName?uncap_first} ${field.comment!}
    */
    @Override
    public void deleteBy${field.propertyName?cap_first}(${field.propertyType} ${field.propertyName?uncap_first})
    {
        ${gateway}.removeById(${field.propertyName?uncap_first});
    }
</#if>
</#list>
}
