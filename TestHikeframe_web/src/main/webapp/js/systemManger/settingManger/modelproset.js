Ext.define('Ext.modelprosetting', {
	extend : 'Ext.data.Model',
	fields : ['id','name','cid','descr','creattime']
});
var modelStore = Ext.create('Ext.data.Store', {
	model : 'Ext.modelprosetting',
	proxy : {
		type : 'ajax',
		url : rootUrl + '/timeSetController/queryModelPro',
		reader : {
			type : 'json',
			root : 'data'
		}
	},
	autoLoad:true
});

var equseObj2=function(obj1,obj2){
	var flag=true;
	for(n in obj1){
		if(obj1[n]!=obj2[n]){
			flag=false;
			break;
		}
	}
	return flag;
}


var saveModelPro=function(params){
	Ext.Ajax.request({
		    url:rootUrl+'/timeSetController/saveModelPro',
		    async: false,
		    params:params,
		    success: function(response){
		    	var respText = Ext.JSON.decode(response.responseText);
		    	Ext.Msg.alert('温馨提示',"成功添加了"+respText.data+"条记录");
		    	modelStore.reload();
		    }
		});
}
var rowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
    clicksToEdit: 2,
    autoCancel: true,
    saveBtnText: '保存',
    cancelBtnText: '取消',
    listeners:{
  	  validateedit:function(){
  		 
  	  },
    	canceledit:function(){
    		modelStore.reload();
      	},
  	  edit:function(editor, context, eOpts){
//  		  var newValues=context.newValues;
  		  var newValues = context.record.data;
  		  var originalValues=context.originalValues;
  		  if(equseObj2(newValues,originalValues)){
  			  return;
  		  }
  		  saveModelPro(newValues);
  	  }
    }
})
var modelgrid = Ext.create('Ext.grid.Panel', {
	store : modelStore,
		columns : [{
			header : "ID",
			dataIndex:'id',
			hidden:true
		},{
			header : "名称",
			dataIndex : 'name',
		    editor: {
		        allowBlank: false
		    }
		},{
			header : "ID",
			dataIndex : 'cid',
		    editor: {
		        allowBlank: false
		    }
		}, {
			header : "创建时间",
			dataIndex : 'creattime',
			hidden:true,
			field:'textfield'
		}, {
			header : "描述",
			dataIndex : 'descr',
			hidden:true,
			field:'textfield'
		}],
	plugins: [rowEditing],
	tbar:[{text: '删除',
		icons:'common_del',
          handler: function(){
        	  var rec = modelgrid.getSelectionModel().getSelection();
              if(rec){
              	 if(rec.length==0){
                   	Ext.Msg.alert('温馨提示',"请先选中一条!");
              	 }else if(rec.length==1){
              		var ids=rec[0].data.id;
              		Ext.Ajax.request({
            		    url:rootUrl+'/timeSetController/deleteModelPro',
            		    async: false,
            		    params:{
            		    	ids:ids
            		    },
            		    success: function(response){
            		    	var respText = Ext.JSON.decode(response.responseText);
            		    	Ext.Msg.alert('温馨提示',"成功删除了"+respText.data+"条记录");
            		    	modelStore.reload();
            		    }
            		});
              	 }
              }
          }
	},{text: '新增',
		icons:'common_del',
        handler: function(){
        	rowEditing.cancelEdit();
        	var r = Ext.create('Ext.modelprosetting', {
        		id: '',
                name: '必填选项',
                cid: '必填选项',
                descr: '',
                creattime: ''
            });
        	modelStore.insert(0, r);
            rowEditing.startEdit(0, 0);
        }
	}], listeners: {
        'selectionchange': function(view, records) {
//        	modelgrid.down('#removeEmployee').setDisabled(!records.length);
        }
    }
	
});

var modelproWin = Ext.create('widget.window', {
	title : '认证周期管理',
	header : {
		titlePosition : 2,
		titleAlign : 'center'
	},
	closable : true,
	closeAction : 'hide',
	frame:false,
	width : 460,
	minWidth : 350,
	height : 330,
	layout : {
		type : 'fit'
	},
	items:[modelgrid]
});