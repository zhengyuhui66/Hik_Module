/**
 * 车辆管理
 */
addOrUpdateUrL = rootUrl + '/vehicleManger/saveOrupdateVehicle';

//Ext.Loader.setConfig({  
//	   enabled: true,  
//	   paths : {//'类名前缀':'所在路径'  
//	      'App.ux' : rootUrl+'/js/busManger/deviceinfo'  
//	   }  
//	}); 
Ext.define('App.ux.deviceinfo',{
	extend:'Ext.form.Panel',
	xtype:'deviceinfo',
	config:{
		deviceid:'',
		state:'',
		ifonline:'',
		reporttime:'',
		speed:'',
		timeout:''
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
var vehicleGrid = Ext.define('KitchenSink.view.tree.TreeGrid', {
	extend : 'Ext.grid.Panel',
	requires : ['Ext.data.*', 
	            'Ext.grid.*', 
	            'Ext.util.*', 
	            'Ext.toolbar.Paging', 
	            'Ext.ux.ProgressBarPager', 
	            'Ext.selection.CheckboxModel',
	            'App.ux.deviceinfo'],
	xtype : 'material-grid',
	padding : '5 5 5 5',
	initComponent : function() {
		var showAuditProce = [];
		var filterPanel = Ext.create('Ext.panel.Panel', {
			width : 380,
			flex : 1,
			layout : {
				type : 'hbox',
				align : 'middle',
				pack : 'center'
			},
			autoScroll : true,
			overflowX : true,
			overflowY : true,
			html : '<div style="min-width:355px;max-width:380px;height:260px;overflow:auto"><img id="materShow" src="" onerror="this.src=\'' + rootUrl + "/images/default/default.jpg" + '\'"></img></div>'

		});

		/**
		 * 车辆模型
		 */
		Ext.define('vechileModel', {
			extend : 'Ext.data.Model',
			fields : [{
				name : 'CODE',
				type : 'string'
			}, {
				name : 'STATE',
				type : 'string'
			}, {
				name : 'OWERCODE',
				type : 'string'
			}, {
				name : 'MARK',
				type : 'string'
			}, {
				name : 'CORPID',
				type : 'string'
			}, {
				name : 'CORPNAME',
				type : 'string'
			}, {
				name : 'ROUTEID',
				type : 'string'
			}, {
				name : 'ROUTENAME',
				type : 'string'
			}, {
				name : 'ID',
				type : 'string'
			}, {
				name : 'BRAND',
				type : 'string'
			}, {
				name : 'MODEL',
				type : 'string'
			}, {
				name : 'COLOR',
				type : 'string'
			}, {
				name : 'USEDATE',
				type : 'string'
			}, {
				name : 'CREATOR',
				type : 'string'
			}, {
				name : 'MODIFIER',
				type : 'string'
			}, {
				name : 'CREATETIME',
				type : 'string'
			}, {
				name : 'MODIFYTIME',
				type : 'string'
			}]
		});
		
		/**
		 * 线路模型
		 */
		Ext.define('routeModel', {
			extend : 'Ext.data.Model',
			fields : [{
				name : 'ID',
				type : 'string'
			}, {
				name : 'NAME',
				type : 'string'
			}, {
				name : 'STATE',
				type : 'string'
			}, {
				name : 'CREATOR',
				type : 'string'
			}, {
				name : 'MODIFIER',
				type : 'string'
			}, {
				name : 'CREATETIME',
				type : 'string'
			}, {
				name : 'MODIFYTIME',
				type : 'string'
			}, {
				name : 'MARK',
				type : 'string'
			}]
		});	
		/**
		 * 公交单位模型
		 */
		Ext.define('corpModel', {
			extend : 'Ext.data.Model',
			fields : [{
				name : 'ID',
				type : 'string'
			}, {
				name : 'NAME',
				type : 'string'
			}, {
				name : 'ADDRESS',
				type : 'string'
			}, {
				name : 'CREATOR',
				type : 'string'
			}, {
				name : 'MODIFIER',
				type : 'string'
			}, {
				name : 'CREATETIME',
				type : 'string'
			}, {
				name : 'MODIFYTIME',
				type : 'string'
			}, {
				name : 'MARK',
				type : 'string'
			}]
		});

		/**
		 * 车辆加载的STORE
		 */
		var materialStore = Ext.create('Ext.data.Store', {
			storeId : 'simpsonsStore',
			model : 'vechileModel',
			pageSize : defaultPageSize,
			proxy : {
				type : 'ajax',
				url : rootUrl + '/vehicleManger/queryVehicleList',
				reader : {
					type : 'json',
					root : 'elementList',
					totalProperty : 'total'
				}
			},
			autoLoad:true
		});
		/**
		 * 线路选择的STORE
		 */
		var routeStore = Ext.create('Ext.data.Store', {
			model : 'routeModel',
			proxy : {
				type : 'ajax',
				url : rootUrl + '/common/queryAllRoute',
				reader : {
					type : 'json',
					root : 'data'
				}
			},
			autoLoad:true
		});
		/**
		 * 所属单位选择的STORE
		 */
		var corpStore = Ext.create('Ext.data.Store', {
			model : 'corpModel',
			proxy : {
				type : 'ajax',
				url : rootUrl + '/common/queryAllCorp',
				reader : {
					type : 'json',
					root : 'data'
				}
			},
			autoLoad:true
		});
		
		/**
		 * 默认加载第一页
		 */
		materialStore.loadPage(1);
 //--------- 弃用-------------是否可投放广告单选组开始 ----------------------//
//             var ifadvertRadioGroup = new Ext.form.RadioGroup({
//                 fieldLabel: '可投放广告',
//                 width: 100,
//                 items: [{
//                     name: 'IFADVERT',
//                     inputValue: '1',
//                     boxLabel: '是(默认)',
//                     checked: true
//                 }, {
//                     name: 'IFADVERT',
//                     inputValue: '0',
//                     boxLabel: '否'
//                 }]
//             });
              //------- 弃用---------------状态单选组开始----------------------//
//             var stateRadioGroup = new Ext.form.RadioGroup({
//             	 id:'stateGroup',
//                 fieldLabel: '车辆状态',
//                 width: 100,
//                 items: [{
//                     name: 'STATE',
//                     inputValue: '1',
//                     boxLabel: '正常(默认)',
//                     id:'formalState',
//                     checked: true
//                 }, {
//                     name: 'STATE',
//                     inputValue: '0',
//                     id:'errorState',
//                     boxLabel: '异常'
//                 }]
//             });
    //----------------------新增车辆Action----------------------//         
		/**
		 * 新增按钮 (新增)
		 */
		var addAction = Ext.create('Ext.Action', {
			iconCls : 'common_add',
			text : '新增车辆',
			handler : function(widget, event) {
				vehicleAddWin.show(this, function() {
					Ext.getCmp("simpleForm").getForm().reset();
					vehicleAddWin.setTitle("新增车辆");
				});
			}
		});
		var addVehicleItems = Ext.widget({
			xtype : 'form',
			id : 'simpleForm',
			fieldDefaults : {
				labelWidth : 100,
				labelAlign : 'right'
			},
			  layout: {
                     type: 'vbox',
                     align: 'stretch'
                 },
			padding : '20 20 0 0',
			border : false,
			
			defaults : {
				anchor : '100%',
				margin : '10 0 10 0'
			},
		items : [{
			xtype : 'textfield',
			id:'tf_code',
            name: 'code',
            fieldLabel: '公交车牌'
        },{
        	xtype : 'textfield',
			id:'tf_owercode',
            name: 'owercode',
            fieldLabel: '自编号'
        },{
			xtype: 'combo',
			fieldLabel: '所属线路',
			forceSelection : true,
			editable:false,
			store:routeStore,
			displayField:'NAME', 
			valueField  : 'ID',    //真实的字段
			selectOnTab: false,
			allowBlank: false,
			emptyText:'请选择...',
			id:'tf_routeid',
			name:'routeid',
			listeners:{
			      change:function(me, newValue, oldValue, eOpts){
			       var temp=me.displayTplData;
			        if(temp.length>0){
			        	var id=temp[0].ID;
			        	var name=temp[0].NAME;
			        	Ext.getCmp('tf_routeid').setValue(id);
			        	Ext.getCmp('tf_routename').setValue(name);
			        		}
			        	}
			        }
        },{
        	xtype : 'textfield',
			id:'tf_routename',
            name: 'routename',
            hidden:true
        },{
			xtype: 'combo',
			fieldLabel: '所属单位',
			forceSelection : true,
			editable:false,
			store:corpStore,
			displayField:'NAME', 
			valueField  : 'ID',    //真实的字段
			selectOnTab: false,
			allowBlank: false,
			emptyText:'请选择...',
			id:'tf_corpid',
			name:'corpid',
			listeners:{
		      change:function(me, newValue, oldValue, eOpts){
		       var temp=me.displayTplData;
		        if(temp.length>0){
		        	var id=temp[0].ID;
		        	var name=temp[0].NAME;
		        	Ext.getCmp('tf_corpid').setValue(id);
		        	Ext.getCmp('tf_corpname').setValue(name);
		        		}
		        	}
		        }
        },{
        	xtype : 'textfield',
			id:'tf_corpname',
            name: 'corpname',
            hidden:true
        },{
        	xtype : 'textfield',
			id:'tf_brand',
            name: 'brand',
            fieldLabel: '品牌'
        },{
        	xtype : 'textfield',
			id:'tf_model',
            name: 'model',
            fieldLabel: '型号'
        },{
        	xtype : 'textfield',
			id:'tf_color',
            name: 'color',
            fieldLabel: '颜色'
        },{
           id:'tf_usedate',
           xtype: "datefield",
		   name: "usedate",
		   fieldLabel: "上线使用时间",
		   editable: false,
		   selectOnFocus:true,
		   width:80,
		   emptyText: "--请选择--",
		   format: "Y/m/d H:i:s"//日期的格式
        },{
            xtype: 'radiogroup',
            fieldLabel: '车辆状态',
            id:'tf_state',
            items: [
                {boxLabel: '正常', name: 'state', inputValue:'1',checked: true},
                {boxLabel: '注销', name: 'state', inputValue:'0',margin:'0 0 0 10'}
            ]
        },{
        	xtype : 'textfield',
			id:'tf_mark',
            name: 'mark',
            fieldLabel: '备注'
		},
		{
			xtype : 'textfield',
			id:'tf_id',
            name: 'id',
            hidden:true
        }]
		});
		
		/**
		 * 新增或编辑车辆
		 */
    	var getIfhavVehicle=function(name){
    		flag=true;
    		$.ajax({
				type : "GET", //提交方式  
				url : rootUrl + '/common/queryIfhaveBusInfo',//路径  
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
		var vehicleAddWin = Ext.create('widget.window', {
			title : '新增车辆',
			header : {
				titlePosition : 2,
				titleAlign : 'center'
			},
			closable : true,
			modal: true,
			closeAction : 'hide',
			width : 550,
			minWidth : 350,
			height : 450,
			layout: {
                    type: 'border'
                },
			items :
			{
				 	region: 'center',
//                    xtype: 'panel',
                    overflowY:'auto',
                    autoScroll:true,
                    layout:{
                    	type:'vbox',
                    	align: 'stretch'
                    },
                    id:'addvehicleitems',
                    xtype:'form',
                    items:[addVehicleItems],
                    buttons: [{
			                text: '提交',
			                handler: function() {
			                	
			                	var code=Ext.getCmp('tf_code').getValue();
			                	if(vehicleAddWin.title=="新增车辆"){
				                	var ifhave=getIfhavVehicle(code);
				                	if(ifhave){
				                		 Ext.Msg.show({
		                           				title : '提示',
		                           				msg :"提交失败!</br>车辆重复录入",
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
		                                url: addOrUpdateUrL,
		                                success:function(form,action){
		                                	if(action&&action.result&&action.result.success){
		                                		if(action.result.success=="true"){
		                                			materialStore.reload();
		                                		}else if(action.result.success=="false"){
//		                                			Ext.Msg.alert('温馨提示',"上传失败1!");
		                                			Ext.Msg.show({
		                                   				title : '温馨提示',
		                                   				msg :"上传失败!",
		                                   				width : 250,
		                                   				icon : Ext.Msg.ERROR,//INFO,ERROR,QUESTION,WARNING
		                                   				buttonText: { ok: '确定' }
		                                   			});
		                                		}
		                                	}else{
//		                                		Ext.Msg.alert('温馨提示',"上传失败2!");
		                                		Ext.Msg.show({
	                                   				title : '温馨提示',
	                                   				msg :"上传失败!",
	                                   				width : 250,
	                                   				icon : Ext.Msg.ERROR,//INFO,ERROR,QUESTION,WARNING
	                                   				buttonText: { ok: '确定' }
	                                   			});
		                                	}
		                                	Ext.getCmp('simpleForm').getForm().reset();
		    			                	vehicleAddWin.close();
		                                },failure:function(form,action){
//		                                	Ext.Msg.alert('温馨提示',"上传失败3!");
		                                	Ext.Msg.show({
                                   				title : '温馨提示',
                                   				msg :"上传失败!",
                                   				width : 250,
                                   				icon : Ext.Msg.ERROR,//INFO,ERROR,QUESTION,WARNING
                                   				buttonText: { ok: '确定' }
                                   			});
		                                	Ext.getCmp('simpleForm').getForm().reset();
		    			                	vehicleAddWin.close();
		                                }
		                          });
			                }
			            },{
			                text: '取消',
			                handler: function(){
			                	this.up('form').getForm().reset();
			                	 vehicleAddWin.close();
			                }
			            }]
			} 
			
		});
		
//		---------------------------------------------------------------------------
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
                   fieldLabel: '车辆名称',
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
	            }]
            ,buttons: [{
			                text: '提交',
			                handler: function() {
			                	this.up('form').getForm().submit({
		                                clientValidation:true,
		                                waitMsg:'请稍候',
		                                waitTitle:'正在更新',
		                                url: rootUrl+'/vehicleManger/setting',
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
        //导出Excel
        var exportAction=Ext.create('Ext.Action', {
        	iconCls:'common_excel',
            text: '导出Excel',
            handler: function(widget, event) {
            	var url=rootUrl+'/vehicleManger/exportVehiclemanger?';
        		var title='车辆信息';
        		var searchStr  = Ext.getCmp('_searchCondiction').getValue();
            	if(searchStr){
            		url+="searchStr="+searchStr+"&"
            	}
            	exportExcel(me,url,title);
            }
        });
		//----------------------编辑车辆Action----------------------//

		var editAction=Ext.create('Ext.Action',{
			iconCls : 'common_edit',
			text : '编辑车辆',
			handler : function(widget, event) {
					addUpdateFlag=false;
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
                		
                			vehicleAddWin.show(this,function(){
                				Ext.getCmp("simpleForm").getForm().reset();
                				vehicleAddWin.setTitle("更新车辆信息");
                				Ext.getCmp("tf_id").setValue(tempdata.ID);// Api判断 有ID则为更新，无ID则为插入
                				Ext.getCmp("tf_code").setValue(tempdata.CODE);
                       		 	Ext.getCmp("tf_owercode").setValue(tempdata.OWERCODE);
                       		 	Ext.getCmp("tf_routeid").setValue(tempdata.ROUTEID);
                       		 	Ext.getCmp("tf_routename").setValue(tempdata.ROUTENAME);
                       		 	Ext.getCmp("tf_corpid").setValue(tempdata.CORPID);
                       		 	Ext.getCmp("tf_corpname").setValue(tempdata.CORPNAME);
                       		 	Ext.getCmp("tf_brand").setValue(tempdata.BRAND);
                       		 	Ext.getCmp("tf_color").setValue(tempdata.COLOR);
                       		 	Ext.getCmp('tf_state').setValue({'state':tempdata.STATE});
                       		 	Ext.getCmp("tf_usedate").setValue(tempdata.USEDATE);
                       		 	Ext.getCmp("tf_mark").setValue(tempdata.MARK);
                       		 	Ext.getCmp("tf_model").setValue(tempdata.MODEL);
                			});
                	 }
                }
			}
		});
		//----------------------查看车辆Action----------------------//

		var delAction=Ext.create('Ext.Action',{
			iconCls : 'common_del',
			text : '删除车辆',
			handler : function(widget, event) {
					addUpdateFlag=false;
                var rec = me.getSelectionModel().getSelection();
                if(rec){
                	 if(rec.length==0){
//                     	Ext.Msg.alert('温馨提示',"请先选中一条!");
                		 Ext.Msg.show({
             				title : '温馨提示',
             				msg :"请先选中一条",
             				width : 250,
             				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
             				buttonText: { ok: '确定'}
             			});
                	 }else if(rec.length>0){
                		var params=new Array();
                    	for(var i=0;i<rec.length;i++){
                    		params.push(rec[i].data.ID);
                    		
                    	}
                     	Ext.Msg.show({
                    	     title:'温馨提示',
                     	     msg: '确定要删除吗?',
                     	    buttonText: {yes:'是', no:'取消'},
                     	     icon: Ext.Msg.QUESTION,
                     	     fn:function(btn,txt){
                     	    	 if(btn=="yes"){
                                 	Ext.Ajax.request({
                             	    url:rootUrl+'/vehicleManger/deleteVehicle',
                             	    async: false,
                             	    params: {
                             	    	ids: params.toString()
                     			    },
                             	    success: function(response){
                             	    	var respText = Ext.JSON.decode(response.responseText);
//                     			    	Ext.Msg.alert('温馨提示',"成功删除了"+respText.data+"条记录");
                             	    	Ext.Msg.show({
                             				title : '温馨提示',
                             				msg :"成功删除了"+respText.data+"条记录",
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
			}
		});
		

		var me = this;

		var inParentId = function(_userId, userIds) {
			if (!userIds) {
				return false;
			}
			for (var i = 0; i < userIds.length; i++) {
				if (_userId == userIds[i].userId) {
					return true;
				}
			}
			return false;
		};
		/**
		 *验证日期格式， 
		 */
		 function RQcheck(RQ) {
            var date = RQ;
            var result = date.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
            if (result == null)
                return false;
            var d = new Date(result[1], result[3] - 1, result[4]);
            return (d.getFullYear() == result[1] && (d.getMonth() + 1) == result[3] && d.getDate() == result[4]);

        }

	      var setAction = Ext.create('Ext.Action', {
	        	iconCls:'common_set',
	            text: '设置',
	            handler: function(widget, event) {
	            	 var rec = me.getSelectionModel().getSelection();
	                 if(rec){
	                 	 if(rec.length==0){
//	                      	Ext.Msg.alert('温馨提示',"请先选中一条!");
	                 		Ext.Msg.show({
                 				title : '温馨提示',
                 				msg :"请先选中一条!",
                 				width : 250,
                 				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
                 				buttonText: { ok: '确定'}
                 			});
	                 	 }else if(rec.length>0){
	                 		var params=new Array();
	                 		var param_names=new Array();
	                    	for(var i=0;i<rec.length;i++){
	                    		params.push(rec[i].data.ID);
	                    		param_names.push(rec[i].data.CODE);
	                    	}
//	                    	setwin.show()
	                    	setwin.show(this,function(){
	                    		Ext.getCmp("setsimpleid").getForm().reset();
	                    		Ext.getCmp("_setid").setValue(params);
	                    		Ext.getCmp("_setname").setValue(param_names);
	                    		
//	                    		win.setTitle("新增线路");
	            			});

	                 	 }
	                 }
	            }
	        });

	        var win = Ext.create('widget.window', {
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
//                tools: [{type: 'pin'}],
//                layout: {
//                	region: 'center',
//                    type: 'vbox'
//                },
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
		Ext.apply(this, {
			store : materialStore,
			columnLines : true,
			rowLines : true,
			flex : 1,
			minWidth : 900,
			maxWidth : 1300,
			columns : [{
				text : '序号',
				id : 'ID',
				width : 80,
				align : 'center',
				hidden:true,
				dataIndex : 'ID'
			}, {
				text : '公交车牌',
				id : 'CODE',
				flex : 1,
				align : 'center',
				dataIndex : 'CODE'
			}, {
				text : '自编号',
				flex : 1,
				align :'center',
				sortable : true,
				hidden:true,
				id : 'OWERCODE',
				dataIndex : 'OWERCODE'
			}, {
				text : '所属线路',
				flex : 1,
				align : 'center',
				dataIndex : 'ROUTENAME',
				sortable : true
			}, {
				text : '所属单位',
				flex : 1,
				align : 'center',
				dataIndex : 'CORPNAME'
			}, {
				text : '状态',
				flex : 1,
				align : 'center',
				dataIndex : 'STATE',
				renderer:function(value){
	              	if(value=='1'){
	              		return '<font face="arial" color="green" >正常</font>';
	              	}
	              	if(value=='0'){
	              		return '<font face="arial" color="red" >注销</font>';
	              	}
	              }
			}, {
				text : '备注',
				flex : 1,
				align : 'center',
				dataIndex : 'MARK'
			},{
	            text: '查看设备',
	            menuDisabled: true,
	            xtype: 'actioncolumn',
	            tooltip: 'Edit task',
	            align: 'center',
	            iconCls:'common_edit',
	            handler: function(grid, rowIndex, colIndex, actionItem, event, record, row){
	            		var rdata=record.data;
	            		Ext.Ajax.request({
		    			    url: rootUrl+'/vehicleManger/queryDeviceList',
		    			    async:false,
		    			    params: {
		    			        id:rdata.ID
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
				    			    	    		timeout:respText[i].timeout
				    			    	    	});
		    			        		Ext.getCmp('deviceInfoWin').add(tempDeviceInfo);
		    			        	}
		    			        	win.show();
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
	        }],
			bbar : {
				xtype : 'pagingtoolbar',
				pageSize : defaultPageSize,
				store : materialStore,
				nextText : '下一页',
				prevText : '上一页',
				firstText : '第一页',
				lastText : '最后一页',
				refreshText : '刷新',
				displayInfo : true,
				displayMsg : '显示{0}-{1}条，共{2}条',
				emptyMsg : "没有数据",
				plugins : new Ext.ux.ProgressBarPager()
			},
			selModel : Ext.create('Ext.selection.CheckboxModel', {
				mode : "MULTI",
				width : 222,
				header : "a"
			}),
			tbar : [addAction,editAction,delAction,setAction,exportAction,'->', {
				xtype : 'textfield',
				emptyText : '请输入需要搜索的信息',
				width : 200,
				name : 'searchCondiction',
				id : '_searchCondiction'
			}, {
				xtype : 'button',
				text : '搜索',
				iconCls : 'common_search',
				handler : function() {
					var searchStr = Ext.getCmp('_searchCondiction').getValue();
					materialStore.getProxy().extraParams = {
						searchStr : searchStr
					};
					materialStore.loadPage(1);
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
	name : 'materialManger',
	launch : function() {
		Ext.create('Ext.container.Viewport', {
			layout : 'fit',
			border : false,
			items : [{
				xtype : 'panel',
				overflowX : 'auto',
				layout : {
					type : 'hbox',
					align : 'stretch',
					pack : 'center'
				},

				items : vehicleGrid
			}]
		});
	}
});
