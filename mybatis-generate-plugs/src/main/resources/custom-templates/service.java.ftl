package ${package.Service};

import ${cfg.packDTO}.${entity?substring(0,entity?length-2)}Dto;
import ${cfg.packDTO}.${entity?substring(0,entity?length-2)}QryDto;
import ${superServiceClassPackage};
import com.tyyd.potato.core.data.Page;
import com.tyyd.potato.core.data.PageData;

/**
 * <p>
 * ${table.comment!} 服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity?substring(0,entity?length-2)}Dto> {

    public PageData<${entity?substring(0,entity?length-2)}Dto> page(${entity?substring(0,entity?length-2)}QryDto qry, Page page);
}
</#if>
