
var apstartmarker=new Array();
var apendmarker=new Array();
var busPolyline=new Array();
var infowindow = new Array();
var num=0;
var mouseTool=null;
var addressStore=null;

var addressMarker=new Array();
var addressPloyline=new Array();
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
    	
    	Ext.define('addressModel',{
    	    extend: 'Ext.data.Model',
    	    fields: [
    	        {name: 'id', type: 'string'},
    	        {name: 'name', type: 'string'},
    	        {name: 'longitude1', type: 'string'},
    	        {name: 'latitude1', type: 'string'},
    	        {name: 'longitude2', type: 'string'},
    	        {name: 'latitude2', type: 'string'},
    	        {name: 'longitude3', type: 'string'},
    	        {name: 'latitude3', type: 'string'},
    	        {name: 'longitude4', type: 'string'},
    	        {name: 'latitude4', type: 'string'},
    	        {name: 'longitude5', type: 'string'},
    	        {name: 'latitude5', type: 'string'},
    	        {name: 'longitude6', type: 'string'},
    	        {name: 'latitude6', type: 'string'},
    	        {name: 'longitude7', type: 'string'},
    	        {name: 'latitude7', type: 'string'},
    	        {name: 'longitude8', type: 'string'},
    	        {name: 'latitude8', type: 'string'},
    	        {name: 'longitude9', type: 'string'},
    	        {name: 'latitude9', type: 'string'},
    	        {name: 'longitude10', type: 'string'},
    	        {name: 'latitude10', type: 'string'}
    	    ]
    	});
    	

    	/**
    	 * addressStore    
    	 */
    	addressStore=Ext.create('Ext.data.Store',{
    		model:'addressModel',
    	    proxy: {
    	        type: 'ajax',
    	        url: rootUrl+'/amapc/queryAddress',
    	        reader: {
    	            type: 'json',
    	            root: 'data'
    	        }
    	    }, autoLoad:true
    	});

       	
    	/**
    	 * 模版周期Store    
    	 */
    	var routeStore=Ext.create('Ext.data.Store',{
    		model:'uUser',
    	    proxy: {
    	        type: 'ajax',
    	        url: rootUrl+'/common/queryRoadInfo',
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
    	var viewaddressgrid= Ext.create('Ext.grid.Panel', {
    	    store: addressStore,
    	    flex:1,
    	    columns: [
    	    	{text: "ID", flex: 1, sortable: false, dataIndex: 'id',hidden:true,align:'center'},
    	        {text: "地址名称", flex: 1, sortable: false, dataIndex: 'name',align:'center'},
    	       {text: "longitude1", flex: 1, sortable: false, dataIndex: 'longitude1',hidden:true,align:'center'},
    	       {text: "latitude1", flex: 1, sortable: false, dataIndex: 'latitude1',hidden:true,align:'center'},
    	       {text: "longitude2", flex: 1, sortable: false, dataIndex: 'longitude2',hidden:true,align:'center'},
    	       {text: "latitude2", flex: 1, sortable: false, dataIndex: 'latitude2',hidden:true,align:'center'},
    	       {text: "longitude3", flex: 1, sortable: false, dataIndex: 'longitude3',hidden:true,align:'center'},
    	       {text: "latitude3", flex: 1, sortable: false, dataIndex: 'latitude3',hidden:true,align:'center'},
    	       {text: "longitude4", flex: 1, sortable: false, dataIndex: 'longitude4',hidden:true,align:'center'},
    	       {text: "latitude4", flex: 1, sortable: false, dataIndex: 'latitude4',hidden:true,align:'center'},
    	       {text: "longitude5", flex: 1, sortable: false, dataIndex: 'longitude5',hidden:true,align:'center'},
    	       {text: "latitude5", flex: 1, sortable: false, dataIndex: 'latitude5',hidden:true,align:'center'},
    	       {text: "longitude6", flex: 1, sortable: false, dataIndex: 'longitude6',hidden:true,align:'center'},
    	       {text: "latitude6", flex: 1, sortable: false, dataIndex: 'latitude6',hidden:true,align:'center'},
    	       {text: "longitude7", flex: 1, sortable: false, dataIndex: 'longitude7',hidden:true,align:'center'},
    	       {text: "latitude7", flex: 1, sortable: false, dataIndex: 'latitude7',hidden:true,align:'center'},
    	       {text: "longitude8", flex: 1, sortable: false, dataIndex: 'longitude8',hidden:true,align:'center'},
    	       {text: "latitude8", flex: 1, sortable: false, dataIndex: 'latitude8',hidden:true,align:'center'},
    	       {text: "longitude9", flex: 1, sortable: false, dataIndex: 'longitude9',hidden:true,align:'center'},
    	       {text: "latitude9", flex: 1, sortable: false, dataIndex: 'latitude9',hidden:true,align:'center'},
    	       {text: "longitude10", flex: 1, sortable: false, dataIndex: 'longitude10',hidden:true,align:'center'},
    	       {text: "latitude10", flex: 1, sortable: false, dataIndex: 'latitude10',hidden:true,align:'center'}
    	        ],
    	    columnLines: true,
    	    selModel:Ext.create('Ext.selection.CheckboxModel',{
    	    	mode:"MULTI",
    	    	width : 222,
    	    	header : "a"}),
    	    id:'viewaddressgrid',
    	    tbar:['->',{
    	    	 xtype:'button',
    	    	 text: '展示到地图上',
    	    	 iconCls:'common_search',
    	    	 handler:function(){
    	    		 var rec = Ext.getCmp('viewaddressgrid').getSelectionModel().getSelection();
	                   if(rec.length==0){
//	                   		Ext.Msg.alert('温馨提示',"请先选中一条!");
	                	   Ext.Msg.show({
                				title : '温馨提示',
                				msg :"请先选中一条!",
                				width : 250,
                				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
                				buttonText: { ok: '确定'}
                			});
	                   }else if(rec.length>0){
	                	   hideaddressPoly();
                		   hideaddressMarker();
	                	   addressMarker=new Array();
                		   addressPloyline=new Array();
                		
	                	   for(var i=0;i<rec.length;i++){
	                		   var polygonArr = new Array();
	                		   for(var j=1;j<=10;j++){
	                			   if(rec[i].data['longitude'+j]&&rec[i].data['latitude'+j]){
	                				   polygonArr.push([rec[i].data['longitude'+j],rec[i].data['latitude'+j]])
	                			   }
	                		   }
	                		   var _polygon=new AMap.Polygon({
		                		   map:map,
		                	        path: polygonArr,//设置多边形边界路径
		                	        strokeColor: "#FF33FF", //线颜色
		                	        strokeOpacity: 0.2, //线透明度
		                	        strokeWeight: 3,    //线宽
		                	        fillColor: "#1791fc", //填充色
		                	        fillOpacity: 0.35//填充透明度
		                	    });
	                		   addressPloyline.push(_polygon);
	                		   var _marker=new AMap.Marker({
	                	            icon: "http://webapi.amap.com/theme/v1.3/markers/n/mark_b.png",
	                	            position: [rec[i].data['longitude1'],rec[i].data['latitude1']],
	                	            map:map
	                	        });
	                		   _marker.setLabel({//label默认蓝框白底左上角显示，样式className为：amap-marker-label
	                		        offset: new AMap.Pixel(20, 20),//修改label相对于maker的位置
	                		        content: rec[i].data.name
	                		    });
	                		   addressMarker.push(_marker);
	                	   }
	                   }
    	    	 }
    	    },{
   	    	 xtype:'button',
	    	 text: '删除',
	    	 iconCls:'common_del',
	    	 handler:function(){
	    		 var rec = Ext.getCmp('viewaddressgrid').getSelectionModel().getSelection();
                 if(rec.length==0){
//                 		Ext.Msg.alert('温馨提示',"请先选中一条!");
                	 Ext.Msg.show({
          				title : '温馨提示',
          				msg :"请先选中一条!",
          				width : 250,
          				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
          				buttonText: { ok: '确定'}
          			});
                 }else if(rec.length>0){
                		Ext.Msg.show({
                    	     title:'温馨提示',
                     	     msg: '确定要删除吗?',
                     	   buttonText: {yes:'是', no:'取消'},
                     	     icon: Ext.Msg.QUESTION,
                     	     fn:function(btn,txt){
                     	    	 if(btn=="yes"){
                     	    		 var ids = new Array();
                     	    		 for(var i=0;i<rec.length;i++){
                     	    			ids.push(rec[i].data.id);
                     	    		 }
                     	    		Ext.Ajax.request({
                      				    url: rootUrl+'/amapc/deleteAddress',
                      				    params: {ids:ids.toString()},
                      				    success: function(response){
                      				    	var text = response.responseText;
                     	    		        var _respText = Ext.JSON.decode(text);
//                     	    		      Ext.Msg.alert('温馨提示',"共删除了"+_respText.data+"记录");
                     	    		       Ext.Msg.show({
                     	         				title : '温馨提示',
                     	         				msg :"共删除了"+_respText.data+"记录",
                     	         				width : 250,
                     	         				icon : Ext.Msg.INFO,//INFO,ERROR,QUESTION,WARNING
                     	         				buttonText: { ok: '确定'}
                     	         			});
                     	    		 	addressStore.reload();
                      				    }
                      				});
                     	    		
                     	    	 }
                     	     }
                   		});
                 }
	    	 }}]
    	   
    	});
    	/**
    	 * 广告位展示
    	 */
    	var viewroutegrid= Ext.create('Ext.grid.Panel', {
    	    store: routeStore,
    	    flex:1,
    	    id:'viewroutegrid',
    	    columns: [
    	    	{text: "ID", flex: 1, sortable: false, dataIndex: 'id',hidden:true,align:'center'},
    	        {text: "参考路线", flex: 1, sortable: false, dataIndex: 'name',align:'left'}
    	        ],
    	    columnLines: true,
    	    selModel:Ext.create('Ext.selection.CheckboxModel',{mode:"MULTI",width : 222,header : "a"}),
    	    tbar:['->',{
	        	   xtype:'button',
	        	   text: '展示到地图上',
	        	   iconCls:'common_search',
	        	   handler: function() {
	        		   var rec = Ext.getCmp('viewroutegrid').getSelectionModel().getSelection();
	                   if(rec.length==0){
//	                   		Ext.Msg.alert('温馨提示',"请先选中一条!");
	                	   Ext.Msg.show({
                				title : '温馨提示',
                				msg :"请先选中一条!",
                				width : 250,
                				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
                				buttonText: { ok: '确定'}
                			});
	                   }else if(rec.length>0){
	                	   hidebusPolyline();
	                	   hideapstartmarker();
	                	   hideapendmarker();
							  busPolyline=new Array();
							  apstartmarker=new Array();
							  apendmarker = new Array();
							  infowindow = new Array();
							  num=0;
	                	   for(var i=0;i<rec.length;i++){
	                		   var tname=rec[i].data.name;
	                		   AMap.service(["AMap.LineSearch"], function() {
			    	            var linesearch = new AMap.LineSearch({
									    	            pageIndex:1,
									    	            city:'绍兴',
									    	            pageSize:1,
									    	            extensions:'all'//返回全部信息
									    	            });
			    	            //TODO:调用search方法
			    	            linesearch.search(tname, function(status, result){
			    	                //取回公交路线查询结果
			    	                if(status === 'complete' && result.info === 'OK'){
			    	                            //取得了正确的公交路线结果
			    	                	var routeInfo=result.lineInfo[0];
			    	                	var distance=routeInfo.distance;
			    	                	var end_stop=routeInfo.end_stop;
			    	                	var start_stop=routeInfo.start_stop;
			    	                	var name=routeInfo.name;
			    	                	var type=routeInfo.type;
			    	                	var sdiv='<div>'+
			    	                			 '全名:'+name+'</br>'+
			    	                			 '全长:'+distance+'公理</br>'+
			    	                			 '起点站:'+start_stop+'</br>'+
			    	                			 '终点站:'+end_stop+'</br>'+
			    	                			 '线路类型:'+type+'</br>'+
			    	                			'</div>';
			    	                    lineSearch_Callback(result,map,sdiv);
			    	                    console.info('=='+i);
			    	                            //结果处理函数，demo中绘制了线路
			    	                }else{
			    	                    //无数据或者查询失败
			
			    	                }
			    	            });
			    	            
			    	            
			    	        });
	                    	}
	                	   console.info('end');
	                   }
	        	    }
	           }]
    	});

        /*公交路线查询服务返回数据解析概况*/
        function lineSearch_Callback(data,_map,routeName) {
            var lineArr = data.lineInfo;
            var lineNum = data.lineInfo.length;
            if (lineNum == 0) {
            } else {
                for (var i = 0; i < lineNum; i++) {
                    var pathArr = lineArr[i].path;
                    var stops = lineArr[i].via_stops;
                    var startPot = stops[0].location;
                    var endPot = stops[stops.length - 1].location;

                    if (i == 0) drawbusLine(startPot, endPot, pathArr,_map,routeName);
                }
            }
        }

        /*绘制路线*/
        function drawbusLine(startPot, endPot, BusArr,_map,routeName) {
            //绘制起点，终点
            var _apstartmarker=new AMap.Marker({
                map: _map,
                position: [startPot.lng, startPot.lat], //基点位置
                icon: "http://webapi.amap.com/theme/v1.3/markers/n/start.png",
                zIndex: 10,
                routename:routeName,
                num:num
            });
            var _apendmarker=new AMap.Marker({
                map: _map,
                position: [endPot.lng, endPot.lat], //基点位置
                icon: "http://webapi.amap.com/theme/v1.3/markers/n/end.png",
                zIndex: 10,
                routename:routeName,
                num:num
            });
            //绘制乘车的路线
            var _busPolyline = new AMap.Polyline({
                map: _map,
                path: BusArr,
                strokeColor: "#09f",//线颜色
                strokeOpacity: 0.8,//线透明度
                strokeWeight: 6//线宽
            });
            var _infowindow = new AMap.InfoWindow({
                content: routeName,
                offset: new AMap.Pixel(0, -30),
                size:new AMap.Size(230,0)
            });
            apendmarker.push(_apendmarker);
            busPolyline.push(_busPolyline);
            apstartmarker.push(_apstartmarker);
            infowindow.push(_infowindow);
            AMap.event.addListener(apendmarker[num], 'click', function(a) {
            	var _routename=a.target.G.routename;
            	var _num=a.target.G.num;
            	infowindow[_num].open(map, apendmarker[_num].getPosition())
            })
               AMap.event.addListener(apstartmarker[num], 'click', function(a) {
            	var _routename=a.target.G.routename;
            	var _num=a.target.G.num;
            	infowindow[_num].open(map, apstartmarker[_num].getPosition())
            })
             num++;
            _map.setFitView();
        }

		        this.items = [{
        					xtype:'panel',
        					width:250,
        					layout:{
        						type:'vbox',
        						align:'stretch',
        						pack:'center'
        					},
						    items:[viewaddressgrid,viewroutegrid]
						},{
							layout:'fit',
							xtype:'panel',
							frame:false,
							flex:1,
			                html: "<div style='width:100%;height:100%;text-align:center' id='container'></div>" +
			                		"<div class='button-group'>"+
									    "<input type='button' class='button' value='鼠标绘制面' id='polygon'/>"+
									    "<input type='button' class='button' style='margin-left:10px;' value='清除公交线路' id='clearoute'/>"+
									    "<input type='button' class='button' style='margin-left:10px;' value='清除地址' id='clearaddress'/>"+
									    "<input type='button' class='button' style='margin-left:10px;' value='清除画线' id='clearploy'/>"+
									"</div>"
						}
		        ];
		
		        this.callParent();
		    }
		});
var map=null;
var addressForm= Ext.create('Ext.form.Panel', {
    plain: true,
    border: 0,
    margin:5,
    height: 125,
    fieldDefaults: {
        labelWidth: 30,
        anchor: '100%'
    },
//    flex:1,
    xtype: 'fieldset',
    items: [{
                fieldLabel: '名称',
                name: 'name',
                xtype: 'textfield',
                id:'nameid'
            },{
                fieldLabel: '描述',
                name: 'descr',
                xtype: 'textareafield',
                hight:80,
                id:'descrid'
            },{
                fieldLabel: '经纬',
                hidden:true,
                xtype: 'textfield',
                name: 'latlong',
                id:'latlongid'
            }
    ],
    buttons: [{ 
      text: '保存',
      handler: function(){
    	  this.up('form').getForm().submit({
              clientValidation:true,
              waitMsg:'请稍候',
              waitTitle:'正在保存',
              url:rootUrl+'/amapc/saveaddress',
              success:function(form,action){
              	if(action&&action.result&&action.result.success){
              		if(action.result.success=="true"){
              			//成功后更新表格并关闭win
              			addressStore.reload();
              		}
              	}else{
//              		Ext.Msg.alert('温馨提示',"更新失败");
              	  Ext.Msg.show({
       				title : '温馨提示',
       				msg :"更新失败",
       				width : 250,
       				icon : Ext.Msg.ERROR,//INFO,ERROR,QUESTION,WARNING
       				buttonText: { ok: '确定'}
       			});
              	}
              	addressForm.getForm().reset();
              	win.close();
              	
              },  
              failure:function(form,action){  
            	  Ext.Msg.show({
         				title : '温馨提示',
         				msg :"更新失败",
         				width : 250,
         				icon : Ext.Msg.ERROR,//INFO,ERROR,QUESTION,WARNING
         				buttonText: { ok: '确定'}
         			});
              }  
          })
      	}
      },{    
          text:'取消',
          handler: function() {
        	  addressForm.getForm().reset();
        	  win.close();
          }
      }]
});
var win= Ext.create('widget.window', {
    title: '新增区域地址',
    header: {
        titlePosition: 2,
        titleAlign: 'center'
    },
    closable: false,
    modal: true,
    closeAction: 'hide',
    width: 450,
    minWidth: 350,
    height: 200,
    layout: {
        type: 'fit'
    },
    items:addressForm
});
//隐藏自定义地址
var hideaddressPoly=function(){
	if(addressPloyline.length==0){
		return;
	}
 	 for(var i=0;i<addressPloyline.length;i++){
		 addressPloyline[i].hide();
	 }
}
//隐藏自定义地址标签
var hideaddressMarker=function(){
	if(addressMarker.length==0){
		return;
	}
 	 for(var i=0;i<addressMarker.length;i++){
 		addressMarker[i].hide();
	 }
}
//隐藏公交线路
var hidebusPolyline=function(){
	for(var i=0;i<busPolyline.length;i++){
		if(busPolyline.length==0){
			return;
		}
		  busPolyline[i].setMap();								  									  
	}
}
//隐藏公交线路开始点
var hideapstartmarker=function(){
	for(var i=0;i<apstartmarker.length;i++){
		if(apstartmarker.length==0){
			return;
		}
		  apstartmarker[i].setMap();
	}
}
//隐藏公交线路终点
var hideapendmarker=function(){
	for(var i=0;i<apendmarker.length;i++){
		if(apendmarker.length==0){
			return;
		}
		  apendmarker[i].setMap();	
	}
}
/*
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
			}],
			listeners:{
				afterlayout:function(){
					  map = new AMap.Map('container',{
					        zoom: 10,
					        center: [120.581699,29.997984],
					        resizeEnable: true
					    });
					    //在地图中添加MouseTool插件
					    mouseTool = new AMap.MouseTool(map);
					    $('#polygon').click(function(){
					    	 map.setDefaultCursor("crosshair");
					    	 mouseTool.polygon();
					    });
					    
					    $('#clearoute').click(function(){
					    	hidebusPolyline();
					    	hideapstartmarker();
					    	hideapendmarker();
					    });
					    
			    		$('#clearaddress').click(function(){
			    			 hideaddressPoly();
					    	 hideaddressMarker();
			    		});
			    		
			    		$('#clearploy').click(function(){
			    			 mouseTool.close(true);
			    		});				    
					    
					    close( Boolean)
					      AMap.event.addListener(mouseTool,"draw",function(e,b){
					    	  map.setDefaultCursor("pointer");
					            var drawObj = e.obj;  //obj属性就是绘制完成的覆盖物对象。 
					            var pointsCount = e.obj.getPath().length; //获取节点个数 
//					           alert("多边形节点数："+pointsCount+"<br>节点坐标："+e.obj.getPath()); 
					           if(pointsCount>9){
//					        	   Ext.Msg.alert('温馨提示',"点数已超过9个范围!");
					        	   Ext.Msg.show({
	                     				title : '温馨提示',
	                     				msg :"点数已超过9个范围!",
	                     				width : 250,
	                     				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
	                     				buttonText: { ok: '确定'}
	                     			});
					        	   mouseTool.close(true);
					        	   return;
					           }
	              	    		 win.show(null,function(){
	              	    			 Ext.getCmp('latlongid').setValue(drawObj.getPath());			                  	    			 
	              	    		 })

					           mouseTool.close(false);
					        });
				}
			}
    	});
    }
});

