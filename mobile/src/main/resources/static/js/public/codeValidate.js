/*********  图形验证码 start **************/
function loadVerifyCodeImage() {
    $(".verifyCodeImage").attr("src","/user/get/verifycodeImg?"+ Math.random());
    $(".verifyCodeImage").show();
}

$(".verifyCodeImage").click(function() {
    loadVerifyCodeImage();
});

/*********  图形验证码 end ***********/

/**
 * 获取验证码60秒倒计时
 */
function remoteCodeCountDown(btnId){
    var countdown = 60;
    var btnObj = $('#' + btnId);
    countDownFunc(btnObj);
    function countDownFunc(btnObj) {
        if (countdown <= 0) {
            btnObj.removeClass("aDisabledBtn");
            btnObj.text("重新获取验证码");
            countdown = 60;
        } else {
            btnObj.addClass("aDisabledBtn");
            btnObj.text("(" + countdown + ") s 重新发送");
            countdown--;
            setTimeout(function() {
                    countDownFunc(btnObj) }
                ,1000)
        }
    }
}
