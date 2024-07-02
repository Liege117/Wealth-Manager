<%@page import="com.example.wealthmanager.entity.Ledger"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="com.example.wealthmanager.entity.Insurance"
        import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Assets And Liabilities</title>
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
            margin-top: 20px; /* Adjusted margin to avoid being too close to the top */
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
        td{
        	background-color:white
        }
        
        .form-section {
            margin-bottom: 30px;
        }
        .form-section:last-child {
            margin-bottom: 0;
        }
        .delete-form {
            display: flex;
            align-items: center;
        }
        .delete-form input[type="text"] {
            margin-right: 10px;
            position:relative;
            top:7px;
            width: 400px;
            align-items:center;
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
    <h1>Asset and Liabilities</h1>
        <h2>Available Items</h2>
        <h3>${error}</h3>
        <table>
            <tr>
                <th>Name</th>
                <th>Item Type</th>
                <th>Coverage Amount</th>
                <th>Action</th>
            </tr>
            <% List<Ledger> items = (List<Ledger>) request.getAttribute("items");
               	if (items != null && !items.isEmpty()) {
                	for (Ledger item : items) {
            %>
            <tr>
            	<td><%= item.getLedgerName() %></td>
                <td><%= item.getLedgerType() %></td>
                <td>&#8377; <%= item.getCoverageAmount() %></td>
                <td>
                	<form action="/assets/pay" method="post" style="display:inline;">
						<input type="hidden" name="itemName" value="<%=item.getLedgerName()%>" />
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
            <h1>Add Items</h1>
            <form action="/assets/add" method="post">
                <label for="ledgerName">Item Name:</label>
                <input type="text" id="ledgerName" name="ledgerName" required>

                <label for="ledgerType">Item Type:</label>
                <select id="ledgerType" name="ledgerType" required>
                	<option value="Asset">Asset</option>
                	<option value="Liability">Liability</option>
                </select>
                
                <label for="coverageAmount">Coverage Amount:</label>
                <input type="number" id="coverageAmount" name="coverageAmount" step="0.01" required>

                <button class= "button" type="submit">Submit</button>
            </form>
        </div>

        <form action="/dashboard" method="get" class="button">
            <button type="submit">Dashboard</button>
        </form>
    </div>
</body>
</html>