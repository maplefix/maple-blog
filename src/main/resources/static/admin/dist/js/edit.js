/**
 * @author Maple created on 2019/7/25
 * @description 博客编辑模块
 * @version v1.0
 */
//声明editor.md编辑器
let blogEditor;
// Tags Input
$('#label').tagsInput({
    width: '100%',
    height: '38px',
    defaultText: '文章标签'
});

//Initialize Select2 Elements
$('.select2').select2();

$(function () {
    blogEditor = editormd({
        id:"blog-editorMd",//注意：这里是上面DIV的id
        width:"100%",
        height:720,
        syncScrolling: "single",
        path: "/admin/plugins/editormd/lib/",
        //theme : "dark", //工具栏暗黑主题
        //previewTheme : "dark",//预览区域黑色主题
        //editorTheme : "pastel-on-dark",//编辑区域黑色
        codeFold : true,
        saveHTMLToTextarea : true,    // 保存 HTML 到 Textarea
        searchReplace : true,
        //watch : false,                // 关闭实时预览
        htmlDecode : "style,script,iframe|on*",            // 开启 HTML 标签解析，为了安全性，默认不开启
        //toolbar  : false,             //关闭工具栏
        //previewCodeHighlight : false, // 关闭预览 HTML 的代码块高亮，默认开启
        emoji : true,
        taskList : true,
        tocm  : true,                   // Using [TOCM]
        tex : true,                   // 开启科学公式TeX语言支持，默认关闭
        flowChart : true,             // 开启流程图支持，默认关闭
        sequenceDiagram : true,       // 开启时序/序列图支持，默认关闭,
        dialogLockScreen : false,   // 设置弹出层对话框不锁屏，全局通用，默认为true
        dialogShowMask : false,     // 设置弹出层对话框显示透明遮罩层，全局通用，默认为true
        dialogDraggable : false,    // 设置弹出层对话框不可拖动，全局通用，默认为true
        dialogMaskOpacity : 0.4,    // 设置透明遮罩层的透明度，全局通用，默认值为0.1
        dialogMaskBgColor : "#000", // 设置透明遮罩层的背景颜色，全局通用，默认为#fff


        /**上传图片相关配置如下*/
        imageUpload : true,
        imageFormats : ["jpg", "jpeg", "gif", "png", "bmp", "webp"],//限制图片上传格式
        imageUploadURL : "/api/admin/blog/upload", //这个是上传图片时的访问地址
        /*
         上传的后台必须返回一个 JSON 数据，结构如下：
         {
            success : 0 | 1,           // 0 表示上传失败，1 表示上传成功
            message : "提示的信息，上传成功或上传失败及错误信息等。",
            url     : "图片地址"        // 上传成功时才返回
         }
         */
        onload : function() {
            //console.log('onload', this);
            //this.fullscreen();
            //this.unwatch();
            //this.watch().fullscreen();

            //this.setMarkdown("#PHP");
            //this.width("100%");
            //this.height(480);
            //this.resize("100%", 640);
        }
    });


    //初始化加载时给隐藏的input添加文件属性
   /* $('#upload').on('change',$('#upload'),function(){
        fileUpload();
    });*/
});
$('#uploadCoverImage').click(function () {
    //文件标签点击事件
    $("#upload").click();
});

/**
 * 文件上传
 */
function fileUpload(){
    $.ajaxFileUpload({
        url: '/api/admin/file/upload', //用于文件上传的服务器端请求地址
        secureuri: false, //是否需要安全协议，一般设置为false
        fileElementId: 'upload', //文件上传域的ID
        dataType: 'json', //返回值类型 一般设置为json
        data:{
            "serverType":"1"
        },
        success: function (data, status){  //服务器成功响应处理函数
            /*var description = "";
            for(var i in data){
                var property=data[i];
                description+=i+" = "+property+"\n";
            }*/
            //console(data.code);
            if (data.code === "0") {
                $("#coverImg").attr("src", data.url);
                $("#coverImg").attr("style", "width: 128px;height: 128px;display:block;");
            } else {
                swal(data.message, {
                    icon: "error",
                });
            }
        },
        error: function (data, status, e){//服务器响应失败处理函数
            swal(data.message, {
                icon: "error",
            });
        }
    })
}
//使用ajaxFile方式上传封面按钮点击事件
$('#uploadCoverImage1').click(function () {
    /*//
    let btn = $("#uploadCoverImage");
    let options = {
        action: '/file/upload',
        name: 'file',
        autoSubmit: true,
        dataType: 'json',//'json'
        onSubmit: function (file, extension) {
            if (!(extension && /^(jpg|jpeg|png|gif|svg|ico)$/.test(extension.toLowerCase()))) {
                alert('只支持jpg、jpeg、png、gif、svg、ico格式的文件！');
                return false;
            }
            //btn.text("上传中...")
        },
        onComplete: function (file, response) {
            //response = eval("("+response+")");
            alert(response);

            if (response.code === "0") {
                $("#coverImg").attr("src", response.url);
                $("#coverImg").attr("style", "width: 128px;height: 128px;display:block;");
            } else {
                swal(response.message, {
                    icon: "error",
                });
            }
        }
    }
    new AjaxUpload(btn,options);*/

});
/**
 * 确认按钮
 */
$('#confirmButton').click(function () {
    let title = $('#title').val();
    let categoryId = $('#categoryId').val()==0?"":$('#categoryId').val();
    let label = $('#label').val();
    let content = blogEditor.getMarkdown();
    if (isNull(title)) {
        swal("请输入文章标题", {
            icon: "error",
        });
        return;
    }
    if (!validLength(title, 150)) {
        swal("标题过长", {
            icon: "error",
        });
        return;
    }
    if (isNull(categoryId)) {
        swal("请选择文章分类", {
            icon: "error",
        });
        return;
    }
    if (isNull(label)) {
        swal("请输入文章标签", {
            icon: "error",
        });
        return;
    }
    if (!validLength(label, 150)) {
        swal("标签过长", {
            icon: "error",
        });
        return;
    }
    if (isNull(content)) {
        swal("请输入文章内容", {
            icon: "error",
        });
        return;
    }
    if (!validLength(content, 100000)) {
        swal("文章内容过长", {
            icon: "error",
        });
        return;
    }
    $('#articleModal').modal('show');
});

/**
 * 保存文章按钮
 */
$('#saveButton').click(function () {
    var blogId = $('#blogId').val();
    var title = $('#title').val();
    var label = $('#label').val();
    var summary = $('#summary').val();
    var categoryId = $('#categoryId').val()==0?"":$('#categoryId').val();
    var content = blogEditor.getMarkdown();
    if(summary == "" || summary == null){
        summary = content.substring(1,100);
    }
    var coverImg = $('#coverImg')[0].src;
    var status = $("input[name='status']:checked").val();
    var support = $("input[name='support']:checked").val();
    var originalFlag = $("input[name='originalFlag']:checked").val();
    if (isNull(coverImg) || coverImg.indexOf('img-upload') != -1) {
        swal("封面图片不能为空", {
            icon: "error",
        });
        return;
    }
    var url = '/api/admin/blog/save';
    var swlMessage = '保存成功';
    var data = {
        "title": title,
        "label": label,
        "summary": summary,
        "categoryId": categoryId,
        "content": content,
        "coverImg": coverImg,
        "status": status,
        "support": support,
        "originalFlag": originalFlag
    };
    if (blogId != null && blogId != "" && blogId != undefined && blogId != 0) {
        url = '/api/admin/blog/update';
        swlMessage = '修改成功';
        data = {
            "blogId": blogId,
            "title": title,
            "label": label,
            "summary": summary,
            "categoryId": categoryId,
            "content": content,
            "coverImg": coverImg,
            "status": status,
            "support": support,
            "originalFlag": originalFlag
        };
    }
    //console.log(data);
    $.ajax({
        type: 'POST',//方法类型
        url: url,
        data: data,
        success: function (result) {
            result=eval("("+result+")");
            if (result.code === 0) {
                $('#articleModal').modal('hide');
                swal({
                    title: swlMessage,
                    type: 'success',
                    icon: "success",
                    showCancelButton: false,
                    confirmButtonColor: '#3085d6',
                    confirmButtonText: '返回博客列表',
                    confirmButtonClass: 'btn btn-success',
                    buttonsStyling: false
                }).then(function () {
                    window.location.href = "/api/admin/blog";
                })
            }
            else {
                $('#articleModal').modal('hide');
                swal(result.message, {
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
 * 取消按钮，回到博客列表
 */
$('#cancelButton').click(function () {
    window.location.href = "/api/admin/blog";
});

/**
 * 随机封面功能
 */
$('#randomCoverImage').click(function () {
    var rand = parseInt(Math.random() * 40 + 1);
    $("#coverImg").attr("src", '/admin/dist/img/rand/' + rand + ".jpg");
    $("#coverImg").attr("style", "width:160px ;height: 120px;display:block;");
});
