Ext.define('Ext.skinsetting', {
	extend : 'Ext.data.Model',
	fields : ['id','name','skinname','descr','createtime']
});
var skinStore = Ext.create('Ext.data.Store', {
	model : 'Ext.skinsetting',
	proxy : {
		type : 'ajax',
		url : rootUrl + '/timeSetController/querySKIN',
		reader : {
			type : 'json',
			root : 'data'
		}
	},
	autoLoad:true
});




var saveSKINPro=function(params){
	Ext.Ajax.request({
		    url:rootUrl+'/timeSetController/insertSKIN',
		    async: false,
		    params:params,
		    success: function(response){
		    	var respText = Ext.JSON.decode(response.responseText);
		    	Ext.Msg.alert('温馨提示',"成功添加了"+respText.data+"条记录");
		    	skinStore.reload();
		    }
		});
}
var rowStyleEditing = Ext.create('Ext.grid.plugin.RowEditing', {
    clicksToEdit: 2,
    autoCancel: true,
    saveBtnText: '保存',
    cancelBtnText: '取消',
    listeners:{
  	  validateedit:function(){
  		 
  	  },
    	canceledit:function(){
    		skinStore.reload();
      	},
  	  edit:function(editor, context, eOpts){
//  		  var newValues=context.newValues;
  		  var newValues = context.record.data;
  		  var originalValues=context.originalValues;
  		  if(equseObj(newValues,originalValues)){
  			  return;
  		  }
  		saveSKINPro(newValues);
  	  }
    }
})
var Stylegrid = Ext.create('Ext.grid.Panel', {
	store : skinStore,
		columns : [{
			header : "ID",
			dataIndex:'id',
				hidden:true
		},{
			header : "皮肤名称",
			dataIndex : 'name',
		    editor: {
		        allowBlank: false
		    }
		},{
			header : "皮肤地址",
			dataIndex : 'skinname',
		    editor: {
		        allowBlank: false
		    }
		}, {
			header : "创建时间",
			dataIndex : 'createtime',
			hidden:true,
			field:'textfield'
		}, {
			header : "描述",
			dataIndex : 'descr',
			field:'textfield'
		}],
	plugins: [rowStyleEditing],
	tbar:[{text: '删除',
		icons:'common_del',
          handler: function(){
        	  var rec = Stylegrid.getSelectionModel().getSelection();
              if(rec){
              	 if(rec.length==0){
                   	Ext.Msg.alert('温馨提示',"请先选中一条!");
              	 }else if(rec.length==1){
              		var ids=rec[0].data.id;
              		Ext.Ajax.request({
            		    url:rootUrl+'/timeSetController/deleteSKIN',
            		    async: false,
            		    params:{
            		    	ids:ids
            		    },
            		    success: function(response){
            		    	var respText = Ext.JSON.decode(response.responseText);
            		    	Ext.Msg.alert('温馨提示',"成功删除了"+respText.data+"条记录");
            		    	skinStore.reload();
            		    }
            		});
              	 }
              }
          }
	},{text: '新增',
		icons:'common_del',
        handler: function(){
        	rowStyleEditing.cancelEdit();
        	var r = Ext.create('Ext.skinsetting', {
        		id: '',
                name: '必填选项',
                skinname: '必填选项',
                descr: '',
                createtime: ''
            });
        	skinStore.insert(0, r);
            rowStyleEditing.startEdit(0, 0);
        }
	}], listeners: {
        'selectionchange': function(view, records) {
//        	modelgrid.down('#removeEmployee').setDisabled(!records.length);
        }
    }
	
});

var styleWin= Ext.create('widget.window', {
	title : '皮肤管理',
	header : {
		titlePosition : 2,
		titleAlign : 'center'
	},
	closable : true,
	closeAction : 'hide',
	frame:false,
	width : 550,
	minWidth : 350,
	height : 330,
	layout : {
		type : 'fit'
	},
//	html:'23'
	items:[Stylegrid]
});

