<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script type="text/javascript" src="layout_file/js/jquery-3.6.0.min.js"></script>

<script type="text/javascript">
	window.history.forward();
	function noBack() {
		window.history.forward();
	}
	$(document).ready(function () {
		watermarkreport();
	});
</script>
<form:form name="role_mst" id="role_mst" action="role_mstAction"
	method='POST' commandName="role_mstCMD">
	<div class="container">
		<div class="card">
			<div class="card-header">
				<h5>Role Master</h5>
			</div>
			<!-- end of card-header -->
			<div class="card-body card-block">
				<div class="row mb-3">
					<div class="col-md-2">
						<label for="text-input">Role Name <strong
							class="txt-clr">*</strong></label>
					</div>
					<div class="col-md-3">
						<input id="role" name="role"
							style="font-family: 'FontAwesome', Arial;" maxlength="45"
							class="form-control" autocomplete="off">
					</div>
				</div>
				<div class="row mb-3">
					<div class="col-md-2">
						<label for="text-input">Role Type <strong
							class="txt-clr">*</strong></label>
					</div>
					<div class="col-md-4">
						<select name="role_type" id="role_type" style="width: 100%;"
							class="select2 narrow wrap">
							<option value="0">--Select--</option>
							<c:forEach var="item" items="${getRoleType}" varStatus="num">
								<option value="${item.role_type}">${item.role_type}</option>
							</c:forEach>
						</select>
					</div>
				</div>

				<div class="row mb-3">
					<div class="col-md-2">
						<label for="text-input">Access Level <strong
							class="txt-clr">*</strong></label>
					</div>
					<div class="col-md-4">
						<select name="access_lvl" id="access_lvl" style="width: 100%;"
							onchange="access_lev(this.value)" class="select2 narrow wrap">
							<option value="">--Select--</option>
							<option value="Unit">Unit</option>
							<option value="Formation">Formation</option>
							<option value="Line_dte">Line Dte</option>
							<option value="Depot">DEPOT</option>
						</select>
					</div>
				</div>
				<div class="row mb-3">
					<div class="col-md-2" id="sub_lev" style="display: none">
						<label for="text-input">Sub Access Level <strong class="txt-clr">*</strong></label>
					</div>
					<div class="col-md-4">
						<select name="formation_se" class="form-control" id="formation_se" style="display: none" onchange="value_pass(this.value)">
							<option value="">--Select--</option>
							<option value="Command">Command</option>
							<option value="Corps">Corps</option>
							<option value="Division">Division</option>
							<option value="Brigade">Brigade</option>
						</select>
						
						<select name="line_dte_se" class="form-control" id="line_dte_se" style="display: none" onchange="value_pass(this.value)">
							<option value="">--Select--</option>
							<option value="Arm">Arm</option>
							<option value="Staff">Staff</option>
						</select>
					</div>
				</div>
				<div class="row mb-3">
					<div class="col-md-2" id="sub_lev1" style="display: none">
						<label for="text-input">Staff Level <strong class="txt-clr">*</strong></label>
					</div>
					<div class="col-md-4">
						<select name="staff_se" class="form-control" id="staff_se" style="display: none" onchange="value_pass1(this.value)">
							<option value="">--Select--</option>
							<option value="MGO">MGO</option>
							<option value="SD">SD</option>
							<option value="WE">WE</option>
						</select>
					</div>
				</div>
				<input id="sub_lvl_text" name="sub_access_lvl" type="hidden">
				<input id="staff_text" name="staff_lvl" type="hidden">
			</div>
			<!-- end of card-body -->

			<div class="card-footer">
				<input type="reset" class="btn btn-success btn-sm" value="Clear">
				<input type="submit" class="btn btn-primary btn-sm" value="Save" onclick="return isValid();">
			</div>
			<!-- end of card-footer -->

		</div>
		<!-- end of card -->
	</div>
	<!-- end of container -->
</form:form>

<div class="container">
	<div id="divSerachInput">
		<input id="searchInput" type="text"
			style="font-family: 'FontAwesome', Arial; margin-bottom: 5px; width: 50%;"
			placeholder="&#xF002; Search Word" size="35" class="form-control">
	</div>
	<div id="divPrint">
		<div id="divShow"></div>
		<div class="watermarked" data-watermark="" id="divwatermark"
			style="display: block;">
			<span id="ip"></span>
			<table id="RoleReport"
				class="table no-margin table-striped  table-hover  table-bordered report_print">
				<thead>
					<tr style="font-size: 15px;">
						<th width="10" style="text-align: center;">Sr No</th>
						<th width="20">Role Name</th>
						<th width="20">Role Type</th>
						<th width="20">Access Level</th>
						<th width="20">Sub Access Level</th>
						<th width="20">Staff Level</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="item" items="${list}" varStatus="num">
						<tr style="font-size: 15px;">
							<td width="10" align="center">${num.index+1}</td>
							<td width="20">${item.role}</td>
							<td width="20">${item.role_type}</td>
							<td width="20">${item.access_lvl}</td>
							<td width="20">${item.sub_access_lvl}</td>
							<td width="20">${item.staff_lvl}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>


<script>
   $(document).ready(function () {
	$("#searchInput").on("keyup", function() {
			var value = $(this).val().toLowerCase();
			$("#RoleReport tbody tr").filter(function() { 
			$(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
			});
		});
   });

</script>

<script>
    function access_lev(v)
	{
    	document.getElementById('sub_lvl_text').value="";
		if(v == "Formation"){
			document.getElementById('sub_lev').style.display='block';
			document.getElementById('formation_se').style.display='block';
			document.getElementById('line_dte_se').style.display='none'; 
			document.getElementById('sub_lev1').style.display='none';
    		document.getElementById('staff_se').style.display='none';
    		document.getElementById('staff_text').value="";
			
		}
		else if(v == "Line_dte" ){
			document.getElementById('sub_lev').style.display='block';
			document.getElementById('line_dte_se').style.display='block';
			document.getElementById('formation_se').style.display='none';
		}
		else if(v == "Depot" ){
			document.getElementById('sub_lev').style.display='block';
			document.getElementById('line_dte_se').style.display='none';
			document.getElementById('formation_se').style.display='none';
			document.getElementById('sub_lev1').style.display='none';
    		document.getElementById('staff_se').style.display='none';
    		document.getElementById('staff_text').value="";
		}
		else if(v == "Unit" ){
			document.getElementById('sub_lev').style.display='none';
			document.getElementById('line_dte_se').style.display='none';
			document.getElementById('formation_se').style.display='none';			
			document.getElementById('sub_lev1').style.display='none';
    		document.getElementById('staff_se').style.display='none';
    		document.getElementById('staff_text').value="";
		}
	} 
    
    function value_pass(v){
    	document.getElementById('sub_lvl_text').value =v;
    	if(v == "Staff"){
    		document.getElementById('sub_lev1').style.display='block';
    		document.getElementById('staff_se').style.display='block';
    	}
    	else{
    		document.getElementById('sub_lev1').style.display='none';
    		document.getElementById('staff_se').style.display='none';
    		document.getElementById('staff_text').value="";
    	}
    }
    
    function value_pass1(v){
    	document.getElementById('staff_text').value =v;
    }

   function isValid()
   {	
	   if($("input#role").val()==""){
  			alert("Please Enter Role");
  			$("input#role").focus();
  			return false;
  		}
	   
    	if($("select#role_type").val()==" "){
   			alert("Please Select Role Type");
   			$("select#role_type").focus();
   			return false;
   		}  
    	
    	if($("select#role_type").val()=="Other" && $("#role_text").val() == ""){
    		alert("please Enter Role type");
    		return false;
    	}
    	
    	if($("select#access_lvl").val()==""){
   			alert("Please Select Access Level");
   			$("select#role_type").focus();
   			return false;
   		}  
   
    	 if($("select#access_lvl").val() != "Unit" && $("select#access_lvl").val() != "Depot"){
	    	 if($("input#sub_lvl_text").val() == ""){
	    		 alert("Please Select Sub Access Level");   			
	   			return false;
	    	 }
    	}
    	return true;
   	}
</script>