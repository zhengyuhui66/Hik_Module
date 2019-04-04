var addOrUpdateUrL=rootUrl+'/advertMmController/saveadvertModel';

/**
 * 主面板
 */
var advertModelGrid=Ext.define('KitchenSink.view.tree.TreeGrid', {
    extend: 'Ext.grid.Panel',
    requires: [
        'Ext.data.*',
        'Ext.grid.*',
        'Ext.util.*',
        'Ext.toolbar.Paging',
        'Ext.ux.ProgressBarPager',
        'Ext.selection.CheckboxModel'
    ],    
	padding:'5 5 5 5',
    xtype: 'tree-grid',
    initComponent: function() {
    	var me = this;
    	/**
    	 * 广告模版模型
    	 */
    	Ext.define('advertmodel', {
    	    extend: 'Ext.data.Model',
    	    fields: [
    	        {name: 'id', type: 'string'},
//    	        {name: 'modelid',  type: 'string'},
//    	        {name: 'modelmodeid',  type: 'string'},
    	        {name: 'url',  type: 'string'},
    	        {name: 'name',  type: 'string'},
//    	        {name: 'modename',  type: 'string'},
    	        {name: 'filename',  type: 'string'},
    	        {name: 'username',  type: 'string'},
    	        {name: 'telphone',type:'string'},
    	        {name: 'cycid',type:'string'},
    	        {name: 'cycname',type:'string'},
    	        {name: 'createtime',type:'string'},
    	        {name: 'updatetime',type:'string'},
    	        {name: 'desc',type:'string'},
    	        {name: 'modelskin',type:'string'},
    	        {name: 'modelskinid',type:'string'},
    	        {name: 'advposid1',type:'string'},
    	        {name: 'advposid2',type:'string'},
    	        {name: 'advposid3',type:'string'},
    	        {name: 'advposid4',type:'string'},
    	        {name: 'advposid5',type:'string'},
    	        {name: 'advposname1',type:'string'},
    	        {name: 'advposname2',type:'string'},
    	        {name: 'advposname3',type:'string'},
    	        {name: 'advposname4',type:'string'},
    	        {name: 'advposname5',type:'string'},
    	    ]
    	});
        /**
         * 新增按钮
         */
        var addAction = Ext.create('Ext.Action', {
        	iconCls:'common_add',
            text: '新增',
            handler: function(widget, event) {
            	Ext.getCmp('simpleForm').getForm().reset();
            	win.show();
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
            text: '删除',
            handler: function(widget, event) {
           	 var rec = me.getSelectionModel().getSelection();
             if(rec.length==0){
//             	Ext.Msg.alert('温馨提示',"请先选中一条!");
            	 Ext.Msg.show({
      				title : '提示',
      				msg : "请先选中一条!",
      				width : 250,
      				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
      				buttonText: { ok: '确定' }
      			});
             }else if(rec.length>=1){
            	 var obj=new Object();
            	 for(var i=0;i<rec.length;i++){
            		 obj[rec[i].data.id]=rec[i].data.filename
            	 }
             	Ext.Msg.show({
             	     title:'温馨提示',
              	     msg: '确定要删除吗?',
              	   buttonText: {yes:'保存', no:'取消'},
              	     icon: Ext.Msg.QUESTION,
              	     fn:function(btn,txt){
              	    	 if(btn=="yes"){
              	    		Ext.Ajax.request({
               				    url: rootUrl+'/advertMmController/deleteadvertModel',
               				    params: { 
               				    	obj:JSON.stringify(obj)
               				    },
               				    success: function(response){
               				    	var text = response.responseText;
              	    		        var _respText = Ext.JSON.decode(text);
	              	    		    Ext.Msg.alert('温馨提示',_respText.data);
	              	    		    trStore.reload();
               				    }
               				});
              	    		
              	    	 }
              	     }
            		});
       
             }
            }
        });
        
        /**
         * 更新按钮
         */
        var editAction = Ext.create('Ext.Action', {
        	iconCls:'common_edit',
            text: '查看/修改',
            handler: function(widget, event) {
            		var rec = me.getSelectionModel().getSelection();
                    if(rec){
                    	 if(rec.length==0){
//                         	Ext.Msg.alert('温馨提示',"请先选中一条!");
                    		 Ext.Msg.show({
                   				title : '提示',
                   				msg : "请先选中一条!",
                   				width : 250,
                   				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
                   				buttonText: { ok: '确定' }
                   			});
                    	 }else if(rec.length>1){
//                    		 Ext.Msg.alert('温馨提示',"您选中了多条记录!</br>编辑只能选择一条");
                    		 Ext.Msg.show({
                   				title : '提示',
                   				msg : "您选中了多条记录!</br>编辑只能选择一条",
                   				width : 250,
                   				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
                   				buttonText: { ok: '确定' }
                   			});
                    	 }else{
                    		 var tempdata=rec[0].data;
                    		 modifywin.show(this,function(){
//                    				Ext.getCmp("modifyformId").getForm().reset();
                    				Ext.getCmp("_id").setValue(tempdata.id);
                    				Ext.getCmp("_mMname").setValue(tempdata.name);
//                    				Ext.getCmp("_msubMname").setValue(tempdata.modename);
//                           		 	Ext.getCmp("_mMId").setValue(tempdata.modelmodeid);
                           		 	Ext.getCmp("_skin_id").setValue(tempdata.modelskinid+'');
                           		 	Ext.getCmp("_cyc_id").setValue(tempdata.cycid+'');
	                           		Ext.getCmp("_advposid1").setValue(tempdata.advposid1+'');
	                           		Ext.getCmp("_advposid2").setValue(tempdata.advposid2+'');
	                           		Ext.getCmp("_advposid3").setValue(tempdata.advposid3+'');
	                           		Ext.getCmp("_advposid4").setValue(tempdata.advposid4+'');
	                           		Ext.getCmp("_advposid5").setValue(tempdata.advposid5+'');
	                          		Ext.getCmp("_advposname1").setValue(tempdata.advposname1+'');
	                          		Ext.getCmp("_advposname2").setValue(tempdata.advposname2+'');
	                          		Ext.getCmp("_advposname3").setValue(tempdata.advposname3+'');
	                          		Ext.getCmp("_advposname4").setValue(tempdata.advposname4+'');
	                          		Ext.getCmp("_advposname5").setValue(tempdata.advposname5+'');
	                          		Ext.getCmp("_desc").setValue(tempdata.desc+'');
	                          		
//                           		 	Ext.getCmp("_modelid").setValue(tempdata.modelid);
                    			});
                    	 }
                    }
            	}
        });
        
        
        
        /**
         * 模型浏览
         */
        var winModelView = Ext.create('widget.window', {
                title: '模型浏览',
                header: {
                    titlePosition: 2,
                    titleAlign: 'center'
                },
                closable: true,
                modal: true,
                closeAction: 'hide',
                width: 550,
                minWidth: 350,
                height: 550,
                layout: {
                    type: 'fit'
                },
                html :'<iframe scrolling="auto" id="modelViewid" frameborder="0" width="100%" height="100%" src=""> </iframe>' //record.data.text,
            });
        /**
         * 模型详情
         */
        var modeldetailwin = Ext.create('widget.window', {
                title: '查看详情',
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
                    type: 'fit'
                },
                html :'<iframe scrolling="auto" id="modelViewid" frameborder="0" width="100%" height="100%" src=""> </iframe>' //record.data.text,
            });
        /**
         * 查看按钮
         */
        var updateAction = Ext.create('Ext.Action', {
        	iconCls:'common_check',
            text: '模型效果',
            handler: function(widget, event) {
//            	 me.getSelectionModel().refresh();
           	 var rec = me.getSelectionModel().getSelection();
        	 if(rec){
             	 if(rec.length==0){
//                  	Ext.Msg.alert('温馨提示',"请先选中一条!");
             		 Ext.Msg.show({
            				title : '提示',
            				msg : "请先选中一条!",
            				width : 250,
            				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
            				buttonText: { ok: '确定' }
            			});
             	 }else if(rec.length>0){
             		var url=rootUrl+"/"+rec[0].data.url;
             		var modelskinid=rec[0].data.modelskinid;
             		winModelView.show(this,function(){
             			
             			Ext.Ajax.request({
           				    url: rootUrl+'/timeSetController/getSKINById',
           				    params: { 
           				    	id:modelskinid
           				    },
           				    success: function(response){
           				    	var text = response.responseText;
          	    		        var _respText = Ext.JSON.decode(text);
          	    		        var random =Math.random();
//          	    		        console.info("======>"+_respText);
          	    		        if(_respText&&_respText.data&&_respText.data.length>0){
          	    		        	var temp=_respText.data[0].skinname;
          	    		        	$("#modelViewid").attr('src',url+'?modelskin='+temp+'&noCache='+random);          	    		        	
          	    		        }else{
          	    		        	$("#modelViewid").attr('src',url);     
          	    		        }
           				    }
           				});
             			
        			});
             	 }
        	 }
            }
        });
        
//        /**
//         * 查看按钮
//         */
//        var deltailAction = Ext.create('Ext.Action', {
//        	iconCls:'common_check',
//            text: '查看详情',
//            handler: function(widget, event) {
//           	 var rec = me.getSelectionModel().getSelection();
//        	 if(rec){
//             	 if(rec.length!=1){
//                  	Ext.Msg.alert('温馨提示',"请先选中一条!");
//             	 }else if(rec.length>0){
//             		var url=rec[0].data.url;
//             		var modelskinid=rec[0].data.modelskinid;
//             		winModelView.show(this,function(){
//             			
//             			Ext.Ajax.request({
//           				    url: rootUrl+'/timeSetController/getSKINById',
//           				    params: { 
//           				    	id:modelskinid
//           				    },
//           				    success: function(response){
//           				    	var text = response.responseText;
//          	    		        var _respText = Ext.JSON.decode(text);
//          	    		        var random =Math.random();
////          	    		        console.info("======>"+_respText);
//          	    		        if(_respText&&_respText.data&&_respText.data.length>0){
//          	    		        	var temp=_respText.data[0].skinname;
//          	    		        	$("#modelViewid").attr('src',url+'?modelskin='+temp+'&noCache='+random);          	    		        	
//          	    		        }else{
//          	    		        	$("#modelViewid").attr('src',url);     
//          	    		        }
//           				    }
//           				});
//             			
//        			});
//             	 }
//        	 }
//            }
//        });
        
        /**
    	 * 物料模块模型
    	 */
    	Ext.define('skinModel',{
    	    extend: 'Ext.data.Model',
    	    fields: [
    	        {name: 'id', type: 'string'},
    	        {name: 'name', type: 'string'}
    	    ]
    	});
    	/**
    	 * 物料模块Store    
    	 */
    	var skinStore=Ext.create('Ext.data.Store',{
    		model:'skinModel',
    	    proxy: {
    	        type: 'ajax',
    	        url: rootUrl+'/common/querySKIN',
    	        reader: {
    	            type: 'json',
    	            root: 'data'
    	        }
    	    },
    	    autoLoad:true
    	});
    	
    	/**
    	 * 模版周期Store    
    	 */
    	var cycStore=Ext.create('Ext.data.Store',{
    		model:'skinModel',
    	    proxy: {
    	        type: 'ajax',
    	        url: rootUrl+'/common/queryAuthCyc',
    	        reader: {
    	            type: 'json',
    	            root: 'data'
    	        }
    	    },
    	    autoLoad:true
    	});
        var modifyform = Ext.widget({
            xtype: 'form',
//            layout: 'form',
            id: 'modifyformId',
            fieldDefaults: {
                labelWidth : 100,
                labelAlign : 'right'
            },
            padding:'0 20 0 20',
            border:false,
            defaultType: 'textfield',
            defaults:{
            	anchor:'100%'
            },
            items: [
					{
					    xtype: 'fieldset',
					    title: '模版信息',
					    defaultType: 'textfield',
					    layout: 'anchor',
					    defaults: {
					        anchor: '100%'
					    },
					    items:[{
					      fieldLabel: 'ID',
					      name: 'id',
					      id:'_id',
					      hidden:true
					  },{
					      fieldLabel: '模版名称',
					      name: 'mMname',
					      id:'_mMname',
					      allowBlank: false,
					      blankText: '不能为空!'
					  },{
					      xtype: 'combo',
					      store:skinStore,
					      displayField:'name', 
					      valueField  : 'id',    //真实的字段
					      fieldLabel: '皮肤选择',
					      selectOnTab: false,
					      name: 'modelskin',
					      id:'_skin_id',
					      margin:'20 0 20 0',
					      emptyText   : '请选择...'
					  },{
					      xtype: 'combo',
					      store:cycStore,
					      displayField:'name', 
					      valueField  : 'id',    //真实的字段
					      fieldLabel: '模版周期类型选择',
					      selectOnTab: false,
					      name: 'modelcyc',
					      id:'_cyc_id',
					      margin:'20 0 20 0',
					      emptyText   : '请选择...'
					  },{
					      fieldLabel: '描述',
					      name: 'desc',
					      id:'_desc',
					      allowBlank: false,
					      blankText: '不能为空!'
					  }]
					 },{
						    xtype: 'fieldset',
						    title: '广告位信息',
						    defaultType: 'textfield',
						    layout: 'anchor',
						    defaults: {
						        anchor: '100%',
						        xtype:'container',
						        layout: 'hbox',
						        defaultType: 'textfield',
						        margin: '0 0 10 5'
						    },
						    items:[
						     {
			                    items: [{
			                        fieldLabel: '1号广告位ID',
			                        name: 'advposid1',
			                        id:'_advposid1',
			                        flex: 1
			                    }, {
			                        name: 'advposname1',
			                        id: '_advposname1',
			                        flex: 1,
			                        emptyText: '广告位名称'
			                    }]
						    },{
						    	items: [{
			                        fieldLabel: '2号广告位ID',
			                        name: 'advposid2',
			                        id:'_advposid2',
			                        flex: 1
			                    }, {
			                        name: 'advposname2',
			                        id: '_advposname2',
			                        flex: 1,
			                        emptyText: '广告位名称'
			                    }]
						    },{
						    	items: [{
			                        fieldLabel: '3号广告位ID',
			                        name: 'advposid3',
			                        id:'_advposid3',
			                        flex: 1
			                    }, {
			                        name: 'advposname3',
			                        id: '_advposname3',
			                        flex: 1,
			                        emptyText: '广告位名称'
			                    }]
						    },{
						    	items: [{
			                        fieldLabel: '4号广告位ID',
			                        name: 'advposid4',
			                        id:'_advposid4',
			                        flex: 1
			                    }, {
			                        name: 'advposname4',
			                        id: '_advposname4',
			                        flex: 1,
			                        emptyText: '广告位名称'
			                    }]
						    },{
						    	items: [{
			                        fieldLabel: '5号广告位ID',
			                        name: 'advposid5',
			                        id:'_advposid5',
			                        flex: 1
			                    }, {
			                        name: 'advposname5',
			                        id: '_advposname5',
			                        flex: 1,
			                        emptyText: '广告位名称'
			                    }]
							    }
						    ]
						 }
//                    {
//                fieldLabel: 'ID',
//                name: 'id',
//                id:'_id',
//                hidden:true
//            },{
//                fieldLabel: '模版名称',
//                name: 'mMname',
//                id:'_mMname',
//                allowBlank: false,
//                blankText: '不能为空!'
//            },{
//		        xtype: 'combo',
//		        store:skinStore,
//		        displayField:'name', 
//		        valueField  : 'id',    //真实的字段
//		        fieldLabel: '皮肤选择',
//		        selectOnTab: false,
//		        name: 'modelskin',
//		        id:'_skin_id',
//		        margin:'20 0 20 0',
//		        emptyText   : '请选择...'
//	         }
            ],buttons: [{
	                text: '提交',
	                handler: function() {
	                	this.up('form').getForm().submit({  
                                clientValidation:true,
                                waitMsg:'请稍候',
                                waitTitle:'正在更新',
                                url: rootUrl+'/advertMmController/updateadvertModel',
                                success:function(form,action){
                                	if(action&&action.result&&action.result.success){
                                		if(action.result.success=="true"){
                                			//成功后更新表格并关闭win
//                                			trStore.reload();
                                			me.getSelectionModel().clearSelections()
                                			
                                		}else if(action.result.success=="false"){
//                                			modifywin.close();
//                                			alert("上传失败！")
                                			 Ext.Msg.show({
                                    				title : '提示',
                                    				msg : "上传失败！",
                                    				width : 250,
                                    				icon : Ext.Msg.ERROR,//INFO,ERROR,QUESTION,WARNING
                                    				buttonText: { ok: '确定' }
                                    			});
                                		}
                                	}else{
//                                		modifywin.close();
//                                		alert("上传失败！.")
                                		Ext.Msg.show({
                            				title : '提示',
                            				msg : "上传失败！",
                            				width : 250,
                            				icon : Ext.Msg.ERROR,//INFO,ERROR,QUESTION,WARNING
                            				buttonText: { ok: '确定' }
                            			});
                                	}
                                	modifywin.close();
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
	                	modifywin.close();
	                }
	            }]
	        });
        

        
        var simple = Ext.widget({
            xtype: 'form',
            id: 'simpleForm',
            fieldDefaults: {
                labelWidth : 100,
                labelAlign : 'right'
            },
            padding:'20 20 0 0',
            border:false,
            defaultType: 'textfield',
            defaults:{
            	anchor:'100%',
            	margin:'20 0 0 0'
            },
            items: [{
	                xtype: 'filefield',
	                id: 'file',
	                emptyText: '模版文件',
	                fieldLabel: '模版文件',
	                name: 'file',
	                allowBlank: false,
	                margin:'0 0 0 0',
	                blankText: '不能为空!',
	                buttonText: '<span style="width:50px;margin:0px 10px 0px 10px;">选择文件</span>'
	            },{
	                fieldLabel: '广告位ID',
	                name: 'subModelid',
	                id:'_subModelid',
	                emptyText: '广告位_1_id,广告位_2_id,广告位_3_id',
	                allowBlank: false,
	                blankText: '不能为空!'
	            },{
	                fieldLabel: '广告位名称',
	                name: 'subModelname',
	                id:'_subModelname',
	                emptyText: '广告位_1,广告位_2,广告位_3',
	                allowBlank: false,
	                blankText: '不能为空!'
	                
	            },{
	                fieldLabel: '模版名称',
	                name: 'modelname',
	                id:'_modelname',
	                emptyText: '模版名称',
	                allowBlank: false,
	                blankText: '不能为空!'
	            },{
			        xtype: 'combo',
			        store:skinStore,
			        displayField:'name',
			        valueField  : 'id',    //真实的字段
			        fieldLabel: '皮肤选择',
			        selectOnTab: false,
			        name: 'modelskin',
			        id:'skin_id',
			        emptyText   : '请选择...'
			    },{			        
			    	xtype: 'combo',
			        store:cycStore,
			        displayField:'name',
			        valueField  : 'id',    //真实的字段
			        fieldLabel: '模版周期',
			        selectOnTab: false,
			        name: 'modelcyc',
			        id:'cyc_id',
			        emptyText   : '请选择...'
			    },{
	            	xtype: 'textareafield',
	            	height: 80,
	                fieldLabel: '模版描述',
	                name: 'description',
	                id: '_description'
	            }],
			
			            buttons: [{
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
		                                			//成功后更新表格并关闭win
		                                			trStore.reload();
//		                                			win.close();
		                                		}else if(action.result.success=="false"){
//		                                			win.close();
//		                                			alert("上传失败！")
		                                			Ext.Msg.show({
	                                    				title : '提示',
	                                    				msg : "上传失败！",
	                                    				width : 250,
	                                    				icon : Ext.Msg.ERROR,//INFO,ERROR,QUESTION,WARNING
	                                    				buttonText: { ok: '确定' }
	                                    			});
		                                		}
		                                	}else{
//		                                		alert("上传失败！.")
		                                		Ext.Msg.show({
                                    				title : '提示',
                                    				msg : "上传失败！",
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
         * 新增模版的面板
         */
        var win = Ext.create('widget.window', {
                title: '模版',
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
                    overflowY:'auto',
                    autoScroll:true,
                    layout:{
                    	type:'vbox',
                    	align: 'stretch'
                    },
                    items:simple
                }
            });

        
        /**
		 * 修改模版的面板
		 */
        var modifywin = Ext.create('widget.window', {
                title: '修改模版',
                header: {
                    titlePosition: 2,
                    titleAlign: 'center'
                },
                closable: false,
                modal: true,
                closeAction: 'hide',
                width: 550,
                minWidth: 350,
                height: 430,
                layout: {
                    type: 'border'
                },
                items: {
                    region: 'center',
                    xtype: 'panel',
                    overflowY:'auto',
                    autoScroll:true,
                    layout:{
                    	type:'vbox',
                    	align: 'stretch'
                    },
                    items:modifyform
                }
            });
        
   	 /**
   	  * 得到所有用户信息
   	  */
   	 var trStore=Ext.create('Ext.data.Store', {
   		    storeId:'idfd',
   		    model: advertmodel,
   		    pageSize:defaultPageSize,
   		    proxy: {
   		    	type: 'ajax',
   		    	url:rootUrl+'/advertMmController/queryadvertModel',
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
                dataIndex: 'id'
            },{
                text: '模版引入地址',
                flex: 3,
                hidden:true,
                align:'center',
                dataIndex: 'url'
            },{
            	text: '模版文件名称',
            	flex: 1,
            	align:'center',
            	dataIndex: 'filename'
            },{
                text: '模版名称',
                flex: 1,
                align:'center',
                dataIndex: 'name'
            },{
                text: '制作上传人',
                flex: 1,
                align:'center',
                dataIndex: 'username'
            },{
                text: '制作上传人电话',
                flex: 1,
                align:'center',
                dataIndex: 'telphone'
            },{
                text: '上传时间',
                flex: 1,
                align:'center',
                dataIndex: 'createtime'
            },{
                text: '最后修改时间',
                flex: 1,
                align:'center',
                dataIndex: 'updatetime',
                sortable: true
            },{
                text: '模版周期',
                flex: 1,
                align:'center',
                dataIndex: 'cycname',
                sortable: true
            },{
                text: '模版周期ID',
                hidden:true,
                dataIndex: 'cycid'
            },{
                text: '皮肤',
                flex: 1,
                align:'center',
                dataIndex: 'modelskin'
            },{
                text: '广告位1ID',
                hidden:true,
                dataIndex: 'advposid1',
            },{
                text: '广告位2ID',
                hidden:true,
                dataIndex: 'advposid2',
            },{
                text: '广告位3ID',
                hidden:true,
                dataIndex: 'advposid3',
            },{
                text: '广告位4ID',
                hidden:true,
                dataIndex: 'advposid4',
            },{
                text: '广告位5ID',
                hidden:true,
                dataIndex: 'advposid5',
            },{
                text: '广告位1名称',
                hidden:true,
                dataIndex: 'advposname1',   	
            },{
                text: '广告位2名称',
                hidden:true,
                dataIndex: 'advposname2',   	
            },{
                text: '广告位3名称',
                hidden:true,
                dataIndex: 'advposname3',   	
            },{
                text: '广告位4名称',
                hidden:true,
                dataIndex: 'advposname4',   	
            },{
                text: '广告位5名称',
                hidden:true,
                dataIndex: 'advposname5',   	
            },{
                text: '皮肤ID',
                hidden:true,
                dataIndex: 'modelskinid'
            },{
                text: '描述',
                hidden:true,
                dataIndex: 'desc'
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
            },tbar: [addAction,updateAction,refreshAction,editAction,deleteAction,'->',
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
				items:advertModelGrid
			}]
    	});
    }
});





