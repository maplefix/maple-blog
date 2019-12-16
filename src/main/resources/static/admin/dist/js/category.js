/**
 * @author Maple created on 2019/7/25
 * @description 博客分类模块
 * @version v2.1
 */
$(function () {
    $("#jqGrid").jqGrid({
        url: '/api/admin/category/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'categoryId', index: 'categoryId', width: 50, key: true, hidden: true},
            {label: '分类名称', name: 'categoryName', index: 'categoryName', width: 80},
            {label: '关联博客', name: 'count', index: 'count', width: 50,formatter: blogCountFormatter},
            {label: '分类图标', name: 'categoryIcon', index: 'categoryIcon', width: 60, formatter: imgFormatter},
            {label: '创建时间', name: 'createDate', index: 'createDate', width: 70},
            {label: '修改时间', name: 'updateDate', index: 'updateDate', width: 70},
            {label: '分类权重', name: 'height', index: 'height', width: 50},
            {label: '描述信息', name: 'description', index: 'description', width: 100}
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

    jQuery("select.image-picker").imagepicker({
        hide_select: false,
    });

    jQuery("select.image-picker.show-labels").imagepicker({
        hide_select: false,
        show_label: true,
    });
    $(window).resize(function () {
        $("#jqGrid").setGridWidth($(".card-body").width());
    });
    let container = jQuery("select.image-picker.masonry").next("ul.thumbnails");
    container.imagesLoaded(function () {
        container.masonry({
            itemSelector: "li",
        });
    });
    //关联博客数量格式
    function blogCountFormatter(cellvalue) {
        return "<button type='button' class='btn btn-block btn-sm' style='width: 50%;'>"+cellvalue+"篇</button>";
    }
});
//分类图片格式化
function imgFormatter(cellvalue) {
    return "<a href='" + cellvalue + "'> <img src='" + cellvalue + "' height=\"50\" width=\"50\" alt='icon'/></a>";
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
 * jqGrid重新加载
 */
function reload() {
    jQuery("#jqGrid").trigger("reloadGrid");
}
/**
 * 查询按钮
 */
function searchCategory(){
    let startDate = $("#beginDate").val();
    let endDate = $("#endDate").val();
    let categoryName = $("#keyword").val();
    $("#jqGrid").jqGrid('setGridParam',{
        datatype:'json',
        page:1,
        postData : {
            "startDate" : startDate,
            "endDate":endDate,
            "categoryName": categoryName
        },
        page:1
    }).trigger("reloadGrid");
}

/**
 * 重置按钮
 */
function resetCategory() {
    $("#keyword").val('');
    $("#beginDate").val('');
    $("#endDate").val('');
    $("#jqGrid").trigger("reloadGrid");
}

/**
 * 新增按钮
 */
function categoryAdd() {
    reset();
    $('.modal-title').html('');
    $('#categoryModal').modal('show');
}

/**
 * 绑定modal上的保存按钮
 */
$('#saveButton').click(function () {
    let categoryName = $("#categoryName").val();
    if (!validCN_ENString2_18(categoryName)) {
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("请输入符合规范的分类名称！");
    } else {
        let params = $("#categoryForm").serialize();
        let url = '/api/admin/category/save';
        let id = $("#categoryId").val();
        if (id != null && id != undefined && id != "") {
            url = '/api/admin/category/update';
        }
        $.ajax({
            type: 'POST',//方法类型
            url: url,
            data: params,
            success: function (result) {
                result=eval("("+result+")");
                if (result.code === 0) {
                    $('#categoryModal').modal('hide');
                    swal("保存成功", {
                        icon: "success",
                    });
                    reload();
                }
                else {
                    $('#categoryModal').modal('hide');
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
 * 分类编辑
 */
function categoryEdit() {
    reset();
    let id = $("#jqGrid").jqGrid("getGridParam","selrow");
    let rowData = $("#jqGrid").jqGrid("getRowData",id);
    if (id == null) {
        swal("请选择一条数据", {
            icon: "error",
        });
        return;
    }
    $('.modal-title').html('');
    $('#categoryModal').modal('show');
    $("#categoryId").val(rowData.categoryId);
    $("#categoryName").val(rowData.categoryName);
    $("#description").val(rowData.description);
    $("#height").val(rowData.height);
    //设置图片回显选中,这里将categoryIcon格式成html标签了，下面的操作原理是：
    //先获取到格式后的字符串，用正则从字符串中获取到img标签，然后取到img标签的src属性，然后根据src值和option标签值做对比回显。
    const all_select=$("#categoryIcon > option");
    for(const i=0;i<all_select.length;i++){
        const value=all_select[i].value;
        const imgReg = /<img.*?(?:>|\/>)/gi;
        const srcReg = /src=[\'\"]?([^\'\"]*)[\'\"]?/i;
        const arr = rowData.categoryIcon.match(imgReg);  // arr 为包含所有img标签的数组
        const src = arr[0].match(srcReg)[1];
        console.log(src+":"+value)
        if(src == value){  //取select中所有的option的值与其进行对比，相等则令这个option添加上selected属性
            //alert();
            $("#categoryIcon option[value='"+value+"']").attr("selected","selected");
        }
    }
}

/**
 * 删除分类
 */
function deleteCategory() {
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
                    url: "/api/admin/category/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (result) {
                        result=eval("("+result+")");
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
 * 重置分类名称和选中的图片
 */
function reset() {
    $("#categoryName").val('');
    $("#description").val('');
    $("#height").val('');
    $("#categoryIcon option:first").prop("selected", 'selected');
    $('#edit-error-msg').css("display", "none");
}

/**
 * 导出分类数据
 */
function exportCategory() {
    let ids = getSelectedRows();
    swal({
        title: "确认弹框",
        text: "确认要导出所选数据吗?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "POST",
                    url: "/api/admin/category/export",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (result) {
                        result=eval("("+result+")");
                        if (result.code === 0) {
                            swal("导出成功", {
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