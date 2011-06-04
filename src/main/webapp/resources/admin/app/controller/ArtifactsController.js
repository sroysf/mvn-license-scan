Ext.define('AM.controller.ArtifactsController', {
    extend: 'Ext.app.Controller',

    stores: ['ArtifactsStore','LicensesStore'],
    
    models: ['MavenCoordinate', 'License'],
    
    refs : [ {
		ref : 'editButton',
		selector : 'artifactsList button[action=edit]'
	}, {
		ref : 'artifactGrid',
		selector : 'artifactsList'
	}],
	
    views: ['artifacts.ArtifactManagerView',
            'artifacts.ArtifactList',
            'artifacts.ArtifactEdit'],
         
    init : function() {
		this.control({
			'artifactsList' : {
				itemclick : this.artifactselected,
				itemdblclick : this.doubleClickEdit
			},
			'artifactsList button[action=edit]' : {
				click : this.editSelected
			},
			'artifactsList button[action=new]' : {
				click : this.createNewArtifact
			},
			'artifactEdit button[action=save]' : {
				click : this.saveArtifact
			}

		});
	},

	artifactselected : function() {
		console.log("An item was clicked on the artifact grid");
		var button = this.getEditButton();
		button.setDisabled(false);
	},
	
	doubleClickEdit:function(model, record) {
		console.log("Editing record : " + record);
		this.editArtifact(record);
	},

	editSelected : function() {
		var grid = this.getArtifactGrid();
		var selectedItem = grid.getSelectionModel().getSelection()[0];
		
		this.editArtifact(selectedItem);
	},
	
	editArtifact: function (artifact) {
		console.log("Starting edit of artifact id : " + artifact.get('id'));
		var editWin = Ext.widget('artifactEdit');
		
		
		editWin.down('form').loadRecord(artifact);
		editWin.show();
	},
	
	createNewArtifact: function() {
		console.log("Creating new artifact");
		var editWin = Ext.widget('artifactEdit');
		var artifact = Ext.create('AM.model.MavenCoordinate');
		editWin.down('form').loadRecord(artifact);
		editWin.show();
	},
	
	saveArtifact: function (button) {
		var win = button.up('window');
		var form   = win.down('form');
        var artifact = form.getRecord();
        var values = form.getValues();
        artifact.set(values);
        
		console.log("Saving :");
		console.log(values);
		
		/*var artifactsStore = Ext.data.StoreManager.lookup('artifactsStore');
		
		if (artifact.get('id') == "") {
			
			console.log("Saving new entry : " + artifact);
			artifact.save({
				success: function(savedArtifact) {
					console.log("Got back : " + savedArtifact);
					console.log("Generated id : " + savedArtifact.get('id'));
					artifactsStore.insert(artifactsStore.getCount(), savedArtifact);
				}
			});
		} else {
			console.log("Synchronizing updates from store to server");
			artifactsStore.sync();
		}
		
		win.close();*/
	}
    
});