//验证用户名是否存在
function checkUser(obj){
    $.ajax({
        url : contextPath + "/user/checkUserName",
        data:{"username":obj.value},
        method:"post",
        success:function(data){
            $("#msg").css("display","block");
            if(data == 102){	//用户名已经存在
                $("#tip").html("用户名不合法");
                $("#tip").removeClass("alert-success");
                $("#tip").addClass("alert-danger");
            }else{
                $("#tip").html("用户名可以注册");
                $("#tip").removeClass("alert-danger");
                $("#tip").addClass("alert-success");
            }
        }
    })
}

//新用户注册
function register(){
    var datas = $("#regForm").serialize();
    $.ajax({
        url : contextPath + "/user/register",
        data:datas,
        method:"post",
        success:function(data){
            if(data == 'success'){
                alert("注册成功，请登录");
                $("#register").modal('hide');
            }
        }
    })
}

//用户登录
function login() {
    var datas = $("#loginForm").serialize();
    $.ajax({
        url : contextPath + "/user/login",
        data:datas,
        method:"post",
        success:function (data) {
            $("#userTip").css("display","none");
            $("#pwdTip").css("display","none");
            if(data == 100){
                $("#login").modal('hide');
                window.location.href = contextPath + "/home/index"
            }
            else if(data == 101){	//用户不存在
                $("#userTip").css("display","block");
            }
            else{	//密码不正确
                $("#pwdTip").css("display","block");
            }
        }
    })
}
