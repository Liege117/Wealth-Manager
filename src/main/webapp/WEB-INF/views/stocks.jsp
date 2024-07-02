<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@	page import="com.example.wealthmanager.entity.Stock"
		 import="java.util.List"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Stocks</title>
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
            background-color: #f2d0a9;
        }
        .main {
            padding: 10px;
            height: 100%;
        }
        .container {
            width: 90%;
            max-width: 1200px;
            background-color: #DCDCDD;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
            margin-top: 20px;
        }
        h1 {
            color: #2a9d8f;
            text-align: center;
        }
        .cards {
            display: flex;
            justify-content: space-around;
        }
        .card {
            display: flex;
            justify-content: space-around;
            margin-bottom: 20px;
            background-color: #2a9d8f;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
            color: white;
        }
        .stock-table {
            width: 100%;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .stock-table table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            border-radius: 10px;
            overflow: hidden;
        }
        .stock-table th, .stock-table td {
            border: 1px solid #264653;
            padding: 12px;
            text-align: left;
        }
        .stock-table th {
            background-color: #e76f51;
        }
        .btn {
            background-color: #2a9d8f;
            color: black;
            padding: 10px 20px;
            margin: 20px 0;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s ease;
        }
        .btn:hover {
            background-color: #1f776d; /* Darker teal for button hover state */
        }
        .graph-container {
            width: 100%;
            max-width: 600px;
            height: 400px;
            margin: 20px auto;
        }
        .add-stock {
            background-color: #DCDCDD; /* Coral background for form */
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
        }
        .add-stock label {
            display: block;
            margin-bottom: 10px;
            font-weight: bold;
        }
        .add-stock input[type="text"],
        .add-stock input[type="number"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #2a9d8f;
            border-radius: 5px;
            box-sizing: border-box;
        }
        .add-stock button {
            background-color: #2a9d8f;
            color: black;
            padding: 10px 20px;
            margin: 20px 0;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s ease;
        }
        .add-stock button:hover {
            background-color: #1b3734;
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
            text-color: red;
            text-align: center;
            margin-top: 10px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Stocks</h1>
        <div class="cards">
        	<div class="card">
            	<h3>Total Investment: &#8377; ${totalInvestment}</h3>
            </div>
            <div class="card">
            	<h3>Total Returns: &#8377; ${totalReturns}</h3>
        	</div>
        </div>
        
        <form action="/dashboard" method="get">
                <button type="submit" class="btn">Dashboard</button>
        </form>
        <h3>${error}</h3>
        <div class="stock-table">
            <%
                List<Stock> stocks = (List<Stock>) request.getAttribute("stocks");
                String message = (String) request.getAttribute("message");
                String error = (String) request.getAttribute("error");
                if (message != null && !message.isEmpty()) {
            %>
                <p class="no-stocks-message"><%= message %></p>
            <%
                } else if (error != null && !error.isEmpty()) {
            %>
                <p class="error-message"><%= error %></p>
            <%
                } else if (stocks != null && !stocks.isEmpty()) {
            %><table>
                    <tr>
                        <th>Stock Name</th>
                        <th>Quantity</th>
                        <th>Value</th>
                        <th>Total Price</th>
                        <th>Current Value</th>
                        <th>Total Amount</th>
                        <th>Action</th>
                    </tr>
                    <%
                        for (Stock stock : stocks) {
                    %><tr>
                        <td><%= stock.getStockName() %></td>
                        <td><%= stock.getQuantity() %></td>
                        <td> &#8377; <%= stock.getOriginalPrice() %></td>
                        <td> &#8377;<%= stock.getTotalPrice() %></td>
                        <td> &#8377;<%= stock.getMarketPrice() %></td>
                        <td> &#8377; <%= stock.getAmount() %></td>
                        <td>
                        	<form action="/stocks/pay" method="post" style="display:inline;">
								<input type="hidden" name="stockName" value="<%=stock.getStockName()%>" />
								<button type="submit">Sell</button>
							</form>
						</td>
                    </tr>
                    <%
                        }
                    %>
                </table>
            <%
                }
            %>
        </div>
        <form action="/stocks/fetch" method="get">
            <button type="submit" class="btn">Fetch Prices</button>
        </form>
        <br><br>
        
        <div id="addStock" class="add-stock">
            <form action="/stocks/add" method="post" >
                <label for="stockName">Stock Name:</label>
                <input type="text" id="stockName" name="stockName" required>
                
                <label for="quantity">Quantity:</label>
                <input type="number" id="quantity" name="quantity" required>
                
                <label for="originalPrice">Original Price:</label>
                <input type="number" id="originalPrice" name="originalPrice" step="0.01" required>
                
                <button id="btn" type="submit" onclick="document.getElementById('addStock').style.display = 'block'">Add Stock</button>
            </form>
        </div>
    </div>
</body>
</html>