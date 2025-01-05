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
<script nonce="${cspNonce}" type="text/javascript">
	window.history.forward();
	function noBack() {
		window.history.forward();
	}
	$(document).ready(function() {
		$('html').bind('cut copy paste', function(e) {
			e.preventDefault();
		});
		$("html").on("contextmenu", function(e) {
			return false;
		});
	});
</script>
</head>
<body>
	<form:form name="search_user_mst" id="search_user_mst" action="#"
		method='POST'>
		<div class="container" align="center">
			<div class="card">
				<div class="card-header">
					<h4>SEARCH USER MASTER</h4>
				</div>
				<div class="card-body card-block cue_text">
					<div class="row">
						<div class='google-input col-md-3 mb-2 select-wrapper'>
							<select name="access_lvl" class="form-control" id="access_lvl"><!-- onchange="access_lev(this.value)"  -->
								<option value="">--Select--</option>
								<option value="All">All</option>
								<option value="Username">User Id</option>
							</select> <label for="text-input">SEARCH BY<strong
								class="txt-clr">*</strong></label>
						</div>
						<div id="username_div" class='google-input col-md-3 mb-2 d-none' > <!-- style="display: none;" -->
							<input type="text" id="login_name" name="login_name"
								class="form-control disablecopypaste" autocomplete='off'
								maxlength="70" 
								placeholder="Search"> <i class="autocom_search_icon"></i></input><!-- onkeypress="return isNumberKey(event,this);" -->
							<label>USER ID<strong class="txt-clr">*</strong></label> <span
								class="errorClass" id="inward_no_lbl">${inward_no_lbl}</span>
						</div>
					</div>
				</div>
				<div class='card-footer' align='center'>
					<a href="search_user_mstUrl"><button type="button"
							class="btn btn-dark">
							<i class="fa fa-refresh"></i>&nbsp;Clear
						</button></a>
					<button type="button" class="btn btn-info" id="btn-search" value='Search'>
						<i class="fa fa-save"></i>&nbsp;Search
					</button><!--  onclick="Search();isValidateClientSide();" -->
				</div>
			</div>
		</div>
	</form:form>

	<div class="container d-none" id="divPrint" > <!-- style="display: none" -->
		<div class="tbl-color">
			<div class="col-md-12">
				<div class="table-responsive">
					<table id="applicanttbl1"
						class="table no-margin table-striped table-hover table-bordered report_print text-align">
						<thead>
							<tr>
								<th>ID</th>
								<th>User Name</th>
								<th>User Id</th>
								<th>Role</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>

	<c:url value="search_user_by_role" var="searchUrl" />
	<form:form action="${searchUrl}" method="post" id="searchForm"
		name="searchForm" modelAttribute="">
		<input type="hidden" name="access_lvl1" id="access_lvl1" value="0" />
		<input type="hidden" name="user_name" id="user_name" value="" />
	</form:form>

	<c:url value="update_user_mstUrl" var="updateUrl" />
	<form:form action="${updateUrl}" method="post" id="updateForm"
		name="updateForm" modelAttribute="updateid">
		<input type="hidden" name="updateid" id="updateid" value="0" />
	</form:form>

	<c:url value="search_user_mstUrl" var="mainFormUrl" />
	<form:form action="${mainFormUrl}" method="GET" id="mainForm"
		name="mainForm"></form:form>

	<script nonce="${cspNonce}">
	document.addEventListener('DOMContentLoaded', function() {
		document.getElementById('btn-search').onclick = function() {
			Search();;
		}; 
		 document.getElementById('access_lvl').onchange = function() {
			access_lev(this.value);
		};
// 		 document.getElementById('login_name').onkeypress = function() {
// 			 return isNumberKey(event,this);
// 			};
	});	
	
	
	function getUsername() {
			var wepetext = $("#login_name");
			wepetext.autocomplete({
				source : function(request, response) {
					$.ajax({
						type : 'POST',
						url : "getUsernameList?" + key + "=" + value,
						data : {
							userName : $("#login_name").val()
						},
						success : function(data) {
							var susval = [];
							var length = data.length - 1;
							var enc = data[length].substring(0, 16);
							for (var i = 0; i < data.length; i++) {
								susval.push(dec(enc, data[i]));
							}
							response(susval);
						}
					});
				},
				minLength : 1,
				autoFill : true,
			});
		}
		var page = 0;
		var length_menu = 10;
		var table = "";
		$(document).ready(function() {
			getUsername();

			mockjax1('applicanttbl1');
			table = dataTable('applicanttbl1');

			if ('${access_lvl1}' != "") {
				$("select#access_lvl").val('${access_lvl1}');

				if ('${access_lvl1}' == "Username") {
					$("div#username_div").removeClass("d-none");
					$("#login_name").removeClass("d-none");
					$("#login_name").val('${user_name}');
				}
				//$("div#divwatermark").val('').addClass('watermarked'); 
				//watermarkreport();
				$("#divPrint").removeClass("d-none");
				//document.getElementById("printId").disabled = false;	
			}
		});

		function Search() {
			if ($("#access_lvl").val() == "") {
				alert("Please Select Options");
				return false;
			} else {
				if ($("#access_lvl").val() == "Username") {
					if ($("#login_name").val() != "") {
						$("#user_name").val($("#login_name").val());
					} else {
						alert("Please Enter Username");
						return false;
					}
				}
				$("#access_lvl1").val($("#access_lvl").val());
				document.getElementById('searchForm').submit();
				table.ajax.reload();
			}
		}

		var access_lvl, sub_access_lvl, role_id;
		function access_lev(v) {
			$("#login_name").val("");
			var sub_lvl = "";
			if ((v != "") || (v != '')) {
				if (v == "All") {
					$("div#username_div").addClass("d-none");
					//document.getElementById('username_div').style.display = 'none';
				} else if (v == "Username") {
					$("div#username_div").removeClass("d-none");
					$("#login_name").removeClass("d-none");
					//document.getElementById('username_div').style.display = 'block';
					//document.getElementById('login_name').style.display = 'block';
					getUsername();
				} else {
					$("div#username_div").addClass("d-none");
					$("#login_name").addClass("d-none");
					//document.getElementById('username_div').style.display = 'none';
					document.getElementById('login_name').value = "";
					//document.getElementById('login_name').style.display = 'none';
				}
			} else {
				document.getElementById('username_div').style.display = 'none';
				document.getElementById('login_name').value = "";
				document.getElementById('login_name').style.display = 'none';
				alert("Access level is not defined.");
			}
		}

		function clearall() {
			document.getElementById('divPrint').style.display = 'none';
			//document.getElementById("printId").disabled = true;
			$("div#username_div").addClass("d-none");
			$("#login_name").addClass("d-none");
		}
		function printDiv() {
			var printLbl = [];
			var printVal = [];
			printDivOptimize('divPrint', 'Search User', printLbl, printVal);
		}
	</script>

	<script nonce="${cspNonce}">
		var newWin;
		function editData(userid) {
			document.getElementById('updateid').value = userid;
			document.getElementById('updateForm').submit();
		}

		function dataTable(tableName) {
			var table = $('#' + tableName)
					.DataTable(
							{
								"order" : [ [ 0, "asc" ] ],
								"lengthMenu" : [ [ 10, 25, 50, 100, -1 ],
										[ 10, 25, 50, 100, "All" ] ],
								"scrollY" : "400px",
								"scrollX" : true,
								"scrollCollapse" : true,
								"sPaginationType" : "full_numbers",
								"bLengthChange" : true,
								'language' : {
									'loadingRecords' : '&nbsp;',
									'processing' : '<div class="spinner">Processing...&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>'
								},
								ajax : '/Value',
								'processing' : true,
								"serverSide" : true,
								dom : 'lBfrtip',
								buttons : [ 'colvis',
										$.extend(true, {}, buttonCommon, {
											extend : 'excelHtml5',
											exportOptions : {
												columns : ':visible'
											}
										}), $.extend(true, {}, buttonCommon, {
											extend : 'pdfHtml5'
										}) ]
							});
			return table;
		}

		jsonObj = [];
		var FilteredRecords = 0;
		var buttonCommon = {
			exportOptions : {
				format : {
					body : function(data, row, column, node) {
						return data;
					}
				}
			}
		};
		function mockjax1(tableName) {
			$.mockjax({
				url : '/Value',
				responseTime : 1000,// 1 second
				response : function(settings) {
					$.ajaxSetup({
						async : false
					});
					data(tableName);
					this.responseText = {
						draw : settings.data.draw,
						data : jsonObj,
						recordsTotal : jsonObj.length,
						recordsFiltered : FilteredRecords
					};
				}
			});
		}

		function data(tableName) {
			// Default Parameter
			var table = $('#' + tableName).DataTable();
			var info = table.page.info();
			var currentPage = info.page;
			var pageLength = info.length;
			var startPage = info.start;
			var endPage = info.end;
			var Search = table.search();
			var order = table.order();
			var orderColunm = order[0][0] + 1; // colunm name
			var orderType = order[0][1]; // Type ex. (asc/desc)

			// Default Parameter
			var login_name = $('#login_name').val();
			jsonObj = [];

			$.post("getUserReportList1?" + key + "=" + value, {
				startPage : startPage,
				pageLength : pageLength,
				Search : Search,
				orderColunm : orderColunm,
				orderType : orderType,
				user_name1 : login_name
			}, function(j) {
				for (var i = 0; i < j.length; i++) {
					a = i + 1;
					jsonObj
							.push([ a,j[i].login_name, j[i].username,
									j[i].role ]); //,j[i].access_lvl, j[i].sub_access_lvl,j[i].userid
				}
			});
			$.post("getUserReportListTotalCount?" + key + "=" + value, {
				Search : Search,
				user_name1 : login_name
			}, function(j) {
				FilteredRecords = j;
			});
		}
	</script>