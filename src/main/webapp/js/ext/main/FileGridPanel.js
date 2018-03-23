    var folderCount = new Ext.Toolbar.TextItem('文件夹: 0');
    var fileCount = new Ext.Toolbar.TextItem('文件: 0');
    var usedSpace = new Ext.Toolbar.TextItem('已用：0 M');
    var freeSpace = new Ext.Toolbar.TextItem('剩余：0 M');
    var usedSpaceVal;
    var freeSpaceVal;
    var totalSpaceVal;


FileGridPanel = Ext.extend(Ext.grid.GridPanel, {
    id : 'fileGrid',
	sm : null,
	ds : null,
	path : null,
	isShare : 'false',
	currentPath : '',//保存当前浏览的文件路径
	actions : null,
	gridContextMenu : null,
	constructor : function() {
		this.actions = [
			new Ext.Action({
				text : '向上',
				iconCls : 'db-icn-back',
				handler : function() {
					this.listParentFiles();
				},
				scope : this
			}),
			new Ext.Action({
				text : '我的电脑',
				iconCls : 'db-icn-world',
				handler : function() {
					this.listRootFiles();
				},
				scope : this
			}),
			new Ext.Action({
				text : '新建文件夹',
				id : 'newFolderID',
				iconCls : 'db-icn-folder-new',
				handler : function(){
					this.createFolder();
				},
				scope : this
			}),
			new Ext.Action({
				text : '删除',
				id : 'deleteFilesID',
				iconCls : 'db-icn-delete',
				handler : function(){
					this.deleteFiles();
				},
				scope : this
			}),
			new Ext.Action({
				text : '压缩',
				iconCls : 'db-icn-folder-zip',
				handler : function(){
					this.compressionFiles();
				},
				scope : this
			}),
			new Ext.Action({
				text : '解压缩',
				handler : function(){
					this.decompressionFiles();
				},
				scope : this
			}),
			new Ext.Action({
				text : '刷新',
				iconCls : 'db-icn-refresh',
				handler : function(){
					this.refresh();
				},
				scope : this
			}),
			new Ext.Action({
				text : '上传',
				id : 'uploadID',
				iconCls : 'db-icn-upload',
				handler : function() {
					this.showUploadDialog();
				},
				scope : this
			}),
			new Ext.Action({
			    id : 'downloadID',
				text : '下载',
				iconCls : 'db-icn-download',
				handler : function(){
					this.download(this.isShare);
				},
				scope : this
			}),
			new Ext.Action({
				text : '剪切',
				iconCls : 'db-icn-cut',
				handler : function(){
					Ext.Msg.alert('温馨提示','!');
				},
				scope : this
			}),
			new Ext.Action({
				text : '复制',
				iconCls : 'db-icn-copy',
				handler : function(){
					Ext.Msg.alert('温馨提示','!');
				},
				scope : this
			}),
			new Ext.Action({
				text : '粘贴',
				iconCls : 'db-icn-paste',
				handler : function(){
					Ext.Msg.alert('温馨提示','!');
				},
				scope : this
			}),
			new Ext.Action({//index = 12
			    id : 'renameID',
				text : '重命名',
				iconCls : 'db-icn-rename',
				handler : function(){
					this.rename();
				},
				scope : this
			}),
			new Ext.Action({//index = 13
			    id : 'shareID',
				text : '共享/取消',
				iconCls : 'db-icn-folder-share',
				handler : function(){
					this.shareFolder();
				},
				scope : this
			})
		];
		
		this.gridContextMenu = new Ext.menu.Menu({
			items : [
				//this.actions[2],this.actions[8],'-',
				//this.actions[4],this.actions[5],'-',
				//this.actions[9],this.actions[10],this.actions[11],'-',
				//this.actions[3],this.actions[12]
				this.actions[2],this.actions[6],'-',
				this.actions[3],//this.actions[9],this.actions[10],this.actions[11],'-',
				this.actions[7],this.actions[8],this.actions[12],this.actions[13]
			]
		});
		
		//	重写SectionModel	
		if (Ext.grid.CheckboxSelectionModel) {
   			var interceptOnMouseDown = Ext.grid.CheckboxSelectionModel.prototype.onMouseDown.createInterceptor(function(e, t){
 		   
			 if (this.hd == null && t.className == 'x-grid3-hd-checker') {   
			            this.hd = t.parentNode;    
			            this.on('rowdeselect', this.offHdChecker, this);   
			        }   
			 		  	this.on('rowdeselect', this.handleDeselect, this);
			   			this.on('rowselect', this.handleSelect, this);    
			  } 
			 );
			 Ext.override(Ext.grid.CheckboxSelectionModel, {
			  hd : null,     
			    onMouseDown : interceptOnMouseDown, 
			     offHdChecker : function() {
			            if (this.hd == null)   
			                return;    
			            var hd = Ext.fly(this.hd);   
			            var isChecked = hd.hasClass('x-grid3-hd-checker-on');   
			            if(isChecked)   
			                hd.removeClass('x-grid3-hd-checker-on');   
			        },     
			    handleSelect:function(){
				      if(this.selections.length >1) {
				      	Ext.getCmp('downloadID').setDisabled(true);
				      	Ext.getCmp('fileGrid').gridContextMenu.items.get(5).disable();
				      	if(Ext.getCmp('fileGrid').isShare=='false') {
					      	Ext.getCmp('renameID').setDisabled(true);
					      	Ext.getCmp('fileGrid').gridContextMenu.items.get(6).disable();
					      	Ext.getCmp('shareID').setDisabled(true);
					      	Ext.getCmp('fileGrid').gridContextMenu.items.get(7).disable();
				      	}
				      }
				      
				      if(this.grid.store.getCount()!=this.selections.length)
				             return; 
				      var hd = Ext.fly(this.grid.getView().innerHd).child('div.x-grid3-hd-checker'); 
				      if(!hd.hasClass('x-grid3-hd-checker-on'))   
				      hd.addClass('x-grid3-hd-checker-on'); 
			    },    
			    handleDeselect:function(){
				    if(this.selections.length <= 1) {
				      	Ext.getCmp('downloadID').setDisabled(false);
				      	Ext.getCmp('fileGrid').gridContextMenu.items.get(5).enable();
				      	if(Ext.getCmp('fileGrid').isShare=='false') {
					      	Ext.getCmp('renameID').setDisabled(false);
					      	Ext.getCmp('fileGrid').gridContextMenu.items.get(6).enable();
					      	Ext.getCmp('shareID').setDisabled(false);
					      	Ext.getCmp('fileGrid').gridContextMenu.items.get(7).enable();
				      	}
				      }
				    if( this.grid.store.getCount() != this.selections.length +1)
				    return; 
				      var hd = Ext.fly(this.grid.getView().innerHd).child('div.x-grid3-hd-checker');  
				      if(hd.hasClass('x-grid3-hd-checker-on')) 
				       hd.removeClass('x-grid3-hd-checker-on');
			    }
			}); 
		}

		function thumbnail (data, metadata, record, rowIndex, columnIndex, store){  
            var url = store.getAt(rowIndex).get('bannerUrl');  
            var fileName = store.getAt(rowIndex).get('fileName');  
            //qtitle标题 qtip:内容  
            var img = "<img src='"+url+"/"+fileName+ "' width='300' height='200'>";  
            var displayText  = '<div ext:qtitle="" ext:qtip="'+img+'">'+data+'</div>';    
            return displayText;    
        }  
		
		this.sm = new Ext.grid.CheckboxSelectionModel(), 
		this.ds = new Ext.data.Store({
			url : 'getFiles.do',
			reader : new Ext.data.JsonReader({totalProperty : 'totalProperty',root : 'root'}, 
				 [{name : 'id',type : 'string'},
			 	 {name : 'fileName',type : 'string'}, 
			 	 {name : 'lastModifyDate'}, 
			     {name : 'leaf',type : 'bool'}, 
			     {name : 'share',type : 'bool'}, 
				 {name : 'fileSize',type : 'string'}]
			),
			sortInfo : { 
				field : 'fileSize'
			}
		}),
		
		FileGridPanel.superclass.constructor.call(this, {
			title : '文件管理',
			loadMask : {
				msg : '列表加载中...'
			},
			columns : [this.sm, {
				header : '类型',
				width : 70,
				dataIndex : 'id',
				//menuDisabled : true,
				//resizable : false,
				sortable : true,
				renderer : this.formatIcon
			}, {
				header : '名称',
				width : 150,
				dataIndex : 'fileName',
				id : 'fileName',
				sortable : true
			}, {
				header : '修改日期',
				width : 150,
				dataIndex : 'lastModifyDate',
				sortable : true,
				renderer : this.formatDate
			}, {
				header : '大小',
				width : 150,
				dataIndex : 'fileSize',
				sortable : true ,
				renderer : this.formatSize
			}],
			
			autoExpandColumn : 'fileName',
			ds : this.ds,
			sm : this.sm,

			tbar : [
				//this.actions[0],
				this.actions[2],this.actions[6],'-',
				this.actions[3],//this.actions[9],this.actions[10],this.actions[11],'-',
				this.actions[7],this.actions[8],this.actions[12],this.actions[13]

			],
		
			//状态栏	
	        bbar: new Ext.StatusBar({
	            id: 'file-status',
	            // values to set initially:   
	            text: '',   
	            
	            // These are just the standard toolbar TextItems we created above.  They get
	            // custom classes below in the render handler which is what gives them their
	            // customized inset appearance.
	            items: [usedSpace, ' ', freeSpace, ' ',folderCount, ' ', fileCount, ' ']//, clock, ' ']
	        }),
 
			listeners : {
				'rowclick' : this.onRowClick,
				'rowdblclick' : this.onRowDblClick,
				'rowcontextmenu' : this.onRowContextMenu,
				'contextmenu' : function(e){e.stopEvent();},
				'render': {
                fn: function(){
                    // Add a class to the parent TD of each text item so we can give them some custom inset box 
                    // styling. Also, since we are using a greedy spacer, we have to add a block level element
                    // into each text TD in order to give them a fixed width (TextItems are spans).  Insert a
                    // spacer div into each TD using createChild() so that we can give it a width in CSS.
                    Ext.fly(usedSpace.getEl().parentNode).addClass('x-status-text-panel').createChild({cls:'spacer'});
                    Ext.fly(freeSpace.getEl().parentNode).addClass('x-status-text-panel').createChild({cls:'spacer'});
                    Ext.fly(folderCount.getEl().parentNode).addClass('x-status-text-panel').createChild({cls:'spacer'});
                    Ext.fly(fileCount.getEl().parentNode).addClass('x-status-text-panel').createChild({cls:'spacer'});
                }
            },
				scope : this
			}
		});
	},
	
	download : function(){
		var records = this.getSelectionModel().getSelections();
		if(records.length <= 0){
			Ext.Msg.alert('操作提示','请选择一个文件或者文件夹');
		} else {
			var tempPath=this.currentPath.substring(1);
			if(tempPath=='*') {
				tempPath='';
			}
			if(records.length == 1 && records[0].data.leaf){
				window.open(encodeURI('download.do?path=' 
					+ tempPath 
			     	+ '&name=' + records[0].data.fileName
			     	+ '&isShare=' + this.isShare));
				/*var mask = new Ext.LoadMask(this.el, {msg:"请求下载中,请稍等..."});
				mask.show();
				
				Ext.Ajax.request({
					url : 'download.action',
					params: {
						path: this.currentPath.substring(1),
						name: records[0].data.fileName
					},
					callback : function(options,success,response){
						alert(response.responseText);
						//var result = Ext.util.JSON.decode(response.responseText);
						//if(!result.success){
							
						//}
						mask.hide();								
					}
				});*/
			} else {
				Ext.Msg.alert("温馨提示","请选中文件");
				return;
				Ext.Msg.confirm('操作提示','是否打包下载?',function(){
					var paths = [];
					for(var i = 0; i < records.length; i++){
						paths.push(records[i].data.id);
					}
					window.location = 'downloadAll.action?paths=' + paths + '&path=' + this.currentPath;
					var mask = new Ext.LoadMask(this.el, {msg:"请求下载中,请稍等..."});
					mask.show();
					
					Ext.Ajax.request({
						url : 'downloadAll.action',
						params: {
							paths: paths,
							path: path
						},
						callback : function(options,success,response){
							var result = Ext.util.JSON.decode(response.responseText);
							if(!result.success){
								
							}
							mask.hide();								
						}
					});
				},this);
			}
		}
	},
	
	compressionFiles : function(){
		var records = this.getSelectionModel().getSelections();
		if(records.length <= 0){
			Ext.Msg.alert('操作提示','至少选择一个文件！');
		}else{
			var paths = [];
			for(var i = 0; i < records.length; i++){
				paths.push(records[i].data.id);	
			}
			var mask = new Ext.LoadMask(this.el, {msg:"压缩中,请稍等..."});
			mask.show();
			Ext.Ajax.request({
				timeout : 60000,
				url : 'compressionFiles.action',
				callback : function(options,success,response){
					var result = Ext.util.JSON.decode(response.responseText);
					if(!result.success){
						Ext.Msg.show({
							title : '错误提示',
							msg : '文件压缩错误!',
							buttons : Ext.Msg.OK,
							icon : Ext.Msg.ERROR
						});		
					}else{
						this.refresh();
					}
					mask.hide();
				},
				params : {
					paths : paths,
					path : this.currentPath
				},
				scope : this
			});
		}
	},
	decompressionFiles : function(){
		var records = this.getSelectionModel().getSelections();
		if(records.length <= 0){
			Ext.Msg.alert('操作提示','至少选择一个文件!',function(){
				return;
			});	
		}else{
			var paths = [];
			for(var i = 0; i< records.length; i++){
				paths.push(records[i].data.id);	
			}
			var mask = new Ext.LoadMask(this.el, {msg:"解压缩中,请稍等..."});
			mask.show();
			Ext.Ajax.request({
				url : 'decompressionFiles.action',
				callback : function(options,success,response){
					var result = Ext.util.JSON.decode(response.responseText);
					if(!result.success){
						Ext.Msg.show({
							title : '错误提示',
							msg : '文件解压缩错误!',
							buttons : Ext.Msg.OK,
							icon : Ext.Msg.ERROR
						});		
					}else{
						this.refresh();
					}
					mask.hide();
				},
				params : {
					paths : paths,
					path : this.currentPath
				},
				scope : this
			});
		}
	},
	deleteFiles : function(){
		var records = this.getSelectionModel().getSelections();
		if(records.length <= 0){
			Ext.Msg.alert('操作提示','至少选择一个文件或者文件夹！',function(){
				return;
			});
		}else{
		    var deleteSize = 0;
			Ext.Msg.confirm('操作确认','您确实要删除这些文件?',function(btn){
				if(btn == 'yes'){
					var paths = [];
					for(var i = 0; i < records.length; i++){
						paths.push(records[i].data.id);
						deleteSize = eval(deleteSize + eval(records[i].data.fileSize));
					}
					//Ext.Msg.alert(records[0].data.fileSize);	
					var mask = new Ext.LoadMask(this.el, {msg:"文件删除中,请稍等..."});
					mask.show();
					Ext.Ajax.request({
						url : 'deleteFiles.do',
						callback : function(options,success,response){
							var result = Ext.util.JSON.decode(response.responseText);
							/*if(!result.success){
								Ext.Msg.show({
									title : '错误提示',
									msg : '删除文件错误!',
									buttons : Ext.Msg.OK,
									icon : Ext.Msg.ERROR
								});	
							}*/
							if(success){
								this.refresh();	
								if(Ext.getCmp('fileTree').path==null) 
									Ext.getCmp('fileTree').root.reload();
								else 
									Ext.getCmp('fileTree').path.reload();
								Ext.getCmp('shareTree').root.reload();
								var hd = Ext.fly(Ext.getCmp('fileGrid').getView().innerHd).child('div.x-grid3-hd-checker');  
							      if(hd.hasClass('x-grid3-hd-checker-on')) {
							        hd.removeClass('x-grid3-hd-checker-on');
							    }
							    //更新状态栏的文件数量
							    var countFile = this.getStore().getCount();
						       	j = 0;
								for(i=0 ; i<countFile ; i++) {
									if(this.getStore().getAt(i).data.leaf) {
										j++;
									}
								}
								Ext.fly(fileCount.getEl()).update('文件: '+ eval(j-records.length));
							    //磁盘空间
							    usedSpaceVal = (result.usedSpace)*1024*1024;
							    freeSpaceVal = eval(totalSpaceVal - usedSpaceVal);
								Ext.fly(usedSpace.getEl()).update('已用: '+ Ext.util.Format.fileSize(usedSpaceVal));
								Ext.fly(freeSpace.getEl()).update('剩余: '+ Ext.util.Format.fileSize(freeSpaceVal));
							}
							mask.hide();
						},
						params : {
							paths : paths
						},
						scope : this
					});
				}
			},this);
		}
	},
	createFolder : function(){
		Ext.Msg.prompt('新建文件夹','请输入文件夹名称',function(btn, text){
		    if (btn == 'ok'){
		    if (text.length > 8) {
		    	Ext.Msg.alert('操作提示','文件夹名称不能大于8个字符！',function(){
				return;
				});
		    } else {
				Ext.Ajax.request({
					url : 'createFolder.do',
					callback : function(options, success, response) {
						var result = Ext.util.JSON.decode(response.responseText);
						if (result.success) {
							this.refresh();
							if(Ext.getCmp('fileTree').path==null) 
								Ext.getCmp('fileTree').root.reload();
							else 
								Ext.getCmp('fileTree').path.reload();
						} else {
							Ext.Msg.show({
							   title:'错误提示',
							   msg: '新建文件夹操作失败. 请重试!',
							   buttons: Ext.Msg.OK,
							   icon: Ext.Msg.ERROR
							});
						}
					},
					params : {
						path : this.currentPath,
						name : text
					},
					scope : this
				});
		    }
		  }
		},this);
	},
	showUploadDialog : function(){
		var url = "netdisk_fileupload.jsp?t="+Math.random()+"&path="+encodeURI(this.currentPath);
		var win = new Ext.Window({
			  title : '上传文件',
			  width : 330,
			  height : 200,
			  id:'netdiskframepanel',
			  isTopContainer : true,
			  modal : true,
			  resizable : false,
			  listeners: {   
				    close:function(){
				    	Ext.getCmp('fileGrid').getStore().reload();
				    	Ext.getCmp('shareTree').root.reload();
						//更新剩余和已用空间		
					    Ext.Ajax.request({
					    url:'getPersonalSpace.do',
						success: function(resp,opts) {
				 				var respText = Ext.util.JSON.decode(resp.responseText);                     
								usedSpaceVal = respText.usedSpace*1024*1024;
				    			freeSpaceVal = eval(respText.totalSpace - respText.usedSpace)*1024*1024;
				    			totalSpaceVal = eval(respText.totalSpace*1024*1024);
				    			var fileCountq = eval(respText.fileCount + j);
								Ext.fly(usedSpace.getEl()).update('已用: '+Ext.util.Format.fileSize(usedSpaceVal));
								Ext.fly(freeSpace.getEl()).update('剩余: '+ Ext.util.Format.fileSize(freeSpaceVal));
				           }
						});
				    }
			   },
			   contentEl : Ext.DomHelper.append(document.body, {
			   tag : 'iframe',
			   id:'netdiskiframe',
			   style : "border 0px none;scrollbar:true",
			   src : url,
			   height : "100%",
			   width : "100%"
			  })
		 })
		 win.show(); 
	},
	refresh : function(){
		this.getStore().reload();
		var hd = Ext.fly(Ext.getCmp('fileGrid').getView().innerHd).child('div.x-grid3-hd-checker');  
			      if(hd.hasClass('x-grid3-hd-checker-on')) {
			        hd.removeClass('x-grid3-hd-checker-on');
			      }
	},
	listRootFiles : function() { // 根目录
		this.listFiles();
	},
	listParentFiles : function() { // 向上目录
		if (this.getStore().getCount() > 0) {// 有记录才继续
			var orgPath = this.getStore().getAt(0).data.id
			var path = '\\' + orgPath;// 获得路径
			var index = -1;
			for (var i = 0; i < 2; i++) {
				index = path.lastIndexOf('\\');
				if (index != -1) {
					path = path.substring(0, index);
				}
			}
			index = orgPath.lastIndexOf('\\');
			if (index != -1) {
				this.listFiles(path);// 获得上一级文件夹名称
			}
		} else {// 如果是空文件夹
			if (this.currentPath.length > 0) {
				var index = this.currentPath.lastIndexOf('\\');
				if (index != -1) {
					this.currentPath = this.currentPath.substring(0, index);
					this.listFiles(this.currentPath);
				} else {
					this.listFiles();
				}
			}
		}
	},
	listFiles : function(_node) { // 获得文件夹中文件列表
	
		this.currentPath = '/' + (Ext.isEmpty(_node) ? '*' : _node);
		this.getStore().load({
		
			params : {
				'node' : _node,
				'isShare' : this.isShare
			},
			//设置状态栏
			callback : function() {
				var sb = Ext.getCmp('file-status');
				if(_node == '*') 
				       _node = '';
			   if(Ext.getCmp('fileGrid').isShare == 'false') {
		            sb.setStatus({
		                text: '我的文件柜'+_node
		        	});
	        	} else {
	        		sb.setStatus({
		                text: ''
		        	});
	        	}
	        	var countFile = this.getCount();
	        	j = 0;
				for(i=0 ; i<countFile ; i++) {
					if(!this.getAt(i).data.leaf) {
						j++;
					}
				}
				Ext.fly(folderCount.getEl()).update('文件夹: '+j);
				Ext.fly(fileCount.getEl()).update('文件: '+ eval(this.getCount()-j));
			}
			
		});
	},
	shareFolder : function() {
	
	var records = this.getSelectionModel().getSelections();
		if(records.length ==0) {
			Ext.Msg.alert('操作提示','请选择一个文件夹共享');
			return ;
		}
		if(records.length ==1 && records[0].data.leaf){
			Ext.Msg.alert('操作提示','只能选择文件夹共享');
			return;
		} else {
			
			var folderName = records[0].data.fileName
			var shareFolderPath = Ext.getCmp('file-status').text+'\\'+records[0].data.fileName;
			
			var url = encodeURI(encodeURI("netdisk_sharefolder.jsp?folderName="+folderName+"&folderPath="+shareFolderPath));
			 var win = new Ext.Window({
			  title : '共享文件夹',
			  // maximizable : true,
			  // maximized : true,
			  width : 780,
			  height : 470,
			  // autoScroll : true,
			  // bodyBorder : true,
			  // draggable : true,
			  id:'netdiskframepanel',
			  isTopContainer : true,
			  modal : true,
			  resizable : false,
			  listeners: {   
				    close:function(){   
				    	Ext.getCmp('fileGrid').getStore().reload();
				        Ext.getCmp('shareTree').root.reload();
				    }
			   		
			   },
			  contentEl : Ext.DomHelper.append(document.body, {
			   tag : 'iframe',
			   id:'netdiskiframe',
			   style : "border 0px none;scrollbar:true",
			   src : url,
			   height : "100%",
			   width : "100%"
			  })
			 })
			 win.show(); 
		}
	},
	rename : function(){
	  var records=this.getSelectionModel().getSelections();
	  if(records.length <= 0){
			Ext.Msg.alert('操作提示','请选择一个文件或者文件夹！',function(){
				return;
			});
		}else{
		 	Ext.Msg.prompt('重命名','请输入文件夹名称',function(btn, text){
			var paths = [];
					for(var i = 0; i < records.length; i++){
						paths.push(records[i].data.id);	
						
					}
		    if (btn == 'ok'){
		    if (text.length > 8) {
		    	Ext.Msg.alert('操作提示','文件夹名称不能大于8个字符！',function(){
				return;
				});
		    } else {
				Ext.Ajax.request({
					url : 'renameFolder.do',
					callback : function(options, success, response) {
						var result = Ext.util.JSON.decode(response.responseText);
						if (result.success) {
							this.refresh();
							if(Ext.getCmp('fileTree').path==null) 
								Ext.getCmp('fileTree').root.reload();
							else 
								Ext.getCmp('fileTree').path.reload();
							Ext.getCmp('shareTree').root.reload();
						}else{
						Ext.Msg.show({
						title:'提示错误',
						msg:'重命名失败，请重试',
						buttons: Ext.Msg.OK,
					    icon: Ext.Msg.ERROR	
					    }		
						)};			
				},		
			params:{
						paths : paths,
						path : this.currentPath,
						name : text
					},
					scope : this
				});
			 }
		    }
		},this);
	}
	
   },
	
	formatIcon : function(_v, cellmeta, record){
		if(!record.data.leaf){
			if(!record.data.share) {
				return '<div class="db-icn-folder-collapsed" style="height: 16px;background-repeat: no-repeat;"/>' +
						'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;文件夹</div>';
			} else {
				return '<div class="db-ft-folder-shared-small" style="height: 16px;background-repeat: no-repeat;"/>' +
						'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;文件夹</div>';
			}
		}
		
		var extensionName = '';
		var returnValue = '';
		var index = _v.lastIndexOf('.');
		if(index == -1){
			return '<div class="db-ft-unknown-small" style="height: 16px;background-repeat: no-repeat;"/>' +
					'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' + extensionName.toUpperCase() + '</div>';
		}else{
			extensionName = _v.substring(index + 1);
			extensionName = extensionName == "html" ? "htm" : extensionName;
			extensionName = extensionName == "php" ? "htm" : extensionName;
			extensionName = extensionName == "jsp" ? "htm" : extensionName;
			var css = '.db-ft-' + extensionName.toLowerCase() + '-small';
			if(Ext.isEmpty(Ext.util.CSS.getRule(css),true)){
				returnValue = '<div class="db-ft-unknown-small" style="height: 16px;background-repeat: no-repeat;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' 
								+ extensionName.toUpperCase() + '</div>';
			}else{
				returnValue = '<div class="db-ft-' + extensionName.toLowerCase() + '-small" style="height: 16px;background-repeat: no-repeat;"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' 
								+ extensionName.toUpperCase();	+ '</div>';
			}
			return returnValue;	
		}
	},
	formatDate : function(_v) {
		return _v.replace(/T/g, ' ');
	},
	formatSize : function(_v) {
		if(_v=="") return "";
		return Ext.util.Format.fileSize(_v)
	},
	onEnterForSearch : function() {
		Ext.Msg.alert('温馨提示','!');
	},
	onRowClick : function(grid,rowIndex,e){
		
	},
	onRowDblClick : function(grid, rowIndex) {
	    var hd = Ext.fly(this.getView().innerHd).child('div.x-grid3-hd-checker');  
					      if(hd.hasClass('x-grid3-hd-checker-on')) {
					        hd.removeClass('x-grid3-hd-checker-on');
		}
		if (!grid.getStore().getAt(rowIndex).data.leaf) {// 如果文件夹才获访问
			grid.listFiles(grid.getStore().getAt(rowIndex).data.id);
			if(Ext.getCmp('fileTree').path!=null){
			 Ext.getCmp('fileTree').path.expand(false,false);
			}
		} else {
			var extensionName = '';
			var path = grid.getStore().getAt(rowIndex).data.id;
			var index = path.lastIndexOf('.')
			if(index == -1){
				this.download();
			}else{
				extensionName = path.substring(index + 1);
				if(extensionName =='png' || extensionName=='jpg'|| extensionName=='gif'|| extensionName=='bmp') {
			    	 var url = "netdisk_img_show.jsp?path="+encodeURI(path)+'&isShare=' + this.isShare;
			    	 window.open(url);
				} else {
					this.download();
				}
			}
		}
	},
	onRowContextMenu : function(grid, index, e){
		e.stopEvent();
		if (this.getSelectionModel().isSelected(index) !== true) {
			this.getSelectionModel().clearSelections();
			this.getSelectionModel().selectRow(index);
			this.fireEvent('rowclick',grid,index,e);
		}
		this.gridContextMenu.showAt(e.getXY());
	}
});
