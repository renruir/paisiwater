var maxG;
var speed;
var scale = 1;
var centerX,centerY;
rad = Math.PI * 2 / 100; //将360度分成100份，那么每一份就是rad度
var canvas;
var context;  //获取画图环境，指明为2d
var img;
var text_elem;

// function reloadVars() {
//
// }

//绘制蓝色外圈
function blueCircle(n) {
    context.save();
    context.strokeStyle = "#d0eff4"; //设置描边样式
    context.lineWidth = 60 * scale; //设置线宽
    context.beginPath(); //路径开始
    context.arc(centerX, centerY, 190 * scale, -Math.PI / 2, -Math.PI / 2 + n * rad, false); //用于绘制圆弧context.arc(x坐标，y坐标，半径，起始角度，终止角度，顺时针/逆时针)
    context.stroke(); //绘制
    // context.closePath(); //路径结束
    context.restore();
}


//百分比文字绘制
// function text(n) {
//     context.save(); //save和restore可以保证样式属性只运用于该段canvas元素
//     context.strokeStyle = "#fff"; //设置描边样式
//     context.font = "60px Arial"; //设置字体大小和字体
//     //绘制字体，并且指定位置
//     context.strokeText(n.toFixed(0) + "%", centerX - 25, centerY + 10);
//     context.stroke(); //执行绘制
//     context.restore();
// }

//动画循环
function drawFrame() {
    if (speed < maxG)
        window.requestAnimationFrame(drawFrame);
    context.clearRect(0, 0, canvas.width, canvas.height);
    blueCircle(speed);
    text_elem.innerText = speed + '%';
    if (speed > 100) speed = 0;
    speed += 1;
}

function drawProgress(canvasId, max) {
    var index = canvasId.charAt(canvasId.length - 1); /*第几组*/
    canvas = document.getElementById(canvasId);
    context = canvas.getContext('2d');
    img = document.getElementById('round_' + index);
    var docEl = document.documentElement;
    var clientWidth = docEl.clientWidth;
    if (clientWidth < 720) {
        scale = clientWidth / 720;
    }

    var content_main = document.getElementById('content_main_' + index);
    centerY = img.offsetTop + img.height / 2 - 11 * scale;   //Canvas中心点y轴坐标
    centerX = img.offsetLeft + img.width / 2;  //Canvas中心点x轴坐标
    speed = 0; //加载的快慢就靠它了
    canvas.width = content_main.clientWidth;
    canvas.height = content_main.clientHeight;
    maxG = max;
    text_elem = document.getElementById('percent_label_' + index);
    drawFrame()
}