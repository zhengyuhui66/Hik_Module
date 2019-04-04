
var corMangerGrid=Ext.define('Ext.corMangerGrid.TreeGrid', {
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
    	 * 公司信息模型
    	 */
    	Ext.define('corMangerModel', {
    	    extend: 'Ext.data.Model',
    	    fields:["id","name","address","createtime","creator","modifier","modifytime","mark"]
    	});
      	var getIfhavCorp=function(name){
    		flag=true;
    		$.ajax({
				type : "GET", //提交方式  
				url : rootUrl + '/common/queryIfhaveCorpInfo',//路径  
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
	                   fieldLabel: '公司名称',
	                   allowBlank: false,
	                   name: 'name',
	                   id:'_name'
	               },{
	            	   xtype: 'textfield',
	                   fieldLabel: '公司地址',
	                   allowBlank: false,
	                   name: 'address',
	                   id:'_address'
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
			                	if(win.title=='新增公司'){
				                	var ifhave=getIfhavCorp(_name);
				                	if(ifhave){
				                		 Ext.Msg.show({
		                           				title : '提示',
		                           				msg :"提交失败!</br>公司名称重复录入",
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
		                                url: rootUrl+'/cormangercontroller/addorUpdatecormanger',
		                                success:function(form,action){
		                                	if(action&&action.result&&action.result.success){
		                                		if(action.result.success=="true"){
		                                			trStore.reload();
		                                		}else if(action.result.success=="false"){
//		                                			Ext.Msg.alert('温馨提示',"上传失败!");
		                                			Ext.Msg.show({
		                                				title : '提示',
		                                				msg :"上传失败!",
		                                				width : 250,
		                                				icon : Ext.Msg.ERROR,//INFO,ERROR,QUESTION,WARNING
		                                				buttonText: { ok: '确定' }
		                                			});
		                                		}
		                                	}else{
//		                                		Ext.Msg.alert('温馨提示',"上传失败!");
		                                		Ext.Msg.show({
		                            				title : '提示',
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
		                                	Ext.getCmp('simpleForm').getForm().reset();
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
        /**
         * 新增按钮
         */
        var addAction = Ext.create('Ext.Action', {
        	iconCls:'common_add',
            text: '新增',
            handler: function(widget, event) {
            	win.show(this,function(){
            		Ext.getCmp("simpleForm").getForm().reset();
            		win.setTitle("新增公司");
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
            	var url=rootUrl+'/cormangercontroller/exportcormanger?';
            	var searchStr  = Ext.getCmp('_searchCondiction').getValue();
            	if(searchStr){
            		url+="searchStr="+searchStr+"&"
            	}
        		var title='公司信息';
            	exportExcel(me,url,title);
//     		   trStore.getProxy().extraParams = {searchStr:searchStr};
        		
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
                      	Ext.Msg.alert('温馨提示',"请先选中一条!");
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
                             	    url:rootUrl+'/cormangercontroller/deletecormanger',//addOrUpdateUrL,
                             	    async: false,
                             	    params: {
                             	    	ids: params.toString()
                     			    },
                             	    success: function(response){
                             	    	var respText = Ext.JSON.decode(response.responseText);
//                     			    	Ext.Msg.alert('删除成功',"共删除"+respText.data+"条记录");
                             	    	Ext.Msg.show({
                            				title : '提示',
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
         * 更改物料按钮
         */
        var updateAction = Ext.create('Ext.Action', {
        	iconCls:'common_edit',
            text: '更改公司信息',
            handler: function(widget, event) {
                var rec = me.getSelectionModel().getSelection();
                if(rec){
                	 if(rec.length==0){
//                     	Ext.Msg.alert('温馨提示',"请先选中一条!");
                		 Ext.Msg.show({
             				title : '提示',
             				msg :"请先选中一条!",
             				width : 250,
             				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
             				buttonText: { ok: '确定' }
             			});
                	 }else if(rec.length>1){
//                		 Ext.Msg.alert('温馨提示',"您选中了多条记录!</br>编辑只能选择一条");
                		 Ext.Msg.show({
             				title : '提示',
             				msg :"您选中了多条记录!</br>编辑只能选择一条",
             				width : 250,
             				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
             				buttonText: { ok: '确定' }
             			});
                	 }else{
                		 var tempdata=rec[0].data;
                			win.show(this,function(){
                			
                				win.setTitle("更新公司信息");
    		                	Ext.getCmp('_id').setValue(tempdata.id);
			                	Ext.getCmp('_name').setValue(tempdata.name);
			                	Ext.getCmp('_address').setValue(tempdata.address);
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
   		    	url:rootUrl+'/cormangercontroller/querycormanger',
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
           	  text: '公司名称',
	            flex: 1,
	            align:'center',
	            dataIndex: 'name',
	            sortable: true
            },{
         	  text: '公司地址',
              flex: 1,
              align:'center',
              dataIndex: 'address',
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
    name: 'mainCorp',
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
				items:corMangerGrid
			}]

    	});
    }
});