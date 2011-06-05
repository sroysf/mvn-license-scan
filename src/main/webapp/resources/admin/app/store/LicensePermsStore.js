Ext.define('AM.store.LicensePermsStore', {
    extend: 'Ext.data.Store',
    model : 'AM.model.LicensePermission',
    
    sorters : [
               {
               	property : 'name',
               	direction : 'ASC'
               }
           ]
});