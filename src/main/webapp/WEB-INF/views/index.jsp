<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container-fluid">
		<h3>KB 국민은행 거래내역 조회</h3>
		<h5>조회를 위해 KB국민은행 빠른조회 가입이 필요합니다.</h5>
		<div class="col-md-4">
			<form id="bankForm">
				<div class="form-group">
					<label for="example-text-input" class="col-2 col-form-label">계좌번호</label>
					<div class="col-10">
						<input class="form-control" type="text" name="account" placeholder="계좌번호" required>
					</div>
				</div>
				<div class="form-group">
					<label for="example-text-input" class="col-2 col-form-label">계좌비밀번호</label>
					<div class="col-10">
						<input class="form-control" type="password" 
						placeholder="계좌 비밀번호" 
						autocomplete="off"
						name="password" required>
					</div>
				</div>
				<div class="form-group">
					<label for="example-text-input" class="col-2 col-form-label">인터넷뱅킹 ID</label>
					<div class="col-10">
						<input class="form-control" type="text" 
						placeholder="인터넷뱅킹 ID" 
						autocomplete="off"
						name="id" required>
					</div>
				</div>
				<button type="submit" class="btn btn-primary" id="autoButton">확인</button>
			</form>
		</div>
		<table class="table">
			<thead>
				<tr>
					<th>거래일시</th>
					<th>이름</th>
					<th>입금금액</th>
					<th>출금금액</th>
					<th>잔액</th>
					<th>은행명</th>
					<th>입금방식</th>
					<th>통장표시</th>
				</tr>
			</thead>
			<tbody id='result'>
				
			</tbody>
		</table>
	</div>
	<script>
		$(document).ready(function(){
			$("#bankForm").submit(function(event){
				event.preventDefault();
				$("#result").html('');
				$.post("/bank", $(this).serialize(), function(data){
					$.each(data, function(i, item){
						var html = '<tr>';
						html += '<td>'+item.transfer_date+'</td>';
						html += '<td>'+item.name+'</td>';
						html += '<td>'+item.input+'</td>';
						html += '<td>'+item.output+'</td>';
						html += '<td>'+item.remain+'</td>';
						html += '<td>'+item.bank+'</td>';
						html += '<td>'+item.content+'</td>';
						html += '<td>'+item.display+'</td></tr>';
						$("#result").append(html);
					});
				});
			});
		});
	</script>
</body>
</html>