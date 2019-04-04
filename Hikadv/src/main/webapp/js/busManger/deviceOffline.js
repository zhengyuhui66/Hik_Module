
var deviceOfflineGrid=Ext.define('Ext.deviceOfflineGrid.TreeGrid', {
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
    	var me = this;
//    	/**
//    	 * 车辆线路模型
//    	 */
//    	Ext.define('COMMONMODEL', {
//    	        extend: 'Ext.data.Model',
//    	        fields: ["id","name"]
//    	    });
//    	var routeStore = Ext.create('Ext.data.Store', {
//    		model : 'COMMONMODEL',
//    		proxy : {
//    			type : 'ajax',
//    			url : rootUrl + '/common/queryRoadInfo',
//    			reader : {
//    				type : 'json',
//    				root : 'data'
//    			}
//    		},
//    		autoLoad:true
//    	});
    	
    	
//    	var busStore = Ext.create('Ext.data.Store', {
//    		model : 'COMMONMODEL',
//    		proxy : {
//    			type : 'ajax',
//    			url : rootUrl + '/common/queryBusInfo',
//    			reader : {
//    				type : 'json',
//    				root : 'data'
//    			}
//    		}
//    	});
    	/**
    	 * 线路信息模型
    	 */
    	Ext.define('corMangerModel', {
    	    extend: 'Ext.data.Model',
    	    fields:["id","equipmentid","apmac","vehicleid","busname","routeid","routename","onlinetime","offlinetime","alarmtype","isoffline"]
    	});
//        var simple = Ext.widget({
//            xtype: 'form',
//            id: 'simpleForm',
//            fieldDefaults: {
//                labelWidth : 100,
//                labelAlign : 'right'
//            },
//            padding:'20 20 0 0',
//            border:false,
//           layout: {
//                     type: 'vbox',
//                     align: 'stretch'
//	                 },
//		             defaults: {
//		                 anchor: '100%',
//		            	 xtype: 'container',
//		                 layout: 'hbox',
//		                 border:1,
//		                 defaultType: 'textfield',
//		                 margin: '0 0 5 0'
//		             },
//	                 items:[{
//                	   xtype: 'textfield',
//	                   fieldLabel: 'ID',
//	                   name: 'id',
//	                   hidden:true,
//	                   id:'_id'
//	               },{
//	            	   xtype: 'textfield',
//	                   fieldLabel: '设备编号',
//	                   emptyText : '14306073Xxxxxxxxxxxxx',
//	                   allowBlank: false,
//	                   name: 'equipmentid',
//	                   id:'_equipmentid'
//	               },{
//	            	   xtype: 'textfield',
//	                   fieldLabel: '设备MAC地址',
//	                   emptyText : 'XX-XX-XX-XX-XX-XX',
//	                   allowBlank: false,
//	                   name: 'apmac',
//	                   id:'_apmac'
//	               },{
//	            	   xtype: 'textfield',
//	                   fieldLabel: '上报时间(分钟)',
//	                   allowBlank: false,
//	                   name: 'reporttime',
//	                   id:'_reporttime'
//	               },{
//	            	   xtype: 'textfield',
//	                   fieldLabel: '上网速度(Kbps)',
//	                   allowBlank: false,
//	                   name: 'speed',
//	                   id:'_speed'
//	               },{
//	            	   xtype: 'textfield',
//	                   fieldLabel: '上网超时(分钟)',
//	                   allowBlank: false,
//	                   name: 'timeout',
//	                   id:'_timeout'
//	               },{
//						items:[{
//									xtype:'combo',
//			   		    		   store: routeStore,
//				                   fieldLabel: '线路',
//				                   displayField: 'name',
//			   		    		   valueField: 'id',
//			   		    		   minChars: 1,
//				                   name: 'routeid',
//				                   id:'_routeid',
//				                   listeners:{
//				                	   change:function(me, newValue, oldValue, eOpts){
//				                		   var tempValue=me.rawValue;
//				                		   routeStore.getProxy().extraParams = {linename:tempValue};
//				                		   routeStore.load();
//				                		   Ext.getCmp('_vehicleid').setValue('');
//				                	   },
//				                	   select:function(combo, records, eOpts){
//				                		   if(records.length>0){
//					                		   var newValue=records[0].data.id;
//					                		   busStore.getProxy().extraParams = {lineId:newValue};
//					                		   busStore.load();
//				                		   }
//				                	   }
//				                   }
//				               },{
//				            	   xtype:'combo',
//				            	   labelWidth : 48,
//			   		    		   store: busStore,
//			   		    		   displayField: 'name',
//			   		    		   valueField: 'id',
//			   		    		   minChars: 1,
//				                   fieldLabel: '车辆',
//				                   name: 'vehicleid',
//				                   id:'_vehicleid',
//				                   listeners:{
//				                	   change:function(me, newValue, oldValue, eOpts){
//				                		   var tempValue=me.rawValue;
//				                		   var lineId = Ext.getCmp('_routeid').getValue();
//				                		   busStore.getProxy().extraParams = {lineId:lineId,busName:newValue};
//				                		   busStore.load();
//				                	   },
//				                   }
//				               }]
//	               },{
//	            	   xtype: 'textareafield',
//	                   fieldLabel: '备注',
//	                   name: 'mark',
//	                   id:'_mark' 
//		           }],buttons: [{
//			                text: '提交',
//			                handler: function() {
//			                	this.up('form').getForm().submit({
//		                                clientValidation:true,
//		                                waitMsg:'请稍候',
//		                                waitTitle:'正在更新',
//		                                url: rootUrl+'/devicemangercontroller/addorUpdatedevicemanger',
//		                                success:function(form,action){
//		                                	if(action&&action.result&&action.result.success){
//		                                		if(action.result.success=="true"){
//		                                			trStore.reload();
//		                                		}else if(action.result.success=="false"){
//		                                			Ext.Msg.alert('温馨提示',"上传失败!");
//		                                		}
//		                                	}else{
//		                                		Ext.Msg.alert('温馨提示',"上传失败!");
//		                                	}
//		                                	win.close();
//		                                },  
//		                                failure:function(form,action){
//		                                	
//		                                }
//		                            });
//			                }
//			            },{
//			                text: '取消',
//			                handler: function(){
//			                	this.up('form').getForm().reset();
//			                	 win.close();
//			                }
//			            }]
//			        });
//        /**
//         * 车辆信息的面板
//         */
//        var win = Ext.create('widget.window', {
//                title: '投放条件',
//                header: {
//                    titlePosition: 2,
//                    titleAlign: 'center'
//                },
//                closable: false,
//                closeAction: 'hide',
//                width: 550,
//                minWidth: 350,
//                height: 350,
//                layout:'fit',
//                items:[simple]
//            });
//        /**
//         * 新增按钮
//         */
//        var addAction = Ext.create('Ext.Action', {
//        	iconCls:'common_add',
//            text: '新增',
//            handler: function(widget, event) {
//            	win.show(this,function(){
//            		Ext.getCmp("simpleForm").getForm().reset();
//            		win.setTitle("新增设备");
//    			});
//            }
//        });
//        
//        /**
//         * 刷新按钮
//         */
//        var refreshAction = Ext.create('Ext.Action', {
//        	iconCls:'common_refresh',
//            text: '刷新',
//            handler: function(widget, event) {
//            	trStore.reload();
//            }
//        });
//        
//        var exportAction=Ext.create('Ext.Action', {
//        	iconCls:'common_excel',
//            text: '导出Excel',
//            handler: function(widget, event) {
//            	var url=rootUrl+'/devicemangercontroller/exportdevicemanger';
//        		var title='设备信息';
//            	exportExcel(me,url,title);
//            }
//        });
//        /**
//         * 删除按钮
//         */
//        var deleteAction = Ext.create('Ext.Action', {
//        	iconCls:'common_del',
//            text: '删除',
//            handler: function(widget, event) {
//            	 var rec = me.getSelectionModel().getSelection();
//                 if(rec){
//                 	 if(rec.length==0){
//                      	Ext.Msg.alert('温馨提示',"请先选中一条!");
//                 	 }else if(rec.length>0){
//                 		var params=new Array();
//                    	for(var i=0;i<rec.length;i++){
//                    		params.push(rec[i].data.id);
//                    	}
//                    	
//                     	Ext.Msg.show({
//                    	     title:'温馨提示',
//                     	     msg: '确定要删除吗?',
//                     	    buttonText: {yes:'是', no:'取消'},
//                     	     icon: Ext.Msg.QUESTION,
//                     	     fn:function(btn,txt){
//                     	    	 if(btn=="yes"){
//                                 	Ext.Ajax.request({
//                             	    url:rootUrl+'/devicemangercontroller/deletedevicemanger',//addOrUpdateUrL,
//                             	    async: false,
//                             	    params: {
//                             	    	ids: params.toString()
//                     			    },
//                             	    success: function(response){
//                             	    	var respText = Ext.JSON.decode(response.responseText);
//                     			    	Ext.Msg.alert('删除成功',"共删除"+respText.data+"条记录");
//                     			    	trStore.reload();
//                             	    }
//                             	});
//                     	    		
//                     	    	 }
//                     	     }
//                   		});
//
//                 	 }
//                 }
//            }
//        });
//        
//        /**
//         * 更改按钮
//         */
//        var updateAction = Ext.create('Ext.Action', {
//        	iconCls:'common_edit',
//            text: '更改设备信息',
//            handler: function(widget, event) {
//                var rec = me.getSelectionModel().getSelection();
//                if(rec){
//                	 if(rec.length==0){
//                     	Ext.Msg.alert('温馨提示',"请先选中一条!");
//                	 }else if(rec.length>1){
//                		 Ext.Msg.alert('温馨提示',"您选中了多条记录!</br>编辑只能选择一条");
//                	 }else{
//                		 var tempdata=rec[0].data;
//                			win.show(this,function(){
//                			
//                				win.setTitle("更新线路信息");
//                				Ext.getCmp('_id').setValue(tempdata.id);
//        						Ext.getCmp('_equipmentid').setValue(tempdata.equipmentid);
//        						Ext.getCmp('_apmac').setValue(tempdata.apmac); 
//	            				 Ext.getCmp('_reporttime').setValue(tempdata.reporttime); 
//	    						 Ext.getCmp('_routeid').setValue(+(tempdata.routeid)); 
//								 Ext.getCmp('_vehicleid').setValue(+(tempdata.vehicleid)); 
//								 Ext.getCmp('_speed').setValue(tempdata.speed); 
//								 Ext.getCmp('_timeout').setValue(tempdata.timeout);
//								 Ext.getCmp('_mark').setValue(tempdata.mark);
////    		                	Ext.getCmp('_id').setValue(tempdata.id);
////			                	Ext.getCmp('_name').setValue(tempdata.name);
////			                	Ext.getCmp('_state').setValue(tempdata.state);
////			                	Ext.getCmp('_mark').setValue(tempdata.mark);
//
//                			});
//                	 }
//                }
//            }
//        });
    	
   	 /**
   	  * 得到所有用户信息
   	  */
   	 var trStore=Ext.create('Ext.data.Store', {
   		    model: corMangerModel,
   		    pageSize:defaultPageSize,
   		    proxy: {
   		    	type: 'ajax',
   		    	url:rootUrl+'/deviceofflinecontroller/queryofflinemanger',
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
              dataIndex: 'id',
              hidden:true
            },{//"id","equipmentid","apmac","vehicleid","isonline","lastonlinetime","lastreporttime","reporttime","state","creator","modifier","createtime","modifytime","mark","ver","speed","timeout"
           	  text: '设备编号',
	          flex: 1,
	          align:'center',
	          dataIndex: 'equipmentid',
	          sortable: true
            },{
         	  text: 'MAC',
              flex: 1,
              align:'center',
              dataIndex: 'apmac',
              sortable: true
            },{
         	  text: '车辆ID',
              flex: 1,
              hidden:true,
              align:'center',
              dataIndex: 'vehicleid',
              sortable: true
            },{
           	  text: '车辆名称',
                flex: 1,
                align:'center',
                dataIndex: 'busname',
                sortable: true
            },{
           	  text: '线路名称',
                flex: 1,
                align:'center',
                dataIndex: 'routename',
                sortable: true
            },{
           	  text: '线路ID',
                flex: 1,
                align:'center',
                hidden:true,
                dataIndex: 'routeid',
                sortable: true
            },{
           	  text: '在线时间',
              flex: 1,
              align:'center',
              dataIndex: 'onlinetime',
              sortable: true
            },{
         	  text: '下线时间',
              flex: 1,
              align:'center',
              dataIndex: 'offlinetime',
              sortable: true   
            },{
           	  text: '警告标识',
              flex: 1,
              align:'center',
              dataIndex: 'alarmtype',
              sortable: true   
            },{
         	  text: '是否在线',
              flex: 1,
              align:'center',
              dataIndex: 'isoffline',
              sortable: true,
              renderer:function(value){
                	if(value=='true'){
                		return '<font face="arial" color="green" >在线</font>';
                	}
                	if(value=='false'){
                		return '<font face="arial" color="red" >离线</font>';
                	}
              }
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
            },tbar: ['->',
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
    name: 'mainRoute',
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
				items:deviceOfflineGrid
			}]

    	});
    }
});