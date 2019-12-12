/**
 * Created by renrui on 2016/12/20 0020.
 */

var wave = (function () {
    var ctx;
    var wave;
    var canvas;
    var waveImage;
    var canvasWidth;
    var canvasHeight;
    var needAnimate = false;

    function init(callback) {
        wave = document.getElementById('wave');
        canvas = document.createElement('canvas');
        if (!canvas.getContext) return;
        ctx = canvas.getContext('2d');
        canvasWidth = wave.parentNode.offsetWidth;
        canvasHeight = wave.parentNode.offsetHeight;
        canvas.setAttribute('width', canvasWidth);
        canvas.setAttribute('height', canvasHeight);
        wave.appendChild(canvas);
        waveImage = new Image();
        waveImage.onload = function () {
            waveImage.onload = null;
            callback();
        }
        waveImage.src = '../images/wave_icon2.png';
    }

    function animate() {
        //如果浏览器支持requestAnimFrame则使用requestAnimFrame否则使用setTimeout
        window.requestAnimFrame = (function () {
            return window.requestAnimationFrame ||
                window.webkitRequestAnimationFrame ||
                window.mozRequestAnimationFrame ||
                function (callback) {
                    window.setTimeout(callback, 1000 / 60);
                };
        })();
        // 波浪大小
        var boHeight = canvas.height / 15;
        var posHeight = canvas.height / 1.5;
        //初始角度为0
        var step = 0;
        //定义三条不同波浪的颜色
        var lines = ["rgba(240,240,240, 0.2)",
            "rgba(0,255,255, 0.2)",
            "rgba(136,222,252, 0.2)"];

        function loop() {
            ctx.clearRect(0, 0, canvas.width, canvas.height);
            step++;
            //画3个不同颜色的矩形
            for (var j = lines.length - 1; j >= 0; j--) {
                ctx.fillStyle = lines[j];
                //每个矩形的角度都不同，每个之间相差45度
                var angle = (step + j * 50) * Math.PI / 180;
                var deltaHeight = Math.sin(angle) * boHeight;
                var deltaHeightRight = Math.cos(angle) * boHeight;
                ctx.beginPath();
                ctx.moveTo(0, posHeight + deltaHeight);
                ctx.bezierCurveTo(canvas.width / 2, posHeight + deltaHeight - boHeight, canvas.width / 2, posHeight + deltaHeightRight - boHeight, canvas.width, posHeight + deltaHeightRight);
                ctx.lineTo(canvas.width, canvas.height);
                ctx.lineTo(0, canvas.height);
                ctx.lineTo(0, posHeight + deltaHeight);
                ctx.closePath();
                ctx.fill();
            }
            requestAnimFrame(loop);
        }

        loop();
    }

    function start() {
        if (!ctx) return init(start);
        needAnimate = true;
        setTimeout(function () {
            if (needAnimate) animate();
        }, 500);
    }

    function stop() {
        needAnimate = false;
    }

    return {start: start, stop: stop};
}());
