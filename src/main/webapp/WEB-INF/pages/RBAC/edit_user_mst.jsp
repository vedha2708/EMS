<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript" src="layout_file/js/jquery-3.6.0.min.js"></script>

<style type="text/css">
textarea {
	text-transform: unset;
}
.btn-group-sm>.btn, .btn-sm {
	font-size: 12px;
	line-height: 1.5;
}
.glyphicon-refresh::before {
	content: "\e031";
}
.glyphicon {
	font-family: 'Glyphicons Halflings';
	font-style: normal;
	font-weight: 400;
	line-height: 1;
}
</style>
<script type="text/javascript">
	window.history.forward();
	function noBack() {
		window.history.forward();
	}
	$(document).ready(function () {
	    $('body').bind('cut copy paste', function (e) {
    	    e.preventDefault();
    	});
   	$("body").on("contextmenu",function(e){
    	return false;
    });
});
</script>
<body>
	<form:form name="edit_user_mst" action="edit_User_Mst_Action"
		method='POST' commandName="edit_User_MstCMD">
		<div class="container">
			<div class="card">
				<div class="card-header">
					<h5>Edit User Master</h5>
				</div> <!-- end of card-header -->
				<div class="card-body card-block">
					<div class="row mb-3">
								<div class="col-md-2">
									<label for="text-input">User Name <strong
										class="txt-clr">*</strong>
									</label>
								</div>
								<div class="col-md-4">
									<input type="hidden" id="userId" name="userId" value="${edit_User_MstCMD.userId}"> 
									<input id="login_name" name="login_name" style="font-family: 'FontAwesome', Arial;" value="${edit_User_MstCMD.login_name}" class="form-control" autocomplete="off" maxlength="70">
								</div>
					</div>
					<div class="row mb-3">
								<div class="col-md-2">
									<label for="text-input">User ID <strong class="txt-clr">*</strong>
									</label>
								</div>
								<div class="col-md-4">
									<input type="hidden" id="user_name" name="user_name" value="${edit_User_MstCMD.userName}"> <input id="user" name="user" style="font-family: 'FontAwesome', Arial;" readonly="readonly" value="${edit_User_MstCMD.userName}" maxlength="30" class="form-control" autocomplete="off" required>
								</div>
					</div>
					<div class="row mb-3">
								<div class="col-md-2">
									<label for="text-input">Password <strong class="txt-clr">*</strong>
									</label>
								</div>
								<div class="col-md-4">
									<input id="user_password" type="password" name="password" pattern="(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[$#^@&%_.~!*](?!.*\s).{8,28}"
										class="form-control" value="${password}" autocomplete="off" title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters">
								</div>
					</div>
					<div class="row mb-3">
								<div class="col-md-2">
									<label for="text-input">Re-Password <strong class="txt-clr">*</strong> </label>
								</div>
								<div class="col-md-4">
									<input id="user_re_password" type="password" name="re_password" pattern="(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[$#^@&%_.~!*](?!.*\s).{8,28}"
										class="form-control" value="${password}" autocomplete="off" required>
								</div>
					</div>
					<div class="row mb-3">
								<div class="col-md-2">
									<label for="text-input">Role <strong class="txt-clr">*</strong>
									</label>
								</div>
								<div class="col-md-4">
									<input type="hidden" id="role1" name="role1" class="form-control" value="${getRole[0].role}" autocomplete="off" required> 
									<select name="user_role_id" id="user_role_id" onchange="getaccess_lev(this.value);" style="width: 100%;" class="select2 narrow wrap">
										<c:forEach var="item" items="${getRoleNameList}"
											varStatus="num">
											<option value="${item.roleId}">${item.role}</option>
										</c:forEach>
									</select>
								</div>
					</div>
					<div class="row mb-3">
						<label for="passid">
						 <b>1) Password should be a mix of
								alphabets, numerals and special characters ( $#^@\&%_.~!*)
								without any space in between.</b><br> <b>2) Password must
								contain both upper and lowercase letters.</b><br> <b>3)
								Password length should be between 8 to 28 characters.</b>
						</label>
					</div>
				</div><!-- end of card-body -->
				
				<div class="card-footer">
					<input type="submit" class="btn btn-secondary btn-sm" value="Update" onclick="return isValid();">
					<a href="search_user_mstUrl" class="btn btn-danger btn-sm"> Cancel </a>
				</div> <!-- end of card-footer -->
				<input id="access_lve" name="access_lve1" type="hidden" /> 
				<input id="sub_access_lve" name="sub_access_lve1" type="hidden" />
			</div> <!-- end of card -->
		</div> <!-- end of container -->
	</form:form>
<script>
$(document).ready(function () {
	var a=$("input#user_name").val();
   	var role= $("input#role1").val();
 	var sList = new Array();
    var options = '<option value="'+"0"+'">'+ "--Select--" + '</option>';
	<c:forEach var="item" items="${getRoleNameList}" varStatus="num" >
		if('${item.role}' == role.trim()){
			options += '<option value="${item.roleId}" selected >${item.role}</option>';
			getaccess_lev('${item.roleId}');
		}else{
			 options += '<option value="${item.roleId}" >${item.role }</option>';
		 }
	</c:forEach>
	$("select#user_role_id").html(options); 
});
function isValid(){
	 if($("#login_name").val()==""){
		alert("Please Enter User Name");
		$("#login_name").focus();
		return false;
	} 
	if($("#user_name").val()==""){
		alert("Please Enter User ID");
		$("#user_name").focus();
		return false;
	} 
	if($("#user_password").val()==""){
		alert("Please Enter User Password");
		$("#user_password").focus();
		return false;
	} 
	if($("#user_re_password").val()==""){
		alert("Please Enter  Re-Password");
		$("#user_re_password").focus();
		return false;
	} 
	if($("#user_password").val()!=$("#user_re_password").val()){
		alert("Password and Re-Password didn't match");
		$("#user_re_password").focus();
		return false;
	}
	if($("select#user_role_id").val()=="0"){
		alert("Please Select Role Id");
		$("select#user_role_id").focus();
		return false;
	} 
	return true;
} 
</script>
<script>
	var access_lvl,sub_access_lvl,role_id;
   	function getaccess_lev(val) {
		document.getElementById('sub_access_lve').value="";
		role_id = val;
		<c:forEach var="item" items="${getRoleNameList}" varStatus="num" >
			if('${item.roleId}' == val){		
				$("#access_lve").val('${item.access_lvl}');
				$("#sub_access_lve").val('${item.sub_access_lvl}');
				access_lvl = '${item.access_lvl}';
				sub_access_lvl = '${item.sub_access_lvl}';
			}
		</c:forEach>
	}
</script>