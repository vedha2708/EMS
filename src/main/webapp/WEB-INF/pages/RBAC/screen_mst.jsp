<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script type="text/javascript" src="layout_file/js/jquery-3.6.0.min.js"></script>

<script type="text/javascript">
	window.history.forward();
	function noBack() {
		window.history.forward();
	}
	$(document).ready(function () {
		$('html').bind('cut copy paste', function (e) {
			e.preventDefault();
		});
		   
		$("html").on("contextmenu",function(e){
			return false;
		}); 
		$('#screen_name').keyup(function() {
			this.value = this.value.toUpperCase();
		}); 
			
		if('${module_id1}' != ""){
		    $("div#divwatermark").val('').addClass('watermarked'); 
	   		$("div#divSerachInput").show();
	   		$("#divPrint").show();
	   		$("#screen_module_id").val('${module_id1}');
	   		sm_id_sel('${module_id1}','${sub_module_id1}');
	    }
	    
		$("#searchInput").on("keyup", function() {
  			var value = $(this).val().toLowerCase();
  			$("#ScreenReport tbody tr").filter(function() { 
  			$(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
  			});
	  	});
		    
		try{
	    	if(window.location.href.includes("msg="))
	    	{
	    		var url = window.location.href.split("?msg")[0];
	    		window.location = url;
	    	} 	
	    }
		catch (e) {} 
	});
	</script>
<form:form name="screen_mst" id="screen_mst" action="screen_mstAction"
	method='POST' commandName="screen_mstCMD">
	<div class="container">
		<div class="card">
			<div class="card-header">
				<h4 align="center">SCREEN MASTER</h4>
			</div>
			<!-- end of card-header -->
			<div class="card-body card-block">
				<div class="row mb-3">
					<div class="col-md-2">
						<label for="text-input">Screen Name <strong
							class="txt-clr">*</strong></label>
					</div>
					<div class="col-md-4">
						<input id="screen_name" name="screen_name"
							style="font-family: 'FontAwesome', Arial; text-transform: uppercase"
							class="form-control" autocomplete="off" maxlength="80">
					</div>
					<div class="col-md-2">
						<label for="text-input">Screen URL <strong
							class="txt-clr">*</strong></label>
					</div>
					<div class="col-md-4">
						<input id="screen_url" name="screen_url"
							style="font-family: 'FontAwesome', Arial;" class="form-control"
							autocomplete="off" maxlength="125">
					</div>
				</div>
				<div class="row mb-3">
					<div class="col-md-2">
						<label class=" form-control-label">Module <strong
							class="txt-clr">*</strong></label>
					</div>
					<div class="col-md-4">
						<select name="module.id" class="form-control"
							id="screen_module_id">
							<option value="0">--Select--</option>
							<c:forEach var="item" items="${getModuleNameList}"
								varStatus="num">
								<option value="${item.id}">${item.module_name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-md-2">
						<label class=" form-control-label">Sub-Module <strong
							class="txt-clr">*</strong></label>
					</div>
					<div class="col-md-4">
						<!-- <select name="" class="form-control" id ="modulelist"  onchange="myFunction(event)">  </select> -->
						<select name="sub_module.id" class="form-control"
							id="screen_submodule_id">
							<option value="0">--Select--</option>
						</select>
					</div>
				</div>
				<input type="hidden" name="screen_id" id="screen_id" />
			</div>
			<!-- end of card-body -->
			<div class="card-footer" align="center">

				<a href="screen_mstUrl"><button type="button"
						class="btn btn-dark">
						<i class="fa fa-refresh"></i>&nbsp;Clear
					</button></a>
				<button type="submit" class="btn btn-primary" id="save_btn"
					value='Save' onclick="return isValid();">
					<i class="fa fa-save"></i>&nbsp;Save
				</button>
				<button type="submit" class="btn btn-primary" id="update_btn"
					value='Update' onclick="return isValid();" style="display: none">
					<i class="fa fa-save"></i>&nbsp;Update
				</button>
				<button type="button" class="btn btn-info" id="btn-search"
					value='Search' onclick="return reportScreen();">
					<i class="fa fa-save"></i>&nbsp;Search
				</button>
			</div>
			<!-- end of card-footer -->
		</div>
		<!-- end of card -->
	</div>
	<!-- end of container -->

</form:form>


<div class="container">
	<div class="col-md-12">
		<div class="tbl-color">
			<div id="divSerachInput">
				<input id="searchInput" type="text"
					style="font-family: 'FontAwesome', Arial; margin-bottom: 5px; width: 50%;"
					placeholder="&#xF002; Search Word" size="35" class="form-control">
			</div>
			<table id="ScreenReport"
				class="table no-margin table-striped  table-hover  table-bordered report_print scrn_tbl">
				<thead>
					<tr>
						<th>Sr No</th>
						<th>Screen Name</th>
						<th>Module Name</th>
						<th>Sub Module Name</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="item" items="${list}" varStatus="num">
						<tr>
							<td>${num.index+1}</td>
							<td>${item.screen_name}</td>
							<td>${item.module_name}</td>
							<td>${item.submodule_name}</td>
							<td>${item.id}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>
<c:url value="screen_report" var="screen_reportUrl" />
<form:form action="${screen_reportUrl}" method="post" id="searchForm"
	name="searchForm">
	<input type="hidden" name="module_id1" id="module_id1" value="0" />
	<input type="hidden" name="sub_module_id1" id="sub_module_id1"
		value="0" />
</form:form>

<script>
   	function isValid()
   	{	
		if($("input#screen_name").val()==""){
			alert("Please Enter Screen Name");
			$("input#screen_name").focus();
			return false;
		}
   		if($("input#screen_url").val()==""){
   			alert("Please Select Screen URL");
   			$("input#screen_url").focus();
   			return false;
   		} 
   		if($("select#screen_module_id").val() == 0){
   			alert("Please Select Module");
   			$("select#screen_module_id").focus();
   			return false;
   		} 
   		if($("select#screen_submodule_id").val() == 0){
   			alert("Please Select Sub Module");
   			$("select#screen_submodule_id").focus();
   			return false;
   		} 
	   	return true;
	}
   	</script>
<script>
   	$(document).ready(function () {
	    $('select#screen_module_id').change(function() {
		    var mid = this.value; 
		    var sList = new Array();
		    var options = '<option value="'+"0"+'">'+ "--Select--" + '</option>';
			<c:forEach var="item" items="${getSubModuleNameList}" varStatus="num" >
				if('${item.module.id}' == mid){
					options += '<option value="${item.id}">${item.submodule_name}</option>';
				}
			</c:forEach>
			$("select#screen_submodule_id").html(options); 
		});  
   	});
  
   function sm_id_sel(mid,smid){
	   var options = '<option value="'+"0"+'">'+ "--Select--" + '</option>';
	   <c:forEach var="item" items="${getSubModuleNameList}" varStatus="num" >
		if('${item.module.id}' == mid){
			 if('${item.id}' == smid){
				options += '<option value="${item.id}" selected >${item.submodule_name}</option>';
			 }else{
				 options += '<option value="${item.id}" >${item.submodule_name}</option>';
			 }
		}	
		</c:forEach>
		$("select#screen_submodule_id").html(options);
   }
   
   function clearall()
   {		
   	document.getElementById('divPrint').style.display='none';
  
   	$("#searchInput").val("");
   	$("div#divSerachInput").hide();  
   	
   }
  
</script>

<script>
function reportScreen(){
	
	$("#module_id1").val($("select#screen_module_id").val());
	$("#sub_module_id1").val($("select#screen_submodule_id").val());
	
	document.getElementById('searchForm').submit();
}
</script>

<script>
function Update(id,sc_name,sc_url,mid,smid){	
	document.getElementById('screen_name').value=sc_name;
	document.getElementById('screen_url').value=sc_url;
	document.getElementById("screen_url").readOnly = true; 	
	$("select#screen_module_id").val(mid);	
	sm_id_sel(mid,smid);
	document.getElementById('screen_id').value=id;
	document.getElementById('update_btn').style.display='inline-block'; 
	document.getElementById('save_btn').style.display='none'; 
}

</script>
<script>
$(function(){
	$('#screen_name').keyup(function(){	
		yourInput= this.value.toUpperCase();
		re = /[0-9`~!@#$%^&*()_|+\-=?;:'",.<>\{\}\[\]\\]/gi;
		var isSplChar = re.test(yourInput);
		if(isSplChar)
		{
			//alert("Don't Enter Special Character");
			var no_spl_char = yourInput.replace(/[0-9`~!@#$%^&*()_|+\-=?;:'",.<>\{\}\[\]\\]/gi, '');
			$(this).val(no_spl_char);
		}
	});
});
</script>