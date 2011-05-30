Ext.define('AM.view.artifacts.ArtifactManagerView' ,{
    extend: 'Ext.panel.Panel',
    alias : 'widget.artifactsManager',

    items : [
        {xtype : 'artifactsList'
        }
    ],
    
    bodyPadding: 5,
    
    initComponent: function() {
    	
    	this.title = 'Maven Artifacts';
    	
        this.callParent(arguments);
    }
});
