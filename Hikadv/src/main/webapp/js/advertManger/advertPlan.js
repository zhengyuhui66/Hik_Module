/**
 * 路线临时存储
 */
var mainRoadLine = [];
/**
 * 暂停投放值
 */
var pauseValueYES=0;
var pauseValueNO=1;
var myMask = new Ext.LoadMask(Ext.getBody(),{msg:"正在进行中 请稍后......"});
/**
 * 初始化加载所有的路线
 */

Ext.Ajax.request({
			url : rootUrl + '/advertPlanController/queryRoadInfo',
			async : false,
			success : function(response) {
				var result = Ext.JSON.decode(response.responseText);
				if (result) {
					for (var i = 0; i < result.data.length; i++) {
						var temp = new Object();
						var val = result.data[i];
						temp.html = '<div style="width:200px;height:100px;text-align:center;cursor:pointer;background-image:url('+rootUrl+'/images/busImage.jpg);" class="lineClass"><h3 style="float:left;margin-top:40px;margin-left:5px;">'
								+ val
								+ '</h3></div><div><input type="checkbox" name="allRoute" style="width:20px;height:20px;" value="'
								+ val + '"/></div>';
						mainRoadLine.push(temp);
					}
				}
			}
		});

Ext.define('tUser', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'name',
		type : 'string'
	} ]
});
var tUserStore = Ext.create('Ext.data.Store', {
	model : 'tUser'
});
/**
 * 车辆信息面板
 */
var allRoutes = new Array();
var setDefaultVal = function() {
	var stime = new Date();
	var str = stime.format("yyyy-MM-dd hh:mm:ss");
	var etime = new Date();
	etime.setDate(etime.getDate() + 1);
	var etr = etime.format("yyyy-MM-dd hh:mm:ss");
	$("#tpstime").val(str);
	$("#tfstime").val(str);
	$("#tpetime").val(etr);
	$("#tfetime").val(etr);
	Ext.getCmp('fcheckbox').setValue(false);
	Ext.getCmp('tcheckbox').setValue(false);
	preModelmodelgrid.getStore().removeAll();
	afterModelmodelgrid.getStore().removeAll();
	Ext.getCmp('preModelmodelgrid').setValue('请选择...');
	Ext.getCmp('afterModelmodelgrid').setValue('请选择...');

}

/**
 * 确认投放按钮
 */
var ybutton = Ext.create('Ext.Button',
				{
					text : '确认投放',
					id : 'aButton',
					iconCls:'common_sure',
					tag : 'Y',
					handler : function(_button) {
						var rec = Ext.getCmp("busInfoid").getSelectionModel()
								.getSelection();
						var params = new Array();
						if (rec.length == 0) {
//							Ext.Msg.alert('温馨提示', "请先选中一条!");
							Ext.Msg.show({
                				title : '提示',
                				msg : "请先选中一条!",
                				width : 250,
                				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
                				buttonText: { ok: '确定' }
                			});
							return;
						} else if (rec.length > 0) {
							for (var i = 0; i < rec.length; i++) {
								params.push(rec[i].data.id);
							}
						}
						
						var rcheckbox = Ext.getCmp('rcheckbox').getValue();
						var pcheckbox = Ext.getCmp('pcheckbox').getValue();
						if (rcheckbox) {
							var stime = $("#stime").val();
							var etime = $("#etime").val();
							if (!etime) {
//								Ext.Msg.alert('温馨提示', "请先选择结束时间!");
								Ext.Msg.show({
	                				title : '提示',
	                				msg : "请先选择结束时间!",
	                				width : 250,
	                				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
	                				buttonText: { ok: '确定' }
	                			});
								return;
							}
							var modelId = Ext.getCmp('modelId').getValue();
							if (!modelId) {
//								Ext.Msg.alert('温馨提示', "请选择模版!");
								Ext.Msg.show({
	                				title : '提示',
	                				msg : "请选择模版!",
	                				width : 250,
	                				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
	                				buttonText: { ok: '确定' }
	                			});
								return;
							}
							var advertId = Ext.getCmp('advertId').getValue();
							if (!advertId) {
//								Ext.Msg.alert('温馨提示', "请选择广告位!");
								Ext.Msg.show({
	                				title : '提示',
	                				msg : "请选择广告位!",
	                				width : 250,
	                				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
	                				buttonText: { ok: '确定' }
	                			});
								return;
							}
							var modelmodeId = Ext.getCmp('modelmodeId')
									.getValue();
							if (!modelmodeId) {
//								Ext.Msg.alert('温馨提示', "请选择广告!");
								Ext.Msg.show({
	                				title : '提示',
	                				msg : "请选择广告!",
	                				width : 250,
	                				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
	                				buttonText: { ok: '确定' }
	                			});
								return;
							}
							
							myMask.show();
							Ext.Ajax.request({
										url : rootUrl+ '/advertPlanController/updateAdvertPlan',
										async : true,
										params : {
											stime : stime,
											etime : etime,
											modelId : modelId,
											modelmodeId : modelmodeId,
											advertId : advertId,
											busId : params.toString()
										},
										success : function(response) {
											var respText = Ext.JSON.decode(response.responseText);
											if (respText && respText.data) {
												myMask.hide();
//												Ext.Msg.alert('温馨提示', "投放了"+ respText.data + "车辆");
												Ext.Msg.show({
					                				title : '提示',
					                				msg : "投放了"+ respText.data + "车辆",
					                				width : 250,
					                				icon : Ext.Msg.INFO,//INFO,ERROR,QUESTION,WARNING
					                				buttonText: { ok: '确定' }
					                			});
												myStore.reload();
											}
										}
									});
						}

						if (pcheckbox) {
							var pstime = $("#pstime").val();
							var petime = $("#petime").val();
							if (!petime) {
//								Ext.Msg.alert('温馨提示', "请先选择结束时间!");
								Ext.Msg.show({
	                				title : '提示',
	                				msg : "请先选择结束时间!",
	                				width : 250,
	                				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
	                				buttonText: { ok: '确定' }
	                			});
								return;
							}
							var pmodelId = Ext.getCmp('pmodelId').getValue();
							if (!pmodelId) {
//								Ext.Msg.alert('温馨提示', "请选择模版!");
								Ext.Msg.show({
	                				title : '提示',
	                				msg : "请选择模版!",
	                				width : 250,
	                				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
	                				buttonText: { ok: '确定' }
	                			});
								return;
							}
							var padvertId = Ext.getCmp('padvertId').getValue();
							if (!padvertId) {
//								Ext.Msg.alert('温馨提示', "请选择广告位!");
								Ext.Msg.show({
	                				title : '提示',
	                				msg : "请选择广告位!",
	                				width : 250,
	                				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
	                				buttonText: { ok: '确定' }
	                			});
								return;
							}
							var pmodelmodeId = Ext.getCmp('pmodelmodeId').getValue();
							if (!pmodelmodeId) {
//								Ext.Msg.alert('温馨提示', "请选择广告!");
								Ext.Msg.show({
	                				title : '提示',
	                				msg : "请选择广告!",
	                				width : 250,
	                				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
	                				buttonText: { ok: '确定' }
	                			});
								return;
							}
							myMask.show();
							Ext.Ajax.request({
										url : rootUrl+ '/advertPlanController/updateLoginedAdvertPlan',
										async : true,
										params : {
											stime : pstime,
											etime : petime,
											modelId : pmodelId,
											modelmodeId : pmodelmodeId,
											advertId : padvertId,
											busId : params.toString()
										},
										success : function(response) {
											myMask.hide();
											var respText = Ext.JSON.decode(response.responseText);
											if (respText && respText.data) {
//												Ext.Msg.alert('温馨提示', "登陆后投放了"+ respText.data + "车辆");
												Ext.Msg.show({
					                				title : '提示',
					                				msg :"登陆后投放了"+ respText.data + "车辆",
					                				width : 250,
					                				icon : Ext.Msg.INFO,//INFO,ERROR,QUESTION,WARNING
					                				buttonText: { ok: '确定' }
					                			});
												myStore.reload();
											}
										}
									});
						}

						if (!pcheckbox && !rcheckbox) {
//							Ext.Msg.alert('温馨提示', "请选择一个要投放的点");
							Ext.Msg.show({
                				title : '提示',
                				msg :"请选择一个要投放的点",
                				width : 250,
                				icon : Ext.Msg.WARNING,//INFO,ERROR,QUESTION,WARNING
                				buttonText: { ok: '确定' }
                			});
						}
					}
				});

///**
// * 单车辆的历史记录模型
// */
Ext.define('BusAdvertHistory', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'name',
		type : 'string'
	}, {
		name : 'modename',
		type : 'string'
	}, {
		name : 'stime',
		type : 'string'
	}, {
		name : 'etime',
		type : 'string'
	}, {
		name : 'advertid',
		type : 'string'
	}, {
		name : 'advertUrl',
		type : 'string'
	}, {
		name : 'advertName',
		type : 'string'
	}, {
		name : 'state',
		type : 'string'
	}

	]
});
/**
 * 单车辆的历史记录STORE
 */
var busAdvertHistoryStore = Ext.create('Ext.data.Store', {
	model : 'BusAdvertHistory',
	proxy : {
		type : 'ajax',
		url : rootUrl + '/advertPlanController/getBusAdvertPlanHistory',
		reader : {
			type : 'json',
			root : 'data'
		}
	}
});

/**
 * 单车辆登陆后的历史记录STORE
 */
var busLoginedAdvertHistoryStore = Ext.create('Ext.data.Store', {
	model : 'BusAdvertHistory',
	proxy : {
		type : 'ajax',
		url : rootUrl + '/advertPlanController/getBusLoginedAdvertPlanHistory',
		reader : {
			type : 'json',
			root : 'data'
		}
	}
});

/**
 * 车辆模型
 */
Ext.define('User', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',
		type : 'string'
	}, {
		name : 'code',
		type : 'string'
	}, {
		name : 'color',
		type : 'int'
	}, {
		name : 'brand',
		type : 'string'
	}, {
		name : 'name',
		type : 'string'
	}, {
		name : 'pname',
		type : 'string'
	}, {
		name : 'modelid',
		type : 'string'
	}, {
		name : 'pmodelid',
		type : 'string'
	}, {
		name : 'pause',
		type : 'string'
	}, {
		name : 'ppause',
		type : 'string'
	} ]
});
/**
 * 车辆信息STORE
 */
var myStore = Ext.create('Ext.data.Store', {
	model : 'User',
	proxy : {
		type : 'ajax',
		url : rootUrl + '/advertPlanController/queryBusInfo',
		reader : {
			type : 'json',
			root : 'data'
		}
	}
});

// ------------------------11111111111111111---------------------------------------------
/**
 * 模型模型
 */
Ext.define('uUser', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'modelid',
		type : 'string'
	}, {
		name : 'name',
		type : 'string'
	} ]
});
/**
 * 模型信息Store
 */
var ModelStore = Ext.create('Ext.data.Store', {
	model : 'uUser',
	proxy : {
		type : 'ajax',
		url : rootUrl + '/common/queryAllModel',
		reader : {
			type : 'json',
			root : 'data'
		}
	},
	autoLoad : true
});

// -----------------------------------------------------------------------

/**
 * 模型模块模型
 */
Ext.define('subuUser', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'modelmodeid',
		type : 'string'
	}, {
		name : 'name',
		type : 'string'
	}, {
		name : 'advertid',
		type : 'string'
	} ]
});
/**
 * 模型模块Store
 */
var ModelModeStore = Ext.create('Ext.data.Store', {
	model : 'subuUser',
	proxy : {
		type : 'ajax',
		url : rootUrl + '/advertPlanController/queryModelModeById',
		reader : {
			type : 'json',
			root : 'data'
		}
	}
});
// ------------------------22222222222222222---------------------------------------------
/**
 * 模型信息Store
 */
var PModelStore = Ext.create('Ext.data.Store', {
	model : 'uUser',
	proxy : {
		type : 'ajax',
		url : rootUrl + '/common/queryAllModel',
		reader : {
			type : 'json',
			root : 'data'
		}
	},
	autoLoad : true
});

// -----------------------------------------------------------------------
/**
 * 模型模块Store
 */
var PModelModeStore = Ext.create('Ext.data.Store', {
	model : 'subuUser',
	proxy : {
		type : 'ajax',
		url : rootUrl + '/advertPlanController/queryModelModeById',
		reader : {
			type : 'json',
			root : 'data'
		}
	}
});
// -----------------------------------------------------------------

/**
 * 广告模型
 */
Ext.define('advertUser', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'advertid',
		type : 'string'
	}, {
		name : 'name',
		type : 'string'
	} ]
});
/**
 * 广告Store
 */
var AdvertStore = Ext.create('Ext.data.Store', {
	model : 'advertUser',
	proxy : {
		type : 'ajax',
		url : rootUrl + '/common/queryAllAdvert',
		reader : {
			type : 'json',
			root : 'data'
		}
	},
	autoLoad : true
});
var pbbr = Ext.create(
				'Ext.toolbar.Toolbar',
				{
					width : 500,
					items : [
							{
								xtype : 'checkboxfield',
								boxLabel : '认证成功界面:',
								name : 'topping',
								inputValue : '1',
								id : 'pcheckbox'
							},
							{
								xtype : 'panel',
								frame : false,
								border : false,
								html : '开始时间:<input class="Wdate" style="height:24px;width:150px;" id="pstime" type="text" onClick="WdatePicker({dateFmt:\'yyyy-MM-dd HH:mm:ss\',maxDate:\'#F{$dp.$D(\\\'petime\\\')}\'})"/>'
							},
							{
								xtype : 'panel',
								frame : false,
								border : false,
								html : '结束时间:<input class="Wdate" style="height:24px;width:150px;" id="petime" type="text" onClick="WdatePicker({dateFmt:\'yyyy-MM-dd HH:mm:ss\',minDate:\'#F{$dp.$D(\\\'pstime\\\')}\'})"/>'
							},
							{
								xtype : 'combo',
								store : PModelStore,
								displayField : 'name',
								valueField : 'modelid', // 真实的字段
								labelWidth : 30,
								width : 150,
								fieldLabel : '模版',
								selectOnTab : false,
								name : 'pmodeName',
								id : 'pmodelId',
								emptyText : '请选择...',
								onReplicate : function() {
									this.getStore().clearFilter();
								},
								listeners : {
									select : function(combo, records, eOpts) {
										if (records) {
											var data = records[0].getData();
											if (!data || !data.modelid) {
												return;
											}
											PModelModeStore.getProxy().extraParams = {
												modelid : data.modelid
											};
											PModelModeStore.reload();
											Ext.getCmp('pmodelmodeId')
													.setValue('请选择');
										}
									}
								}
							}, {
								xtype : 'combo',
								store : PModelModeStore,
								displayField : 'name',
								valueField : 'modelmodeid', // 真实的字段
								labelWidth : 50,
								width : 150,
								fieldLabel : '广告位',
								selectOnTab : false,
								name : 'pmodelmodeName',
								id : 'pmodelmodeId',
								emptyText : '请选择...',
								onReplicate : function() {
									this.getStore().clearFilter();
								}
							}, {
								xtype : 'combo',
								store : AdvertStore,
								displayField : 'name',
								valueField : 'advertid', // 真实的字段
								labelWidth : 30,
								width : 150,
								fieldLabel : '广告',
								selectOnTab : false,
								name : 'padvertName',
								id : 'padvertId',
								emptyText : '请选择...',
								onReplicate : function() {
									this.getStore().clearFilter();
								}
							} ]
				});

			var rbbr = Ext.create('Ext.toolbar.Toolbar',
				{
					width : 500,
					items : [
							{
								xtype : 'checkboxfield',
								boxLabel : '请求认证首页:',
								name : 'topping',
								inputValue : '2',
								id : 'rcheckbox'
							},
							{
								xtype : 'panel',
								frame : false,
								border : false,
								html : '开始时间:<input class="Wdate" style="height:24px;width:150px;" id="stime" type="text" onClick="WdatePicker({dateFmt:\'yyyy-MM-dd HH:mm:ss\',maxDate:\'#F{$dp.$D(\\\'etime\\\')}\'})"/>'
							},
							{
								xtype : 'panel',
								frame : false,
								border : false,
								html : '结束时间:<input class="Wdate" style="height:24px;width:150px;" id="etime" type="text" onClick="WdatePicker({dateFmt:\'yyyy-MM-dd HH:mm:ss\',minDate:\'#F{$dp.$D(\\\'stime\\\')}\'})"/>'
							},
							{
								xtype : 'combo',
								store : ModelStore,
								displayField : 'name',
								valueField : 'modelid', // 真实的字段
								labelWidth : 30,
								width : 150,
								fieldLabel : '模版',
								selectOnTab : false,
								name : 'modeName',
								id : 'modelId',
								emptyText : '请选择...',
								onReplicate : function() {
									this.getStore().clearFilter();
								},
								listeners : {
									select : function(combo, records, eOpts) {
										if (records) {
											var data = records[0].getData();
											if (!data || !data.modelid) {
												return;
											}
											ModelModeStore.getProxy().extraParams = {
												modelid : data.modelid
											};
											ModelModeStore.reload();
											Ext.getCmp('modelmodeId').setValue(
													'请选择');
										}
									}
								}
							}, {
								xtype : 'combo',
								store : ModelModeStore,
								displayField : 'name',
								valueField : 'modelmodeid', // 真实的字段
								labelWidth : 50,
								width : 150,
								fieldLabel : '广告位',
								selectOnTab : false,
								name : 'modelmodeName',
								id : 'modelmodeId',
								emptyText : '请选择...',
								onReplicate : function() {
									this.getStore().clearFilter();
								}
							}, {
								xtype : 'combo',
								store : AdvertStore,
								displayField : 'name',
								valueField : 'advertid', // 真实的字段
								labelWidth : 30,
								width : 150,
								fieldLabel : '广告',
								selectOnTab : false,
								name : 'advertName',
								id : 'advertId',
								emptyText : '请选择...',
								onReplicate : function() {
									this.getStore().clearFilter();
								}
							} ]
				});
			
			
			
			
	var pausePutAdvert = function(params,value,url) {
		myMask.show();
		var result=0
			Ext.Ajax.request({
				url : url,
				async : false,
				params : {
					busids : params.toString(),
					value:value
				},
				success : function(response) {
					var respText = Ext.JSON.decode(response.responseText);
					if (respText && respText.data) {
						myMask.hide();
						result+=+(respText.data);
						if(value==1){
//							Ext.Msg.alert('温馨提示', "取消暂停投放了"+result+"个广告位");
							Ext.Msg.show({
                				title : '提示',
                				msg :"取消暂停投放了"+result+"个广告位",
                				width : 250,
                				icon : Ext.Msg.INFO,//INFO,ERROR,QUESTION,WARNING
                				buttonText: { ok: '确定' }
                			});
							
						}else if(value==0){
//							Ext.Msg.alert('温馨提示', "暂停投放了"+result+"个广告位");
							Ext.Msg.show({
                				title : '提示',
                				msg : "暂停投放了"+result+"个广告位",
                				width : 250,
                				icon : Ext.Msg.INFO,//INFO,ERROR,QUESTION,WARNING
                				buttonText: { ok: '确定' }
                			});
						}
//						myStore.reload();
					}
				}
			});
		return result;
			
	}
			
/**
 * 暂停按钮
 */
var pauseAction = Ext.create('Ext.Action', {
	iconCls:'common_pause',
    text: '暂停投放',
    handler: function(widget, event) {
    	var rec = Ext.getCmp("busInfoid").getSelectionModel().getSelection();
		var params = new Array();
		if (rec.length == 0) {
			Ext.Msg.alert('温馨提示', "请先选中一条!");
			return;
		} else if (rec.length > 0) {
			for (var i = 0; i < rec.length; i++) {
				params.push(rec[i].data.id);
			}
			var rcheckbox = Ext.getCmp('rcheckbox').getValue();
			var pcheckbox = Ext.getCmp('pcheckbox').getValue();
			if(!pcheckbox&&!rcheckbox){
//				Ext.Msg.alert('温馨提示', "请选择左上角【请求认证首页】/【认证成功界面】</br>来选择取消投放的界面");
				return;
			}
			var result=0;
			if(rcheckbox){   
				result+=pausePutAdvert(params.toString(), pauseValueYES,rootUrl + '/advertPlanController/pausePutAdvert');
			}
			if(pcheckbox){
				result+=pausePutAdvert(params.toString(), pauseValueYES,rootUrl + '/advertPlanController/pausePutLoginedAdvert');
			}
			Ext.Msg.alert('温馨提示', "暂停投放了"+result+"个广告位");
			myStore.reload();
		}
    }
});

/**
 * 取消暂停
 */
var regainAction = Ext.create('Ext.Action', {
	iconCls:'common_regain',
    text: '取消暂停',
    handler: function(widget, event) {
    	var rec = Ext.getCmp("busInfoid").getSelectionModel().getSelection();
		var params = new Array();
		if (rec.length == 0) {
			Ext.Msg.alert('温馨提示', "请先选中一条!");
			return;
		} else if (rec.length > 0) {
			for (var i = 0; i < rec.length; i++){
				params.push(rec[i].data.id);
			}
			var rcheckbox = Ext.getCmp('rcheckbox').getValue();
			var pcheckbox = Ext.getCmp('pcheckbox').getValue();
			if(!pcheckbox&&!rcheckbox){
				Ext.Msg.alert('温馨提示', "请选择左上角【请求认证首页】/【认证成功界面】</br>来选择取消投放的界面");
				return;
			}
			var result=0;
			if(rcheckbox){   
				result+=pausePutAdvert(params.toString(), pauseValueNO,rootUrl + '/advertPlanController/pausePutAdvert');
			}
			if(pcheckbox){
				result+=pausePutAdvert(params.toString(), pauseValueNO,rootUrl + '/advertPlanController/pausePutLoginedAdvert');
			}
			Ext.Msg.alert('温馨提示', "取消暂停了"+result+"个广告位");
			myStore.reload();
		}
    }
});


var cancelPutAdvert = function(params, url) {
	var result=0;
	myMask.show();
		Ext.Ajax.request({
			url : url,
			async : false,
			params : {
				busids : params.toString()
			},
			success : function(response) {
				myMask.hide();
				var respText = Ext.JSON.decode(response.responseText);
				if (respText && respText.data) {
					result+=+(respText.data);		
					Ext.Msg.alert('温馨提示', "取消了"+result+"个广告位");
				}
			}
		});
		return result;
}
/**
 * 取消按钮
 */
var cancelAction = Ext.create('Ext.Action', {
	iconCls : 'common_cancel',
	text : '取消投放',
	handler : function(widget, event) {
		var rec = Ext.getCmp("busInfoid").getSelectionModel().getSelection();
		var params = new Array();
		if (rec.length == 0) {
			Ext.Msg.alert('温馨提示', "请先选中一条!");
			return;
		} else if (rec.length > 0) {
			for (var i = 0; i < rec.length; i++) {
				params.push(rec[i].data.id);
			}
			var rcheckbox = Ext.getCmp('rcheckbox').getValue();
			var pcheckbox = Ext.getCmp('pcheckbox').getValue();
			if(!pcheckbox&&!rcheckbox){
				Ext.Msg.alert('温馨提示', "请选择左上角【请求认证首页】/【认证成功界面】</br>来选择取消投放的界面");
			}
			Ext.Msg.show({
				title : '温馨提示',
				msg : '取消投放不可恢复,</br>确定要取消投放吗?',
				buttonText: {yes:'保存', no:'取消'},
				icon : Ext.Msg.QUESTION,
				fn : function(btn, txt){
						if (btn == "yes") {
							var result=0;
							if(rcheckbox){   
								result+=cancelPutAdvert(params.toString(), rootUrl + '/advertPlanController/cancelPutAdvert');
							}
							if(pcheckbox){
								result+=cancelPutAdvert(params.toString(), rootUrl + '/advertPlanController/cancelPutLoginedAdvert');
							}
							Ext.Msg.alert('温馨提示', "取消了投放了"+ result+"个广告位");
							myStore.reload();
						}
					}
				});
		}
	}
});
/**
 * 车辆信息GRID
 */
var busInfo = Ext.create(
				'Ext.grid.Panel',
				{
					store : myStore,
					flex : 3,
					id : "busInfoid",
					selModel : Ext.create('Ext.selection.CheckboxModel', {
						mode : "SIMPLE"
					}),
					columns : [
							Ext.create('Ext.grid.RowNumberer'),
							{
								text : "车辆ID",
								hidden : true,
								dataIndex : 'id'
							},
							{
								text : "车牌号",
								flex : 1,
								align:'center',
								sortable : true,
								dataIndex : 'code'
							},
							{
								text : "颜色",
								hidden : true,
								dataIndex : 'color'
							},
							{
								text : "车型号",
								hidden : true,
								dataIndex : 'brand'
							},
							{
								text : "请求认证首页",
								flex : 1,
								align:'center',
								dataIndex : 'name',
								renderer : function(val,rec) {
									var value=val;
									if (!val) {
										value='暂无模版';
									}
									var pause=rec.record.data.pause;
									var result='<div class="';
									if(!pause||pause==1){
										result+='normal_flag" title="正常运行"><span style="margin-left:15px;">'+value+'</span></div>';									
									}else{
										result+='pause_flag" title="暂停投放"><span style="margin-left:15px;">'+value+'</span></div>';	
									}
									return result;
								}
							},{
								text : "模版ID",
								hidden : true,
								dataIndex : 'modelid'
							},{
								text : "是否暂停",
								hidden : true,
								dataIndex : 'pause',
								renderer : function(val) {
									if (!val||val==1) {
										return '不暂停'
									}
									return '暂停';
								}
							},
							{
								text : '查看投放记录',
								width : 90,
								menuDisabled : true,
								xtype : 'actioncolumn',
								flex : 1,
								tooltip : '查看投放记录',
								align : 'center',
								iconCls:'common_history',
								handler : function(grid, rowIndex, colIndex,
										actionItem, event, record, row) {
									if (record && record.data) {
										var rdata = record.data;
										advertBugwin.show(this,function() {
											busAdvertHistoryStore.getProxy().extraParams = {
												busid : rdata.id
											};
											busAdvertHistoryStore.reload();
										});
									}
								},
								isDisabled : function(view, rowIdx, colIdx,
										item, record) {
									return false;
								}
							},
							{
								text : '查看广告效果',
								width : 90,
								menuDisabled : true,
								xtype : 'actioncolumn',
								flex : 1,
								tooltip : '广告效果',
								align : 'center',
								iconCls:'common_view',
								handler : function(grid, rowIndex, colIndex,
										actionItem, event, record, row) {

									if (record && record.data) {
										var rdata = record.data;

										advertShowwin.show(this,function() {
															Ext.Ajax.request({
																		url : rootUrl+ '/getAdvertController/queryByBusId',
																		async : false,
																		params : {
																			busId : rdata.id
																		},
																		success : function(response) {
																			var respText = Ext.JSON.decode(response.responseText);
																			var url = respText.data[0]+ "?";
																			for (var i = 1; i < respText.data.length; i++) {
																				for ( var n in respText.data[i]) {
																					url += n+ "='"+ respText.data[i][n]+ "'&";
																				}
																			}
																			url = url.substring(0,url.length - 1);
																			$("#advertviewids").attr('src',url);
																		}
																	});
														});
									}
								},
								isDisabled : function(view, rowIdx, colIdx,
										item, record) {
									return false;
								}
							},
							{
								text : "广告ID",
								flex : 1,
								sortable : true,
								hidden : true,
								dataIndex : 'advert_id'
							},
							// --------------------------登陆成功后的信息--------------------------------
							{
								text : "认证成功界面",
								flex : 1,
								sortable : true,
								dataIndex : 'pname',
								renderer : function(val,rec) {
									var value=val;
									if (!val) {
										value='暂无模版';
									}
									var pause=rec.record.data.ppause;
									var result='<div class="';
									if(!pause||pause==1){
										result+='normal_flag" title="正常运行"><span style="margin-left:15px;">'+value+'</span></div>';									
									}else{
										result+='pause_flag" title="暂停投放"><span style="margin-left:15px;">'+value+'</span></div>';	
									}
									return result;
								} 
							},{
								text : "登陆后模版ID",
								flex : 1,
								sortable : true,
								hidden : true,
								dataIndex : 'pmodelid'
							},{
								text : "是否暂停",
								flex : 1,
								sortable : true,
								hidden : true,
								dataIndex : 'ppause',
								renderer : function(val) {
									if (!val||val==1) {
										return '不暂停'
									}
									return '暂停';
								}
							},{
								text : '查看投放记录',
								width : 90,
								menuDisabled : true,
								xtype : 'actioncolumn',
								flex : 1,
								tooltip : '查看投放记录',
								align : 'center',
								iconCls:'common_history',
								handler : function(grid, rowIndex, colIndex,
										actionItem, event, record, row) {
									if (record && record.data) {
										var rdata = record.data;
										advertLoginedBugwin.show(this,
											function() {
												busLoginedAdvertHistoryStore.getProxy().extraParams = {
													busid : rdata.id
												};
												busLoginedAdvertHistoryStore.reload();
											});
									}
								},
								isDisabled : function(view, rowIdx, colIdx,
										item, record) {
									return false;
								}
							},
							{
								text : '查看广告效果',
								width : 90,
								menuDisabled : true,
								xtype : 'actioncolumn',
								flex : 1,
								tooltip : '广告效果',
								align : 'center',
								iconCls:'common_view',
								handler : function(grid, rowIndex, colIndex,
										actionItem, event, record, row) {

									if (record && record.data) {
										var rdata = record.data;
										advertShowwin.show(this,function() {
											Ext.Ajax.request({
													url : rootUrl+ '/getAdvertController/queryByLoginedBusId',
														async : false,
														params : {
															busId : rdata.id
														},
														success : function(response) {
															var respText = Ext.JSON.decode(response.responseText);
															var url = respText.data[0]+ "?";
															for (var i = 1; i < respText.data.length; i++) {
																for ( var n in respText.data[i]) {
																	url += n+ "='"+ respText.data[i][n]+ "'&";
																}
															}
															url = url.substring(0,url.length - 1);
															$("#advertviewids").attr('src',url);
														}
													});
											});
									}
								},
								isDisabled : function(view, rowIdx, colIdx,
										item, record) {
									return false;
								}
							}, {
								text : "登陆后广告ID",
								flex : 1,
								sortable : true,
								hidden : true,
								dataIndex : 'advert_id'
							} ],
					dockedItems : [ rbbr, pbbr ],
					bbar : [{xtype:'panel',
						   border:false,
						   html:'<span style="color:red;">注:</span>'+
							   '<div class="normal_flag" style="display:inline;">'+
									'<span style="margin-left:15px;">标志为正常动行</span>'+
								'</div>'+
								'<div class="pause_flag" style="display:inline;margin-left:30px;">'+
									'<span style="margin-left:15px;">标志为暂停运行</span>'+
								'</div>'
								},'->', ybutton,pauseAction,regainAction,cancelAction],
					columnLines : true
				});


//			

/**
 * 车辆信息GRID
 */
var busAdvertHistorygrid = Ext.create('Ext.grid.Panel', {
	store : busAdvertHistoryStore,
	selModel : Ext.create('Ext.selection.CheckboxModel', {
		mode : "SIMPLE"
	}),
	columns : [ Ext.create('Ext.grid.RowNumberer'), {
		text : "模版名称",
		flex : 1,
		align:'center',
		sortable : true,
		dataIndex : 'name'
	}, {
		text : "广告位名称",
		flex : 1,
		align:'center',
		sortable : true,
		dataIndex : 'modename'
	}, {
		text : "投放生效时间",
		flex : 1,
		align:'center',
		sortable : true,
		dataIndex : 'stime'
	}, {
		text : "投放失效时间",
		flex : 1,
		align:'center',
		sortable : true,
		dataIndex : 'etime'
	}, {
		text : "广告ID",
		flex : 1,
		hidden:true,
		align:'center',
		sortable : true,
		dataIndex : 'advertid'
	}, {
		text : "广告链接地址",
		flex : 1,
		align:'center',
		sortable : true,
		dataIndex : 'advertUrl'
	}, {
		text : "广告名称",
		flex : 1,
		align:'center',
		sortable : true,
		dataIndex : 'advertName'
	}, {
		text : "是否生效",
		align:'center',
		width:80,
		sortable : true,
		dataIndex : 'state',
		renderer : function(val) {
			if (val==1) {
				return '生效'
			}else if(val==0){
				return "失效"
			}else{
				return val;				
			}
		}
	} ],

	columnLines : true
});

/**
 * 车辆信息GRID
 */
var busLoginedAdvertHistorygrid = Ext.create('Ext.grid.Panel', {
	store : busLoginedAdvertHistoryStore,
	selModel : Ext.create('Ext.selection.CheckboxModel', {
		mode : "SIMPLE"
	}),
	columns : [ Ext.create('Ext.grid.RowNumberer'), {
		text : "模版名称",
		flex : 1,
		sortable : true,
		align:'center',
		dataIndex : 'name'
	}, {
		text : "广告位名称",
		flex : 1,
		sortable : true,
		align:'center',
		dataIndex : 'modename'
	}, {
		text : "投放生效时间",
		flex : 1,
		sortable : true,
		align:'center',
		dataIndex : 'stime'
	}, {
		text : "投放失效时间",
		flex : 1,
		sortable : true,
		align:'center',
		dataIndex : 'etime'
	}, {
		text : "广告ID",
		flex : 1,
		sortable : true,
		hidden:true,
		align:'center',
		dataIndex : 'advertid'
	}, {
		text : "广告链接地址",
		flex : 1,
		sortable : true,
		align:'center',
		dataIndex : 'advertUrl'
	}, {
		text : "广告名称",
		flex : 1,
		sortable : true,
		align:'center',
		dataIndex : 'advertName'
	}, {
		text : "是否生效",
		width:80,
		sortable : true,
		align:'center',
		dataIndex : 'state',
		renderer : function(val) {
			if (val==1) {
				return '生效'
			}else if(val==0){
				return "失效"
			}else{
				return val;				
			}
		}
	} ],

	columnLines : true
});
/**
 * 车辆信息的面板
 */
var win = Ext.create('widget.window', {
	title : '车辆信息',
	header : {
		titlePosition : 2,
		titleAlign : 'center'
	},
	closable : true,
	  modal: true,
	closeAction : 'hide',
	width : 1050,
	minWidth : 350,
	height : 450,
	layout : {
		type : 'border'
	},
	items : {
		region : 'center',
		xtype : 'panel',
		layout : {
			type : 'vbox',
			align : 'stretch'
		},
		items : [ busInfo ]
	}
});

var panel = Ext.create('Ext.Panel', {
	id : 'main-panel',
	flex:1,
	minWidth:900,
	maxWidth:1300,
	layout : {
		type : 'hbox',
		pack : 'center'
	},
	overflowY : "auto",
	items : {
		layout : {
			type : 'table',
			columns : 5
		},
		border : false,
		defaults : {
			margin : '30 15 30 0'
		},
		items : mainRoadLine,
		tbar : [{
					xtype : 'checkboxfield',
					boxLabel : '全选',
					name : 'topping',
					listeners : {
						change : function(panel, newValue, oldValue, eOpts) {
							console.info('newValue:' + newValue + '  oldValue:'
									+ oldValue);
							$("input:checkbox[name='allRoute']").attr(
									"checked", newValue);
						}
					}
				},{
					xtype : 'button',
					text : '线路投放',
					iconCls:'common_put',
					handler : function(){
						allRoutes = [];
						$("input[name='allRoute']:checked").each(
								function() {
									var allRoute = new Object();
									allRoute.name = $(this).val();
									allRoutes.push(allRoute);
								});
						if(allRoutes.length==0){
							Ext.Msg.alert('温馨提示', "请先选择一条线跑!");
							return;
						}
						
						putAllRountwin.show(this, function() {
							setDefaultVal();
					
							tUserStore.loadData(allRoutes, false);
						});
					}
				}]
	},listeners : {
		afterlayout : function() {
			/**
			 * 委托点击触发事件
			 */

			
	
			$('body').delegate('.lineClass', 'click', function() {
				var tempText = $(this).text();
				win.show(this,function() {
					var stime = new Date();
					var str = stime.format("yyyy-MM-dd hh:mm:ss");
					var etime = new Date();
					etime.setMonth(etime.getMonth() + 1);
					var etr = etime.format("yyyy-MM-dd hh:mm:ss");
					$("#stime").val(str);
					$("#etime").val(etr);
					$("#pstime").val(str);
					$("#petime").val(etr);

					myStore.getProxy().extraParams = {
						lineId : tempText
					};
					myStore.reload();
				});
			});

		}
	}
});
/**
 * 车辆广告列表的面板
 */
var advertBugwin = Ext.create('widget.window', {
	title : '车辆广告列表',
	header : {
		titlePosition : 2,
		titleAlign : 'center'
	},
	closable : true,
	  modal: true,
	closeAction : 'hide',
	width : 1150,
	minWidth : 350,
	height : 550,
	layout : {
		type : 'fit'
	},
	items : busAdvertHistorygrid
});
/**
 * 车辆广告列表的面板
 */
var advertLoginedBugwin = Ext.create('widget.window', {
	title : '车辆广告列表',
	header : {
		titlePosition : 2,
		titleAlign : 'center'
	},
	closable : true,
	  modal: true,
	closeAction : 'hide',
	width : 1150,
	minWidth : 350,
	height : 550,
	layout : {
		type : 'fit'
	},
	items : busLoginedAdvertHistorygrid
});

/**
 * 查看广告效果
 */
var advertShowwin = Ext.create(
				'widget.window',
				{
					title : '当前广告效果',
					header : {
						titlePosition : 2,
						titleAlign : 'center'
					},
					closable : true,
					  modal: true,
					closeAction : 'hide',
					width : 850,
					minWidth : 350,
					height : 550,
					layout : {
						type : 'fit'
					},
					html : '<div  style="width:100%;height:100%;"><iframe id="advertviewids" style="width:100%;height:100%;">暂无信息</iframe></div>'
				});

// -----------------------------------------------------------------------------
/**
 * 广告位展示
 */
var preModelmodelgrid = Ext.create(
				'Ext.grid.Panel',
				{
					store : ModelModeStore,
					plugins : [ Ext.create('Ext.grid.plugin.CellEditing', {
						clicksToEdit : 1
					}) ],
					overflowY : 'auto',
					columns : [ {
						text : "广告位名称",
						flex : 1,
						align:'center',
						sortable : false,
						dataIndex : 'name'
					}, {
						text : "广告位ID",
						dataIndex : 'modelmodeid',
						hidden : true
					}, {
						text : "广告",
						flex : 1,
						sortable : false,
						align:'center',
						dataIndex : 'advertid',
						editor : new Ext.form.field.ComboBox({
							typeAhead : true,
							triggerAction : 'all',
							selectOnTab : true,
							emptyText : '请选择',
							displayField : 'name',
							valueField : 'name',
							store : AdvertStore,
							lazyRender : true,
							listClass : 'x-combo-list-small'
						})
					} ],
					columnLines : true,
					height : 180,
					tbar : [
							{
								xtype : 'checkboxfield',
								boxLabel : '请求认证首页:',
								name : 'topping',
								inputValue : '3',
								id : 'tcheckbox'
							},
							{
								xtype : 'panel',
								frame : false,
								border : false,
								html : '开始时间:<input class="Wdate" style="height:24px;width:150px;" id="tpstime" type="text" onClick="WdatePicker({dateFmt:\'yyyy-MM-dd HH:mm:ss\',maxDate:\'#F{$dp.$D(\\\'tpetime\\\')}\'})"/>'
							},
							{
								xtype : 'panel',
								frame : false,
								border : false,
								html : '结束时间:<input class="Wdate" style="height:24px;width:150px;" id="tpetime" type="text" onClick="WdatePicker({dateFmt:\'yyyy-MM-dd HH:mm:ss\',minDate:\'#F{$dp.$D(\\\'tpstime\\\')}\'})"/>'
							},
							{
								xtype : 'combo',
								store : ModelStore,
								displayField : 'name',
								valueField : 'modelid', // 真实的字段
								labelWidth : 30,
								width : 150,
								fieldLabel : '模版',
								selectOnTab : false,
								name : 'preModelmodelgrid',
								id : 'preModelmodelgrid',
								emptyText : '请选择...',
								onReplicate : function() {
									this.getStore().clearFilter();
								},
								listeners : {
									select : function(combo, records, eOpts) {
										if (records) {
											var data = records[0].getData();
											if (!data || !data.modelid) {
												return;
											}
											ModelModeStore.getProxy().extraParams = {
												modelid : data.modelid
											};
											ModelModeStore.reload();
										}
									}
								}
							} ]
				});

/**
 * 广告位展示
 */
var afterModelmodelgrid = Ext.create(
				'Ext.grid.Panel',
				{
					store : PModelModeStore,
					plugins : [ Ext.create('Ext.grid.plugin.CellEditing', {
						clicksToEdit : 1
					}) ],
					columns : [ {
						text : "广告位名称",
						flex : 1,
						align:'center',
						dataIndex : 'name'
					}, {
						text : "广告位ID",
						dataIndex : 'modelmodeid',
						hidden : true
					}, {
						text : "广告",
						flex : 1,
						align:'center',
						dataIndex : 'advertid',
						editor : new Ext.form.field.ComboBox({
							typeAhead : true,
							triggerAction : 'all',
							selectOnTab : true,
							emptyText : '请选择',
							displayField : 'name',
							valueField : 'name',
							store : AdvertStore,
							lazyRender : true,
							listClass : 'x-combo-list-small'
						})
					} ],
					columnLines : true,
					height : 180,
					tbar : [
							{
								xtype : 'checkboxfield',
								boxLabel : '认证成功界面:',
								name : 'topping',
								inputValue : '4',
								id : 'fcheckbox'
							},
							{
								xtype : 'panel',
								frame : false,
								border : false,
								html : '开始时间:<input class="Wdate" style="height:24px;width:150px;" id="tfstime" type="text" onClick="WdatePicker({dateFmt:\'yyyy-MM-dd HH:mm:ss\',maxDate:\'#F{$dp.$D(\\\'tfetime\\\')}\'})"/>'
							},
							{
								xtype : 'panel',
								frame : false,
								border : false,
								html : '结束时间:<input class="Wdate" style="height:24px;width:150px;" id="tfetime" type="text" onClick="WdatePicker({dateFmt:\'yyyy-MM-dd HH:mm:ss\',minDate:\'#F{$dp.$D(\\\'tfstime\\\')}\'})"/>'
							},
							{
								xtype : 'combo',
								store : PModelStore,
								displayField : 'name',
								valueField : 'modelid', // 真实的字段
								labelWidth : 30,
								width : 150,
								fieldLabel : '模版',
								selectOnTab : false,
								name : 'afterModelmodelgrid',
								id : 'afterModelmodelgrid',
								emptyText : '请选择...',
								onReplicate : function() {
									this.getStore().clearFilter();
								},
								listeners : {
									select : function(combo, records, eOpts) {
										if (records) {
											var data = records[0].getData();
											if (!data || !data.modelid) {
												return;
											}
											PModelModeStore.getProxy().extraParams = {
												modelid : data.modelid
											};
											PModelModeStore.reload();
										}
									}
								}
							} ]
				});

var modelList = Ext.create('Ext.grid.Panel', {
	flex : 1,
	id : 'modelListid',
	store : tUserStore,
	columns : [ {
		text : '投放线路列表',
		dataIndex : 'name'
	} ],
	height : 660
});

var fun = function (p,total) {
    return function () {
        if (p == total) {
            Ext.MessageBox.close();
            Ext.Msg.alert('温馨提示', "投放成功");
        } else {
            Ext.MessageBox.updateProgress(p/total, '正在投放第' + p + '条线路，共'+total+'条线路');
        }
    };
}
var p=0;
var totalProgress=0;
var putRouteInfo = function(url, stime, etime, modelId, modeladvertid, routeIds){
	Ext.Ajax.request({
		url : url,
		async : true,
		params : {
			stime : stime,
			etime : etime,
			routeId : JSON.stringify(routeIds),
			modelId : modelId,
			modeAdvertid : JSON.stringify(modeladvertid)
		},
		success : function(response) {
			var respText = Ext.JSON.decode(response.responseText);
			if (respText && respText.data!=null) {
				var temp=fun(++p,totalProgress);
				temp();
			} else {
				Ext.Msg.alert('温馨提示', "投放了失败");
			}
			putAllRountwin.close();
		}
	});
}

/**
 * 新增按钮
 */
var addAction = Ext.create('Ext.Action', {
	iconCls:'common_sure',
    text: '确认投放',
    handler: function(widget, event) {
    	p=0;
    	
    	allRoutes = [];
		$("input[name='allRoute']:checked").each(
				function() {
					var allRoute = new Object();
					allRoute.name = $(this).val();
					allRoutes.push(allRoute);
				});
		if(allRoutes.length==0){
			Ext.Msg.alert('温馨提示', "请先选择一条线跑!");
			return;
		}
		var tcheckbox = Ext.getCmp('tcheckbox').getValue();
		var fcheckbox = Ext.getCmp('fcheckbox').getValue();
		if (!tcheckbox && !fcheckbox) {
			Ext.Msg.alert('温馨提示', "请选择所需的投放");
			return;
		}
		
		if(tcheckbox&&fcheckbox){
			totalProgress=allRoutes.length*2;
		}else{
			totalProgress=allRoutes.length;
		}
		Ext.MessageBox.progress("请等待", "数据处理进度...");
		if (tcheckbox) {
			var preModelid = Ext.getCmp("preModelmodelgrid").getValue();
			if (!preModelid|| preModelid == '请选择...') {
				Ext.Msg.alert('温馨提示', "请选择模版");
				return;
			}
			var tpstime = $("#tpstime").val();
			if (!tpstime) {
				Ext.Msg.alert('温馨提示',"未选择开始时间!");
				return;
			}
			var tpetime = $("#tpetime").val();
			if (!tpetime) {
				Ext.Msg.alert('温馨提示',"未选择结束时间!");
				return;
			}
			var store = preModelmodelgrid.getStore();
			var count = store.getCount();
			var tempObj = new Object();
			for (var i = 0; i < count; i++) {
				var record = store.getAt(i);
				tempObj[record.data.modelmodeid] = record.data.advertid;
			}

			var _url = rootUrl+'/advertPlanController/updateRouteAdvertPlan';
			for(var i=0;i<allRoutes.length;i++){
				putRouteInfo(_url,tpstime,tpetime,preModelid,tempObj,allRoutes[i]);
			}
		}

		if (fcheckbox) {
			var aftModelid = Ext.getCmp("afterModelmodelgrid").getValue();
			if (!aftModelid|| aftModelid == '请选择...') {
				Ext.Msg.alert('温馨提示', "请选择模版!");
			}
			var tfstime = $("#tfstime").val();
			if (!tfstime) {
				Ext.Msg.alert('温馨提示',"未选择开始时间!");
			}

			var tfetime = $("#tfetime").val();
			if (!tfetime) {
				Ext.Msg.alert('温馨提示',"未选择结束时间!");
			}

			var _store = afterModelmodelgrid.getStore();
			var _count = _store.getCount();
			var _tempObj = new Object();
			for (var i = 0; i < _count; i++) {
				var record = _store.getAt(i);
				_tempObj[record.data.modelmodeid] = record.data.advertid;
			}
			var _url = rootUrl+ '/advertPlanController/updateLoginedRouteAdvertPlan';
			for(var i=0;i<allRoutes.length;i++){
				putRouteInfo(_url, tfstime,tfetime, aftModelid,_tempObj, allRoutes[i]);
			}
		}
    }
});



var queryBusidsByRoutenames=function(routenames){
	var result=[];
	Ext.Ajax.request({
		url : rootUrl+ '/advertPlanController/queryBusidByRouteIds',
		async : false,
		params : {
			routeIds:routenames
		},
		success : function(response) {
			var respText = Ext.JSON.decode(response.responseText);
			if (respText && respText.data) {
				result= respText.data;
			}
		}
	});
	return result;
}

var getBusIdsChecked=function(){
	
	var allroutes = [];
	$("input[name='allRoute']:checked").each(function() {
		allroutes.push($(this).val());
	});
	if(allroutes.length==0){
		Ext.Msg.alert('温馨提示', "请先选择一条线路");
	}
	var busids = queryBusidsByRoutenames(allroutes.toString());
	return busids;
}

var pauseOrRegain = function(yesorno) {
	var tcheckbox = Ext.getCmp('tcheckbox').getValue();
	var fcheckbox = Ext.getCmp('fcheckbox').getValue();
	if (!tcheckbox && !fcheckbox) {
		Ext.Msg.alert('温馨提示', "请选择左上角【请求认证首页】/【认证成功界面】</br>来选择取消投放的界面");
		return;
	}
	var busids=getBusIdsChecked();
	if (tcheckbox) {
		pausePutAdvert(busids, yesorno, rootUrl
				+ '/advertPlanController/pausePutAdvert');
	}
	if (fcheckbox) {
		pausePutAdvert(busids, yesorno, rootUrl
				+ '/advertPlanController/pausePutLoginedAdvert');
	}
}

/**
 * 暂停投放
 */
var pauseActions = Ext.create('Ext.Action', {
	iconCls : 'common_pause',
	text : '暂停投放',
	handler : function(widget, event) {
		pauseOrRegain(pauseValueYES);
	}
});

/**
 * 取消暂停
 */
var regainActions = Ext.create('Ext.Action', {
	iconCls:'common_regain',
    text: '取消暂停',
    handler: function(widget, event) {
    	pauseOrRegain(pauseValueNO);
    }
});

/**
 * 取消投放
 */
var cancelActions = Ext.create('Ext.Action', {
	iconCls:'common_cancel',
    text: '取消投放',
    handler: function(widget, event) {
    	var tcheckbox = Ext.getCmp('tcheckbox').getValue();
    	var fcheckbox = Ext.getCmp('fcheckbox').getValue();
    	if (!tcheckbox && !fcheckbox) {
    		Ext.Msg.alert('温馨提示', "请选择左上角【请求认证首页】/【认证成功界面】</br>来选择取消投放的界面");
    		return;
    	}
    	var busids=getBusIdsChecked();
    	if(tcheckbox){
    		cancelPutAdvert(busids.toString(), rootUrl + '/advertPlanController/cancelPutAdvert');    		
    	}
    	if(fcheckbox){
    		cancelPutAdvert(busids.toString(), rootUrl + '/advertPlanController/cancelPutLoginedAdvert');    		
    	}
    }
});

/**
 * 车辆信息面板
 */
var putAllRountPanel = Ext.create('Ext.Panel', {
	id : 'putAllRountPanel-panel',
	layout : {
		type : 'hbox',
		pack : 'center'
	},
	items : [ {
		xtype : 'panel',
		flex : 3,
		layout : {
			type : 'vbox',
			align : 'stretch'
		},
		items : [ preModelmodelgrid, afterModelmodelgrid ]
	}, modelList ],
	bbar : ['->', addAction, pauseActions, regainActions, cancelActions ]
});
/**
 * 车辆广告列表的面板
 */
var putAllRountwin = Ext.create('widget.window', {
	title : '投放线路',
	header : {
		titlePosition : 2,
		titleAlign : 'center'
	},
	closable : true,
	  modal: true,
	closeAction : 'hide',
	frame:false,
//	border:false,
	width : 960,
	minWidth : 350,
	height : 430,
	layout : {
		type : 'fit'
	},
	items : putAllRountPanel
});
// ------------------------------------------------------------------
/**
 * 命名空间，主函数
 */
Ext.application({
	name : 'materialManger',
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
//				html:'333'
				items:panel
			}]
		});
	}
});
