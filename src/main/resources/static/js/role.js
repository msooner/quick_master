var Role = function () {
    return {
        //main function to initiate the module
        init: function () {
            var oTable = $('#sample_editable_1').dataTable({
                "aLengthMenu": [
                    [5, 15, 20, -1],
                    [5, 15, 20, "All"] // change per page values here
                ],
                // set the initial value
                "iDisplayLength": 5,
                "sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span6'i><'span6'p>>",
                "sPaginationType": "bootstrap",
                "oLanguage": {
                    "sLengthMenu": "_MENU_ 条记录每页",
                    "oPaginate": {
                        "sPrevious": "Prev",
                        "sNext": "Next"
                    }
                },
                "aoColumnDefs": [{
                        'bSortable': false,
                        'aTargets': [0]
                    }
                ]
            });

            jQuery('#sample_editable_1_wrapper .dataTables_filter input').addClass("m-wrap medium"); // modify table search input
            jQuery('#sample_editable_1_wrapper .dataTables_length select').addClass("m-wrap small"); // modify table per page dropdown
            jQuery('#sample_editable_1_wrapper .dataTables_length select').select2({
                showSearchInput : false //hide search box with special css class
            }); // initialzie select2 dropdown

            var nEditing = null;

            //添加部门跳转部门添加页面
            $("#sample_editable_1_new").live("click", function(e) {
                window.location.href = "/admin/add-role";
            });
            //角色授权
            $("#grant_authorization").live("click", function(e) {
                window.location.href = "/admin/authorization-role";
            });

            //点击删除按键操作
            $(".portlet-body table td a").live("click", function(e){
                if (confirm("确定要删除吗？")) {
                    var roleId = $(this).attr("data-did");
                    $.ajax({
                        type: "post",
                        url: "/admin/delete-role",
                        data: {
                            id: roleId
                        },
                        success: function (data) {
                            alert(data.message);
                            window.location.href = "/admin/role-manager";
                        },
                        error: function() {
                            console.log('删除角色失败!');
                        }
                    });
                } else {
                    return false;
                }
                return false;
            });
        }
    };
}();

//添加保存角色信息
$("#addRoleForm").click(function(){
    var parentName = $("#parentId>option").html();
    var roleName = $("#roleName").val();
    //检验部门名称是否为空
    if (roleName.length == 0 || roleName == undefined) {
        showError('角色名称不能为空!');
        return false;
    }
    if (parentName != "无上级角色" && roleName == parentName) {
        showError('角色名称不能和上级角色相同!');
        return false;
    }
    $.ajax({
        type: "post",
        url: "/admin/add-role-result",
        data: {
            roleName: roleName,
            parentId: $("#parentId").val(),
            createBy: $("#createBy").val()
        },
        success: function(data) {
            if (data.code != 0) {
                showError(data.message);
                return false;
            }
            alert("角色信息添加成功!");
            window.location.href = "/admin/role-manager";
        },
        error: function(data) {
            console.log(data);
        }
    });

    return false;
});

//添加保存角色信息
$("#editRoleForm").click(function(){
    var parentName = $("#parentId>option").html();
    var roleName = $("#roleName").val();
    //检验部门名称是否为空
    if (roleName.length == 0 || roleName == undefined) {
        showError('角色名称不能为空!');
        return false;
    }
    if (parentName != "无上级角色" && roleName == parentName) {
        showError('角色名称不能和上级角色相同!');
        return false;
    }
    $.ajax({
        type: "post",
        url: "/admin/edit-role-result",
        data: {
            id: $("#roleId").val(),
            roleName: roleName,
            parentId: $("#parentId").val(),
            createBy: $("#createBy").val()
        },
        success: function(data) {
            if (data.code != 0) {
                showError(data.message);
                return false;
            }
            alert("角色信息编辑成功!");
            window.location.href = "/admin/role-manager";
        },
        error: function(data) {
            console.log(data);
        }
    });

    return false;
});

//添加保存角色权限信息
$("#authorizationRoleForm").click(function(){
    var moduleIdArray = new Array();
    $("input[name='moduleIds']:checked").each(function () {
        moduleIdArray.push($(this).val());//向数组中添加元素
    });
    var moduleIds = moduleIdArray.join(",");
    //检验部门名称是否为空
    $.ajax({
        type: "post",
        url: "/admin/authorization-role-result",
        data: {
            id: $("#roleId").val(),
            moduleIds: moduleIds,
            parentId: $("#parentId").val(),
            createBy: $("#createBy").val()
        },
        success: function(data) {
            if (data.code != 0) {
                showError(data.message);
                return false;
            }
            alert("角色授权成功!");
            window.location.href = "/admin/role-manager";
        },
        error: function(data) {
            console.log(data);
        }
    });

    return false;
});

//展示错误信息
var showError = function(message) {
    $("#show-error").removeClass("hide");
    $("#show-error>span").html(message);
};

//级联获取二级角色
// $("#roleId").change(function() {
//     $("#parentId").empty();
//     var parentId = $("#roleId").val();
//     //获取下级子角色
//     $.ajax({
//         type: "post",
//         url: "/admin/get-child-role-list",
//         data: {
//             parentId: parentId
//         },
//         success: function (data) {
//             if (data.code != 0) {
//                 showError(data.message);
//             }
//             if (data.data.length > 0) {
//                 for(var i = 0; i < data.data.length; i++) {
//                     $("#parentId").append($("<option value=" + data.data[i].id + ">" + data.data[i].roleName +  "</option>"));
//                 }
//             } else {
//                 $("#parentId").append($("<option value="+ $('#roleId').val() +">无下级角色分类</option>"));
//             }
//         },
//         error: function (data) {
//             showError(data);
//         }
//     });
// });


