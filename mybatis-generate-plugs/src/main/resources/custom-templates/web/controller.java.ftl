<#assign bo="${entity?substring(0,entity?length-2)}Bo">
<#assign cmd="${(entity?substring(0,entity?length-2) + cfg.app_serviceName_cmd!)?uncap_first}">
<#assign qry="${(entity?substring(0,entity?length-2) + cfg.app_serviceName_qry!)?uncap_first}">
<#assign query="${entity?substring(0,entity?length-2)}Qry">

package ${cfg.packController};

import org.seryu.framework.data.page.PageData;
import ${cfg.packService}.cmd.${entity?substring(0,entity?length-2)}${cfg.app_serviceName_cmd!};
import ${cfg.packService}.qry.${entity?substring(0,entity?length-2)}${cfg.app_serviceName_qry!};
import ${cfg.packBO}.${bo};
import ${cfg.packQry}.${query};

import org.seryu.framework.web.BaseController;
import org.seryu.framework.web.result.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
* @description:  ${table.comment!}控制层
* @author: ${author}
* @create: ${date}
**/
@Api(tags = "${table.comment!}管理", value = "${cfg.api_url[table.name]!}", description = "${table.comment!}管理")
@RestController
@RequestMapping("${cfg.api_url[table.name]}")
public class ${entity?substring(0,entity?length-2)}${cfg.web_controller!} extends BaseController
{
    private static final String TAG_NAME = "${table.comment!}管理";

    @Autowired
    private ${entity?substring(0,entity?length-2)}${cfg.app_serviceName_qry!} ${qry};

    @Autowired
    private ${entity?substring(0,entity?length-2)}${cfg.app_serviceName_cmd!} ${cmd};

    /**
    * 查询${table.comment!}列表
    * @return BaseResult
    */
    @ApiOperation(value="${table.comment!}列表", notes="${table.comment!}列表", httpMethod = "POST",tags = TAG_NAME,response = ${bo}.class)
    @RequestMapping(value = "/findAll",method = RequestMethod.POST)
    public BaseResult<List<${bo}>> findAll()
    {
        ${query} query = new ${query}();
        query.setPageSize(99999l);
        return success(${qry}.page(query).getList());
    }

    /**
    * 查询${table.comment!}列表
    * @param query ${table.comment!}信息查询条件
    * @return BaseResult
    */
    @ApiOperation(value="查询${table.comment!}列表", notes="查询${table.comment!}列表", httpMethod = "POST",tags = TAG_NAME,response = ${bo}.class)
    @RequestMapping(value = "/index",method = RequestMethod.POST)
    public BaseResult<PageData<${bo!}>> page(@RequestBody ${query} query)
    {
        return success(${qry}.page(query));
    }

    /**
    * 创建${table.comment!}
    * @param info
    * @return BaseResult
    */
    @ApiOperation(value="创建${table.comment!}", notes="创建${table.comment!}", httpMethod = "PUT",tags = TAG_NAME,response = BaseResult.class)
    @RequestMapping(value = "/create",method = RequestMethod.PUT)
    public BaseResult create(@RequestBody ${bo!} info)
    {
        ${cmd}.create(info);
        return success();
    }


<#list table.fields as field>
<#if field.keyIdentityFlag>
    /**
    * 查看${table.comment!}
    * @param info
    * @return BaseResult
    */
    @ApiOperation(value="查询${table.comment!}", notes="查询${table.comment!}", httpMethod = "POST",tags = TAG_NAME,response = ${bo!}.class)
    @RequestMapping(value = "/info",method = RequestMethod.POST)
    public BaseResult<${bo!}> info(@RequestBody ${bo!} info)
    {
        return success(${qry}.infoBy${field.propertyName?cap_first}(info.getId()));
    }

    /**
    * 更新${table.comment!}
    * @param info
    * @return BaseResult
    */
    @ApiOperation(value="更新${table.comment!}", notes="更新${table.comment!}", httpMethod = "PUT",tags = TAG_NAME,response = BaseResult.class)
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    public BaseResult update(@RequestBody ${bo!} info)
    {
        ${cmd}.updateBy${field.propertyName?cap_first}(info);
        return success();
    }

    /**
    * 删除${table.comment!}
    * @param info
    * @return BaseResult
    */
    @ApiOperation(value="删除${table.comment!}", notes="删除${table.comment!}", httpMethod = "DELETE",tags = TAG_NAME,response = BaseResult.class)
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public BaseResult delete(@RequestBody ${bo!} info)
    {
        ${cmd}.deleteBy${field.propertyName?cap_first}(info.get${field.propertyName?cap_first}());
        return success();
    }
</#if>
</#list>
 }
