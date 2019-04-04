/**
 * 模型模块模型
 */
Ext.define('comModel',{
    extend: 'Ext.data.Model',
    fields: [
        {name: 'id', type: 'string'},
        {name: 'name', type: 'string'}
    ]
}); 

/**
 * 广告模型模块模型
 */
Ext.define('advcomModel',{
    extend: 'Ext.data.Model',
    fields: [
        {name: 'advertid', type: 'string'},
        {name: 'name', type: 'string'}
    ]
}); 

var bSsTimePanel = Ext.create('Ext.Panel', {
    baseCls:'x-plain',
    xtype: 'panel',
    frame:false,
    border:false,
    html:'开始时间:<input class="Wdate" style="height:24px;width:100px;" id="psstime" type="text" onClick="WdatePicker({dateFmt:\'yyyy-MM-dd\',maxDate:\'#F{$dp.$D(\\\'psetime\\\')}\'})"/>'
});

var bSeTimePanel = Ext.create('Ext.Panel', {
    baseCls:'x-plain',
    xtype: 'panel',
    frame:false,
    border:false,
    html:'结束时间:<input class="Wdate" style="height:24px;width:100px;" id="psetime" type="text" onClick="WdatePicker({dateFmt:\'yyyy-MM-dd\',minDate:\'#F{$dp.$D(\\\'psstime\\\')}\'})"/>'
});

/**
 * 模型模块Store    
 */
var bcStore=Ext.create('Ext.data.Store',{
	model:'comModel',
    proxy: {
        type: 'ajax',
        url: rootUrl+'/showClickController/getAllRold',
        reader: {
            type: 'json',
            root: 'data'
        }
    },
    autoLoad:true
});
/**
 * 模型模块Store    
 */
var acStore=Ext.create('Ext.data.Store',{
	model:'advcomModel',
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
//===========================公共==================================================


var assubStore=Ext.create('Ext.data.Store',{
	model:'comModel',
    proxy: {
        type: 'ajax',
        url: rootUrl+'/showClickController/getMaterByAdvertId',
        reader: {
            type: 'json',
            root: 'data'
        }
    }
});


var bcsubStore=Ext.create('Ext.data.Store',{
	model:'comModel',
    proxy: {
        type: 'ajax',
        url: rootUrl+'/showClickController/getBusIdByRoldId',
        reader: {
            type: 'json',
            root: 'data'
        }
    }
});
var bcCombobox=Ext.create('Ext.form.ComboBox', {
    fieldLabel: '路线',
    id:'bcCombobox',
    labelWidth:30,
    width:150,
    store: bcStore,
    displayField: 'name',
    valueField: 'id',
    listeners:{
	  	select:function(combo, records,eOpts){
	  		if(records){
	  			var data =records[0];
	  			bcsubStore.getProxy().extraParams = {roldId:data.data.id};
	  			bcsubStore.reload();
	  			Ext.getCmp('bcSubCombobox').setValue('0');
	  		}
	  	}
	  }
});
var bcSubCombobox=Ext.create('Ext.form.ComboBox', {
    fieldLabel: '选择车辆',
    store: bcsubStore,
    id:'bcSubCombobox',
    labelWidth:70,
    width:200,
    displayField: 'name',
    valueField: 'id'
});
var asCombobox=Ext.create('Ext.form.ComboBox', {
    fieldLabel: '选择广告',
    labelWidth:70,
    width:150,
    id:'asCombobox',
    store: acStore,
    displayField: 'name',
    valueField: 'advertid',
    listeners:{
	  	select:function(combo, records,eOpts){
	  		if(records){
	  			var data =records[0];
	  			assubStore.getProxy().extraParams = {advertId:data.data.id};
	  			assubStore.reload();
	  			Ext.getCmp('asSubCombobox').setValue('0');
	  		}
	  	}
	  }
}); 
var asSubCombobox=Ext.create('Ext.form.ComboBox', {
    fieldLabel: '选择物料',
    store: assubStore,
    id:'asSubCombobox',
    labelWidth:70,
    width:200,
    queryMode: 'local',
    displayField: 'name',
    valueField: 'id'
});
/**
 * 新增按钮
 */
var asbutton = Ext.create('Ext.Action', {
//	icon:'../../images/icons/common_search.png',
	iconCls:'common_search',
    text: '查询',
    handler: function(widget, event) {
    	 var stime=$("#psstime").val(); 
    	 var str=$("#psetime").val();
    	 str = str.replace(/-/g,"/");
    	 var _etime = new Date(str);
    	 _etime.setDate(_etime.getDate()+1);
    	 var etime=_etime.format('yyyy-MM-dd');
    	 var roldId=Ext.getCmp('bcCombobox').getValue(); 
    	 var busId=Ext.getCmp('bcSubCombobox').getValue(); 
    	 var advertid=Ext.getCmp('asCombobox').getValue(); 
    	 var materid=Ext.getCmp('asSubCombobox').getValue(); 
    	queryShow(stime,etime,roldId,busId,advertid,materid);
    }
});

/**
 * 广告点击统计量
 */
var Showpanel = Ext.create('Ext.panel.Panel', {
    id:'show-advert-panel',
    flex:1,
    maxHeight:500,
    height:500,
    minWidth:1150,
    maxWidth:1300,
	padding:'5 5 5 5',
//    title:'浏览量广告统计示意图',
//    style:'margin-top:30px',
//    maxHeight:500,
//    minWidth:1150,
//    items:[{
//    	border:false,
    html:'<div id="showId" style="height:400px;background-color:write;">暂无数据</div>',      	
//    }],
    tbar:{style: 'background-color:#177ecb;',items:[bSsTimePanel,bSeTimePanel,bcCombobox,bcSubCombobox,asCombobox,asSubCombobox,'->',asbutton]}
});
//======================end=================================================================================
//var tbutton = Ext.create('Ext.Action', {
////	icon:'../../images/icons/common_search.png',
//	iconCls:'search',
//    text: '查询',
//    handler: function(widget, event) {
//    }
//});
var panel = Ext.create('Ext.Panel', {
        id:'main-panel',
		xtype:'panel',
		title:'',
		border:false,
		style:'margin-bottom:50px;',
		layout: {
        	type:'table',
        	columns:1,
        	align:'center'
        },
      items:[Showpanel] //Clickpanel,
    });

var option = function(xAxisData,series,tip,title,subtitle){
	return  {
			title: {
		        text: title,
		        subtext: subtitle
		    },
		    tooltip: {
		        trigger: 'axis'
		    },
		    toolbox: {
		        feature: {
		            dataView: {show: true, readOnly: false},
		            magicType: {show: true, type: ['line', 'bar']},
		            restore: {show: true},
		            saveAsImage: {show: true}
		        }
		    },
		    legend: {
		        data:[tip]
		    },
		    grid: {
		        left: '3%',
		        right: '4%',
		        bottom: '3%',
		        containLabel: true
		    },
		    xAxis: [
		        {
		            type: 'category',
		            data: xAxisData
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value'
		        }
		    ],
		    series: [
		        {
		            name:tip,
		            barWidth:30,
		            itemStyle: {
		                normal: {
		                    color:'rgb(21,127,204)'
		                },
		                emphasis: {
		                    color:'rgb(173,210,237)'
		                }
		            },
		            type:'bar',
		            data:series
		        }
		    ]
		};
}		

var showeCharts = null;
//var clickeCharts = null;
var queryShow=function(stime,etime,roldId,busId,advertid,materid){
	Ext.Ajax.request({
	    url: rootUrl+'/showClickController/queryShowInfo',
	    async: false,
	    params:{
	    	stime:stime,
	    	etime:etime,
	    	roldId:roldId,
	    	busId:busId,
	    	advertid:advertid,
	    	materid:materid
	    },
	    success: function(response){
	    	var respTexts = Ext.JSON.decode(response.responseText);
	    	var respText=respTexts.data;
	    	console.info(respText);
	    	var xAxisData=new Array();
	    	var series= new Array();
	    	for(var i=0;i<respText.length;i++){
	    		xAxisData.push(respText[i].time.substring(0,10));
	    		series.push(+(respText[i].count));
	    	}
	    	showeCharts.setOption(option(xAxisData,series,'展示次数','广告投放数据分析','展示次数统计'));
	    }
	});
}

/**
 * 命名空间，主函数
 */
Ext.application({
    name: 'showClick',
    launch: function() {
    	Ext.create('Ext.container.Viewport', {
            layout:{
            	type:'fit'
            },
			items:[{
				xtype:'panel',
				layout:{
					type:'hbox',
					align:'stretch',
					pack:'center'
				},
				overflowX:'auto',
				items:Showpanel
			}],
            listeners:{
            	afterlayout:function(){
            		
            		
					var stime = new Date();
					stime.setDate(stime.getDate()-10);
					var str=stime.format("yyyy-MM-dd");
					var etime = new Date();
					var etr= etime.format("yyyy-MM-dd");
            		$("#psstime").val(str);   
            		$("#psetime").val(etr);  
            		
					
            		showeCharts = echarts.init(document.getElementById('showId'));
//            		clickeCharts = echarts.init(document.getElementById('clickId'));
            		window.onresize = function () {
            			showeCharts.resize();
                    };
                    var _etime = new Date();					
                    _etime.setDate(_etime.getDate()+1);
                    var _etr= _etime.format("yyyy-MM-dd");
            		queryShow(str,_etr,null,null,null,null);
//            		queryClick(str,etr,null,null,null,null);
		    	}
            }
    	});
    }
});