Ext.define('AM.model.MavenCoordinate', {
    extend: 'Ext.data.Model',
    
    fields: [{name : 'id', type: 'string'}, 
             {name : 'groupId', type: 'string'},
             {name : 'artifactId', type: 'string'},
             {name : 'version', type: 'string'},
             {name : 'licenseInfoSource', type: 'string'},
             {name : 'licenseId', type: 'string'}
    ]

	// http://stackoverflow.com/questions/1810311/ext-js-columnmodel-binding-to-a-complex-json-object
	// http://stackoverflow.com/questions/5711992/extjs-null-safe-retrieval-of-complex-objects-using-jsonreader
	// This type of mapping of the nested license works ok inbound, but may
	// want to consider forcing id based serialization anyway in order to drastically
	// reduce the AJAX payload size.
	// Then, the datagrids can use a custom renderer to look up the license by id
	// from the license store. Try this before completely going down this path.
	
	// Also, here is a useful post about nested json that might be exactly the type of thing I'm looking for.
	// Need to understand the best way to do this before moving on with writing a rest app.
	// http://numberformat.wordpress.com/2011/04/23/nested-json/
});
