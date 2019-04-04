var advnum=9;
var advGroupSetGrid=Ext.define('Ext.advGroupSetGrid.TreeGrid', {
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
    	Ext.define('advGroupSetModel', {
    	    extend: 'Ext.data.Model',
    	    fields: [{name: 'id', type: 'string'},
    	             {name: 'advname1', type: 'string'},
    	             {name: 'advname2', type: 'string'},
    	             {name: 'advname3', type: 'string'},
    	             {name: 'advname4', type: 'string'},
    	             {name: 'advname5', type: 'string'},
    	             {name: 'advname6', type: 'string'},
    	             {name: 'advname7', type: 'string'},
    	             {name: 'advname8', type: 'string'},
    	             {name: 'advname9', type: 'string'},
    	             {name: 'advid1', type: 'string'},
    	             {name: 'advid2', type: 'string'},
    	             {name: 'advid3', type: 'string'},
    	             {name: 'advid4', type: 'string'},
    	             {name: 'advid5', type: 'string'},
    	             {name: 'advid6', type: 'string'},
    	             {name: 'advid7', type: 'string'},
    	             {name: 'advid8', type: 'string'},
    	             {name: 'advid9', type: 'string'},
    	             {name: 'creator', type: 'string'},
    	             {name: 'creatime', type: 'string'},
    	             {name: 'modifier', type: 'string'},
    	             {name: 'modifytime', type: 'string'},
    	             {name: 'playstrager', type: 'string'},
    	             {name: 'playpristrager', type: 'string'},
    	             {name: 'playstragerid', type: 'string'},
//    	             {name: 'playpristragerid', type: 'string'},
    	             {name: 'descr', type: 'string'},
    	             {name: 'name', type: 'string'}]
    	});
    	Ext.define('advmodel', {
    	    extend: 'Ext.data.Model',
    	    fields: ['advertid','name']
    	});
    	var getadvStore=function(){
    		return Ext.create('Ext.data.Store', {
        		model : 'advmodel',
        		proxy : {
        			type : 'ajax',
        			url : rootUrl + '/common/queryAllAdvert',
        			reader : {
        				type : 'json',
        				root : 'data'
        			}
        		},
        		autoLoad:true
        	});
    	}
    	
    	var advStore= new Array();
    	for(var i=1;i<=advnum;i++){
    		var temp=getadvStore();
    		advStore.push(temp);
    	}

    	Ext.define('Ext.advprosetting', {
    		extend : 'Ext.data.Model',
    		fields : ['id','name']
    	});
    	
    	var playfunStore=Ext.create('Ext.data.Store',{
        		model:'Ext.advprosetting',
        		proxy:{
        			type : 'ajax',
        			url : rootUrl + '/common/queryplayfun',
        			reader : {
        				type : 'json',
        				root : 'data'
        			}
        		},autoLoad:true
        	});
    	
    	var getadvPropertysStore=function(){
    		return Ext.create('Ext.data.Store', {
        		model : 'Ext.advprosetting',
        		proxy : {
        			type : 'ajax',
        			url : rootUrl+'/common/getPropertys',
        			reader : {
        				type : 'json',
        				root : 'data'
        					}
        		}
        	});
    	}
    	var advPropertysStore= new Array();
    	for(var i=1;i<=advnum;i++){
    		var temp=getadvPropertysStore();
    		advPropertysStore.push(temp);
    	}

    	var getadvsubStore=function(){
    		return Ext.create('Ext.data.Store', {
        		model : 'Ext.advprosetting',
        		proxy : {
        			type : 'ajax',
        			url : rootUrl+'/common/getProperty',
        			reader : {
        				type : 'json',
        				root : 'data'
        			}
        		}
        	});
    	}
    	
    	var advsubStore= new Array();
    	for(var i=1;i<=advnum;i++){
    		var temp=getadvsubStore();
    		advsubStore.push(temp);
    	}

    	var getpropertysCombo=function(_advPropertysStore,_advsubStore,_advStore,i){
    		return Ext.create('Ext.form.ComboBox', {
    		    fieldLabel: '筛选',
    			emptyText:'广告'+i+'大分类',
    		    store: _advPropertysStore,
    		    displayField: 'name',
    		    valueField: 'id',
    		    flex:1,
    		    id:'propertysid'+i,
    		    listeners:{
    				change:function(th, record, index, eOpts){
//    					if(record&&record[0].data){
    						var id=record;
    						_advsubStore.on('beforeload', function (store, options) {
    					        var new_params = {id:id};
    					        Ext.apply(_advsubStore.proxy.extraParams, new_params);
    					    });
    						Ext.getCmp('propertyid'+i).setValue('');
    						Ext.getCmp('_advid'+i).setValue('');
    						_advsubStore.load();
    						_advStore.on('beforeload', function (store, options) {
    					        var new_params = {pid:id};
    					        Ext.apply(_advStore.proxy.extraParams, new_params);
    					    });
    						_advStore.load();
//    					}
    				}
    			}
    		});
    	}
    	var propertysCombo= new Array();
    	for(var i=1;i<=advnum;i++){
    		var temp=getpropertysCombo(advPropertysStore[i],advsubStore[i],advStore[i],i);
    		propertysCombo.push(temp);
    	}


    	var getpropertyCombo=function(_advsubStore,_advStore,i){
    		return Ext.create('Ext.form.ComboBox', {
    			emptyText:'广告'+i+'小分类',
    			margin:'0 0 0 10',
    		    store: _advsubStore,
    		    displayField: 'name',
    		    valueField: 'id',
    		    flex:1,
    		    id:'propertyid'+i,
    		    listeners:{
    				change:function(th, record, index, eOpts){
//    					if(record&&record[0].data){
    						var id=record;
    						var pid=Ext.getCmp('propertysid'+i).getValue();
    						_advStore.on('beforeload', function (store, options) {
    					        var new_params = { pid:pid,id:id};
    					        Ext.apply(_advStore.proxy.extraParams, new_params);
    					    });
    						Ext.getCmp('_advid'+i).setValue('');
    						_advStore.load();
//    					}
    				}
    			}
    		});
    	}
    	
    	var propertyCombo= new Array();
    	for(var i=1;i<=advnum;i++){
    		var temp=getpropertyCombo(advsubStore[i],advStore[i],i);
    		propertyCombo.push(temp);
    	}


    	var getAdvCombo=function(i,_advStore){
    		return Ext.create('Ext.form.ComboBox', {
    		    fieldLabel: '广告'+i,
    		    store: _advStore,
    		    flex:2,
    		    displayField: 'name',
    		    valueField: 'advertid',
    		    id:'_advid'+i,
    		    name:'advid'+i
    		});
    	}
    	var advCombo=new Array();
    	for(var i=1;i<=advnum;i++){
    		var temp=getAdvCombo(i,advStore[i]);
    		advCombo.push(temp);
    	}

    	
    	
        var simple = Ext.widget({
            xtype: 'form',
            id: 'simpleForm',
            fieldDefaults: {
                labelWidth : 60,
                labelAlign : 'right'
            },
            padding:'20 20 0 0',
            border:false,
            layout:{
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
	                   maxLength:30,//最多字符设置
	                   maxLengthText:'最多可输入30个字',
	                   name: 'name',
	                   id:'_name'
	               },{ margin:'10 0 0 0',
  		        		items:[propertysCombo[0],propertyCombo[0],advCombo[0]]
	               },{ margin:'10 0 0 0',
  		        		items:[propertysCombo[1],propertyCombo[1],advCombo[1]]
	               },{ margin:'10 0 0 0',
  		        		items:[propertysCombo[2],propertyCombo[2],advCombo[2]]
	               },{ margin:'10 0 0 0',
  		        		items:[propertysCombo[3],propertyCombo[3],advCombo[3]]
	               },{ margin:'10 0 0 0',
  		        		items:[propertysCombo[4],propertyCombo[4],advCombo[4]]
	               },{ margin:'10 0 0 0',
	            		items:[propertysCombo[5],propertyCombo[5],advCombo[5]]
	               },{ margin:'10 0 0 0',
	            		items:[propertysCombo[6],propertyCombo[6],advCombo[6]]
	               },{ margin:'10 0 0 0',
	            		items:[propertysCombo[7],propertyCombo[7],advCombo[7]]
	               },{ margin:'10 0 0 0',
	            		items:[propertysCombo[8],propertyCombo[8],advCombo[8]]
	               },{
	        			fieldLabel:'播放策略',
   		        		xtype:'combo',
   		    			margin:'10 10 10 10',
   		    		    store: playfunStore,
   		    		    displayField: 'name',
   		    		    allowBlank: false,
   		    		    valueField: 'id',
   		    		    name:'playstrager',
   		    		    id:'_playstrager',
   		    		    flex:1,
	   		    		 onReplicate: function(){
					            this.getStore().clearFilter();
	   		    		 }
	               }],
	               buttons: [{
			                text: '提交',
			                handler: function() {
			                	var f=false;
			                	for(var i=1;i<9;i++){
			                		var temp=Ext.getCmp('_advid'+i).getValue()
			                		if(temp){
			                			f=true;
			                			break;
			                		}
			                	}
			                	if(!f){
//			                		Ext.Msg.alert('温馨提示',"至少需要一则广告!");
			                		Ext.Msg.show({
			            				title : '提示',
			            				msg : '至少需要一则广告!',
			            				width : 250,
			            				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
			            				buttonText: { ok: '确定' }
			            			});
			                		return;
			                	}
			                	this.up('form').getForm().submit({
		                                clientValidation:true,
		                                waitMsg:'请稍候',
		                                waitTitle:'正在更新',
		                                url: rootUrl+'/advertGroupController/saveOrupdateAdvertgroup',
		                                success:function(form,action){
		                                	if(action&&action.result&&action.result.success){
		                                		if(action.result.success=="true"){
		                                			trStore.reload();
		                                		}else if(action.result.success=="false"){
//		                                			Ext.Msg.alert('温馨提示',"上传失败!");
		                                			Ext.Msg.show({
		        			            				title : '提示',
		        			            				msg : '上传失败!',
		        			            				width : 250,
		        			            				icon : Ext.Msg.ERROR,
		        			            				buttonText: { ok: '确定' }
		        			            			});
		                                		}
		                                	}else{
		                                		Ext.Msg.show({
		    			            				title : '提示',
		    			            				msg : '上传失败!',
		    			            				width : 250,
		    			            				icon : Ext.Msg.ERROR,
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
         * 广告集的面板
         */
        var win = Ext.create('widget.window', {
                title: '广告集',
                header: {
                    titlePosition: 2,
                    titleAlign: 'center'
                },
                closable: false,
                modal: true,
                closeAction: 'hide',
                width: 550,
                minWidth: 350,
                height: 490,
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
//            		Ext.getCmp('_playstrager').setValue(1);  
//                	Ext.getCmp('_playpristrager').setValue(2);
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
            text: '删除广告集',
            handler: function(widget, event) {

            	 var rec = me.getSelectionModel().getSelection();
                 if(rec){
                 	 if(rec.length==0){
                 		Ext.Msg.show({
            				title : '提示',
            				msg : '请先选中一条!',
            				width : 250,
            				icon : Ext.Msg.ERROR,
            				buttons :Ext.Msg.OK
            			});
//                      	Ext.Msg.alert('温馨提示',"请先选中一条!");
                 	 }else if(rec.length>0){
                 		var params=new Array();
                    	for(var i=0;i<rec.length;i++){
                    		params.push(rec[i].data.id);
                    	}
                    	
                     	Ext.Msg.show({
                    	     title:'温馨提示',
                     	     msg: '确定要删除吗?',
                     	    buttonText: {yes:'确定', no:'取消'},
                     	     icon: Ext.Msg.QUESTION,
                     	     fn:function(btn,txt){
                     	    	 if(btn=="yes"){
                                 	Ext.Ajax.request({
                             	    url:rootUrl+'/advertGroupController/deleteAdvertgroup',//addOrUpdateUrL,
                             	    async: false,
                             	    params: {
                             	    	ids: params.toString()
                     			    },
                             	    success: function(response){
                             	    	var respText = Ext.JSON.decode(response.responseText);
                             	    	if(respText.success=="true"){
                             	    		Ext.Msg.show({
                                				title : '提示',
                                				msg : "成功删除了"+respText.data+"条记录",
                                				width : 250,
                                				icon : Ext.Msg.INFO,
                                				buttonText: { ok: '确定' }
                                			});
//                         			    	Ext.Msg.alert('温馨提示',"成功删除了"+respText.data+"条记录");
                         			    	trStore.reload();
                             	    	}else{
                             	    		Ext.Msg.show({
                                				title : '提示',
                                				msg : '广告集已存在于投放产品中不能删除',
                                				width : 250,
                                				icon : Ext.Msg.ERROR,
                                				bbuttonText: { ok: '确定' }
                                			});
//                             	    		Ext.Msg.alert('温馨提示',"广告集已存在于投放产品中不能删除");
                             	    	}
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
            text: '更改广告集',
            handler: function(widget, event) {
                var rec = me.getSelectionModel().getSelection();
                if(rec){
                	 if(rec.length==0){
                		 Ext.Msg.show({
             				title : '提示',
             				msg : "请先选中一条!",
             				width : 250,
             				icon : Ext.Msg.WARNING,
             				buttonText: { ok: '确定' }
             			});
//                     	Ext.Msg.alert('温馨提示',"请先选中一条!");
                	 }else if(rec.length>1){
                		 Ext.Msg.show({
             				title : '提示',
             				msg : "您选中了多条记录!</br>编辑只能选择一条",
             				width : 250,
             				icon : Ext.Msg.WARNING,
             				buttonText: { ok: '确定' }
             			});
//                		 Ext.Msg.alert('温馨提示',"您选中了多条记录!</br>编辑只能选择一条");
                	 }else{
                		 var tempdata=rec[0].data;
                			win.show(this,function(){
                				win.setTitle("更新");
                				Ext.getCmp("simpleForm").getForm().reset();
    		                	Ext.getCmp('_id').setValue(tempdata.id);
			                	Ext.getCmp('_name').setValue(tempdata.name);
			                	Ext.getCmp('_advid1').setValue(tempdata.advid1?+(tempdata.advid1):'');  
			                	Ext.getCmp('_advid2').setValue(tempdata.advid2?+(tempdata.advid2):'');
			                	Ext.getCmp('_advid3').setValue(tempdata.advid3?+(tempdata.advid3):'');
			                	Ext.getCmp('_advid4').setValue(tempdata.advid4?+(tempdata.advid4):'');
			                	Ext.getCmp('_advid5').setValue(tempdata.advid5?+(tempdata.advid5):'');
			                	Ext.getCmp('_advid6').setValue(tempdata.advid6?+(tempdata.advid6):'');
			                	Ext.getCmp('_advid7').setValue(tempdata.advid7?+(tempdata.advid7):'');
			                	Ext.getCmp('_advid8').setValue(tempdata.advid8?+(tempdata.advid8):'');
			                	Ext.getCmp('_advid9').setValue(tempdata.advid8?+(tempdata.advid8):'');
			                	Ext.getCmp('_playstrager').setValue(tempdata.playstragerid?+(tempdata.playstragerid):'');  
//			                	Ext.getCmp('_playpristrager').setValue(tempdata.playpristragerid?+(tempdata.playpristragerid):'');
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
   		    model: advGroupSetModel,
   		    pageSize:defaultPageSize,
   		    proxy: {
   		    	type: 'ajax',
   		    	url:rootUrl+'/advertGroupController/getAdvertgroup',
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
         	  text: '名称',
              flex: 1,
              align:'center',
              dataIndex: 'name',
              sortable: true
            },{
	         	  text: 'ID',
	         	 hidden:true,
	              flex: 1,
	              align:'center',
	              dataIndex: 'id',
	              sortable: true
            },{
           	  	text: '广告1',
                flex: 1,
                align:'center',

                dataIndex: 'advname1',
                sortable: true
            },{
             	  text: '广告2',
                  flex: 1,
                  align:'center',
                  dataIndex: 'advname2',
                  sortable: true
            },{
             	  text: '广告3',
                  flex: 1,
                  align:'center',
                  dataIndex: 'advname3',
                  sortable: true
            },{
             	  text: '广告4',
                  flex: 1,
                  align:'center',
                  dataIndex: 'advname4',
                  sortable: true
            },{
             	  text: '广告5',
                  flex: 1,
                  align:'center',
                  dataIndex: 'advname5',
                  sortable: true
            },{
             	  text: '广告6',
                  flex: 1,
                  align:'center',
                  dataIndex: 'advname6',
                  sortable: true
            },{
             	  text: '广告7',
                  flex: 1,
                  align:'center',
                  dataIndex: 'advname7',
                  sortable: true
            },{
             	  text: '广告8',
                  flex: 1,
                  align:'center',
                  dataIndex: 'advname8',
                  sortable: true
            },{
             	  text: '广告9',
                  flex: 1,
                  align:'center',
                  dataIndex: 'advname9',
                  sortable: true
            },{
	              dataIndex: 'advid1',
	              hidden:true
            },{
            	  dataIndex: 'advid2',
                  hidden:true
            },{
            	  dataIndex: 'advid3',
                  hidden:true
            },{
            	  dataIndex: 'advid4',
                  hidden:true
            },{
            	  dataIndex: 'advid5',
                  hidden:true
            },{
            	  dataIndex: 'advid6',
                  hidden:true
            },{
            	  dataIndex: 'advid7',
                  hidden:true
            },{
            	  dataIndex: 'advid8',
                  hidden:true
            },{
            	  dataIndex: 'advid9',
                  hidden:true
            },{
           	  text: '播放策略',
	          flex: 1,
	          align:'center',
	          dataIndex: 'playstrager',
	          sortable: true
            },{
           	  text: '优先级播放策略',
              flex: 1,
              align:'center',
              hidden:true,
              dataIndex: 'playpristrager',
              sortable: true
            },{
          	  dataIndex: 'playstragerid',
                hidden:true
//            },{
//          	  dataIndex: 'playpristragerid',
//                hidden:true
            },{
         	  text: '条件创建人',
              flex: 1,
              align:'center',
              hidden:true,
              dataIndex: 'creator',
              sortable: true
            },{
         	  text: '创建时间',
              flex: 1,
              align:'center',
              hidden:true,
              dataIndex: 'creatime',
              sortable: true
            },{
         	  text: '修改人',
              flex: 1,
              align:'center',
              hidden:true,
              dataIndex: 'modifier',
              sortable: true
            },{
         	  text: '修改时间',
              flex: 1,
              align:'center',
              hidden:true,
              dataIndex: 'modifytime',
              sortable: true
            },{
         	  text: '说明',
              flex: 1,
              align:'center',
              hidden:true,
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
    name: 'advGroupSet',
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
				items:advGroupSetGrid
			}]

    	});
    }
});