
 document.addEventListener('DOMContentLoaded', function () {

	 document.getElementById('login_id').onclick =
		 function() {
			 validation();
		 };	
		
	 
	  document.getElementById('btnrefresh').onclick =
					 function() {
						   clear();
						    captcha();
					 };

					 document.getElementById('googledot_1').onclick =
						 function() {
							 gotodot(n);
						 };	
					 document.getElementById('googledot_2').onclick =
						 function() {
							 gotodot(n);
						 };
					 document.getElementById('googledot_3').onclick =
						 function() {
							 gotodot(n);
						 };	
						  document.getElementById('googledot_4').onclick =
						 function() {
							 gotodot(n);
						 };	
			
}); 
 
 

  function validation() {
			var ck_username = /^[A-Za-z0-9_]{1,20}$/;
			var ck_password =  /^[A-Za-z0-9!@#$%^&*()_]{6,20}$/;
			var a = document.getElementById("username");
			if (a.value == "" ||a.value == "'" || a.value == null || a.value.toString().trim() == "" ||a.value == "'''" ) {
				alert("Enter username");
				a.focus();
				return false;
			}
			var b = document.getElementById("password");
			if (b.value == "" || b.value == "'"|| b.value == null || b.value.toString().trim() == "" ) {
				alert("Enter password");
				b.focus();
				return false;
			}	
			var iCapcha = removeSpaces(jQuery('#txtInput').val());
			if(iCapcha == "" || iCapcha.length != 5){
				alert("Enter valid Captcha!");
				jQuery('#txtInput').focus();
		    	return false;
		    }
			if(iCapcha != ""){
				var test = ValidCaptcha(iCapcha);
				if(test != "0"){
					jQuery('#csrfIdSet').attr('name',csrfparname);
			    	jQuery('#csrfIdSet').attr('value',csrfvalue);
			    	jQuery('#myFormId').attr('action', yuji);
			    	return true;
				}else{
					alert("Captcha Validation failed!");
					jQuery('#txtInput').focus();
					return false;
				}
			}
			return false;
		}
		// Validate the Entered input aganist the generated security code function   
		function ValidCaptcha(iCapcha){
			var test = "0";
	    	try{
				$.ajax({
					url : "checkCapchaCode?"+csrfparname+"="+csrfvalue,
					type : 'POST',
					data : {iCapcha:iCapcha},
					success : function(data) {
						if(data){
							test = data;
			     		}
					},
					async : false,
				});
	    	}catch(err){
	    		console.log(err.message);
	    	}
			return test;
	    }
		// Remove the spaces from the entered and generated code
		function removeSpaces(string)
		{
		    return string.split(' ').join('');
		}
	
	 

			// Start Canvas Capcha
			function captcha() {
				$("#capcha").attr("src", "genCapchaCode");
			};
			function clear() {
				$("#txtInput").val("");
			};	


			function gotodot(n){
				hideImages();
				counter = n;
				var currentImage = imageSlides[n];
				var currentDot = circles[n];
				 
				currentImage.addClass("visible");
				removeDots();
				currentDot.addClass("dot");
				counter++;
				clearInterval(imageSlideshowInterval);
				imageSlideshowInterval = setInterval(slideshow, 10000);
			}
		
		