Ext.define('AM.controller.LicensePolicies', {
    extend: 'Ext.app.Controller',

    stores: ['LicensePolicies'],
    
    models: ['LicensePolicy'],
    
    views: ['licensePolicy.List'],
         
    init: function() {
        
    }
});