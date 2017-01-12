var prepaid = "";
function weixinRecharge(amount, cardNo,body,notifyUrl,payType,orderId, noticeurl) {
	if (parseFloat(amount) <= 0) {
		winBox("请输入金额");
		return;
	}
	$.ajax({
		async : false,
		type : 'post',
		url : 'FrmWeixinPay.recharge',
		data : {
			'body' : body,
			'amount' : amount,
			'cardNo' : cardNo,
			'notifyUrl' : notifyUrl,
			'payType':payType,
			'orderId':orderId
		},
		dataType : 'json',
		success : function(data) {
			if (data.result == 'error') {
				winBox(data.msg);
				return;
			}
			prepaid = data.prepaid;
			if (typeof WeixinJSBridge == "undefined") {
				if (document.addEventListener) {
					document.addEventListener('WeixinJSBridgeReady',
							onBridgeReady, false);
				} else if (document.attachEvent) {
					document.attachEvent('WeixinJSBridgeReady',
							onBridgeReady);
					document.attachEvent('onWeixinJSBridgeReady',
							onBridgeReady);
				}
			} else {
				onBridgeReady();
			}
			if(data.result == 'successAndroid'){
				JSobj.wxPay(prepaid.appid.toString(), prepaid.mch_id.toString(), 
						prepaid.prepay_id.toString(), prepaid.nonce_str.toString(), 
						prepaid.timestamp.toString(), prepaid.sign.toString());
			}
			if(data.result == 'successIPhone'){
				window.webkit.messageHandlers.webViewApp.postMessage(prepaid);
				/*webViewApp(prepaid.appid.toString(), prepaid.mch_id.toString(), 
						prepaid.prepay_id.toString(), prepaid.nonce_str.toString(), 
						prepaid.timestamp.toString(), prepaid.sign.toString());*/
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			winBox("出现未知异常");
			return;
		}
	});
}

function onBridgeReady() {
	var jsonData = JSON.parse(prepaid);
	WeixinJSBridge.invoke('getBrandWCPayRequest', {
		"appId" : jsonData.appId,
		"timeStamp" : jsonData.timeStamp,
		"nonceStr" : jsonData.nonceStr,
		"package" : jsonData.packages,
		"signType" : "MD5",
		"paySign" : jsonData.paySign
	}, function(res) {
		if (res.err_msg == "get_brand_wcpay_request:ok") {
			window.location.href='FrmActionResult?msg=订单完成!&url=FrmCardPage';
		}else{
			window.location.href='FrmActionResult?msg=订单未完成!&url=FrmCardPage';
		}
	});
}

function ReturnForApp(remark){
	
	if (remark.indexOf("成功")) {
		window.location.href='FrmActionResult?msg=订单完成!&url=FrmCardPage';
	}else if (remark.indexOf("失败")) {
		window.location.href='FrmActionResult?msg=订单未完成!&url=FrmCardPage';
	}else{
		winBox(remark);
	}
}
