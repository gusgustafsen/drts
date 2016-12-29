$(document).ready(function(){
	
	// Set the unload message whenever any input element get changed.
    $('#request-form :input').on('change', function() {
        setConfirmUnload(true);
    });

    // Turn off the unload message whenever a form get submitted properly.
    $('form#request-form').on('submit', function() {
        setConfirmUnload(false);
    });
	
	$("#fileuploader").uploadFile({
		url: "../file",
		multiple: true,
		fileName: "myfile",
		showStatusAfterSuccess: false,
		showAbort: false,
		showDone: false,
		uploadButtonClass: "upload-button",
		dragDropStr: "",
		allowedTypes: "pdf,txt,csv,xls,xlsx,ppt,pptx,doc,docx,bmp,jpg,jpeg,png,tiff,gif",
		dynamicFormData: function()
		{
			var current_request = null;
			
			current_request = $("#current_request_id_div").text();
			
			console.log("current_request: " + current_request);
			
			var data = {current_request_id: current_request}
			
			return data;
		},
		onSuccess:function(files, data, xhr)
		{
			location.reload();
		}
	});
	
	var active_tab = $("#current_tab").text();
	
    $("#drtsTabs").tabs({
        heightStyle: "content",
        active: active_tab
    });
    
    $(".datepicker").datepicker({
        minDate: new Date,
        dateFormat: 'mm-dd-yy'
    });
    
    $(".date-from").datepicker({
    	dateFormat: 'mm-dd-yy',
    	onClose: function(selectedDate){
    		$(".date-to").datepicker("option", "minDate", selectedDate);
    	}
    });
    	
    $(".date-to").datepicker({
    	dateFormat: 'mm-dd-yy',
    	onClose: function(selectedDate){
    		$(".date-from").datepicker("option", "maxDate", selectedDate);
    	}
    });
    
    $(".requested-date-from").datepicker({
    	dateFormat: 'mm-dd-yy',
    	onClose: function(selectedDate){
    		$(".requested-date-to").datepicker("option", "minDate", selectedDate);
    	}
    });
    	
    $(".requested-date-to").datepicker({
    	dateFormat: 'mm-dd-yy',
    	onClose: function(selectedDate){
    		$(".requested-date-from").datepicker("option", "maxDate", selectedDate);
    	}
    });
    
    $(".resolved-date-from").datepicker({
    	dateFormat: 'mm-dd-yy',
    	onClose: function(selectedDate){
    		$(".resolved-date-to").datepicker("option", "minDate", selectedDate);
    	}
    });
    	
    $(".resolved-date-to").datepicker({
    	dateFormat: 'mm-dd-yy',
    	onClose: function(selectedDate){
    		$(".resolved-date-from").datepicker("option", "maxDate", selectedDate);
    	}
    });
    
    $(".updated-date-from").datepicker({
    	dateFormat: 'mm-dd-yy',
    	onClose: function(selectedDate){
    		$(".updated-date-to").datepicker("option", "minDate", selectedDate);
    	}
    });
    	
    $(".updated-date-to").datepicker({
    	dateFormat: 'mm-dd-yy',
    	onClose: function(selectedDate){
    		$(".updated-date-from").datepicker("option", "maxDate", selectedDate);
    	}
    });
    
    $('#top-menu li.active > ul > li').hover(
        function()
        {
            $(this).parent('ul').parent('li').addClass('menuActive');
        },
        function()
        {
            $(this).parent('ul').parent('li').removeClass('menuActive');
        }
	);

	$("#top-menu li:not('li.active') > ul > li").hover(
		function()
        {
			$(this).parent('ul').parent('li').addClass('menuInactive');
		},
		function()
        {
			$(this).parent('ul').parent('li').removeClass('menuInactive');
		}
	);
});

function setConfirmUnload(on)
{
    var message = "You have made changes to the request. Do you want to leave the page without saving the changes?";
    window.onbeforeunload = (on) ? function() { return message; } : null;
}

