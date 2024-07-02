<%@page import="com.example.wealthmanager.entity.Transactions" import="java.util.List" import="java.text.DecimalFormat"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Transaction Details</title>
<style>
        body {
            font-family: Georgia, serif;
            background-color: #f2d0a9; /* Light coral background */
            margin: 0;
            padding: 20px;
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        .container {
            max-width: 800px;
            width: 100%;
            background-color: #DCDCDD; /* Dark cyan background for container */
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            color: black;
        }

        .cards {
            display: flex;
            justify-content: space-around;
        }

        .card {
            background-color: #e76f51; /* Teal background for cards */
            padding: 10px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 20%;
            text-align: center;
        }

        h1 {
            text-align: center;
            margin-bottom: 20px;
            color: #2a9d8f; /* Yellow color for headings */
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            padding: 8px;
            border: 1px solid #e9c46a; /* Yellow border for table */
            text-align: left;
        }

        th {
            background-color: #e76f51; /* Coral background for table headers */
            color: white;
        }

        .modal {
    		display: none; /* Hidden by default */
    		position: fixed; /* Stay in place */
    		z-index: 1; /* Sit on top */
    		left: 0;
    		top: 0;
    		width: 100%; /* Full width */
    		height: 100%; /* Full height */
    		background-color: rgb(0,0,0); /* Fallback color */
    		background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
    		display: flex;
    		align-items: center;
		}

/* Modal Content */
.modal-content {
    background-color: #fefefe;
    margin: 15% auto; /* 15% from the top and centered */
    padding: 20px;
    border: 1px solid #888;
    width: 80%; /* Could be more or less, depending on screen size */
    max-width: 400px; /* Maximum width */
    border-radius: 10px;
    box-shadow: 0 5px 15px rgba(0,0,0,0.3);
    position: relative;
}

/* The Close Button */
.close {
    color: #aaa;
    float: right;
    font-size: 28px;
    font-weight: bold;
}

.close:hover,
.close:focus {
    color: black;
    text-decoration: none;
    cursor: pointer;
}



form label {
    margin-bottom: 5px;
    font-weight: bold;
}

form input[type="number"],
form input[type="text"],
form input[type="date"],
form select {
    width: 80%;
    padding: 5px;
    margin-bottom: 15px;
    border: 1px solid #ccc;
    border-radius: 5px;
    box-sizing: border-box;
    font-size: 14px;
    color: #333333;
}

form button:hover {
    background-color: #0056b3;
}

#message {
    margin-top: 10px;
    font-size: 14px;
    color: red; /* Adjust color based on message type */
}

        .filter-container {
            margin-top: 30px;
            display: flex;
    		justify-content: space-between;
    		align-items: center;
    		margin-bottom: 20px;
    		margin-left:10px;
        }
        .filter-form{
        	display: flex;
    		align-items: center;
        }

        .filter-container label, .filter-container select {
            margin-right: 10px;
        }
        .filter-container label {
    		font-size: 14px; 
   	 		color: #333; 
		}

		.filter-container select {
    		padding: 5px; 
    		border-radius: 5px; 
    		border: 1px solid #ccc; 
    		font-size: 14px; 
		}
        .filter-container input[type="date"] {
   	 		padding: 5px; 
   	 		border-radius: 5px; 
    		border: 1px solid #ccc; 
    		font-size: 14px; 
		}
        .btn{
        	margin-top:5px;
        	padding: 10px 20px;
            background-color: #2a9d8f; 
            border: 1px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
         }
    </style>
</head>
<body>
	<div class="container">
		<h1>Transaction Details</h1>
		<div class="user-details">
			<p> <strong>Card Number:</strong> ${user.cardNumber}</p>
			<p><strong>Card Holder Name:</strong> ${user.cardHolderName}</p>
			<p><strong>Email:</strong> ${user.email}</p>
		</div>
		
		<div class="cards">
			<div class="card">
				<h3>Balance: &#8377; ${user.savings}</h3>
			</div>
			<div class="card">
				<h3>Total Income: &#8377; ${totalIncome}</h3>
			</div>
			<div class="card">
				<h3>Total Expense: &#8377; ${totalExpense}</h3>
			</div>
		</div>
		<h3 style="color:red">${message}</h3>
		<div class="filter-container">
			<form action="/transactions/filter" method="get">
				<label for="filterType">Filter by:</label> 
				<select id="filterType" name="filterType">
					<option value="monthly">Monthly</option>
					<option value="weekly">Weekly</option>
					<option value="date">Custom Date</option>
				</select>
				
				<div id="custom-date-filter">
					<label for="filterValue">Enter Date:</label> 
					<input type="date" id="filterValue" name="filterValue">
				</div>
				
				<button class="btn" type="submit">Apply Filter</button>
			</form>
			
			<button class="btn" onclick="document.getElementById('addTransactionModal').style.display='block'">
					Add Transaction</button>
		</div>
		<table>
			<thead>
				<tr>
					<th>Amount</th>
					<th>Type</th>
					<th>Subject</th>
					<th>Date</th>
				</tr>
			</thead>
			<tbody>
				<%
				List<Transactions> transactions = (List<Transactions>) request.getAttribute("transactions");
				if (transactions != null) {
					for (Transactions transaction : transactions) {
				%>
				<tr>
					<td> &#8377; <%=transaction.getAmount()%></td>
					<td><%=transaction.getType()%></td>
					<td><%=transaction.getSubject()%></td>
					<td><%=transaction.getDate()%></td>
				</tr>
				<%
				}
				} else {
				%>
				<tr>
					<td colspan="4">No transactions found.</td>
				</tr>
				<%
				}
				%>
			</tbody>
		</table>
	</div>

	<!-- The Modal -->
	<div id="addTransactionModal" class="modal" style="display: none;">
		<div class="modal-content">
			<span class="close"
				onclick="document.getElementById('addTransactionModal').style.display='none'">&times;</span>
			<form action="/transactions/add" method="post">
				<label for="amount">Amount:</label> 
				<input type="number" id="amount" name="amount" required><br> <br> 
				
				<label for="type">Type:</label> 
				<select id="type" name="type" required>
					<option value="Credit">Credit</option>
					<option value="Debit">Debit</option>
					
				</select><br> <br> <label for="recipient">Entity:</label> 
				<input type="text" id="subject" name="subject" required><br><br>
				
				<label for="date">Date:</label> 
				<input type="date" id="date" name="date" required>

				<button class="btn" type="submit">Add Transaction</button>
				<div id="message">${message}</div>
			</form>
		</div>
	</div>
	<a href="/dashboard">
		<button type="submit" class="btn"> 
		<span>DashBoard</span></button></a>
</body>
</html>