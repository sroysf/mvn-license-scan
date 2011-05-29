Ext.define('AM.model.MavenCoordinate', {
    extend: 'Ext.data.Model',
    
    fields: [{name : 'id', type: 'string'}, 
             {name : 'groupId', type: 'string'},
             {name : 'artifactId', type: 'string'},
             {name : 'version', type: 'string'},
             {name : 'licenseInfoSource', type: 'string'},
             {name : 'license_id', type: 'string'}
    ],

    associations: [
  	    {type : 'belongsTo', model: 'License'}
  	]
});
