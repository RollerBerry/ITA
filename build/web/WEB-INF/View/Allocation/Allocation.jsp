<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Allocation, java.util.ArrayList, java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Table with Pagination and Message</title>
    <style>
        /* General Styles */
        body {
            background: #eee;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 0;
            box-sizing: border-box;
            font-family: Arial, sans-serif;
        }
        .container {
            max-width: 100%;
            padding: 20px;
        }
        .card {
            width: 100%;
            box-shadow: 0 20px 27px 0 rgba(0, 0, 0, 0.1);
            border-radius: 8px;
            overflow: hidden;
            background-color: #fff;
        }
        .card-header {
            background-color: #3f51b5;
            color: #fff;
            padding: 15px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        
        /* Display message styles */
        .dropdown-message {
            display: none; /* Hidden by default */
            padding: 15px;
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
            border-radius: 5px;
            position: fixed;
            top: 20px;
            right: 20px;
            z-index: 1000;
            transition: opacity 0.5s ease;
        }
        .dropdown-message.error {
            background-color: #f8d7da;
            color: #721c24;
            border-color: #f5c6cb;
        }
        .dropdown-message.show {
            display: block; /* Show when active */
        }

        /* Table and Pagination styles */
        .table-responsive {
            overflow-x: auto;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
       th, td {
    padding: 0.75rem 1.25rem;
    white-space: nowrap; /* Prevents text from wrapping */
    border-bottom: 1px solid #ddd;
}
td.description-cell {
    white-space: normal; /* Allows wrapping for long text */
    word-wrap: break-word; /* Breaks words that are too long to fit */
    max-width: 200px; /* Set a maximum width for the description cell */
}

td {
    vertical-align: middle; /* Centers text vertically within cells */
}

td.date-cell {
    white-space: nowrap; /* Ensures From Date and To Date stay on the same line */
}

/* Optional: reduce padding for date cells if needed */
td.date-cell {
    padding: 0.5rem 1rem;
}

        .text-end {
    text-align: end;
}

.action-buttons {
    display: flex;
    flex-direction: column; /* Stack buttons vertically */
    gap: 5px; /* Space between the buttons */
    align-items: flex-end; /* Align buttons to the right */
}

.action-buttons a {
    display: block; /* Make each button take the full width of the container */
    width: 80px; /* Set a consistent width */
    text-align: center; /* Center text inside the button */
    text-decoration: none;
    padding: 8px;
    border-radius: 4px;
    font-weight: bold;
    transition: background-color 0.3s, color 0.3s;
}

.action-buttons a:hover {
    color: #fff;
}

.action-buttons a[href*="updateAllocation"] {
    background-color: #3f51b5; /* Blue for Update */
    color: #fff;
    border: 1px solid #3f51b5;
}

.action-buttons a[href*="updateAllocation"]:hover {
    background-color: #303f9f; /* Darker blue on hover */
}

.action-buttons a[href*="deleteAllocation"] {
    background-color: #f44336; /* Red for Delete */
    color: #fff;
    border: 1px solid #f44336;
}

.action-buttons a[href*="deleteAllocation"]:hover {
    background-color: #d32f2f; /* Darker red on hover */
}

        .pagination {
            display: flex;
            justify-content: center;
            padding: 15px;
        }
        .pagination button {
            padding: 5px 10px;
            margin: 0 5px;
            background-color: #3f51b5;
            color: #fff;
            border: none;
            cursor: pointer;
            border-radius: 4px;
            transition: background-color 0.3s;
        }
        .pagination button:hover {
            background-color: #303f9f;
        }
    </style>
</head>
<body>
    <!-- Message Display -->
    <c:if test="${not empty message}">
        <div class="dropdown-message ${messageType} show">
            ${message}
        </div>
    </c:if>
           

    <div class="container">
        <div class="card">
            <div class="card-header">
                <h5>Allocations</h5>
                <a href="addAllocation" style="text-decoration: none; color: inherit;">Add Application</a>
            </div>
            
            <div class="table-responsive">
                <table class="table mb-0" id="allocationsTable">
                    <thead>
                        <tr>
                            <th>STT</th>
                            <th>Member</th>
                            <th>Project</th>
                            <th>Role</th>
                            <th>From Date</th>
                            <th>To Date</th>
                            <th>Effort Rate</th>
                            <th>Description</th>
                            <th class="text-end">Action</th>
                        </tr>
                    </thead>
                    <tbody id="tableBody">
                        <%
                            List<Allocation> allocations = (List<Allocation>) request.getAttribute("allocations");
                            if (allocations != null && !allocations.isEmpty()) {
                                int counter = 1;
                                for (Allocation allocation : allocations) {
                        %>
                            <tr>
                                <td><a href="viewdetails?id=<%= allocation.getID() %>"><%= counter++ %></a></td>
                                <td><%= allocation.getMemberId() %></td>
                                <td><%= allocation.getProjectId() %></td>
                                <td><%= allocation.getRoleId() %></td>
                                <td class="date-cell"><%= allocation.getFromDate() %></td>
<td class="date-cell"><%= allocation.getToDate() %></td>

                                <td><%= allocation.getEffort() %></td>
                               <td class="description-cell"><%= allocation.getDescription() %></td>

                                <td class="text-end">
                                    <div class="action-buttons">
                                        <a href="updateAllocation?id=<%= allocation.getID() %>">Update</a>
                                        <a href="deleteAllocation?id=<%= allocation.getID() %>">Delete</a>
                                    </div>
                                </td>
                            </tr>
                        <%
                                }
                            } else {
                        %>
                            <tr>
                                <td colspan="9" class="text-center">No allocations found.</td>
                            </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            </div>

            <!-- Pagination Controls -->
           <div class="pagination">
    <button onclick="prevPage()" id="prevBtn">Previous</button>
    <span id="pageNumber">Page 1</span> <!-- Display the current page number here -->
    <button onclick="nextPage()" id="nextBtn">Next</button>
</div>

        </div>
    </div>

    <script>
        const rowsPerPage = 5; // Rows per page
        let currentPage = 1;
        const tableBody = document.getElementById("tableBody");
        const rows = Array.from(tableBody.getElementsByTagName("tr"));
        const totalPages = Math.ceil(rows.length / rowsPerPage);

        function displayPage(page) {
            const start = (page - 1) * rowsPerPage;
            const end = start + rowsPerPage;

            rows.forEach((row, index) => {
                row.style.display = index >= start && index < end ? "table-row" : "none";
            });

            document.getElementById("pageNumber").textContent = `Page ${page} of ${totalPages}`;
        }

        function nextPage() {
            if (currentPage < totalPages) {
                currentPage++;
                displayPage(currentPage);
            }
        }

        function prevPage() {
            if (currentPage > 1) {
                currentPage--;
                displayPage(currentPage);
            }
        }

        // Initial display
        displayPage(currentPage);

        // Show the dropdown message if it exists
        window.onload = function() {
            const messageDiv = document.querySelector('.dropdown-message');
            if (messageDiv) {
                messageDiv.classList.add('show'); // Show the message

                // Auto-hide after 5 seconds
                setTimeout(() => {
                    messageDiv.classList.remove('show'); // Hide the message
                }, 5000);
            }
        };
    </script>
</body>
</html>
