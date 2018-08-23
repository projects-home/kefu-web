 
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
        url: '../userinfo/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '用户id', name: 'uid', index: 'uid', width: 80 }, 			
			{ label: '部门', name: 'deptid', index: 'deptid', width: 80 }, 			
			{ label: '姓名', name: 'name', index: 'name', width: 80 }, 			
			{ label: '昵称', name: 'nickname', index: 'nickname', width: 80 }, 			
			{ label: '性别（0女 1男）', name: 'sex', index: 'sex', width: 80 }, 			
			{ label: '生日', name: 'birthday', index: 'birthday', width: 80 }, 			
			{ label: '身份证', name: 'cardid', index: 'cardid', width: 80 }, 			
			{ label: '签名', name: 'signature', index: 'signature', width: 80 }, 			
			{ label: '毕业院校', name: 'school', index: 'school', width: 80 }, 			
			{ label: '学历', name: 'education', index: 'education', width: 80 }, 			
			{ label: '现居住地址', name: 'address', index: 'address', width: 80 }, 			
			{ label: '联系电话', name: 'phone', index: 'phone', width: 80 }, 			
			{ label: '邮箱', name: 'email', index: 'email', width: 80 }, 			
			{ label: '备注', name: 'remark', index: 'remark', width: 80 }, 			
			{ label: '个人头像', name: 'profilephoto', index: 'profilephoto', width: 80 }, 			
			{ label: '创建时间', name: 'createdate', index: 'createdate', width: 80 }, 			
			{ label: '创建人', name: 'createuser', index: 'createuser', width: 80 }, 			
			{ label: '修改时间', name: 'updatedate', index: 'updatedate', width: 80 }, 			
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
		userInfo: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.userInfo = {};
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
			var url = vm.userInfo.id == null ? "../userinfo/save" : "../userinfo/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.userInfo),
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
				    url: "../userinfo/delete",
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
			$.get("../userinfo/info/"+id, function(r){
                vm.userInfo = r.userInfo;
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