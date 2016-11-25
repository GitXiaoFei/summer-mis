/**
 * 此文件配合Block101使用
 */

function showChoice(choiceBox) {
	if ($("#" + choiceBox + "choice li").length > 0) {
		$("#" + choiceBox + "choice").show(200);
	} else {
		showMsg("暂无可选内容");
	}
}

function choiceItem(choiceBox, item, childPage, dataUrl){
	var classType = "所有大类,所有中类,所有系列,(空),所有品牌";
	var value = classType.indexOf(item) == -1 ? item : "";
	if(childPage){
		$("#" + choiceBox + "input").attr({"value":value});
		 $.post(dataUrl, {parent: item},
				function(result){
					$("#" + choiceBox + "choice").hide();
					$("#" + childPage + "list ul").html(result);
		 		});
		$("#" + childPage + "choice").show(200);
	}else{
		$("#" + choiceBox + "choice").hide();
		$("#" + choiceBox + "input").attr({"value":value});
	}
}

/*加号*/
function addClick(){
	var n = 0;
	var m = 0;
	var plus = $(".gobuy .plus");
	var minus = $(".gobuy .minus");
	var inputEvent = $("inout[name='buyNumber']");
	
	n = parseInt($(this).prev().val());
	m = parseInt($(this).parents(".c_buy").find(".nub").html());
	if( n >= m ){
		$(this).prev().val(m).attr({"value":m});
	}else{
		n++;
		$(this).prev().val(n).attr({"value":n});
	}
};

/*减号*/
function diffClick(){
	var n = 0;
	var m = 0;
	var plus = $(".gobuy .plus");
	var minus = $(".gobuy .minus");
	var inputEvent = $("inout[name='buyNumber']");
	
	n = parseInt($(this).next().val());
	if( n < 1){
		$(this).next().val("0").attr({"value": 0});
	}
	else{
		n--;
		$(this).next().val(n).attr({"value":n});
	}
}

function inputEvent(val,obj){
	$(obj).val("");
}
