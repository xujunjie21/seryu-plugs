package ${cfg.packDTO};

import org.seryu.framework.data.page.Page;

import lombok.Data;
import lombok.ToString;


/**
 * <p>
 * ${table.comment!} 领域查询模型
 * </p>
 * @author ${author}
 * @since ${date}
 */
@Data
@ToString
public class ${entity?substring(0,entity?length-2)}QryDto extends Page
{
    <#assign key="${table.name!}">
    <#list table.fields as field>
    <#if (cfg.search[key+'.'+field.name])?? >
    /**
    * ${field.comment}
    */
    private ${field.propertyType} ${field.propertyName};
    </#if>
    </#list>

    /** 开始时间 */
    private String startTime;

    /** 结束时间 */
    private String endTime;
}
