
var addOrUpdateUrL=rootUrl+'/userController/editUser';
var addUpdateFlag=true;

/**
 * 主面板
 */
var _tree=Ext.define('KitchenSink.view.tree.TreeGrid', {
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
    	var roleRadio=new Array();

    	var puserid=getUserId();
    	var valiateAdd=function(value){
    		var result=true;
        	Ext.Ajax.request({
    		    url: rootUrl+'/userController/valiateAdd',
    		    async: false,
    		    params:{
    		    	username:value
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
    	
    	
    	var valiateUpdate=function(id,value){
    		var result=true;
        	Ext.Ajax.request({
    		    url: rootUrl+'/userController/valiateUpdate',
    		    async: false,
    		    params:{
    		    	username:value,
    		    	userid:id
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
    	
    	var me=this;
//    	var fuserids=null;
        var simple = Ext.widget({
            xtype: 'form',
            id: 'simpleForm',
            frame: false,
            boder:false,
            bodyPadding: '0 20 0 20',
            fieldDefaults: {
                labelWidth : 120,
                labelAlign : 'right',
                labelStyle : 'margin-left:-20px;margin-top:15px;',
                fieldStyle : 'margin-top:15px;margin-right:80px;'
            },
            defaultType: 'textfield',
            items: [
      { xtype: 'fieldset',
        title: '基本信息',
        defaultType: 'textfield',
        defaults: {
            anchor: '100%'
        },
        items:[{
                fieldLabel: '用户ID',
                name: 'userid',
                id:'_userid',
                hidden:true
            },{
            	fieldLabel: '用户名称',
                name: 'user_name',
                id: '_user_name',
                allowBlank: false,
                blankText: '不能为空!',
                validateOnBlur:true,
                invalidText: '有重复记录',
                validator:function(){
                	var value=this.getValue();
                	var result=true;
                	if(!addUpdateFlag){
                		var userid=Ext.getCmp('_userid').getValue();
                		//修改操作
                		result=valiateUpdate(userid,value);
                	}else{
                		//新增操作
                    	result=valiateAdd(value);
                    }
                	return result;
                	}
                	
            },{
                fieldLabel: '用户密码',
                name: 'pword',
                id:'_pword',
                allowBlank: false
            },{
                fieldLabel: '用户手机号',
                name: 'telphone',
                id: '_telphone',
                allowBlank: false
            },{
                fieldLabel: '用户邮箱',
                name: 'email',
                id: '_email',
                allowBlank: false
            },{
            	xtype: 'textareafield',
            	height: 80,
                margin: '0',
                fieldLabel: '用户描述',
                name: 'description',
                fieldStyle:'margin-bottom:15px;margin-top:15px',
                id: '_description'
            }
            ]}
            ],

            buttons: [{
                text: '提交',
                handler: function() {
                	var username=Ext.getCmp('_user_name').getValue();
                	var result=username.replace(/[^\x00-\xff]/g, "**").length;
                	if(result>10){
                		Ext.Msg.alert('温馨提示',"用户名字节数太长,请小于11位");
                		return;
                	}
                	this.up('form').getForm().submit({
                                clientValidation:true,
                                waitMsg:'请稍候',
                                waitTitle:'正在更新',
                                url:addOrUpdateUrL,
                                success:function(form,action){
                                	if(action&&action.result&&action.result.success){
                                		if(action.result.success=="true"){
                                			//成功后更新表格并关闭win
                                			trStore.reload();
                                			win.close();
                                		}else if(action.result.success=="false"){
                                			win.close();
                                			Ext.Msg.alert('温馨提示',"更新失败");
                                		}
                                	}else{
                                		win.close();
                                		Ext.Msg.alert('温馨提示',"更新失败");
                                	}
                                },  
                                failure:function(form,action){  
                                	
                                }  
                                     
                            })
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
         * 主角信息选项
         */
//        --------------------------------------------------------------------------------
        var rolePanel=Ext.create('Ext.form.Panel', {
            width: 300,
            height: 125,
            frame:false,
            border:false,
            bodyPadding: 10,
            xtype: 'fieldset',
            items:[{
            	xtype:'radiogroup',
	            layout: {
	                type: 'table',
	                columns: 3
	            },
	            id:"_checkbox",
	            items:roleRadio
            },{
            	xtype: 'textfield',
            	fieldLabel: '用户ID',
            	hidden:true,
            	name: 'userid',
                id: '_userids'
            }],
            
            buttons: [{
              text: '提交',
              handler: function() {
                 	this.up('form').getForm().submit({
                        clientValidation:true,
                        waitMsg:'请稍候',
                        waitTitle:'正在更新',
                        url:rootUrl+'/userController/addUserRole',
                        success:function(form,action){
                        	if(action&&action.result&&action.result.success){
                        		if(action.result.success=="true"){
                        			//成功后更新表格并关闭win
                        			trStore.reload();
                        		}
                        	}else{
                        		Ext.Msg.alert('温馨提示',"更新失败");
                        	}
                        	assagin.close();
                        },  
                        failure:function(form,action){  
                        	
                        }  
                    })

              }
          },{
              text: '取消',
              handler: function(){
              	assagin.close();
              }
          }]
        });
//        ======================================
        
        /**
         * 角色信息选项所需的弹出的window面板
         */
        var assagin = Ext.create('widget.window', {
            title: '用户分配角色',
            header: {
                titlePosition: 2,
                titleAlign: 'center'
            },
            closable: true,
            closeAction: 'hide',
            width: 450,
            minWidth: 250,
            height: 250,
            tools: [{type: 'pin'}],
            layout: {
                type: 'border'
            },
            items: {
                region: 'center',
                xtype: 'panel',
                layout:'fit',
                items:rolePanel
            }
        });
//        ----------------------------------------------------------------------------------
        /**
         * 新增或更新弹出的面板
         */
        var win = Ext.create('widget.window', {
                title: '编辑',
                header: {
                    titlePosition: 2,
                    titleAlign: 'center'
                },
                closable: true,
                closeAction: 'hide',
                width: 450,
                minWidth: 350,
                height: 450,
                layout: {
                    type: 'border'
                },
                items: {
                    region: 'center',
                    xtype: 'panel',
                    layout:'fit',
                    items:simple
                }
            });
    	 //菜单模型       
    	Ext.define('Ext.menu.model', {
    	    extend: 'Ext.data.Model',
    	    fields: [{
    	        name: 'userid',
    	        type: 'string'
    	    },{
    	        name: 'pword',
    	        type: 'string'
    	    }, {
    	        name: 'user_name',
    	        type: 'string'
    	    }, {
    	        name: 'telphone',
    	        type: 'string'
    	    }, {
    	        name: 'email',
    	        type: 'string'
    	    }, {
    	        name: 'create_time',
    	        type: 'string'
    	    }, {
    	        name: 'login_time',
    	        type: 'string'
    	    }, {
    	        name: 'last_login_time',
    	        type: 'string'
    	    }, {
    	        name: 'login_times',
    	        type: 'string'
    	    },{
    	        name: 'description',
    	        type: 'string'
    	    },{
    	        name: 'puserid',
    	        type: 'string'
    	    }]
    	}); 
    	
    	
    	 /**
    	  * 得到所有用户信息
    	  */
    	 var trStore=Ext.create('Ext.data.Store', {
    		    storeId:'simpsonsStore',
    		    model: Ext.menu.model,
    		    pageSize:defaultPageSize,
    		    proxy: {
    		    	type: 'ajax',
    		    	url:rootUrl+'/userController/getUser',
    		        reader: {
    		            type: 'json',
    		            root: 'elementList',  
    		            totalProperty: 'total'
    		        }
    		    }
    		});
    	 trStore.loadPage(1);
    	 var trStorereLoad=function(){
	    	 trStore.reload({
		        	callback:function(records, options, success){
		        		if(records.length==0){
		        			if(options.page==1){
		        				return;
		        			}else{
		        				trStore.loadPage(options.page-1);        	    			        				
		        			}
		        		}
		        	}
		        });
    	 }
    	 /**
    	  * ajax请求
    	  */
    	var Extshow=function(param){ Ext.Msg.show({
      	     title:'温馨提示',
      	     msg: '确定要删除吗?',
      	   buttonText: {yes:'是', no:'取消'},
      	     icon: Ext.Msg.QUESTION,
      	     fn:function(btn,txt){
      	    	 if(btn=="yes"){
   	    		 Ext.Ajax.request({
    			    url: rootUrl+'/userController/deleteUser',
    			    params: {
    			        userid: param//tempdata.userid
    			    },
    			    success: function(response){
    			    	var respText = Ext.JSON.decode(response.responseText);
    			    	Ext.Msg.alert('温馨提示',"成功删除了"+respText.data+"条记录");
    			    	trStorereLoad();
    			    }
    			});
      	    		
      	    	 }
      	     }
    		});
    	}
    	 /**
    	  * 删除按钮
    	  */
        var deleteAction = Ext.create('Ext.Action', {
            //icon: '../../../components/extjs/shared/icons/fam/delete.gif',  // Use a URL in the icon config
        	iconCls:'common_del',
            text: '删除',
            handler: function(widget, event) {
                var rec = me.getSelectionModel().getSelection();
                if(rec.length==0){
                	Ext.Msg.alert('温馨提示',"请先选中一条!");
                }else if(rec.length>1){
                	var params=new Array();
                	for(var i=0;i<rec.length;i++){
                		params.push(rec[i].data.userid);
                	}
                	Extshow(params.toString());             	
                }else{
                	 var tempdata=rec[0].data;
                	 Extshow(tempdata.userid);
                }
          } 
        });
        /**
         * 新增按钮
         */
        var addAction = Ext.create('Ext.Action', {
            //icon: '../../../components/extjs/shared/icons/fam/add.gif',
        	iconCls:'common_add',
            text: '新增',
            handler: function(widget, event) {
            	Ext.getCmp("simpleForm").getForm().reset();
            	win.show(this,function(){
            		addUpdateFlag=true;
            		win.setTitle("新增");
            		addOrUpdateUrL=rootUrl+'/userController/addUser';
    			});
            }
        });
        /**
         * 分配角色按钮
         */
        var assignedRole=Ext.create('Ext.Action',{
            //icon: '../../../components/extjs/shared/icons/fam/user_edit.png',
        	iconCls:'common_role',
            text: '分配角色',
            handler: function(widget, event) {
            	var rec = me.getSelectionModel().getSelection();
                if(rec.length!=1){
                	Ext.Msg.alert('温馨提示',"请先选中一条!");
                	return;
                }
                var userid=rec[0].data.userid
                roleRadio=[];
    	        Ext.getCmp('_checkbox').removeAll();
            	Ext.Ajax.request({
            	    url : rootUrl+'/userController/getRoleById',
            	    async: false,
            	    params:{
            	    	userid:userid
            	    },
            	    success: function(response){
            	        var text = response.responseText;
            	        var respText = Ext.JSON.decode(text);
            	        var result = new Array();
            	        for(var i=0;i<respText.data.length;i++){
            	        	var obj = {};
            	        	obj.inputValue=""+respText.data[i].trid
            	        	obj.boxLabel=""+respText.data[i].name;
            	        	obj.checked=respText.data[i].checked?true:false;
            	        	obj.name="trid";
            	        	result.push(obj);
            	        }
            	        roleRadio=result;
            	        Ext.getCmp('_checkbox').add(roleRadio);
            	        Ext.getCmp('_checkbox').doLayout();
            	       
            	    }
            	});
            	
            	assagin.show(this,function(){
            		Ext.getCmp('_userids').setValue(userid);
    			});
            }
        });
        /**
         * 更新按钮
         */
        var updateAction = Ext.create('Ext.Action', {
            //icon: '../../../components/extjs/shared/icons/fam/table_refresh.png',
        	iconCls:'common_edit',
            text: '更改',
            handler: function(widget, event) {
            	addUpdateFlag=false;
            	Ext.getCmp("simpleForm").getForm().reset();
                var rec = me.getSelectionModel().getSelection();
                if(rec){
                	 if(rec.length==0){
                     	Ext.Msg.alert('温馨提示',"请先选中一条!");
                	 }else if(rec.length>1){
                		 Ext.Msg.alert('温馨提示',"您选中了多条记录!</br>编辑只能选择一条");
                	 }else{
                		 var tempdata=rec[0].data;
                			win.show(this,function(){
                				addOrUpdateUrL=rootUrl+'/userController/editUser';
                				win.setTitle("更新");
                				Ext.getCmp("_userid").setValue(tempdata.userid);
                       		 	Ext.getCmp("_pword").setValue(tempdata.pword);
                       		 	Ext.getCmp("_user_name").setValue(tempdata.user_name);
                       		 	Ext.getCmp("_telphone").setValue(tempdata.telphone);
                       		 	Ext.getCmp("_email").setValue(tempdata.email);
                       		 	Ext.getCmp("_description").setValue(tempdata.description);
                			});
                	 }
                }

            
            }
        });
        Ext.apply(this, {
        	store:trStore,
        	 columnLines:true,
        	 rowLines:true, 
      		flex:1,
    		minWidth:900,
    		maxWidth:1300,
            columns: [{
                text: '用户ID',
                flex: 1,
                sortable: true,
                hidden:true,
                dataIndex: 'userid'
            },{
                text: '用户密码',
                flex: 1,
                dataIndex: 'pword',
                hidden:true,
                sortable: true
            },{
        	   text: '用户名称',
               flex: 1,
               dataIndex: 'user_name',
               sortable: true
           },{
                text: '用户手机号码',
                flex: 1,
                dataIndex: 'telphone',
                sortable: true
            },{
                text: '用户邮箱',
                flex: 1,
                dataIndex: 'email',
                sortable: true
            },{
                text: '用户创建时间',
                flex: 1,
                hidden:true,
                dataIndex: 'create_time',
                sortable: true
            },{
                text: '第一次登陆时间',
                flex: 1,
                hidden:true,
                dataIndex: 'login_time',
                sortable: true
            },{
                text: '最近一次登陆时间',
                flex: 1,
                dataIndex: 'last_login_time',
                sortable: true
            },{
                text: '登陆次数',
                flex: 1,
                dataIndex: 'login_times',
                sortable: true
            },{
                text: '用户描述',
                flex: 1,
                dataIndex: 'description',
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
            },
            selModel:Ext.create('Ext.selection.CheckboxModel',{mode:"MULTI",width : 222,header : "a"}),
            tbar: [
                   addAction,
                   updateAction,
                   deleteAction,
                   assignedRole,
                   '->',
                   {
		        		xtype:'textfield',
		        		emptyText: '请输入需要搜索的信息',
		        		width:200,
		                name: 'searchCondiction',
		                id: '_searchCondiction'
                   },{
                	   xtype:'button',
                	   text: '搜索',
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




Ext.application({
    name: 'menuManger',
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
				items:_tree
			}]
    	});
    }
});

