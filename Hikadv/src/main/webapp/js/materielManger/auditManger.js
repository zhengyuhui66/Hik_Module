var commonSence={
		AUDITED:"已审核",
		/**
		 * 未审核
		 */
		UNAUDIT:"未审核",
		/**
		 * 未通审核
		 */
		AUDITED_UNPASS:"审核未通过",
		/**
		 * 通过审核
		 */
		AUDITED_PASS:"审核通过"
}


var materialGrid=Ext.define('KitchenSink.view.tree.TreeGrid', {
    extend: 'Ext.grid.Panel',
    requires: [
//        'Ext.data.*',
//        'Ext.grid.*',
//        'Ext.util.*',
//        'Ext.toolbar.Paging',
        'Ext.ux.ProgressBarPager',
//        'Ext.selection.CheckboxModel'
    ],    
	padding:'5 5 5 5',
    xtype: 'material-grid',
    initComponent: function() {
    	var tempItems=new Object();
    	var checkBoxItems=new Array();
    	var getReviceId=new Array();
    	var mater_path="";
    	var mater_name="";
    	/**
    	 * 菜单模型
    	 */
    	Ext.define('Ext.menu.model', {
    	    extend: 'Ext.data.Model',
    	    fields: [
             {
     	        name: 'audit_id',
    	        type: 'string'
    	    },{
    	        name: 'customer_id',
    	        type: 'string'
    	    },{
    	        name: 'mater_name',
    	        type: 'string'
    	    },{
    	        name: 'mater_size',
    	        type: 'string'
    	    },{
    	        name: 'mater_type',
    	        type: 'string'
    	    }, {
    	        name: 'create_time',
    	        type: 'string'
    	    }, {
    	        name: 'mater_path',
    	        type: 'string'
    	    }, {
    	        name: 'mater_state',
    	        type: 'string'
    	    }, {
    	        name: 'audit_state',
    	        type: 'string'
    	    }, {
    	        name: 'audit_result',
    	        type: 'string'
    	    }, {
    	        name: 'audit_stime',
    	        type: 'string'
    	    }, {
    	        name: 'audit_name',
    	        type: 'string'
    	    }, {
    	        name: 'audit_proce',
    	        type: 'string'
    	    }, {
    	        name: 'mater_id',
    	        type: 'string'
    	    }, {
    	        name: 'mater_width',
    	        type: 'string'
    	    }, {
    	        name: 'mater_height',
    	        type: 'string' 	
    	        	
    	    }]
    	}); 
    	
    	
    	/**
    	 * 物料审核展示加载的STORE
    	 */
    	var materialStore=Ext.create('Ext.data.Store', {
    	    storeId:'simpsonsStore',
    	    model: Ext.menu.model,
    	    pageSize:defaultPageSize,
    	    proxy: {
    	    	type: 'ajax',
    	    	url:rootUrl+'/materialController/queryMaterAudit',
    	        reader: {
    	            type: 'json',
    	            root: 'elementList',  
    	            totalProperty: 'total'
    	        }
    	    }
    	});
    	/**
    	 * 默认加载第一页
    	 */
    	materialStore.loadPage(1);

        var material = Ext.create('Ext.form.Panel', {
            plain: true,
            border: 0,
            margin:5,
            width:100,
            fieldDefaults: {
                labelWidth: 90,
                anchor: '100%'
            },
            flex:1,
            overflowY:'auto',
//            layout:'fit',
            layout: {
                type: 'vbox',
                align: 'stretch'
            },
            title: '物料展示',
            items: [{
				xtype: 'fieldset',
				title: '审核信息',
				layout: {
	                type: 'vbox',
	                align: 'stretch'  // Child items are stretched to full width
	            },
	            defaults: {
	                anchor: '100%',
	           	 	xtype: 'container',
	                layout: 'hbox',
	                border:1,
	                xtype: 'displayfield',

	                margin: '0 0 5 0'
	            },
                items:[{
			                fieldLabel: '审核ID',
			                hidden:true,
			                name: 'audit_id',
			                id:'id_auditid'
			            },{
			                fieldLabel: '上传用户',
			                width:400,
			                name: 'user_id',
			                id:'id_userid'
			            },{
			                fieldLabel: '物料名称',
			                name: 'mater_name',
			                id:'id_mater_name'
			            },{
			                fieldLabel: '物料类型',
			                name: 'mater_type',
			                id:'id_mater_type'
			            },{
			                fieldLabel: '物料大小',
			                name: 'mater_size',
			                id:'id_mater_size'
			            },{ 
			                fieldLabel: '审核流程',
			                name: 'audit_name',
			                id:'id_audit_name'	
			            },{
			                fieldLabel: '审核流程ID',
			                hidden:true,
			                name: 'audit_proce',
			                id:'id_audit_proce'	
			            },{ 
			                fieldLabel: '审核时间',
			                hidden:true,
			                name: 'audit_stime',
			                id:'id_audit_stime'
			            },{
			            	
			                fieldLabel: '物料ID',
			                name: 'mater_id',
			                id:'id_mater_id'	
			            },{ 
			                fieldLabel: '物料高度(像素)',
			                name: 'mater_height',
			                id:'id_mater_height'	
			            },{ 
			                fieldLabel: '物料宽度(像素)',
			                name: 'mater_width',
			                id:'id_mater_width'	
			            },{
			                xtype:'tbspacer',
			                flex:1
			            },{
			                xtype: 'textareafield',
			                emptyText:"审核意见",
			                name: 'audit_desc',
			                height: 100,
			                margin: 0,
			                id:'id_audit_desc'
			            }]
            }],
            buttons: [{
	          text: commonSence.AUDITED_PASS,
	          id:'passButtonId',
	          handler: function(){

	        	  auditFun(commonSence.AUDITED_PASS);
	          	}
		      },{    
		          text:commonSence.AUDITED_UNPASS,
		          id:'unpassButtonId',
		          handler: function() {
		        	  auditFun(commonSence.AUDITED_UNPASS);
		          }
		      }]
        });

      
      
        var auditFun=function(reuslt){
        	 var audit_proce= Ext.getCmp('id_audit_proce').getValue();
        	 var mater_id= Ext.getCmp('id_mater_id').getValue();
        	 
        	 var flag=false;
        	 if(audit_proce!=0){ 
        		 Ext.Ajax.request({
				    url: rootUrl+'/materialController/checkiPassAudit',
				    params: {  
				    	audit_proce:audit_proce,
				    	mater_id:mater_id
				    },
				    async: false,
				    success: function(response){
				    	var respText = Ext.JSON.decode(response.responseText);
				    	var _date = respText.data[0];
				    	if(_date.audit_state!=commonSence.AUDITED||_date.audit_result!=commonSence.AUDITED_PASS){
				    		flag=true;
				    		win.close();
				    		if(_date.audit_state==commonSence.AUDITED){
//				    			Ext.Msg.alert('温馨提示',_date.name+_date.audit_result);	
				    			Ext.Msg.show({
                     				title : '温馨提示',
                     				msg :_date.name+_date.audit_result,
                     				width : 250,
                     				icon : Ext.Msg.INFO,//INFO,ERROR,QUESTION,WARNING
                     				buttonText: { ok: '确定'}
                     			});
				    		}else{
//				    			Ext.Msg.alert('温馨提示',_date.name+_date.audit_state);		
				    			Ext.Msg.show({
                     				title : '温馨提示',
                     				msg :_date.name+_date.audit_state,
                     				width : 250,
                     				icon : Ext.Msg.INFO,//INFO,ERROR,QUESTION,WARNING
                     				buttonText: { ok: '确定'}
                     			});
				    		}
				    	}
				    }
				});
        	 }
        	if(flag){
        		return;
        	}
		  	var audit= Ext.getCmp('id_auditid').getValue();
		  	var audit_desc= Ext.getCmp('id_audit_desc').getValue();
			  Ext.Ajax.request({
				    url: rootUrl+'/materialController/auditUpdate',
				    params: {  
				    	auditId:audit,
				    	audit_state:commonSence.AUDITED,
				    	audit_result:reuslt,
				    	audit_desc:audit_desc,
				    	mater_id:mater_id,
				    	audit_proce:audit_proce
				    },
				    success: function(response){
				    	win.close();
				    	var respText = Ext.JSON.decode(response.responseText);
				    	if(respText.success=='true'){
//				    		Ext.Msg.alert('温馨提示',"成功审核了"+respText.data+"条记录");
				    		Ext.Msg.show({
                 				title : '温馨提示',
                 				msg :"成功审核了"+respText.data+"条记录",
                 				width : 250,
                 				icon : Ext.Msg.INFO,//INFO,ERROR,QUESTION,WARNING
                 				buttonText: { ok: '确定'}
                 			});
				    	}else if(respText.success=='false'){
//				    		Ext.Msg.alert('温馨提示',respText.data);	
				    		Ext.Msg.show({
                 				title : '温馨提示',
                 				msg :respText.data,
                 				width : 250,
                 				icon : Ext.Msg.INFO,//INFO,ERROR,QUESTION,WARNING
                 				buttonText: { ok: '确定'}
                 			});
				    	}
				    		
				    	materialStore.reload();
				    }
				});
        }

//        var filterPanel = Ext.create('Ext.panel.Panel',{
//            bodyPadding: 5,  // Don't want content to crunch against the borders
//            layout:{
//            	type:'hbox',
//            	align: 'middle',
//            	pack: 'center'
//            },
//    		overflowX:'auto',
//    		overflowY:'auto',
//            height:460,
//  	        name: 'msg',
//  	        id:'id_msg',
//  	        html:'<div id="img_id" style="width:470px;height:450px;overflow:auto"><img id="materShow" src="" onerror="this.src=\''+rootUrl+"/images/default/default.jpg"+'\'"></img></div>'
//        });
//        /**
//         * 新增或更新弹出的面板
//         */
//        var win= Ext.create('widget.window', {
//                title: '新增物料审核',
//                header: {
//                    titlePosition: 2,
//                    titleAlign: 'center'
//                },
//                closable: false,
//                closeAction: 'hide',
//                width: 1050,
//                minWidth: 350,
//                height: 450,
//                layout: {
//                    type: 'border'
//                },
//                items: {
//                    region: 'center',
//                    xtype: 'panel',
//                    layout:{
//                    	type:'hbox',
//                    	align: 'stretch'
//                    },
//                    items:[material,
//                           {
//								xtype: 'fieldset',
//								title: '审核图片',
//								flex:1,
//								margin:5,
//					    		overflowX:'auto',
//					    		overflowY:'auto',
//		                        items:filterPanel
//		                    }]
//                }
//            });
        /**
         * 物料审核的面板
         */
        var win = Ext.create('widget.window', {
                title: '物料审核',
                header: {
                    titlePosition: 2,
                    titleAlign: 'center'
                },
                closable: true,
                closeAction: 'hide',
                width: 650,
                modal: true,
                frame:false,
                border:false,
                minWidth: 350,
                height: 350,
                layout: {
                    type: 'border',
                    padding: 5
                },
                items: {
                    region: 'center',
                    xtype: 'tabpanel',
                    activeTab: 0,
                    border:false,
                    frame:false,
                    items:[material,{
//								xtype: 'fieldset',
//								title: '基本信息',
//								margin:5,
//								layout:'fit',
//								width:600,
//	                            items:materialInfo
//                            }, {
								title: '物料展示',
								stateId:'materinfo',
								html:'<div style="width:100%;height:100%;overflow:auto;padding:10px;"><img id="materShow" src="" onerror="this.src=\''+rootUrl+"/images/default/default.jpg"+'\'"></img></div>'
                            }],
                            listeners:{
                            	  'tabchange':function(me, eOpts){
                            		  if(eOpts.stateId=='materinfo'){
                            			  $("#materShow").attr('src',mater_path);
                            			  $("#materShow").attr('alt',mater_name);
                            		  }
                            	  }
                              }
                }
            });
    	var resetAuditUser=function(){
    		Ext.Ajax.request({
    		    url: rootUrl+'/materialController/getAuditChecked',
    		    async: false,
    		    success: function(response){
    		    	var respText = Ext.JSON.decode(response.responseText);
    		    	var data = respText.data;
    		    	for(var i=0;i<data.length;i++){
    		    		var checkedweeks = Ext.getCmp('audit_'+data[i].auditId); 
    		    		var itema=checkedweeks.items;
    		    		for(var k=0;k<itema.length;k++){  
		    			    if(itema.get(k).inputValue==data[i].userId)
		    			    	itema.get(k).setValue(true);
		    			}
    		    	}
    		    }
    		});
    	}
    	var tr=getHaveRight('setVaAudit');

        /**
         * 刷新按钮
         */
        var refreshAction = Ext.create('Ext.button.Button', {
        	iconCls:'common_refresh',
            text: '刷新',
            focusCls:'blue',
            cls:'blue',
            overCls:'refresh',
            handler: function(widget, event) {
            	materialStore.reload();
            }
        });
        var checkAllifPassAudit=function(materid){
    		var flag=null;
        	Ext.Ajax.request({
    		    url: rootUrl+'/materialController/checkAllPassAudit',
    		    async: false,
    		    params:{mater_id:materid},
    		    success: function(response){
    		    	var respText = Ext.JSON.decode(response.responseText);
    		    	var data = respText.data;
    		    	flag=data;
    		    }
    		});
    		return flag;
    	}
    	/**
    	 *物料审核展示的表格
    	 *
    	 */
        Ext.apply(this, {
        	store:materialStore,
        		columnLines:true,
        		rowLines:true, 
        		flex:1,
        		minWidth:900,
        		maxWidth:1300,
        	    columns: [
        	      {
          	        text: '审核ID',
          	        hidden:true,
          	        dataIndex: 'audit_id'
          	    },{
        	        text: '物料上传者',
        	        flex: 1,
        	        align:'center',
        	        sortable: true,
        	        dataIndex: 'customer_id'
        	    },{
        	        text: '物料名称',
        	        flex: 1,
        	        align:'center',
        	        dataIndex: 'mater_name',
        	        sortable: true
        	    },{
        		   text: '物料大小',
        	       hidden:true,
        	       dataIndex: 'mater_size'
        	   },{
        	        text: '物料类型',
        	        hidden:true,
        	        dataIndex: 'mater_type'
        	    },{
        	        text: '物料生成时间',
        	        hidden:true,
        	        dataIndex: 'create_time'
        	    },{
        	        text: '物料存储路径',
        	        hidden:true,
        	        dataIndex: 'mater_path'
        	    },{
        	        text: '物料状态',
        	        hidden:true,
        	        dataIndex: 'mater_state'
        	    },{
        	        text: '审核结果',
        	        flex: 1,
        	        align:'center',
        	        dataIndex: 'audit_result',
        	        sortable: true
        	    },{
        	        text: '审核状态',
        	        flex: 1,
        	        align:'center',
        	        dataIndex: 'audit_state',
        	        sortable: true
        	    },{
        	        text: '审核时间',
        	        flex: 1,
        	        align:'center',
        	        dataIndex: 'audit_stime',
        	        sortable: true
        	    },{
        	        text: '审核流程ID',
        	        hidden:true,
        	        dataIndex: 'audit_proce'
        	    },{
        	        text: '物料ID',
        	        flex: 1,
        	        hidden:true,
        	        dataIndex: 'mater_id',
        	        sortable: true
        	    },{
        	        text: '物料高度(像素)',
        	        flex: 1,
        	        align:'center',
        	        dataIndex: 'mater_height',
        	        sortable: true
        	    },{
        	        text: '物料宽度(像素)',
        	        flex: 1,
        	        align:'center',
        	        dataIndex: 'mater_width',
        	        sortable: true
        	    },{
        	    	 text: '审核流程', 
        	    	 flex: 1,
        	    	 align:'center',
        	    	 dataIndex: 'audit_name', 
        	    	 icon: '../../components/extjs/shared/icons/fam/add.gif'	 
        	    },{
       	    	 xtype:'actioncolumn',
    	    	 text: '审核', 
    	    	 align:'center',
    	    	 items:[{
                     getClass: function(v, meta, rec) {
                    	 return 'auditicons_1';
//                    	 var _v = rec.get('audit_name');
//                         if (_v=='政治审核') {
//                             return 'auditicons_0';
//                         }else if(_v=='法律审核'){
//                             return 'auditicons_1';
//                         }else if(_v=='规则审核'){
//                        	 return 'auditicons_2';
//                         }
                     },
                     handler: function(grid, rowIndex, colIndex) {
                    	 var rec = grid.getStore().getAt(rowIndex); 
                    	 
                    	 var auditid=rec.get('audit_id');
                    	 mater_name = rec.get('mater_name');
                    	 var mater_type=rec.get('mater_type');
                    	 var mater_size=rec.get('mater_size');
//                    	 mater_path=rec.get('mater_path');
//                    	 mater_path=rootUrl+"/"+rec.get('mater_path');
                    	 var _msg=rec.get('mater_path');
                    	 mater_path=_msg.indexOf("load/")>0?rootUrl+"/"+_msg:httpUrlResources+_msg;
                    	 var userId = rec.get('customer_id');
                    	 var mater_id = rec.get('mater_id');
                    	 var audit_name = rec.get('audit_name');
                    	 var audit_proce= rec.get('audit_proce');
                    	 var mater_width = rec.get('mater_width');
                    	 var mater_height = rec.get('mater_height');
                    	 var audit_stime = rec.get('audit_stime');
                    	 
                    	 material.getForm().reset();
                    	 win.show(this,function(){
                    		 Ext.getCmp('id_auditid').setValue(auditid);
                    		 Ext.getCmp('id_userid').setValue(userId);
                    		 Ext.getCmp('id_mater_name').setValue(mater_name);
                    		 Ext.getCmp('id_mater_type').setValue(mater_type);
                    		 Ext.getCmp('id_mater_size').setValue(mater_size+'Byte');
                    		 Ext.getCmp('id_audit_name').setValue(audit_name);
                    		 Ext.getCmp('id_audit_proce').setValue(audit_proce);
                    		 Ext.getCmp('id_mater_id').setValue(mater_id);
                    		 Ext.getCmp('id_mater_width').setValue(mater_width);
                    		 Ext.getCmp('id_mater_height').setValue(mater_height);
                    		 Ext.getCmp('id_audit_stime').setValue(audit_stime);
                    		 
                    		 $("#materShow").attr('src',mater_path);
                    		 $("#materShow").attr('alt',mater_name);
                    		 
                    		 var ifpassAudit=checkAllifPassAudit(mater_id);
                    		 Ext.getCmp('passButtonId').setDisabled(ifpassAudit);
                    		 Ext.getCmp('unpassButtonId').setDisabled(ifpassAudit);
                    	 })
                     }
                 }]
        	    }
        	    ],bbar: {
        	        xtype: 'pagingtoolbar',
        	        pageSize:defaultPageSize,
        	        store: materialStore,
                    nextText:'下一页',
                    prevText:'上一页',
                    firstText:'第一页',
                    lastText:'最后一页',
                    refreshText:'刷新',
        	        displayInfo: true,
        	        displayMsg: '显示{0}-{1}条，共{2}条',
        	        plugins: new Ext.ux.ProgressBarPager()
        	    },
        	    tbar: [/*addAction,*/refreshAction,'->',
                       {
	        		xtype:'textfield',
	        		emptyText: '请输入需要搜索的信息',
	        		width:200,
	                name: 'searchCondiction',
	                id: '_searchCondiction'
	           },{
	        	   xtype:'button',
	        	   text: '搜索',
	        	   iconCls:'common_search',
	        	   handler: function() {
	        		   var searchStr  = Ext.getCmp('_searchCondiction').getValue();
	        		   materialStore.getProxy().extraParams = {searchStr:searchStr};
	        		   materialStore.loadPage(1);
	        	    }
	           }]});
        
        this.callParent();
    }
});


/**
 * 命名空间，主函数
 */
Ext.application({
    name: 'materialManger',
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

				items:materialGrid
			}]
//            layout: 'fit',
//    		overflowX:'auto',
//    		overflowY:'auto',
//            defaultType: 'container',
//            items: [{
//                layout:'fit',
//                items:materialGrid
//            }]
    	});
    }
});
