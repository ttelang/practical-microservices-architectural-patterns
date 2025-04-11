
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
  

     <link rel="stylesheet" href="<c:url value='/static/css/bootstrap.min.css'  />">
     <script src="<c:url value='/static/js/jquery.min.js'  />"></script>
     <script src="<c:url value='/static/js/bootstrap.min.js'  />"></script>

   
    <link href="<c:url value='/static/css/font-awesome.min.css'  />" rel='stylesheet'/>
   
    <script src="<c:url value='/static/js/angular.js' />" ></script>
	<script src="<c:url value='/static/js/angular-route.js' />" ></script>
    <script src="<c:url value='/static/js/app.js' />"></script>

        
    <script src="<c:url value='/static/js/controller/application_controller.js' />"></script>     
    <script src="<c:url value='/static/js/service/application_service.js' />"></script>
</head>
<body ng-app="ecomApp">
 <div  ng-controller="ApplicationController  as appCtrl" data-ng-init="init()"  >
   <div class="tab-content">
				<div id="new" class="tab-pane fade in active">
					<div class="panel panel-info">
					 <div class="panel-heading">
							        <span style="color: red"> <strong>Products</strong>
							        </span>
				        </div>							      
						<div class="panel-body">
							<table class="table table-striped table table-sm">
								<thead>
									<tr>
										<th>Product</th>
										<th>Price</th>
										<th>stock</th>
										<th></th>
									</tr>
								</thead>
								 <tbody>
									<tr ng-repeat="product in products">
									    <td>{{product.description}}</span></td>  
										<td>{{product.price}}</span></td>
										<td>{{product.stock}}</span></td>
										<td><span><a class="btn btn-warning" role="button" ng-click="appCtrl.doOrder(product.id,product.price)">Order One Now !</a></span></td>
									</tr>
								</tbody>
							</table>
							
						</div>
					</div>

				</div>
				
				<br/>
				<br/>
				<hr>
				
				<div id="new" class="tab-pane fade in active">
					<div class="panel panel-info">
					 <div class="panel-heading">
							        <span style="color: red"> <strong>Orders</strong>
							        </span>
				        </div>							      
						<div class="panel-body">
							<table class="table table-striped table table-sm">
								<thead>
									<tr>
										<th>Id</th>
										<th>Price</th>
										<th>Number</th>
										<th>Product</th>
										<th>Status</th>
									</tr>
								</thead>
								 <tbody>
									<tr ng-repeat="order in orders">
									    <td>{{order.id}}</span></td>  
										<td>{{order.price}}</span></td>
										<td>{{order.number}}</span></td>
										<td>{{order.productDescription}}</span></td>
										<td>{{order.orderStatus}}</span></td>
									</tr>
								</tbody>
							</table>
							
						</div>
					</div>

				</div>
</div>
</body>
</html>