var Department = function () {
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
                window.location.href = "/admin/add-department";
            });

            //点击删除按键操作
            $(".portlet-body table td a").live("click", function(e){
                if (confirm("确定要删除吗？")) {
                    var departmentId = $(this).attr("data-did");
                    $.ajax({
                        type: "post",
                        url: "/admin/delete-department",
                        data: {
                            departmentId: departmentId
                        },
                        success: function (data) {
                            if(data.code ==0) {
                                window.location.href = "/admin/department-manager";
                            } else {
                                alert(data.message);
                            }
                        },
                        error: function() {
                            console.log('删除部门失败!');
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
