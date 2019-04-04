/**
 * 产品模型
 */
Ext.define('PROCOMBOMODEL', {
        extend: 'Ext.data.Model',
        fields: ["id","proname"]
    });
/**
 * 产品模型
 */
Ext.define('COMMONMODEL', {
        extend: 'Ext.data.Model',
        fields: ["id","name"]
    });
/**
 * 查询认证周期对应的产品
 */
var groupStore = function(i){
	return Ext.create('Ext.data.Store', {
		model : 'PROCOMBOMODEL',
		id:'proStore'+i,
		proxy : {
			type : 'ajax',
			url : rootUrl + '/ppr/queryputProduct',
			reader : {
				type : 'json',
				root : 'data'
			},
			extraParams:{authid:i}
		},
		autoLoad:true
	});
}

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
	    			tempData.push({boxLabel:_data[i].name,inputValue:_data[i].id});
	    		}
	    	}
	    	Ext.getCmp('timesetpit').add(tempData);
	    }
	});
}    

var authcyc=new Array();
/**
 * 查询认证周期，并赋值相应的下拉选项
 */
var getputProduct=function(){
	Ext.Ajax.request({
	    url:rootUrl+'/common/queryAuthCyc',
	    async: false,
	    success: function(response){
	    	var respText = Ext.JSON.decode(response.responseText);
	    	if(respText&&respText.data){
	    		var _data=respText.data;
	    		var tempCompont= new Array();
	    		for(var i=0;i<_data.length;i++){
		    			var tempStore=groupStore(_data[i].id);
		    			authcyc.push(_data[i].id);
		    			var tempObj={
		    					xtype : 'combobox',
		    					maxWidth:250,
		    					store:tempStore,
		    					displayField: 'proname',
		      		    		valueField: 'id',
		    					fieldLabel :_data[i].name,
		    					name :'authcyc'+_data[i].id,
		    					id :'authcyc'+_data[i].id
		    			}
		    			tempCompont.push(tempObj);
	    		}
	    		Ext.getCmp('getProductid').add(tempCompont);
	    	}
	    }
	});
}    

Ext.define('Post', {
        extend: 'Ext.data.Model',
        idProperty: 'bid',
        fields: [{
            name: "id",
            convert: undefined
        }, {
            name: "bid",
            convert: undefined
        }, {
            name: "apid",
            convert: undefined
        },{
            name: "name",
            convert: undefined
        }, {
            name: "brand",
            convert: undefined
        }, {
            name: "model",
            convert: undefined
        }, {
            name: "color",
            convert: undefined
        }, {
            name: "corpid",
            convert: undefined
        }, {
            name: "corpname",
            convert: undefined
        }, {
            name: "routeid",
            convert: undefined
        }]
    });
/**
 * 
 */
var treeStore=Ext.create('Ext.data.TreeStore', {
    model: 'Post',
    proxy: {
        type: 'ajax',
        reader: 'json',
        url:rootUrl+'/ppr/queryBusInfo'
//        url:rootUrl+'/js/tree.json'
    }
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
var chd = function(node, check){
    node.set('checked', check);
    if (node.isNode) {
        node.eachChild(function(child) {
                    chd(child, check);
                });
    }
};
	var busInfoPanel=Ext.create('Ext.tree.Panel', {
        title: '投放车辆',
        flex:1,
        collapsible: true,
        loadMask: true,
        useArrows: true,
        rootVisible: false,
        id:'busInfoPanel',
        store: treeStore,
        animate: false,
        plugins: [{
            ptype: 'bufferedrenderer'
        }],
        columns: [{
            xtype: 'treecolumn', //this is so we know which column will show the tree
            text: '名称',
            flex: 2.5,
            sortable: true,
            dataIndex: 'name'
        },{
            text: 'APID',
            flex: 1,
            dataIndex: 'apid',
            sortable: true,
            hidden:true
        },{
            text: '路线ID',
            flex: 1,
            dataIndex: 'id',
            sortable: true,
            hidden:true
        }, {
            text: '车辆ID',
            flex: 2,
            dataIndex: 'bid',
            hidden:true
        },{
            text: '车辆品牌',
            flex: 1,
            dataIndex: 'brand',
            sortable: true,
            hidden:true
        },{
            text: '车辆类型',
            flex: 1,
            dataIndex: 'model',
            sortable: true,
            hidden:true
        },{
            text: '车辆颜色',
            flex: 1,
            dataIndex: 'model',
            sortable: true,
            hidden:true
        },{
            text: '所属公交公司ID',
            flex: 1,
            dataIndex: 'model',
            sortable: true,
            hidden:true
        },{
            text: '所属公交公司',
            flex: 1,
            dataIndex: 'model',
            sortable: true,
            hidden:true
        },{
            text: '所属路线',
            flex: 1,
            dataIndex: 'routeid',
            sortable: true,
            hidden:true
        }],
		tbar : ['->',{
				xtype:'combo',
    		   store: routeStore,
               fieldLabel: '线路',
               displayField: 'name',
               valueField: 'id',
               name: 'routeid',
               minChars: 1,
               matchFieldWidth: true,
               id:'_routeid',
               listeners:{
            	   change:function(me, newValue, oldValue, eOpts){
            		   var tempValue=me.rawValue;
            		   routeStore.getProxy().extraParams = {linename:tempValue};
            		   routeStore.load();
            	   },
            	   select:function(combo, records, eOpts){
            		   if(records.length>0){
                		   var newValue=records[0].data.id;
                		   treeStore.getProxy().extraParams = {routeId:newValue};
                		   treeStore.load();
            		   }
            	   }
            	   ,expand:function(field, eOpts){
//            		   alert('expand');
            		   routeStore.getProxy().extraParams = {linename:''};
            		   routeStore.load();
            	   }
               }
           },
				{
					xtype:'button',
					iconCls:'common_check',
					text : '投放测试',
					handler : function() {
						var params=getParams();
						if($.isEmptyObject(params)){
							return;
						}
						testPlan(params,"test");
					}
				},{
					xtype:'button',
					iconCls:'common_put',
					text : '投放',
					handler : function() {
						var params=getParams();
						if($.isEmptyObject(params)){
							return;
						}
						testPlan(params,"normal");
					}
				},{
					xtype:'panel',
					width:'60px',
					height:'20px',
					border:false,
					html: '<div><span style="font-size: 12px;font-weight:bold;font-family: helvetica,arial,verdana,sans-serif;color: #666">指示灯&nbsp&nbsp</span><img id="putflagid" align="absmiddle" src="'+rootUrl+'/images/putflagfalse.png" alt="投放指示灯" width="20px"></div>'
//				},{
//					xtype:'button',
//					iconCls:'common_put',
//					text : '定位测试',
//					handler : function() {
//						$('.x-tree-node-text').each(function(){
//							  var str1=$(this).html();//文本框的值
//							  console.info(str1);
//							  if(str1=='test'){
//								  var checked=$(this).prev().prev();
//								  checked.focus();
//							  }
//						});
						
//						浙D33223  浙D33207
//						  var checked=$("span:contains('浙D33223')").prev().prev();
//						  checked.focus();
//						var grid=Ext.getCmp('busInfoPanel');
//						grid.getView().scrollToTop();
//						grid.getView().focusCell(100, 0);
////						grid.getView().focusRow(1,100);
//						var store = grid.getStore();
//						var row = grid.getSelectionModel();
//						row.select(200);
//						var focus=grid.getView();
//						
//						var getRootNode = store.getRootNode();
						
//						var tr=$(".x-grid-row-selected").find('input');
//						tr.focus();
//						console.info(tr);
//						focus.focusRow(200);
//						var record = row.getSelection();
	
//						var user =store.getAt(200);
//						var user = Ext.create('Post', {
//						    id   : '',
//						    bid : 'Conan',
//						    apid  : 546,
//						    name: '00-03-7F-7F-87-6D',
//						    brand:'',
//						    model:'',
//						    color:'',
//						    corpid:'',
//						    corpname:'',
//						    routeid:''
//						});
//						focus.focusNode(user);
//						row.isFocused(user);
//						row.setLastFocused(user);
//						alert(row.getSelection()+"  isfocus:"+row.isFocused(user));
//							alert(1);
//							Ext.getCmp('').focus(true,1000);
//					}
				}],
		        listeners:{
		        	checkChange:function(node, checked){
		        		if (checked) {
		                    node.eachChild(function(child) {
		                                chd(child, true);
		                            });
		                } else {
		                    node.eachChild(function(child) {
		                                chd(child, false);
		                            });
		                }
		        	}
		        }
    });
	
	
	/**
     * 车辆信息的面板
     */
    var win = Ext.create('widget.window', {
            title: '已被投放信息',
            header: {
                titlePosition: 2,
                titleAlign: 'center'
            },
            closable: true,
            modal: true,
            closeAction: 'hide',
            width: 650,
            minWidth: 350,
            height: 450,
            layout: {
                type: 'border'
            },
            html:'<p>测试结果：以下是已被投放过的信息</p><div id="alreadyputid"></div>'
        });
    
    
    var testPlan = function(params,flag){
    	var myMask = new Ext.LoadMask(putplan, {msg:"正在测试... ..."});
    	myMask.show();
		Ext.Ajax.request({
		    url:rootUrl+'/ppr/putPlantest',
		    async: true,
		    params:params,
		    success: function(response){
		    	myMask.hide();
		    	var respText = Ext.JSON.decode(response.responseText);
		    	if(respText&&respText.data){
					if (respText.success == "true"&&respText.data.length>0) {
							var _data=respText.data;
							var _html="";
							for(var i=0;i<_data.length;i++){
								_html+="<span>车辆名称:"+_data[i].busname+" 认证周期:"+_data[i].authcycname+" 排期时间:"+_data[i].datetime+" 时间片段:"+_data[i].timeslice+" 产品名称:"+_data[i].proname+"</span></br>";
							}
							win.show(null,function(){
								$("#alreadyputid").html(_html);																	
							});
					} else if (respText.success == "false"||respText.data.length==0) {
							if("normal"==flag){
								putAction(params);
							}else if("test"==flag){
//								Ext.Msg.alert('温馨提示','筛选条件中没有被投放过的痕迹，</br>所有可以投放');		
								Ext.Msg.show({
          	         				title : '温馨提示',
          	         				msg :"筛选条件中没有被投放过的痕迹，</br>所有可以投放",
          	         				width : 250,
          	         				icon : Ext.Msg.INFO,//INFO,ERROR,QUESTION,WARNING
          	         				buttonText: { ok: '确定'}
          	         			});
							}
						}else{
//							Ext.Msg.alert('温馨提示',respText.data);
							Ext.Msg.show({
      	         				title : '温馨提示',
      	         				msg :respText.data,
      	         				width : 250,
      	         				icon : Ext.Msg.INFO,//INFO,ERROR,QUESTION,WARNING
      	         				buttonText: { ok: '确定'}
      	         			});
						}
					}
		    	}
		});
    }

//    var fun = function (p,total) {
//        return function () {
//        	console.info(p+'=**='+total);
//            if (p >= total) {
//                Ext.MessageBox.close();
//                Ext.Msg.alert('温馨提示', "投放成功");
//            } else {
//            	Ext.MessageBox.updateProgress(p/total, '正在投放第' + p + '块数据，共'+total+'块数据','正在投放中');
//            }
//        };
//    }
//    var p=0;
//    var totalProgress=0;
    var putAction=function(params){
        Ext.Msg.show({
   	     title:'温馨提示',
    	     msg: '确定要投放吗?',
    	     buttonText: {yes:'确定', no:'取消'},
    	     icon: Ext.Msg.QUESTION,
    	     fn:function(btn,txt){
    	    	 if(btn=="yes"){
    	    		 var timeslices=params.timeslice.split(",");
    	    	    var _stime = new Date(params.stime.replace(/-/g, "/"));//params.stime);
    	    		 var _etime = new Date(params.etime.replace(/-/g, "/"));//params.etime);
//    	    		 var days = _etime.getTime() - _stime.getTime();
//    	    		 var time = parseInt(days / (1000 * 60 * 60 * 24));
//    	    		 totalProgress= timeslices.length*(time+1);
//    	    		 p=0;
    	    		 var type='yyyy-MM-dd';
    	    			
    	    			var sauthcyc=new Object();
//    	    			var ii=0;
//    	    			==========================================
    	    			var times= new Array();
    	    			while(_stime.format(type)<_etime.format(type)){
    	    				 times.push(_stime.format(type));
    	    				 _stime.setDate(_stime.getDate()+1);
//    	    				 _params.stime=_stime.format(type);
    	    			}
    	    			 var _params = new Object();
    	    			_params.stime=times.toString();
    	    			 _params.timeslice=params.timeslice;
	    				 _params.apid=params.apid;
	    				 for(var j=0;j<authcyc.length;j++){
							var t=Ext.getCmp('authcyc'+authcyc[j]).getValue();
							if(t!=null){
								sauthcyc[authcyc[j]]=t;
							}
						 }
	    				 _params.authcyc=JSON.stringify(sauthcyc);
	    				 putFun(_params);
//	    				 ==================================================
//    	    		 while(_stime.format(type)<=_etime.format(type)){
//    	    			 _stime.setDate(_stime.getDate()+1);
//    	    			 for(var i=0;i<timeslices.length;i++){
//    	    				 var _params = new Object();
//    	    				 for(var j=0;j<authcyc.length;j++){
//								var t=Ext.getCmp('authcyc'+authcyc[j]).getValue();
//								if(t!=null){
//									sauthcyc[authcyc[j]]=t;
//								}
//							 }
//    	    				 _params.stime=_stime.format(type);
//    	    				 _params.timeslice=timeslices[i];
//    	    				 _params.apid=params.apid;
//    	    				 _params.authcyc=JSON.stringify(sauthcyc);
//    	    				 ii++;
//    	    				 _params.i=ii;
//    	    				 putFun(_params);
//    	    				 (function(tempi,k){
//	    						setTimeout(function(){
//	    							putFun(k);
//	    						},tempi*10);
//    	    				 })(ii,_params);
//    	    				 
//    	    	    	}  
//    	    		 }
    	    	 }
    	     }
   		});
    }
// 	var myMask = new Ext.LoadMask(putplan, {msg:"正在测试... ..."});
//	myMask.show();
//	Ext.Ajax.request({
//	    url:rootUrl+'/ppr/putPlantest',
//	    async: true,
//	    params:params,
//	    success: function(response){
//	    	myMask.hide();
    
//    var showMsgBox=function(msg){
//   	 var msgBox = Ext.MessageBox.show({    
//            title:'提示',    
//            msg:msg,    
//            modal:true,    
//            width:300,    
//            progress:true   
//        });    
//       
//        var count = 6;//滚动条被刷新的次数    
//        var percentage = 0;//进度百分比    
//        var progressText = '';//进度条信息    
//       
//        var task = {   
//            run:function(){    
//                count--;    
//                //计算进度    
////                percentage = count/10;    
//                //生成进度条文字    
//                progressText = '倒计时'+count+"秒";    
//                //更新信息提示对话框    
//                msgBox.updateProgress(percentage,progressText,'正在初始化数据中');    
//                //刷新10次后关闭信息提示框    
//                if (count<0)    
//                {    
//                    Ext.TaskManager.stop(task);    
//                    msgBox.hide();    
//                }    
//            },    
//            interval:1000    
//        }    
//        Ext.TaskManager.start(task);
//    }
    
    
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

	
	var putFun=function(_params){
//		var timestamp=new Date().getTime();
//	 	var myMask = new Ext.LoadMask(putplan, {msg:"时间较长，请耐心等待</br>正在投放中......"});
//		myMask.show();
		Ext.Ajax.request({
		    url: rootUrl+'/ppr/putPan3',
		    params:_params,
		    async:true,
		    success: function(response){
//		    	myMask.hide();
//		    	var timestamp2=new Date().getTime();
		    	var text = response.responseText;
		    	var _respText = Ext.JSON.decode(text);
		        if(_respText.success=='true'){
//			        	var tempFun=fun(++p,totalProgress);
//			        	tempFun();
		        	$("#putflagid").attr('src',rootUrl+'/images/putflagtrue.png');
		        		Ext.Msg.show({
	         				title : '温馨提示',
	         				msg :respText.data,
	         				width : 250,
	         				icon : Ext.Msg.INFO,//INFO,ERROR,QUESTION,WARNING
	         				buttonText: { ok: '确定'}
	         			});
//		          		Ext.Msg.alert('温馨提示', _respText.data);
		          		
//		        	 showMsgBox(_respText.data);
			        }else{
			        	Ext.Msg.show({
  	         				title : '温馨提示',
  	         				msg :respText.data,
  	         				width : 250,
  	         				icon : Ext.Msg.INFO,//INFO,ERROR,QUESTION,WARNING
  	         				buttonText: { ok: '确定'}
  	         			});
//			        	Ext.Msg.alert('温馨提示', _respText.data);
//			        	Ext.MessageBox.close();
			        }
			    }
		});
	}

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
					items : [ {
						xtype : 'container',
						margin : '0 10 0 20',
						defaults : {
							width : 250,
							labelWidth:60,
							margin : '10 0 0 0',
							labelAlign : 'left',
							xtype : 'datefield'
						},
						items : [ {
							fieldLabel : '开始时间',
							name : 'stime',
							id : '_stime',
							itemId : '_stime',
							endDateField: '_etime', // id of the end date field
							minValue : sstime,
							value:sstime,
							listeners:{
								change:function(me, newValue, oldValue, eOpts){
									 var sDate=Ext.getCmp('_stime').rawValue;
									 var _st = new Date(sDate.replace(/-/g, "/"));
									 var _st2 = new Date(sDate.replace(/-/g, "/"));
									 _st2.format('MM/dd/yyyy');
									 _st2.setDate(_st2.getDate()+1);
									 _st.setMonth(_st.getMonth()+1);
									 _st.setDate(_st.getDate()-1);
									 _st.format('MM/dd/yyyy');
									Ext.getCmp('_etime').setMaxValue(_st);
									Ext.getCmp('_etime').setMinValue(_st2);
									Ext.getCmp('_etime').setValue(_st);
								}
							}
						}, {
							fieldLabel : '结束时间',
							margin : '10 0 0 20',
							name : 'etime',
							id : '_etime',
							itemId : '_etime',
							startDateField: '_stime',
							value : eetime,
							maxValue:eetime,
							minValue:minetime
						} ]
					}, { xtype: 'displayfield',height:1, margin:'0 0 20 0', value: '<hr width=100% style="height:1px;border:none;border-top:1px dashed #177ebc;"/>' },{
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
					}, {
						fieldLabel : 'AP集',
						hidden:true,
						xtype: 'textfield',
						name : 'apid',
						id : '_apid'
					},{ xtype: 'displayfield',height:1, margin:'0 0 20 0', value: '<hr width=100% style="height:1px;border:none;border-top:1px dashed #177ebc;"/>' },{
						margin:'0 0 0 20',
						defaults : {
							labelAlign : 'left',
							labelWidth:65,
							margin:'0 20 10 0'
						},
						id:'getProductid',
						items : []
					}]
				},
				busInfoPanel]//,
//				tbar : [
//						{
//							xtype:'button',
//							text : '投放测试',
//							handler : function() {
//								var params=getParams();
//								if($.isEmptyObject(params)){
//									return;
//								}
//								testPlan(params,"test");
//							}
//						},
//						{
//							xtype:'button',
//							text : '投放',
//							handler : function() {
//								var params=getParams();
//								if($.isEmptyObject(params)){
//									return;
//								}
//								testPlan(params,"normal");
//								
//							}
//						}]
			});


var getParams=function() {
	var params=new Object();
	var busInfos=Ext.getCmp('busInfoPanel').getChecked();
	var temp=new Array();
	for(var i=0;i<busInfos.length;i++){
		if(busInfos[i].data.apid){
			temp.push(busInfos[i].data.apid);										
		}
	}
	//添加AP参数
	params.apid=temp.toString();
	Ext.getCmp('_apid').setValue(temp.toString());
	//判断时间片段是否为空
	var timesetpit=Ext.getCmp('timesetpit').getValue();
	if($.isEmptyObject(timesetpit)){
		alert("时间片段未选择");
		return;
	}
	
	params.timeslice=timesetpit.timeslice.toString();
	//判断车辆是否为空
	if(temp.length==0){
//		alert("车辆未选择");
		Ext.Msg.show({
			title : '温馨提示',
			msg :"车辆未选择",
			width : 250,
			icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
			buttonText: { ok: '确定'}
		});
		return;
	}
	var flag=true;
	var sauthcyc = new Object();
	for(var i=0;i<authcyc.length;i++){
		var t=Ext.getCmp('authcyc'+authcyc[i]).getValue();
		if(t!=null){
			sauthcyc[authcyc[i]]=t;										
		}
		if(t){
			flag=false;
		}
	}
	if(flag){
//		alert("周期产品未选择");
		Ext.Msg.show({
				title : '温馨提示',
				msg :"周期产品未选择",
				width : 250,
				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
				buttonText: { ok: '确定'}
			});
		return;
	}
	params.authcyc=JSON.stringify(sauthcyc);
	var stime=Ext.getCmp('_stime').rawValue;
	var etime=Ext.getCmp('_etime').rawValue;	
	var _stime = new Date(stime);
	var _etime = new Date(etime);
	var type='yyyy-MM-dd';
	params.stime=_stime.format(type);
	params.etime=_etime.format(type);
	if(!stime){
//		alert("开始时间未选择");
		Ext.Msg.show({
			title : '温馨提示',
			msg :"开始时间未选择",
			width : 250,
			icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
			buttonText: { ok: '确定'}
		});
	}
	
	if(!etime){
//		alert("结束时间未选择");
		Ext.Msg.show({
			title : '温馨提示',
			msg :"结束时间未选择",
			width : 250,
			icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
			buttonText: { ok: '确定'}
		});
	}
	return params;
}


getTimeSet();
getputProduct();


/**
 * 命名空间，主函数
 */
Ext.application({
	name : 'putplan',
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
			}],listeners:{
				afterlayout:function(){
					window.setInterval(hello,5000); 
				}
			}
		});
	}
});
