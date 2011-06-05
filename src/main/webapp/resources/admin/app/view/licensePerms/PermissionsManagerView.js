Ext.define('AM.view.licensePerms.PermissionsManagerView' ,{
    extend: 'Ext.panel.Panel',
    alias : 'widget.permissionsManager',

    items : [
        {xtype : 'licensePolicyList'
        },
        {xtype : 'permissionsList'}
    ],
    
    bodyPadding: 5,
    
    initComponent: function() {
    	
    	this.title = 'License Permissions';
    	
        this.callParent(arguments);
    }
});
