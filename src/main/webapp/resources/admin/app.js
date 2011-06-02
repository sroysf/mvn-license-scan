
Ext.Loader.setConfig({enabled:true});

var SERVER_ROOT = '/mvn-license-scan/rest/';

Ext.application({
    name: 'AM',

    appFolder: 'app',

    controllers: [
        'MainController',
        'ArtifactsController',
        'PermissionsController',
        'LicensesController'
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