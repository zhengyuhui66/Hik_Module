


var addOrUpdateUrL= rootUrl+'/getAdvertController/saveAdvert';

/**
 * 主面板
 */
var advertGrid=Ext.define('KitchenSink.view.tree.TreeGrid', {
    extend: 'Ext.grid.Panel',
    requires: [
        'Ext.data.*',
        'Ext.grid.*',
        'Ext.util.*',
        'Ext.toolbar.Paging',
        'Ext.ux.ProgressBarPager',
        'Ext.selection.CheckboxModel'
    ],    
    xtype: 'tree-grid',
	padding:'5 5 5 5',
    initComponent: function() {
    	var addUpdateFlag=true;
    	var mater_path="";
    	/**
    	 * 广告模型
    	 */
    	Ext.define('advert', {
    	    extend: 'Ext.data.Model',
    	    fields: [
    	        {name: 'advertid', type: 'string'},
    	        {name: 'userid',  type: 'string'},
    	        {name: 'username',  type: 'string'},
    	        {name: 'matername',  type: 'string'},
    	        {name: 'matertype',  type: 'string'},
    	        {name: 'materpath',  type: 'string'},
    	        {name: 'materid',type:'string'},
    	        {name: 'adverturl',type:'string'},
    	        {name: 'createtime',type:'string'},
    	        {name: 'name',type:'string'},
    	        {name: 'desc',type:'string'},
    	        {name: 'advertpropertyid',type:'string'},
    	        {name: 'advertproperty',type:'string'},
    	        {name: 'pid',type:'string'}
    	    ]
    	});
//    	========================================================
    	/**
    	 * 物料模块模型
    	 */
    	Ext.define('materModel',{
    	    extend: 'Ext.data.Model',
    	    fields: [
    	        {name: 'materids', type: 'string'},
    	        {name: 'matername', type: 'string'},
    	        {name: 'materpath', type: 'string'}
    	    ]
    	});
    	/**
    	 * 物料模块Store    
    	 */
    	var materStore=Ext.create('Ext.data.Store',{
    		model:'materModel',
    	    proxy: {
    	        type: 'ajax',
    	        url: rootUrl+'/getAdvertController/getMater',
    	        reader: {
    	            type: 'json',
    	            root: 'data'
    	        }
    	    },
    	    autoLoad:true
    	});
//    	========================================================
    	/**
    	 * 广告分类模型
    	 */
    	Ext.define('propertyModel',{
    	    extend: 'Ext.data.Model',
    	    fields: [
    	        {name: 'id', type: 'string'},
    	        {name: 'name', type: 'string'}
    	    ]
    	});
    	
    	var materTypeStore=Ext.create('Ext.data.Store',{
    		model:'propertyModel',
    	    proxy: {
    	        type: 'ajax',
    	        url: rootUrl+'/materialController/queryMaterType',
    	        reader: {
    	            type: 'json',
    	            root: 'data'
    	        }
    	    },
    	    autoLoad:true
    	});
    	var materSizeStore=Ext.create('Ext.data.Store',{
    		model:'propertyModel',
    	    proxy: {
    	        type: 'ajax',
    	        url: rootUrl+'/materialController/queryMaterSize',
    	        reader: {
    	            type: 'json',
    	            root: 'data'
    	        }
    	    },
    	    autoLoad:true
    	});
    	var materUserStore=Ext.create('Ext.data.Store',{
    		model:'propertyModel',
    	    proxy: {
    	        type: 'ajax',
    	        url: rootUrl+'/materialController/queryMaterCreator',
    	        reader: {
    	            type: 'json',
    	            root: 'data'
    	        }
    	    },
    	    autoLoad:true
    	});
    	
    	
//----------------------优先投放条件--------------------------------------------
    	var putConditionStore=Ext.create('Ext.data.Store',{
    		model:'propertyModel',
    	    proxy: {
    	        type: 'ajax',
    	        url: rootUrl+'/putmangersetcontroller/queryputSet',
    	        reader: {
    	            type: 'json',
    	            root: 'data'
    	        }
    	    },
    	    autoLoad:true
    	});
 //------------------------------------------------------------------
    	/**
    	 * 查询
    	 */
    	var propertysStore=Ext.create('Ext.data.Store',{
    		model:'propertyModel',
    	    proxy: {
    	        type: 'ajax',
    	        url: rootUrl+'/common/getPropertys',
    	        reader: {
    	            type: 'json',
    	            root: 'data'
    	        }
    	    },
    	    autoLoad:true
    	});
    	/**
    	 * 广告分类Store    
    	 */
    	var propertyStore=Ext.create('Ext.data.Store',{
    		model:'propertyModel',
    	    proxy: {
    	        type: 'ajax',
    	        url: rootUrl+'/common/getProperty',
    	        reader: {
    	            type: 'json',
    	            root: 'data'
    	        }
    	    }
    	});
//    	=============================================================
    	
    	
    	var validator =function(value){
        	var result=true;
        	Ext.Ajax.request({
    		    url: rootUrl+'/getAdvertController/getrepeatAdvertName',
    		    async: false,
    		    params:{
    		    	name:value
    		    },
    		    success: function(response){
    		    	var respTexts = Ext.JSON.decode(response.responseText);
    		    	var respText=respTexts.data;
    		    	if(!respText){
    		    		result=false;
    		    	}else if(respText){
    		    		result=true;
    		    	}
    		    }
    		});
        	
        	return result;
    	}
    	//----------------------优先投放条件--------------------------------------------        	
    	var putConditionid=0;
    	var addputCond=function(){
    		putConditionid++;
	        var tex=Ext.create('Ext.form.ComboBox', {
	    		margin:'5 0 0 5',
	    	    store: putConditionStore,
	    	    width:120,
	    	    name:'putcondId',
	    	    id:'_putcondId'+putConditionid,
	    	    displayField: 'name',
	    	    valueField: 'id'
	    	});
			Ext.getCmp('multiCondid').add(tex);
    	}
    	//----------------------优先投放条件--------------------------------------------   	
        var simple = Ext.widget({
            xtype: 'form',
            id: 'simpleForm',
            overflowY:'auto',
            fieldDefaults: {
                labelWidth : 100,
                labelAlign : 'right'
            },
            title:'基本信息',
            padding:'20 20 0 0',
            border:false,
            defaultType: 'textfield',
            defaults:{
            	anchor:'100%',
            	margin:'15 0 0 0'
            },
            items: [{
			    	xtype: 'fieldset',
		            title: '优先投放的条件',
			    	 anchor: '100%',
			    	margin:'10 0 0 45',
			    	padding:'10 10 10 10',
			    	layout:{
			    		type:'hbox',
			    		align:'center'
			    	},
		    		items:[{
							xtype : 'combo',
							store : materTypeStore,
							displayField : 'name',
							valueField : 'id', 
							selectOnTab : false,
							name : 'materType',
							id : '_materType',
//							width:100,
							flex:1,
							emptyText : '类型筛选',
							onReplicate : function() {
								this.getStore().clearFilter();
							},
							listeners : {
								change : function(me,newValue,oldValue,eOpts) {
									var temp = me.displayTplData;
									var id=null;
									if (temp&&temp.length > 0) {
										var id= temp[0].id;
									}
									var materSizeid=Ext.getCmp('_materSize').getValue();
									var materCreatorid=Ext.getCmp('_materCreator').getValue();
									materStore.getProxy().extraParams = {
										materTypeid:id,
										materSizeid:materSizeid,
										materCreatorid:materCreatorid
									};
									materStore.reload();
								}
							}
						},{
							xtype : 'combo',
							store : materSizeStore,
							displayField : 'name',
							valueField : 'id', // 真实的字段
							selectOnTab : false,
							name : 'materSize',
							id : '_materSize',
							margin:'0 10 0 0',
//							width:100,
							flex:1,
							emptyText : '大小筛选',
							onReplicate : function() {
								this.getStore().clearFilter();
							},
							listeners : {
								change : function(me,newValue,oldValue,eOpts) {
									var temp = me.displayTplData;
									var id=null;
									if (temp&&temp.length > 0) {
										id = temp[0].id;
									
									}
									var materTypeid=Ext.getCmp('_materType').getValue();
									var materCreatorid=Ext.getCmp('_materCreator').getValue();
									materStore.getProxy().extraParams = {
										materTypeid:materTypeid,
										materSizeid:id,
										materCreatorid:materCreatorid
									};
									materStore.reload();
								}
							}
						},
						{
							xtype : 'combo',
							store : materUserStore,
							displayField : 'name',
							valueField : 'id', // 真实的字段
							selectOnTab : false,
							name : 'materCreator',
							id : '_materCreator',
//							width:100,
							flex:1,
							emptyText : '上传人筛选',
							onReplicate : function() {
								this.getStore().clearFilter();
							},
							listeners : {
								change : function(me,newValue,oldValue,eOpts) {
									var temp = me.displayTplData;
									var id=null;
									if (temp&&temp.length > 0) {
										id = temp[0].id;
									}
									var materTypeid=Ext.getCmp('_materType').getValue();
									var materSizeid=Ext.getCmp('_materSize').getValue();
									materStore.getProxy().extraParams = {
										materTypeid:materTypeid,
										materSizeid:materSizeid,
										materCreatorid:id
									};
									materStore.reload();
								}
							}
						}]
//----------------------优先投放条件--------------------------------------------
			    
            	
//		        xtype:'container',
//		        layout: 'hbox',
//		    	items: [{
//		    		xtype: 'fieldset',
//		            title: '筛选物料',
//		            anchor: '100%',
//			    	 margin:'0 0 0 50',
//			    	padding:'10 17 10 10',
//			    	layout:{
//			    		type:'hbox',
//			    		align:'left'
//			    	},
//			    	defaults:{
//			    		margin:'0 20 0 20'
//			    	},
//			    	items : [
//					{
//						xtype : 'combo',
//						store : materTypeStore,
//						displayField : 'name',
//						valueField : 'id', 
//						selectOnTab : false,
//						name : 'materType',
//						id : '_materType',
////						width:100,
////						flex:1,
//						emptyText : '类型筛选',
//						onReplicate : function() {
//							this.getStore().clearFilter();
//						},
//						listeners : {
//							change : function(me,newValue,oldValue,eOpts) {
//								var temp = me.displayTplData;
//								var id=null;
//								if (temp&&temp.length > 0) {
//									var id= temp[0].id;
//								}
//								var materSizeid=Ext.getCmp('_materSize').getValue();
//								var materCreatorid=Ext.getCmp('_materCreator').getValue();
//								materStore.getProxy().extraParams = {
//									materTypeid:id,
//									materSizeid:materSizeid,
//									materCreatorid:materCreatorid
//								};
//								materStore.reload();
//							}
//						}
//					},
//					{
//						xtype : 'combo',
//						store : materSizeStore,
//						displayField : 'name',
//						valueField : 'id', // 真实的字段
//						selectOnTab : false,
//						name : 'materSize',
//						id : '_materSize',
////						width:100,
////						flex:1,
//						emptyText : '大小筛选',
//						onReplicate : function() {
//							this.getStore().clearFilter();
//						},
//						listeners : {
//							change : function(me,newValue,oldValue,eOpts) {
//								var temp = me.displayTplData;
//								var id=null;
//								if (temp&&temp.length > 0) {
//									id = temp[0].id;
//								
//								}
//								var materTypeid=Ext.getCmp('_materType').getValue();
//								var materCreatorid=Ext.getCmp('_materCreator').getValue();
//								materStore.getProxy().extraParams = {
//									materTypeid:materTypeid,
//									materSizeid:id,
//									materCreatorid:materCreatorid
//								};
//								materStore.reload();
//							}
//						}
//					},
//					{
//						xtype : 'combo',
//						store : materUserStore,
//						displayField : 'name',
//						valueField : 'id', // 真实的字段
//						selectOnTab : false,
//						name : 'materCreator',
//						id : '_materCreator',
////						width:100,
////						flex:1,
//						emptyText : '上传人筛选',
//						onReplicate : function() {
//							this.getStore().clearFilter();
//						},
//						listeners : {
//							change : function(me,newValue,oldValue,eOpts) {
//								var temp = me.displayTplData;
//								var id=null;
//								if (temp&&temp.length > 0) {
//									id = temp[0].id;
//								}
//								var materTypeid=Ext.getCmp('_materType').getValue();
//								var materSizeid=Ext.getCmp('_materSize').getValue();
//								materStore.getProxy().extraParams = {
//									materTypeid:materTypeid,
//									materSizeid:materSizeid,
//									materCreatorid:id
//								};
//								materStore.reload();
//							}
//						}
//					}
//					]
//		    	}]
		    },{
				xtype : 'combo',
				store : materStore,
				displayField : 'matername',
				valueField : 'materids', //真实的字段
				fieldLabel : '物料选择',
				selectOnTab : false,
				 allowBlank: false,
//				 width:400,
				name : 'materid',
				id : 'materid_id',
				emptyText : '请选择...',
				onReplicate : function() {
					this.getStore().clearFilter();
				},
				listeners : {
					change : function(me,newValue,oldValue,eOpts) {
						var temp = me.displayTplData;
						if(temp&&temp[0]&&temp[0].materpath){
							mater_path=httpUrlResources+temp[0].materpath;
							$("#materViewId").attr("src",mater_path);							
						}
					}
				}
			},{
	    	 
		        xtype:'container',
		        layout: {
		        	type:'hbox',
		    		align:'center'
		        },
		        anchor: '100%',
		        defaultType: 'textfield',
		    	items: [{
					        xtype: 'combo',
					        store:propertysStore,
					        displayField:'name', 
					        valueField  : 'id',    //真实的字段
					        fieldLabel: '广告分类',
					        selectOnTab: false,
					        allowBlank: false,
					        name: 'propertysid',
					        id:'propertysid',
					        emptyText   : '请选择...',
					        onReplicate: function(){
					            this.getStore().clearFilter();
					        },listeners:{
					        	change:function(me, newValue, oldValue, eOpts){
					        		var temp=me.displayTplData;
					        		if(temp.length>0){
					        				var id=temp[0].id;
					        				propertyStore.getProxy().extraParams = {id:id};
					        				propertyStore.reload();
					        				Ext.getCmp('propertyid').setValue('');
					        		}
					        	}
					        }
			            }, {
			            	xtype: 'combo',
					        store:propertyStore,
					        margin:'0 0 0 34',
					        displayField:'name', 
					        valueField  : 'id',    //真实的字段
					        selectOnTab: false,
					        allowBlank: false,
					        name: 'propertyid',
					        id:'propertyid',
					        emptyText:'请选择...',
					        onReplicate: function(){
					            this.getStore().clearFilter();
					        },listeners:{
					        	select:function(combo, records,eOpts){
					        		if(records){
					        			var record = records[0].getData();
					        		}
					        	},
					        	change:function(me, newValue, oldValue, eOpts){
					        		var temp=me.displayTplData;
					        		if(temp.length>0){
						        		
					        		}
					        	}
					        }
            }]},{
	                fieldLabel: '链接地址',
	                name: 'adverturl',
	                id:'_adverturl',
	                emptyText:'广告名称',
	                allowBlank: false,
	                blankText: '不能为空!'
	            },{
	                fieldLabel: '广告名称',
	                name: 'advertname',
	                id:'_advertname',
	                allowBlank: false,
	                emptyText:'广告名称',
	                blankText: '不能为空!',
	                validateOnBlur:true,
	                invalidText: '有重复记录',
	                validator:function(){
	                	if(!addUpdateFlag){
	                		//如果是修改则无需验证
	                		return true;
	                	}
	                	var value=this.getValue();
	                	var result=true;
	                	Ext.Ajax.request({
	            		    url: rootUrl+'/getAdvertController/getrepeatAdvertName',
	            		    async: false,
	            		    params:{
	            		    	name:value
	            		    },
	            		    success: function(response){
	            		    	var respTexts = Ext.JSON.decode(response.responseText);
	            		    	var respText=respTexts.data;
	            		    	if(!respText){
	            		    		result='有重复记录';
	            		    	}else if(respText){
	            		    		result=true;
	            		    	}
	            		    }
	            		});
	                	
	                	return result;
	                }
	            },{
	                fieldLabel: '广告ID',
	                name: 'adverid',
	                id:'_advertid',
	                hidden:true
	            },{
	            	xtype: 'textareafield',
	            	height: 80,
	                fieldLabel: '广告描述',
	                name: 'description',
	                id: '_description'
//----------------------优先投放条件--------------------------------------------
	            },{
			    	xtype: 'fieldset',
		            title: '优先投放的条件',
			    	 anchor: '100%',
			    	margin:'10 0 0 45',
			    	padding:'10 10 10 10',
			    	layout:{
			    		type:'vbox',
			    		align:'left'
			    	},
		    		items:[{
			        	 xtype:'button',
			        	 text: '新增',
			        	 handler:addputCond
		    		},{ 
		    			layout:{
				             type: 'table',
				             columns: 3
				         },
				         xtype:'container',
				         fieldLabel: '广告优先投放条件',
				         id:'multiCondid',
				         items:[]
			        }]
//----------------------优先投放条件--------------------------------------------
			    }], buttons: [{
			                text: '提交',
			                handler: function() {
			                	this.up('form').getForm().submit({
		                                clientValidation:true,
		                                waitMsg:'请稍候',
		                                waitTitle:'正在更新',
		                                url: addOrUpdateUrL,
		                                success:function(form,action){
		                                	if(action&&action.result&&action.result.success){
		                                		if(action.result.success=="true"){
		                                			trStore.reload();
		                                		}else if(action.result.success=="false"){
		                                			Ext.Msg.show({
		        			            				title : '提示',
		        			            				msg : '上传失败!',
		        			            				width : 250,
		        			            				icon : Ext.Msg.ERROR,//INFO,ERROR,QUESTION,WARNING
		        			            				buttonText: { ok: '确定' }
		        			            			});
//		                                			Ext.Msg.alert('温馨提示',"上传失败!");
		                                		}
		                                	}else{
		                                		Ext.Msg.show({
	        			            				title : '提示',
	        			            				msg : '上传失败!',
	        			            				width : 250,
	        			            				icon : Ext.Msg.ERROR,//INFO,ERROR,QUESTION,WARNING
	        			            				buttonText: { ok: '确定' }
	        			            			});
		                                	}
		                                	Ext.getCmp('simpleForm').getForm().reset();
		    			                	win.close();
		                                },  
		                                failure:function(form,action){  
		                                	Ext.Msg.show({
        			            				title : '提示',
        			            				msg : '上传失败!',
        			            				width : 250,
        			            				icon : Ext.Msg.ERROR,//INFO,ERROR,QUESTION,WARNING
        			            				buttonText: { ok: '确定' }
        			            			});
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
         * 新增物料审核的面板
         */
        var win = Ext.create('widget.window', {
                title: '新增物料审核',
                header: {
                    titlePosition: 2,
                    titleAlign: 'center'
                },
                closable: false,
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
                    items:[simple,{
//								xtype: 'fieldset',
//								title: '基本信息',
//								margin:5,
//								layout:'fit',
//								width:600,
//	                            items:materialInfo
//                            }, {
								title: '物料展示',
								stateId:'materinfo',
								html:'<div style="width:100%;height:100%;overflow:auto;padding:10px;"><img id="materViewId" src="" onerror="this.src=\''+rootUrl+"/images/default/default.jpg"+'\'"></img></div>'
                            }],
                            listeners:{
                          	  'tabchange':function(me, eOpts){
                          		  if(eOpts.stateId=='materinfo'){
                          			  $("#materViewId").attr('src',mater_path);
                          		  }
                          	  }
                            }
                }
            });
//        /**
//         * 广告的面板
//         */
//        var win = Ext.create('widget.window', {
//                title: '广告',
//                header: {
//                    titlePosition: 2,
//                    titleAlign: 'center'
//                },
//                closable: false,
//                modal: true,
//                closeAction: 'hide',
//                width: 550,
//                minWidth: 350,
//                height: 450,
//                layout: {
//                    type: 'border'
//                },
//                items: {
//                    region: 'center',
//                    xtype: 'panel',
//                    overflowY:'auto',
//                    autoScroll:true,
//                    layout:{
//                    	type:'vbox',
//                    	align: 'stretch'
//                    },
//                    items:[simple,{
//                        	xtype:'panel',
//                        	margin:'10 10 10 10',
//                        	text:'物料预览',
//                        	html:'<div style="width:526px;height:127px;overflow:auto;"><img id="materViewId" src="'+rootUrl+"/images/default/default.jpg"+'" onerror="this.src=\''+rootUrl+"/images/default/default.jpg"+'\'"/></div>'
//                    }]
//                }
//            });
        /**
         * 新增按钮
         */
        var addAction = Ext.create('Ext.Action', {
        	iconCls:'common_add',
            text: '新增',
            handler: function(widget, event) {
            	addUpdateFlag=true;
            	win.show(this,function(){
            		addOrUpdateUrL= rootUrl+'/getAdvertController/saveAdvert';
            		win.setTitle("新增广告");
            		$("#materViewId").attr("src","");
//----------------------优先投放条件--------------------------------------------
            		Ext.getCmp('multiCondid').removeAll();
            		putConditionid=0;
//----------------------优先投放条件--------------------------------------------

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
     		   trStore.getProxy().extraParams = {searchStr:searchStr};
     		   trStore.loadPage(1);
            }
        });
        /**
         * 删除按钮
         */
        var deleteAction = Ext.create('Ext.Action', {
        	iconCls:'common_del',
            text: '删除广告',
            handler: function(widget, event) {
            	 var rec = me.getSelectionModel().getSelection();
                 if(rec){
                 	 if(rec.length==0){
//                      	Ext.Msg.alert('温馨提示',"请先选中一条!");
                 		Ext.Msg.show({
            				title : '提示',
            				msg : '请先选中一条!',
            				width : 250,
            				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
            				buttonText: { ok: '确定' }
            			});
                 	 }else if(rec.length>0){
                 		var params=new Array();
                    	for(var i=0;i<rec.length;i++){
                    		params.push(rec[i].data.advertid);
                    		
                    	}
                    	Ext.Ajax.request({
                    	    url:rootUrl+'/getAdvertController/deleteAdvert',
                    	    async: false,
                    	    params: {
            			        advertid: params.toString()
            			    },
                    	    success: function(response){
                    	    	var respText = Ext.JSON.decode(response.responseText);
                    	    	if(respText.success=='true'){
//                    	    		Ext.Msg.alert('温馨提示',"成功删除了"+respText.data+"条记录");          
                    	    		Ext.Msg.show({
                        				title : '提示',
                        				msg :"成功删除了"+respText.data+"条记录",
                        				width : 250,
                        				icon : Ext.Msg.INFO,//INFO,ERROR,QUESTION,WARNING
                        				buttonText: { ok: '确定' }
                        			});
                    	    		trStore.reload();
                    	    	}else{
//                    	    		Ext.Msg.alert('温馨提示',"删除失败"+respText.data); 
                    	    		Ext.Msg.show({
                        				title : '提示',
                        				msg : "删除失败"+respText.data,
                        				width : 250,
                        				icon : Ext.Msg.ERROR,//INFO,ERROR,QUESTION,WARNING
                        				buttonText: { ok: '确定' }
                        			});
                    	    	}
                    	    }
                    	});
                 	 }
                 }
                 
 
            }
        });
        
        var me=this;
        /**
         * 更改物料按钮
         */
        var updateAction = Ext.create('Ext.Action', {
        	iconCls:'common_edit',
            text: '更改物料',
            handler: function(widget, event) {
            	addUpdateFlag=false;
                var rec = me.getSelectionModel().getSelection();
                if(rec){
                	 if(rec.length==0){
//                     	Ext.Msg.alert('温馨提示',"请先选中一条!");
                		 Ext.Msg.show({
             				title : '提示',
             				msg : "请先选中一条!",
             				width : 250,
             				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
             				buttonText: { ok: '确定' }
             			});
                	 }else if(rec.length>1){
                		 Ext.Msg.show({
             				title : '提示',
             				msg : "您选中了多条记录!</br>编辑只能选择一条",
             				width : 250,
             				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
             				buttonText: { ok: '确定' }
             			});
//                		 Ext.Msg.alert('温馨提示',"您选中了多条记录!</br>编辑只能选择一条");
                	 }else{
                		 var tempdata=rec[0].data;
                			win.show(this,function(){
                				Ext.getCmp("simpleForm").getForm().reset();
                				$("#materViewId").attr("src","");
                				addOrUpdateUrL= rootUrl+'/getAdvertController/updateAdvert';
                				win.setTitle("更新广告信息");
                				

                				Ext.getCmp("_advertid").setValue(tempdata.advertid);
                				Ext.getCmp("materid_id").setValue(tempdata.materid);
                       		 	Ext.getCmp("_advertname").setValue(tempdata.name);
                       		 	Ext.getCmp("_adverturl").setValue(tempdata.adverturl);
                       		 	Ext.getCmp("_description").setValue(tempdata.desc);
                       		 	var tempadvp=tempdata.advertpropertyid;
                       		 	var tempid=tempdata.pid;
                       		 	if(tempid==0){
                       		 		Ext.getCmp("propertysid").setValue(tempadvp+'');
                       		 	}else{
                       		 		Ext.getCmp("propertysid").setValue(tempid+'');
                       		 		Ext.getCmp("propertyid").setValue(tempadvp+'');
                       		 	}
//----------------------优先投放条件--------------------------------------------
                				Ext.getCmp('multiCondid').removeAll();
	                       		 Ext.Ajax.request({
	                         	    url:rootUrl+'/getAdvertController/getConditionByAdvid',
	                         	    async: false,
	                         	    params: {
	                 			        id: tempdata.advertid
	                 			    },
	                         	    success: function(response){
	                         	    	var respText = Ext.JSON.decode(response.responseText);
	                         	    	var respData = respText.data;
	                         	    	putConditionid=0;
	                         	    	for(var i=1;i<=respData.length;i++){
	                         	    		addputCond();
	                         	    		Ext.getCmp('_putcondId'+i).setValue(respData[i-1]+'');
	                         	    	}
	                         	    }
	                         	});
//----------------------优先投放条件--------------------------------------------
                			});
                	 }
                }
            }
        });
    	
   	 /**
   	  * 得到所有用户信息
   	  */
   	 var trStore=Ext.create('Ext.data.Store', {
   		    storeId:'idadvert',
   		    model: advert,
   		    pageSize:defaultPageSize,
   		    proxy: {
   		    	type: 'ajax',
   		    	url:rootUrl+'/getAdvertController/getAdvert',
   		        reader: {
   		            type: 'json',
   		            root: 'elementList',  
   		            totalProperty: 'total'
   		        }
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
                dataIndex: 'advertid'
            },{
                text: '上传用户ID',
                dataIndex: 'userid',
                hidden:true
            },{
        	   text: '上传用户名称',
               flex: 1,
               align:'center',
               dataIndex: 'username',
               sortable: true
           },{
                text: '物料名称',
                flex: 1,
                align:'center',
                dataIndex: 'matername',
                sortable: true
            },{
                text: '物料类型',
                flex: 1,
                hidden:true,
                align:'center',
                dataIndex: 'matertype',
                sortable: true
            },{
                text: '物料来源地址',
                flex: 2,
                hidden:true,
                align:'center',
                dataIndex: 'materpath',
                sortable: true
            },{
                text: '物料ID',
                hidden:true,
                dataIndex: 'materid'
            },{
                text: '广告业务链接地址',
                flex: 2,
                align:'center',
                dataIndex: 'adverturl'
            },{
                text: '广告名称',
                flex: 1,
                align:'center',
                dataIndex: 'name'
            },{
                text: '广告创建时间',
                flex: 1,
                align:'center',
                dataIndex: 'createtime'
            },{
                text: '广告描述',
                flex: 1,
                align:'center',
                dataIndex: 'desc'
            },{
                text: '广告属性ID',
                flex: 1,
                align:'center',
                dataIndex: 'advertpropertyid',
                hidden:true
            },{
                text: '广告属性',
                flex: 1,
                align:'center',
                dataIndex: 'advertproperty'
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
            },tbar: [addAction,updateAction,deleteAction,refreshAction,'->',
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
		        		   trStore.getProxy().extraParams = {searchStr:searchStr};
		        		   trStore.loadPage(1);
		        	    }
		           }]
        });
        this.callParent();
    }
});
/**
 * 命名空间，主函数
 */
Ext.application({
    name: 'advertModelManger',
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
				items:advertGrid
			}]

    	});
    }
});