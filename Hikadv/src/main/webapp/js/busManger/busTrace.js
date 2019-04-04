
/**
 * 命名空间，主函数
 */

var mouseTool=null;
var map=null;
Ext.define('COMMONMODEL', {
    extend: 'Ext.data.Model',
    fields: ["id","name"]
});
var routeStore = Ext.create('Ext.data.Store', {
	model : 'COMMONMODEL',
	proxy : {
		type : 'ajax',
		url : rootUrl + '/common/queryRoadInfo',
		reader : {
			type : 'json',
			root : 'data'
		}
	},
	autoLoad:true
});

var busStore = Ext.create('Ext.data.Store', {
	model : 'COMMONMODEL',
	proxy : {
		type : 'ajax',
		url : rootUrl + '/common/queryBusInfo',
		reader : {
			type : 'json',
			root : 'data'
		}
	},
	autoLoad:true
});
var deviceStore = Ext.create('Ext.data.Store', {
	model : 'COMMONMODEL',
	proxy : {
		type : 'ajax',
		url : rootUrl + '/common/queryDeviceInfo',
		reader : {
			type : 'json',
			root : 'data'
		}
	},
	autoLoad:true
});

var changeendtime=function(){
	var stime=$("#starttime").val();
	if(!stime){
		return;
	}
	var etime=$("#endtime").val();
	if(!etime){
		$("#endtime").val(stime);
		return;
	}
	var stimes=stime.split(' ');
	var etimes=etime.split(' ');
	$("#endtime").val(stimes[0]+' '+etimes[1]);
}

var changestarttime=function(){
	var etime=$("#endtime").val();
	if(!etime){
		return;
	}
	var stime=$("#starttime").val();
	if(!stime){
		$("#starttime").val(etime);
		return;
	}
	var stimes=stime.split(' ');
	var etimes=etime.split(' ');
	$("#starttime").val(etimes[0]+' '+stimes[1]);
}
var busName = Ext.create('Ext.panel.Panel',{
	border:false,
	width:'100%',
	height:'100%',
	html:'<div id="kid"  style="width:100%;height:100%;text-align:center" >测试解密那</div>',
	tbar:{
		xtype:"container",
		border:false,
		items:[{
		     //tbar第一行工具栏
		     xtype:"toolbar",
		     items : [{
		    	 store: routeStore,
	           fieldLabel: '线路',
	           xtype:'combo',
	           margin : '10 10 0 0',
	           labelWidth:63,
	           labelAlign:'right',
	           width:130,
	           displayField: 'name',
	   		   valueField: 'id',
	           name: 'routeid',
	           minChars: 1,
	           id:'_routeid',
	           listeners:{
	        	   change:function(me, newValue, oldValue, eOpts){
	        		   var tempValue=me.rawValue=='全部'?'':me.rawValue;
	        		   routeStore.getProxy().extraParams = {linename:tempValue};
	        		   routeStore.load();
	        		   Ext.getCmp('_busid').setValue('');
	        	   },
	        	   select:function(combo, records, eOpts){
	        		   if(records.length>0){
	            		   var newValue=records[0].data.id;
	            		   busStore.getProxy().extraParams = {lineId:newValue};
	            		   busStore.load();
	        		   }
	        	   },
			      afterRender : function(combo) {
			          combo.setValue('');//同时下拉框会将与name为firstValue值对应的 text显示
			       }
	           }
	       },{
	   		   store: busStore,
	   		   displayField: 'name',
	   		   valueField: 'id',
	   		   minChars: 1,
	   		   xtype:'combo',
	   		   width:130,
	   		   labelWidth:30,
	   		   margin : '10 10 0 20',
	           fieldLabel: '车辆',
	           name: 'busid',
	           id:'_busid',
	           listeners:{
	        	   change:function(me, newValue, oldValue, eOpts){
	        		   var tempValue=me.value;
	        		   var lineId = Ext.getCmp('_routeid').getValue();
	        		   busStore.getProxy().extraParams = {lineId:lineId,busName:newValue};
	        		   Ext.getCmp('_deviceid').setValue('');
	        	   },
	        	   select:function(combo, records, eOpts){
	        		   if(records.length>0){
	            		   var newValue=records[0].data.id;
	            		   deviceStore.getProxy().extraParams = {busid:newValue};
	            		   deviceStore.load();
	        		   }
	        	   },
	        	   afterRender : function(combo) {
			          combo.setValue('');//同时下拉框会将与name为firstValue值对应的 text显示
			       }
	           }
	       },{
	  		   store: deviceStore,
	   		   displayField: 'name',
	   		   valueField: 'name',
	   		   minChars: 1,
	   		   xtype:'combo',
	   		   labelWidth:30,
	   		   width:250,
	   		   margin : '10 10 0 20',
	           fieldLabel: '设备',
	           name: 'deviceid',
	           id:'_deviceid',
	           listeners:{
	        	   change:function(me, newValue, oldValue, eOpts){
	        	   },
	        	   afterRender : function(combo) {
			          combo.setValue('');//同时下拉框会将与name为firstValue值对应的 text显示
			       }
	           }
	       }]
		},{
		     //tbar第二行工具栏
		     xtype:"toolbar",
		     items : [{
	        	   xtype:'panel',
	        	   border:false,
	        	   html:'开始时间:&nbsp;&nbsp;&nbsp;<input type="text" style="height:24px" class="Wdate" id="starttime"  onfocus="WdatePicker({skin:\'whyGreen\',dateFmt:\'yyyy-MM-dd HH:mm:ss\',maxDate:\'%y-%M-%d %h:%m:{%s-1}\',onpicked:changeendtime()})"/>'
	           },{
	        	   xtype:'panel',
	        	   border:false,
	        	   html:'节束时间:&nbsp;&nbsp;&nbsp;<input type="text" style="height:24px" class="Wdate" id="endtime"  onfocus="WdatePicker({skin:\'whyGreen\',dateFmt:\'yyyy-MM-dd HH:mm:ss\',maxDate:\'%y-%M-%d %h:%m:%s\',onpicked:changestarttime()})"/>'
//	        	   html:'节束时间:&nbsp;&nbsp;&nbsp;<input type="text" style="height:24px" class="Wdate" id="endtime"  onfocus="WdatePicker({skin:\'whyGreen\',dateFmt:\'yyyy-MM-dd HH:mm:ss\',maxDate:\'#F{$dp.$D(\\\'starttime\\\')}\',minDate:\'#F{$dp.$D(\\\'starttime\\\')}\'})"/>'
	           },{
	        	   xtype: 'button', 
	        	   text: '搜索',
	        	   iconCls:'common_search',
	        	   handler:function(){
	        		   var equipmentid=Ext.getCmp('_deviceid').getValue();
	        		   var starttime =$("#starttime").val();
	        		   var endtime = $("#endtime").val();
	        		   Ext.Ajax.request({
	                	    url:rootUrl+'/bustracecontroller/querybustrace',//addOrUpdateUrL,
	                	    async: false,
	                	    params: {
	                	    	equipmentid:equipmentid,
	                	    	starttime:starttime,
	                	    	endtime:endtime
	        			    },
	        			    success: function(response){
	        			    	var respText = Ext.JSON.decode(response.responseText);
	                 	    	if(respText.success=="true"){
	                 	    		var lineArr = new Array();
	                 	    		for(var i=0;i<respText.data.length;i++){
	                 	    			var temp=respText.data[i];
	                 	    			lineArr.push([parseFloat(temp.longitude),parseFloat(temp.latitude)]);
	                 	    		}
				                    var polyline = new AMap.Polyline({
				                       path: lineArr,          //设置线覆盖物路径
				                       strokeColor: "#0000FF", //线颜色
				                       strokeOpacity: 1,       //线透明度
				                       strokeWeight: 5,        //线宽
				                       strokeStyle: "solid",   //线样式
				                       strokeDasharray: [10, 5] //补充线样式
				                    });
				                    polyline.setMap(map);
	                 	    	}else{
//	                 	    		Ext.Msg.alert('温馨提示',"无线路");
	                 	    		Ext.Msg.show({
	                    				title : '提示',
	                    				msg :"无线路",
	                    				width : 250,
	                    				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
	                    				buttonText: { ok: '确定' }
	                    			});
	                 	    	} 
	                	    }
	                	});
	        	   }
	           }]
		}]
	}
//	tbar: [{
//		   store: routeStore,
//           fieldLabel: '线路',
//           xtype:'combo',
//           margin : '10 10 0 20',
//           labelWidth:30,
//           width:130,
//           displayField: 'name',
//   		   valueField: 'id',
//           name: 'routeid',
//           minChars: 1,
//           id:'_routeid',
//           listeners:{
//        	   change:function(me, newValue, oldValue, eOpts){
//        		   var tempValue=me.rawValue=='全部'?'':me.rawValue;
//        		   routeStore.getProxy().extraParams = {linename:tempValue};
//        		   routeStore.load();
//        		   Ext.getCmp('_busid').setValue('');
//        	   },
//        	   select:function(combo, records, eOpts){
//        		   if(records.length>0){
//            		   var newValue=records[0].data.id;
//            		   busStore.getProxy().extraParams = {lineId:newValue};
//            		   busStore.load();
//        		   }
//        	   },
//		      afterRender : function(combo) {
//		          combo.setValue('');//同时下拉框会将与name为firstValue值对应的 text显示
//		       }
//           }
//       },{
//   		   store: busStore,
//   		   displayField: 'name',
//   		   valueField: 'id',
//   		   minChars: 1,
//   		   xtype:'combo',
//   		 width:130,
//   		   labelWidth:30,
//   		   margin : '10 10 0 20',
//           fieldLabel: '车辆',
//           name: 'busid',
//           id:'_busid',
//           listeners:{
//        	   change:function(me, newValue, oldValue, eOpts){
//        		   var tempValue=me.value;
//        		   var lineId = Ext.getCmp('_routeid').getValue();
//        		   busStore.getProxy().extraParams = {lineId:lineId,busName:newValue};
//        		   Ext.getCmp('_deviceid').setValue('');
//        	   },
//        	   select:function(combo, records, eOpts){
//        		   if(records.length>0){
//            		   var newValue=records[0].data.id;
//            		   deviceStore.getProxy().extraParams = {busid:newValue};
//            		   deviceStore.load();
//        		   }
//        	   },
//        	   afterRender : function(combo) {
//		          combo.setValue('');//同时下拉框会将与name为firstValue值对应的 text显示
//		       }
//           }
//       },{
//  		   store: deviceStore,
//   		   displayField: 'name',
//   		   valueField: 'name',
//   		   minChars: 1,
//   		   xtype:'combo',
//   		   labelWidth:30,
//   		   width:250,
//   		   margin : '10 10 0 20',
//           fieldLabel: '设备',
//           name: 'deviceid',
//           id:'_deviceid',
//           listeners:{
//        	   change:function(me, newValue, oldValue, eOpts){
//        	   },
//        	   afterRender : function(combo) {
//		          combo.setValue('');//同时下拉框会将与name为firstValue值对应的 text显示
//		       }
//           }
////       },{
////		       xtype: 'textfield',
////	           fieldLabel: '设备轨迹',
////	           emptyText : '14306073Xxxxxxxxxxxxxx',
////	           labelWidth:60,
////	           allowBlank: false,
////	           name: 'busName',
////	           id:'_busName'
//           },{
//        	   xtype:'panel',
//        	   border:false,
//        	   html:'开始时间:&nbsp;&nbsp;&nbsp;<input type="text" style="height:24px" class="Wdate" id="starttime"  onfocus="WdatePicker({skin:\'whyGreen\',dateFmt:\'yyyy-MM-dd HH:mm:ss\',maxDate:\'%y-%M-%d %h:%m:{%s-1}\',onpicked:changeendtime()})"/>'
//           },{
//        	   xtype:'panel',
//        	   border:false,
//        	   html:'节束时间:&nbsp;&nbsp;&nbsp;<input type="text" style="height:24px" class="Wdate" id="endtime"  onfocus="WdatePicker({skin:\'whyGreen\',dateFmt:\'yyyy-MM-dd HH:mm:ss\',maxDate:\'%y-%M-%d %h:%m:%s\',onpicked:changestarttime()})"/>'
////        	   html:'节束时间:&nbsp;&nbsp;&nbsp;<input type="text" style="height:24px" class="Wdate" id="endtime"  onfocus="WdatePicker({skin:\'whyGreen\',dateFmt:\'yyyy-MM-dd HH:mm:ss\',maxDate:\'#F{$dp.$D(\\\'starttime\\\')}\',minDate:\'#F{$dp.$D(\\\'starttime\\\')}\'})"/>'
//           },{
//        	   xtype: 'button', 
//        	   text: '搜索',
//        	   iconCls:'common_search',
//        	   handler:function(){
//        		   var equipmentid=Ext.getCmp('_deviceid').getValue();
//        		   var starttime =$("#starttime").val();
//        		   var endtime = $("#endtime").val();
//        		   Ext.Ajax.request({
//                	    url:rootUrl+'/bustracecontroller/querybustrace',//addOrUpdateUrL,
//                	    async: false,
//                	    params: {
//                	    	equipmentid:equipmentid,
//                	    	starttime:starttime,
//                	    	endtime:endtime
//        			    },
//        			    success: function(response){
//        			    	var respText = Ext.JSON.decode(response.responseText);
//                 	    	if(respText.success=="true"){
//                 	    		var lineArr = new Array();
//                 	    		for(var i=0;i<respText.data.length;i++){
//                 	    			var temp=respText.data[i];
//                 	    			lineArr.push([parseFloat(temp.longitude),parseFloat(temp.latitude)]);
//                 	    		}
//			                    var polyline = new AMap.Polyline({
//			                       path: lineArr,          //设置线覆盖物路径
//			                       strokeColor: "#0000FF", //线颜色
//			                       strokeOpacity: 1,       //线透明度
//			                       strokeWeight: 5,        //线宽
//			                       strokeStyle: "solid",   //线样式
//			                       strokeDasharray: [10, 5] //补充线样式
//			                    });
//			                    polyline.setMap(map);
//                 	    	}else{
////                 	    		Ext.Msg.alert('温馨提示',"无线路");
//                 	    		Ext.Msg.show({
//                    				title : '提示',
//                    				msg :"无线路",
//                    				width : 250,
//                    				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
//                    				buttonText: { ok: '确定' }
//                    			});
//                 	    	} 
//                	    }
//                	});
//        	   }
//           }
//	     ]
});



Ext.application({
    name: 'mainTrace',
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
				items:busName
			}],
			listeners:{
				afterlayout:function(){
//					  map = new AMap.Map('kid',{
//					        zoom: 10,
//					        center: [120.581699,29.997984],
//					        resizeEnable: true
//					    });
					    //在地图中添加MouseTool插件
					    mouseTool = new AMap.MouseTool(map);
					    
					    
					    
					    
					    //初始化地图对象，加载地图
					    var district, map = new AMap.Map("kid", {
					        resizeEnable: true,
					        center:[120.581699,29.997984],//地图中心点
					        zoom: 10 //地图显示的缩放级别
					    });
//					    addBeiJing();
//					    function addBeiJing() {
//					        //加载行政区划插件
//					        AMap.service('AMap.DistrictSearch', function() {
//					            var opts = {
//					                subdistrict: 1,   //返回下一级行政区
//					                extensions: 'all',  //返回行政区边界坐标组等具体信息
//					                level: 'city'  //查询行政级别为 市
//					            };
//					            //实例化DistrictSearch
//					            district = new AMap.DistrictSearch(opts);
//					            district.setLevel('district');
//					            //行政区查询
//					            district.search('上虞区', function(status, result) {
//					                var bounds = result.districtList[0].boundaries;
//					                var polygons = [];
//					 				console.info(bounds.toString());
//					                if (bounds) {
//					                    for (var i = 0, l = bounds.length; i < l; i++) {
//					                        //生成行政区划polygon
//					                        var polygon = new AMap.Polygon({
//					                            map: map,
//					                            strokeWeight: 1,
//					                            path: bounds[i],
//					                            fillOpacity: 0.7,
//					                            fillColor: '#CCF3FF',
//					                            strokeColor: '#CC66CC'
//					                        });
//					                        polygons.push(polygon);
//					                    }
//					                    map.setFitView();//地图自适应
//					                }
//					            });
//					        });
//					    }
//					    var date = new Date();
//						var sdate=date.format("yyyy-MM-dd hh:mm:ss");
//						//初始化开始时间
//						$("#starttime").val(sdate);
//						var edate=date.format("yyyy-MM-dd hh:mm:ss");
//						$("#endtime").val(edate);
//					    $('#starttime').bind('blur', function() {
//					    	alert('改变');
//					    	});
//					    $("#starttime").change(function(){
//					    	  alert('更新');
//					    	});
//					    $('#starttime').live('change', function() {
//					    	  alert("Live handler called."); 
//					    	});
//					    $('#starttime').live('onchange', function() {
//					    	  alert("Live handler called."); 
//					    	});
//					    $('#starttime').on('change', function() {
//					    	  alert("Live handler called."); 
//					    	});


				}
			}

    	});
    }
});