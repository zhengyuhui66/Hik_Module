

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
		    	 * 模型模型
		    	 */
		    	Ext.define('xUser',{
		    	    extend: 'Ext.data.Model',
		    	    fields: [
		    	        {name: 'id', type: 'string'},
		    	        {name: 'name', type: 'string'},
		    	        {name: 'stime', type: 'string'},
		    	        {name: 'sdtime', type: 'string'},
		    	        {name: 'etime', type: 'string'},
		    	        {name: 'edtime', type: 'string'},
		    	        {name: 'creatime', type: 'string'},
		    	        {name: 'descr', type: 'string'}
		    	    ]
		    	});
		    	
		    	/**
		    	 * 查询终端系统信息
		    	 */
		    	var timeStore=Ext.create('Ext.data.Store',{
		   		    model: xUser,
		   		    pageSize:defaultPageSize,
		   		    proxy: {
		   		    	type: 'ajax',
		   		    	url:rootUrl+'/time/querytime',
		   		        reader: {
		   		            type: 'json',
		   		            root: 'elementList',  
		   		            totalProperty: 'total'
		   		        }
		   		    }
		    	});
		    	timeStore.loadPage(1);
		    	/**
		    	 * 查询终端系统分组信息
		    	 */
		    	var timegroStore=Ext.create('Ext.data.Store',{
		   		    model: uUser,
		   		    pageSize:defaultPageSize,
		   		    proxy: {
		   		    	type: 'ajax',
		   		    	url:rootUrl+'/time/querygtime',
		   		        reader: {
		   		            type: 'json',
		   		            root: 'elementList',  
		   		            totalProperty: 'total'
		   		        }
		   		    }
		    	});
		    	timegroStore.loadPage(1);
//		    	===========操作按钮====================================================================
		    	/**
		    	 * 新增终端系统面板form
		    	 */
		        var stime=new Date();
		        stime.setDate(stime.getDate()-7);
		        var etime=new Date();
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
		             			layout:{
		             				type:'hbox',
		             				pack:'center',
		             				align:'middle'
		             			},
		             			items:[{
									fieldLabel : '开始时间',
									xtype : 'datefield',
									flex:3,
									name : 'stime',
									id : '_stime',
									format:'Y-m-d',
									itemId : '_stime',
									allowBlank: false,
									minValue:new Date(),
									value:stime,
									listeners:{
										change:function(me, newValue, oldValue, eOpts){
											 var sDate=Ext.getCmp('_stime').rawValue;
											 var _st = new Date(sDate.replace(/-/g, "/"));
											 _st.setDate(_st.getDate()+7);
											 _st.format('MM/dd/yyyy');
											Ext.getCmp('_etime').setValue(_st);
										}
									}
		             			},{
									xtype : 'timefield',
									flex:2,
									name : 'sdtime',
									allowBlank: false,
									id : '_sdtime',
									format:'H:m:i',
									itemId : '_sdtime'
		             			}]
								}, {
									layout:{
			             				type:'hbox',
			             				pack:'center',
			             				align:'middle'
			             			},
			             			items:[{
										fieldLabel : '结束时间',
										xtype : 'datefield',
										flex:3,
										allowBlank: false,
										format:'Y-m-d',
										name : 'etime',
										id : '_etime',
										itemId : '_etime',
										startDateField: '_stime',
										value:etime,
										listeners:{
											change:function(me, newValue, oldValue, eOpts){
												 var sDate=Ext.getCmp('_etime').rawValue;
												 var _st = new Date(sDate.replace(/-/g, "/"));
												 _st.setDate(_st.getDate()-7);
												 _st.format('MM/dd/yyyy');
												Ext.getCmp('_stime').setValue(_st);
											}
										}
			             			},{
										xtype : 'timefield',
										flex:2,
										name : 'edtime',
										allowBlank: false,
										id : '_edtime',
										format:'H:m:i',
										itemId : '_edtime'
									}]
								}
		 	               ,{
		 	            	   xtype: 'textfield',
		 	                   fieldLabel: '名称',
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
		 		                                url: rootUrl+'/time/saveOrupdatetime',
		 		                                success:function(form,action){
		 		                                	if(action&&action.result&&action.result.success){
		 		                                		if(action.result.success=="true"){
		 		                                			timeStore.reload();
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
		         * 新增的时间面板
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
		                height: 300,
		                layout: {
		                    type: 'fit'
		                },
		                items:[simple]
		            });
		        /**
		         * 新增时间按钮
		         */
		        var addAction = Ext.create('Ext.Action', {
		        	iconCls:'common_add',
		            text: '新增',
		            handler: function(widget, event) {
		            	win.show(this,function(){
		            		win.setTitle('新增时间');
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
		            	timeStore.getProxy().extraParams = {searchStr:searchStr};
		            	timeStore.loadPage(1);
		            }
		        });
		        
		        /**
		         * 删除时间按钮
		         */
		        var deleteAction = Ext.create('Ext.Action', {
		        	iconCls:'common_del',
		            text: '删除',
		            handler: function(widget, event) {
		            	 var rec = timegrid.getSelectionModel().getSelection();
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
		    		                    	    url:rootUrl+'/time/deletetime',
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
			    		            			    	timeStore.reload();
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
		         * 更新时间按钮
		         */
		        var updateAction = Ext.create('Ext.Action', {
		        	iconCls:'common_edit',
		            text: '更改',
		            handler: function(widget, event) {
		            	win.show(this,function(){
		            		win.setTitle('修改时间');
		            		var rec = timegrid.getSelectionModel().getSelection();
			                 if(rec){
			                 	 if(rec.length!=1){
//			                      	Ext.Msg.alert('温馨提示',"请先选中一条!");
			                 		Ext.Msg.show({
			  	         				title : '温馨提示',
			  	         				msg :"请先选中一条!",
			  	         				width : 250,
			  	         				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
			  	         				buttonText: { ok: '确定'}
			  	         			});
			                 	 }else if(rec.length==1){
			                 		Ext.getCmp('_id').setValue(rec[0].data.id);
			                 		Ext.getCmp('_name').setValue(rec[0].data.name);
			                 		Ext.getCmp('_descr').setValue(rec[0].data.descr);
			                 		Ext.getCmp('_stime').setValue(rec[0].data.stime);
			                 		Ext.getCmp('_sdtime').setValue(rec[0].data.sdtime);
			                 		Ext.getCmp('_etime').setValue(rec[0].data.etime);
			                 		Ext.getCmp('_edtime').setValue(rec[0].data.edtime);
			                 	 }
			                 }
		            });
		          }
		        });
//		        =============================================================================================
		        /**
		         * 查所有不包含被分组的时间
		         */
		        var _timeStore=Ext.create('Ext.data.Store',{
		    		model:'uUser',
		    	    proxy: {
		    	        type: 'ajax',
		    	        url: rootUrl+'/time/queryListtime',
		    	        reader: {
		    	            type: 'json',
		    	            root: 'data'
		    	        }
		    	    }
		    	}); 
		        var timeGrid=Ext.create('Ext.grid.Panel', {
		            flex:1,
		            height:300,
		            store:_timeStore,
		            columns: [
		                { text: 'id', hidden:true,  dataIndex: 'id' },
		                { text: '名称',flex:1, dataIndex: 'name'}
		            ]
		        });
		        
//		        =========================================================================
		        /**
		         * 查所有包含被分组的时间
		         */
		        var _gtimeStore=Ext.create('Ext.data.Store',{
		    		model:'uUser',
		    	    proxy: {
		    	        type: 'ajax',
		    	        url: rootUrl+'/time/queryListgtime',
		    	        reader: {
		    	            type: 'json',
		    	            root: 'data'
		    	        }
		    	    }
		    	});
		        var gtimeGrid=Ext.create('Ext.grid.Panel', {
		            flex:1,
		            height:300,
		            store:_gtimeStore,
		            columns: [
		                { text: 'id', hidden:true,  dataIndex: 'id' },
		                { text: '分组时间名称',flex:1,  dataIndex: 'name'}
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
 	                   fieldLabel: '时间分组名称',
 	                   allowBlank: false,
 	                   name: 'gtimename',
 	                   id:'_gtimename'
		            },{
		         	   xtype: 'textfield',
 	                   fieldLabel: '时间分组ID',
 	                   hidden:true, 
 	                   name: 'gtimeid',
 	                   id:'_timeid'
		            },{
		         	   xtype: 'textfield',
 	                   fieldLabel: '分组列表',
 	                   hidden:true,
 	                   name: 'timeids',
 	                   id:'_timeids'
		            },{
		            	xtype:'panel',
		            	flex:5,
		            	layout:{
			            	type:'hbox',
			            	pack:'center',
			            	align:'stretchmax'
			            },
			        	items:[timeGrid,{
			        		xtype:'panel',
			            	layout:{
			            		type:'vbox',
				            	pack:'center',
				            	align:'stretchmax'
			            	},items:[{
			            		xtype:'button',
			            		text: '>>',
			            		handler: function(){ 
			            		     var rec = timeGrid.getSelectionModel().getSelection();
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
					                 			_timeStore.remove(Ext.create('uUser',rec[i].data));
					                 			_gtimeStore.add(Ext.create('uUser',rec[i].data));
		    		                    	}
					                 	 }
					                 }
			            		}
			            	},{
			            		xtype:'button',
			            		margin:'10 0 0 0',
			            		text: '<<',
			            		handler: function() {
		            			  var rec = gtimeGrid.getSelectionModel().getSelection();
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
				                 			_gtimeStore.remove(Ext.create('uUser',rec[i].data));
				                 			_timeStore.add(Ext.create('uUser',rec[i].data));
	    		                    	}
				                 	 }
				                  }
			            		}
			            	}]
			        	},gtimeGrid]
		            },{
		            	flex:1,
		            	xtype: 'textareafield',
 	                    fieldLabel: '时间分组描述',
 	                    allowBlank: false,
 	                    height:80,
 	                    name: 'gtimedesc',
 	                    id:'_gtimedesc'		            	
		            }],
		            buttons: [{
			                text: '提交',
 			                handler: function(){
 			                	var _gtimeList = new Array();
 			                	_gtimeStore.each(function(record) {
 			                		_gtimeList.push(record.get('id'));
		 			             });
 			                	Ext.getCmp('_timeids').setValue(_gtimeList.toString());
 			                	gpanel.getForm().submit({
 		                                clientValidation:true,
 		                                waitMsg:'请稍候',
 		                                waitTitle:'正在更新',
 		                                url: rootUrl+'/time/saveOrupdategtime',
 		                                success:function(form,action){
 		                                	if(action&&action.result&&action.result.success){
 		                                		if(action.result.success=="true"){
 		                                			timegroStore.reload();
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
		         * 时间分组弹出窗的面板
		         */
		        var gwin = Ext.create('widget.window', {
		                title: '时间码分组',
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
		               		_timeStore.getProxy().extraParams = {id:null};
		            		_gtimeStore.getProxy().extraParams = {id:null};
		            		_timeStore.reload();   
		            		_gtimeStore.reload();
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
		            	timegroStore.getProxy().extraParams = {searchStr:searchStr};
		            	timegroStore.loadPage(1);
		            }
		        });
		        
		        /**
		         * 删除分组按钮
		         */
		        var gdeleteAction = Ext.create('Ext.Action', {
		        	iconCls:'common_del',
		            text: '删除',
		            handler: function(widget, event) {
		            	 var rec = timegrogrid.getSelectionModel().getSelection();
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
		    		                    	    url:rootUrl+'/time/deletegtime',
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
			    		          	         				msg :"成功删除了",
			    		          	         				width : 250,
			    		          	         				icon : Ext.Msg.INFO,//INFO,ERROR,QUESTION,WARNING
			    		          	         				buttonText: { ok: '确定'}
			    		          	         			});
			    		            			    	timegroStore.reload();
		    		                    	    	}else{
		    		                    	    		Ext.Msg.show({
			    		          	         				title : '温馨提示',
			    		          	         				msg :"删除失败",
			    		          	         				width : 250,
			    		          	         				icon : Ext.Msg.INFO,//INFO,ERROR,QUESTION,WARNING
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
		         * 更改时间分组按钮
		         */
		        var gupdateAction = Ext.create('Ext.Action', {
		        	iconCls:'common_edit',
		            text: '更改',
		            handler: function(widget, event) {
		            	var rec=timegrogrid.getSelectionModel().getSelection();
	            		
	            		if(rec.length!=1){
//	            			Ext.Msg.alert('温馨提示',"请先选中一条!");
	            			Ext.Msg.show({
      	         				title : '温馨提示',
      	         				msg :"请先选中一条!",
      	         				width : 250,
      	         				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
      	         				buttonText: { ok: '确定'}
      	         			});
	            		}else{
	            			gwin.show(null,function(){
			            		gwin.setTitle('更改分组');
			            		var _id=rec[0].data.id;
			            		var _name=rec[0].data.name;
			            		var _descr = rec[0].data.descr;
			            		Ext.getCmp('_gtimename').setValue(_name);
			            		Ext.getCmp('_timeid').setValue(_id);
			            		Ext.getCmp('_gtimedesc').setValue(_descr);
			            		     
			            		_timeStore.getProxy().extraParams = {id:_id};
			            		_gtimeStore.getProxy().extraParams = {id:_id};
			            		_timeStore.reload();   
			            		_gtimeStore.reload();
			            	});
	            		
	            		}
		            }
		        });
//		        =============================================================================================
		    	/**
		    	 * 时间全列
		    	 */
		    	var timegrid= Ext.create('Ext.grid.Panel', {
		    	    store: timeStore,
		    	    flex:1,
		    	    columns: [{text: "ID", flex: 1, sortable: false,hidden:true,  dataIndex: 'id',align:'center'},
		    	        {text: "时间", flex: 1, sortable: false, dataIndex: 'name',align:'center'},
		    	        {text: "开始日期", flex: 1, sortable: false, dataIndex: 'stime',align:'center'},
		    	        {text: "开始时间", flex: 1, sortable: false, dataIndex: 'sdtime',align:'center'},
		    	        {text: "结束日期", flex: 1, sortable: false, dataIndex: 'etime',align:'center'},
		    	        {text: "结束时间", flex: 1, sortable: false, dataIndex: 'edtime',align:'center'},
		    	        {text: "创建时间", flex: 1, sortable:false, dataIndex: 'creatime',align:'center'},
		    	        {text: "描述", flex: 1, sortable:false, dataIndex: 'descr',align:'center'}],
			    	bbar: {
			            xtype: 'pagingtoolbar',
			            pageSize:defaultPageSize,
			            store: timeStore,
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
				        		   timeStore.getProxy().extraParams = {searchStr:searchStr};
				        		   timeStore.loadPage(1);
				        	    }
				           }]
		    	});
		    	
		    	/**
		    	 * 时间分组全列
		    	 */
		    	var timegrogrid= Ext.create('Ext.grid.Panel', {
		    	    store: timegroStore,
		    	    flex:1,
		    	    columns: [{text: "ID", flex: 1, sortable: false,hidden:true,  dataIndex: 'id',align:'center'},
		    	        {text: "时间分组名称", flex: 1, sortable: false, dataIndex: 'name',align:'center'},
		    	        {text: "创建时间", flex: 1, sortable:false, dataIndex: 'creatime',align:'center'},
		    	        {text: "描述", flex: 1, sortable:false, dataIndex: 'descr',align:'center'}],
			        bbar: {
		                xtype: 'pagingtoolbar',
		                pageSize:defaultPageSize,
		                store: timegroStore,
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
				        		   timegroStore.getProxy().extraParams = {searchStr:searchStr};
				        		   timegroStore.loadPage(1);
				        	    }
				           }]
		    	});
		        this.items = [timegrid,timegrogrid];
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

