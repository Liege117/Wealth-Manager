<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.time.LocalDateTime"
		 import="java.time.format.DateTimeFormatter" 
		 import="java.util.Locale"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - Wealth Management System</title>
    <style>
        body {
            font-family: Georgia, serif;
            background-color: #f2d0a9;
            margin: 0;
            padding: 20px;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .main {
            background-color: #DCDCDD;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 80%;
        }
        .wel {
    		display: grid;
    		grid-template-columns: 1fr 1fr 1fr;
    		background-color: #DCDCDD;
    		border-radius: 8px;
    		font-size: 26px;
    		font-weight: bold;
		}
		#wel-text{
			justify-self:stretch;
			text-align:center;

		}
		.time {	
			justify-self: end;
   		 	border-radius: 10px;
    		width:200px;
    		text-align:center;
		}
       	.btn {
       		color:#2a9d8f;
       		display: flex;
       		padding:20px;
    		justify-content: space-around;
  		  	gap: 10px;
    		grid-template-columns: 1fr 1fr 1fr 1fr;
    		grid-template-rows: auto;
		}
        .btn button {
        	align-item:center;
            padding: 25px;
            background-color: #e76f51;
            border: none;
            color: black;
            border-radius: 5px;
            cursor: pointer;
            font-size: 24px;
            transition: background-color 0.3s ease;
        }
        .btn button:hover {
            background-color: #0056b3;
        }
        .pie-chart{
        	display:grid;
        	grid-template-columns:1fr 1fr;
        	grid-template-rows:auto;
        	padding-left:20px;
        	margin-left:40px;
        }
        .card {
			align-self:center;
			margin-left:40px;
			background-color: #2a9d8f;
			padding: 20px;
			border-radius: 10px;
			box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
			width: 60%;
			height:50%;
			text-align: center;
		}
		.p{
		padding:10px;
		margin:0;
		}
		.logout-container {
    position: fixed;
    bottom: 20px;
    right: 20px;
}

.logout-button {
    background-color: #dc3545; /* Button background color */
    color: #fff; /* Button text color */
    padding: 10px 20px; /* Padding for the button */
    border: none; /* Remove default border */
    border-radius: 5px; /* Rounded corners */
    cursor: pointer; /* Pointer cursor on hover */
    font-size: 16px; /* Font size */
    transition: background-color 0.3s ease; /* Smooth background color transition */
}

.logout-button:hover {
    background-color: #c82333; /* Darker background color on hover */
}
    </style>
</head>
<body>
     <script>
        function updateTime() {
            const currentTimeElement = document.getElementById('current-time');
            const now = new Date();
            const hours = String(now.getHours()).padStart(2, '0');
            const minutes = String(now.getMinutes()).padStart(2, '0');
            const seconds = String(now.getSeconds()).padStart(2, '0');
            currentTimeElement.textContent = hours+":"+minutes+":"+seconds;
        }
        setInterval(updateTime, 1000);
        updateTime();
    </script>
    <div class="main">
        <div class="wel" >
            <p id="wel-text">Welcome ${user.name}!</p>
            <div></div>
            <div class="time">
                	<p><%
                        LocalDateTime now = LocalDateTime.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ENGLISH);
                        String formattedNow = now.format(formatter);
                        out.print(formattedNow);
                    %></p>
                <p id="current-time"></p>
            </div>
        </div>
        <div class="btn">
            <form action="/transactions" method="get">
                <button type="submit" class="btn1"><span>Transaction</span></button>
            </form>
            <form action="/stocks" method="get">
                <button type="submit" class="btn2"><span>Stocks</span></button>
            </form>
            <form action="/insurance" method="get">
                <button type="submit" class="btn3"><span>Insurance</span></button>
            </form>
            <form action="/assets" method="get">
                <button type="submit" class="btn3"><span>Assets Liabilities</span></button>
            </form>
        </div>

     <div class="pie-chart">
            <img src="data:image/png;base64,${chartImage}" alt="Pie Chart for Total Investment, Total Expense, Total Income">
     	<div class="card">
     		<h3>Balance Remaining: &#8377; ${savings}</h3>
			<h3>Total Income: &#8377; ${totalIncome}</h3>
			<h3>Total Expense: &#8377; ${totalExpense}</h3>
			<h3>Total Investment: &#8377; ${totalInvestment}</h3>
			<h3>Total Returns: &#8377; ${totalReturns}</h3>
		</div>
	</div>
</div>
</body>
</html>