Ext.define('AM.view.licenses.LicenseList' ,{
    extend: 'Ext.grid.Panel',
    alias : 'widget.licensesList',
    
    height: 600,

    fbar: [
           { type: 'button', 
        	 text: 'New License',
        	 action: 'new'},
        	 
           { type: 'button', 
	       	 text: 'Edit Selected',
	       	 disabled : true,
             action: 'edit'}	 
         ],
    
    initComponent: function() {
    	
    	this.store = 'LicensesStore';
    	
    	this.columns = [
    	                {header: 'Name',  dataIndex: 'name',  flex: 1},
    	                {header: 'URL', dataIndex: 'url', flex: 1}
    	            ];
    	
        this.callParent(arguments);
    }
});
