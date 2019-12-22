/**
 * Created by renrui on 2016/12/6 0006.
 */
/**
 * public
 */
const TYPE_WATER_PURIFIER = 1;
const TYPE_AIR_PURIFIER = 2;

//联网状态
const ONLINE_STATE = 100; //在线
const OFFLINE_STATE = 101;//离线
const UNKNOWN_STATE = 199; //未知

//设备型号
const DEVICE_MODEL_CR400D1 = "CR400D1";
const DEVICE_MODEL_CD400D1 = "CD400D1";
const DEVICE_MODEL_S3 = "KJ300F-S3"
const DEVICE_MODEL_S5 = "KJ550F-S5"
const DEVICE_MODEL_S8 = "KJ760F-S8"

/**
 * begin for water purifier
 */
//产品类型
const DEVICE_WATER_PURIFIER = "1";
const DEVICE_AIR_PURIFIER = "2";


//一次报文中固定的头和尾的总长度
const FIXED_LENGTH_MESSAGE = 10;

//状态数据回复数据包长度
const STATE_DATA_PACKAGE_LENGTH = 45;
//滤芯复位数据包长度
const FILTER_RESET_PACKAGE_LENGTH = 50;

const ADDR_PURE_WATER_DEVICE = 0x24; //净水器客户端

const ADDR_PHONE_CLINET = 0XD5;//手机app及其他客户端

//rinse state
const RINSE_START = "rinse_start";
const RINSE_COMPLETED = "rinse_completed";
const RINSING = "rinsing";
const RINSE_INVALID = "rinse_invalid"

//work state
const WORKING_STATE_RINSING = "冲洗";//正在冲水

const WORKING_STATE_PURING = "制水";//正在制水

const WORKING_STATE_WATER_FULL = "水箱满";//水箱水满

const WORKING_STATE_STANDBY = "待机";//净水器待机

const WORKING_STATE_SOURCE_WATER_SHORTAGE = "原水缺水";//原水缺水

const WORKING_STATE_NONE = "未知";//设备在线，但是状态未获取到就是未知

const WORKING_STATE_OFFLINE = "离线";//设备离线

const WORKING_STATE_DEVICE_STOP = "停机"; //设备停机

// fault code
const DEVICE_FAULT_K1 = "电磁阀K1故障";

const DEVICE_FAULT_K2 = "电磁阀K2故障";

const DEVICE_FAULT_K3 = "增压泵K3故障";

const DEVICE_FAULT_K3_VALVE = "电磁阀K3故障"

const DEVICE_FAULT_K4_VALVE = "电磁阀K4故障"

const DEVICE_FAULT_K5_VALVE = "电磁阀K5故障"

const DEVICE_FAULT_HIGH_PRESSURE = "高压开关故障或忘记关水";

const DEVICE_FAULT_WATER_LEAKAGE = "设备漏水";

const DEVICE_FAULT_REVERSE_VALVE_FAULT = "高压开关故障或忘记关水";

const DEVICE_FAULT_WATER_SHORTAGE = "设备缺水"

const DEVICE_FAULT_UNKNOW = "未知故障"

const COMMAND_RINSE = "command_rinse";
const COMMAND_FILTER_RESET = "command_filter_reset";
const COMMAND_CHILDREN_LOCK = "command_children_lock";
const COMMAND_UV = "command_uv";

var dataHex;
var index = 8;//索引位置，前面8个是固定的头
var is_fault = false;
var fault_code = 0xff;


function getValidData(data) {
    var i = data.length();
    var validData = new Array();
    while (i--) {
        if (i > 6 && i < data.length - 2) {
            validData[i] = data[i];
        }
    }
    return validData;
}

//16进制输入转成字符串
function bytes2Str(arr) {
    try {
        var str = "";
        for (var i = 0; i < arr.length; i++) {
            var tmp = arr[i].toString(16);
            if (tmp.length == 1) {
                tmp = "0" + tmp;
            }
            str += tmp;
        }
        return str;
    } catch (e) {
        console.info(e.message);
    }
}

//16进制输入转成字符串
function bytes2StrForPrint(arr) {
    try {
        var str = "";
        for (var i = 0; i < arr.length; i++) {
            var tmp = arr[i].toString(16);
            if (tmp.length == 1) {
                tmp = "0" + tmp + " ";
            }
            str = str + tmp + " ";
        }
        return str;
    } catch (e) {
        console.info(e.message);
    }
}

function stringToHex(str) {
    var urlArray = new Array()
    for (var i = 0; i < str.length; i++) {
        urlArray[i] = parseInt(str.charCodeAt(i).toString(16), 16);
    }
    return urlArray;
}

//十进制转成十六进制
function dec2hex(data) {
    try {
        var i = data.byteLength;
        var dataHex = new Array();
        while (i--) {
            dataHex[i] = data[i].toString(16);
        }
        return dataHex;
    } catch (e) {
        console.error(e.message);
    }
}
/*
 Convert value as 16-bit unsigned integer to 4 digit hexadecimal number prefixed with "0x".
 */
function hex16(val) {
    var n = val & 0xFFFF;
    var str = n.toString(16).toUpperCase();
    n = str.length;
    if (n < 2) str = "000" + str;
    else if (n < 3) str = "00" + str;
    else if (n < 4) str = "0" + str;
    return "0x" + str;
}


//获取整条报文的长度 返回十进制长度
function getMessageLength(message) {
    return message[5];
}

function isSend2Client(data) {
    if (data[3] == ADDR_PHONE_CLINET) {
        return true
    }
    return false;
}

function isFromPureWater(data) {
    if (data[4] == ADDR_PURE_WATER_DEVICE) {
        return true;
    }
    return false;
}

function isStateQuery(data) {
    if (data[6] == 0x31) {
        return true;
    }
    return false;
}

function getValidDataLength() {
    var validDataLength = getMessageLength - FIXED_LENGTH_MESSAGE;
}


function getDeviceModel(data) {
    var device_model;
    switch (parseInt(data, 16)) {
        case 0x10:
            device_model = DEVICE_MODEL_CR400D1;
            break;
        case 0x11:
            device_model = DEVICE_MODEL_CD400D1;
            break;
        case 0x21:
            device_model = DEVICE_MODEL_S3;
            break;
        case 0x22:
            device_model = DEVICE_MODEL_S5;
            break;
        case 0x23:
            device_model = DEVICE_MODEL_S8;
            break;

    }
    return device_model;
}

function getRinseState() {
    var rinse_state = RINSE_INVALID;
    switch (parseInt(dataHex[index + 3], 16)) {
        case 0x10:
            rinse_state = RINSE_START;
            break;
        case 0x12:
            rinse_state = RINSE_COMPLETED;
            break;
        case 0x13:
            rinse_state = RINSING;
            break;
    }
    return rinse_state;
}

function getWorkingState() {
    var working_state;
    switch (parseInt(dataHex[index + 4], 16)) {
        case 0x01:
            working_state = WORKING_STATE_RINSING;
            break;
        case 0x02:
            working_state = WORKING_STATE_PURING;
            break;
        case 0x03:
            working_state = WORKING_STATE_WATER_FULL;
            break;
        case 0x04:
            working_state = WORKING_STATE_SOURCE_WATER_SHORTAGE;
            break;
        case 0x05:
            working_state = WORKING_STATE_STANDBY;
            break;
        case 0x06:
            working_state = WORKING_STATE_DEVICE_STOP;
            break;
    }
    return working_state;
}

function getFaultState(data) {
    if (parseInt(data, 16) == 0x01) {
        is_fault = true;
    } else {
        is_fault = false;
    }
    return is_fault;
}

function getFaultCodeNum() {
    if (is_fault) {
        var errorCode = parseInt(dataHex[index + 6]);
        return errorCode;
    }
}

function getFaultCode() {
    if (is_fault) {
        switch (parseInt(dataHex[index + 6], 16)) {
            case 0x01:
                fault_code = DEVICE_FAULT_K1;
                break;
            case 0x02:
                fault_code = DEVICE_FAULT_K2;
                break;
            case 0x03:
                fault_code = DEVICE_FAULT_K3;
                break;
            case 0x04:
                fault_code = DEVICE_FAULT_K3_VALVE;
                break;
            case 0x05:
                fault_code = DEVICE_FAULT_K4_VALVE;
                break;
            case 0x06:
                fault_code = DEVICE_FAULT_K5_VALVE;
                break;
            case 0x09:
                fault_code = DEVICE_FAULT_HIGH_PRESSURE;
                break;
            case 0x10:
                fault_code = DEVICE_FAULT_WATER_LEAKAGE;
                break;
            case 0x11:
                fault_code = DEVICE_FAULT_REVERSE_VALVE_FAULT;
                break;
            case 0x12:
                fault_code = DEVICE_FAULT_WATER_SHORTAGE;
                break;
            default:
                fault_code = DEVICE_FAULT_UNKNOW;
        }
    }
    return fault_code;
}

function getFilterMargin() {
    var filter_margin = new Array(parseInt(dataHex[index + 7], 16), parseInt(dataHex[index + 8], 16),
        parseInt(dataHex[index + 9], 16), parseInt(dataHex[index + 10], 16),
        parseInt(dataHex[index + 11], 16), parseInt(dataHex[index + 12], 16));
    return filter_margin;
}

function getTemperature(high, low) {
    try {
        low = parseInt(low, 16);
        high = parseInt(high, 16);
        var temp1 = low | (high << 8);
        return (temp1.toString(10) / 10).toString();
    } catch (e) {
        console.error(e.message);
    }
}

function getPressure(high, low) {
    try {
        low = parseInt(low, 16);
        high = parseInt(high, 16);
        var temp = low | (high << 8);
        return temp;
    } catch (e) {
        console.error(e.message);
    }
}

function getSourceWaterPressure() {
    return getPressure(dataHex[index + 17], dataHex[index + 18]);
}

function getBeforeFilterPressure() {
    return getPressure(dataHex[index + 19], dataHex[index + 20]);
}

function getPureWaterPressure() {
    return getPressure(dataHex[index + 21], dataHex[index + 22]);
}

function getVersion(value) {
    try {
        var version;
        var value10 = parseInt(value, 16);
        if (value10 < 256) {
            var valueString = value10.toString();
            if (value10 < 100) {
                var tempLast = valueString.charAt(valueString.length - 1);
                var tempFirst = valueString.charAt(0)
                version = tempFirst + "." + tempLast;
            } else {
                var tempLast = valueString.charAt(valueString.length - 1);
                var tempFirst = valueString.substr(0, 2)
                version = tempFirst + "." + tempLast;
            }
        }
        return version;
    } catch (e) {
        console.log(e.message);
    }

}

function getTDS(high, low) {
    try {
        low = parseInt(low, 16);
        high = parseInt(high, 16);
        var temp = low | (high << 8);
        return temp;
    } catch (e) {
        console.log(e.message);
    }

}

function str2Bytes(str) {
    try {
        var pos = 0;
        var len = str.length;
        if (len % 2 != 0) {
            return null;
        }
        len /= 2;
        var hexA = new Array();
        for (var i = 0; i < len; i++) {
            var s = str.substr(pos, 2);
            var v = s.toString(16);
            hexA.push(v);
            pos += 2;
        }
        return hexA;
    } catch (e) {
        console.log(e.message);
    }

}

/**
 * arguments[0]: wifi model version
 * arguments[1]: update package size
 * arguments[2]: update package download url
 * arguments[3]: update package checksum
 * arguments[4]: device id
 */

function wifiModelUpdate() {
    try {
        var size;
        var data = new Array(0xa1, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff);
        //get wifi model newest version with hex
        var index_va = arguments[0].indexOf("VA");
        var version = arguments[0].substring(index_va + 2, arguments[0].length);
        version = parseFloat(version) * 10;
        data[1] = parseInt(version.toString(16), 16);

        //get wifi model update package size saved with 4 bytes
        var sizeStr = parseInt(arguments[1]).toString(16);
        console.log("file size: " + sizeStr);
        if (sizeStr.length < 10) {
            var zero = '00000000';
            var tmp = zero.length - sizeStr.length;
            size = zero.substr(0, tmp) + sizeStr;
        }
        var packageSize = new Array();
        packageSize = str2Bytes(size);

        //calculate checkCode for package
        var checkCode = new Array();
        checkCode = str2Bytes(arguments[3]);
        data[2] = parseInt(packageSize[0], 16);
        data[3] = parseInt(packageSize[1], 16);
        data[4] = parseInt(packageSize[2], 16);
        data[5] = parseInt(packageSize[3], 16);

        data[6] = parseInt(checkCode[0], 16);
        data[7] = parseInt(checkCode[1], 16);
        data[8] = parseInt(checkCode[2], 16);
        data[9] = parseInt(checkCode[3], 16);

        //get url with hex and save to array
        var url = stringToHex(arguments[2]);
        var updateInfo = data.concat(url)

        //send message
        sendMessage(updateInfo, TYPE_WATER_PURIFIER, arguments[4]);
    } catch (e) {
        console.info(e.message);
    }

}

/**
 * 可变长度的参数函数
 * argument[0]:command(rinse, filter_reset, children_lock)
 * arguments[1]: device id (mac + random() + _1)
 * arguments[2]: filter_number or child_lock_state
 * arguments[N]: other
 */
function sendCommand2Devices() {
    var data = new Array(0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff);

    if (arguments[0] == COMMAND_RINSE) {
        data[0] = 0x11;
    } else if (arguments[0] == COMMAND_FILTER_RESET) {
        data[0] = 0x12;
        data[2] = parseInt(arguments[2], 16) + 1;
    } else if (arguments[0] == COMMAND_CHILDREN_LOCK) {
        data[0] = 0x14;
        if (arguments[2] == "on") {
            data[2] == 0x01;
        } else {
            data[2] == 0x02
        }
    } else if (arguments[0] == COMMAND_UV) {
        data[0] = 0x16;//UV灯
        data[2] = 0x01;//on
    }
    //send message to MQTT server
    sendMessage(data, TYPE_WATER_PURIFIER, arguments[1]);
}

//查询设备状态，页面第一次打开或者重新加载时发送次命令
function queryDeviceStates(type, deviceId) {
    if (type == TYPE_WATER_PURIFIER) {
        var data = new Array(0x21, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff);
    } else if (type == TYPE_AIR_PURIFIER) {
        var data = new Array(0x21, 0x21, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff);
    }
    sendMessage(data, type, deviceId);
}

//按照协议插入固定格式的包头和包尾
function insertHeader(data, type) {
    if (data.length == 0) {
        return;
    }
    var length = data.length + FIXED_LENGTH_MESSAGE;
    if (type == TYPE_WATER_PURIFIER) {
        if (data[0] == 0x21) { //for query state
            data.unshift(0xB2);
            data.unshift(0x7A, 0x7A, 0x24, 0xD5, length, 0, 0);
        } else if (data[0] == 0x11 || data[0] == 0x12 || data[0] == 0x14 || data[0] == 0x15) {//for command
            data.unshift(0xB1);
            data.unshift(0x7A, 0x7A, 0x24, 0xD5, length, 0, 0);
        } else if (data[0] == 0xa1) { // for wifi model update
            data.unshift(0xBA);
            data.unshift(0x7A, 0x7A, 0xD1, 0xD5, length, 0, 0);
        }
    } else if (type == TYPE_AIR_PURIFIER) {
        if (data[0] == 0x21) { //for query state
            data.unshift(0xB2);
            data.unshift(0x7A, 0x7A, 0x03, 0xD5, length, 0, 0);
        } else if (data[0] > 0x10 && data[0] < 0x1f) {
            data.unshift(0xB1);
            data.unshift(0x7A, 0x7A, 0x03, 0xD5, length, 0, 0);
        }
    }

    var crcResult = getCrc16(data);
    data.push(parseInt(crcResult.substr(4, 2), 16), parseInt(crcResult.substr(2, 2), 16));
    return data;
}

function getCrc16(arry) {
    if (arry.length == 0) {
        return;
    }
    var crc = 0xffff;
    var temp;
    var length = arry.length;
    for (var i = 0; i < length; i++) {
        temp = arry[i] & 0x00ff;
        crc = crc ^ temp;
        for (var j = 0; j < 8; j++) {
            if (crc & 0x0001) {
                crc = crc >> 1;
                crc = crc ^ 0xA001;
            } else {
                crc = crc >> 1;
            }
        }
    }
    crc = (crc >> 8) + (crc << 8);
    return hex16(crc);
}


//发送消息
function sendMessage(data, type, deviceId) {
    var msg = insertHeader(data, type);
    console.log("send msg length: " + msg.length);
    console.log("send msg: " + bytes2StrForPrint(msg));
    var buffer = new ArrayBuffer(msg.length);
    var byteStream = new Uint8Array(buffer);
    for (var i = 0; i < msg.length; i++) {
        byteStream[i] = msg[i];
    }
    message = new Paho.MQTT.Message(byteStream);
    message.destinationName = "nodes/" + deviceId + "/cmd";

    //message._setQos(2);
    message._setRetained(false);
    //message._setRetained(true);
    client.send(message);
}

//获取状态信息
function getDeviceStateInfo(sourcedata) {

    dataHex = dec2hex(sourcedata);

    var info = {
        "device_version": getVersion(dataHex[index + 1]),
        "device_model": getDeviceModel(dataHex[index + 2]),
        "rinse_state": getRinseState(),
        "working_state": getWorkingState(),
        "is_fault": getFaultState(dataHex[index + 5]),
        "fault_code": getFaultCode(),
        "filter": getFilterMargin(),
        "pure_water_temperature": getTemperature(dataHex[index + 13], dataHex[index + 14]),
        "machine_temperature": getTemperature(dataHex[index + 15], dataHex[index + 16]),
        "source_water_pressure": getSourceWaterPressure(),
        "before_filter_pressure": getBeforeFilterPressure(),
        "pure_water_pressure": getPureWaterPressure(),
        "source_water_TDS": getTDS(dataHex[index + 23], dataHex[index + 24]),
        "pure_water_TDS": getTDS(dataHex[index + 25], dataHex[index + 26])
    };
    return info;
}

function getResetResult(data) {
    if (data == 0x01) {
        return true;
    }
    return false;
}

function getResetSeries(series) {
    return parseInt(series, 16);
}

function getRFid() {
    return "000";
}

function getFilterResetState(filterData) {
    if (!(filterData.length == 0) && filterData.length == FILTER_RESET_PACKAGE_LENGTH) {
        var infoData = dec2hex(filterData);
        var filterInfo = {
            "is_reset_success": getResetResult(infoData[index + 3]),
            "reset_series": getResetSeries(infoData[index + 34]),
            "RFID_1": getRFid(),
            "RFID_2": getRFid(),
            "RFID_3": getRFid(),
            "RFID_4": getRFid(),
            "RFID_5": getRFid(),
            "RFID_6": getRFid(),
        }
        return filterInfo;
    }
}

Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1,                 //月份
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
}
/**
 * end for water purifier
 */


/**
 * begin for air purifier
 */


const CMD_AP_POWER = 201;
const CMD_AP_AUTO_MODE = 202;
const CMD_AP_SLEEP_MODE = 203;
const CMD_AP_CHILD_LOCK = 204;
const CMD_AP_ANION = 205;
const CMD_AP_LIGHT = 206;
const CMD_AP_VOLUME = 207;
const CMD_AP_FILTER_RESET = 208;
const CMD_AP_TIMER = 209;

const STATE_AP_POWER_ON = 300;
const STATE_AP_POWER_OFF = 301;
const STATE_AP_AUTO = 302;
const STATE_AP_AUTO_OFF = 303;
const STATE_AP_SLEEP = 304;
const STATE_AP_SLEEP_OFF = 305;
const STATE_AP_CHILD_LOCK = 306;
const STATE_AP_CHILD_LOCK_OFF = 307;
const STATE_AP_ANION = 308;
const STATE_AP_ANION_OFF = 309;
const STATE_AP_LIGHT = 310;
const STATE_AP_LIGHT_OFF = 311;
const STATE_AP_TIMER_TO_OFF = 312
const STATE_AP_TIMER_TO_ON = 313;
const STATE_AP_TIMER_CANCEL = 313;

const STATE_AP_OTHER = 399;


/**
 * 可变长度的参数函数
 * argument[0]:command(power, auto_mode, sleep_mode, child_lock, anion, uv, volume, filter_reset, timer)
 * arguments[1]: device id (mac + random() + _1)
 * arguments[2]: filter_number or child_lock_state
 * arguments[N]: other
 */
function sendAirPurifierCommand() {
    var data = new Array(0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff);

    if (arguments[0] == CMD_AP_POWER) {
        data[0] = 0x11;
        if (arguments[2] == "on") {
            data[2] = 0x10;
        } else {
            data[2] = 0x00
        }
    } else if (arguments[0] == CMD_AP_AUTO_MODE) {
        data[0] = 0x12;
        if (arguments[2] == "on") {
            data[2] = 0x10;
        } else {
            data[2] = 0x00
        }
    } else if (arguments[0] == CMD_AP_SLEEP_MODE) {
        data[0] = 0x13;
        if (arguments[2] == "on") {
            data[2] = 0x10;
        } else {
            data[2] = 0x00
        }
    } else if (arguments[0] == CMD_AP_CHILD_LOCK) {
        data[0] = 0x14;
        if (arguments[2] == "on") {
            data[2] = 0x10;
        } else {
            data[2] = 0x00
        }
    } else if (arguments[0] == CMD_AP_ANION) {
        data[0] = 0x15;
        if (arguments[2] == "on") {
            data[2] = 0x10;
        } else {
            data[2] = 0x00
        }
    } else if (arguments[0] == CMD_AP_LIGHT) {
        data[0] = 0x16;
        if (arguments[2] == "on") {
            data[2] = 0x10;
        } else {
            data[2] = 0x00
        }
    } else if (arguments[0] == CMD_AP_VOLUME) {
        data[0] = 0x17;
        data[2] = arguments[2];
    } else if (arguments[0] == CMD_AP_FILTER_RESET) {
        data[0] = 0x18;
        data[2] = arguments[2];
    } else if (arguments[0] == CMD_AP_TIMER) {
        data[0] = 0x19;
        if (arguments[2] == "on") {
            data[2] = 0x10;
            data[3] = parseInt(arguments[3], 10);
        } else {
            data[2] = 0x00
        }
    }
    //send message to MQTT server
    console.log("data is = " + bytes2StrForPrint(data));
    sendMessage(data, TYPE_AIR_PURIFIER, arguments[1]);
}

function getAPPowerStat(data) {
    if (parseInt(data, 16) == 0x10) {
        return STATE_AP_POWER_ON;
    } else {
        return STATE_AP_POWER_OFF;
    }
}

function getAPWorkState(data) {
    if (parseInt(data, 16) == 0x01) {
        return STATE_AP_AUTO;
    } else if (parseInt(data, 16) == 0x02) {
        return STATE_AP_SLEEP;
    } else {
        return STATE_AP_OTHER;
    }
}

function getAPFaultCode(data) {
    if (parseInt(data, 16) == 0x01) {
        return DEVICE_FAULT_K1;
    }
}

function getAPLockState(data) {
    if (parseInt(data, 16) == 0x10) {
        return STATE_AP_CHILD_LOCK;
    } else if (parseInt(data, 16) == 0x00) {
        return STATE_AP_CHILD_LOCK_OFF;
    }
}

function getAPLightState(data) {
    if (parseInt(data, 16) == 0x10) {
        return STATE_AP_LIGHT;
    } else {
        return STATE_AP_LIGHT_OFF;
    }
}

function getAPAnionState(data) {
    if (parseInt(data, 16) == 0x10) {
        return STATE_AP_ANION;
    } else {
        return STATE_AP_ANION_OFF;
    }
}

function getVolumeGrade(data) {
    return parseInt(data, 16)
}

function getTimerState(data) {
    if (parseInt(data, 16) == 0x00) {
        return STATE_AP_TIMER_TO_OFF;
    } else if(parseInt(data, 16) == 0x00){
        return STATE_AP_TIMER_TO_ON;
    } else {
        return STATE_AP_TIMER_CANCEL;
    }
}

function getTowBitDex(high, low) {
    try {
        low = parseInt(low, 16);
        high = parseInt(high, 16);
        var temp = low | (high << 8);
        return temp;
    } catch (e) {
        console.log(e.message);
        return -1;
    }
}

function getFiltersLife() {
    var filterLife = new Array(getTowBitDex(airDateHex[index + 14], airDateHex[index + 15]),
        getTowBitDex(airDateHex[index + 17], airDateHex[index + 17]), getTowBitDex(airDateHex[index + 20], airDateHex[index + 21]));
    return filterLife;
}

function getFilterPercent() {
    var percent = new Array(parseInt(airDateHex[index + 16], 16), parseInt(airDateHex[index + 19], 16), parseInt(airDateHex[index + 22], 16));
    return percent;
}

function ap_temp_state(data) {
    if (parseInt(data, 16) == 0x01) {
        return 1;
    } else {
        return -1;
    }
}

function getAPTemperature(state, data) {
    return ap_temp_state(state) * parseInt(data, 16);
}

function getAPHumidity(data) {
    return parseInt(data, 16);
}

function getPMState(data) {
    if (parseInt(data, 16) == 0x01) {
        return 0;
    } else {
        return -1;
    }
}

function getAirQuality(data) {
    var pmValue = getTowBitDex(airDateHex[index + 28], airDateHex[index + 29]);
    if (pmValue <= 50) {
        return 1;
    } else if (pmValue > 50 && pmValue <= 100) {
        return 2;
    } else if (pmValue > 100 && pmValue <= 150) {
        return 3;
    } else if (pmValue > 150 && pmValue <= 200) {
        return 4;
    } else {
        return 5
    }
}


function getAirPurifierState(sourcedata) {
    airDateHex = dec2hex(sourcedata);
    var info = {
        "ap_version": getVersion(airDateHex[index + 1]),
        "ap_model": getDeviceModel(airDateHex[index + 2]),
        "ap_power": getAPPowerStat(airDateHex[index + 3]),
        "ap_work_state": getAPWorkState(airDateHex[index + 4]),
        "ap_fault_state": getFaultState(airDateHex[index + 5]),
        "ap_fault_code": getAPFaultCode(airDateHex[index + 6]),
        "ap_child_lock": getAPLockState(airDateHex[index + 7]),
        "ap_anion": getAPAnionState(airDateHex[index + 8]),
        "ap_light": getAPLightState(airDateHex[index + 9]),
        "ap_volume_grade": getVolumeGrade(airDateHex[index + 10]),
        "ap_timer_state": getTimerState(airDateHex[index + 11]),
        "ap_timer": getTowBitDex(airDateHex[index + 12], airDateHex[index + 13]),
        "ap_filter_life": getFiltersLife(),
        "ap_filter_percent": getFilterPercent(),
        "ap_temperature": getAPTemperature(airDateHex[index + 23], airDateHex[index + 24]),
        "ap_humidity": getAPHumidity(airDateHex[index + 25]),
        "ap_pm_state": getPMState(airDateHex[index + 26]),
        "ap_pm_value": getTowBitDex(airDateHex[index + 28], airDateHex[index + 29]),
        // "ap_air_quality": getAirQuality(airDateHex[index + 27]),
        "ap_air_quality": getAirQuality(),
    }
    return info;
}


/**
 * end for air purifier
 */