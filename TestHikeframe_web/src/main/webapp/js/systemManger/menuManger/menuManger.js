
//最后一项子菜单名称
var LastsubMenuNumber=3;
//默认RRID
var defaultRRID=0;
//所有权限JSON和数组集合

Array.prototype.remove=function(val){
	var num=this.indexOf(val);
	if(num>-1){
		this.splice(num, 1);
	}
}

/**
 * 递归实现
 * 取消先中时，所有的父节点角色，也一并选中
 */
var  setRoleCheckBoxTrue=function(id,obj){
	var _id=0;
	var bool=true;
	for(var i=0;i<obj.length;i++){
		if(obj[i].trid==id){
			_id=obj[i].pid;
			if(!Ext.getCmp('model_'+_id)){
				return;
			}
			Ext.getCmp('model_'+_id).setValue(true);
			setRoleCheckBoxTrue(_id,obj);
			bool=false;
		}
	}
	if(bool==true){
		return;
	}
}
/**
 * 递归实现
 * 取消先中时，所有的子节点角色，也一并消除
 */
var  setRoleCheckBoxFalse=function(id,obj){
	var _id=0;
	var bool=true;
	for(var i=0;i<obj.length;i++){
		if(obj[i].pid==id){
			_id=obj[i].trid;
			if(!Ext.getCmp('model_'+_id)){
				return;
			}
			Ext.getCmp('model_'+_id).setValue(false);
			setRoleCheckBoxFalse(_id,obj);
			bool=false;
		}
	}
	if(bool==true){
		return;
	}
}

//var rightName=new Array();
var rightNames=new Object();

var rightArray=[];
//全局参数
var right_param={
	text:"",
	rrid:"",
	description:"",
	role:"",
	leve:"",
	parent_id:""
}
//新增或更新地址
var addOrUpdateUrL="";
//必填提示	
var required = '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';
//AJAX加载角色信息
$.ajax({  
	type : "GET",  //提交方式  
	url : rootUrl+'/menuController/getRoleInfo',//路径  
	async:false,
	data : {},//数据，这里使用的是Json格式进行传输  
	success : function(result) {//返回数据根据结果进行相应的处理  
		 var respText = Ext.JSON.decode(result);
	 if(respText&&respText.data){
	 	var tempData=respText.data;
	 	for(var i=0;i<tempData.length;i++){
	 		var rights = new Object();
	 		rightNames[tempData[i].trid]=false;
	 		if(tempData[i]){
	 			rights.defaultType='checkboxfield';
	 			rights.name='model_type';
	 			rights.layout='hbox';
	 			var _items = new Array();
	 			//组装信息
	 			_items.push({boxLabel:tempData[i].name,
	 						name:'model_'+tempData[i].trid,
	 						id:'model_'+tempData[i].trid,
	 						inputValue:tempData[i].trid,
	 						flex:1,boxLabelAlign:'before',
	 						listeners:{
	 							change:function(field, newValue, oldValue, eOpts){
	 								if(newValue==true){
	 									setRoleCheckBoxTrue(this.inputValue,tempData);
	 									rightNames[this.inputValue]=true;
	 									Ext.getCmp("_role").setValue(JSON.stringify(rightNames));
	 								}else{
	 									setRoleCheckBoxFalse(this.inputValue,tempData);
	 									rightNames[this.inputValue]=false;
	 									Ext.getCmp("_role").setValue(JSON.stringify(rightNames));
	 								}
	 							}
	 						}});
	 			if(tempData[++i]){
	 				rightNames[tempData[i].trid]=false;
	 				_items.push({boxLabel:tempData[i].name,
 						name:'model_t',
 						id:'model_'+tempData[i].trid,
 						inputValue:tempData[i].trid,
 						flex:1,boxLabelAlign:'before',
 						listeners:{
 							change:function(field, newValue, oldValue, eOpts){
 								if(newValue==true){
 									setRoleCheckBoxTrue(this.inputValue,tempData);
 									rightNames[this.inputValue]=true;
 									Ext.getCmp("_role").setValue(JSON.stringify(rightNames));
 								}else{
 									setRoleCheckBoxFalse(this.inputValue,tempData);
 									rightNames[this.inputValue]=false;
 									Ext.getCmp("_role").setValue(JSON.stringify(rightNames));
 								}
 							}
 						}});
	 			}
	 			if(tempData[++i]){
	 				rightNames[tempData[i].trid]=false;
	 				_items.push({boxLabel:tempData[i].name,
 						name:'model_t',
 						id:'model_'+tempData[i].trid,
 						inputValue:tempData[i].trid,
 						flex:1,boxLabelAlign:'before',
 						listeners:{
 							change:function(field, newValue, oldValue, eOpts){
 								if(newValue==true){
 									setRoleCheckBoxTrue(this.inputValue,tempData);
 									rightNames[this.inputValue]=true;
 									Ext.getCmp("_role").setValue(JSON.stringify(rightNames));
 								}else{
 									setRoleCheckBoxFalse(this.inputValue,tempData);
 									rightNames[this.inputValue]=false;
 									Ext.getCmp("_role").setValue(JSON.stringify(rightNames));
 								}
 							}
 						}});
	 			}
	 			rights.items=_items;
	 		}
	 		rightArray.push(rights);
	 	}
	 } 
	}  
});  

//角色下拉store
var arrayStore=Ext.create('Ext.data.ArrayStore', {
    fields: ['value', 'text'],
    data : rightArray
});
//弹出窗的编辑和添加PANEL
    var simple = Ext.widget({
        xtype: 'form',
       
        id: 'simpleForm',
        border:false,
        frame: false,
        bodyPadding: '0 20 0 20',
        layout:{
        	type:'vbox',
        	align:'stretch',
        	pack:'center'
        },
        fieldDefaults: {
            labelStyle : 'margin-right:23px;margin-top:15px;',
        	style:'text-align:right;'
        },
        items:[{
        	  xtype: 'fieldset',
              title: '选项',
              defaultType: 'textfield',
              layout: 'anchor',
              flex:1,
              defaults: {
                  anchor: '100%'
              },
              items: [{
              	xtype:'textfield',
                fieldLabel: '目录名称',
                name: 'text',
                id:'_text',
                fieldStyle : 'margin-right:23px;margin-top:15px;',
                allowBlank: false
            },{
            	xtype:'textfield',
                fieldLabel: '链接地址',
                name: 'description',
                id:'_description',
                fieldStyle : 'margin-right:23px;margin-top:15px;',
                allowBlank: false
            },{
            	xtype:'textfield',
                fieldLabel: '索引值',
                name: 'rrid',
                id: '_rrid',
                hidden:true
            },{
            	xtype:'textfield',
                fieldLabel: '父类索引值',
                name: 'parent_id',
                hidden:true,
                id: '_parent_id'
            }, {
                fieldLabel: '菜单级别',
                name: 'leve',
                hidden:true,
                id: '_leve'
            }, {
                fieldLabel: '权限角色汇总',
                name: 'role',
                hidden:true,
                id: '_role'
            }
            ]},
            {
            	  xtype: 'fieldset',
                  title: '角色类型',
//                  width: 420,  //宽度220  
                  flex:1,
                  fieldLabel: '机型类型',  
                  items:rightArray
            }
        ],
        

        buttons: [{
            text: '提交',
            handler: function() {
            	this.up('form').getForm().submit({  
                            clientValidation:true,
                            waitMsg:'请稍候',
                            waitTitle:'正在更新',
                            url:addOrUpdateUrL,
                            success:function(form,action){
                            	if(action&&action.result&&action.result.success){
                            		if(action.result.success=="true"){
                            			//成功后更新表格并关闭win
                            			_store.reload();
                            			win.close();
                            		}else if(action.result.success=="false"){
                            			alert("更新失败！")
                            		}
                            	}else{
                            		alert("更新失败.")
                            	}
                            	 this.up('form').getForm().reset();
                            },  
                            failure:function(form,action){  
                            	 this.up('form').getForm().reset();
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

////弹出窗的编辑和添加WINDOW 
    
        var win = Ext.create('widget.window', {
                title: '编辑',
                header: {
                    titlePosition: 2,
                    titleAlign: 'center'
                },
                frame:false,
                border:false,
                closable: false,
                closeAction: 'hide',
                width: 450,
                minWidth: 350,
                height: 350,
                tools: [{type: 'pin'}],
                layout: {
                    type: 'border'
                },
                items: {
                    region: 'center',
                    xtype: 'panel',
                    layout:'fit',
                    items: simple
                }
            });
 //菜单模型       
Ext.define('Ext.menu.model', {
    extend: 'Ext.data.Model',
    fields: [{
        name: 'text',
        type: 'string'
    },{
        name: 'rrid',
        type: 'string'
    }, {
        name: 'parent_id',
        type: 'string'
    }, {
        name: 'description',
        type: 'string'
    }, {
        name: 'leve',
        type: 'string'
    }]
}); 

var _store=Ext.create('Ext.data.TreeStore',{
    model: Ext.menu.model,
    proxy: {
        type: 'ajax',
        url:rootUrl+'/menuController/editmenu'
    },
    folderSort: true
});



//-------------------------------角色信息-----------------------------------------------
var trStore=Ext.create('Ext.data.Store', {
    storeId:'simpsonsStore',
    fields:['trid', 'pid', 'name','description','create_time'],
    proxy: {
    	type: 'ajax',
    	url:rootUrl+'/menuController/getRoleInfo',
        reader: {
            type: 'json',
            root: 'data'
        }
    },
	autoLoad:true
});
var storeGrid=Ext.create('Ext.grid.Panel', {
    title: '角色列表',
    store: trStore,
    columns: [
        { text: '角色序号',  dataIndex: 'trid',flex:1},
        { text: '父类角色序号', dataIndex: 'pid',flex:1},
        { text: '角色名称', dataIndex: 'name',flex:1},
        { text: '角色描述', dataIndex: 'description',flex:1},
        { text: '角色创建时间', dataIndex: 'create_time',flex:1}
    ],
    height: 400,
    width: 400
});
//----------------------------------角色信息结束------------------------------------------------------
//---初始化树状面板展示菜单权限----------------------------------
var _tree=Ext.define('KitchenSink.view.tree.TreeGrid', {
    extend: 'Ext.tree.Panel',
    requires: [
        'Ext.data.*',
        'Ext.grid.*',
        'Ext.tree.*',
        'Ext.ux.CheckColumn',
    ],    
    xtype: 'tree-grid',
    autoScroll:true,
    overflowY:true,
//    minHeight:800,
//    maxHeight:1000,
	padding:'5 5 5 5',
    manageHeight : false,
    rootVisible: false,
    multiSelect: true,
    initComponent: function() {
    	var me=this;
    	/**
    	 * 删除按钮
    	 */
        var deleteAction = Ext.create('Ext.Action', {
            icon: '../../../components/extjs/shared/icons/fam/delete.gif',  // Use a URL in the icon config
            text: '删除菜单',
            disabled: true,
            handler: function(widget, event) {
                var rec = me.getSelectionModel().getSelection()[0];
                if (rec) {
                	Ext.Msg.show({
                	     title:'温馨提示',
                	     msg: '确定要删除吗?',
                	     buttonText: {yes:'是', no:'取消'},
                	     icon: Ext.Msg.QUESTION,
                	     fn:function(btn,txt){
                	    	 if(btn=="yes"){
                	    		 Ext.Ajax.request({
                	    			    url: rootUrl+'/menuController/deleteMenu',
                	    			    params: {
                	    			        rrid: rec.get('rrid')
                	    			    },
                	    			    success: function(response){
                	    			        var text = response.responseText;
                	    			        _store.reload();
                	    			    }
                	    			});
                	    	 }
                	     }
                	});
                }
            }
        });
        /**
         * 新增按钮
         */
        var addAction = Ext.create('Ext.Action', {
            icon: '../../../components/extjs/shared/icons/fam/add.gif',
            text: '添加子菜单',
            disabled: true,
            handler: function(widget, event) {
                var rec = me.getSelectionModel().getSelection()[0];
                if (rec) {
                		right_param.leve=rec.get('leve');
                		if(right_param.leve==LastsubMenuNumber){
                			Ext.Msg.alert('温馨提示',"此为最后一级菜单,不能添加子菜单!");
                			return;
                		}
                		addOrUpdateUrL=rootUrl+'/menuController/addMenu',
                		right_param.text=rec.get('text');
                		right_param.rrid=rec.get('rrid');
                		right_param.description=rec.get('description');
                		right_param.parent_id=rec.get('parent_id');
	                	win.show(this,function(){
	                		$("#_role").children().children().eq(1).children().css('margin-top','15px');
	                    	$("#window-1012_header_hd-textEl").html("添加");
	                    	Ext.getCmp("_parent_id").setValue(right_param.rrid);
	                    	Ext.getCmp("_rrid").setValue(defaultRRID);
	                    	Ext.getCmp("_leve").setValue(+(right_param.leve)+1);
	                	});
                }
            }
        });
        
        /**
         * 新增同级按钮
         */
        var addSamAction = Ext.create('Ext.Action', {
            icon: '../../../components/extjs/shared/icons/fam/add.gif',
            text: '添加同级菜单',
            disabled: true,
            handler: function(widget, event) {
                var rec = me.getSelectionModel().getSelection()[0];
                if (rec) {
                		right_param.leve=rec.get('leve');
                		addOrUpdateUrL=rootUrl+'/menuController/addMenu',
                		right_param.text=rec.get('text');
                		right_param.rrid=rec.get('rrid');
                		right_param.description=rec.get('description');
                		right_param.parent_id=rec.get('parent_id');
                	win.show(this,function(){
                		$("#_role").children().children().eq(1).children().css('margin-top','15px');
                    	$("#window-1012_header_hd-textEl").html("添加");
                    	Ext.getCmp("_parent_id").setValue(right_param.parent_id);
                    	Ext.getCmp("_rrid").setValue(defaultRRID);
                    	Ext.getCmp("_leve").setValue(right_param.leve);
                	});
                }
            }
        });
        /**
         * 鼠标右击菜单事件，选项
         */
        var contextMenu = Ext.create('Ext.menu.Menu', {
            items: [
                addAction,
                addSamAction,
                deleteAction
            ]
        });
        /**
         * 初始化参数
         */
        Ext.apply(this, {
        	store:_store,
        	 columnLines:true,
        	 rowLines:false, 
      		flex:1,
    		minWidth:900,
    		maxWidth:1300,
            columns: [{
                xtype: 'treecolumn',
                text: '菜单目录',
                flex: 1,
                sortable: true,
                dataIndex: 'text'
            },{
                text: '目录索引',
                flex: 1,
                dataIndex: 'rrid',
                hidden:true,
                sortable: true
            },{
        	   text: '父类索引',
               flex: 1,
               dataIndex: 'parent_id',
               hidden:true,
               sortable: true
           },{
                text: '链接地址',
                flex: 2,
                dataIndex: 'description',
                sortable: true
            }, {
                text: '编辑',
                width: 55,
                menuDisabled: true,
                xtype: 'actioncolumn',
                tooltip: 'Edit task',
                align: 'center',
                iconCls:'common_edit',
                handler: function(grid, rowIndex, colIndex, actionItem, event, record, row){
                	
                	if(record&&record.data){
                		var rdata=record.data;
                		right_param.text=rdata.text;
                		right_param.rrid=rdata.rrid;
                		right_param.description=rdata.description;
                		right_param.leve=rdata.leve;
                		right_param.parent_id=rdata.parent_id;
                		
                		Ext.Ajax.request({
    	    			    url: rootUrl+'/menuController/getRightRoles',
    	    			    async:false,
    	    			    params: {
    	    			        rrid:rdata.rrid
    	    			    },
    	    			    success: function(response){
    	    			        var text = response.responseText;
    	    			        var respText = Ext.JSON.decode(text);
    	    			        right_param.role=respText.data;
    	    			        for(var n in rightNames){
	    	    			        var boolean=false;
    	    			        	for(var i=0;i<right_param.role.length;i++){
	    	    			        	if(n==right_param.role[i]){
	    	    			        		boolean=true;
	    	    			        		break;
	    	    			        	}
	    	    			        }
    	    			        	rightNames[n]=boolean;
    	    			        }
    	    			        right_param.role=JSON.stringify(rightNames);
    	    			    }
    	    			});
                		
                	   }
                	addOrUpdateUrL=rootUrl+'/menuController/updataOrAddMenu',
                        win.show(this, function() {
                        	Ext.getCmp("_text").setValue(right_param.text);
                        	Ext.getCmp("_rrid").setValue(right_param.rrid);
                        	Ext.getCmp("_parent_id").setValue(right_param.parent_id);
                        	Ext.getCmp("_description").setValue(right_param.description);
                        	Ext.getCmp("_leve").setValue(right_param.leve);
                        	Ext.getCmp("_role").setValue(right_param.role);
                        	for(var n in rightNames){
                        		Ext.getCmp('model_'+n).setValue(rightNames[n]);
                        	}
                        	 
                        });
                },
                isDisabled: function(view, rowIdx, colIdx, item, record) {
                    return false;
                }
            }],
            viewConfig: {
                stripeRows: true,
                listeners: {
                    itemcontextmenu: function(view, rec, node, index, e) {
                        e.stopEvent();
                        contextMenu.showAt(e.getXY());
                        return false;
                    }
                }
            }
        });
        
        this.getSelectionModel().on({
            selectionchange: function(sm, selections) {
                if (selections.length) {
                    addAction.enable();
                    deleteAction.enable();
                    addSamAction.enable();
                } else {
                    addAction.disable();
                    deleteAction.disable();
                    addSamAction.disable();
                }
            }
        });
        
        this.callParent();
    }
});



/**
 * 主函数，命名空间
 */
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
//    		margin: '0 0 5 0',
//    		 layout: 'fit',
//            defaultType: 'container',
//            items: [{
//                columnWidth: 0.49,
//                padding: '0 5 0 0',
//                style:{
//                	width:'100%',
//                	height:'100%'
//                },
//                layout:'fit',
//                items:_tree
//            }]
    	});
    }
});

