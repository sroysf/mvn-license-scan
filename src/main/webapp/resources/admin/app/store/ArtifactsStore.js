Ext.define('AM.store.ArtifactsStore', {
    extend: 'Ext.data.Store',
    model : 'AM.model.MavenCoordinate'
    	
    // Per attached article, moved proxy into model
    // http://edspencer.net/2011/02/proxies-extjs-4.html
    	
});