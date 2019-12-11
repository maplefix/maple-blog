/**
 * @author Maple created on 2019/7/25
 * @description 登录日志理模块
 * @version v2.1
 */
$(function () {
    //加载登录日志数据列表
    $("#jqGrid").jqGrid({
        url: '/api/admin/loginLog/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'loginLogId', index: 'loginLogId', width: 50, key: true, hidden: true},
            {label: '登录名', name: 'loginName', index: 'loginName', width: 35},
            {label: '登录IP', name: 'loginIp', index: 'loginIp', width: 60},
            {label: '登录地区', name: 'loginLocation', index: 'loginLocation', width: 60},
            {label: '浏览器', name: 'loginBrowser', index: 'loginBrowser', width: 50},
            {label: '操作系统', name: 'loginOs', index: 'loginOs', width: 60},
            {label: '状态', name: 'status', index: 'status', width: 50, formatter: statusFormatter},
            {label: '登录日期', name: 'loginDate', index: 'loginDate', width: 50},
            {label: '登录信息', name: 'loginMsg', index: 'loginMsg', width: 30}
        ],
        height: 700,
        rowNum: 20,
        rowList: [20, 40, 80],
        styleUI: 'Bootstrap',
        loadtext: '信息读取中...',
        rownumbers: false,
        rownumWidth: 20,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        viewrecords: true,
        jsonReader: {
            root: "data.list",
            page: "data.currPage",
            total: "data.totalPage",
            records: "data.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "size",
            order: "order",
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });
    $(window).resize(function () {
        $("#jqGrid").setGridWidth($(".card-body").width());
    });
    //登录状态格式化
    function statusFormatter(cellvalue) {
        if (cellvalue == '0') {
            return "<button type=\"button\" class=\"btn btn-block btn-success btn-sm\" style=\"width: 50%;\">成功</button>";
        }else if (cellvalue == '1') {
            return "<button type=\"button\" class=\"btn btn-block btn-danger btn-sm\" style=\"width: 50%;\">失败</button>";
        }
    }
});

/**
 * 查询按钮
 */
function searchLog(){
    let loginIp = $("#loginIp").val();
    let status = $("#status").val();
    let beginDate = $("#beginDate").val();
    let endDate = $("#endDate").val();
    if(loginIp != null && loginIp != '' && loginIp != undefined){
        loginIp = loginIp.trim();
    }
    $("#jqGrid").jqGrid('setGridParam',{
        datatype:'json',
        page:1,
        postData : {
            "loginIp": loginIp,
            "status": status,
            "beginDate": beginDate,
            "endDate": endDate
        },
        page:1
    }).trigger("reloadGrid");
}
/**
 * 加载日期组件
 */
$("#beginDate").datetimepicker({
    //format: 'yyyy-mm-dd hh:ii:ss',
    format: 'yyyy-mm-dd',
    //minView:'month',
    language: 'zh-CN',
    todayHighlight: true,
    autoclose:true,
    minView: "month",
    todayBtn: true,
    //startDate:new Date() //限制开始日期从当前日期开始
}).on("click",function(){
    $("#beginDate").datetimepicker("setEndDate",$("#endDate").val())
});

$("#endDate").datetimepicker({
    format: 'yyyy-mm-dd',
    //minView:'month',
    language: 'zh-CN',
    todayHighlight: true,
    autoclose:true,
    minView: "month",
    todayBtn: true,
    startDate:new Date()
}).on("click",function(){
    $("#endDate").datetimepicker("setStartDate",$("#beginDate").val());
});
/**
 * 重置按钮
 */
function resetLog() {
    $("#loginIp").val('');
    $("#status").val('');
    $("#beginDate").val('');
    $("#endDate").val('');
    $("#jqGrid").trigger("reloadGrid");
}


/**
 * 批量删除登录日志项
 */
function deleteLog() {
    let ids = getSelectedRows();
    if (ids == null) {
        return;
    }
    swal({
        title: "确认弹框",
        text: "确认要删除选择的日志项吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "POST",
                    url: "/api/admin/loginLog/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (result) {
                        result = eval("("+result+")");
                        if (result.code === 0) {
                            swal("删除成功", {
                                icon: "success",
                            });
                            $("#jqGrid").trigger("reloadGrid");
                        } else {
                            swal(result.msg, {
                                icon: "error",
                            });
                        }
                    }
                });
            }
        }
    );
}

/**
 * 导出登录日志 TODO
 */
function logExport() {

}