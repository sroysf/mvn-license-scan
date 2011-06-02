Ext.define('AM.view.licenses.LicenseList' ,{
    extend: 'Ext.grid.Panel',
    alias : 'widget.licensesList',

    initComponent: function() {
    	
    	this.store = 'LicensesStore';
    	
    	this.columns = [
    	                {header: 'Name',  dataIndex: 'name',  flex: 1},
    	                {header: 'URL', dataIndex: 'url', flex: 1}
    	            ];
    	
        this.callParent(arguments);
        
        this.store.load();
    }
});
