Ext.define('AM.view.artifacts.ArtifactList' ,{
    extend: 'Ext.grid.Panel',
    alias : 'widget.artifactsList',

    
    fbar: [
           { type: 'button', 
        	 text: 'New Artifact',
        	 action: 'new'},
        	 
           { type: 'button', 
	       	 text: 'Edit Selected',
	       	 disabled : true,
             action: 'edit'}
         ],
         
    initComponent: function() {
    	
    	var licensesStore = Ext.data.StoreManager.lookup('LicensesStore');
    	
    	this.store = 'ArtifactsStore';
    	
    	this.columns = [
    	                {header: 'Group Id',  dataIndex: 'groupId',  flex: 2},
    	                {header: 'Artifact Id', dataIndex: 'artifactId', flex: 1},
    	                {header: 'Version', dataIndex: 'version', flex: 1},
    	                {header: 'License', dataIndex: 'licenseId', flex: 3,
    	                	renderer : function(value) {
    	                		var license = licensesStore.getById(value);
    	                		return license.get('name');
    	                	}}
    	            ];
    	
        this.callParent(arguments);
    }
});
