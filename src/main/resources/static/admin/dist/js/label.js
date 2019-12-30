/**
 * @author Maple created on 2019/7/25
 * @description 博客标签模块
 * @version v2.1
 */
$(function () {
    //加载博客标签数据列表
    $("#jqGrid").jqGrid({
        url: '/api/admin/label/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'labelId', index: 'labelId', width: 50, key: true, hidden: true},
            {label: '标签名称', name: 'labelName', index: 'labelName', width: 120},
            {label: '关联博客', name: 'blogCount', index: 'blogCount', width: 50,formatter: blogCountFormatter},
            {label: '创建时间', name: 'createDate', index: 'createDate', width: 120},
            {label: '更新时间', name: 'updateDate', index: 'updateDate', width: 120},
            {label: '备注', name: 'remark', index: 'remark', width: 120}
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
    //关联博客数格式化
    function blogCountFormatter(cellvalue) {
        return "<button type='button' class='btn btn-block btn-sm' style='width: 50%;'>"+cellvalue+"篇</button>";
    }
});
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
 * 查询按钮
 */
function searchLabel(){
    var startDate = $("#beginDate").val();
    var endDate = $("#endDate").val();
    var labelName = $("#keyword").val();
    $("#jqGrid").jqGrid('setGridParam',{
        datatype:'json',
        page:1,
        postData : {
            "startDate" : startDate,
            "endDate":endDate,
            "labelName": labelName
        },
        page:1
    }).trigger("reloadGrid");
}

/**
 * 重置按钮
 */
function resetLabel() {
    $("#keyword").val('');
    $("#beginDate").val('');
    $("#endDate").val('');
    $("#jqGrid").trigger("reloadGrid");
}
/**
 * jqGrid重新加载
 */
function reload() {
    jQuery("#jqGrid").trigger("reloadGrid");
}

//绑定modal上的保存按钮
$('#saveButton').click(function () {
    var params = $("#labelForm").serialize();
    var labelName = $("#labelName").val();
    if (!validCN_ENString2_18(labelName)) {
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("请输入符合规范的标签名称！");
    } else {
        var url = "/api/admin/label/save";
        var id = $("#labelId").val();
        if(id != null && id != undefined && id != ""){
            url = "/api/admin/label/update";
        }
        $.ajax({
            type: 'POST',//方法类型
            url: url,
            data: params,
            success: function (result) {
                result=eval("("+result+")");
                console.log(result)
                console.log(result.code)
                if (result.code === 0) {
                    console.log(result.code)
                    $('#labelModal').modal('hide');
                    swal("操作成功", {
                        icon: "success",
                    });
                    reload();
                }else {
                    $('#labelModal').modal('hide');
                    swal(result.msg, {
                        icon: "error",
                    });
                }
                ;
            },
            error: function () {
                swal("操作失败", {
                    icon: "error",
                });
            }
        });
    }
});

/**
 * 编辑标签按钮
 */
function labelEdit() {
    reset();
    $('#edit-error-msg').css("display", "none");
    var id = $("#jqGrid").jqGrid("getGridParam","selrow");
    var rowData = $("#jqGrid").jqGrid("getRowData",id);
    if (id == null) {
        swal("请选择一条数据", {
            icon: "error",
        });
        return;
    }
    $('.modal-title').html('');
    $('#labelModal').modal('show');
    $("#labelId").val(rowData.labelId);
    $("#labelName").val(rowData.labelName);
    $("#remark").val(rowData.remark);
}

/**
 * 重置输入框的值
 */
function reset() {
    $("#labelId").val('');
    $("#labelName").val('');
    $("#remark").val('');
}
/**
 * 新增标签方法
 */
function labelAdd() {
    reset();
    $('#edit-error-msg').css("display", "none");
    $('.modal-title').html('');
    $('#labelModal').modal('show');
}

/**
 * 批量删除数据
 */
function deleteLabel() {
    var ids = getSelectedRows();
    if (ids == null) {
        return;
    }
    swal({
        title: "确认弹框",
        text: "确认要删除选择的标签吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "POST",
                    url: "/api/admin/label/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (result) {
                        result=eval("("+result+")");
                        if (result.code == 0) {
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
 * 导出标签列表数据
 */
function exportLabel() {
    let ids = getSelectedRows();
    swal({
        title: "确认弹框",
        text: "确认要导出选择的标签吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "POST",
                    url: "/api/admin/label/export",
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
