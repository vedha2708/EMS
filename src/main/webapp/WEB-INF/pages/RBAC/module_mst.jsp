<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script type="text/javascript" src="layout_file/js/jquery-3.6.0.min.js"></script>

<script type="text/javascript"
	src="js/datatableJS/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="js/datatableJS/js/jquery.mockjax.js"></script>
<link rel="stylesheet" href="js/datatableJS/datatables.min.css">


<script type="text/javascript">
		window.history.forward();
		function noBack() {
			window.history.forward();
		}
		
		$(document).ready(function (){
			
			$('html').bind('cut copy paste', function (e) {
		        e.preventDefault();
		    });
		   
		    $("html").on("contextmenu",function(e){
		        return false;
		    }); 
		    
		    $('#module_name').keyup(function() {
		        this.value = this.value.toUpperCase();
		    }); 
		
		  
		    try{
	    		   if(window.location.href.includes("msg="))
	    			{
	    			 
	    				var url = window.location.href.split("?msg")[0];
	    				window.location = url;
	    			} 	
	    		}
	    		catch (e) {
	    			
	    		} 
			});
		
		
	</script>

<form:form name="module_mst" id="module_mst" action="module_mstAction"
	method='POST' commandName="module_mstCMD">
	<div class="container">
		<div class="card">
			<div class="card-header">
				<h4 align="center">MODULE MASTER</h4>
				<!-- <strong>Schedule Details </strong> -->
			</div>
			<div class="card-body card-block">
				<div class="row mb-3">
					<div class="col-md-2">
						<label for="text-input">Module Name <strong
							class="txt-clr">*</strong></label>
					</div>
					<div class="col-md-4">
						<input id="module_name" name="module_name"
							style="font-family: 'FontAwesome', Arial;" class="form-control"
							autocomplete="off" maxlength="20"> <input
							id="module_old_name" name="module_old_name" type="hidden"
							class="form-control" autocomplete="off">
					</div>
				</div>
			</div>
			<!-- end of card-body -->
			<div class="card-footer" align='center'>

				<a href="module_mstUrl"><button type="button"
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
			</div>
			<!-- end of card-footer -->
		</div>
		<!-- end of card -->
	</div>
	<!-- end of container -->
</form:form>

<div class="container" align="center">
	<div class="col-md-12">
		<div class="tbl-color">
			<table id="ModuleReport"
				class="table no-margin table-striped table-hover table-bordered report_print text-align">
				<thead style="background: #1d2e5c;">
					<tr>
						<th>Sr No</th>
						<th>Module Name</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="item" items="${list}" varStatus="num">
						<tr>
							<td>${num.index+1}</td>
							<td>${item.module_name}</td>
							<td>${item.id}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>

<script>
function isValid()
{
	if($("#module_name").val() == ""){
		alert("Please Enter Module Name");
		$("#module_name").focus();
		return false;
   	}
	return true;
}
</script>

<script>	
	function Update(id,name){	
		document.getElementById('module_name').value=name;
		document.getElementById('module_old_name').value=id;
		document.getElementById('update_btn').style.display='inline-block'; 
		document.getElementById('save_btn').style.display='none'; 
	}
</script>