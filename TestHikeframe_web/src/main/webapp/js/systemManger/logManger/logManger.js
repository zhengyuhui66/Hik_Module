        
//新增或更新地址
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
    	 //菜单模型
    	Ext.define('Ext.menu.model', {
    	    extend: 'Ext.data.Model',
    	    fields: [{
    	        name: 'username',
    	        type: 'string'
    	    },{
    	        name: 'operatetime',
    	        type: 'string'
    	    }, {
    	        name: 'content',
    	        type: 'string'
    	    }, {
    	        name: 'curd',
    	        type: 'string'
    	    },{
    	    	name:'aopMethod',
    	    	type:'string'
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
    		    	url:rootUrl+'/logController/getLogInfo',
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
                text: '用户ID',
                flex: 1,
                sortable: true,
                dataIndex: 'username'
            },{
                text: '操作时间',
                flex: 1,
                dataIndex: 'operatetime',
                sortable: true
            },{
        	   text: '内容',
               flex: 1,
               dataIndex: 'content'
           },{
                text: '操作类型',
                flex: 1,
                dataIndex: 'curd'
            },{
            	text:'操作参数',
            	flex:1,
            	dataIndex:'aopMethod'
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
            tbar: ['->',
                   {
		        		xtype:'textfield',
		        		emptyText: '请输入需要搜索的信息',
		        		width:200,
		                name: 'condition',
		                id: '_condition'
                   },{
                	   xtype:'button',
                	   text: '搜索',
                	   handler: function() {
                		   var condition  = Ext.getCmp('_condition').getValue();
                		   trStore.getProxy().extraParams = {condition:condition};
                		   trStore.loadPage(1);
                	    }
                   }]
        });
        
        this.callParent();
    }
});




Ext.application({
    name: 'logManger',
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

