$(window).load(function() {
    $(".loader").fadeOut("slow");
});

$(document).ready(function() {
	//Disable cut copy paste
    $('body').bind('cut copy paste', function (e) {
        e.preventDefault();
    });
   
    //Disable mouse right click
    $("body").on("contextmenu",function(e){
        return false;
    });

//========================= character 255 limit
(function ($) {
    "use strict";
    $.fn.charCounter1 = function (options) {
        if (typeof String.prototype.format == "undefined") {
            String.prototype.format = function () {
                var content = this;
                for (var i = 0; i < arguments.length; i++) {
                    var replacement = '{' + i + '}';
                    content = content.replace(replacement, arguments[i]);
                }
                return content;
            };
        }
        var options = $.extend({
            backgroundColor: "#e6fefe", /*"#e5f5e3;",*/ /*"#FFFFFF",*/
            position: {right: 10, //top: 10
                bottom: 10
            },
            font: { size: 11,color: "#034a4a"}, /*#a26c0a*/ /*#646464*/ 
            limit: 255
        }, options);
        return this.each(function () {
            var el = $(this),
                wrapper = $("<div/>").addClass('focus-textarea').css({
                    "position": "relative",
                        "display": "inline-block"
                }),
                label = $("<span/>").css({
                   /* "zIndex": 999,*/
                	 "zIndex": 0,
                        "backgroundColor": options.backgroundColor,
                        "position": "absolute",
                        "font-size": options.font.size,
                        "color": options.font.color
                }).css(options.position);
            if(options.limit > 0){
                label.text("{0}/{1}".format(el.val().length, options.limit));
                el.prop("maxlength", options.limit);
            }else{
                label.text(el.val().length);
            }
            el.wrap(wrapper);
            el.before(label);
            el.on("keyup", updateLabel).on("keypress", updateLabel).on('keydown', updateLabel);
            function updateLabel(e) {
                if(options.limit > 0){
                    label.text("{0}/{1}".format($(this).val().length, options.limit));
                }else{
                    label.text($(this).val().length);
                }
            }
        });
    }
})(jQuery);
$(".char-counter1").charCounter1();

//=========================== Character 100 limit
(function ($) {
    "use strict";
    $.fn.charCounter100 = function (options) {
        if (typeof String.prototype.format == "undefined") {
            String.prototype.format = function () {
                var content = this;
                for (var i = 0; i < arguments.length; i++) {
                    var replacement = '{' + i + '}';
                    content = content.replace(replacement, arguments[i]);
                }
                return content;
            };
        }
        var options = $.extend({
            backgroundColor: "#e6fefe", /*"#e5f5e3;",*/ /*"#FFFFFF",*/
            position: {right: 10, //top: 10
                bottom: 10
            },
            font: { size: 11,color: "#034a4a"}, /*#a26c0a*/ /*#646464*/ 
            limit: 100
        }, options);
        return this.each(function () {
            var el = $(this),
                wrapper = $("<div/>").addClass('focus-textarea').css({
                    "position": "relative",
                        "display": "inline-block"
                }),
                label = $("<span/>").css({
                   /* "zIndex": 999,*/
                	 "zIndex": 0,
                        "backgroundColor": options.backgroundColor,
                        "position": "absolute",
                        "font-size": options.font.size,
                        "color": options.font.color
                }).css(options.position);
            if(options.limit > 0){
                label.text("{0}/{1}".format(el.val().length, options.limit));
                el.prop("maxlength", options.limit);
            }else{
                label.text(el.val().length);
            }
            el.wrap(wrapper);
            el.before(label);
            el.on("keyup", updateLabel).on("keypress", updateLabel).on('keydown', updateLabel);
            function updateLabel(e) {
                if(options.limit > 0){
                    label.text("{0}/{1}".format($(this).val().length, options.limit));
                }else{
                    label.text($(this).val().length);
                }
            }
        });
    }
})(jQuery);
$(".char-counter100").charCounter100();

//=========================== Character 1000 limit
(function ($) {
    "use strict";
    $.fn.charCounter1000 = function (options) {
        if (typeof String.prototype.format == "undefined") {
            String.prototype.format = function () {
                var content = this;
                for (var i = 0; i < arguments.length; i++) {
                    var replacement = '{' + i + '}';
                    content = content.replace(replacement, arguments[i]);
                }
                return content;
            };
        }
        var options = $.extend({
            backgroundColor: "#e6fefe", /*"#e5f5e3;",*/ /*"#FFFFFF",*/
            position: {right: 10, //top: 10
                bottom: 10
            },
            font: { size: 11,color: "#034a4a"}, /*#a26c0a*/ /*#646464*/ 
            limit: 1000
        }, options);
        return this.each(function () {
            var el = $(this),
                wrapper = $("<div/>").addClass('focus-textarea').css({
                    "position": "relative",
                        "display": "inline-block"
                }),
                label = $("<span/>").css({
                   /* "zIndex": 999,*/
                	 "zIndex": 0,
                        "backgroundColor": options.backgroundColor,
                        "position": "absolute",
                        "font-size": options.font.size,
                        "color": options.font.color
                }).css(options.position);
            if(options.limit > 0){
                label.text("{0}/{1}".format(el.val().length, options.limit));
                el.prop("maxlength", options.limit);
            }else{
                label.text(el.val().length);
            }
            el.wrap(wrapper);
            el.before(label);
            el.on("keyup", updateLabel).on("keypress", updateLabel).on('keydown', updateLabel);
            function updateLabel(e) {
                if(options.limit > 0){
                    label.text("{0}/{1}".format($(this).val().length, options.limit));
                }else{
                    label.text($(this).val().length);
                }
            }
        });
    }
})(jQuery);
$(".char-counter1000").charCounter1000();

});






//================ Print Report===================
function printDivOptimize(divName,header,allLbl,allVal,divwatermarkid) {
	$('.lastCol').hide();
	$("div#divShow").empty();
	$("div#divShow").show();
	
	var row="";
 	var printLbl = allLbl; 
 	var printVal = allVal; 
	    row +='<div class="print_content"> ';
	 	row +='<div class="print_logo"> ';
		row +='<div class="row"> ';
		/*row +='<div class="col-3 col-sm-3 col-md-3"><img src="js/miso/images/indianarmy_smrm5aaa.jpg"></div> ';
		row +='<div class="col-6 col-sm-6 col-md-6"><h3>'+header+'</h3> </div> ';
		row +='<div class="col-3 col-sm-3 col-md-3" align="right"><img src="js/miso/images/dgis-logo.png"></div> ';
		row +='</div> ';
		row +='</div> ';*/
		row +='<div class="col-2 col-sm-2 col-md-2"><img src="js/login/images/indian-army-logo2.png" style="height: 35px;"></div> ';
		row +='<div class="col-8 col-sm-8 col-md-8"><h6 class="top_head">human resource management system 3.0</h6></div> ';
		row +='<div class="col-2 col-sm-2 col-md-2" align="right"><img src="js/login/images/indian-army-logo1.png" style="max-width: 155px; height: 35px;"></div> ';
		
		row +='</div> ';
		row +='</div> ';
		row +='<h6 class="inn_head">'+header+'</h6>';
		
		row +='	<div class="print_text"> ';
		row +='<div class="row"> ';
		
		var i;
		for (i = 0; i < printLbl.length; i++) {
			row +='<div class="col-12 col-sm-6 col-md-6"><label class=" label_left form-control-label" id="lbluc">'+ printLbl[i]+'</label> ';
			row +='<label class="label_right" id="uploaded_wepelbl">'+ printVal[i]+'</label> </div>';
		}
		row +='</div> ';
		row +='</div> ';
		row +='</div> ';
		
	 $("div#divShow").append(row); 
	 
	 $("div#"+divwatermarkid).val('').addClass('watermarked');	
     watermarkreport();
	 
 		let popupWinindow
 		let innerContents = document.getElementById(divName).innerHTML;
 		popupWinindow = window.open('', '_blank', 'width=600,height=700,scrollbars=yes,menubar=no,toolbar=no,location=no,status=no,titlebar=no');
 		popupWinindow.document.open();
 		popupWinindow.oncontextmenu = function () {
 			return false;
 		}
	   // popupWinindow.document.write('<html><head><link rel="stylesheet" href="js/miso/assets/css/bootstrap.min.css"><link rel="stylesheet" href="js/miso/assets/scss/style.css"><style>table td{font-size:12px;} table th{font-size:13px !important;}</style></head><body onload="window.print()"  oncopy="return false" oncut="return false" onpaste="return false" oncontextmenu="return false">' + innerContents + '</html>');
       popupWinindow.document.write('<html><head><link rel="stylesheet" href="js/css/bootstrap.min.css"><link rel="stylesheet" href="js/css/style.css"><link rel="stylesheet" href="js/printWatermark/printWatermark.css"><style> table td{font-size:12px;font-weight:normal;} table th{font-size:13px !important;} </style></head><body onload="window.focus(); window.print(); window.close();" oncopy="return false" oncut="return false" onpaste="return false" oncontextmenu="return false">' +innerContents+  '</html>');
	   
	   popupWinindow.document.close();
	   $("div#divShow").hide();
	   $('.lastCol').show();
}

//================= Server Side Validation
function isValidateServerSide()
{
	$(".errorClass").hide();
	$(".errMsgServer").empty();
	$(".errMsgClient").empty();
	$("div#divLine").hide();
	var errMsg = "";
    var errCount = 0;
    var spans = document.getElementsByClassName('errorClass');
	for(var i=0; i<spans.length; i++) {
		if(spans[i].innerHTML != "")
		{
			errCount++;
			errMsg += "<div class='col-md-3'>"+spans[i].outerHTML+"</div>";
		}
	}  
	if (errCount > 0) {		
		$(".errMsgServer").append("<div class='row'>" + errMsg + "</div>");
		$("div#errMsgServerDiv").hide();
		$("div#divLine").show();
		$(".errorClass").show();
		return false;
	} 
	else return true;
	
	return true;
}
//=================================== Validation For AADHAAR No.
function AadharValidate() {
    var aadhar = document.getElementById("aadhar_no").value;
    var adharcardTwelveDigit = /^\d{12}$/;
   // var adharSixteenDigit = /^\d{16}$/;
    if (aadhar != '') {
        if (aadhar.match(adharcardTwelveDigit)) {
            return true;
        }
       /* else if (aadhar.match(adharSixteenDigit)) {
            return true;
        }*/
        else {
           alert("Invalid Aadhaar No.");
           document.getElementById("aadhar_no").value="";
           $("input#aadhar_no").focus();
            return false;
        }
        
    }
    
    return true;
}

//========================= Validation for PAN No.
function PANValidate()
{	
	var panVal = $('#pan_no').val();
	var regpan = /^([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}?$/;

	if(regpan.test(panVal)){
		return true;
	} else {
		  alert("Invalid PAN No.");
		  document.getElementById("pan_no").value=""; 
		  $("#pan_no").focus();
		  return false;
	}
	
	return true;
}

//=========================== Validation for IFSC Code
function IFSC_CodeValidate()
{	
	var IFSC_CodeVal = $('#ifsc').val();
	var regpan =  /^[A-Z]{4}0[A-Z0-9]{6}$/;
	
	if(regpan.test(IFSC_CodeVal)){
		return true;
	} else {
		  alert("Invalid IFSC Code");
		  document.getElementById("ifsc").value=""; 
		  $("#ifsc").focus();
		  return false;
	}
	return true;
}

//======================== Validation For Only integer
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
 
//=================== Validation For Double value
function isNumberKeyDouble(evt) {
	  var charCode = (evt.which) ? evt.which : evt.keyCode;
	if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	} else {
		if (evt.target.value.search(/\./) > -1 && charCode == 46) {
			return false;
		}
	}		 
	return true;
}

//======================== Validation For String
function onlyAlphabetsString(e, t) {
    try {
        if (window.event) {
            var charCode = window.event.keyCode;
        }
        else if (e) {
            var charCode = e.which;
        }
        else { return true; }
        if ((charCode > 64 && charCode < 91) || (charCode > 96 && charCode < 123))
            return true;
        else
            return false;
    }
    catch (err) {
        alert(err.Description);
    }
} 
//============================== Validation For String and special characters
function onlyAlphabets(e, t) {
    try {
        if (window.event) {
            var charCode = window.event.keyCode;
        }
        else if (e) {
            var charCode = e.which;
        }
        else { return true; }
        if ((charCode > 57 && charCode < 127) || (charCode > 31 && charCode < 48))
            return true;
        else
            return false;
    }
    catch (err) {
        alert(err.Description);
    }
} 

//========================== Child Window Closed
function checkChild() {
    if (newWin.closed) {
        location.reload();
    }
}

//============================ whole String in replaceall string
String.prototype.replaceAll = function(searchStr, replaceStr) {
	var str = this;
    // no match exists in string?
    if(str.indexOf(searchStr) === -1) {
        // return string
        return str;
    }
    // replace and remove first match, and do another recursirve search/replace
    return (str.replace(searchStr, replaceStr)).replaceAll(searchStr, replaceStr);
}


function toHex(str) {
	var hex = '';
	for(var i=0;i<str.length;i++) {
		hex += ''+str.charCodeAt(i).toString(16);
	}
   	return hex;    		
}
function hex_to_ascii(str1)
{
	var hex  = str1.toString();
	var str = '';
	for (var n = 0; n < hex.length; n += 2) {
		str += String.fromCharCode(parseInt(hex.substr(n, 2), 16));
	}
	return str;
}