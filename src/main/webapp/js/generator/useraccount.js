 
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
        url: '../useraccount/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '帐号', name: 'account', index: 'account', width: 80 }, 			
			{ label: '密码', name: 'password', index: 'password', width: 80 }, 			
			{ label: '禁用状态（0 启用  1 禁用）', name: 'disablestate', index: 'disablestate', width: 80 }, 			
			{ label: '是否删除（0未删除1已删除）', name: 'isdel', index: 'isdel', width: 80 }, 			
			{ label: '创建日期', name: 'createdate', index: 'createdate', width: 80 }, 			
			{ label: '修改日期', name: 'updatedate', index: 'updatedate', width: 80 }, 			
			{ label: '修改人', name: 'updateuser', index: 'updateuser', width: 80 }			
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
		userAccount: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.userAccount = {};
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
			var url = vm.userAccount.id == null ? "../useraccount/save" : "../useraccount/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.userAccount),
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
				    url: "../useraccount/delete",
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
			$.get("../useraccount/info/"+id, function(r){
                vm.userAccount = r.userAccount;
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