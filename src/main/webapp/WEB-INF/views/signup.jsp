<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="style.css">
    <style>
        body {
            font-family: Georgia, serif;
            background-color: #f8f9fa;
            margin: 60;
            padding: 20px;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .container {
        	margin-top:140px;
            background-color: #DCDCDD;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 80%;
            max-width: 600px;
            
        }
        h1 {
            text-align: center;
            margin-bottom: 20px;
        }
        label {
         	margin-left:20px;
            display: block;
            margin-bottom: 5px;
        }
        input, select {
            width: 80%;
            padding: 10px;
            margin-left:20px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        button {
         	margin-left:20px;
         	width:30%;
           	padding: 10px 20px;
            background-color: #2a9d8f; /* Coral background for buttons */
            border: none;
            color: black;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
        }
        button:hover {
            background-color: #0056b3;
        }
        h3{
         margin-left:20px;
        }
        
        .popup {
            position: relative;
            display: inline-block;
            cursor: pointer;
        }
        .popup .popuptext {
            visibility: hidden;
            width: 160px;
            background-color: #555;
            color: #fff;
            text-align: center;
            border-radius: 6px;
            padding: 8px 0;
            position: absolute;
            z-index: 1;
            bottom: 125%;
            left: 50%;
            margin-left: -80px;
        }
        .popup .popuptext::after {
            content: "";
            position: absolute;
            top: 100%;
            left: 50%;
            margin-left: -5px;
            border-width: 5px;
            border-style: solid;
            border-color: #555 transparent transparent transparent;
        }
        .popup .show {
            visibility: visible;
            -webkit-animation: fadeIn 1s;
            animation: fadeIn 1s;
        }
        @-webkit-keyframes fadeIn {
            from {opacity: 0;} 
            to {opacity: 1;}
        }
        @keyframes fadeIn {
            from {opacity: 0;}
            to {opacity:1 ;}
        }
    </style>
    <script>
        function showPopup(message) {
            var popup = document.getElementById("errorPopup");
            popup.innerText = message;
            popup.classList.add("show");
            setTimeout(function() { popup.classList.remove("show"); }, 3000);
        }
    </script>
</head>
 <title>Sign Up - Wealth Management System</title>
<body>
    <div class="container">
        <h1>Sign Up</h1>
        <div class="popup">
            <span class="popuptext" id="errorPopup"></span>
        </div>
        <form action="/signup" method="post">
            <!-- User Details -->
            <label for="name">Name:</label>
            <input type="text" id="name" name="name" required>

            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required>

            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>

            <label for="phoneNumber">Phone Number:</label>
            <input type="text" id="phoneNumber" name="phoneNumber" required>

            <label for="gender">Gender:</label>
            <select id="gender" name="gender" required>
                <option value="Male">Male</option>
                <option value="Female">Female</option>
                <option value="Other">Other</option>
            </select>

            <label for="dateOfBirth">Date of Birth:</label>
            <input type="date" id="dateOfBirth" name="dateOfBirth" required>

            <label for="savings">Savings:</label>
            <input type="number" id="savings" name="savings" required>

            <!-- Card details -->
            <h3>Enter your Card Details</h3>
            <label for="cardNumber">Card Number:</label>
            <input type="text" id="cardNumber" name="cardNumber" required>

            <label for="cardHolderName">Card Holder Name:</label>
            <input type="text" id="cardHolderName" name="cardHolderName" required>

            <label for="expiryDate">Expiry Date:</label>
            <input type="date" id="expiryDate" name="expiryDate" required>

            <button type="submit">Sign Up</button>
        </form>
        <br>
        <a href="/login"><button>Login</button></a>
    </div>
</body>
</html>
