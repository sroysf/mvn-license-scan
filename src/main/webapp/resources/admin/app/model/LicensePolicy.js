Ext.define('AM.model.LicensePolicy', {
    extend: 'Ext.data.Model',
    
    fields: [{name: 'id', type: 'string'},
             {name: 'name', type: 'string'}
    ],
    
    associations: [
 	    {type : 'hasMany', model: 'LicensePermission', name: 'permissions'}
   	]
});