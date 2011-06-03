Ext.define('AM.view.licenses.LicenseEdit', {
    extend: 'Ext.window.Window',
    alias : 'widget.licenseEdit',

    title : 'License',
    layout: 'fit',
    autoShow: false,
    modal:true,

    initComponent: function() {
        this.items = [
            {
                xtype: 'form',
                items: [
                    {
                        xtype: 'textfield',
                        name : 'name',
                        fieldLabel: 'Name',
                        width:500
                    },
                    {
                        xtype: 'textfield',
                        name : 'url',
                        fieldLabel: 'URL',
                        width:500
                    }
                ]
            }
        ];

        this.buttons = [
            {
                text: 'Save',
                action: 'save'
            },
            {
                text: 'Cancel',
                scope: this,
                handler: this.close
            }
        ];

        this.callParent(arguments);
    }
});