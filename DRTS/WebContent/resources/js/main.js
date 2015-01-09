$(document).ready(function(){
    
    $("#drtsTabs").tabs({
        heightStyle: "content"
    });
    
    $(".datepicker").datepicker({
        minDate: new Date,
        dateFormat: 'mm-dd-yyyy'
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

