/**
 * 产品模型
 */
Ext.define('PROCOMBOMODEL', {
        extend: 'Ext.data.Model',
        fields: ["id","proname"]
    });
Ext.define('SCHEDULEMODEL', {
        extend: 'Ext.data.Model',
        fields: [{
        			name:'id',
        			type:'string'
        		},{
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
	    	    	name:'state',
	    	    	type:'string'
	    	    }]
    });
/**
 * 产品模型
 */
Ext.define('COMMONMODEL', {
        extend: 'Ext.data.Model',
        fields: ["id","name"]
    });
/**
 * 查询认证周期对应的模型
 */
var modelStore =function(extraParams){
	return  Ext.create('Ext.data.Store', {
		model : 'COMMONMODEL',
		proxy : {
			type : 'ajax',
			url : rootUrl + '/common/queryAllModel',
			reader : {
				type : 'json',
				root : 'data'
			},
			extraParams:extraParams
		},
		autoLoad:true
	});
}

/**
 * 查询认证周期/模型对应的产品
 */
var productStore = function(extraParams){
	return Ext.create('Ext.data.Store', {
		model : 'COMMONMODEL',
		proxy : {
			type : 'ajax',
			url : rootUrl + '/common/queryProduct',
			reader : {
				type : 'json',
				root : 'data'
			},
			extraParams:extraParams
		},
		autoLoad:true
	});
}

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
var authcycStore = Ext.create('Ext.data.Store', {
	model : 'COMMONMODEL',
	proxy : {
		type : 'ajax',
		url : rootUrl + '/common/queryAuthCyc',
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
/**
 * 查询时间设置
 */
var getTimeSet=function(){
	Ext.Ajax.request({
	    url:rootUrl+'/common/querytimeSet',
	    async: false,
	    success: function(response){
	    	var respText = Ext.JSON.decode(response.responseText);
	    	if(respText&&respText.data){
	    		var _data=respText.data;
	    		var tempData=new Array();
	    		for(var i=0;i<_data.length;i++){
	    			tempData.push({boxLabel:_data[i].name,inputValue:_data[i].id,checked:i==0?true:false});

	    		}
	    	}
	    	Ext.getCmp('timesetpit').add(tempData);
	    }
	});
}    

var authcyc=new Array();
var splitline={ xtype: 'displayfield',height:1, margin:'0 0 20 0', value: '<hr width=100% style="height:1px;border:none;border-top:1px dashed #177ebc;"/>' };
/**
 * 查询认证周期，并赋值相应的下拉选项
 */
var temptotal=0;
var _productstore=new Array();
var _modelstore=new Array();
var getputProduct=function(){
	Ext.Ajax.request({
	    url:rootUrl+'/common/queryAuthCyc',
	    async: false,
	    success: function(response){
	    	var respText = Ext.JSON.decode(response.responseText);
	    	
	    	if(respText&&respText.data){
	    		var _data=respText.data;
	    		var tempData=new Array();

	    		temptotal=_data.length;
	    		for(var i=0;i<_data.length;i+=2){
	    			_modelstore.push(modelStore({cycid:_data[i].id}));
	    			_productstore.push(productStore({cycid:_data[i].id}));
					var temp={
							margin : '0 0 0 20',
							defaults : {
								labelAlign : 'left',
								labelWidth:35,
								width:150,
								margin:i==_data.length/2?'0 20 10 0':'0 20 0 0'
							},
							items:[{xtype:'checkbox',width:80,boxLabel:_data[i].name,inputValue:_data[i].id,checked:i==0?true:false,id:'cycauthidcheck'+i},
							       {
										xtype:'combo',
				   		    		   store: _modelstore[i],
					                   fieldLabel: '模版',
					                   displayField: 'name',
				   		    		   valueField: 'id',
					                   name: 'model'+i,
					                   id:'_model'+i,
					                   listeners:{
					                	   change:function(me, newValue, oldValue, eOpts){
					                		   var t=me.id.split('_model');
					                		   var n=t[1];
					                		   var cycid=Ext.getCmp('cycauthidcheck'+n).inputValue;
					                		   _productstore[n].getProxy().extraParams = {modelid:newValue,cycid:cycid};
					                		   _productstore[n].load();
					                		   Ext.getCmp('_product'+n).setValue('');
					                	   },afterRender:function(combo) {
				                		          combo.setValue('');//同时下拉框会将与name为firstValue值对应的 text显示
			                		       }
					                   }
					               },{
										xtype:'combo',
				   		    		   store: _productstore[i],
				   		    		   displayField: 'name',
				   		    		   valueField: 'id',
					                   fieldLabel: '产品',
					                   name: 'product'+i,
					                   id:'_product'+i,
					                   listeners:{
					                	   afterRender:function(combo) {
				                		      combo.setValue('');//同时下拉框会将与name为firstValue值对应的 text显示
			                		       }
					                   }
					               },
					               ]
						}
					
					
					
					if((i+1)<_data.length){
						_modelstore.push(modelStore({cycid:_data[i+1].id}));
		    			_productstore.push(productStore({cycid:_data[i+1].id}));
						temp.items.push(
								{xtype:'checkbox',width:80,boxLabel:_data[i+1].name,inputValue:_data[i+1].id,checked:(i+1)==0?true:false,id:'cycauthidcheck'+(i+1)},
								{
								xtype:'combo',
							   store: _modelstore[i+1],
						    fieldLabel: '模版',
						    displayField: 'name',
							   valueField: 'id',
						    name: 'model'+(i+1),
						    id:'_model'+(i+1),
						    listeners:{
						 	   change:function(me, newValue, oldValue, eOpts){
						 		   var t=me.id.split('_model');
						 		   var n=t[1];
						 		   var cycid=Ext.getCmp('cycauthidcheck'+n).inputValue;
						 		   _productstore[n].getProxy().extraParams = {modelid:newValue,cycid:cycid};
						 		   _productstore[n].load();
						 		   Ext.getCmp('_product'+n).setValue('');
						 	   },afterRender:function(combo) {
								          combo.setValue('');//同时下拉框会将与name为firstValue值对应的 text显示
							       }
						    }
						},{
								xtype:'combo',
							   store: _productstore[i+1],
							   displayField: 'name',
							   valueField: 'id',
						    fieldLabel: '产品',
						    name: 'product'+(i+1),
						    id:'_product'+(i+1),
						    listeners:{
						 	   afterRender:function(combo) {
								      combo.setValue('');//同时下拉框会将与name为firstValue值对应的 text显示
							       }
						    }
						})
					}
					tempData.push(splitline);
					tempData.push(temp);
	    		}
		    	Ext.getCmp('mainModel').add(tempData);
	    	}
	    }
	});
}   



//
//{xtype:'checkbox',width:100,boxLabel:_data[i+1].name,inputValue:_data[i+1].id,checked:(i+1)==0?true:false,id:'cycauthidcheck'+(i+1)},
//{
//		xtype:'combo',
//	   store: _modelstore[i+1],
//    fieldLabel: '模版',
//    displayField: 'name',
//	   valueField: 'id',
//    name: 'model'+(i+1),
//    id:'_model'+(i+1),
//    listeners:{
// 	   change:function(me, newValue, oldValue, eOpts){
// 		   var t=me.id.split('_model');
// 		   var n=t[1];
// 		   var cycid=Ext.getCmp('cycauthidcheck'+n).inputValue;
// 		   _productstore[n].getProxy().extraParams = {modelid:newValue,cycid:cycid};
// 		   _productstore[n].load();
// 		   Ext.getCmp('_product'+n).setValue('');
// 	   },afterRender:function(combo) {
//		          combo.setValue('');//同时下拉框会将与name为firstValue值对应的 text显示
//	       }
//    }
//},{
//		xtype:'combo',
//	   store: _productstore[i+1],
//	   displayField: 'name',
//	   valueField: 'id',
//    fieldLabel: '产品',
//    name: 'product'+i+1,
//    id:'_product'+i+1,
//    listeners:{
// 	   afterRender:function(combo) {
//		      combo.setValue('');//同时下拉框会将与name为firstValue值对应的 text显示
//	       }
//    }
//}
 /**
  * 得到所有用户信息
  */
 var putScheduleStore=Ext.create('Ext.data.Store', {
	    model: 'SCHEDULEMODEL',
	    proxy: {
	    	type: 'ajax',
	    	url:rootUrl+'/psc/queryPutLog',
	    	reader : {
				type : 'json',
	            root: 'elementList',  
	            totalProperty: 'total'
			}
	    }
	});
	var  hello=function(){
		Ext.Ajax.request({
			url : rootUrl + '/common/getPutFlag',
			async : true,
			success : function(response) {
				var respTexts = Ext.JSON.decode(response.responseText);
				var respText = respTexts.data;
				if(respText[0].flag==1){
					$("#putflagid").attr('src',rootUrl+'/images/putflagtrue.png');
				}else if(respText[0].flag==0){
					$("#putflagid").attr('src',rootUrl+'/images/putflagfalse.png');
				}
			}
		}); 
	} 	
 var getseleced=function(url,msg){
	 
	 var myMask = new Ext.LoadMask(putplan, {msg:"正在执行"+msg+"中... ..."});
	 var rec =putScheduleGridPanel.getSelectionModel().getSelection();
	 var tempid=new Array();
	 if(rec){
     	 if(rec.length==0){
//          	Ext.Msg.alert('温馨提示',"请先选中一条!");
     		Ext.Msg.show({
   				title : '温馨提示',
   				msg :"请先选中一条!",
   				width : 250,
   				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
   				buttonText: { ok: '确定'}
   			});
          	return ;
     	 }else{
     		for(var i=0;i<rec.length;i++){
     			tempid.push(rec[i].data.id);
     		}               		
     	 }
     }
	 myMask.show();
	 Ext.Ajax.request({
  	    url:url,//rootUrl+'/putmangersetcontroller/deleteAdvputSet',//addOrUpdateUrL,
  	    async: true,
  	    params: {
  	    	ids: tempid.toString()
		    },
  	    success: function(response){
  	    	 myMask.hide();
  	    	var respText = Ext.JSON.decode(response.responseText);
  	    	if(respText.success=='true'){
  	    		 putScheduleStore.reload();
  	    	}else if(respText.success=='false'){
//  	  	    	Ext.Msg.alert('温馨提示',respText.data);
  	    		Ext.Msg.show({
  	   				title : '温馨提示',
  	   				msg :respText.data,
  	   				width : 250,
  	   				icon : Ext.Msg.ERROR,//INFO,ERROR,QUESTION,WARNING
  	   				buttonText: { ok: '确定'}
  	   			});
  	    	}
//  	    	 putScheduleStore.reload();
		    	
  	    }
  	});
	 return ;
 }
// var showMsgBox=function(msg){
//	 var msgBox = Ext.MessageBox.show({    
//         title:'提示',    
//         msg:msg,    
//         modal:true,    
//         width:300,    
//         progress:true   
//     });    
//    
//     var count = 6;//滚动条被刷新的次数    
//     var percentage = 0;//进度百分比    
//     var progressText = '';//进度条信息    
//    
//     var task = {   
//         run:function(){    
//             count--;    
//             //计算进度    
////             percentage = count/10;    
//             //生成进度条文字    
//             progressText = '倒计时'+count+"秒";    
//             //更新信息提示对话框    
//             msgBox.updateProgress(percentage,progressText,'正在初始化数据中');    
//             //刷新10次后关闭信息提示框    
//             if (count<0)    
//             {    
//                 Ext.TaskManager.stop(task);    
//                 msgBox.hide();    
//             }    
//         },    
//         interval:1000    
//     }    
//     Ext.TaskManager.start(task);
// }
 
 //判断是否可以投放
 var checkput=function(){
	 var timeslice=Ext.getCmp('timesetpit').getValue();
	 if($.isEmptyObject(timeslice)){
//		 Ext.Msg.alert('温馨提示','请选择时间片段');
		 Ext.Msg.show({
  				title : '温馨提示',
  				msg :'请选择时间片段',
  				width : 250,
  				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
  				buttonText: { ok: '确定'}
  			});
		 return false;
	 }
	 var cycauth=true;
	 for(var i=0;i<temptotal;i++){
	 	   var tempcheckbox=Ext.getCmp('cycauthidcheck'+i).getValue();
	 	   if(tempcheckbox){
	 		 cycauth=false;
	 		 break;
	 	   }
	  }
	 if(cycauth){
//		 Ext.Msg.alert('温馨提示','请选择认证周期');
		 Ext.Msg.show({
				title : '温馨提示',
				msg :'请选择认证周期',
				width : 250,
				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
				buttonText: { ok: '确定'}
			});
		 return false;
	 }
	 return true;
 }
 var getAllselect=function(param,url){
	var tempflag=checkput();
	if(!tempflag){
		return;
	}
	 Ext.Ajax.request({
  	    url:url,
  	    async: true,
  	    params:param,
  	    success: function(response){
  	    	var respText = Ext.JSON.decode(response.responseText);
  	    	if(respText.success=='false'){
//  	    		Ext.Msg.alert('温馨提示',respText.data);
  	    		Ext.Msg.show({
  					title : '温馨提示',
  					msg :respText.data,
  					width : 250,
  					icon : Ext.Msg.ERROR,//INFO,ERROR,QUESTION,WARNING
  					buttonText: { ok: '确定'}
  				});
  	    	}else if(respText.success=='true'){
  	  	    	$("#putflagid").attr('src',rootUrl+'/images/putflagtrue.png');
	  	  	    Ext.Msg.show({
					title : '温馨提示',
					msg :respText.data,
					width : 250,
					icon : Ext.Msg.INFO,//INFO,ERROR,QUESTION,WARNING
					buttonText: { ok: '确定'}
				});
//  	    		Ext.Msg.alert('温馨提示',respText.data);
  	    	}
  	    },failure: function(resp,opts) {   
  	    	 if(resp.timeout){
//  	    		 Ext.Msg.alert('错误',"返回超时"); 
  	    		Ext.Msg.show({
					title : '温馨提示',
					msg :"返回超时",
					width : 250,
					icon : Ext.Msg.ERROR,//INFO,ERROR,QUESTION,WARNING
					buttonText: { ok: '确定'}
				});
  	    	 } else{
//            Ext.Msg.alert('错误', respText.error);   
  	    		Ext.Msg.show({
					title : '温馨提示',
					msg :respText.error,
					width : 250,
					icon : Ext.Msg.ERROR,//INFO,ERROR,QUESTION,WARNING
					buttonText: { ok: '确定'}
				});
  	    	 }
  	    }    
  	});
	 return ;
 }
 
 var getCondition=function(){
	 var _stime = Ext.getCmp('_stime').getValue();
	    var _etime = Ext.getCmp('_etime').getValue();
	    var timeslice=Ext.getCmp('timesetpit').getValue();
	    var cycinfo=new Array();
	    for(var i=0;i<temptotal;i++){
	 	   var tempcheckbox=Ext.getCmp('cycauthidcheck'+i).getValue();
	 	   if(tempcheckbox){
	 		   var  value =Ext.getCmp('cycauthidcheck'+i).inputValue;
		       var  modelid=Ext.getCmp('_model'+i).getValue();
		       var  productid=Ext.getCmp('_product'+i).getValue();
		       cycinfo.push({cycid:value,modelid:modelid,productid:productid});
	 	   }
	    }
	   var routeid =Ext.getCmp('_routeid').getValue();
	   var busid =Ext.getCmp('_busid').getValue();
	  return {stime:_stime,etime:_etime,timeslice:timeslice,routeid:routeid,busid:busid,cycinfo:JSON.stringify(cycinfo)};
 };
var searchFun=function(){
	   var g =getCondition();
	   putScheduleStore.getProxy().extraParams = g;// {stime:_stime,etime:_etime,timeslice:timeslice,routeid:routeid,busid:busid,cycinfo:JSON.stringify(cycinfo)};
	   putScheduleStore.loadPage(1);
}
 
var putScheduleGridPanel=Ext.create('Ext.grid.Panel', {
	    store: putScheduleStore,
	    requires: [
	               'Ext.toolbar.Paging',
	               'Ext.ux.ProgressBarPager',
	               'Ext.selection.CheckboxModel'
	           ],  
	    flex:1,
	    width: 400,
	    columns: [
	        { header: '车辆名称', dataIndex: 'busname',flex: 1},
	        { header: 'AP地址', dataIndex: 'apmac',flex: 1},
	        { header: '认证周期', dataIndex: 'authcycname',flex: 1},
	        { header: '排期时间', dataIndex: 'datetime',flex: 1},
	        { header: '时间片段', dataIndex: 'timeslice',flex: 1 },
	        { header: '产品名称', dataIndex: 'proname',flex: 1 },
	        { header: '投放状态', dataIndex: 'state',flex: 1 }
	    ],bbar: {
            xtype: 'pagingtoolbar',
            pageSize:defaultPageSize,
            store: putScheduleStore,
            nextText:'下一页',
            prevText:'上一页',
            firstText:'第一页',
            lastText:'最后一页',
            refreshText:'刷新',
            displayInfo: true,
            displayMsg: '显示{0}-{1}条，共{2}条',
            plugins:Ext.create('Ext.ux.ProgressBarPager')// new Ext.ux.ProgressBarPager()
        },
	    tbar:[{
            xtype: 'radiogroup',
            fieldLabel: '操作选项',
            id:'racutoid',
            width:300,
            margin:'0 0 0 -20',
            items: [
                {boxLabel: '全部', name: 'rbauto', inputValue: 1},
                {boxLabel: '选中的部份', name: 'rbauto', inputValue: 2, checked: true}
            ]
        },'->',{
	    	xtype:'button',
	    	iconCls:'common_add',
	    	text:'全部暂停',
	    	handler: function() {
	    		 var flag=Ext.getCmp('racutoid').getValue();
	    		 if(flag.rbauto==1){
	    			 Ext.Msg.show({
	               	     title:'温馨提示',
	                	     msg: '确定要全部暂停吗?',
	                	    buttonText: {yes:'是', no:'取消'},
	                	     icon: Ext.Msg.QUESTION,
	                	     fn:function(btn,txt){
	                	    	 if(btn=="yes"){
	                	    		 var condition=getCondition();
	            	    			 getAllselect(condition,rootUrl+'/psc/pausePutLog');
	                	    	 }
	                	     }
	              		});
	    			
	    		 }else if(flag.rbauto==2){
	    			 Ext.Msg.show({
	               	     title:'温馨提示',
	                	     msg: '确定要暂停选中的部分吗?',
	                	    buttonText: {yes:'是', no:'取消'},
	                	     icon: Ext.Msg.QUESTION,
	                	     fn:function(btn,txt){
	                	    	 if(btn=="yes"){
	                	    		 getseleced(rootUrl+'/psc/pausePut',"暂停");
	                	    	 }
	                	     }
	              		});
	    			
	    		 }
	        }
	    },{
	    	xtype:'button',
	    	iconCls:'common_add',
	    	text:'恢复运行',
	    	handler: function() {
	    		 var flag=Ext.getCmp('racutoid').getValue();
	    		 if(flag.rbauto==1){
	    			 Ext.Msg.show({
	               	     title:'温馨提示',
	                	     msg: '确定要全部恢复吗?',
	                	    buttonText: {yes:'是', no:'取消'},
	                	     icon: Ext.Msg.QUESTION,
	                	     fn:function(btn,txt){
	                	    	 if(btn=="yes"){
	                	    		 var condition=getCondition();
	            	    			 getAllselect(condition,rootUrl+'/psc/normalPutLog');
	                	    	 }
	                	     }
	              		});
	    			 
	    		 }else if(flag.rbauto==2){
	    			 Ext.Msg.show({
	               	     title:'温馨提示',
	                	     msg: '确定要恢复选中的部分吗?',
	                	    buttonText: {yes:'是', no:'取消'},
	                	     icon: Ext.Msg.QUESTION,
	                	     fn:function(btn,txt){
	                	    	 if(btn=="yes"){
	                	    		 getseleced(rootUrl+'/psc/normalPut',"恢复");
	                	    	 }
	                	     }
	              		});
	    			
	    		 }
	        }
	    },{
	    	xtype:'button',
	    	iconCls:'common_add',
	    	text:'取消投放',
	    	handler: function() {
	    		 var flag=Ext.getCmp('racutoid').getValue();
	    		 if(flag.rbauto==1){
	    			 Ext.Msg.show({
	               	     	title:'温馨提示',
	                	    msg: '确定要全部取消吗?',
	                	    buttonText: {yes:'是', no:'取消'},
	                	    icon: Ext.Msg.QUESTION,
	                	    fn:function(btn,txt){
                	    	 if(btn=="yes"){
                	    		 var condition=getCondition();
            	    			 getAllselect(condition,rootUrl+'/psc/deletePutLog');
                	    	 }
	                	     }
	              		});
	    			
	    		 }else if(flag.rbauto==2){
	    			 Ext.Msg.show({
	               	     	title:'温馨提示',
	                	    msg: '确定要取消选中的部分吗?',
	                	    buttonText: {yes:'是', no:'取消'},
	                	    icon: Ext.Msg.QUESTION,
	                	    fn:function(btn,txt){
	                	    	if(btn=="yes"){
	                	    		 getseleced(rootUrl+'/psc/cancelPut',"取消");
	                	    	}
	                	     }
	              		});
	    		 }
	        }
	    },{
	    	xtype:'button',
	    	text:'查询/刷新',
	    	iconCls:'common_add',
	    	handler: function() {
	    		var tempflag=checkput();
	    		if(!tempflag){
	    			return;
	    		}
	    		searchFun();
	        }
	    },{
			xtype:'panel',
			width:'60px',
			height:'20px',
			border:false,
			html: '<div><span style="font-size: 12px;font-weight:bold;font-family: helvetica,arial,verdana,sans-serif;color: #666">指示灯&nbsp&nbsp</span><img id="putflagid" align="absmiddle" src="'+rootUrl+'/images/putflagfalse.png" alt="投放指示灯" width="20px"></div>'
			}],
	    selModel:Ext.create('Ext.selection.CheckboxModel',{mode:"MULTI",width : 222,header : "a"})
	});
	
//	putScheduleStore.load();
	/**
     * 车辆信息的面板
     */
    var win = Ext.create('widget.window', {
            title: '已被投放信息',
            header: {
                titlePosition: 2,
                titleAlign: 'center'
            },
            closable: false,
            modal: true,
            closeAction: 'hide',
            width: 650,
            minWidth: 350,
            height: 450,
            layout: {
                type: 'border'
            },
            html:'<p>未投放成功：以下是已被投放的信息</p><div id="alreadyputid"></div>'
        });
//    var stime=new Date();
//    stime.setDate(stime.getDate()-1);
//    var etime=new Date();
//    etime.setDate(stime.getDate()+1);
    var sstime=new Date();
    sstime.setDate(sstime.getDate()-1);
    
    var minetime=new Date();
    minetime.setDate(sstime.getDate()+1);
    
    var eetime=new Date();
    eetime.setMonth(sstime.getMonth()+1);
    var putplan= Ext.widget({
				xtype : 'form',
				id : 'simpleForm',
				fieldDefaults : {
					labelWidth : 100,
					labelAlign : 'right'
				},
				flex : 1,
				minWidth : 900,
				maxWidth : 1300,
				border : true,
				layout : {
					type : 'vbox',
					align : 'stretch'
				},
				items:[{
					border:false,
					layout : {
						type : 'vbox',
						align : 'stretch'
					},
					defaults : {
						xtype : 'container',
						layout : 'hbox',
						labelWidth:60,
						labelAlign : 'left'
					},
					id:'mainModel',
					items : [{
								xtype : 'container',
								
								defaults : {
									width : 200,
									
//									margin : '10 0 0 0',
									labelAlign : 'left',
//									xtype : 'datefield'
								},
								items : [ {
									labelWidth:60,
									margin : '10 10 0 20',
									fieldLabel : '开始时间',
									xtype : 'datefield',
									name : 'stime',
									id : '_stime',
									itemId : '_stime',
									minValue:sstime,
									format: 'Y-m-d',
									value:sstime,
									listeners:{
										change:function(me, newValue, oldValue, eOpts){
											
											
											 var sDate=Ext.getCmp('_stime').rawValue;
											 var _st = new Date(sDate.replace(/-/g, "/"));
											 var _st2 = new Date(sDate.replace(/-/g, "/"));
											 _st2.format('yyyy-MM-dd');
											 _st2.setDate(_st2.getDate()+1);
											 _st.setMonth(_st.getMonth()+1);
											 _st.setDate(_st.getDate()-1);
											 _st.format('yyyy-MM-dd');
											Ext.getCmp('_etime').setMaxValue(_st);
											Ext.getCmp('_etime').setMinValue(_st2);
											Ext.getCmp('_etime').setValue(_st);
											 
											 
//											 var sDate=Ext.getCmp('_stime').rawValue;
//											 var _st = new Date(sDate.replace(/-/g, "/"));
//											 var _st2 = new Date(sDate.replace(/-/g, "/"));
//											 _st2.format('MM/dd/yyyy');
//											 _st2.setDate(_st2.getDate()+1);
//											 _st.setMonth(_st.getMonth()+1);
//											 _st.setDate(_st.getDate()-1);
//											 _st.format('MM/dd/yyyy');
//											Ext.getCmp('_etime').setMaxValue(_st);
//											Ext.getCmp('_etime').setMinValue(_st2);
//											Ext.getCmp('_etime').setValue(_st);
										}
									}
								}, {
									fieldLabel : '结束时间',
									labelWidth:60,
									margin : '10 10 0 20',
									xtype : 'datefield',
									name : 'etime',
									id : '_etime',
									itemId : '_etime',
									startDateField: '_stime',
									value:eetime,
									format: 'Y-m-d',
									maxValue:eetime,
									minValue:minetime
								},{
				   		    		   store: routeStore,
					                   fieldLabel: '线路',
					                   xtype:'combo',
					                   margin : '10 10 0 20',
					                   labelWidth:30,
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
				   		    		   labelWidth:30,
				   		    		   margin : '10 10 0 20',
					                   fieldLabel: '车辆',
					                   name: 'busid',
					                   id:'_busid',
					                   listeners:{
					                	   change:function(me, newValue, oldValue, eOpts){
					                		   var tempValue=me.rawValue;
					                		   var lineId = Ext.getCmp('_routeid').getValue();
					                		   busStore.getProxy().extraParams = {lineId:lineId,busName:newValue};
//					                		   busStore.load();
					                	   },
					                	   afterRender : function(combo) {
			                		          combo.setValue('');//同时下拉框会将与name为firstValue值对应的 text显示
			                		       }
					                   }
					               } ]///////////////////////////////////////////////////////////////////////////////
						}, splitline,
								{
									xtype : 'checkboxgroup',
									fieldLabel: '时间片段',
									margin : '0 0 0 20',
									layout : {
										type : 'table',
										columns : 8
									},
									id:'timesetpit',
									defaults : {
										name : 'timeslice',
										margin:'0 20 0 0'
									},
									items : []
						},
//						splitline,
//							{
//								margin : '0 0 0 20',
//								
//								defaults : {
//									xtype:'combo',
//									labelAlign : 'left',
//									labelWidth:35,
//									margin:'0 20 0 0'
//								},
//								items:[{
//					   		    		   store: routeStore,
//						                   fieldLabel: '线路',
//						                   displayField: 'name',
//					   		    		   valueField: 'id',
//						                   name: 'routeid',
//						                   minChars: 1,
//						                   id:'_routeid',
//						                   listeners:{
//						                	   change:function(me, newValue, oldValue, eOpts){
//						                		   var tempValue=me.rawValue;
//						                		   routeStore.getProxy().extraParams = {linename:tempValue};
//						                		   Ext.getCmp('_busid').setValue('');
//						                	   },
//						                	   select:function(combo, records, eOpts){
//						                		   if(records.length>0){
//							                		   var newValue=records[0].data.id;
//							                		   busStore.getProxy().extraParams = {lineId:newValue};
//							                		   busStore.load();
//						                		   }
//						                	   },
//				                		      afterRender : function(combo) {
//				                		          combo.setValue('');//同时下拉框会将与name为firstValue值对应的 text显示
//				                		       }
//						                   }
//						               },{
//					   		    		   store: busStore,
//					   		    		   displayField: 'name',
//					   		    		   valueField: 'id',
//					   		    		   minChars: 1,
//						                   fieldLabel: '车辆',
//						                   name: 'busid',
//						                   id:'_busid',
//						                   listeners:{
//						                	   change:function(me, newValue, oldValue, eOpts){
//						                		   var tempValue=me.rawValue;
//						                		   var lineId = Ext.getCmp('_routeid').getValue();
//						                		   busStore.getProxy().extraParams = {lineId:lineId,busName:newValue};
//						                	   },
//						                	   afterRender : function(combo) {
//				                		          combo.setValue('');//同时下拉框会将与name为firstValue值对应的 text显示
//				                		       }
//						                   }
//						               }]
//							}
						]
				},putScheduleGridPanel]
			});
		getTimeSet();
		getputProduct();


/**
 * 命名空间，主函数
 */
Ext.application({
	name : 'putscheduleManger',
	launch : function() {
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
				items:putplan
			}],
			listeners:{
				afterlayout:function(){
					window.setInterval(hello,5000); 
					searchFun();
				}
			}
		});
	}
});
