       var  map=null;
       var mouseTool=null;
       var markers=null;
       var collects={}; 
       var tempCollects=[];
       var flag=true;
       var searchFun=function(){
        	var tempEquipemnts=$("#equipmentids").val();
        	if(tempEquipemnts.trim()){
        		_equipmentids.split(",");
				tempCollects=_equipmentids;
				flag=true;
        		
        	}else{
        		tempCollects=[];
        	}
        }

var deviceMangerGrid=Ext.define('Ext.deviceMangerGrid.TreeGrid', {
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
    	var me = this;
    	/**
    	 * 车辆线路模型
    	 */
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
    		}
    	});
    	/**
    	 * 线路信息模型
    	 */
    	Ext.define('corMangerModel', {
    	    extend: 'Ext.data.Model',
    	    fields:["id","equipmentid","apmac","vehicleid","busname","routename","routeid","isonline","lastonlinetime","lastreporttime","reporttime","state","creator","modifier","createtime","modifytime","mark","ver","speed","timeout"]
    	});
    	
    	
    	
    	
    	var getIfhavDevice=function(equipmentid,apmac){
    		flag=true;
    		$.ajax({
				type : "GET", //提交方式  
				url : rootUrl + '/common/queryIfhaveDevice',//路径  
				async : false,
				data : {
					equipmentid:equipmentid,
					apmac:apmac
				},//数据，这里使用的是Json格式进行传输  
				success : function(result) {//返回数据根据结果进行相应的处理  
					var tempdata=eval('(' + result+ ')');
					if(tempdata.data.length==0){
					    flag=false;	
					}else{
						flag=true;
					}
					
				}
			});
    		return flag;
    	}
        var simple = Ext.widget({
            xtype: 'form',
            id: 'simpleForm',
            fieldDefaults: {
                labelWidth : 100,
                labelAlign : 'right'
            },
            overflowY:'auto',
            padding:'20 20 0 0',
            border:false,
           layout: {
                     type: 'vbox',
                     align: 'stretch'
	                 },
		             defaults: {
		                 anchor: '100%',
		            	 xtype: 'container',
		                 layout: 'hbox',
		                 border:1,
//		                 defaultType: 'textfield',
		                 margin: '10 0 10 0'
		             },
	                 items:[{
                	   xtype: 'textfield',
	                   fieldLabel: 'ID',
	                   name: 'id',
	                   hidden:true,
	                   id:'_id'
	               },{
	            	   xtype: 'textfield',
	                   fieldLabel: '设备编号',
	                   emptyText : '14306073Xxxxxxxxxxxxx',
	                   allowBlank: false,
	                   name: 'equipmentid',
	                   id:'_equipmentid'
	               },{
	            	   xtype: 'textfield',
	                   fieldLabel: '设备MAC地址',
	                   emptyText : 'XX-XX-XX-XX-XX-XX',
	                   allowBlank: false,
	                   name: 'apmac',
	                   id:'_apmac'
	               },{
	            	   xtype: 'textfield',
	                   fieldLabel: '上报时间(秒)',
	                   allowBlank: false,
	                   name: 'reporttime',
	                   id:'_reporttime'
	               },{
	            	   xtype: 'textfield',
	                   fieldLabel: '上网速度(Kbps)',
	                   allowBlank: false,
	                   name: 'speed',
	                   id:'_speed'
	               },{
	            	   xtype: 'textfield',
	                   fieldLabel: '上网超时(分钟)',
	                   allowBlank: false,
	                   name: 'timeout',
	                   id:'_timeout'
	               },{
	            	   xtype: 'displayfield',
	                   fieldLabel: '最后上线时间',
	                   name: 'lastonlinetime',
	                   id:'_lastonlinetime'
	               },{
						items:[{
								xtype:'combo',
		   		    		   store: routeStore,
			                   fieldLabel: '线路',
			                   displayField: 'name',
		   		    		   valueField: 'id',
		   		    		   minChars: 1,
			                   name: 'routeid',
			                   id:'_routeid',
			                   listeners:{
			                	   change:function(me, newValue, oldValue, eOpts){
			                		   var tempValue=me.rawValue=='全部'?'':me.rawValue;
			                		   /*routeStore.getProxy().extraParams = {linename:tempValue};
			                		   routeStore.load();*/
			                		   Ext.getCmp('_vehicleid').setValue('');
			                		   if(!tempValue){
			                			   return;
			                		   }
			                		   busStore.getProxy().extraParams = {lineId:newValue};
			                		   busStore.load();
			                	   },
//			                	   select:function(combo, records, eOpts){
//			                		   console.info('=========>select1');
//			                		   if(records.length>0){
//				                		   var newValue=records[0].data.id;
//				                		   busStore.getProxy().extraParams = {lineId:newValue};
//				                		   busStore.load();
//				                		   console.info('=========>select2');
//			                		   }
//			                	   },
	                		      afterRender : function(combo) {
	                		          combo.setValue('');//同时下拉框会将与name为firstValue值对应的 text显示
	                		       }
			                   }
			               },{
			            	   xtype:'combo',
			            	   labelWidth : 48,
		   		    		   store: busStore,
		   		    		   displayField: 'name',
		   		    		   valueField: 'id',
		   		    		   minChars: 1,
			                   fieldLabel: '车辆',
			                   name: 'vehicleid',
			                   id:'_vehicleid',
			                   listeners:{
			                	   change:function(me, newValue, oldValue, eOpts){
			                		   var tempValue=me.rawValue;
			                		   var lineId = Ext.getCmp('_routeid').getValue();
			                		   busStore.getProxy().extraParams = {lineId:lineId,busName:newValue};
//			                		   busStore.load();
			                	   },
			                	   afterRender : function(combo) {
	                		          combo.setValue('');//同时下拉框会将与name为firstValue值对应的 text显示
	                		       }
			                   }
			               }]
	               
	               
	               },{
	                   xtype: 'radiogroup',
	                   fieldLabel: '状态',
	                   id:'_state',
	                   items: [
	                       {boxLabel: '正常', name: 'state', inputValue: 1,checked: true},
	                       {boxLabel: '注销', name: 'state', inputValue: 0,margin:'0 0 0 10'},
	                   ]
	               },{
	            	   xtype: 'textareafield',
	                   fieldLabel: '备注',
	                   name: 'mark',
	                   id:'_mark' 
		           }],buttons: [{
			                text: '提交',
			                handler: function() {
			                	var routeid=Ext.getCmp('_routeid').getValue();
			                	var vehicleid=Ext.getCmp('_vehicleid').getValue();
			                	
			                	var equipmentid=Ext.getCmp('_equipmentid').getValue();
			                	var apmac=Ext.getCmp('_apmac').getValue();
			                	if(win.title=="新增设备"){
			                	var ifhave=getIfhavDevice(equipmentid,apmac);
			                	if(ifhave){
			                		 Ext.Msg.show({
	                           				title : '提示',
	                           				msg :"提交失败!</br>设备重复录入",
	                           				width : 250,
	                           				icon : Ext.Msg.ERROR,//INFO,ERROR,QUESTION,WARNING
	                           				buttonText: { ok: '确定' }
	                           			});
			                		return;
			                	}
			                	}
			                	if(!routeid){
			                		 Ext.Msg.show({
                           				title : '提示',
                           				msg :"提交失败!</br>请选择线路",
                           				width : 250,
                           				icon : Ext.Msg.ERROR,//INFO,ERROR,QUESTION,WARNING
                           				buttonText: { ok: '确定' }
                           			});
			                		 return;
			                	}
			                	if(!vehicleid){
			                		 Ext.Msg.show({
                           				title : '提示',
                           				msg :"提交失败!</br>请选择车辆",
                           				width : 250,
                           				icon : Ext.Msg.ERROR,//INFO,ERROR,QUESTION,WARNING
                           				buttonText: { ok: '确定' }
                           			});
			                		 return;
			                	}
			                	

			                	
			                	this.up('form').getForm().submit({
		                                clientValidation:true,
		                                waitMsg:'请稍候',
		                                waitTitle:'正在更新',
		                                url: rootUrl+'/devicemangercontroller/addorUpdatedevicemanger',
		                                success:function(form,action){
		                                	if(action&&action.result&&action.result.success){
		                                		if(action.result.success=="true"){
		                                			trStore.reload();
		                                		}else if(action.result.success=="false"){
//		                                			Ext.Msg.alert('温馨提示',"上传失败!");
		                                			 Ext.Msg.show({
		                                  				title : '提示',
		                                  				msg :"上传失败!",
		                                  				width : 250,
		                                  				icon : Ext.Msg.ERROR,//INFO,ERROR,QUESTION,WARNING
		                                  				buttonText: { ok: '确定' }
		                                  			});
		                                		}
		                                	}else{
//		                                		Ext.Msg.alert('温馨提示',"上传失败!");
		                                		Ext.Msg.show({
	                                  				title : '提示',
	                                  				msg :"上传失败!",
	                                  				width : 250,
	                                  				icon : Ext.Msg.ERROR,//INFO,ERROR,QUESTION,WARNING
	                                  				buttonText: { ok: '确定' }
	                                  			});
		                                	}
		                                	Ext.getCmp('simpleForm').getForm().reset();
		                                	win.close();
		                                },  
		                                failure:function(form,action){
		                                	
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
        /**
         * 车辆信息的面板
         */
        var win = Ext.create('widget.window', {
                title: '投放条件',
                header: {
                    titlePosition: 2,
                    titleAlign: 'center'
                },
                closable: false,
                modal: true,
                closeAction: 'hide',
                width: 550,
                minWidth: 350,
                height: 350,
                layout:'fit',
                items:[simple]
            });
        /**
         * 新增按钮
         */
        var addAction = Ext.create('Ext.Action', {
        	iconCls:'common_add',
            text: '新增',
            handler: function(widget, event) {
            	win.show(this,function(){
            		Ext.getCmp("simpleForm").getForm().reset();
            		win.setTitle("新增设备");
    			});
            }
        });
        
        /**
         * 刷新按钮
         */
        var refreshAction = Ext.create('Ext.Action', {
        	iconCls:'common_refresh',
            text: '刷新',
            handler: function(widget, event) {
            	trStore.reload();
            }
        });
        
        var exportAction=Ext.create('Ext.Action', {
        	iconCls:'common_excel',
            text: '导出Excel',
            handler: function(widget, event) {
            	var url=rootUrl+'/devicemangercontroller/exportdevicemanger?';
        		var title='设备信息';
        		var searchStr  = Ext.getCmp('_searchCondiction').getValue();
            	if(searchStr){
            		url+="searchStr="+searchStr+"&"
            	}
            	exportExcel(me,url,title);
            }
        });
        /**
         * 删除按钮
         */
        var deleteAction = Ext.create('Ext.Action', {
        	iconCls:'common_del',
            text: '删除',
            handler: function(widget, event) {
            	 var rec = me.getSelectionModel().getSelection();
                 if(rec){
                 	 if(rec.length==0){
                      	Ext.Msg.alert('温馨提示',"请先选中一条!");
                 	 }else if(rec.length>0){
                 		var params=new Array();
                    	for(var i=0;i<rec.length;i++){
                    		params.push(rec[i].data.id);
                    	}
                    	
                     	Ext.Msg.show({
                    	     title:'温馨提示',
                     	     msg: '确定要删除吗?',
                     	    buttonText: {yes:'是', no:'取消'},
                     	     icon: Ext.Msg.QUESTION,
                     	     fn:function(btn,txt){
                     	    	 if(btn=="yes"){
                                 	Ext.Ajax.request({
                             	    url:rootUrl+'/devicemangercontroller/deletedevicemanger',//addOrUpdateUrL,
                             	    async: false,
                             	    params: {
                             	    	ids: params.toString()
                     			    },
                             	    success: function(response){
                             	    	var respText = Ext.JSON.decode(response.responseText);
//                     			    	Ext.Msg.alert('删除成功',"共删除"+respText.data+"条记录");
                             	    	Ext.Msg.show({
                              				title : '删除成功',
                              				msg :"共删除"+respText.data+"条记录",
                              				width : 250,
                              				icon : Ext.Msg.INFO,//INFO,ERROR,QUESTION,WARNING
                              				buttonText: { ok: '确定' }
                              			});
                     			    	trStore.reload();
                             	    }
                             	});
                     	    		
                     	    	 }
                     	     }
                   		});

                 	 }
                 }
            }
        });
        
        /**
         * 更改按钮
         */
        var updateAction = Ext.create('Ext.Action', {
        	iconCls:'common_edit',
            text: '更改/查看',
            handler: function(widget, event) {
                var rec = me.getSelectionModel().getSelection();
                if(rec){
                	 if(rec.length==0){
//                     	Ext.Msg.alert('温馨提示',"请先选中一条!");
                		 Ext.Msg.show({
               				title : '温馨提示',
               				msg :"请先选中一条!",
               				width : 250,
               				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
               				buttonText: { ok: '确定' }
               			});
                	 }else if(rec.length>1){
//                		 Ext.Msg.alert('温馨提示',"您选中了多条记录!</br>编辑只能选择一条");
                		 Ext.Msg.show({
               				title : '温馨提示',
               				msg :"您选中了多条记录!</br>编辑只能选择一条",
               				width : 250,
               				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
               				buttonText: { ok: '确定' }
               			});
                	 }else{
                		 var tempdata=rec[0].data;
                			win.show(this,function(){
                				win.setTitle("更新信息");
                				Ext.getCmp('_id').setValue(tempdata.id);
        						Ext.getCmp('_equipmentid').setValue(tempdata.equipmentid);
        						Ext.getCmp('_apmac').setValue(tempdata.apmac); 
	            				 Ext.getCmp('_reporttime').setValue(tempdata.reporttime); 
	    						 Ext.getCmp('_routeid').setValue(+(tempdata.routeid)); 
								 Ext.getCmp('_vehicleid').setValue(+(tempdata.vehicleid));
								 Ext.getCmp('_speed').setValue(tempdata.speed); 
								 Ext.getCmp('_state').setValue({'state':tempdata.state});
								 Ext.getCmp('_timeout').setValue(tempdata.timeout);
								 Ext.getCmp('_mark').setValue(tempdata.mark);
								 Ext.getCmp('_lastonlinetime').setValue(tempdata.lastonlinetime);
                			});
                	 }
                }
            }
        });
        

 
        
        var gpspanel=Ext.create('Ext.panel.Panel',{
        	border:false,
        	width:'100%',
        	height:'100%',
        	html:'<div id="kid"  style="width:100%;height:100%;text-align:center;" >高德地图'+
		        	'<div style="position:absolute;top:50px;right:50px;z-index:1">'+
		        	 '<input type="text" name="equipmentids" style="width:300px" value="sd" id="equipmentids"></input>'+
		        	 '<input type="button" name="check" value="查询" id="checkEquip" onclick="searchFun()"></input>'+
		        	'<div>'+
        		'</div>',
			listeners:{
				afterlayout:function(){
					if(!mouseTool){
						   mouseTool = new AMap.MouseTool(map);
					}
					if(!map){
					   var scale = new AMap.Scale({
					        visible: true
					    }),
					    toolBar = new AMap.ToolBar({
					        visible: true
					    });
						//初始化地图对象，加载地图
					     map= new AMap.Map("kid", {
					        resizeEnable: true,
					        center:[120.581699,29.997984],//地图中心点
					        zoom: 10 //地图显示的缩放级别
					    });
					     map.addControl(scale);
					     map.addControl(toolBar);
					}
				}
			}
        });
        
        /**
         * 实时位置查看
         */
        var wingps = Ext.create('widget.window', {
                title: '实时位置查看',
                header: {
                    titlePosition: 2,
                    titleAlign: 'center'
                },
                closable: true,
                modal: true,
                closeAction: 'hide',
                width: '100%',
                height: '100%',
                layout:'fit',
                items:[gpspanel],
                listeners:{
                	close:function(){
//                		alert('关闭websocket');
//                		$.ajax({
//          					type : "GET", //提交方式  
//          					url : rootUrl + '/devicemangercontroller/setdevicegpssupportall',//路径  
//          					async : true,
//          					data : {
//    							flag:'0'
//          					},//数据，这里使用的是Json格式进行传输  
//          					success : function(result) {//返回数据根据结果进行相应的处理  
// 
//          					}
//          				});
                	}
                }
            });
        var gpsAction = Ext.create('Ext.Action', {
        	iconCls:'common_put',
            text: '实时定位',
            handler: function(widget, event) {
//                var rec = me.getSelectionModel().getSelection();
//                if(rec){
//                	 if(rec.length==0){
//                		 Ext.Msg.show({
//               				title : '温馨提示',
//               				msg :"请先选中一条!",
//               				width : 250,
//               				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
//               				buttonText: { ok: '确定' }
//               			});
//                	 }else{
                		 //设置GPS临时上报时间
                		 websocket();
//	                		var equipments=[];
//	         				for(var i=0;i<rec.length;i++){
//	         					equipments.push(rec[i].data.equipmentid);
//	         				}
                			wingps.show(this,function(){
                				//查询GPS；
                				
                			});
//                	 }
//                }
            }
        });
 
        
        
        //关闭信息窗体
        var closeInfoWindow=function () {
            map.clearInfoWindow();
        }
        //构建自定义信息窗体
       var createInfoWindow = function (title, content) {
            var info = document.createElement("div");
            info.className = "info";

            //可以通过下面的方式修改自定义窗体的宽高
            //info.style.width = "400px";
            // 定义顶部标题
            var top = document.createElement("div");
            var titleD = document.createElement("div");
            var closeX = document.createElement("img");
            top.className = "info-top";
            titleD.innerHTML = title;
            closeX.src = "http://webapi.amap.com/images/close2.gif";
            closeX.onclick = closeInfoWindow;

            top.appendChild(titleD);
            top.appendChild(closeX);
            info.appendChild(top);

            // 定义中部内容
            var middle = document.createElement("div");
            middle.className = "info-middle";
            middle.style.backgroundColor = 'white';
            middle.innerHTML = content;
            info.appendChild(middle);

            // 定义底部内容
            var bottom = document.createElement("div");
            bottom.className = "info-bottom";
            bottom.style.position = 'relative';
            bottom.style.top = '0px';
            bottom.style.margin = '0 auto';
            var sharp = document.createElement("img");
            sharp.src = "http://webapi.amap.com/images/sharp.png";
            bottom.appendChild(sharp);
            info.appendChild(bottom);
            return info;
        }
       
       
       var infoWindow = new AMap.InfoWindow({
           isCustom: true,  //使用自定义窗体
           content: createInfoWindow("无设备",['暂无']),
           offset: new AMap.Pixel(16, -45)
       });
		var showAjax=function(title,markers){
			$.ajax({
				type : "GET", //提交方式  
				url : rootUrl + '/devicemangercontroller/queryDevices',//路径  
				async : true,
				data : {
					equipmentids:title
				},//数据，这里使用的是Json格式进行传输  
				success : function(result) {//返回数据根据结果进行相应的处理  
					var tempdata=eval('(' + result+ ')');
//					[{"id":538,"equipmentid":"14306073X00037F8272D0","busname":null,"routename":null,"reporttime":2,"ver":"V1.00.0-9301","speed":1024,"timeout":15000}]
					if(tempdata){
						content = [];
		    	        content.push("线路:"+tempdata[0].routename?tempdata[0].routename:'未登记');
		    	        content.push("车牌号:"+tempdata[0].busname?tempdata[0].busname:'未登记');
		    	        content.push("固件版本:"+tempdata[0].ver);
		    	        content.push("网速:"+tempdata[0].speed+'Kbps');
		    	        content.push("上网超时时间:"+tempdata[0].timeout+'分钟');
		    	        content.push("GPS上报时间间隔:"+tempdata[0].reporttime+'s');
		    	        infoWindow.setContent(createInfoWindow(title, content.join("<br/>")));
			            infoWindow.open(map, markers.getPosition());
					}
				}
			});
		}
        var showMap=function(tempdata){
        	var tmarkers=collects[tempdata.equipmentid];
			  if(tmarkers){
				  map.remove(tmarkers);
			  }
		    var markers=new AMap.Marker({
		        map: map,
				position: [tempdata.longitude, tempdata.latidude],
		        icon: new AMap.Icon({            
		            size: new AMap.Size(64, 64),  //图标大小
		            image: rootUrl+"/images/icons/router.png",
		            imageSize:new AMap.Size(32,32)
//      		            imageOffset: new AMap.Pixel(0, -60)
		        })        
		    });

    		  AMap.event.addListener(markers, 'click', function() {
//	      			var title = tempdata.equipmentid,
	      			showAjax(tempdata.equipmentid,markers);

		        });
    		
    		  collects[tempdata.equipmentid]=markers;
        }
        var websocket=function(){
      	  if(!window.WebSocket){
      		  window.WebSocket = window.MozWebSocket;
      	  }
      	  if(window.WebSocket){
      		  socket = new WebSocket("ws://"+rootIp+":7397/websocket");
      		  socket.onmessage = function(event){
      			  var tempdata=eval('(' + event.data+ ')');
//      			  console.info(tempdata);
//      			{equipmentid:"+equipmentid+",longitude:"+longitude+",latidude:"+latidude+"}
      			  if(tempCollects.length!=0){
//      				var tempEl=$.inArray(tempdata.equipmentid,tempCollects);
      				var tempEl=tempCollects.indexOf(tempdata.equipmentid);
      				if(flag){
      					var tempColem=[];
      					for(var n in collects){
      						tempColem.push(collects[n]);
      					}
      					map.remove(tempColem);	
      					flag=false;
      				}
      				if(tempEl>0){
      					showMap(tempdata); 
      				}
      			  }else{
      				 showMap(tempdata); 
      			  }
      		  };
      		  socket.onopen = function(event){
//      			  console.info('打开websocket通信连接');
//      				$.ajax({
//      					type : "GET", //提交方式  
//      					url : rootUrl + '/devicemangercontroller/setdevicegpssupportall',//路径  
//      					async : true,
//      					data : {
//							flag:'1'
//      					},//数据，这里使用的是Json格式进行传输  
//      					success : function(result) {//返回数据根据结果进行相应的处理  
//      					}
//      				});
      		  };
      	 
      		  socket.onclose = function(event){
      		  };
      	  }else{
      			alert("您的浏览器不支持WebSocket协议！");
      	  }
        }
   	 /**
   	  * 得到所有用户信息
   	  */
   	 var trStore=Ext.create('Ext.data.Store', {
   		    model: corMangerModel,
   		    pageSize:defaultPageSize,
   		    proxy: {
   		    	type: 'ajax',
   		    	url:rootUrl+'/devicemangercontroller/querydevicemanger',
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
              dataIndex: 'id',
              hidden:true
            },{//"id","equipmentid","apmac","vehicleid","isonline","lastonlinetime","lastreporttime","reporttime","state","creator","modifier","createtime","modifytime","mark","ver","speed","timeout"
           	  text: '设备编号',
	          flex: 1,
	          align:'center',
	          dataIndex: 'equipmentid',
	          sortable: true
            },{
         	  text: 'MAC',
              flex: 1,
              hidden:true,
              align:'center',
              dataIndex: 'apmac',
              sortable: true
            },{
         	  text: '车辆ID',
              flex: 1,
              align:'center',
              hidden:true,
              dataIndex: 'vehicleid',
              sortable: true
            },{
           	  text: '所属车辆',
                flex: 1,
                align:'center',
                dataIndex: 'busname',
                sortable: true
            },{
             	  text: '车辆自编号',
                  flex: 1,
                  align:'center',
                  dataIndex: 'owercode',
                  sortable: true
                      
                
            },{
           	  text: '所属线路',
                flex: 1,
                align:'center',
                dataIndex: 'routename',
                sortable: true
            },{
           	  text: '线路ID',
                flex: 1,
                hidden:true,
                align:'center',
                dataIndex: 'routeid',
                sortable: true
            },{
           	  text: '是否在线',
              flex: 1,
              align:'center',
              dataIndex: 'isonline',
              sortable: true,
              renderer:function(value){
              	if(value=='1'){
              		return '<font face="arial" color="green" >在线</font>';
              	}else if(value=='0'){
              		return '<font face="arial" color="red" >离线</font>';
              	}
              }
            },{
         	  text: '最后活动时间',
              flex: 1,
              hidden:true,
              align:'center',
              dataIndex: 'lastonlinetime',
              sortable: true   
            },{
           	  text: '最后上报时间',
              flex: 1,
              align:'center',
              hidden:true,
              dataIndex: 'lastreporttime',
              sortable: true   
            },{
         	  text: '上报间隔(S)',
              flex: 1,
              align:'center',
              dataIndex: 'reporttime',
              sortable: true
            },{
           	  text: '设备状态',
	          flex: 1,
	          align:'center',
	          dataIndex: 'state',
	          sortable: true,
	          renderer:function(value){
	              	if(value=='1'){
	              		return '<font face="arial" color="green" >正常</font>';
	              	}else if(value=='0'){
	              		return '<font face="arial" color="red" >注销</font>';
	              	}else if(value=="2"){
	              		return '<font face="arial" color="red" >未登记</font>';
	              	}
	              }
            },{
           	  text: '创建人',
              flex: 1,
              hidden:true,
              align:'center',
              dataIndex: 'creator',
              sortable: true
            },{
         	  text: '创建时间',
              flex: 1,
              hidden:true,
              align:'center',
              dataIndex: 'createtime',
              sortable: true
            },{
         	  text: '修改人',
              flex: 1,
              hidden:true,
              align:'center',
              dataIndex: 'modifier',
              sortable: true
            },{
         	  text: '修改时间',
              flex: 1,
              hidden:true,
              align:'center',
              dataIndex: 'modifytime',
              sortable: true
            },{
         	  text: '说明',
              flex: 1,
              align:'center',
              hidden:true,
              dataIndex: 'mark',
              sortable: true
            },{
         	  text: '设备固件版本号',
              flex: 1,
              align:'center',
              hidden:true,
              dataIndex: 'ver',
              sortable: true
            },{
         	  text: '下载速度(Kbps)',
              flex: 1,
              align:'center',
              dataIndex: 'speed',
              sortable: true
            },{
         	  text: '上网超时时间(分钟)',
              flex: 1,
              align:'center',
              dataIndex: 'timeout',
              sortable: true
            }],selModel : Ext.create('Ext.selection.CheckboxModel', {
				mode : "MULTI",
				width : 222,
				header : "a"
			}),bbar: {
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
            },tbar: [addAction,updateAction,deleteAction,refreshAction,exportAction,gpsAction,'->',
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
    name: 'mainRoute',
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
				items:deviceMangerGrid
			}]

    	});
    }
});