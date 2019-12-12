<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html style="height: 100%">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%--<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0">--%>
</head>
<body style="height: 100%; width:100%; margin: 0">
<div id="container" style="height: 100%; width:100% "></div>




<script type="text/javascript" src="/web/js/echarts-all-3.js?v=1.3" charset="UTF-8"></script>

<script type="text/javascript" src="/web/js/dataTool.min.js?v=1.3" ></script>

<script type="text/javascript" src="/web/js/china.js?v=1.3" ></script>

<script type="text/javascript" src="/web/js/bmap.min.js?v=1.3" ></script>

<script src="/web/js/jquery-1.10.1.min.js" type="text/javascript"></script>
<script type="text/javascript">
    var provincedata = new Array();
    var citydata = new Array();
    var regiondata = new Array();
    var provincelist = ("${provincelist}").split(",");
    <%--var pro_row_idlist = ("${pro_row_idlist}").split(",");--%>
    var protdslist = ("${protdslist}").split(",");
    var citylist = ("${citylist}").split(",");
    var citytdslist = ("${citytdslist}").split(",");
    var regionlist = ("${regionlist}").split(",");
    var region_cityidlist = ("${region_cityidlist}").split(",");
    var regiontdslist = ("${regiontdslist}").split(",");

    function contains(str, subStr) {
        var startChar = subStr.substring(0,1);
        var strLen = subStr.length;

        for (var j=0; j<str.length-strLen+1; j++) {
            if (str.charAt(j) == startChar) {
                if (str.substring(j, j+strLen) == subStr) {
                    return true;
                }
            }
        }
        return false;
    }
    for (var i=0 ; i<provincelist.length ; i++){
//        console.info("province : " + provincelist[i] + ", tds : " + protdslist[i]);
        if(provincelist[i] =="内蒙"){
            provincedata.push({"name":'内蒙古',"value":protdslist[i],"selected":false});
        }else{
            provincedata.push({"name":provincelist[i],"value":protdslist[i],"selected":false});
        }
    }
    provincedata.push({"name": '香港',"value": 0, "selected":false, "itemStyle":{ "normal":{"opacity":0,"label":{"show":false}}} });
    provincedata.push({"name": '澳门',"value": 0, "selected":false, "itemStyle":{ "normal":{"opacity":0,"label":{"show":false}}} });

    for (var i=0 ; i<citylist.length ; i++){
        if(contains(citylist[i],"日喀则地区")) {
            citydata.push({"name":"日喀则市","value":citytdslist[i],"selected":false});
        }else if(contains(citylist[i],"昌都地区")){
            citydata.push({"name":"昌都市","value":citytdslist[i],"selected":false});
        }else if(contains(citylist[i],"林芝地区")) {
            citydata.push({"name": "林芝市", "value": citytdslist[i], "selected": false});
        }else if(contains(citylist[i],"海东地区")) {
            citydata.push({"name": "海东市", "value": citytdslist[i], "selected": false});
        }else if(contains(citylist[i],"神农架")) {
            citydata.push({"name": "神农架林区", "value": citytdslist[i], "selected": false});
        }else if(contains(citylist[i],"大兴安岭")) {
            citydata.push({"name": "大兴安岭地区", "value": citytdslist[i], "selected": false});
        } else if(!contains(citylist[i],"地区") && !contains(citylist[i],"市") && !contains(citylist[i],"自治州") && !contains(citylist[i],"盟")){
            citydata.push({"name":citylist[i]+"市","value":citytdslist[i],"selected":false});
        }else{
            citydata.push({"name":citylist[i],"value":citytdslist[i],"selected":false});
        }
        console.info("city : " + citylist[i] + ", tds : " + citytdslist[i]);
    }
    for (var i=0 ; i<regionlist.length ; i++){
//        console.info("region : " + regionlist[i] + ", tds : " + regiontdslist[i]);
        if(region_cityidlist[i] == 1 || region_cityidlist[i] == 2 || region_cityidlist[i] == 3 || region_cityidlist[i] == 4){
            if(contains(regionlist[i],"荣昌县")){
                citydata.push({"name": "荣昌区","value": regiontdslist[i],"selected":false});
            }else if(contains(regionlist[i],"潼南县")){
                citydata.push({"name": "潼南区","value": regiontdslist[i],"selected":false});
            }else if(contains(regionlist[i],"铜梁县")){
                citydata.push({"name": "铜梁区","value": regiontdslist[i],"selected":false});
            }else if(contains(regionlist[i],"璧山县")){
                citydata.push({"name": "璧山区","value": regiontdslist[i],"selected":false});
            }else{
                citydata.push({"name": regionlist[i],"value": regiontdslist[i],"selected":false});
            }
            console.info("city : " + regionlist[i] + ", tds : " + regiontdslist[i]);
        }
        regiondata.push({"name":regionlist[i],"value":regiontdslist[i],"selected":false});
    }

    var dom = document.getElementById("container");
    var myChart = echarts.init(dom);

    option = {
        backgroundColor: '#ffffff',
        title: {
            text: '全国主要城市水质量',
//            subtext: 'data from FBI Warning',
            x:'center',
            textStyle: {
                color: '#000'
            }
        },
        tooltip: {
            trigger: 'item',
            formatter: function (params) {
                return params.name + ' : ' + params.value;
            }
        },
//        legend: {
//            orient: 'vertical',
//            y: 'bottom',
//            x:'right',
//            data:['tds'],
//            textStyle: {
//                color: '#fff'
//            }
//        },
        visualMap: {
            type: 'continuous',
            min: 0,
            max: 500,
            itemWidth: 25,
            itemHeight: 160,
            calculable: true,
            inRange: {
//                color: ['#50a3ba', '#eac736', '#d94e5d']
                color: ['lightskyblue','yellow','red']
            },
            textStyle:{
                fontSize: 10
            }
        },
        series: [
            {
                name: 'tds',
                type: 'map',
                map: 'china',
                layoutCenter: ['30%', '50%'],
                layoutSize: 700,
//                roam: true,
                selectedMode : 'single',
//                data:[
//                    {name: "北京", value: 294},
//                    {name: "广东", value: 55},
//                    {name: "河北", value: 316},
//                    {name: "山东", value: 401}
//                ],
                label: {
                    normal: {
                        show: true,
                        textStyle:{
                            fontSize: 10,
                        }
                    },
                    emphasis: {
                        show: false,
                        textStyle:{
                            fontSize: 10,
                        }
                    }
                },
                itemStyle: {
                    emphasis: {
                        areaColor: '#000',
                        opacity: '0.1',
                        borderColor: '#fff',
                        borderWidth: 1,
                        label:{show:true}
                    }
                },
                data: provincedata
            }
        ],
        animation: false
    };
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
        myChart.on("click", ProvinceClick);
    }

    function ProvinceClick(param){
        var selectedProvince = param.name;
        $("#test").text(selectedProvince);
        console.info("selectedProvince is " + selectedProvince);

        $.get('/web/json/' + selectedProvince + '.json', function (geoJson) {
            echarts.registerMap(selectedProvince, geoJson);
            option.series[1] = {
                name: '市级数据',
                type: 'map',
                mapType: selectedProvince,
//                itemStyle: {
//                    normal: {label: {show: true}},
//                    emphasis: {label: {show: true}}
//                },
                layoutCenter: ['77%', '50%'],
                layoutSize: 450,
                roam: true,
                data: citydata,
                label: {
                    normal: {
                        show: true,
                        textStyle:{
                            fontSize: 6,
                        }
                    },
                    emphasis: {
                        show: false,
                        textStyle:{
                            fontSize: 6,
                        }
                    }
                },
                itemStyle: {
                    emphasis: {
                        areaColor: '#000',
                        opacity: '0.1',
                        borderColor: '#fff',
                        borderWidth: 5,
                        label:{show:true}
                    }
                }
            };
            myChart.setOption(option, true);
        });
    }


</script>
</body>
</html>