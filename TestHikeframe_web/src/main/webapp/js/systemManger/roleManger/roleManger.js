	/**
	 * 高级管理员ID
	 */
	var SENIOR_ID="1E86A11BB795485D994A62F912FC3B1B";
	/**
	 * 超级管理员  
	 */
	var HYPER_ID="26141179392546AEB38B784C465BE226";
/**
 * 角色列表Store
 */
var trStore=Ext.create('Ext.data.Store', {
    storeId:'simpsonsStore',
    fields:['trid', 'pid', 'name','description','create_time'],
    proxy: {
    	type: 'ajax',
    	url:rootUrl+'/roleController/getRoleInfo',
        reader: {
            type: 'json',
            root: 'data'
        }
    },
	autoLoad:true
});
/**
 * 编辑或者新增面板form
 */
var simple = Ext.widget({
    xtype: 'form',
    layout: 'form',
    id: 'simpleForm',
    border:false,
    frame: false,
    bodyPadding: '0 20 0 20',
    fieldDefaults: {
        labelWidth : 120,
        labelAlign : 'right'
    },
    defaultType: 'textfield',
    items: [{ xtype: 'fieldset',
			title: '基本信息',
			defaultType: 'textfield',
			defaults: {
			    anchor: '100%'
			},
			items:[{
			        fieldLabel: '角色序号',
			        name: 'trid',
			        id:'_trid',
			        hidden:true
			    },{
			    	labelStyle : 'margin-left:-20px;',
			    	xtype:'combobox',
			        fieldLabel: '父类角色',
			        displayField: 'name',
			        valueField:'trid',
			        name:'pid',
			        id:'_pid',
			        height:20,
			        width: 320,
			        labelWidth: 120,
			        store:trStore,
			        queryMode: 'local'
			    },{
			        labelStyle : 'margin-left:-20px;margin-top:15px;',
			        fieldStyle : 'margin-top:15px;',
			        fieldLabel: '角色名称 ',
			        name: 'name',
			        id: '_name',
			        allowBlank: false
			    },{
			        labelStyle : 'margin-left:-20px;margin-top:15px;',
			        fieldStyle : 'margin-top:15px;',
			        fieldLabel: '角色描述',
			        name: 'description',
			        id: '_description',
			        allowBlank: false
			    },{
			        fieldLabel: '角色描述',
			        name: 'create_time',
			        id: '_create_time',
			        hidden:true
			     }
			    ]}
			    ],
			
			    buttons: [{
			        text: '提交',
			        handler: function() {
			        	this.up('form').getForm().submit({  
			                        clientValidation:true,
			                        waitMsg:'请稍候',
			                        waitTitle:'正在更新',
			                        url:rootUrl+'/roleController/addOrUpdateRoleInfo',
			                        success:function(form,action){
			                        	if(action&&action.result&&action.result.success){
			                        		if(action.result.success=="true"){
			                        			//成功后更新表格并关闭win
			                        			trStore.reload();
			                        		}else if(action.result.success=="false"){
			                        			alert("更新失败！")
			                        		}
			                        	}else{
			                        		
			                        		alert("更新失败.")
			                        	}
			                        	win.close();
			                        	Ext.getCmp('simpleForm').getForm().reset();
			                        },  
			                        failure:function(form,action){  
			                        	
			                        }   
			                    })
			        }
			    },{
			        text: '取消',
			        handler: function(){
			        	this.up('form').getForm().reset();
			        	 win.close();
			        }
			    }]
			});
/**
 * 新增或者更新的window面板
 */
var win = Ext.create('widget.window', {
    title: '新增角色',
    header: {
        titlePosition: 2,
        titleAlign: 'center'
    },
    border:false,
    frame:false,
    closable: true,
    closeAction: 'hide',
    width: 450,
    minWidth: 350,
    height: 270,
    layout: {
        type: 'border'
    },
    items: {
        region: 'center',
        xtype: 'panel',
        layout:'fit',
        items:simple
    }
});




/**
 * 新增角色按钮
 */
var addAction = Ext.create('Ext.Action', {
    //icon: '../../../components/extjs/shared/icons/fam/add.gif',
	iconCls:'common_add',
    text: '新增',
    handler: function(widget, event) {
    	win.show(this,function(){
    		Ext.getCmp('simpleForm').getForm().reset();
    		win.setTitle("新增角色");
    	});
    }
});
/**
 * 授权角色按钮
 */
var addRightAction = Ext.create('Ext.Action', {
    //icon: '../../../components/extjs/shared/icons/fam/cog.gif',
	iconCls:'common_auth',
    text: '授权',
    handler: function(widget, event) {
    	var rec=storeGrid.getSelectionModel().getSelection();
    	if(rec.length!=1){
        	Ext.Msg.alert('温馨提示',"请先选中一条!");
        }else{
        	Ext.getCmp("simpleForm").getForm().reset();
        	winRight.show(this,function(){
        		treeStore.getProxy().extraParams={
        			ptrid:rec[0].data.pid,
        			trid:rec[0].data.trid
        		};
        		treeStore.load();
        	});
        	}
        
    	
    }
});
/**
 * 更新角色按钮
 */
var updateAction = Ext.create('Ext.Action', {
    //icon: '../../../components/extjs/shared/icons/fam/table_refresh.png',
	iconCls:'common_edit',
    text: '修改',
    handler: function(widget, event) {
    	var rec=storeGrid.getSelectionModel().getSelection();
        if(rec.length==0){
        	Ext.Msg.alert('温馨提示',"请先选中一条!");
        }else if(rec.length>1){
        	Ext.Msg.alert('温馨提示',"您选中了多条，请选中一条!");           	
        }else{
        	 var tempdata=rec[0].data;
        	 if(tempdata.trid==SENIOR_ID){
     			Ext.Msg.alert('温馨提示',"高级管理不可操作");
     			return;
     		}
     		if(tempdata.trid==HYPER_ID){
     			Ext.Msg.alert('温馨提示',"超级管理员不可操作");
     			return;
     		}
        	 win.show(this,function(){
            	 win.setTitle("修改角色");
            	 Ext.getCmp('_trid').setValue(tempdata.trid); 
				 Ext.getCmp('_pid').setValue(tempdata.pid);
				 Ext.getCmp('_name').setValue(tempdata.name);
				 Ext.getCmp('_description').setValue(tempdata.description);
				 Ext.getCmp('_create_time').setValue(tempdata.create_time);
            });
        }
	}
});
/**
 * 删除操伯请求AJAX
 */
var deleteAjax=function(trid){
	
	Ext.Msg.show({
 	     title:'温馨提示',
 	     msg: '确定要删除吗?',
 	    buttonText: {yes:'是', no:'取消'},
 	     icon: Ext.Msg.QUESTION,
 	     fn:function(btn,txt){
 	    	 if(btn=="yes"){
 	    		Ext.Ajax.request({
 	    		    url: rootUrl+'/roleController/deleteRoleById',
 	    		    async:false,
 	    		    params: {
 	    		    	trid:trid
 	    		    },
 	    		    success: function(response){
 	    		        var text = response.responseText;
 	    		        var respText = Ext.JSON.decode(text);
 	    		        if(respText.success=="true"){
 	    		        	Ext.Msg.alert('温馨提示',"成功删除"+respText.data+"条信息");
 	    		        }else{
 	    		        	Ext.Msg.alert('温馨提示',"删除失败!");
 	    		        }
 	    		        trStore.reload();
 	    		    }
 	    		});
 	    		
 	    	 }
 	     }
		});
	

}
/**
 * 删除按钮
 */
var deleteAction = Ext.create('Ext.Action', {
    //icon: '../../../components/extjs/shared/icons/fam/delete.gif',
	iconCls:'common_del',
    text: '删除',
    handler: function(widget, event) {
    	var rec=storeGrid.getSelectionModel().getSelection();
        if(rec.length==0){
        	Ext.Msg.alert('温馨提示',"请先选中一条!");
        }else if(rec.length>1){
        	var params=new Array();
        	for(var i=0;i<rec.length;i++){
        		if(rec[i].data.trid==SENIOR_ID){
        			Ext.Msg.alert('温馨提示',"高级管理不可操作");
        			return;
        		}
        		if(rec[i].data.trid==HYPER_ID){
        			Ext.Msg.alert('温馨提示',"超级管理员不可操作");
        			return;
        		}
        		params.push(rec[i].data.trid);
        	}
        	deleteAjax(params.toString());             	
        }else{
        	 var tempdata=rec[0].data;
        	 if(tempdata.trid==SENIOR_ID){
     			Ext.Msg.alert('温馨提示',"高级管理不可操作");
     			return;
     		}
     		if(tempdata.trid==HYPER_ID){
     			Ext.Msg.alert('温馨提示',"超级管理员不可操作");
     			return;
     		}
        	 deleteAjax(tempdata.trid);
    }
    }
});
/**
 * 角色列表的gridPanel
 */
var storeGrid=Ext.create('Ext.grid.Panel', {
    store: trStore,
    columnLines:true,
	 rowLines:true, 
		flex:1,
		minWidth:900,
		maxWidth:1300,
		padding:'5 5 5 5',
	 frame:false,
	 selModel:Ext.create('Ext.selection.CheckboxModel',{mode:"SIMPLE"}),
    columns: [
        { text: '角色序号',  dataIndex: 'trid',flex:1,hidden:true},
        { text: '父类角色序号', dataIndex: 'pid',flex:1,hidden:true},
        { text: '角色名称', dataIndex: 'name',flex:1},
        { text: '角色描述', dataIndex: 'description',flex:1},
        { text: '角色创建时间', dataIndex: 'create_time',flex:1}
    ],
    height: 400,
    width: 400,
    tbar: [addAction,updateAction,deleteAction,addRightAction]
});

/**
 *选中角色查询所拥有的Store权限查询
 */
var treeStore=Ext.create('Ext.data.TreeStore',{
    proxy: {
        type: 'ajax',
        url:rootUrl+'/roleController/getPRoleRight'
    }
});

/**
 * 保存角色授权信息
 * @param rrid 权限ID 数组
 * @param trid 角色ID
 */
function saveRoleRight(rrid,trid){ 
	if(!rrid||!trid){
		return;
	}
	Ext.Ajax.request({
	    url: rootUrl+'/roleController/saveRoleRight',
	    async:false,
	    params: {
	    	trid:trid,
	    	rrid:rrid
	    },
	    success: function(response){
	        var text = response.responseText;
	        var respText = Ext.JSON.decode(text);
	        if(respText.success=="true"){
	        	Ext.Msg.alert('温馨提示',"授权成功");
	        }else{
	        	Ext.Msg.alert('温馨提示',"授权失败!");
	        }
	        winRight.close();
	        trStore.reload();
	    }
	});
}
/**
 * 角色授权信息面板
 */
var rightList=Ext.create('Ext.tree.Panel',{
	rootVisible: false,
	autoScroll : true,
	overflowY:true,
	useArrows: true,
	store:treeStore,
	buttons: [{
	    text: '提交',
	    handler: function() {
	    	var records = rightList.getView().getChecked(),
	    	names = [];
			Ext.Array.each(records, function(rec){
				names.push(rec.raw.rrid);
			});
			var rec=storeGrid.getSelectionModel().getSelection();
		   	var _trid=rec[0].data.trid;
		   	var _rrid=names.toString();
			saveRoleRight(_rrid,_trid);
	    }
	},{
	    text: '取消',
	    handler: function(){
	    	winRight.close();
	    }
	}],
	listeners: {
		checkchange:function(node,checked){
			if (node.parentNode != null) { 
				//子节点选中 
				node.cascadeBy(function(node){ 
					node.set('checked', checked);
				return true; 
			}); 
		}
		}
	}
});
/**
 * * 角色授权信息面板所承载的window面板
 */
var winRight = Ext.create('widget.window', {
    title: '授权列表',
    header: {
        titlePosition: 2,
        titleAlign: 'center'
    },
    closable: true,
    closeAction: 'hide',
    width: 550,
    minWidth: 450,
    height: 450,
    layout: {
        type: 'border'
    },
    layout:'fit',
    items: rightList
});
/**
 * 命名空间，主函数
 */
Ext.application({
    name: 'menuManger',
    launch: function() {
    	Ext.create('Ext.container.Viewport', {
			layout:'fit',
			 border:false,
			items:[{
				xtype:'panel',
				overflowX:'auto',
				layout:{
					type:'hbox',
					align:'stretch',
					pack:'center'
				},
				items:storeGrid
			}]
//            layout: 'fit',
//            defaultType: 'container',
//            items: [{
//                layout:'fit',
//                items:storeGrid
//            }]
    	});
    }
});

