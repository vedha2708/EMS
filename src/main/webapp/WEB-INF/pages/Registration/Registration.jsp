<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script type="text/javascript" src="layout_file/js/jquery-3.6.0.min.js"></script>

<form:form name="RegistrationFormid" id="RegistrationFormid" action="RegistrationAction"
	method='POST' commandName="RegistrationCMD">
	<div class="container">
		<div class="card">
			<div class="card-header">
				<h4 align="center">Registration</h4>
			</div>
			<!-- end of card-header -->
			<div class="card-body card-block">
				<div class="row mb-3">
					<div class="col-md-2">
						<label for="text-input">Name <strong
							class="txt-clr text-red">*</strong></label>
					</div>
					<div class="col-md-4">
						<input type="text" id="name" name="name" autocomplete="off" class="form-control">
					</div>
				</div>
				
				<div class="row mb-3">
					<div class="col-md-2">
						<label for="text-input">User Name <strong
							class="txt-clr text-red">*</strong></label>
					</div>
					<div class="col-md-4">
						<input type="text" id="username" name="username" autocomplete="off" class="form-control">
					</div>
				</div>
				
				
				<div class="row mb-3">
					<div class="col-md-2">
						<label for="text-input">Password <strong
							class="txt-clr text-red">*</strong></label>
					</div>
					<div class="col-md-4">
						<input type="password" name="password" id="password" autocomplete="off" class="form-control">
					</div>
				</div>
				
				
				
				<div class="row mb-3">
					<div class="col-md-2">
						<label for="text-input">Confirm Password <strong
							class="txt-clr text-red">*</strong></label>
					</div>
					<div class="col-md-4">
						<input type="password" name="cnf_password" id="cnf_password" autocomplete="off" class="form-control">
					</div>
				</div>
				
				<div class="row mb-3">
					<div class="col-md-2">
						<label for="text-input">Email <strong
							class="txt-clr text-red">*</strong></label>
					</div>
					<div class="col-md-4">
						<input type="text" id="email" name="email" class="form-control" autocomplete="off">
					</div>
				</div>
				
				<div class="row mb-3">
					<div class="col-md-2">
						<label for="text-input">Mobile No <strong
							class="txt-clr text-red">*</strong></label>
					</div>
					<div class="col-md-4">
						<input type="text" id="mobile_no" name="mobile_no" maxlength="10" autocomplete="off" class="form-control">
					</div>
				</div>
				
				<div class="row mb-3">
					<div class="col-md-2">
						<label for="text-input">Address <strong
							class="txt-clr text-red">*</strong></label>
					</div>
					<div class="col-md-4">
						<textarea rows="5" cols="5" id="address"  name="address" class="form-control"></textarea>
					</div>
				</div>
				
				<div class="row mb-3">
					<div class="col-md-2">
						<label for="text-input">Date of Birth <strong
							class="txt-clr text-red">*</strong></label>
					</div>
					<div class="col-md-4">
					<input type="date" id="dob" name="dob" autocomplete="off" class="form-control">
					</div>
				</div>
				
				<div class="row mb-3">
					<div class="col-md-2">
						<label for="text-input">Date of Joining <strong
							class="txt-clr text-red">*</strong></label>
					</div>
					<div class="col-md-4">
					<input type="date" id="joining_date" name="joining_date" autocomplete="off" class="form-control">
					</div>
				</div>
				
				
				<div class="row mb-3">
					<div class="col-md-2">
						<label for="text-input">Gender <strong
							class="txt-clr text-red">*</strong></label>
					</div>
					<div class="col-md-4">
					<label>Male</label>&nbsp;&nbsp;<input type="radio" id="gender1" name="gender" value="Male" 
					autocomplete="off" >
					<label>Female</label>&nbsp;&nbsp;
					<input type="radio" id="gender2" name="gender" value="Female" 
					autocomplete="off" >
					</div>
				</div>
				
				
				<div class="row mb-3">
					<div class="col-md-2">
						<label for="text-input">Blood Group <strong
							class="txt-clr text-red">*</strong></label>
					</div>
					<div class="col-md-4">
					<input type="text" id="blood_group" name="blood_group" autocomplete="off" class="form-control">
					</div>
				</div>
				
				
			</div>
			<!-- end of card-body -->
			<div class="card-footer" align="center">

				<a href="RegistrationUrl"><button type="button"
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
 	$(document).ready(function () {
    
 	});
</script>

