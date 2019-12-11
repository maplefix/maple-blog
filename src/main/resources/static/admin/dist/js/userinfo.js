
/**
 * @author Maple created on 2019/7/25
 * @description 个人信息管理模块
 * @version v2.1
 */
$(function () {
    //修改个人信息
    $('#updateUserButton').click(function () {
        $("#updateUserButton").attr("disabled",true);
        let avatar = $('#userAvatar').attr('src');
        let loginName = $('#loginName').val();
        let userName = $('#userName').val();
        let email = $('#email').val();
        let phone = $('#phone').val();
        if (validUserForUpdate(loginName, userName,email,phone)) {
            //ajax提交数据
            $.ajax({
                type: "POST",
                url: "/api/admin/userInfo/update",
                data: {
                    "avatar":avatar,
                    "loginName":loginName,
                    "userName":userName,
                    "email":email,
                    "phone":phone
                },
                success: function (r) {
                    console.log(r);
                    if (r == 'success') {
                        swal("修改成功", {
                            icon: "success",
                        });
                        //睡眠三秒再将按钮恢复
                        setTimeout(function (){
                            $("#updateUserButton").attr("disabled",false);
                        }, 3000);

                    } else {
                        swal("修改失败", {
                            icon: "error",
                        });
                    }
                }
            });
        }
    });

    //修改密码
    $('#updatePasswordButton').click(function () {
        $("#updatePasswordButton").attr("disabled",true);
        var originalPassword = $('#originalPassword').val();
        var newPassword = $('#newPassword').val();
        if (validPasswordForUpdate(originalPassword, newPassword)) {
            var params = $("#userPasswordForm").serialize();
            $.ajax({
                type: "POST",
                url: "/api/admin/userInfo/password",
                data: params,
                success: function (r) {
                    console.log(r);
                    if (r == 'success') {
                        swal("修改成功,即将重新登录...", {
                            icon: "success",
                        });

                        //睡眠三秒再将按钮恢复
                        setTimeout(function (){
                            window.location.href = '/api/admin/login';
                        }, 3000);
                    } else {
                        $('#updatePassword-info').css("display", "block");
                        $('#updatePassword-info').html("原密码错误,修改失败！");
                        /*swal("原密码错误,修改失败", {
                            icon: "error",
                        });*/
                    }
                }
            });
        }
        $("#updatePasswordButton").attr("disabled",false);
    });
})

/**
 * 名称验证
 */
function validUserForUpdate(loginName, userName,email,phone) {
    if (isNull(loginName) || loginName.trim().length < 1) {
        $('#updateUserName-info').css("display", "block");
        $('#updateUserName-info').html("请输入登陆名称！");
        return false;
    }
    if (isNull(userName) || userName.trim().length < 1) {
        $('#updateUserName-info').css("display", "block");
        $('#updateUserName-info').html("昵称不能为空！");
        return false;
    }
    if (!isVailEmail(email)) {
        $('#updateUserName-info').css("display", "block");
        $('#updateUserName-info').html("请输入符合规范的邮箱！");
        return false;
    }
    if (!PhoneNum(phone)) {
        $('#updateUserName-info').css("display", "block");
        $('#updateUserName-info').html("请输入符合规范的电话号码！");
        return false;
    }
    return true;
}

/**
 * 密码验证
 */
function validPasswordForUpdate(originalPassword, newPassword) {
    if (isNull(originalPassword) || originalPassword.trim().length < 1) {
        $('#updatePassword-info').css("display", "block");
        $('#updatePassword-info').html("请输入原密码！");
        return false;
    }
    if (isNull(newPassword) || newPassword.trim().length < 1) {
        $('#updatePassword-info').css("display", "block");
        $('#updatePassword-info').html("新密码不能为空！");
        return false;
    }
    if (!validPassword(newPassword)) {
        $('#updatePassword-info').css("display", "block");
        $('#updatePassword-info').html("请输入符合规范的密码！");
        return false;
    }
    return true;
}
var cropper;
//修改头像
function updateAvatar() {
    let imgSrc = $("#loginUserAvatar").val();
    $('#avatarModal').modal('show');
    var options =
        {
            checkCrossOrigin: true,
            thumbBox: '.thumbBox',
            spinner: '.spinner',
            imgSrc: imgSrc
        }
    cropper = $('.imageBox').cropbox(options);
    $('#upload-file').on('change', function(){
        var reader = new FileReader();
        reader.onload = function(e) {
            options.imgSrc = e.target.result;
            cropper = $('.imageBox').cropbox(options);
        }
        reader.readAsDataURL(this.files[0]);
        //this.files = [];
    })
    $('#btnCrop').on('click', function(){
        var img = cropper.getDataURL();
        $('.cropped').html('');
        $('.cropped').append('<img src="'+img+'" align="absmiddle" style="width:64px;margin-top:4px;border-radius:64px;box-shadow:0px 0px 12px #7E7E7E;" ><p>64px*64px</p>');
        $('.cropped').append('<img src="'+img+'" align="absmiddle" style="width:128px;margin-top:4px;border-radius:128px;box-shadow:0px 0px 12px #7E7E7E;"><p>128px*128px</p>');
        $('.cropped').append('<img src="'+img+'" align="absmiddle" style="width:180px;margin-top:4px;border-radius:180px;box-shadow:0px 0px 12px #7E7E7E;"><p>180px*180px</p>');
    })
    $('#btnZoomIn').on('click', function(){
        cropper.zoomIn();
    })
    $('#btnZoomOut').on('click', function(){
        cropper.zoomOut();
    })
}
//绑定modal上的保存按钮
$('#saveButton').click(function () {

    let img = cropper.getBlob();
    let file = blogToFile(img,"avatarFile.jpg");
    let url = '/api/admin/userInfo/updateAvatar';
    let formData = new FormData();
    formData.append("avatarFile", file, "avatarFile.jpg");
    $.ajax({
        type: 'POST',//方法类型
        url: url,
        data: formData,
        processData: false,
        contentType: false,
        mimeType: "multipart/form-data",
        async: false,
        success: function (data){  //服务器成功响应处理函数
            data = eval("("+data+")");
            console.log(data);
            if (data.code === "0") {
                $('#avatarModal').modal('hide');
                $("#userAvatar").attr('src',data.url);
                swal(data.message, {
                    icon: "success",
                });
            } else {
                swal(data.message, {
                    icon: "error",
                });
            }
        },
        error: function (data){//服务器响应失败处理函数
            swal(data.message, {
                icon: "error",
            });
        }
    });

});

/**
 * 为了解决Ajax传blog后台接收到的文件一直为blob，此处利用H5将blog转为File
 * @param dataurl
 * @param filename
 * @returns {*}
 */
function blogToFile(blob, fileName){
    blob.lastModifiedDate =new Date();
    blob.name = fileName;
    return blob;
}
