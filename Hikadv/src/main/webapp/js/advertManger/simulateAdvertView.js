

var panel=Ext.define('panel', {
    extend: 'Ext.Container',
    xtype: 'basic-panels',
    requires: [
               'Ext.selection.CellModel',
               'Ext.grid.*',
               'Ext.data.*',
               'Ext.util.*',
               'Ext.form.*'
           ],
    layout: {
             type: 'hbox',
             padding:'5',
             align:'stretch'
    },
    tdAttrs: {style: 'padding: 10px;'},
    padding:'5 5 5 5',
    flex:1,
    minWidth:1000,
    maxWidth:1300,
    defaults: {
        xtype: 'panel',
        bodyPadding: 10
    },

    initComponent: function () {
    	
    	 this.cellEditing = new Ext.grid.plugin.CellEditing({
             clicksToEdit: 1
         });

    	/**
    	 * 广告模型
    	 */
    	Ext.define('advertUser',{
    	    extend: 'Ext.data.Model',
    	    fields: [
    	        {name: 'advertid', type: 'string'},
    	        {name: 'name', type: 'string'}
    	    ]
    	});
    	/**
    	 * 广告Store
    	 */
    	var AdvertStore=Ext.create('Ext.data.Store',{
    		model:'advertUser',
    	    proxy: {
    	        type: 'ajax',
    	        url: rootUrl+'/common/queryAllAdvert',
    	        reader: {
    	            type: 'json',
    	            root: 'data'
    	        }
    	    },
    	    autoLoad:true
    	});
    	
    	

    	/**
    	 * 模型模块模型
    	 */
    	Ext.define('subuUser',{
    	    extend: 'Ext.data.Model',
    	    fields: [
    	        {name: 'advposid', type: 'string'},
    	        {name: 'advposname', type: 'string'},
    	        {name: 'advertid', type: 'string'}
    	    ]
    	});
    	/**
    	 * 模型模块Store    
    	 */
    	var ModelModeStore=Ext.create('Ext.data.Store',{
    		model:'subuUser',
    	    proxy: {
    	        type: 'ajax',
    	        url: rootUrl+'/advertMmController/queryModelModeById',
    	        reader: {
    	            type: 'json',
    	            root: 'data'
    	        }
    	    }
    	});
    	/**
    	 * 模型模型
    	 */
    	Ext.define('uUser',{
    	    extend: 'Ext.data.Model',
    	    fields: [
    	        {name: 'id', type: 'string'},
    	        {name: 'name', type: 'string'}
    	    ]
    	});
    	/**
    	 * 模型信息Store
    	 */
    	var ModelStore=Ext.create('Ext.data.Store',{
    		model:'uUser',
    	    proxy: {
    	        type: 'ajax',
    	        url: rootUrl+'/common/queryAllModel',
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
    		model:'uUser',
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
    	/**
    	 * 广告位展示
    	 */
    	var viewModelmodelgrid= Ext.create('Ext.grid.Panel', {
    	    store: ModelModeStore,
    	    plugins: [this.cellEditing],
    	    columns: [
    	        {text: "广告位名称", flex: 1, sortable: false, dataIndex: 'advposname',align:'center'},
    	        {text: "广告位ID", flex: 1, sortable:false, dataIndex: 'advposid',hidden:true},
    	        {text: "广告", 
    	        	align:'center',
    	        	flex: 1,
    	        	sortable:false, 
    	        	dataIndex: 'advertid',
		        	 editor: new Ext.form.field.ComboBox({
	                    typeAhead: true,
	                    triggerAction: 'all',
	                    selectOnTab: true,
	                    emptyText:'请选择',
	    	            displayField:'name',
	    	            valueField  : 'name',
	                    store:AdvertStore,
	                    lazyRender: true,
	                    listClass: 'x-combo-list-small'
	                })}
    	        ],
    	    columnLines: true
    	});
    	
    	/**
    	 * 取消投放按钮
    	 */
    	var qbutton=Ext.create('Ext.Button', {
    	    text: '投放效果',
    	    id : 'cButton',
    	    iconCls:'common_buttonView',
    	    tag : 'Y',
    	    handler :function(_button){
    	    	var store = viewModelmodelgrid.getStore();
    	    	var count = store.getCount();
    	    	var tempObj = new Object();
    	    	for (var i = 0; i < count; i++) {
	    	    	var record = store.getAt(i);
	    	    	tempObj[record.data.advposid]=record.data.advertid;
    	    	}
    	    	var modelid=Ext.getCmp("pmodelId").getValue();
    	    	Ext.Ajax.request({
        		    url: rootUrl+'/getAdvertController/getViewAdvert',
        		    async: false,
        		    params:{
        		    	id:modelid,
        		    	params:JSON.stringify(tempObj)
        		    },
        		    success: function(response){
        		    	var respTexts = Ext.JSON.decode(response.responseText);
        		    	var respText=respTexts.data;
        		    	var url=rootUrl+"/";
        		    	if(respText[0]){
        		    		url+=respText[0].url+'?modelskin='+respText[0].modelskin;        		    		
        		    	}
        		    	if(respText[1]){
        		    		for(var n in respText[1]){
        		    			url+='&'+n+'="'+httpUrlResources+respText[1][n]+'"';
        		    		}
        		    	}
        		    	$("#viewAdvert").attr('src',url);
        		    }
        		});
    	    }
    	        
    	});


    	var pbbr=Ext.create('Ext.toolbar.Toolbar', {
    			border:false,
			    items: [{
					  xtype: 'combo',
					  store:cycStore,
					  displayField:'name',
					  valueField  : 'id',    //真实的字段
					  labelWidth:30,
					  width:150,
					  border:false,
					  fieldLabel: '类型',
					  selectOnTab: false,
					  name: 'cycName',
					  id:'cycid',
					  emptyText   : '请选择...',
					  onReplicate: function(){
					      this.getStore().clearFilter();
					  },listeners:{
					  	select:function(combo, records,eOpts){
					  		if(records){
					  			var data =records[0].getData();
					  			if(!data||!data.id){
					  				return;
					  			}
					  			ModelStore.getProxy().extraParams = {cycid:data.id};
					  			ModelStore.reload();
					  			Ext.getCmp('pmodelId').setValue('');
					  		}
					  	}
					  }
					},{
						  xtype: 'combo',
						  store:ModelStore,
						  displayField:'name',
						  valueField  : 'id',    //真实的字段
						  labelWidth:30,
						  width:150,
						  border:false,
						  fieldLabel: '模版',
						  selectOnTab: false,
						  name: 'pmodeName',
						  id:'pmodelId',
						  emptyText   : '请选择...',
						  onReplicate: function(){
						      this.getStore().clearFilter();
						  },listeners:{
						  	select:function(combo, records,eOpts){
						  		if(records){
						  			var data =records[0].getData();
						  			if(!data||!data.id){
						  				return;
						  			}
						  			ModelModeStore.getProxy().extraParams = {id:data.id};
						  			ModelModeStore.reload();
						  		}
						  	}
						  }
						}
				    ]
	    		});
		        this.items = [{
	        					xtype:'panel',
	        					width:450,
							    items:viewModelmodelgrid,
							    tbar:[pbbr,'->',qbutton]
							},
				            {
								layout:'fit',
								xtype:'panel',
								frame:false,
								overflowY:'auto',
								flex:1,
				                html: "<div style='width:100%;height:100%;text-align:center'><iframe id='viewAdvert' frameborder='no' border='0' style='width:375px;height:750px;' src=''><h1>投放预览区</h1></iframe></div>"
	                      }
		        ];
		
		        this.callParent();
		    }
		});
/**
 * 命名空间，主函数
 */
Ext.application({
    name: 'materialManger',
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
				items:panel
			}]
    	});
    }
});

