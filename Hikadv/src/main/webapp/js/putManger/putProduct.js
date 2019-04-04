var addorupdate=true;
var putProductGrid=Ext.define('Ext.putProductGrid.TreeGrid', {
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
    	 * 广告模型
    	 */
    	Ext.define('putProductModel', {
    	    extend: 'Ext.data.Model',
    	    fields: ["id","proname","modelid","name","advgroup1","advgroup2","advgroup3","advgroup4","advgroup5","advgroupname1","advgroupname2","advgroupname3","advgroupname4","advgroupname5","creatime","creator","modifier","modifytime","isdelete","descr"]
    	});
        	
    	
    	
    	Ext.define('advgroupModel', {
    	    extend: 'Ext.data.Model',
    	    fields: ['id','name']
    	});
    	
    	
    	var modelStore = Ext.create('Ext.data.Store', {
			model : 'advgroupModel',
			proxy : {
				type : 'ajax',
				url : rootUrl + '/common/queryAllModel',
				reader : {
					type : 'json',
					root : 'data'
				}
			},
			autoLoad:true
		});
    	
		var groupStore = Ext.create('Ext.data.Store', {
				model : 'advgroupModel',
				proxy : {
					type : 'ajax',
					url : rootUrl + '/common/queryAdvgroup',
					reader : {
						type : 'json',
						root : 'data'
					}
				},
				autoLoad:true
			});
		
		
		var getSubModel=function(newValue){
			for(var n=1;n<=5;n++){
				Ext.getCmp('_advgroup'+n).show();									
			}
			Ext.Ajax.request({
         	    url:rootUrl+'/common/queryModelNum',//addOrUpdateUrL,
         	    async: true,
         	    params: {
         	    	id:newValue
 			    },
         	    success: function(response){
         	    	var respText = Ext.JSON.decode(response.responseText);
 			    	if(respText.success=='true'&&respText.data){
 			    		var num=respText.data[0];
 			    		for(var i=+(num)+1;i<=5;i++){
 			    			Ext.getCmp('_advgroup'+i).hide();
 			    		}
 			    	}else{
// 			    		alert('模版有问题');
 			    		Ext.Msg.show({
	 			   			title : '温馨提示',
	 			   			msg :"模版有问题",
	 			   			width : 250,
	 			   			icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
	 			   			buttonText: { ok: '确定'}
 			    		});
 			    	}
         	    }
         	});
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
	                   name: 'proname',
	                   id:'_proname'
	               },{
	            	   xtype:'combo',
	            	   store: modelStore,
	                   fieldLabel: '模版',
	                   margin:'10 0 0 0',
	                   allowBlank: false,
	                   displayField: 'name',
  		    		   valueField: 'id',
	                   name: 'modelid',
	                   id:'_modelid',
	                   listeners : {
							change : function(me,newValue,oldValue,eOpts) {
								if(addorupdate){
										getSubModel(newValue);										
								}
							}
						}
	               },{
                     items:[{
                    		flex:1,
                    		xtype:'combo',
	   		    			margin:'30 0 0 0',
	   		    		    store: groupStore,
    	                   fieldLabel: '1号位广告集',
    	                   displayField: 'name',
	   		    		    valueField: 'id',
    	                   name: 'advgroup1',
    	                   id:'_advgroup1'
    	               },{
    	            		flex:1,
    	            		xtype:'combo',
	   		    			margin:'30 0 0 5',
	   		    		    store: groupStore,
	   		    		    displayField: 'name',
	   		    		    valueField: 'id',
    	                   fieldLabel: '2号位广告集',
    	                   name: 'advgroup2',
    	                   id:'_advgroup2'
    	               }]
	               },{
                     items:[{
                    		flex:1,
                    		xtype:'combo',
	   		    			margin:'10 0 0 0',
	   		    		    store: groupStore,
	   		    		    displayField: 'name',
	   		    		    valueField: 'id',
    	                   fieldLabel: '3号位广告集',
    	                   name: 'advgroup3',
    	                   id:'_advgroup3'
    	               },{
    	            		flex:1,
    	            		xtype:'combo',
	   		    			margin:'10 0 0 5',
	   		    		    store: groupStore,
	   		    		    displayField: 'name',
	   		    		    valueField: 'id',
    	                   fieldLabel: '4号位广告集',
    	                   name: 'advgroup4',
    	                   id:'_advgroup4'
    	               }]
                     },{
                    	 xtype:'combo',
   		    			margin:'10 0 0 0',
   		    		    store: groupStore,
   		    		    displayField: 'name',
		    		    valueField: 'id',
	 	                 fieldLabel: '5号位广告集',
	 	                 name: 'advgroup5',
	 	                 id:'_advgroup5'
                     },{
		            	   xtype: 'textareafield',
		                   fieldLabel: '产品描述',
		                   height:40,
		                   margin:'30 0 0 0',
		                   name: 'descr',
		                   id:'_descr'
		               }],buttons: [{
			                text: '提交',
			                handler: function() {
			                	this.up('form').getForm().submit({
		                                clientValidation:true,
		                                waitMsg:'请稍候',
		                                waitTitle:'正在更新',
		                                url: rootUrl+'/putproductcontroller/addorUpdateputProduct',
		                                success:function(form,action){
		                                	if(action&&action.result&&action.result.success){
		                                		if(action.result.success=="true"){
		                                			trStore.reload();
		                                		}else if(action.result.success=="false"){
		                                			Ext.Msg.show({
		                    	 			   			title : '温馨提示',
		                    	 			   			msg :"上传失败!",
		                    	 			   			width : 250,
		                    	 			   			icon : Ext.Msg.ERROR,//INFO,ERROR,QUESTION,WARNING
		                    	 			   			buttonText: { ok: '确定'}
		                     			    		});
//		                                			Ext.Msg.alert('温馨提示',"上传失败!");
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
                height: 450,
                layout:'fit',
                items:[simple]
//                layout: {
//                    type: 'border'
//                },
//                items: {
//                    region: 'center',
//                    xtype: 'panel',
//                    layout:{
//                    	type:'vbox',
//                    	align: 'stretch'
//                    },
//                    items:[simple]
//                }
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
            		addorupdate=true;
            		win.setTitle("新增广告");
					for(var i=1;i<=5;i++){
			    			Ext.getCmp('_advgroup'+i).show();
			    		}
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
            	var url=rootUrl+'/putproductcontroller/exportputProduct?';
        		var title='广告投放产品';
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
                             	    url:rootUrl+'/putproductcontroller/deleteputProduct',//addOrUpdateUrL,
                             	    async: false,
                             	    params: {
                             	    	ids: params.toString()
                     			    },
                             	    success: function(response){
                             	    	var respText = Ext.JSON.decode(response.responseText);
//                     			    	Ext.Msg.alert('无法删除',respText.data);
                             	    	Ext.Msg.show({
                	 			   			title : '温馨提示',
                	 			   			msg :respText.data,
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
        
//        var me=this;
        /**
         * 更改物料按钮
         */
        var updateAction = Ext.create('Ext.Action', {
        	iconCls:'common_edit',
            text: '更改',
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
			                	Ext.getCmp('_proname').setValue(tempdata.proname);
			                	Ext.getCmp('_modelid').setValue(tempdata.modelid);
			                	Ext.getCmp('_advgroup1').setValue(tempdata.advgroup1);
			                	Ext.getCmp('_advgroup2').setValue(tempdata.advgroup2);
			                	Ext.getCmp('_advgroup3').setValue(tempdata.advgroup3);
			                	Ext.getCmp('_advgroup4').setValue(tempdata.advgroup4);
			                	Ext.getCmp('_advgroup5').setValue(tempdata.advgroup5);
			                	Ext.getCmp('_descr').setValue(tempdata.descr);
			                	getSubModel(tempdata.modelid);
			                	addorupdate=false;
//								 me.getSelectionModel().deselectAll();

                			});
                	 }
                }
            }
        });
    	
   	 /**
   	  * 得到所有用户信息
   	  */
   	 var trStore=Ext.create('Ext.data.Store', {
   		    model: putProductModel,
   		    pageSize:defaultPageSize,
   		    proxy: {
   		    	type: 'ajax',
   		    	url:rootUrl+'/putproductcontroller/queryputProduct',
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
            },{
              dataIndex: 'modelid',
              hidden:true
            },{
           	  text: '产品名称',
                flex: 1,
                align:'center',
                dataIndex: 'proname',
                sortable: true
            },{
         	  text: '模版',
              flex: 1,
              align:'center',
              dataIndex: 'name',
              sortable: true
            },{
                dataIndex: 'advgroup1',
                hidden:true
            },{
                dataIndex: 'advgroup2',
                hidden:true
            },{
                dataIndex: 'advgroup3',
                hidden:true
            },{
                dataIndex: 'advgroup4',
                hidden:true
            },{
                dataIndex: 'advgroup5',
                hidden:true
            },{
	         	  text: '一号位广告集',
	              flex: 1,
	              id:'advgroupname1id',
	              align:'center',
	              dataIndex: 'advgroupname1',
	              sortable: true
            },{
           	  text: '二号位广告集',
	            flex: 1,
	            align:'center',
	            dataIndex: 'advgroupname2',
	            sortable: true
            },{
           	  text: '三号位广告集',
                flex: 1,
                align:'center',
                dataIndex: 'advgroupname3',
                sortable: true
            },{
           	  text: '四号位广告集',
                flex: 1,
                align:'center',
                dataIndex: 'advgroupname4',
                sortable: true
            },{
           	  text: '五号位广告集',
                flex: 1,
                align:'center',
                dataIndex: 'advgroupname5',
                sortable: true
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
            },tbar: [addAction,updateAction,deleteAction,refreshAction,exportAction,'->',
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
    name: 'putProduct',
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
				items:putProductGrid
			}]

    	});
    }
});