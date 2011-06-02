Ext.define('AM.model.LicensePermission', {
    extend: 'Ext.data.Model',
    
    fields: [{name: 'id', type: 'string'},
             {name: 'approved', type: 'boolean', defaultValue:false},
             {name: 'policyId', type: 'string'},
             {name: 'licenseId', type: 'string'}
    ]
});
