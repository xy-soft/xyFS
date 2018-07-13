<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:th="http://www.thymeleaf.org">

<head>
<!-- CSS INCLUDE -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous"></link>

<!-- EOF CSS INCLUDE -->
<style>
.pagination-centered {
	text-align: center;
}
.disabled {
	pointer-events: none;
	opacity: 0.5;
}
.pointer-disabled {
	pointer-events: none;
}
</style>

</head>
<body>

	<!-- START PAGE CONTAINER -->
	<div class="container-fluid">
		<!-- START PAGE SIDEBAR -->
		<!-- commented out     <div th:replace="fragments/header :: header">&nbsp;</div> -->
		<div class="page-header">
        	<h1>公共页面....</h1>
    	</div>
		<!-- END PAGE SIDEBAR -->
		<!-- PAGE TITLE -->
		<div class="page-title">
			<h2>
				<span class="fa fa-arrow-circle-o-left"></span> 文件清单
			</h2>
		</div>
		<!-- END PAGE TITLE -->
		<div class="row">
			<table class="table datatable">
				<thead>
					<tr>
						<th>#</th>
						<th>id</th>
						<th>FileName</th>
						<th>AppId</th>
					</tr>
				</thead>
				<tbody>
				<#list filelist as item>
 					<tr>
						<td >Text ...</td>
						<td >${item.fileid}</td>
						<td >${item.fileName}</td>
						<td><button type="button"
								class="btn btn-primary btn-condensed">
								<i class="glyphicon glyphicon-folder-open"></i>
							</button>
						</td>
					</tr>
				</#list>
				</tbody>
			</table>
			<!-- PAGE Nav -->
			
			<!-- PAGE NAV END-->
		</div>
		<!-- END PAGE CONTENT -->
		
		<footer class="footer">
		    <div class="container">
		        <p class="text-muted">©2016-2019 昕有灵犀</p>
		    </div>
		</footer>

		<!-- END PAGE CONTAINER -->
	</div>
		<script
  src="https://code.jquery.com/jquery-1.11.1.min.js"
  integrity="sha256-VAvG3sHdS5LqTT+5A/aeq/bZGa/Uj04xKxY8KM/w9EE="
  crossorigin="anonymous"></script>
 

	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
		integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
		crossorigin="anonymous"></script>

</body>
</html>