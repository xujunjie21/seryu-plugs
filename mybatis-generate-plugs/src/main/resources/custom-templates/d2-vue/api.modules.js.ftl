<#assign js_name="${table.name?replace(cfg.tablePrefix,'')?replace(cfg.detail,'')?upper_case}">

export default ({ request }) => ({
    /**
    * @description ${table.comment!}列表
    */
    ${js_name}_ALL (query = {}) {
        return request({
            url: '${cfg.api_url[table.name]}/index',
            method: 'post',
            data: query
        })
    },
    /**
    * @description ${table.comment!}创建
    */
    ${js_name}_CREATE (data) {
        return request({
            url: '${cfg.api_url[table.name]}/create',
            method: 'put',
            data
        })
    },
    /**
    * @description ${table.comment!}详情
    */
    ${js_name}_DETAIL (id) {
        return request({
            url: '${cfg.api_url[table.name]}/info',
            method: 'post',
            data: {
            id
            }
        })
    },
    /**
    * @description ${table.comment!}编辑
    */
    ${js_name}_UPDATE (data) {
        return request({
            url: '${cfg.api_url[table.name]}/update',
            method: 'put',
            data
        })
    },
    /**
    * @description ${table.comment!}删除
    */
    ${js_name}_DELETE (id) {
        return request({
            url: '${cfg.api_url[table.name]}/delete',
            method: 'delete',
            data: {
            id
            }
        })
    }
})
