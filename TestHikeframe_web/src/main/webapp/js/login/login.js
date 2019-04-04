var _login=Ext.define('KitchenSink.view.form.LoginForm', {
    extend: 'Ext.form.Panel',
    xtype: 'login-form',
    frame:false,
    border:false,
    width: 320,
    title:'<span>广告推送平台</span>',
    height:220,
    html:'<a style="color:red;display:none;" id="tipInfo">登陆信息有误</a>',
    items: [{
	        	labelWidth:50,
	            fieldLabel: '账户',
	            name: 'username',
	            id:'username',
	            emptyText: '请输入帐户号码',
	            margin:'30 0 0 50',
	       		allowBlank: false,
	       		blankText: '不能为空!',
	            xtype:'textfield'
	        },{
	        	labelWidth:50,
	            fieldLabel: '密码',
	            name: 'passWord',
	            id:'password',
	            emptyText: '请输入帐户密码',
	            inputType: 'password',
	            margin:'30 0 0 50',
	       		allowBlank: false,
	       		blankText: '不能为空!',
	            xtype:'textfield'
	        },{
		        layout:{
		        	type:'hbox',
		        	pack:'center',
		        	align:'middle'
		        },
		        id:'verificode',
		        hidden:true,
		        margin:'13 0 0 0',
		        border:false,
		        frame:false,
		        items:[{
			        	labelWidth:50,
			        	width:204,
			        	margin:'0 0 0 47',
			            fieldLabel: '验证码',
			            name: 'verificode',
			            xtype:'textfield',
			            emptyText: '验证码'
			        },{
			        	html:'<img id="vimg" width="60px" height="20px" title="点击更换" onclick="changeCode();" src="'+rootUrl+'/Wifi/getCode">'
			        }]
	        },{
	            name: 'flag',
	            id:'flag',
	            hidden:true,
	            value:'0',
	            xtype:'textfield'
	        }
    ],
    buttonAlign:'center',
    buttons: [
        { text:'重置',
        	formBind: true, //only enabled once the form is valid
        	style:'background-color:red;',
        	handler:function(){
        		this.up('form').getForm().reset();
        		$("#tipInfo").css('display','none');
        	}
        },{ text:'登陆',
        	margin:'0 0 20 20',
        	style:'background-color:red;',
        	handler:function(){
            //获取当前的表单form  
            var form = this.up('form').getForm();  
            //判断否通过了表单验证，如果不能空的为空则不能提交  
            if(form.isValid()){ 
                form.submit(  
                        {  
                            clientValidation:true,  
                            waitMsg:'请稍候',  
                            waitTitle:'正在验证登录',  
                            url:rootUrl+'/userlogin',  
                            success:function(form,action){  
                                //登录成功后的操作，这里只是提示一下  
                            	if(action&&action.result&&action.result.success){
                            		if(action.result.success=="true"){
                            			window.location.href=rootUrl+'/index'; 
                            		}else if(action.result.success=="false"){
                            			$("#tipInfo").css('display','inline');
                            			$("#tipInfo").html(action.result.data);
                            			Ext.getCmp('username').setMargin('13 0 0 50');
                            			Ext.getCmp('password').setMargin('13 0 0 50');
                            			Ext.getCmp('verificode').show();
                            			Ext.getCmp('flag').setValue('1');
                            			changeCode();
                            		}
                            	}else{
                        			$("#tipInfo").css('display','inline');
                        			$("#tipInfo").html("系统内部错误");
                        			Ext.getCmp('username').setMargin('13 0 0 50');
                        			Ext.getCmp('password').setMargin('13 0 0 50');
                        			Ext.getCmp('verificode').show();
                        			Ext.getCmp('flag').setValue('1');
                        			changeCode();
                            	}
                            },  
                            failure:function(form,action){  
                            	$("#tipInfo").css('display','inline');
                    			$("#tipInfo").html("系统内部错误");
                    			Ext.getCmp('username').setMargin('13 0 0 50');
                    			Ext.getCmp('password').setMargin('13 0 0 50');
                    			Ext.getCmp('verificode').show();
                    			Ext.getCmp('flag').setValue('1');
                    			changeCode();
                            }  
                                 
                        })
                }  
            }   
        }
    ]
});
var changeCode=function(){
	var imgNode = document.getElementById("vimg");
	imgNode.src = rootUrl+'/Wifi/getCode?t='+Math.random();
}
Ext.application({
    name: 'ext.hik.login',
    launch: function() {
    	Ext.create('Ext.container.Viewport', {
    		layout: {
				  type: 'vbox',
				  align: 'center',
				  pack:'center'
			    },
			 style: "background-image:url("+rootUrl+"/images/bg.png);",
    		items:_login
    	});
    }
});