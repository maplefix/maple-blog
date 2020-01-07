
/**
 * @author Maple created on 2019/7/25
 * @description 访问日志管理模块
 * @version v1.0
 */
$(function () {
    //加载访问日志数据列表
    $("#jqGrid").jqGrid({
        url: '/api/admin/visitLog/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'visitLogId', index: 'visitLogId', width: 50, key: true, hidden: true},
            {label: '模块名称', name: 'module', index: 'module', width: 30},
            {label: '访问IP', name: 'visitIp', index: 'visitIp', width: 50},
            {label: '访问地区', name: 'visitLocation', index: 'visitLocation', width: 50},
            {label: '访问URL', name: 'requestUrl', index: 'requestUrl', width: 90},
            {label: '访问浏览器', name: 'visitBrowser', index: 'visitBrowser', width: 35},
            {label: '操作系统', name: 'visitOs', index: 'visitOs', width: 35},
            {label: '状态', name: 'status', index: 'status', width: 50, formatter: statusFormatter},
            {label: '访问日期', name: 'visitDate', index: 'visitDate', width: 50},
            {label: '错误信息', name: 'visitMsg', index: 'visitMsg', width: 50}
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
    let visitIp = $("#visitIp").val();
    let module = $("#module").val();
    let beginDate = $("#beginDate").val();
    let endDate = $("#endDate").val();
    let status = $("#status").val();
    if(visitIp != null && visitIp != '' && visitIp != undefined){
        visitIp = visitIp.trim();
    }
    $("#jqGrid").jqGrid('setGridParam',{
        datatype:'json',
        page:1,
        postData : {
            "visitIp": visitIp,
            "module": module,
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
    $("#visitIp").val('');
    $("#module").val('');
    $("#status").val('');
    $("#beginDate").val('');
    $("#endDate").val('');
    $("#jqGrid").trigger("reloadGrid");
}


/**
 * 批量删除访问日志项
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
                    url: "/api/admin/visitLog/delete",
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
 * 导出访问日志
 */
function exportVisitLog() {
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
                    url: "/api/admin/visitLog/export",
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