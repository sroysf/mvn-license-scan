Ext.define('AM.store.LicensePolicies', {
    extend: 'Ext.data.Store',
    model : 'AM.model.LicensePolicy',
    autoLoad : true,
    
	proxy: {
   		type : 'rest',
   		url  : SERVER_ROOT + 'license_policy',
   		reader: {
            type: 'json',
            root: 'policies'
        }
   	}
});