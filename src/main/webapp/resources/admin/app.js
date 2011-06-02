
Ext.Loader.setConfig({enabled:true});

var SERVER_ROOT = '/mvn-license-scan/rest/';

function describeObjectProperties(obj) {
	for (var x in obj) {
		var prop = obj[x];
		if (typeof(prop) != 'function') {
			console.log(x + " : " + prop);
		}
	}
}

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