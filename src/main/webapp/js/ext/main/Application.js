
Application = Ext.extend(Ext.Viewport, {
	fileGridPanel : null, // 文件类表
	fileTreePanel : null, // 树导航
	shareTreePanel : null,
	constructor : function(_cfg) {
		this.fileGridPanel = new FileGridPanel();
		this.fileTreePanel = new FileTreePanel();
		this.shareTreePanel = new ShareTreePanel();
		this.fileTreePanel.selectPath(Ext.getCmp('fileTree').root.id);
		this.fileTreePanel.root.expand(false,false);
		this.fileGridPanel.listFiles("*");
		this.fileTreePanel.on('click', function(_node) {
			this.fileGridPanel.listFiles(_node.id); // 左边树单击右边网格同步
		}, this);
		
		this.shareTreePanel.on('click', function(_node) {
			if(_node.id == '*') return;
			this.fileGridPanel.listFiles(_node.id); // 左边树单击右边网格同步
		}, this);
		Application.superclass.constructor.call(this, {
	
			layout : 'border',
			items : [{
				xtype : 'box',
				region : 'north',
				applyTo : 'header',
				height : 0
			}, {
				title : '文件目录',
				region : 'west',
				layout : 'anchor',
				margins : '0 0 0 0',
				width : 275,
				Height : 100,
				minSize : 0,
				maxSize : 350,
				//collapsible : true,
				split : true,
				items : [
				   this.fileTreePanel, 
				   this.shareTreePanel
				]
			}, 
			{
				region : 'center',
				layout : 'card',
				margins : '0 0 0 0',
				activeItem : 0,
				border : false,
				items : [this.fileGridPanel]
			}]

		});
	}
});

Ext.onReady(function() {
	Ext.BLANK_IMAGE_URL = '../../js/ext/resources/images/default/s.gif';
	new Application({
	});
});

Ext.Ajax.request({
	url:'getPersonalSpace.do',
	success: function(resp,opts) {
		var respText = Ext.util.JSON.decode(resp.responseText);                     
		usedSpaceVal = respText.usedSpace*1024*1024;
		freeSpaceVal = eval(respText.totalSpace - respText.usedSpace)*1024*1024;
		totalSpaceVal = eval(respText.totalSpace*1024*1024);
		Ext.fly(usedSpace.getEl()).update('已用: '+Ext.util.Format.fileSize(usedSpaceVal));
		Ext.fly(freeSpace.getEl()).update('剩余: '+ Ext.util.Format.fileSize(freeSpaceVal));
			
	}
});
