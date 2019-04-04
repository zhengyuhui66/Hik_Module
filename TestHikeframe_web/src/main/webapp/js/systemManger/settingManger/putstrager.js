Ext.define('Ext.putstragersetting', {
	extend : 'Ext.data.Model',
	fields : ['id','name','cid','interval','descr','creatime']
});
var putstragerStore = Ext.create('Ext.data.Store', {
	model : 'Ext.putstragersetting',
	proxy : {
		type : 'ajax',
		url : rootUrl + '/timeSetController/queryPutStragerPro',
		reader : {
			type : 'json',
			root : 'data'
		}
	},
	autoLoad:true
});

var savePutStragerPro=function(params){
	Ext.Ajax.request({
		    url:rootUrl+'/timeSetController/savePutStragerPro',
		    async: false,
		    params:params,
		    success: function(response){
		    	var respText = Ext.JSON.decode(response.responseText);
		    	Ext.Msg.alert('温馨提示',"成功添加了"+respText.data+"条记录");
		    	putstragerStore.reload();
		    }
		});
}
var rowPutStragerEditing = Ext.create('Ext.grid.plugin.RowEditing', {
    clicksToEdit: 2,
    autoCancel: true,
    saveBtnText: '保存',
    cancelBtnText: '取消',
    listeners:{
  	  validateedit:function(){
  		 
  	  },
  	canceledit:function(){
  		putstragerStore.reload();
  	},
  	  edit:function(editor, context, eOpts){
//  		  var newValues=context.newValues;
  		  var newValues = context.record.data;
  		  var originalValues=context.originalValues;
  		  if(equseObj(newValues,originalValues)){
  			  return;
  		  }
  		  savePutStragerPro(newValues);
  	  }
    }
})

var putstragergrid = Ext.create('Ext.grid.Panel', {
	store : putstragerStore,
		columns : [{
			header : "ID2",
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
		},{
			header : "间隔",
			dataIndex : 'interval',
			editor: {
		        allowBlank: false
		    }
		}, {
			header : "创建时间",
			dataIndex : 'creatime',
			hidden:true,
			field:'textfield'
		}, {
			header : "描述",
			dataIndex : 'descr',
			field:'textfield'
		}],
	plugins: [rowPutStragerEditing],
	tbar:[{text: '删除',
		icons:'common_del',
          handler: function(){
        	  var rec = putstragergrid.getSelectionModel().getSelection();
              if(rec){
              	 if(rec.length==0){
                   	Ext.Msg.alert('温馨提示',"请先选中一条!");
              	 }else if(rec.length==1){
              		var ids=rec[0].data.id;
              		Ext.Ajax.request({
            		    url:rootUrl+'/timeSetController/deletePutStragerPro',
            		    async: false,
            		    params:{
            		    	id:ids
            		    },
            		    success: function(response){
            		    	var respText = Ext.JSON.decode(response.responseText);
            		    	Ext.Msg.alert('温馨提示',"成功删除了"+respText.data+"条记录");
            		    	putstragerStore.reload();
            		    }
            		});
              	 }
              }
          }
	},{text: '新增',
		icons:'common_del',
        handler: function(){
        	rowPutStragerEditing.cancelEdit();
        	var r = Ext.create('Ext.putstragersetting', {
        		id: '',
                name: '必填选项',
                cid: '必填选项',
                interval:'必填选项',
                descr: '',
                creattime: ''
            });
        	putstragerStore.insert(0, r);
            rowPutStragerEditing.startEdit(0, 0);
        }
	}], listeners: {
        'selectionchange': function(view, records) {
//        	modelgrid.down('#removeEmployee').setDisabled(!records.length);
        }
    }
	
});

var putStragerWin = Ext.create('widget.window', {
	title : '播放策略管理',
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
	items:[putstragergrid]
});