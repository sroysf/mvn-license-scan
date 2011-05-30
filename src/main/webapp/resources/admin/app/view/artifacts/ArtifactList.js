Ext.define('AM.view.artifacts.ArtifactList' ,{
    extend: 'Ext.grid.Panel',
    alias : 'widget.artifactsList',

    initComponent: function() {
    	
    	this.store = 'ArtifactsStore';
    	
    	this.columns = [
    	                {header: 'Group Id',  dataIndex: 'groupId',  flex: 1},
    	                {header: 'Artifact Id', dataIndex: 'artifactId', flex: 1},
    	                {header: 'Version', dataIndex: 'version', flex: 1},
    	                {header: 'License name', dataIndex: 'licenseName', flex: 1},
    	                {header: 'License URL', dataIndex: 'licenseUrl', flex: 1}
    	            ];
    	
        this.callParent(arguments);
    }
});
