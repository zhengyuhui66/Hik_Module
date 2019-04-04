Ext.define('App.ux.deviceinfo',{
	extend:'Ext.form.Panel',
	xtype:'deviceinfo',
	config:{
		deviceid:'',
		state:'',
		ifonline:'',
		reporttime:'',
		speed:'',
		timeout:'',
		code:'',
		bustate:'',
	},
	border:false,
    frame: false,
    defaultType: 'fieldset',
    padding:'0 20 0 20',
	width:'100%',
	flex:1,
	initComponent : function(){
        	  this.items=[{
		        	   layout:{
		        	        type:'hbox',
		        	        align:'stretch'
		        	    },
		        	   items:[{
		        	    	flex:1,
		        	    	xtype: 'displayfield',
		        	    	labelAlign:'right',
		  	                fieldLabel: '车辆名称',
		  	                value:this.code
		        	    },{
		        	    	flex:1,
		        	    	xtype: 'displayfield',
		        	    	labelAlign:'right',
		  	                fieldLabel: '车辆状态',
		  	                value:this.bustate
		        	    	}]
	        	    },{
		        	   layout:{
		        	        type:'hbox',
		        	        align:'stretch'
		        	    },
		        	   items:[{
		        	    	flex:1,
		        	    	xtype: 'displayfield',
		        	    	labelAlign:'right',
		  	                fieldLabel: '设备ID',
		  	                value:this.deviceid
		        	    },{
		        	    	flex:1,
		        	    	xtype: 'displayfield',
		        	    	labelAlign:'right',
		  	                fieldLabel: '设备状态',
		  	                value:this.state
		        	    	}]
	        	    },{
	        	    	layout:{
	        	        	type:'hbox',
	        	        	align:'stretch'
	        	        },
	        	    	items:[{
	        	    		flex:1,
	        	    		xtype: 'displayfield',
	        	    		labelAlign:'right',
	  	                   	fieldLabel: '是否在线',
	  	                   	value: this.ifonline
	        	    	},{
	        	    		flex:1,
	        	    		xtype: 'displayfield',
	        	    		labelAlign:'right',
	  	                   	fieldLabel: '上报时间',
	  	                   	value: this.reporttime
	        	    	}]
	        	    },{
	        	    	layout:{
	        	        	type:'hbox',
	        	        	align:'stretch'
	        	        },
	        	    	items:[{
	        	    		flex:1,
	        	    		xtype: 'displayfield',
	  	                   	fieldLabel: '上网网速',
	  	                   	labelAlign:'right',
	  	                	value: this.speed
	        	    	},{
	        	    		flex:1,
	        	    		xtype: 'displayfield',
	  	                   	fieldLabel: '上网超时时间',
	  	                   	labelAlign:'right',
	  	                   	value:this.timeout
	        	    	}]
	          },{ xtype: 'displayfield',height:1, margin:'0 0 20 0', value: '<hr width=100% style="height:1px;border:none;border-top:1px dashed #177ebc;"/>' }]
		       this.callParent();
	}
})
var routeMangerGrid=Ext.define('Ext.routeMangerGrid.TreeGrid', {
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
    	/**
    	 * 线路信息模型
    	 */
    	Ext.define('corMangerModel', {
    	    extend: 'Ext.data.Model',
    	    fields:["id","name","state","createtime","creator","modifier","modifytime","mark"]
    	});
    	
    	
//    	var radiogroup= new Ext.form.RadioGroup({  
//            fieldLabel : "radioGroup",  
//            items : [{  
//                        boxLabel : '生效',  
//                        inputValue : "1",  
//                        name : "state",  
//                        checked : true  
//                    }, {  
//                        boxLabel : '失效',  
//                        name : "state",  
//                        inputValue : "0"  
//                    }]  
//        });
    	var getIfhavRoute=function(name){
    		flag=true;
    		$.ajax({
				type : "GET", //提交方式  
				url : rootUrl + '/common/queryIfhaveRouteInfo',//路径  
				async : false,
				data : {
					name:name
				},//数据，这里使用的是Json格式进行传输  
				success : function(result) {//返回数据根据结果进行相应的处理  
					var tempdata=eval('(' + result+ ')');
					if(tempdata.data.length==0){
					    flag=false;	
					}else{
						flag=true;
					}
					
				}
			});
    		return flag;
    	}
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
//             	defaults : {
//    				anchor : '100%',
    				margin : '10 0 10 0'
//    			},
//                 defaultType: 'textfield',
//                 margin: '0 0 5 0'
             },
                 items:[{
                	   xtype: 'textfield',
	                   fieldLabel: 'ID',
	                   name: 'id',
	                   hidden:true,
	                   id:'_id'
	               },{
	            	   xtype: 'textfield',
	                   fieldLabel: '线路名称',
	                   allowBlank: false,
	                   name: 'name',
	                   id:'_name'
                   },{
	                   xtype: 'radiogroup',
	                   fieldLabel: '线路状态',
	                   id:'_state',
	                   items: [
	                       {boxLabel: '正常', name: 'state', inputValue:'1',checked: true},
	                       {boxLabel: '注销', name: 'state', inputValue:'0',margin:'0 0 0 10'}
	                   ]
	               },{
	            	   xtype: 'textareafield',
	                   fieldLabel: '产品描述',
	                   height:40,
	                   margin:'30 0 0 0',
	                   name: 'mark',
	                   id:'_mark'
		            }],buttons: [{
			                text: '提交',
			                handler: function() {
			                	
			                	var _name=Ext.getCmp('_name').getValue();
			                	if(win.title=="新增线路"){
				                	var ifhave=getIfhavRoute(_name);
				                	if(ifhave){
				                		 Ext.Msg.show({
		                           				title : '提示',
		                           				msg :"提交失败!</br>线路重复录入",
		                           				width : 250,
		                           				icon : Ext.Msg.ERROR,//INFO,ERROR,QUESTION,WARNING
		                           				buttonText: { ok: '确定' }
		                           			});
				                		return;
				                	}
			                	}
			                	this.up('form').getForm().submit({
		                                clientValidation:true,
		                                waitMsg:'请稍候',
		                                waitTitle:'正在更新',
		                                url: rootUrl+'/routemangercontroller/addorUpdateroutemanger',
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
		                                   				buttonText: { ok: '确定' }
		                                   			});
		                                		}
		                                	}else{
//		                                		Ext.Msg.alert('温馨提示',"上传失败!");
		                                		Ext.Msg.show({
	                                   				title : '温馨提示',
	                                   				msg :"上传失败!",
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
                                   				title : '温馨提示',
                                   				msg :"上传失败!",
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
                height: 350,
                layout:'fit',
                items:[simple]
            });
        
        var setsimple = Ext.widget({
            xtype: 'form',
            id: 'setsimpleid',
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
                 margin : '10 0 10 0'
            },
                 items:[{
                	   xtype: 'textfield',
	                   fieldLabel: 'ID',
	                   name: 'id',
	                   hidden:true,
	                   id:'_setid'
	               },{
	            	   xtype: 'textfield',
	                   fieldLabel: '线路名称',
	                   allowBlank: false,
	                   name: 'name',
	                   id:'_setname'
                   },{
                	   xtype: 'textfield',
	                   fieldLabel: '用户下载速度(Kbps)',
	                   allowBlank: false,
	                   name: 'speed',
	                   id:'_setspeed'
	               },{
	            	   xtype: 'textfield',
	                   fieldLabel: '上网超时时间</br>(分钟)',
	                   allowBlank: false,
	                   name: 'timeout',
	                   id:'_settimeout'
	               },{
	            	   xtype: 'component',
	                   html: '<div style="margin-left:100px;">注:参数设置效果，请到相应的设备信息表里查询</div>'
		            }],buttons: [{
			                text: '提交',
			                handler: function() {
			                	this.up('form').getForm().submit({
		                                clientValidation:true,
		                                waitMsg:'请稍候',
		                                waitTitle:'正在更新',
		                                url: rootUrl+'/routemangercontroller/setting',
		                                success:function(form,action){
		                                	if(action&&action.result&&action.result.success){
		                                		if(action.result.success=="true"){
//		                                			Ext.Msg.alert('温馨提示',action.result.data);
		                                			Ext.Msg.show({
		                                   				title : '温馨提示',
		                                   				msg :action.result.data,
		                                   				width : 250,
		                                   				icon : Ext.Msg.INFO,//INFO,ERROR,QUESTION,WARNING
		                                   				buttonText: { ok: '确定' }
		                                   			});
		                                		}else if(action.result.success=="false"){
//		                                			Ext.Msg.alert('温馨提示',"设置失败!");
		                                			Ext.Msg.show({
		                                   				title : '温馨提示',
		                                   				msg :"设置失败!",
		                                   				width : 250,
		                                   				icon : Ext.Msg.ERROR,//INFO,ERROR,QUESTION,WARNING
		                                   				buttonText: { ok: '确定' }
		                                   			});
		                                		}
		                                	}else{
//		                                		Ext.Msg.alert('温馨提示',"设置失败!");
		                                		Ext.Msg.show({
	                                   				title : '温馨提示',
	                                   				msg :"设置失败!",
	                                   				width : 250,
	                                   				icon : Ext.Msg.ERROR,//INFO,ERROR,QUESTION,WARNING
	                                   				buttonText: { ok: '确定' }
	                                   			});
		                                	}
		                                	setwin.close();
		                                },  
		                                failure:function(form,action){
		                                	
		                                }
		                            });
			                }
			            },{
			                text: '取消',
			                handler: function(){
			                	this.up('form').getForm().reset();
			                	setwin.close();
			                }
			            }]
			        });
        /**
         * 设置信息的面板
         */
        var setwin = Ext.create('widget.window', {
                title: '参数设置',
                header: {
                    titlePosition: 2,
                    titleAlign: 'center'
                },
                closable: false,
                modal: true,
                closeAction: 'hide',
                width: 550,
                minWidth: 350,
                height: 350,
                layout:'fit',
                items:[setsimple]
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
            		win.setTitle("新增线路");
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
        
        var exportAction=Ext.create('Ext.Action', {
        	iconCls:'common_excel',
            text: '导出Excel',
            handler: function(widget, event) {
            	var url=rootUrl+'/routemangercontroller/exportroutemanger?';
        		var title='线路信息';
        		var searchStr  = Ext.getCmp('_searchCondiction').getValue();
            	if(searchStr){
            		url+="searchStr="+searchStr+"&"
            	}
            	exportExcel(me,url,title);
            }
        });
        /**
         * 删除按钮
         */
        var deleteAction = Ext.create('Ext.Action', {
        	iconCls:'common_del',
            text: '删除',
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
                             	    url:rootUrl+'/routemangercontroller/deleteroutemanger',//addOrUpdateUrL,
                             	    async: false,
                             	    params: {
                             	    	ids: params.toString()
                     			    },
                             	    success: function(response){
                             	    	var respText = Ext.JSON.decode(response.responseText);
//                     			    	Ext.Msg.alert('删除成功',"共删除"+respText.data+"条记录");
                             	    	Ext.Msg.show({
                               				title : '温馨提示',
                               				msg :"共删除"+respText.data+"条记录",
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
        /**
         * 设置按钮
         */
        var setAction = Ext.create('Ext.Action', {
        	iconCls:'common_set',
            text: '设置',
            handler: function(widget, event) {
            	 var rec = me.getSelectionModel().getSelection();
                 if(rec){
                 	 if(rec.length==0){
                      	Ext.Msg.alert('温馨提示',"请先选中一条!");
                 	 }else if(rec.length>0){
                 		var params=new Array();
                 		var param_names=new Array();
                    	for(var i=0;i<rec.length;i++){
                    		params.push(rec[i].data.id);
                    		param_names.push(rec[i].data.name);
                    	}
//                    	setwin.show()
                    	setwin.show(this,function(){
                    		Ext.getCmp("setsimpleid").getForm().reset();
                    		Ext.getCmp("_setid").setValue(params);
                    		Ext.getCmp("_setname").setValue(param_names);
                    		
//                    		win.setTitle("新增线路");
            			});

                 	 }
                 }
            }
        });
        /**
         * 更改按钮
         */
        var updateAction = Ext.create('Ext.Action', {
        	iconCls:'common_edit',
            text: '更改线路信息',
            handler: function(widget, event) {
                var rec = me.getSelectionModel().getSelection();
                if(rec){
                	 if(rec.length==0){
                     	Ext.Msg.alert('温馨提示',"请先选中一条!");
                	 }else if(rec.length>1){
                		 Ext.Msg.alert('温馨提示',"您选中了多条记录!</br>编辑只能选择一条");
                	 }else{
                		 var tempdata=rec[0].data;
                			win.show(this,function(){
                			
                				win.setTitle("更新线路信息");
                				
                				
                				
    		                	Ext.getCmp('_id').setValue(tempdata.id);
			                	Ext.getCmp('_name').setValue(tempdata.name);
			                	Ext.getCmp('_state').setValue({'state':tempdata.state});
			                	Ext.getCmp('_mark').setValue(tempdata.mark);
			                	
                			});
                	 }
                }
            }
        });
    	
   	 /**
   	  * 得到所有用户信息
   	  */
   	 var trStore=Ext.create('Ext.data.Store', {
   		    model: corMangerModel,
   		    pageSize:defaultPageSize,
   		    proxy: {
   		    	type: 'ajax',
   		    	url:rootUrl+'/routemangercontroller/queryroutemanger',
   		        reader: {
   		            type: 'json',
   		            root: 'elementList',  
   		            totalProperty: 'total'
   		        }
   		    }
   		});
     var devicewin = Ext.create('widget.window', {
         title: '车辆中的设备列表',
         header: {
             titlePosition: 2,
             titleAlign: 'center'
         },
         frame:false,
         border:false,
         closable: true,
         closeAction: 'hide',
         width: 500,
         minWidth: 350,
         height: 350,
         layout: {
             type: 'border'
         },
          items:[
          {
         	 region: 'center',
              id:'deviceInfoWin',
              overflowY:'auto',
              autoScroll:true,
              xtype:'form',
              width: 500,
              minWidth: 350,
              height: 350,
              items:[]
          }]
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
            },{
           	  text: '线路名称',
	            flex: 1,
	            align:'center',
	            dataIndex: 'name',
	            sortable: true
            },{
         	  text: '线路状态',
              flex: 1,
              align:'center',
              dataIndex: 'state',
              sortable: true,
              renderer:function(value){
	              	if(value=='1'){
	              		return '<font face="arial" color="green" >正常</font>';
	              	}
	              	if(value=='0'){
	              		return '<font face="arial" color="red" >注销</font>';
	              	}
	              }
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
              dataIndex: 'createtime',
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
              dataIndex: 'mark',
              sortable: true
            },{
	            text: '查看设备相关信息',
	            menuDisabled: true,
	            xtype: 'actioncolumn',
	            tooltip: 'Edit task',
	            align: 'center',
	            iconCls:'common_edit',
	            handler: function(grid, rowIndex, colIndex, actionItem, event, record, row){
	            		var rdata=record.data;
	            		Ext.Ajax.request({
		    			    url: rootUrl+'/routemangercontroller/queryDeviceList',
		    			    async:false,
		    			    params: {
		    			        id:rdata.id
		    			    },
		    			    success: function(response){
		    			        var text = response.responseText;
		    			        var respText = Ext.JSON.decode(text);
		    			        if(respText.length>0){
		    			        	Ext.getCmp('deviceInfoWin').removeAll();
		    			        	
		    			        	for(var i=0;i<respText.length;i++){
		    			        		var tempDeviceInfo=
		    			        			Ext.create('App.ux.deviceinfo',{
				    			    	    		deviceid:respText[i].equipmentid,
				    			    	    		state:respText[i].state==1?'正常':'注销',
				    			    	    		ifonline:respText[i].isonline==1?'在线':'离线',
				    			    	    		reporttime:respText[i].reporttime,
				    			    	    		speed:respText[i].speed,
				    			    	    		timeout:respText[i].timeout,
				    			    	    		bustate:respText[i].bustate==1?'正常':'注销',
				    			    	    		code:respText[i].code
				    			    	    	});
		    			        		Ext.getCmp('deviceInfoWin').add(tempDeviceInfo);
		    			        	}
		    			        	devicewin.show();
		    			        }else{
		    			        	Ext.Msg.show({
		                 				title : '温馨提示',
		                 				msg :"该车辆没有安装设备",
		                 				width : 250,
		                 				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
		                 				buttonText: { ok: '确定'}
		                 			});
		    			        }

		    			    }
		    			});
	            },
	            isDisabled: function(view, rowIdx, colIdx, item, record) {
	                return false;
	            }
	        }
            ],selModel : Ext.create('Ext.selection.CheckboxModel', {
				mode : "MULTI",
				width : 222,
				header : "a"
			}),bbar: {
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
            },tbar: [addAction,updateAction,deleteAction,refreshAction,exportAction,setAction,'->',
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
				items:routeMangerGrid
			}]

    	});
    }
});