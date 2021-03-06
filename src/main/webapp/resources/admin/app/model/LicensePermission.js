Ext.define('AM.model.LicensePermission', {
    extend: 'Ext.data.Model',
    
    fields: [{name: 'id', type: 'string'},
             {name: 'approved', type: 'boolean', defaultValue:false},
             {name: 'policyId', type: 'string'},
             {name: 'licenseId', type: 'string'}
    ],
    
    proxy: {
   		type : 'rest',
   		url  : SERVER_ROOT + 'permission',
   		reader: {
            type: 'json',
            successProperty: 'success',
            idProperty: 'id',
            root: undefined
        },
        
        listeners: {
        	exception: {
        		fn : function (proxy, response, operation) {
        			console.log('Completed request to service');
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
