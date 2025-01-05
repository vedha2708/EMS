<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script type="text/javascript" src="layout_file/js/jquery-3.6.0.min.js"></script>

<style>
.text-red{
	color: red !important;
}

</style>
<form:form name="LeaveType_mst" id="LeaveType_mst" action="LeaveType_mstAction"
	method='POST' commandName="LeaveType_mstCMD">
	<div class="container">
		<div class="card">
			<div class="card-header">
				<h4 align="center">Salary Structure</h4>
			</div>
			<!-- end of card-header -->
			<div class="card-body card-block">
				<div class="row mb-3">
					<div class="col-md-2">
						<label for="text-input">Salary Month <strong 
							class="txt-clr text-red">*</strong></label>
					</div>
					
					<div class="col-md-4">
						<input type="date" id="from_date" name="from_date" class="form-control">
					</div>
					
				<div class="card">
			
			<!-- end of card-header -->
			<div class="card-body card-block">
			
			
			<div class="table-responsive">
				<table border="1" class="table">
					<tr>
					<th>Salary Month</th>
					<th>Earnings</th>
					<th>Deduction</th>
					<th>Net Salary</th>
					<th>Action</th>
					</tr>
				
				<tbody>
				
				<c:forEach var="item" items="${list}" varStatus="num"> 
		<tr>
							<td>${num.index+1}</td>
							<td>${item[0]}</td>
							<td>${item[1]}</td> 
							<td>${item[2]}</td>
						</tr>
			
 					</c:forEach> 
 									</tbody>
				
				</table>
				
				
	
				</div>
				
			</div>
			
		</div>					
					
			<!--		<select id="leave_type" name="leave_type" class="form-control">
					<option value="0">--Select--</option>
						<option value="CL">CL</option>
						<option value="EL">EL</option>
						<option value="EL">PL</option>
						<option value="EL">SL</option> 
					</select>
					</div>
				</div>
				
				<div class="row mb-3">
					<div class="col-md-2">
						<label for="text-input">From Date <strong
							class="txt-clr text-red">*</strong></label>
					</div>
					<div class="col-md-4">
						<input type="date" id="from_date" name="from_date" class="form-control">
					</div>
				</div>
				
				<div class="row mb-3">
					<div class="col-md-2">
						<label for="text-input">To Date <strong
							class="txt-clr text-red">*</strong></label>
					</div>
					<div class="col-md-4">
						<input type="date" id="to_date" name="to_date" class="form-control">
					</div>
				</div>
				
				<div class="row mb-3">
					<div class="col-md-2">
						<label for="text-input">Reason <strong
							class="txt-clr text-red">*</strong></label>
					</div>
					<div class="col-md-4">
						<input type="text" id="reason" name="reason" class="form-control">
					</div>
				</div>
				
				<div class="row mb-3">
					<div class="col-md-2">
						<label for="text-input">Description <strong
							class="txt-clr text-red">*</strong></label>
					</div>
					<div class="col-md-4">
						<textarea rows="5" cols="5" id="description"  name="description" class="form-control"></textarea>
					</div>
				</div>
				
			</div> -->
			<!-- end of card-body -->
			<div class="card-footer" align="center">

				<a href="LeaveTypeUrl"><button type="button"
						class="btn btn-dark">
						<i class="fa fa-refresh"></i>&nbsp;Clear
					</button></a>
				<button type="submit" class="btn btn-primary" id="save_btn"
					value='Save' onclick="return isValid();">
					<i class="fa fa-save"></i>&nbsp;Save
				</button>
				<!-- <button type="submit" class="btn btn-primary" id="update_btn"
					value='Update' onclick="return isValid();" style="display: none">
					<i class="fa fa-save"></i>&nbsp;Update
				</button> -->
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
    
 	});
</script>