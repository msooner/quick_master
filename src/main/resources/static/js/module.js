var Module = function () {
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
                window.location.href = "/admin/add-module";
            });

            //点击删除按键操作
            $(".portlet-body table td a").live("click", function(e){
                if (confirm("确定要删除吗？")) {
                    var roleId = $(this).attr("data-did");
                    $.ajax({
                        type: "post",
                        url: "/admin/delete-module",
                        data: {
                            id: roleId
                        },
                        success: function (data) {
                            alert(data.message);
                            window.location.href = "/admin/module-manager";
                        },
                        error: function() {
                            console.log('删除模块失败!');
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

//添加保存模块信息
$("#addModuleForm").click(function(){
    var parentName = $("#parentId>option").html();
    var moduleName = $("#moduleName").val();
    var moduleUrl = $("#moduleUrl").val();
    //检验部门名称是否为空
    if (moduleName.length == 0 || moduleName == undefined) {
        showError('模块名称不能为空!');
        return false;
    }
    //检测moduleUrl是否合法
    if (moduleUrl.length == 0 || moduleUrl == undefined) {
        showError('模块URL不能为空!');
        return false;
    }
    if (! /^\/\w+(-\w+)*/g.test(moduleUrl)) {
        showError('模块URL格式有误!');
        return false;
    }
    if (parentName != "无上级模块" && moduleName == parentName) {
        showError('模块名称不能和上级模块相同!');
        return false;
    }
    $.ajax({
        type: "post",
        url: "/admin/add-module-result",
        data: {
            moduleName: moduleName,
            moduleUrl: moduleUrl,
            parentId: $("#parentId").val(),
            createBy: $("#createBy").val()
        },
        success: function(data) {
            if (data.code != 0) {
                showError(data.message);
                return false;
            }
            alert("模块信息添加成功!");
            window.location.href = "/admin/module-manager";
        },
        error: function(data) {
            console.log(data);
        }
    });

    return false;
});

//添加保存模块信息
$("#editModuleForm").click(function(){
    var parentName = $("#parentId").find("option:selected").text();
    var moduleName = $("#moduleName").val();
    var moduleUrl = $("#moduleUrl").val();
    //检验部门名称是否为空
    if (moduleName.length == 0 || moduleName == undefined) {
        showError('模块名称不能为空!');
        return false;
    }
    //检测moduleUrl是否合法
    if (moduleUrl.length == 0 || moduleUrl == undefined) {
        showError('模块URL不能为空!');
        return false;
    }
    if (! /^\/\w+(-\w+)*/g.test(moduleUrl)) {
        showError('模块URL格式有误!');
        return false;
    }
    if (parentName != "无上级模块" && moduleName == parentName) {
        showError('模块名称不能和上级模块相同!');
        return false;
    }
    $.ajax({
        type: "post",
        url: "/admin/edit-module-result",
        data: {
            id: $("#id").val(),
            moduleName: moduleName,
            moduleUrl: moduleUrl,
            parentId: $("#parentId").val(),
            createBy: $("#createBy").val()
        },
        success: function(data) {
            if (data.code != 0) {
                showError(data.message);
                return false;
            }
            alert("模块信息添加成功!");
            window.location.href = "/admin/module-manager";
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
