<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script type="text/javascript" src="layout_file/js/jquery-3.6.0.min.js"></script>

<style>
.multiselect {
	width: 200px;
}

.selectBox {
	position: relative;
}

.selectBox select {
	width: 100%;
}

.overSelect {
	position: absolute;
	left: 0;
	right: 0;
	top: 0;
	bottom: 0;
}

#checkboxes {
	display: none;
	border: 1px #dadada solid;
}

#checkboxes label {
	display: block;
	text-align: left;
	padding-left: 30px;
}

#checkboxes label:hover {
	background-color: #1e90ff;
}

#checkboxes label input[type="checkbox"] {
	margin-right: 10px;
}

#checkboxes label, #submodulecheckboxes label, #screencheckboxes label {
	margin-bottom: 0;
}

#submodulecheckboxes {
	display: none;
	border: 1px #dadada solid;
}

#submodulecheckboxes label {
	display: block;
	text-align: left;
	padding-left: 30px;
}

#submodulecheckboxes label:hover {
	background-color: #1e90ff;
}

#submodulecheckboxes label input[type="checkbox"] {
	margin-right: 10px;
}

#screencheckboxes {
	display: none;
	border: 1px #dadada solid;
}

#screencheckboxes label {
	display: block;
	text-align: left;
	padding-left: 30px;
}

#screencheckboxes label:hover {
	background-color: #1e90ff;
}

#screencheckboxes label input[type="checkbox"] {
	margin-right: 10px;
}
</style>


<script type="text/javascript">
	window.history.forward();
	function noBack() {
		window.history.forward();
	}
	$(document).ready(function () {
		$('#role').keyup(function() {
	    	this.value = this.value.toUpperCase();
	    }); 
	});
</script>
<form:form name="roleMst" id="roleMst" action="roleAction" method='POST'
	commandName="roleCMD">
	<div class="container">
		<div class="card">
			<div class="card-header">
				<h4 align="center">LINK ROLE MASTER</h4>
			</div>
			<!-- end of card-header -->
			<div class="card-body card-block">
				<div class="row mb-3">
					<div class="col-md-2">
						<label for="text-input">Role Name <strong
							class="txt-clr">*</strong></label>
					</div>
					<div class="col-md-4">
						<select name="roleid" class="form-control" id="roleid">
							<option value="0">--Select--</option>
							<c:forEach var="item" items="${getRoleNameList}" varStatus="num">
								<option value="${item.roleId}">${item.role}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="row mb-3">
					<div class="col-md-2">
						<label class=" form-control-label">Module <strong
							class="txt-clr">*</strong></label>
					</div>
					<div class="col-md-4 selectBox" onclick="showCheckboxes()">
						<select id="moduleaction" name="" class="form-control">
							<option>--Select--</option>
						</select>
						<div class="overSelect"></div>
					</div>
					<div class="col-md-2">
						<label class=" form-control-label">Sub-Module <strong
							class="txt-clr">*</strong></label>
					</div>
					<div class="col-md-4 selectBox" onclick="showModuleCheckboxes()">
						<select id="sub_moduleaction" name="" class="form-control">
							<option>--Select--</option>
						</select>
						<div class="overSelect"></div>
					</div>
				</div>
				<div class="row mb-3">
					<div class="col-md-2"></div>
					<div class="col-md-4">
						<div id="checkboxes"></div>
					</div>
					<div class="col-md-2"></div>
					<div class="col-md-4">
						<div id="submodulecheckboxes"></div>
					</div>
				</div>
				<div class="row mb-3">
					<div class="col-md-2">
						<label class=" form-control-label">Screen Name.<strong
							class="txt-clr">*</strong></label>
					</div>
					<div class="col-md-10 selectBox" onclick="screenCheckboxes()">
						<select id="screenaction" name="" class="form-control">
							<option>--Select--</option>
						</select>
						<div class="overSelect"></div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-2"></div>
					<div class="col-md-10">
						<div id="screencheckboxes"></div>
					</div>
				</div>
			</div>
			<!-- end of card-body -->

			<input type="hidden" name="rolehid" id="rolehid" /> <input
				type="hidden" name="modulehid" id="modulehid" /> <input
				type="hidden" name="submodulehid" id="submodulehid" /> <input
				type="hidden" name="screenhid" id="screenhid" /> <input
				type="hidden" name="screenhid_old" id="screenhid_old" /> <input
				type="hidden" name="screen_listhid" id="screen_listhid" /> <input
				type="hidden" name="screen_listhid_old" id="screen_listhid_old" />

			<div class="card-footer" align="center">
				<a href="rolemstnewUrl"><button type="button"
						class="btn btn-dark">
						<i class="fa fa-refresh"></i>&nbsp;Clear
					</button></a>
				<button type="submit" class="btn btn-primary" id="save_btn"
					value='Save' onclick="return isValid();">
					<i class="fa fa-save"></i>&nbsp;Save
				</button>
			</div>
			<!-- end of card-footer -->

		</div>
		<!-- end of card -->
	</div>
	<!-- end of container -->
</form:form>

<script>
	var Screen_id =new Array();	
	var Screen_list =new Array();	
	var Screen_list_old =new Array();	
	var modulehid_val =new Array();
	var submodulehid_val =new Array();
	var options_sce = "";
	var m_join="";
	var old_len = 0;
	var old_smlen = 0;
	var old_m;
	var old_sm;
	
	$('select#roleid').change(function() {
		modulehid_val= [];
		Screen_list=[];
		Screen_list_old=[];
		submodulehid_val=[];
		Screen_id=[];
		var selected = $("#roleid option:selected");	
		
		var module="0";
		var submodule="0";
		var val;
		var com = "";
	   	selected.each(function () {
	  		val =  $(this).val();
	  	}); 
	   
	   	document.getElementById('rolehid').value=val;
	   	document.getElementById("submodulehid").value = "";
	   	document.getElementById("modulehid").value = "";
	 
	  	options_sce = "";
	  	m_join="";
	  	document.getElementById('screenhid').value="";
	  	document.getElementById('screenhid_old').value="";
	  	document.getElementById('screen_listhid').value="";
	  	document.getElementById('screen_listhid_old').value="";
	  	old_len = 0;
	  	old_smlen = 0;
	 
	  	$('#checkboxes').find('input:checkbox[name=chk]').remove();
	  	$('#checkboxes').find('label[id=module_chk_lable]').remove(); 	
	  	$('#submodulecheckboxes').find('input:checkbox[name=subchk]').remove();
	  	$('#submodulecheckboxes').find('label[id=submodule_chk_lbl]').remove(); 
	  	$('#screencheckboxes').find('input:checkbox[name=screenchk]').remove();
	  	$('#screencheckboxes').find('label[id=screen_chk_lable]').remove(); 	
		
		modulesel(val);
	});  


	function modulesel(val){
		var options = "";
		var k1=0;	
		$.post("getModuleNameListlink?"+key+"="+value, function(j) {
			$.post("getrolebymodule?"+key+"="+value,{roleid : val}, function(k) {
				var kk = new Array();
				for ( var f = 0; f < k.length; f++){
					kk.push("'"+k[f]+"'");
				}
				for ( var i = 0; i < j.length; i++){
					if(kk.includes("'"+j[i].id+"'")){
						$("div#checkboxes").append('<label for="'+j[i].id+'" id="module_chk_lable"> <input type="checkbox"    onclick="chkString();"  name ="chk" id="'+j[i].id+'" value ='+j[i].id+' checked />'+j[i].module_name+'</label>');
						modulehid_val.push(j[i].id)
						$("input#modulehid").val(modulehid_val);
						modulepass(j[i].id,"1");
					}
					else{
						$("div#checkboxes").append('<label for="'+j[i].id+'"  id="module_chk_lable"> <input type="checkbox"    onclick="chkString();"  name ="chk" id="'+j[i].id+'" value ='+j[i].id+' />'+j[i].module_name+'</label>');       						
					}
				} 
			});			
		});  
	}

	var expanded = false;
	function showCheckboxes() {
    	var checkboxes = document.getElementById("checkboxes");
     	if (!expanded) {
       		checkboxes.style.display = "block";
       		checkboxes.style.overflow= "auto";
       		checkboxes.style.height= "150px";
       		expanded = true;
		} else {
       		checkboxes.style.display = "none";
       		expanded = false;
     	}
   	}
	
	var old_m_sc ="";
   	function chkString()
   	{
		old_m =document.getElementById('modulehid').value;
		if(old_m != ""){
			old_m_sc = old_m.split(",");
			old_len = old_m_sc.length;
		}
		var ruleName =new Array();	
		$("input:checkbox[name=chk]:checked").each(function(){  		
			ruleName.push($(this).val());	     			  
	 	});		 	
		document.getElementById('modulehid').value=ruleName;
		
		var new_len = $('input:checkbox[name=chk]:checked').length;
	  	var m = "";
	  	var flag;
	  	if(new_len > old_len ){
			m = differenceOf2Arrays_add(old_m,ruleName);
	  		flag = 1;
	  	}
	  	else if(new_len < old_len ){
	  		m = differenceOf2Arrays_del(old_m,ruleName);
	  		flag = 0;
		}
	  	old_len = $('input:checkbox[name=chk]:checked').length; 
	  	modulepass(parseInt(m),flag);
	}  	

   	/* sub_module */
	function modulepass(valu1,flag){
		var sub_m = "";
		var role_id = $("input#rolehid").val();
		$.post("getSubModuleNameListlink?"+key+"="+value,{valu1:valu1},function(j) {
			var i1=0;
			$.post("getrolemodulebysubmod?"+key+"="+value,{roleid:role_id,moduleid:valu1}, function(j1) {
				for ( var i = 0; i < j.length; i++){	
					if(flag == 1){					
						if(j[i].id == j1[i1]){								
							$("div#submodulecheckboxes").append('<label for="'+j[i].id+'" id="submodule_chk_lbl"> <input type="checkbox"  onclick="subchkString();"  name ="subchk" id="'+j[i].module_id+'_'+j[i].id+'" value ="'+j[i].module_id+'_'+j[i].id+'" checked />'+j[i].submodule_name+'</label>');
							submodulehid_val.push(j[i].module_id+'_'+j[i].id);
							screenlist_final(j[i].module_id+'_'+j[i].id,flag);
							old_smlen = $('input:checkbox[name=subchk]:checked').length;	
							i1=i1+1;
						}
						else{							
							$("div#submodulecheckboxes").append('<label for="'+j[i].id+'" id="submodule_chk_lbl"> <input type="checkbox"  onclick="subchkString();"  name ="subchk" id="'+j[i].module_id+'_'+j[i].id+'" value ="'+j[i].module_id+'_'+j[i].id+'"  />'+j[i].submodule_name+'</label>');								
						}
					}
					else{
						$('#submodulecheckboxes').find('input:checkbox[id='+j[i].module_id+'_'+j[i].id+']').remove();
						$('#submodulecheckboxes').find('label[for='+j[i].id+']').remove();								
						var remove_val = j[i].module_id+'_'+j[i].id;
						dynamic_removevalue(remove_val,submodulehid_val)
						screenlist_final(remove_val,flag)
					}
				}
				$("input#submodulehid").val(submodulehid_val);
				//ScreenSel();
			});			
		});	
	}
	function subchkString(){
		old_sm =document.getElementById('submodulehid').value;	
		if(document.getElementById('submodulehid').value == "" ){
			old_smlen = 0;
		}
		else{			
			var old_smlen1 = old_sm.split(",");
			old_smlen = old_smlen1.length;
		}  
		$("input:checkbox[name=subchk]:checked").each(function(){
			if(submodulehid_val.indexOf($(this).val()) == -1 && submodulehid_val.includes($(this).val()) === false){	
				submodulehid_val.push($(this).val());
			}
			$("input#submodulehid").val(submodulehid_val);
		});	
	 	
		$("input:checkbox[name=subchk]:not(:checked)").each(function(){
			if(submodulehid_val.includes($(this).val()) === true){	
				$.ajaxSetup({
				    async: false
				});
				dynamic_removevalue($(this).val(),submodulehid_val)
			}
			$("input#submodulehid").val(submodulehid_val);
		});
		var new_smlen = $('input:checkbox[name=subchk]:checked').length;
		var sm = "";
	  	var sm_flag="";
	  	if(new_smlen > old_smlen ){
	  		sm = differenceOf2Arrays_add(old_sm,submodulehid_val);	
	  		sm_flag = 1;
	  	}
	  	else if(new_smlen < old_smlen ){	
	  		 sm = differenceOf2Arrays_del(old_sm,submodulehid_val);		  		
	  		 sm_flag = 0;
	  	}
	  	screenlist_final(sm,sm_flag);
	}
	
	var expanded = false;
   	function showModuleCheckboxes() {
		var checkboxes = document.getElementById("submodulecheckboxes");
     	if (!expanded) {
       		checkboxes.style.display = "block";
       		checkboxes.style.overflow= "auto";
       		checkboxes.style.height= "150px";
       		expanded = true;
		} else {
       		checkboxes.style.display = "none";
       		expanded = false;
     	}
   	}
   
   	/* Screen Function call */
  	var module_id,submodule_id;
  	var old_sm_len1 = 0;
  	var sm_len1 = 0;
   	var old_submodule_id = "";
   	var  o_sm_len =0;
	var temp = 0;

    function screenlist_final(sc,flag){
		var role_id = $("input#rolehid").val();
		var module_id,submodule_id;
		var val2 = sc.toString().split("_");
			 
		for(var j=0;j<val2.length;j++){
			if( j == 0){
				module_id = val2[j];				   
			}
			j=j+1;
			submodule_id = val2[j];
		} 
		
		if(submodule_id == undefined || submodule_id == 'undefined'){
			submodule_id=0;
		}
			  
		if(module_id == "" || module_id == 'undefined' ){
			module_id= 0;
		}
		
		$.ajaxSetup({
			async: false
		});    
		
		$.post("getScreenName_mod_submodList?"+key+"="+value,{moduleid:module_id,submoduleid:submodule_id},function(j) {
			$.ajaxSetup({
				async: false
			});
		    $.post("getrolemodulesubmodbyscreen?"+key+"="+value,{moduleid:module_id,submoduleid:submodule_id,role_id:role_id}, function(j1) {
		    	var i1=0;
			    for ( var i = 0; i < j.length; i++){
			    	if(flag == 1){			    		
			    		if(j[i].id == j1[i1]){
			    			$("div#screencheckboxes").append('<label for="'+j[i].id+'" id="screen_chk_lable"> <input type="checkbox"  onclick="screenchkString();"  name ="screenchk" id="'+j[i].screen_module_id+'_'+j[i].screen_submodule_id+'_'+j[i].id+'" value ='+j[i].screen_module_id+'_'+j[i].screen_submodule_id+'_'+j[i].id+' checked />'+j[i].screen_name+'</label>');
			    			Screen_id.push(j[i].screen_module_id+'_'+j[i].screen_submodule_id+'_'+j[i].id);
			    			$("input#screenhid").val(Screen_id);
			    			$("input#screenhid_old").val(Screen_id);
			    			i1=i1+1;
			    		}
			    		else{
			    			$("div#screencheckboxes").append('<label for="'+j[i].id+'" id="screen_chk_lable"> <input type="checkbox"  onclick="screenchkString();"  name ="screenchk" id="'+j[i].screen_module_id+'_'+j[i].screen_submodule_id+'_'+j[i].id+'" value ='+j[i].screen_module_id+'_'+j[i].screen_submodule_id+'_'+j[i].id+'  />'+j[i].screen_name+'</label>');
			    		}  			
			    	}
			    	else{
			    		$('#screencheckboxes').find('input:checkbox[id='+j[i].screen_module_id+'_'+j[i].screen_submodule_id+'_'+j[i].id+']').remove();
						$('#screencheckboxes').find('label[for='+j[i].id+']').remove();		
						var remove_val = j[i].screen_module_id+'_'+j[i].screen_submodule_id+'_'+j[i].id;
						removevalue_screen(remove_val);
			    	}
			    }	
		    });	
		});
	}  

    var expanded = false;
	function screenCheckboxes() {	
    	var checkboxes = document.getElementById("screencheckboxes");
    	if (!expanded) {
       		checkboxes.style.display = "block";
       		checkboxes.style.overflow= "auto";
       		checkboxes.style.height= "200px";
       		expanded = true;
	    } else {
    		checkboxes.style.display = "none";
       		expanded = false;
     	}
   	}
   
	function screenchkString()
   	{
		var ele = document.getElementsByName("screenchk");	 
	   	var ruleName =new Array();
	   	var st="";
	 	for(var i=0;i<ele.length;i++){
	    	if( ele[i].checked)
	     	{
	    		st =st + "," + ele[i].value;
	    	 	ruleName.push(ele[i].value);	    
	     	}
	   	}
		document.getElementById('screenhid').value=ruleName;
  	} 
   
	function isValid(){
		if($("select#roleid").val()== "0"){
 			alert("Please Select Role");
 			$("select#roleid").focus();
 			return false;
 		}
	  	if($("input#modulehid").val()==""){
			alert("Please Select Module");
			return false;
		}
	  	if($("input#submodulehid").val()==""){
			alert("Please Select Sub Module");
			return false;
		}
	  	if($("input#screenhid").val()=="" ){
			alert("Please Select Screen");
			return false;
		}
		return true;
  	}
   	
	function differenceOf2Arrays_add(array1, array2) {
  		var add_temp = [];	  
	 	array1 = array1.toString().split(',');
	   	array2 = array2.toString().split(',');	
		for(var i in array2) {	    	
			if(array1.indexOf(array2[i]) === -1 ) {			
				add_temp.push(array2[i]);			   
		   	}
	   	}    
		return add_temp;
	}
 
  	function differenceOf2Arrays_del(array1, array2) {	  
		var del_temp = [];	
	   	array1 = array1.toString().split(',');
	   	array2 = array2.toString().split(',');
		for (var i in array1) {
	    	if(array2.indexOf(array1[i]) === -1){
	   			del_temp.push(array1[i]);
	   		}
	   	} 
		return del_temp;
	} 
  
  	// removevalue 
  	function removevalue(remove_val) {	
		var number = remove_val;
    	var str =document.getElementById("submodulehid").value;
      	var strArray = str.split(',');
      	for (var i = 0; i < strArray.length; i++) {    	
        	if (strArray[i] === number) {
            	strArray.splice(i, 1);              
          	}
      	}
     	document.getElementById("submodulehid").value = strArray;
   }
  
  	function removevalue_screen(remove_val) {	
    	var number = remove_val;
    	var str =document.getElementById("screenhid").value;
      	var strArray = str.split(',');
      	for (var i = 0; i < strArray.length; i++) {    	
        	if (strArray[i] === number) {
            	strArray.splice(i, 1);              
          	}
      	}
     	document.getElementById("screenhid").value = strArray;
  	}
  
  	function dynamic_removevalue(value,list) {
		for(var i=0;i<=list.length;i++){
			if(list[i] == value){
	      		list.splice(i--,1);
	       		return list;
	    		break;
			}
		}
	}
	function dynamic_sub_removevalue(value,arr) {
		for(var i=0;i<=arr.length;i++){
	    	if(arr[i].toString() == value.toString()){
	        	arr.splice(i--,1);
	        	return arr;
	        	break;
	        }
	    }
  	}
</script>