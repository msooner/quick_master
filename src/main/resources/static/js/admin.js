var Admin = function () {
    return {
        //main function to initiate the module
        init: function () {
            //在键盘按下并释放及提交后验证提交表单
            $('.admin-form').validate({
                //默认值为label，指定使用什么标签标记错误
                errorElement: 'label',
                //默认error，指定错误提示的css类名
                errorClass: 'help-inline',
                //默认true，提交表单后，未通过验证的表单会获取焦点
                focusInvalid: false,
                //自定义规则
                rules: {
                    username: {
                        required: true,
                        minlength: 6,
                        maxlength: 20
                    },
                    password: {
                        required: true,
                        minlength: 6,
                        maxlength: 20

                    },
                    rePassword: {
                        equalTo: "#password" //输入值必须和#register_password相同
                    },
                    email: {
                        required: true,
                        minlength: 10,
                        email: true

                    }
                },
                //自定义提示信息
                messages: {
                    username: {
                        //required: i18n('login.loginRequired'),
                        required: "请输入用户名.",
                        minlength: "用户名必需由两个字母组成!"
                    },
                    password: {
                        required: "请输入密码.",
                        minlength: "用户名必需由两个字母组成!"
                    },
                    email: {
                        required: "请输入email.",
                        minlength: "请输入正确的邮箱格式!"
                    }
                },

                invalidHandler: function (event, validator) { //display error alert on form submit
                    $('.alert-error', $('.admin-form')).hide();
                    $('#show-error').hide();
                },
                //可以给未通过验证的元素加效果
                highlight: function (element) { // hightlight error inputs
                    $(element)
                        .closest('.control-group').addClass('error'); // set error class to the control group
                },

                //要验证的元素通过验证后的动作
                success: function (label) {
                    label.closest('.control-group').removeClass('error');
                    label.remove();
                },
                //定义错误放在哪里
                errorPlacement: function (error, element) {
                    error.addClass('help-small no-left-padding').insertAfter(element.closest('.input-icon'));
                },

                //提交事件
                submitHandler: function (form) {
                    ajaxSubmit();
                }
            });

            function ajaxSubmit() {
                var isLocked = $('input[name="isLocked"]:checked').val();
                $.ajax({
                    type: "POST",
                    url: "/admin/add-manager-result",
                    data: {
                        username: $('#username').val(),
                        password: $('#password').val(),
                        rePassword: $('#rePassword').val(),
                        email: $('#email').val(),
                        deptId: $('#deptId').val(),
                        roleId: $('#roleId').val(),
                        isLocked: isLocked,
                        createdBy: $('#createBy').val()
                    },
                    success: function (data) {
                        if (data.code != 0) {
                            $("#show-error").removeClass("hide");
                            $("#show-error>span").html(data.message);

                            return false;
                        }
                        window.location.href = "/admin/admin-manager";
                    },
                    error: function (e) {
                        console.log(e);
                    }
                });
            }

            $('.admin-form input').keypress(function (e) {
                if (e.which == 13) {
                    if ($('.admin-form').validate().form()) {
                        //window.location.href = "index.html";
                        //alert("aaaaaa");
                    }
                    return false;
                }
            });

            //在键盘按下并释放及提交后验证提交表单
            $('.edit-admin-form').validate({
                //默认值为label，指定使用什么标签标记错误
                errorElement: 'label',
                //默认error，指定错误提示的css类名
                errorClass: 'help-inline',
                //默认true，提交表单后，未通过验证的表单会获取焦点
                focusInvalid: false,
                //自定义规则
                rules: {
                    username: {
                        required: true,
                        minlength: 6,
                        maxlength: 20
                    },
                    password: {
                        required: true,
                        minlength: 6,
                        maxlength: 50

                    },
                    email: {
                        required: true,
                        minlength: 10,
                        email: true

                    }
                },
                //自定义提示信息
                messages: {
                    username: {
                        //required: i18n('login.loginRequired'),
                        required: "请输入用户名.",
                        minlength: "用户名必需由字母和数字组成,长度大于6,小于20!"
                    },
                    password: {
                        required: "请输入密码.",
                        minlength: "密码必须大于6，小于20!"
                    },
                    email: {
                        required: "请输入邮箱.",
                        minlength: "邮箱格式必须为xx@xx.xxx!"
                    }
                },

                invalidHandler: function (event, validator) { //display error alert on form submit
                    $('.alert-error', $('.admin-form')).hide();
                    $('#show-error').hide();
                },
                //可以给未通过验证的元素加效果
                highlight: function (element) { // hightlight error inputs
                    $(element)
                        .closest('.control-group').addClass('error'); // set error class to the control group
                },

                //要验证的元素通过验证后的动作
                success: function (label) {
                    label.closest('.control-group').removeClass('error');
                    label.remove();
                },
                //定义错误放在哪里
                errorPlacement: function (error, element) {
                    error.addClass('help-small no-left-padding').insertAfter(element.closest('.input-icon'));
                },

                //提交事件
                submitHandler: function (form) {
                    ajaxEditSubmit();
                }
            });

            function ajaxEditSubmit() {
                var isLocked = $('input[name="isLocked"]:checked').val();
                $.ajax({
                    type: "POST",
                    url: "/admin/edit-manager-result",
                    data: {
                        id: $("#id").val(),
                        username: $('#username').val(),
                        password: $('#password').val(),
                        email: $('#email').val(),
                        deptId: $('#deptId').val(),
                        roleId: $('#roleId').val(),
                        isLocked: isLocked,
                        createdBy: $('#createBy').val()
                    },
                    success: function (data) {
                        if (data.code != 0) {
                            $("#show-error").removeClass("hide");
                            $("#show-error>span").html(data.message);

                            return false;
                        }
                        window.location.href = "/admin/admin-manager";
                    },
                    error: function (e) {
                        console.log(e);
                    }
                });
            }

            $('.edit-admin-form input').keypress(function (e) {
                if (e.which == 13) {
                    if ($('.edit-admin-form').validate().form()) {
                        //window.location.href = "index.html";
                        //alert("aaaaaa");
                    }
                    return false;
                }
            });
        }
    };

}();
