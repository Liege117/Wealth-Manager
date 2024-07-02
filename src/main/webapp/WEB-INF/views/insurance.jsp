<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="com.example.wealthmanager.entity.Insurance" import="java.util.List"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Insurance Details</title>
<style>
body {
	font-family: Georgia, serif;
	margin: 0;
	padding: 0;
	display: flex;
	align-items: center;
	justify-content: center;
	flex-direction: column;
	min-height: 100vh;
	background-color: #D3E4CD; /* Light green background color */
}

.container {
	background-color: #DCDCDD;
	padding: 20px;
	border-radius: 10px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
	width: 90%;
	max-width: 800px;
	margin-top: 20px;
	/* Adjusted margin to avoid being too close to the top */
}

h1, h2 {
	margin-bottom: 20px;
	color: #333333;
}

label {
	display: block;
	margin-bottom: 5px;
	font-weight: bold;
	color: #333333;
}

input[type="text"], input[type="date"], input[type="number"] {
	width: 100%;
	padding: 10px;
	margin-bottom: 15px;
	border: 1px solid #ccc;
	border-radius: 5px;
	box-sizing: border-box;
	font-size: 14px;
	color: #333333;
}

button {
	padding: 10px 20px;
	background-color: #2a9d8f; /* Coral background for buttons */
	border: none;
	color: black;
	border-radius: 5px;
	cursor: pointer;
	font-size: 16px;
	transition: background-color 0.3s ease;
}

button:hover {
	background-color: #0056b3;
}

table {
	width: 100%;
	border-collapse: collapse;
	margin-top: 20px;
}

th, td {
	padding: 12px;
	border: 1px solid #ddd;
	text-align: left;
	font-size: 14px;
	color: #333333;
}

th {
	background-color: #e76f51;
}

td {
	background-color: white
}

.form-section {
	margin-bottom: 30px;
}

.form-section:last-child {
	margin-bottom: 0;
}
#error-message {
            color: red;
            text-align: center;
            margin-top: 10px;
        }
</style>
</head>
<body>
	<div class="container">
		<h1>Insurance</h1>
		<h2>Available Policies</h2>
		<h3>${error}</h3>
		<table>
			<tr>
				<th>Name</th>
				<th>Policy Number</th>
				<th>Policy Type</th>
				<th>Start Date</th>
				<th>End Date</th>
				<th>Premium Amount</th>
				<th>Coverage Amount</th>
				<th>Paid</th>
				<th>Action</th>
			</tr>
			<%
			List<Insurance> policies = (List<Insurance>) request.getAttribute("policies");
			if (policies != null && !policies.isEmpty()) {
				for (Insurance policy : policies) {
			%>
			<tr>
				<td><%=policy.getName()%></td>
				<td><%=policy.getPolicyNumber()%></td>
				<td><%=policy.getPolicyType()%></td>
				<td><%=policy.getStartDate()%></td>
				<td><%=policy.getEndDate()%></td>
				<td>&#8377; <%=policy.getMonthlyAmount()%></td>
				<td>&#8377; <%=policy.getTotalAmount()%></td>
				<td>&#8377; <%=policy.getTotalPaid()%></td>
				<td>
					<form action="/insurance/pay" method="post" style="display:inline;">
						<input type="hidden" name="policyNumber" value="<%=policy.getPolicyNumber()%>" />
						<button type="submit">Pay</button>
					</form>
				</td>
			</tr>
			<%
				}
			} else {
			%>
			<tr>
				<td colspan="7" style="text-align: center;">No policies available</td>
			</tr>
			<%
			}
			%>
		</table>

		<div class="form-section">
			<h1>Add Insurance</h1>
			<form action="/insurance/add" method="post">
				<label for="name">Name:</label> 
				<input type="text" id="name" name="name" required> 
				
				<label for="policyNumber">Policy Number:</label> 
					<input type="text" id="policyNumber" name="policyNumber"
					required> 
					<label for="policyType">Policy Type:</label> 
					<input type="text" id="policyType" name="policyType" required> 
					
					<label for="startDate">Start Date:</label> 
					<input type="date" id="startDate" name="startDate" required> 
					
					<label for="endDate">End Date:</label> 
					<input type="date" id="endDate" name="endDate" required> 
					
					<label for="monthlyAmount">Monthly Amount:</label> 
					<input type="number" id="monthlyAmount" name="monthlyAmount" step="0.01" required> 
					
					<label for="totalAmount">Total Amount:</label> 
					<input type="number" id="totalAmount" name="totalAmount" step="0.01" required>

				<button class="button" type="submit">Submit</button>
			</form>
			<form action="/dashboard" method="get" class="button">
				<button type="submit" >Dashboard</button>
			</form>
		</div>
		
	</div>
</body>
</html>