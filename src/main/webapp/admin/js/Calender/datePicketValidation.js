	function datepicketDate(inpt){
		$('#'+inpt).datepicker({showOn: 'both', buttonImageOnly: true,
			buttonImage: 'js/Calender/cal_ico.png',
			dateFormat: 'dd/mm/yy',
			changeMonth: true,
			changeYear: true,
			yearRange: '1890:2099',
			maxDate: new Date()
		});
		$('img.ui-datepicker-trigger').css({'cursor' : 'pointer', "vertical-align" : 'middle'});
	}
	
	function validateDate(a,c) {
		var b =/(^(((((0?[1-9])|(1\d)|(2[0-8]))\/((0?[1-9])|(1[0-2])))|((31\/((0[13578])|(1[02])))|((29|30)\/((0?[1,3-9])|(1[0-2])))))\/(((18|19|20)[0-9][0-9]))|(29\/0?2\/(18|19|20)(([02468][048])|([13579][26]))))$)/i;
		if (a == "DD/MM/YYYY") {} 
		else {
			if (b.test(a) == false) {
				alert("Invalid Date");
				c.value = "";
				c.focus()
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
			a.value = b
		} else {
			a.style.color = "#000000"
		}
	}
	
	function convertDateFormate(date){
		 var data="";
		  if(date!=null && date!=""){
			var d = new Date(date);
			data = [
				('0' + d.getDate()).slice(-2),
				 d.toLocaleString('default', { month: 'short' }),
				d.getFullYear()
			].join('-');
		  }
			return data;
		}

	

	
	 function ParseDateColumn(data) {
		
		  var date="";
		  if(data!=null && data!=""){			 
			 var d = new Date(data);			 
			 var day=('0' + d.getDate()).slice(-2);
			 var month=('0' + (d.getMonth() + 1)).slice(-2);
			 var year=""+d.getFullYear();		    				 
			 date=year+"-"+month+"-"+day;
			   		}
			    return date;
		}
	 
	 function ParseDateColumncommission(data) {
		  
		  var date="";
		  if(data!=null && data!=""){			 
			 var d = new Date(data);			 
			 var day=('0' + d.getDate()).slice(-2);
			 var month=('0' + (d.getMonth() + 1)).slice(-2);
			 var year=""+d.getFullYear();		    				 
			// date=year+"-"+month+"-"+day;
			 date=day+"/"+month+"/"+year;
			   		}
			    return date;
		}
	 
	 function validateDate_BackDate(a,c) {
			var b =/(^(((((0?[1-9])|(1\d)|(2[0-8]))\/((0?[1-9])|(1[0-2])))|((31\/((0[13578])|(1[02])))|((29|30)\/((0?[1,3-9])|(1[0-2])))))\/(((18|19|20)[0-9][0-9]))|(29\/0?2\/(18|19|20)(([02468][048])|([13579][26]))))$)/i; ////  for dd/mm/yyyy
			//var b = /^(0[1-9]|1\d|2\d|3[01])\-(0[1-9]|1[0-2])\-(19|20)\d{2}$/; //  for dd-MM-yyyy
			if (a == "DD/MM/YYYY") {} 
			else {
				if (b.test(a) == false) {
					alert("Invalid Date");
					c.value = "";
					c.focus()
				}else{
					var d = new Date();
					var dateParts = a.split("/");
					var dateObject = new Date(+dateParts[2], dateParts[1] - 1, +dateParts[0]);
					if(d < dateObject){
						alert("Invalid Date");
						c.value = "";
						c.focus()
					}
				}
			}
		}
		
		function validateDate_FutureDate(a,c) {
			var b =/(^(((((0?[1-9])|(1\d)|(2[0-8]))\/((0?[1-9])|(1[0-2])))|((31\/((0[13578])|(1[02])))|((29|30)\/((0?[1,3-9])|(1[0-2])))))\/(((18|19|20)[0-9][0-9]))|(29\/0?2\/(18|19|20)(([02468][048])|([13579][26]))))$)/i; ////  for dd/mm/yyyy
			//var b = /^(0[1-9]|1\d|2\d|3[01])\-(0[1-9]|1[0-2])\-(19|20)\d{2}$/; //  for dd-MM-yyyy
			if (a == "DD/MM/YYYY") {} 
			else {
				if (b.test(a) == false) {
					alert("Invalid Date");
					c.value = "";
					c.focus()
				}else{
					var d = new Date();
					var dateParts = a.split("/");
					var dateObject = new Date(+dateParts[2], dateParts[1] - 1, +dateParts[0]);
					if(d < dateObject){
						alert("Invalid Date");
						c.value = "";
						c.focus()
					}
				}
			}
		}
		
		
		function setMaxDate(id,max){
			var dateParts = max.split("/");
			$('#'+id).datepicker('option', 'maxDate', new Date(+dateParts[2], dateParts[1] - 1, +dateParts[0]));

		}

		   function setMinDate(id,min){
				var dateParts = min.split("/");
				$('#'+id).datepicker('option', 'minDate', new Date(+dateParts[2], dateParts[1] - 1, +dateParts[0]));

			}

		function getformatedDate(dateString){
			var dateParts = dateString.split("/");
			return new Date(+dateParts[2], dateParts[1] - 1, +dateParts[0]); 
		}
		
		
function isNumberKey(evt) {

	var charCode = (evt.which) ? evt.which : evt.keyCode;
	if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	} else {
		if (charCode == 46) {
			return false;
		}
	}
	return true;

}
		
		