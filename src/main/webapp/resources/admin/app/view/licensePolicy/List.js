Ext.define('AM.view.licensePolicy.List' ,{
    extend: 'Ext.grid.Panel',
    alias : 'widget.licensePolicyList',

    title : 'Policies',

    initComponent: function() {
        this.store = 'LicensePolicies',

        this.columns = [
            {header: 'Name',  dataIndex: 'name',  flex: 1}
        ];

        this.callParent(arguments);
    }
});
