var Login = function () {
    return {
        //main function to initiate the module
        init: function () {
        	//在键盘按下并释放及提交后验证提交表单
           $('.login-form').validate({
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
	                remember: {
	                    required: false
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
	                }
	            },

	            invalidHandler: function (event, validator) { //display error alert on form submit   
	                $('.alert-error', $('.login-form')).show();
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
               $.ajax({
				   type: "POST",
				   url: "/loginResult",
				   data: {
                       username: $('#username').val(),
                       password: $('#password').val(),
                       rememberMe: $('#rememberMe').val()
				   },
				   success: function (data) {
					   if (data.code != 0) {
						   $("#show-error").removeClass("hide");
						   $("#show-error>span").html(data.message);

						   return false;
					   }
					   window.location.href = "/admin"
                   },
				   error: function (e) {
					   console.log(e);
                   }
			   });
           }

	        $('.login-form input').keypress(function (e) {
	            if (e.which == 13) {
	                if ($('.login-form').validate().form()) {
	                    //window.location.href = "index.html";
						//alert("aaaaaa");
	                }
	                return false;
	            }
	        });

	        $('.forget-form').validate({
	            errorElement: 'label', //default input error message container
	            errorClass: 'help-inline', // default input error message class
	            focusInvalid: false, // do not focus the last invalid input
	            ignore: "",
	            rules: {
	            	username: {
	            		required: true,
                        minlength: 6,
                        maxlength: 20
					},
	                email: {
	                    required: true,
	                    email: true
	                }
	            },
	            messages: {
	            	username: {
	            		required: "请输入用户名."	,
                        minlength: "用户名必需由两个字母组成!"
					},
	                email: {
	                    required: "请输入邮箱地址."
	                }
	            },

	            invalidHandler: function (event, validator) { //display error alert on form submit   

	            },

	            highlight: function (element) { // hightlight error inputs
	                $(element)
	                    .closest('.control-group').addClass('error'); // set error class to the control group
	            },

	            success: function (label) {
	                label.closest('.control-group').removeClass('error');
	                label.remove();
	            },

	            errorPlacement: function (error, element) {
	                error.addClass('help-small no-left-padding').insertAfter(element.closest('.input-icon'));
	            },

	            submitHandler: function (form) {
	                //window.location.href = "index.html";
                    ajaxSendResetPasswordEmail();
	            }
	        });

	        function ajaxSendResetPasswordEmail() {
				$.ajax({
					type: "POST",
					url: "/sendResetPasswordEmail",
					data: {
						username: $("#forget_username").val(),
						email: $("#forget_email").val()
					},
					success: function (data) {
						if (data.code != 0) {
                            $("#show-forget-error").removeClass("hide");
                            $("#show-forget-error>span").html(data.message);

							return false;
						}
						//跳转至登录页
                        window.location.href = "/login";
                    }
				});
            }

	        $('.forget-form input').keypress(function (e) {
	            if (e.which == 13) {
	                if ($('.forget-form').validate().form()) {
	                    window.location.href = "/admin";
	                }
	                return false;
	            }
	        });

	        jQuery('#forget-password').click(function () {
	            jQuery('.login-form').hide();
	            jQuery('.forget-form').show();
	        });

	        jQuery('#back-btn').click(function () {
	            jQuery('.login-form').show();
	            jQuery('.forget-form').hide();
	        });

	        $('.register-form').validate({
	            errorElement: 'label', //default input error message container
	            errorClass: 'help-inline', // default input error message class
	            focusInvalid: false, // do not focus the last invalid input
	            ignore: "",
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
	                    equalTo: "#reg-password" //输入值必须和#register_password相同
	                },
	                email: {
	                    required: true,
	                    email: true
	                },
	                tnc: {
	                    required: true
	                }
	            },
	            messages: { // custom messages for radio buttons and checkboxes
                    username: {
                        required: "请求输入用户名.",
                        minlength: "用户名必需由两个字母组成!"
                    },
                    password: {
                        required: "请求输入密码.",
                        minlength: "用户名必需由两个字母组成!"
                    },
					rePassword: {
                    	required: "请求输入重复密码!",
					},
	                tnc: {
	                    required: "请先接受注册协议."
	                }
	            },

	            invalidHandler: function (event, validator) { //display error alert on form submit   
                    $('.alert-error', $('.register-form')).show();
                    $('#show-reg-error').hide();
	            },

	            highlight: function (element) { // hightlight error inputs
	                $(element)
	                    .closest('.control-group').addClass('error'); // set error class to the control group
	            },

	            success: function (label) {
	                label.closest('.control-group').removeClass('error');
	                label.remove();
	            },

	            errorPlacement: function (error, element) {
	                if (element.attr("name") == "tnc") { // insert checkbox errors after the container                  
	                    error.addClass('help-small no-left-padding').insertAfter($('#register_tnc_error'));
	                } else {
	                    error.addClass('help-small no-left-padding').insertAfter(element.closest('.input-icon'));
	                }
	            },
				//验证成功提交事件
	            submitHandler: function (form) {
					ajaxRegisterSubmit();
	            }
	        });

            function ajaxRegisterSubmit() {
            	var username = $("#reg-username").val();
            	var password = $("#reg-password").val();
            	if (username == password) {
                    $("#show-reg-error").removeClass("hide");
                    $("#show-reg-error>span").html("用户名和密码不能相同!");
                    return false;
				}
				$.ajax({
					type: "POST",
					url: "/registerResult",
					data: {
						username: $("#reg-username").val(),
						password: $("#reg-password").val(),
						rePassword: $("#reg-rePassword").val(),
						email: $("#reg-email").val()
					},
					success: function (data) {
                        if (data.code != 0) {
                            $("#show-reg-error").removeClass("hide");
                            $("#show-reg-error>span").html(data.message);

                            return false;
                        }

                        window.location.href = "/admin";
                    }
				});
			}

            $('.reset-forget-form').validate({
                errorElement: 'label', //default input error message container
                errorClass: 'help-inline', // default input error message class
                focusInvalid: false, // do not focus the last invalid input
                ignore: "",
                rules: {
                    password: {
                        required: true,
                        minlength: 6,
                        maxlength: 20
                    },
                    rePassword: {
                        equalTo: "#reset-password" //输入值必须和#register_password相同
                    },
                },
                messages: { // custom messages for radio buttons and checkboxes
                    password: {
                        required: "请求输入密码.",
                        minlength: "密码长度为6-20位!"
                    },
                    rePassword: {
                        required: "请求输入密码.",
                        minlength: "密码长度为6-20位!"
                    }
                },

                invalidHandler: function (event, validator) { //display error alert on form submit
                    $('.alert-error', $('.reset-forget-form')).show();
                    $('#show-reset-forget-error').hide();
                },

                highlight: function (element) { // hightlight error inputs
                    $(element)
                        .closest('.control-group').addClass('error'); // set error class to the control group
                },

                success: function (label) {
                    label.closest('.control-group').removeClass('error');
                    label.remove();
                },

                errorPlacement: function (error, element) {
                    error.addClass('help-small no-left-padding').insertAfter(element.closest('.input-icon'));
                },
                //验证成功提交事件
                submitHandler: function (form) {
                    ajaxResetPasswordSubmit();
                },

            });

            function ajaxResetPasswordSubmit() {
                var password = $("#reset-password").val();
                var rePassword = $("#reset-rePassword").val();
                var username = $("#reset-username").val();
                var email = $("#reset-email").val();
                if (username == password) {
                    $("#show-reset-forget-error").removeClass("hide");
                    $("#show-reset-forget-error>span").html("用户名和密码不能相同!");
                    return false;
                }
                $.ajax({
                    type: "POST",
                    url: "/updateForgotPassword",
                    data: {
                        username: username,
                        password: password,
                        rePassword: rePassword,
                        email: email
                    },
                    success: function (data) {
                        if (data.code != 0) {
                            $("#show-reset-forget-error").removeClass("hide");
                            $("#show-reset-forget-error>span").html(data.message);

                            return false;
                        }

                        window.location.href = "/resetPasswordResult";
                    }
                });
            }

	        jQuery('#register-btn').click(function () {
	            jQuery('.login-form').hide();
	            jQuery('.register-form').show();
	        });

	        jQuery('#register-back-btn').click(function () {
	            jQuery('.login-form').show();
	            jQuery('.register-form').hide();
	        });
        }

    };

}();