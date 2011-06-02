Ext.define('AM.store.LicensesStore', {
    extend: 'Ext.data.Store',
    model : 'AM.model.License',
    autoLoad : true,
    
	proxy: {
   		type : 'rest',
   		url  : SERVER_ROOT + 'license',
   		reader: {
            type: 'json'
        },
        
        listeners: {
        	exception: {
        		fn : function (proxy, response, operation) {
        			console.log('Completed request to artifacts service');
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