Ext.define('AM.store.Users', {
    extend: 'Ext.data.Store',
    model : 'AM.model.User',
    autoLoad : true,

	proxy: {
	    type: 'ajax',
	    api: {read: 'app/data/users-read.html',
	    	  update: 'app/data/users-update.html'},
	    reader: {
	        type: 'json',
	        root: 'users',
	        successProperty: 'success'
	    }
	}
});