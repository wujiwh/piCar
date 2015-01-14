var WH;
if (!!WH && (typeof WH != 'object' || WH.name)) throw new Error("namespace already exists!");
WH = {};

WH.name = 'wangheng\'s car control project!';
WH.author="wangheng";
WH.version="0.1";
(function($) {

	WH.Common = {
		ajaxRequest : _ajaxRequest
	};

	function _ajaxRequest(actionUrl, postData, successHandler,
		errorHandler) {
			setTimeout(function() {
				$.ajax({
					cache : false,
				async : true,
				type : "post",
				url : actionUrl,
				data : postData,
				dataType : "json",
				success : successHandler,
				error : errorHandler
				});
			}, 0);
		}

})(jQuery);
