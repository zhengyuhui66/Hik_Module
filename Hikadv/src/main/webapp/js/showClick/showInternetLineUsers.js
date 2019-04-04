
//------------------------------车辆信息查询条件组件----------------------------------------------------------
//var busGridReload = function() {
//	var stime = $("#bustime").val();
//	var etime = $("#buetime").val();
//	var name = Ext.getCmp('busPanelTextId').getValue();
//	busStore.getProxy().extraParams = {
//		stime : stime,
//		etime : etime,
//		name : name
//	};
//	busStore.reload();
//}
///**
// * 车辆开始时间组件
// */
//var bussTimePanel = Ext
//		.create(
//				'Ext.Panel',
//				{
//					baseCls : 'x-plain',
//					xtype : 'panel',
//					frame : false,
//					border : false,
//					html : '开始时间:<input class="Wdate" style="height:24px;width:150px;" id="bustime" type="text" onClick="WdatePicker({dateFmt:\'yyyy-MM-dd HH:mm:ss\',maxDate:\'#F{$dp.$D(\\\'buetime\\\')}\'})"/>'
//				});
///**
// * 车辆结束时间组件
// */
//var buseTimePanel = Ext
//		.create(
//				'Ext.Panel',
//				{
//					baseCls : 'x-plain',
//					xtype : 'panel',
//					frame : false,
//					border : false,
//					html : '结束时间:<input class="Wdate" style="height:24px;width:150px;" id="buetime" type="text" onClick="WdatePicker({dateFmt:\'yyyy-MM-dd HH:mm:ss\',minDate:\'#F{$dp.$D(\\\'bustime\\\')}\'})"/>'
//				});
///**
// * 车辆新增按钮
// */
//var busbutton = Ext.create('Ext.Action', {
//	iconCls:'common_search',
//	text : '查询',
//	handler : function(widget, event) {
//		busGridReload();
//	}
//});

// ---------------------------------路线信息查询条件组件----------------------------------------------------------
var routeReload = function() {
	
	var stime = $("#routestime").val();
	var etime = $("#routeetime").val();
	var name = Ext.getCmp('routePanelTextId').getValue();
	suftotal(name,stime,etime);
	routeStore.getProxy().extraParams = {
		stime : stime,
		etime : etime,
		name : name
	};
	routeStore.loadPage(1);
//	routeStore.reload();
}

var suftotal=function(name,stime,etime){
	Ext.Ajax.request({
	    url: rootUrl+'/showClickController/queryRouteSufCountTotal',
	    async: false,
	    params:{
	    	name:name,
	    	stime:stime,
	    	etime:etime
	    },
	    success: function(response){
	    	var respTexts = Ext.JSON.decode(response.responseText);
	    	if(respTexts&&respTexts.data&&respTexts.data.length>0){
	    		routePanel.setTitle('路线上网人数统计列表  当前查询总人数为'+respTexts.data[0].total+'人次');
	    	}
	    }
	});
}
/**
 * 路线开始时间组件
 */
var routesTimePanel = Ext
		.create('Ext.Panel',
				{
					baseCls : 'x-plain',
					xtype : 'panel',
					frame : false,
					border : false,
					html : '开始时间:<input class="Wdate" style="height:24px;width:150px;" id="routestime" type="text" onClick="WdatePicker({dateFmt:\'yyyy-MM-dd HH:mm:ss\',maxDate:\'#F{$dp.$D(\\\'routeetime\\\')}\'})"/>'
				});
/**
 * 路线结束时间组件
 */
var routeeTimePanel = Ext
		.create(
				'Ext.Panel',
				{
					baseCls : 'x-plain',
					xtype : 'panel',
					frame : false,
					border : false,
					html : '结束时间:<input class="Wdate" style="height:24px;width:150px;" id="routeetime" type="text" onClick="WdatePicker({dateFmt:\'yyyy-MM-dd HH:mm:ss\',minDate:\'#F{$dp.$D(\\\'routestime\\\')}\'})"/>'
				});
/**
 * 路线新增按钮
 */
var routebutton = Ext.create('Ext.Action', {
	iconCls:'common_search',
	text : '查询',
	handler : function(widget, event) {
		routeReload();
	}
});
/**
 * 菜单模型
 */
Ext.define('Ext.menu.model', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'name',
		type : 'string'
	}, {
		name : 'count',
		type : 'string'
	} ]
});
///**
// * 车辆为单位的加载的STORE
// */
//var busStore = Ext.create('Ext.data.Store', {
//	storeId : 'simpsonsStore',
//	model : Ext.menu.model,
//	pageSize : 10,
//	proxy : {
//		type : 'ajax',
//		url : rootUrl + '/showClickController/queryBusSufCount',
//		reader : {
//			type : 'json',
//			root : 'elementList',
//			totalProperty : 'total'
//		}
//	}
//});
/**
 * 路线为单位的加载的STORE
 */
var routeStore = Ext.create('Ext.data.Store', {
	storeId : 'simpsonsStore',
	model : Ext.menu.model,
	pageSize : defaultPageSize,
	proxy : {
		type : 'ajax',
		url : rootUrl + '/showClickController/queryRouteSufCount',
		reader : {
			type : 'json',
			root : 'elementList',
			totalProperty : 'total'
		}
	}
});
///**
// * 车辆信息GRID
// */
//var busPanel = Ext.create('Ext.grid.Panel', {
//	store : busStore,
//	flex : 1,
//	title : '车辆上网人数统计列表',
//	id : "busPanel",
//	padding : '5 5 5 5',
//	selModel : Ext.create('Ext.selection.CheckboxModel', {
//		mode : "SIMPLE"
//	}),
//	columns : [ Ext.create('Ext.grid.RowNumberer',{width:30}), {
//		text : "车辆名称",
//		flex : 1,
//		sortable : true,
//		dataIndex : 'name'
//	}, {
//		text : "上网人数",
//		flex : 1,
//		sortable : true,
//		dataIndex : 'count'
//	} ],
//	bbar : {
//		xtype : 'pagingtoolbar',
//		pageSize : 10,
//		store : busStore,
//		displayInfo : true,
//        nextText:'下一页',
//        prevText:'上一页',
//        firstText:'第一页',
//        lastText:'最后一页',
//        refreshText:'刷新',
//		displayMsg : '显示{0}-{1}条，共{2}条',
//		plugins : Ext.create('Ext.ux.ProgressBarPager', {})
//	},
//	tbar : [ bussTimePanel, buseTimePanel, {
//		xtype : 'textfield',
//		emptyText : '请输入需要搜索的信息',
//		width : 300,
//		fieldLabel : '车辆名称',
//		labelWidth : 60,
//		name : 'busPanelText',
//		id : 'busPanelTextId'
//	}, '->', busbutton ]
//});
/**
 * 车辆信息GRID
 */
var routePanel = Ext.create('Ext.grid.Panel', {
	store : routeStore,
	flex : 1,
	title : '路线上网人数统计列表',
	id : "routePanel",
	minWidth:900,
	maxWidth:1300,
	padding : '5 5 5 5',
	selModel : Ext.create('Ext.selection.CheckboxModel', {
		mode : "SIMPLE"
	}),
	columns : [ Ext.create('Ext.grid.RowNumberer',{width:30}), {
		text : "线路名称",
		flex : 1,
		align:'center',
		sortable : true,
		dataIndex : 'name'
	}, {
		text : "上网人数",
		flex : 1,
		align:'center',
		sortable : true,
		dataIndex : 'count'
	} ],
	bbar : {
		xtype : 'pagingtoolbar',
		pageSize : defaultPageSize,
		store : routeStore,
        nextText:'下一页',
        prevText:'上一页',
        firstText:'第一页',
        lastText:'最后一页',
        refreshText:'刷新',
		displayInfo : true,
		displayMsg : '显示{0}-{1}条，共{2}条',
		plugins : Ext.create('Ext.ux.ProgressBarPager', {})
	},
	tbar : [ routesTimePanel, routeeTimePanel, {
		xtype : 'textfield',
		emptyText : '请输入需要搜索的信息',
		width : 300,
		fieldLabel : '线路名称',
		labelWidth : 60,
		name : 'routePanelText',
		id : 'routePanelTextId'
	}, '->', routebutton ]
});
/**
 * 命名空间，主函数
 */
Ext.application({
	name : 'showInternetUsers',
	launch : function() {
		Ext.create('Ext.container.Viewport', {
			layout:'fit',

			items:[{
				xtype:'panel',
				overflowX:'auto',
				layout:{
					type:'hbox',
					align:'stretch',
					pack:'center'
				},
				items:[routePanel]
			}],
//			layout : {
//				type : 'hbox',
//				align : 'stretch'
//			},
//			overflowX : 'auto',
//			overflowY : 'auto',
//			items : [routePanel],// busPanel, 
			listeners : {
				afterlayout : function() {

					var stime = new Date();
					stime.setDate(stime.getDate() - 10);
					var str = stime.format("yyyy-MM-dd hh:mm:ss");
					var etime = new Date();
					etime.setDate(etime.getDate() - 1);
					var etr = etime.format("yyyy-MM-dd hh:mm:ss");

//					$("#bustime").val(str);
//					$("#buetime").val(etr);
					$("#routestime").val(str);
					$("#routeetime").val(etr);
//					busGridReload();
					routeReload();
				}
			}
		});
	}
});