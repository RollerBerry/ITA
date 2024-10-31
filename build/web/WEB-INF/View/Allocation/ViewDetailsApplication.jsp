<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Allocation"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Application Details</title>
    <style>
         body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            margin: 0;
            padding: 20px;
        }
        .container {
            max-width: 1200px;
            margin: 20px auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        .header {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
        }
        .detail-row {
            display: flex;
            justify-content: space-between;
            padding: 10px 0;
            border-bottom: 1px solid #e0e0e0;
        }
        .detail-row:last-child {
            border-bottom: none;
        }
        .label {
            font-weight: bold;
            color: #666;
        }
        .value {
            color: #333;
        }
        .buttons {
            display: flex;
            justify-content: space-between;
            margin-top: 20px;
        }
        .button {
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
        }
        .button.view {
            background-color: #4CAF50;
            color: white;
        }
        .button.delete {
            background-color: #f44336;
            color: white;
        }
    </style>
</head>
<body>

<% Allocation allocation = (Allocation) request.getAttribute("allocation"); %>

<div class="container">
    <h2 class="header">Application Details</h2>
    
    <div class="detail-row">
        <span class="label">Name:</span>
        <span class="value"><%= allocation.getMemberId() %></span>
    </div>
    
    <div class="detail-row">
        <span class="label">Project:</span>
        <span class="value"><%= allocation.getProjectId() %></span>
    </div>
    
    <div class="detail-row">
        <span class="label">Role:</span>
        <span class="value"><%= allocation.getRoleId() %></span>
    </div>
    
    <div class="detail-row">
        <span class="label">Start Date:</span>
        <span class="value"><%= allocation.getFromDate() %></span>
    </div>
    
    <div class="detail-row">
        <span class="label">End Date:</span>
        <span class="value"><%= allocation.getToDate() %></span>
    </div>
    
    <div class="detail-row">
      <span class="label">Effort:</span>
<span class="value"><%=  allocation.getEffort() %> %</span>

    </div>
    
    <div class="detail-row">
        <span class="label">Description:</span>
        <span class="value"><%= allocation.getDescription() %></span>
    </div>

    <div class="detail-row">
        <span class="label">Created By:</span>
        <span class="value"><%= allocation.getCreate_by() %></span>
    </div>
    
   
</div>

</body>
</html>
