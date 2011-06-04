Ext.define('AM.store.LicensesStore', {
    extend: 'Ext.data.Store',
    model : 'AM.model.License',
    
    sorters : [
        {
        	property : 'name',
        	direction : 'ASC'
        }
    ]
    	
    // Per attached article, moved proxy into model
    // http://edspencer.net/2011/02/proxies-extjs-4.html
});