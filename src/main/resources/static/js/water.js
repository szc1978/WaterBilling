

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
