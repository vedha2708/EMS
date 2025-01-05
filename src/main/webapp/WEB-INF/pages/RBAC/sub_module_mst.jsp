<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html>
<head>
<script type="text/javascript" src="layout_file/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
		window.history.forward();
		function noBack() {
			window.history.forward();
		}
		
		$(document).ready(function () {
		
		     $('#submodule_name').keyup(function() {
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
	    		
	    		
	    		$("#searchInput").on("keyup", function() {
		  			var value = $(this).val().toLowerCase();
		  			$("#SubModuleReport tbody tr").filter(function() { 
		  			$(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
		  			});
		  		});
	    		
			});
	</script>
</head>
<body>

	<form:form name="sub_module_mst" id="sub_module_mst"
		action="sub_module_mstAction" method='POST'
		commandName="sub_module_mstCMD">
		<div class="container">
			<div class="card">
				<div class="card-header">
					<h4 align="center">SUB MODULE MASTER</h4>
				</div>
				<!-- end of card-header -->
				<div class="card-body card-block">
					<div class="row mb-3">
						<div class="col-md-2">
							<label for="text-input">Sub Module Name <strong
								class="txt-clr">*</strong></label>
						</div>
						<div class="col-md-4">
							<input id="submodule_name" name="submodule_name"
								style="font-family: 'FontAwesome', Arial; text-transform: uppercase;"
								class="form-control" autocomplete="off" maxlength="255">
						</div>
					</div>
					<div class="row mb-3">
						<div class="col-md-2">
							<label class=" form-control-label">Module <strong
								class="txt-clr">*</strong></label>
						</div>
						<div class="col-md-4">
							<!-- <select name="" class="form-control" id ="modulelist"  onchange="myFunction(event)">  </select> -->
							<select name="module.id" class="form-control" id="module_id">
								<option value="0">--Select--</option>
								<c:forEach var="item" items="${getModuleNameList}"
									varStatus="num">
									<option value="${item.id}">${item.module_name}</option>
								</c:forEach>
							</select>
						</div>
					</div>

					<div class="row mb-3">
						<div class="col-md-6">
							<div id="modulecheckboxes"></div>
						</div>
					</div>

					<input type="hidden" name="submodule_id" id="submodule_id" /> <input
						type="hidden" name="submodule_old" id="submodule_old" /> <input
						type="hidden" name="module_old" id="module_old" />

				</div>
				<!-- end of card-body -->

				<div class="card-footer" align="center">
					<a href="sub_module_mstUrl"><button type="button"
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

	<div class="container">
		<div class="col-md-12">
			<div class="tbl-color">
				<div id="divSerachInput">
					<input id="searchInput" type="text"
						style="font-family: 'FontAwesome', Arial; margin-bottom: 5px; width: 50%;"
						placeholder="&#xF002; Search Word" size="35" class="form-control">
				</div>

				<span id="ip"></span>
				<table id="SubModuleReport"
					class="table no-margin table-striped  table-hover  table-bordered report_print">
					<thead>
						<tr style="font-size: 15px;">
							<th width="10%">Sr No</th>
							<th width="40%">Sub Module Name</th>
							<th width="40%">Module Name</th>
							<th width="10%" style="text-align: center;" class='lastCol'>Action</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${list}" varStatus="num">
							<tr style="font-size: 13px;">
								<td width="10%">${num.index+1}</td>
								<td width="40%">${item.submodule_name}</td>
								<td width="40%">${item.module_name}</td>
								<td width="10%" align="center">${item.id}</td>
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
	   if($("input#submodule_name").val()==""){
			alert("Please Enter Sub Module Name");
			$("input#submodule_name").focus();
			return false;
		}
	   
   		 if($("select#module_id").val()== 0){
   			alert("Please Select Module");
   			$("select#modulelist").focus();
   			return false;
   		}  
   	
   	return true;
  
   }
      
</script>

	<script>

function Update(id,name,mid){	
	document.getElementById('submodule_name').value=name;
	document.getElementById('submodule_id').value=id; 	
	$("select#module_id").val(mid);
	document.getElementById('update_btn').style.display='inline-block'; 
	document.getElementById('save_btn').style.display='none'; 
}

</script>
</body>
</html>
