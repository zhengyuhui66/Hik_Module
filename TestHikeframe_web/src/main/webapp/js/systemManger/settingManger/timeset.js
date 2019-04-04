//--时间设置------------------------------------------------------------------------
var addOrupdateURL=null;

Ext.define('Ext.timesetting', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',
		type : 'string'
	}, {
		name : 'name',
		type : 'string'
	}, {
		name : 'starttime',
		type : 'string'
	}, {
		name : 'endtime',
		type : 'string'
	}, {
		name : 'descr',
		type : 'string'
	}]
});
var timeStore = Ext.create('Ext.data.Store', {
	model : 'Ext.timesetting',
	proxy : {
		type : 'ajax',
		url : rootUrl + '/timeSetController/getTime',
		reader : {
			type : 'json',
			root : 'data'
		}
	},
	autoLoad:true
});
var timeform = Ext.widget({
    xtype: 'form',
    id: 'timeformId',
    fieldDefaults: {
        labelWidth : 100,
        labelAlign : 'right'
    },
    flex:2,
    padding:'20 20 0 0',
    border:false,
    defaultType: 'textfield',
    defaults:{
    	anchor:'100%'
    },
    items: [{
        fieldLabel: 'ID',
        name: 'id',
        id:'id',
        hidden:true,
    	labelStyle : 'margin-top:15px;',
        fieldStyle:'margin-top:15px'
    },{
        fieldLabel: '名称',
        name: 'name',
        id:'name',
        allowBlank: false,
    	labelStyle : 'margin-top:15px;',
        fieldStyle:'margin-top:15px',
        blankText: '不能为空!'
    },{
        fieldLabel: '开始时间',
        xtype: 'timefield',
        name: 'starttime',
        format:'H:i:s',
        allowBlank: false,
        id:'starttime',
        blankText: '不能为空!'
    },{
        fieldLabel: '结束时间',
        xtype: 'timefield',
        name: 'endtime',
        format:'H:i:s',
        allowBlank: false,
        blankText: '不能为空!',
        id:'endtime'
     },{
        fieldLabel: '描述',
     	labelStyle : 'margin-top:15px;',
        fieldStyle:'margin-top:15px',
        xtype:'textareafield',
        name: 'descr',
        id:'descr'
      }],
	 buttons: [{
                text: '提交',
                handler: function() {
                	this.up('form').getForm().submit({  
                            clientValidation:true,
                            waitMsg:'请稍候',
                            waitTitle:'正在更新',
                            url: addOrupdateURL,
                            success:function(form,action){
                            	if(action&&action.result&&action.result.success){
                            		if(action.result.success=="true"){
                            			//成功后更新表格并关闭win
                            			timeStore.reload();
                            		}else if(action.result.success=="false"){
                            			alert("上传失败！")
                            		}
                            	}else{
                            		alert("上传失败！.")
                            	}
                            	addWin.close();
                            },  
                            failure:function(form,action){
                            }
                        });
                	}
	            },{
	                text: '取消',
	                handler: function(){
	                	this.up('form').getForm().reset();
	                	addWin.close();
	                }
	            }]
	        });

var addAction = Ext.create('Ext.Action', {
	iconCls:'common_add',
    text: '新增',
    handler: function(widget, event) {
    	addWin.setTitle('新增');
    	addOrupdateURL=rootUrl+'/timeSetController/addTime',
    	addWin.show(this,function(){
    		timeform.getForm().reset();
    	});
    }
});
var updateAction = Ext.create('Ext.Action', {
	iconCls:'common_update',
    text: '更新',
    handler: function(widget, event) {
    	var rec = timegrid.getSelectionModel().getSelection();
    	if(rec&&rec.length==1){
    		var tempdata=rec[0].data;    		
        	addWin.setTitle('更新');
        	addOrupdateURL=rootUrl+'/timeSetController/editTime',
        	addWin.show(this,function(){
        		Ext.getCmp("id").setValue(tempdata.id);
        		Ext.getCmp("name").setValue(tempdata.name);
				Ext.getCmp("starttime").setValue(tempdata.starttime);
       		 	Ext.getCmp("endtime").setValue(tempdata.endtime);
       		 	Ext.getCmp("descr").setValue(tempdata.descr);
        	});
    	}else{
    		Ext.Msg.alert('温馨提示',"请选中一条");
    	}
    }
});
var deleteAction = Ext.create('Ext.Action', {
	iconCls:'common_del',
    text: '删除',
    handler: function(widget, event) {
    	
    	var rec = timegrid.getSelectionModel().getSelection();
    	if(rec&&rec.length>0){
    		var params=new Array();
    		for(var i=0;i<rec.length;i++){
    			params.push(rec[i].data.id);
    		}
         	Ext.Msg.show({
        	     title:'温馨提示',
         	     msg: '确定要删除吗?',
         	    buttonText: {yes:'是', no:'取消'},
         	     icon: Ext.Msg.QUESTION,
         	     fn:function(btn,txt){
         	    	 if(btn=="yes"){
         	    		Ext.Ajax.request({
         	        	    url:rootUrl+'/timeSetController/deleteTime',
         	        	    async: false,
         	        	    params: {
         				        id: params.toString()
         				    },
         	        	    success: function(response){
         	        	    	var respText = Ext.JSON.decode(response.responseText);
         				    	Ext.Msg.alert('温馨提示',"成功删除了"+respText.data+"条记录");
         				    	timeStore.reload();
         	        	    }
         	        	});
         	    	 }
         	     }
       		});
         	

    	}
    }
});
var refreshAction = Ext.create('Ext.Action', {
	iconCls:'common_refresh',
    text: '刷新',
    handler: function(widget, event) {
    	timeStore.reload();
    }
});
var timegrid = Ext.create('Ext.grid.Panel', {
	store : timeStore,
	flex:3,
	
	selModel : Ext.create('Ext.selection.CheckboxModel', {
		mode : "SIMPLE"
	}),
	columns : [Ext.create('Ext.grid.RowNumberer'), 
	           {
				text : "ID",
				flex : 1,
				align:'center',
				sortable:true,
				hidden:true,
				dataIndex:'id'
			},{
				text : "名称",
				flex : 1,
				align:'center',
				sortable : true,
				dataIndex : 'name'
			}, {
				text : "开始时间",
				flex : 1,
				align:'center',
				sortable : true,
				dataIndex : 'starttime'
			}, {
				text : "结束时间",
				flex : 1,
				align:'center',
				sortable : true,
				dataIndex : 'endtime'
			}, {
				text : "描述",
				flex : 1,
				align:'center',
				sortable : true,
				dataIndex : 'descr'
			}],
	columnLines : true,
	tbar: [addAction,updateAction,deleteAction,refreshAction]
});

var timeWin = Ext.create('widget.window', {
	title : '时间设置',
	header : {
		titlePosition : 2,
		titleAlign : 'center'
	},
	closable : true,
	closeAction : 'hide',
	frame:false,
	width : 960,
	minWidth : 350,
	height : 430,
	layout : {
		type : 'fit'
	},
	items : timegrid
});

var addWin = Ext.create('widget.window', {
	title : '新增设置时间',
	header : {
		titlePosition : 2,
		titleAlign : 'center'
	},
	closable : true,
	closeAction : 'hide',
	frame:false,
	width : 360,
	minWidth : 350,
	height : 330,
	layout : {
		type : 'fit'
	},
	items : timeform
});