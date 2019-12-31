/**
 * @author Maple created on 2019/12/2
 * @description 书单管理模块
 * @version v2.1
 */
$(function () {
    //加载书单数据列表
    $("#jqGrid").jqGrid({
        url: '/api/admin/bookList/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'bookListId', index: 'bookListId', width: 50, key: true, hidden: true},
            {label: '书名', name: 'bookName', index: 'bookName', width: 60},
            {label: '作者名', name: 'bookAuthor', index: 'bookAuthor', width: 60},
            {label: '创建时间', name: 'createDate', index: 'createDate', width: 50},
            {label: '更新时间', name: 'updateDate', index: 'updateDate', width: 50},
            {label: '开始时间', name: 'readBeginDate', index: 'readBeginDate', width: 50},
            {label: '结束时间', name: 'readEndDate', index: 'readEndDate', width: 50},
            {label: '书评', name: 'reviews', index: 'reviews', width: 150},
            {label: '备注', name: 'remark', index: 'remark', width: 50,hidden: true},
            {label: '状态', name: 'readStatus', index: 'readStatus', width: 50, formatter: statusFormatter}
        ],
        height: 700,
        rowNum: 20,
        rowList: [20, 40, 80],
        styleUI: "Bootstrap",
        loadtext: "信息读取中...",
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

    //阅读状态格式化
    function statusFormatter(cellvalue) {
        if (cellvalue == '0') {
            return "<button type=\"button\" class=\"btn btn-block btn-warning btn-sm\" style=\"width: 100%;\">未阅读</button>";
        }else if (cellvalue == '1') {
            return "<button type=\"button\" class=\"btn btn-block btn-success btn-sm\" style=\"width: 100%;\">阅读中</button>";
        }else if (cellvalue == '2') {
            return "<button type=\"button\" class=\"btn btn-block btn-secondary btn-sm\" style=\"width: 100%;\">阅读完</button>";
        }
    }
});

/**
 * 查询按钮
 */
function searchBookList(){
    let keyWord = $("#keyword").val();
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
function resetBookList() {
    $("#keyword").val('');
    $("#jqGrid").trigger("reloadGrid");
}

/**
 * 添加按钮
 */
function bookListAdd() {
    reset();
    $('.modal-title').html('');
    $('#bookListModal').modal('show');
}

/**
 * 编辑或新增确认按钮
 */
$('#saveButton').click(function () {
    let bookListId = $("#bookListId").val();
    let bookName = $("#bookName").val();
    let bookAuthor = $("#bookAuthor").val();
    let remark = $("#remark").val();

    if (bookName == "" || bookName == null) {
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("请输入符合规范的书名！");
        return;
    }
    if (bookAuthor == "" || bookAuthor == null) {
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("请输入符合规范的作者名！");
        return;
    }
    let params = $("#bookListForm").serialize();
    let url = '/api/admin/bookList/save';
    if (bookListId != null && bookListId != "" && bookListId != undefined ) {
        url = '/api/admin/bookList/update';
    }
    $.ajax({
        type: 'POST',//方法类型
        url: url,
        data: params,
        //contentType: "application/json",
        success: function (result) {
            result = eval("("+result+")");
            if (result.code === 0) {
                $('#bookListModal').modal('hide');
                swal("保存成功", {
                    icon: "success",
                });
                reload();
                $("#jqGrid").trigger("reloadGrid");
            }else {
                $('#bookListModal').modal('hide');
                swal(result.msg, {
                    icon: "error",
                });
            }
        },
        error: function () {
            swal("操作失败", {
                icon: "error",
            });
        }
    });

});

/**
 * 编辑书单
 */
function bookListEdit() {
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
    $("#bookListId").val(rowData.bookListId);
    $("#bookName").val(rowData.bookName);
    $("#bookAuthor").val(rowData.bookAuthor);
    $("#remark").val(rowData.remark);
    $('#bookListModal').modal('show');
}

/**
 * 批量删除书单项
 */
function bookListDelete() {
    let ids = getSelectedRows();
    if (ids == null) {
        return;
    }
    swal({
        title: "确认弹框",
        text: "确认要删除选择的书单项吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "POST",
                    url: "/api/admin/bookList/delete",
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
    $("#bookListId").val('');
    $("#bookName").val('');
    $("#bookAuthor").val('');
    $("#remark").val('');
    $('#edit-error-msg').css("display", "none");
}

/**
 * 开始阅读
 */
function startRead(){
    $('#edit-error-msg').css("display", "none");
    let id = $("#jqGrid").jqGrid("getGridParam","selrow");
    let rowData = $("#jqGrid").jqGrid("getRowData",id);
    if (id == null) {
        swal("请选择一条数据", {
            icon: "error",
        });
        return;
    }
    swal({
        title: "确认弹框",
        text: "确认开始阅读这本书吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
        if (flag) {
            $.ajax({
                type: 'POST',//方法类型
                url: "/api/admin/bookList/startRead",
                data: {
                    "bookListId": rowData.bookListId
                },
                //contentType: "application/json",
                success: function (result) {
                    result = eval("(" + result + ")");
                    if (result.code === 0) {
                        $('#bookListModal').modal('hide');
                        swal("开始阅读成功", {
                            icon: "success",
                        });
                        reload();
                        $("#jqGrid").trigger("reloadGrid");
                    } else {
                        $('#bookListModal').modal('hide');
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
}

/**
 * 结束阅读
 */
function endRead(){
    $('#edit-error-msg').css("display", "none");
    let id = $("#jqGrid").jqGrid("getGridParam","selrow");
    let rowData = $("#jqGrid").jqGrid("getRowData",id);
    if (id == null) {
        swal("请选择一条数据", {
            icon: "error",
        });
        return;
    }
    swal({
        title: "确认弹框",
        text: "确认结束阅读这本书吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
        if (flag) {
            $.ajax({
                type: 'POST',//方法类型
                url: "/api/admin/bookList/endRead",
                data: {
                    "bookListId": rowData.bookListId
                },
                //contentType: "application/json",
                success: function (result) {
                    result = eval("(" + result + ")");
                    if (result.code === 0) {
                        $('#bookListModal').modal('hide');
                        swal("结束阅读成功", {
                            icon: "success",
                        });
                        reload();
                        $("#jqGrid").trigger("reloadGrid");
                    } else {
                        $('#bookListModal').modal('hide');
                        swal(result.msg, {
                            icon: "error",
                        });
                    }
                },
                error: function () {
                    swal("操作失败", {
                        icon: "error",
                    });
                }
            });
        }
    });
}

/**
 * 写书评按钮
 */
function writeReviews(){
    $('#edit-error-msg').css("display", "none");
    let id = $("#jqGrid").jqGrid("getGridParam","selrow");
    let rowData = $("#jqGrid").jqGrid("getRowData",id);
    if (id == null) {
        swal("请选择一条数据", {
            icon: "error",
        });
        return;
    }
    $("#bookListIdV").val(rowData.bookListId);
    $("#reviews").val(rowData.reviews);
    $('.modal-title').html('');
    $('#writeReviewsModal').modal('show');
}
/**
 * 书评提交按钮
 */
$('#saveReviewsButton').click(function () {
    let params = $("#reviewsForm").serialize();
    let url = '/api/admin/bookList/writeReviews';
    $.ajax({
        type: 'POST',//方法类型
        url: url,
        data: params,
        //contentType: "application/json",
        success: function (result) {
            result = eval("("+result+")");
            if (result.code === 0) {
                $('#writeReviewsModal').modal('hide');
                $('#bookListModal').modal('hide');
                swal("书评保存成功", {
                    icon: "success",
                });
                reload();
                $("#jqGrid").trigger("reloadGrid");
            }else {
                $('#writeReviewsModal').modal('hide');
                swal(result.msg, {
                    icon: "error",
                });
            }
        },
        error: function () {
            swal("操作失败", {
                icon: "error",
            });
        }
    });
});

/**
 * 导出书单列表数据
 */
function exportBookList() {
    let ids = getSelectedRows();
    swal({
        title: "确认弹框",
        text: "确认要导出选择的书单项吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "POST",
                    url: "/api/admin/bookList/export",
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
            //formSubmit(ids);
            }
        }
    );
}

/**
 * 构建表单提交
 * @param ids
 */
function formSubmit(ids) {
    var form=$("<form>");//定义一个form表单
    form.attr("style","display:none");
    form.attr("target","");
    form.attr("method","post");
    form.attr("action","bookList/export");
    var input1=$("<input>");
    input1.attr("type","hidden");
    input1.attr("name","ids");
    input1.attr("value",ids);
    $("body").append(form);//将表单放置在web中
    form.append(input1);
    form.submit();
}