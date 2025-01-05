document.addEventListener('DOMContentLoaded', function () {
	document.body.addEventListener("click", resetTimer);
	document.body.addEventListener("focus", parent_disable);
	//document.getElementById('lgoutbtn').addEventListener('click', formSubmit);
	
	$("body").on("contextmenu",function(){
	       return false;
	    }); 

});