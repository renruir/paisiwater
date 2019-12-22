(function () {
    $.MsgBox = {
        InformSuccess: function (title,content) {
            $("#popup_center").remove();
            GenerateInformHtml_success(title,content);
            btnOk();
            btnNo();
        },

        InformFail: function (title,content) {
            $("#popup_center").remove();
            GenerateInformHtml_fail(title,content);
            btnOk();
            btnNo();
        },

        // content ,表示要显示的内容
        InformWait:function(title,content){
            $("#popup_center").remove();
            GenerateInformHtml_waiting(title,content);
            btnOk();
            btnNo();
        },

        Confirm: function (text,callback) {
            $("#popup_center").remove();
            GenerateComfirmHtml(text);
            btnOk(callback);
            btnNo();
        },

        Remove:function () {
            $("#popup_center").remove();
        }
    }
    //生成Html
    var GenerateComfirmHtml = function (text) {
        var _html = "<div class=\"popup_center\" id='popup_center'>\n" +
            "                <div id=\"mb_box\" style=\"width: 100%; height: 100%; z-index: 100; position: fixed; background-color: black; top: 0px; left: 0px; opacity: 0.6;\"></div>\n" +
            "                <div class=\"common popup_dialog\" id=\"dialog-1\" style=\"visibility: visible;z-index: 101\">\n" +
            "                    <div class=\"popup_dialog_title\">\n" +
            "                        <label class=\"popup_dialog_titlelabel\">警告</label>\n" +
            "                        <label class=\"popup_dialog_closebutton\" id=\"popup_dialog_closebutton\"></label>\n" +
            "                    </div>\n" +
            "                    <div class=\"popup_dialog_content\">\n" +
            "                        <div class=\"popup_dialog_content_comfirm\">\n" +
            "                            <label style=\"font-size: 0.24rem;line-height:0.48rem\">重置滤芯会讲滤芯的使用状态重置，并从头计数。此功能针对新购买滤芯替换以后，重置状态，重新计数。\n" +
            "                                <b>是否确认对<label style=\"color:#5cd1e7\">"+ text +"</label>进行重置？</b>\n" +
            "                            </label>\n" +
            "                        </div>\n" +
            "                    </div>\n" +
            "                    <div class=\"popup_dialog_bottom\">\n" +
            "                        <button class=\"popup_dialog_bottom_button\" data-dismiss=\"JDialog\" id=\"popup_dialog_bottom_button\">确认重置</button>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "            </div>";
        $("body").append(_html);
    }

    var GenerateInformHtml_success = function (title,content) {
        var _html = "<div class=\"popup_center\" id='popup_center'>\n" +
            "                <div id=\"mb_box\" style=\"width: 100%; height: 100%; z-index: 100; position: fixed; background-color: black; top: 0px; left: 0px; opacity: 0.6;\"></div>\n" +
            "                <div class=\"common popup_dialog\" id=\"dialog-1\" style=\"visibility: visible;z-index: 101\">\n" +
            "                    <div class=\"popup_dialog_title\">\n" +
            "                        <label class=\"popup_dialog_titlelabel\">"+ title +"</label>\n" +
            "                        <label class=\"popup_dialog_closebutton\" id=\"popup_dialog_closebutton\"></label>\n" +
            "                    </div>\n" +
            "                    <div class=\"popup_dialog_content\">\n" +
            "                        <div class=\"popup_dialog_content_inform\">\n" +
            "                            <div class=\"popup_dialog_content_inform_success_icon\"></div><label class=\"popup_dialog_content_inform_text\"><b>"+ content +"</b></label>\n" +
            "                        </div>\n" +
            "                    </div>\n" +
            "                    <div class=\"popup_dialog_bottom\">\n" +
            "                        <button class=\"popup_dialog_bottom_button\" data-dismiss=\"JDialog\" id=\"popup_dialog_bottom_button\" style=\"letter-spacing: 0.4rem;padding-left: 0.4rem;\">返回</button>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "            </div>";
        $("body").append(_html);
    }

    var GenerateInformHtml_fail = function (title,content) {
        var _html = "<div class=\"popup_center\" id='popup_center'>\n" +
            "            <div id=\"mb_box\" style=\"width: 100%; height: 100%; z-index: 100; position: fixed; background-color: black; top: 0px; left: 0px; opacity: 0.6;\"></div>\n" +
            "            <div class=\"common popup_dialog\" id=\"dialog-1\" style=\"visibility: visible;z-index: 101\">\n" +
            "                <div class=\"popup_dialog_title\">\n" +
            "                        <label class=\"popup_dialog_titlelabel\">"+ title +"</label>\n" +
            "                    <label class=\"popup_dialog_closebutton\" id=\"popup_dialog_closebutton\"></label>\n" +
            "                </div>\n" +
            "                <div class=\"popup_dialog_content\">\n" +
            "                    <div class=\"popup_dialog_content_inform\">\n" +
            "                        <div style=\"height: 1.04rem;\"><div class=\"popup_dialog_content_inform_fail_icon\"></div><label class=\"popup_dialog_content_inform_text\"><b>"+ content +"</b></label></div>\n" +
            "                        <div style=\"font-size:0.24rem;color:#666666;letter-spacing: 1px;\">请检查网络或者净水器</div>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "                <div class=\"popup_dialog_bottom\">\n" +
            "                    <button class=\"popup_dialog_bottom_button\" data-dismiss=\"JDialog\" id=\"popup_dialog_bottom_button\" style=\"letter-spacing: 0.4rem;padding-left: 0.4rem;\">返回</button>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "        </div>";
        $("body").append(_html);
    }

    var GenerateInformHtml_waiting = function (title,content) {
        var _html = "<div class=\"popup_center\" id='popup_center'>\n" +
            "            <div id=\"mb_box\" style=\"width: 100%; height: 100%; z-index: 100; position: fixed; background-color: black; top: 0px; left: 0px; opacity: 0.6;\"></div>\n" +
            "            <div class=\"common popup_dialog\" id=\"dialog-1\" style=\"visibility: visible;z-index: 101\">\n" +
            "                <div class=\"popup_dialog_title\">\n" +
            "                        <label class=\"popup_dialog_titlelabel\">"+ title +"</label>\n" +
            "                </div>\n" +
            "                <div class=\"popup_dialog_content\">\n" +
            "                    <div class=\"popup_dialog_content_inform\">\n" +
            "                        <div style=\"font-size:0.4rem;color:#666666;letter-spacing: 1px;\">"+ content +"</div>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "        </div>";
        $("body").append(_html);
    }


    //确定按钮事件
    var btnOk = function (callback) {
        $("#popup_dialog_bottom_button").click(function () {
            $("#popup_center").remove();
            if (typeof (callback) == 'function') {
                callback();
            }
        });
    }
    //取消按钮事件
    var btnNo = function () {
        $("#popup_dialog_closebutton").click(function () {
            $("#popup_center").remove();
        });
    }
})();