/**
 * Created by renrui on 2016/12/6 0006.
 */

//一次报文中固定的头和尾的总长度
const FIXED_LENGTH_MESSAGE = 10;

//状态数据回复数据包长度
const STATE_DATA_PACKAGE_LENGTH = 45;
//滤芯复位数据包长度
const FILTER_RESET_PACKAGE_LENGTH = 50;

const ADDR_PURE_WATER_DEVICE = 0x24; //净水器客户端

const ADDR_PHONE_CLINET = 0XD5;//手机app及其他客户端

//设备型号
const DEVICE_MODEL_CR400D1 = "CR400D1";
const DEVICE_MODEL_CD400D1 = "CD400D1";

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

// fault code
const DEVICE_FAULT_K1 = "进水电磁阀K1故障";

const DEVICE_FAULT_K2 = "冲洗电磁阀K2故障";

const DEVICE_FAULT_K3 = "增压泵K3故障";

const DEVICE_FAULT_HIGH_PRESSURE = "高压开关故障或忘记关水";

const DEVICE_FAULT_WATER_LEAKAGE = "设备漏水";

const DEVICE_FAULT_REVERSE_VALVE_FAULT = "高压开关故障或忘记关水";

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


function getDeviceModel() {
    var device_model;
    switch (parseInt(dataHex[index+2], 16)) {
        case 0x10:
            device_model = DEVICE_MODEL_CR400D1;
            break;
        case 0x11:
            device_model = DEVICE_MODEL_CD400D1;
            break;
    }
    return device_model;
}

function getRinseState() {
    var rinse_state = RINSE_INVALID;
    switch (parseInt(dataHex[index+3], 16)) {
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
    switch (parseInt(dataHex[index+4], 16)) {
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
    }
    return working_state;
}

function getFaultState() {
    if (parseInt(dataHex[index+5], 16) == 0x01) {
        is_fault = true;
    } else {
        is_fault = false;
    }
    return is_fault;
}

function getFaultCodeNum() {
    if(is_fault){
        var errorCode = parseInt(dataHex[index+6]);
        return errorCode;
    }
}

function getFaultCode() {
    if (is_fault) {
        switch (parseInt(dataHex[index+6], 16)) {
            case 0x01:
                fault_code = DEVICE_FAULT_K1;
                break;
            case 0x02:
                fault_code = DEVICE_FAULT_K2;
                break;
            case 0x03:
                fault_code = DEVICE_FAULT_K3;
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
            default:
                fault_code = DEVICE_FAULT_UNKNOW;
        }
    }
    return fault_code;
}

function getFilterMargin() {
    var filter_margin = new Array(parseInt(dataHex[index+7], 16), parseInt(dataHex[index+8], 16),
        parseInt(dataHex[index+9], 16), parseInt(dataHex[index+10], 16),
        parseInt(dataHex[index+11], 16), parseInt(dataHex[index+12], 16));
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
    return getPressure(dataHex[index+17], dataHex[index+18]);
}

function getBeforeFilterPressure() {
    return getPressure(dataHex[index+19], dataHex[index+20]);
}

function getPureWaterPressure() {
    return getPressure(dataHex[index+21], dataHex[index+22]);
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
        console.log("file size: "+sizeStr);
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
        sendMessage(updateInfo, arguments[4]);
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
    }else if(arguments[0] == COMMAND_UV){
        data[0] = 0x16;//UV灯
        data[2] = 0x01;//on
    }
    //send message to MQTT server
    sendMessage(data, arguments[1]);
}

//查询设备状态，页面第一次打开或者重新加载时发送次命令
function queryDeviceStates(deviceId) {
    var data = new Array(0x21, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff);
    sendMessage(data, deviceId);
}

//按照协议插入固定格式的包头和包尾
function insertHeader(data) {
    if (data.length == 0) {
        return;
    }
    var length = data.length + FIXED_LENGTH_MESSAGE;
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
    // var test = [0x55, 0xAA, 0x35, 0xE5, 0x13, 0xB1, 0x12, 0xff, 0x01, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff, 0xff];
    var crcResult = getCrc16(data);

    data.push(parseInt(crcResult.substr(4,2), 16), parseInt(crcResult.substr(2,2), 16));
    return data;
}

function getCrc16(arry) {
    if(arry.length == 0){
        return;
    }
    var crc = 0xffff;
    var temp;
    var length = arry.length;
    for (var i = 0; i < length; i++) {
        temp = arry[i] & 0x00ff;
        crc = crc ^ temp;
        for(var j = 0; j < 8; j++){
            if(crc & 0x0001){
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
function sendMessage(data, deviceId) {
    var msg = insertHeader(data);
    console.info("send message's length: " + msg.length);
    console.info("send message is: " + msg.toLocaleString());
    var buffer = new ArrayBuffer(msg.length);
    var byteStream = new Uint8Array(buffer);
    for (var i = 0; i < msg.length; i++) {
        byteStream[i] = msg[i];
    }
    message = new Paho.MQTT.Message(byteStream);
    message.destinationName = "nodes/" + deviceId + "/cmd";

    message._setRetained(true);
    client.send(message);
}

//获取状态信息
function getDeviceStateInfo(sourcedata) {

    dataHex = dec2hex(sourcedata);

    var info = {
        "device_version": getVersion(dataHex[index+1]),
        "device_model": getDeviceModel(),
        "rinse_state": getRinseState(),
        "working_state": getWorkingState(),
        "is_fault": getFaultState(),
        "fault_code": getFaultCode(),
        "filter": getFilterMargin(),
        "pure_water_temperature": getTemperature(dataHex[index+13], dataHex[index+14]),
        "machine_temperature": getTemperature(dataHex[index+15], dataHex[index+16]),
        "source_water_pressure": getSourceWaterPressure(),
        "before_filter_pressure": getBeforeFilterPressure(),
        "pure_water_pressure": getPureWaterPressure(),
        "source_water_TDS": getTDS(dataHex[index+23], dataHex[index+24]),
        "pure_water_TDS": getTDS(dataHex[index+25], dataHex[index+26])
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
            "is_reset_success": getResetResult(infoData[index+3]),
            "reset_series": getResetSeries(infoData[index+34]),
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

