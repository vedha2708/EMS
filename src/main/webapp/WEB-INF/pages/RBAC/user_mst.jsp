<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title></title>
<script type="text/javascript" src="layout_file/js/jquery-3.6.0.min.js"></script>
<!-- <script type="text/javascript" src="layout_file/js/jquery-2.1.4.min.js"></script> -->

<script type="text/javascript" src="js/AES_ENC_DEC/lib/aes.js"></script>
<script type="text/javascript" src="js/AES_ENC_DEC/lib/pbkdf2.js"></script>
<script type="text/javascript" src="js/AES_ENC_DEC/AesUtil.js"></script>
<link rel="stylesheet" href="js/autoComplate/autoComplate.css">
<link href="js/Calender/jquery-ui.css" rel="Stylesheet"></link>
<script src="js/Calender/jquery-ui.js" type="text/javascript"></script>
<link rel="stylesheet" href="js/Calender/Calender_jquery-ui.css">

<style nonce="${cspNonce}">
label {
	display: inline-block;
	text-align: left;
}

.ui-autocomplete {
	position: absolute;
	top: 27%;
	left: 33.1%;
	cursor: default;
}
</style>

<form:form name="user_mst" id="user_mst" action="user_mstAction"
	method='POST' commandName="user_mstCMD">
	<div class="container" align="center">
		<div class="card">
			<div class="card-header">
				<h4>CREATE USER MASTER</h4>
			</div>
			<!-- end of card-header -->
			<div class="card-body card-block cue_text">
				<div class="row">
					<div class='google-input col-md-3 mb-2'>
						<input type="text" id="user_name" name="user_name"
							placeholder="Search" autocomplete="off" class="form-control">
						<label>USER ID<strong class="txt-clr">*</strong></label> <span
							class="errorClass" id="user_name_lbl">${user_name_lbl}</span>
					</div>

					<div class='google-input col-md-3 mb-2'>
						<input type="text" id="login_name" name="login_name"
							class="form-control" autocomplete="off"> <label>USER
							NAME<strong class="txt-clr">*</strong>
						</label> <span class="errorClass" id="login_name_lbl">${login_name_lbl}</span>
					</div>

					<div class='google-input col-md-3 mb-2'>
						<input type="password" id="user_password" name="user_password"
							class="form-control" autocomplete="off" required
							pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"> <label>PASSWORD<strong
							class="txt-clr">*</strong></label> <span class="errorClass"
							id="user_password_lbl">${user_password_lbl}</span>
					</div>
					<div class='google-input col-md-3 mb-2'>
						<input type="password" id="user_re_password" maxlength="28"
							name="user_re_password" class="form-control" autocomplete="off"
							required pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}">
						<label>RE-PASSWORD<strong class="txt-clr">*</strong></label> <span
							class="errorClass" id="user_re_password_lbl">${user_re_password_lbl}</span>
					</div>

				</div>

				<div class="row">

					<div class='google-input col-md-3 mb-2 select-wrapper'>
						<select name="user_role_id" id="user_role_id" class="form-control"> <!-- style="width: 100%;" -->
							<option value="0">--Select--</option>
							<c:forEach var="item" items="${getRoleNameList}" varStatus="num">
								<option value="${item.roleId}">${item.role}</option>
							</c:forEach>
						</select> <label>ROLE<strong class="txt-clr">*</strong></label> <span
							class="errorClass" id="user_role_id_lbl">${user_role_id_lbl}</span>
					</div>

				</div>

				<div class="row ">
					<label for="passid" class="note-area">
					<p><b>Note:</b></p>
						<!-- b-info --> <b>1) Password should be a mix of alphabets,
							numerals and special characters ($#^@&%_.~!*) without any space
							in between.</b><br> <b>2) Password must contain both upper
							and lowercase letters.</b><br> <b>3) Password length should
							be between 8 to 28 characters.</b>
					</label>
				</div>
			</div>
			<!-- end of card-body -->

			<div class="card-footer" align='center'>
				<a href="user_mstUrl"><button type="button" class="btn btn-dark">
						<i class="fa fa-refresh"></i>&nbsp;Clear
					</button></a>
				<button type="submit" class="btn btn-primary" value="Save" id="saveBtn">
					<i class="fa fa-save"></i>&nbsp;Save
				</button><!-- onclick="return isValidateClientSide();"  -->

			</div>
			<!-- end of card-footer -->

			<input id="access_lve" name="access_lve1" type="hidden" /> <input
				id="sub_access_lve" name="sub_access_lve1" type="hidden" /> <input
				id="formation_code" name="user_formation_no" type="hidden" />

		</div>
		<!-- end of card -->
	</div>
	<!-- end of container -->
</form:form>

<script nonce="${cspNonce}">
document.addEventListener('DOMContentLoaded', function() {
	document.getElementById('saveBtn').onclick = function() {
		return isValidateClientSide();
	};
});

	function isValidateClientSide() {
		var errCount = 0;
		document.getElementById("user_name_lbl").innerHTML = "";
		document.getElementById("login_name_lbl").innerHTML = "";
		document.getElementById("user_password_lbl").innerHTML = "";
		document.getElementById("user_re_password_lbl").innerHTML = "";
		document.getElementById("user_role_id_lbl").innerHTML = "";

		if ($("#user_name").val().trim() == "") {
			errCount++;
			document.getElementById("user_name_lbl").innerHTML = "<i class='fa fa-exclamation'></i>Please Enter USER ID";
			$("#user_name").focus();
		}
		if ($("#login_name").val().trim() == "") {
			errCount++;
			document.getElementById("login_name_lbl").innerHTML = "<i class='fa fa-exclamation'></i>Please Enter USER NAME";
			$("#login_name").focus();
		}

		if ($("#user_password").val().trim() == "") {
			errCount++;
			document.getElementById("user_password_lbl").innerHTML = "<i class='fa fa-exclamation'></i>Please Enter USER PASSWORD";
			$("#user_password").focus();
		}
		if ($("#user_password").val().length < 8
				| $("#user_password").val().length > 28) {
			errCount++;
			document.getElementById("user_password_lbl").innerHTML = "<i class='fa fa-exclamation'></i>Please Enter PASSWORD AT LEAST 8 TO 28 DIGIT";
			$("#user_password").focus();
		}
		if ($("#user_re_password").val().trim() == "") {
			errCount++;
			document.getElementById("user_re_password_lbl").innerHTML = "<i class='fa fa-exclamation'></i>Please Enter USER RE-PASSWORD";
			$("#user_re_password").focus();
		}
		if ($("#user_re_password").val().length < 8
				| $("#user_re_password").val().length > 28) {
			errCount++;
			document.getElementById("user_re_password_lbl").innerHTML = "<i class='fa fa-exclamation'></i>Please Enter PASSWORD AT LEAST 8 TO 28 DIGIT";
			$("#user_re_password").focus();
		}
		if ($("select#user_role_id").val() == "0") {
			errCount++;
			document.getElementById("user_role_id_lbl").innerHTML = "<i class='fa fa-exclamation'></i>Please Select ROLE ID";
			$("#user_role_id").focus();
		}
		if (errCount > 0) {
			return false;
		} else {
			return true;
		}
		return true;
	}
</script>