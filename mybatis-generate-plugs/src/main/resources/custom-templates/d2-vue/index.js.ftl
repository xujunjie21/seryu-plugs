<#assign js_name="${table.name?replace(cfg.tablePrefix,'')?replace(cfg.detail,'')?upper_case}">
<#assign permission_name="${table.name?replace(cfg.tablePrefix,'')?replace(cfg.detail,'')?replace('_','-')?lower_case}">

import utils from '@/utils'
import table from '@/mixins/crud-table.js'

export default {
    mixins: [ table ],
        components: {
            componentForm: () => import('./form')
    },
    render () {
        const page =
            <d2-container spacious>
                <d2-search-panel slot="header" vModel={ this.search.panel.active }>
                    <d2-bar slot="title">
                        <d2-bar-space/>
                        { this.p('query') ? <d2-bar-cell>{ this.vNodePaginationMini }</d2-bar-cell> : <d2-bar-cell>{ this.vNodeSearchPanelAlertNoPermissionQuery }</d2-bar-cell> }
                        <d2-bar-space/>
                        <d2-bar-cell>
                            <el-button-group>
                                { this.p('query') ? this.vNodeButtonSearch : null }
                                { this.vNodeButtonTableColumnsFilterTrigger }
                            </el-button-group>
                        </d2-bar-cell>
                        { this.p('add') ? <d2-bar-cell>{ this.vNodeButtonCreate }</d2-bar-cell> : null }
                    </d2-bar>
                    { this.p('query') ? this.vNodeSearchForm : null }
                </d2-search-panel>
                { this.vNodeTable }
                <d2-bar slot="footer">
                    <d2-bar-cell>{ this.vNodePaginationFull }</d2-bar-cell>
                    <d2-bar-space/>
                </d2-bar>
                <component-form ref="form" on-success={ this.research }/>
                { this.vNodeTableColumnsFilter }
            </d2-container>
        return page
    },
    data () {
        return {
            api: {
                index: '${js_name}_ALL',
                delete:'${js_name}_DELETE'
            },
            permission: {
                query: 'terminal:${permission_name}:query',
                add: 'terminal:${permission_name}:add',
                edit: 'terminal:${permission_name}:edit',
                remove: 'terminal:${permission_name}:remove'
            }
        }
    },
    computed: {
        // 配置项
        // 表格列
        // 建议的书写顺序 [prop] -> [label] -> [align] -> [minWidth][width] -> [fixed] -> [other] -> [render][formatter] -> [if][show]
        settingColumns () {
            return [
<#list table.fields as field>
<#if field.propertyType == "String">
                { prop: '${field.name?lower_case}', label: '${field.comment}'},
</#if>
<#if field.propertyType == "Long" || field.propertyType == "Integer">
                { prop: '${field.name?lower_case}', label: '${field.comment}', render: ({ row }) => <d2-dict name="${field.name?lower_case}" value={ row.${field.name?lower_case} }/>},
</#if>
</#list>
                { prop: 'create_user_id', label: '创建人员', show: false },
                { prop: 'create_date', label: '创建时间', width: '200px', formatter: row => utils.time.format(row.create_date, 'YYYY/M/D HH:mm:ss')},
                { prop: 'update_user_id', label: '更新人员', width: '100px', show: false },
                { prop: 'update_date', label: '更新时间', width: '200px', formatter: row => utils.time.format(row.update_date, 'YYYY/M/D HH:mm:ss')}
            ].map(setting => {
                setting.sortable = 'custom'
                return setting
        })
    },
    // 配置项
    // 表格操作列配置
    settingActionsConfig () {
        return ({ row }) => [
            ...this.p('edit', [{ icon: 'el-icon-edit-outline', label: '修改', action: () => this.edit(row.id) }], []),
            ...this.p('remove', [{ icon: 'el-icon-delete', label: '删除', type: 'danger', confirm: `确定删除 [ ${r'${row.name}'} ] 吗`, action: () => this.delete(row.id) }], [])
    ]
    },
    // 配置项
    // 表格搜索条件
    // 建议的书写顺序 [prop] -> [label] -> [default] -> [render] -> [if][show]
    settingSearch () {
        return [
<#list table.fields as field>
    <#if field.propertyType == "String">
            {
            prop: '${field.name?lower_case}',
            label: '${field.comment}',
            default: '',
            render: () => <el-input vModel={ this.search.form.model.id } style="width:100px;" clearable/>
            },
    </#if>
    <#if field.propertyType == "Long" || field.propertyType == "Integer">
            {
            prop: '${field.name?lower_case}',
            label: '${field.comment}',
            default: this.$env.VUE_CHANNEL_DICT_EMPTY_NUMBER,
            render: () => <d2-dict-radio vModel={ this.search.form.model.${field.name?lower_case} } name="${field.name?lower_case}" button all/>
            },
    </#if>
</#list>
            {
                prop: 'start_time',
                label: '开始时间',
                default: '',
                render: () => <el-date-picker vModel={ this.search.form.model.start_time } value-format="yyyy-MM-dd" type="date" placeholder="开始时间" style="width:130px;"/>
            },
            {
                prop: 'end_time',
                label: '结束时间',
                default: '',
                render: () => <el-date-picker vModel={ this.search.form.model.end_time } value-format="yyyy-MM-dd" type="date" placeholder="结束时间" style="width:130px;"/>
            }
        ]
        }
    },
    methods: {
        onFormSuccess () {
            this.$store.dispatch('d2admin/permission/load', { focus: true })
            this.research()
        }
    }
}
