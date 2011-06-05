Ext.define('AM.store.LicensePolicyStore', {
    extend: 'Ext.data.Store',
    model : 'AM.model.LicensePolicy',
    
    sorters : [
               {
               	property : 'name',
               	direction : 'ASC'
               }
           ]
});