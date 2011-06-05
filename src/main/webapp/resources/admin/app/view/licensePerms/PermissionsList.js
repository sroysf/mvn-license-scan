Ext.define('AM.view.licensePerms.PermissionsList' ,{
    extend: 'Ext.grid.Panel',
    alias : 'widget.permissionsList',

    height: 400,
    
    autoScroll : true,
    
    /*fbar: [
           { type: 'button', 
        	 text: 'New Artifact',
        	 action: 'new'},
        	 
           { type: 'button', 
	       	 text: 'Edit Selected',
	       	 disabled : true,
             action: 'edit'}
         ],*/
         
    initComponent: function() {
    	
    	var licensesStore = Ext.data.StoreManager.lookup('LicensesStore');
    	
    	this.store = 'LicensePermsStore';
    	
    	this.columns = [
    	                {header: 'License Name', dataIndex: 'licenseId', flex: 3,
    	                	renderer : function(value) {
    	                		var license = licensesStore.getById(value);
    	                		return license.get('name');
    	                	}
    	                },
    	                {header: 'License URL', dataIndex: 'licenseId', flex: 3,
    	                	renderer : function(value) {
    	                		var license = licensesStore.getById(value);
    	                		return license.get('url');
    	                	}
    	                },
    	                {header: 'Approved', dataIndex: 'approved'
    	                
    	                }
    	            ];
    	
        this.callParent(arguments);
    }
});
