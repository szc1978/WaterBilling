

$(function (){
	$("#float_number_input").myvalidate({
		filter_type: "positiveNumber",
		enterCallback: function (obj){
			//enter key callback
			alert(parseFloat(obj.val()));
		}, valCallback: function (val){
			//pressup callback,  return value
			//$("div").html(val);
		}
	});
	$("#float_number_input").focus();
});

(function ($) {  
    var printAreaCount = 0;  
    $.fn.printArea = function () {  
        var ele = $(this);  
        var idPrefix = "printArea_";  
        removePrintArea(idPrefix + printAreaCount);  
        printAreaCount++;  
        var iframeId = idPrefix + printAreaCount;  
        var iframeStyle = 'position:absolute;width:0px;height:0px;left:-500px;top:-500px;';  
        iframe = document.createElement('IFRAME');  
        $(iframe).attr({  
            style: iframeStyle,  
            id: iframeId  
        });  
        document.body.appendChild(iframe);  
        var doc = iframe.contentWindow.document;  
        $(document).find("link").filter(function () {  
            return $(this).attr("rel").toLowerCase() == "stylesheet";  
        }).each(  
                function () {  
                    doc.write('<link type="text/css" rel="stylesheet" href="'  
                            + $(this).attr("href") + '" >');  
                });  
        doc.write('<div class="' + $(ele).attr("class") + '">' + $(ele).html()  
                + '</div>');  
        doc.close();  
        var frameWindow = iframe.contentWindow;  
        frameWindow.close();  
        frameWindow.focus();  
        frameWindow.print();  
    }  
    var removePrintArea = function (id) {  
        $("iframe#" + id).remove();  
    };  
})(jQuery); 

function float_input_validate(obj){
	obj.value = obj.value.replace(/[^\d.]/g,""); //清除"数字"和"."以外的字符
	obj.value = obj.value.replace(/^\./g,""); //验证第一个字符是数字而不是
	obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的
	obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
	obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'); //只能输入两个小数
}

function int_input_validate(obj) {
	obj.value = obj.value.replace(/[^\d]/g,"");
}