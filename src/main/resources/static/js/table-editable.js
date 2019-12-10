var TableEditable = function () {
    return {
        //main function to initiate the module
        init: function () {
            function restoreRow(oTable, nRow) {
                var aData = oTable.fnGetData(nRow);
                var jqTds = $('>td', nRow);

                for (var i = 0, iLen = jqTds.length; i < iLen; i++) {
                    oTable.fnUpdate(aData[i], nRow, i, false);
                }

                oTable.fnDraw();
            }

            function editRow(oTable, nRow) {
                var aData = oTable.fnGetData(nRow);
                var jqTds = $('>td', nRow);
                jqTds[0].innerHTML = '<input type="text" class="m-wrap small" value="' + aData[0] + '">';
                jqTds[1].innerHTML = '<input type="text" class="m-wrap small" value="' + aData[1] + '">';
                jqTds[2].innerHTML = '<input type="text" class="m-wrap small" value="' + aData[2] + '">';
                jqTds[3].innerHTML = '<input type="text" class="m-wrap small" value="' + aData[3] + '">';
                jqTds[4].innerHTML = '<a class="edit" href="">保存</a>';
                jqTds[5].innerHTML = '<a class="cancel" href="">取消</a>';
            }

            function saveRow(oTable, nRow) {
                var jqInputs = $('input', nRow);
                oTable.fnUpdate(jqInputs[0].value, nRow, 0, false);
                oTable.fnUpdate(jqInputs[1].value, nRow, 1, false);
                oTable.fnUpdate(jqInputs[2].value, nRow, 2, false);
                oTable.fnUpdate(jqInputs[3].value, nRow, 3, false);
                oTable.fnUpdate('<a class="edit" href="">编辑</a>', nRow, 4, false);
                oTable.fnUpdate('<a class="delete" href="">删除</a>', nRow, 5, false);
                oTable.fnDraw();
            }

            function cancelEditRow(oTable, nRow) {
                var jqInputs = $('input', nRow);
                oTable.fnUpdate(jqInputs[0].value, nRow, 0, false);
                oTable.fnUpdate(jqInputs[1].value, nRow, 1, false);
                oTable.fnUpdate(jqInputs[2].value, nRow, 2, false);
                oTable.fnUpdate(jqInputs[3].value, nRow, 3, false);
                oTable.fnUpdate('<a class="edit" href="">编辑</a>', nRow, 4, false);
                oTable.fnDraw();
            }

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

            // $('#sample_editable_1_new').click(function (e) {
            //     e.preventDefault();
            //     var aiNew = oTable.fnAddData(['', '', '', '',
            //             '<a class="edit" href="">编辑</a>', '<a class="cancel" data-mode="new" href="">取消</a>'
            //     ]);
            //     var nRow = oTable.fnGetNodes(aiNew[0]);
            //     editRow(oTable, nRow);
            //     nEditing = nRow;
            // });
            //
            // $('#sample_editable_1 a.delete').live('click', function (e) {
            //     e.preventDefault();
            //
            //     if (confirm("你确定删除这条记录吗 ?") == false) {
            //         return;
            //     }
            //
            //     var nRow = $(this).parents('tr')[0];
            //     oTable.fnDeleteRow(nRow);
            //     alert("Deleted! Do not forget to do some ajax to sync with backend :)");
            // });

            // $('#sample_editable_1 a.cancel').live('click', function (e) {
            //     e.preventDefault();
            //     if ($(this).attr("data-mode") == "new") {
            //         var nRow = $(this).parents('tr')[0];
            //         oTable.fnDeleteRow(nRow);
            //     } else {
            //         restoreRow(oTable, nEditing);
            //         nEditing = null;
            //     }
            // });

            // $('#sample_editable_1 a.edit').live('click', function (e) {
            //     e.preventDefault();
            //
            //     /* Get the row as a parent of the link that was clicked on */
            //     var nRow = $(this).parents('tr')[0];
            //
            //     if (nEditing !== null && nEditing != nRow) {
            //         /* Currently editing - but not this row - restore the old before continuing to edit mode */
            //         restoreRow(oTable, nEditing);
            //         editRow(oTable, nRow);
            //         nEditing = nRow;
            //     } else if (nEditing == nRow && this.innerHTML == "保存") {
            //         /* Editing this row and want to save it */
            //         saveRow(oTable, nEditing);
            //         nEditing = null;
            //         alert("Updated! Do not forget to do some ajax to sync with backend :)");
            //     } else {
            //         /* No edit in progress - let's start one */
            //         editRow(oTable, nRow);
            //         nEditing = nRow;
            //     }
            // });

            //添加管理员跳转管理员页面
            $("#sample_editable_1_new").live("click", function(e) {
                window.location.href = "/admin/addManager";

            });

            //点击删除按键操作
            $(".portlet-body table td a").live("click", function(e){
                if (confirm("确定要删除吗？")) {
                    var userId = $(this).attr("data-uid");
                    $.ajax({
                        type: "post",
                        url: "/admin/delete-admin",
                        data: {
                            userId: userId
                        },
                        success: function (data) {
                            if(data.code ==0) {
                                window.location.href = "/admin/admin-manager";
                            } else {
                                alert(data.message);
                            }
                        },
                        error: function() {
                            console.log('删除用户失败!');
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
