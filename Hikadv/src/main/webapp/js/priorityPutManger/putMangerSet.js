
var putMangerSetGrid=Ext.define('Ext.putMangerSetGrid.TreeGrid', {
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
    	 * 广告模型
    	 */
    	Ext.define('baseComboModel', {
    	    extend: 'Ext.data.Model',
    	    fields: [
    	             {name: 'id', type: 'string'},
    	             {name: 'name', type: 'string'}
    	    ]
    	});   	
    	var eventStore = Ext.create('Ext.data.Store', {
    		model : 'baseComboModel',
    		proxy : {
    			type : 'ajax',
    			url : rootUrl + '/common/queryProEvent',
    			reader : {
    				type : 'json',
    				root : 'data'
    			}
    		},
    		autoLoad:true
    	});
    	
//    	var clientTypeStore = Ext.create('Ext.data.Store', {
//    		model : 'baseComboModel',
//    		proxy : {
//    			type : 'ajax',
//    			url : rootUrl + '/common/queryProClientType',
//    			reader : {
//    				type : 'json',
//    				root : 'data'
//    			}
//    		},
//    		autoLoad:true
//    	});
//    	
//    	var clientSysStore = Ext.create('Ext.data.Store', {
//    		model : 'baseComboModel',
//    		proxy : {
//    			type : 'ajax',
//    			url : rootUrl + '/common/queryProClientSys',
//    			reader : {
//    				type : 'json',
//    				root : 'data'
//    			}
//    		},
//    		autoLoad:true
//    	});

    	var phoneStore = Ext.create('Ext.data.Store', {
    		model : 'baseComboModel',
    		proxy : {
    			type : 'ajax',
    			url : rootUrl + '/common/queryProPhone',
    			reader : {
    				type : 'json',
    				root : 'data'
    			}
    		},
    		autoLoad:true
    	});
    	
    	var addressStore = Ext.create('Ext.data.Store', {
    		model : 'baseComboModel',
    		proxy : {
    			type : 'ajax',
    			url : rootUrl + '/common/queryProAddress',
    			reader : {
    				type : 'json',
    				root : 'data'
    			}
    		},
    		autoLoad:true
    	});
    	var timeStore = Ext.create('Ext.data.Store', {
    		model : 'baseComboModel',
    		proxy : {
    			type : 'ajax',
    			url : rootUrl + '/common/queryProTime',
    			reader : {
    				type : 'json',
    				root : 'data'
    			}
    		},
    		autoLoad:true
    	});
    	
    	/**
    	 * 广告模型
    	 */
    	Ext.define('putMangerSetModel', {
    	    extend: 'Ext.data.Model',
    	    fields: [
    	             {name: 'id', type: 'string'},
//    	             {name: 'stime', type: 'string'},
    	             {name: 'timeid', type: 'string'},
    	             {name: 'timename', type: 'string'},
    	             {name: 'addredssid', type: 'string'},
    	             {name: 'addressname', type: 'string'},
    	             {name: 'eventid', type: 'string'},
    	             {name: 'eventname', type: 'string'},
//    	             {name: 'phonetype', type: 'string'},
//    	             {name: 'ptypename', type: 'string'},
//    	             {name: 'phonesystem', type: 'string'},
//    	             {name: 'psysname', type: 'string'},
    	             {name: 'phone', type: 'string'},
    	             {name: 'pname', type: 'string'},
//    	             {name: 'clicktype', type: 'string'},
//    	             {name: 'viewtype', type: 'string'},
    	             {name: 'creator', type: 'string'},
    	             {name: 'creatime', type: 'string'},
    	             {name: 'modifier', type: 'string'},
    	             {name: 'modifytime', type: 'string'},
    	             {name: 'descr', type: 'string'},
    	             {name: 'name', type: 'string'}
    	    ]
    	});
//        var stime=new Date();
//        stime.setDate(stime.getDate()-7);
//        var etime=new Date();
        var simple = Ext.widget({
            xtype: 'form',
            id: 'simpleForm',
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
                 defaultType: 'textfield',
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
	            	   
	            	   xtype: 'combo',
	                   fieldLabel: '发生时间',
   		    		   displayField: 'name',
   		    		   store:timeStore,
   		    		   valueField: 'id',
	                   name: 'timeid',
	                   id:'_timeid'
                 },{
	            	   xtype: 'combo',
	                   fieldLabel: '发生地址',
   		    		   displayField: 'name',
   		    		   store:addressStore,
   		    		   valueField: 'id',
	                   name: 'addredssid',
	                   id:'_addredssid'
	               },{
	            	   xtype: 'combo',
	                   fieldLabel: '发生事件',
	                   store:eventStore,
   		    		   displayField: 'name',
   		    		   valueField: 'id',
	                   name: 'eventid',
	                   id:'_eventid'
	               },{
                	   xtype: 'combo',
	                   fieldLabel: '手机号',
	                   store:phoneStore,
   		    		   displayField: 'name',
   		    		   valueField: 'id',
	                   name: 'phone',
	                   id:'_phone'
//	            	   items:[{
//	                    	   flex:1,
//	                    	   xtype: 'combo',
//	    	                   fieldLabel: '手机号',
//	    	                   store:phoneStore,
//	       		    		   displayField: 'name',
//	       		    		   valueField: 'id',
//	    	                   name: 'phone',
//	    	                   id:'_phone'
//	    	               },{
//	    	            	   flex:1,
//	    	            	   xtype: 'combo',
//	    	                   fieldLabel: '手杨型号',
//	    	                   name: 'phonetype',
//	    	                   store:clientTypeStore,
//	       		    		   displayField: 'name',
//	       		    		   valueField: 'id',
//	    	                   id:'_phonetype'
//	    	               },{
//	    	            	   flex:1,
//	    	            	   xtype: 'combo',
//	    	            	   store:clientSysStore,
//	    	                   fieldLabel: '手机操作系统',
//	    	                   name: 'phonesystem',
//	       		    		   displayField: 'name',
//	       		    		   valueField: 'id',
//	    	                   id:'_phonesystem'
//		    	           }]
//	                 },{
//		            	   xtype: 'textareafield',
//		                   fieldLabel: '广告点击习惯',
//		                   height:40,
//		                   name: 'clicktype',
//		                   id:'_clicktype'
//		               },{
//		            	   xtype: 'textareafield',
//		                   fieldLabel: '用户浏览习惯',
//		                   height:40,
//		                   name: 'viewtype',
//		                   id:'_viewtype'
		               }],buttons: [{
			                text: '提交',
			                handler: function() {
			                	var t1=Ext.getCmp('_id').getValue();
			                	var t2=Ext.getCmp('_name').getValue();
			                	var t3=Ext.getCmp('_timeid').getValue();
//			                	var t4=Ext.getCmp('_etime').getValue();	
								var t5=Ext.getCmp('_addredssid').getValue();
								var t6=Ext.getCmp('_eventid').getValue();
								var t7=Ext.getCmp('_phone').getValue();
//								var t8=Ext.getCmp('_phonetype').getValue();
//								var t9=Ext.getCmp('_phonesystem').getValue();
//								var t10=Ext.getCmp('_clicktype').getValue();
//								var t11=Ext.getCmp('_viewtype').getValue();
								if(!t1&&!t2&&!t3&&!t5&&!t6&&!t7/*&&!t8&&!t9&&!t10&&!t11*/){
									alert('未填信息');
									return;
								}
			                	this.up('form').getForm().submit({
		                                clientValidation:true,
		                                waitMsg:'请稍候',
		                                waitTitle:'正在更新',
		                                url: rootUrl+'/putmangersetcontroller/addorUpdateAdvputSet',
		                                success:function(form,action){
		                                	if(action&&action.result&&action.result.success){
		                                		if(action.result.success=="true"){
		                                			trStore.reload();
		                                		}else if(action.result.success=="false"){
//		                                			Ext.Msg.alert('温馨提示',"上传失败!");
		                                			Ext.Msg.show({
		    		          	         				title : '温馨提示',
		    		          	         				msg :"上传失败!",
		    		          	         				width : 250,
		    		          	         				icon : Ext.Msg.ERROR,//INFO,ERROR,QUESTION,WARNING
		    		          	         				buttonText: { ok: '确定'}
		    		          	         			});
		                                		}
		                                	}else{
//		                                		Ext.Msg.alert('温馨提示',"上传失败!");
		                                		Ext.Msg.show({
	    		          	         				title : '温馨提示',
	    		          	         				msg :"上传失败!",
	    		          	         				width : 250,
	    		          	         				icon : Ext.Msg.ERROR,//INFO,ERROR,QUESTION,WARNING
	    		          	         				buttonText: { ok: '确定'}
	    		          	         			});
		                                	}
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
         * 车辆信息的面板
         */
        var win = Ext.create('widget.window', {
                title: '投放条件',
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
            		win.setTitle("新增广告");
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
  	         				title : '温馨提示',
  	         				msg :"请先选中一条!",
  	         				width : 250,
  	         				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
  	         				buttonText: { ok: '确定'}
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
	                             	    url:rootUrl+'/putmangersetcontroller/deleteAdvputSet',//addOrUpdateUrL,
	                             	    async: false,
	                             	    params: {
	                             	    	ids: params.toString()
	                     			    },
	                             	    success: function(response){
	                             	    	var respText = Ext.JSON.decode(response.responseText);
//	                     			    	Ext.Msg.alert('温馨提示',"成功删除了"+respText.data+"条记录");
	                             	    	Ext.Msg.show({
	                   	         				title : '温馨提示',
	                   	         				msg :"成功删除了"+respText.data+"条记录",
	                   	         				width : 250,
	                   	         				icon : Ext.Msg.INFO,//INFO,ERROR,QUESTION,WARNING
	                   	         				buttonText: { ok: '确定'}
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
         * 更改物料按钮
         */
        var updateAction = Ext.create('Ext.Action', {
        	iconCls:'common_edit',
            text: '更改投放条件',
            handler: function(widget, event) {
                var rec = me.getSelectionModel().getSelection();
                if(rec){
                	 if(rec.length==0){
//                     	Ext.Msg.alert('温馨提示',"请先选中一条!");
                		 Ext.Msg.show({
   	         				title : '温馨提示',
   	         				msg :"请先选中一条!",
   	         				width : 250,
   	         				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
   	         				buttonText: { ok: '确定'}
   	         			});
                	 }else if(rec.length>1){
//                		 Ext.Msg.alert('温馨提示',"您选中了多条记录!</br>编辑只能选择一条");
                		 Ext.Msg.show({
   	         				title : '温馨提示',
   	         				msg :"您选中了多条记录!</br>编辑只能选择一条",
   	         				width : 250,
   	         				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
   	         				buttonText: { ok: '确定'}
   	         			});
                	 }else{
                		 var tempdata=rec[0].data;
                			win.show(this,function(){
                			
                				win.setTitle("更新");
                				Ext.getCmp("simpleForm").getForm().reset();
    		                	Ext.getCmp('_id').setValue(tempdata.id);
			                	Ext.getCmp('_name').setValue(tempdata.name);
			                	Ext.getCmp('_timeid').setValue(tempdata.timeid);
//			                	Ext.getCmp('_stime').setValue(tempdata.stime);
//			                	Ext.getCmp('_etime').setValue(tempdata.etime);
								Ext.getCmp('_addredssid').setValue(tempdata.addredssid);
								Ext.getCmp('_eventid').setValue(tempdata.eventid);
								Ext.getCmp('_phone').setValue(tempdata.phone);
//								Ext.getCmp('_phonetype').setValue(tempdata.phonetype);
//								Ext.getCmp('_phonesystem').setValue(tempdata.phonesystem);
//								Ext.getCmp('_clicktype').setValue(tempdata.clicktype);
//								Ext.getCmp('_viewtype').setValue(tempdata.viewtype);
								 me.getSelectionModel().deselectAll();

                			});
                	 }
                }
            }
        });
    	
   	 /**
   	  * 得到所有用户信息
   	  */
   	 var trStore=Ext.create('Ext.data.Store', {
   		    model: putMangerSetModel,
   		    pageSize:defaultPageSize,
   		    proxy: {
   		    	type: 'ajax',
   		    	url:rootUrl+'/putmangersetcontroller/queryAdvputSet',
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
//            },{
//         	  text: '开始时间',
//              flex: 1,
//              align:'center',
//              dataIndex: 'stime',
//              sortable: true
              
//              TIMENAME
//      		ADDRESSNAME
//      		EVENTNAME
//      		PTYPENAME
//      		PSYSNAME
//      		PNAME
            },{
//         	  text: '时间',
//              flex: 1,
//              align:'center',
              dataIndex: 'timeid',
              hidden:true
//              sortable: true
            },{
           	  text: '时间',
                flex: 1,
                align:'center',
                dataIndex: 'timename',
                sortable: true
            },{
//         	  text: '发生地址',
//              flex: 1,
//              align:'center',
              dataIndex: 'addredssid',
              hidden:true
//              sortable: true
            },{
           	  text: '发生地址',
                flex: 1,
                align:'center',
                dataIndex: 'addressname',
                sortable: true
            },{
//         	  text: '发生事件',
//              flex: 1,
//              align:'center',
              dataIndex: 'eventid',
              hidden:true
//              sortable: true
            },{
           	  text: '发生事件',
                flex: 1,
                align:'center',
                dataIndex: 'eventname',//'eventname',
                sortable: true
            },{
//         	  text: '终端型号',
//              flex: 1,
//              align:'center',
              dataIndex: 'phonetype',
              hidden:true
//              sortable: true
//            },{
//           	  text: '终端型号',
//                flex: 1,
//                align:'center',
//                dataIndex: 'ptypename',
//                sortable: true
              
//            },{
//         	  text: '终端系统',
//              flex: 1,
//              align:'center',
//              dataIndex: 'phonesystem',
//              hidden:true
//              sortable: true
//            },{
//           	  text: '终端系统',
//                flex: 1,
//                align:'center',
//                dataIndex: 'psysname',
//                sortable: true
            },{
//         	  text: '手机号',
//              flex: 1,
//              align:'center',
              dataIndex: 'phone',
              hidden:true,
//              sortable: true
            },{
           	  text: '手机号',
                flex: 1,
                align:'center',
                dataIndex: 'pname',
                sortable: true
//            },{
//         	  text: '广告点击习惯属性',
//              flex: 1,
//              align:'center',
//              dataIndex: 'clicktype',
//              sortable: true
//            },{
//         	  text: '用户上网习惯属性',
//              flex: 1,
//              align:'center',
//              dataIndex: 'viewtype',
//              sortable: true
            },{
         	  text: '条件创建人',
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
         	  text: '修改人',
              flex: 1,
              align:'center',
              dataIndex: 'modifier',
              sortable: true
            },{
         	  text: '修改时间',
              flex: 1,
              align:'center',
              dataIndex: 'modifytime',
              sortable: true
            },{
         	  text: '说明',
              flex: 1,
              align:'center',
              dataIndex: 'descr',
              sortable: true
            },{
         	  text: '条件名称',
              flex: 1,
              align:'center',
              dataIndex: 'name',
              sortable: true
            }
            ],bbar: {
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
				items:putMangerSetGrid
			}]

    	});
    }
});