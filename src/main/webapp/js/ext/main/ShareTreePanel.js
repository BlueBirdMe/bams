ShareTreePanel = Ext.extend(Ext.tree.TreePanel, {
    id : 'shareTree',
    path : null,
	constructor : function(_cfg) {
		Ext.apply(this, _cfg);
		ShareTreePanel.superclass.constructor.call(this, {
			region : 'center',
			split : true,
			margins : '-1 -1 2 -1',
			height:300,
			autoScroll : true,
			lines : true,
			loader : new Ext.tree.TreeLoader({
				dataUrl : 'getShareDirectories.do'
			}),
			root : new Ext.tree.AsyncTreeNode({
				id : '*',
				iconCls : 'db-icn-world',
				text : '共享文件柜'
			}),
			listeners : {   
                'click' : function(node) {  
                 Ext.getCmp('fileGrid').getStore().removeAll();
                		//隐藏按钮
                		Ext.getCmp('newFolderID').setDisabled(true);
				      	Ext.getCmp('fileGrid').gridContextMenu.items.get(0).disable();
				      	Ext.getCmp('deleteFilesID').setDisabled(true);
				      	Ext.getCmp('fileGrid').gridContextMenu.items.get(3).disable();
				      	Ext.getCmp('uploadID').setDisabled(true);
				      	Ext.getCmp('fileGrid').gridContextMenu.items.get(4).disable(); 
				      	Ext.getCmp('renameID').setDisabled(true);
				      	Ext.getCmp('fileGrid').gridContextMenu.items.get(6).disable(); 
				      	Ext.getCmp('shareID').setDisabled(true);
				      	Ext.getCmp('fileGrid').gridContextMenu.items.get(7).disable(); 
                        this.path = node; 
                        Ext.getCmp('fileGrid').isShare = 'true';
                        var hd = Ext.fly(Ext.getCmp('fileGrid').getView().innerHd).child('div.x-grid3-hd-checker');  
					      if(hd.hasClass('x-grid3-hd-checker-on')) {
					        hd.removeClass('x-grid3-hd-checker-on');
					      }
                } 
            }
		});
	}
});

