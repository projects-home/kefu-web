 
layui.use('table', function(){
  var table = layui.table;
  //监听表格复选框选择
  table.on('checkbox(list)', function(obj){
    console.log(obj)
  });
  //监听工具条
  table.on('tool(list)', function(obj){
    var data = obj.data;
    if(obj.event === 'show'){
    	layer.alert('查看行：<br>'+ JSON.stringify(data))
    } else if(obj.event === 'del'){
      layer.confirm('真的删除行么', function(index){
        obj.del();
        layer.close(index);
      });
    } else if(obj.event === 'edit'){
      layer.alert('编辑行：<br>'+ JSON.stringify(data))
    } 
  });
  
  var $ = layui.$, active = {
    getCheckData: function(){ //获取选中数据
      var checkStatus = table.checkStatus('listTable')
      ,data = checkStatus.data;
	   if(data.length<1){
	    	 layer.alert('请选择数据')
	   }else{
		   var ids ="";
		   for(var i=0;i<data.length;i++){
			   ids+="ids="+data[i].id+"&";
		   } 
		   layer.alert('选中ID：<br>'+ids) 
	   } 
    } 
  };
  
  $('.listTable .layui-btn').on('click', function(){
    var type = $(this).data('type');
    active[type] ? active[type].call(this) : '';
  });
});
 




$(function () {
    $("#jqGrid").jqGrid({
        url: '../userdepartment/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '部门名称', name: 'name', index: 'name', width: 80 }, 			
			{ label: '部门人数', name: 'count', index: 'count', width: 80 }, 			
			{ label: '等级', name: 'level', index: 'level', width: 80 }, 			
			{ label: '上级部门ID', name: 'parentid', index: 'parentid', width: 80 }, 			
			{ label: '备注', name: 'remark', index: 'remark', width: 80 }, 			
			{ label: '创建时间', name: 'createdate', index: 'createdate', width: 80 }, 			
			{ label: '修改时间', name: 'updatedate', index: 'updatedate', width: 80 }, 			
			{ label: '修改人', name: 'updateuser', index: 'updateuser', width: 80 }, 			
			{ label: '是否删除（0否1是）', name: 'isdel', index: 'isdel', width: 80 }			
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		userDepartment: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.userDepartment = {};
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			var url = vm.userDepartment.id == null ? "../userdepartment/save" : "../userdepartment/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.userDepartment),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../userdepartment/delete",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(id){
			$.get("../userdepartment/info/"+id, function(r){
                vm.userDepartment = r.userDepartment;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});