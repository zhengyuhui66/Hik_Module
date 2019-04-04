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
var bFsTimePanel = Ext.create('Ext.Panel', {
    baseCls:'x-plain',
    xtype: 'panel',
    frame:false,
    border:false,
    html:'开始时间:<input class="Wdate" style="height:24px;width:100px;" id="pfstime" type="text" onClick="WdatePicker({dateFmt:\'yyyy-MM-dd\',maxDate:\'#F{$dp.$D(\\\'pfetime\\\')}\'})"/>'
});

var bFeTimePanel = Ext.create('Ext.Panel', {
    baseCls:'x-plain',
    xtype: 'panel',
    frame:false,
    border:false,
    html:'结束时间:<input class="Wdate" style="height:24px;width:100px;" id="pfetime" type="text" onClick="WdatePicker({dateFmt:\'yyyy-MM-dd\',minDate:\'#F{$dp.$D(\\\'pfstime\\\')}\'})"/>'
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
//---------------------浏览量车辆统计量--------------------------------------------


var acsubStore=Ext.create('Ext.data.Store',{
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

var acCombobox=Ext.create('Ext.form.ComboBox', {
    fieldLabel: '选择广告',
    labelWidth:70,
    width:150,
    id:'acCombobox',
    store: acStore,
    displayField: 'name',
    valueField: 'advertid',
    listeners:{
	  	select:function(combo, records,eOpts){
	  		if(records){
	  			var data =records[0];
	  			acsubStore.getProxy().extraParams = {advertId:data.data.id};
	  			acsubStore.reload();
	  			Ext.getCmp('acSubCombobox').setValue('0');
	  		}
	  	}
	  }
});  

var acSubCombobox=Ext.create('Ext.form.ComboBox', {
    fieldLabel: '选择物料',
    store: acsubStore,
    id:'acSubCombobox',
    labelWidth:70,
    width:200,
    displayField: 'name',
    valueField: 'id'
});

var bSsubStore=Ext.create('Ext.data.Store',{
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

var bSCombobox=Ext.create('Ext.form.ComboBox', {
    fieldLabel: '路线',
    labelWidth:30,
    width:150,
    id:'bSCombobox',
    store: bcStore,
    displayField: 'name',
    valueField: 'id',
    listeners:{
	  	select:function(combo, records,eOpts){
	  		if(records){
	  			var data =records[0];
	  			bSsubStore.getProxy().extraParams = {roldId:data.data.id};
	  			bSsubStore.reload();
	  			Ext.getCmp('bSSubCombobox').setValue('0');
	  		}
	  	}
	  }
});
var bSSubCombobox=Ext.create('Ext.form.ComboBox', {
    fieldLabel: '选择车辆',
    id:'bSSubCombobox',
    store: bSsubStore,
    labelWidth:70,
    width:200,
    queryMode: 'local',
    displayField: 'name',
    valueField: 'id'
});
/**
 * 新增按钮
 */
var sbutton = Ext.create('Ext.Action', {
	iconCls:'common_search',
    text: '查询',
    handler: function(widget, event) {
    	getEcharts();
    }
});
var getEcharts=function(){
	var stime=$("#pfstime").val();
//	 var etime=$("#pfetime").val(); 
//	 
//	 
//	 var stime=$("#psstime").val(); 
	 var str=$("#pfetime").val();
	 str = str.replace(/-/g,"/");
	 var _etime = new Date(str);
	 _etime.setDate(_etime.getDate()+1);
	 var etime=_etime.format('yyyy-MM-dd');
	 
	 var roldId=Ext.getCmp('bSCombobox').getValue();  
	 var busId=Ext.getCmp('bSSubCombobox').getValue();  
	 var advertid=Ext.getCmp('acCombobox').getValue();  
	 var materid=Ext.getCmp('acSubCombobox').getValue();  
	queryClick(stime,etime,roldId,busId,advertid,materid);
}
/**
 * 浏览量车辆统计量
 */
var Clickpanel = Ext.create('Ext.panel.Panel', {
        id:'show-bus-panel',
        flex:1,
        maxHeight:500,
        height:500,
        minWidth:1150,
        maxWidth:1300,
    	padding:'5 5 5 5',
        html:'<div id="clickId" style="height:400px;background-color:write;">暂无数据</div>',
        tbar:{style: 'background-color:#177ecb;',items:[bFsTimePanel,bFeTimePanel,bSCombobox,bSSubCombobox,acCombobox,acSubCombobox,'->',sbutton]}
    });

var panel = Ext.create('Ext.Panel', {
        id:'main-panel',
		xtype:'panel',
		title:'',
		border:false,
		style:'margin:50px;',
		layout: {
        	type:'table',
        	columns:1,
        	align:'center'
        },
      items:[Clickpanel]//,Showpanel]
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

//var showeCharts = null;
var clickeCharts = null;

var queryClick=function(stime,etime,roldId,busId,advertid,materid){
	
	Ext.Ajax.request({
	    url: rootUrl+'/showClickController/queryClickInfo',
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
	    	clickeCharts.setOption(option(xAxisData,series,'点击次数','广告投放数据分析','广告点击次数统计'));
	    }
	});
	
}
flag=true;


/**
 * 命名空间，主函数
 */
Ext.application({
    name: 'showClick',
    launch: function() {
    	Ext.create('Ext.container.Viewport', {
    		layout:'fit',
			items:[{
				xtype:'panel',
				layout:{
					type:'hbox',
					align:'stretch',
					pack:'center'
				},
				overflowX:'auto',
				items:Clickpanel
//				items:busPanel
			}],
            listeners:{
            	afterlayout:function(){
            		if(flag){
            			var stime = new Date();
            			var etime = new Date();
            			stime.setDate(stime.getDate()-10);            		
            			etime.setDate(etime.getDate());
            			var str=stime.format("yyyy-MM-dd");
            			var etr= etime.format("yyyy-MM-dd"); 
            			$("#pfstime").val(str);
            			$("#pfetime").val(etr);
            			flag=false;
            		}
	            		clickeCharts = echarts.init(document.getElementById('clickId'));
	            		getEcharts();
		    	}
            }
    	});
    }
});