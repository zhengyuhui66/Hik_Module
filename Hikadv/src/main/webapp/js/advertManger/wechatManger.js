
var mmppGrid=Ext.define('Ext.mmppGrid.TreeGrid', {
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
    	
    	/**
    	 * 公众号模型
    	 */
    	Ext.define('putMangerSetModel', {
    	    extend: 'Ext.data.Model',
    	    fields: [
    	             {name: 'id', type: 'string'},
    	             {name: 'name', type: 'string'},
    	             {name: 'wechatid', type: 'string'},
    	             {name: 'type', type: 'string'},
    	             {name: 'introduce', type: 'string'},
    	             {name: 'identify', type: 'string'},
    	             {name: 'phone', type: 'string'},
    	             {name: 'address', type: 'string'},
    	             {name: 'maininfo', type: 'string'},
    	             {name: 'owner', type: 'string'},
    	             {name: 'email', type: 'string'},
    	             {name: 'orgid', type: 'string'},
    	             {name: 'appid', type: 'string'},
    	             {name: 'appsecret', type: 'string'},
    	             {name: 'accesstoken', type: 'string'},
    	             {name: 'creator', type: 'string'},
    	             {name: 'creatime', type: 'string'},
    	             {name: 'modifyer', type: 'string'},
    	             {name: 'modifytime', type: 'string'},
    	             {name: 'user_adv', type: 'string'},   
    	             {name: 'advertproperty', type: 'string'},
    	             {name: 'advertpropertys', type: 'string'}
    	    ]
    	});
//    	----------------优先投放模块-----------------------------
    	Ext.define('propertyModel',{
    	    extend: 'Ext.data.Model',
    	    fields: [
    	        {name: 'id', type: 'string'},
    	        {name: 'name', type: 'string'}
    	    ]
    	});
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
//    	----------------------------------------------
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
    	    },
    	    autoLoad:true
    	});
    	
    	var userStore=Ext.create('Ext.data.Store',{
    		model:'propertyModel',
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
        var simple = Ext.widget({
            xtype: 'form',
            id: 'simpleForm',
//            overflowY:'auto',
            fieldDefaults: {
                labelWidth : 100,
                labelAlign : 'right'
            },
            padding:'20 20 0 0',
            border:false,
            layout: {
                     type: 'vbox',
                     align: 'stretch'
                 },
             defaults: {
                 anchor: '100%',
            	 xtype: 'container',
                 layout: 'hbox',
                 border:1,
                 margin: '0 0 5 0'
             },
             items:[{
            	   xtype: 'textfield',
                   fieldLabel: 'ID',
                   name: 'id',
                   hidden:true,
                   id:'_id'
               },{
            	   xtype: 'textfield',
                   fieldLabel: '名称',
                   allowBlank: false,
                   name: 'name',
                   id:'_name'
               },{
            	   layout: {
                       type: 'hbox',
                       pack:'center',
                       align: 'middle'
                   },
                   items:[{
	            	   xtype: 'textfield',
	                   fieldLabel: '微信号',
	                   flex:1,
	                   name: 'wechatid',
	                   id:'_wechatid'
	               },{
	            	   xtype: 'textfield',
	                   fieldLabel: '类型',
	                   flex:1,
	                   name: 'type',
	                   id:'_type'
                   }]
	             },{
	            	   xtype: 'textareafield',
	                   fieldLabel: '介绍',
	                   height:40,
	                   name: 'introduce',
	                   id:'_introduce'
	             },{
	            	   xtype: 'textareafield',
	                   fieldLabel: '认证情况',
	                   height:40,
	                   name: 'identify',
	                   id:'_identify'
	             },{
	            	   layout: {
	                       type: 'hbox',
	                       pack:'center',
	                       align: 'middle'
	                   },
	                   items:[{
		            	   xtype: 'textfield',
		                   fieldLabel: '客服电话',
		                   flex:1,
		                   name: 'phone',
		                   id:'_phone'
		               },{
		            	   xtype: 'textfield',
		                   fieldLabel: '所在地址',
		                   flex:1,
		                   name: 'address',
		                   id:'_address'
	                   }]
	             },{
	            	   layout: {
	                       type: 'hbox',
	                       pack:'center',
	                       align: 'middle'
	                   },
	                   items:[{
		            	   xtype: 'textfield',
		                   fieldLabel: '主体信息',
		                   flex:1,
		                   name: 'maininfo',
		                   id:'_maininfo'
		               },{
		            	   xtype: 'textfield',
		                   fieldLabel: '运营者',
		                   flex:1,
		                   name: 'owner',
		                   id:'_owner'
	                   }]
	             },{
	            	   layout: {
	                       type: 'hbox',
	                       pack:'center',
	                       align: 'middle'
	                   },
	                   items:[{
		            	   xtype: 'textfield',
		                   fieldLabel: '邮箱',
		                   flex:1,
		                   name: 'email',
		                   id:'_email'
		               },{
		            	   xtype: 'textfield',
		                   fieldLabel: '原始ID',
		                   flex:1,
		                   name: 'orgid',
		                   id:'_orgid'
	                   }]
	             },{
	            	   layout: {
	                       type: 'hbox',
	                       pack:'center',
	                       align: 'middle'
	                   },
	                   items:[{
		            	   xtype: 'textfield',
		                   fieldLabel: '应用ID',
		                   allowBlank: false,
		                   flex:1,
		                   name: 'appid',
		                   id:'_appid'
		               },{
		            	   xtype: 'textfield',
		                   fieldLabel: '应用密钥',
		                   allowBlank: false,
		                   flex:1,
		                   name: 'appsecret',
		                   id:'_appsecret'
	                   }]
	             },{
	            	 xtype: 'fieldset',
			            title: '公众号属性',
				    	 anchor: '100%',
				    	margin:'10 10 0 50',
				    	padding:'10 10 10 10',
	            	   layout: {
	                       type: 'hbox',
//	                       pack:'center',
	                       align: 'middle',
	                       flex:'Ratio'
	                   },
	                   items:[{
					        xtype: 'combo',
					        store:propertysStore,
					        displayField:'name', 
					        valueField  : 'id',    //真实的字段
					        fieldLabel: '大类',
					        selectOnTab: false,
					        labelWidth:30,
					        flex:1,
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
//					        margin:'0 0 0 34',
					        labelWidth:30,
					        fieldLabel: '小类',
					        displayField:'name', 
					        valueField  : 'id',    //真实的字段
					        selectOnTab: false,
					        allowBlank: false,
					        flex:1,
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
			            },{
			            	xtype: 'combo',
					        store:userStore,
					        labelWidth:50,
					        fieldLabel: '广告主',
					        displayField:'name', 
					        valueField  : 'id',    //真实的字段
					        selectOnTab: false,
					        allowBlank: false,
					        flex:1,
					        name: 'user_adv',
					        id:'_user_adv',
					        emptyText:'请选择...',
//		            	   xtype: 'textfield',
//		                   fieldLabel: '广告主',
//		                   labelWidth:50,
//		                   allowBlank: false,
//		                   flex:2,
//		                   name: 'user_adv',
//		                   id:'_user_adv'
	                   }]
	            },{
			    	xtype: 'fieldset',
		            title: '优先投放的条件',
			    	 anchor: '100%',
			    	margin:'10 10 0 50',
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
	              }
	             ],buttons: [{
		                text: '提交',
		                handler: function() {
		                	this.up('form').getForm().submit({
	                                clientValidation:true,
	                                waitMsg:'请稍候',
	                                waitTitle:'正在更新',
	                                url: rootUrl+'/wechatmancontroller/addorUpdateWechat',
	                                success:function(form,action){
	                                	if(action&&action.result&&action.result.success){
	                                		if(action.result.success=="true"){
	                                			trStore.reload();
	                                		}else if(action.result.success=="false"){
//	                                			Ext.Msg.alert('温馨提示',"上传失败!");
	                                			Ext.Msg.show({
	                                				title : '提示',
	                                				msg : "上传失败!",
	                                				width : 250,
	                                				icon : Ext.Msg.ERROR,//INFO,ERROR,QUESTION,WARNING
	                                				buttonText: { ok: '确定' }
	                                			});
	                                		}
	                                	}else{
//	                                		Ext.Msg.alert('温馨提示',"上传失败!");
	                                		Ext.Msg.show({
                                				title : '提示',
                                				msg : "上传失败!",
                                				width : 250,
                                				icon : Ext.Msg.ERROR,//INFO,ERROR,QUESTION,WARNING
                                				buttonText: { ok: '确定' }
                                			});
	                                	}
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
         * 公众号的面板
         */
        var win = Ext.create('widget.window', {
                title: '公众号',
                header: {
                    titlePosition: 2,
                    titleAlign: 'center'
                },
                closable: false,
                modal: true,
                closeAction: 'hide',
                width: 550,
                minWidth: 350,
                height: 550,
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
                    overflowY:'auto',
                    items:[simple]
                }
            });
        /**
         * 新增按钮
         */
        var addAction = Ext.create('Ext.Action', {
        	iconCls:'common_add',
            text: '新增',
            handler: function(widget, event) {
            	win.show(this,function(){
            		Ext.getCmp("simpleForm").getForm().reset();
            		win.setTitle("新增公众号");
            		Ext.getCmp('multiCondid').removeAll();
            		putConditionid=0;
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
            	trStore.reload();
            }
        });
        /**
         * 删除按钮
         */
        var deleteAction = Ext.create('Ext.Action', {
        	iconCls:'common_del',
            text: '删除投放条件',
            handler: function(widget, event) {

            	 var rec = me.getSelectionModel().getSelection();
                 if(rec){
                 	 if(rec.length==0){
//                      	Ext.Msg.alert('温馨提示',"请先选中一条!");
                 		Ext.Msg.show({
            				title : '提示',
            				msg : "请先选中一条!",
            				width : 250,
            				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
            				buttonText: { ok: '确定' }
            			});
                 	 }else if(rec.length>0){
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
	                             	    url:rootUrl+'/wechatmancontroller/deleteWechat',//addOrUpdateUrL,
	                             	    async: false,
	                             	    params: {
	                             	    	ids: params.toString()
	                     			    },
	                             	    success: function(response){
	                             	    	var respText = Ext.JSON.decode(response.responseText);
//	                     			    	Ext.Msg.alert('温馨提示',respText.data);
	                             	    	Ext.Msg.show({
                                				title : '提示',
                                				msg : respText.data,
                                				width : 250,
                                				icon : Ext.Msg.INFO,//INFO,ERROR,QUESTION,WARNING
                                				buttonText: { ok: '确定' }
                                			});
	                     			    	trStore.reload();
	                             	    }
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
         * 更改按钮
         */
        var updateAction = Ext.create('Ext.Action', {
        	iconCls:'common_edit',
            text: '更改公众号',
            handler: function(widget, event) {
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
//                		 Ext.Msg.alert('温馨提示',"您选中了多条记录!</br>编辑只能选择一条");
                		 Ext.Msg.show({
             				title : '提示',
             				msg : "您选中了多条记录!</br>编辑只能选择一条",
             				width : 250,
             				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
             				buttonText: { ok: '确定' }
             			});
                	 }else{
                		 var tempdata=rec[0].data;
                			win.show(this,function(){
                				win.setTitle("更新");
                				Ext.getCmp('_id').setValue(tempdata.id);
                				Ext.getCmp('_name').setValue(tempdata.name);
                				Ext.getCmp('_identify').setValue(tempdata.identify);
								Ext.getCmp('_introduce').setValue(tempdata.introduce);
								Ext.getCmp('_type').setValue(tempdata.type);
								Ext.getCmp('_wechatid').setValue(tempdata.wechatid);
								Ext.getCmp('_address').setValue(tempdata.address);
								Ext.getCmp('_phone').setValue(tempdata.phone);
								Ext.getCmp('_owner').setValue(tempdata.owner);
								Ext.getCmp('_maininfo').setValue(tempdata.maininfo);
								Ext.getCmp('_orgid').setValue(tempdata.orgid);
								Ext.getCmp('_email').setValue(tempdata.email);
								Ext.getCmp('_appid').setValue(tempdata.appid);
								Ext.getCmp('_appsecret').setValue(tempdata.appsecret);
								Ext.getCmp('_user_adv').setValue(tempdata.user_adv);
//								Ext.getCmp('_user_adv').setValue('11262');
								Ext.getCmp('propertysid').setValue(tempdata.advertpropertys);
								Ext.getCmp('propertyid').setValue(tempdata.advertproperty);
//								Ext.getCmp('_user_adv').setValue(+(tempdata.user_adv));
//								Ext.getCmp('propertyid').setValue(+(tempdata.advertproperty));
//								Ext.getCmp('propertysid').setValue(+(tempdata.advertpropertys));
								 me.getSelectionModel().deselectAll();
	                				Ext.getCmp('multiCondid').removeAll();
		                       		 Ext.Ajax.request({
		                         	    url:rootUrl+'/getAdvertController/getConditionByAdvid',
		                         	    async: false,
		                         	    params: {
		                 			        id: tempdata.id
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
                			});
                	 }
                }
            }
        });
    	
   	 /**
   	  * 得到所有公众号信息
   	  */
   	 var trStore=Ext.create('Ext.data.Store', {
   		    model: putMangerSetModel,
   		    pageSize:defaultPageSize,
   		    proxy: {
   		    	type: 'ajax',
   		    	url:rootUrl+'/wechatmancontroller/queryWechat',
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
              flex: 1,
              align:'center',
              hidden:true,
              dataIndex: 'id',
              sortable: true
            },{
           	  text: '公众号名称',
                flex: 1,
                align:'center',
                dataIndex: 'name',
                sortable: true
            },{
         	  text: '微信号',
              flex: 1,
              align:'center',
              dataIndex: 'wechatid',
              sortable: true
            },{
           	  	text: '类型',
                flex: 1,
                align:'center',
                dataIndex: 'type',
                sortable: true  
            },{
         	  text: '介绍',
              flex: 1,
              align:'center',
              dataIndex: 'introduce',
              sortable: true  
            },{
            	text: '认证情况',
                flex: 1,
                align:'center',
                dataIndex: 'identify',
                sortable: true  
            },{
         	  text: '客服电话',
              flex: 1,
              align:'center',
              dataIndex: 'phone',
              sortable: true  
            },{
           	  	text: '所在地址',
	            flex: 1,
	            align:'center',
	            dataIndex: 'address',
	            sortable: true 
            },{
         	  text: '主体信息',
              flex: 1,
              align:'center',
              dataIndex: 'maininfo',
              sortable: true 
            },{
           	  text: '主体信息',
	            flex: 1,
	            align:'center',
	            dataIndex: 'maininfo',
	            sortable: true 
            },{
         	  text: '运营者',
              flex: 1,
              align:'center',
              dataIndex: 'owner',
              sortable: true 
            },{
           	  text: '登录邮箱',
                flex: 1,
                align:'center',
                dataIndex: 'email',
                sortable: true 
            },{
         	  text: '原始ID',
              flex: 1,
              align:'center',
              dataIndex: 'orgid',
              sortable: true 
            },{
           	  text: '应用ID',
              flex: 1,
              align:'center',
              dataIndex: 'appid',
              sortable: true 
            },{
         	  text: '应用密钥',
              flex: 1,
              align:'center',
              dataIndex: 'appsecret',
              sortable: true 
            },{
           	  text: '请求令牌',
              flex: 1,
              align:'center',
              dataIndex: 'accesstoken',
              sortable: true 
            },{
         	  text: '创建者',
              flex: 1,
              align:'center',
              dataIndex: 'creator',
              sortable: true 
            },{
           	  	text: '创建时间',
                flex: 1,
                align:'center',
                dataIndex: 'creatime',
                sortable: true 
            },{
         	  text: '修改者',
              flex: 1,
              align:'center',
              dataIndex: 'modifyer',
              sortable: true   
            },{
           	  text: '修改时间',
                flex: 1,
                align:'center',
                dataIndex: 'modifytime',
                sortable: true  
            },{ 
             	  text: '所属广告主',
                  flex: 1,
                  align:'center',
                  dataIndex: 'user_adv',
                  sortable: true  
            },{
             	  text: '小类属性',
                  flex: 1,
                  align:'center',
                  dataIndex: 'advertproperty',
                  sortable: true  
            },{
             	  text: '大类属性',
                  flex: 1,
                  align:'center',
                  dataIndex: 'advertpropertys',
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
    name: 'putMangerSet',
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
				items:mmppGrid
			}]

    	});
    }
});