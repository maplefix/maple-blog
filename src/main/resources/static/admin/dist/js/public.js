
/**
 * @author Maple created on 2019/7/25
 * @description ajax扩展，用于每个ajax请求头设置JWT的token信息及通用校验
 * @version v2.1
 */
$.extend({
    newAjax: function(option) {
        let accessToken = localStorage.getItem('accessToken');
        let args = {
            type: "get",
            cache: false,
            dataType: "json", //默认后不显示图片上传中
            data: null,
            contentType: 'application/json',
            beforeSend: function(request) {
                request.setRequestHeader("Authorization", accessToken);
            },
            dataFilter: function(d) {  },
            success: function(data) { },
            error: function(d) {
                console.log(d)
            }
        }

        $.each(option, function(key, value) {
            args[key] = value;
        });

        if(!args.url || args.url == undefined) {
            throw new Error('ajax参数错误！');
        };

        $.ajax(args);
    }

});

<!-- 正则验证 start-->
/**
 * 验证邮箱
 * @param sText
 * @returns {boolean}
 */
function isVailEmail (sText){
    let reEmail=/^(\w+\.?)*\w+@(?:\w+\.)\w+$/;
    return reEmail.test(sText);
}

/**
 * 验证手机号
 * @param sText
 * @constructor
 */
function PhoneNum(sText){
    let rePhone = /^1[0-9]{10}/;
    return rePhone.test(sText);
}

/**
 * 判空
 *
 * @param obj
 * @returns {boolean}
 */
function isNull(obj) {
    if (obj == null || obj == undefined || obj.trim() == "") {
        return true;
    }
    return false;
}

/**
 * 参数长度验证
 *
 * @param obj
 * @param length
 * @returns {boolean}
 */
function validLength(obj, length) {
    if (obj.trim().length < length) {
        return true;
    }
    return false;
}

/**
 * url验证
 *
 * @param str
 * @returns {boolean}
 */
function isURL(str_url) {
    let strRegex = "^((https|http|ftp|rtsp|mms)?://)"
        + "(([0-9]{1,3}\.){3}[0-9]{1,3}"
        + "|"
        + "([0-9a-zA-Z_!~*'()-]+\.)*"
        + "([0-9a-zA-Z][0-9a-zA-Z-]{0,61})?[0-9a-zA-Z]\."
        + "[a-zA-Z]{2,6})"
        + "(:[0-9]{1,4})?"
        + "((/?)|"
        + "(/[0-9a-zA-Z_!~*'().;?:@&=+$,%#-]+)+/?)$";
    let re = new RegExp(strRegex);
    if (re.test(str_url)) {
        return (true);
    } else {
        return (false);
    }
}

/**
 * 用户名称验证 4到16位（字母，数字，下划线，减号）
 *
 * @param userName
 * @returns {boolean}
 */
function validUserName(userName) {
    let pattern = /^[a-zA-Z0-9_-]{4,16}$/;
    if (pattern.test(userName.trim())) {
        return (true);
    } else {
        return (false);
    }
}

/**
 * 正则匹配2-18位的中英文字符串
 *
 * @param str
 * @returns {boolean}
 */
function validCN_ENString2_18(str) {
    let pattern = /^[a-zA-Z0-9-\u4E00-\u9FA5_,， ]{2,18}$/;
    if (pattern.test(str.trim())) {
        return (true);
    } else {
        return (false);
    }
}

/**
 * 正则匹配2-100位的中英文字符串
 *
 * @param str
 * @returns {boolean}
 */
function validCN_ENString2_100(str) {
    let pattern = /^[a-zA-Z0-9-\u4E00-\u9FA5_,， ]{2,100}$/;
    if (pattern.test(str.trim())) {
        return (true);
    } else {
        return (false);
    }
}

/**
 * 用户密码验证 最少6位，最多20位字母或数字的组合
 *
 * @param password
 * @returns {boolean}
 */
function validPassword(password) {
    let pattern = /^[a-zA-Z0-9]{6,20}$/;
    if (pattern.test(password.trim())) {
        return (true);
    } else {
        return (false);
    }
}

<!-- 正则验证 end-->

/**
 * 获取jqGrid选中的一条记录
 * @returns {*}
 */
function getSelectedRow() {
    let grid = $("#jqGrid");
    let rowKey = grid.getGridParam("selrow");
    if (!rowKey) {
        swal("请选择一条记录", {
            icon: "warning",
        });
        return;
    }
    let selectedIDs = grid.getGridParam("selarrrow");
    if (selectedIDs.length > 1) {
        swal("只能选择一条记录", {
            icon: "warning",
        });
        return;
    }
    return selectedIDs[0];
}

/**
 * 获取jqGrid选中的一条记录(不出现弹框)
 * @returns {*}
 */
function getSelectedOneRowData() {
    debugger
    let id = $("#jqGrid").jqGrid("getGridParam","selrow");
    let rowData = $("#jqGrid").jqGrid("getRowData",id);
    if(id == null || id == "" || id == undefined){
        swal("请选择一条记录", {
            icon: "warning",
        });
        return;
    }
    return rowData[0];
}

/**
 * 获取jqGrid选中的多条记录id
 * @returns {*}
 */
function getSelectedRows() {
    //选中的多行数据的id集合
    let ids = $("#jqGrid").jqGrid("getGridParam","selarrrow");
    //let rowData = $("#jqGrid").jqGrid("getRowData",id);
    if(ids.length<1){
        swal("请选择一条记录", {
            icon: "warning",
        });
        return;
    }
    return ids;
}

/**
 * jqGrid重新加载
 */
function reload() {
    jQuery("#jqGrid").trigger("reloadGrid");
}
