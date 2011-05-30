
Ext.Loader.setConfig({enabled:true});

var SERVER_ROOT = '/mvn-license-scan/';

Ext.application({
    name: 'AM',

    appFolder: 'app',

    controllers: [
        'Users',
        'LicensePolicies'
    ],

    launch: function() {
        Ext.create('Ext.panel.Panel', {
            layout: 'fit',
            items: [
                {
                    xtype: 'licensePolicyList',
                }
            ],
            renderTo: adminAppBody
        });
    }
});