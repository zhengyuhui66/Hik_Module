Ext.define('Ext.advprosetting', {
	extend : 'Ext.data.Model',
	fields : ['id','name']
});
var advStore = Ext.create('Ext.data.Store', {
	model : 'Ext.advprosetting',
	proxy : {
		type : 'ajax',
		url : rootUrl + '/timeSetController/queryAdvPro',
		reader : {
			type : 'json',
			root : 'data'
		}
	}
});
var advsubStore = Ext.create('Ext.data.Store', {
	model : 'Ext.advprosetting',
	proxy : {
		type : 'ajax',
		url : rootUrl + '/timeSetController/queryAdvPro',
		reader : {
			type : 'json',
			root : 'data'
		}
	}
});
advStore.load({  
    params: {pid:0,leve:1}  
}) 
var saveAdvPro=function(params,leve){
	Ext.Ajax.request({
		    url:rootUrl+'/timeSetController/saveadvPro',
		    async: false,
		    params:params,
		    success: function(response){
		    	var respText = Ext.JSON.decode(response.responseText);
		    	Ext.Msg.alert('温馨提示',"成功添加了"+respText.data+"条记录");
		    	if(leve==1){
		    		advStore.reload();		    		
		    	}else if(leve==2){
		    		advsubStore.reload();
		    	}
		    }
		});
}
var rowAdvEditing = Ext.create('Ext.grid.plugin.RowEditing', {
    clicksToEdit: 2,
    autoCancel: true,
    saveBtnText: '保存',
    cancelBtnText: '取消',
    listeners:{
  	  validateedit:function(){
  		 
  	  },
  	  canceledit:function(){
  		advStore.reload();
  	  },
  	  edit:function(editor, context, eOpts){
//  		  var newValues=context.newValues;
  		var newValues = context.record.data;
  		  var originalValues=context.originalValues;
  		  if(equseObj(newValues,originalValues)){
  			  return;
  		  }
  		  newValues.pid=0;
  		  newValues.leve=1;
  		saveAdvPro(newValues,1);

  	  }
    }
})

var rowAdvsubEditing = Ext.create('Ext.grid.plugin.RowEditing', {
    clicksToEdit: 2,
    autoCancel: true,
    saveBtnText: '保存',
    cancelBtnText: '取消',
    listeners:{
  	  validateedit:function(){
  		 
  	  },
  	canceledit:function(){
    	advsubStore.reload();
  	},
  	  edit:function(editor, context, eOpts){
//  		  var newValues=context.newValues;
  		  var newValues = context.record.data;
  		  var originalValues=context.originalValues;
  		  if(equseObj(newValues,originalValues)){
  			  return;
  		  }
		var rec = advgrid.getSelectionModel().getSelection();
        if(rec){
        	 if(rec.length==0){
             	Ext.Msg.alert('温馨提示',"请先选父类中一条!");
        	 }else if(rec.length==1){
        		var ids=rec[0].data.id;
	    		  newValues.pid=ids;
	      		  newValues.leve=2;
          		saveAdvPro(newValues,2);
        	 }
        }

  	  }
    }
})
var advgrid = Ext.create('Ext.grid.Panel', {
	store : advStore,
		columns : [{
			header : "ID",
			hidden:true,
			dataIndex:'id'
		},{
			header : "名称",
			dataIndex : 'name',
		    editor: {
		        allowBlank: false
		    }
		}],
	flex:1,
	plugins: [rowAdvEditing],
	
	tbar:[{text: '删除',
		icons:'common_del',
          handler: function(){
        	  var rec = advgrid.getSelectionModel().getSelection();
              if(rec){
              	 if(rec.length==0){
                   	Ext.Msg.alert('温馨提示',"请先选中一条!");
              	 }else if(rec.length==1){
              		var ids=rec[0].data.id;
              		Ext.Ajax.request({
            		    url:rootUrl+'/timeSetController/deleteAdvpro',
            		    async: false,
            		    params:{
            		    	ids:ids
            		    },
            		    success: function(response){
            		    	var respText = Ext.JSON.decode(response.responseText);
            		    	Ext.Msg.alert('温馨提示',"成功删除了"+respText.data+"条记录");
            		    	advStore.reload();
            		    	advsubStore.removeAll();
            		    }
            		});
              	 }
              }
          }
	},{text: '新增',
		icons:'common_del',
        handler: function(){
        	rowAdvEditing.cancelEdit();
        	var r = Ext.create('Ext.advprosetting', {
        		id: '',
                name: '必填选项'
            });
        	advStore.insert(0, r);
        	rowAdvEditing.startEdit(0, 0);
        }
	}],
	listeners:{
		select:function(th, record, index, eOpts){
			if(record&&record.data){
				var id=record.data.id;
				advsubStore.load({
				    params: {pid:id,leve:2}  
				}) 
			}
		}
	}
});
var advsubgrid = Ext.create('Ext.grid.Panel', {
	store : advsubStore,
		columns : [{
			header : "ID",
			hidden:true,
			dataIndex:'id'
		},{
			header : "名称",
			dataIndex : 'name',
		    editor: {
		        allowBlank: false
		    }
		}],
	flex:1,
	plugins: [rowAdvsubEditing],
	tbar:[{text: '删除',
		icons:'common_del',
          handler: function(){
        	  var rec = advsubgrid.getSelectionModel().getSelection();
              if(rec){
              	 if(rec.length==0){
                   	Ext.Msg.alert('温馨提示',"请先选中一条!");
              	 }else if(rec.length==1){
              		var ids=rec[0].data.id;
              		Ext.Ajax.request({
            		    url:rootUrl+'/timeSetController/deleteAdvpro',
            		    async: false,
            		    params:{
            		    	ids:ids
            		    },
            		    success: function(response){
            		    	var respText = Ext.JSON.decode(response.responseText);
            		    	Ext.Msg.alert('温馨提示',"成功删除了"+respText.data+"条记录");
            		    	advsubStore.reload();
            		    }
            		});
              	 }
              }
          }
	},{text: '新增',
		icons:'common_del',
        handler: function(){
        	rowAdvsubEditing.cancelEdit();
        	var r = Ext.create('Ext.advprosetting', {
        		id: '',
                name: '必填选项'
            });
        	advsubStore.insert(0, r);
        	rowAdvsubEditing.startEdit(0, 0);
        }
	}]
	
});
var advproWin = Ext.create('widget.window', {
	title : '广告属性管理',
	header : {
		titlePosition : 2,
		titleAlign : 'center'
	},
	closable : true,
	closeAction : 'hide',
	frame:false,
	width : 500,
	minWidth : 350,
	height : 330,
	layout:{
		type:'hbox',
		align:'stretch',
		pack:'center'
	},
	items:[advgrid,advsubgrid]
});