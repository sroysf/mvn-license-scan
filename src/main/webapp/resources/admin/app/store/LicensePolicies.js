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
        },
        
        listeners: {
        	exception: {
        		fn : function (proxy, response, operation) {
        			if (response.status != 200) {
	        			console.log("Caught an exception from server ");
	        			for (x in response) {
	        				console.log(x + ' -> ' + response[x]);
	        			}
        			}
        		}
        	}
        }
   	}
});