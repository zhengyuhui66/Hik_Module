
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
	/**
	 * 广告模型
	 */
    	Ext.define('SCHEDULELOGMODEL', {
            extend: 'Ext.data.Model',
            fields: [{
    	    	        name: 'busname',
    	    	        type: 'string'
    	    	    },{
    	    	    	name: 'apmac',
    	    	        type: 'string'
    	    	    },{
    	    	        name: 'authcycname',
    	    	        type: 'string'
    	    	    },{
    	    	        name: 'datetime',
    	    	        type: 'string'
    	    	    },{
    	    	        name: 'timeslice',
    	    	        type: 'string'
    	    	    },{
    	    	        name: 'proname',
    	    	        type: 'string'
    	    	    },{
    	    	    	name: 'creattime',
    	    	    	type:'string'
    	    	    },{
    	    	    	name:'creator',
    	    	    	type:'string'
    	    	    },{
    	    	    	name:'state',
    	    	    	type:'string'
    	    	    }]
        });
    	
//    var _store = Ext.create('Ext.data.Store', {
//		model : 'SCHEDULELOGMODEL',
//		proxy : {
//			type : 'ajax',
//			url : rootUrl + '/psl/queryPutLog',
//			reader : {
//				type : 'json',
//				root : 'data'
//			}
//		},
//		autoLoad:true
//	});
    
    var me=this;
   	 /**
   	  * 得到所有用户信息
   	  */
   	 var trStore=Ext.create('Ext.data.Store', {
   		    model: SCHEDULELOGMODEL,
   		    pageSize:defaultPageSize,
   		    proxy: {
   		    	type: 'ajax',
   		    	url:rootUrl+'/psl/queryPutLog',
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
            columns: [
            {//"id","busname","authcycname","","","proname","","",""
	        	text: 'ID',
	            flex: 1,
	            hidden:true,
	            align:'center',
	            dataIndex: 'id',
	            sortable: true
            },{
         	  text: '车辆名称',
              flex: 1,
              align:'center',
              dataIndex: 'busname',
              sortable: true
            },{
         	  text: '认证周期',
              flex: 1,
              align:'center',
              dataIndex: 'authcycname',
              sortable: true
            },{
        	 text: 'AP物理地址',
             flex: 1,
             align:'center',
             dataIndex: 'apmac',
             sortable: true
            },{
           	  text: '排期日期',
	          flex: 1,
	          align:'center',
	          dataIndex: 'datetime',
	          sortable: true
            },{
           	  text: '时间片段',
              flex: 1,
              align:'center',
              dataIndex: 'timeslice',
              sortable: true
            },{
         	  text: '产品',
              flex: 1,
              align:'center',
              dataIndex: 'proname',
              sortable: true  
            },{
           	  text: '排期人',
                flex: 1,
                align:'center',
                dataIndex: 'creator',
                sortable: true
            },{
           	  text: '创建时间',
                flex: 1,
                align:'center',
                dataIndex: 'creattime',
                sortable: true
            },{
         	  text: '排期状态',
              flex: 1,
              align:'center',
              dataIndex: 'state',
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
            },tbar: ['->',
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