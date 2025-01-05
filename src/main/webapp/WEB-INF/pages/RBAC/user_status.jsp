<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script type="text/javascript" src="layout_file/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="layout_file/js/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="js/AES_ENC_DEC/lib/aes.js"></script>
<script type="text/javascript" src="js/AES_ENC_DEC/lib/pbkdf2.js"></script>
<script type="text/javascript" src="js/AES_ENC_DEC/AesUtil.js"></script>
<link rel="stylesheet" href="js/autoComplate/autoComplate.css">
<link href="js/Calender/jquery-ui.css" rel="Stylesheet"></link>
<script src="js/Calender/jquery-ui.js" type="text/javascript"></script>

<script type="text/javascript"
	src="js/datatableJS/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="js/datatableJS/js/jquery.mockjax.js"></script>
<link rel="stylesheet" href="js/datatableJS/datatables.min.css">

<style nonce="${cspNonce}">
.ui-toolbar.ui-widget-header, .dataTables_scrollHead.ui-state-default {
	width: calc(100% - 8px) !important;
}

.watermarked::before {
	color: #3c3838;
	opacity: 1;
	width: calc(100% - 8px) !important;
}

.dataTables_wrapper {
	opacity: 0.9;
}

.card-body.card-block .row {
	justify-content: center;
}
</style>
<script nonce="${cspNonce}">
var page = 0;
var length_menu = 10;
var table ="";
$(document).ready(function() {
	
	mockjax1();
	var table = $('#searchUserTBL').DataTable({
		"order": [[ 0, "asc" ]],
		"lengthMenu": [[10, 25, 50], [10, 25, 50]],
		"scrollX":         true,
		"scrollY":        "400px",
	    "scrollCollapse": true,
	   	"sPaginationType": "full_numbers",
	    "bLengthChange" : true,        
	    'language': {
            'loadingRecords': '&nbsp;',
           'processing': '<div class="spinner"></div>' 
        },
        ajax: '/Value',
        'processing': true,
       	"serverSide": true,
        dom: 'lBfrtip',
		
   	});
	
	$('#search_id').on('click', function(){
    	jsonObj = [];
    	table.ajax.reload();
    });
	
	});
	
jsonObj = [];
var FilteredRecords = 0;
function mockjax1(){
	$.mockjax({
	    url: '/Value',
	    responseTime: 1000,// 1 second
	    response: function(settings){
	    	$.ajaxSetup({
				async : false
			});
			data();
			this.responseText = {
	    		draw: settings.data.draw,
				data: jsonObj,
				recordsTotal: jsonObj.length,
	            recordsFiltered: FilteredRecords
			};
	    }
	});
}
function data(){
	// Default Parameter
	var table = $('#searchUserTBL').DataTable();
	var info = table.page.info();
	var currentPage = info.page;
	var pageLength = info.length;
	var startPage = info.start;
	var endPage = info.end;
	var Search = table.search();
	var order = table.order();
	var orderColunm = order[0][0]+1; // colunm name
	var orderType = order[0][1]; // Type ex. (asc/desc)
	// Default Parameter
	jsonObj = [];
	// Advanced Search Parameter
	var index = $(table.column(order[0][0]).header()).index();
	if(index == "0"){
		orderColunm = "userid";
	}else if(index == "1"){
		orderColunm = "sub_access_lvl";
	}else if(index == "2"){
		orderColunm = "role_type";
	}else if(index == "3"){
		orderColunm = "unit_name";
	}else if(index == "4"){
		orderColunm = "username";
	}else if(index == "5"){
		orderColunm = "created_on";
     }
	// Advanced Search Parameter
	
	  var login_name=$("#login_name").val();
	  var access_lvl=$("#userid").val();
	
	$.post("searchUserStatus_getFilteredDataList_SQL?"+key+"="+value,{startPage:startPage,pageLength:pageLength,Search:Search,orderColunm:orderColunm,orderType:orderType,access_lvl:access_lvl,login_name:login_name},function(j) {
		for (var i = 0; i < j.length ; i++) {
			jsonObj.push([j[i][0],
// 				j[i][1],
				j[i][2],
				j[i][3],
				j[i][4],
// 				j[i][5],
				j[i][6]
				]);
		}
	});
	$.post("searchUserStatus_getTotalCount_SQL?"+key+"="+value,{Search:Search,access_lvl:access_lvl,login_name:login_name},function(j) {
		FilteredRecords = j;
	});
}
</script>

<form:form name="" id="" action="" method='POST' commandName="">
	<div class="container" align="center">
		<div class="card">
			<div class="card-header">
				<h4>USER ACTIVATION</h4>
			</div>
			<div class="card-body card-block cue_text">
				<div class="row">
					<div class='google-input offset-md-3 col-md-3 mb-2 select-wrapper'>
						<select name="userid" class="form-control" id="userid" onchange="">
							<!-- <option value="">--Select--</option> -->
							<option value="1">Active User</option>
							<option value="0">Deactive User</option>
						</select> <label>USER STATUS</label> <span class="errorClass"
							id="userid_lbl">${userid_lbl}</span>
					</div>
					<div class='google-input col-md-3 mb-2' id="username_div">
						<input id="login_name" class="form-control" maxlength="70"
							name="login_name" autocomplete="off"> <label>USER
							ID</label> <span class="errorClass" id="login_name_lbl">${login_name_lbl}</span>
					</div>
				</div>
			</div>
			<div class="card-footer" align="center">
				<a href="user_statusUrl"><button type="button"
						class="btn btn-dark">
						<i class="fa fa-refresh"></i>&nbsp;Clear
					</button></a>
				<button type="button" class="btn btn-info" id="search_id"
					value='Search' onclick="isValid();">
					<i class="fa fa-save"></i>&nbsp;Search
				</button>
			</div>
		</div>
	</div>
</form:form>

<div id="result" class="container">
	<div class="tbl-color">
		<div class="col-12 col-sm-12 col-md-12 form-group">
			<div class="table-responsive">
				<table id="searchUserTBL"
					class="table no-margin table-striped table-hover table-bordered report_print text-align">
					<thead>
						<tr>
							<th>Sr No</th>
<!-- 							<th>Branch Type</th> -->
							<th>Role</th>
							<th>User Id</th>
<!-- 							<th>Submission Date</th> -->
							<th>Action</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
</div>

<c:url value="ActiveDataURl" var="ActiveUrl" />
<form:form action="${ActiveUrl}" method="post" id="ActiveForm"
	name="ActiveForm" modelAttribute="">
	<input type="hidden" name="acid1" id="acid1" value="0" />
	<input type="hidden" name="status1" id="status1" value="0" />
	<input type="hidden" name="login_name1" id="login_name1" value="0" />
</form:form>

<c:url value="DeactiveDataURl" var="DeactiveURl" />
<form:form action="${DeactiveURl}" method="post" id="DeactiveForm"
	name="DeactiveForm" modelAttribute="">
	<input type="hidden" name="dcid1" id="dcid1" value="0" />
	<input type="hidden" name="status1" id="status1" value="1" />
	<input type="hidden" name="login_name1" id="login_name1" value="0" />
</form:form>

<c:url value="pdfUserStatusReport_url" var="merUrl2" />
<form:form action="${merUrl2}" method="post" id="search1" name="search1">
	<input type="hidden" name="typeReport" id="typeReport" value="pdfL" />
</form:form>
<script nonce="${cspNonce}">

$(document).ready(function () {
	
	getUsername();
	
	  if('${status1}' != ""){
		$("Select#userid").val('${status1}');	
		$("#login_name").val('${login_name1}');	
	  }  
		
	  try{
		   if(window.location.href.includes("msg="))
			{
				var url = window.location.href.split("&msg")[0];
				window.location = url;
			} 	
		}
		catch (e) {
			
		} 
});


function getUsername() {
	var wepetext=$("#login_name");
	wepetext.autocomplete({
		source: function( request, response ) {
			$.ajax({
	        	type: 'POST',
	        	url: "getUsernameList?"+key+"="+value,
	        	data: {userName:$("#login_name").val()},
	          	success: function( data ) {
					var susval = [];
	        	  	var length = data.length-1;
	        	  	var enc = data[length].substring(0,16);
		        	for(var i = 0;i<data.length;i++){
		        		susval.push(dec(enc,data[i]));
		        	}
		        	response( susval ); 
	          	}
	        });
		},
		minLength: 1,
		autoFill: true,     	     
	});
}

function clearall()
{	
	$("#login_name").val("");
}

function isValid(){
	var errcount=0;
	document.getElementById("userid_lbl").innerHTML="";

	if($("select#userid").val() == "")
	{
		errcount++;
         document.getElementById("userid_lbl").innerHTML="<i class='fa fa-exclamation'></i>Please Select User Status";
	}
    if(errcount>0){
    	return false;
    }
   return true;	
}
</script>

<script nonce="${cspNonce}">
function ActiveData(id){
	$("#acid1").val(id);	
	document.getElementById('ActiveForm').submit();
}

function DeactiveData(id){
	$("#dcid1").val(id);	
	document.getElementById('DeactiveForm').submit();
} 
</script>

<script nonce="${cspNonce}">
function getPDFExecl(pdf_excel){
	document.getElementById('typeReport').value=pdf_excel;
	document.getElementById('search1').submit();	
}
</script>

