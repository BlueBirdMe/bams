FileTreePanel = Ext.extend(Ext.tree.TreePanel, {
    id : 'fileTree',
    path : null,
	constructor : function(_cfg) {
		Ext.apply(this, _cfg);
		FileTreePanel.superclass.constructor.call(this, {
			region : 'center',
			split : true,
			margins : '-1 -1 2 -1',
			height:251,
			autoScroll : true,
			lines : true,
			loader : new Ext.tree.TreeLoader({
				dataUrl : 'getDirectories.do'
			
			}),
			root : new Ext.tree.AsyncTreeNode({
				id : '*',
				iconCls : 'db-icn-world',
				text : '我的文件柜'
			}),
			
			listeners : {  
                'click' : function(node) { 
                        //显示按钮
                		Ext.getCmp('newFolderID').setDisabled(false);
				      	Ext.getCmp('fileGrid').gridContextMenu.items.get(0).enable();
				      	Ext.getCmp('deleteFilesID').setDisabled(false);
				      	Ext.getCmp('fileGrid').gridContextMenu.items.get(3).enable();
				      	Ext.getCmp('uploadID').setDisabled(false);
				      	Ext.getCmp('fileGrid').gridContextMenu.items.get(4).enable(); 
				      	Ext.getCmp('downloadID').setDisabled(false);
				      	Ext.getCmp('fileGrid').gridContextMenu.items.get(5).enable(); 
				      	Ext.getCmp('renameID').setDisabled(false);
				      	Ext.getCmp('fileGrid').gridContextMenu.items.get(6).enable(); 
				      	Ext.getCmp('shareID').setDisabled(false);
				      	Ext.getCmp('fileGrid').gridContextMenu.items.get(7).enable();   
                        this.path = node; 
                        Ext.getCmp('fileGrid').isShare = 'false';
                        var hd = Ext.fly(Ext.getCmp('fileGrid').getView().innerHd).child('div.x-grid3-hd-checker');  
					      if(hd.hasClass('x-grid3-hd-checker-on')) {
					        hd.removeClass('x-grid3-hd-checker-on');
					      }
                } 
            }
		});
	}
});

