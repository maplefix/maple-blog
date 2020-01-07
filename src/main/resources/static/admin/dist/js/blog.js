/**
 * @author Maple created on 2019/7/25
 * @description 博客模块
 * @version v1.0
 */
$(function () {
    initCategory();
    //加列表数据
    $("#jqGrid").jqGrid({
        url: '/api/admin/blog/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'blogId', index: 'blogId', width: 50, key: true, hidden: true},
            {label: '标题', name: 'title', index: 'title', width: 140},
            {label: '预览图', name: 'coverImg', index: 'coverImg', width: 120, formatter: coverImageFormatter},
            {label: '浏览量', name: 'hits', index: 'hits', width: 60},
            {label: '状态', name: 'status', index: 'status', width: 60, formatter: statusFormatter},
            {label: '博客分类', name: 'categoryName', index: 'categoryName', width: 60},
            {label: '创建时间', name: 'createDate', index: 'createDate', width: 90},
            {label: '更新时间', name: 'updateDate', index: 'updateDate', width: 90}
        ],
        height: 700,
        rowNum: 15,
        rowList: [15, 20, 50],
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

    /**
     * 加载博客分类下拉框
     */
    function initCategory(){
        $.ajax({
            url: '/api/admin/category/list',
            //type: "POST",
            success: function (result) {
                result=eval("("+result+")");
                if(result ==null){
                    $("#categoryId").append("<option value='0' selected='selected'></option>");
                }
                if (result.code === 0) {
                    //console.log(result.data.list)
                    var list = result.data.list;
                    for(let i=0;i<list.length;i++){
                        $("#categoryId").append("<option value='"+list[i].categoryId+"'>"+list[i].categoryName+"</option>");
                    }
                }
            }
        });
    }
    $(window).resize(function () {
        $("#jqGrid").setGridWidth($(".card-body").width());
    });
    //博客封面加载
    function coverImageFormatter(cellvalue) {
        return "<img src='" + cellvalue + "' height=\"120\" width=\"160\" alt='coverImage'/>";
    }
    //博客状态格式化
    function statusFormatter(cellvalue) {
        if (cellvalue == '2') {
            return "<button type=\"button\" class=\"btn btn-block btn-warning btn-sm\" style=\"width: 50%;\">草稿箱</button>";
        }else if (cellvalue == '1') {
            return "<button type=\"button\" class=\"btn btn-block btn-success btn-sm\" style=\"width: 50%;\">已发布</button>";
        }else if (cellvalue == '3') {
            return "<button type=\"button\" class=\"btn btn-block btn-danger btn-sm\" style=\"width: 50%;\">垃圾箱</button>";
        }
    }

});
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
 * 搜索功能
 */
function searchBlog() {
    //标题关键字
    let title = $('#keyword').val();
    if (!validLength(title, 20)) {
        swal("搜索字段长度过大!", {
            icon: "error",
        });
        return false;
    }
    //数据封装
    let startDate = $("#beginDate").val();
    let endDate = $("#endDate").val();
    let categoryId = $("#categoryId").val();
    let status = $("#status").val();
    $("#jqGrid").jqGrid('setGridParam',{
        datatype:'json',
        page:1,
        postData : {
            "title": title,
            "startDate" : startDate,
            "endDate":endDate,
            "categoryId": categoryId
        },
        page:1
    }).trigger("reloadGrid");
}

/**
 * 重置按钮
 */
function resetBlog() {
    $("#keyword").val('');
    $("#beginDate").val('');
    $("#endDate").val('');
    $("#categoryId").val('');
    $("#status").val('');
    $("#jqGrid").trigger("reloadGrid");
}
/**
 * jqGrid重新加载
 */
function reload() {
    let page = $("#jqGrid").jqGrid('getGridParam', 'page');
    $("#jqGrid").jqGrid('setGridParam', {
        page: page
    }).trigger("reloadGrid");
}

/**
 * 新增博客按钮
 */
function addBlog() {
    window.location.href = "/api/admin/blog/edit";
}

/**
 * 编辑博客
 */
function editBlog() {
    let id = $("#jqGrid").jqGrid("getGridParam","selrow");
    let rowData = $("#jqGrid").jqGrid("getRowData",id);
    if (id == null) {
        swal("请选择一条数据", {
            icon: "error",
        });
        return;
    }
    window.location.href = "/api/admin/blog/edit/" + id;
}

/**
 * 批量删除博客
 */
function deleteBlog() {
    let ids = getSelectedRows();
    if (ids == null) {
        return;
    }
    swal({
        title: "确认弹框",
        text: "确认要删除数据吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "POST",
                    url: "/api/admin/blog/delete",
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
                            swal(result.message, {
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
 * 博客列表数据导出，没选中则导出全部
 */
function exportBlog() {
    //获取选中数据
    let ids = getSelectedRows();
    swal({
        title: "确认弹框",
        text: "确认要导出数据吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "POST",
                    url: "/api/admin/blog/export",
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