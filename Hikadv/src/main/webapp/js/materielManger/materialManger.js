//	/**
//	 * 高级管理员ID
//	 */
//	var SENIOR_ID="1E86A11BB795485D994A62F912FC3B1B";
//	/**
//	 * 超级管理员  
//	 */
//	var HYPER_ID="26141179392546AEB38B784C465BE226";
	addOrUpdateUrL= rootUrl+'/getAdvertController/updateAdvert';
var materialGrid=Ext.define('KitchenSink.view.tree.TreeGrid',{
    extend: 'Ext.grid.Panel',
    requires:[
        'Ext.data.*',
        'Ext.grid.*',
        'Ext.util.*',
        'Ext.toolbar.Paging',
        'Ext.ux.ProgressBarPager',
        'Ext.selection.CheckboxModel'
    ],
    xtype: 'material-grid',
	padding:'5 5 5 5',
    initComponent: function() {
    	 var showAuditProce=[];
    	 var mater_path="";
     	
     	/**
     	 * 物料查看
     	 */
         var materialInfo = Ext.create('Ext.form.Panel', {
             plain: true,
             border: 0,
             bodyPadding: 5,
             flex: 1,
             overflowY:'auto',
             fieldDefaults: {
                 labelWidth: 60,
                 anchor: '100%'
             },
             layout: {
                 type: 'vbox',
                 align: 'stretch'
             },
             defaults: {
                 anchor: '100%',
            	 xtype: 'container',
                 layout: 'hbox',
                 
                 border:1,
                 defaultType: 'displayfield',
                 margin: '0 0 5 0'
             },
             items:[{
                 items:[{
                	 width:250,
	                   fieldLabel: '物料名称',
	                   name: 'mater_name',
	                   id:'id_mater_name'
	               },{
	            	   width:250,
	                   fieldLabel: '上传用户',
	                   name: 'user_id',
	                   id:'id_userid'
	               }]
             },{
                 items:[{
                	 width:250,
	                   fieldLabel: '物料类型',
	                   name: 'mater_type',
	                   id:'id_mater_type'
	               },{
	            	   width:250,
	                   fieldLabel: '物料大小',
	                   name: 'mater_size',
	                   id:'id_mater_size'
	               }]
             },{
                 items:[{
                	 width:250,
	                   fieldLabel: '物料高度',
	                   name: 'mater_height',
	                   id:'id_mater_height'
	               },{
	            	   width:250,
	                   fieldLabel: '物料宽度',
	                   name: 'mater_width',
	                   id:'id_mater_width'
	               }]
             },{              
            	 xtype:'tbspacer',
                 flex:1
             },{
            	 xtype:'panel',
            	 layout:'fit',
            	 margin:0,
            	 html:'<div id="audiePanel" style="height:130px;">'+
	            	'<table border="1" width="100%" height="100%">'+
		            	'<tr>'+
				            '<td>暂无数据</td>'+
				            '<td>暂无数据</td>'+
			            '</tr>'+
		            '</table>'+
		          '<div>'
		      }]
         });
         
         
//         var filterPanel = Ext.create('Ext.panel.Panel',{
//             width: 380,
//             flex: 1,
//             layout:{
//             	type:'hbox',
//             	align: 'middle',
//             	pack: 'center'
//             },
//             autoScroll:true,
//    	 	 overflowX:true,
//             overflowY:true,
//             html:'<div style="min-width:355px;max-width:380px;height:260px;overflow:auto"><img id="materShow" src="" onerror="this.src=\''+rootUrl+"/images/default/default.jpg"+'\'"></img></div>'
//            
//         });
         
    	/**
    	 * 菜单模型
    	 */
    	Ext.define('Ext.menu.model', {
    	    extend: 'Ext.data.Model',
    	    fields: [{
    	        name: 'mater_id',
    	        type: 'string'
    	    },{
    	        name: 'custom_id',
    	        type: 'string'
    	    },{
    	        name: 'username',
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
    	    },{
    	        name: 'create_time',
    	        type: 'string'
    	    },{
    	        name: 'update_time',
    	        type: 'string'
    	    },{
    	        name: 'mater_path',
    	        type: 'string'
    	    },{
    	        name: 'mater_state',
    	        type: 'string'
    	    },{
    	        name: 'mater_width',
    	        type: 'string'
    	    },{
    	        name: 'mater_height',
    	        type: 'string'
    	    },{
    	        name: 'trid',
    	        type: 'string'
    	    }]
    	}); 
    	
    	
    	/**
    	 * 物料展示加载的STORE
    	 */
    	var materialStore=Ext.create('Ext.data.Store', {
    	    storeId:'simpsonsStore',
    	    model: Ext.menu.model,
    	    pageSize:defaultPageSize,
    	    proxy: {
    	    	type: 'ajax',
    	    	url:rootUrl+'/materialController/queryMateriel',
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
    	var material = Ext.create('Ext.ux.uploadPanel.UploadPanel',{ 
    	    id:"material",
    	    addFileBtnText : '选择文件...',  
    	    uploadBtnText : '上传',  
    	    removeBtnText : '移除所有',  
    	    cancelBtnText : '取消上传',
    	    file_size_limit : 1,//MB
    	    restore:materialStore,
    	    upload_url:rootUrl+'/materialController/uploadMateriel',
            buttons: [{
                text: '关闭',
                handler: function(){
                	Ext.getCmp('material').onRemove();
                	 win.close();
                }
            	}]
    	});  
        /**
         * 新增或更新弹出的面板
         */
        var win = Ext.create('widget.window', {
                title: '新增物料',
                header: {
                    titlePosition: 2,
                    titleAlign: 'center'
                },
                closable: false,
                modal: true,
                closeAction: 'hide',
                width: 480,
                minWidth: 550,
                height: 450,
                layout: {
                    type: 'border'
                },
                items: {
                    region: 'center',
                    xtype: 'panel',
                    layout:'fit',
                    items:material
                }
            });
        
        /**
         * 刷新按钮
         */
        var refreshAction = Ext.create('Ext.Action', {
        	iconCls:'common_refresh',
            text: '刷新',
            handler: function(widget, event) {
            	 materialStore.reload();
            }
        });
        /**
         * 物料信息展示的面板
         */
        var winAudit = Ext.create('widget.window', {
                title: '物料信息展示',
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
//                    layout:{
//                    	type:'hbox',
//                    	align: 'stretch'
//                    },
                    items:[{
								xtype: 'fieldset',
								title: '基本信息',
								margin:5,
								stateId:'baseinfo',
								layout:'fit',
								width:600,
	                            items:materialInfo
                            }, {
//								xtype: 'fieldset',
								title: '物料展示',
								stateId:'materinfo',
//					             html:'<div style="min-width:355px;max-width:380px;height:260px;overflow:auto"><img id="materShow" src="" onerror="this.src=\''+rootUrl+"/images/default/default.jpg"+'\'"></img></div>'
								html:'<div style="width:100%;height:100%;overflow:auto;padding:10px;"><img id="materShow" src="" onerror="this.src=\''+rootUrl+"/images/default/default.jpg"+'\'"></img></div>'
//								margin:5,
//								layout:'fit',
//	                            items:filterPanel
                            }],
                      listeners:{
                    	  'tabchange':function(me, eOpts){
                    		  if(eOpts.stateId=='materinfo'){
                    			  $("#materShow").attr('src',mater_path);
                    		  }
                    	  }
                      }
                }
            });
        /**
         * 新增按钮 (新增)
         */
        var addAction = Ext.create('Ext.Action', {
        	iconCls:'common_add',
            text: '新增',
            handler: function(widget, event) {
            	
            	
//            	Ext.getCmp("material").getForm().reset();
            	win.show(this,function(){
            		addOrUpdateUrL=rootUrl+'/materialController/uploadMateriel';
    			});
            }
        });
        
//        /**
//         * 查看物料图片
//         */
//        var showAction = Ext.create('Ext.Action', {
//        	iconCls:'common_add',
//            text: '查看',
//            handler: function(widget, event) {
//            }
//        });
        
//        /**
//         * 更新按钮 (更新)
//         */
//        var updataAction = Ext.create('Ext.Action', {
//        	iconCls:'common_edit',
//            text: '编辑',
//            handler: function(widget, event){
//            	 var rec = me.getSelectionModel().getSelection();
//                 if(rec){
//                 	 if(rec.length==0){
//                      	Ext.Msg.alert('温馨提示',"请先选中一条!");
//                 	 }else if(rec.length>1){
//                 		 Ext.Msg.alert('温馨提示',"您选中了多条记录!</br>编辑只能选择一条");
//                 	 }else{
//                 		 var tempdata=rec[0].data;
//                 		 if(tempdata.mater_state=='审核已全部通过'){
//                 			 Ext.Msg.alert('温馨提示',"审核已全部通过</br>无法更改");
//                 			 return;
//                 		 }else if(tempdata.mater_state=='已删除'){
//                 			Ext.Msg.alert('温馨提示',"已删除</br>无法更改");
//                 			return;
//                 		 }
//                 			win.show(this,function(){
//                 				addOrUpdateUrL= rootUrl+'/materialController/updateMateriel';
//                 				win.setTitle("更新物料");
//                 				Ext.getCmp("_materId").setValue(tempdata.mater_id);
//                 			});
//                 	 }
//                 }
//            }
//        });
        
        var me=this;
        
        var inParentId=function(_userId,userIds){
        	if(!userIds){
        		return false;
        	}
        	for(var i=0;i<userIds.length;i++){
        		if(_userId==userIds[i].userId){
        			return true;
        		}
        	}
        	return false
        }
        var deleteAction = Ext.create('Ext.Action', {
        	iconCls:'common_del',
        	
            text: '删除',
            handler: function(widget, event){
            	 var rec = me.getSelectionModel().getSelection();
                 if(rec.length==0){
//                 	Ext.Msg.alert('温馨提示',"请先选中一条!");
                	 Ext.Msg.show({
          				title : '温馨提示',
          				msg :"请先选中一条!",
          				width : 250,
          				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
          				buttonText: { ok: '确定'}
          			});
                 }else if(rec.length>=1){
                	var userIds=getSubUser();
                 	var params=new Array();
                 	var fileNames = new Array();
                 	for(var i=0;i<rec.length;i++){
                 		if(inParentId(rec[i].data.custom_id,userIds)){
                 				
//                 			if(rec[i].data.mater_state=='审核已全部通过'){
//                    			 Ext.Msg.alert('温馨提示',"审核已全部通过</br>无法删除");
//                    			 continue;
//                    		 }
                 			params.push(rec[i].data.mater_id);
                 			fileNames.push(rec[i].data.mater_name);
                 		}else{ 
//                 			Ext.Msg.alert('温馨提示',"用户"+rec[i].data.custom_id+"无权删除操作");
                 			Ext.Msg.show({
                  				title : '温馨提示',
                  				msg :"用户"+rec[i].data.custom_id+"无权删除操作",
                  				width : 250,
                  				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
                  				buttonText: { ok: '确定'}
                  			});
                 		}
                 	}
                 	if(params==null||params.length==0){
                 		return ;
                 	}
                 	
                 	Ext.Ajax.request({
       				    url: rootUrl+'/getAdvertController/getAdvBymaterId',
       				    params: { 
       				    	materId:params.toString(),
       				    },
       				    success: function(response){
       				    	var text = response.responseText;
      	    		        var _respText = Ext.JSON.decode(text);
      	    		        if(_respText.data[0].count!=0){
//      	    		        	Ext.Msg.alert('温馨提示',"已制成广告，无法删除");
      	    		        	Ext.Msg.show({
                      				title : '温馨提示',
                      				msg :"已制成广告，无法删除",
                      				width : 250,
                      				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
                      				buttonText: { ok: '确定'}
                      			});
      	    		        	return;
      	    		        }else{
      	    		        	Ext.Msg.show({
      	                  	     title:'温馨提示',
      	                   	     msg: '确定要删除吗?',
      	                   	   buttonText: {yes:'是', no:'取消'},
      	                   	     icon: Ext.Msg.QUESTION,
      	                   	     fn:function(btn,txt){
      	                   	    	 if(btn=="yes"){
      	                   	    		Ext.Ajax.request({
      	                    				    url: rootUrl+'/materialController/deleteMaterInfo',
      	                    				    params: { 
      	                    				    	materId:params.toString(),
      	                    				    	fileNames:fileNames.toString()
      	                    				    },
      	                    				    success: function(response){
      	                    				    	var text = response.responseText;
      	                   	    		        var _respText = Ext.JSON.decode(text);
//      	                   	    		        Ext.Msg.alert('温馨提示',"共删除了"+_respText.data+"记录");
		      	                   	    		   Ext.Msg.show({
		      	                       				title : '温馨提示',
		      	                       				msg :"共删除了"+_respText.data+"记录",
		      	                       				width : 250,
		      	                       				icon : Ext.Msg.INFO,//INFO,ERROR,QUESTION,WARNING
		      	                       				buttonText: { ok: '确定'}
		      	                       			});
      	                   	    		        materialStore.reload();
      	                    				    }
      	                    				});
      	                   	    		
      	                   	    	 }
      	                   	     }
      	                 		});
      	    		        }
       				    }
       				});
                 }
            }
        });        
        
    	/**
    	 *物料展示的表格
    	 */
        Ext.apply(this, {
        	store:materialStore,
        		columnLines:true,
        		rowLines:true,
        		flex:1,
        		minWidth:900,
        		maxWidth:1300,
        	    columns: [{
        	        text: '物料ID',
        	        id:'mater_id',
        	        hidden:true,
        	        dataIndex: 'mater_id'
        	    },{ 
        	    	text: '上传用户ID',
        	        id:'custom_id',
        	        hidden:true,
        	        dataIndex: 'custom_id'
        	    },{ 
        	    	text: '上传用户',
        	        flex: 1,
        	        align:'center',
        	        sortable: true,
        	        id:'username',
        	        dataIndex: 'username'
        	    },{
        	        text: '物料名称',
        	        flex: 1,
        	        align:'center',
        	        dataIndex: 'mater_name',
        	        sortable: true
        	    },{
        		   text: '物料大小(字节)',
        	       hidden:true,
        	       
        	       dataIndex: 'mater_size'
        	   },{
        	        text: '物料类型',
        	        hidden:true,
        	        dataIndex: 'mater_type'
        	    },{
        	        text: '物料生成时间',
        	        flex: 1,
        	        align:'center',
        	        dataIndex: 'create_time',
        	        sortable: true
        	    },{
        	        text: '物料更新时间',
        	        hidden:true,
        	        dataIndex: 'update_time'
        	    },{
        	        text: '物料存储路径',
        	        flex: 1,
        	        align:'center',
        	        dataIndex: 'mater_path',
        	        sortable: true
        	    },{
        	        text: '物料状态',
        	        flex: 1,
        	        align:'center',
        	        dataIndex: 'mater_state',
        	        sortable: true
        	    },{
        	        text: '物料高度(像素)',
        	        hidden:true,
        	        dataIndex: 'mater_height'
        	    },{
        	        text: '物料宽度(像素)',
        	        hidden:true,
        	        dataIndex: 'mater_width'
        	    },{
        	        text: '角色ID',
        	        hidden:true,
        	        dataIndex: 'trid'
        	    },{
          	    	 xtype:'actioncolumn',
        	    	 text: '查看审核状态', 
        	    	 align:'center',
        	    	 flex:1,
        	    	 items:[{
                         getClass: function(v, meta, rec) {
                                 return 'auditicons_check';
                         },
                         handler: function(grid, rowIndex, colIndex) {
                        	 var rec = grid.getStore().getAt(rowIndex); 
                        	 var materId=rec.get('mater_id');
                        	 var mater_name=rec.get('mater_name');
                        	 var username=rec.get('username');
                			 var mater_type=rec.get('mater_type');
                			 var mater_size=rec.get('mater_size');
//                			 mater_path=rootUrl+"/"+rec.get('mater_path');
                			 var _msg=rec.get('mater_path');
                			 mater_path=_msg.indexOf("load/")>0?rootUrl+"/"+_msg:httpUrlResources+_msg;
                			 var mater_width=rec.get('mater_width');
                			 var mater_height= rec.get('mater_height');
                			 
         	    		      winAudit.show(this,function(){
	         	    		    	Ext.getCmp("id_mater_name").setValue(mater_name);   
	         	    		    	Ext.getCmp("id_userid").setValue(username);
	         	    		    	Ext.getCmp("id_mater_type").setValue(mater_type);
	         	    		    	Ext.getCmp("id_mater_size").setValue(mater_size+'Byte');
	         	    		    	Ext.getCmp("id_mater_width").setValue(mater_width);
	         	    		    	Ext.getCmp("id_mater_height").setValue(mater_height);
	         	    		    	
	         	    		    		$("#materShow").attr('src',mater_path);		    		

            	    		    	$("#audiePanel").parent().css('height','100%');
	                               	 Ext.Ajax.request({
		               				    url: rootUrl+'/materialController/getAuditInfo',
		               				    params: { 
		               				    	materId:materId
		               				    },
		               				    success: function(response){
		               				    	var text = response.responseText;
		              	    		        var _respText = Ext.JSON.decode(text);
		              	    		        var respText=_respText.data;
		              	    		        if(respText.length==0){
		              	    		        	$("#audiePanel").html("暂无数据");
		              	    		        	return;
		              	    		        }
		              	    		        var result = '<table  width="100%" height="100%"><tr>';
		              	    		        for(var i=0;i<respText.length;i++){//audit_user  audit_telphone
		              	    		        	result+='<td>流程名:'+respText[i].name+
		              	    		        	'</br>审核状态:'+respText[i].audit_state+
		              	    		        	'</br>审核结果:'+respText[i].audit_result+
		              	    		        	'</br>审核意见:'+(respText[i].audit_desc?respText[i].audit_desc:'')+
		              	    		        	'</br>审核时间:'+(respText[i].audit_stime?respText[i].audit_stime:'')+
		              	    		        	'</br>审核人:'+(respText[i].audit_user?respText[i].audit_user:'')+
		              	    		        	'</br>联系方式:'+(respText[i].audit_telphone?respText[i].audit_telphone:'')+
		              	    		        	'</td>';
		              	    		        }
		              	    		        result+='</tr></table>';
		              	    		        $("#audiePanel").html(result);
		               				    }
		               				});
                           	 });
                         }
                     }]
            	    }],bbar: {
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
        	    selModel:Ext.create('Ext.selection.CheckboxModel',{mode:"MULTI",width : 222,header : "a"}),
        	    tbar: [addAction,/*updataAction,*/deleteAction,refreshAction,'->', {
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
    	});
    }
});