Ext.define('AM.controller.ArtifactsController', {
    extend: 'Ext.app.Controller',

    stores: ['ArtifactsStore'],
    
    models: ['MavenCoordinate', 'License'],
    
    views: ['artifacts.ArtifactManagerView',
            'artifacts.ArtifactList'],
         
    init: function() {
    	this.control({
            'artifactsList': {
            	itemdblclick: this.editArtifact
            }
        });
    },
    
    editArtifact : function (grid, record) {
    	console.log('Double clicked on ' + record.get('id'));
    	
    	var store = Ext.data.StoreManager.lookup('ArtifactsStore');
    	console.log("Store = " + store);
    	for (x in store) {
    		console.log(x + ' -> ' + store[x]);
    	}
    	var a = store.first();
    	console.log("First = " + first);
    	console.log(a.groupId);
    },
    
});