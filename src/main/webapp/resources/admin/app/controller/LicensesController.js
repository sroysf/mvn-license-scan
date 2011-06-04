Ext.define('AM.controller.LicensesController', {
	extend : 'Ext.app.Controller',

	stores : [ 'LicensesStore' ],

	models : [ 'License' ],

	views : [ 'licenses.LicenseList', 'licenses.LicenseManagerView', 'licenses.LicenseEdit' ],

	refs : [ {
		ref : 'editButton',
		selector : 'licensesList button[action=edit]'
	}, {
		ref : 'licenseGrid',
		selector : 'licensesList'
	}],

	init : function() {
		this.control({
			'licensesList' : {
				itemclick : this.licenseSelected,
				itemdblclick : this.doubleClickEdit
			},
			'licensesList button[action=edit]' : {
				click : this.editSelected
			},
			'licensesList button[action=new]' : {
				click : this.createNewLicense
			},
			'licenseEdit button[action=save]' : {
				click : this.saveLicense
			}

		});
	},

	licenseSelected : function() {
		console.log("An item was clicked on the license grid");
		var button = this.getEditButton();
		button.setDisabled(false);
	},
	
	doubleClickEdit:function(model, record) {
		console.log("Editing record : " + record);
		this.editLicense(record);
	},

	editSelected : function() {
		var grid = this.getLicenseGrid();
		var selectedItem = grid.getSelectionModel().getSelection()[0];
		
		this.editLicense(selectedItem);
	},
	
	editLicense: function (license) {
		console.log("Starting edit of license id : " + license.get('id'));
		var editWin = Ext.widget('licenseEdit');
		
		
		editWin.down('form').loadRecord(license);
		editWin.show();
	},
	
	createNewLicense: function() {
		console.log("Creating new license");
		var editWin = Ext.widget('licenseEdit');
		var license = Ext.create('AM.model.License');
		editWin.down('form').loadRecord(license);
		editWin.show();
	},
	
	saveLicense: function (button) {
		var win = button.up('window');
		var form   = win.down('form');
        var license = form.getRecord();
        var values = form.getValues();
        license.set(values);
        
		console.log("Saving :");
		console.log(values);
		
		var licensesStore = Ext.data.StoreManager.lookup('LicensesStore');
		
		if (license.get('id') == "") {
			
			console.log("Saving new entry : " + license);
			license.save({
				success: function(savedLicense) {
					console.log("Got back : " + savedLicense);
					console.log("Generated id : " + savedLicense.get('id'));
					licensesStore.insert(licensesStore.getCount(), savedLicense);
				}
			});
		} else {
			console.log("Synchronizing updates from store to server");
			licensesStore.sync();
		}
		
		win.close();
	}

/*
 * This is the previous working code
 * 
 * 
 * stores: ['LicensePolicies'],
 * 
 * models: ['LicensePolicy'],
 * 
 * views: ['AppMain'],
 * 
 * init: function() { this.control({ 'licensePolicyList': { select:
 * this.addLicense } }); },
 * 
 * addLicense : function (field, value) { console.log('Double clicked on ' +
 * field.getValue());
 * 
 * var l = new AM.model.LicensePolicy({name : 'specialLicense'});
 * console.log("Created new license policy : " + l); var licensePoliciesStore =
 * this.getLicensePoliciesStore(); console.log("LP store : " +
 * licensePoliciesStore);
 * 
 * licensePoliciesStore.add(l); licensePoliciesStore.sync(); }
 */

});