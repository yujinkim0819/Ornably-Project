$(function () {
    function updateAddress() {
        let addressName = $("#addressName").val();
        let customAddressName = $("#customAddressName").val().trim();
		let address = "";
		
		if (addressName === "") {
			$("#addressName").val("");
			return;
		} else if (addressName === "customAddress") {
			address = addressName;
		}
		
		if(!addressName) {
			$("addressName").val("");
			return;
		}	
    }
	
	$("#addressName").on("change", function () {
	    if ($(this).val() === "customAddress") {
	        $(this).hide();
	        $("#customAddressName").show().focus();
	    } else {
	        $("#customAddressName").hide().val("");
	        $(this).show();
	    }
	    updateAddress();
	});
	
	$("#customAddressName").on("input", updateAddress)

});