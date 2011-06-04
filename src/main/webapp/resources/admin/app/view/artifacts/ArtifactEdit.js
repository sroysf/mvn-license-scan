Ext.define('AM.view.artifacts.ArtifactEdit', {
    extend: 'Ext.window.Window',
    alias : 'widget.artifactEdit',

    title : 'Maven Artifact',
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
                        name : 'groupId',
                        fieldLabel: 'Group Id',
                        width:500
                    },
                    {
                        xtype: 'textfield',
                        name : 'artifactId',
                        fieldLabel: 'Artifact Id',
                        width:500
                    },
                    {
                        xtype: 'textfield',
                        name : 'version',
                        fieldLabel: 'Version',
                        width:500
                    },
                    {
                        xtype: 'textfield',
                        name : 'licenseInfoSource',
                        fieldLabel: 'License info source',
                        width:500
                    },
                    {
                        xtype: 'combo',
                        name : 'licenseId',
                        store : 'LicensesStore',
                        fieldLabel: 'License',
                        displayField: 'name',
                        valueField: 'id',
                        forceSelection: true,
                        allowBlank: false,
                        typeAhead: true,
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