/**
 * @author Maple created on 2019/7/25
 * @description 字典管理模块
 * @version v1.0
 */
$(function () {
    //加载字典数据列表
    $("#jqGrid").jqGrid({
        url: '/api/admin/dict/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'dictId', index: 'dictId', width: 50, key: true, hidden: true},
            {label: '关键字', name: 'keyWord', index: 'keyWord', width: 60},
            {label: '字典名', name: 'dictName', index: 'dictName', width: 80},
            {label: '字典值', name: 'dictValue', index: 'dictValue', width: 110},
            {label: '排序值', name: 'sort', index: 'sort', width: 25},
            {label: '添加时间', name: 'createDate', index: 'createDate', width: 50},
            {label: '修改时间', name: 'updateDate', index: 'updateDate', width: 50},
            {label: '备注说明', name: 'remark', index: 'remark', width: 50}
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
});

/**
 * 查询按钮
 */
function searchDict(){
    let keyWord = $("#keyword1").val();
    if(keyWord != null && keyWord != '' && keyWord != undefined){
        keyWord = keyWord.trim();
    }
    $("#jqGrid").jqGrid('setGridParam',{
        datatype:'json',
        page:1,
        postData : {
            "keyWord": keyWord
        },
        page:1
    }).trigger("reloadGrid");
}

/**
 * 重置按钮
 */
function resetDict() {
    $("#keyword1").val('');
    $("#jqGrid").trigger("reloadGrid");
}

/**
 * 添加按钮
 */
function dictAdd() {
    reset();
    $('.modal-title').html('');
    $('#dictModal').modal('show');
}

/**
 * 绑定modal上的保存按钮
 */
$('#saveButton').click(function () {
    let dictId = $("#dictId").val();
    let keyWord = $("#keyWord").val();
    let dictName = $("#dictName").val();
    let dictValue = $("#dictValue").val();
    let sort = $("#sort").val();
    let remark = $("#remark").val();
    if (!validCN_ENString2_18(keyWord)) {
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("请输入符合规范的关键字名称！");
        return;
    }
    if (!validCN_ENString2_18(dictName)) {
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("请输入符合规范的字典名！");
        return;
    }
    if (isNull(dictValue)) {
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("请输入符合规范的字典值！");
        return;
    }
    if (isNull(sort) || sort < 0) {
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("请输入符合规范的排序值！");
        return;
    }
    let params = $("#dictForm").serialize();
    let url = '/api/admin/dict/save';
    if (dictId != null && dictId != "" && dictId != undefined ) {
        url = '/api/admin/dict/update';
    }
    $.ajax({
        type: 'POST',//方法类型
        url: url,
        data: params,
        //contentType: "application/json",
        success: function (result) {
            result = eval("("+result+")");
            if (result.code === 0) {
                $('#dictModal').modal('hide');
                swal("保存成功", {
                    icon: "success",
                });
                reload();
                $("#jqGrid").trigger("reloadGrid");
            }
            else {
                $('#dictModal').modal('hide');
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

});

/**
 * 编辑字典
 */
function dictEdit() {
    reset();
    $('#edit-error-msg').css("display", "none");
    let id = $("#jqGrid").jqGrid("getGridParam","selrow");
    let rowData = $("#jqGrid").jqGrid("getRowData",id);
    if (id == null) {
        swal("请选择一条数据", {
            icon: "error",
        });
        return;
    }
    $('.modal-title').html('');
    //填充数据至modal
    $("#dictId").val(rowData.dictId);
    $("#keyWord").val(rowData.keyWord);
    $("#dictName").val(rowData.dictName);
    $("#dictValue").val(rowData.dictValue);
    $("#sort").val(rowData.sort);
    $("#remark").val(rowData.remark);
    $('#dictModal').modal('show');
}

/**
 * 批量删除字典项
 */
function deleteDict() {
    let ids = getSelectedRows();
    if (ids == null) {
        return;
    }
    swal({
        title: "确认弹框",
        text: "确认要删除选择的字典项吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "POST",
                    url: "/api/admin/dict/delete",
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
 * 重置按钮
 */
function reset() {
    //填充数据至modal
    $("#dictId").val('');
    $("#keyWord").val('');
    $("#dictName").val('');
    $("#dictValue").val('');
    $("#sort").val('');
    $("#remark").val('');
    $('#edit-error-msg').css("display", "none");
}

/**
 * 导出字典列表数据
 */
function exportDict() {
    let ids = getSelectedRows();
    swal({
        title: "确认弹框",
        text: "确认要导出选择的字典项吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "POST",
                    url: "/api/admin/dict/export",
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