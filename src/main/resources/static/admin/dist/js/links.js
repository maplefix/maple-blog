/**
 * @author Maple created on 2019/7/25
 * @description 友链管理模块
 * @version v2.1
 */
$(function () {
    //加载友链数据列表
    $("#jqGrid").jqGrid({
        url: '/api/admin/links/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'linksId', index: 'linksId', width: 50, key: true, hidden: true},
            {label: '网站名称', name: 'linksName', index: 'linksName', width: 60},
            {label: '网站链接', name: 'linksUrl', index: 'linksUrl', width: 80},
            {label: '网站favicon', name: 'headerImg', index: 'headerImg', width: 110},
            {label: '网站描述', name: 'description', index: 'description', width: 100},
            {label: '邮件地址', name: 'linksEmail', index: 'linksEmail', width: 50},
            {label: '排序值', name: 'linksRank', index: 'linksRank', width: 25},
            {label: '添加时间', name: 'createDate', index: 'createDate', width: 50},
            {label: '修改时间', name: 'updateDate', index: 'updateDate', width: 50, hidden: true},
            {label: '是否显示', name: 'display', index: 'display', width: 50, hidden: true},
            {label: '友链类型', name: 'linksType', index: 'linksType', width: 50, hidden: true}
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
function searchLinks(){
    var linksName = $("#keyword").val();
    if(linksName != null && linksName != '' && linksName != undefined){
        linksName = linksName.trim();
    }
    $("#jqGrid").jqGrid('setGridParam',{
        datatype:'json',
        page:1,
        postData : {
            "linksName": linksName
        },
        page:1
    }).trigger("reloadGrid");
}

/**
 * 重置按钮
 */
function resetlinks() {
    $("#keyword").val('');
    $("#jqGrid").trigger("reloadGrid");
}

/**
 * 添加按钮
 */
function linksAdd() {
    reset();
    $('.modal-title').html('');
    $('#linksModal').modal('show');
}

/**
 * 绑定modal上的保存按钮
 */
$('#saveButton').click(function () {
    let linksId = $("#linksId").val();
    let linksName = $("#linksName").val();
    let linksUrl = $("#linksUrl").val();
    let description = $("#description").val();
    let linksRank = $("#linksRank").val();
    if (!validCN_ENString2_18(linksName)) {
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("请输入符合规范的名称！");
        return;
    }
    if (!isURL(linksUrl)) {
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("请输入符合规范的网址！");
        return;
    }
    if (!validCN_ENString2_100(description)) {
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("请输入符合规范的描述！");
        return;
    }
    if (isNull(linksRank) || linksRank < 0) {
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("请输入符合规范的排序值！");
        return;
    }
    let params = $("#linksForm").serialize();
    let url = '/api/admin/links/save';
    if (linksId != null && linksId != "" && linksId != undefined ) {
        url = '/api/admin/links/update';
    }
    $.ajax({
        type: 'POST',//方法类型
        url: url,
        data: params,
        //contentType: "application/json",
        success: function (result) {
            result = eval("("+result+")");
            if (result.code === 0) {
                $('#linksModal').modal('hide');
                swal("保存成功", {
                    icon: "success",
                });
                reload();
                $("#jqGrid").trigger("reloadGrid");
            }
            else {
                $('#linksModal').modal('hide');
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
 * 友链编辑
 */
function linksEdit() {
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
    //填充数据至modal
    $("#linksId").val(rowData.linksId);
    //下拉框回显
    var all_select=$("#linksType > option");
    for(var i=0;i<all_select.length;i++){
        var value=all_select[i].value;
        console.log(rowData)
        console.log(rowData.linksType)
        if(rowData.linksType == value){  //取select中所有的option的值与其进行对比，相等则令这个option添加上selected属性
            $("#linksType option[value='"+value+"']").attr("selected","selected");
        }
    }

    $("#linksName").val(rowData.linksName);
    $("#headerImg").val(rowData.headerImg);
    $("#linksEmail").val(rowData.linksEmail);
    $("#linksUrl").val(rowData.linksUrl);
    $("#description").val(rowData.description);
    $("#linksRank").val(rowData.linksRank);
    console.log(rowData.display)

    if (rowData.display === '1') {//显示

        $("input[name=display][value='1']").attr("checked",true);
    }else {
        $("input[name=display][value='0']").attr("checked",true);
    }

    $('#linksModal').modal('show');
}

/**
 * 批量删除友链
 */
function deleteLinks() {
    var ids = getSelectedRows();
    if (ids == null) {
        return;
    }
    swal({
        title: "确认弹框",
        text: "确认要删除选择的友链吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "POST",
                    url: "/api/admin/links/delete",
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
 * 重置功能
 */
function reset() {
    //填充数据至modal
    $("#linksId").val('');
    $("#linksType option:first").prop("selected", 'selected');
    $("#linksName").val('');
    $("#headerImg").val('');
    $("#linksEmail").val('');
    $("#linksUrl").val('');
    $("#description").val('');
    $("#linksRank").val(0);
    $("input[name=display][value='1']").attr("checked",true);
    $('#edit-error-msg').css("display", "none");

}