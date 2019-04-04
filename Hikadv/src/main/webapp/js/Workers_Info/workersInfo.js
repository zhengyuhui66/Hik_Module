var _panel=Ext.create('Ext.panel.Panel', {
    title: 'Panel with ButtonGroup',
    width: 1300,
    height:200,
    bodyPadding: 10,
    html: 'HTML Panel Content',
    tbar: [{
        xtype: 'buttongroup',
        columns: 3,
        title: 'Clipboard',
        items: [{
            text: 'Paste',
            scale: 'large',
            rowspan: 3,
            iconCls: 'add',
            iconAlign: 'top',
            cls: 'btn-as-arrow'
        },{
            xtype:'splitbutton',
            text: 'Menu Button',
            scale: 'large',
            rowspan: 3,
            iconCls: 'add',
            iconAlign: 'top',
            arrowAlign:'bottom',
            menu: [{ text: 'Menu Item 1' }]
        },{
            xtype:'splitbutton', text: 'Cut', iconCls: 'add16', menu: [{text: 'Cut Menu Item'}]
        },{
            text: 'Copy', iconCls: 'add16'
        },{
            text: 'Format', iconCls: 'add16'
        }]
    }]
});

var store = Ext.create('Ext.data.TreeStore', {
    root: {
        expanded: true,
        children: [
            { text: "detention", leaf: true },
            { text: "homework", expanded: true, children: [
                { text: "book report", leaf: true },
                { text: "alegrbra", leaf: true}
            ] },
            { text: "buy lottery tickets", leaf: true }
        ]
    }
});

var _treePanel=Ext.create('Ext.tree.Panel', {
//    title: 'Simple Tree',
	region: 'west',
    collapsible: true,
    title: '目录餐单',
    width: 200,
    height: 150,
    store: store,
    rootVisible: false
});

Ext.application({
    name: 'HelloExt',
    launch: function() {
    	Ext.create('Ext.container.Viewport', {
    	    layout: 'border',
    	    items: [{
    	        region: 'north',
    	        html: '<h1 class="x-panel-header">广告推送平台</h1>',
    	        border: false,
    	        margin:'0 0 0 20'
    	    }, //{
//    	        region: 'west',
//    	        collapsible: true,
//    	        title: '目录餐单',
//    	        width: 250,
//    	        items:_treePanel
    	    //},
    	    _treePanel,
    	    {
    	        region: 'center',
    	        xtype: 'tabpanel', // TabPanel本身没有title属性
    	        activeTab: 0,      // 配置默认显示的激活面板
    	        items: [{
    	            title: '系统菜单',
    	            html: 'The first tab\'s content1. Others may be added dynamically',
    	            closable:true
    	        },{
    	            title: '公告管理',
    	            html: 'The first tab\'s content2. Others may be added dynamically',
    	            closable:true
    	        },{
    	            title: '广告管理',
    	            html: 'The first tab\'s content3. Others may be added dynamically',
    	            closable:true
    	        },{
    	            title: '广告设置',
    	            layout: 'fit',
    	            items:_panel,
    	            closable:true
    	        }]
    	    }]
    	});
    }
});
