//----------------------首页---------------------------------------------------------
//var myMask = new Ext.LoadMask(Ext.getBody(),{msg:"正在进行中 请稍后......"});
var showRouteEcharts=null;
var showAdvEcharts=null;
/**
 * 菜单模型
 */
Ext.define('Ext.menu.model', {
    extend: 'Ext.data.Model',
    fields: [{
        name: 'name',
        type: 'string'
    },{
        name: 'counts',
        type: 'string'
    }]
}); 

var advTop10Store=Ext.create('Ext.data.Store', {
    storeId:'advTop10StoreId',
    model: Ext.menu.model,
    proxy: {
    	type: 'ajax',
    	url:rootUrl+'/homeController/getTop10ClickCountByAdv',
        reader: {
            type: 'json',
            root: 'data'
        }
    },
	autoLoad:true
});

var routeTop10Store=Ext.create('Ext.data.Store', {
    storeId:'routeTop10StoreId',
    model: Ext.menu.model,
    proxy: {
    	type: 'ajax',
    	url:rootUrl+'/homeController/getTop10ClickCountByRoute',
        reader: {
            type: 'json',
            root: 'data'
        }
    },
	autoLoad:true
});

var clickAdvgrid=Ext.create('Ext.grid.Panel', {
    title: '广告点击量TOP10',
    store: advTop10Store,
    columns: [
        { text: '广告名称',  dataIndex: 'name',flex:1,align:'center'},
        { text:'点击量',dataIndex:'counts',flex:1,align:'center'}
    ],
    height: 320,
    width: 300
});

var clickRoutegrid=Ext.create('Ext.grid.Panel', {
    title: '线路点击量TOP10',
    margin:'15 0 0 0',
    store: routeTop10Store,
    columns: [
        { text: '线路名称',  dataIndex: 'name',flex:1,align:'center'},
        { text:'点击量',dataIndex:'counts',flex:1,align:'center'}
    ],
    height: 320,
    width: 300
});
var getTotalInfo=function(){
	Ext.Ajax.request({
		url : rootUrl + '/homeController/getTotalInfo',
		async : false,
		success : function(response) {
			var respTexts = Ext.JSON.decode(response.responseText);
			var respText = respTexts.data;
			$("#bustotalid").html("当前车辆总数为"+respText[1].totalbus+"辆");
			$("#linetotalid").html("当前线路总数为"+respText[2].totalroute+"条");
			$("#advtotalid").html("当前广告总数有"+respText[0].totaladv+"支");
			$("#usertotalid").html("历史用户上网总数有"+respText[3].totaluser+"户");
		}
	});
}
var totalPanel=Ext.create('Ext.panel.Panel', {
	region: 'north',
    height:150,
    border:false,
    frame:false,
    layout: {
        type: 'column'
    },
    defaults:{height: 150,columnWidth:.25,padding:'0 5 0 5'},
    items: [{
	            html: '<div style="text-align:center;padding:10px;"><a><i class="fa fa-bus fa-5x" style="color: rgb(26, 179, 148);"></i></a></div><div style="text-align:center;"><h3 id="bustotalid">公交线路总数:10条</h3></div>'
	        },{
	            html: '<div style="text-align:center;padding:10px;"><a><i class="fa fa-road fa-5x" style="color: rgb(26, 179, 148);"></i></a></div><div style="text-align:center;"><h3 id="linetotalid">公交线路总数:10条</h3></div>'
	        },{
	            html: '<div style="text-align:center;padding:10px;"><a><i class="fa fa-newspaper-o fa-5x" style="color: rgb(26, 179, 148);"></i></a></div><div style="text-align:center;"><h3 id="advtotalid">公交线路总数:10条</h3></div>'
	        },{
	            html: '<div style="text-align:center;padding:10px;"><a><i class="fa fa-television fa-5x" style="color: rgb(26, 179, 148);"></i></a></div><div style="text-align:center;"><h3 id="usertotalid">公交线路总数:10条</h3></div>'
	        }],
    listeners:{
    	afterlayout:function(){
    		
			getTotalInfo();
			
    	}
    }
});
var topPanel=Ext.create('Ext.panel.Panel', {
	region: 'west',
    width:310,
    border:false,
    frame:false,
    height:670,
    layout: {
        type:'vbox',
        padding:'5',
        align:'center'
    },
    items:[clickAdvgrid,clickRoutegrid]
});

var getTop10showAdv=function(){
	Ext.Ajax.request({
		url : rootUrl + '/homeController/getTop10ShowCountByAdv',
		async : true,
		success : function(response) {
			var respTexts = Ext.JSON.decode(response.responseText);
			var respText = respTexts.data;
			if(respText){
				var name=new Array();
				var value=new Array();
				for(var i=0;i<respText.length;i++){
					name.push(respText[i].name);
					value.push(+(respText[i].counts));
				}
				showAdvEcharts.setOption(getOption('广告展示量TOP10','展示量',name,value));
			}
		}
	});
}
var getTop10showRoute=function(){
	var result=null;
	Ext.Ajax.request({
		url : rootUrl + '/homeController/getTop10ShowCountByRoute',
		async : true,
		success : function(response) {
			var respTexts = Ext.JSON.decode(response.responseText);
			var respText = respTexts.data;
			if(respText){
				var name=new Array();
				var value=new Array();
				for(var i=0;i<respText.length;i++){
					name.push(respText[i].name);
					value.push(+(respText[i].counts));
				}
				showRouteEcharts.setOption(getOption('公交线路广告展示量TOP10','展示量',name,value));
			}
		}
	});
	return result;
}
var showRouteechartsPanel=Ext.create('Ext.panel.Panel', {
//    frame:false,
    border:false,
    frame:false,
	title:'TOP10路线广告展示量',
	margin:'15 0 0 0',
    layout:'fit',
    items:[{
    	xtype:'panel',
	    height:282,
    	html:'<div style="background-color:write;height:310px;" id="showRouteId">暂无数据</div>'
    }]
});
var showAdvechartsPanel=Ext.create('Ext.panel.Panel', {
//    frame:false, 
    border:false,
    frame:false,
	title:'TOP10广告展示量',
    layout:'fit',
    items:[{
    	xtype:'panel',
	    height:282,
    	html:'<div style="background-color:write;height:310px;" id="showAdvId">暂无数据</div>'
    }]
});
var echartsPanel=Ext.create('Ext.panel.Panel', {
    border:false,
    frame:false,
	region: 'center',
	flex:3,
    layout: {
        type:'vbox',
        padding:'5',
        align:'stretch'
    },
    items:[showAdvechartsPanel,showRouteechartsPanel]
});
var testPanel=Ext.create('Ext.panel.Panel', {
    border:false,
    frame:false,
    layout: {
        type:'hbox',
        align:'stretch'
    },
    items: [topPanel,echartsPanel]
});
var mainPanel=Ext.create('Ext.panel.Panel', {
    border:false,
    frame:false,
    padding:'5 20 0 0',
//    maxWidth:1300,
    overflowY:'auto',
    maxWidth:1300,
    minWidth:900,
    flex:1,
    layout: {
        type:'vbox',
        align:'stretch'
    },
//    html:'<div style="width:1000px;height:7000px;background-color:red;"></div>'
    items: [totalPanel,testPanel]
});
//var mainPanel=Ext.create('Ext.panel.Panel', {
//    margin:'15 20 0 0',
//    layout:'fit',
//    items:[{
//    	xtype:'panel',
//    	layout: {
//            type:'vbox',
//            align:'stretch'
//        },
//      items: [totalPanel,testPanel]
//    }],
//
//});
//-------------------------------------------
//-------------------------首页结束-------------------------------------------------------

var _tree = Ext.define('KitchenSink.view.tree.CheckTree',
		{
			extend : 'Ext.tree.Panel',
			requires : [ 'Ext.data.TreeStore' ],
			xtype : 'check-tree',
			region : 'west',
			collapsible : true,
			rootVisible : false,
			useArrows : true,
			frame : false,
			id:'treeId',
			title : '目录菜单',
			width : 250,
			height : 300,
			listeners : {
				itemclick : function(view, record, item, index, e,
						eOpts) {
					if (record && record.data && record.data.text) {
						if(record.raw.description.indexOf('XXXX')!=-1){
							return;
						}
						var nodeText = record.data.text;
						var tabPanel = Ext.getCmp('tabWindow');
						var tabBar = tabPanel.getTabBar();
						var tabIndex;
						for (var i = 0; i < tabBar.items.length; i++) {
							if (tabBar.items.get(i).getText() === nodeText) {
								tabIndex = i;
							}
						}
						if (Ext.isEmpty(tabIndex)) {
							tabPanel.add({
										title : record.data.text,
										bodyPadding : 10,
										html : '<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="'
												+ rootUrl
												+ record.raw.description
												+ '"> </iframe>', // record.data.text,
										closable : true
									});
							tabIndex = tabPanel.items.length - 1;
						}
						tabPanel.setActiveTab(tabIndex);
					}
				}
			},
			initComponent : function() {
				Ext.apply(this, {
					store : new Ext.data.TreeStore({
						proxy : {
							type : 'ajax',
							url : rootUrl + '/menuController/menu'
						}
					})
				});
				this.callParent();
			},

			onCheckedNodesClick : function() {
				var records = this.getView().getChecked(), names = [];
				console.info(records);
				Ext.Array.each(records, function(rec) {
					names.push(rec.get('text'));
				});
				console.info(records);
			}
		})

Ext.apply(Ext.form.VTypes, {
	password : function(val, field) {
		if (field.initialPassField) {
			var pwd = Ext.getCmp(field.initialPassField);
			return (val == pwd.getValue());
		}
		return true;
	},
	passwordText : '两次输入的密码不一致!'
});

var simple = Ext.widget({
			xtype : 'form',
			layout : 'form',
			id : 'simpleForm',
			fieldDefaults : {
				labelWidth : 100,
				labelAlign : 'right'
			},
			padding : '20 20 0 0',
			border : false,
			defaultType : 'textfield',
			items : [
					{
						xtype : 'displayfield',
						fieldLabel : '用户名',
						name : 'username',
						id : '_username',
						margin : '20 0 0 0',
						labelStyle : 'margin-top:20px;',
						fieldStyle : 'margin-top:20px'
					},
					{
						fieldLabel : '原密码',
						name : 'oldpword',
						id : '_oldpword',
						inputType : 'password',
						emptyText : '原密码',
						margin : '20 0 0 0',
						labelStyle : 'margin-top:20px;',
						fieldStyle : 'margin-top:20px',
						validateOnBlur : true,
						invalidText : '密码不正确',
						beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ],
						validator : function() {
							var username = Ext.getCmp('_username').getValue();
							if (username == null || username == '') {
								return;
							}
							var result = null;
							Ext.Ajax.request({
								url : rootUrl + '/checkpword',
								async : false,
								params : {
									username : username,
									passWord : this.getValue()
								},
								success : function(response) {
									var respTexts = Ext.JSON
											.decode(response.responseText);
									var respText = respTexts.data;
									if (!respText) {
										result = '密码不正确';
									} else if (respText) {
										result = true;
									}
								}
							});
							return result;
						}
					},
					{
						fieldLabel : '新密码',
						name : 'newpword',
						id : '_newpword',
						inputType : 'password',
						beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ],
						emptyText : '新密码',
						margin : '20 0 0 0',
						labelStyle : 'margin-top:20px;',
						fieldStyle : 'margin-top:20px'
					},
					{
						fieldLabel : '再输入新密码',
						name : 'newpwords',
						id : '_newpwords',
						vtype : 'password',
						beforeLabelTextTpl : [ '<span style="color:red;font-weight:bold" data-qtip="必填选项">*</span>' ],
						initialPassField : '_newpword',
						inputType : 'password',
						emptyText : '再输入新密码',
						margin : '20 0 0 0',
						labelStyle : 'margin-top:20px;',
						fieldStyle : 'margin-top:20px'
					} ],

			buttons : [ {
				text : '提交',
				handler : function() {
					var oldpword = Ext.getCmp('_oldpword').getValue();
					if (!oldpword) {
						Ext.Msg.alert('温馨提示', "旧密码未填写");
						return;
					}
					var newpword = Ext.getCmp('_newpword').getValue();
					if (!newpword) {
						Ext.Msg.alert('温馨提示', "新密码未填写");
						return;
					}
					var newpwords = Ext.getCmp('_newpwords').getValue();
					if (!newpwords) {
						Ext.Msg.alert('温馨提示', "二次新密码未填写");
						return;
					}
					if(newpword!=newpwords){
						Ext.Msg.alert('温馨提示', "二次密码输入不一致");
						return;
					}
					this.up('form').getForm().submit({
						clientValidation : true,
						waitMsg : '请稍候',
						waitTitle : '正在更新',
						url : rootUrl + '/menuController/updatepword',
						success : function(form, action) {
							if (action && action.result) {
								if (action.result) {
									// 成功后更新表格并关闭win
									Ext.Msg.alert('温馨提示', "更新密码成功");
									win.close();
									window.location = rootUrl + '/login';
								} else if (!action.result) {
									win.close();
									Ext.Msg.alert('温馨提示', "更新密码失败");
								}
							} else {
								win.close();
								alert("上传失败！.")
							}
						},
						failure : function(form, action) {

						}
					});
				}
			}, {
				text : '取消',
				handler : function() {
					this.up('form').getForm().reset();
					win.close();
				}
			} ]
		});

/**
 * 车辆信息的面板
 */
var win = Ext.create('widget.window', {
	title : '修改密码',
	header : {
		titlePosition : 2,
		titleAlign : 'center'
	},
	closable : true,
	modal: true,
	closeAction : 'hide',
	width : 550,
	minWidth : 350,
	height : 350,
	layout : {
		type : 'fit'
	},
	items : [ simple ]
});

/**
 * 车辆信息的面板
 */
var clientwin = Ext.create('widget.window', {
	title : '客户端下载',
	header : {
		titlePosition : 2,
		titleAlign : 'center'
	},
	closable : true,
	closeAction : 'hide',
	width : 300,
	minWidth : 350,
	height : 350,
	modal: true,
	layout : {
		type : 'fit'
	},
	html:'<div style="margin:20px 0px 0px 40px">android客户端下载:<a href="" id="anrDoloadId">点击下载</a></div><div style="margin:20px 0px 0px 40px">扫描二维码下载:<div><div style="margin:20px 0px 0px 20px"><img src="images/client.png" height="150px"></img></div>'
});
var showClentWin=function(){
	clientwin.show(null,function(){
		Ext.Ajax.request({
			url : rootUrl + '/mobile/getupdateinfo',
			async : true,
			params:{
				 clienttype : 'android'
 		    },
			success : function(response) {
				var respTexts = Ext.JSON.decode(response.responseText);
				var respText = respTexts.data;
				$("#anrDoloadId").attr("href",rootUrl+"/"+respText[0].URL);
			}
		});
	});
}
var resetPassword = function() {
	win.show(null, function() {
		var userName = getUserName();
		Ext.getCmp("_username").setValue(userName);
	});
}
var getOption=function(text,data,xAxis,series){
	return {
		    tooltip : {
		        trigger: 'axis'
		    },
		    legend: {
		        data:[data]
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            magicType : {show: true, type: ['line', 'bar']},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true,
		    xAxis : [
		        {
		            type : 'category',
		            data : xAxis
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value'
		        }
		    ],
		    series : [
		        {
		            name:data,
		            type:'bar',
		            barWidth:30,
		            itemStyle: {
		                normal: {
		                    color:'rgb(21,127,204)'
		                },
		                emphasis: {
		                    color:'rgb(173,210,237)'
		                }
		            },
		            data:series,
		            markPoint : {
		                data : [
		                    {type : 'max', name: '最大值'},
		                    {type : 'min', name: '最小值'}
		                ]
		            },
		            markLine : {
		                data : [
		                    {type : 'average', name: '平均值'}
		                ]
		            }
		        }
		    ]
		};
}
//var flag=true;
                    
Ext.application({
			name : 'HelloExt',
			launch : function() {
				Ext.create('Ext.container.Viewport',
							{
								layout : 'border',
								overflowX : 'auto',
								overflowY : 'auto',
								items : [{
										region : 'north',
										html : '<div style="height:70px;background-color:#177ecb;background-image:url('
												+ rootUrl
												+ '/images/left.jpg);background-repeat:repeat-y;padding-top:10px;">'
												
												+ '<div  style="height:60px;margin-left:45px;background-image:url('
												+ rootUrl
												+ '/images/logo.png);background-repeat:no-repeat;border-bottom:2px solid #288FD4;">'
												+ '<div style="float:right;margin-right:10px;padding-top:20px;"><a style="color:white;"">当前用户:'
												+ userName
												+ '</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="'
												+ rootUrl
														+ '/login" style="color:white;">退出</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="resetPassword();" style="color:white;">重置密码</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a style="color:white;margin-right:30px;" onclick="showClentWin();">客户端下载'
												+ '</a>'
//												+ '<img id="putflagid" src="'+rootUrl+'/images/putflagfalse.png" alt="投放指示灯" width="30px" style="margin-right:20px;margin-top:-10px;">'
												+ '</div>'
//												+'<div  style="float:right;margin-right:40px;padding-top:20px;background-image:url('
//												+ rootUrl
//												+ '/images/putflagfalse.png);background-repeat:repeat-y;padding-top:10px;">'
//												+'</div>'
												+ '</div>'
												+ '</div>',
											border : false
										}, _tree, {
											id : 'tabWindow',
											region : 'center',
											border:false,
											frame:false,
											layout:'fit',
											
											xtype : 'tabpanel', // TabPanel本身没有title属性
											activeTab : 0, // 配置默认显示的激活面板
											items : [{
									            title: '首页',
									            closable: false,
												layout:'fit',
												 border:false,
//												 html:'------'
												items:[{
													xtype:'panel',
													layout:{
														type:'hbox',
														align:'stretch',
														pack:'center'
													},
													items:mainPanel
												}]

									        }]
										} ],
										listeners:{
							            	afterlayout:function(){
							            			showRouteEcharts = echarts.init(document.getElementById('showRouteId'));
							            			showAdvEcharts = echarts.init(document.getElementById('showAdvId'));
							            			getTop10showRoute();
							            			getTop10showAdv();
									    	}
							            }
								});
			}
		});