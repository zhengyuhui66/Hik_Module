

var panel=Ext.define('panel', {
    extend: 'Ext.Container',
    xtype: 'basic-panels',
    requires: [
               'Ext.selection.CellModel',
               'Ext.grid.*',
               'Ext.ux.ProgressBarPager',
               'Ext.data.*',
               'Ext.util.*',
               'Ext.form.*'
           ],
    layout: {
             type: 'hbox',
             padding:'5',
             align:'stretch'
    },
    tdAttrs: {style: 'padding: 10px;'},
    padding:'5 5 5 5',
    flex:1,
    minWidth:1000,
    maxWidth:1300,
    layout:{
		type:'hbox',
		align:'stretch',
		pack:'center'
	},
    initComponent: function () {
		    	/**
		    	 * 模型模型
		    	 */
		    	Ext.define('uUser',{
		    	    extend: 'Ext.data.Model',
		    	    fields: [
		    	        {name: 'id', type: 'string'},
		    	        {name: 'name', type: 'string'},
		    	        {name: 'creatime', type: 'string'},
		    	        {name: 'descr', type: 'string'}
		    	    ]
		    	});
		    	/**
		    	 * 查询手机信息
		    	 */
		    	var phoneStore=Ext.create('Ext.data.Store',{
		   		    model: uUser,
		   		    pageSize:defaultPageSize,
		   		    proxy: {
		   		    	type: 'ajax',
		   		    	url:rootUrl+'/pmangc/queryphone',
		   		        reader: {
		   		            type: 'json',
		   		            root: 'elementList',  
		   		            totalProperty: 'total'
		   		        }
		   		    }
		    	});
		    	phoneStore.loadPage(1);
		    	/**
		    	 * 查询手机分组信息
		    	 */
		    	var phonegroStore=Ext.create('Ext.data.Store',{
		   		    model: uUser,
		   		    pageSize:defaultPageSize,
		   		    proxy: {
		   		    	type: 'ajax',
		   		    	url:rootUrl+'/pmangc/querygphone',
		   		        reader: {
		   		            type: 'json',
		   		            root: 'elementList',  
		   		            totalProperty: 'total'
		   		        }
		   		    }
		    	});
		    	phonegroStore.loadPage(1);
//		    	===========操作按钮====================================================================
		    	/**
		    	 * 新增手机号面板form
		    	 */
		    	 var simple = Ext.widget({
		             xtype: 'form',
		             id: 'simpleForm',
		             fieldDefaults: {
		                 labelWidth : 60,
		                 labelAlign : 'right'
		             },
		             padding:'20 20 0 0',
		             border:false,
		             defaults: {
		                  anchor: '100%',
		             	  xtype: 'container',
		                  layout: 'hbox',
		                  border:1,
		                  defaultType: 'textfield',
		                  margin: '0 0 5 0'
		             },
		             items:[{
		                 	   xtype: 'textfield',
		 	                   fieldLabel: 'ID',
		 	                   hidden:true,
		 	                   name: 'id',
		 	                   id:'_id'
		 	               },{
		 	            	   xtype: 'textfield',
		 	                   fieldLabel: '手机号',
		 	                   allowBlank: false,
		 	                   name: 'name',
		 	                   id:'_name'
		 	               },{
		 	            	   xtype: 'textareafield',
		 	            	   height:80,
		 	                   fieldLabel: '描述',
		 	                   name: 'descr',
		 	                   id:'_descr'
		 	               }],
		 	               buttons: [{
		 			                text: '提交',
		 			                handler: function() {
		 			                	this.up('form').getForm().submit({
		 		                                clientValidation:true,
		 		                                waitMsg:'请稍候',
		 		                                waitTitle:'正在更新',
		 		                                url: rootUrl+'/pmangc/saveOrupdatephone',
		 		                                success:function(form,action){
		 		                                	if(action&&action.result&&action.result.success){
		 		                                		if(action.result.success=="true"){
		 		                                			phoneStore.reload();
		 		                                		}else if(action.result.success=="false"){
//		 		                                			Ext.Msg.alert('温馨提示',"上传失败!");
		 		                                			Ext.Msg.show({
				    		          	         				title : '温馨提示',
				    		          	         				msg :"上传失败!",
				    		          	         				width : 250,
				    		          	         				icon : Ext.Msg.ERROR,//INFO,ERROR,QUESTION,WARNING
				    		          	         				buttonText: { ok: '确定'}
				    		          	         			});
		 		                                		}
		 		                                	}else{
//		 		                                		Ext.Msg.alert('温馨提示',"上传失败!");
		 		                                		Ext.Msg.show({
			    		          	         				title : '温馨提示',
			    		          	         				msg :"上传失败!",
			    		          	         				width : 250,
			    		          	         				icon : Ext.Msg.ERROR,//INFO,ERROR,QUESTION,WARNING
			    		          	         				buttonText: { ok: '确定'}
			    		          	         			});
		 		                                	}
		 		                                	Ext.getCmp('simpleForm').getForm().reset();
		 		                                	win.close();
		 		                                },  
		 		                                failure:function(form,action){  
		 		                                	
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
		         * 新增的手机号面板
		         */
		        var win = Ext.create('widget.window', {
		                title: '新增',
		                header: {
		                    titlePosition: 2,
		                    titleAlign: 'center'
		                },
		                closable: false,
		                modal: true,
		                closeAction: 'hide',
		                width: 250,
		                minWidth: 350,
		                height: 250,
		                layout: {
		                    type: 'fit'
		                },
		                items:[simple]
		            });
		        /**
		         * 新增手机号按钮
		         */
		        var addAction = Ext.create('Ext.Action', {
		        	iconCls:'common_add',
		            text: '新增',
		            handler: function(widget, event) {
		            	win.show(this,function(){
		            		win.setTitle('新增手机号');
		            	});
		            }
		        });
		        /**
		         * 刷新按钮
		         */
		        var refreshAction = Ext.create('Ext.Action', {
		        	iconCls:'common_refresh',
		            text: '刷新',
		            handler: function(widget, event) {
		            	var searchStr  = Ext.getCmp('_searchCondiction').getValue();
		            	phoneStore.getProxy().extraParams = {searchStr:searchStr};
		            	phoneStore.loadPage(1);
		            }
		        });
		        
		        /**
		         * 删除手机号按钮
		         */
		        var deleteAction = Ext.create('Ext.Action', {
		        	iconCls:'common_del',
		            text: '删除',
		            handler: function(widget, event) {
		            	 var rec = phonegrid.getSelectionModel().getSelection();
		                 if(rec){
		                 	 if(rec.length==0){
//		                      	Ext.Msg.alert('温馨提示',"请先选中一条!");
		                 		Ext.Msg.show({
          	         				title : '温馨提示',
          	         				msg :"请先选中一条!",
          	         				width : 250,
          	         				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
          	         				buttonText: { ok: '确定'}
          	         			});
		                 	 }else if(rec.length>0){
		                 		 
		                 	   	Ext.Msg.show({
		                    	     title:'温馨提示',
		                     	     msg: '确定要删除吗?',
		                     	   buttonText: {yes:'是', no:'取消'},
		                     	     icon: Ext.Msg.QUESTION,
		                     	     fn:function(btn,txt){
		                     	    	 if(btn=="yes"){
		                     	    		var params=new Array();
		    		                    	for(var i=0;i<rec.length;i++){
		    		                    		params.push(rec[i].data.id);
		    		                    	}
		    		                    	Ext.Ajax.request({
		    		                    	    url:rootUrl+'/pmangc/deletephone',
		    		                    	    async: false,
		    		                    	    params: {
		    		            			        ids: params.toString()
		    		            			    },
		    		                    	    success: function(response){
		    		                    	    	var respText = Ext.JSON.decode(response.responseText);
//		    		            			    	Ext.Msg.alert('温馨提示',"成功删除了"+respText.data+"条记录");
		    		                    	    	if(respText.data>0){
			    		                    	    	Ext.Msg.show({
			    		          	         				title : '温馨提示',
			    		          	         				msg :"删除成功",
			    		          	         				width : 250,
			    		          	         				icon : Ext.Msg.INFO,//INFO,ERROR,QUESTION,WARNING
			    		          	         				buttonText: { ok: '确定'}
			    		          	         			});
			    		            			    	phoneStore.reload();
		    		                    	    	}else{
		    		                    	    		Ext.Msg.show({
			    		          	         				title : '温馨提示',
			    		          	         				msg :"删除失败",
			    		          	         				width : 250,
			    		          	         				icon : Ext.Msg.ERROR,//INFO,ERROR,QUESTION,WARNING
			    		          	         				buttonText: { ok: '确定'}
			    		          	         			});
		    		                    	    	}
		    		                    	    }
		    		                    	});
		                     	    	 }
		                     	     }
		                   		});
		                 	 }
		                 }
		            }
		        });
		        /**
		         * 更新手机号按钮
		         */
		        var updateAction = Ext.create('Ext.Action', {
		        	iconCls:'common_edit',
		            text: '更改',
		            handler: function(widget, event) {
		            	win.show(this,function(){
		            		win.setTitle('修改手机号');
		            		var rec = phonegrid.getSelectionModel().getSelection();
			                 if(rec){
			                 	 if(rec.length!=1){
//			                      	Ext.Msg.alert('温馨提示',"请先选中一条!");
			                 		Ext.Msg.show({
	          	         				title : '温馨提示',
	          	         				msg :"请先选中一条",
	          	         				width : 250,
	          	         				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
	          	         				buttonText: { ok: '确定'}
	          	         			});
			                 	 }else if(rec.length==1){
			                 		Ext.getCmp('_id').setValue(rec[0].data.id);
			                 		Ext.getCmp('_name').setValue(rec[0].data.name);
			                 		Ext.getCmp('_descr').setValue(rec[0].data.descr);
			                 	 }
			                 }
		            });
		          }
		        });
//		        =============================================================================================
		        /**
		         * 查所有不包含被分组的手机号
		         */
		        var _phoneStore=Ext.create('Ext.data.Store',{
		    		model:'uUser',
		    	    proxy: {
		    	        type: 'ajax',
		    	        url: rootUrl+'/pmangc/queryListphone',
		    	        reader: {
		    	            type: 'json',
		    	            root: 'data'
		    	        }
		    	    }
		    	}); 
		        var phoneGrid=Ext.create('Ext.grid.Panel', {
		            flex:1,
		            height:300,
		            store:_phoneStore,
		            columns: [
		                { text: 'id', hidden:true,  dataIndex: 'id' },
		                { text: '名称',flex:1, dataIndex: 'name'}
		            ]
		        });
		        
//		        =========================================================================
		        /**
		         * 查所有包含被分组的手机号
		         */
		        var _gphoneStore=Ext.create('Ext.data.Store',{
		    		model:'uUser',
		    	    proxy: {
		    	        type: 'ajax',
		    	        url: rootUrl+'/pmangc/queryListgphone',
		    	        reader: {
		    	            type: 'json',
		    	            root: 'data'
		    	        }
		    	    }
		    	});
		        var gphoneGrid=Ext.create('Ext.grid.Panel', {
		            flex:1,
		            height:300,
		            store:_gphoneStore,
		            columns: [
		                { text: 'id', hidden:true,  dataIndex: 'id' },
		                { text: '分组手机号名称',flex:1,  dataIndex: 'name'}
		            ]
		        });
		        /**
		         * 分组操作面板
		         */
		        var gpanel=Ext.create('Ext.form.Panel', {
		        	flex:1,
		        	id:'gpanelid',
		            layout:{
		            	type:'vbox',
		            	pack:'center',
		            	align:'stretch'
		            },
		            items:[{
		         	   xtype: 'textfield',
 	                   fieldLabel: '手机号分组名称',
 	                   allowBlank: false,
 	                   name: 'gphonename',
 	                   id:'_gphonename'
		            },{
		         	   xtype: 'textfield',
 	                   fieldLabel: '手机号分组ID',
 	                   hidden:true, 
 	                   name: 'gphoneid',
 	                   id:'_phoneid'
		            },{
		         	   xtype: 'textfield',
 	                   fieldLabel: '分组列表',
 	                   hidden:true,
 	                   name: 'phoneids',
 	                   id:'_phoneids'
		            },{
		            	xtype:'panel',
		            	flex:5,
		            	layout:{
			            	type:'hbox',
			            	pack:'center',
			            	align:'stretchmax'
			            },
			        	items:[phoneGrid,{
			        		xtype:'panel',
			            	layout:{
			            		type:'vbox',
				            	pack:'center',
				            	align:'stretchmax'
			            	},items:[{
			            		xtype:'button',
			            		text: '>>',
			            		handler: function(){
			            		     var rec = phoneGrid.getSelectionModel().getSelection();
					                 if(rec){
					                 	 if(rec.length==0){
//					                      	Ext.Msg.alert('温馨提示',"请先选中一条!");
					                 		Ext.Msg.show({
    		          	         				title : '温馨提示',
    		          	         				msg :"请先选中一条!",
    		          	         				width : 250,
    		          	         				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
    		          	         				buttonText: { ok: '确定'}
    		          	         			});
					                 	 }else if(rec.length>0){
					                 		for(var i=0;i<rec.length;i++){
					                 			_phoneStore.remove(Ext.create('uUser',rec[i].data));
					                 			_gphoneStore.add(Ext.create('uUser',rec[i].data));
		    		                    	}
					                 	 }
					                 }
			            		}
			            	},{
			            		xtype:'button',
			            		margin:'10 0 0 0',
			            		text: '<<',
			            		handler: function() {
		            			  var rec = gphoneGrid.getSelectionModel().getSelection();
				                  if(rec){
				                 	 if(rec.length==0){
//				                      	Ext.Msg.alert('温馨提示',"请先选中一条!");
				                 		Ext.Msg.show({
		          	         				title : '温馨提示',
		          	         				msg :"请先选中一条!",
		          	         				width : 250,
		          	         				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
		          	         				buttonText: { ok: '确定'}
		          	         			});
				                 	 }else if(rec.length>0){
				                 		for(var i=0;i<rec.length;i++){
				                 			_gphoneStore.remove(Ext.create('uUser',rec[i].data));
				                 			_phoneStore.add(Ext.create('uUser',rec[i].data));
	    		                    	}
				                 	 }
				                  }
			            		}
			            	}]
			        	},gphoneGrid]
		            },{
		            	flex:1,
		            	 xtype: 'textareafield',
	 	                   fieldLabel: '手机号分组描述',
	 	                   allowBlank: false,
	 	                   height:80,
	 	                   name: 'gphonedesc',
	 	                   id:'_gphonedesc'		            	
		            }],
		            buttons: [{
			                text: '提交',
 			                handler: function(){
 			                	var _gphoneList = new Array();
 			                	_gphoneStore.each(function(record) {
 			                		_gphoneList.push(record.get('id'));
		 			             });
 			                	Ext.getCmp('_phoneids').setValue(_gphoneList.toString());
 			                	gpanel.getForm().submit({
 		                                clientValidation:true,
 		                                waitMsg:'请稍候',
 		                                waitTitle:'正在更新',
 		                                url: rootUrl+'/pmangc/saveOrupdategphone',
 		                                success:function(form,action){
 		                                	if(action&&action.result&&action.result.success){
 		                                		if(action.result.success=="true"){
 		                                			phonegroStore.reload();
 		                                		}else if(action.result.success=="false"){
// 		                                			Ext.Msg.alert('温馨提示',"上传失败!");
 		                                			Ext.Msg.show({
		    		          	         				title : '温馨提示',
		    		          	         				msg :"上传失败!",
		    		          	         				width : 250,
		    		          	         				icon : Ext.Msg.ERROR,//INFO,ERROR,QUESTION,WARNING
		    		          	         				buttonText: { ok: '确定'}
		    		          	         			});
 		                                		}
 		                                	}else{
// 		                                		Ext.Msg.alert('温馨提示',"上传失败!");
 		                                		Ext.Msg.show({
	    		          	         				title : '温馨提示',
	    		          	         				msg :"上传失败!",
	    		          	         				width : 250,
	    		          	         				icon : Ext.Msg.ERROR,//INFO,ERROR,QUESTION,WARNING
	    		          	         				buttonText: { ok: '确定'}
	    		          	         			});
 		                                	}
 		                                	gpanel.getForm().reset();
 		                                	gwin.close();
 		                                },  
 		                                failure:function(form,action){  
 		                                	
 		                                }
 		                            });
 			                }
 			            },{
 			                text: '取消',
 			                handler: function(){
 			                	gpanel.getForm().reset();
 			                	gwin.close();
 			                }
 			            }]
		        });
		        
		        /**
		         * 手机号分组弹出窗的面板
		         */
		        var gwin = Ext.create('widget.window', {
		                title: '手机号码分组',
		                header: {
		                    titlePosition: 2,
		                    titleAlign: 'center'
		                },
		                closable: false,
		                modal: true,
		                closeAction: 'hide',
		                width: 450,
		                minWidth: 350,
		                height: 450,
		                layout: {
		                    type: 'fit'
		                },
		                items:[gpanel]
		            });
		        /**
		         * 新增分组按钮
		         */
		        var gaddAction = Ext.create('Ext.Action', {
		        	iconCls:'common_add',
		            text: '新增',
		            handler: function(widget, event) {
		            	gwin.show(null,function(){
		            		gwin.setTitle('新增分组');
		               		_phoneStore.getProxy().extraParams = {id:null};
		            		_gphoneStore.getProxy().extraParams = {id:null};
		            		_phoneStore.reload();   
		            		_gphoneStore.reload();
		            	});
		            }
		        });
		        /**
		         * 刷新分组按钮
		         */
		        var grefreshAction = Ext.create('Ext.Action', {
		        	iconCls:'common_refresh',
		            text: '刷新',
		            handler: function(widget, event) {
		            	var searchStr  = Ext.getCmp('_searchCondiction').getValue();
		            	phonegroStore.getProxy().extraParams = {searchStr:searchStr};
		            	phonegroStore.loadPage(1);
		            }
		        });
		        
		        /**
		         * 删除分组按钮
		         */
		        var gdeleteAction = Ext.create('Ext.Action', {
		        	iconCls:'common_del',
		            text: '删除',
		            handler: function(widget, event) {
		            	 var rec = phonegrogrid.getSelectionModel().getSelection();
		                 if(rec){
		                 	 if(rec.length==0){
//		                      	Ext.Msg.alert('温馨提示',"请先选中一条!");
		                 		Ext.Msg.show({
          	         				title : '温馨提示',
          	         				msg :"请先选中一条!",
          	         				width : 250,
          	         				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
          	         				buttonText: { ok: '确定'}
          	         			});
		                 	 }else if(rec.length>0){
		                    	Ext.Msg.show({
		                    	     title:'温馨提示',
		                     	     msg: '确定要删除吗?',
		                     	   buttonText: {yes:'是', no:'取消'},
		                     	     icon: Ext.Msg.QUESTION,
		                     	     fn:function(btn,txt){
		                     	    	 if(btn=="yes"){
		                     	    		var params=new Array();
		    		                    	for(var i=0;i<rec.length;i++){
		    		                    		params.push(rec[i].data.id);
		    		                    		
		    		                    	}
		    		                    	Ext.Ajax.request({
		    		                    	    url:rootUrl+'/pmangc/deletegphone',
		    		                    	    async: false,
		    		                    	    params: {
		    		            			        ids: params.toString()
		    		            			    },
		    		                    	    success: function(response){
		    		                    	    	var respText = Ext.JSON.decode(response.responseText);
//		    		            			    	Ext.Msg.alert('温馨提示',"成功删除了"+respText.data+"条记录");
		    		                    	    	if(respText.data>0){
			    		                    	    	Ext.Msg.show({
			    		          	         				title : '温馨提示',
			    		          	         				msg :"成功删除",
			    		          	         				width : 250,
			    		          	         				icon : Ext.Msg.INFO,//INFO,ERROR,QUESTION,WARNING
			    		          	         				buttonText: { ok: '确定'}
			    		          	         			});
		    		            			    	phonegroStore.reload();
		    		                    	    	}else{
		    		                    	    		Ext.Msg.show({
			    		          	         				title : '温馨提示',
			    		          	         				msg :"删除失败",
			    		          	         				width : 250,
			    		          	         				icon : Ext.Msg.ERROR,//INFO,ERROR,QUESTION,WARNING
			    		          	         				buttonText: { ok: '确定'}
			    		          	         			});
		    		                    	    	}
		    		                    	    }
		    		                    	});
		                     	    	 }
		                     	     }
		                   		});
		                 	 }
		                 }
		            }
		        });
		        /**
		         * 更改手机分组按钮
		         */
		        var gupdateAction = Ext.create('Ext.Action', {
		        	iconCls:'common_edit',
		            text: '更改',
		            handler: function(widget, event) {
		            	var rec=phonegrogrid.getSelectionModel().getSelection();
	            		
	            		if(rec.length!=1){
	            			Ext.Msg.alert('温馨提示',"请先选中一条!");
	            		}else{
	            			gwin.show(null,function(){
			            		gwin.setTitle('更改分组');
			            		var _id=rec[0].data.id;
			            		var _name=rec[0].data.name;
			            		var _descr = rec[0].data.descr;
			            		Ext.getCmp('_gphonename').setValue(_name);
			            		Ext.getCmp('_phoneid').setValue(_id);
			            		Ext.getCmp('_gphonedesc').setValue(_descr);
			            		     
			            		_phoneStore.getProxy().extraParams = {id:_id};
			            		_gphoneStore.getProxy().extraParams = {id:_id};
			            		_phoneStore.reload();   
			            		_gphoneStore.reload();
			            	});
	            		
	            		}
		            }
		        });
//		        =============================================================================================
		    	/**
		    	 * 手机全列
		    	 */
		    	var phonegrid= Ext.create('Ext.grid.Panel', {
		    	    store: phoneStore,
		    	    flex:1,
		    	    columns: [{text: "ID", flex: 1, sortable: false,hidden:true,  dataIndex: 'id',align:'center'},
		    	        {text: "手机号", flex: 1, sortable: false, dataIndex: 'name',align:'center'},
		    	        {text: "创建时间", flex: 1, sortable:false, dataIndex: 'creatime',align:'center'},
		    	        {text: "描述", flex: 1, sortable:false, dataIndex: 'descr',align:'center'}],
			    	bbar: {
			            xtype: 'pagingtoolbar',
			            pageSize:defaultPageSize,
			            store: phoneStore,
			            nextText:'下一页',
			            prevText:'上一页',
			            firstText:'第一页',
			            lastText:'最后一页',
			            refreshText:'刷新',
			            displayInfo: true,
			            displayMsg: '显示{0}-{1}条，共{2}条',
			            plugins: new Ext.ux.ProgressBarPager()
			        },
		            tbar: [addAction,updateAction,deleteAction,refreshAction,'->',
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
				        		   phoneStore.getProxy().extraParams = {searchStr:searchStr};
				        		   phoneStore.loadPage(1);
				        	    }
				           }]
		    	});
		    	
		    	/**
		    	 * 手机分组全列
		    	 */
		    	var phonegrogrid= Ext.create('Ext.grid.Panel', {
		    	    store: phonegroStore,
		    	    flex:1,
		    	    columns: [{text: "ID", flex: 1, sortable: false,hidden:true,  dataIndex: 'id',align:'center'},
		    	        {text: "手机分组名称", flex: 1, sortable: false, dataIndex: 'name',align:'center'},
		    	        {text: "创建时间", flex: 1, sortable:false, dataIndex: 'creatime',align:'center'},
		    	        {text: "描述", flex: 1, sortable:false, dataIndex: 'descr',align:'center'}],
			        bbar: {
		                xtype: 'pagingtoolbar',
		                pageSize:defaultPageSize,
		                store: phonegroStore,
		                nextText:'下一页',
		                prevText:'上一页',
		                firstText:'第一页',
		                lastText:'最后一页',
		                refreshText:'刷新',
		                displayInfo: true,
		                displayMsg: '显示{0}-{1}条，共{2}条',
		                plugins: new Ext.ux.ProgressBarPager()
		            },
		            tbar: [gaddAction,gupdateAction,gdeleteAction,grefreshAction,'->',
		                     {
				        		xtype:'textfield',
				        		emptyText: '请输入需要搜索的信息',
				        		width:200,
				                name: 'gsearchCondiction',
				                id: '_gsearchCondiction'
				           },{
				        	   xtype:'button',
				        	   text: '搜索',
				        	   iconCls:'common_search',
				        	   handler: function() {
				        		   var searchStr  = Ext.getCmp('_gsearchCondiction').getValue();
				        		   phonegroStore.getProxy().extraParams = {searchStr:searchStr};
				        		   phonegroStore.loadPage(1);
				        	    }
				           }]
		    	});
		        this.items = [phonegrid,phonegrogrid];
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
				items:panel
			}]
    	});
    }
});

