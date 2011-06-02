Ext.define('AM.view.licenses.LicenseManagerView' ,{
    extend: 'Ext.panel.Panel',
    alias : 'widget.licensesManager',

    items : [
        {xtype : 'licensesList'
        }
    ],
    
    bodyPadding: 5,
    
    initComponent: function() {
    	
    	this.title = 'Licenses';
    	
        this.callParent(arguments);
    }
});
