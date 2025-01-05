<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="container">
	<div class="card">
		<div class="card-header">
			<h5><i class="menu-icon fa fa-user"></i> My Profile</h5>
		</div> <!-- end of card-header -->
		<div class="card-body card-block" style="font-weight: bold;font-size: 18px;">
			<div class="row mb-3">
						<div class="col-md-2">
							<label for="text-input">User Name</label>
						</div>
						<div class="col-md-1">
							:
						</div>
						<div class="col-md-6">
							<label for="text-input">${mp.login_name}</label>
						</div>
			</div>
			<div class="row mb-3">
						<div class="col-md-2">
							<label for="text-input">User ID</label>
						</div>
						<div class="col-md-1">
							:
						</div>
						<div class="col-md-6">
							<label for="text-input">${mp.userName}</label>
						</div>
			</div>
			<%-- <c:if test="${mp.army_no != null}">
			</c:if> --%>
		</div> <!-- end of card-body -->
	</div> <!-- end of card -->
</div> <!-- end of container -->