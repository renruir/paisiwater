/**
 * Created by songxin on 2016/12/7.
 */
const wtf = {"number":[{"value":"一"},{"value":"二"},{"value":"三"},{"value":"四"}]};
const CR400D1_cfg = {"level":[{"ch":"超滤膜滤芯", "en":"UF"},{"ch":"活性炭滤芯", "en":"CTO"},{"ch":"RO反渗透滤芯", "en":"RO"},{"ch":"RO反渗透滤芯", "en":"RO"}]};
const Introduce = {
    "UF" :"过滤胶体、铁锈、悬浮物、泥沙、大分子有机物，实现进化过程",
    "CTO":"吸附水中异色、异味、余氯及部分有机物，保护反渗透滤芯",
    "RO" :"过滤水中重金属、细菌、水垢等杂质，过滤精度达到0.00001微米"
};
var details;

//根据设备显示滤芯名称
function setFilterDetails(arg) {
    if(arg == null || arg == "")
        return;
    details = eval(arg + "_cfg");
    $("#span1").html(details.level[0].ch);
    $("#span2").html(details.level[1].ch);
    $("#span3").html(details.level[2].ch);
    $("#span4").html(details.level[3].ch);
    $("#label1").html(details.level[0].en);
    $("#label2").html(details.level[1].en);
    $("#label3").html(details.level[2].en);
    $("#label4").html(details.level[3].en);
}

//根据level与设备显示滤芯
function switchFilter(arg) {
    if(details == null || details == "")
        return;
    else
        return details.level[arg].ch;
}

function switchNumber(arg) {
    return wtf.number[arg].value;
}

function switchIntroduce(arg){
    if(details == null || details == "")
        return;
    else
        var filterid = details.level[arg].en;
        var introduce;
        switch (filterid){
            case "UF":
                introduce = "过滤胶体、铁锈、悬浮物、泥沙、大分子有机物，实现净化过程";
                break;
            case "CTO":
                introduce = "吸附水中异色、异味、余氯及部分有机物，保护反渗透滤芯";
                break;
            case "RO":
                introduce = "过滤水中重金属、细菌、水垢等杂质，过滤精度达到0.00001微米";
                break;
        }
        return introduce;
}

function buy() {
    if (confirm("确定进入购买页面吗")) {
        window.open("https://weidian.com/?userid=1164650891&wfr=c&ifr=shopdetail");
    }
}
