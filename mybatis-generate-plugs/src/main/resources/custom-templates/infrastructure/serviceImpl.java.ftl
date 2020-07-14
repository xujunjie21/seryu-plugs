package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${cfg.packDTO}.${entity?substring(0,entity?length-2)}Dto;
import ${cfg.packDTO}.${entity?substring(0,entity?length-2)}QryDto;
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.seryu.framework.data.page.Page;
import org.seryu.framework.data.page.PageData;

/**
 * <p>
 * ${table.comment!} 服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity},${entity?substring(0,entity?length-2)}Dto>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity},${entity?substring(0,entity?length-2)}Dto> implements ${table.serviceName} {

    @Override
    public PageData<${entity?substring(0,entity?length-2)}Dto> page(${entity?substring(0,entity?length-2)}QryDto qry, Page page)
    {
        LambdaQueryWrapper<${entity}> wrapper = new LambdaQueryWrapper();
        // TODO 请填写业务需要过滤条件
        <#assign key="${table.name!}">
        <#list table.fields as field>
            <#if (cfg.search[key+'.'+field.name])?? && ('like' == cfg.search[key+'.'+field.name]) >
        // 模糊搜索 ${field.comment!}
        wrapper.like(!StringUtils.isEmpty(qry.get${field.propertyName?cap_first}()),${entity}::get${field.propertyName?cap_first},qry.get${field.propertyName?cap_first}());
            </#if>
            <#if (cfg.search[key+'.'+field.name])?? && ('type' == cfg.search[key+'.'+field.name]) >
        // 状态搜索 ${field.comment!} 0为全部
        wrapper.eq(!StringUtils.isEmpty(qry.get${field.propertyName?cap_first}())&& 0 != qry.get${field.propertyName?cap_first}(),${entity}::get${field.propertyName?cap_first},qry.get${field.propertyName?cap_first}());
            </#if>
            <#if (cfg.search[key+'.'+field.name])?? && ('eq' == cfg.search[key+'.'+field.name]) >
        // 精确搜索 ${field.comment!}
        wrapper.eq(!StringUtils.isEmpty(qry.get${field.propertyName?cap_first}()),${entity}::get${field.propertyName?cap_first},qry.get${field.propertyName?cap_first}());
            </#if>
        </#list>

        wrapper.between(!StringUtils.isEmpty(qry.getStartTime())||!StringUtils.isEmpty(qry.getEndTime()),${entity}::getCreateDate, qry.getStartTime(),qry.getEndTime());
        return selectPage(page, wrapper,${entity}.class);
    }
}
</#if>
