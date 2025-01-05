<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script type="text/javascript" src="layout_file/js/jquery-3.6.0.min.js"></script>
<div class="container"  id="divPrint">
         		<div style="margin:1.5rem 0;text-transform: uppercase;text-align: center;">
         		 <h5>Status of Inactive Users (Inactive since more than one month)</h5><!-- <strong>Schedule Details </strong> -->
         		 </div> <!-- end of div -->
         	
			<div id="divShow" style="display: block;"></div>
			<div  class="watermarked" data-watermark="" id="divwatermark" style="display: block;">
				<span id="ip"></span>
		     <table id="RoleReport" class="table no-margin table-striped  table-hover  table-bordered" >
						<thead>
							<tr>
								<th>User Name</th>	
								<th>Last Login date</th>	
							
							</tr>
						</thead> 
				<tbody>
						<c:forEach var="item" items="${list}" varStatus="num" >
									<tr>
										<td style="font-size: 15px;">${item.username}</td>	
											<td style="font-size: 15px;">${item.date}</td>	
										
									</tr>
							</c:forEach>
		                </tbody>
		        </table>
        	</div>	
		</div>	 
<script>
</script>