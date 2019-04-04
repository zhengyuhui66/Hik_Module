/**
 * 主面板
 */
var clientGrid=Ext.define('TreeGrid', {
    extend: 'Ext.grid.Panel',
    requires: [
        'Ext.data.*',
        'Ext.grid.*',
        'Ext.util.*',
        'Ext.toolbar.Paging',
        'Ext.ux.ProgressBarPager',
        'Ext.selection.CheckboxModel'
    ],    
	padding:'5 5 5 5',
    xtype: 'tree-grid',
    initComponent: function() {
    	var me = this;
    	/**
    	 * 广告模版模型
    	 */
    	Ext.define('clientmodel', {
    	    extend: 'Ext.data.Model',
    	    fields: [
    	        {name: 'id', type: 'string'},
    	        {name: 'name',  type: 'string'},
    	        {name: 'type',  type: 'string'},
    	        {name: 'version',  type: 'string'},
    	        {name: 'versionname',type:'string'},
    	        {name: 'versiondesc',type:'string'},
    	        {name: 'url',type:'string'},
    	        {name: 'creator',type:'string'},
    	        {name: 'creatime',type:'string'}
    	    ]
    	});
   




        
        var simple = Ext.widget({
            xtype: 'form',
            id: 'simpleForm',
            fieldDefaults: {
                labelWidth : 100,
                labelAlign : 'right'
            },
            padding:'20 20 0 0',
            border:false,
            defaultType: 'textfield',
            defaults:{
            	anchor:'100%',
            	margin:'20 0 0 0'
            },
            items: [{
	                xtype: 'filefield',
	                id: 'file',
	                emptyText: '客户端文件',
	                fieldLabel: '客户端文件',
	                name: 'file',
	                allowBlank: false,
	                margin:'0 0 0 0',
	                blankText: '不能为空!',
	                buttonText: '<span style="width:50px;margin:0px 10px 0px 10px;">选择文件</span>'
	            },{
	                fieldLabel: '版本号',
	                name: 'version',
	                id:'_version',
	                emptyText: '版本号',
	                allowBlank: false,
	                blankText: '不能为空!'
	            },{
	                fieldLabel: '版本号名称',
	                name: 'versionname',
	                id:'_versionname',
	                emptyText: '版本号名称',
	                allowBlank: false,
	                blankText: '不能为空!'
	                
	            },{
	                fieldLabel: '版本说明',
	                name: 'versiondesc',
	                id:'_versiondesc',
	                emptyText: '版本说明',
	                allowBlank: false,
	                blankText: '不能为空!'
	            }],
			
			            buttons: [{
			                text: '提交',
			                handler: function() {
			                	this.up('form').getForm().submit({  
		                                clientValidation:true,
		                                waitMsg:'请稍候',
		                                waitTitle:'正在更新',
		                                url: rootUrl+'/client/upload',
		                                success:function(form,action){
		                                	if(action&&action.result&&action.result.success){
		                                		if(action.result.success=="true"){
		                                			//成功后更新表格并关闭win
		                                			trStore.reload();
		                                		}else if(action.result.success=="false"){
//		                                			alert("上传失败！")
		                                			 Ext.Msg.show({
		                                   				title : '温馨提示',
		                                   				msg :"提交失败!",
		                                   				width : 250,
		                                   				icon : Ext.Msg.ERROR,//INFO,ERROR,QUESTION,WARNING
		                                   				buttonText: { ok: '确定'}
		                                   			});
		                                		}
		                                	}else{
//		                                		alert("上传失败！.")
		                                		 Ext.Msg.show({
		                               				title : '温馨提示',
		                               				msg :"提交失败!",
		                               				width : 250,
		                               				icon : Ext.Msg.ERROR,//INFO,ERROR,QUESTION,WARNING
		                               				buttonText: { ok: '确定'}
		                               			});
		                                	}
		                                	Ext.getCmp("simpleForm").getForm().reset();
		                                	win.close();
		                                },  
		                                failure:function(form,action){  
		                                	 Ext.Msg.show({
		                           				title : '温馨提示',
		                           				msg :"提交失败!",
		                           				width : 250,
		                           				icon : Ext.Msg.ERROR,//INFO,ERROR,QUESTION,WARNING
		                           				buttonText: { ok: '确定'}
		                           			});
//		                                	Ext.getCmp("simpleForm").getForm().reset();
//		                                	win.close();
		                                }
		                            });
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
         * 新增模版的面板
         */
        var win = Ext.create('widget.window', {
                title: '模版',
                header: {
                    titlePosition: 2,
                    titleAlign: 'center'
                },
                closable: true,
                closeAction: 'hide',
                width: 550,
                minWidth: 350,
                height: 400,
                layout: {
                    type: 'border'
                },
                items: {
                    region: 'center',
                    xtype: 'panel',
                    layout:{
                    	type:'vbox',
                    	align: 'stretch'
                    },
                    items:simple
                }
            });
        
   	 /**
   	  * 得到所有用户信息
   	  */
   	 var trStore=Ext.create('Ext.data.Store', {
   		    model: clientmodel,
   		    pageSize:defaultPageSize,
   		    proxy: {
   		    	type: 'ajax',
   		    	url:rootUrl+'/client/query',
   		        reader: {
   		            type: 'json',
   		            root: 'elementList',  
   		            totalProperty: 'total'
   		        }
   		    }
   		});
     /**
      * 新增按钮
      */
     var addAction = Ext.create('Ext.Action', {
     	iconCls:'common_add',
         text: '新增',
         handler: function(widget, event) {
         	win.show();
         }
     });
  
     /**
      * 刷新按钮
      */
     var refreshAction = Ext.create('Ext.Action', {
     	iconCls:'common_refresh',
         text: '刷新',
         handler: function(widget, event) {
         	trStore.reload();
         }
     });
   	 trStore.loadPage(1);
        Ext.apply(this, {
        	store:trStore,
        	 columnLines:true,
        	 rowLines:true, 
     		flex:1,
    		minWidth:900,
    		maxWidth:1300,
            columns: [{
                text: 'ID',
                hidden:true,
                dataIndex: 'id'
            },{
                text: '名称',
                flex: 1,
                align:'center',
                dataIndex: 'name'
            },{
            	text: '类型',
            	flex: 1,
            	align:'center',
            	dataIndex: 'type'
            },{
                text: '版本号',
                flex: 1,
                align:'center',
                dataIndex: 'version'
            },{
                text: '版本号描述',
                flex: 2,
                align:'center',
                dataIndex: 'versiondesc'
            },{
                text: '版本名称',
                flex: 1,
                align:'center',
                dataIndex: 'versionname'
            },{
                text: '上传时间',
                flex: 1,
                align:'center',
                dataIndex: 'creatime'
            },{
                text: '客户端下载地址',
                flex: 1,
                align:'center',
                dataIndex: 'url',
                sortable: true
            },{
                text: '创建人',
                flex: 1,
                align:'center',
                dataIndex: 'creator',
                sortable: true
            }],bbar: {
                xtype: 'pagingtoolbar',
                pageSize:defaultPageSize,
                store: trStore,
                nextText:'下一页',
                prevText:'上一页',
                firstText:'第一页',
                lastText:'最后一页',
                refreshText:'刷新',
                displayInfo: true,
                displayMsg: '显示{0}-{1}条，共{2}条',
                plugins: new Ext.ux.ProgressBarPager()
            },tbar: [addAction,refreshAction]
        });
        
        this.callParent();
    }
});
/**
 * 命名空间，主函数
 */
Ext.application({
    name: 'clientManger',
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
				items:clientGrid
			}]
    	});
    }
});





