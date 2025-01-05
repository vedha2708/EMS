<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script type="text/javascript" src="layout_file/js/jquery-3.6.0.min.js"></script>

<style>
.text-red{
	color: red !important;
}

</style>
<form:form name="ViewLeaveFormid" id="ViewLeaveFormid" action="ViewLeaveAction"
	method='POST' commandName="ViewLeaveFormidCMD">
	<div class="container">
		<div class="card">
			<div class="card-header">
				<h4 align="center">View Leave Application</h4>
			</div>
			<!-- end of card-header -->
			<div class="card-body card-block">
			
				<div class="row mb-3">
					<div class="col-md-2">
						<label for="text-input">Name</label>
					</div>
					<div class="col-md-4">
					<input type="hidden" id="vid" name="vid" value="${list[0][0]}">
					${list[0][1]} 
					</div>
				</div>
				
				<div class="row mb-3">
					<div class="col-md-2">
						<label for="text-input">Leave Type</label>
					</div>
					<div class="col-md-4">
					${list[0][2]} 
					</div>
				</div>
				
				<div class="row mb-3">
					<div class="col-md-2">
						<label for="text-input">From Date </label>
					</div>
					<div class="col-md-4">${list[0][3]} 
					</div>
				</div>
				
				<div class="row mb-3">
					<div class="col-md-2">
						<label for="text-input">To Date </label>
					</div>
					<div class="col-md-4">${list[0][4]} 
					</div>
				</div>
				
				<div class="row mb-3">
					<div class="col-md-2">
						<label for="text-input">Reason</label>
					</div>
					<div class="col-md-4">
					${list[0][5]} 
					</div>
				</div>
				
				<div class="row mb-3">
					<div class="col-md-2">
						<label for="text-input">Description </label>
					</div>
					<div class="col-md-4">
					${list[0][6]} 
					</div>
				</div>
				
			</div>
			<!-- end of card-body -->
			<div class="card-footer" align="center">
<input type="hidden" id="h_id" name="h_id" value=""/>
<input type="" id="h_status" name="h_status" value=""/>
				<a href="Approve_LeaveUrl"><button type="button"
						class="btn btn-dark">
						<i class="fa fa-refresh"></i>&nbsp;Back
					</button></a>
				 <button type="submit" class="btn btn-primary" id="approve_btn"
					value='Approve' onclick="change_status(1);">Approve
				</button> 
				 <button type="submit" class="btn btn-primary" id="reject_btn"
					value='Reject' onclick="change_status(2);" >&nbsp;Reject
				</button> 
				<!-- <button type="button" class="btn btn-info" id="btn-search"
					value='Search' onclick="return reportScreen();">
					<i class="fa fa-save"></i>&nbsp;Search
				</button> -->
			</div>
			<!-- end of card-footer -->
		</div>
		<!-- end of card -->
	</div>
	<!-- end of container -->

</form:form>

<script>
 	$(document).ready(function () {
 		if("${vid}" != ""){
 			$("#h_id").val("${vid}");
 		}
 	});
 	function change_status(vv){
 		$("#h_status").val(vv);
 	}
</script>

