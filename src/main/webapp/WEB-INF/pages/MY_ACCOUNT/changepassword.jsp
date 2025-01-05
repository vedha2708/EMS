<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title></title>
<script type="text/javascript" src="layout_file/js/jquery-3.6.0.min.js"></script>

<form:form name="changePass" id="changePass"
	action="changePassword_Action" method='POST'>
	<div class="container">
		<div class="card">
			<div class="card-header">
				<h4 align="center">CHANGE PASSWORD</h4>
			</div>
			<!-- end of card-header -->

			<div class="card-body card-block cue_text">
				<div class="row">
					<div class='google-input col-md-3 mb-2'>
						<input type="text" id="" name=""
							class="form-control disablecopypaste" autocomplete='off'
							maxlength="" value="${userDetails.login_name}"></input>
						<!-- readonly="true"  value='${inward_no}'-->
						<label>NAME </label>
					</div>

					<div class='google-input col-md-3 mb-2'>
						<input type="text" id="" name=""
							class="form-control disablecopypaste" autocomplete='off'
							maxlength="" value="${userDetails.userName}"></input>
						<!-- readonly="true"  value='${inward_no}'-->
						<label>USER NAME </label>
					</div>
				</div>
				<div class="row">
					<div class='google-input col-md-3 mb-2'>
						<input type="password" id="old_pass" name="old_pass"
							class="form-control" maxlength="28" autocomplete="off" required>
						<label>OLD PASSWORD </label>
					</div>
					<div class='google-input col-md-3 mb-2'>
						<input id="new_pass" type="password" maxlength="28"
							name="new_pass"
							pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%!^\\&_.~*]).{8,28}$"
							class="form-control" autocomplete="off"
							title="Must contain at least one number and one uppercase and lowercase letter and one special character and at least 8 or 28 characters"
							autocomplete="off" required> <label>NEW PASSWORD
						</label>
					</div>
					<div class='google-input col-md-3 mb-2'>
						<input id="c_password" type="password" maxlength="28"
							name="c_password"
							pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%!^\\&_.~*]).{8,28}$"
							class="form-control" autocomplete="off" required> <label>CONFIRM
							NEW PASSWORD </label>
					</div>
				</div>
				<div class="row ">
					<label for="passid" class="text-danger  b-danger">
						<!-- b-info --> <b>1) Password should be a mix of alphabets,
							numerals and special characters ($#^@&%_.~!*) without any space
							in between.</b><br> <b>2) Password must contain both upper
							and lowercase letters.</b><br> <b>3) Password length should
							be between 8 to 28 characters.</b>
					</label>
				</div>
			</div>
			<!-- end of card-body -->

			<div class="card-footer" align="center">
				<a href="user_mstUrl"><button type="button" class="btn btn-dark">
						<i class="fa fa-refresh"></i>&nbsp;Clear
					</button></a>
				<button type="submit" class="btn btn-primary" value="Save" id="saveBtn"><i class="fa fa-save"></i>&nbsp;Save
				</button><!-- onclick="return isValid();" -->
			</div>
			<!-- end of card-footer -->

		</div>
		<!-- end of card -->
	</div>
	<!-- end of container -->

</form:form>

<script nonce="${cspNonce}">

document.addEventListener('DOMContentLoaded', function() {

	document.getElementById('saveBtn').onclick = function() {
		return isValid();
	};
});
function isValid()
{	
	if($("#old_pass").val()==""){
		alert("Please Enter Old Password");
		$("#old_pass").focus();
		return false;
	} 
	if($("#new_pass").val()==""){
		alert("Please Enter  New Password");
		$("#new_pass").focus();
		return false;
	} 
	if($("#c_password").val()==""){
		alert("Please Enter Confirm New Password");
		$("#c_password").focus();
		return false;
	} 
	if($("#new_pass").val() != $("#c_password").val()){
		alert("Passwords do not match.");
		$("#new_pass").focus();
		return false;
	} 
	return true;
}
</script>
