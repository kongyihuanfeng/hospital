var app = {
    webSocketUrl : '',
    wsServer : null,
    imgarr : ['images/touxiang.png', 'images/touxiangm.png'],
    $container : $('.message'),
    $messageText : $('#messageText'),
    $SendBtn : $('.footer p'),
    initPage : function(){
        var that = this;
        that.initData();
        that.initEvent();
    },

    initData : function(){
        var that = this;
        that.webSocketUrl = "ws://localhost:80/weChat/client/001";
        that.connectServer();
    },
    initEvent : function(){
        var that = this;
       $('.footer').on('keyup', 'input', function() {
            var text = that.$messageText.val();
            if (text!=null && text!='') {
                that.$SendBtn.css('background', '#114F8E').prop('disabled', true);
            } else {
                that.$SendBtn.css('background', '#ddd').prop('disabled', false);
            }
        })
        that.$SendBtn.click(function() {
            var message = that.$messageText.val();
            that.sendMessage(message);
        })
    },
    connectServer : function(){
        var that = this;
        try{
            that.wsServer = new WebSocket(that.webSocketUrl);
            that.wsServer.onopen = function () {
                // 使用 send() 方法发送数据
                that.onOpen();
            };
            // 接收服务端数据时触发事件
            that.wsServer.onmessage = function (evt) {
                var received_msg = evt.data;
                that.onMessage(received_msg);
            };
          
            // 断开 web socket 连接成功触发事件
            that.wsServer.onclose = function () {
                that.onClose();
            };
        }catch(err){
            console.log('当前客户端不支持webSocket');
        }

    },

    onOpen : function(){
        var that = this;
    },

    onMessage : function(message){
        var that = this;
        that.renderSend({
            type:'send',
            imageUrl : that.imgarr[0],
            message : message,
        });
    },

    onClose : function(){
        var that = this;
    },

    sendMessage : function(message){
        var that = this;
        that.renderSend({
            type:'show',
            imageUrl : that.imgarr[1],
            message : message,
        });
        if(that.wsServer)
            that.wsServer.send(message);
    },
    
    renderSend : function(data){
        var that = this;
        var html = that.messageHtml(data);
        that.$container.append(html);
        $('body').animate({
            scrollTop: that.$container.outerHeight() - window.innerHeight
        }, 200)
    },

    messageHtml : function(data){
        var that = this;
        var html = "<div class='{{type}}'><div class='msg'><img src={{imageUrl}} />" 
                    + "<p><i class='msg_input'></i>{{message}}</p></div></div>";
        return that.renderData(html,data);
    },

    renderData : function(template,data){
        var that = this;
        var reg = /{{(\w+)}}/g;
        var renderHtml = template.replace(reg,function(word){
            var field = word.substr(2,word.length-4);
            value = data[word.substr(2,word.length-4)];
            return that.htmlEncode(value);
         });
        return renderHtml;
    },

    htmlEncode : function(str){
        if (str && typeof str == "string") {
            var s = "";
            if(str.length == 0) return "";
            s = str.replace(/&/g,"&amp;");
            s = s.replace(/</g,"&lt;");
            s = s.replace(/>/g,"&gt;");
            s = s.replace(/ /g,"&nbsp;");
            s = s.replace(/\'/g,"&#39;");
            s = s.replace(/\"/g,"&quot;");
            return s;
        } 
        return str;
    },
}

app.initPage();