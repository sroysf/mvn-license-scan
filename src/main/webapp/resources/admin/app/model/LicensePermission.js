Ext.define('AM.model.LicensePermission', {
    extend: 'Ext.data.Model',
    
    fields: [{name: 'id', type: 'string'},
             {name: 'approved', type: 'boolean', defaultValue:false},
             {name: 'licensePolicy_id', type: 'string'},
             {name: 'license_id', type: 'string'}
    ],

	associations: [
	    {type : 'belongsTo', model: 'LicensePolicy'},
	    {type : 'belongsTo', model: 'License'}
	]
});
