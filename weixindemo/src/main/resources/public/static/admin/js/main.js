layui.use(['layer', 'form', 'element', 'jquery', 'dialog','layim'], function(layer, form, element, jquery, dialog,layim) {
	var layer = layer;
	var element = element();
	var form = form();
	var $ = jquery;
	var dialog = dialog;
	var hideBtn = $('#hideBtn');
	var mainLayout = $('#main-layout');
	var mainMask = $('.main-mask');
	//监听导航点击
	element.on('nav(leftNav)', function(elem) {
		var navA = $(elem).find('a');
		var id = navA.attr('data-id');
		var url = navA.attr('data-url');
		var text = navA.attr('data-text');
		if(!url){
			return;
		}
		var isActive = $('.main-layout-tab .layui-tab-title').find("li[lay-id=" + id + "]");
		if(isActive.length > 0) {
			//切换到选项卡
			element.tabChange('tab', id);
		} else {
			element.tabAdd('tab', {
				title: text,
				content: '<iframe src="' + url + '" name="iframe' + id + '" class="iframe" framborder="0" data-id="' + id + '" scrolling="auto" width="100%"  height="100%"></iframe>',
				id: id
			});
			element.tabChange('tab', id);
			
		}
		mainLayout.removeClass('hide-side');
	});
	//监听导航点击
	element.on('nav(rightNav)', function(elem) {
		var navA = $(elem).find('a');
		var id = navA.attr('data-id');
		var url = navA.attr('data-url');
		var text = navA.attr('data-text');
		if(!url){
			return;
		}
		var isActive = $('.main-layout-tab .layui-tab-title').find("li[lay-id=" + id + "]");
		if(isActive.length > 0) {
			//切换到选项卡
			element.tabChange('tab', id);
		} else {
			element.tabAdd('tab', {
				title: text,
				content: '<iframe src="' + url + '" name="iframe' + id + '" class="iframe" framborder="0" data-id="' + id + '" scrolling="auto" width="100%"  height="100%"></iframe>',
				id: id
			});
			element.tabChange('tab', id);
			
		}
		mainLayout.removeClass('hide-side');
	});
	//菜单隐藏显示
	hideBtn.on('click', function() {
		if(!mainLayout.hasClass('hide-side')) {
			mainLayout.addClass('hide-side');
		} else {
			mainLayout.removeClass('hide-side');
		}
	});
	//遮罩点击隐藏
	mainMask.on('click', function() {
		mainLayout.removeClass('hide-side');
	});
	var app = {
		currentUrl:"/weixin/chatService/getCurrentUserInfo",
		webSocketUrl:"ws://47.104.189.235/weixin/weChat/server/",
		mineChatObj : null,
		mine : null,
		chatMensMap:{},
		chatMens:[],
		initPage:function(){
			var that = this;
			that.initData();
			this.initEvent();
		},
		initData:function(){
			var that = this;
			$.get(that.currentUrl, function(data){
				  var result = JSON.parse(data);
				  if(result.mine){
					  that.mine = result.mine;
					  that.mineChatObj = layim.config({
					    brief: true, //是否简约模式（如果true则不显示主面板）
					    "mine":that.mine
					  });
					  that.connectWebSocket();
				  }
			});  
		},
		connectWebSocket:function(){
			var that = this;
			 // 初始化一个 WebSocket 对象 /weChat/{role}/{userid}
			 that.ws = new WebSocket(that.webSocketUrl+that.mine.id);
				
				// 建立 web socket 连接成功触发事件
			 that.ws.onopen = function () {
				  // 使用 send() 方法发送数据
					that.onopen();
				};
				
				// 接收服务端数据时触发事件
				that.ws.onmessage = function (evt) {
				  var received_msg = evt.data;
				  that.onmessage(received_msg);
				};
				
				// 断开 web socket 连接成功触发事件
				that.ws.onclose = function () {
				  that.onclose();
				};
		},
		initEvent: function(){
			var that = this;
			$('#mineChat').click(function(){
				that.showDialog();
			});
			
			layim.on('sendMessage', function(res){
				  var mine = res.mine; //包含我发送的消息及我的信息
				  var to = res.to
				  debugger;
				  that.sendMessage({
					  userid:to.id,
					  message:mine.content,
				  });
			});
		},
		
		showDialog:function(data){
			var that = this;
			that.chatMens.forEach(function(val) {
				that.mineChatObj.chat(val);
			});
		},
		
		onopen:function(){
			var that = this;
		},
		
		onmessage : function(data){
			var that = this;
			var result = JSON.parse(data);
			if(!that.chatMensMap[result.id]){
				that.chatMens.push({
						name: result.username
					    ,type: 'friend'
					    ,avatar: result.avatar
					    ,id: result.id
				});
				that.chatMensMap[result.id]=[];
			}
			that.chatMensMap[result.id].push(result);
			layim.getMessage({
				  username: result.username //消息来源用户名
				  ,avatar: result.avatar //消息来源用户头像
				  ,id: result.id //消息的来源ID（如果是私聊，则是用户id，如果是群聊，则是群组id）
				  ,type: "friend" //聊天窗口来源类型，从发送消息传递的to里面获取
				  ,content: result.message //消息内容
				 // ,cid: 0 //消息id，可不传。除非你要对消息进行一些操作（如撤回）
				  ,mine: false //是否我发送的消息，如果为true，则会显示在右方
				  ,fromid:  result.id //消息的发送者id（比如群组中的某个消息发送者），可用于自动解决浏览器多窗口时的一些问题
				  ,timestamp: new Date().getTime() //服务端时间戳毫秒数。注意：如果你返回的是标准的 unix 时间戳，记得要 *1000
			});
		},
		
		onclose: function(){
			var that = this;
		},
		
		sendMessage : function(res){
			var that = this;
			that.ws.send(JSON.stringify(res));
		},
		
		
	}
	app.initPage();
	
	$('#loginout').click(function(){
		$.ajax({
			url:'webUser/loginout',
			async:false,
		})				
	})

	//示范一个公告层
//	layer.open({
//		  type: 1
//		  ,title: false //不显示标题栏
//		  ,closeBtn: false
//		  ,area: '300px;'
//		  ,shade: 0.8
//		  ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
//		  ,resize: false
//		  ,btn: ['火速围观', '残忍拒绝']
//		  ,btnAlign: 'c'
//		  ,moveType: 1 //拖拽模式，0或者1
//		  ,content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49; color: #fff; font-weight: 300;">后台模版1.1版本今日更新：<br><br><br>数据列表页...<br><br>编辑删除弹出功能<br><br>失去焦点排序功能<br>数据列表页<br>数据列表页<br>数据列表页</div>'
//		  ,success: function(layero){
//		    var btn = layero.find('.layui-layer-btn');
//		    btn.find('.layui-layer-btn0').attr({
//		      href: 'http://www.layui.com/'
//		      ,target: '_blank'
//		    });
//		  }
//		});
});