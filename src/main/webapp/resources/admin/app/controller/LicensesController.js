Ext.define('AM.controller.LicensesController', {
    extend: 'Ext.app.Controller',

    stores: ['LicensesStore'],
    
    models: ['License'],
    
    views: ['licenses.LicenseList',
            'licenses.LicenseManagerView'],
         
    init: function() {
    	
    }
    
    /*
     *  This is the previous working code
     * 
     * 
    stores: ['LicensePolicies'],
    
    models: ['LicensePolicy'],
    
    views: ['AppMain'],
         
    init: function() {
    	this.control({
            'licensePolicyList': {
                select: this.addLicense
            }
        });
    },
    
    addLicense : function (field, value) {
    	console.log('Double clicked on ' + field.getValue());
    	
    	var l = new AM.model.LicensePolicy({name : 'specialLicense'});
    	console.log("Created new license policy : " + l);
    	var licensePoliciesStore = this.getLicensePoliciesStore();
    	console.log("LP store : " + licensePoliciesStore);
    	
    	licensePoliciesStore.add(l);
    	licensePoliciesStore.sync();
    }
     */
    
});