<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script type="text/javascript" src="layout_file/js/jquery-3.6.0.min.js"></script>
<form:form name="LeaveType_mst" id="LeaveType_mst" action="LeaveType_mstAction"
	method='POST' commandName="screen_mstCMD">
	<div class="container">
	<div class="card">
			<div class="card-header">
				<h4 align="center">Search Leave Application</h4>
			</div>
			<!-- end of card-header -->
			<div class="card-body card-block">
			<div class="row">
			<div class="col-lg-6">
			<div class="form-group row">
			<div class="col-lg-4"><label>Start Date</label></div>
			<div class="col-lg-8"><input type="text" class="form-control" id="s_date" name="s_date"></div>
			</div></div>
			<div class="col-lg-6">
			<div class="form-group row">
			<div class="col-lg-4"><label>Start Date</label></div>
			<div class="col-lg-8"><input type="text" class="form-control" id="s_date" name="s_date"></div>
			</div></div>
			</div>
			</div>
			<div class="card-footer">
			
			<button type="submit" class="btn btn-primary">Search</button>
			<button type="submit" class="btn btn-success">Clear</button>
			
			</div>
			</div>
		<div class="card">
			
			<!-- end of card-header -->
			<div class="card-body card-block">
			
			
			<div class="table-responsive">
				<table border="1" class="table">
					<tr>
					<th>Ser no</th>
					<th>Name</th>
					<th>Leave Type</th>
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
		<!-- end of card -->
	</div>
	<!-- end of container -->

</form:form>

<c:url value="view_Leave" var="view" />
<form:form action="${view}" method="post" id="viewForm" name="viewForm"
	modelAttribute="vid">
	<input type="hidden" name="vid" id="vid" value="0" />
</form:form>

<script>

function viewData(id) {
	document.getElementById("vid").value = id;
	document.getElementById('viewForm').submit();
}
</script>