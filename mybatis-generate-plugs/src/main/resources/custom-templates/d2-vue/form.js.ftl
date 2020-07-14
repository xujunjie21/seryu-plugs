<#assign js_name="${table.name?replace(cfg.tablePrefix,'')?replace(cfg.detail,'')?upper_case}">

import form from '@/mixins/crud-form'
import utils from '@/utils/index'

export default {
    mixins: [ form ],
    data () {
        return {
            api: {
                detail: '${js_name}_DETAIL',
                create: '${js_name}_CREATE',
                update: '${js_name}_UPDATE'
            },
                dialog: {
                width: '900px'
            }
        }
    },
    computed: {
        setting () {
            return [
                <#list table.fields as field>
                    <#if !field.keyIdentityFlag>
                    <#if field.propertyType == "String">
                    {
                        prop: '${field.name?lower_case}',
                        label: '${field.comment}',
                        render: () => <el-input vModel={ this.form.model.${field.name?lower_case}} clearable/>
                    },
                    </#if>
                    <#if field.propertyType == "Long" || field.propertyType == "Integer">
                    {
                        prop: '${field.name?lower_case}',
                        default: this.$env.VUE_APP_DICT_STATUS_ACTIVE,
                        label: '${field.comment}',
                        render: () => <d2-dict-radio vModel={ this.form.model.${field.name?lower_case} } name="${field.name?lower_case}" button/>
                    },
                    </#if>
                    </#if>
                    </#list>
                    {
                        prop: 'remark',
                        default: '',
                        label: '备注',
                        col: { span: 24 },
                        render: () => <el-input vModel={ this.form.model.remark } clearable/>
                    }
                ]
            }
        },
    methods: {
        /**
        * @description 在提交表单之前可选进行数据处理
        * @param {Object} data 默认的表单数据
        */
        transformSubmitData (data) {
            // 新建模式下全部发送
            return data
        }
    }
}
