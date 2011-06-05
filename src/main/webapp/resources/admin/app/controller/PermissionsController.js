Ext.define('AM.controller.PermissionsController', {
    extend: 'Ext.app.Controller',

    stores: ['LicensePolicyStore', 'LicensePermsStore'],
    
    models: ['License', 'LicensePolicy', 'LicensePermission'],
    
    views: ['licensePerms.PermissionsManagerView', 'licensePerms.PolicyList', 'licensePerms.PermissionsList'],
         
    init: function() {
    	this.control({
            'licensePolicyList': {
                select: this.policySelected
            }
        });
    },
    
    policySelected: function(field, value) {
    	console.log("Selected : " + field.value);
    	
    	// Populate the permissions store with the right values
    	// I cannot find any documentation of the params config object used here, but it
    	// seems to be the right way to do it across all the examples found on the web.
    	// Frustrating that I could not have known this without doing lots of google searches...
    	var permStore = Ext.data.StoreManager.lookup('LicensePermsStore');
    	permStore.removeAll(true);
    	permStore.load({params:{policyId: field.value}});
    }
});