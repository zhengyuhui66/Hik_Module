

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
    layout:'fit',
//    layout:{
//		type:'hbox',
//		align:'stretch',
//		pack:'center'
//	},
    initComponent: function () {
		    	/**
		    	 * 模型模型
		    	 */
		    	Ext.define('uUser',{
		    	    extend: 'Ext.data.Model',
		    	    fields: [{name: "userid", type: 'string'},
							{name: "content", type: 'string'},
							{name: "creatime", type: 'string'},
							{name: "creator", type: 'string'},
							{name: "descr", type: 'string'}]
		    	});
		    	
		    	Ext.define('tUser',{
		    	    extend: 'Ext.data.Model',
		    	    fields: [{name: "id", type: 'string'},
							{name: "name", type: 'string'}]
		    	});

		    	/**
		    	 * 查询手机分组信息
		    	 */
		    	var messpushgroStore=Ext.create('Ext.data.Store',{
		   		    model: uUser,
		   		    pageSize:defaultPageSize,
		   		    proxy: {
		   		    	type: 'ajax',
		   		    	url:rootUrl+'/mpc/queryForpage',
		   		        reader: {
		   		            type: 'json',
		   		            root: 'elementList',  
		   		            totalProperty: 'total'
		   		        }
		   		    }
		    	});
		    	messpushgroStore.loadPage(1);
//		    	===========操作按钮====================================================================
//		        /**
//		         * 查所有不包含被分组的手机号
//		         */
		        var _userStore=Ext.create('Ext.data.Store',{
		    		model:'tUser',
		    	    proxy: {
		    	        type: 'ajax',
		    	        url: rootUrl+'/common/getAllUser',
		    	        reader: {
		    	            type: 'json',
		    	            root: 'data'
		    	        }
		    	    },
		   		    autoLoad:true
		    	}); 
		        var userGrid=Ext.create('Ext.grid.Panel', {
		            flex:1,
		            height:300,
		            store:_userStore,
		            columns: [
		                { text: 'id', hidden:true,  dataIndex: 'id' },
		                { text: '广告主列表',flex:1, dataIndex: 'name'}
		            ]
		        });
		        
//		        =========================================================================
		        /**
		         * 查所有包含被分组的手机号
		         */
		        var _gmesspushStore=Ext.create('Ext.data.Store',{
		    		model:'tUser',
		    	});
		        var gmesspushGrid=Ext.create('Ext.grid.Panel', {
		            flex:1,
		            height:300,
		            store:_gmesspushStore,
		            columns: [
		                { text: 'id', hidden:true,  dataIndex: 'id' },
		                { text: '消息接收者',flex:1,  dataIndex: 'name'}
		            ]
		        });
		        /**
		         * 推送面板
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
		         	   xtype: 'textareafield',
 	                   fieldLabel: '推送内容',
 	                   padding:'10 10 10 10',
 	                   height:80,
 	                   allowBlank: false,
 	                   name: 'content',
 	                   id:'_content'
		            },{
		            	xtype:'panel',
		            	flex:5,
		            	layout:{
			            	type:'hbox',
			            	pack:'center',
			            	align:'stretchmax'
			            },
			        	items:[userGrid,{
			        		xtype:'panel',
			            	layout:{
			            		type:'vbox',
				            	pack:'center',
				            	align:'stretchmax'
			            	},items:[{
			            		xtype:'button',
			            		text: '>>|',
			            		handler: function(){
			            			for(var i =0;i<_userStore.getCount();i++){
			            				_gmesspushStore.add(_userStore.getAt(i)); //遍历每一行
			            			}
			            			_userStore.removeAll();
			            		}
			            	},{
			            		xtype:'button',
			            		text: '>>',
			            		margin:'10 0 0 0',
			            		handler: function(){
			            		     var rec = userGrid.getSelectionModel().getSelection();
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
					                 			_userStore.remove(Ext.create('tUser',rec[i].data));
					                 			_gmesspushStore.add(Ext.create('tUser',rec[i].data));
		    		                    	}
					                 	 }
					                 }
			            		}
			            	},{
			            		xtype:'button',
			            		margin:'10 0 0 0',
			            		text: '<<',
			            		handler: function() {
		            			  var rec = gmesspushGrid.getSelectionModel().getSelection();
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
				                 			_gmesspushStore.remove(Ext.create('tUser',rec[i].data));
				                 			_userStore.add(Ext.create('tUser',rec[i].data));
	    		                    	}
				                 	 }
				                  }
			            		}
			            	},{
			            		xtype:'button',
			            		margin:'10 0 0 0',
			            		text: '|<<',
			            		handler: function() {
			            			for(var i =0;i<_gmesspushStore.getCount();i++){
			            				_userStore.add(_gmesspushStore.getAt(i)); //遍历每一行
			            			}
			            			_gmesspushStore.removeAll();
			            		}
			            	}]
			        	},gmesspushGrid]
		            },{
		            	 xtype: 'textfield',
	 	                   fieldLabel: '描述',
	 	                  padding:'5 5 0 0',
	 	                   allowBlank: false,
	 	                   name: 'messpushdesc',
	 	                   id:'_messpushdesc'		            	
		            }],
		            buttons: [{
			                text: '提交',
 			                handler: function(){
 			                	var _content=Ext.getCmp('_content').getValue();
 			                	var _descr = Ext.getCmp('_messpushdesc').getValue();
 			                	var alisa = new Array();
 			                	for(var i =0;i<_gmesspushStore.getCount();i++){
		            				var tr=_gmesspushStore.getAt(i); //遍历每一行
		            				var temp=tr.data.name;
		            				alisa.push(temp);
//		            				console.info(tr);
		            			}
 			                	if(alisa.length==0){
// 			                		Ext.Msg.alert('温馨提示',"没有选择消息接收者");
 			                		Ext.Msg.show({
  	                       				title : '温馨提示',
  	                       				msg :"没有选择消息接收者",
  	                       				width : 250,
  	                       				icon : Ext.Msg.ERROR,//INFO,ERROR,QUESTION,WARNING
  	                       				buttonText: { ok: '确定'}
  	                       			});
 			                		return 
 			                	}
 			                	Ext.Ajax.request({
		                    	    url:rootUrl+'/mpc/pushMessage',
		                    	    async: false,
		                    	    params: {
		            			        content:_content,
		            			        descr:_descr,
		            			        alisa:alisa.toString()
		            			    },
		                    	    success: function(response){
		                    	    	var respText = Ext.JSON.decode(response.responseText);
		                    	    	if(respText.success=='false'){
		                    	    		if(respText.data=='null'){
//		                    	    			Ext.Msg.alert('温馨提示',"CID不存在");
		                    	    			Ext.Msg.show({
		      	                       				title : '温馨提示',
		      	                       				msg :"CID不存在",
		      	                       				width : 250,
		      	                       				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
		      	                       				buttonText: { ok: '确定'}
		      	                       			});
		                    	    		}else{
//		                    	    			Ext.Msg.alert('温馨提示',"推送失败");
		                    	    			Ext.Msg.show({
		      	                       				title : '温馨提示',
		      	                       				msg :"推送失败",
		      	                       				width : 250,
		      	                       				icon : Ext.Msg.ERROR,//INFO,ERROR,QUESTION,WARNING
		      	                       				buttonText: { ok: '确定'}
		      	                       			});
		                    	    		}
//		                    	    		Ext.Msg.alert('温馨提示',"推送失败");
		                    	    		Ext.Msg.show({
	      	                       				title : '温馨提示',
	      	                       				msg :"推送失败",
	      	                       				width : 250,
	      	                       				icon : Ext.Msg.ERROR,//INFO,ERROR,QUESTION,WARNING
	      	                       				buttonText: { ok: '确定'}
	      	                       			});
		                    	    	}else{
//		                    	    		Ext.Msg.alert('温馨提示',"成功推送了"+respText.data+"条记录");
		                    	    		Ext.Msg.show({
	      	                       				title : '温馨提示',
	      	                       				msg :"成功推送了"+respText.data+"条记录",
	      	                       				width : 250,
	      	                       				icon : Ext.Msg.INFO,//INFO,ERROR,QUESTION,WARNING
	      	                       				buttonText: { ok: '确定'}
	      	                       			});
		                    	    	}
		                    	    }
		                    	});
 			                	
// 			                	var _gmesspushList = new Array();
// 			                	_gmesspushStore.each(function(record) {
// 			                		_gmesspushList.push(record.get('id'));
//		 			             });
// 			                	Ext.getCmp('_messpushids').setValue(_gmesspushList.toString());
// 			                	gpanel.getForm().submit({
// 		                                clientValidation:true,
// 		                                waitMsg:'请稍候',
// 		                                waitTitle:'正在更新',
// 		                                url: rootUrl+'/pmangc/saveOrupdategmesspush',
// 		                                success:function(form,action){
// 		                                	if(action&&action.result&&action.result.success){
// 		                                		if(action.result.success=="true"){
// 		                                			messpushgroStore.reload();
// 		                                		}else if(action.result.success=="false"){
// 		                                			Ext.Msg.alert('温馨提示',"上传失败!");
// 		                                		}
// 		                                	}else{
// 		                                		Ext.Msg.alert('温馨提示',"上传失败!");
// 		                                	}
// 		                                	gpanel.getForm().reset();
// 		                                	gwin.close();
// 		                                },  
// 		                                failure:function(form,action){  
// 		                                	
// 		                                }
// 		                            });
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
		         * 弹出窗的面板
		         */
		        var gwin = Ext.create('widget.window', {
		                title: '推送',
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
//		            		_userStore.getProxy().extraParams = {id:null};
//		            		_userStore.reload();   
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
		            	messpushgroStore.getProxy().extraParams = {searchStr:searchStr};
		            	messpushgroStore.loadPage(1);
		            }
		        });
		        

//		        =============================================================================================
		    	
		    	/**
		    	 * 手机分组全列
		    	 */
		    	var messpushgrogrid= Ext.create('Ext.grid.Panel', {
		    	    store: messpushgroStore,
		    	    flex:1,
		    	    columns:[{text: "ID", flex: 1, sortable: false, dataIndex: 'userid',align:'center'},
		    	             {text: "内容", flex: 1, sortable: false,dataIndex: 'content',align:'center'},
		    	             {text: "推送时间", flex: 1, sortable: false, dataIndex: 'creatime',align:'center'},
		    	             {text: "推送人", flex: 1, sortable: false,dataIndex: 'creator',align:'center'},
		    	             {text: "描述", flex: 1, sortable: false, dataIndex: 'descr',align:'center'}],
			        bbar: {
		                xtype: 'pagingtoolbar',
		                pageSize:defaultPageSize,
		                store: messpushgroStore,
		                nextText:'下一页',
		                prevText:'上一页',
		                firstText:'第一页',
		                lastText:'最后一页',
		                refreshText:'刷新',
		                displayInfo: true,
		                displayMsg: '显示{0}-{1}条，共{2}条',
		                plugins: new Ext.ux.ProgressBarPager()
		            },
		            tbar: [gaddAction,grefreshAction,'->',
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
				        		   messpushgroStore.getProxy().extraParams = {searchStr:searchStr};
				        		   messpushgroStore.loadPage(1);
				        	    }
				           }]
		    	});
		        this.items = [messpushgrogrid];
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

