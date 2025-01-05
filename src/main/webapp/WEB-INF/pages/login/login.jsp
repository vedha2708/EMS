<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="shortcut icon" href="admin/layout_file/images/favicon.png">
<meta name="description" content="">
<meta name="author" content="">
<title><spring:message code="myapp.title" /></title>
<link rel="stylesheet" href="admin/login_file/css/style.css">
<link href="admin/layout_file/assets/css/plugins/bootstrap.min.css"
	rel="stylesheet">



<script src="admin/layout_file/js/jquery-3.6.0.min.js"></script>
<script src="admin/js/assets/js/bootstrap.min.js"></script>


<script type="text/javascript" src="admin/ACR_Alljs/login.js"></script>

<script type="text/javascript" nonce="${cspNonce}">

	  	var   csrfparname ="${_csrf.parameterName}";
	  	var csrfvalue="${_csrf.token}";
		var yuji = "<c:url value='/auth/login_check?targetUrl=${targetUrl}' />";
		var numb = [1,2,3,4,5];
		var imageSlides=new Array();
		var circles=new Array();
		
 	jQuery(document).ready(function() {
		
			
			$('body').bind('cut copy paste', function (e) {
				e.preventDefault();
			});
			$(".dropdown").hover(function(){
				var dropdownMenu = $(this).children(".dropdown-menu");
				if(dropdownMenu.is(":visible")){
					dropdownMenu.parent().toggleClass("open");
				}
			});
			$(window).scroll(function() {
			    if ($(document).scrollTop() > 50) {
			      $(".header_bottom").addClass("head_nav");
			    } else {
			      $(".header_bottom").removeClass("head_nav");
			    }
			});
			
			var msg = "";
	   		msg = jQuery('label#msg').text();
	   		if('${error}' != ""){
				jQuery("div#errorDiv").show();
			}
			if('${msg}' != ""){
				window.alert = function(al, $){
				    return function(msg) {
				        al.call(window,msg);
				        $(window).trigger("okbuttonclicked");
				    };
				}(window.alert, window.jQuery);

				$(window).on("okbuttonclicked", function() {
				    console.log("you clicked ok");
				    window.location = window.location.href.split("?")[0];
				});
				alert('${msg}');
				jQuery("div#errorDiv").show();
			}	
			
			
		
	   		// End Canvas Capcha
	   		
	   		jQuery(document).on('keypress', function(event) {
	     		var regex = new RegExp("^[a-zA-Z0-9\\[\\] \\+ \\* \\-.,/ ~!@#$^&%_]+$");
	     	    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
// 	     	    if (!regex.test(key)) {
// 	     	       event.preventDefault();
// 	     	       return false;
// 	     	    } 
	     	});
			
			im=0;
			$(".imageSlides").each(function(){
		        imageSlides[im]=$(this);
		        im++;
		        });
		 	ic=0;
			$(".circle").each(function(){
		        circles[ic]=$(this);
		        ic++;
		        });
			$("#leftArrow").click(arrowClick);
			$("#rightArrow").click(arrowClick);
		});	
		
//	 	IMAGE SLIDES & CIRCLES ARRAYS, & COUNTER
		var counter = 0;

		// HIDE ALL IMAGES FUNCTION
		function hideImages() {
		  for (var i = 0; i < imageSlides.length; i++) {
		    imageSlides[i].removeClass("visible");
		  }
		}

		// REMOVE ALL DOTS FUNCTION
		function removeDots() {
		  for (var i = 0; i < imageSlides.length; i++) {
		    circles[i].removeClass("dot");
		  }
		}

		// SINGLE IMAGE LOOP/CIRCLES FUNCTION
// 		function imageLoop() {
// 			var currentImage = imageSlides[counter];
// 		  	var currentDot = circles[counter];
// 		 	currentImage.addClass("visible");
// 		  	removeDots();
// 		  	currentDot.addClass("dot");
// 		  	counter++;
// 		}

		// LEFT & RIGHT ARROW FUNCTION & CLICK EVENT LISTENERS
		function arrowClick(e) {
		  var target = e.target;
		  if (target == leftArrow) {
		    clearInterval(imageSlideshowInterval);
		    hideImages();
		    removeDots();
		    if (counter == 1) {
		      counter = (imageSlides.length - 1);
		      imageLoop();
		      imageSlideshowInterval = setInterval(slideshow, 10000);
		    } else {
		      counter--;
		      counter--;
		      imageLoop();
		      imageSlideshowInterval = setInterval(slideshow, 10000);
		    }
		  } 
		  else if (target == rightArrow) {
		    clearInterval(imageSlideshowInterval);
		    hideImages();
		    removeDots();
		    if (counter == imageSlides.length) {
		      counter = 0;
		      imageLoop();
		      imageSlideshowInterval = setInterval(slideshow, 10000);
		    } else {
		      imageLoop();
		      imageSlideshowInterval = setInterval(slideshow, 10000);
		    }
		  }
		}
		// 	rightArrow.addEventListener('click', arrowClick);
		// IMAGE SLIDE FUNCTION
// 		function slideshow() {
// 		  if (counter < imageSlides.length) {
// 		    imageLoop();
// 		  } else {
// 		    counter = 0;
// 		    hideImages();
// 		    imageLoop();
// 		  }
// 		}
		// SHOW FIRST IMAGE, & THEN SET & CALL SLIDE INTERVAL
// 		setTimeout(slideshow, 1000);
// 		var imageSlideshowInterval = setInterval(slideshow, 10000);
	    
// 		document.onkeydown = function(e) {
// 			if(e.keyCode == 123) { return false; }
// 			if(e.keyCode == 44) {  return false; }
// 			if(e.ctrlKey && e.keyCode == 'E'.charCodeAt(0)){ return false; } 
// 			if(e.ctrlKey && e.shiftKey && e.keyCode == 'I'.charCodeAt(0)){ return false; }
// 			if(e.ctrlKey && e.shiftKey && e.keyCode == 'J'.charCodeAt(0)){ return false; }
// 			if(e.ctrlKey && e.keyCode == 'U'.charCodeAt(0)){ return false; }
// 			if(e.ctrlKey && e.keyCode == 'H'.charCodeAt(0)){ return false; }
// 			if(e.ctrlKey && e.keyCode == 'A'.charCodeAt(0)){ return false; }
// 			if(e.ctrlKey && e.keyCode == 'E'.charCodeAt(0)){ return false; }
// 		}  


		window.history.forward();
		function noBack() {
			window.history.forward();
		}
	
	</script>

<style type="text/css" nonce="${cspNonce}">

</style>
</head>

<body>

	<nav class="navbar navbar-expand-lg">
		<div class="container-fluid">

<div class="row">
<div class="col-lg-6">
			<div class="header_logo">
				<img src="admin/layout_file/images/logo2.png">
				<h1 class="text-hedding-top text-center">Employee Management System</h1>
			</div></div>
			<div class="col-lg-6">
			<ul class="nav">
			<li class="active"><a href="#home">Home</a></li>
			<li><a href="#about">About</a></li>
			<li><a href="#contact">contact</a></li>
			
			</ul>
			</div>
</div>
		</div>
	</nav>

	<main>

		<section class="hero" id="home">
			<div class="container-fluid">

<div class="row">
<div class="col-lg-6">
<div class="home-img">
<img src="admin/login_file/images/ems.png"></div>
</div><div class="col-lg-6">
<div class="login-box">
		
			
			<!--                             <form class="custom-form contact-form bg-white shadow-lg" action="#" method="post" role="form"> -->
			<form role="form" name='loginForm' action="#" method='POST'
				id="myFormId" class="custom-form contact-form">
				<h2>Login Here</h2>

				<div class="row">
					<div class="col-lg-12 col-md-12 col-12 p-0 mb-3">
						<div class="form-group">
						<div class="col-lg-4">
						 <label>Username</label>
						 </div>
						 <div class="col-lg-8">
							<input id="username" type='text' name='username'
								class="form-control disablecopypaste" maxlength="30" size="35"
								autocomplete="off" placeholder=" ">
						</div>
						</div>
					</div>
					<div class="col-lg-12 col-md-12 col-12 p-0 mb-3">
						<div class="form-group">
						<div class="col-lg-4"> <label>Password
							</label></div>
							<div class="col-lg-8">
							<input id="password" type='password' name='password'
								class="form-control disablecopypaste" maxlength="28" size="35"
								
								autocomplete="off" placeholder=" " />
							<!-- pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%!^\\&_.~*]).{8,28}$" -->
						</div>
						</div>
					</div>


					<div class="col-lg-12 p-0">
						<div class="enter_captcha pe-0">
							<div class="form-group">
							<div class="col-lg-4"> <label>Enter
									Captcha </label></div>
									<div class="col-lg-4">
								<input type='text' class="form-control disablecopypaste"
									size="35" id="txtInput" name="txtInput" placeholder=" "
									maxlength="5" autocomplete="off" />
									</div>
							</div>
						</div>
						<div class="col-md-4 col-sm-6 col-6 p-0">
							<div class="form-group captcha">
								<div class="input-group">
									<img id="capcha" src="genCapchaCode" class="p-0 imgcaptcha"
										class="form-control disablecopypaste" /> <span
										class="input-group-btn">
										<button class="btn btn-primary btn-sm" id="btnrefresh"
											tabindex="-1" type="button">
											<img src="admin/login_file/images/referesh.ico">
										</button>
									</span>
								</div>
							</div>
						</div>
					</div>
					<div class="form-group login">
						<button type="submit" class="form-control" id="login_id">LOGIN</button><!--onclick="return validation();"  -->
					    <div class="tooltip">
							<a href="/@{/RegistrationUrl}">Create an account</a>
						</div> 
					</div>
					<input type="hidden" id="csrfIdSet" name="" value="" />
				</div>
			</form>
			<div class="circle1 circle-aqua"></div>
		</div>


</div></div></div>
		</section>

		<section id="about">
			<div class="container-fluid">

<div class="row">
<div class="col-lg-6">
<div class="about-text">
<h2>About</h2>
<p>Bhaskaracharya National Institute for Space Applications and Geo-informatics
Bhaskaracharya National Institute for Space Applications and Geo-informatics{BISAG-N} is an Autonomous Scientific Society registered under the Societies Registration Act, 1860 under the MeitY, Government of India to undertake technology development & management, research & development, facilitate National & International cooperation, capacity building and support technology transfer & entrepreneurship development in area of geo-spatial technology.</p>
</div></div>
<div class="col-lg-6">
<div class="home-img">
<img src="admin/login_file/images/ems.png"></div>
</div>
</div></div>
</section>
	
	<section id="contact">
			<div class="container">

<div class="row">
<div class="col-lg-12">

<div class="contact-item">
<h2>Contact</h2>
										<h4>Gandhinagar Address</h4>
										
										<p>
											Near CH '0' Circle, Indulal Yagnik Marg,
												<br> Gandhinagar-Ahmedabad Highway, Gandhinagar-382 007
												Gujarat, India.
											
										</p>
									</div>
									</div>
</div></div>
</section>	
	</main>

		
<!-- 	<footer class="site-footer"> -->
<!-- 		<div class="container"> -->
<!-- 			<div class="row align-items-center"> -->

<!-- 				<div class="col-lg-12 col-12     display-contents"> -->
<!-- 					<div class="col-md-2"> -->
<!-- 						<img class="navy_logo" alt="" -->
<!-- 							src="admin/layout_file/images/navy_logo.png"> -->
<!-- 					</div> -->
<!-- 					<div class="col-md-8 p-0"> -->
<!-- 						<p class="p3" align="center"> -->
<!-- 							<b class="center b2"> All Rights Reserved with Bisag-N. This site -->
<!-- 								is developed and maintained by <a href="https://bisag-n.in/" -->
<!-- 								target="_new" title="Go to https://bisag-n.in/" class="bisag-n">BISAG-N</a>. -->
<!-- 							</b> <b><span>Last Updated On 19-01-2023</span></b> -->
<!-- 						</p> -->
<!-- 					</div> -->
<!-- 					<div class="col-md-2 bisag-logo-footer"> -->
<!-- 						<a href="https://bisag-n.in/" target="_new"> <img class="b_logo" -->
<!-- 							alt="" src="admin/layout_file/images/logo2.png"> -->

<!-- 						</a> -->
<!-- 					</div> -->
<!-- 				</div> -->




<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</footer> -->
<script type="text/javascript"  nonce="${cspNonce}">

$(document).ready(function () {
    $('.nav li').click(function(e) {

        $('.nav li').removeClass('active');

        var $this = $(this);
        if (!$this.hasClass('active')) {
            $this.addClass('active');
        }
        //e.preventDefault();
    });
});
//Function to mark an item from a section width a css class.
function menuOnScroll(mySection, myMenu, myClass) {
  $(window).scroll(function(){
    var elScroll = $(window).scrollTop();
    $(mySection).each(function(i){
      if ($(this).offset().top <= elScroll) {
        $(myMenu).removeClass(myClass);
        $(myMenu).eq(i).addClass(myClass);
      }
    });
  });
}

//Call function.
//First parameter will be the section that we want to go.
//Second will be the item that will change his css.
//Third will be the class css to add to the item( Our second parameter) Is IMPORTAT to note that we must to skip the dot of our class.
menuOnScroll('section','nav ul li a', 'inSection');



//Function to animate the scroll when click on a menu item.
//IMPORTANT. This function is only use for animate the scroll, you could skip it if you want.
function scrollToAnyPoint (navItem) {
  var getAttr;
  $(navItem).click(function(e){
    e.preventDefault();
    getAttr = $(this).attr('href');
    var toSection = $(getAttr).offset().top;
    $("html, body").animate({scrollTop:toSection}, 1000)
  });
}
//Call Function
scrollToAnyPoint('nav ul li a');
</script>
</body>
</html>

