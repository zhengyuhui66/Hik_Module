var SMSCONTENTNUM='XXXXXX';
var getDIVPanel=function(url,name,id){
	var temp='<div style="height:100%;text-align:center;"><div id="'+id+'" class="setting" style="height:64px;width:64px;margin:5px auto;cursor:pointer;background-image:url('+url+');background-repeat:no-repeat;"></div><div>'+name+'</div></div>'
	return temp;
}
/**
 * 比较两个对像的
 */
var equseObj=function(obj1,obj2){
	var flag=true;
	for(n in obj1){
		if(obj1[n]!=obj2[n]){
			flag=false;
			break;
		}
	}
	return flag;
}
var subpanel=function(id,getdivpael){
	var tempPanel = Ext.create('Ext.Panel', {
		id :id,
		flex:1,
		width:200,
		height:100,
		html:getdivpael
	});
	return tempPanel;
}
var applyandSure=true;
//--短信设置----------------------------------------------------------------------------------
var smsPanel = Ext.create('Ext.panel.Panel', {
    items: [{
    	width: 600,
        height: 400,
        id:'smscontent',
        name:'smscontent',
        xtype: 'textareafield'
    }],
    tbar:['请用XXXXXX表示输入码'],
    buttons: [{
        text: '应用',
        handler: function() {
        	submitSMS();
        	applyandSure=false;
        	}
        },{
            text: '确定',
            handler: function(){
            	if(applyandSure){
            		submitSMS();            		
            	}else{
            		applyandSure=true;
            	}
            	smsWin.close();
            }
        }]
});
var submitSMS=function(){
	var tempValue=Ext.getCmp('smscontent').getValue();
	if(tempValue.indexOf(SMSCONTENTNUM)==-1){
		Ext.Msg.alert('温馨提示',"需包含"+SMSCONTENTNUM+"来表示验证码");
		return;
	}
	
	Ext.Ajax.request({
	    url:  rootUrl+'/timeSetController/updateSMS',
	    async: false,
	    params:{
	    	smscontent:tempValue
	    },
	    success: function(response){
	    	var respText = Ext.JSON.decode(response.responseText);
	    	var success=respText.success;
	    	if(success=='success'){
	    		Ext.Msg.alert('温馨提示',"应用成功");
	    	}
	    	
	    }
	});
}
var smsWin = Ext.create('widget.window', {
	title : '短信设置',
	header : {
		titlePosition : 2,
		titleAlign : 'center'
	},
	closable : true,
	closeAction : 'hide',
	frame:false,
	width : 600,
	minWidth : 350,
	height : 400,
	layout : {
		type : 'fit'
	},
	items:smsPanel
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
			columns : 4
		},
		border : false,
		defaults : {
			margin : '30 15 30 0'
		},
		items :[
		        subpanel('time-panel',getDIVPanel(rootUrl+'/images/icons/timesetting.png','时间设置','timeId')),
		        subpanel('sms-panel',getDIVPanel(rootUrl+'/images/icons/smssetting.png','短信设置','smsId')),
		        subpanel('panel-panel',getDIVPanel(rootUrl+'/images/icons/routesetting.png','投放设置','planId')),
		        subpanel('style-panel',getDIVPanel(rootUrl+'/images/icons/stylesetting.png','皮肤管理','styleId')),
		        subpanel('advproperty-panel',getDIVPanel(rootUrl+'/images/icons/advproperty.png','广告属性管理','advproId')),
		        subpanel('modelproperty-panel',getDIVPanel(rootUrl+'/images/icons/modelproperty.png','认证周期管理','modelproId')),
		        subpanel('putstragerId-panel',getDIVPanel(rootUrl+'/images/icons/putstrager.png','播放策略管理','putstragerId'))
		        ]
		
	},listeners : {
		afterlayout : function() {
			/**
			 * 委托点击触发事件
			 */
			$('body').delegate('.setting', 'click', function() {
				var id=$(this).attr('id');
				if('timeId'==id){
					timeWin.show(this,function(){});					
				}else if('smsId'==id){
					smsWin.show(this,function(){
						Ext.Ajax.request({
						    url:  rootUrl+'/timeSetController/querySMS',
						    async: false,
						    success: function(response){
						    	var respText = Ext.JSON.decode(response.responseText);
						    	var success=respText.success;
						    	if(success=='true'){
						    		var content=respText.data[0];
						    		Ext.getCmp('smscontent').setValue(content);
						    	}
						    	
						    }
						});
					});
				}else if('planId'==id){
					
				}else if('styleId'==id){
					styleWin.show(this);
				}else if('modelproId'==id){
					modelproWin.show(this,function(){
						modelStore.reload();
					});
				}else if('advproId'==id){
					advproWin.show(this,function(){
						advStore.reload();
					});
				}else if('putstragerId'==id){
					putStragerWin.show(this,function(){
						putstragerStore.reload();
					});
				}
				
			});

		}
	}
});


Ext.application({
    name: 'menuManger',
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
			}]
    	});
    }
});

