Ext.define('AM.view.licensePolicy.List' ,{
    extend: 'Ext.form.field.ComboBox',
    alias : 'widget.licensePolicyList',

    initComponent: function() {
    	
    	this.fieldLabel = 'License Policy';
    	
        this.store = 'LicensePolicies';
        
        this.queryMode = 'local';
        
        this.displayField = 'name';
        
        this.valueField = 'id';

        this.columns = [
            {header: 'Name',  dataIndex: 'name',  flex: 1}
        ];
        
        this.fbar = [
                     {	type: 'button',
                         text: 'New',
                         action: 'new'
                     }
                 ];

        this.callParent(arguments);
    }
});
