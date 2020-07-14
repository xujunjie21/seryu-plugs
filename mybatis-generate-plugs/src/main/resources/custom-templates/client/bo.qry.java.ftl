package ${cfg.packQry};

import org.seryu.framework.data.page.Page;

import lombok.Data;
import lombok.ToString;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * <p>
 * ${table.comment!} 业务查询模型
 * </p>
 * @author ${author}
 * @since ${date}
 */
@Data
@ToString
@ApiModel(value="${table.comment!}业务查询模型", description="${table.comment!}业务查询模型")
public class ${entity?substring(0,entity?length-2)}Qry extends Page
{
    <#assign key="${table.name!}">
    <#list table.fields as field>
    <#if (cfg.search[key+'.'+field.name])?? >
    @ApiModelProperty(value = "${field.comment}",name = "${field.name?lower_case}" , dataType="${field.propertyType}")
    private ${field.propertyType} ${field.propertyName};
    </#if>
    </#list>

    @ApiModelProperty(value="开始时间: YYYY-MM-dd ",name="start_time",dataType ="String",example = "2020-04-23")
    private String startTime;

    @ApiModelProperty(value="结束时间: YYYY-MM-dd ",name="end_time",dataType ="String",example = "2020-04-23")
    private String endTime;
}
