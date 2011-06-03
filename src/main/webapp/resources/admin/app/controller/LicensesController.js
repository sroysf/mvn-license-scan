Ext.define('AM.controller.LicensesController', {
	extend : 'Ext.app.Controller',

	stores : [ 'LicensesStore' ],

	models : [ 'License' ],

	views : [ 'licenses.LicenseList', 'licenses.LicenseManagerView' ],

	refs : [ {
		ref : 'editButton',
		selector : 'licensesList button[action=edit]'
	}, {
		ref : 'licenseGrid',
		selector : 'licensesList'
	} ],

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
			}
		});
	},

	licenseSelected : function() {
		console.log("An item was clicked on the license grid");
		var button = this.getEditButton();
		button.setDisabled(false);
	},
	
	doubleClickEdit:function(model, record) {
		var licenseId = record.get('id');
		this.editLicense(licenseId);
	},

	editSelected : function() {
		var grid = this.getLicenseGrid();
		var selectedItem = grid.getSelectionModel().getSelection()[0];
		
		var licenseId = selectedItem.get('id');
		this.editLicense(licenseId);
	},
	
	editLicense: function (licenseId) {
		console.log("Starting edit of license id : " + licenseId);
	},
	
	createNewLicense: function() {
		console.log("Creating new license");
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