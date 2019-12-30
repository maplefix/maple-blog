/**
 * @author Maple created on 2019/7/25
 * @description 操作日志管理模块
 * @version v2.1
 */
$(function () {
    //加载操作日志数据列表
    $("#jqGrid").jqGrid({
        url: '/api/admin/operationLog/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'operId', index: 'operId', width: 10, key: true, hidden: true},
            {label: '模块名称', name: 'module', index: 'module', width: 40},
            {label: '业务类型', name: 'businessType', index: 'businessType', width: 30},
            {label: '请求方法', name: 'method', index: 'method', width: 130},
            {label: '请求IP', name: 'operIp', index: 'operIp', width: 50},
            {label: '操作地区', name: 'operLocation', index: 'operLocation', width: 60},
            {label: '操作URL', name: 'operUrl', index: 'operUrl', width: 50},
            {label: '操作浏览器', name: 'operBrowser', index: 'operBrowser', width: 50},
            {label: '操作系统', name: 'operOs', index: 'operOs', width: 50},
            {label: '状态', name: 'status', index: 'status', width: 50, formatter: statusFormatter},
            {label: '操作日期', name: 'operDate', index: 'operDate', width: 60},
            {label: '错误信息', name: 'operMsg', index: 'operMsg', width: 50}
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
    let operIp = $("#operIp").val();
    let module = $("#module").val();
    let businessType = $("#businessType").val();
    let status = $("#status").val();
    let beginDate = $("#beginDate").val();
    let endDate = $("#endDate").val();
    if(operIp != null && operIp != '' && operIp != undefined){
        operIp = operIp.trim();
    }
    $("#jqGrid").jqGrid('setGridParam',{
        datatype:'json',
        page:1,
        postData : {
            "operIp": operIp,
            "module": module,
            "businessType": businessType,
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
    $("#operIp").val('');
    $("#module").val('');
    $("#businessType").val('');
    $("#status").val('');
    $("#beginDate").val('');
    $("#endDate").val('');
    $("#jqGrid").trigger("reloadGrid");
}


/**
 * 批量删除操作日志项
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
                    url: "/api/admin/operationLog/delete",
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
 * 导出操作日志
 */
function exportOperationLog() {
    let ids = getSelectedRows();
    swal({
        title: "确认弹框",
        text: "确认要导出选择的日志项吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "POST",
                    url: "/api/admin/operationLog/export",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (result) {
                        result = eval("("+result+")");
                        if (result.code === 0) {
                            swal("导出成功", {
                                icon: "success",
                                //两秒自动关闭
                                timer:2000
                            });
                            //下载excel操作
                            window.location.href = "common/download?fileName=" + result.msg + "&deleteFlag=" + true;
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