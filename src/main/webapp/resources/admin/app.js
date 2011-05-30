
Ext.Loader.setConfig({enabled:true});

var SERVER_ROOT = '/mvn-license-scan/';

Ext.application({
    name: 'AM',

    appFolder: 'app',

    controllers: [
        'MainController',
        'LicensePolicies'
    ],
    
    launch: function() {
        Ext.create('Ext.panel.Panel', {
            layout: 'fit',
            items: [
                {
                    xtype: 'appMain'
                }
            ],
            renderTo: adminAppBody
        });
    }
});