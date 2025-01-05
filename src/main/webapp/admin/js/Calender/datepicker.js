function disableDays(date) {
    var day = date.getDay();
    return [(day != 0)]; // 0 means sunday, 1 means monday, etc..
}

function validateDate(a,c) {
	//var b =/(^(((((0?[1-9])|(1\d)|(2[0-8]))\/((0?[1-9])|(1[0-2])))|((31\/((0[13578])|(1[02])))|((29|30)\/((0?[1,3-9])|(1[0-2])))))\/(((18|19|20)[0-9][0-9]))|(29\/0?2\/(18|19|20)(([02468][048])|([13579][26]))))$)/i; ////  for dd/mm/yyyy
	//var b = /^(0[1-9]|1\d|2\d|3[01])\-(0[1-9]|1[0-2])\-(19|20)\d{2}$/; //  for dd-MM-yyyy
	var b=/^(?:(?:31(\/|-|\.)(?:0?[13578]|1[02]))\1|(?:(?:29|30)(\/|-|\.)(?:0?[1,3-9]|1[0-2])\2))(?:(?:1[6-9]|[2-9]\d)?\d{2})$|^(?:29(\/|-|\.)(?:0?2)\3(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\d|2[0-8])(\/|-|\.)(?:(?:0?[1-9])|(?:1[0-2]))\4(?:(?:1[6-9]|[2-9]\d)?\d{2})$/; //  for dd-MM-yyyy
	//var b=/^(?:(?:1[6-9]|[2-9]\d)?\d{2})(?:(?:(\/|-|\.)(?:0?[13578]|1[02])\1(?:31))|(?:(\/|-|\.)(?:0?[13-9]|1[0-2])\2(?:29|30)))$|^(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00)))(\/|-|\.)0?2\3(?:29)$|^(?:(?:1[6-9]|[2-9]\d)?\d{2})(\/|-|\.)(?:(?:0?[1-9])|(?:1[0-2]))\4(?:0?[1-9]|1\d|2[0-8])$/;  // for yyyy-mm-dd
	if (a == "DD-MM-YYYY" || a == "") {} 
	else {
		if (b.test(a) == false) {
			alert("Invalid Date format");
			c.value = "";
			c.focus();
		}
		else if (b.test(a) == true) {
			var dt=new Date(a);
			var newdate = a.split("-").reverse().join("-");
			var newdate1=new Date(newdate);
			var day = newdate1.getDay();  
			
			if([(day != 0)]=="false")
			{
				//alert("Invalid Date because of selected date of Sunday");
				c.value = "";
				c.focus();
			}
		}
	}
}
function clickclear(a, b) {
	if (a.value == b) {
		a.value = "";
		a.style.color = "#000000"
	}else{
		a.addEventListener('keydown', function(event) {
            const key = event.key; 
            if (key === "Backspace" || key === "Delete") { }
            else{
            	var fvalue = a.value;
    			if(fvalue.length == 2){
    				a.value = a.value+"/"; 
    			}
    			if(fvalue.length == 5){
    				a.value = a.value+"/"; 
    			}
            }
        });
	} 
}
function clickrecall(a, b) {

	if (a.value == b || a.value == "") {
		a.value = "";
	} else {
		a.style.color = "#000000"
	}
}
var DateHelper = {
    addDays : function(aDate, numberOfDays) {
        aDate.setDate(aDate.getDate() + numberOfDays); // Add numberOfDays
        return aDate;                                  // Return the date
    },
    format : function format(date) {
        return [
           ("0" + date.getDate()).slice(-2),           // Get day and pad it with zeroes
           ("0" + (date.getMonth()+1)).slice(-2),      // Get month and pad it with zeroes
           date.getFullYear()                          // Get full year
        ].join('-');                                   // Glue the pieces together
        
        /* return [
           date.getFullYear(),                          // Get full year
           ("0" + (date.getMonth()+1)).slice(-2),      // Get month and pad it with zeroes
            ("0" + date.getDate()).slice(-2),           // Get day and pad it with zeroes
        ].join('-');  */                                 // Glue the pieces together
    }
}

function dateOnload(val){
	var dateParts = val.split("/");
	var dateObject = new Date(+dateParts[2], dateParts[1] - 1, +dateParts[0]);
	var d = DateHelper.format(DateHelper.addDays(dateObject, 1));
	$("#listing_dt").datepicker("option", "maxDate", new Date());
	$("#prepop_initiallease_date").datepicker("option", "minDate", d);
   // $("#prepop_finalterm_date").datepicker("option", "minDate", d);
	//$("#issue_date").datepicker("option", "minDate", d);
	getTodateWithYear(val);
}
function noFutureDate(inputid){
	$('#'+inputid).datepicker({
		showOn: 'both', 
		buttonImageOnly: true,
		buttonImage: 'WBPMP/Calender/cal_ico.png',
		dateFormat: 'dd/mm/yy',
		changeMonth: true,
		changeYear: true,
		maxDate: new Date(),
		yearRange: '1890:2099'
	});
	$('img.ui-datepicker-trigger').css({'cursor' : 'pointer', "vertical-align" : 'middle'});
}
function futureDateOnly(inputid){
	$('#'+inputid).datepicker({
		showOn: 'both', 
		buttonImageOnly: true,
		buttonImage: 'WBPMP/Calender/cal_ico.png',
		dateFormat: 'dd/mm/yy',
		changeMonth: true,
		changeYear: true,
		minDate: new Date(),
		yearRange: '1890:2099'
	});
	$('img.ui-datepicker-trigger').css({'cursor' : 'pointer', "vertical-align" : 'middle'});
}
function simpleDatePick(inputid){
	$('#'+inputid).datepicker({
		showOn: 'both', 
		buttonImageOnly: true,
		buttonImage: 'WBPMP/Calender/cal_ico.png',
		dateFormat: 'dd/mm/yy',
		changeMonth: true,
		changeYear: true,
		yearRange: '1890:2099'
	});
	$('img.ui-datepicker-trigger').css({'cursor' : 'pointer', "vertical-align" : 'middle'});
}
function FromToDateFn(fromid,toid){
	$('#'+fromid).datepicker({
		showOn: 'both', 
		buttonImageOnly: true,
		buttonImage: 'WBPMP/Calender/cal_ico.png',
		dateFormat: 'dd/mm/yy',
		changeMonth: true,
		changeYear: true,
		yearRange: '1890:2099',
		maxDate: new Date(),
		onSelect: function (selected) {
            var dateParts = selected.split("/");
			var dateObject = new Date(+dateParts[2], dateParts[1] - 1, +dateParts[0]);
			var d = DateHelper.format(DateHelper.addDays(dateObject, 1));
			$("#"+toid).datepicker("option", "minDate", d);
        }
	});
	$('img.ui-datepicker-trigger').css({'cursor' : 'pointer', "vertical-align" : 'middle'});
	$('#'+toid).datepicker({
		showOn: 'both', 
		buttonImageOnly: true,
		buttonImage: 'WBPMP/Calender/cal_ico.png',
		dateFormat: 'dd/mm/yy',
		changeMonth: true,
		changeYear: true,
		yearRange: '1890:2099',
		onSelect: function (selected) {
           $("#"+fromid).datepicker("option", "maxDate", selected);
        }
	});
	$('img.ui-datepicker-trigger').css({'cursor' : 'pointer', "vertical-align" : 'middle'});
}