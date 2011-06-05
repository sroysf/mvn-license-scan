Ext.define('AM.view.AppMain' ,{
    extend: 'Ext.tab.Panel',
    alias : 'widget.appMain',

    title : 'License Manager Administration Console',
    
    //width: 950,
    //height: 800,
    animCollapse: true,
    activeTab: 2,
    
    renderTo: document.body,  // This should be to the adminAppBody div, shouldn't it?
    
    initComponent: function() {
    	
    	var artifactsLoadComplete = false;
    	var licensesLoadComplete = false;
    	var policiesLoadComplete = false;
    	var self = this;
    	
    	// Show a loading window...
    	var waitWindow = Ext.Msg.wait('','Loading data from server...', 
    			{
	    			animate : true,
	    			interval : 250,
	    			fn : function () {
	    				console.log("Waiting message finished!");
	    			}
    			});
    	
    	function checkLoadCompleteStatus() {
    		if (artifactsLoadComplete && licensesLoadComplete && policiesLoadComplete) {
    			console.log("Both loads are done!");
    			waitWindow.close();
    			console.log("Closed window.");
    		}
    	}
    	
    	// Initiate important data loads...
    	var licensesStore = Ext.data.StoreManager.lookup('LicensesStore');
    	var artifactsStore = Ext.data.StoreManager.lookup('ArtifactsStore');
    	var policiesStore = Ext.data.StoreManager.lookup('LicensePolicyStore');
    	
    	licensesStore.load(function(records, operation, success) {
    		if (success) {
    			licensesLoadComplete = true;
    			checkLoadCompleteStatus();
    		} else {
    			console.log("WARNING : Unable to load license data.");
    		}
		});
    	
    	artifactsStore.load(function(records, operation, success) {
    		if (success) {
    			artifactsLoadComplete = true;
    			checkLoadCompleteStatus();
    		} else {
    			console.log("WARNING : Unable to load artifacts data.");
    		}
		});
    	
    	policiesStore.load(function(records, operation, success) {
    		if (success) {
    			policiesLoadComplete = true;
    			checkLoadCompleteStatus();
    		} else {
    			console.log("WARNING : Unable to load license policies data.");
    		}
		});
    	
    	
    	// add the child components
    	this.items = [{                        
        	xtype: 'licensesManager'
        },{
            xtype: 'artifactsManager',
        },{
            xtype: 'permissionsManager'
            //title: 'Permissions',
        }];
    	
		this.callParent(arguments);
    }
});
