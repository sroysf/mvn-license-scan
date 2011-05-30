Ext.define('AM.view.AppMain' ,{
    extend: 'Ext.tab.Panel',
    alias : 'widget.appMain',

    title : 'License Manager Administration Console',
    
    //width: 950,
    //height: 800,
    animCollapse: true,
    activeTab: 0,
    
    renderTo: document.body,
    
    items: [{                        
        xtype: 'panel',
        title: 'Licenses',
    },{
        xtype: 'artifactsManager',
    },{
        xtype: 'panel',
        title: 'Permissions',
    }],

    initComponent: function() {
    	
    	console.log("Initialized outer panel");
    	
        this.callParent(arguments);
    }
});
