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
    	             {name: 'clienttype', type: 'string'},
    	             {name: 'contents', type: 'string'},
    	             {name: 'contract', type: 'string'},
    	             {name: 'scorts', type: 'string'},
    	             {name: 'attachmentpath', type: 'string'},
    	             {name: 'nettype', type: 'string'},
    	             {name: 'system', type: 'string'},
    	             {name: 'creator', type: 'string'},
    	             {name: 'creatime', type: 'string'}
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
            defaults:{
            	anchor:'100%',
            	margin:'20 0 0 0'
            },
            items: [{
            	    xtype:'textareafield',
	                fieldLabel: '回复内容',
	                name: 'versiondesc',
	                id:'_versiondesc',
	                emptyText: '回复内容',
	                allowBlank: false,
	                blankText: '不能为空!'
	            }],buttons: [{
			                text: '提交',
			                handler: function() {
			                	this.up('form').getForm().submit({  
		                                clientValidation:true,
		                                waitMsg:'请稍候',
		                                waitTitle:'正在更新',
		                                url: rootUrl+'/feedback/reply',
		                                success:function(form,action){
		                                	if(action&&action.result&&action.result.success){
		                                		if(action.result.success=="true"){
		                                			//成功后更新表格并关闭win
		                                			trStore.reload();
		                                		}else if(action.result.success=="false"){
//		                                			alert("回复失败！")
		                                			Ext.Msg.show({
		                                 				title : '温馨提示',
		                                 				msg :"回复失败！",
		                                 				width : 250,
		                                 				icon : Ext.Msg.ERROR,//INFO,ERROR,QUESTION,WARNING
		                                 				buttonText: { ok: '确定'}
		                                 			});
		                                		}
		                                	}else{
//		                                		alert("回复失败！")
		                                		Ext.Msg.show({
		                             				title : '温馨提示',
		                             				msg :"回复失败！",
		                             				width : 250,
		                             				icon : Ext.Msg.ERROR,//INFO,ERROR,QUESTION,WARNING
		                             				buttonText: { ok: '确定'}
		                             			});
		                                	}
		                                	Ext.getCmp("simpleForm").getForm().reset();
		                                	win.close();
		                                },  
		                                failure:function(form,action){  
		                                	Ext.getCmp("simpleForm").getForm().reset();
		                                	win.close();
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
                closable: false,
                modal: true,
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
   		    	url:rootUrl+'/feedback/query',
   		        reader: {
   		            type: 'json',
   		            root: 'elementList',  
   		            totalProperty: 'total'
   		        }
   		    }
   		});
//     /**
//      * 新增按钮
//      */
//     var addAction = Ext.create('Ext.Action', {
//     	iconCls:'common_add',
//         text: '新增',
//         handler: function(widget, event) {
//         	win.show();
//         }
//     });
  
   	 
//   	
   var replyAction = Ext.create('Ext.Action', {
  	iconCls:'common_add',
      text: '回复',
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
    		
    		
//    		   "id"                 NUMBER(10)                      not null,
//    		   CLIENTTYPE           VARCHAR(30),
//    		   CONTENTS             VARCHAR(500),
//    		   CONTRACT             VARCHAR(30),
//    		   SCORTS               VARCHAR(30),
//    		   ATTACHMENTPATH       VARCHAR(100),
//    		   NETTYPE              VARCHAR(30),
//    		   SYSTEM               VARCHAR(30),
//    		   CREATOR              VARCHAR(30),
//    		   CREATIME             VARCHAR(30),
    		   
            columns: [{
                text: 'ID',
                hidden:true,
                dataIndex: 'id'
            },{
                text: '客户端版本号',
                flex: 1,
                align:'center',
                dataIndex: 'CLIENTTYPE'
            },{
            	text: '内容',
            	flex: 1,
            	align:'center',
            	dataIndex: 'CONTENTS'
            },{
                text: '联系人',
                flex: 1,
                align:'center',
                dataIndex: 'CONTRACT'
            },{
                text: '评分',
                flex: 2,
                align:'center',
                dataIndex: 'SCORTS'
            },{
                text: '附件类型',
                flex: 1,
                align:'center',
                dataIndex: 'ATTACHMENTPATH'
            },{
                text: '网络类型',
                flex: 1,
                align:'center',
                dataIndex: 'NETTYPE'
            },{
                text: '客户端系统',
                flex: 1,
                align:'center',
                dataIndex: 'SYSTEM',
                sortable: true
            },{
                text: '创建人',
                flex: 1,
                align:'center',
                dataIndex: 'CREATOR',
                sortable: true
            },{
                text: '创建时间',
                flex: 1,
                align:'center',
                dataIndex: 'CREATIME',
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
            },tbar: [replyAction,refreshAction]
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





